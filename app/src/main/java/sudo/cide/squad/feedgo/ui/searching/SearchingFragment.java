package sudo.cide.squad.feedgo.ui.searching;

import android.os.Bundle;
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

import sudo.cide.squad.feedgo.R;
import sudo.cide.squad.feedgo.ReportStore;

public class SearchingFragment extends Fragment implements OnMapReadyCallback {

    private SearchingViewModel searchingViewModel;
    private MapView mapView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchingViewModel =
                ViewModelProviders.of(this).get(SearchingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_searching, container, false);

        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = null;
        int count = 0;
        for (ReportStore store : searchingViewModel.getReportStores()) {
            latLng = new LatLng(store.getLatitude(), store.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(store.getTitle())
                    .snippet("Description:\n" + store.getDescription() + "Category:\n" + store.getCategory()));
            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(getContext(), "Hello, World!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F));
        Toast.makeText(getContext(), "" + count, Toast.LENGTH_SHORT).show();
    }
}