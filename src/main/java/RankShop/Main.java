package RankShop;

import RankShop.commands.RankShop;
import RankShop.events.onResponded;
import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.Config;

import java.io.File;

public class Main extends PluginBase {

    private static Main plugin;
    private Config api;


    @Override
    public void onEnable() {
        plugin = this;
        Main.getPlugin().saveResource("/data/api.yml");
        Config cfg = new Config(new File(Main.getPlugin().getDataFolder(), "api.yml"));
        this.saveDefaultConfig();
        Server.getInstance().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int currentTick) {
                if (Server.getInstance().getPluginManager().getPlugin("Multipass") == null && Server.getInstance().getPluginManager().getPlugin("LuckPerms") != null || Server.getInstance().getPluginManager().getPlugin("Multipass") != null && Server.getInstance().getPluginManager().getPlugin("LuckPerms") == null){
                    if (Server.getInstance().getPluginManager().getPlugin("Multipass") != null){
                        cfg.set("API.MultiPass.use", true);
                        cfg.save();
                    }
                    if (Server.getInstance().getPluginManager().getPlugin("LuckPerms") != null){
                        cfg.set("API.LuckPerms.use", true);
                        cfg.save();
                    }
                }else{
                    Server.getInstance().getLogger().critical("§cPlease install all required Plugins you forgot (LuckPerms or MultiPass) or You have MultiPass and LuckPerms installed!");
                    Server.getInstance().getPluginManager().getPlugin("RankShop").onDisable();
                }
                if (Server.getInstance().getPluginManager().getPlugin("EconomyAPI") == null && Server.getInstance().getPluginManager().getPlugin("LlamaEconomy") != null || Server.getInstance().getPluginManager().getPlugin("EconomyAPI") != null && Server.getInstance().getPluginManager().getPlugin("LlamaEconomy") == null){
                    if (Server.getInstance().getPluginManager().getPlugin("EconomyAPI") != null){
                        cfg.set("API.EconomyAPI.use", true);
                        cfg.save();
                        getServer().getLogger().info(plugin.getConfig().getString("options.messages.prefix") + " " + plugin.getConfig().getString("options.messages.plugin-activate"));
                    }
                    if (Server.getInstance().getPluginManager().getPlugin("LlamaEconomy") != null){
                        cfg.set("API.LlamaEconomy.use", true);
                        cfg.save();
                        getServer().getLogger().info(plugin.getConfig().getString("options.messages.prefix") + " " + plugin.getConfig().getString("options.messages.plugin-activate"));
                    }
                }else{
                    Server.getInstance().getLogger().critical("§cPlease install all required Plugins you forgot (EconomyAPI or LlamaEconomy) or You have EconomyAPI and LlamaEconomy installed!");
                    Server.getInstance().getPluginManager().getPlugin("RankShop").onDisable();
                }
                Main.getPlugin().saveResource("/data/api.yml");
                plugin.saveDefaultConfig();
                plugin.saveDefaultConfig();
                getServer().getCommandMap().register("help", new RankShop(getPlugin().getConfig().getString("Command-options.name"), getPlugin().getConfig().getString("Command-options.description"), getPlugin().getConfig().getString("Commands-options.usagemessage"), new String[]{}));
                getServer().getPluginManager().registerEvents(new onResponded(), plugin);
            }
        }, 5);

    }

    @Override
    public void onDisable() {
        this.getServer().getLogger().info(plugin.getConfig().getString("options.messages.prefix") + " " + plugin.getConfig().getString("options.messages.plugin-deactivate"));
    }

    public static Main getPlugin() {
        return plugin;
    }
}
