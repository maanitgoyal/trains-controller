package unsw.trains;

import java.util.Iterator;
import java.util.List;

import unsw.utils.Position;

public interface InterfaceTrainAndCargo {
    /**
     * Removes expired perishable cargo from the train and updates their positions
     * and timers.
     */
    default public void removeExpiredCargo() {
        Iterator<Load> it = this.getTrainLoads().iterator();
        while (it.hasNext()) {
            Load load = it.next();
            if (load instanceof PerishableCargoLoad) {
                PerishableCargoLoad oth = (PerishableCargoLoad) load;
                if (oth.getMinsTillPerished() == 0) {
                    it.remove();
                } else {
                    Position cor = this.getTrainPosition();
                    oth.setLoadCurrPosition(cor);
                    oth.decMinsTillPerished();
                }
            }
        }
    }

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
        for (Load load : this.getTrainLoads()) {
            if (!(load instanceof PassengerLoad))
                s += load.getLoadWeight();
        }
        return s;
    }

    List<Load> getTrainLoads();
    Position getTrainPosition();
    double getSpeed();
    void setSpeed(double speed);
    double getOriginalSpeed();
}