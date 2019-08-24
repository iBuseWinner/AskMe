package ru.ibusewinner.spigot.askme;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AskMeListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if(AnsCommand.helpers.contains(e.getPlayer())) {
            AnsCommand.helpers.remove(e.getPlayer());
        }
    }

    public static HashMap<UUID, ItemStack> items = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        List<ItemStack> drops = e.getDrops();

        for(int i = 0; i < drops.size(); i++) {
            List<String> lore = drops.get(i).getItemMeta().getLore();

            if(lore.contains("§5Невыпадаемый")) {
                items.put(e.getEntity().getUniqueId(), drops.get(i));
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        Inventory inv = p.getInventory();

        for(int i = 0; i < items.size(); i++) {
            if(items.containsKey(p.getUniqueId())) {
                inv.addItem(items.get(i));
                items.remove(i);
            }
        }

    }

}
