package sudo.cide.squad.feedgo.ui.searching;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import sudo.cide.squad.feedgo.MainActivity;
import sudo.cide.squad.feedgo.util.Global;
import sudo.cide.squad.feedgo.util.ReportStore;

public class SearchingViewModel extends ViewModel {

    private ArrayList<ReportStore> reportStores;
    private ArrayList<ReportStore> dangerStores;
    private ArrayList<ReportStore> dangerStoresNearUser;

    public SearchingViewModel() {
        reportStores = Global.getReportStores();
        dangerStores = filterStore(reportStores);
        dangerStoresNearUser = filterStore(dangerStores, MainActivity.latitude, MainActivity.longitude);

        int[] size = {reportStores.size(), dangerStores.size(), dangerStoresNearUser.size()};
    }

    private ArrayList<ReportStore> filterStore(ArrayList<ReportStore> reportStores) {
        ArrayList<ReportStore> result = new ArrayList<>();
        for (ReportStore store :
                reportStores) {

            if (store.getCategory().equals("Danger")) {
                result.add(store);
            }

        }
        return result;
    }

    private ArrayList<ReportStore> filterStore(ArrayList<ReportStore> rStores, double u_latitude, double u_longitude) {
        ArrayList<ReportStore> result = new ArrayList<>();
        for (ReportStore store :
                rStores) {
            double t_latitude = store.getLatitude();
            double t_longitude = store.getLongitude();

            double dLat = Math.abs(t_longitude - u_longitude);
            double dLng = Math.abs(u_latitude - t_latitude);

            if (dLat < 0.050 && dLng < 0.050) {
                result.add(store);
            }
        }
        return result;
    }

    private double getDistance(double x1, double x2, double y1, double y2) {
        return Math.hypot(Math.abs(y2 - y1), Math.abs(x2 - x1));
    }

    ArrayList<ReportStore> getReportStores() {
        return reportStores;
    }

    ArrayList<ReportStore> getDangerStores() {
        return dangerStores;
    }

    ArrayList<ReportStore> getDangerStoresNearUser() {
        return dangerStoresNearUser;
    }
}