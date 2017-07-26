package de.bergwerklabs.dungeonfighters.game.core.specialitem.arrow;

/**
 * Created by Yannic Rieger on 23.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public enum ArrowType {
    POISON("§2☠ §fGiftbogen"),
    FIRE("§c☀ §fFeuerbogen"),
    EXPLOSION("§e❖ §fExplosionsbogen"),
    NORMAL("§fNormaler Bogen");

    public String getBowName() {
        return bowName;
    }

    private String bowName;

    ArrowType(String bowName) {;
        this.bowName = bowName;
    }
}
