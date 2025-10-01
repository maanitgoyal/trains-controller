package unsw.trains;

import unsw.utils.Position;

public class DepotStation extends Station {
    public DepotStation(String stationId, String type, double x, double y) {
        super(
            stationId,
            type,
            new Position(x, y),
            8,
            0,
            0
        );
    }
}
