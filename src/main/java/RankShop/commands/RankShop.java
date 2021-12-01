package RankShop.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import RankShop.Main;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import ru.nukkit.multipass.Multipass;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class RankShop extends Command {

    public static String prefix = Main.getPlugin().getConfig().getString("options.messages.prefix");
    public static Config cfg = new Config(new File(Main.getPlugin().getDataFolder(), "api.yml"));

    public RankShop(String name, String description, String usageMessage, String[] aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player){
            if (!sender.hasPermission("rankshop.buy.not")) {
                if (args.length == 0) {
                    if (cfg.get("API.EconomyAPI.use") == null && cfg.get("API.LlamaEconomy.use") == null) {
                        sender.sendMessage("§f[§4ALERT§f] | Please restart this Server or reload! api.yml == null!");
                    } else {
                        FormWindowSimple ui = new FormWindowSimple(Main.getPlugin().getConfig().getString("UI.Title"), Main.getPlugin().getConfig().getString("UI.Content"));
                        List<String> RankList = Main.getPlugin().getConfig().getStringList("RankList");
                        Iterator two = RankList.iterator();
                        while (two.hasNext()) {
                            String rank = (String) two.next();
                            if (Main.getPlugin().getConfig().getBoolean("Ranks." + rank + ".picture") == false) {
                                ui.addButton(new ElementButton("§e" + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name") + "\n" + Main.getPlugin().getConfig().getString("UI.Button")));
                            } else if (Main.getPlugin().getConfig().getBoolean("Ranks." + rank + ".picture") == true) {
                                ui.addButton(new ElementButton("§e" + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name") + "\n" + Main.getPlugin().getConfig().getString("UI.Button"), new ElementButtonImageData("path", Main.getPlugin().getConfig().getString("Ranks." + rank + ".pictureData"))));
                            }
                        }
                        ((Player) sender).showFormWindow(ui);
                    }
                } else {
                    sender.sendMessage(prefix + Main.getPlugin().getConfig().getString("Command-options.usagemessage"));
                }
            }else{
                sender.sendMessage(prefix + Main.getPlugin().getConfig().getString("Command-options.messages.HasPermission"));
            }
        }else {
            sender.sendMessage(prefix + Main.getPlugin().getConfig().getString("Command-options.messages.IsNotAPlayer"));
        }
        return true;
    }
}
