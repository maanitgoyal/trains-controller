package unsw.trains.Loads;

import java.util.HashMap;

import unsw.response.models.LoadInfoResponse;
import unsw.trains.Stations.Station;
import unsw.utils.Position;

public class Load {
    private String loadId;
    private String loadType;
    private String destStationId;
    private Position currPosition;
    private int weight;

    public Load(String startStationId, String destStationId, String loadId, String loadType, int weight,
            HashMap<String, Station> stations) {
        this.destStationId = destStationId;
        this.loadId = loadId;
        this.loadType = loadType;
        this.currPosition = stations.get(startStationId).getStationCoordinates();
        this.weight = weight;
    }

    /**
     * Gets the unique identifier of the load.
     * 
     * @return the load ID
     */
    public String getLoadId() {
        return this.loadId;
    }

    /**
     * Gets the type of the load.
     * 
     * @return the load type
     */
    public String getLoadType() {
        return this.loadType;
    }

    /**
     * Gets the destination station ID for this load.
     * 
     * @return the destination station ID
     */
    public String getLoadDestinationStationId() {
        return this.destStationId;
    }

    /**
     * Sets the current position of the load.
     * 
     * @param newPos the new position to set
     */
    public void setLoadCurrPosition(Position newPos) {
        this.currPosition = newPos;
    }

    /**
     * Gets the current position of the load.
     * 
     * @return a copy of the current position
     */
    public Position getLoadCurrPosition() {
        return new Position(this.currPosition.getX(), this.currPosition.getY());
    }

    /**
     * Gets the weight of the load.
     * 
     * @return the load weight
     */
    public int getLoadWeight() {
        return this.weight;
    }
    
    /**
     * Creates a LoadInfoResponse object for this load.
     * 
     * @return a LoadInfoResponse containing the load's ID and type
     */
    public LoadInfoResponse getLoadInfoResponseOfLoad() {
        return new LoadInfoResponse(this.loadId, this.loadType);
    }
}
