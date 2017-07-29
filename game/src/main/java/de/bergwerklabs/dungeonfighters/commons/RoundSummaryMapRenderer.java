package de.bergwerklabs.dungeonfighters.commons;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Yannic Rieger on 06.06.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class RoundSummaryMapRenderer extends MapRenderer {

    private static BufferedImage cachedImage;

    /**
     * @param file Image for the round summary.
     */
    public RoundSummaryMapRenderer(File file) {
        if (cachedImage == null) {
            try {
                cachedImage = ImageIO.read(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        for (int x = 0; x < 128; x++) {
            for (int y = 0; y < 128; y++) {
                int color = cachedImage.getRGB(x, y);
                mapCanvas.setPixel(x, y, MapPalette.matchColor(new Color(color)));
            }
        }
       // mapCanvas.drawImage(0, 0, cachedImage);
    }
}
