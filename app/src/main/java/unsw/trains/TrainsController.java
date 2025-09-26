package unsw.trains;

import java.util.ArrayList;
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
    List<Station> stations = new ArrayList<>();
    List<Track> tracks = new ArrayList<>();
    List<Train> trains = new ArrayList<>();
    public void createStation(String stationId, String type, double x, double y) {
        Station st = new Station(stationId, type, x, y);
        stations.add(st);
    }

    public void createTrack(String trackId, String fromStationId, String toStationId) {
        Track tr = new Track(trackId, fromStationId, toStationId);
        tracks.add(tr);
    }

    public void createTrain(String trainId, String type, String stationId, List<String> route)
            throws InvalidRouteException {
        Train t = new Train(trainId, type, stationId, route, this.tracks);
        trains.add(t);
    }

    public List<String> listStationIds() {
        List<String> l = new ArrayList<>();
        for (Station st : stations) {
            l.add(st.getStationId());
        }
        return l;
    }

    public List<String> listTrackIds() {
        List<String> l = new ArrayList<>();
        for (Track tr : tracks) {
            l.add(tr.getTrackId());
        }
        return l;
    }

    public List<String> listTrainIds() {
        List<String> l = new ArrayList<>();
        for (Train t : trains) {
            l.add(t.getTrainId());
        }
        return l;
    }

    public TrainInfoResponse getTrainInfo(String trainId) {
        // Todo: Task avii
        return null;
    }

    public StationInfoResponse getStationInfo(String stationId) {
        // Todo: Task aviii
        return null;
    }

    public TrackInfoResponse getTrackInfo(String trackId) {
        // Todo: Task aix
        return null;
    }

    public void simulate() {
        // Todo: Task bi
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
        // Todo: Task bii
    }

    public void createCargo(String startStationId, String destStationId, String cargoId, int weight) {
        // Todo: Task bii
    }

    public void createPerishableCargo(String startStationId, String destStationId, String cargoId, int weight,
            int minsTillPerish) {
        // Todo: Task biii
    }

    public void createTrack(String trackId, String fromStationId, String toStationId, boolean isBreakable) {
        // Todo: Task ci
    }

    public void createPassenger(String startStationId, String destStationId, String passengerId, boolean isMechanic) {
        // Todo: Task cii
    }
}
