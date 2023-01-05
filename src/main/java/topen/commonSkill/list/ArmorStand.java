package topen.commonSkill.list;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import topen.PlayerFileManager;
import topen.commonSkill.SkillManager;
import topen.commonSkill.iPassiveSkill;
import topen.TopenPlayer;

public class ArmorStand implements iPassiveSkill {
    @Override
    public char getRank() {
        return 'U';
    }

    @Override
    public String getName() {
        return "갑옷 거치대의 주인";
    }

    @Override
    public String getId() {
        return "Master_of_Armor_Stand";
    }

    @Override
    public String getDescription() {
        return "갑옷 거치대를 소환할 때 팔이 생성되게 합니다.";
    }



    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(! SkillManager.skillsUsing.contains(getId())) return;
        final Player player = e.getPlayer();
        final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
        if (topenPlayer.getIPassiveSkills().contains(getId()) && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && player.getInventory().getItemInMainHand().getType().equals(Material.ARMOR_STAND)) {
            e.setCancelled(true);
            final Location location = e.getClickedBlock().getLocation().add(0.5, 1, 0.5);
            final org.bukkit.entity.ArmorStand armorStand = ((org.bukkit.entity.ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND));
            final Location direction = armorStand.getLocation().setDirection(player.getLocation().subtract(armorStand.getLocation()).toVector());
            armorStand.teleport(direction);
            armorStand.setArms(true);
        }
    }
}
