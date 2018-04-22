package com.soul.app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.soul.app.R;
import com.soul.app.activity.*;
import com.soul.app.application.ApplicationController;
import com.soul.app.models.req.GeneralReq;
import com.soul.app.models.res.ObjResp;
import com.soul.app.retrofit.ApiClient;
import com.soul.app.utils.Lg;
import com.soul.app.utils.PrefUtils;
import com.soul.app.utils.Utility;
import com.soul.app.utils.msupport.MSupportConstants;
import com.soul.app.utils.msupport_1.MSupport;
import com.github.mrengineer13.snackbar.SnackBar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by bhanu on 29/12/15.
 */
public abstract class BaseGpsActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    public static GoogleApiClient mGoogleApiClient = null;
    public Location mLocation;
    //String userId;
    private String TAG = BaseGpsActivity.class.getSimpleName();
    private GoogleMap mGoogleMap;

//    @Override
//    public void onLocationChanged(Location location) {
//        updateLocation(location);
//
//
//    }

    public static GoogleApiClient getGoogleClient() {
        return mGoogleApiClient;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLocation();
    }

    void initLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        //    mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            Log.e(TAG, "onConnected" + bundle);
        } catch (Exception e) {
            // DialogUtils.showToast(this, "Please Try Again");
            new SnackBar.Builder(this)
                    .withMessage(getResources().getString(R.string.please_try_again)).show();
        }
        if (!MSupport.isMSupportDevice(this))
            requestLocationUpdate(1000 * 10 * 6 * 3);  // call Service with in 1 min  (60*1000*10)
        else {
            if (MSupport.checkOrRequestPermission(this, MSupportConstants.ACCESS_COARSE_LOCATION, MSupportConstants.MY_PERMISSION_LOCATION)) {
                requestLocationUpdate(1000 * 10 * 6 * 3);  // call Service with in 1 min (60*1000*10)
            }
        }
    }

    private LocationRequest getLocationRequest(int millsInterval) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(millsInterval); // Update location every secon

        return locationRequest;
    }

    public void requestLocationUpdate(int millsec) {
        //  if (!MSupport.isMSupportDevice(this)) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Lg.i(TAG, "permission not granted");

            return;
        } else {
            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLocation != null) {
                String userId = PrefUtils.getSharedPrefString(getApplicationContext(), PrefUtils.USER_ID);
                if (!TextUtils.isEmpty(userId)) {
                    String add = saveLoc(mLocation);
                    updateLocation(mLocation, add);
                }
            }
            LocationRequest locationRequest = getLocationRequest(millsec);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    String userId = PrefUtils.getSharedPrefString(getApplicationContext(), PrefUtils.USER_ID);
                    if (!TextUtils.isEmpty(userId)) {
                        String add = saveLoc(location);
                        updateLocation(location, add);
                    }
                }
            });
        }

        // } else {
        //     MSupport.checkOrRequestPermission(this, MSupportConstants.ACCESS_COARSE_LOCATION, MSupportConstants.MY_PERMISSION_LOCATION);
        //  }
    }

   /* protected void setUpdateInterval(int sec) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        requestLocationUpdate(sec * 1000);
    }*/

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "onConnectionSuspended" + i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed" + connectionResult.toString());

    }

    public abstract void mapReady(GoogleMap googleMap);

    public void setupMap(int mapContainerId) {
        SupportMapFragment supportMapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction().replace(mapContainerId, supportMapFragment).commit();
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (!MSupport.isMSupportDevice(BaseGpsActivity.this)) {
                    mGoogleMap = googleMap;
                    //mGoogleMap.setOnMapClickListener();
                    mGoogleMap.setTrafficEnabled(true);
                    mGoogleMap.setMyLocationEnabled(true);
                    mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
                    mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
                    mGoogleMap.getUiSettings().setTiltGesturesEnabled(true);
                    mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    mGoogleMap.getUiSettings().setCompassEnabled(true);
                    //setCurrentLocation(20, -10, R.drawable.down_arrow);
                    mapReady(mGoogleMap);
                } else {
                    MSupport.checkOrRequestPermission(BaseGpsActivity.this, MSupportConstants.ACCESS_COARSE_LOCATION, MSupportConstants.MY_PERMISSION_LOCATION);
                }
            }
        });
    }

    public void setCircleBackGroundMarker(LatLng latLng) {
        MarkerOptions mMarkerOptionsCircle = new MarkerOptions().position(latLng);
        // mMarkerOptionsCircle.icon(BitmapDescriptorFactory.fromResource(R.drawable.circle));
        mMarkerOptionsCircle.anchor(0.5f, 0.68f).alpha(0.5f);
        mGoogleMap.addMarker(mMarkerOptionsCircle);
    }

    public Marker addMarker(LatLng latLng, int markerDrawableId) {
        //final LatLng latLng = new LatLng(lat, lng);
        MarkerOptions mMarkerOptionsPin = new MarkerOptions().position(latLng);
        if (markerDrawableId != 0)
            mMarkerOptionsPin.icon(BitmapDescriptorFactory.fromResource(markerDrawableId));
        return mGoogleMap.addMarker(mMarkerOptionsPin);
    }


    public Marker setCurrentLocation(double lat, double lng, int markerDrawableId) {
        LatLng latLng = new LatLng(lat, lng);
        setCircleBackGroundMarker(latLng);
        zoomCameraToCurrentPosition(latLng);
        return addMarker(latLng, markerDrawableId);
    }

    private void zoomCameraToCurrentPosition(final LatLng latLng) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            }
        }, 300);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions != null && permissions.length > 0) {
            if (permissions[0].equalsIgnoreCase(MSupportConstants.ACCESS_COARSE_LOCATION)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdate(1000 * 10 * 6 *3);  // call Service with in 1 min (60*1000*10)
                } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {

                }
            }
        }
    }

    private void updateLocation(Location location, String add) {

        if (ApplicationController.getApplicationInstance().isNetworkConnected()) {
            try {
                GeneralReq generalReq = new GeneralReq();
                //  String lat = location.getLatitude() + "";
                generalReq.setLatitude(location.getLatitude() + "");
                generalReq.setLongitude(location.getLongitude() + "");
                generalReq.setAddress(add);

                generalReq.setUser_id(PrefUtils.getSharedPrefString(this, PrefUtils.USER_ID));
                generalReq.setLast_seen(System.currentTimeMillis() + "");


                PrefUtils.getSharedPrefString(getApplicationContext(), PrefUtils.USER_ID);
                Call<ObjResp> call = new ApiClient().getApis().updateLoc(generalReq);
//            showProgressDialog(true);
                call.enqueue(new Callback<ObjResp>() {

                    @Override
                    public void onResponse(Call<ObjResp> call, Response<ObjResp> response) {
                        if (response.isSuccessful()) {
                            Lg.e("location", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<ObjResp> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String saveLoc(Location location) {
        List locList = Utility.getAddressFromLatLong(BaseGpsActivity.this, location.getLatitude(), location.getLongitude(), 0);
        String myLoc = "";
        if (locList != null) {
            int size = locList.size();
            if (size > 0) {
                myLoc = locList.get(size - 1).toString();
                //PrefUtils.setSharedPrefStringData(BaseGpsActivity.this, PrefUtils.PREF_LOCATION, myLoc);
            }
        }
        return myLoc;
    }
}
