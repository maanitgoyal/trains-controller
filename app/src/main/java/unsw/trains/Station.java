package unsw.trains;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.LoadInfoResponse;
import unsw.response.models.TrainInfoResponse;
import unsw.utils.Position;

public class Station extends Position {
    private String stationId;
    private String type;
    private int maxTrains;
    private double passengers;
    private double cargo;
    private int curTrains;
    private List<Load> loads = new ArrayList<>();
    private List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();
    private List<TrainInfoResponse> trainInfoResponses = new ArrayList<>();

    public void setType(String t) {
        switch (t) {
            case "PassengerStation":
                this.maxTrains = 2;
                this.passengers = Double.POSITIVE_INFINITY;
                this.cargo = 0;
                break;
            case "CargoStation":
                this.maxTrains = 4;
                this.passengers = 0;
                this.cargo = Double.POSITIVE_INFINITY;
                break;
            case "CentralStation":
                this.maxTrains = 8;
                this.passengers = Double.POSITIVE_INFINITY;
                this.cargo = Double.POSITIVE_INFINITY;
                break;
            case "DepotStation":
                this.maxTrains = 8;
                this.passengers = 0;
                this.cargo = 0;
                break;
            default:
                break;
        }
    }

    public Station(String stationId, String type, double x, double y) {
        super(x, y);
        this.stationId = stationId;
        this.type = type;
        this.curTrains = 0;
        setType(type);
    }


    public String getStationId() {
        return this.stationId;
    }

    public String getStationType() {
        return type;
    }

    public Position getStationCoordinates() {
        return new Position(this.getX(), this.getY());
    }

    public int getMaxTrains() {
        return maxTrains;
    }

    public boolean checkIfStationIsFull() {
        return this.curTrains == this.maxTrains;
    }

    public void incrementCurrTrains() {
        this.curTrains++;
    }

    public void decrementCurrTrains() {
        this.curTrains--;
    }

    public boolean canPassengersBeOnThisStation() {
        return this.passengers == Double.POSITIVE_INFINITY;
    }

    public boolean canCargoBeOnThisStation() {
        return this.cargo == Double.POSITIVE_INFINITY;
    }

    public void addLoadToStation(Load ld) {
        this.loads.add(ld);
    }

    public void delLoadFromStation(Load ld) {
        if (loads.size() == 0) return;
        loads.removeIf(load -> load.getLoadId().equals(ld.getLoadId()));
    }

    public List<Load> getStationLoads() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        return this.loads; 
    }

    public List<LoadInfoResponse> getLoadInfoResponsesofStation() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        this.loadInfoResponses.clear();
        for (Load ld : this.loads) {
            this.loadInfoResponses.add(new LoadInfoResponse(ld.getLoadId(), ld.getLoadType()));
        }
        return this.loadInfoResponses;
    }

    public List<TrainInfoResponse> getTrainInfoResponsesOnStation(List<Train> trains) {
        List<Train> tr = Helper.trainsOnStation(trains, this);
        this.trainInfoResponses.clear();
        for (Train train : tr) {
            this.trainInfoResponses.add(new TrainInfoResponse(train.getTrainId(),
            this.getStationId(), train.getType(), train.getTrainPosition(), train.getLoadInfoResponsesOfTrain()));
        }
        return this.trainInfoResponses;
    }
}
