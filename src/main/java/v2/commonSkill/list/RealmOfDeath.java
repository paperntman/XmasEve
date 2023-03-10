package v2.commonSkill.list;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import v2.Main;
import v2.PlayerFileManager;
import v2.TopenPlayer;
import v2.commonSkill.iActiveSkill;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RealmOfDeath implements iActiveSkill {

    int task1, task2;

    @Override
    public int manaNeeded() {
        return 100;
    }

    @Override
    public void onUse(Player attacker) {
        final List<Entity> entities = attacker.getWorld().getNearbyEntities(attacker.getLocation(), 5, 5, 5).stream().filter(entity -> entity instanceof Player && entity != attacker)
                .sorted(Comparator.comparing(entity -> attacker.getLocation().distance(entity.getLocation()))).toList();
        if(entities.size() > 0){
            Player victim = ((Player) entities.get(0));
            final World realmOfDeath = Bukkit.getWorld("Realm_of_Death");
            assert realmOfDeath != null;
            final Location location = realmOfDeath.getSpawnLocation();

            final Location victimLocation = victim.getLocation();
            final Location attackerLocation = attacker.getLocation();

            victim.teleport(location);
            attacker.teleport(location);

            victim.setGameMode(GameMode.ADVENTURE);
            attacker.setGameMode(GameMode.ADVENTURE);

            ArrayList<Integer> taskList = new ArrayList<>();
            final double[] stolenAttribute = new double[5];
            final Attribute[] attributeList = {Attribute.GENERIC_ARMOR, Attribute.GENERIC_MOVEMENT_SPEED, Attribute.GENERIC_ATTACK_SPEED, Attribute.GENERIC_ATTACK_DAMAGE};
            final double[] stolenHealth = {0};

            for (int i = 0; i < attributeList.length; i++) {
                final AttributeInstance victimAttribute = victim.getAttribute(attributeList[i]);
                final AttributeInstance attackerAttribute = attacker.getAttribute(attributeList[i]);
                assert victimAttribute != null;
                assert attackerAttribute != null;
                stolenAttribute[i] = victimAttribute.getBaseValue()/10;
                victimAttribute.setBaseValue(victimAttribute.getBaseValue()- stolenAttribute[i]);
                attackerAttribute.setBaseValue(attackerAttribute.getBaseValue()+ stolenAttribute[i]);

                System.out.printf("Attribute : %s, Stolen : %f, Victim : %f, Attacker : %f\n", attributeList[i].name(), stolenAttribute[i], victimAttribute.getBaseValue(), attackerAttribute.getBaseValue());

                int finalI = i;
                taskList.add(Bukkit.getScheduler().runTaskLater(Main.main, () -> {
                    victimAttribute.setBaseValue(victimAttribute.getBaseValue() + stolenAttribute[finalI]);
                    attackerAttribute.setBaseValue(attackerAttribute.getBaseValue() - stolenAttribute[finalI]);
                }, 800).getTaskId());

            }
            final TopenPlayer attackerTopenPlayer = PlayerFileManager.getPlayer(attacker.getUniqueId());
            final TopenPlayer victimTopenPlayer = PlayerFileManager.getPlayer(victim.getUniqueId());

            stolenHealth[0] = victimTopenPlayer.getMaxHealth() / 10;

            attackerTopenPlayer.setMaxHealth(attackerTopenPlayer.getMaxHealth()+stolenHealth[0]);
            victimTopenPlayer.setMaxHealth(victimTopenPlayer.getMaxHealth()-stolenHealth[0]);

            taskList.add(Bukkit.getScheduler().runTaskLater(Main.main, () -> {
                attackerTopenPlayer.setMaxHealth(attackerTopenPlayer.getMaxHealth()-stolenHealth[0]);
                victimTopenPlayer.setMaxHealth(victimTopenPlayer.getMaxHealth()+stolenHealth[0]);
            }, 800).getTaskId());


            System.out.printf("Attribute : %s, Stolen : %f, Victim : %f, Attacker : %f\n", "health", stolenHealth[0], victimTopenPlayer.getMaxHealth(), attackerTopenPlayer.getMaxHealth());




            AtomicInteger ticks = new AtomicInteger();
            task1 = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, () -> {
                if(ticks.get() > 800) cancelTask();
                if(victim.isDead() || attacker.isDead()){
                    taskList.forEach(Bukkit.getScheduler()::cancelTask);
                    for (int i = 0; i < attributeList.length; i++) {
                        int finalI = i;
                        Bukkit.getScheduler().runTaskLater(Main.main, () -> {
                            final AttributeInstance victimAttribute = victim.getAttribute(attributeList[finalI]);;
                            final AttributeInstance attackerAttribute = attacker.getAttribute(attributeList[finalI]);
                            assert victimAttribute != null;
                            assert attackerAttribute != null;
                            victimAttribute.setBaseValue(victimAttribute.getBaseValue() + stolenAttribute[finalI]);
                            attackerAttribute.setBaseValue(attackerAttribute.getBaseValue() - stolenAttribute[finalI]);
                            System.out.printf("Attribute : %s, Stolen : %f, Victim : %f, Attacker : %f\n", attributeList[finalI].name(), stolenAttribute[finalI], victimAttribute.getBaseValue(), attackerAttribute.getBaseValue());
                            }, 600);
                    }

                    Bukkit.getScheduler().runTaskLater(Main.main, () -> {
                    attackerTopenPlayer.setMaxHealth(attackerTopenPlayer.getMaxHealth()-stolenHealth[0]);
                    victimTopenPlayer.setMaxHealth(attackerTopenPlayer.getMaxHealth()+stolenHealth[0]);
                    System.out.printf("Attribute : %s, Stolen : %f, Victim : %f, Attacker : %f\n", "health", stolenHealth[0], victimTopenPlayer.getMaxHealth(), attackerTopenPlayer.getMaxHealth());
                    attacker.teleport(attackerLocation);
                    victim.teleport(victimLocation);
                    }, 600);


                    cancelTask();
                }
                ticks.getAndIncrement();
            }, 0, 1);
        }
    }

    private void cancelTask(){
        Bukkit.getScheduler().cancelTask(task1);
    }

    @Override
    public char getRank() {
        return 'S';
    }

    @Override
    public String getName() {
        return "????????? ??????";
    }

    @Override
    public String getId() {
        return "Realm-of-Death";
    }

    @Override
    public String getDescription() {
        return """
                ?????? ????????? ??????????????? ????????? ????????? ????????? ?????? ?????? ?????? ????????? ?????? ?????? ??????????????? 10%??? ????????????.\s
                ????????? ???????????? ?????? ???????????? 30??? ?????? ?????? ???????????? ???????????????.\s
                ?????? ??? ?????? ?????? ????????? : ?????????, ?????? ??????, ?????? ??????, ?????????, ?????? ??????""";
    }
}
