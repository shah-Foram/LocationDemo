package com.locationdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.locationdemo.R;
import com.locationdemo.model.LocationModel;

import org.w3c.dom.Text;

import java.util.ArrayList;


/***
 * Adapter to display locations in recyclerview
 */
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context ;
    private ArrayList<LocationModel> locationList;
        public  LocationAdapter(final Context context , final ArrayList<LocationModel> locationList)
        {

            this.context = context ;
            this.locationList = locationList;
        }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_display_location, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        LocationModel model = locationList.get(position);
        myViewHolder.tvLocation.setText(model.getLocation());
        myViewHolder.tvLat.setText(model.getLatitude());
        myViewHolder.tvLng.setText(model.getLongtitute());

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation;
        TextView tvLat;
        TextView tvLng;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLocation = itemView.findViewById(R.id.txtLocation);
            tvLat = itemView.findViewById(R.id.txtlat);
            tvLng = itemView.findViewById(R.id.txtlong);

        }

    }

    }
