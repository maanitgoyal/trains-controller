package unsw.trains;

import unsw.utils.Position;

public class CargoStation extends Station {
    public CargoStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            4,
            0,
            Double.POSITIVE_INFINITY
        );
    }
}
