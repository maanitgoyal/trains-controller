package unsw.trains;

import java.util.HashMap;

public class CargoLoad extends Load {
    public CargoLoad(String startStationId, String destStationId, String loadId, String loadType, int weight, HashMap<String, Station> stations) {
        super(startStationId, destStationId, loadId, loadType, weight, stations);
    }
}
