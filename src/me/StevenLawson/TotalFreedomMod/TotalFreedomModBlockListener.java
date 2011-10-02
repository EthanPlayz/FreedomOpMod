package me.StevenLawson.TotalFreedomMod;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class TotalFreedomModBlockListener extends BlockListener
{
    public static TotalFreedomMod plugin;
    private static final Logger log = Logger.getLogger("Minecraft");

    TotalFreedomModBlockListener(TotalFreedomMod instance)
    {
        plugin = instance;
    }

    @Override
    public void onBlockBurn(BlockBurnEvent event)
    {
        if (!plugin.allowFireSpread)
        {
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event)
    {
        if (!plugin.allowFirePlace)
        {
            event.setCancelled(true);
            return;
        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event)
    {
        try
        {
            if (plugin.nukeMonitor)
            {
                Player p = event.getPlayer();

                Location player_pos = p.getLocation();
                Location block_pos = event.getBlock().getLocation();

                if (player_pos.distance(block_pos) > plugin.nukeMonitorRange)
                {
                    p.setOp(false);
                    p.setGameMode(GameMode.SURVIVAL);
                    p.getInventory().clear();

                    plugin.tfBroadcastMessage(p.getName() + " has been flagged for possible freecam nuking.", ChatColor.RED);

                    event.setCancelled(true);
                    return;
                }

                TFUserInfo playerdata = (TFUserInfo) plugin.userinfo.get(p);
                if (playerdata != null)
                {
                    playerdata.incrementBlockDestroyCount();

                    if (playerdata.getBlockDestroyCount() > plugin.nukeMonitorCount)
                    {
                        plugin.tfBroadcastMessage(p.getName() + " is breaking blocks too fast!", ChatColor.RED);

                        p.setOp(false);
                        p.setGameMode(GameMode.SURVIVAL);
                        p.getInventory().clear();

                        event.setCancelled(true);
                        return;
                    }
                }
                else
                {
                    playerdata = new TFUserInfo();
                    playerdata.incrementBlockDestroyCount();
                    plugin.userinfo.put(p, playerdata);
                }
            }
        }
        catch (Exception ex)
        {
            log.info("Exception in TFM Block Listener onBlockBreak: " + ex.getMessage());
        }
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event)
    {
        ItemStack is = new ItemStack(event.getBlockPlaced().getType(), 1, (short) 0, event.getBlockPlaced().getData());
        Player p = event.getPlayer();

        if (is.getType() == Material.LAVA || is.getType() == Material.STATIONARY_LAVA)
        {
            if (plugin.allowLavaPlace)
            {
                log.info(String.format("%s placed lava @ %s",
                        p.getName(),
                        plugin.formatLocation(event.getBlock().getLocation())));

                p.getInventory().clear(p.getInventory().getHeldItemSlot());
            }
            else
            {
                int slot = p.getInventory().getHeldItemSlot();
                ItemStack heldItem = new ItemStack(Material.COOKIE, 1);
                p.getInventory().setItem(slot, heldItem);

                p.sendMessage(ChatColor.GOLD + "LAVA NO FUN, YOU EAT COOKIE INSTEAD, NO?");

                event.setCancelled(true);
                return;
            }
        }
        else if (is.getType() == Material.WATER || is.getType() == Material.STATIONARY_WATER)
        {
            if (plugin.allowWaterPlace)
            {
                log.info(String.format("%s placed water @ %s",
                        p.getName(),
                        plugin.formatLocation(event.getBlock().getLocation())));

                p.getInventory().clear(p.getInventory().getHeldItemSlot());
            }
            else
            {
                int slot = p.getInventory().getHeldItemSlot();
                ItemStack heldItem = new ItemStack(Material.COOKIE, 1);
                p.getInventory().setItem(slot, heldItem);

                p.sendMessage(ChatColor.GOLD + "Does this look like a waterpark to you?");

                event.setCancelled(true);
                return;
            }
        }
        else if (is.getType() == Material.FIRE)
        {
            if (plugin.allowFirePlace)
            {
                log.info(String.format("%s placed fire @ %s",
                        p.getName(),
                        plugin.formatLocation(event.getBlock().getLocation())));

                p.getInventory().clear(p.getInventory().getHeldItemSlot());
            }
            else
            {
                int slot = p.getInventory().getHeldItemSlot();
                ItemStack heldItem = new ItemStack(Material.COOKIE, 1);
                p.getInventory().setItem(slot, heldItem);

                p.sendMessage(ChatColor.GOLD + "It's gettin (too) hot in here...");

                event.setCancelled(true);
                return;
            }
        }
        else if (is.getType() == Material.TNT)
        {
            if (plugin.allowExplosions)
            {
                log.info(String.format("%s placed TNT @ %s",
                        p.getName(),
                        plugin.formatLocation(event.getBlock().getLocation())));

                p.getInventory().clear(p.getInventory().getHeldItemSlot());
            }
            else
            {
                int slot = p.getInventory().getHeldItemSlot();
                ItemStack heldItem = new ItemStack(Material.COOKIE, 1);
                p.getInventory().setItem(slot, heldItem);

                p.sendMessage(ChatColor.GRAY + "TNT is currently disabled.");

                event.setCancelled(true);
                return;
            }
        }
    }
}
