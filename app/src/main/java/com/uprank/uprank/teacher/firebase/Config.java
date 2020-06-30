package com.uprank.uprank.teacher.firebase;

public class Config {
    public static final String TOPIC = "model_click";
    public static final String FIREBASE_API = "https://fcm.googleapis.com/fcm/send";
    public static final String FIREBASE_SENDER_ID = "88527745065";
    public static final String FIREBASE_LEGACY_SERVER_KEY = "AIzaSyCnFn9ogXk_KvK8uC4PGF1Fp5bS5w6A9D0";
    public static final String FIREBASE_SERVER_KEY = "AAAAAbT2OnU:APA91bFCnVEMcrbnF2miPUfd422kd7yDHnz5PAnXvzl_-z6LMGulPGfWPWJ1iPAMXmJTzZfut73x_ViuvakOqPjdywFuFm9dvMlZ07CVQGQvdFmJgy48Ql2TWzDS9earOBm7EmT-pDZf";
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";
    public static String content = "";
    public static String title = "";
    public static String imageUrl = "";
    public static String gameUrl = "";
    public static String Shared_Pref_Name = "model_click";
}
