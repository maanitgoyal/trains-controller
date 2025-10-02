package unsw.trains;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import unsw.exceptions.InvalidRouteException;
import unsw.response.models.LoadInfoResponse;
import unsw.response.models.TrainInfoResponse;
import unsw.utils.Position;

public class Train {
    private String trainId;
    private String type;
    private String lastStationVisited;
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
    private boolean atStation = true;
    private boolean reverseRoute = false;

    public Train(String trainId, String type, String stationId, List<String> route,
    Position pos, double speed, double originalSpeed, boolean passengers,
    boolean cargo, int maxLoad, boolean circularRoute) throws InvalidRouteException {
        this.trainId = trainId;
        this.lastStationVisited = stationId;
        this.route = route;
        this.location = stationId;
        this.type = type;
        this.position = pos;
        this.speed = speed;
        this.originalSpeed = originalSpeed;
        this.passengers = passengers;
        this.cargo = cargo;
        this.maxLoad = maxLoad;
        this.circularRoute = circularRoute;
    }

    public String getTrainId() {
        return this.trainId;
    }

    public void setLocation(String loc) {
        this.location = loc;
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

    public String getLastStationVisited() {
        return this.lastStationVisited;
    }

    public void setLastStationVisited(String st) {
        this.lastStationVisited = st;
    }

    public List<String> getRoute() {
        return this.route;
    }
    
    public double getOriginalSpeed() {
        return this.originalSpeed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return this.speed;
    }

    public boolean isCircular() {
        return this.circularRoute;
    }
    
    public boolean getReverseRoute() {
        return this.reverseRoute;
    }

    public void setReverseRoute() {
        this.reverseRoute = !this.reverseRoute;
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

    public boolean canPassengersBeOnThisTrain() {
        return this.passengers;
    }
    
    public boolean getAtStation() {
        return this.atStation;
    }

    public void setAtStation() {
        this.atStation = !this.atStation;
    }

    public boolean canCargoBeOnThisTrain() {
        return this.cargo;
    }

    public int getTotalLoadWeightOfTrain() {
        int s = 0;
        for (Load load: loads) s += load.getLoadWeight();
        return s;
    }

    public TrainInfoResponse getTrainInfoResponseOfTrain() {
        return new TrainInfoResponse(this.trainId, this.location, this.type, this.position, this.getLoadInfoResponsesOfTrain());
    }

    public List<LoadInfoResponse> getLoadInfoResponsesOfTrain() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();
        for (Load ld : this.loads) loadInfoResponses.add(ld.getLoadInfoResponseOfLoad());
        return loadInfoResponses;
    }
}
