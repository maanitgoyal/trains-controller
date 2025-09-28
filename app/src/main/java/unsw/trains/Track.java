package unsw.trains;

import unsw.utils.TrackType;

public class Track {
    private String trackId;
    private String fromStationId;
    private String toStationId;
    private int durability;
    private TrackType trackType;
    private boolean isBreakable;

    public Track(String trackId, String fromStationId, String toStationId, boolean isBreakable) {
        this.trackId = trackId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.durability = 10;
        this.isBreakable = isBreakable;
        this.trackType = (!isBreakable) ? TrackType.NORMAL : TrackType.UNBROKEN;
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

    public TrackType getTrackType() {
        return this.trackType;
    }

    public int getDurability() {
        return this.durability;
    }

    public void decDurabilityOfTrack(int dec) {
        this.durability -= dec;
    }

    public void incDurabilityOfTrack(int inc) {
        this.durability += inc;
    }

    public boolean isTrackBreakable() {
        return this.isBreakable;
    }
}
