package unsw.trains.Stations;

import unsw.utils.Position;

public class CargoStation extends Station {
    private static final int MAX_TRAINS = 4;
    public CargoStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            MAX_TRAINS,
            false,
            true
        );
    }
}
