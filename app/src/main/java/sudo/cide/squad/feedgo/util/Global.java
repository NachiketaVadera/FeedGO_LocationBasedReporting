package sudo.cide.squad.feedgo.util;

import java.util.ArrayList;

public class Global {
    private static String userID;
    private static ArrayList<ReportStore> reportStores;
    private static int notificationRadius = 400;
    private static GeofenceStore geofenceStore;
    private static boolean inRadius = false;

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

    public static int getNotificationRadius() {
        return notificationRadius;
    }

    public static void setNotificationRadius(int notificationRadius) {
        Global.notificationRadius = notificationRadius;
    }

    public static GeofenceStore getGeofenceStore() {
        return geofenceStore;
    }

    public static void setGeofenceStore(GeofenceStore geofenceStore) {
        Global.geofenceStore = geofenceStore;
    }

    public static boolean isInRadius() {
        return inRadius;
    }

    public static void setInRadius(boolean bool) {
        inRadius = bool;
    }
}
