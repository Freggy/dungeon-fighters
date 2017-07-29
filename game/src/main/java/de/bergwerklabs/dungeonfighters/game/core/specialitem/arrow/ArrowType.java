package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p> Type of arrows that deal special damage.
 *
 * @author Yannic Rieger
 */
public enum ArrowType {
    POISON("§2☠ §fGiftbogen"),
    FIRE("§c☀ §fFeuerbogen"),
    EXPLOSION("§e❖ §fExplosionsbogen"),
    NORMAL("§fNormaler Bogen");

    /**
     * Gets the name of the bow that can shoot this type of arrow.
     */
    public String getBowName() {
        return bowName;
    }

    private String bowName;

    /**
     * @param bowName The name of the bow that can shoot this type of arrow.
     */
    ArrowType(String bowName) {
        this.bowName = bowName;
    }
}
