import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public void task1(String text) {

    }

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry(1099);
            Server server = (Server)reg.lookup("server");

            String text = """
                Letting the days go by, let the water hold me down
                Letting the days go by, water flowing underground
                Into the blue again after the money's gone
                Once in a lifetime, water flowing underground""";
            System.out.println(server.simplifyText(text));
            System.out.println(server.findMostCommon(text));


            server.rotateImage90("dog.jpg");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
