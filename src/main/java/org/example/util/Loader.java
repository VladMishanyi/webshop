package org.example.util;

import java.util.*;

public class Loader {
    public final static Map<String, String> kayValueParams = new HashMap<>();
    public final static List<String> singleParam = new ArrayList<>();

    public static void parseArgs(String[] args) {

        for(int i = 0; i < args.length; i++) {
            if (args[i].charAt(0) == '-') {

                if (args[i].charAt(1) == '-') {
                    if (args[i].length() < 3) throw new IllegalArgumentException("Not a valid argument: "+args[i]);
                    String[] kayValue = args[i].split("=");
                    kayValueParams.put(kayValue[0].substring(2), kayValue[1]);
                }
            } else {
                singleParam.add(args[i]);
            }
        }
    }
}
