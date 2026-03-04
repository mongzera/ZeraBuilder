package com.chemicaldev.zerabuilder.util;

import java.time.Instant;

public class SQLiteHelperUtil {
    public String getISODate(){
        return getISODateTime().split("T")[0];
    }

    public String getISODateTime(){
        Instant instant = Instant.now();
        return instant.toString();
    }
}
