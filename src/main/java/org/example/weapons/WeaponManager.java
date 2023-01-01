package org.example.weapons;

import org.example.weapons.list.normal_bow;
import org.example.weapons.list.normal_healing_rod;
import org.example.weapons.list.normal_long_sword;

import java.util.ArrayList;
import java.util.List;

public class WeaponManager {
    static List<Weapon> weaponList = new ArrayList<>();
    public static void setup(){
        weaponList.add(new normal_long_sword());
        weaponList.add(new normal_bow());
        weaponList.add(new normal_healing_rod());
    }

    public static List<Weapon> getWeaponList() {
        return weaponList;
    }
}
