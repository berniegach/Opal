package com.aurainnovations.opal.database;

public class ShoppingList
{
    private int id;
    private String date;
    private String items;
    private String title;
    private String people;

    public ShoppingList(int id, String date, String items, String title, String people)
    {
        this.id = id;
        this.date = date;
        this.items = items;
        this.title = title;
        this.people = people;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getItems()
    {
        return items;
    }

    public void setItems(String items)
    {
        this.items = items;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPeople()
    {
        return people;
    }

    public void setPeople(String people)
    {
        this.people = people;
    }


}
