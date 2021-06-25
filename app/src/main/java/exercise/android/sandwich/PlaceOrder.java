package exercise.android.sandwich;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;

public class PlaceOrder extends AppCompatActivity{
    private String fullName;
    private int numOfPickles;
    private boolean addHummus;
    private boolean addTahini;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_place_order);

        EditText fullNameField = (EditText) findViewById(R.id.name);
        NumberPicker numOfPicklesField = (NumberPicker) findViewById(R.id.picklesPicker);
        CheckBox addHummusField = (CheckBox) findViewById(R.id.hummusCheckBox);
        CheckBox addTahiniField = (CheckBox) findViewById(R.id.tahiniCheckBox);
        EditText commentField = (EditText) findViewById(R.id.commentContent);

        numOfPicklesField.setMinValue(0);
        numOfPicklesField.setMaxValue(10);

        numOfPicklesField.setOnValueChangedListener((picker, oldVal, newVal) -> {
            this.numOfPickles = newVal;
        });

        fullNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                fullName = fullNameField.getText().toString();
            }
        });

        commentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                comment = commentField.getText().toString();
            }
        });


        addHummusField.setOnCheckedChangeListener((compoundButton, isChecked) -> addHummus = isChecked);
        addTahiniField.setOnCheckedChangeListener(((compoundButton, isChecked) -> addTahini = isChecked));


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fullName", fullName);
        outState.putInt("numOfPickles", numOfPickles);
        outState.putBoolean("addHummus", addHummus);
        outState.putBoolean("addTahini", addTahini);
        outState.putString("comment", comment);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fullName = savedInstanceState.getString("fullName");
        numOfPickles = savedInstanceState.getInt("numOfPickles");
        addHummus = savedInstanceState.getBoolean("addHummus");
        addTahini = savedInstanceState.getBoolean("addTahini");
        comment = savedInstanceState.getString("comment");


        EditText fullNameField = (EditText) findViewById(R.id.name);
        NumberPicker numOfPicklesField = (NumberPicker) findViewById(R.id.picklesPicker);
        CheckBox addHummusField = (CheckBox) findViewById(R.id.hummusCheckBox);
        CheckBox addTahiniField = (CheckBox) findViewById(R.id.tahiniCheckBox);
        EditText commentField = (EditText) findViewById(R.id.commentContent);


        fullNameField.setText(fullName);
        numOfPicklesField.setValue(numOfPickles);
        addHummusField.setChecked(addHummus);
        addTahiniField.setChecked(addTahini);
        commentField.setText(comment);
    }
}

