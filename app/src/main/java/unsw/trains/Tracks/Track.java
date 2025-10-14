package unsw.trains.Tracks;

import unsw.response.models.TrackInfoResponse;
import unsw.utils.TrackType;

public abstract class Track {
    private String trackId;
    private String fromStationId;
    private String toStationId;
    private int durability;
    private TrackType trackType;
    private static final int MAX_DURABILITY = 10;

    public Track(String trackId, String fromStationId, String toStationId, TrackType type) {
        this.trackId = trackId;
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.durability = MAX_DURABILITY;
        this.trackType = type;
    }

    /**
     * Gets the unique identifier of the track.
     *
     * @return the track ID
     */
    public String getTrackId() {
        return this.trackId;
    }

    /**
     * Gets the ID of the station from which the track starts.
     *
     * @return the from-station ID
     */
    public String getFromStationId() {
        return this.fromStationId;
    }

    /**
     * Gets the ID of the station to which the track leads.
     *
     * @return the to-station ID
     */
    public String getToStationId() {
        return this.toStationId;
    }

    /**
     * Sets the type of the track.
     *
     * @param type the track type to set
     */
    public void setTrackType(TrackType type) {
        this.trackType = type;
    }

    /**
     * Gets the type of the track.
     *
     * @return the track type
     */
    public TrackType getTrackType() {
        return this.trackType;
    }

    /**
     * Gets the MAX_DURABILITY of the track.
     *
     * @return the MAX_DURABILITY
     */
    public int getMaxDurability() {
        return MAX_DURABILITY;
    }

    /**
     * Sets the durability of the track.
     *
     * @param dur the durability value to set
     */
    public void setDurability(int dur) {
        this.durability = dur;
    }

    /**
     * Gets the durability of the track.
     *
     * @return the durability value
     */
    public int getDurability() {
        return this.durability;
    }

    /**
     * Creates a TrackInfoResponse object for this track.
     *
     * @return a TrackInfoResponse containing track details
     */
    public TrackInfoResponse getTrackInfoResponseOfTrack() {
        return new TrackInfoResponse(trackId, fromStationId, toStationId, trackType, durability);
    }
}
