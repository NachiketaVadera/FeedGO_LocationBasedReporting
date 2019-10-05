package sudo.cide.squad.fidobite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.LocationRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 9001;

    public static double latitude;
    public static double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_searching, R.id.navigation_reporting, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        LocationRequest request = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(8000L)
                .setInterval(10000L);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getApplicationContext());
        ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        Disposable subscription = locationProvider.getUpdatedLocation(request).subscribe(new Consumer<Location>() {
            @Override
            public void accept(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });
    }
}
