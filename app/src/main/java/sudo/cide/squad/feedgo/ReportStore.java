package sudo.cide.squad.feedgo;

public class ReportStore {

    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private String category;

    ReportStore(String title, String description, double latitude,
                double longitude, String category) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
