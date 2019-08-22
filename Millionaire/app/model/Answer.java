package who.is.millionnaire.model;

public class Answer {
    private String mContent;
    private boolean mIsRight = false;

    public Answer(String content, boolean isRight) {
        this.mContent = content;
        this.mIsRight = isRight;
    }

    public String getContent() {
        return this.mContent;
    }

    public boolean checkRight() {
        return mIsRight;
    }
}
