package com.aurainnovations.opal;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.aurainnovations.opal.database.Inventory;
import com.aurainnovations.opal.database.ShoppingList;
import com.aurainnovations.opal.main.ItemDialog;
import com.aurainnovations.opal.main.cart.CartContent;
import com.aurainnovations.opal.main.cart.CartFragment;
import com.aurainnovations.opal.main.cart.PeopleContent;
import com.aurainnovations.opal.main.list.ShoppingListContent;
import com.aurainnovations.opal.main.list.ShoppingListFragment;
import com.aurainnovations.opal.main.shop.CategoriesContent;
import com.aurainnovations.opal.main.shop.InventoryContent;
import com.aurainnovations.opal.main.shop.ShopFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Iterator;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity implements
        ShopFragment.OnListFragmentInteractionListener, ItemDialog.NoticeDialogListener,
        ShoppingListFragment.OnListFragmentInteractionListener, CartFragment.OnListFragmentInteractionListener
{
    public static LinkedHashMap<Integer, Inventory> inventoryLinkedHashMap;
    public static LinkedHashMap<Integer, ShoppingList> shoppingListLinkedHashMap;
    public static LinkedHashMap<Integer,Integer> cartLinkedHashMap;
    public static LinkedHashMap<Integer,Integer> tempCartLinkedHashMap;
    public static LinkedHashMap<Integer,Integer> itemPriceSizeLinkedHashMap;
    public static Double tempTotal =0.0;
    private  ExtendedFloatingActionButton fab;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar materialToolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingToolbar);
        final AppBarLayout appBarLayout = findViewById(R.id.appbarlayout);
        //appBarLayout.setExpanded(false);
        setSupportActionBar(materialToolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                 R.id.navigation_shop, R.id.navigation_list, R.id.navigation_cart, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
        {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
            {
               // if(destination.getId() != R.id.navigation_home)
                    //appBarLayout.setExpanded(false, true);
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tempCartLinkedHashMap=cartLinkedHashMap;
                getTotal();
                navController.navigate(R.id.navigation_cart);
            }
        });
        start_background_tasks();
        cartLinkedHashMap = new LinkedHashMap<>();
        tempCartLinkedHashMap = new LinkedHashMap<>();
        itemPriceSizeLinkedHashMap = new LinkedHashMap<>();
    }
    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Quit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finishAffinity();
                        //Intent intent=new Intent(Intent.ACTION_MAIN);
                        // intent.addCategory(Intent.CATEGORY_HOME);
                        // startActivity(intent);
                    }
                }).create().show();
    }

    @Override
    public void onShopItemInteraction(InventoryContent.InventoryItem item)
    {
        Inventory inventory = inventoryLinkedHashMap.get(Integer.parseInt(item.id));
        DialogFragment dialog = new ItemDialog(inventory);
        dialog.show(getSupportFragmentManager(), "ItemDialogFragment");
    }
    /**
     * implementation of ShopFragment.java
     * */
    @Override
    public void onCategoryItemInteraction(CategoriesContent.CategoryItem item)
    {

    }
    private void start_background_tasks()
    {
        new InventoryTask().execute((Void)null);
        new ShoppingListTask().execute((Void)null);
    }
    /**
     * implementation of ItemDialog.java
     * */
    @Override
    public void onAddCart(int id, int item_count, int itemPriceSizePos)
    {
        cartLinkedHashMap.put(id,item_count);
        itemPriceSizeLinkedHashMap.put(id, itemPriceSizePos);
        if(getCartCount()>0)
            fab.setVisibility(View.VISIBLE);
        fab.setText(Integer.toString(getCartCount()));
    }
    /**
     * implementation of ShoppingListFragment.java
     * */
    @Override
    public void onListFragmentInteraction(ShoppingListContent.ShoppingListItem item)
    {

    }
    private int getCartCount()
    {
        int total_count=0;
        Iterator iterator = cartLinkedHashMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            LinkedHashMap.Entry<Integer, Integer>set = (LinkedHashMap.Entry<Integer, Integer>) iterator.next();
            int count = set.getValue();
            total_count += count;
        }
        return total_count;
    }
    private void getTotal()
    {
        Iterator iterator= tempCartLinkedHashMap.entrySet().iterator();
        Double total=0.0;
        while(iterator.hasNext())
        {
            LinkedHashMap.Entry<Integer, Integer>set = (LinkedHashMap.Entry<Integer, Integer>) iterator.next();
            int id=set.getKey();
            int count=set.getValue();
            Inventory inv = inventoryLinkedHashMap.get(id);

            int pos = itemPriceSizeLinkedHashMap.get(id);
            String priceString = inv.getPrices();
            final String[] prices = priceString.split(":");
            Double price = Double.parseDouble( prices[pos]);

            total += count*price;

        }
        tempTotal = total;
    }
    /*************************************************************************************************************************************************************************************
     * implementation of CartFragment.java
     * ************************************************************************************************************************************************************************************/
    @Override
    public void onListFragmentInteraction(CartContent.CartItem item)
    {

    }

    @Override
    public void onListFragmentInteraction2(PeopleContent.PersonItem item)
    {

    }

    @Override
    public void onProceed()
    {
        cartLinkedHashMap.clear();;
        tempCartLinkedHashMap.clear();
        tempTotal = 0.0;
        fab.setText(Integer.toString(getCartCount()));
        fab.setVisibility(View.GONE);
        navController.navigate(R.id.navigation_shop);
    }

    @Override
    public void totalItemsChanged()
    {
        fab.setText(Integer.toString(getCartCount()));
        if(getCartCount()>0)
            fab.setVisibility(View.VISIBLE);
        else
            fab.setVisibility(View.GONE);
    }
    /*******************************************************************************************************************************************************************************************/

    private class InventoryTask extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected void onPreExecute()
        {
            inventoryLinkedHashMap=new LinkedHashMap<>();
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            Inventory blue_band=new Inventory(1,"Blue band","6164004676211","330.00","1 kg",
                    "Blue band margarines have been a daily source of essential nutrients for families for decades. It is made from high quality vegatable oils" +
                            "and hence and important source of essential fats. A thin layer of spread on bread everyday makes a big contribution to the healthy development and growth of the " +
                            "whole family");
            Inventory vinegar=new Inventory(2,"Vinegar","6162000023213","89.0","700 ml",
                    "Dress up your salads with zesta substitute white vinegar");
            Inventory black_pepper=new Inventory(3,"Black pepper","6161100911208","130.0","100 g",
                    "Use in meat dishes, soups, sauces, cheese dishes, salads dressings and as a light seasoning on fresh fruits");
            Inventory chocolate = new Inventory(4, "Drinking chocolate","7622210304551","433.0","450 g",
                    "This cadbury jar packs the real cadbury chocolate flavour. It comes in a beautifully equipped jar ensuring its freshness and longevity." +
                            " Enjoy as a hot chocolate drink or get creative.");
            Inventory mustard = new Inventory(5, "Mustard", "6221033173217","178.0","245 g",
                    "Using 100% natural ingredients, including stone-ground mustard seeds and secret blend of spices and vinegar. Heinz yellow mustard" +
                            " delivers a perfect balance of flavour and tang.");
            Inventory nescafe = new Inventory(6, "Nescafe","7891000260968","758.0","200 g",
                    "The unmistakable taste and aroma that we all know and love is still as good as ever. Our signature coffee has  medium dark roast that gives it a full flavour" +
                            " and wonderfully invigorating taste. Start your day right with the first sips of this classic.");
            Inventory royco = new Inventory(7, "Royco","6161100605442","123.0","200 g",
                    "We believe the quality of ingredients makes a real difference to your mchuzi mix, which is why our chefs has chosen quality ingredients, herbs" +
                            " and spices for royca mchuzi mix beef.");
            Inventory salt = new Inventory(8, "Salt","6161101280044","57.0","2 kg",
                    "Kensalt table salt is fortified with Potassium Iodide as recommended by the health authorities.");
            inventoryLinkedHashMap.put(1,blue_band);
            inventoryLinkedHashMap.put(2,vinegar);
            inventoryLinkedHashMap.put(3,black_pepper);
            inventoryLinkedHashMap.put(4,chocolate);
            inventoryLinkedHashMap.put(5,mustard);
            inventoryLinkedHashMap.put(6,nescafe);
            inventoryLinkedHashMap.put(7,royco);
            inventoryLinkedHashMap.put(8,salt);
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean successful) {

        }
    }
    private class ShoppingListTask extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected void onPreExecute()
        {
            shoppingListLinkedHashMap = new LinkedHashMap<>();
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
           ShoppingList list1 = new ShoppingList(1,"25/04/2020","1;1,2;1,3;2","Weekend List","1;Robert Daniel,2;Julie Turner");
            ShoppingList list2 = new ShoppingList(2,"26/04/2020","5;1,2;1,4;2","Birthday Party","3;James Watt");
            ShoppingList list3 = new ShoppingList(3,"27/04/2020","2;1,1;1,3;2","Monthly Grocery","4;Natalie");

            shoppingListLinkedHashMap.put(1,list1);
            shoppingListLinkedHashMap.put(2,list2);
            shoppingListLinkedHashMap.put(3,list3);
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean successful) {

        }
    }
}
