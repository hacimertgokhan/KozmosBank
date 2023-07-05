package net.mixium.kozmosbank.mysql.manager;

import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.mysql.MySqlConnector;
import net.mixium.kozmosbank.mysql.interfaces.DataAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class DataSQL {

    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM kozmosbank WHERE UUID=?");
            statement.setString(1, uuid.toString());

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated
    public static void createPlayer(final UUID uuid, Player player) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("SELECT * FROM kozmosbank WHERE uuid=?");
            statement.setString(1, uuid.toString());
            ResultSet results = statement.executeQuery();
            results.next();
            System.out.print(1);
            Date date = new Date();
            String toDay = date.getMinutes()+":"+date.getHours()+":"+date.getDay()+"/"+date.getMonth()+"/"+date.getYear();
            if (playerExists(uuid) != true) {
                PreparedStatement insert = MySqlConnector.getConnection()
                        .prepareStatement("INSERT INTO kozmosbank (uuid,player,balance,startup_bonus) VALUES (?,?,?,?)");
                insert.setString(1, uuid.toString());
                insert.setString(2, player.getName());
                insert.setInt(3, 0);
                insert.setBoolean(4, false);
                insert.executeUpdate();
                if (defaultconfig.getConfig().getBoolean("kozmos-bank.console.data-created")) {
                    Bukkit.getLogger().warning("[KozmosBank] Data created for " + player.getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetBalance(UUID uuid) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE kozmosbank SET balance=? WHERE uuid=?");
            statement.setInt(1, 0);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setBalance(UUID uuid, int amount) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE kozmosbank SET balance=? WHERE uuid=?");
            statement.setInt(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void setStartupBalance(UUID uuid, boolean bool) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE kozmosbank SET startup_bonus=? WHERE uuid=?");
            statement.setBoolean(1, bool);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addBalance(Player player, int val) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE kozmosbank SET balance=? WHERE uuid=?");
            statement.setInt(1, (DataPlayer.getPlayerBalance(player) + val));
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void removeBalance(Player player, int val) {
        try {
            PreparedStatement statement = MySqlConnector.getConnection()
                    .prepareStatement("UPDATE kozmosbank SET balance=? WHERE uuid=?");
            statement.setInt(1, (DataPlayer.getPlayerBalance(player) - val));
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
