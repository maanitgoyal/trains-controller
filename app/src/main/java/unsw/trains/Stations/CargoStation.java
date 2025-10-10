package unsw.trains.Stations;

import unsw.utils.Position;

public class CargoStation extends Station {
    public CargoStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            4,
            false,
            true
        );
    }
}
