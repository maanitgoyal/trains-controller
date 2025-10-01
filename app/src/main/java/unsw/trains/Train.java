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

    public Train(String trainId, String type, String stationId, List<String> route,
    Position pos, double speed, double originalSpeed, boolean passengers,
    boolean cargo, int maxLoad, boolean circularRoute) throws InvalidRouteException {
        this.trainId = trainId;
        this.lastIndex = 0;
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
