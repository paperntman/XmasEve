package v3.player;

import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import v3.Main;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerManager {
    public static TopenPlayer getPlayer(Player player){
        String ret = "";
        try (FileReader reader = new FileReader(Main.main.getDataFolder().getPath() + File.separator + player.getUniqueId())) {
            ret += ((char) reader.read());
        } catch (FileNotFoundException e){
            return new TopenPlayer(
                    player.getUniqueId(),
                    new HashMap<>(),
                    20,
                    20,
                    player.getHealth(),
                    20,
                    0,
                    0,
                    0,
                    new String[9],
                    new HashSet<>(),
                    "none"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(ret);

        Map<String, Object> state = new HashMap<>();
        JSONObject states = jsonObject.getJSONObject("states");
        for (String s : states.keySet()) {
            state.put(s, states.get(s));
        }

        return new TopenPlayer(
                UUID.fromString(jsonObject.getString("uuid")),
                state,
                jsonObject.getDouble("maxMp"),
                jsonObject.getDouble("maxHp"),
                jsonObject.getDouble("Mp"),
                jsonObject.getDouble("Hp"),
                jsonObject.getDouble("shield"),
                jsonObject.getDouble("absorption"),
                jsonObject.getInt("gold"),
                jsonObject.getJSONArray("hotbar").toList().stream().map(Object::toString).toArray(String[]::new),
                jsonObject.getJSONArray("skills").toList().stream().map(Object::toString).collect(Collectors.toSet()),
                jsonObject.getString("class"));
    }

    private static JSONObject playerToJsonObject(TopenPlayer player){
        JSONObject mainObject = new JSONObject();

        mainObject.append("uuid", player.getUUID().toString());
        mainObject.append("gold", player.getGold());
        mainObject.append("hp", player.getHp());
        mainObject.append("mp", player.getMp());
        mainObject.append("maxHp", player.getMaxHp());
        mainObject.append("maxMp", player.getMaxMp());
        mainObject.append("shield", player.getShield());
        mainObject.append("absorption", player.getAbsorption());

        Map<String, Object> states = player.getStates();
        JSONObject state = new JSONObject();
        for (String s : states.keySet()) {
           state.put(s, states.get(s).toString());
        }
        mainObject.append("states", state);

        mainObject.append("hotbar", new JSONArray(player.getHotbar()));

        mainObject.append("skills", new JSONArray(player.getSkills()));

        mainObject.append("class", player.getaClass().toString());

        return mainObject;
    }

    public static void savePlayer(TopenPlayer player){
        JSONObject jsonObject = playerToJsonObject(player);
        try (FileWriter fileWriter = new FileWriter(Main.main.getDataFolder().getPath() + File.separator + player.getUUID().toString())) {
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
