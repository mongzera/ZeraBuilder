package com.chemicaldev.zerabuilder.query;

public class ZLog {
    public static void warn(String feature){
        System.out.println(
                "[SQLite] ALTER TABLE does not support: " + feature
        );
    }
}
