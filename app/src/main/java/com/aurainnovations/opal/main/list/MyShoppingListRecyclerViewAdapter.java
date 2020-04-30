package com.aurainnovations.opal.main.list;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aurainnovations.opal.R;
import com.aurainnovations.opal.main.list.ShoppingListFragment.OnListFragmentInteractionListener;
import com.aurainnovations.opal.main.list.ShoppingListContent.ShoppingListItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ShoppingListItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyShoppingListRecyclerViewAdapter extends RecyclerView.Adapter<MyShoppingListRecyclerViewAdapter.ViewHolder>
{

    private final List<ShoppingListItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyShoppingListRecyclerViewAdapter(List<ShoppingListItem> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        String[] date = mValues.get(position).date.split("/");
        holder.mDayView.setText(date[0]);
        holder.mMonthView.setText(date[1]);
        holder.mYearView.setText(date[2]);
        holder.mTitleView.setText(mValues.get(position).title);

        String[] items = mValues.get(position).items.split(",");
        holder.mItemsView.setText(String.valueOf(items.length) +" items");

        String[] people = mValues.get(position).people.split(",");
        String persons ="";
        for(int c=0; c<people.length; c++)
        {
            String[] onePerson = people[c].split(";");
            if(!persons.contentEquals(""))
                persons+=", "+onePerson[1];
            else
                persons+=onePerson[1];
        }
        holder.mPeopleView.setText(persons);
        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView mDayView;
        public final TextView mMonthView;
        public final TextView mYearView;
        public final ImageButton mShareView;
        public final ImageButton mOptionsView;
        public final TextView mItemsView;
        public final TextView mTitleView;
        public final TextView mPeopleView;
        public ShoppingListItem mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mDayView = (TextView) view.findViewById(R.id.day);
            mMonthView = (TextView) view.findViewById(R.id.month);
            mYearView = (TextView) view.findViewById(R.id.year);
            mShareView = view.findViewById(R.id.share);
            mOptionsView = view.findViewById(R.id.options);
            mItemsView = view.findViewById(R.id.items_count);
            mPeopleView = view.findViewById(R.id.people);
            mTitleView = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
