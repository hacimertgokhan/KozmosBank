package net.mixium.kozmosbank.mysql.manager;

import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.mysql.MySqlConnector;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DataPlayer {
    public static MySqlConnector mySqlConnector = new MySqlConnector(KozmosBank.get());

    public static int getPlayerBalance(Player player) {
        UUID uuid = player.getUniqueId();
        try {
            PreparedStatement statement = mySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM kozmosbank WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();

            return (results.getInt("balance"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
