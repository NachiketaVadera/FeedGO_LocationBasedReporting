package sudo.cide.squad.feedgo.ui.searching;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pathsense.android.sdk.location.PathsenseLocationProviderApi;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sudo.cide.squad.feedgo.GeofenceReciever;
import sudo.cide.squad.feedgo.R;
import sudo.cide.squad.feedgo.util.GeofenceStore;
import sudo.cide.squad.feedgo.util.Global;
import sudo.cide.squad.feedgo.util.ReportStore;

import static sudo.cide.squad.feedgo.MapsActivity.TAG;

public class SearchingFragment extends Fragment implements OnMapReadyCallback {

    private SearchingViewModel searchingViewModel;
    private LatLng latLng = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchingViewModel =
                ViewModelProviders.of(this).get(SearchingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_searching, container, false);


        MapView mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);

        for (int i = 0; i < searchingViewModel.getDangerStores().size(); i++) {
            ReportStore store = searchingViewModel.getDangerStores().get(i);
            latLng = new LatLng(store.getLatitude(), store.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(store.getTitle())
                    .snippet("Description:\n" + store.getDescription() + "\nCategory:\n" + store.getCategory()));
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    final SweetAlertDialog alertDialog = new SweetAlertDialog(Objects.requireNonNull(getContext()), SweetAlertDialog.NORMAL_TYPE);
                    alertDialog.setTitle(marker.getTitle());
                    alertDialog.setContentText(marker.getSnippet());
                    alertDialog.setConfirmButton("Cool", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            alertDialog.dismissWithAnimation();
                        }
                    });
                    alertDialog.show();
                }
            });
        }
        if (searchingViewModel.getDangerStoresNearUser().size() > 3) {
            PolygonOptions options = new PolygonOptions();
            String category = "";
            String title = "";
            String description = "";
            for (ReportStore s : searchingViewModel.getDangerStoresNearUser()) {
                if (s.getCategory().equals("Danger")) {
                    options.add(new LatLng(s.getLatitude(), s.getLongitude()));
                    category = s.getCategory();
                    title = s.getTitle();
                    description = s.getDescription();
                }
            }
            googleMap.addPolygon(options);
            PathsenseLocationProviderApi providerApi = PathsenseLocationProviderApi.getInstance(getContext());
            String geofenceID = "danger_fence_1";
            providerApi.addGeofence(geofenceID, latLng.latitude, latLng.longitude
                    , Global.getNotificationRadius(), GeofenceReciever.class);
            GeofenceStore geofenceStore =
                    new GeofenceStore(geofenceID, title,
                            category, description, latLng);

            Global.setGeofenceStore(geofenceStore);

            DocumentReference firestore = FirebaseFirestore.getInstance().document("app/store");

            firestore.collection("geofence")
                    .add(geofenceStore)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.i(TAG, "onSuccess: Data stored successfully with " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: store attempt failed", e);
                        }
                    });
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F));
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Toast.makeText(getContext(), "lat: " + latLng.latitude
                        + "\nlng: " + latLng.longitude, Toast.LENGTH_SHORT).show();
            }
        });
    }
}