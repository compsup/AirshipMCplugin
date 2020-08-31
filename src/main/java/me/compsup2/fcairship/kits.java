package me.compsup2.fcairship;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class kits implements CommandExecutor {
    private FCAirship mains;
    public kits(FCAirship fcAirship) {
        this.mains = fcAirship;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        HashMap<Player, UUID> blueTeam = mains.getBlueTeam();
        HashMap<Player, UUID> redTeam = mains.getRedTeam();
        if (cmd.getName().equalsIgnoreCase("bomber")) {
            Player p = (Player) sender;
            Inventory inv = p.getInventory();

            inv.clear();
            ItemStack tnt = new ItemStack(Material.TNT, 64);
            ItemStack sword = new ItemStack(Material.STONE_SWORD, 1);
            ItemStack bucket = new ItemStack(Material.WATER_BUCKET, 1);
            ItemStack repeater = new ItemStack(Material.REPEATER, 64);
            ItemStack wood_plank = new ItemStack(Material.BIRCH_PLANKS, 64);
            ItemStack diamond_pickaxe = new ItemStack(Material.DIAMOND_PICKAXE, 1);
            ItemStack redstone = new ItemStack(Material.REDSTONE, 64);
            ItemStack button = new ItemStack(Material.STONE_BUTTON, 64);
            ItemStack sheild = new ItemStack(Material.SHIELD, 1);
            //ItemStack helmet = new ItemStack(); Is done in the main file because of team reasons.
            ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
            ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS, 1);

            inv.addItem(tnt);

        }

        if (cmd.getName().equalsIgnoreCase("archer")) {
            Player p = (Player) sender;
            Inventory inv = p.getInventory();

        }

        if (cmd.getName().equalsIgnoreCase("melee")) {
            Player p = (Player) sender;
            Inventory inv = p.getInventory();

        }

        return true;

    }
}
