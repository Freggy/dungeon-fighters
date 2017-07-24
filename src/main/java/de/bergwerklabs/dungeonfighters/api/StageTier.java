package de.bergwerklabs.dungeonfighters.api;

/**
 * Created by Yannic Rieger on 24.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public enum StageTier {

    ONE(200),
    TWO(300),
    THREE(400);

    /**
     *
     * @return
     */
    public int getMaxGold() {
        return maxGold;
    }

    private int maxGold;

    /**
     *
     * @param maxGold
     */
    StageTier(int maxGold) {
        this.maxGold = maxGold;
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
        if (position > 1 && position < 5) return StageTier.ONE;
        else if (position > 5 && position < 9) return StageTier.TWO;
        else if (position > 9 && position < 13) return StageTier.THREE;
        else return StageTier.ONE;
    }

}
