package unsw.trains;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import unsw.response.models.LoadInfoResponse;
import unsw.response.models.TrainInfoResponse;
import unsw.utils.Position;

public class Station {
    private String stationId;
    private String type;
    private int maxTrains;
    private double passengers;
    private double cargo;
    private Position stationCoordinates;
    private List<Load> loads = new ArrayList<>();

    public Station(String stationId, String type, Position pos, int maxTrains, double passengers, double cargo) {
        this.stationId = stationId;
        this.type = type;
        this.stationCoordinates = pos;
        this.maxTrains = maxTrains;
        this.passengers = passengers;
        this.cargo = cargo;
    }

    public String getStationId() {
        return this.stationId;
    }

    public String getStationType() {
        return type;
    }

    public Position getStationCoordinates() {
        return this.stationCoordinates;
    }

    public int getMaxTrains() {
        return maxTrains;
    }

    public boolean checkIfStationIsFull(List<Train> trains) {
        return Helper.trainsOnStation(trains, this).size() == this.maxTrains;
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

    public List<Load> getStationLoads() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        return this.loads; 
    }

    public List<LoadInfoResponse> getLoadInfoResponsesofStation() {
        this.loads.sort((l1, l2) -> l1.getLoadId().compareTo(l2.getLoadId()));
        List<LoadInfoResponse> loadInfoResponses = new ArrayList<>();
        for (Load ld : this.loads) {
            loadInfoResponses.add(new LoadInfoResponse(ld.getLoadId(), ld.getLoadType()));
        }
        return loadInfoResponses;
    }

    public List<TrainInfoResponse> getTrainInfoResponsesOnStation(List<Train> trains) {
        List<Train> tr = Helper.trainsOnStation(trains, this);
        List<TrainInfoResponse> trainInfoResponses = new ArrayList<>();
        for (Train train : tr) {
            trainInfoResponses.add(new TrainInfoResponse(train.getTrainId(),
            this.getStationId(), train.getType(), train.getTrainPosition(), train.getLoadInfoResponsesOfTrain()));
        }
        return trainInfoResponses;
    }
}
