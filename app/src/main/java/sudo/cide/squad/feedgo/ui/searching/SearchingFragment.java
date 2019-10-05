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
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sudo.cide.squad.feedgo.R;
import sudo.cide.squad.feedgo.util.MapMarker;
import sudo.cide.squad.feedgo.util.ReportStore;

import static sudo.cide.squad.feedgo.MainActivity.TAG;

public class SearchingFragment extends Fragment implements OnMapReadyCallback {

    private SearchingViewModel searchingViewModel;
    private Collection<MapMarker> markers;
    private LatLng latLng = null;
    private LatLng staticLatLng;
    private GoogleMap gMap;

    private static double getDistance(double x1, double x2, double y1, double y2) {
        return Math.hypot(Math.abs(y2 - y1), Math.abs(x2 - x1));
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchingViewModel =
                ViewModelProviders.of(this).get(SearchingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_searching, container, false);

        staticLatLng = new LatLng(23.1869508, 72.6288302);

        markers = new ArrayList<>();

        MapView mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        mapView.getMapAsync(this);

        return view;
    }

    private void setUpClusterManager(Collection<MapMarker> markers) {
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(staticLatLng, 18F));
        ClusterManager<MapMarker> clusterManager = new ClusterManager<>(Objects.requireNonNull(getContext()), gMap);
        clusterManager.addItems(markers);
        gMap.setOnCameraIdleListener(clusterManager);
        gMap.setOnMarkerClickListener(clusterManager);
        clusterManager.cluster();
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MapMarker>() {
            @Override
            public boolean onClusterClick(Cluster<MapMarker> cluster) {
                Toast.makeText(getContext(), "" + cluster.getSize(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "Map", Toast.LENGTH_SHORT).show();
        gMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        int count = 0;

        for (ReportStore store : searchingViewModel.getReportStores()) {
            latLng = new LatLng(store.getLatitude(), store.getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(store.getTitle())
                    .snippet("Description:\n" + store.getDescription() + "\nCategory:\n" + store.getCategory()));
            ArrayList<String> list = checkMatchingMarker(marker, searchingViewModel.getReportStores());
            for (int i = 0; i < list.size(); i++) {
                String[] strings = list.get(i).split("\n");
                Log.i(TAG, "list item:\n\n\n" + list.get(i));
                markers.add(new MapMarker(Double.parseDouble(strings[0]),
                        Double.parseDouble(strings[1]),
                        strings[2],
                        "Description:\n" + strings[3] + "\nCategory:\n" + strings[4]));
            }
            setUpClusterManager(markers);

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
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F));
        Toast.makeText(getContext(), "" + count, Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> checkMatchingMarker(Marker marker, ArrayList<ReportStore> reportStores) {
        ArrayList<String> result = new ArrayList<>();
        for (ReportStore store : reportStores) {
            if (store.getTitle().contains(marker.getTitle())
                    || marker.getTitle().contains(store.getTitle())) {
                if (getDistance(store.getLatitude(), store.getLongitude(),
                        marker.getPosition().latitude, marker.getPosition().longitude) > 0.5D) {
                    result.add(store.getLatitude() +
                            "\n" +
                            store.getLongitude() +
                            "\n" +
                            store.getTitle() +
                            "\n" +
                            store.getDescription() +
                            "\n" +
                            store.getCategory());
                }
            }
        }
        return result;
    }
}