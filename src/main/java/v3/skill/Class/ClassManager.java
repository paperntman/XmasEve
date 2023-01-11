package v3.skill.Class;

import v3.skill.list.Shield;
import v3.skill.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassManager {

    private static final Map<Class, List<Skill>> classListMap = new HashMap<>();
    public static List<String> getSkillList(Class className){
        return classListMap.get(className).stream().map(Skill::getName).toList();
    }

    public static void setup(){
        classListMap.put(Class.warrior, List.of(new Shield()));
        classListMap.put(Class.none, new ArrayList<>());
    }
}
