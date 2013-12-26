package org.techniche.technothlon.katana.technopedia;

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
public class TechnopediaContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<TechnopediaItem> ITEMS = new ArrayList<TechnopediaItem>();


    public static Map<String, TechnopediaItem> ITEM_MAP = new HashMap<String, TechnopediaItem>();

    static {
        // Add 3 sample items.
        addItem(new TechnopediaItem("1", "Item 1"));
        addItem(new TechnopediaItem("2", "Item 2"));
        addItem(new TechnopediaItem("3", "Item 3"));
    }

    private static void addItem(TechnopediaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TechnopediaItem {
        public String id;
        public String content;

        public TechnopediaItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
