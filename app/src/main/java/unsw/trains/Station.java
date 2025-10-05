package unsw.trains;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import unsw.response.models.LoadInfoResponse;
import unsw.response.models.StationInfoResponse;
import unsw.response.models.TrainInfoResponse;
import unsw.utils.Position;

public class Station {
    private String stationId;
    private String type;
    private int maxTrains;
    private boolean passengers;
    private boolean cargo;
    private Position stationCoordinates;
    private List<Load> loads = new ArrayList<>();

    public Station(String stationId, String type, Position pos, int maxTrains, boolean passengers, boolean cargo) {
        this.stationId = stationId;
        this.type = type;
        this.stationCoordinates = pos;
        this.maxTrains = maxTrains;
        this.passengers = passengers;
        this.cargo = cargo;
    }

    /**
     * Gets the unique identifier of the station.
     * 
     * @return the station ID
     */
    public String getStationId() {
        return this.stationId;
    }

    /**
     * Gets the type of the station.
     * 
     * @return the station type
     */
    public String getStationType() {
        return type;
    }

    /**
     * Gets the coordinates of the station.
     * 
     * @return the station's position
     */
    public Position getStationCoordinates() {
        return this.stationCoordinates;
    }

    /**
     * Gets the maximum number of trains allowed at this station.
     * 
     * @return the maximum number of trains
     */
    public int getMaxTrains() {
        return maxTrains;
    }

    /**
     * Checks if the station is full given the list of trains.
     * 
     * @param trains the list of all trains
     * @return true if the station is full, false otherwise
     */
    public boolean checkIfStationIsFull(List<Train> trains) {
        return Helper.trainsOnStation(trains, this).size() == this.maxTrains;
    }

    /**
     * Checks if passengers can be on this station.
     * 
     * @return true if passengers are allowed, false otherwise
     */
    public boolean canPassengersBeOnThisStation() {
        return this.passengers;
    }

    /**
     * Checks if cargo can be on this station.
     * 
     * @return true if cargo is allowed, false otherwise
     */
    public boolean canCargoBeOnThisStation() {
        return this.cargo;
    }

    /**
     * Adds a load to the station.
     * 
     * @param ld the load to add
     */
    public void addLoadToStation(Load ld) {
        this.loads.add(ld);
    }

    /**
     * Removes a load from the station by its ID.
     * 
     * @param ld the load to remove
     */
    public void delLoadFromStation(Load ld) {
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

    /**
     * Gets the list of loads currently at the station, sorted by load ID.
     * 
     * @return the list of loads
     */
    public List<Load> getStationLoads() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        return this.loads;
    }

    /**
     * Gets a list of LoadInfoResponse objects for all loads at the station.
     * 
     * @return the list of LoadInfoResponse objects
     */
    public List<LoadInfoResponse> getLoadInfoResponsesofStation() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();
        for (Load ld : this.loads)
            loadInfoResponses.add(ld.getLoadInfoResponseOfLoad());
        return loadInfoResponses;
    }

    /**
     * Gets a list of TrainInfoResponse objects for all trains currently at the
     * station.
     * 
     * @param trains the list of all trains
     * @return the list of TrainInfoResponse objects
     */
    public List<TrainInfoResponse> getTrainInfoResponsesOnStation(List<Train> trains) {
        List<Train> tr = Helper.trainsOnStation(trains, this);
        List<TrainInfoResponse> trainInfoResponses = new ArrayList<>();
        for (Train train : tr)
            trainInfoResponses.add(train.getTrainInfoResponseOfTrain());
        return trainInfoResponses;
    }

    /**
     * Creates a StationInfoResponse object for this station.
     * 
     * @param trains the list of all trains
     * @return a StationInfoResponse containing station details
     */
    public StationInfoResponse getStationInfoResponseOfStation(List<Train> trains) {
        return new StationInfoResponse(stationId, type, stationCoordinates,
                getLoadInfoResponsesofStation(), getTrainInfoResponsesOnStation(trains));
    }
}
