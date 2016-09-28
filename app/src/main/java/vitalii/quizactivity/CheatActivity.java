package vitalii.quizactivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
    public static final String TAG = "Q";
    public static final String EXTRA_ANSWER_IS_TRUE = "vitalii.quizactivity.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN_OR_NOT = "vitalii.quizactivity.answer_shown";
    public static final String IS_ANSWER_SHOWN = "IS_ANSWER_SHOWN";
    private boolean isAnswerShown;
    private Button mShowAnswer;
    private TextView mTextView;
    private boolean mAnswerIsTrue;

    /**
     * создает макет из пакета если токовой был сохранен
     */
//Create
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mTextView = (TextView) findViewById(R.id.answerTextView);
        /**
         * добавляет слушателя
         */
//mShowAnswer
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click(mShowAnswer)=>setText = " + mAnswerIsTrue);
                setAnswerShownResult(true);//в случае нажатия
                if (mAnswerIsTrue) {
                    mTextView.setText(R.string.true_button);
                } else {
                    mTextView.setText(R.string.false_button);
                }

            }
        });
    }

    /**
     * устанавливает был ли подсмотрен ответ
     */
//ShownResult
    private void setAnswerShownResult(boolean isAnswerShown) {
        this.isAnswerShown = isAnswerShown;
        Log.d(TAG, "setAnswerShownResult(" + isAnswerShown + ")");
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN_OR_NOT, isAnswerShown);
        setResult(RESULT_OK, data);
        Log.d(TAG, " packExtra(EXTRA(" + isAnswerShown + "))");

    }

    /**
     * сохраняет состояние перед возвратом или поворотом
     */
//Save
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_ANSWER_SHOWN, isAnswerShown);
        Log.d(TAG, "onSaveInstanceState()isAnswerShown = " + isAnswerShown);
    }

    /**
     * востанавливает состояние после возврата или поворота
     */
//Restore
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.isAnswerShown = savedInstanceState.getBoolean(IS_ANSWER_SHOWN, false);
        Log.d(TAG, "onRestoreInstanceState()isAnswerShown = " + isAnswerShown);
        setAnswerShownResult(isAnswerShown);
    }
}

