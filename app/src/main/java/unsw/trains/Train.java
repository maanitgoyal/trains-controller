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

    /**
     * Gets the unique identifier of the train.
     * 
     * @return the train ID
     */
    public String getTrainId() {
        return this.trainId;
    }

    /**
     * Sets the current location of the train.
     * 
     * @param loc the location to set
     */
    public void setLocation(String loc) {
        this.location = loc;
    }

    /**
     * Gets the current location of the train.
     * 
     * @return the location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Gets the type of the train.
     * 
     * @return the train type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Gets the current position of the train.
     * 
     * @return the train's position
     */
    public Position getTrainPosition() {
        return this.position;
    }

    /**
     * Sets the current position of the train.
     * 
     * @param newPos the new position to set
     */
    public void setTrainPosition(Position newPos) {
        this.position = newPos;
    }

    /**
     * Gets the ID of the last station visited by the train.
     * 
     * @return the last station ID
     */
    public String getLastStationVisited() {
        return this.lastStationVisited;
    }

    /**
     * Sets the last station visited by the train.
     * 
     * @param st the station ID to set
     */
    public void setLastStationVisited(String st) {
        this.lastStationVisited = st;
    }

    /**
     * Gets the route of the train as a list of station IDs.
     * 
     * @return the route list
     */
    public List<String> getRoute() {
        return this.route;
    }

    /**
     * Gets the original speed of the train.
     * 
     * @return the original speed
     */
    public double getOriginalSpeed() {
        return this.originalSpeed;
    }

    /**
     * Sets the current speed of the train.
     * 
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Gets the current speed of the train.
     * 
     * @return the current speed
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Checks if the train's route is circular.
     * 
     * @return true if the route is circular, false otherwise
     */
    public boolean isCircular() {
        return this.circularRoute;
    }

    /**
     * Checks if the train is currently set to reverse its route.
     * 
     * @return true if reversing, false otherwise
     */
    public boolean getReverseRoute() {
        return this.reverseRoute;
    }

    /**
     * Toggles the reverse route flag for the train.
     */
    public void setReverseRoute() {
        this.reverseRoute = !this.reverseRoute;
    }

    /**
     * Adds a load to the train.
     * 
     * @param ld the load to add
     */
    public void addLoadToTrain(Load ld) {
        this.loads.add(ld);
    }

    /**
     * Removes a load from the train by its ID.
     * 
     * @param ld the load to remove
     */
    public void delLoadFromTrain(Load ld) {
        if (loads.isEmpty())
            return;

        Iterator<Load> it = loads.iterator();
        while (it.hasNext()) {
            Load load = it.next();
            if (load.getLoadId().equals(ld.getLoadId())) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Gets the maximum load weight the train can carry.
     * 
     * @return the maximum load weight
     */
    public int getMaxTrainLoad() {
        return this.maxLoad;
    }

    /**
     * Gets the list of loads currently on the train.
     * 
     * @return the list of loads
     */
    public List<Load> getTrainLoads() {
        return loads;
    }

    /**
     * Checks if passengers are allowed on this train.
     * 
     * @return true if passengers are allowed, false otherwise
     */
    public boolean canPassengersBeOnThisTrain() {
        return this.passengers;
    }

    /**
     * Checks if the train is currently at a station.
     * 
     * @return true if at a station, false otherwise
     */
    public boolean getAtStation() {
        return this.atStation;
    }

    /**
     * Toggles the at-station flag for the train.
     */
    public void setAtStation() {
        this.atStation = !this.atStation;
    }

    /**
     * Checks if cargo is allowed on this train.
     * 
     * @return true if cargo is allowed, false otherwise
     */
    public boolean canCargoBeOnThisTrain() {
        return this.cargo;
    }

    /**
     * Gets the total weight of all loads currently on the train.
     * 
     * @return the total load weight
     */
    public int getTotalLoadWeightOfTrain() {
        int s = 0;
        for (Load load : loads) s += load.getLoadWeight();
        return s;
    }

    /**
     * Creates a TrainInfoResponse object for this train.
     * 
     * @return a TrainInfoResponse containing train details
     */
    public TrainInfoResponse getTrainInfoResponseOfTrain() {
        return new TrainInfoResponse(this.trainId, this.location, this.type, this.position,
                this.getLoadInfoResponsesOfTrain());
    }

    /**
     * Gets a list of LoadInfoResponse objects for all loads currently on the train.
     * 
     * @return the list of LoadInfoResponse objects
     */
    public List<LoadInfoResponse> getLoadInfoResponsesOfTrain() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();
        for (Load ld : this.loads) loadInfoResponses.add(ld.getLoadInfoResponseOfLoad());
        return loadInfoResponses;
    }
}
