package me.compsup2.fcairship;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class FCAirship extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("bomber").setExecutor(new kits(this));
        // Plugin startup logic
        this.getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        boolean enabled = getConfig().getBoolean("enabled");
        if (!enabled) {
            getLogger()
                .info(
                    ChatColor.DARK_RED +
                    "This plugin has been disabled because an admin disabled it in the config.yml"
                );
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            getLogger()
                .info(
                    ChatColor.BLUE +
                    "\n\nAirship MiniGame Enabled!\n" +
                    ChatColor.GOLD +
                    "For support contact me in discord(compsup2#5005)\n\n"
                );
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    //<editor-fold>
    HashMap<Player, UUID> blueTeam = new HashMap<>();
    HashMap<Player, UUID> redTeam = new HashMap<>();
    public HashMap<Player, UUID> getBlueTeam() {
        return blueTeam;
    }
    public HashMap<Player, UUID> getRedTeam() {
        return redTeam;
    }

    String game_starting_message = getConfig().getString("game-starting-message");
    Integer time_to_game_start = getConfig().getInt("time-to-game-start");
    Location loc_red = new Location(Bukkit.getWorld("world"), 0, 0, -0, 0, 0);
    Location loc_blue = new Location(Bukkit.getWorld("world"), -0, 0, -0, 0, 0);

    //</editor-fold>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You must be a player to use that command!");
                return true;
            }
            Player p = (Player) sender;
            Bukkit.broadcastMessage(ChatColor.GOLD + game_starting_message + time_to_game_start);

            new BukkitRunnable() {

                @Override
                public void run() {
                    boolean add_to_blue = true;
                    for (Player online_player : getServer().getOnlinePlayers()) {
                        if (add_to_blue) {
                            blueTeam.put(online_player, online_player.getUniqueId());
                            online_player.sendMessage("You have been put on the blue team!");
                            add_to_blue = false;
                        } else {
                            redTeam.put(online_player, online_player.getUniqueId());
                            online_player.sendMessage("You have been put on the red team!");
                            add_to_blue = true;
                        }
                    }
                }
            }.runTaskLaterAsynchronously(this, time_to_game_start * 20);
            // Set up teams and make them even
        }
        return true;
    }

    @EventHandler
    // Check if the player has broken the obsidian. If so then end the game.
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Material m = e.getBlock().getType();
        if (m.equals(Material.OBSIDIAN)) {
            if (blueTeam.get(player) == player.getUniqueId()) {
                Bukkit.broadcastMessage(
                    ChatColor.RED +
                    player.getDisplayName() +
                    ChatColor.GOLD +
                    " has destroyed the enemy's core!" +
                    ChatColor.BLUE +
                    "Blue Team " +
                    ChatColor.GOLD +
                    "has won!"
                );
            } else {
                Bukkit.broadcastMessage(
                    ChatColor.RED +
                    player.getDisplayName() +
                    ChatColor.GOLD +
                    " has destroyed the enemy's core!" +
                    ChatColor.RED +
                    "Red Team " +
                    ChatColor.GOLD +
                    "has won!"
                );
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (blueTeam.get(p) == p.getUniqueId()) {
            p.teleport(loc_blue);
            // Send player to there spawn
        } else {
            p.teleport(loc_red);
            // Send player to there spawn
        }
    }

    @EventHandler
    // If someone dies then respawn them without the death screen.
    // Respawn code
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();
        p.spigot().respawn();
        Inventory inv = p.getInventory();
        inv.clear();
        p.sendMessage("You died!");
        Bukkit.broadcastMessage(
                p.getDisplayName() + "has been killed by " + killer.getDisplayName()
        );
    }
}
