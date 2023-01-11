package v2.commonSkill;

import org.bukkit.entity.Player;

public interface iActiveSkill extends iSkill{
    public int manaNeeded();
    public void onUse(Player p);
}
