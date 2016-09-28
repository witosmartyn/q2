package vitalii.quizactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

// CONTROLLER  Activity = Викторина
public class QuizActivity extends Activity {
    private final static String TAG = "Q";
    private final static String MCURRENT_INDEX = "MCURRENT_INDEX";
    private static final String MISCHEATER = "MISCHEATER";
    private boolean mIsCheater;
    private Button mTrueButton, mFalseButton, mCheatButton;
    private ImageButton mNextButton, mPrevButton;
    private TextView mQuestionTextView;
    private int mCurrentIndex;
    /**
     * обьект хранящий {вопросы},{ответы},{инф был ли подсмотрен ответ};
     */
    TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse(R.string.question_text1, false, false),
            new TrueFalse(R.string.question_text2, false, false),
            new TrueFalse(R.string.question_text3, true, false),
            new TrueFalse(R.string.question_text4, true, false),
    };
//Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mCurrentIndex = (int) savedInstanceState.get("MCURRENT_INDEX");
            mIsCheater = (savedInstanceState.getBoolean(MISCHEATER, mIsCheater));
            Log.d(TAG, "extract mCurrentIndex = " + mCurrentIndex);
            Log.d(TAG, "extract mCurrentIndex = " + mIsCheater);
        }
        Log.d(TAG, "onCreate" + mCurrentIndex);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_guestion_button);
        mPrevButton = (ImageButton) findViewById(R.id.prev_guestion_button);
        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

//true
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                Log.d(TAG, "mTrueButton");
            }
        });
//false
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mFalseButton");
                checkAnswer(false);
            }
        });
//nest
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCheatInfo();
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
//Prev
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCheatInfo();
                mCurrentIndex = mCurrentIndex == 0 ? mQuestionBank.length - 1 : mCurrentIndex - 1;
                updateQuestion();
                Log.d(TAG, "mPrevButton" + mCurrentIndex);
            }
        });
//Cheat
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "mCheatButton(start CheatActivity)");
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i, 0);
            }
        });

//TextView
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mQuestionBank[mCurrentIndex].setCheater(mIsCheater);
                updateCheatInfo();
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                Log.d(TAG, "ClickTextView" + mCurrentIndex);
                updateQuestion();

            }
        });
        updateQuestion();
    }
//Save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putInt(MCURRENT_INDEX, mCurrentIndex);
        outState.putBoolean(MISCHEATER, mIsCheater);
        Log.d(TAG, "save mCurrentIndex = " + mCurrentIndex);
        Log.d(TAG, "save mIsCheater = " + mIsCheater);
    }

//extract Extra
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        } else mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN_OR_NOT, false);
    }

    /**
     * Проверка ответа, в случае подглядывания выводится сообщение о том что читтерство это плохо
     */
//check
    private void checkAnswer(boolean userPressed) {
        Log.d(TAG, "checkAnswer(" + userPressed + ")");
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
        int messageResID;
        if (mIsCheater) {
            messageResID = R.string.judgment_toast;
        } else {
            if (userPressed == answerIsTrue) {
                messageResID = R.string.correct_toast;
            } else {
                messageResID = R.string.incorrect_toast;
            }
        }
        Log.d(TAG, "Toast(" + messageResID + ")");
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show();
    }

    /**
     * Сохранение информации о читтерстве ;
     */
//CheatInfo
    private void updateCheatInfo() {
        Log.d(TAG, "updateCheatInfo " + mIsCheater + " =>> " + mQuestionBank[mCurrentIndex].isCheater());
        mQuestionBank[mCurrentIndex].setCheater(mIsCheater);


    }

    /**
     * Обновление вопроса;
     * Обновление mIsCheater <== из хранилища
     * Обновление текста вопроса
     */
//Question
    private void updateQuestion() {
        Log.d(TAG, "updateQuestion to " + mCurrentIndex);
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mIsCheater = mQuestionBank[mCurrentIndex].isCheater();
        mQuestionTextView.setText(question);
    }
}
