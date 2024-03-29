package com.aurainnovations.opal.main.cart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurainnovations.opal.MainActivity;
import com.aurainnovations.opal.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CartFragment extends Fragment
{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CartFragment()
    {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CartFragment newInstance(int columnCount)
    {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        Context context=getContext();

        ImageButton addButton = view.findViewById(R.id.add_person);

        //people
        RecyclerView recyclerViewPerson = view.findViewById(R.id.person_list);
        recyclerViewPerson.setAdapter(new MyPersonRecyclerViewAdapter(PeopleContent.ITEMS, mListener));

        RecyclerView recyclerView=view.findViewById(R.id.list);
        TextView textViewTotal = view.findViewById(R.id.total);
        Button proceed = view.findViewById(R.id.proceed);
        textViewTotal.setText("Ksh. "+ MainActivity.tempTotal.intValue());
        if (mColumnCount <= 1)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else
        {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        CartContent content=new CartContent();
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(content.ITEMS, mListener, textViewTotal));
        proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MainActivity.tempTotal<=0.0)
                    Toast.makeText(getContext(),"Cart empty",Toast.LENGTH_SHORT).show();
                else
                    mListener.onProceed();
            }
        });
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener
    {
        void onListFragmentInteraction(CartContent.CartItem item);
        void onListFragmentInteraction2(PeopleContent.PersonItem item);
        void onProceed();
        void totalItemsChanged();
    }
}
