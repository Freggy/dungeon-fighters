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

    ONE(200, Range.closed(1, 4)),
    TWO(300, Range.closed(5, 8)),
    THREE(400, Range.closed(9, 11));

    /**
     *
     * @return
     */
    public int getMaxGold() {
        return maxGold;
    }

    private int maxGold;
    private Range<Integer> range;

    /**
     *
     * @param maxGold
     */
    StageTier(int maxGold, Range<Integer> range) {
        this.maxGold = maxGold;
        this.range = range;
    }

    /**
     *
     * @param duration
     * @param timeSpent
     * @return
     */
    public int calculateGold(double duration, double timeSpent) {
        double percantage = 100 - (timeSpent / duration) * 100;
        if (percantage >= 64) return this.maxGold;
        else if (percantage >= 34) return Math.round(this.maxGold * 0.75F);
        else if (percantage >= 0) return Math.round(this.maxGold * 0.50F);
        else return 0;
    }

    /**
     *
     * @param position
     * @return
     */
    public static StageTier getStageTierByPosition(int position) {
        return Arrays.stream(StageTier.values()).filter(tier -> tier.range.contains(position)).findFirst().get();
    }

}
