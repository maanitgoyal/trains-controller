package unsw.trains;

import java.util.List;

import unsw.utils.Position;

public class Passenger {
    private String passengerId;
    private String passengerType;
    private String destStationId;
    private Position currPosition;

    public Passenger(String startStationId, String destStationId, String passengerId, List<Station> stations) {
        this.destStationId = destStationId;
        this.passengerId = passengerId;
        this.passengerType = "Passenger";
        this.currPosition = Helper.findStation(stations, startStationId).getStationCoordinates();
    }
}
