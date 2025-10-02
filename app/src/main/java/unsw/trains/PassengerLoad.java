package unsw.trains;

import java.util.HashMap;

public class PassengerLoad extends Load {
    private boolean isMechanic;

    public PassengerLoad(String startStationId, String destStationId, String loadId, String loadType, int weight, boolean isMechanic, HashMap<String, Station> stations) {
        super(startStationId, destStationId, loadId, loadType, weight, stations);
        this.isMechanic = isMechanic;
    }

    public boolean isPassengerMechanic() {
        return this.isMechanic;
    }
}
