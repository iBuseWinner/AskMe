package ru.ibusewinner.spigot.askme;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AskCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if(!(s instanceof Player)) {
            s.sendMessage(AskMe.getStr("messages.player-only"));
            return false;
        }

        Player p = (Player)s;

        if(!p.hasPermission("askme.ask")) {
            p.sendMessage(AskMe.getStr("messages.no-perm"));
            return false;
        }

        if(a.length < 1) {
            p.sendMessage(AskMe.getStr("messages.ask-usage"));
            return false;
        }

        String msg = "";
        for(int i = 0; i < a.length; i++) {
            msg = msg + " " + a[i];
        }

        for(Player pp : Bukkit.getOnlinePlayers()) {
            if(AnsCommand.helpers.contains(pp)) {
                pp.sendMessage(AskMe.getStr("messages.question")
                        .replaceAll("%player%", p.getName())
                        .replaceAll("%message%", msg));
            }
        }

        return true;
    }
}
