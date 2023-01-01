package org.example.weapons;

import org.bukkit.event.player.PlayerInteractEvent;

public interface WeaponRodInterface extends Weapon{
    public void interactEvent(PlayerInteractEvent e);
}
