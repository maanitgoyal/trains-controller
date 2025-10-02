package unsw.trains;

import java.util.HashMap;

import unsw.utils.Position;

public class PerishableCargoLoad extends Load {
    private int minsTillPerish;

    public PerishableCargoLoad(String startStationId, String destStationId, String loadId, String loadType, int weight, int minsTillPerish, HashMap<String, Station> stations) {
        super(startStationId, destStationId, loadId, loadType, weight, stations);
        this.minsTillPerish = minsTillPerish;
    }

    public int getMinsTillPerished() {
        return this.minsTillPerish;
    }

    public void decMinsTillPerished() {
        this.minsTillPerish--;
    }

    public boolean shouldPerishableBeEmbarked(Train t, Station st) {
        Position cur = this.getLoadCurrPosition();
        if ((cur.distance(st.getStationCoordinates()) / t.getSpeed()) <= this.minsTillPerish) return true;
        return false;
    }
}
