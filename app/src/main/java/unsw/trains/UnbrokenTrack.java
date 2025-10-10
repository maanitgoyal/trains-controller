package unsw.trains;

import unsw.utils.TrackType;

public class UnbrokenTrack extends Track {
    public UnbrokenTrack(String trackId, String fromStationId, String toStationId) {
        super(trackId, fromStationId, toStationId, TrackType.UNBROKEN);
    }

    /**
     * Decreases the durability of the track by a specified amount, not going below
     * zero.
     * 
     * @param dec the amount to decrease
     */
    public void decDurabilityOfTrack(int dec) {
        super.setDurability(Math.max(super.getDurability() - dec, 0));
    }

    /**
     * Increases the durability of the track by a specified amount, not exceeding
     * 10.
     * 
     * @param inc the amount to increase
     */
    public void incDurabilityOfTrack(int inc) {
        super.setDurability(Math.min(10, super.getDurability() + inc));
    }
}
