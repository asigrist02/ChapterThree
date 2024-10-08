package com.example.chapterthree

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.example.chapterthree.databinding.ActivityMainBinding

private const val  TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    private lateinit var binding:ActivityMainBinding
    //lateinit' allows initializing a not-null property outside of a constructor

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)

    )

    private var currentIndex = 0
    private var correctAnswers = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate(Bundle) called")

        // trueButton = findViewById(R.id.true_button)
        // falseButton = findViewById(R.id.false_button)


        binding.trueButton.setOnClickListener {
           /* Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            )
                .show()

            */
            checkAnswer(true)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false // disables the buttons on click
        }


        binding.falseButton.setOnClickListener {
           /* Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            )
                .show()

            */
            checkAnswer(false)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false // disables the buttons on click


        }

        binding.nextButton.setOnClickListener {
            if (currentIndex == questionBank.size -1){
                computeScore()
            }
            currentIndex = (currentIndex + 1) % questionBank.size
           // val questionTextResId = questionBank[currentIndex].textResId
           // binding.questionTextView.setText(questionTextResId)
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true // enables the buttons on click

        }
        binding.previousButton.setOnClickListener {
            currentIndex = if (currentIndex - 1 < 0) {
                questionBank.size - 1
            } else {
                currentIndex - 1

            }
            updateQuestion()
        }
        // This next bit of code will add an OnClickListener for the TextView
        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            // val questionTextResId = questionBank[currentIndex].textResId
            // binding.questionTextView.setText(questionTextResId)
            updateQuestion()
        }


      //  val questionTextResId = questionBank [currentIndex].textResId
      //  binding.questionTextView.setText(questionTextResId)
        updateQuestion()
    }


    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() called")

    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion(){
        val questionTextResId = questionBank [currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)

    }

    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        if (userAnswer == correctAnswer) {
            correctAnswers++
        }



        val messageResID = if (userAnswer == correctAnswer) {
            R.string.correct
        } else{
            R.string.incorrect
        }

        Toast.makeText(this,
            messageResID,
            Toast.LENGTH_SHORT)
            .show()


    }

    fun computeScore() {
        val score = (correctAnswers.toDouble() / questionBank.size) * 100
        val formattedScore = String.format("%.1f", score) + " %"
        Toast.makeText(this, "Your Score: $formattedScore", Toast.LENGTH_LONG).show()
        correctAnswers = 0
        currentIndex = 0
    }


}