package unsw.trains;

import java.util.HashMap;

import unsw.response.models.LoadInfoResponse;
import unsw.utils.Position;

public class Load {
    private String loadId;
    private String loadType;
    private String destStationId;
    private Position currPosition;
    private int weight;
    private String trainAssigned = null;

    public Load(String startStationId, String destStationId, String loadId, String loadType, int weight, HashMap<String, Station> stations) {
        this.destStationId = destStationId;
        this.loadId = loadId;
        this.loadType = loadType;
        this.currPosition = stations.get(startStationId).getStationCoordinates();
        this.weight = weight;
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

    public LoadInfoResponse getLoadInfoResponseOfLoad() {
        return new LoadInfoResponse(this.loadId, this.loadType);
    }
}
