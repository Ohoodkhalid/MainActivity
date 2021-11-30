package com.example.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
   /// lateinit var layout: ConstraintLayout
    lateinit var numGame :Button
    lateinit var phGuss :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      //  layout = findViewById(R.id.clMain)
        numGame = findViewById(R.id.NumGAMEB)
        phGuss = findViewById(R.id.guessPhB)

        numGame.setOnClickListener{
            val intent = Intent(this, NumbersGame::class.java)
            startActivity(intent)
        }

        phGuss.setOnClickListener{
            val intent = Intent(this, GuessThePhrase::class.java)
            startActivity(intent)
        }



    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.NumG-> {
                val intent = Intent(this, NumbersGame::class.java)
                startActivity(intent)
                return true
            }
            R.id.guessPH -> {
                val intent = Intent(this, GuessThePhrase::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    }
