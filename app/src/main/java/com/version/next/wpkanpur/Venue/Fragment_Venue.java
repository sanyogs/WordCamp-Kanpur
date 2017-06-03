/**
 * WordCamp Kanpur
 *
 * @package    WordCamp Kanpur
 * @author     Sanyog Shelar <sanyog@hotmail.com>
 * @copyright  Copyright (c) WHMCS Limited 2005-2013
 * @license    WordCamp is released under the GPL
 * @version    1.3
 * @link       https://2017.kanpur.wordcamp.org
 */

package com.version.next.wpkanpur.Venue;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.version.next.wpkanpur.GPSTracker;
import com.version.next.wpkanpur.R;

import java.util.Calendar;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Chintal-Pragma on 12/22/2016.
 */

public class Fragment_Venue extends Fragment implements OnMapReadyCallback
{

    private GoogleMap mMap;
    MapView mMapView;
    String latitude="",longitude="";
    Double current_latitude,current_longitude;
    ImageView iv_venue_share,iv_venue_navigate;
    TextView tv_openingmap;
    GPSTracker gps;

    TextView tv_days_togo;
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         rootview = inflater.inflate(R.layout.venue,container,false);

        mMapView = (MapView)rootview. findViewById(R.id.mapView);
        iv_venue_share = (ImageView)rootview. findViewById(R.id.iv_venue_share);
        iv_venue_navigate = (ImageView)rootview. findViewById(R.id.iv_venue_navigate);
        tv_openingmap = (TextView)rootview.findViewById(R.id.tv_openingmap);

        set_timer();

        if (!weHavePermissionToReadExternalStorage())
        {
            requestwritReadexternalstoragePermissionFirst();
        }



        gps = new GPSTracker(getActivity());

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        get_current_location();

        iv_venue_share.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                share_location();
            }
        });

        iv_venue_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigate_location();
            }
        });


        tv_openingmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!weHavePermissionToReadExternalStorage())
                {
                    requestwritReadexternalstoragePermissionFirst();
                }
                else
                {
                    navigate_location();
                }


            }
        });


        return rootview;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;
        mMap.clear();

        //LatLng latlng =  new LatLng(19.049893d, 72.8297693d);
        LatLng latlng =  new LatLng(Double.parseDouble( getString(R.string.latitude)), Double.parseDouble(getString(R.string.longitude)));
        if (weHavePermissionToReadExternalStorage())
        {
            try
            {
                    if (latitude.equals(""))
                    {
                        latitude = String.valueOf(latlng.latitude);
                    }

                    if (longitude.equals(""))
                    {
                        longitude = String.valueOf(latlng.longitude);
                    }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        else
        {
            requestwritReadexternalstoragePermissionFirst();
        }


        mMap.addMarker(new MarkerOptions().position(latlng).title(getResources().getString(R.string.map_vanue_markertext)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18.0f));
    }



    public void share_location()
    {
       // https://www.google.com/maps/dir/Surat,+Gujarat,+India/19.050034,72.829661/@20.1242595,72.3386365


        String uri ="http://maps.google.com/maps?q=" + latitude + "," + longitude + "&iwloc=A";

        Log.d("uri->","uri->"+uri);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,uri);
        startActivity(Intent.createChooser(shareIntent, "Share link using"));



    }

    public void navigate_location()
    {

        Log.d("latitude_sharelocation","latitude_sharelocation->"+latitude);
        Log.d("latitude_sharelocation","latitude_sharelocation->"+longitude);

        //String uri ="http://maps.google.com/maps?q=" + latitude + "," + longitude + "&iwloc=A";
      /*  String url = "http://maps.google.com/maps?q=" +current_latitude+","+current_longitude+"&daddr="+latitude+","+longitude+"&mode=driving";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(url)));*/

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr="+current_latitude+","+current_longitude+"&daddr="+getString(R.string.latitude)+","+getString(R.string.longitude)));
        startActivity(intent);

    }

    public void get_current_location()
    {
        Log.d("canGetLocation", "canGetLocation->" + gps.canGetLocation());

        if (gps.canGetLocation())
        {
            current_latitude = gps.getLatitude();
            current_longitude = gps.getLongitude();

            Log.d("current_latitude","current_latitude"+current_latitude);
            Log.d("current_longitude","current_longitude"+current_longitude);



        } else {
            gps.showSettingsAlert();
        }

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
    }

    private boolean weHavePermissionToReadExternalStorage() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestwritReadexternalstoragePermissionFirst() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
        {
            requestForResultreadExternalStoragePermission();
        }
        else
        {
            requestForResultreadExternalStoragePermission();
        }
    }

    private void requestForResultreadExternalStoragePermission()
    {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 112);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 112 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getActivity().getApplicationContext(),"Permission granted",Toast.LENGTH_SHORT).show();

            if (mMap != null)
            {
                onMapReady(mMap);
            }

        }
    }

    public void set_timer()
    {
        tv_days_togo = (TextView)rootview.findViewById(R.id.tv_days_togo);

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH,Integer.parseInt(getString(R.string.timer_date)));
        thatDay.set(Calendar.MONTH,Integer.parseInt(getString(R.string.timer_month))); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, Integer.parseInt(getString(R.string.timer_year)));

        Calendar today = Calendar.getInstance();

        long diff = thatDay.getTimeInMillis() -  today.getTimeInMillis() ; //re
        long days = diff / (24 * 60 * 60 * 1000);

      //  tv_days_togo.setText(days +"days to go");

        String date_to_live = getString(R.string.no_of_days);
        date_to_live = "-"+date_to_live;

        int date_to_live_val = Integer.parseInt(date_to_live) + 1;
        Log.d("date_to_live","date_to_live"+date_to_live_val);

        if (days > 0)
        {
            Log.d("days>0","days"+days);
            tv_days_togo.setText(days +"days to go");
        }
        else if (days ==0 || days >= date_to_live_val)
        {
            Log.d("days=0","days"+days);
            tv_days_togo.setText("Wordcamp is live");
        }
        else
        {
            Log.d("days<0","days"+days);
            tv_days_togo.setVisibility(View.GONE);
        }

    }
}
