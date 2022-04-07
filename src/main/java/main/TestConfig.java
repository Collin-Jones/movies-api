package main;

public class TestConfig {
    public static final String DB_HOST = "jdbc:mysql://emp.fulgentcorp.com:3306/collin?allowPublicKeyRetrieval=true&useSSL=false";
    public static final String DB_USER = "collin";
    public static final String DB_PW = "dU2ffePp4Tz8b6nx";

    public static String getHost(){
        return DB_HOST;
    }

    public static String getUser(){
        return DB_USER;
    }

    public static String getPassword(){
        return DB_PW;
    }
}
