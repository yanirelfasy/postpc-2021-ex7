package exercise.android.sandwich;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

public class PlaceOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_place_order);

        NumberPicker np = (NumberPicker) findViewById(R.id.picklesPicker);
        np.setMinValue(0);
        np.setMaxValue(10);
    }
}
