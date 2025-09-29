package unsw.trains;

import java.util.ArrayList;
import java.util.List;

import unsw.utils.TrackType;

import java.lang.Math;

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
        List<Load> loads = new ArrayList<>(t.getTrainLoads());
        for (Load load : loads) {
            if (load.getLoadDestinationStationId().equals(st.getStationId())) {
                t.delLoadFromTrain(load);
                load.setLoadReachedDestination();
            }
        }
    }

    public static void simulateLoadEmbark(Station st, Train t) {
        List<Load> loads = new ArrayList<>(st.getStationLoads());
        for (Load load : loads) {
            if (doesTrainReachDestination(t, load) &&
            (t.getCargoWeightOfTrain() + load.getLoadWeight()) <= t.getMaxTrainLoad() &&
            !load.hasLoadReachedDestination() && load.getLoadTrainAssigned() == null) {
                
                boolean can = false;
                if (load.getLoadType().equals("Passenger") && t.canPassengersBeOnThisTrain()) can = true;
                else if (load.getLoadType().equals("Cargo") && t.canCargoBeOnThisTrain()) can = true;
                else if (load.getLoadType().equals("PerishableCargo")
                && t.canCargoBeOnThisTrain() && load.shouldPerishableBeEmbarked(t, st)) can = true; 

                if (can) {
                    t.addLoadToTrain(load);
                    st.delLoadFromStation(load);
                    load.setLoadTrain(t.getTrainId());
                    break;
                }
            }
        }
    }

    public static Track isThereATrack(List<Track> tracks, String st1, String st2) {
        for (Track track : tracks) {
            if ((track.getFromStationId().equals(st1) && track.getToStationId().equals(st2))
            || (track.getFromStationId().equals(st2) && track.getToStationId().equals(st1))) return track;
        }
        return null;
    }

    public static List<Train> trainsOnStation(List<Train> trains, Station st) {
        List<Train> res = new ArrayList<>();
        for (Train train : trains) {
            if (train.getTrainPosition().equals(st.getStationCoordinates())) res.add(train);
        }
        res.sort((t1, t2) -> t1.getTrainId().compareTo(t2.getTrainId()));
        return res;
    }

    public static void trackSimulator(Train t, Track track) {
        if (track.getDurability() == 0 && track.getTrackType() == TrackType.BROKEN) {
            // do repair stuff in task cii
            return;
        };
        int dec = (int) (1 + Math.ceil((double) t.getTotalLoadWeightOfTrain() / 1000));
        track.decDurabilityOfTrack(dec);
        if (track.getDurability() == 0) {
            track.setTrackType(TrackType.BROKEN);
        }
    }
}
