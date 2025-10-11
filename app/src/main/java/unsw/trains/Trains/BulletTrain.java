package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class BulletTrain extends IntermediateTrainAndCargo {
    public BulletTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String, Track> tracks) throws InvalidRouteException {
        super(
            trainId,
            type,
            stationId,
            route,
            pos,
            tracks,
            5,
            true,
            true,
            5000,
            true
        );
    }
}
