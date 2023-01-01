package org.example.weapons;

import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface WeaponSwordInterface extends Weapon{
    public void hitEvent(EntityDamageByEntityEvent e);
}
