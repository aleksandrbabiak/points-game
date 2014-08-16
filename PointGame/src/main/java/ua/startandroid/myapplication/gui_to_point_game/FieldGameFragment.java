package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import ua.startandroid.myapplication.R;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 22.07.14
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
public class FieldGameFragment extends Fragment implements IFieldGameFragment, View.OnClickListener {
    private BoxAdapter boxAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.field_fragment, null);
        boxAdapter = new BoxAdapter(getActivity());
        GridView gridView = (GridView) view.findViewById(R.id.fieldGame_gridView);
        gridView.setAdapter(boxAdapter);
        return view;
    }

    @Override
    public void oneMove() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public BoxAdapter getBoxAdapter() {
    return boxAdapter;
    }


    @Override
    public void onClick(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
