package topen.Skill.list;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import topen.Main;
import topen.Skill.iActiveSkill;

public class swordsman_skill implements iActiveSkill {
    @Override
    public void onUse(Player p) {
        final AttributeInstance attribute = p.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        final double defaultValue = attribute.getValue();
        attribute.setBaseValue(attribute.getBaseValue()+defaultValue/2);
        Bukkit.getScheduler().runTaskLater(Main.main, () ->
                attribute.setBaseValue(attribute.getBaseValue()-defaultValue/2), 100);
    }

    @Override
    public char getRank() {
        return 'D';
    }

    @Override
    public String getName() {
        return "검사의 재능";
    }

    @Override
    public String getId() {
        return "swordsman-skill";
    }

    @Override
    public String getDescription() {
        return "공격 속도가 5초간 50% 증가합니다.";
    }
}
