package topen.Skill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import topen.PlayerManager;
import topen.TopenPlayer;
import topen.weapon.WeaponManager;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class SkillUse implements Listener {

    class temp{
        ItemStack[] itemStacks;
        int slot;

        public temp(ItemStack[] itemStacks, int slot) {
            this.itemStacks = itemStacks;
            this.slot = slot;
        }
    }
    private Map<Player, temp> waiting = new HashMap<>();
    private boolean justChanged = false;

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if (WeaponManager.getWeaponAsItem(e.getItem()) != null) {

            if(! (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
            final Player player = e.getPlayer();
            final TopenPlayer topenPlayer = PlayerManager.getPlayer(player.getUniqueId());
            final PlayerInventory inventory = e.getPlayer().getInventory();

            ItemStack[] tempArray = IntStream.range(0, 9).mapToObj(inventory::getItem).toArray(ItemStack[]::new);
            for (int i = 0; i < tempArray.length; i++) {
                if(tempArray[i] == null) tempArray[i] = new ItemStack(Material.AIR);
            }
            final String[] topenPlayerHotbar = topenPlayer.getHotbar();
            for (int i = 0; i < topenPlayerHotbar.length; i++) {
                final iSkill skill = SkillManager.getSkillByID(topenPlayerHotbar[i]);
                if(skill == null) {
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                    continue;
                }


                ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                final ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;

                itemMeta.setDisplayName(
                        String.format(ChatColor.LIGHT_PURPLE+"[%c] - %s", skill.getRank(), skill.getName())
                );
                itemStack.setItemMeta(itemMeta);

                player.getInventory().setItem(i, itemStack);
            }

            waiting.put(player, new temp(tempArray, player.getInventory().getHeldItemSlot()));
            player.getInventory().setHeldItemSlot(8);
            justChanged = true;

        }
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent e){
        System.out.println("이거 실행은 됨.");
        if(!waiting.containsKey(e.getPlayer()) || e.getNewSlot() == 8) return;

        System.out.println("이거 실행은 됨?");
        final Player player = e.getPlayer();
        if(waiting.containsKey(player)){
            final ItemStack item = player.getInventory().getItem(e.getNewSlot());

            if(item != null) {

                final String regex = ".{6}(.*)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(item.getItemMeta().getDisplayName().replace(ChatColor.LIGHT_PURPLE+"", ""));
                String name = "";
                if (matcher.find()) {
                    name = matcher.group(1);
                }

                System.out.println(name);
                ((iActiveSkill) SkillManager.getSkillByName(name)).onUse(player);
            }
            IntStream.range(0, 9).forEach(value -> player.getInventory().setItem(value, waiting.get(player).itemStacks[value]));
            player.getInventory().setHeldItemSlot(waiting.get(player).slot);
            waiting.remove(player);
        }
    }
}
