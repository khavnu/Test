package who.is.millionnaire.model;

import java.util.List;

public class Question {

    public String content;
    public int level =0;
    public List<Answer> answerArray;

    public Question(String content, List<Answer>  answerArray) {
        this.content = content;
        this.level = 0;
        this.answerArray = answerArray;
    }

    public Question(String content,int level, List<Answer> answerArray) {
        this.content = content;
        this.level = level;
        this.answerArray = answerArray;
    }

}
