package com.wanli.fss.obocar.Pojo;

public class SessionLoger {
    private static String sessionId;

    public static String getSessionId() {
        return sessionId;
    }
    public static void setSessionId(String sessionId) {
        SessionLoger.sessionId = sessionId;
    }
}
