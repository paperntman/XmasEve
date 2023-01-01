package topen.Skill.list;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import topen.Main;
import topen.Skill.iActiveSkill;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RealmOfDeath implements iActiveSkill {

    int asdf;
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
            final Attribute[] attributeList = {Attribute.GENERIC_MAX_HEALTH, Attribute.GENERIC_ARMOR, Attribute.GENERIC_MOVEMENT_SPEED, Attribute.GENERIC_ATTACK_SPEED, Attribute.GENERIC_ATTACK_DAMAGE};

            for (int i = 0; i < attributeList.length; i++) {
                final AttributeInstance victimAttribute = victim.getAttribute(attributeList[i]);
                final AttributeInstance attackerAttribute = attacker.getAttribute(attributeList[i]);
                assert victimAttribute != null;
                assert attackerAttribute != null;
                stolenAttribute[i] = victimAttribute.getBaseValue()/10;
                victimAttribute.setBaseValue(victimAttribute.getBaseValue()- stolenAttribute[i]);
                attackerAttribute.setBaseValue(attackerAttribute.getBaseValue()+ stolenAttribute[i]);

                System.out.printf("Attribute : %s, Stolen : %f, Victim : %f, Attacker : %f", attributeList[i].name(), stolenAttribute[i], victimAttribute.getBaseValue(), attackerAttribute.getBaseValue());

                int finalI = i;
                taskList.add(Bukkit.getScheduler().runTaskLater(Main.main, () -> {
                    victimAttribute.setBaseValue(victimAttribute.getBaseValue() + stolenAttribute[finalI]);
                    attackerAttribute.setBaseValue(attackerAttribute.getBaseValue() - stolenAttribute[finalI]);
                    attacker.teleport(attackerLocation);
                    victim.teleport(victimLocation);
                }, 800).getTaskId());

            }


            AtomicInteger ticks = new AtomicInteger();
            asdf = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, () -> {
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
                    attacker.teleport(attackerLocation);
                    victim.teleport(victimLocation);
                    cancelTask();
                }
                ticks.getAndIncrement();
            }, 0, 1);
        }
    }

    private void cancelTask(){
        Bukkit.getScheduler().cancelTask(asdf);
    }

    @Override
    public char getRank() {
        return 'S';
    }

    @Override
    public String getName() {
        return "죽음의 세계";
    }

    @Override
    public String getId() {
        return "Realm-of-Death";
    }

    @Override
    public String getDescription() {
        return """
                가장 가까운 플레이어를 죽음의 세계로 추방해 지속 시간 동안 대상이 지닌 주요 능력치들의 10%를 훔칩니다.\s
                죽음의 세계에서 적을 처치하면 30초 동안 훔친 능력치를 유지합니다.\s
                훔칠 수 있는 주요 능력치 : 공격력, 공격 속도, 최대 체력, 방어력, 이동 속도""";
    }
}
