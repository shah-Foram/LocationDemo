package com.locationdemo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.locationdemo.AndroidApp;
import com.locationdemo.R;
import com.locationdemo.adapter.LocationAdapter;
import com.locationdemo.model.LocationModel;
import com.locationdemo.service.BackgroundLocationUpdateService;
import com.locationdemo.util.Preference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.

    private LocationAdapter adapter;
    private ArrayList<LocationModel> locationList;
    private RecyclerView rvLocation;
    private ImageView ivLogout;
    private TextView tvEmptyView;
    private SwipeRefreshLayout swipeRefreshLayout;

    String[] permissions= new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_main;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initializeComponents() {

        rvLocation = findViewById(R.id.rvLocation);
        ivLogout = findViewById(R.id.header_iv_btnLogout);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        tvEmptyView = findViewById(R.id.empty_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        ivLogout.setOnClickListener(this);
        locationList = new ArrayList<>();
        adapter=new LocationAdapter(this,locationList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // Add this to your Recycler view
        rvLocation.setLayoutManager(layoutManager);
        rvLocation.setAdapter(adapter);

        if(checkPermissions())
        {
            this.startService(new Intent(this, BackgroundLocationUpdateService.class));
            displayLocation();
        }
        else {
            requestPermissions(
                    permissions,
                    MULTIPLE_PERMISSIONS);
        }
    }

    private void displayLocation() {
        locationList.clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(true);
                Query mQueryRef = AndroidApp.getInstance().getmDatabase().child("users").child(Preference.getInstance().getUserId()).child("location");
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        locationList.clear();
                        adapter.notifyDataSetChanged();
                        if(dataSnapshot.getChildrenCount()==0)
                        {
                            tvEmptyView.setVisibility(View.VISIBLE);
                        }
                        else {
                            tvEmptyView.setVisibility(View.GONE);
                            for(DataSnapshot dsp : dataSnapshot.getChildren()){
                                LocationModel locationModel =  new LocationModel(dsp.child("latitude").getValue().toString(),dsp.child("longtitute").getValue().toString(),dsp.child("timestamp").getValue().toString());
                                locationModel.setLocation(getAddressFromLatLong(dsp.child("latitude").getValue().toString(),dsp.child("longtitute").getValue().toString()));
                                locationList.add(locationModel);
                            }
                            Collections.reverse(locationList);
                        }

                       swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                     swipeRefreshLayout.setRefreshing(false);

                    }
                };
                mQueryRef.addValueEventListener(postListener);

    }

    /***
     * Get Address from latitude and longitude
     * @param latitude
     * @param longitude
     * @return
     */
    private String getAddressFromLatLong(String latitude, String longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            if (addresses.size() != 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                return address;
            }

            } catch(IOException e){
                e.printStackTrace();
            }

        return "";
    }

    /***
     * Check runtime permission is granted or not
     * @return
     */
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;
                            Snackbar.make(this.findViewById(android.R.id.content),
                                    "Please Grant Permissions to get the location",
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.M)
                                        @Override
                                        public void onClick(View v) {
                                            requestPermissions(
                                                  permissions,
                                                    MULTIPLE_PERMISSIONS);
                                        }
                                    }).show();
                        }
                        else {
                            this.startService(new Intent(this.getBaseContext(), BackgroundLocationUpdateService.class));
                        }
                    }
                }
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayLocation();
    }

    @Override
    public void onRefresh() {
        displayLocation();
    }
}
