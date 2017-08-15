package de.bergwerklabs.dungeonfighters.game.core.stats;

public enum Statistic {
    BATTLE_ZONE_KILLS(""),
    FAILS(""),
    COMPLETED_MODULES(""),
    DEATHMATCH_KILLS(""),
    TOTAL_GOLD_EARNED(""),
    GAMES_WON(""),
    GAMES_PLAYED(""),
    TOTAL_POINTS(""),
    GAMES_LOST("");

    private String dataKey;

    /**
     * @param dataKey
     */
    Statistic(String dataKey) {
        this.dataKey = dataKey;
    }

    /**
     *
     */
    public String getDataKey() {
        return dataKey;
    }
}
