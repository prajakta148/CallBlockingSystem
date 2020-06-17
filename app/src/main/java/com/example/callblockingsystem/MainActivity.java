package com.example.callblockingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this, "Started the app", Toast.LENGTH_SHORT).show();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED || checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS,Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
                //doStuff();
            }
        }
    }
    @Override
    public void onLocationChanged(Location location) {

        TextView txt = (TextView) this.findViewById(R.id.textView1);

        if (location == null) {

            txt.setText("-.- km/h");
        } else {
            float nCurrentSpeed = location.getSpeed() * 3.6f;
            txt.setText(String.format("%.2f", nCurrentSpeed) + " km/h");
            if(nCurrentSpeed>60)
            {
                MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.ring);
                ring.start();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doStuff();
                    //Toast.makeText(this, "Permission granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Permission NOT granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                }

                return;
            }

        }
    }
    private void doStuff() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm .requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            //commented, this is from the old version
            // this.onLocationChanged(null);
        }
        Toast.makeText(this,"Waiting for GPS connection!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
