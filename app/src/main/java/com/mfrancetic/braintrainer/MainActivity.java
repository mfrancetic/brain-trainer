package com.mfrancetic.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

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

    private int successfulTasks = 0;

    private int totalTasks = 0;


    private int[][] tasksArray = {{2, 3}, {4, 5}, {7, 8}, {11, 5}, {3, 4},
            {9, 11}, {14, 16}, {1, 0}, {13, 19}, {12, 18},
            {33, 23}, {12, 2}, {1, 18}, {12, 33}, {4, 8}};

    private int[][] taskSolutionsArray = {{5, 8, 9, 10}, {1, 8, 9, 13}, {13, 15, 4, 12}, {21, 17, 15, 16}, {7, 11, 14, 13},
            {22, 21, 30, 20}, {32, 40, 30, 18}, {2, 3, 1, 4}, {42, 22, 32, 52}, {20, 30, 26, 38},
            {56, 54, 66, 64}, {16, 14, 22, 20}, {29, 20, 18, 19}, {42, 45, 35, 36}, {18, 14, 11, 12}
    };

    private int[] solutionsArray = {0, 2, 1, 3, 0,
            3, 2, 2, 0, 1,
            0, 1, 3, 1, 3};

    private int taskCounter = 0;

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
        startGame(view);
    }

    public void startGame(View view) {
        evaluationTextView.setText("");
        resetCounters();
        showGame();
        updateTask(taskCounter);
        startTimer();
        updateScoreTextView(successfulTasks, totalTasks);
        updateSolutionsGrid(taskCounter);
    }

    private void showGame() {
        goButton.setVisibility(View.INVISIBLE);
        solutionGridView.setVisibility(View.VISIBLE);
        taskTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        timeRemainingTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        evaluationTextView.setVisibility(View.VISIBLE);
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
        timer.cancel();
    }

    private void updateTimer(int secondsLeft) {
        String timeRemaining = secondsLeft + getString(R.string.seconds);
        timeRemainingTextView.setText(timeRemaining);
    }

    private void updateScoreTextView(int successfulTasks, int totalTasks) {
        String scoreText = successfulTasks + "/" + totalTasks;
        scoreTextView.setText(scoreText);
    }

    private void updateTask(int taskNumber) {
        int[] task = tasksArray[taskNumber];
        String taskText = task[0] + " + " + task[1];
        taskTextView.setText(taskText);
    }

    private void updateSolutionsGrid(int taskNumber) {
        int[] solution = taskSolutionsArray[taskNumber];
        solutionOneTextView.setText(String.valueOf(solution[0]));
        solutionTwoTextView.setText(String.valueOf(solution[1]));
        solutionThreeTextView.setText(String.valueOf(solution[2]));
        solutionFourTextView.setText(String.valueOf(solution[3]));
    }

    public void checkSolution(View view) {
        int chosenSolution = Integer.parseInt(view.getTag().toString());
        if (isSolutionCorrect(chosenSolution)) {
            evaluationTextView.setText(getString(R.string.correct));
            successfulTasks++;
        } else {
            evaluationTextView.setText(getString(R.string.wrong));
        }
        totalTasks++;
        updateScoreTextView(successfulTasks, totalTasks);

        if (areMoreTasksAvailable(taskCounter)) {
            displayNextTask();
        } else {
            displayNoMoreTasks();
        }
    }

    private void resetCounters() {
        taskCounter = 0;
        successfulTasks = 0;
        totalTasks = 0;
    }

    private boolean areMoreTasksAvailable(int taskCounter) {
        return taskCounter < (tasksArray.length - 1);
    }

    private void displayNextTask() {
        taskCounter++;
        updateTask(taskCounter);
        updateSolutionsGrid(taskCounter);
    }

    private void displayNoMoreTasks() {
        Toast.makeText(this, getString(R.string.no_more_tasks), Toast.LENGTH_LONG).show();
        displayGameOver();
    }

    private boolean isSolutionCorrect(int chosenSolution) {
        return chosenSolution == solutionsArray[taskCounter];
    }
}