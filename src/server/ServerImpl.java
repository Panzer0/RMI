package server;

import com.sun.source.tree.Tree;
import shared.Server;

import java.rmi.RemoteException;
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
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();
        System.out.println(server.findMostCommon("""
                Letting the days go by, let the water hold me down
                Letting the days go by, water flowing underground
                Into the blue again after the money's gone
                Once in a lifetime, water flowing underground"""));
    }

}
