package com.luckynumbers.mycax.instantluckynumbers.getyournumber;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("luckynumbers");
    }

    private EditText mFirstName;
    private EditText mLastName;
    private HTextView mOutputText;

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirstName = findViewById(R.id.firstName);
        mLastName = findViewById(R.id.lastName);
        mOutputText = findViewById(R.id.output_text);
        mOutputText.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(HTextView hTextView) {
                //noop
            }
        });

        ImageButton mButton_Calculate = findViewById(R.id.imageButtonCalculate);
        mButton_Calculate.setOnClickListener(this);
        ImageButton mButton_Reset = findViewById(R.id.imageButtonReset);
        mButton_Reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imageButtonCalculate) {
            if (isEmpty(mFirstName) && isEmpty(mLastName) || isEmpty(mFirstName) || isEmpty(mLastName)) {
                Snackbar.make(v , "Input fields cannot be empty", Toast.LENGTH_SHORT).show();

            } else if (isAlpha(mFirstName.getText().toString()) && isAlpha(mLastName.getText().toString())) {
                String result = Calculate(mFirstName.getText().toString(), mLastName.getText().toString());
                mOutputText.animateText(result);

            } else {
                Snackbar.make(v, "Input fields can only contain letters!", Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.imageButtonReset) {
            mFirstName.getText().clear();
            mLastName.getText().clear();
            mOutputText.animateText("");
        }
    }

    public native String Calculate(String jFirstName, String jLastName);
}
