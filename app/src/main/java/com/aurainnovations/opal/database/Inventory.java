package com.aurainnovations.opal.database;

public class Inventory
{
    private int id;
    private String name;
    private String code;
    private String prices;
    private String sizes;
    private String description;

    public Inventory(int id, String name, String code, String prices, String sizes, String description)
    {
        this.id = id;
        this.name = name;
        this.code = code;
        this.prices = prices;
        this.sizes = sizes;
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getPrices()
    {
        return prices;
    }

    public void setPrices(String prices)
    {
        this.prices = prices;
    }

    public String getSizes()
    {
        return sizes;
    }

    public void setSizes(String sizes)
    {
        this.sizes = sizes;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
