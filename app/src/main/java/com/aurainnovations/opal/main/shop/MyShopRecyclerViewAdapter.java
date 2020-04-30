package com.aurainnovations.opal.main.shop;

import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurainnovations.opal.R;
import com.aurainnovations.opal.main.shop.ShopFragment.OnListFragmentInteractionListener;
import com.aurainnovations.opal.main.shop.InventoryContent.InventoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link InventoryItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyShopRecyclerViewAdapter extends RecyclerView.Adapter<MyShopRecyclerViewAdapter.ViewHolder>
{

    private final List<InventoryItem> mValues;
    private List<InventoryItem>itemsCopy;
    private final OnListFragmentInteractionListener mListener;

    public MyShopRecyclerViewAdapter(List<InventoryItem> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
        itemsCopy=new ArrayList<>();
        itemsCopy.addAll(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mItemView.setText(mValues.get(position).name);

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onShopItemInteraction(holder.mItem);
                }
            }
        });
        new ImageTask( holder.mImageView, Integer.parseInt(mValues.get(position).id)).execute((Void)null);
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mItemView;
        public final ImageButton mImageButton;
        public InventoryItem mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.image);
            mItemView =  view.findViewById(R.id.item);
            mImageButton = view.findViewById(R.id.favorite);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mItemView.getText() + "'";
        }
    }
    public void filter(String text)
    {
        mValues.clear();
        if(text.isEmpty())
            mValues.addAll(itemsCopy);
        else
        {
            text=text.toLowerCase();
            for(InventoryItem item:itemsCopy)
            {
                if(item.name.toLowerCase().contains(text))
                    mValues.add(item);
            }
        }
        notifyDataSetChanged();
    }
    private class ImageTask extends AsyncTask<Void, Void, Boolean>
    {
        ImageView imageView;
        int img_r = 0;
        int id;
        public ImageTask(ImageView imageView, int id)
        {
            this.imageView = imageView;
            this.id = id;
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            if(id==1)
                img_r=R.drawable.a1;
            else if(id==2)
                img_r=R.drawable.a2;
            else if(id == 3)
                img_r=R.drawable.a3;
            else if(id == 4)
                img_r=R.drawable.chocolate;
            else if(id == 5)
                img_r=R.drawable.mustard;
            else if(id == 6)
                img_r=R.drawable.nescafe;
            else if(id == 7)
                img_r=R.drawable.royco;
            else if(id == 8)
                img_r=R.drawable.salt;
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean successful)
        {
            if(img_r != 0)
                imageView.setImageResource(img_r);
        }
    }
}
