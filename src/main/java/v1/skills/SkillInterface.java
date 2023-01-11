package v1.skills;

import org.bukkit.event.Listener;

import java.util.concurrent.atomic.AtomicBoolean;

public interface SkillInterface extends Listener {


    AtomicBoolean enabled = new AtomicBoolean();
    default boolean isEnabled() {
        return enabled.get();
    }

    default void setEnabled(boolean enable){
        enabled.set(enable);
    }

    public String getId();
    public String getName();

}
