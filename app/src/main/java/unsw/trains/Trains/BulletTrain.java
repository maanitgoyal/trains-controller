package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Helper;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public class BulletTrain extends Train implements InterfaceTrainAndCargo {
    public BulletTrain(String trainId, String type, String stationId, List<String> route, Position pos, HashMap<String, Track> tracks) throws InvalidRouteException {
        super(
            trainId,
            type,
            stationId,
            route,
            pos,
            5,
            5,
            true,
            true,
            5000,
            (Helper.isThereATrack(tracks, route.get(0), route.get(route.size() - 1)) != null)
        );
    }
}
