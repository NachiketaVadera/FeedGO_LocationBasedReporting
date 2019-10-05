package sudo.cide.squad.feedgo;

class Global {
    private static String userName;

    static String getUserName() {
        return userName;
    }

    static void setUserName(String userName) {
        Global.userName = userName;
    }
}
