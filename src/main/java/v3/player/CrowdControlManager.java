package v3.player;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import v3.Main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CrowdControlManager implements Listener {
    enum CC{
        stunned, slowed
    }
    private static final Multimap<Entity, Integer> ccIDMultimap = ArrayListMultimap.create();
    private static final Multimap<Entity, CC> ccMultimap = ArrayListMultimap.create();
    private static final Map<Entity, Location> stunMap = new HashMap<>();

    public static void CCEntity(Entity entity, CC cc, int durationTicks){
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, () -> ccMultimap.put(entity, cc), 0, 1);

        ccIDMultimap.put(entity, taskID);
        if(cc==CC.stunned) stunMap.put(entity, entity.getLocation());

        Bukkit.getScheduler().runTaskLater(Main.main, () -> {
            Bukkit.getScheduler().cancelTask(taskID);
            ccIDMultimap.remove(entity, taskID);
        }, durationTicks);
    }

    public static void ClearCC(Entity entity){
        ccIDMultimap.get(entity).forEach(Bukkit.getScheduler()::cancelTask);
        ccIDMultimap.removeAll(entity);
    }

    public static void setup(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, () -> {
            for (Entity entity : ccMultimap.keySet()) {
                Collection<CC> ccCollection = ccMultimap.removeAll(entity);
                for (CC cc : ccCollection) {
                    switch (cc){
                        case slowed -> {
                            PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 2, 1, true);
                            effect.apply(((LivingEntity) entity));
                        }
                        case stunned -> entity.teleport(stunMap.get(entity));
                    }
                }
            }
        }, 0, 1);
    }
}
