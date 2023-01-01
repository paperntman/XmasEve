package org.example.skills;

public class HealSkill implements SkillInterface{
    @Override
    public boolean isEnabled() {
        return SkillInterface.super.isEnabled();
    }

    @Override
    public String getId() {
        return "Heal";
    }

    @Override
    public String getName() {
        return "힐";
    }

}
