package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Loads.Load;
import unsw.trains.Loads.PassengerLoad;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class RepairTrain extends Train {
    private static final double MAX_SPEED = 2;
    private static final int MAX_LOAD = 3500;
    public RepairTrain(String trainId, String type, String stationId, List<String> route, Position pos,
            HashMap<String, Track> tracks) throws InvalidRouteException {
        super(
            trainId,
            type,
            stationId,
            isRouteValid(tracks, route),
            pos,
            MAX_SPEED,
            true,
            false,
            MAX_LOAD,
            false
        );
    }

    /**
     * Gets the number of mechanics currently on the train.
     *
     * @return the number of mechanics
     */
    public int getMechanicsOnTrain() {
        int s = 0;
        for (Load ld : super.getTrainLoads()) {
            if (ld instanceof PassengerLoad) {
                PassengerLoad oth = (PassengerLoad) ld;
                if (oth.isPassengerMechanic()) s += 1;
            }
        }
        return s;
    }
}
