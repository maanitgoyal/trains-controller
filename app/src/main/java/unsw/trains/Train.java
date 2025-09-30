package unsw.trains;

import java.util.ArrayList;
import java.util.Iterator;
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
    private double originalSpeed;
    private int maxLoad;
    private boolean passengers;
    private boolean cargo;
    private boolean circularRoute;
    private Position position;
    private String location;
    private List<Load> loads = new ArrayList<>();
    private List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();
    private boolean atStation = true;

    public boolean isRouteValid(List<Track> tracks, String st1, String st2) {
        if (tracks.size() < 3) return false;
        return Helper.isThereATrack(tracks, st1, st2) != null;
    }

    public void setType(String type, List<Track> tracks, String st1, String st2) throws InvalidRouteException {
        switch (type) {
            case "PassengerTrain":
                if (isRouteValid(tracks, st1, st2)) {
                    throw new InvalidRouteException("Invalid Route!");
                }
                this.speed = 2;
                this.originalSpeed = 2;
                this.passengers = true;
                this.cargo = false;
                this.maxLoad = 3500;
                this.circularRoute = false;
                break;
            case "RepairTrain":
                if (isRouteValid(tracks, st1, st2)) {
                    throw new InvalidRouteException("Invalid Route!");
                }
                this.speed = 2;
                this.originalSpeed = 2;
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
                this.originalSpeed = 3;
                this.passengers = false;
                this.cargo = true;
                this.maxLoad = 5000;
                this.circularRoute = false;
                break;
            case "BulletTrain":
                this.speed = 5;
                this.originalSpeed = 5;
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
    
    
    public void decreaseTrainSpeed() {
        this.speed *= (1 - ((this.getCargoWeightOfTrain()) * 0.01) / 100);
    }

    public double getSpeed() {
        return this.speed;
    }

    public void resetSpeed() {
        this.speed = this.originalSpeed;
    }

    public boolean isCircular() {
        return this.circularRoute;
    }
    
    public void addLoadToTrain(Load ld) {
        this.loads.add(ld);
    }

    public void delLoadFromTrain(Load ld) {
        if (loads.isEmpty()) return;

        Iterator<Load> it = loads.iterator();
        while (it.hasNext()) {
            Load load = it.next();
            if (load.getLoadId().equals(ld.getLoadId())) {
                it.remove();
                break;
            }
        }
    }

    public int getMaxTrainLoad() {
        return this.maxLoad;
    }

    public List<Load> getTrainLoads() {
        return loads;
    }

    public int getMechanicsOnTrain() {
        int s = 0;
        for (Load ld : loads) if (ld.isMechanic()) s += 1;
        return s;
    }

    public boolean canPassengersBeOnThisTrain() {
        return this.passengers;
    }

    public boolean canCargoBeOnThisTrain() {
        return this.cargo;
    }

    public int getTotalLoadWeightOfTrain() {
        int s = 0;
        for (Load load: loads) s += load.getLoadWeight();
        return s;
    }

    public int getCargoWeightOfTrain() {
        int s = 0;
        for (Load load : loads) {
            if (load.getLoadType().equals("Cargo") || load.getLoadType().equals("PerishableCargo")) s += load.getLoadWeight();
        }
        return s;
    }

    public List<LoadInfoResponse> getLoadInfoResponsesOfTrain() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        this.loadInfoResponses.clear();
        for (Load ld : this.loads) {
            this.loadInfoResponses.add(new LoadInfoResponse(ld.getLoadId(), ld.getLoadType()));
        }
        return this.loadInfoResponses;
    }

    public void removeExpiredCargo() {
        Iterator<Load> it = loads.iterator();
        while (it.hasNext()) {
            Load load = it.next();
            if (load.getLoadType().equals("PerishableCargo")) {
                if (load.getMinsTillPerished() == 0) {
                    it.remove();
                } else {
                    Position cor = this.getTrainPosition();
                    load.setLoadCurrPosition(cor);
                    load.decMinsTillPerished();
                }
            }
        }
    }

    public boolean getAtStation() {
        return this.atStation;
    }

    public void setAtStation() {
        this.atStation = !this.atStation;
    }
}
