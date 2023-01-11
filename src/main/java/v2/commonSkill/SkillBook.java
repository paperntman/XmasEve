package v2.commonSkill;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SkillBook{
    public iSkill skill;

    public SkillBook(iSkill skill) {
        this.skill = skill;
    }

    public ItemStack getItem(){
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;

        itemMeta.setDisplayName(
                String.format(ChatColor.LIGHT_PURPLE+"[%c] - %s", skill.getRank(), skill.getName())
        );
        itemMeta.setLore(
                List.of(new String[]{String.format("%c급 스킬 %s을(를) 배울 수 있는 책입니다.", skill.getRank(), skill.getName())
                        , "우클릭을 통해 학습하세요."})
        );

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
