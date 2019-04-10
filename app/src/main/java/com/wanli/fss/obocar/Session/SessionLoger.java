package com.wanli.fss.obocar.Session;

public class SessionLoger {
    private static String sessionId;
    private static String peerId;//对端的ID对于乘客来说就是司机的ID

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        SessionLoger.sessionId = sessionId;
    }

    public static String getPeerId() {
        return peerId;
    }

    public static void setPeerId(String peerId) {
        SessionLoger.peerId = peerId;
    }
}
