package sudo.cide.squad.feedgo.ui.searching;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import sudo.cide.squad.feedgo.util.Global;
import sudo.cide.squad.feedgo.util.ReportStore;

public class SearchingViewModel extends ViewModel {

    private ArrayList<ReportStore> reportStores;

    public SearchingViewModel() {
        reportStores = Global.getReportStores();
    }

    public ArrayList<ReportStore> getReportStores() {
        return reportStores;
    }
}