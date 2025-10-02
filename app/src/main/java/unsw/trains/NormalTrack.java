package unsw.trains;

import unsw.utils.TrackType;

public class NormalTrack extends Track {
    public NormalTrack(String trackId, String fromStationId, String toStationId) {
        super(trackId, fromStationId, toStationId, TrackType.NORMAL);
    }
}
