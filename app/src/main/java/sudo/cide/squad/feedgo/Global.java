package sudo.cide.squad.feedgo;

import java.util.ArrayList;

public class Global {
    private static String userID;
    private static ArrayList<ReportStore> reportStores;

    static String getUserID() {
        return userID;
    }

    static void setUserName(String userName) {
        Global.userID = userName;
    }

    static void setStoreData(ArrayList<ReportStore> reportStores) {
        Global.reportStores = reportStores;
    }

    public static ArrayList<ReportStore> getReportStores() {
        return reportStores;
    }
}
