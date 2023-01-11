package v3.skill.list;

import org.bukkit.entity.Player;
import v3.player.PlayerManager;
import v3.player.TopenPlayer;
import v3.skill.ActiveSkill;

public class Shield implements ActiveSkill {
    @Override
    public void onUse(Player player) {
        TopenPlayer topenPlayer = PlayerManager.getPlayer(player);
        topenPlayer.addAbsorption(topenPlayer.getMaxHp() / 10);
    }

    @Override
    public String getID() {
        return "shield";
    }

    @Override
    public String getName() {
        return "쉴드";
    }

    @Override
    public String getRank() {
        return "D";
    }

    @Override
    public String getDescription() {
        return "사용자의 체력의 10%에 비례한 방어막을 얻습니다.";
    }

    @Override
    public String toString() {
        return getID();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
