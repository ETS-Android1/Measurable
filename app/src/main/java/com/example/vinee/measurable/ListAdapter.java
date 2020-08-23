package com.example.vinee.measurable;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

import static com.example.vinee.measurable.R.id.parent;

/**
 * Created by Vineet Sridhar on 3/21/2018.
 */

public class ListAdapter extends BaseAdapter{

    //Create instance variables
    private Context context;
    private int layout;
    private ArrayList<Result> resultList;
    private LayoutInflater mInflator;
    private View row;
    private int counter = 0;

    //Create constructor
    public ListAdapter(Context context, int layout, ArrayList<Result> resultList) {
        this.context = context;
        this.layout = layout;
        this.resultList = resultList;
        mInflator = LayoutInflater.from(context);
    }

    //Override necessary methods from abstract class: BaseAdapter
    @Override
    public int getCount() {
        return resultList.size();
    }
    @Override
    public Object getItem(int i) {
        return resultList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    //Create adapter for custom listView
    @Override
    public View getView(int i, final View view, ViewGroup viewGroup) {
        row = view;
        //Create and link views
        final TextView name, length, specifier;
        ImageView delete;
        if(row == null){
            row = mInflator.inflate(layout, viewGroup, false);
        }
        name =(TextView) row.findViewById(R.id.nameView);
        length = (TextView) row.findViewById(R.id.measurement);
        delete = (ImageView) row.findViewById(R.id.delete);
        specifier = (TextView) row.findViewById(R.id.specifier);

        //Get result from specific item on list
        final Result result = resultList.get(i);

        //Show distance in multiple ways
        final int ft =(int) result.getMeasure()/12;
        final int in = ((int) result.getMeasure())%12;
        //Round to 2 decimal places
        final DecimalFormat df = new DecimalFormat("##.##");
        name.setText(result.getName());
        length.setText(ft + "\'" + in + "\"");

        //Set identifier
        if(result.isHeight())
            specifier.setText("Height");
        else
            specifier.setText("Distance");

        //If user wants to delete a measurement
        delete.setTag(i);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intialize db
                Paper.init(row.getContext());
                int index =(int) v.getTag();
                //Remove the result from the list
                HomeActivity.resultList.remove(index);
                if(index == 0)
                    HomeActivity.holder.setVisibility(View.VISIBLE);
                //Update the database
                Paper.book().write("measure", HomeActivity.resultList);
                resultList = HomeActivity.resultList;
                //Tell the ListAdapter that there was a change in the data
                notifyDataSetChanged();
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Show data in different ways
                switch(counter){
                    case 0:
                        length.setText(df.format(result.getMeasure() * 0.0254) + "m");
                        counter++;
                        break;
                    case 1:
                        length.setText(df.format(result.getMeasure()) + "in");
                        counter++;
                        break;
                    case 2:
                        length.setText(ft + "\'" + in + "\"");
                        counter = 0;
                        break;
                }

            }
        });
        return row;
    }
}
