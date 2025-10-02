package unsw.trains;

import unsw.utils.TrackType;

public class UnbrokenTrack extends Track {
    public UnbrokenTrack(String trackId, String fromStationId, String toStationId) {
        super(trackId, fromStationId, toStationId, TrackType.UNBROKEN);
    }

    public void decDurabilityOfTrack(int dec) {
        super.setDurability(Math.max(super.getDurability() - dec, 0));;
    }

    public void incDurabilityOfTrack(int inc) {
        super.setDurability(Math.min(10, super.getDurability() + inc));
    }
}
