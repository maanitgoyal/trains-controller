package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class CargoTrain extends IntermediateTrainAndCargo {
    private static final double MAX_SPEED = 3;
    private static final int MAX_LOAD = 5000;
    public CargoTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String,
    Track> tracks) throws InvalidRouteException {
        super(
            trainId,
            type,
            stationId,
            isRouteValid(tracks, route),
            pos,
            tracks,
            MAX_SPEED,
            false,
            true,
            MAX_LOAD,
            false
        );
    }
}
