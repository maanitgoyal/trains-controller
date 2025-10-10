package unsw.trains.Loads;

import java.util.HashMap;

import unsw.trains.Stations.Station;
import unsw.trains.Trains.Train;
import unsw.utils.Position;

public class PerishableCargoLoad extends Load {
    private int minsTillPerish;

    public PerishableCargoLoad(String startStationId, String destStationId, String loadId, String loadType, int weight, int minsTillPerish, HashMap<String, Station> stations) {
        super(startStationId, destStationId, loadId, loadType, weight, stations);
        this.minsTillPerish = minsTillPerish;
    }

    /**
     * Gets the number of minutes until the cargo perishes.
     * 
     * @return minutes until perish
     */
    public int getMinsTillPerished() {
        return this.minsTillPerish;
    }

    /**
     * Decrements the minutes until the cargo perishes by one.
     */
    public void decMinsTillPerished() {
        this.minsTillPerish--;
    }

    /**
     * Determines if the perishable cargo can be embarked on the train at the
     * station without perishing before arrival.
     * 
     * @param t The train to check.
     * @param st The station to check.
     * @return true if the cargo can be embarked and reach before perishing, false
     *         otherwise
     */
    public boolean shouldPerishableBeEmbarked(Train t, Station st) {
        Position cur = this.getLoadCurrPosition();
        if ((cur.distance(st.getStationCoordinates()) / t.getSpeed()) <= this.minsTillPerish)
            return true;
        return false;
    }
}
