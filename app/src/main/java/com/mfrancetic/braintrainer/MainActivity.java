package com.mfrancetic.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button playAgainButton;

    private Button goButton;

    private TextView scoreTextView;

    private TextView timeRemainingTextView;

    private TextView taskTextView;

    private GridLayout solutionGridView;

    private TextView evaluationTextView;

    private TextView solutionOneTextView;

    private TextView solutionTwoTextView;

    private TextView solutionThreeTextView;

    private TextView solutionFourTextView;

    private long timeRemaining = 30000;

    private long timeInterval = 1000;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = findViewById(R.id.play_again_button);
        goButton = findViewById(R.id.go_button);
        timeRemainingTextView = findViewById(R.id.time_remaining_text_view);
        scoreTextView = findViewById(R.id.score_text_view);
        taskTextView = findViewById(R.id.task_text_view);
        solutionGridView = findViewById(R.id.solutions_grid_view);
        solutionOneTextView = findViewById(R.id.solution_1_text_view);
        solutionTwoTextView = findViewById(R.id.solution_2_text_view);
        solutionThreeTextView = findViewById(R.id.solution_3_text_view);
        solutionFourTextView = findViewById(R.id.solution_4_text_view);
        evaluationTextView = findViewById(R.id.evaluation_text_field);

        showGoButton();
    }

    public void playAgain(View view) {
        showGame();
        startTimer();
    }

    public void startGame(View view) {
        showGame();
        startTimer();
    }

    private void showGame() {
        goButton.setVisibility(View.INVISIBLE);
        solutionGridView.setVisibility(View.VISIBLE);
        taskTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        timeRemainingTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        evaluationTextView.setVisibility(View.INVISIBLE);
        solutionGridView.setClickable(true);
    }

    private void showGoButton() {
        goButton.setVisibility(View.VISIBLE);
        solutionGridView.setVisibility(View.INVISIBLE);
        taskTextView.setVisibility(View.INVISIBLE);
        scoreTextView.setVisibility(View.INVISIBLE);
        timeRemainingTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        evaluationTextView.setVisibility(View.INVISIBLE);

    }

    private void startTimer() {
         timer = new CountDownTimer(timeRemaining, timeInterval) {
            @Override
            public void onTick(long millisecondsUntilDone) {
                long millisecondsTillDoneLong = millisecondsUntilDone / 1000;
                updateTimer((int) millisecondsTillDoneLong);
            }

            @Override
            public void onFinish() {
                timer.cancel();
                updateTimer(0);
                displayGameOver();
            }
        }.start();
    }

    private void displayGameOver() {
        evaluationTextView.setVisibility(View.VISIBLE);
        evaluationTextView.setText(getString(R.string.done));
        playAgainButton.setVisibility(View.VISIBLE);
        solutionGridView.setClickable(false);
    }

    private void updateTimer(int secondsLeft) {
        String timeRemaining = secondsLeft + getString(R.string.seconds);
        timeRemainingTextView.setText(timeRemaining);
    }
}