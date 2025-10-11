package unsw.trains.Tracks;

import unsw.trains.Trains.RepairTrain;
import unsw.trains.Trains.Train;
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

    /**
     * Repairs a track by increasing its durability using mechanics on the repair
     * train.
     *
     * @param t The repair train.
     * @param track The track to repair.
     */
    public void fixDurabilityOfTrack(RepairTrain t) {
        int mech = t.getMechanicsOnTrain();
        this.incDurabilityOfTrack(1 + mech * 2);
        if (this.getDurability() == 10) this.setTrackType(TrackType.UNBROKEN);
    }

    /**
     * Simulates the effect of a train traveling on a track, decreasing its
     * durability.
     *
     * @param t The train traveling on the track.
     * @param track The track being traveled on.
     */
    public void trackSimulator(Train t) {
        if (this.getTrackType() != TrackType.UNBROKEN || t instanceof RepairTrain) return;
        int dec = (int) (1 + Math.ceil((double) t.getTotalLoadWeightOfTrain() / 1000));
        this.decDurabilityOfTrack(dec);
        if (this.getDurability() == 0) this.setTrackType(TrackType.BROKEN);
    }
}
