package com.aurainnovations.opal.main.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesContent
{
    public static final List<CategoryItem> ITEMS = new ArrayList<CategoryItem>();
    public static final Map<String, CategoryItem> ITEM_MAP = new HashMap<String, CategoryItem>();

    private static final int COUNT = 4;

    static
    {
        for (int i = 0; i < COUNT; i++)
        {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(CategoryItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static CategoryItem createDummyItem(int position)
    {
        String[] items = new String[]{"Pastries","Cosmetics","Groceries","Furniture"};
        return new CategoryItem(String.valueOf(position), items[position]);
    }

    public static class CategoryItem
    {
        public final String id;
        public final String content;

        public CategoryItem(String id, String content)
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
