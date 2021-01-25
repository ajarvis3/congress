package com.example.mongo_endpoint;

import com.mongodb.ConnectionString;

public class Constants {
    private static final String MONGO_DB_PASSWORD = "myAwesomePassword1";
    private static final String MONGO_USER = "ajarvis";
    public static final String MONGO_DB = "congress";

    public static final String MONGO_CONNECTION_STRING = String.format(
        "mongodb+srv://%s:%s@cluster0.l7nkp.mongodb.net/test?w=majority", 
        MONGO_USER, MONGO_DB_PASSWORD, MONGO_DB
    );

    public static final ConnectionString MONGO_CONNECTION = new ConnectionString(
        MONGO_CONNECTION_STRING
    );
}

