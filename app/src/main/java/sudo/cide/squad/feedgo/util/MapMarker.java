package sudo.cide.squad.feedgo.util;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MapMarker implements ClusterItem {

    private LatLng position;
    private String title;
    private String snippet;

    public MapMarker(double lat, double lng, String title, String snippet) {
        position = new LatLng(lat, lng);
        this.title = title;
        this.snippet = snippet;
    }

    public MapMarker(double lat, double lng) {
        position = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }
}
