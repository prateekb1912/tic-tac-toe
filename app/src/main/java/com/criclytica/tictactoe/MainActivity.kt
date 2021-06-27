package com.criclytica.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Initialize the 2D array of buttons displayed on screen
    lateinit var board: Array<Array<Button>>

    // Define player variable to determine current player
    var PLAYER = true

    // Define how many turns have been played
    var TURNS = 0

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

        initBoardStatus()

        btnReset.setOnClickListener{
            PLAYER = true
            TURNS = 0
            initBoardStatus()
        }
    }

    /*
    A function to initialize our board :
     setting the status to -1,
     enabling all buttons,
     and setting all texts to empty
     */
    private fun initBoardStatus() {
        for(i in 0..2){
            for(j in 0..2){
                boardStatus[i][j] = -99
                board[i][j].isEnabled = true
                board[i][j].text = ""

                tvHead.text = "Player X turn"
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
             when(v.id) {
                R.id.btn1 -> {
                    updateValue(row = 0, col = 0, player = PLAYER)
                }
                R.id.btn2 -> {
                    updateValue(row = 0, col = 1, player = PLAYER)
                }
                R.id.btn3 -> {
                    updateValue(row = 0, col = 2, player = PLAYER)
                }
                R.id.btn4 -> {
                    updateValue(row = 1, col = 0, player = PLAYER)
                }
                R.id.btn5 -> {
                    updateValue(row = 1, col = 1, player = PLAYER)
                }
                R.id.btn6 -> {
                    updateValue(row = 1, col = 2, player = PLAYER)
                }
                R.id.btn7 -> {
                    updateValue(row = 2, col = 0, player = PLAYER)
                }
                R.id.btn8 -> {
                    updateValue(row = 2, col = 1, player = PLAYER)
                }
                R.id.btn9 -> {
                    updateValue(row = 2, col = 2, player = PLAYER)
                }
            }
        }
    }

    /*
    A function to update the board and status values whenever a player
    completes their turn and clicks the button
     */
    private fun updateValue(row: Int, col: Int, player: Boolean) {
        var text = if(player) "X" else "0"
        val value = if(player) 1 else 0

        board[row][col].apply {
            isEnabled = false
            setText(text)
        }
        boardStatus[row][col] = value

        val gameComp = isGameWon()

        if(!gameComp[0]) {
            text = if (text == "X") "0" else "X"
            tvHead.setText("Player $text turn")
            PLAYER = !player
        }

        else {
            if(gameComp[1])
                text = "Player X won the game!!!"
            else
                text = "Player 0 won the game!!!"

            tvHead.setText(text)

            for (i in board) {
                for (b in i) {
                    b.isEnabled = false
                }
            }
        }

        TURNS++

        if(TURNS == 9)
        {
            tvHead.setText("It's a draw!")
        }

    }

    private fun isGameWon(): Array<Boolean> {
        var winStatus  =  Array(2){false}

        // Horizontal check
        for(i in 0..2) {
            var sum = 0
            for(j in 0..2) {
                sum += boardStatus[i][j]
            }

            if(sum == 3)
            {
                Log.i("TTT", "Horizontal passed")
                winStatus[0] = true
                winStatus[1] = true
                break
            }
            else if(sum == 0)
            {
                Log.i("TTT", "Horizontal passed")
                winStatus[0] = true
                winStatus[1] = false
                break
            }
        }

        // Vertical check
        for(j in 0..2) {
            var sum = 0
            for(i in 0..2) {
                sum += boardStatus[i][j]
            }

            if(sum == 3)
            {
                Log.i("TTT", "Vertical passed")
                winStatus[0] = true
                winStatus[1] = true
                break
            }
            else if(sum == 0)
            {
                Log.i("TTT", "Vertical passed")
                winStatus[0] = true
                winStatus[1] = false
                break
            }
        }


        // Diagonal check
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2] && boardStatus[0][0] != -99)
        {
            Log.i("TTT", "Diagonal 1 passed")
            winStatus[0] = true
            winStatus[1] = boardStatus[0][0] == 1
        }


        if(boardStatus[2][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[0][2] && boardStatus[1][1] != -99)
        {
            Log.i("TTT", "Diagonal 2 passed")
            winStatus[0] = true
            winStatus[1] = boardStatus[1][1] == 1
        }


        return winStatus
    }
}