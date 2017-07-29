package de.bergwerklabs.dungeonfighters.api.game.config;

/**
 * Created by Yannic Rieger on 29.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class BaseDungeonGameConfig {

    /**
     *
     */
    public String getTimerString() { return this.timerString; }

    /**
     *
     */
    public int getDuration() { return this.duration; }

    /**
     *
     */
    public int getWarningTime() { return this.warningTime; }

    private String timerString;
    private int duration;
    private int warningTime;

    /**
     * @param timerString
     * @param duration
     * @param warningTime
     */
    public BaseDungeonGameConfig(String timerString, int duration, int warningTime) {
        this.timerString = timerString;
        this.duration = duration;
        this.warningTime = warningTime;
    }
}
