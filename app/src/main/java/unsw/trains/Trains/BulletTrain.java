package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class BulletTrain extends IntermediateTrainAndCargo {
    private static final double MAX_SPEED = 5;
    private static final int MAX_LOAD = 5000;
    public BulletTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String,
    Track> tracks) throws InvalidRouteException {
        super(
            trainId,
            type,
            stationId,
            route,
            pos,
            tracks,
            MAX_SPEED,
            true,
            true,
            MAX_LOAD,
            isThereATrack(tracks, route.get(0), route.get(route.size() - 1)) != null
        );
    }
}
