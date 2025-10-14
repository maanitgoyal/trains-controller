package unsw.trains.Loads;

import java.util.HashMap;

import unsw.trains.Stations.Station;

public class PassengerLoad extends Load {
    private boolean isMechanic;

    public PassengerLoad(String startStationId, String destStationId, String loadId, String loadType, int weight,
            boolean isMechanic, HashMap<String, Station> stations) {
        super(startStationId, destStationId, loadId, loadType, weight, stations);
        this.isMechanic = isMechanic;
    }

    /**
     * Checks if the passenger is a mechanic.
     *
     * @return true if the passenger is a mechanic, false otherwise
     */
    public boolean isPassengerMechanic() {
        return this.isMechanic;
    }
}
