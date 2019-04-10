package com.wanli.fss.obocar.Session;

public class SessionLoger {
    private static String sessionId;

    public static String getSessionId() {
        return sessionId;
    }
    public static void setSessionId(String sessionId) {
        SessionLoger.sessionId = sessionId;
    }
}
