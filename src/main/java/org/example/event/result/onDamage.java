package org.example.event.result;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class onDamage {
    public void event(EntityDamageEvent e) {
        if(e.getEntity() instanceof final Player player){
            player.setNoDamageTicks(0);
        }
    }
}
