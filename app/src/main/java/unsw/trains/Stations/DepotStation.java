package unsw.trains.Stations;

import unsw.utils.Position;

public class DepotStation extends Station {
    private static final int MAX_TRAINS = 8;
    public DepotStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            MAX_TRAINS,
            false,
            false
        );
    }
}
