package RankShop.events;

import RankShop.Main;
import cn.nukkit.IPlayer;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;
import net.lldv.llamaeconomy.LlamaEconomy;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class onResponded implements Listener {

    String rank1;
    public static Config cfg = new Config(new File(Main.getPlugin().getDataFolder(), "api.yml"));

    @EventHandler
    public void onResponded(PlayerFormRespondedEvent event){
        Player player = event.getPlayer();
        if (!event.wasClosed()){
            if (event.getWindow() instanceof FormWindowSimple){
                FormWindowSimple getUi = (FormWindowSimple) event.getWindow();
                FormResponseSimple getRs = (FormResponseSimple) event.getResponse();
                if (getUi.getTitle().equalsIgnoreCase(Main.getPlugin().getConfig().getString("UI.Title"))){
                    String button = getRs.getClickedButton().getText();
                    int buttonID = getRs.getClickedButtonId();
                    List<String> ranks = Main.getPlugin().getConfig().getStringList("RankList");
                    Iterator iterator = ranks.iterator();
                    while (iterator.hasNext()){
                        String rank = (String) iterator.next();
                        if (button.equalsIgnoreCase("§e" + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name") + "\n" + Main.getPlugin().getConfig().getString("UI.Button"))){
                            FormWindowSimple ui = new FormWindowSimple("» §e" + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name"), Main.getPlugin().getConfig().getString("RankUI.Content") + "§e" + Main.getPlugin().getConfig().getString("Ranks." + rank + ".cost") + "!");
                            if (Main.getPlugin().getConfig().getBoolean("RankUI.ButtonBuyPicture") == false){
                                ui.addButton(new ElementButton(Main.getPlugin().getConfig().getString("RankUI.ButtonBuy")));
                            }else{
                                ui.addButton(new ElementButton(Main.getPlugin().getConfig().getString("RankUI.ButtonBuy"), new ElementButtonImageData("path", Main.getPlugin().getConfig().getString("RankUI.ButtonBuyPictureData"))));
                            }
                            if (Main.getPlugin().getConfig().getBoolean("RankUI.ButtonBackPicture") == false){
                                ui.addButton(new ElementButton(Main.getPlugin().getConfig().getString("RankUI.ButtonBack")));
                                player.showFormWindow(ui);
                            }else{
                                ui.addButton(new ElementButton(Main.getPlugin().getConfig().getString("RankUI.ButtonBack"), new ElementButtonImageData("path", Main.getPlugin().getConfig().getString("RankUI.ButtonBackPictureData"))));
                                player.showFormWindow(ui);
                            }
                        }
                    }
                }
                int buttonID = getRs.getClickedButtonId();
                List<String> ranks = Main.getPlugin().getConfig().getStringList("RankList");
                Iterator iterator = ranks.iterator();
                while (iterator.hasNext()) {
                    String rank = (String) iterator.next();
                    if (getUi.getTitle().equalsIgnoreCase("» §e" + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name"))) {
                        String button = getRs.getClickedButton().getText();
                        if (button.equalsIgnoreCase(Main.getPlugin().getConfig().getString("RankUI.ButtonBuy"))) {
                            if (cfg.getBoolean("API.LlamaEconomy.use") == true) {
                                if (LlamaEconomy.getAPI().getMoney(player) >= Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost")) {
                                    if (cfg.getBoolean("API.LuckPerms.use") == true) {
                                        LlamaEconomy.getAPI().reduceMoney(player, Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost"));
                                        Server.getInstance().dispatchCommand(Main.getPlugin().getServer().getConsoleSender(), "lp user " + player.getName() + " parent add " + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name"));
                                        player.sendMessage("hi");
                                    } else if (cfg.getBoolean("API.MultiPass.use") == true) {
                                        LlamaEconomy.getAPI().reduceMoney(player, Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost"));
                                        player.sendMessage(Main.getPlugin().getConfig().getString("Command-options.messages.HaveBuyARank"));
                                        Server.getInstance().dispatchCommand(Main.getPlugin().getServer().getConsoleSender(), "user " + player.getName() + " setgroup " + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name"));
                                    }
                                } else {
                                    double a = Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost") - LlamaEconomy.getAPI().getMoney(player);
                                   player.sendMessage(Main.getPlugin().getConfig().getString("Command-options.messages.HaveNotEnoughMoney") + "§e " + a + " Dollars!");
                                }
                            }else if (cfg.getBoolean("API.EconomyAPI.use") == true) {

                                if (EconomyAPI.getInstance().myMoney(player) >= Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost")) {
                                    if (cfg.getBoolean("API.LuckPerms.use") == true) {
                                        LlamaEconomy.getAPI().reduceMoney(player, Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost"));
                                        Server.getInstance().dispatchCommand(Main.getPlugin().getServer().getConsoleSender(), "lp user " + player.getName() + " parent add " + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name"));
                                    } else if (cfg.getBoolean("API.MultiPass.use") == true) {
                                        EconomyAPI.getInstance().reduceMoney(player, Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost"));
                                        player.sendMessage(Main.getPlugin().getConfig().getString("Command-options.messages.HaveBuyARank"));
                                        Server.getInstance().dispatchCommand(Main.getPlugin().getServer().getConsoleSender(), "user " + player.getName() + " setgroup " + Main.getPlugin().getConfig().getString("Ranks." + rank + ".name"));
                                    }
                                } else {
                                    double a = Main.getPlugin().getConfig().getInt("Ranks." + rank + ".cost") - EconomyAPI.getInstance().myMoney(player);
                                    player.sendMessage(Main.getPlugin().getConfig().getString("Command-options.messages.HaveNotEnoughMoney") + "§e " + a + " Dollars!");
                                }
                            }
                        }
                    }
                }
            }
        }else{
            return;
        }
    }
}
