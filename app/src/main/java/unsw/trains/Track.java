package unsw.trains;

import unsw.utils.TrackType;

public class Track {
    private String trackId;
    private String fromStationId;
    private String toStationId;
    private int durability;
    private TrackType trackType;

    public Track(String trackId, String fromStationId, String toStationId) {
        this.trackId = trackId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.durability = 10;
        this.trackType = TrackType.NORMAL;
    }

    public String getTrackId() {
        return this.trackId;
    }

    public String getFromStationId() {
        return fromStationId;
    }

    public String getToStationId() {
        return toStationId;
    }
}
