package ua.startandroid.myapplication.gui_to_point_game;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 29.07.14
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
public interface IChatFragment {
    public ISentListener iSentListener = null;
    public void setSentButtonClickListener(ISentListener iSentListener);
    public void addNowMessage(String str);

}
