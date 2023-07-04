package net.mixium.kozmosbank.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.mixium.kozmosbank.KozmosBank;
import net.mixium.kozmosbank.files.defaultconfig;
import net.mixium.kozmosbank.mysql.interfaces.DataAdapter;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class MySqlConnector extends DataAdapter {
    private final KozmosBank plugin;
    private static HikariDataSource hikariDataSource;

    public MySqlConnector(KozmosBank plugin) {
        this.plugin = plugin;
    }


    @Override
    public void setupSource() {
        this.setupConnection();
        this.checkTables();
    }

    @Override
    public void saveData(Player player) {
        try(Connection con = this.getConnection()){
            String sql = "INSERT INTO kozmosbank(uuid, player, balance) VALUES (?,?,?) ON DUPLICATE KEY UPDATE player=player,balance=balance;";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1,player.getUniqueId().toString());
            statement.setString(2, player.getName().toString());
            statement.setInt(3,0);

            statement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void removeData(Player player) {
        try(Connection con = this.getConnection()){
            PreparedStatement statement = con.prepareStatement("DELETE FROM kozmosbank WHERE id=?");
            statement.setString(1,player.getUniqueId().toString());
            statement.executeUpdate();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }


    private void setupConnection(){
        HikariConfig dbConfig = new HikariConfig();
        String host = defaultconfig.getConfig().getString("kozmosbank.$mysql.address");
        String database = defaultconfig.getConfig().getString("kozmosbank.$mysql.database");

        // Address
        dbConfig.setJdbcUrl("jdbc:mysql://" + host + "/" + database);

        dbConfig.setUsername(defaultconfig.getConfig().getString("kozmosbank.$mysql.username"));
        dbConfig.setPassword(defaultconfig.getConfig().getString("kozmosbank.$mysql.password"));
        dbConfig.setDriverClassName(defaultconfig.getConfig().getString("kozmosbank.$mysql.driver"));
        dbConfig.setPoolName("kozmosbank@Mixeration");

        // Encoding
        dbConfig.addDataSourceProperty("characterEncoding", "utf8");
        dbConfig.addDataSourceProperty("encoding", "UTF-8");
        dbConfig.addDataSourceProperty("useUnicode", "true");

        // Request mysql over SSL
        dbConfig.addDataSourceProperty("useSSL", defaultconfig.getConfig().getString("kozmosbank.$mysql.useSSL"));

        this.hikariDataSource = new HikariDataSource(dbConfig);

        plugin.getLogger().info("Mysql datasource ready!");
    }

    private void checkTables(){
        try(Connection con = this.getConnection(); Statement statement = con.createStatement()){
            String $0sql = "CREATE TABLE IF NOT EXISTS kozmosbank (\n" +
                    "    uuid VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                    "    player VARCHAR(255) NOT NULL,\n" +
                    "    coin INT NOT NULL,\n" +
                    "    lastplay INT NOT NULL,\n" +
                    "    firstplay INT" +
                    ")  ENGINE=INNODB;";
            String $1sql = "CREATE TABLE IF NOT EXISTS phytData (\n" +
                    "    world VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                    "    x VARCHAR(255) NOT NULL,\n" +
                    "    y VARCHAR(255) NOT NULL,\n" +
                    "    z VARCHAR(255) NOT NULL,\n" +
                    "    yaw VARCHAR(255),\n" +
                    "    pitch VARCHAR(255) NOT NULL,\n" +
                    "    phytid VARCHAR(255) NOT NULL" +
                    ")  ENGINE=INNODB;";
            statement.executeUpdate($0sql);
            statement.executeUpdate($1sql);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static boolean playerExists(UUID uuid) {
        try {
            PreparedStatement statement = hikariDataSource.getConnection()
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

}
