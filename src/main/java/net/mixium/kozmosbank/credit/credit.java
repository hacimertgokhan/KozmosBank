package net.mixium.kozmosbank.credit;

import net.mixium.kozmosbank.files.defaultconfig;

public class credit {

    public static boolean creditMode = defaultconfig.getConfig().getBoolean("kozmos-bank.credit.enable");

    /*

        Iterator var0 = MXConfig.getConfig().getConfigurationSection("mx-rank.player-ranks").getKeys(false).iterator();

        while (var0.hasNext()) {

     */

    public static double calculatedRefundAmount(int amount, double tax){return (amount + (amount*tax));}

}
