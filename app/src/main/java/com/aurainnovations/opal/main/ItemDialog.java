package com.aurainnovations.opal.main;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.aurainnovations.opal.R;
import com.aurainnovations.opal.database.Inventory;


public class ItemDialog extends DialogFragment
{
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onAddCart(int id, int item_count, int itemPriceSizePos);
    }
    // Use this instance of the interface to deliver action events
    NoticeDialogListener listener;
    private String barcode;
    private Inventory inventory;
    private int itemCount = 0;
    private int itemPriceSizePos;
    public ItemDialog(Inventory inv)
    {
        itemCount += 1;
       inventory = inv;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        final View view = inflater.inflate(R.layout.item_dialog, null);
        int img_r = 0;
        int id = inventory.getId();
        if(inventory == null)
            dismiss();
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
        if(img_r != 0)
            ((ImageView)view.findViewById(R.id.image)).setImageResource(img_r);

        //prices
        String priceString = inventory.getPrices();
        final String[] prices = priceString.split(":");
        String[] sizes = inventory.getSizes().split(":");
        final TextView textViewPrice = view.findViewById(R.id.price);
        textViewPrice.setText("Ksh. "+ prices[0]);
        ((TextView)view.findViewById(R.id.item)).setText(inventory.getName());

        Spinner sizeSpinner = view.findViewById(R.id.size_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),   android.R.layout.simple_spinner_item, sizes);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        sizeSpinner.setAdapter(spinnerArrayAdapter);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                textViewPrice.setText("Ksh. "+ prices[position]);
                itemPriceSizePos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ((TextView)view.findViewById(R.id.item)).setText(inventory.getName());
        ((TextView)view.findViewById(R.id.count)).setText(Integer.toString(itemCount));
        ((TextView)view.findViewById(R.id.description)).setText(inventory.getDescription());
        ((ImageButton)view.findViewById(R.id.plus)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemCount+=1;
                ((TextView)view.findViewById(R.id.count)).setText(Integer.toString(itemCount));
            }
        });
        ((ImageButton)view.findViewById(R.id.minus)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                itemCount-=1;
                if(itemCount < 1)
                    itemCount = 1;
                ((TextView)view.findViewById(R.id.count)).setText(Integer.toString(itemCount));
            }
        });
        ((ImageButton)view.findViewById(R.id.add)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                listener.onAddCart(inventory.getId(), itemCount, itemPriceSizePos);
                dismiss();
            }
        });
        ((ImageButton)view.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
        builder.setView(view);
        /*builder.setMessage(R.string.dialog_fire_missiles)
                .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/
        // Create the AlertDialog object and return it
        return builder.create();
    }
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(this.getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
