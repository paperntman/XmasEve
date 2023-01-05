package topen.commonSkill.list.Necromancy;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Player;
import topen.commonSkill.iActiveSkill;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class shadowChange implements iActiveSkill {

    Map<UUID, Entity> list = new HashMap<>();

    @Override
    public int manaNeeded() {
        return 50;
    }

    @Override
    public void onUse(Player p) {
        if(list.get(p.getUniqueId()) == null || list.get(p.getUniqueId()).isDead()){
            list.remove(p.getUniqueId());
            final Husk entity = (Husk) p.getWorld().spawnEntity(p.getLocation(), EntityType.HUSK);
            entity.setAI(false);
            entity.setSilent(true);
            list.put(p.getUniqueId(), entity);
        }else{
            Temp temp = new Temp(p);
            final Entity shadow = list.get(p.getUniqueId());
            p.teleport(shadow.getLocation());
            p.setFallDistance(shadow.getFallDistance());
            shadow.teleport(temp.getLocation());
            shadow.setFallDistance(temp.fallDistance);
        }
    }

    private class Temp{
        Location location;
        float fallDistance;

        public Temp(Entity entity) {
            this.location = entity.getLocation();
            this.fallDistance = entity.getFallDistance();
        }

        public Location getLocation() {
            return location;
        }

        public float getFallDistance() {
            return fallDistance;
        }
    }

    @Override
    public char getRank() {
        return 'A';
    }

    @Override
    public String getName() {
        return "그림자 교환";
    }

    @Override
    public String getId() {
        return "shadow-change";
    }

    @Override
    public String getDescription() {
        return "그림자가 없을 시 자신의 자리에 그림자를 소환합니다.\n그림자가 있을 시, 그림자와 위치를 교환합니다.";
    }
}
