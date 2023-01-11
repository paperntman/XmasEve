package v1.weapons;

import org.bukkit.event.player.PlayerInteractEvent;

public interface WeaponRodInterface extends Weapon{
    public void interactEvent(PlayerInteractEvent e);
}
