package topen.Skill;

import java.io.Serializable;

public interface iSkill extends Serializable {
    public char getRank();
    public String getName();
    public String getId();
    public String getDescription();
}
