package unsw.trains;

import unsw.response.models.TrackInfoResponse;
import unsw.utils.TrackType;

public class Track {
    private String trackId;
    private String fromStationId;
    private String toStationId;
    private int durability;
    private TrackType trackType;

    public Track(String trackId, String fromStationId, String toStationId, TrackType type) {
        this.trackId = trackId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.durability = 10;
        this.trackType = type;
    }

    public String getTrackId() {
        return this.trackId;
    }

    public String getFromStationId() {
        return this.fromStationId;
    }

    public String getToStationId() {
        return this.toStationId;
    }

    public void setTrackType(TrackType type) {
        this.trackType = type;
    }

    public TrackType getTrackType() {
        return this.trackType;
    }

    public void setDurability(int dur) {
        this.durability = dur;
    }
    
    public int getDurability() {
        return this.durability;
    }

    public TrackInfoResponse getTrackInfoResponseOfTrack() {
        return new TrackInfoResponse(trackId, fromStationId, toStationId, trackType, durability);
    }
}
