package unsw.trains.Trains;

import java.util.HashMap;
import java.util.List;

import unsw.exceptions.InvalidRouteException;
import unsw.trains.Loads.Load;
import unsw.trains.Loads.PassengerLoad;
import unsw.trains.Tracks.Track;
import unsw.utils.Position;

public abstract class IntermediateTrainAndCargo extends Train {
    private static final double SPEED_REDUCTION = 0.01;
    public IntermediateTrainAndCargo(String trainId, String type, String stationId, List<String> route,
    Position pos, HashMap<String, Track> tracks, double speed, boolean passengers, boolean cargo,
    int maxLoad, boolean circularRoute) throws InvalidRouteException {
        super(trainId, type, stationId, route, pos, speed, passengers, cargo, maxLoad, circularRoute);
    }

    /**
     * Calculates the total weight of cargo (excluding passengers) on the train.
     *
     * @return the total cargo weight
     */
    public int getCargoWeightOfTrain() {
        int s = 0;
        for (Load load : this.getTrainLoads()) if (!(load instanceof PassengerLoad)) s += load.getLoadWeight();
        return s;
    };

    /**
     * Overrides the getSpeed method in the parent class to consider
     * reduction of speed due to cargo weight.
     */
    @Override
    public double getSpeed() {
        return super.getSpeed() * (1 - ((this.getCargoWeightOfTrain()) * SPEED_REDUCTION) / 100);
    }
}
