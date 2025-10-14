package unsw.trains.Stations;

import unsw.utils.Position;

public class CentralStation extends Station {
    private static final int MAX_TRAINS = 8;
    public CentralStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            MAX_TRAINS,
            true,
            true
        );
    }
}
