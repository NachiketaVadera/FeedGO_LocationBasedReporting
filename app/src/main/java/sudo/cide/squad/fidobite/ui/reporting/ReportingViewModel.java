package sudo.cide.squad.fidobite.ui.reporting;

import androidx.lifecycle.ViewModel;

public class ReportingViewModel extends ViewModel {

    private static double latitude;
    private static double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        ReportingViewModel.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        ReportingViewModel.longitude = longitude;
    }
}