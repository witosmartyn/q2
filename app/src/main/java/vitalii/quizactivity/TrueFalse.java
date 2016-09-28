package vitalii.quizactivity;

//MODEL

public class TrueFalse {
    private int mQuestion;
    private boolean mTrueQuestion;
    private boolean mIsCheater;

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }

    public TrueFalse(int mQuestion, boolean mTrueQuestion, boolean mIsCheater) {
        this.mQuestion = mQuestion;
        this.mTrueQuestion = mTrueQuestion;
        this.mIsCheater = mIsCheater;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
