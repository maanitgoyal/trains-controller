package unsw.trains;

import java.util.List;

import unsw.utils.Position;

public class Load {
    private String loadId;
    private String loadType;
    private String destStationId;
    private Position currPosition;
    private int weight;
    private String trainAssigned = null;
    private boolean hasReachedDestination; // void - should be removed in the end.
    private int minsTillPerish;
    private boolean isMechanic;
    // add the functionality for setting load position while simulate.

    public Load(String startStationId, String destStationId, String loadId, String loadType, int weight, boolean isMechanic, List<Station> stations) {
        this.destStationId = destStationId;
        this.loadId = loadId;
        this.loadType = loadType;
        this.currPosition = Helper.findStation(stations, startStationId).getStationCoordinates();
        this.weight = weight;
        this.hasReachedDestination = false;
        this.isMechanic = isMechanic;
    }

    public Load(String startStationId, String destStationId, String loadId, String loadType, int weight, int minsTillPerish, List<Station> stations) {
        this.destStationId = destStationId;
        this.loadId = loadId;
        this.loadType = loadType;
        this.currPosition = Helper.findStation(stations, startStationId).getStationCoordinates();
        this.weight = weight;
        this.hasReachedDestination = false;
        this.minsTillPerish = minsTillPerish;
    }

    public String getLoadId() {
        return this.loadId;
    }

    public String getLoadType() {
        return this.loadType;
    }

    public String getLoadDestinationStationId() {
        return this.destStationId;
    }

    public void setLoadCurrPosition(Position newPos) {
        this.currPosition = newPos;
    }

    public Position getLoadCurrPosition() {
        return new Position(this.currPosition.getX(), this.currPosition.getY());
    }

    public int getLoadWeight() {
        return this.weight;
    }

    public String getLoadTrainAssigned() {
        return this.trainAssigned;
    }

    public void setLoadTrain(String t) {
        this.trainAssigned = t;
    }

    public void setLoadReachedDestination() {
        this.hasReachedDestination = true;
    }
    
    public boolean hasLoadReachedDestination() {
        return this.hasReachedDestination;
    }

    public int getMinsTillPerished() {
        return this.minsTillPerish;
    }

    public boolean shouldPerishableBeEmbarked(Train t, Station st) {
        Position cur = this.getLoadCurrPosition();
        if ((cur.distance(st.getStationCoordinates()) / t.getSpeed()) <= this.minsTillPerish) return true;
        return false;
    }

    public void decMinsTillPerished() {
        this.minsTillPerish--;
    }

    public boolean isMechanic() {
        return this.isMechanic;
    }
}
