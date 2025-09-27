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
    private boolean hasReachedDestination;

    public Load(String startStationId, String destStationId, String loadId, String loadType, int weight, List<Station> stations) {
        this.destStationId = destStationId;
        this.loadId = loadId;
        this.loadType = loadType;
        this.currPosition = Helper.findStation(stations, startStationId).getStationCoordinates();
        this.weight = weight;
        this.hasReachedDestination = false;
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
}
