package de.bergwerklabs.dungeonfighters.api;

import com.google.common.collect.Range;

import java.util.Arrays;

/**
 * Created by Yannic Rieger on 24.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public enum StageTier {

    ONE(200, 1, Range.closed(1, 4)),
    TWO(300, 2, Range.closed(5, 8)),
    THREE(400, 3, Range.closed(9, 11)),
    END(0, 4, null);

    /**
     *
     * @return
     */
    public int getMaxGold() {
        return maxGold;
    }

    public StageTier getNext() {
        return this.next = Arrays.stream(StageTier.values()).filter(tier -> tier.tierValue == tierValue)
                                 .findFirst().orElse(null);
    }

    private int maxGold, tierValue;
    private Range<Integer> range;
    private StageTier next;

    /**
     *
     * @param maxGold
     */
    StageTier(int maxGold, int tierValue, Range<Integer> range) {
        this.maxGold = maxGold;
        this.range = range;
        this.tierValue = tierValue;
        this.next = null;
    }

    /**
     *
     * @param position
     * @return
     */
    public static StageTier getStageTierByPosition(int position) {
        return Arrays.stream(StageTier.values()).filter(tier -> tier.range.contains(position)).findFirst().orElse(null);
    }

    /**
     *
     * @param duration
     * @param timeSpent
     * @return
     */
    public int calculateGold(double duration, double timeSpent) {
        double percantage = (timeSpent / duration) * 100;
        if (percantage >= 64) return this.maxGold;
        else if (percantage >= 34) return Math.round(this.maxGold * 0.75F);
        else if (percantage >= 0) return Math.round(this.maxGold * 0.50F);
        else return 0;
    }

    public boolean isHigherTier(StageTier tier) {
        return this.tierValue > tier.tierValue;
    }

    public boolean isLowerTier(StageTier tier) {
        return !this.isHigherTier(tier);
    }
}
