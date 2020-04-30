package com.aurainnovations.opal.main.cart;

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
public class CartContent
{
    public final List<CartItem> ITEMS = new ArrayList<CartItem>();
    public final Map<String, CartItem> ITEM_MAP = new HashMap<String, CartItem>();



    public CartContent()
    {
        Iterator iterator= MainActivity.tempCartLinkedHashMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            LinkedHashMap.Entry<Integer, Integer>set = (LinkedHashMap.Entry<Integer, Integer>) iterator.next();
            int id=set.getKey();
            int count=set.getValue();
            Inventory inv = MainActivity.inventoryLinkedHashMap.get(id);
            String name = inv.getName();
            String code = inv.getCode();

            int pos = MainActivity.itemPriceSizeLinkedHashMap.get(id);
            String priceString = inv.getPrices();
            final String[] prices = priceString.split(":");
            String[] sizes = inv.getSizes().split(":");
            Double price = Double.parseDouble( prices[pos]);
            String size = sizes[pos];
            String description = inv.getDescription();

            addItem(createItem(id,name,code,price,size,description, count));
        }
    }
    private void addItem(CartItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private CartItem createItem(int id, String name, String code, Double price, String size, String description, int count)
    {
        return new CartItem(id, name, code, price, size, description, count);
    }

    public  class CartItem
    {
        public final String id;
        public final String name;
        public final String code;
        public final Double price;
        public final String size;
        public final String description;
        public int count;

        public CartItem(int id, String name, String code, Double price, String size, String description, int count)
        {
            this.id = String.valueOf(id);
            this.name = name;
            this.code = code;
            this.price = price;
            this.size = size;
            this.description = description;
            this.count = count;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }
}
