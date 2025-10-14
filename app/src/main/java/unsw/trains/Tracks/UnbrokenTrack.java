package unsw.trains.Tracks;

import unsw.trains.Trains.RepairTrain;
import unsw.trains.Trains.Train;
import unsw.utils.TrackType;

public class UnbrokenTrack extends Track {
    public static final int MECHANICS_REPAIR = 2;
    private static final int MAX_DURABILITY = 10;
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
        super.setDurability(Math.min(MAX_DURABILITY, super.getDurability() + inc));
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
        this.incDurabilityOfTrack(1 + mech * MECHANICS_REPAIR);
        if (this.getDurability() == MAX_DURABILITY) this.setTrackType(TrackType.UNBROKEN);
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
