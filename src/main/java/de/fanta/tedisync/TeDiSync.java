package de.fanta.tedisync;

import de.fanta.tedisync.utils.ChatUtil;
import net.luckperms.api.LuckPerms;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public final class TeDiSync extends Plugin {
    private static TeDiSync plugin;
    public static final String PREFIX = ChatUtil.BLUE + "[" + ChatUtil.GREEN + "TeDiSync" + ChatUtil.BLUE + "]";

    private Configuration config;
    @Override
    public void onEnable() {
        plugin = this;

        try {
            Class.forName(LuckPerms.class.getName());
        } catch (NoClassDefFoundError | ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "Without Luckperms this plugin will not work. Please install Luckperms and restart the server.");
            plugin.getLogger().log(Level.SEVERE, "Shutdown.....");
            plugin.getProxy().stop();
        }

        try {
            saveDefaultConfig();
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        new bStats(this).registerbStats();
        new CommandRegistration(this).registerCommands();
        new EventRegistration(this).registerEvents();

        if (config.getBoolean("teamspeak.enabled")) {
            new TeamSpeakTest(this).initTeamSpeakBot();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TeDiSync getPlugin() {
        return plugin;
    }

    private void saveDefaultConfig() throws IOException {
        File config = new File(getDataFolder(), "config.yml");
        if (config.exists()) {
            return;
        }
        InputStream defaultConfig = getClass().getClassLoader().getResourceAsStream("config.yml");
        getDataFolder().mkdirs();
        Files.copy(defaultConfig, config.toPath());
    }

    public Configuration getConfig() {
        return config;
    }
}
