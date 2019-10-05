package sudo.cide.squad.feedgo;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "__FeedGO__";

    public static double latitude;
    public static double longitude;

    private ArrayList<ReportStore> reportStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        reportStores = new ArrayList<>();

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

        CollectionReference reference = firestore.collection("app").document("store").collection("data");
        reference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        ReportStore store = document.toObject(ReportStore.class);
                        reportStores.add(store);
                        Log.i(TAG, document.getId() + " => " + document.getData());
                    }
                    Global.setStoreData(reportStores);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        Disposable subscription = locationProvider.getUpdatedLocation(request).subscribe(new Consumer<Location>() {
            @Override
            public void accept(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                //Toast.makeText(MainActivity.this, latitude + "\n" + longitude, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
