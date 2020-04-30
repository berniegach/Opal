package com.aurainnovations.opal.main.shop;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aurainnovations.opal.R;
import com.aurainnovations.opal.main.shop.CategoriesContent.CategoryItem;
import com.aurainnovations.opal.main.shop.ShopFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyShopCategoriesRecyclerViewAdapter extends RecyclerView.Adapter<MyShopCategoriesRecyclerViewAdapter.ViewHolder>
{

    private final List<CategoryItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyShopCategoriesRecyclerViewAdapter(List<CategoryItem> items, OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_shop_categories, parent, false);
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
                    mListener.onCategoryItemInteraction(holder.mItem);
                }
            }
        });
        new ImageTask( holder.mImageView, position).execute((Void)null);
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
        public final TextView mContentView;
        public CategoryItem mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.image);
            mContentView = view.findViewById(R.id.title);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
    private class ImageTask extends AsyncTask<Void, Void, Boolean>
    {
        ImageView imageView;
        int img_r = 0;
        int position;
        public ImageTask(ImageView imageView, int id)
        {
            this.imageView = imageView;
            this.position = id;
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... params)
        {
            if(position ==0)
                img_r=R.drawable.ic_pastry;
            else if(position ==1)
                img_r=R.drawable.ic_cosmetics;
            else if(position == 2)
                img_r=R.drawable.ic_groceries;
            else if(position == 3)
                img_r=R.drawable.ic_furniture;
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
