package unsw.trains;

import java.util.List;

public class Helper {
    public static Station findStation(List<Station> stations, String stationId) {
        for (Station station : stations) {
            if (station.getStationId().equals(stationId)) return station;
        }
        return null;
    }
}
