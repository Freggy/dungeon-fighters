package de.bergwerklabs.dungeonfighters.commons.animation;

import de.bergwerklabs.dungeonfighters.DungeonFightersPlugin;
import de.bergwerklabs.framework.commons.spigot.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yannic Rieger on 20.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class TitleAnimation {

    private List<Title> frames;
    private int rate;
    private BukkitTask task = null;

    public TitleAnimation(int rate, List<Title> frames) {
        this.rate = rate;
        this.frames = frames;
    }

    public List<Title> getFrames() {
        return Collections.unmodifiableList(frames);
    }

    public int getRate() {
        return rate;
    }

    public void play() {
        Iterator<Title> iterator = frames.iterator();
            this.task = Bukkit.getScheduler().runTaskTimer(DungeonFightersPlugin.getInstance(), () -> {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (iterator.hasNext()) {
                        iterator.next().display(player);
                    }
                    else this.task.cancel();
                });
            }, 0, rate);
    }
}
