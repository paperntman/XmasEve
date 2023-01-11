package v2;

import v2.commonSkill.iSkill;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class TopenPlayer implements Serializable {

    public TopenPlayer(UUID UUID) {
        this.UUID = UUID;
        maxHealth = 400;
        maxMp = 200;
        health = 400;
        mp = 200;
    }

    @Serial
    private static final long serialVersionUID = 1L;
    UUID UUID;
    Set<String> skills = new HashSet<>();
    String[] hotbar = new String[9];
    private final List<String> iPassiveSkills = new ArrayList<>();
    double health, maxHealth;
    int mp, maxMp;
    boolean smartKey = false;

    public boolean isSmartKey() {
        return smartKey;
    }

    public void setSmartKey(boolean smartKey) {
        this.smartKey = smartKey;
        save();
    }

    public boolean useMana(int d){
        if(mp > d){
            mp -= d;
            save();
            return true;
        }else return false;
    }
    public void damage(double d){
        DamageManager.changeHealth(this, -d);
        save();
    }

    public void heal(double d){
        DamageManager.changeHealth(this, d);
        save();
    }
    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
        save();
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
        setHealth(Math.min(getMaxHealth(), getHealth()));
        save();
    }

    public double getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
        save();
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
        save();
    }



    public void addSkill(iSkill skill){
        skills.add(skill.getId());
        PlayerFileManager.savePlayer(this);
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
        save();
    }

    public List<String> getIPassiveSkills() {
        return Collections.unmodifiableList(iPassiveSkills);
    }

    public void addPassiveSkill(String id) {
        iPassiveSkills.add(id);
        save();
    }
    public void removePassiveSkill(String id) {
        iPassiveSkills.remove(id);
        save();
    }

    private void save(){
        PlayerFileManager.savePlayer(this);
    }

}
