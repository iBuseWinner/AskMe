package ru.ibusewinner.spigot.askme;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AnsCommand implements CommandExecutor {

    public static ArrayList<Player> helpers = new ArrayList<>();
    private static ArrayList<String> usage = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if(!(s instanceof Player)) {
            return false;
        } else {
            Player p = (Player)s;

            if(!p.hasPermission("askme.ans")) {
                p.sendMessage(AskMe.getStr("messages.no-perm"));
                return false;
            }

            if(a.length < 1) {
                for(int i = 0; i < usage.size(); i++) {
                    p.sendMessage(usage.get(i)
                            .replaceAll("&", "§"));
                }
                return false;
            } else {
                if(a[0].equalsIgnoreCase("join")) {
                    p.sendMessage(AskMe.getStr("messages.join-helper"));
                    Bukkit.broadcastMessage(AskMe.getStr("messages.join-server")
                            .replaceAll("%helper%", p.getName()));
                    helpers.add(p);
                } else if(a[0].equalsIgnoreCase("quit")) {
                    p.sendMessage(AskMe.getStr("messages.quit-helper"));
                    Bukkit.broadcastMessage(AskMe.getStr("messages.quit-server")
                            .replaceAll("%helper%", p.getName()));
                    helpers.remove(p);
                } else {
                    if(a.length < 2) {
                        for(int i = 0; i < usage.size(); i++) {
                            p.sendMessage(usage.get(i)
                                    .replaceAll("&", "§"));
                        }
                    } else {
                        if(!helpers.contains(p)) {
                            p.sendMessage(AskMe.getStr("messages.arent-work"));
                            return false;
                        }

                        try {
                            Player ask = Bukkit.getPlayer(a[0]);

                            String ans = "";

                            for(int i = 1; i < a.length; i++) {
                                ans = ans+" "+a[i];
                            }

                            ask.sendMessage(AskMe.getStr("messages.answer")
                                    .replaceAll("%helper%", p.getName())
                                    .replaceAll("%player%", ask.getName())
                                    .replaceAll("%message%", ans));

                            for(Player pp : Bukkit.getOnlinePlayers()) {
                                if(helpers.contains(pp)) {
                                    pp.sendMessage(AskMe.getStr("messages.answer")
                                            .replaceAll("%helper%", p.getName())
                                            .replaceAll("%player%", ask.getName())
                                            .replaceAll("%message%", ans));
                                }
                            }
                        } catch (Exception ex) {
                            p.sendMessage(AskMe.getStr("messages.player-not-found"));
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void addlist() {
        usage.addAll(AskMe.cfg.getStringList("messages.ans-usage"));
    }

}
