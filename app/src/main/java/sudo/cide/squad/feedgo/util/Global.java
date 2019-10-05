package sudo.cide.squad.feedgo.util;

import java.util.ArrayList;

public class Global {
    private static String userID;
    private static ArrayList<ReportStore> reportStores;

    public static String getUserID() {
        return userID;
    }

    public static void setUserName(String userName) {
        Global.userID = userName;
    }

    public static void setStoreData(ArrayList<ReportStore> reportStores) {
        Global.reportStores = reportStores;
    }

    public static ArrayList<ReportStore> getReportStores() {
        return reportStores;
    }
}
