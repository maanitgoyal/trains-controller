package unsw.trains.Loads;

import java.util.HashMap;

import unsw.trains.Stations.Station;

public class CargoLoad extends Load {
    public CargoLoad(String startStationId, String destStationId, String loadId, String loadType, int weight, HashMap<String, Station> stations) {
        super(startStationId, destStationId, loadId, loadType, weight, stations);
    }
}
