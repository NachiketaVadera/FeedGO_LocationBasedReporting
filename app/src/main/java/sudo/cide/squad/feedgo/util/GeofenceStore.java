package sudo.cide.squad.feedgo.util;

import com.google.android.gms.maps.model.LatLng;

public class GeofenceStore {

    private String geofenceID;
    private String title;
    private String category;
    private String description;
    private LatLng position;

    public GeofenceStore(String geofenceID, String title, String category, String description, LatLng position) {
        this.geofenceID = geofenceID;
        this.title = title;
        this.category = category;
        this.description = description;
        this.position = position;
    }

    public String getGeofenceID() {
        return geofenceID;
    }

    public void setGeofenceID(String geofenceID) {
        this.geofenceID = geofenceID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
