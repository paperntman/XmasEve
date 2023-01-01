package org.example.weapons;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public interface WeaponBowInterface extends Weapon{
    public void useEvent(EntityShootBowEvent e);
    public void hitEvent(EntityDamageByEntityEvent e);
}
