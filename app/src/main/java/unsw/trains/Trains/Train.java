package unsw.trains.Trains;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import unsw.exceptions.InvalidRouteException;
import unsw.response.models.LoadInfoResponse;
import unsw.response.models.TrainInfoResponse;
import unsw.trains.Loads.CargoLoad;
import unsw.trains.Loads.Load;
import unsw.trains.Loads.PassengerLoad;
import unsw.trains.Loads.PerishableCargoLoad;
import unsw.trains.Stations.Station;
import unsw.trains.Tracks.Track;
import unsw.trains.Tracks.UnbrokenTrack;
import unsw.utils.Position;
import unsw.utils.TrackType;

public class Train {
    private String trainId;
    private String type;
    private String lastStationVisited;
    private List<String> route = new ArrayList<>();
    private double speed;
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
            Position pos, double speed, boolean passengers,
            boolean cargo, int maxLoad, boolean circularRoute) throws InvalidRouteException {
        this.trainId = trainId;
        this.lastStationVisited = stationId;
        this.route = route;
        this.location = stationId;
        this.type = type;
        this.position = pos;
        this.speed = speed;
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
     * Determines the next station a train should visit based on its route and
     * direction.
     *
     * @param t The train.
     * @return The ID of the next station to visit.
     */
    public String findNextStationToVisit(String st) {
        List<String> route = this.getRoute();
        int last = route.indexOf(st);
        int routeSize = route.size();
        String nextStation;
        if (last == routeSize - 1) {
            if (!this.isCircular()) {
                nextStation = route.get(last - 1);
                if (this.getAtStation()) this.setReverseRoute();
            } else nextStation = route.get(0);
        } 
        else if (last == 0 && this.getReverseRoute()) {
            nextStation = route.get(1);
            if (this.getAtStation()) this.setReverseRoute();
        } 
        else if (!this.getReverseRoute()) nextStation = route.get(last + 1);
        else nextStation = route.get(last - 1);
        return nextStation;
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
     * Checks if the train's route contains the destination station of the load.
     *
     * @param train The train to check.
     * @param ld The load whose destination is checked.
     * @return true if the train reaches the load's destination, false otherwise.
     */
    public boolean doesTrainReachDestination(Load ld) {
        return this.getRoute().contains(ld.getLoadDestinationStationId());
    }

    /**
     * Simulates disembarking loads from a train at a station.
     * Removes loads from the train if their destination matches the station.
     *
     * @param st The station where loads may disembark.
     * @param t The train from which loads may disembark.
     */
    public void simulateLoadDisembark(Station st) {
        List<Load> loads = new ArrayList<>(this.getTrainLoads());
        for (Load load : loads) {
            if (Objects.equals(load.getLoadDestinationStationId(), st.getStationId())) this.delLoadFromTrain(load);
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
    public void simulateLoadEmbark(Station st, HashMap<String, Station> stations) {
        List<Load> loads = new ArrayList<>(st.getStationLoads());
        for (Load load : loads) {
            if (doesTrainReachDestination(load) && (this.getTotalLoadWeightOfTrain() + load.getLoadWeight()) <= this.getMaxTrainLoad()) {
                boolean embark = false;
                if (load instanceof PassengerLoad && this.canPassengersBeOnThisTrain()) embark = true;
                else if (load instanceof CargoLoad && this.canCargoBeOnThisTrain()) embark = true;
                else if (load instanceof PerishableCargoLoad && this.canCargoBeOnThisTrain()) {
                    PerishableCargoLoad oth = (PerishableCargoLoad) load;
                    if (oth.shouldPerishableBeEmbarked(this, stations)) embark = true;
                }
                if (embark) {this.addLoadToTrain(load); st.delLoadFromStation(load);}
            }
        }
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
     * Simulates the movement and actions of all trains for one time step.
     * Handles embarking/disembarking, movement, and track/repair logic.
     *
     * @param trains The map of all trains.
     * @param stations The map of all stations.
     * @param tracks The map of all tracks.
     */
    public void moveTrain(HashMap<String, Station> stations, HashMap<String, Track> tracks) {
        String stationIdCur = this.getLastStationVisited();
        Station stationCur = stations.get(stationIdCur);
        String stationIdFinal = this.findNextStationToVisit(stationIdCur);
        Station stationFinal = stations.get(stationIdFinal);
        
        if (stationFinal.checkIfStationIsFull()) return;
        Track track = isThereATrack(tracks, stationIdCur, stationIdFinal);
        if (track != null && track.getTrackType() == TrackType.BROKEN && !(this instanceof RepairTrain)) return;
        
        Position destination = stationFinal.getStationCoordinates();
        Position currentTrainPosition = this.getTrainPosition();

        if (this.getAtStation()) {
            this.simulateLoadEmbark(stationCur, stations);
            this.setAtStation();
        }
        IntermediateTrainAndCargo cargo = (this instanceof IntermediateTrainAndCargo) ? (IntermediateTrainAndCargo) this : null;
        
        if (track != null && track.getTrackType() == TrackType.BROKEN && this instanceof RepairTrain) {
            UnbrokenTrack oth = (UnbrokenTrack) track;
            oth.fixDurabilityOfTrack((RepairTrain) this);
            return;
        }
        
        if (cargo != null) {
            if (currentTrainPosition.isInBound(destination, cargo.getSpeed())) {
                cargo.setTrainPosition(destination);
                cargo.setLastStationVisited(stationIdFinal);
                cargo.setLocation(stationIdFinal);
                if (track instanceof UnbrokenTrack) {
                    UnbrokenTrack oth = (UnbrokenTrack) track;
                    oth.trackSimulator(this);
                }
                cargo.simulateLoadDisembark(stationFinal);
                cargo.setAtStation();
                return;
            }
            cargo.setTrainPosition(currentTrainPosition.calculateNewPosition(destination, cargo.getSpeed()));
            cargo.setLocation(track.getTrackId());
        } else {
            if (currentTrainPosition.isInBound(destination, this.getSpeed())) {
                this.setTrainPosition(destination);
                this.setLastStationVisited(stationIdFinal);
                this.setLocation(stationIdFinal);
                if (track instanceof UnbrokenTrack) {
                    UnbrokenTrack oth = (UnbrokenTrack) track;
                    oth.trackSimulator(this);
                }
                this.simulateLoadDisembark(stationFinal);
                this.setAtStation();
                return;
            }
            this.setTrainPosition(currentTrainPosition.calculateNewPosition(destination, this.getSpeed()));
            this.setLocation(track.getTrackId());
        }
        
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
