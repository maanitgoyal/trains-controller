package unsw.trains;

import unsw.utils.Position;

public class Station extends Position{
    private String stationId;
    private String type;
    private int maxTrains;
    private double passengers;
    private double cargo;

    public void setType(String t) {
        switch (t) {
            case "PassengerStation":
                this.maxTrains = 2;
                this.passengers = Double.POSITIVE_INFINITY;
                this.cargo = 0;
                break;
            case "CargoStation":
                this.maxTrains = 4;
                this.passengers = 0;
                this.cargo = Double.POSITIVE_INFINITY;
                break;
            case "CentralStation":
                this.maxTrains = 8;
                this.passengers = Double.POSITIVE_INFINITY;
                this.cargo = Double.POSITIVE_INFINITY;
                break;
            case "DepotStation":
                this.maxTrains = 8;
                this.passengers = 0;
                this.cargo = 0;
                break;
            default:
                break;
        }
    }

    public Station(String stationId, String type, double x, double y) {
        super(x, y);
        this.stationId = stationId;
        this.type = type;
        setType(type);
    }


    public String getStationId() {
        return this.stationId;
    }

    public String getStationType() {
        return type;
    }
}
