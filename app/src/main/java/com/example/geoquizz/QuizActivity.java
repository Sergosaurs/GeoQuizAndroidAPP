package com.example.geoquizz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFlaseButton;
    private TextView mQuestionTextView;
    private TextView mQuestionValidNum;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";


    //массив с вопросами
    private Question[] mQuestionList = new Question[]{
            new Question(R.string.question_blr, true),
            new Question(R.string.question_ua, false),
            new Question(R.string.question_kaz, true),
            new Question(R.string.question_ru, false),
            new Question(R.string.question_au, false),
            new Question(R.string.question_can, true),
    };

    private int mCurrentIndex = 0;
    private int mQuestionIndex = 0;
    private int mPercentIndex = 0;

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        //создаем View вопроса (берем из массива и подставляем во View)
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionValidNum = (TextView) findViewById(R.id.number_of_valid_quest);
        mQuestionValidNum.setText("Всего ответов: " + mQuestionIndex);

        //две кнопки да/нет
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                blockButtons(mTrueButton, mFlaseButton, false);

            }
        });

        mFlaseButton = findViewById(R.id.false_button);
        mFlaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                blockButtons(mTrueButton, mFlaseButton, false);
            }
        });

        //кнопка Next, при нажатии достает след вопрос
        Button nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionList.length;
                updateQuestion();
                blockButtons(mTrueButton, mFlaseButton, true);
                if (mQuestionIndex == mQuestionList.length) gameOver();
            }
        });
        updateQuestion();

        //кнопка Prev
        Button prevButton = (Button) findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex > 0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionList.length;
                    int question = mQuestionList[mCurrentIndex].getTextResId();
                    mQuestionTextView.setText(question);
                }
                if (mCurrentIndex == 0) {
                    mCurrentIndex = mQuestionList.length - 1;
                    int question = mQuestionList[mCurrentIndex].getTextResId();
                    mQuestionTextView.setText(question);
                }
                updateQuestion();
                blockButtons(mTrueButton, mFlaseButton, true);
            }
        });
        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    //Обновление вопроса
    private void updateQuestion() {
        int question = mQuestionList[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    //правильность ответов true/false
    @SuppressLint("SetTextI18n")
    private void checkAnswer(boolean userPressTrue) {
        boolean answerIsTrue = mQuestionList[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mPercentIndex++;
        } else messageResId = R.string.incorrect_toast;
        Toast toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 400);
        mQuestionIndex++;
        mQuestionValidNum.setText("Всего ответов: " + mQuestionIndex);
        toast.show();
    }

    //блокировка кнопок после ответа
    private void blockButtons(Button b1, Button b2, boolean state) {
        b1.setEnabled(state);
        b2.setEnabled(state);
    }

    private void gameOver() {
        AlertDialog.Builder alertBuider = new AlertDialog.Builder(QuizActivity.this);
        alertBuider.setMessage("Игра окочена! Процент правильных ответов: " + (100 * mPercentIndex) / mQuestionList.length)
                .setCancelable(false)
                .setPositiveButton("Новая Игра", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                        finish();
                    }
                }).setNegativeButton("Выход", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = alertBuider.create();
        alertBuider.show();
    }
}