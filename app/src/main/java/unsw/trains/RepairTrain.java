package unsw.trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.utils.Position;

public class RepairTrain extends Train {
    public RepairTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String, Track> tracks) throws InvalidRouteException {
        super(
            trainId,
            type,
            stationId,
            Helper.isRouteValid(tracks, route),
            pos,
            2,
            2,
            true,
            false,
            3500,
            false
        );
    }

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
