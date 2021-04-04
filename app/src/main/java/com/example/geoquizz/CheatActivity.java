package com.example.geoquizz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private boolean mAnswerIsTrue;
    private TextView mAnswerIsTrueTextView;
    private Button mAnswerIsTrueButton;


    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.geoquizz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOW = "com.example.geoquizz.answer_shown";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOW, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerIsTrueTextView = (TextView) findViewById(R.id.answer_text_view);
        mAnswerIsTrueButton = (Button) findViewById(R.id.show_answer_button);
        mAnswerIsTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerIsTrueTextView.setText(R.string.true_button);
                } else {
                    mAnswerIsTrueTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW, isAnswerShown);
        setResult(RESULT_OK, data);
    }

}