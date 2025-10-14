package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class PassengerTrain extends Train {
    private static final double MAX_SPEED = 2;
    private static final int MAX_LOAD = 3500;
    public PassengerTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String,
    Track> tracks) throws InvalidRouteException {
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
}
