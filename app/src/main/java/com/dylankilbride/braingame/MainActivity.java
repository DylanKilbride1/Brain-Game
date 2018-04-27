package com.dylankilbride.braingame;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  CountDownTimer timer;
  TextView timeGone, sumTextView, answerTextView, resultTextView;
  Button starGameButton, answer1, answer2, answer3, answer4;
  int sumAnswer, leftSideOfEquation, rightSideOfEquation;
  int totalNumberOfAttempts = 0;
  int totalCorrectAnswers = 0;
  boolean gameOngoing = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    createSum();
    updateCenterTextViews();
    resultTextView = (TextView) findViewById(R.id.resultTextView);
    answerTextView = (TextView) findViewById(R.id.correctTextView);


  }

  public void startTimer() {
    gameOngoing = true;
    updateCenterTextViews();
    answerTextView.setText("GO!");
    answerTextView.setTextColor(Color.parseColor("#1bb70c"));
    totalNumberOfAttempts = 0;
    totalCorrectAnswers = 0;
    timer = new CountDownTimer(30100, 1000) {
      @Override
      public void onTick(long milisecondsUntilDone) {
        timeGone = (TextView) findViewById(R.id.timerTextView);
        timeGone.setText("You've got " + Long.toString(milisecondsUntilDone / 1000) + " seconds!");
        starGameButton.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onFinish() {
        gameOngoing = false;
        timeGone.setText("OUT OF TIME!");
        resetTimer();
        if(resultTextView.getText().toString().length()<=0){
          answerTextView.setText("Attempt some questions!");
        } else {
          answerTextView.setText("You got " + totalCorrectAnswers + " out of " + totalNumberOfAttempts + " correct!");
        }
      }
    }.start();
  }

  public void resetTimer() {
    starGameButton.setVisibility(View.VISIBLE);
    starGameButton.setText("REPLAY");
  }

  public int createSum() {
    Random random = new Random();
    leftSideOfEquation = random.nextInt(30 + 1) + 1;
    rightSideOfEquation = random.nextInt(30 + 1) + 1;
    sumAnswer = leftSideOfEquation + rightSideOfEquation;
    sumTextView = (TextView) findViewById(R.id.sumTextView);
    sumTextView.setText(leftSideOfEquation + " + " + rightSideOfEquation);
    return sumAnswer;
  }

  public void updateCenterTextViews() {
    int answer = createSum();
    Random number = new Random();
    int correctAnswerPlaceHolder = number.nextInt(1+4);
    int randomDigitsInRange1, randomDigitsInRange2, randomDigitsInRange3;
    Log.e("Random Placeholder:", String.valueOf(correctAnswerPlaceHolder));
    answer1 = (Button)findViewById(R.id.answer1);
    answer2 = (Button)findViewById(R.id.answer2);
    answer3 = (Button)findViewById(R.id.answer3);
    answer4 = (Button)findViewById(R.id.answer4);
    if(correctAnswerPlaceHolder == 1){
      answer1.setText(String.valueOf(answer));
    }
    else if(correctAnswerPlaceHolder == 2){
      answer2.setText(String.valueOf(answer));
    }
    else if(correctAnswerPlaceHolder == 3){
      answer3.setText(String.valueOf(answer));
    }
    else{
      answer4.setText(String.valueOf(answer));
    }

    randomDigitsInRange1 = number.nextInt(60 - 1) + 1;
    randomDigitsInRange2 = number.nextInt(60 - 1) + 1;
    randomDigitsInRange3  = number.nextInt(60 - 1) + 1;

      if(correctAnswerPlaceHolder == 1){
        answer2.setText(String.valueOf(randomDigitsInRange1));
        answer3.setText(String.valueOf(randomDigitsInRange2));
        answer4.setText(String.valueOf(randomDigitsInRange3));
      } else if(correctAnswerPlaceHolder == 2) {
        answer3.setText(String.valueOf(randomDigitsInRange1));
        answer4.setText(String.valueOf(randomDigitsInRange2));
        answer1.setText(String.valueOf(randomDigitsInRange3));
      } else if (correctAnswerPlaceHolder == 3){
        answer4.setText(String.valueOf(randomDigitsInRange1));
        answer2.setText(String.valueOf(randomDigitsInRange2));
        answer1.setText(String.valueOf(randomDigitsInRange3));
      } else {
        answer1.setText(String.valueOf(randomDigitsInRange1));
        answer2.setText(String.valueOf(randomDigitsInRange2));
        answer3.setText(String.valueOf(randomDigitsInRange3));
      }
    }

  public void startGame(View view) {
    starGameButton = (Button) findViewById(R.id.startButton);
    startTimer();
    resultTextView.setText("0/0");
  }

  public void checkIfAnswerIsCorrect(int answerFromButton) {
    if(answerFromButton == (leftSideOfEquation + rightSideOfEquation)){
      answerTextView.setText("CORRECT");
      updateOngoingResult("correct");
      answerTextView.setTextColor(Color.parseColor("#1bb70c"));
    } else{
      answerTextView.setText("INCORRECT");
      updateOngoingResult("incorrect");
      answerTextView.setTextColor(Color.parseColor("#cc0c12"));
    }
  }

  public void selectAnswerFromButtons(View view) { //Grid buttons being pushed method
    if(gameOngoing) {
      answer1 = (Button) findViewById(R.id.answer1);
      answer2 = (Button) findViewById(R.id.answer2);
      answer3 = (Button) findViewById(R.id.answer3);
      answer4 = (Button) findViewById(R.id.answer4);
      switch (view.getId()) {
        case R.id.answer1:
          checkIfAnswerIsCorrect(Integer.parseInt(answer1.getText()
              .toString()));
              updateCenterTextViews();
          break;
        case R.id.answer2:
          checkIfAnswerIsCorrect(Integer.parseInt(answer2.getText()
              .toString()));
          updateCenterTextViews();
          break;
        case R.id.answer3:
          checkIfAnswerIsCorrect(Integer.parseInt(answer3.getText()
              .toString()));
          updateCenterTextViews();
          break;
        case R.id.answer4:
          checkIfAnswerIsCorrect(Integer.parseInt(answer4.getText()
              .toString()));
          updateCenterTextViews();
          break;
      }
    }
  }

    public void updateOngoingResult(String CorrectOrIncorrect){

      if(CorrectOrIncorrect.equals("correct")){
        totalCorrectAnswers += 1;
        totalNumberOfAttempts += 1;
        resultTextView.setText(totalCorrectAnswers + "/" + totalNumberOfAttempts);
      } else {
        totalNumberOfAttempts += 1;
        resultTextView.setText(totalCorrectAnswers + "/" + totalNumberOfAttempts);
      }

    }
}
