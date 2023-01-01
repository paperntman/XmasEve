package topen;

import java.io.*;
import java.util.UUID;

public class PlayerManager {
    public static TopenPlayer getPlayer(UUID uuid){
        File file = new File(Main.main.getDataFolder().getPath()+File.separator+uuid.toString());
        if(!file.exists()) return new TopenPlayer(uuid);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            return ((TopenPlayer) objectInputStream.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void savePlayer(TopenPlayer player){
        File file = new File(Main.main.getDataFolder().getPath()+File.separator+player.UUID.toString());
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(player);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
