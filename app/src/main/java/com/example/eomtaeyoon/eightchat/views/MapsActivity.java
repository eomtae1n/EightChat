package com.example.eomtaeyoon.eightchat.views;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.eomtaeyoon.eightchat.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.UiSettings;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.view.View.VISIBLE;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener {

    private static final int LOCATION_REQUEST_CODE = 101;
    String TAG = ".MapsActivity";

    Intent intent;

    private TextView mTapTextView;
    private Button btgo;
    private GoogleMap mMap;
    UiSettings mapSettings;
    ListView listView;
    ItemListAdapter adapter;
    List<OneItem> items;
    int cnt = 0;

    public class OneItem {
        String time;
        String content;

        public OneItem(String time, String content) {
            this.time = time;
            this.content = content;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mTapTextView = (TextView) findViewById(R.id.tap_text);
        btgo = (Button) findViewById(R.id.go);
        btgo.setVisibility(View.INVISIBLE);

        /*Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startLocationService();
            }
        });

        items = new ArrayList<OneItem>();

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ItemListAdapter(this, R.layout.item_layout, items);
        listView.setAdapter(adapter);*/
    }

    class ItemListAdapter extends ArrayAdapter<OneItem> {
        private List<OneItem> items;
        private Context context;
        private int layoutResource;

        public void setContext(Context c) {
            this.context = c;
        }

        public ItemListAdapter(Context context, int layoutResource, List<OneItem> items) {
            super(context, layoutResource, items);
            this.context = context;
            this.items =  items;
            this.layoutResource = layoutResource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(layoutResource, null);
            }

            final OneItem oneItem = items.get(position);

            if (oneItem != null) {
                TextView content = (TextView) convertView.findViewById(R.id.content);
                TextView time = (TextView) convertView.findViewById(R.id.time);

                if (content != null){
                    content.setText(oneItem.content);
                }
                if (time != null){
                    time.setText(oneItem.time);
                }
            }
            return convertView;
        }
    }

    private void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GPSListener gpsListener = new GPSListener();
        long minTime = 1000;
        float minDistance = 0;

        try {
            //최근에 알려진 위치 얻어오기
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                /*Timestamp cur = new Timestamp(System.currentTimeMillis());
                items.add(new OneItem(cur.toString(), cnt + "\n(" + latitude + "," + longitude+")"));
                cnt++;
                listView.setAdapter(adapter);*/
            }

            //주기적으로 GPS 정보 받도록 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);
        }
        catch(SecurityException ex) {
            ex.printStackTrace();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            Timestamp cur = new Timestamp(System.currentTimeMillis());
            items.add(new OneItem(cur.toString(), cnt + "\n(" + latitude + "," + longitude+")"));
            cnt++;

            listView.setAdapter(adapter);

        }

        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE);
            }
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng Dongguk = new LatLng(37.558096,126.9960291);
            Marker dongguk = mMap.addMarker(new MarkerOptions().position(Dongguk)
                    .title("Dongguk Univ")
                    .snippet("동국대학교 신공학관"));
        }

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
    }

    @Override
    public void onMapClick(LatLng point) {
        mMap.clear();
        mTapTextView.setText("tapped, point=" + point);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point); // 마커위치설정
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

        TextView lat = (TextView) findViewById(R.id.lat);
        TextView lon = (TextView) findViewById(R.id.lon);

        lat.setText(String.valueOf(point.latitude));
        lon.setText(String.valueOf(point.longitude));

    }

    @Override
    public void onMapLongClick(LatLng point) {
        mMap.clear();
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mTapTextView.setText("long pressed, point=" + point);
        btgo.setVisibility(View.VISIBLE);

        final double lat_d = point.latitude;
        final double lon_d = point.longitude;

        /*Intent intent = getIntent();
        intent.putExtra("lat", String.valueOf(lat_d));
        intent.putExtra("lon", String.valueOf(lon_d));
        setResult(RESULT_OK, intent);

        finish();*/

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point); // 마커위치설정
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point));

        /*List<Address> geocodeMatches = null;
         String Address1;
         String Address2;
         String State;
         String Zipcode;
         String Country;

         try {
         geocodeMatches = new Geocoder(this).getFromLocation(lat_d, lon_d, 1);
         } catch (IOException e) {
         e.printStackTrace();
         }

         if (!geocodeMatches.isEmpty())
         {
         Address1 = geocodeMatches.get(0).getAddressLine(0);
         Address2 = geocodeMatches.get(0).getAddressLine(1);
         State = geocodeMatches.get(0).getAdminArea(0);
         Zipcode = geocodeMatches.get(0).getPostalCode(0);
         Country = geocodeMatches.get(0).getCountryName(0);
         }
         */


        btgo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                intent.putExtra("lat", String.valueOf(lat_d));
                intent.putExtra("lon", String.valueOf(lon_d));
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    protected void requestPermission(String permissionType, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permissionType}, requestCode);
    }

    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if(grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Unable to show location - permission required",
                            Toast.LENGTH_LONG).show();
                } else {
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }
}
