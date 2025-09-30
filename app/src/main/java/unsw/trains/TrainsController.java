package unsw.trains;

import java.util.ArrayList;
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
    List<Station> stations = new ArrayList<>();
    List<Track> tracks = new ArrayList<>();
    List<Train> trains = new ArrayList<>();
    // boolean firstSim = false;

    public void createStation(String stationId, String type, double x, double y) {
        Station st = new Station(stationId, type, x, y);
        stations.add(st);
    }

    public void createTrack(String trackId, String fromStationId, String toStationId) {
        Track tr = new Track(trackId, fromStationId, toStationId, false);
        tracks.add(tr);
    }

    public void createTrain(String trainId, String type, String stationId, List<String> route)
            throws InvalidRouteException {
        Train t = new Train(trainId, type, stationId, route, this.tracks, this.stations);
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
        for (Train t : trains) {
            if (t.getTrainId().equals(trainId)) {
                return new TrainInfoResponse(trainId, t.getLocation(), t.getType(), t.getTrainPosition(),
                t.getLoadInfoResponsesOfTrain());
            }
        }
        return null;
    }

    public StationInfoResponse getStationInfo(String stationId) {
        for (Station station : stations) {
            if (station.getStationId().equals(stationId)) {
                return new StationInfoResponse(stationId, station.getStationType(), station.getStationCoordinates(), station.getLoadInfoResponsesofStation(), station.getTrainInfoResponsesOnStation(trains));
            }
        }
        return null;
    }

    public TrackInfoResponse getTrackInfo(String trackId) {
        for (Track tr : tracks) {
            if (tr.getTrackId().equals(trackId)) {
                return new TrackInfoResponse(trackId, tr.getFromStationId(),
                tr.getToStationId(), tr.getTrackType(), tr.getDurability());
            }
        }
        return null;
    }

    public void simulate() {
        this.trains.sort((t1, t2) -> t1.getTrainId().compareTo(t2.getTrainId()));
        for (Train t : trains) {
            int last = t.getLastIndex();
            int routeSize = t.getRoute().size();

            if (last == routeSize - 1 && !t.isCircular()) continue;
            int cur = (last == routeSize - 1 && t.isCircular()) ? 0 : last + 1;

            String stationIdCur = t.getRoute().get(last);
            Station stationCur = Helper.findStation(stations, stationIdCur);
            String stationIdFinal = t.getRoute().get(cur);
            Station stationFinal = Helper.findStation(this.stations, stationIdFinal);

            if (stationFinal.checkIfStationIsFull()) continue;
            Track track = Helper.isThereATrack(tracks, stationIdCur, stationIdFinal);
            if (track != null && track.getTrackType() == TrackType.BROKEN && t.getType() != "RepairTrain") continue;
            
            Position destination = stationFinal.getStationCoordinates();
            Position currentTrainPosition = t.getTrainPosition();
            stationCur.decrementCurrTrains(); // i think this is void
            Helper.simulateLoadEmbark(stationCur, t);
            t.decreaseTrainSpeed();
            if (track != null && track.getTrackType() == TrackType.BROKEN && t.getType() == "RepairTrain") Helper.fixDurabilityOfTrack(t, track);
            
            if (currentTrainPosition.isInBound(destination, t.getSpeed())) {
                t.setTrainPosition(destination);
                t.setLastIndex(cur);
                stationFinal.incrementCurrTrains(); // i think this is void
                Helper.trackSimulator(t, track);
                Helper.simulateLoadDisembark(stationFinal, t);
                t.resetSpeed();
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
        Load ld = new Load(startStationId, destStationId, passengerId, "Passenger", 70, false, stations);
        Station st = Helper.findStation(stations, startStationId);
        st.addLoadToStation(ld);
    }
    
    public void createCargo(String startStationId, String destStationId, String cargoId, int weight) {
        Load ld = new Load(startStationId, destStationId, cargoId, "Cargo", weight, false, stations);
        Station st = Helper.findStation(stations, startStationId);
        st.addLoadToStation(ld);
    }
    
    public void createPerishableCargo(String startStationId, String destStationId, String cargoId, int weight,
    int minsTillPerish) {
        Load ld = new Load(startStationId, destStationId, cargoId, "PerishableCargo", weight, minsTillPerish, stations);
        Station st = Helper.findStation(stations, startStationId);
        st.addLoadToStation(ld);
    }
    
    public void createTrack(String trackId, String fromStationId, String toStationId, boolean isBreakable) {
        Track t = new Track(trackId, fromStationId, toStationId, isBreakable);
        tracks.add(t);
    }
    
    public void createPassenger(String startStationId, String destStationId, String passengerId, boolean isMechanic) {
        Load ld = new Load(startStationId, destStationId, passengerId, "Passenger", 70, isMechanic, stations);
        Station st = Helper.findStation(stations, startStationId);
        st.addLoadToStation(ld);
    }
}
