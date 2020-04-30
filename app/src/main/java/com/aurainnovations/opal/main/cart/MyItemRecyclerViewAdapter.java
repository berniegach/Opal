package com.aurainnovations.opal.main.cart;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aurainnovations.opal.MainActivity;
import com.aurainnovations.opal.R;
import com.aurainnovations.opal.main.cart.CartContent.CartItem;
import com.aurainnovations.opal.main.cart.CartFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link CartItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>
{

    private final List<CartItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private TextView textViewTotal;

    public MyItemRecyclerViewAdapter(List<CartItem> items, OnListFragmentInteractionListener listener, TextView textView)
    {
        mValues = items;
        mListener = listener;
        textViewTotal = textView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position)
    {
        final int count= mValues.get(position).count;
        holder.mItem = mValues.get(position);
        holder.mPriceView.setText("Ksh. "+Integer.toString(mValues.get(position).price.intValue()));
        holder.mItemView.setText(mValues.get(position).name);
        holder.mCountView.setText(Integer.toString(count));
        holder.mSizeView.setText(mValues.get(position).size);

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
        holder.mPlusView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int count2= count + 1;
                holder.mItem.count=count2;
                MainActivity.tempTotal+=holder.mItem.price;
                new CountTask(count2,  Integer.parseInt(mValues.get(position).id), position).execute((Void)null);
            }
        });
        holder.mMinusView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int count2= count - 1;
                holder.mItem.count=count2;
                MainActivity.tempTotal-=holder.mItem.price;
               new CountTask(count2,  Integer.parseInt(mValues.get(position).id), position).execute((Void)null);
            }
        });
        int id = Integer.parseInt(mValues.get(position).id);

        new ImageTask( holder.mImageView, id).execute((Void)null);
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
        public final TextView mPriceView;
        public final TextView mItemView;
        public final TextView mSizeView;
        public final ImageButton mPlusView;
        public final TextView mCountView;
        public final ImageButton mMinusView;
        public CartItem mItem;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.image);
            mPriceView = view.findViewById(R.id.price);
            mItemView = view.findViewById(R.id.item);
            mSizeView = view.findViewById(R.id.size);
            mPlusView = view.findViewById(R.id.plus);
            mCountView = view.findViewById(R.id.count);
            mMinusView = view.findViewById(R.id.minus);
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mItemView.getText() + "'";
        }
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
    private class CountTask extends AsyncTask<Void, Void, Boolean>
    {
        private int count;
        private int id;
        private int position;
        public CountTask(int count, int id, int position)
        {
            this.count=count;
            this.id=id;
            this.position = position;
        }
        @Override
        protected Boolean doInBackground(Void... voids)
        {
            if(count==0)
            {
                mValues.remove(position);
                MainActivity.tempCartLinkedHashMap.remove(id);
            }
            else
            {
                MainActivity.tempCartLinkedHashMap.put(id, count);
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean successful)
        {
            if(count==0)
                notifyDataSetChanged();
            else
            {
                notifyItemChanged(position);
                textViewTotal.setText("Ksh. "+MainActivity.tempTotal.intValue());
            }
            mListener.totalItemsChanged();
        }
    }
}
