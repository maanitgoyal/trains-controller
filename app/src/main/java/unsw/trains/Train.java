package unsw.trains;

import java.util.ArrayList;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.response.models.LoadInfoResponse;
import unsw.utils.Position;

public class Train {
    private String trainId;
    private String type;
    private int lastIndex;
    private List<String> route = new ArrayList<>();
    private double speed;
    private int maxLoad;
    private boolean passengers;
    private boolean cargo;
    private boolean circularRoute;
    private Position position;
    private String location;
    private List<Load> loads = new ArrayList<>();
    private List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();

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
                this.maxLoad = 3500;
                this.circularRoute = false;
                break;
            case "CargoTrain":
                if (isRouteValid(tracks, st1, st2)) {
                    throw new InvalidRouteException("Invalid Route!");
                }
                this.speed = 3;
                this.passengers = false;
                this.cargo = true;
                this.maxLoad = 5000;
                this.circularRoute = false;
                break;
            case "BulletTrain":
                this.speed = 5;
                this.passengers = true;
                this.cargo = true;
                this.maxLoad = 5000;
                this.circularRoute = true;
                break;
            default:
                break;
        }
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
        this.position = Helper.findStation(stations, stationId).getStationCoordinates();
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
        return this.speed;
    }

    public boolean isCircular() {
        return this.circularRoute;
    }
    
    public void addLoadToTrain(Load ld) {
        this.loads.add(ld);
    }

    public void delLoadFromTrain(Load ld) {
        if (loads.size() == 0) return;
        loads.removeIf(load -> load.getLoadId().equals(ld.getLoadId()));
    }

    public int getMaxTrainLoad() {
        return this.maxLoad;
    }

    public List<Load> getTrainLoads() {
        return loads;
    }

    public boolean canPassengersBeOnThisTrain() {
        return this.passengers;
    }

    public boolean canCargoBeOnThisTrain() {
        return this.cargo;
    }

    public int getLoadWeightOfTrain() {
        int s = 0;
        for (Load load : loads) {
            if (load.getLoadType() == "Cargo") s += load.getLoadWeight();
        }
        return s;
    }

    public void decreaseTrainSpeed() {
        this.speed *= (1 - ((this.getLoadWeightOfTrain()) * 0.01) / 100);
    }

    public List<LoadInfoResponse> getLoadInfoResponsesOfTrain() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        this.loadInfoResponses.clear();
        for (Load ld : this.loads) {
            this.loadInfoResponses.add(new LoadInfoResponse(ld.getLoadId(), ld.getLoadType()));
        }
        return this.loadInfoResponses;
    }
}
