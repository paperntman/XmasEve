package v2.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import v2.PlayerFileManager;
import v2.TopenPlayer;

public class smartKey implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            final TopenPlayer topenPlayer = PlayerFileManager.getPlayer(player.getUniqueId());
            topenPlayer.setSmartKey(!topenPlayer.isSmartKey());
            sender.sendMessage("스마트키 사용 여부 : " + (topenPlayer.isSmartKey() ? "활성" : "비활성"));
        }
        return true;
    }
}
