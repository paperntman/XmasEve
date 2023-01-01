package org.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.example.skills.SkillInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class XPlayer implements Serializable {
    private final UUID player;
    ArrayList<SkillInterface> skillList;

    public XPlayer(Player player) {
        this.player = player.getUniqueId();

        XPlayer xPlayer;
        try {
            File playerFile = new File(Main.DataFolder.getPath() + File.separator + getPlayer().getUniqueId());
            if(playerFile.exists()) {
                ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(playerFile));
                xPlayer = (XPlayer) outputStream.readObject();
                outputStream.close();
                skillList = xPlayer.getSkillList();
            }else skillList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public Player getPlayer() {
        return Bukkit.getPlayer(player);
    }

    public ArrayList<SkillInterface> getSkillList() {
        return skillList;
    }

    public boolean addSkill(SkillInterface skill){
        if(skillList.contains(skill)) return false;
        skillList.add(skill);
        update();
        return true;
    }

    public boolean removeSkill(SkillInterface skill){
        if(!skillList.contains(skill)) return false;
        skillList.remove(skill);
        update();
        return true;
    }

    public void update(){
        try {
            File playerFile = new File(Main.DataFolder.getPath() + File.separator + getPlayer().getUniqueId());
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(playerFile));
            outputStream.writeObject(this);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
