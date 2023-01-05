package topen;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageManager implements Listener {
    public static void changeHealth(TopenPlayer topenPlayer, double v) {
        topenPlayer.setHealth(Math.max(topenPlayer.getHealth()+v, 0));
        final Player player = Bukkit.getPlayer(topenPlayer.UUID);
        assert player != null;
        System.out.println(topenPlayer.getHealth() + " " + topenPlayer.getMaxHealth());
        player.setHealth(20 * topenPlayer.getHealth() / topenPlayer.getMaxHealth());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player player){
            final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
            changeHealth(topenPlayer, -e.getDamage());
            e.setDamage(0);
        }
    }

    @EventHandler
    public void onHeal(EntityRegainHealthEvent e){
        if(e.getEntity() instanceof Player player){
            final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
            changeHealth(topenPlayer, e.getAmount());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(e.getEntity().getUniqueId());
        topenPlayer.setHealth(topenPlayer.getMaxHealth());
        topenPlayer.setMp(topenPlayer.getMaxMp());
    }
}
