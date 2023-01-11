package v3.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DamageManager implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player player){
            TopenPlayer topenPlayer = PlayerManager.getPlayer(player);
            topenPlayer.damage(e.getDamage());
            e.setDamage(0);
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player player){
            TopenPlayer topenPlayer = PlayerManager.getPlayer(player);
            topenPlayer.heal(e.getAmount());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        TopenPlayer topenPlayer = PlayerManager.getPlayer(e.getEntity());
        topenPlayer.damage(Double.POSITIVE_INFINITY);
    }

    @EventHandler
    public void onPlayerRevive(PlayerRespawnEvent e){
        TopenPlayer topenPlayer = PlayerManager.getPlayer(e.getPlayer());
        topenPlayer.heal(Double.POSITIVE_INFINITY);
    }



    public static void updateHeart(Entity entity){
        if(entity instanceof Player player){
            TopenPlayer topenPlayer = PlayerManager.getPlayer(player);
            player.setAbsorptionAmount(topenPlayer.getAbsorption() * topenPlayer.getAbsorption() / topenPlayer.getMaxHp());
            player.setHealth(20 * topenPlayer.getHp() / topenPlayer.getMaxHp());
        }
    }
}
