package ua.startandroid.myapplication.adapter;

import com.my_point_game.MyPointGame;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 12.07.14
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public class UserAdapter implements TransformInUser {

   private MyPointGame.Player player;

    public UserAdapter(MyPointGame.Player player){
        this.player = player;
    }

    @Override
    public int getIdOponent() {
        return player.getOponentId();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getNameUser() {
        return player.getName();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getId() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
