import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl implements Server {
    public ServerImpl() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String simplifyText(String text) throws RemoteException {
        return  text.toLowerCase()
                    .replaceAll("[^\\w\\s]", "")
                    .replaceAll("\\n", " ")
                    .replaceAll("\\s+"," ");
    }

    @Override
    public HashMap<String, Integer> findMostCommon(String text) throws RemoteException {
        HashMap<String, Integer> leaderboard = new HashMap<>();
        text = this.simplifyText(text);
        String[] words = text.split(" ");

        for (String word : words) {
            if (leaderboard.containsKey(word)) {
                leaderboard.replace(word, leaderboard.get(word) + 1);
            } else {
                leaderboard.put(word, 1);
            }
        }


        HashMap<String, Integer> top5 = new HashMap<>();
        for(int i = 0; i < 5; i++) {
            for(String word : leaderboard.keySet()) {
                if(leaderboard.get(word).equals(Collections.max(leaderboard.values()))) {
                    top5.put(word, leaderboard.get(word));
                    leaderboard.remove(word);
                    break;
                }
            }
        }
        return top5;
    }

    private int[] rotateIntArray(int[] array, int h, int w) {
        int[] out = new int[w*h];
        for(int y = 0; y < w; y++) {
            for(int x = 0; x < h; x++) {
                out[x*h + (w-y-1)] = array[y*w + x];
            }
        }
        return out;
    }

    @Override
    public void rotateImage90(String inName, String outName) throws IOException {
        File inFile = new File(inName);
        BufferedImage inImage = ImageIO.read(inFile);
        int w = inImage.getWidth();
        int h = inImage.getHeight();
        int[] buff = new int[w*h];
        inImage.getRGB(0, 0, w, h, buff, 0, w);
        BufferedImage outImage = new BufferedImage(h, w,BufferedImage.TYPE_INT_RGB);
        outImage.setRGB(0, 0, h, w, rotateIntArray(buff, h, w), 0, h);
        File outFile = new File(outName);
        ImageIO.write(outImage, "JPG", outFile);
    }

    public static void main(String[] args) throws RemoteException {
        try{
            Server server = new ServerImpl();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", server);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
