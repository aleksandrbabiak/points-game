package ua.startandroid.myapplication.gui_to_point_game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import ua.startandroid.myapplication.R;

/**
 * Created with IntelliJ IDEA.
 * User: Alex
 * Date: 13.06.14
 * Time: 7:54
 * To change this template use File | Settings | File Templates.
 */
public class SelectPlayGameActivity extends Activity implements View.OnClickListener {
    private Button onlineButton;
    private Button twoPlayer;
    private String nameFirstPlayer;
    private String nameSecondPlayer;
    private Intent intentField;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_play_game);
        onlineButton = (Button) findViewById(R.id.buttonOnline);
        twoPlayer = (Button) findViewById(R.id.buttonTwoPlayer);
        onlineButton.setOnClickListener(this);
        twoPlayer.setOnClickListener(this);
       // INTENT WITH FRAGMENT
        intentField = new Intent(this, FieldToMyPointGameActivity.class);
    }
    private void showTwoPlayerGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View editText = inflater.inflate(R.layout.dialog_signin, null);
        builder.setView(editText);
        builder.setTitle("Please entarance yor names");
        AlertDialog.Builder ok = builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                EditText firstName = (EditText) editText.findViewById(R.id.firstName);
                EditText secondName = (EditText) editText.findViewById(R.id.secondName);
                nameFirstPlayer = firstName.getText().toString();
                nameSecondPlayer = secondName.getText().toString();
                intentField.putExtra("firstName", nameFirstPlayer);
                intentField.putExtra("secondName", nameSecondPlayer);
                intentField.addFlags(1);
 // FRAGMENT ADD
                startActivity(intentField);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOnline:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
            break;
            case R.id.buttonTwoPlayer:
                showTwoPlayerGame();
                break;
        }
    }
}
