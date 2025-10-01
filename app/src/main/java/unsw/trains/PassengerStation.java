package unsw.trains;

import unsw.utils.Position;

public class PassengerStation extends Station {
    public PassengerStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            2,
            Double.POSITIVE_INFINITY,
            0
        );
    }
}
