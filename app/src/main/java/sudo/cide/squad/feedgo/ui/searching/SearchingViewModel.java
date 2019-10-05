package sudo.cide.squad.feedgo.ui.searching;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import sudo.cide.squad.feedgo.Global;
import sudo.cide.squad.feedgo.ReportStore;

public class SearchingViewModel extends ViewModel {

    private ArrayList<ReportStore> reportStores;

    public SearchingViewModel() {
        reportStores = Global.getReportStores();
    }

    public ArrayList<ReportStore> getReportStores() {
        return reportStores;
    }
}