package net.mixium.kozmosbank.tools;

import net.mixium.kozmosbank.files.defaultconfig;

public class storageType {

    public static String storage = defaultconfig.getConfig().getString("kozmos-bank.storage");

    public static String getStorage(){
        return storage;
    }

    public static void $storageTypes(){
        if(storage.equalsIgnoreCase("mysql")) {

        }
    }


}
