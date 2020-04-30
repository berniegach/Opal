package com.aurainnovations.opal.main.cart;

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
public class PeopleContent
{

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PersonItem> ITEMS = new ArrayList<PersonItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PersonItem> ITEM_MAP = new HashMap<String, PersonItem>();

    private static final int COUNT = 3;

    static
    {
        addItem(createDummyItem(1,"Julie Turner"));
        addItem(createDummyItem(2,"Robert Daniel"));
    }

    private static void addItem(PersonItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PersonItem createDummyItem(int position, String name)
    {
        return new PersonItem(String.valueOf(position), name);
    }

    public static class PersonItem
    {
        public final String id;
        public final String content;

        public PersonItem(String id, String content)
        {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString()
        {
            return content;
        }
    }
}
