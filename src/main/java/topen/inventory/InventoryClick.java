package topen.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import topen.PlayerManager;
import topen.Skill.SkillManager;
import topen.Skill.iPassiveSkill;
import topen.Skill.iSkill;
import topen.TopenPlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class InventoryClick implements Listener {

    class temp{
        iSkill iSkill;
        ItemStack[] stacks;

        public temp(topen.Skill.iSkill iSkill, ItemStack[] stacks) {
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

                final TopenPlayer topenPlayer = PlayerManager.getPlayer(player.getUniqueId());
                if(skill instanceof iPassiveSkill passiveSkill) {

                    System.out.println(Arrays.toString(topenPlayer.iPassiveSkills.toArray()));
                    if (topenPlayer.iPassiveSkills.contains(passiveSkill.getId())) {
                        topenPlayer.iPassiveSkills.remove(passiveSkill.getId());
                    }else {
                        topenPlayer.iPassiveSkills.add(passiveSkill.getId());
                    }

                    PlayerManager.savePlayer(topenPlayer);
                    player.openInventory(InventoryCmd.getInventory(player));


                    SkillManager.skillsUsing.clear();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        final TopenPlayer tempTopen = PlayerManager.getPlayer(onlinePlayer.getUniqueId());
                        SkillManager.skillsUsing.addAll(tempTopen.iPassiveSkills);
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

            final TopenPlayer topenPlayer = PlayerManager.getPlayer(player.getUniqueId());
            topenPlayer.setHotbar(temp.iSkill.getId(), e.getNewSlot());
            for (int i = 0; i < temp.stacks.length; i++) {
                player.getInventory().setItem(i, temp.stacks[i]);
            }
            waiting.remove(player);
        }
    }
}
