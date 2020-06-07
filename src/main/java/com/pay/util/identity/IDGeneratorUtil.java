package com.pay.util.identity;

import java.util.Date;
import java.util.Random;



public class IDGeneratorUtil
{
    private static Random rnd;
    private static int randomMaxValue = 1000000;
    private static int corpNumMaxValue = 1000000;
    private static int tableObjectPrefix = 200000000;
    private static int kindMaxValue = 100;

    private static int globalObjectPrefix = 1;
    private static int systemObjectPrefix = 2;
    private static int transObjectPrefix = 3;
    private static int userObjectPrefix = 4;
    private static int logIDPrefix = 5;

    private static long suffix = 100L;

    private static String getRandomValue()
    {
        if (suffix == 1000L) {
            suffix = 100L;
        }
        suffix += 1L;
        return String.valueOf(suffix).substring(1);
    }

    private static String getRandomValue(int index) {
        if (rnd == null) {
            rnd = new Random(System.nanoTime());
        }
        return String.valueOf(randomMaxValue + rnd.nextInt(randomMaxValue) + index).substring(1);
    }

    public static synchronized String getID(int kindValue)
    {
        return kindValue + String.valueOf(System.currentTimeMillis()) + getRandomValue();
    }

    public static synchronized String getID(String kindValue) {
        return kindValue + String.valueOf(System.currentTimeMillis()) + getRandomValue();
    }

    public static String getZhanShiID()
    {
        return (Math.random() * 100.0D + 1.0D) + new Date().getTime() + "";
    }

    public static String getDefaultPwd() {
        return "" + ((Math.random() * 9.0D + 1.0D) * 100000.0D);
    }
}

