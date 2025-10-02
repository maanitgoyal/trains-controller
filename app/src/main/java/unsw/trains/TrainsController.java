package unsw.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.response.models.*;
import unsw.utils.Position;
import unsw.utils.TrackType;

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
        Track tr = new Track(trackId, fromStationId, toStationId, false);
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
        List<String> tr = new ArrayList<>(this.trains.keySet());
        tr.sort((t1, t2) -> t1.compareTo(t2));
        for (String id : tr) {
            Train t = this.trains.get(id);
            int last = t.getLastIndex();
            int routeSize = t.getRoute().size();

            if (last == routeSize - 1 && !t.isCircular()) continue;
            int cur = (last == routeSize - 1 && t.isCircular()) ? 0 : last + 1;

            String stationIdCur = t.getRoute().get(last);
            Station stationCur = this.stations.get(stationIdCur);
            String stationIdFinal = t.getRoute().get(cur);
            Station stationFinal = this.stations.get(stationIdFinal);

            if (stationFinal.checkIfStationIsFull(new ArrayList<>(this.trains.values()))) continue;
            Track track = Helper.isThereATrack(tracks, stationIdCur, stationIdFinal);
            if (track != null && track.getTrackType() == TrackType.BROKEN && t.getType() != "RepairTrain") continue;
            
            Position destination = stationFinal.getStationCoordinates();
            Position currentTrainPosition = t.getTrainPosition();
            if (t.getAtStation()) {
                Helper.simulateLoadEmbark(stationCur, t);
                t.setAtStation();
            }
            t.decreaseTrainSpeed();
            if (track != null && track.getTrackType() == TrackType.BROKEN && t instanceof RepairTrain) {
                Helper.fixDurabilityOfTrack((RepairTrain)t, track);
            }
            
            if (currentTrainPosition.isInBound(destination, t.getSpeed())) {
                t.setTrainPosition(destination);
                t.setLastIndex(cur);
                // stationFinal.incrementCurrTrains(); // i think this is void
                Helper.trackSimulator(t, track);
                Helper.simulateLoadDisembark(stationFinal, t);
                t.resetSpeed();
                t.setAtStation();
                continue;
            }
            t.setTrainPosition(currentTrainPosition.calculateNewPosition(destination, t.getSpeed()));
            t.removeExpiredCargo();
        }
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
        Track t = new Track(trackId, fromStationId, toStationId, isBreakable);
        tracks.put(trackId, t);
    }
    
    public void createPassenger(String startStationId, String destStationId, String passengerId, boolean isMechanic) {
        Load ld = new PassengerLoad(startStationId, destStationId, passengerId, "Passenger", 70, isMechanic, stations);
        Station st = stations.get(startStationId);
        st.addLoadToStation(ld);
    }
}
