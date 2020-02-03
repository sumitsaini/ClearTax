package test.sumit.com.cleartaxdemo;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

public class EditorActivity extends AppCompatActivity {

    public EditText etEditor;
    public TextView tvWordCount;
    public Button btnUndo;
    public String oldString = "";
    public String newString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setProperties();

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedData = savedInstanceState.getString("enteredText");
        try {
            etEditor.setText(savedData);
            etEditor.setSelection(savedData.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initViews() {
        etEditor = findViewById(R.id.et_editor);
        tvWordCount = findViewById(R.id.tv_words_count);
        btnUndo = findViewById(R.id.btn_undo);

    }


    public void setProperties() {

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightPixels=displayMetrics.heightPixels;

        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,heightPixels/2);
        etEditor.setLayoutParams(layoutParams);

        tvWordCount.setText(etEditor.length() + " Words");
        etEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvWordCount.setText(charSequence.toString().length() + " Words");
                newString = charSequence.toString();
                if (charSequence.toString().length() > 0) {
                    btnUndo.setEnabled(true);
                } else {
                    btnUndo.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                oldString = editable.toString();
            }
        });

        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (newString.length() > 0) {
                        int lastIndex = oldString.lastIndexOf(" ");
                        if (lastIndex == -1) {
                            etEditor.setText("");
                            etEditor.setSelection(0);
                        } else {
                            oldString = oldString.substring(0, lastIndex);
                            etEditor.setText(oldString);
                            etEditor.setSelection(etEditor.getText().length());
                        }
                    } else {
                        etEditor.setText(oldString);
                        etEditor.setSelection(etEditor.getText().length());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("enteredText", etEditor.getText().toString());
    }
}
