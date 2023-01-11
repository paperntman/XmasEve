package v2;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class DontSpawnKill implements Listener {

    @EventHandler
    public void onExplosion(BlockExplodeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e){
        e.setCancelled(true);
    }
}
