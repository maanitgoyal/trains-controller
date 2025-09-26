package unsw.trains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String location;

    public boolean isRouteValid(List<Track> tracks, String st1, String st2) {
        if (tracks.size() < 3) return false;
        for (Track track : tracks) {
            if (track.getFromStationId().equals(st1) &&
            track.getToStationId().equals(st2)) return true;
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
                break;
            default:
                break;
        }
    }

    private static Position findLocationOfStation(List<Station> stations, String stationId) {
        for (Station s : stations) {
            if (Objects.equals(s.getStationId(), stationId)) {
                return new Position(s.getX(), s.getY());
            }
        }
        return new Position(0.0, 0.0);
    }

    public Train(String trainId, String type, String stationId, List<String> route, List<Track> tracks, List<Station> stations) throws InvalidRouteException {
        super(findLocationOfStation(stations, stationId).getX(),
        findLocationOfStation(stations, stationId).getY());

        String start = route.get(0);
        String end   = route.get(route.size() - 1);
        setType(type, tracks, start, end);
        this.trainId = trainId;
        this.firstStation = stationId;
        this.route = route;
        this.location = this.firstStation;
        this.type = type;
    }

    public String getTrainId() {
        return this.trainId;
    }

    public String getLocation() {
        return this.location;
    }

    public String getType() {
        return this.type;
    }

    public Position getTrainCoordinates() {
        return new Position(this.getX(), this.getY());
    }
}
