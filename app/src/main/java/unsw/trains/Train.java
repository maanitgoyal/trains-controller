package unsw.trains;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import unsw.exceptions.InvalidRouteException;
import unsw.utils.Position;

public class Train {
    private String trainId;
    private String type;
    private int lastIndex;
    private List<String> route = new ArrayList<>();
    private double speed;
    private int load;
    private boolean passengers;
    private boolean cargo;
    private boolean linearRoute;
    private boolean circularRoute;
    private Position position;
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

    public Station findStation(List<Station> stations, String stationId) {
        for (Station station : stations) {
            if (station.getStationId().equals(stationId)) return station;
        }
        return null;
    }

    public Train(String trainId, String type, String stationId, List<String> route, List<Track> tracks, List<Station> stations) throws InvalidRouteException {
        String start = route.get(0);
        String end = route.get(route.size() - 1);
        setType(type, tracks, start, end);
        this.trainId = trainId;
        this.lastIndex = 0;
        this.route = route;
        this.location = stationId;
        this.type = type;
        this.position = findStation(stations, stationId).getStationCoordinates();
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

    public Position getTrainPosition() {
        return this.position;
    }

    public void setTrainPosition(Position newPos) {
        this.position = newPos;
    }

    public int getLastIndex() {
        return this.lastIndex;
    }

    public void setLastIndex(int index) {
        this.lastIndex = index;
    }

    public List<String> getRoute() {
        return this.route;
    }

    public double getSpeed() {
        return speed;
    }

}
