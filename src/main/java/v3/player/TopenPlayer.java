package v3.player;

import org.bukkit.Bukkit;
import v3.skill.Class.Class;
import v3.skill.Class.ClassManager;

import java.util.*;

public class TopenPlayer {
    private UUID UUID;
    private Map<String, Object> state;
    private double maxMp = 100;
    private double maxHp = 20;
    private double hp = 20;
    private double mp = 100;
    private double shield = 0;
    private double absorption = 0;
    private int gold = 0;
    private String[] hotbar;
    private Set<String> skills;
    private Set<String> classSkills = new HashSet<>();
    private void update(){
        PlayerManager.savePlayer(this);
    }
    private Class Class;

    public TopenPlayer(java.util.UUID UUID, Map<String, Object> state, double maxMp, double maxHp, double hp, double mp, double shield, double absorption, int gold, String[] hotbar, Set<String> skills, String Class) {
        this.UUID = UUID;
        this.state = state;
        this.maxMp = maxMp;
        this.maxHp = maxHp;
        this.hp = hp;
        this.mp = mp;
        this.absorption = absorption;
        this.shield = shield;
        this.gold = gold;
        this.hotbar = hotbar;
        this.skills = skills;
        this.Class = v3.skill.Class.Class.valueOf(Class);
    }

    public void addAbsorption(double amount){
        absorption += amount;
        update();
    }
    public void setShield(double shield) {
        this.shield = shield;
        update();
    }

    public void setGold(int gold) {
        this.gold = gold;
        update();
    }

    public void setState(String stateName, Object stateValue){
        state.put(stateName, stateValue);
        update();
    }

    public void delState(String stateName){
        state.remove(stateName);
        update();
    }
    public void setHotbar(String skillID, int slot){
        for (int i = 0; i < hotbar.length; i++) {
            if(Objects.equals(hotbar[i], skillID)) hotbar[i] = null;
        }
        hotbar[slot] = skillID;
        update();
    }

    public void damage(double damage){
        hp -= Math.max(hp - damage * (1 / (1 + shield * 0.01)), 0);
        if(absorption > 0) hp += absorption;
        absorption = Math.max(maxHp - hp, 0);
        update();
        DamageManager.updateHeart(Bukkit.getPlayer(UUID));
    }

    public void heal(double amount){
        hp += Math.min(hp + amount, maxHp);
        update();
        DamageManager.updateHeart(Bukkit.getPlayer(UUID));
    }

    public void setClass(Class Class){
        this.Class = Class;
        classSkills.clear();
        classSkills.addAll(ClassManager.getSkillList(Class));
        update();
    }



    public double getMaxMp() {
        return maxMp;
    }
    public double getShield() {
        return shield;
    }
    public double getMaxHp() {
        return maxHp;
    }
    public double getHp() {
        return hp;
    }
    public double getMp() {
        return mp;
    }
    public int getGold() {
        return gold;
    }
    public UUID getUUID() {
        return UUID;
    }
    public Map<String, Object> getStates() {
        return state;
    }
    public Object getState(String stateName) {
        return state.get(stateName);
    }
    public String[] getHotbar() {
        return hotbar;
    }
    public Set<String> getSkills() {
        HashSet<String> skills = new HashSet<>(this.skills);
        skills.addAll(classSkills);
        return skills;
    }

    public double getAbsorption() {
        return absorption;
    }

    public v3.skill.Class.Class getaClass() {
        return Class;
    }
}
