package com.example.minesweeper_kotlin

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), OnCellClickListener {
    var tablero: Tablero? = null
    var tam = 0
    var cantBomb = 0
    var markedMines = 0
    private var findAll = true
    var gameOver = false
    var gridRecyclerView: RecyclerView? = null
    var tableroRecyclerAdapter: TableroRecyclerAdapter? = null
    var minesLeft: TextView? = null
    var emoji: TextView? = null
    private lateinit var btnStart: Button
    var cronometro: Chronometer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tam = 9
        cantBomb = 10
        btnStart = findViewById(R.id.btnStart)
        cronometro = findViewById<View>(R.id.cronometro) as Chronometer
        btnStart.setOnClickListener(View.OnClickListener {
            minesLeft = findViewById(R.id.minesLeft)
            emoji = findViewById(R.id.emoji)
            minesLeft?.setText(Integer.toString(cantBomb))
            btnStart.setEnabled(false)
            newGame(tam, cantBomb)
        })
    }

    private fun newGame(tam: Int, cantBomb: Int) {
        btnStart!!.setText(R.string.iniciar)
        emoji!!.setText(R.string.Game)
        cronometro!!.base = SystemClock.elapsedRealtime()
        cronometro!!.start()
        tablero = Tablero(tam) // Se crea el tablero del tamaño deseado
        tablero!!.setBombas(cantBomb) // Se colocan las bombas en el tablero de manera random
        gameOver = false
        markedMines = 0
        gridRecyclerView = findViewById(R.id.activity_main_grid)
        gridRecyclerView?.setLayoutManager(GridLayoutManager(this, 9))
        tableroRecyclerAdapter = TableroRecyclerAdapter(tablero!!.getCells(), this)
        gridRecyclerView?.setAdapter(tableroRecyclerAdapter)
    }

    private fun endGame() {
        btnStart!!.isEnabled = true
        btnStart!!.text = "Restart"
        gameOver = true
        emoji!!.setText(R.string.GameOver)
        cronometro!!.stop()
        val count = tableroRecyclerAdapter!!.itemCount
        for (i in 0 until count) gridRecyclerView!!.getChildAt(i).isEnabled = false
        // tablero.revealBombs();
    }

    override fun cellClick(cellPos: Int): Int {
        val ret = tablero!!.leftClickCell(cellPos, tam)
        if (ret == Cell.bomba) {
            Toast.makeText(
                applicationContext,
                "Has explotado una mina. ¡Suerte para la otra!",
                Toast.LENGTH_LONG
            ).show()
            endGame()
        }
        return ret
    }

    override fun cellClikLong(cellPos: Int) {
        markedMines++
        if (tablero!!.rightClickCell(cellPos)) {
            if (markedMines == cantBomb && findAll) {
                Toast.makeText(
                    applicationContext,
                    "Has encontrado todas las minas, ¡Felicidades, Ganaste la partida!",
                    Toast.LENGTH_LONG
                ).show()
                gameOver = true
                endGame()
            }
        } else findAll = false
        if (markedMines == cantBomb && !findAll) {
            Toast.makeText(
                applicationContext,
                "Te faltaron minas por encontrar, has perdido la partida, ¡Suerte para la otra! ",
                Toast.LENGTH_LONG
            ).show()
            gameOver = true
            endGame()
        }
        val mineleft = cantBomb - markedMines
        if (mineleft >= 0) minesLeft!!.text = Integer.toString(mineleft)
    }
}