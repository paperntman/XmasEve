package topen;

import topen.Skill.iSkill;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class TopenPlayer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    UUID UUID;
    Set<String> skills = new HashSet<>();
    String[] hotbar = new String[9];
    public List<String> iPassiveSkills = new ArrayList<>();

    public TopenPlayer(UUID UUID) {
        this.UUID = UUID;
    }

    public void addSkill(iSkill skill){
        skills.add(skill.getId());
        PlayerManager.savePlayer(this);
    }

    public Set<String> getSkills(){
        return skills;
    }

    public String[] getHotbar() {
        return hotbar;
    }

    public void setHotbar(String id, int number){
        for (int i = 0; i < hotbar.length; i++) {
            if (hotbar[i] == null) {
                continue;
            }
            if(id.equals(hotbar[i])) hotbar[i] = null;
        }
        hotbar[number] = id;
        PlayerManager.savePlayer(this);
    }
    
    
}
