package v2.commonSkill.list;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import v2.commonSkill.iActiveSkill;

import java.util.Arrays;
import java.util.Collection;

public class Heal implements iActiveSkill {
    @Override
    public char getRank() {
        return 'D';
    }

    @Override
    public String getName() {
        return "힐";
    }

    @Override
    public String getId() {
        return "heal";
    }

    @Override
    public String getDescription() {
        return "가장 가까운 아군의 체력을 회복합니다.";
    }


    @Override
    public int manaNeeded() {
        return 65;
    }

    @Override
    public void onUse(Player p) {

        final Collection<Entity> nearbyEntities = p.getWorld().getNearbyEntities(p.getLocation(), 5, 5, 5);
        System.out.println(Arrays.toString(nearbyEntities.toArray()));
    }
}
