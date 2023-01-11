package v1.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import v1.XPlayer;
import v1.skills.SkillInterface;

import java.util.Collections;
import java.util.List;

public class setSkill implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player p){
            XPlayer xPlayer = new XPlayer(p);
            Player player = xPlayer.getPlayer();

            final List<SkillInterface> skillInterfaceList = xPlayer.getSkillList().stream().toList();
            Inventory inventory = Bukkit.createInventory(null, Math.max((int) Math.ceil((double)skillInterfaceList.size()/9), 9), "TestInventory");

            for (int i = 0; i < skillInterfaceList.size(); i++) {
                final SkillInterface skillInterface = skillInterfaceList.get(i);
                ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
                final ItemMeta itemMeta = itemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(skillInterface.getName());
                itemMeta.setLore(Collections.singletonList("우클릭을 통해 학습할 수 있습니다."));
                itemStack.setItemMeta(itemMeta);

                inventory.setItem(i, itemStack);
            }

            p.openInventory(inventory);
        }else{
            sender.sendMessage("플레이어만 사용할 수 있습니다!");
            //TODO 콘솔 관리
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
}
