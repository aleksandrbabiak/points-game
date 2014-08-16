package ua.startandroid.myapplication.controler;
import ua.startandroid.myapplication.client.StartClient;
import ua.startandroid.myapplication.adapter.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 08.07.14
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */
public class GlobalControler {
    private static GlobalControler globalControler;
    private static StartClient startClient;
    private static List<User> allUser = new ArrayList<User>();
    private static User myUser = new User();
    private static boolean isOnlineGaame = false;
    private static boolean isFirstOrSecondMove = false;

    public static boolean getIsFirstOrSecondMove() {
        return isFirstOrSecondMove;
    }

    public static void setIsFirstOrSecondMove(boolean firstOrSecondMove) {
        isFirstOrSecondMove = firstOrSecondMove;
    }

    public GlobalControler(){}

    public static GlobalControler getGlobalControler(){
        if(globalControler == null){
            globalControler = new GlobalControler();
        }
        return globalControler;
    }
    public StartClient getStartClient(){
        return startClient;
    }
    public void setStartClient(StartClient startClient){
        GlobalControler.startClient =  startClient;
    }
    public static void destroyGlobalControler(){
        if (globalControler != null){
            globalControler = null;
        }
    }
    public static void addAllPlayersOnline(User user){

        allUser.add(user);

    }
   public static List<User> allOnlinePlayers(){
       return allUser;
   }
    public static User getMyUser(){
        return myUser;
    }
    public static void setMyUser(User myUser){
        GlobalControler.myUser = myUser;
    }

    public static boolean getIsOnlineGaame() {
        return isOnlineGaame;
    }

    public static void setIsOnlineGaame(boolean onlineGaame) {
        isOnlineGaame = onlineGaame;
    }
}
