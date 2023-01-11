package v1.event;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import v1.event.result.*;

public class EventListener implements Listener {
    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e){
        new onHit().event(e);
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent e){new onBow().event(e);}

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        new onInteract().event(e);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        new onDamage().event(e);
    }

    @EventHandler
    public void onHotbar(PlayerItemHeldEvent e){
        new hotbarManager().event(e);
    }

    @EventHandler
    public void onBlockExplode(BlockExplodeEvent e){
        for (Block block : e.blockList()) {
            if(block.getType().toString().toLowerCase().contains("glass")){
                block.getLocation().getBlock().setType(block.getType());
            }
        }
    }
}
