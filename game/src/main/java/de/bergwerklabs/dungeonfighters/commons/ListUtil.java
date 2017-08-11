package de.bergwerklabs.dungeonfighters.commons;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yannic Rieger on 30.07.2017.
 * <p>  </p>
 *
 * @author Yannic Rieger
 */
public class ListUtil {

    /**
     * Chooses items randomly from a {@link List}. The returned {@link List} will not have duplicate values,
     * because the entries will be removed from the passed {@link List}.
     * <p>
     * <b>NOTE:</b> There will be only no duplicate values, if the passed list does not contain duplicates.
     *
     * @param list {@link List} which contains values that will be picked.
     * @param maxItems Maximum count of times in the returned {@link List}.
     * @param <T> Type of value in this {@link List}.
     * @return a new {@link List} with no duplicates.
     */
    public static <T> List<T> createRandomItemList(List<T> list, int maxItems) {
        SecureRandom random = new SecureRandom();
        List<T> chosen = new ArrayList<>();

        for (int i = 0; i < maxItems - 1; i++) { // TODO: make game size configurable
            if (list.size() == 0) break; // Only for test purposes
            int index = random.nextInt(list.size());
            T game = list.get(index);
            chosen.add(game);
            list.remove(index);
        }
        return chosen;
    }

}
