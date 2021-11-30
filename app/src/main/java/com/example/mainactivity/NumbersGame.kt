package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class NumbersGame : AppCompatActivity() {
    lateinit var recView :RecyclerView
    lateinit var guessButton :Button
    lateinit var numberText :EditText
    var message = ArrayList<String>()
    var number = 0
    var numberOfTry = 5
    var myListNumber = ArrayList<String>()
    var random = Random.nextInt(0,10)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers_game)
         recView = findViewById(R.id.recyclerview)

        guessButton=findViewById(R.id.guessButton)
        numberText = findViewById(R.id.numberText)

        guessButton.setOnClickListener{

            number=  checkNumber(numberText.text.toString())
            ///  number = numberText.text.toString().toInt()
            if(number==random){
                guessButton.isEnabled = false
                numberText.isActivated = false
                message.add("You guessed $number is correct ")
                myListNumber.add(numberText.text.toString())
                recView.adapter=RecyclerViewAdapter( message  ,myListNumber )
                recView.layoutManager = LinearLayoutManager(this)


            }


            number=  checkNumber(numberText.text.toString())
            if (number != random) {
                numberOfTry--
                message.add("You have $numberOfTry guesses left and your guess is  ")
                myListNumber.add(numberText.text.toString())
                recView.adapter = RecyclerViewAdapter( message , myListNumber)
                recView.layoutManager = LinearLayoutManager(this)

                if(numberOfTry == 0 ) {
                    guessButton.isEnabled = false
                    numberText.isActivated = false
                }
            }
        }

    }


    fun checkNumber( number:String):Int{

        try {
            return number.toInt()

        }
        catch(e:Exception){
            println("enter a number only ")
            return 0
        }

    }

    fun clear(){
        message.clear()
        myListNumber.clear()
        recView.adapter=RecyclerViewAdapter( message  ,myListNumber )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu3, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.newG-> {
                clear()
                return true
            }
            R.id.guessPH2 -> {
                val intent = Intent(this, GuessThePhrase::class.java)
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