package org.example.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.example.weapons.Weapon;
import org.example.weapons.WeaponManager;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class getitem implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final List<Weapon> weaponList = WeaponManager.getWeaponList();
        weaponList.forEach(weapon -> {
            if (weapon.getId().equalsIgnoreCase(args[0])) {
                ((Player) sender).getInventory().addItem(weapon.getItem());
            }
        });

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        return WeaponManager.getWeaponList().stream().sorted(Comparator.comparing(Weapon::getId)).map(Weapon::getId).collect(Collectors.toList());
    }
}
