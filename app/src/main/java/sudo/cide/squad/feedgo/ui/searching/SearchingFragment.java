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

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sudo.cide.squad.feedgo.R;
import sudo.cide.squad.feedgo.ReportStore;

public class SearchingFragment extends Fragment implements OnMapReadyCallback {

    private SearchingViewModel searchingViewModel;

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
        LatLng latLng = null;
        int count = 0;
        for (ReportStore store : searchingViewModel.getReportStores()) {
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
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F));
        Toast.makeText(getContext(), "" + count, Toast.LENGTH_SHORT).show();
    }
}