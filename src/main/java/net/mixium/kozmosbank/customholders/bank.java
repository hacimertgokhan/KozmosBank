package net.mixium.kozmosbank.customholders;

import net.mixium.kozmosbank.files.storage;
import net.mixium.kozmosbank.mysql.manager.DataPlayer;
import org.bukkit.entity.Player;

import static net.mixium.kozmosbank.KozmosBank.isSQL;

public class bank {



    public static float getBankBalance(Player player) {
        if(isSQL()) {
            return DataPlayer.getPlayerBalance(player);
        } else {
            return storage.getConfig().getInt("user-datas." + player.getUniqueId().toString() + ".bank-balance");
        }
    }

}
