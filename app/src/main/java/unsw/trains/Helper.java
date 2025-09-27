package unsw.trains;

import java.util.ArrayList;
import java.util.List;

import unsw.response.models.LoadInfoResponse;

public class Helper {
    public static Station findStation(List<Station> stations, String stationId) {
        for (Station station : stations) {
            if (station.getStationId().equals(stationId)) return station;
        }
        return null;
    }

    public static boolean doesTrainReachDestination(Train train, Load ld) {
        List<String> route = train.getRoute();
        if (train.isCircular() && route.contains(ld.getLoadDestinationStationId())) return true;
        else {
            int id = train.getLastIndex();
            for (int i = id; i < route.size(); i++) {
                if (route.get(i).equals(ld.getLoadDestinationStationId())) return true;
            }
        }
        return false;
    }

    public static void simulateLoadDisembark(Station st, Train t) {
        List<Load> loads = t.getTrainLoads();
        for (Load load : loads) {
            if (load.getLoadDestinationStationId().equals(st.getStationId())) {
                st.addLoadToStation(load);
                st.addLoadInfoResponseToStation(new LoadInfoResponse(load.getLoadId(), load.getLoadType()));
                t.delLoadFromTrain(load);
                t.delLoadInfoResponseFromTrain(new LoadInfoResponse(load.getLoadId(), load.getLoadType()));
                load.setLoadReachedDestination();
            }
        }
    }

    public static void simulateLoadEmbark(Station st, Train t) {
        List<Load> loads = st.getStationLoads();
        for (Load load : loads) {
            if (doesTrainReachDestination(t, load) &&
            (t.getLoadWeightOfTrain() + load.getLoadWeight()) <= t.getMaxTrainLoad() &&
            !load.hasLoadReachedDestination() && load.getLoadTrainAssigned() == null) {
                boolean can = false;
                if (load.getLoadType().equals("Passenger") && t.canPassengersBeOnThisTrain()) can = true;
                else if (load.getLoadType().equals("Cargo") && t.canCargoBeOnThisTrain()) can = true;
               
                System.out.println("Value of can: " + can);
                if (can) {
                    t.addLoadToTrain(load);
                    t.addLoadInfoResponseToTrain(new LoadInfoResponse(load.getLoadId(), load.getLoadType()));
                    st.delLoadFromStation(load);
                    st.delLoadInfoResponseFromStation(new LoadInfoResponse(load.getLoadId(), load.getLoadType()));
                    load.setLoadTrain(t.getTrainId());
                }
            }
        }
    }

    public static List<Train> trainsOnStation(List<Train> trains, Station st) {
        List<Train> res = new ArrayList<>();
        for (Train train : trains) {
            if (train.getTrainPosition().equals(st.getStationCoordinates())) res.add(train);
        }
        res.sort((t1, t2) -> t1.getTrainId().compareTo(t2.getTrainId()));
        return res;
    }
}
