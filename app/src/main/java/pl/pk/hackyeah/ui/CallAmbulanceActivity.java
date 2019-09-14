package pl.pk.hackyeah.ui;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import pl.pk.hackyeah.MainActivity;
import pl.pk.hackyeah.R;

@EActivity(R.layout.call_ambulance_activity)
public class CallAmbulanceActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    String mprovider;


    @AfterViews
    public void init() {


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        List<String> providers = locationManager.getProviders(true);
        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            Location prevLocation = location;
            if(location == null) {
                for (String provider : providers) {
                    prevLocation = location;
                    location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        onLocationChanged(location); //todo
                        prevLocation = location;
                        mprovider = provider;
                    }
                }
            }
            location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if  (location != null)
                onLocationChanged(location); //todo no location
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView longitude = (TextView) findViewById(R.id.textView);
        TextView latitude = (TextView) findViewById(R.id.textView1);
        try {
            Address address = getAdressByLocation(location);
            longitude.setText("Lokalizacja: " + address.getCountryName()  + " " + address.getAddressLine(0));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private Address getAdressByLocation(Location location) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        Address address = addresses.get(0);
        return address;

    }

    @Click(R.id.call)
    public void onPhoneClick(){
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL );
            callIntent.setData(Uri.parse("tel:"+"112"));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("Calling a Phone Number", "Call failed", activityException);
        }

          }
}
