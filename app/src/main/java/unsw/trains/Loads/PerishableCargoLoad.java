package unsw.trains.Loads;

import java.util.HashMap;

import unsw.trains.Stations.Station;
import unsw.trains.Trains.Train;
import unsw.utils.Position;

public class PerishableCargoLoad extends Load {
    private int minsTillPerish;

    public PerishableCargoLoad(String startStationId, String destStationId, String loadId, String loadType, int weight,
    int minsTillPerish, HashMap<String, Station> stations) {
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
     * @param stations Hashmap of stations
     * @return true if the cargo can be embarked and reach before perishing, false
     *         otherwise
     */
    public boolean shouldPerishableBeEmbarked(Train t, HashMap<String, Station> stations) {
        double time = 0;
        String cur = t.getLastStationVisited();
        while (!cur.equals(this.getLoadDestinationStationId())) {
            Station st = stations.get(cur);
            Position pos = st.getStationCoordinates();
            String next = t.findNextStationToVisit(cur);
            Station stNext = stations.get(next);
            double dist = pos.distance(stNext.getStationCoordinates());
            time += (dist / t.getSpeed());
            cur = next;
        }
        return time <= this.getMinsTillPerished();
    }
}
