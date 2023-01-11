package v2.commonSkill;

import org.bukkit.Bukkit;
import v2.Main;
import v2.commonSkill.list.*;
import v2.commonSkill.list.Necromancy.shadowChange;

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
        skillSet.add(new ArmorStand());
        skillSet.add(new ArcherMaster());

        for (iSkill iSkill : skillSet) {
            if(iSkill instanceof iPassiveSkill passiveSkill) Bukkit.getPluginManager().registerEvents(passiveSkill, Main.main);
        }
    }

    public static Set<iSkill> getSkillSet(){
        return skillSet;
    }
}
