package v2.commonSkill;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import v2.PlayerFileManager;
import v2.TopenPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class InventoryClick implements Listener {

    static class temp{
        iSkill iSkill;
        ItemStack[] stacks;

        public temp(v2.commonSkill.iSkill iSkill, ItemStack[] stacks) {
            this.iSkill = iSkill;
            this.stacks = stacks;
        }
    }

    Map<Player, temp> waiting = new HashMap<>();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getView().getTitle().equalsIgnoreCase("인벤")){
            e.setCancelled(true);
            if (e.getView().getTopInventory().equals(e.getClickedInventory())) {

                final Player player = (Player) e.getWhoClicked();

                String skillName = "";
                final String regex = ".[A-Z].\\s-\\s(.*)";
                String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
                for (ChatColor value : ChatColor.values()) {
                    displayName = displayName.replace(value+"", "");
                }
                final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(displayName);
                if(matcher.find()) skillName = matcher.group(1);
                final iSkill skill = SkillManager.getSkillByName(skillName);

                final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
                if(skill instanceof iPassiveSkill passiveSkill) {

                    if (!topenPlayer.getIPassiveSkills().contains(passiveSkill.getId())) {
                        topenPlayer.addPassiveSkill(passiveSkill.getId());
                    }else {
                        topenPlayer.removePassiveSkill(passiveSkill.getId());
                    }

                    PlayerFileManager.savePlayer(topenPlayer);
                    player.openInventory(InventoryCmd.getInventory(player));


                    SkillManager.skillsUsing.clear();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        final TopenPlayer tempTopen = PlayerFileManager.getPlayer(onlinePlayer.getUniqueId());
                        SkillManager.skillsUsing.addAll(tempTopen.getIPassiveSkills());
                    }


                }else{

                    waiting.put(player, new temp(skill, IntStream.range(0, 9).mapToObj(player.getInventory()::getItem).toArray(ItemStack[]::new)));
                    IntStream.range(0, 9).forEach(value -> {

                        ItemStack itemStack;
                        if (topenPlayer.getHotbar()[value] == null) {
                            itemStack = new ItemStack(Material.AIR);
                        }else itemStack = new ItemStack(Material.ENCHANTED_BOOK);

                        player.getInventory().setItem(value, itemStack);
                        player.getInventory().setHeldItemSlot(8);
                        player.closeInventory();
                    });

                }
            }
        }
    }

    @EventHandler
    public void onHotbar(PlayerItemHeldEvent e){
        if(waiting.containsKey(e.getPlayer()) && e.getNewSlot() != 8){
            final Player player = e.getPlayer();
            final temp temp = waiting.get(player);

            final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
            topenPlayer.setHotbar(temp.iSkill.getId(), e.getNewSlot());
            for (int i = 0; i < temp.stacks.length; i++) {
                player.getInventory().setItem(i, temp.stacks[i]);
            }
            waiting.remove(player);
        }
    }
}
