package net.mixium.kozmosbank.tools;

import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.files.lang;
import net.mixium.kozmosbank.files.storage;

public class reload {

    public static void reloadAll(){
        storage.reloadConfig();
        defaultconfig.reload();
        lang.reload();
        storage.saveConfig();
        defaultconfig.saveConfig();
        lang.saveConfig();
    }

}
