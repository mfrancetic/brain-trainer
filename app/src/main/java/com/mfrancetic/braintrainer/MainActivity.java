package com.mfrancetic.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button playAgainButton;

    private Button goButton;

    private TextView scoreTextView;

    private TextView timeRemainingTextView;

    private TextView taskTextView;

    private GridLayout solutionGridView;

    private TextView evaluationTextView;

    private TextView solution1TextView;

    private TextView solution2TextView;

    private TextView solution3TextView;

    private TextView solution4TextView;

    private long timeRemaining = 30100;

    private long timeInterval = 1000;

    private CountDownTimer timer;

    private int successfulTasks = 0;

    private int totalTasks = 0;

    ArrayList<Integer> answers = new ArrayList<>();

    private int randomNumberA;

    private int randomNumberB;

    private Random random;

    private int locationOfCorrectAnswer;

    private int wrongAnswer;

    private int correctAnswer;

    private int wrongAnswerBound = 41;

    private int taskBound = 21;

    private int answerLocationBound = 4;

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
        solution1TextView = findViewById(R.id.solution_1_text_view);
        solution2TextView = findViewById(R.id.solution_2_text_view);
        solution3TextView = findViewById(R.id.solution_3_text_view);
        solution4TextView = findViewById(R.id.solution_4_text_view);
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
        createNewTask();
        startTimer();
        updateScoreTextView(successfulTasks, totalTasks);
    }

    private void showGame() {
        goButton.setVisibility(View.INVISIBLE);
        solutionGridView.setVisibility(View.VISIBLE);
        taskTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        timeRemainingTextView.setVisibility(View.VISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        evaluationTextView.setVisibility(View.VISIBLE);
        solution1TextView.setClickable(true);
        solution2TextView.setClickable(true);
        solution3TextView.setClickable(true);
        solution4TextView.setClickable(true);
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
                long secondsUntilDoneLong = millisecondsUntilDone / 1000;
                updateTimer((int) secondsUntilDoneLong);
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
        solution1TextView.setClickable(false);
        solution2TextView.setClickable(false);
        solution3TextView.setClickable(false);
        solution4TextView.setClickable(false);
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

    private void createNewTask() {
        answers.clear();
        random = new Random();
        randomNumberA = random.nextInt(taskBound);
        randomNumberB = random.nextInt(taskBound);
        String taskText = randomNumberA + " + " + randomNumberB;
        taskTextView.setText(taskText);

        locationOfCorrectAnswer = random.nextInt(answerLocationBound);

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                correctAnswer = getCorrectAnswer();
                answers.add(correctAnswer);
            } else {
                wrongAnswer = random.nextInt(wrongAnswerBound);
                while (wrongAnswer == randomNumberA + randomNumberB) {
                    wrongAnswer = random.nextInt(wrongAnswerBound);
                }
                answers.add(wrongAnswer);
            }
        }
        updateSolutionsGrid(answers);
    }

    private void updateSolutionsGrid(ArrayList<Integer> answers) {
        solution1TextView.setText(String.valueOf(answers.get(0)));
        solution2TextView.setText(String.valueOf(answers.get(1)));
        solution3TextView.setText(String.valueOf(answers.get(2)));
        solution4TextView.setText(String.valueOf(answers.get(3)));
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

        createNewTask();
    }

    private void resetCounters() {
        successfulTasks = 0;
        totalTasks = 0;
    }

    private boolean isSolutionCorrect(int chosenSolution) {
        return chosenSolution == locationOfCorrectAnswer;
    }

    private int getCorrectAnswer() {
        return randomNumberA + randomNumberB;
    }
}