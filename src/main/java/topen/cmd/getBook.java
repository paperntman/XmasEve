package topen.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import topen.Skill.SkillBook;
import topen.Skill.SkillManager;
import topen.Skill.iSkill;

import java.util.List;
import java.util.stream.Collectors;

public class getBook implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            final iSkill skillByID = SkillManager.getSkillByID(args[0]);
            if (skillByID == null) {
                sender.sendMessage("없는 스킬입니다!");
                return true;
            }
            player.getInventory().addItem(new SkillBook(skillByID).getItem());
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return SkillManager.getSkillSet().stream().map(iSkill::getId).collect(Collectors.toList());
    }
}
