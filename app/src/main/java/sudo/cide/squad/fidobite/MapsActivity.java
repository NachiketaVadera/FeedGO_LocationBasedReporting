package sudo.cide.squad.fidobite;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String TAG = "__FIDOBITE__";

    private static String title;
    private static String description;
    private static String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.i(TAG, "onCreate: getMapAsync is null");
        }

        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("desc");
        choice = getIntent().getStringExtra("choice");

        Toast toast = Toast.makeText(this, "Long Click Map: Confirm" +
                "\nClick Marker: Make Changes", Toast.LENGTH_LONG);
        View toastView = toast.getView();
        TextView toastText = toastView.findViewById(android.R.id.message);
        toastText.setTextColor(Color.rgb(255, 255, 255));
        toastView.setBackgroundColor(Color.rgb(31, 32, 33));
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng userLocation = new LatLng(MainActivity.latitude, MainActivity.longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .title(title)
                .snippet("Description:" + description + "\nCategory:" + choice));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(userLocation));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f));

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });
    }
}
