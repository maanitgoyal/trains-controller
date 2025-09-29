package trains;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import unsw.response.models.LoadInfoResponse;
import unsw.trains.TrainsController;
import unsw.utils.Position;
import unsw.utils.TrackType;

@Timeout(value = 5, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MyTests {
    // Write your tests here
    @Test
    public void testBreakableTrackDurabilityIsDecreased() {
        TrainsController controller = new TrainsController();
        controller.createStation("s1", "CentralStation", 0.0, 0.0);
        controller.createStation("s2", "CentralStation", 0.0, 12.0);
        controller.createTrack("t1-2", "s1", "s2");
        controller.createCargo("s1", "s2", "c1", 1000);
        assertDoesNotThrow(() -> {
            controller.createTrain("train1", "BulletTrain", "s1", List.of("s1", "s2"));
        });

        // Check durability decreased.
        controller.simulate(4);
        assertEquals(8, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(7, controller.getTrackInfo("t1-2").getDurability());
        controller.createCargo("s1", "s2", "c2", 1000);
        controller.simulate(4);
        assertEquals(5, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(3);
        assertEquals(4, controller.getTrackInfo("t1-2").getDurability());
        controller.createCargo("s1", "s2", "c3", 1000);
        controller.simulate(5);
        assertEquals(2, controller.getTrackInfo("t1-2").getDurability());

        controller.simulate(6);
        assertEquals(1, controller.getTrackInfo("t1-2").getDurability());
        controller.simulate(6);
        assertEquals(0, controller.getTrackInfo("t1-2").getDurability());
        assertEquals(TrackType.BROKEN, controller.getTrackInfo("t1-2").getType());
    }
}
