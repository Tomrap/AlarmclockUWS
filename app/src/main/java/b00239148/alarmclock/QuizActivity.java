package b00239148.alarmclock;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    SongQuiz Quiz;
    int rightAnswer = 0;
    int tries = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Test data exchange between Activities
        Intent test_intent = getIntent();
        TextView textview = (TextView) findViewById(R.id.testTimeViewID);
        textview.setText("Picked Time: " + test_intent.getStringExtra("hour") + ":" + test_intent.getStringExtra("min"));
        //-------------------------------------

        ((TextView) findViewById(R.id.textViewTries_id)).setText("Tries: " + tries);


        Button submitBtn = (Button) findViewById(R.id.submitBtn_id);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_id);

                if(((RadioButton) radioGroup.getChildAt(rightAnswer)).isChecked()){
                    Toast.makeText(getApplicationContext(), "Hooray!!!", Toast.LENGTH_SHORT).show();

                    //Stop Music!

                    onBackPressed();
                }
                else if(radioGroup.getCheckedRadioButtonId() != -1){
                    Toast.makeText(getApplicationContext(), "Wrong answer", Toast.LENGTH_SHORT).show();
                    loadQuizData();
                    Log.d("TAG","wrong");
                }else{
                    Toast.makeText(getApplicationContext(), "Please choose an answer!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        try {
            loadQuizData();
        }
        catch (Exception e){
            Log.d("Error","Error Loading Quiz");
            Log.d("Error",e.getMessage());
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadQuizData(){

        //Here some random function to decide which kind of quiz
        if(Math.floor((Math.random()*2)) == 0)
            Quiz = new TitleQuiz();
        else
            Quiz = new CoverQuiz();


        //TODO make it compatible for both sub classes
        //Object[] answers;
        String[] answers;

        /*if (Quiz instanceof TitleQuiz){
            answers = new String[((String[]) Quiz.getAnswers()).length];
            for (int i=0; i < answers.length; i++){
                answers[i] = ((String[]) Quiz.getAnswers())[i];
            }
        }
        else if(Quiz.getAnswers() instanceof Drawable[]){                       //Subclass of SongQuiz
            answers = new Drawable[((Drawable[]) Quiz.getAnswers()).length];
            for (int i=0; i < answers.length; i++){
                answers[i] = ((Drawable[]) Quiz.getAnswers())[i];
            }
        }
        else{
            answers = null;
        }*/

        //Set question
        ((TextView) findViewById(R.id.questionText)).setText(R.string.question_title);

        answers = new String[((ArrayList<String[]>) Quiz.getAnswers()).size()];

        if(answers != null) {
            for (int i = 0; i < answers.length; i++) {
                answers[i] = (((ArrayList<String[]>) Quiz.getAnswers()).get(i))[0];

                //Save right answer
                if ((((ArrayList<String[]>) Quiz.getAnswers()).get(i))[1] == "true") {
                    Log.d("TAG", "gefunden");
                    rightAnswer = i;
                }
            }
        }else
            answers = new String[0];






        //Get RadioButtons---------------------
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_id);
        Log.d("TAG","gefunden: "+radioGroup.getChildCount() );

        int tempChildCount = radioGroup.getChildCount();
        for (int i=0; i<tempChildCount; i++)
        {
            radioGroup.removeViewAt(0);
        }


        try {
            for (int i=0; i < answers.length; i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(answers[i]);
                radioButton.setId((int) i+1);//set radiobutton id and store it somewhere
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                radioGroup.addView(radioButton, params);
            }
        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }

        radioGroup.clearCheck();

        ArrayList<RadioButton> radioButtonsList = new ArrayList<RadioButton>();

        for (int i=0;i<radioGroup.getChildCount();i++) {
            View rb = radioGroup.getChildAt(i);
            if (rb instanceof RadioButton) {
                radioButtonsList.add((RadioButton)rb);
            }
        }
        Log.d("TAG","you have "+radioButtonsList.size()+" radio buttons");


        //((RadioButton) radioButtonsList.toArray()[0]).setText("test");
        //--------------------------------------
        ((TextView) findViewById(R.id.textViewTries_id)).setText("Tries: " + ++tries);

    }

    //Not used anymore----------------------
    public void changeToSettings(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    //--------------------------------------

    private void findbyIDs(){
        //But all findsbyIds here:
    }


}
