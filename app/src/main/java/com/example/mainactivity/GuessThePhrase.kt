package com.example.mainactivity
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.view.Menu
import android.view.MenuItem

     class GuessThePhrase: AppCompatActivity() {
         lateinit var consLayOut :ConstraintLayout
       private lateinit var sharedPreferences: SharedPreferences
        lateinit var recView: RecyclerView
         lateinit var phraseTextView: TextView
        lateinit var letterTextView: TextView
    private lateinit var myHighScore: TextView
    lateinit var guessedET: EditText
    var guessList = arrayListOf<String>()
    var guessLetters = arrayListOf<String>()
    var phrase = "ohood"
    var answer = ""
    var counter = 10
    var isPhrase = true
    var guessCorrectLetter = ""
     var score = 0
         lateinit var adapterr: RecyclerViewAdapterr
    ///private var highScore = 0

    fun EditText.setMaxLength(maxLength: Int) {
        filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_phrase)

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.ScoreSP), Context.MODE_PRIVATE)
        score = sharedPreferences.getInt(getString(R.string.Score),0)

        myHighScore = findViewById(R.id.tvScore)
        myHighScore.text = "High Score: $score "
        consLayOut = findViewById(R.id.consLayOut)
        recView = findViewById(R.id.recyclerview1)

        adapterr = RecyclerViewAdapterr(guessList)
        recView.adapter = adapterr
        recView.layoutManager = LinearLayoutManager(this)

        phraseTextView = findViewById(R.id.guessPTv)
        letterTextView = findViewById(R.id.guessLTv)

        phraseTextView.text  = "Phrase: " + encodePhrase(phrase)
        val GuessBT = findViewById<Button>(R.id.guessButton)
        guessedET = findViewById(R.id.guessEditText)

        guessedET.setMaxLength(phrase.length)

        GuessBT.setOnClickListener {
            var guessText = guessedET.text.toString()
            if (isPhrase) {
                if (counter > 0) {
                    if (!checkIfEmpty(guessText)) {
                        checkPhrase(guessText)
                    }
                }
            } else {
                if (counter > 0) {
                    if (!checkIfEmpty(guessText)) {
                        guessedET.hint = "Guess the letters of the phrase"
                        checkLetter(guessText[0])
                    }
                } else {
                    showAlertDialog("Oops!,The phrase was $phrase")
                    // replayGame()
                }
            }
            guessedET.text.clear()
        }

    }


    private fun showAlertDialog(title: String) {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage(title)
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                this.recreate()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game Over")
        // show alert dialog
        alert.show()
    }

    fun checkPhrase(guessText: String) {
        if (phrase == guessText) {
            showAlertDialog("Congratulations,You guessed it correctly!")
            score++
            updateScore(score)
            //replayGame()

        } else {
            counter--
            guessedET.hint = "Guess the letters of the phrase"
            guessList.add("$guessText is a wrong guess.")
            guessedET.setMaxLength(1)
            isPhrase = false

            if (counter != 0) {
                guessList.add("$counter guesses remaining!")
            }

            adapterr.updateAdapter(guessList)

//            recView.adapter!!.notifyDataSetChanged()

        }

    }


    fun checkLetter(guessLetter: Char) {

        letterTextView.setVisibility(View.VISIBLE)


        if (phrase.contains(guessLetter)) {//the letter exist in the phrase
            guessLetters.add(guessLetter + "")
            counter--
            guessCorrectLetter = ""
            var countLetters = 0
            for (i in phrase) {
                if (guessLetters.contains(i + "")) {
                    if (guessLetter == i)
                        countLetters++
                    guessCorrectLetter += i
                } else if (i == ' ') {
                    guessCorrectLetter += " "
                } else if (i != guessLetter && i != ' ' && i != '*') {
                    guessCorrectLetter += "*"
                } else {
                    guessCorrectLetter += i
                }
            }

            letterTextView.text = "Guessed letters: " + guessLetters.toString()
            phraseTextView.text = "Phrase:" + guessCorrectLetter

            guessList.add("Found $countLetters $guessLetter(s)")
            guessList.add("$counter guesses remaining")
            recView.adapter!!.notifyDataSetChanged()
        } else { //if the user entered a wrong letter
            counter--
            guessList.add("$guessLetter is a wrong guess")
            guessList.add("$counter guesses remaining")
            recView.adapter!!.notifyDataSetChanged()
        }
        if (phrase == guessCorrectLetter) {
            score++
            updateScore(score)
            showAlertDialog("Congratulations,You guessed it correctly!")

            recView.adapter!!.notifyDataSetChanged()
            // replayGame()

        }

    }

    fun encodePhrase(phrase: String): String {
        var enPhrase = ""
        for (i in phrase) {
            if (i == ' ') {
                enPhrase += " "
            } else {
                enPhrase += "*"
            }

        }
        return enPhrase
    }

    fun checkIfEmpty(str: String): Boolean {
        if (str.isEmpty()) {
            var myLayout = findViewById<ConstraintLayout>(R.id.consLayOut)

            Snackbar.make(myLayout, "Enter a phrase/letter", Snackbar.LENGTH_SHORT).show()
            return true
        }
        return false

    }

    private fun updateScore(score:Int){
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.Score), score)
            apply()
        }
        Snackbar.make(consLayOut, "NEW HIGH SCORE!", Snackbar.LENGTH_LONG).show()


    }
         fun clear (){
             updateScore(0)
             score = 0
             myHighScore.text = "High Score: $score "
             guessList.clear()
             recView.adapter = RecyclerViewAdapterr(guessList)


         }

         override fun onCreateOptionsMenu(menu: Menu?): Boolean {
             menuInflater.inflate(R.menu.menu2, menu)
             return true
         }

         override fun onOptionsItemSelected(item: MenuItem): Boolean {
             when (item.itemId) {
                 R.id.newG -> {

                     clear()
                     return true
                 }
                 R.id.numG2 -> {
                     val intent = Intent(this, NumbersGame::class.java)
                     startActivity(intent)
                     return true
                 }
                 R.id.mainMenu -> {
                     val intent = Intent(this, MainActivity::class.java)
                     startActivity(intent)
                     return true
                 }

             }
             return super.onOptionsItemSelected(item)

         }
}