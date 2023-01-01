package topen.weapon;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.HashSet;
import java.util.Set;

public class EnableManager implements Listener {
    public static Set<iWeapon> weaponSet = new HashSet<>();

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent e){
        weaponSet.clear();

        e.getPlayer().getServer().getOnlinePlayers().forEach(player ->
                weaponSet.add(WeaponManager.getWeaponAsItem(player.getInventory().getItemInMainHand())));
    }
}
