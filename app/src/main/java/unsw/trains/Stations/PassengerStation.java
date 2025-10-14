package unsw.trains.Stations;

import unsw.utils.Position;

public class PassengerStation extends Station {
    private static final int MAX_TRAINS = 4;
    public PassengerStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            MAX_TRAINS,
            true,
            false
        );
    }
}
