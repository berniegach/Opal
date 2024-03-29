package com.aurainnovations.opal.main.cart;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aurainnovations.opal.R;
import com.aurainnovations.opal.main.cart.CartFragment.OnListFragmentInteractionListener;
import com.aurainnovations.opal.main.cart.PeopleContent.PersonItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PersonItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPersonRecyclerViewAdapter extends RecyclerView.Adapter<MyPersonRecyclerViewAdapter.ViewHolder>
{

    private final List<PersonItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyPersonRecyclerViewAdapter(List<PersonItem> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction2(holder.mItem);
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
        public final TextView mContentView;
        public PersonItem mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.person);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
