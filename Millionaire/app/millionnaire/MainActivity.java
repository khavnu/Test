package who.is.millionnaire;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import who.is.millionnaire.model.Answer;
import who.is.millionnaire.model.Question;

public class MainActivity extends AppCompatActivity {


    private List<Question> questionDataLevel1 = new ArrayList<>();
    private List<Question> questionDataLevel2 = new ArrayList<>();
    private List<Question> questionDataLevel3 = new ArrayList<>();
    private List<Question> questionDataLevel4 = new ArrayList<>();

    private Question mCurrentQuestion;
    private List<Answer> mCurrentAnswerList;
    private int mCurrentQuestionIndex = 0;

    private TextView mTvQuestionContent;
    private TextView mTvQuestionNumber;
    private TextView mTvAnsA;
    private TextView mTvAnsB;
    private TextView mTvAnsC;
    private TextView mTvAnsD;
    private TextView mTvTimeCount;

    private TextView mTvFitftyPercentHelp;
    private ImageView mImgSpectorHelp;

    private LinearLayout mLayoutAnsA;
    private LinearLayout mLayoutAnsB;
    private LinearLayout mLayoutAnsC;
    private LinearLayout mLayoutAnsD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvQuestionNumber = findViewById(R.id.tv_score);
        mTvQuestionContent = findViewById(R.id.tv_question_content);
        mTvAnsA = findViewById(R.id.tv_answerA);
        mTvAnsB = findViewById(R.id.tv_answerB);
        mTvAnsC = findViewById(R.id.tv_answerC);
        mTvAnsD = findViewById(R.id.tv_answerD);
        mTvTimeCount = findViewById(R.id.tv_time_count);

        mTvFitftyPercentHelp = findViewById(R.id.fifty_percent_help);
        mImgSpectorHelp = findViewById(R.id.img_spector_help);

        mLayoutAnsA = findViewById(R.id.layout_ansA);
        mLayoutAnsB = findViewById(R.id.layout_ansB);
        mLayoutAnsC = findViewById(R.id.layout_ansC);
        mLayoutAnsD = findViewById(R.id.layout_ansD);

        mTvQuestionNumber.setText("");
        mTvQuestionContent.setText("");
        mTvAnsA.setText("");
        mTvAnsB.setText("");
        mTvAnsC.setText("");
        mTvAnsD.setText("");

        loadQuestionData();

        createQuestion();

        mLayoutAnsA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentAnswerList.get(0).checkRight()) {
                    onRightAnswer();
                } else {
                    onWrongAnswer();
                }
            }
        });

        mLayoutAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentAnswerList.get(1).checkRight()) {
                    onRightAnswer();
                } else {
                    onWrongAnswer();
                }
            }
        });

        mLayoutAnsC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentAnswerList.get(2).checkRight()) {
                    onRightAnswer();
                } else {
                    onWrongAnswer();
                }
            }
        });

        mLayoutAnsD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentAnswerList.get(3).checkRight()) {
                    onRightAnswer();
                } else {
                    onWrongAnswer();
                }
            }
        });

        mTvFitftyPercentHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitftyPercentHelp(mCurrentAnswerList);
            }
        });

        mImgSpectorHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spectorHelp(mCurrentAnswerList);
            }
        });
    }

    private void createQuestion() {
        //Tăng số câu hỏi đã trả lời
        mCurrentQuestionIndex++;
        //lấy dữ liệu bằng level- hiện tại (chỉ) mới lấy level 1
        if (questionDataLevel1.size() <= 0) {
            showDialogOutOfQuestionData();
            return;
        }
        //hoán đổi ngẫu nhiên dữ liêu, lấy phần tử đầu tiên (vì dữ liệu đã được hoán đổi ngẫu nhiên nên không cần lấy ngẫu nhiên nữa)
        Collections.shuffle(questionDataLevel1);
        mCurrentQuestion = questionDataLevel1.get(0);
        mCurrentAnswerList = mCurrentQuestion.answerArray;

        //Xóa phần tử đầu tiên khỏi danh sách câu hỏi để tránh trùng lặp
        questionDataLevel1.remove(0);
        Log.d("CheckQuest", questionDataLevel1.size() + "");
        Collections.shuffle(mCurrentAnswerList);

        //Hiển thị lại toàn bộ câu trả lời - ẩn trong trợ giúp 50-50
        mTvAnsA.setVisibility(View.VISIBLE);
        mTvAnsB.setVisibility(View.VISIBLE);
        mTvAnsC.setVisibility(View.VISIBLE);
        mTvAnsD.setVisibility(View.VISIBLE);


        mTvQuestionContent.setText(mCurrentQuestion.content);
        mTvAnsA.setText(mCurrentAnswerList.get(0).getContent());
        mTvAnsB.setText(mCurrentAnswerList.get(1).getContent());
        mTvAnsC.setText(mCurrentAnswerList.get(2).getContent());
        mTvAnsD.setText(mCurrentAnswerList.get(3).getContent());
        mTvQuestionNumber.setText(mCurrentQuestionIndex + "");
        cancelTimeCountDown();
        startTimeCountDown();
    }


    private void loadQuestionData() {
        questionDataLevel1.add(new Question("1+1 = ?", Arrays.asList(new Answer("1", false)
                , new Answer("2", true), new Answer("3", false), new Answer("4", false))));
        questionDataLevel1.add(new Question("Quốc gia nào có diện tích lớn nhất thế giới?", Arrays.asList(new Answer("Nga", true)
                , new Answer("Mỹ", false), new Answer("Trung quốc", false), new Answer("Việt Nam", false))));
        questionDataLevel1.add(new Question("1 thiên niên kỉ có bao nhiêu năm?", Arrays.asList(new Answer("10", false)
                , new Answer("100", false), new Answer("1000", true), new Answer("10000", false))));
        questionDataLevel1.add(new Question("1+1 = ?", Arrays.asList(new Answer("1", false)
                , new Answer("2", true), new Answer("3", false), new Answer("4", false))));
        questionDataLevel1.add(new Question("Quốc khánh của nước Cộng hòa xã hội chủ nghĩa Việt Nam là ngày nào?", Arrays.asList(new Answer("2-9", true)
                , new Answer("9-2", false), new Answer("30-4", false), new Answer("19-5", false))));
        questionDataLevel1.add(new Question("Quốc gia nào đông dân nhất thế giới hiện nay?", Arrays.asList(new Answer("Mỹ", false)
                , new Answer("Ấn Độ", false), new Answer("Trung Quốc", true), new Answer("Nga", false))));
        questionDataLevel1.add(new Question("1+2 = ?", Arrays.asList(new Answer("1", false)
                , new Answer("2", false), new Answer("3", true), new Answer("4", false))));
        questionDataLevel1.add(new Question("Ai là người đẹp trai(xinh gái) nhất hiện nay?", Arrays.asList(new Answer("100% là tôi", true)
                , new Answer("Iron Man", false), new Answer("Thor", false), new Answer("Captian America", false))));
        questionDataLevel1.add(new Question("1+2 = ?", Arrays.asList(new Answer("1", false)
                , new Answer("2", false), new Answer("3", true), new Answer("4", false))));
        questionDataLevel1.add(new Question("Tháng chạp là tháng mấy tính theo lịch âm?", Arrays.asList(new Answer("1", false)
                , new Answer("7", false), new Answer("12", true), new Answer("2", false))));
        questionDataLevel1.add(new Question("Tháng giêng là tháng mấy tính theo lịch âm?", Arrays.asList(new Answer("1", true)
                , new Answer("7", false), new Answer("12", false), new Answer("2", false))));
        questionDataLevel1.add(new Question("12+10 = ?", Arrays.asList(new Answer("12", false)
                , new Answer("25", false), new Answer("23", false), new Answer("22", true))));
        questionDataLevel1.add(new Question("Đâu là đơn vị tiền tệ chính thức của Vương quốc Anh?", Arrays.asList(new Answer("Đồng Bảng(Pound)", true)
                , new Answer("Đô la(USD)", false), new Answer("Bạt(Bath)", false), new Answer("Ơ rô(Euro)", false))));
        questionDataLevel1.add(new Question("Tại Hà Nội thì ghi 10đ lô hết bao nhiêu tiền", Arrays.asList(new Answer("230 000", true)
                , new Answer("250 000", false), new Answer("200 000", false), new Answer("150 000", false))));
        questionDataLevel1.add(new Question("Tại Hà Nội thì trà đá bao nhiêu tiền 1 cốc", Arrays.asList(new Answer("3 000", true)
                , new Answer("5 000", false), new Answer("2 000", false), new Answer("6 000", false))));
        questionDataLevel1.add(new Question("Tại Hà Nội thì  đánh 10k đề khi trúng được ăn bao nhiêu?", Arrays.asList(new Answer("70 000", true)
                , new Answer("80 000", false), new Answer("75 000", false), new Answer("90 000", false))));
        questionDataLevel1.add(new Question("Tại Hà Nội thì đánh 10 điểm lô khi trúng được ăn bao nhiêu?", Arrays.asList(new Answer("800 000", true)
                , new Answer("810 000", false), new Answer("750 000", false), new Answer("900 000", false))));
        questionDataLevel1.add(new Question("250 : 5 = ?", Arrays.asList(new Answer("50", true)
                , new Answer("55", false), new Answer("40", false), new Answer("45", false))));
        questionDataLevel1.add(new Question("100 x 5 = ?", Arrays.asList(new Answer("500", true)
                , new Answer("550", false), new Answer("450", false), new Answer("600", false))));
    }

    private void onRightAnswer() {

        createQuestion();
    }

    private void onWrongAnswer() {
        showDiaglogWrongAnswer();
    }

    private void reloadData() {
        loadQuestionData();
        mCurrentQuestionIndex = 0;
        createQuestion();
        // Cho phép Trợ giúp 50/50
        mTvFitftyPercentHelp.setClickable(true);
        mTvFitftyPercentHelp.setBackgroundResource(R.drawable.rounded_bg);
        //Cho phép trợ giúp khán giả
        mImgSpectorHelp.setClickable(true);
        mImgSpectorHelp.setColorFilter(ContextCompat.getColor(this, R.color.cyan_text));
    }

    private void showDiaglogWrongAnswer() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.replay_title)
                .setMessage(R.string.replay_mes)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        reloadData();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void showDialogOutOfQuestionData() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.out_of_question_tittle)
                .setMessage(R.string.out_of_question_content)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Thêm câu hỏi, hiện tại mới có 17 câu hỏi -> tải lại sổ câu hỏi
                        reloadData();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    //Todo: phát âm thanh
    private void playSound(int soundResource) {

    }

    private Timer mTimer;
    private static final int LIMIT_TIME = 15;
    private int mLimitTime = LIMIT_TIME;

    private boolean mPauseTimeCounter = false;

    private void startTimeCountDown() {

        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!mPauseTimeCounter) {
                                mLimitTime--;
                            }
                            mTvTimeCount.setText(mLimitTime + "");
                            if (mLimitTime <= 0) {
                                cancel();
                                cancelTimeCountDown();
                                showDiaglogWrongAnswer();
                            }
                        }
                    });
                }
            }, 0, 1000);
        }

    }

    private void cancelTimeCountDown() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mLimitTime = LIMIT_TIME;
        mTvTimeCount.setText(mLimitTime + "");
    }

    private void pauseTimeCounter() {
        mPauseTimeCounter = true;
    }

    private void resumeTimeCounter() {
        mPauseTimeCounter = false;
    }

    //Todo: Xử lí các sự trợ giúp
    private void fitftyPercentHelp(List<Answer> input) {
        //Ẩn toàn bộ câu trả lời
        mTvAnsA.setVisibility(View.GONE);
        mTvAnsB.setVisibility(View.GONE);
        mTvAnsC.setVisibility(View.GONE);
        mTvAnsD.setVisibility(View.GONE);

        Random rand = new Random();
        int randIndex = rand.nextInt(input.size());

        for (int i = 0; i < input.size(); i++) {
            Answer ans = input.get(i);
            if (ans.checkRight()) {
                //Hiển thị câu trả lời đúng
                if (i == 0) {
                    mTvAnsA.setVisibility(View.VISIBLE);
                }
                if (i == 1) {
                    mTvAnsB.setVisibility(View.VISIBLE);
                }
                if (i == 2) {
                    mTvAnsC.setVisibility(View.VISIBLE);
                }
                if (i == 3) {
                    mTvAnsD.setVisibility(View.VISIBLE);
                }
                // Xử lí khi randIndex trùng với câu trả lời đúng và là số cuối(hoạc đầu) trong danh sách câu trả lời
                if (randIndex == i) {
                    if (randIndex >= input.size()) {
                        randIndex -= 1;
                    }
                    if (randIndex == 0) {
                        randIndex += 1;
                    }
                }
                //hiển thị thêm câu trả lời sai
                if (randIndex == 0) {
                    mTvAnsA.setVisibility(View.VISIBLE);
                }
                if (randIndex == 1) {
                    mTvAnsB.setVisibility(View.VISIBLE);
                }
                if (randIndex == 2) {
                    mTvAnsC.setVisibility(View.VISIBLE);
                }
                if (randIndex == 3) {
                    mTvAnsD.setVisibility(View.VISIBLE);
                }
            }
        }
        // Không cho phép Trợ giúp 50/50
        mTvFitftyPercentHelp.setClickable(false);
        mTvFitftyPercentHelp.setBackgroundResource(R.drawable.rounded_bg_disable);
    }

    private void spectorHelp(List<Answer> input) {
        pauseTimeCounter();
        showDialogSpectorHelp(input);
        //Không cho phép trợ giúp từ khán giả
        mImgSpectorHelp.setClickable(false);
        mImgSpectorHelp.setColorFilter(ContextCompat.getColor(this, R.color.gray));

    }

    //Nếu là câu trả lời đúng thì tỉ lệ > 30%
    private static final int RIGHT_MIN_RATIO = 30;

    private void showDialogSpectorHelp(final List<Answer> input) {
        final LinearLayout percentA;
        final LinearLayout percentB;
        final LinearLayout percentC;
        final LinearLayout percentD;

        final TextView tvPercentA;
        final TextView tvPercentB;
        final TextView tvPercentC;
        final TextView tvPercentD;

        final LinearLayout layoutMaxHeight;

        Button btnOK;

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_spector_help);

        percentA = dialog.findViewById(R.id.percent_A);
        percentB = dialog.findViewById(R.id.percent_B);
        percentC = dialog.findViewById(R.id.percent_C);
        percentD = dialog.findViewById(R.id.percent_D);

        tvPercentA = dialog.findViewById(R.id.tv_percentA);
        tvPercentB = dialog.findViewById(R.id.tv_percentB);
        tvPercentC = dialog.findViewById(R.id.tv_percentC);
        tvPercentD = dialog.findViewById(R.id.tv_percentD);

        btnOK = dialog.findViewById(R.id.btn_ok);

        layoutMaxHeight = dialog.findViewById(R.id.layout_max_height);

        layoutMaxHeight.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    layoutMaxHeight.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    layoutMaxHeight.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                //Lấy tỉ lệ ngẫu nhiên
                Random rand = new Random();
                int randRightRatio = 0;
                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).checkRight()) {
                        randRightRatio = RIGHT_MIN_RATIO + rand.nextInt(101 - RIGHT_MIN_RATIO);
                    }
                }

                int randWrong1Ratio = rand.nextInt(101 - randRightRatio);
                int randWrong2Ratio = rand.nextInt(101 - randRightRatio - randWrong1Ratio);
                int randWrong3Ratio = 100 - randRightRatio - randWrong1Ratio - randWrong2Ratio;

                //Lấy chiều cao tối đa của các cột
                int maxHeight = layoutMaxHeight.getHeight() - 40;

                //Danh sách các cột(tiêu đề)  đáp án sai - Lúc đầu chứa cả đáp án đúng
                List<LinearLayout> wrongAnswerColumn = new ArrayList<LinearLayout>();
                wrongAnswerColumn.add(percentA);
                wrongAnswerColumn.add(percentB);
                wrongAnswerColumn.add(percentC);
                wrongAnswerColumn.add(percentD);
                List<TextView> wrongAnswerTv = new ArrayList<TextView>();
                wrongAnswerTv.add(tvPercentA);
                wrongAnswerTv.add(tvPercentB);
                wrongAnswerTv.add(tvPercentC);
                wrongAnswerTv.add(tvPercentD);

                for (int i = 0; i < input.size(); i++) {
                    if (input.get(i).checkRight()) {
                        //Nếu là câu trả lời đúng thì đưa tỉ lệ đúng vào đặt chiều cao tương ứng và xóa cột đúng tương ứng trong danh sách sai
                        if (i == 0) {
                            tvPercentA.setText(randRightRatio + "%");
                            setLayoutHeight(percentA, maxHeight * randRightRatio / 100);
                            wrongAnswerColumn.remove(percentA);
                            wrongAnswerTv.remove(tvPercentA);
                        }

                        if (i == 1) {
                            tvPercentB.setText(randRightRatio + "%");
                            setLayoutHeight(percentB, maxHeight * randRightRatio / 100);
                            wrongAnswerColumn.remove(percentB);
                            wrongAnswerTv.remove(tvPercentB);
                        }

                        if (i == 2) {
                            tvPercentC.setText(randRightRatio + "%");
                            setLayoutHeight(percentC, maxHeight * randRightRatio / 100);
                            wrongAnswerColumn.remove(percentC);
                            wrongAnswerTv.remove(tvPercentC);
                        }

                        if (i == 3) {
                            tvPercentD.setText(randRightRatio + "%");
                            setLayoutHeight(percentD, maxHeight * randRightRatio / 100);
                            wrongAnswerColumn.remove(percentD);
                            wrongAnswerTv.remove(tvPercentD);
                        }
                    }
                }


                //Hiển thị tỉ lệ các câu sai, vì tỉ lệ đã được lấy ngẫu nhiên nên chỉ cần đặt tương ứng như bình thường cho các cột còn lại
                for (int i = 0; i < wrongAnswerColumn.size(); i++) {
                    if (i == 0) {
                        setLayoutHeight(wrongAnswerColumn.get(i), maxHeight * randWrong1Ratio / 100);
                        wrongAnswerTv.get(i).setText(randWrong1Ratio + "%");

                    }
                    if (i == 1) {
                        setLayoutHeight(wrongAnswerColumn.get(i), maxHeight * randWrong2Ratio / 100);
                        wrongAnswerTv.get(i).setText(randWrong2Ratio + "%");
                    }
                    if (i == 2) {
                        setLayoutHeight(wrongAnswerColumn.get(i), maxHeight * randWrong3Ratio / 100);
                        wrongAnswerTv.get(i).setText(randWrong3Ratio + "%");
                    }
                }

            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                resumeTimeCounter();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setLayoutHeight(LinearLayout layout, int height) {
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = height;
        layout.setLayoutParams(params);
    }
}
