package v2.commonSkill;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import v2.PlayerFileManager;
import v2.TopenPlayer;

import java.util.Arrays;
import java.util.Objects;

public class InventoryCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            player.openInventory(getInventory(player));
        }
        return true;
}

    public static Inventory getInventory(Player player){

        final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
        final int size = topenPlayer.getSkills().size();
        Inventory inventory = Bukkit.createInventory(player, Math.max((int) (Math.ceil( (double) size / 9 ) * 9) , 9), "인벤");

        topenPlayer.getSkills().forEach(skillId -> {
            final iSkill skill = SkillManager.getSkillByID(skillId);

            ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
            final ItemMeta itemMeta = stack.getItemMeta();
            assert itemMeta != null;
            assert skill != null;

            final boolean hotbarMatch = Arrays.stream(topenPlayer.getHotbar()).filter(Objects::nonNull).anyMatch(s -> s.equalsIgnoreCase(skillId));
            final boolean passiveMatch = topenPlayer.getIPassiveSkills().stream().filter(Objects::nonNull).anyMatch(s -> s.equalsIgnoreCase(skillId));
            itemMeta.setDisplayName(
                    (hotbarMatch||passiveMatch ? ChatColor.GREEN+"[Enabled] " : ChatColor.RED+"[Disabled] ")+
                    ChatColor.LIGHT_PURPLE+String.format("[%s] - %s", skill.getRank(), skill.getName()));
            itemMeta.setLore(Arrays.asList(skill.getDescription().split("\n")));
            stack.setItemMeta(itemMeta);
            inventory.addItem(stack);
        });

        return inventory;
    }
}
