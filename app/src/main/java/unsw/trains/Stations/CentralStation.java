package unsw.trains.Stations;

import unsw.utils.Position;

public class CentralStation extends Station {
    public CentralStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            8,
            true,
            true
        );
    }
}
