package ru.ibusewinner.spigot.askme;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AskMe extends JavaPlugin {

    public static FileConfiguration cfg;
    public static AskMe plugin;

    @Override
    public void onEnable() {
        cfg = getConfig();
        loadConfig();
        plugin = this;
        AnsCommand.addlist();
        Bukkit.getPluginManager().registerEvents(new AskMeListener(), this);
        Bukkit.getPluginCommand("ans").setExecutor(new AnsCommand());
        Bukkit.getPluginCommand("ask").setExecutor(new AskCommand());
    }

    @Override
    public void onDisable() {

    }

    public static String getStr(String path) {
        String ret = cfg.getString(path)
                .replaceAll("&", "§")
                .replaceAll("%prefix%", cfg.getString("messages.prefix").replaceAll("&", "§"));
        return ret;
    }

    public static AskMe getInstance() {
        return plugin;
    }

    public FileConfiguration loadConfig() {
        cfg = super.getConfig();
        cfg.options().copyDefaults(true);
        saveConfig();
        Bukkit.getConsoleSender().sendMessage("&8[&a&lAsk&c&lMe&8] &aКонфиг успешно загружен! Перезагрузите сервер, если Вам нужно перезагрузить конфиг.");
        return cfg;
    }

    @Override
    public FileConfiguration getConfig() {
        return cfg;
    }

    @Override
    public void saveConfig() {
        try {
            cfg.save(new File(getDataFolder() + "/config.yml"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //To-Do: reload config method

}
