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

    public void removeData(Player player) {
        try(Connection con = this.getConnection()){
            PreparedStatement statement = con.prepareStatement("DELETE FROM kozmosbank WHERE uuid=?");
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
        String host = defaultconfig.getConfig().getString("kozmos-bank.$mysql.address");
        String database = defaultconfig.getConfig().getString("kozmos-bank.$mysql.database");

        // Address
        dbConfig.setJdbcUrl("jdbc:mysql://" + host + "/" + database);

        dbConfig.setUsername(defaultconfig.getConfig().getString("kozmos-bank.$mysql.username"));
        dbConfig.setPassword(defaultconfig.getConfig().getString("kozmos-bank.$mysql.password"));
        dbConfig.setDriverClassName(defaultconfig.getConfig().getString("kozmos-bank.$mysql.driver"));
        dbConfig.setPoolName("kozmosbank@MertGokhan");

        // Encoding
        dbConfig.addDataSourceProperty("characterEncoding", "utf8");
        dbConfig.addDataSourceProperty("encoding", "UTF-8");
        dbConfig.addDataSourceProperty("useUnicode", "true");

        // Request mysql over SSL
        dbConfig.addDataSourceProperty("useSSL", defaultconfig.getConfig().getString("kozmos-bank.$mysql.useSSL"));

        this.hikariDataSource = new HikariDataSource(dbConfig);

        plugin.getLogger().info("Mysql datasource ready!");
    }

    private void checkTables(){
        try(Connection con = this.getConnection(); Statement statement = con.createStatement()){
            String $0sql = "CREATE TABLE IF NOT EXISTS kozmosbank (\n" +
                    "    uuid VARCHAR(255) NOT NULL PRIMARY KEY,\n" +
                    "    player VARCHAR(255) NOT NULL,\n" +
                    "    balance INT NOT NULL,\n" +
                    "    startup_bonus BOOLEAN NOT NULL" +
                    ")  ENGINE=INNODB;";
            statement.executeUpdate($0sql);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

}
