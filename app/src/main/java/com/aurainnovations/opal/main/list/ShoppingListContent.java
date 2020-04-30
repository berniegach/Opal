package com.aurainnovations.opal.main.list;

import com.aurainnovations.opal.MainActivity;
import com.aurainnovations.opal.database.ShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ShoppingListContent
{
    public final List<ShoppingListItem> ITEMS = new ArrayList<ShoppingListItem>();
    public final Map<String, ShoppingListItem> ITEM_MAP = new HashMap<String, ShoppingListItem>();


    public ShoppingListContent()
    {
        Iterator iterator= MainActivity.shoppingListLinkedHashMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            LinkedHashMap.Entry<Integer, ShoppingList>set = (LinkedHashMap.Entry<Integer, ShoppingList>) iterator.next();
            int id=set.getKey();
            ShoppingList list = set.getValue();
            String date = list.getDate();
            String items = list.getItems();
            String title = list.getTitle();
            String people = list.getPeople();
            addItem(createDummyItem(id, date, items, title, people));
        }
    }
    private void addItem(ShoppingListItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private ShoppingListItem createDummyItem(int id, String date, String items, String title, String people)
    {
        return new ShoppingListItem(String.valueOf(id), date, items, title, people);
    }


    public class ShoppingListItem
    {
        public final String id;
        public final String date;
        public final String items;
        public final String title;
        public final String people;

        public ShoppingListItem(String id, String date, String items, String title, String people)
        {
            this.id = id;
            this.date = date;
            this.items = items;
            this.title = title;
            this.people = people;
        }

        @Override
        public String toString()
        {
            return title;
        }
    }
}
