package topen;

import topen.Skill.iPassiveSkill;

import java.io.Serializable;
import java.util.ArrayList;

public class Queue implements Serializable {
    private ArrayList<iPassiveSkill> arrayQueue = new ArrayList<>();
    public void enqueue(iPassiveSkill data) {
        if (!arrayQueue.contains(data)) {
            if(arrayQueue.size() == 5) arrayQueue.remove(0);
            arrayQueue.add(data);
        }
    }

    public iPassiveSkill dequeue() {
        if(arrayQueue.size()==0) {
            return null;
        }
        return arrayQueue.remove(0);
    }

    public ArrayList<iPassiveSkill> getArrayQueue() {
        return arrayQueue;
    }
}
