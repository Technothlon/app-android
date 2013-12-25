package org.techniche.technothlon.katana.tcd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TCDContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<TCDQuestion> ITEMS = new ArrayList<TCDQuestion>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, TCDQuestion> ITEM_MAP = new HashMap<String, TCDQuestion>();

    static {
        // Add 3 sample items.
        addItem(new TCDQuestion("1", "Item 1"));
        addItem(new TCDQuestion("2", "Item 2"));
        addItem(new TCDQuestion("3", "Item 3"));
    }

    private static void addItem(TCDQuestion item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TCDQuestion {
        public String id;
        public String content;

        public TCDQuestion(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
