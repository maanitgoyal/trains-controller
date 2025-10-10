package unsw.trains.Trains;

import java.util.List;

import unsw.trains.Loads.Load;
import unsw.trains.Loads.PassengerLoad;
import unsw.utils.Position;

public interface InterfaceTrainAndCargo {
    /**
     * Decreases the train's speed based on the cargo weight.
     */
    default public void decreaseTrainSpeed() {
        this.setSpeed(getSpeed() * (1 - ((this.getCargoWeightOfTrain()) * 0.01) / 100));
    }

    /**
     * Resets the train's speed to its original value.
     */
    default public void resetSpeed() {
        this.setSpeed(this.getOriginalSpeed());
    }

    /**
     * Calculates the total weight of cargo (excluding passengers) on the train.
     * 
     * @return the total cargo weight
     */
    default public int getCargoWeightOfTrain() {
        int s = 0;
        for (Load load : this.getTrainLoads()) if (!(load instanceof PassengerLoad)) s += load.getLoadWeight();
        return s;
    }

    List<Load> getTrainLoads();
    Position getTrainPosition();
    double getSpeed();
    void setSpeed(double speed);
    double getOriginalSpeed();
}