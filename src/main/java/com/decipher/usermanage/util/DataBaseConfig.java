package com.decipher.usermanage.util;

/**
 * Created by decipher16 on 3/3/17.
 * databse & hibernate properties initializer class
 */
public class DataBaseConfig {
    public static final String DRIVER_CLASS_NAME;
    public static final String URL;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;

    public static final String HIBERNATE_DIALECT;
    public static final Boolean HIBERNATE_SHOW_SQL;
    public static final String HIBERNATE_HBM_2_DDL_AUTO;
    public static final Boolean HIBERNATE_FORMAT_SQL;

    public static final Integer SERVER_STATUS_MODE =1;

    private DataBaseConfig() {

    }

    /****
     * setdatabase mode {local,on perticular Ip} according to server status
     * using static intializer block
     */

    static {
        if (SERVER_STATUS_MODE.equals(1)) {
            DRIVER_CLASS_NAME ="com.mysql.jdbc.Driver";
            URL ="jdbc:mysql://localhost:3306/USER_MANAGEMENT";
            DB_USERNAME ="root";
            DB_PASSWORD ="root";

            HIBERNATE_DIALECT ="org.hibernate.dialect.MySQL5Dialect";
            HIBERNATE_SHOW_SQL =false;
            HIBERNATE_HBM_2_DDL_AUTO ="update";
            HIBERNATE_FORMAT_SQL =false;
        }
        else if (SERVER_STATUS_MODE.equals(2)) {
            DRIVER_CLASS_NAME ="com.mysql.jdbc.Driver";
            URL ="jdbc:mysql://localhost:3306/USER_MANAGEMENT";
            DB_USERNAME ="root";
            DB_PASSWORD ="root";
            HIBERNATE_DIALECT ="org.hibernate.dialect.MySQL5Dialect";
            HIBERNATE_SHOW_SQL =false;
            HIBERNATE_HBM_2_DDL_AUTO ="update";
            HIBERNATE_FORMAT_SQL =false;
        }
        else {
            DRIVER_CLASS_NAME ="com.mysql.jdbc.Driver";
            URL ="jdbc:mysql://localhost:3306/USER_MANAGEMENT";
            DB_USERNAME ="root";
            DB_PASSWORD ="root";
            HIBERNATE_DIALECT ="org.hibernate.dialect.MySQL5Dialect";
            HIBERNATE_SHOW_SQL =true;
            HIBERNATE_HBM_2_DDL_AUTO ="create";
            HIBERNATE_FORMAT_SQL =false;
        }
    }
}
