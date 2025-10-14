package unsw.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import unsw.exceptions.InvalidRouteException;
import unsw.response.models.*;
import unsw.trains.Loads.CargoLoad;
import unsw.trains.Loads.Load;
import unsw.trains.Loads.PassengerLoad;
import unsw.trains.Loads.PerishableCargoLoad;
import unsw.trains.Stations.CargoStation;
import unsw.trains.Stations.DepotStation;
import unsw.trains.Stations.PassengerStation;
import unsw.trains.Stations.Station;
import unsw.trains.Tracks.NormalTrack;
import unsw.trains.Tracks.Track;
import unsw.trains.Tracks.UnbrokenTrack;
import unsw.trains.Trains.BulletTrain;
import unsw.trains.Trains.CargoTrain;
import unsw.trains.Trains.PassengerTrain;
import unsw.trains.Trains.RepairTrain;
import unsw.trains.Trains.Train;
import unsw.utils.Position;

/**
 * The controller for the Trains system.
 *
 * The method signatures here are provided for you. Do NOT change the method signatures.
 */
public class TrainsController {
    // Add any fields here if necessary
    private HashMap<String, Station> stations = new HashMap<>();
    private HashMap<String, Track> tracks = new HashMap<>();
    private HashMap<String, Train> trains = new HashMap<>();

    public void createStation(String stationId, String type, double x, double y) {
        Station st;
        if (Objects.equals("PassengerStation", type)) st = new PassengerStation(stationId, type, x, y);
        else if (Objects.equals("CargoStation", type)) st = new CargoStation(stationId, type, x, y);
        else if (Objects.equals("DepotStation", type)) st = new DepotStation(stationId, type, x, y);
        else if (Objects.equals("CentralStation", type)) st = new DepotStation(stationId, type, x, y);
        else st = null;
        stations.put(stationId, st);
    }

    public void createTrack(String trackId, String fromStationId, String toStationId) {
        Track tr = new NormalTrack(trackId, fromStationId, toStationId);
        tracks.put(trackId, tr);
    }

    public void createTrain(String trainId, String type, String stationId, List<String> route)
            throws InvalidRouteException {
        Train t;
        if (Objects.equals("PassengerTrain", type)) t = new PassengerTrain(trainId, type, stationId, route,
        stations.get(stationId).getStationCoordinates(), tracks);
        else if (Objects.equals("BulletTrain", type)) t = new BulletTrain(trainId, type, stationId, route,
        stations.get(stationId).getStationCoordinates(), tracks);
        else if (Objects.equals("CargoTrain", type)) t = new CargoTrain(trainId, type, stationId, route,
        stations.get(stationId).getStationCoordinates(), tracks);
        else if (Objects.equals("RepairTrain", type)) t = new RepairTrain(trainId, type, stationId, route,
        stations.get(stationId).getStationCoordinates(), tracks);
        else t = null;
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

    /**
     * Removes expired perishable cargo from the train and updates their positions
     * and timers.
     */
    public static void removeExpiredCargo(HashMap<String, Train> trains, HashMap<String, Station> stations) {
        for (Map.Entry<String, Train> entry : trains.entrySet()) {
            Train t = entry.getValue();
            List<Load> loads = new ArrayList<>(t.getTrainLoads());
            for (Load load : loads) {
                if (load instanceof PerishableCargoLoad) {
                    PerishableCargoLoad oth = (PerishableCargoLoad) load;
                    oth.decMinsTillPerished();
                    if (oth.getMinsTillPerished() == 0) t.delLoadFromTrain(load);
                    else {
                        Position cor = t.getTrainPosition();
                        oth.setLoadCurrPosition(cor);
                    }
                }
            }
        }

        for (Map.Entry<String, Station> entry: stations.entrySet()) {
            Station st = entry.getValue();
            List<Load> loads = new ArrayList<>(st.getStationLoads());
            for (Load load : loads) {
                if (load instanceof PerishableCargoLoad) {
                    PerishableCargoLoad oth = (PerishableCargoLoad) load;
                    oth.decMinsTillPerished();
                    if (oth.getMinsTillPerished() == 0) st.delLoadFromStation(load);
                }
            }
        }
    }

    public void simulate() {
        List<String> tr = new ArrayList<>(trains.keySet());
        tr.sort((t1, t2) -> t1.compareTo(t2));
        for (String id : tr) {
            Train t = trains.get(id);
            t.moveTrain(stations, tracks);

        }
        removeExpiredCargo(trains, stations);
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
        Load ld = new PerishableCargoLoad(startStationId, destStationId, cargoId, "PerishableCargo", weight,
        minsTillPerish, stations);
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
