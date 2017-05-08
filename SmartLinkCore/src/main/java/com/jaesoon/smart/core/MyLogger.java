package com.jaesoon.smart.core;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class MyLogger {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(MyLogger.class);

    public static void log(String msg) {
        msg = "[APP][" + System.currentTimeMillis() + "]: " + msg;
//        String model = Property.getProperty("debugModel");
//        if (model.equals("debug")){
        System.out.println(msg);
//        } else if (model.equals("product")) {
        log.debug(msg);
//        }
    }
}  