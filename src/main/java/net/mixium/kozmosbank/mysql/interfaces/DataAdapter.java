package net.mixium.kozmosbank.mysql.interfaces;

import org.bukkit.entity.Player;

public abstract class DataAdapter {

    public abstract void setupSource();

    public abstract void saveData(Player player);

    public abstract void removeData(Player player);


}
