package de.bergwerklabs.dungeonfighters.dungeongames.jnr.config;

import de.bergwerklabs.dungeonfighters.api.game.config.BaseDungeonGameConfig;

/**
 * Created by Yannic Rieger on 26.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class JnrConfig extends BaseDungeonGameConfig {
    /**
     * @param timerString
     * @param duration
     * @param warningTime
     */
    public JnrConfig(String timerString, int duration, int warningTime) {
        super(timerString, duration, warningTime);
    }
}
