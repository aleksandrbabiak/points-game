package ua.startandroid.myapplication.data_base_to_point_game;

/**
 * Created by Alex on 07.05.14.
 */
public class Player {

    private int id;
    private String login;
    private int points;
    private int winGame;
    private int loseGame;

    public int getId() {

        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWin_game() {
        return winGame;
    }

    public void setWin_game(int winGame) {
        this.winGame = winGame;
    }

    public int getLose_game() {
        return loseGame;
    }

    public void setLose_game(int loseGame) {
        this.loseGame = loseGame;
    }
}
