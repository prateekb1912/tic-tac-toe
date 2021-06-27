package com.criclytica.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Initialize the 2D array of buttons displayed on screen
    lateinit var board: Array<Array<Button>>

    // Define player variable to determine current player
    var PLAYER = 0

    // Define how many turns have been played
    var TURN = 0

    /*
    Define a 2D array to determine the game status so as to avoid
    traversing our board
    */
    var boardStatus = Array(3){IntArray(3)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button IDs passed into the board 2D array
        board = arrayOf(
            arrayOf(btn1, btn2, btn3),
            arrayOf(btn4, btn5, btn6),
            arrayOf(btn7, btn8, btn9)
        )

        // Binding each button by an onClickListener
        for(i in board) {
            for(button in i) {
                button.setOnClickListener(this)
            }
        }

        TODO("Implement logic to reset the board")
        btnReset.setOnClickListener{

        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
             TODO("Bind each button to own logic to change text from X to O and vice-versa")
            when(v.id) {
                R.id.btn1 -> {}
                R.id.btn2 -> {}
                R.id.btn3 -> {}
                R.id.btn4 -> {}
                R.id.btn5 -> {}
                R.id.btn6 -> {}
                R.id.btn7 -> {}
                R.id.btn8 -> {}
                R.id.btn9 -> {}
            }
        }
    }
}