package topen.Skill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import topen.PlayerManager;
import topen.TopenPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkillBookImporter implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        final Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand().hasItemMeta()){
            String displayName = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if(displayName.startsWith(ChatColor.LIGHT_PURPLE+"")){
                String lore = p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0);
                Pattern pattern = Pattern.compile("^[A-Z]급\\s스킬\\s(.*?)을[(]를[)] 배울 수 있는 책입니다[.]");
                Matcher matcher = pattern.matcher(lore);
                if(matcher.find()){
                    final TopenPlayer player = PlayerManager.getPlayer(p.getUniqueId());
                    final iSkill skill = SkillManager.getSkillByName(matcher.group(1));
                    assert skill != null;

                    if(player.getSkills().stream().anyMatch(skill1 -> skill1.equalsIgnoreCase(skill.getId()))) return;
                    player.addSkill(skill);
                    p.getInventory().getItemInMainHand().setType(Material.AIR);
                    PlayerManager.savePlayer(player);
                }
            }
        }
    }
}
