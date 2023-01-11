package v3;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import v3.player.TopenPlayer;

public class TopenPlayerHealEvent extends Event  implements Cancellable {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    private TopenPlayer topenPlayer;

    public TopenPlayerHealEvent(TopenPlayer player) {
        this.isCancelled = false;
        topenPlayer = player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public TopenPlayer getTopenPlayer() {
        return topenPlayer;
    }
}
