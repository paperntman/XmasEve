package topen.Skill;

import org.bukkit.Bukkit;
import topen.Main;
import topen.Skill.list.Heal;
import topen.Skill.list.RealmOfDeath;
import topen.Skill.list.Vampiring;
import topen.Skill.list.Necromancy.shadowChange;
import topen.Skill.list.swordsman_skill;

import java.util.HashSet;
import java.util.Set;

public class SkillManager {

    private static final Set<iSkill> skillSet = new HashSet<>();
    public static Set<String> skillsUsing = new HashSet<>();
    public static iSkill getSkillByID(String id){
        for (iSkill iSkill : skillSet) {
            if(iSkill.getId().equalsIgnoreCase(id)) return iSkill;
        }
        return null;
    }
    public static iSkill getSkillByName(String name){
        for (iSkill iSkill : skillSet) {
            if(iSkill.getName().equalsIgnoreCase(name)) return iSkill;
        }
        return null;
    }

    public static void setup(){
        skillSet.add(new Heal());
        skillSet.add(new Vampiring());
        skillSet.add(new swordsman_skill());
        skillSet.add(new shadowChange());
        skillSet.add(new RealmOfDeath());

        for (iSkill iSkill : skillSet) {
            if(iSkill instanceof iPassiveSkill passiveSkill) Bukkit.getPluginManager().registerEvents(passiveSkill, Main.main);
        }
    }

    public static Set<iSkill> getSkillSet(){
        return skillSet;
    }
}
