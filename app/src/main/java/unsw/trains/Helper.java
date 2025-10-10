package unsw.trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Loads.CargoLoad;
import unsw.trains.Loads.Load;
import unsw.trains.Loads.PassengerLoad;
import unsw.trains.Loads.PerishableCargoLoad;
import unsw.trains.Stations.CargoStation;
import unsw.trains.Stations.DepotStation;
import unsw.trains.Stations.PassengerStation;
import unsw.trains.Stations.Station;
import unsw.trains.Tracks.Track;
import unsw.trains.Tracks.UnbrokenTrack;
import unsw.trains.Trains.BulletTrain;
import unsw.trains.Trains.CargoTrain;
import unsw.trains.Trains.InterfaceTrainAndCargo;
import unsw.trains.Trains.PassengerTrain;
import unsw.trains.Trains.RepairTrain;
import unsw.trains.Trains.Train;
import unsw.utils.Position;
import unsw.utils.TrackType;

public class Helper {
    /**
     * Checks if the train's route contains the destination station of the load.
     *
     * @param train The train to check.
     * @param ld The load whose destination is checked.
     * @return true if the train reaches the load's destination, false otherwise.
     */
    public static boolean doesTrainReachDestination(Train train, Load ld) {
        return train.getRoute().contains(ld.getLoadDestinationStationId());
    }

    /**
     * Simulates disembarking loads from a train at a station.
     * Removes loads from the train if their destination matches the station.
     *
     * @param st The station where loads may disembark.
     * @param t The train from which loads may disembark.
     */
    public static void simulateLoadDisembark(Station st, Train t) {
        List<Load> loads = new ArrayList<>(t.getTrainLoads());
        for (Load load : loads) {
            if (Objects.equals(load.getLoadDestinationStationId(), st.getStationId())) t.delLoadFromTrain(load);
        }
    }

    /**
     * Simulates embarking loads onto a train at a station.
     * Loads are added to the train if they are eligible and the train can carry
     * them.
     *
     * @param st The station where loads may disembark.
     * @param t The train to which loads may embark.
     */
    public static void simulateLoadEmbark(Station st, Train t) {
        List<Load> loads = new ArrayList<>(st.getStationLoads());
        for (Load load : loads) {
            if (doesTrainReachDestination(t, load) && (t.getTotalLoadWeightOfTrain() + load.getLoadWeight()) <= t.getMaxTrainLoad()) {
                boolean embark = false;
                if (load instanceof PassengerLoad && t.canPassengersBeOnThisTrain()) embark = true;
                else if (load instanceof CargoLoad && t.canCargoBeOnThisTrain()) embark = true;
                else if (load instanceof PerishableCargoLoad && t.canCargoBeOnThisTrain()) {
                    PerishableCargoLoad oth = (PerishableCargoLoad) load;
                    if (oth.shouldPerishableBeEmbarked(t, st)) embark = true;
                }
                if (embark) {t.addLoadToTrain(load); st.delLoadFromStation(load);}
            }
        }
    }

    /**
     * Checks if there is a track between two stations.
     *
     * @param tracks The map of all tracks.
     * @param st1 The first station ID.
     * @param st2 The second station ID.
     * @return The Track if it exists, null otherwise.
     */
    public static Track isThereATrack(HashMap<String, Track> tracks, String st1, String st2) {
        for (Track track : tracks.values()) {
            if ((track.getFromStationId().equals(st1) && track.getToStationId().equals(st2))
                || (track.getFromStationId().equals(st2) && track.getToStationId().equals(st1))) return track;
        }
        return null;
    }

    /**
     * Returns a sorted list of trains currently at a given station.
     *
     * @param trains The list of all trains.
     * @param st The station to check.
     * @return List of trains at the station, sorted by train ID.
     */
    public static List<Train> trainsOnStation(List<Train> trains, Station st) {
        List<Train> res = new ArrayList<>();
        for (Train train : trains) {
            if (train.getTrainPosition().equals(st.getStationCoordinates())) res.add(train);
        }
        res.sort((t1, t2) -> t1.getTrainId().compareTo(t2.getTrainId()));
        return res;
    }

    /**
     * Repairs a track by increasing its durability using mechanics on the repair
     * train.
     *
     * @param t The repair train.
     * @param track The track to repair.
     */
    public static void fixDurabilityOfTrack(RepairTrain t, UnbrokenTrack track) {
        int mech = t.getMechanicsOnTrain();
        track.incDurabilityOfTrack(1 + mech * 2);
        if (track.getDurability() == 10) track.setTrackType(TrackType.UNBROKEN);
    }

    /**
     * Simulates the effect of a train traveling on a track, decreasing its
     * durability.
     *
     * @param t The train traveling on the track.
     * @param track The track being traveled on.
     */
    public static void trackSimulator(Train t, Track track) {
        if (track.getTrackType() != TrackType.UNBROKEN || t instanceof RepairTrain) return;
        int dec = (int) (1 + Math.ceil((double) t.getTotalLoadWeightOfTrain() / 1000));
        UnbrokenTrack oth = (UnbrokenTrack) track;
        oth.decDurabilityOfTrack(dec);
        if (oth.getDurability() == 0) oth.setTrackType(TrackType.BROKEN);
    }

    /**
     * Checks if a route is valid (not circular) given the tracks.
     *
     * @param tracks The map of all tracks.
     * @param route The route to check.
     * @return The route if valid.
     * @throws InvalidRouteException if the route is circular.
     */
    public static List<String> isRouteValid(HashMap<String, Track> tracks, List<String> route)
            throws InvalidRouteException {
        if (tracks.size() < 3) return route;
        String st1 = route.get(0);
        String st2 = route.get(route.size() - 1);
        Track t = isThereATrack(tracks, st1, st2);
        if (t == null) return route;
        else throw new InvalidRouteException("Route cannot be circular!!");
    }

    /**
     * Creates a station of the specified type and coordinates.
     *
     * @param stationId The ID of the station.
     * @param type The type of the station.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return The created Station object, or null if type is invalid.
     */
    public static Station createStationHelper(String stationId, String type, double x, double y) {
        Station st;
        if (Objects.equals("PassengerStation", type)) st = new PassengerStation(stationId, type, x, y);
        else if (Objects.equals("CargoStation", type)) st = new CargoStation(stationId, type, x, y);
        else if (Objects.equals("DepotStation", type)) st = new DepotStation(stationId, type, x, y);
        else if (Objects.equals("CentralStation", type)) st = new DepotStation(stationId, type, x, y);
        else st = null;
        return st;
    }

    /**
     * Creates a train of the specified type, starting station, and route.
     *
     * @param trainId The ID of the train.
     * @param type The type of the train.
     * @param stationId The starting station ID.
     * @param route The route for the train.
     * @param tracks The map of all tracks.
     * @param stations The map of all stations.
     * @return The created Train object, or null if type is invalid.
     * @throws InvalidRouteException if the route is invalid.
     */
    public static Train createTrainHelper(String trainId, String type, String stationId, List<String> route,
            HashMap<String, Track> tracks, HashMap<String, Station> stations) throws InvalidRouteException {
        Train t;
        if (Objects.equals("PassengerTrain", type)) t = new PassengerTrain(trainId, type, stationId, route, stations.get(stationId).getStationCoordinates(), tracks);
        else if (Objects.equals("BulletTrain", type)) t = new BulletTrain(trainId, type, stationId, route, stations.get(stationId).getStationCoordinates(), tracks);
        else if (Objects.equals("CargoTrain", type)) t = new CargoTrain(trainId, type, stationId, route, stations.get(stationId).getStationCoordinates(), tracks);
        else if (Objects.equals("RepairTrain", type)) t = new RepairTrain(trainId, type, stationId, route, stations.get(stationId).getStationCoordinates(), tracks);
        else t = null;
        return t;
    }

    /**
     * Determines the next station a train should visit based on its route and
     * direction.
     *
     * @param t The train.
     * @return The ID of the next station to visit.
     */
    public static String findNextStationToVisit(Train t) {
        String st = t.getLastStationVisited();
        List<String> route = t.getRoute();
        int last = route.indexOf(st);
        int routeSize = route.size();
        String nextStation;
        if (last == routeSize - 1) {
            if (!t.isCircular()) {
                nextStation = route.get(last - 1);
                if (t.getAtStation()) t.setReverseRoute();
            } else nextStation = route.get(0);
        } 
        else if (last == 0 && t.getReverseRoute()) {
            nextStation = route.get(1);
            if (t.getAtStation()) t.setReverseRoute();
        } 
        else if (!t.getReverseRoute()) nextStation = route.get(last + 1);
        else nextStation = route.get(last - 1);
        return nextStation;
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

    /**
     * Simulates the movement and actions of all trains for one time step.
     * Handles embarking/disembarking, movement, and track/repair logic.
     *
     * @param trains The map of all trains.
     * @param stations The map of all stations.
     * @param tracks The map of all tracks.
     */
    public static void helperSimulate(HashMap<String, Train> trains, HashMap<String, Station> stations,
            HashMap<String, Track> tracks) {
        removeExpiredCargo(trains, stations);
        List<String> tr = new ArrayList<>(trains.keySet());
        tr.sort((t1, t2) -> t1.compareTo(t2));
        for (String id : tr) {
            Train t = trains.get(id);

            String stationIdCur = t.getLastStationVisited();
            Station stationCur = stations.get(stationIdCur);
            String stationIdFinal = findNextStationToVisit(t);
            Station stationFinal = stations.get(stationIdFinal);

            if (stationFinal.checkIfStationIsFull(new ArrayList<>(trains.values()))) continue;
            Track track = Helper.isThereATrack(tracks, stationIdCur, stationIdFinal);
            if (track != null && track.getTrackType() == TrackType.BROKEN && !(t instanceof RepairTrain)) continue;

            Position destination = stationFinal.getStationCoordinates();
            Position currentTrainPosition = t.getTrainPosition();
            if (t.getAtStation()) {
                Helper.simulateLoadEmbark(stationCur, t);
                t.setAtStation();
            }
            InterfaceTrainAndCargo canCarryCargo = (t instanceof InterfaceTrainAndCargo) ? (InterfaceTrainAndCargo) t : null;

            if (canCarryCargo != null) canCarryCargo.decreaseTrainSpeed();

            if (track != null && track.getTrackType() == TrackType.BROKEN && t instanceof RepairTrain) {
                Helper.fixDurabilityOfTrack((RepairTrain) t, (UnbrokenTrack) track);
            }

            if (currentTrainPosition.isInBound(destination, t.getSpeed())) {
                t.setTrainPosition(destination);
                t.setLastStationVisited(stationIdFinal);
                t.setLocation(stationIdFinal);
                Helper.trackSimulator(t, track);
                Helper.simulateLoadDisembark(stationFinal, t);
                if (canCarryCargo != null)
                    canCarryCargo.resetSpeed();
                t.setAtStation();
                continue;
            }
            t.setTrainPosition(currentTrainPosition.calculateNewPosition(destination, t.getSpeed()));
            t.setLocation(track.getTrackId());
        }
    }
}
