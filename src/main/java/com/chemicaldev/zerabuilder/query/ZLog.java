package com.chemicaldev.zerabuilder.query;

public class ZLog {
    public static void warn(String name, String message){
        System.out.println(
                String.format("[%s]: %s", name, message)
        );
    }
}
