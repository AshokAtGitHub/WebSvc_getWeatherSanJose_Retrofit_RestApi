package com.ashok.weather_local_n_us;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ashok on 5/29/2016.
 */
public class AdapterListView_Fcast extends BaseAdapter {
    private ArrayList<ForecastItems> mArrayListFcastItems;
    private static LayoutInflater mLayoutInflater = null;
    Context mContextOfCaller;

    public static final String TAG = "ak";
    //------------------------------------------
    //constructor
    public AdapterListView_Fcast(Context contextOfCaller,
                                 ArrayList<ForecastItems>arrayListOfCaller){
        mArrayListFcastItems = arrayListOfCaller;
        mContextOfCaller = contextOfCaller;
        mLayoutInflater = LayoutInflater.from(mContextOfCaller);
    }
    //-----------------------
    @Override//auto-generated stub
    public int getCount() {
        //return 0;
        return mArrayListFcastItems.size();
    }
    //---------------------------------
    @Override//auto-generated stub
    public Object getItem(int position) {
        //return null;
        return mArrayListFcastItems.get(position);
    }
    //-------------------------------------
    @Override//auto-generated stub
    public long getItemId(int position) {
        //return 0;
        return position;
    }
    //***************************************************
    //***************************************************
    static class ViewHolderForEachRow{
        TextView day;
        TextView hi;
        TextView lo;
        TextView status;//Cloudy, Partly Cloudy etc
        TextView monthDay;//OCT 10 etc.
    }
    //----------------------------------------
    @Override//auto-generated stub
    public View getView(int position, View convertView, ViewGroup parent) {
        View newViewThisRow = convertView;
        ViewHolderForEachRow viewHolderEachRow;
        //Log.i(TAG, "Adapter:getView():position="+position);

        if (convertView == null){
            //Log.i(TAG,"Adapter:getView():ConvertView = NULL!!");
            viewHolderEachRow = new ViewHolderForEachRow();
            newViewThisRow = mLayoutInflater.inflate(R.layout.each_row_list_view,parent,false);

            viewHolderEachRow.day = (TextView) newViewThisRow.findViewById(R.id.day);
            viewHolderEachRow.hi = (TextView) newViewThisRow.findViewById(R.id.hiTemp);
            viewHolderEachRow.lo = (TextView) newViewThisRow.findViewById(R.id.loTemp);
            viewHolderEachRow.status = (TextView) newViewThisRow.findViewById(R.id.status);
            viewHolderEachRow.monthDay = (TextView) newViewThisRow.findViewById(R.id.monthDay);

            newViewThisRow.setTag(viewHolderEachRow);
        }else{
            //Log.i(TAG,"adapter:getView():convertView = NOT-null");
            viewHolderEachRow = (ViewHolderForEachRow)newViewThisRow.getTag();
        }
        ForecastItems currentRowOfFcastItems = mArrayListFcastItems.get(position);

        viewHolderEachRow.day.setText(currentRowOfFcastItems.getDay());
        viewHolderEachRow.hi.setText(currentRowOfFcastItems.getHiTemp());
        viewHolderEachRow.lo.setText(currentRowOfFcastItems.getLoTemp());
        viewHolderEachRow.status.setText(currentRowOfFcastItems.getStatus());
        viewHolderEachRow.monthDay.setText(currentRowOfFcastItems.getMonthDay());

        return newViewThisRow;
    }
    //---------------------------------------------------------------------
}
