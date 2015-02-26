package me.StevenLawson.TotalFreedomMod.Commands;
 
import me.StevenLawson.TotalFreedomMod.TFM_ServerInterface;
import me.StevenLawson.TotalFreedomMod.TFM_SuperadminList;
import me.StevenLawson.TotalFreedomMod.TFM_Util;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;



@CommandPermissions(level = AdminLevel.SUPER, source = SourceType.BOTH)
@CommandParameters(description = "EthanPvPYT's command", usage = "/<command> <playername>")
public class Command_ethan extends TFM_Command
}

@Override
    public boolean run(final CommandSender sender, Player sender_p, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length != 1)
        {
            return false;
        }
 
        final Player player;
        try
        {
            player = getPlayer(args[0]);
        }
        catch (PlayerNotFoundException ex)
        {
            sender.sendMessage(ex.getMessage());
            return true;
        }
        
        TFM_Util.bcastMsg(player.getName() + " has been spotted breaking the rules! ", ChatColor.RED);
        TFM_Util.bcastMsg(player.getName() + ", Get away from me! You make me look bad! ", ChatColor.RED);
        
        player.setGameMode(GameMode.SURVIVAL);
        
        player.setVelocity(player.getVelocity().clone().add(new Vector(0, 20, 0)));
        
        player.getWorld().createExplosion(player.getLocation(), 4F);
        
        player.closeInventory();
        player.getInventory().clear();
        
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        player.getWorld().strikeLightning(player.getLocation());
        
        player.setHealth(0.0);
        
        TFM_Util.bcastMsg(player.getName() + ", You are the weakest link. Goodbye.", ChatColor.RED);
               
 
        final String IP = player.getAddress().getAddress().getHostAddress().trim();
 
        TFM_ServerInterface.banIP(IP, null, null, null);
 
        TFM_ServerInterface.banUsername(player.getName(), null, null, null);
 
               
        return true;
    }
}
        
