package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Helper;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class PassengerTrain extends Train {
    public PassengerTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String, Track> tracks) throws InvalidRouteException {
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
}
