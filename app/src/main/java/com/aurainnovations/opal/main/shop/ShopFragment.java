package com.aurainnovations.opal.main.shop;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.aurainnovations.opal.R;
import com.aurainnovations.opal.main.shop.InventoryContent.InventoryItem;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ShopFragment extends Fragment
{

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShopFragment()
    {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ShopFragment newInstance(int columnCount)
    {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);

        RecyclerView recyclerViewCategories = view.findViewById(R.id.list_categories);
        recyclerViewCategories.setAdapter(new MyShopCategoriesRecyclerViewAdapter(CategoriesContent.ITEMS, mListener));

        recyclerView = view.findViewById(R.id.list);
        Context context = view.getContext();
        mColumnCount = getHorizontalItemCount();
        if (mColumnCount <= 1)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        InventoryContent content = new InventoryContent();
        recyclerView.setAdapter(new MyShopRecyclerViewAdapter(content.ITEMS, mListener));
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener)
        {
            mListener = (OnListFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.shop_fragment_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =   (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =  (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                MyShopRecyclerViewAdapter adapter=( MyShopRecyclerViewAdapter) recyclerView.getAdapter();
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                MyShopRecyclerViewAdapter adapter=( MyShopRecyclerViewAdapter) recyclerView.getAdapter();
                adapter.filter(newText);
                return true;
            }
        });

    }

    public interface OnListFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onShopItemInteraction(InventoryItem item);
        void onCategoryItemInteraction(CategoriesContent.CategoryItem item);
    }
    private int getHorizontalItemCount()
    {
        int screenSize = getContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return 4;

            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return 3;

            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return 2;

            case Configuration.SCREENLAYOUT_SIZE_SMALL:
            default:
                return 1;
        }
    }
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            int pos = parent.getChildLayoutPosition(view);
            int items = getHorizontalItemCount();

            // Add top margin only for the first item to avoid double space between items
            if (pos < items) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }
}
