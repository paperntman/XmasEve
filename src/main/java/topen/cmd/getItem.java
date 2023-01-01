package topen.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import topen.weapon.WeaponManager;
import topen.weapon.iWeapon;

import java.util.List;

public class getItem implements TabCompleter, CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            final iWeapon weaponAsId = WeaponManager.getWeaponAsId(args[0]);
            if (weaponAsId != null) {
                player.getInventory().addItem(weaponAsId.getItem());
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return WeaponManager.getWeaponSet().stream().map(iWeapon::getId).toList();
    }
}
