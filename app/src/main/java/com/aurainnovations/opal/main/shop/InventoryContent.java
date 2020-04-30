package com.aurainnovations.opal.main.shop;

import com.aurainnovations.opal.MainActivity;
import com.aurainnovations.opal.database.Inventory;

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
public class InventoryContent
{

    public final List<InventoryItem> ITEMS = new ArrayList<InventoryItem>();
    public final Map<String, InventoryItem> ITEM_MAP = new HashMap<String, InventoryItem>();

    public InventoryContent()
    {
        Iterator iterator= MainActivity.inventoryLinkedHashMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            LinkedHashMap.Entry<Integer, Inventory>set = (LinkedHashMap.Entry<Integer, Inventory>) iterator.next();
            int id=set.getKey();
            Inventory inv = set.getValue();
            String name = inv.getName();
            String code = inv.getCode();
            String price = inv.getPrices();
            String size = inv.getSizes();
            String description = inv.getDescription();

            addItem(createDummyItem(id, name, code, price,  size, description));
        }
    }


    private void addItem(InventoryItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private InventoryItem createDummyItem(int id, String name, String code, String prices, String sizes, String description)
    {
        return new InventoryItem(String.valueOf(id), name, code, prices, sizes, description);
    }

    public class InventoryItem
    {
        public final String id;
        public final String name;
        public final String code;
        public final String prices;
        public final String sizes;
        public final String description;

        public InventoryItem(String id, String name, String code, String prices, String sizes, String description)
        {
            this.id = id;
            this.name = name;
            this.code = code;
            this.prices = prices;
            this.sizes = sizes;
            this.description = description;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }
}
