package unsw.trains;

import java.util.Iterator;
import java.util.List;

import unsw.utils.Position;

public interface InterfaceTrainAndCargo  {
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

    default public void decreaseTrainSpeed() {
        this.setSpeed(getSpeed() * (1 - ((this.getCargoWeightOfTrain()) * 0.01) / 100));
    }

    default public void resetSpeed() {
        this.setSpeed(this.getOriginalSpeed());
    }

    List<Load> getTrainLoads();
    Position getTrainPosition();
    double getSpeed();
    void setSpeed(double speed);
    double getOriginalSpeed();
    int getCargoWeightOfTrain();
}