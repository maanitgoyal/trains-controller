package unsw.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.response.models.*;

/**
 * The controller for the Trains system.
 *
 * The method signatures here are provided for you. Do NOT change the method signatures.
 */
public class TrainsController {
    // Add any fields here if necessary
    HashMap<String, Station> stations = new HashMap<>();
    HashMap<String, Track> tracks = new HashMap<>();
    HashMap<String, Train> trains = new HashMap<>();

    public void createStation(String stationId, String type, double x, double y) {
        Station st = Helper.createStationHelper(stationId, type, x, y);
        stations.put(stationId, st);
    }

    public void createTrack(String trackId, String fromStationId, String toStationId) {
        Track tr = new NormalTrack(trackId, fromStationId, toStationId);
        tracks.put(trackId, tr);
    }

    public void createTrain(String trainId, String type, String stationId, List<String> route)
            throws InvalidRouteException {
        Train t = Helper.createTrainHelper(trainId, type, stationId, route, tracks, stations);
        trains.put(trainId, t);
    }

    public List<String> listStationIds() {
        return new ArrayList<>(stations.keySet());
    }

    public List<String> listTrackIds() {
        return new ArrayList<>(tracks.keySet());
    }

    public List<String> listTrainIds() {
        return new ArrayList<>(trains.keySet());
    }

    public TrainInfoResponse getTrainInfo(String trainId) {
        return this.trains.get(trainId).getTrainInfoResponseOfTrain();
    }

    public StationInfoResponse getStationInfo(String stationId) {
        return this.stations.get(stationId).getStationInfoResponseOfStation(new ArrayList<>(this.trains.values()));
    }

    public TrackInfoResponse getTrackInfo(String trackId) {
        return this.tracks.get(trackId).getTrackInfoResponseOfTrack();
    }

    public void simulate() {
        Helper.helperSimulate(trains, stations, tracks);
    }

    /**
     * Simulate for the specified number of minutes. You should NOT modify
     * this function.
     */
    public void simulate(int numberOfMinutes) {
        for (int i = 0; i < numberOfMinutes; i++) {
            simulate();
        }
    }

    public void createPassenger(String startStationId, String destStationId, String passengerId) {
        Load ld = new PassengerLoad(startStationId, destStationId, passengerId, "Passenger", 70, false, stations);
        Station st = stations.get(startStationId);
        st.addLoadToStation(ld);
    }
    
    public void createCargo(String startStationId, String destStationId, String cargoId, int weight) {
        Load ld = new CargoLoad(startStationId, destStationId, cargoId, "Cargo", weight, stations);
        Station st = stations.get(startStationId);
        st.addLoadToStation(ld);
    }
    
    public void createPerishableCargo(String startStationId, String destStationId, String cargoId, int weight,
    int minsTillPerish) {
        Load ld = new PerishableCargoLoad(startStationId, destStationId, cargoId, "PerishableCargo", weight, minsTillPerish, stations);
        Station st = stations.get(startStationId);
        st.addLoadToStation(ld);
    }
    
    public void createTrack(String trackId, String fromStationId, String toStationId, boolean isBreakable) {
        Track t;
        if (isBreakable) t = new UnbrokenTrack(trackId, fromStationId, toStationId);
        else t = new NormalTrack(trackId, fromStationId, toStationId);
        tracks.put(trackId, t);
    }
    
    public void createPassenger(String startStationId, String destStationId, String passengerId, boolean isMechanic) {
        Load ld = new PassengerLoad(startStationId, destStationId, passengerId, "Passenger", 70, isMechanic, stations);
        Station st = stations.get(startStationId);
        st.addLoadToStation(ld);
    }
}
