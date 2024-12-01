package com.iit.rakshak;

public class URLs {

    private static final String ROOT_URL = "http://192.168.43.197/saket/Api.php?apicall=";
    private static final String ROOT_URL2 = "http://192.168.43.197/saket/Vsign.php?apicall=";

    //10.0.2.2 for emulator

    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN = ROOT_URL + "login";
    public static final String URL_Heart = ROOT_URL2 + "hrate";
    public static final String URL_Rprate = ROOT_URL2 + "rprate";
    public static final String URL_bp = ROOT_URL2 + "bp";
    public static final String URL_Osaturation = ROOT_URL2 + "osaturation";
    public static final String URL_Vsign = ROOT_URL2 + "allvsign";

}