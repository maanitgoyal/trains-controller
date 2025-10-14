package trains;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import unsw.response.models.LoadInfoResponse;
import unsw.trains.TrainsController;
import unsw.utils.TrackType;

@Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MyTests {
    // Write your tests here
    @Test
    public void depotStationShouldNotHaveAnyLoad() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0, 0);
        controller.createStation("s2", "DepotStation", 0, 10);
        controller.createStation("s3", "CentralStation", 0, 0);
        controller.createTrack("t1-2", "s1", "s2");
        controller.createTrack("t2-3", "s2", "s3");
        controller.createCargo("s1", "s3", "c1", 1000);
        assertDoesNotThrow(() -> {
            controller.createTrain("train1", "BulletTrain", "s1", List.of("s1", "s2", "s3"));
        });
        assertEquals(List.of(new LoadInfoResponse("c1", "Cargo")), controller.getStationInfo("s1").getLoads());
        controller.simulate(3);
        // Train should only stop at DepotStation
        assertEquals(1, controller.getTrainInfo("train1").getLoads().size());
        assertEquals(controller.getTrainInfo("train1").getLocation(), "s2");
    }

    @Test
    public void perishableIsPerishedAtStation() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0, 0);
        controller.createStation("s2", "CentralStation", 0, 50);
        controller.createPerishableCargo("s1", "s2", "c1", 1000, 3);
        assertEquals(1, controller.getStationInfo("s1").getLoads().size());
        controller.simulate(3);
        assertEquals(0, controller.getStationInfo("s1").getLoads().size());
    }
    
    @Test
    public void testPerishableCargo() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0, 0);
        controller.createStation("s2", "CentralStation", 0, 50);
        controller.createStation("s3", "CentralStation", 0, 100);
        controller.createTrack("t1-2", "s1", "s2");
        controller.createTrack("t2-3", "s2", "s3");
        controller.createPerishableCargo("s1", "s3", "c1", 1000, 20);
        assertDoesNotThrow(() -> {
            controller.createTrain("train1", "BulletTrain", "s1", List.of("s1", "s2", "s3"));
        });
        // Check that the load was created at the station.
        assertEquals(List.of(new LoadInfoResponse("c1", "PerishableCargo")), controller.getStationInfo("s1").getLoads());

        // After the first tick, the train with id t1 should have picked up the cargo.
        controller.simulate();
        assertEquals(0, controller.getStationInfo("s1").getLoads().size());
        assertEquals(List.of(new LoadInfoResponse("c1", "PerishableCargo")), controller.getTrainInfo("train1").getLoads());
        controller.simulate(20);

        // Train should not contain any cargo
        assertEquals(0, controller.getTrainInfo("train1").getLoads().size());
    }

    @Test
    public void loadNotEmbarked() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0, 0);
        controller.createStation("s2", "CentralStation", 0, 50);
        controller.createTrack("t1-2", "s1", "s2");
        controller.createCargo("s1", "s2", "c1", 5000);
        controller.createCargo("s1", "s2", "c2", 5000);
        assertDoesNotThrow(() -> {
            controller.createTrain("train1", "BulletTrain", "s1", List.of("s1", "s2"));
        });
        // Check that the load was created at the station.
        assertEquals(List.of(new LoadInfoResponse("c1", "Cargo"), new LoadInfoResponse("c2", "Cargo")),
        controller.getStationInfo("s1").getLoads());

        // After the first tick, the train with id train1 should have picked only 1 cargo
        controller.simulate();
        assertEquals(1, controller.getStationInfo("s1").getLoads().size());
        assertEquals(List.of(new LoadInfoResponse("c1", "Cargo")), controller.getTrainInfo("train1").getLoads());
    }

    @Test
    public void testBreakableTrackDurabilityIsDecreased() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0.0, 0.0);
        controller.createStation("s2", "CentralStation", 0.0, 12.0);
        controller.createTrack("t1-2", "s1", "s2", true);
        controller.createCargo("s1", "s2", "c1", 1000);
        assertDoesNotThrow(() -> {
            controller.createTrain("train1", "BulletTrain", "s1", List.of("s1", "s2"));
        });

        // Check durability decreased.
        controller.simulate(3);
        assertEquals(8, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(7, controller.getTrackInfo("t1-2").getDurability());
        controller.createCargo("s1", "s2", "c2", 1000);
        controller.simulate(3);
        assertEquals(5, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(4, controller.getTrackInfo("t1-2").getDurability());
        controller.createCargo("s1", "s2", "c3", 1000);
        controller.simulate(3);
        assertEquals(2, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(1, controller.getTrackInfo("t1-2").getDurability());
        controller.simulate(3);
        assertEquals(0, controller.getTrackInfo("t1-2").getDurability());
        assertEquals(TrackType.BROKEN, controller.getTrackInfo("t1-2").getType());
    }

    @Test
    public void testIfRepairingWorks() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0.0, 0.0);
        controller.createStation("s2", "CentralStation", 0.0, 12.0);
        controller.createTrack("t1-2", "s1", "s2", true);
        controller.createCargo("s1", "s2", "c1", 1000);
        assertDoesNotThrow(() -> {
            controller.createTrain("train1", "BulletTrain", "s1", List.of("s1", "s2"));
        });

        // Check durability decreased.
        controller.simulate(3);
        assertEquals(8, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(7, controller.getTrackInfo("t1-2").getDurability());
        controller.createCargo("s1", "s2", "c2", 1000);
        controller.simulate(3);
        assertEquals(5, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(4, controller.getTrackInfo("t1-2").getDurability());
        controller.createCargo("s1", "s2", "c3", 1000);
        controller.simulate(3);
        assertEquals(2, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(1, controller.getTrackInfo("t1-2").getDurability());
        controller.simulate(3);
        assertEquals(0, controller.getTrackInfo("t1-2").getDurability());
        assertEquals(TrackType.BROKEN, controller.getTrackInfo("t1-2").getType());

        assertDoesNotThrow(() -> {
            controller.createTrain("train2", "RepairTrain", "s2", List.of("s2", "s1"));
        });
        controller.createPassenger("s2", "s1", "p1", true);
        controller.createPassenger("s2", "s1", "p2", true);
        controller.createPassenger("s2", "s1", "p3", true);
        controller.createPassenger("s2", "s1", "p4", true);
        controller.createPassenger("s2", "s1", "p5", true);
        controller.simulate();
        assertEquals(5, controller.getTrainInfo("train2").getLoads().size());
        assertEquals(10, controller.getTrackInfo("t1-2").getDurability());
        controller.simulate();
        assertEquals(0, controller.getStationInfo("s2").getTrains().size());
    }
}
