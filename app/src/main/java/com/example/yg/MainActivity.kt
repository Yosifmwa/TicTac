package com.example.yg

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }
    private var movesCount = 0

    private lateinit var tvResult: TextView
    private lateinit var btnReset: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = arrayOf(
            findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3),
            findViewById(R.id.btn4), findViewById(R.id.btn5), findViewById(R.id.btn6),
            findViewById(R.id.btn7), findViewById(R.id.btn8), findViewById(R.id.btn9)
        )
        tvResult = findViewById(R.id.tvResult)
        btnReset = findViewById(R.id.btnReset)


        buttons.forEachIndexed { index, btn ->
            val row = index / 3
            val col = index % 3
            btn.setOnClickListener {
                onCellClicked(btn, row, col)
            }

            btnReset.setOnClickListener {
                resetGame()
                tvResult.visibility = View.GONE
                btnReset.visibility = View.GONE
            }
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) return

        button.text = currentPlayer
        board[row][col] = currentPlayer
        movesCount++

        when {
            checkWin() -> {
                showGameOver("Player $currentPlayer wins!")
            }
            movesCount == 9 -> {
                showGameOver("It's a draw!")
            }
            else -> {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
    }

    private fun checkWin(): Boolean {
        for (i in 0..2) {
            if (board[i].all { it == currentPlayer }) return true
            if ((0..2).all { board[it][i] == currentPlayer }) return true
        }
        if ((0..2).all { board[it][it] == currentPlayer }) return true
        if ((0..2).all { board[it][2 - it] == currentPlayer }) return true
        return false
    }
    private fun showGameOver(message: String) {
        tvResult.text = message
        tvResult.visibility = View.VISIBLE
        btnReset.visibility = View.VISIBLE
        disableBoard()
    }

    private fun disableBoard() {
        buttons.forEach { it.isEnabled = false }
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        movesCount = 0
        currentPlayer = "X"
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
            it.contentDescription = getString(R.string.empty_cell)
        }
    }
}
