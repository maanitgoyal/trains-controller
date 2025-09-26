package unsw.trains;

import java.util.ArrayList;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.utils.Position;

public class Train extends Position {
    private String trainId;
    private String type;
    private String firstStation;
    private List<String> route = new ArrayList<>();
    private double speed;
    private int load;
    private boolean passengers;
    private boolean cargo;
    private boolean linearRoute;
    private boolean circularRoute;
    // private double x;
    // private double y;

    public boolean isRouteValid(List<Track> tracks, String st1, String st2) {
        for (Track track : tracks) {
            if (track.getFromStationId() == st1 &&
            track.getToStationId() == st2) return true;
        }
        return false;
    }

    public void setType(String type, List<Track> tracks, String st1, String st2) throws InvalidRouteException {
        switch (type) {
            case "PassengerTrain":
                if (isRouteValid(tracks, st1, st2) || isRouteValid(tracks, st2, st1)) {
                    throw new InvalidRouteException("Invalid Route!");
                }
                this.speed = 2;
                this.passengers = true;
                this.cargo = false;
                this.load = 3500;
                this.linearRoute = true;
                this.circularRoute = false;
                break;
            case "CargoTrain":
                if (isRouteValid(tracks, st1, st2)) {
                    throw new InvalidRouteException("Invalid Route!");
                }
                this.speed = 3;
                this.passengers = false;
                this.cargo = true;
                this.load = 5000;
                this.linearRoute = true;
                this.circularRoute = false;
                break;
            case "BulletTrain":
                this.speed = 5;
                this.passengers = true;
                this.cargo = true;
                this.load = 5000;
                this.linearRoute = true;
                this.circularRoute = true;
            default:
                break;
        }
    }

    public Train(String trainId, String type, String stationId, List<String> route, List<Track> tracks) throws InvalidRouteException {
        super(0, 0);
        setType(type, tracks, route.get(0), route.get(route.size() - 1));
        this.trainId = trainId;
        this.type = type;
        this.firstStation = route.get(0);
        this.route = route;
    }

    public String getTrainId() {
        return trainId;
    }
}
