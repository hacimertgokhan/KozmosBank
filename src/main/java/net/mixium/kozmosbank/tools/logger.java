package net.mixium.kozmosbank.tools;

import net.mixium.kozmosbank.KozmosBank;

import java.util.logging.Logger;

public class logger {
    public static final Logger loggman = Logger.getLogger("Minecraft");
    private static KozmosBank main;

    public logger(KozmosBank main) {
        logger.main = main;
    }

    public Logger getLoggman() {
        return loggman;
    }

    public void send(String message) {
        loggman.info("[KozmosBank:"+ getClass().getSimpleName() +"]; " + message);
    }




}
