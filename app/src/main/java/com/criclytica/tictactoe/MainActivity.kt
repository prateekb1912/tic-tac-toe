package com.criclytica.tictactoe

import android.annotation.SuppressLint
import android.net.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.criclytica.tictactoe.R.color
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Initialize the 2D array of buttons displayed on screen
    lateinit var board: Array<Array<Button>>

    // Define player variable to determine current player
    var PLAYER = true

    // Define how many turns have been played
    var TURNS = 0

    // The last move which was played in the game
    var lastMove:Array<Int> =  Array(2) {0}

    /*
    Define a 2D array to determine the game status so as to avoid
    traversing our board
    */
    var boardStatus = Array(3){IntArray(3)}


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
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
            btnUndo.isEnabled = true
        }

        btnUndo.setOnClickListener {
            var prevText = board[lastMove[0]][lastMove[1]].text

            board[lastMove[0]][lastMove[1]].apply {
                text = ""
                isEnabled = true
            }

            if(prevText == "X")
            {
                tvHead.apply {
                    text = "Player X turn"
                    setTextColor(R.color.red)
                }
                PLAYER = true
            }
            else
                tvHead.apply {
                    text = "Player O turn"
                    setTextColor(R.color.green)
                }
            PLAYER = false
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

                tvHead.apply {
                    setText("Player X turn")
                    setTextColor(ContextCompat.getColor(context, color.red))
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
             when(v.id) {
                R.id.btn1 -> {
                    updateValue(row = 0, col = 0, player = PLAYER)
                    lastMove[0] = 0
                    lastMove[1] = 0
                }
                R.id.btn2 -> {
                    updateValue(row = 0, col = 1, player = PLAYER)
                    lastMove[0] = 0
                    lastMove[1] = 1
                }
                R.id.btn3 -> {
                    updateValue(row = 0, col = 2, player = PLAYER)
                    lastMove[0] = 0
                    lastMove[1] = 2
                }
                R.id.btn4 -> {
                    updateValue(row = 1, col = 0, player = PLAYER)
                    lastMove[0] = 1
                    lastMove[1] = 0
                }
                R.id.btn5 -> {
                    updateValue(row = 1, col = 1, player = PLAYER)
                    lastMove[0] = 1
                    lastMove[1] = 1
                }
                R.id.btn6 -> {
                    updateValue(row = 1, col = 2, player = PLAYER)
                    lastMove[0] = 1
                    lastMove[1] = 2
                }
                R.id.btn7 -> {
                    updateValue(row = 2, col = 0, player = PLAYER)
                    lastMove[0] = 2
                    lastMove[1] = 0
                }
                R.id.btn8 -> {
                    updateValue(row = 2, col = 1, player = PLAYER)
                    lastMove[0] = 2
                    lastMove[1] = 1
                }
                R.id.btn9 -> {
                    updateValue(row = 2, col = 2, player = PLAYER)
                    lastMove[0] = 2
                    lastMove[1] = 2
                }
            }
        }
    }

    /*
    A function to update the board and status values whenever a player
    completes their turn and clicks the button
     */
    @SuppressLint("ResourceAsColor")
    private fun updateValue(row: Int, col: Int, player: Boolean) {
        var text = if(player) "X" else "O"
        val value = if(player) 1 else 0

        var color = if(player) color.red else color.green

        board[row][col].apply {
            isEnabled = false
            setText(text)
            setTextColor(ContextCompat.getColor(context, color))
        }
        boardStatus[row][col] = value

        val gameComp = isGameWon()

        if(!gameComp[0]) {
            text = if (text == "X") "O" else "X"
            color = if(!player) R.color.red else R.color.green

            tvHead.apply {
                setText("Player $text turn")
                setTextColor(ContextCompat.getColor(context, color))
            }
            PLAYER = !player
        }

        else {
            if(gameComp[1])
                text = "Player X won!"
            else
                text = "Player O won!"

            tvHead.apply {
                setText(text)
                setTextColor(ContextCompat.getColor(context, color))
            }

            for (i in board) {
                for (b in i) {
                    b.isEnabled = false
                }
            }

            btnUndo.apply {
                isEnabled = false
                color = R.color.light_gray
            }
        }

        TURNS++

        if(TURNS == 9)
        {
            if(!gameComp[0]) {
                tvHead.apply {
                    setText("It's a draw!")
                    setTextColor(R.color.black)
                }
            }
            else
            {
                if(gameComp[1])
                    text = "Player X won the game!!!"
                else
                    text = "Player 0 won the game!!!"

                tvHead.apply {
                    setText(text)
                    setTextColor(ContextCompat.getColor(context, color))
                }
            }

            btnUndo.apply {
                isEnabled = false
                color = R.color.light_gray
            }
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
                winStatus[0] = true
                winStatus[1] = true
                break
            }
            else if(sum == 0)
            {
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
                winStatus[0] = true
                winStatus[1] = true
                break
            }
            else if(sum == 0)
            {
                winStatus[0] = true
                winStatus[1] = false
                break
            }
        }


        // Diagonal check
        if(boardStatus[0][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[2][2] && boardStatus[0][0] != -99)
        {
            winStatus[0] = true
            winStatus[1] = boardStatus[0][0] == 1
        }


        if(boardStatus[2][0] == boardStatus[1][1] && boardStatus[1][1] == boardStatus[0][2] && boardStatus[1][1] != -99)
        {
            winStatus[0] = true
            winStatus[1] = boardStatus[1][1] == 1
        }


        return winStatus
    }
}