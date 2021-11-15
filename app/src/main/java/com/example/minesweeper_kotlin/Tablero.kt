package com.example.minesweeper_kotlin

import java.util.*

class Tablero(private val tam: Int) {
    private val cells: MutableList<Cell>
    private var rand: Random? = null
    private var pos = 0
    fun setBombas(cantBomb: Int) {
        rand = Random()
        var i = 0
        while (i < cantBomb) {
            pos = rand!!.nextInt(tam * tam - 1) + 1
            if (cells[pos].value == Cell.vacio) {
                cells[pos].value = Cell.bomba
                i++
            }
        }
    }

    fun revealBombs() {
        for (c in cells) {
            if (c.value == Cell.bomba) c.isRevelado = true
        }
    }

    /**
     * @param pos Es la posición de la celda que fue clickeada
     * @param tam Es el tamaño del tablero, para poder calcular el tamaño total de celdas
     * @return la cantidad de bombas que tiene al rededor esa celda
     */
    fun leftClickCell(pos: Int, tam: Int): Int {
        var countBomb = 0
        if (!cells[pos].isRevelado) {
            if (cells[pos].value == Cell.bomba) {
                return Cell.bomba
            } else {
                val real = tam * tam
                if (pos == 0) { // Checo si es la esquina superior izquierda
                    if (cells[pos + 1].value == Cell.bomba) countBomb++
                    if (cells[pos + tam].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam + 1)].value == Cell.bomba) countBomb++
                } else if (pos == 0 + tam - 1) { // Checo si es la esquina superior derecha
                    if (cells[pos - 1].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + tam].value == Cell.bomba) countBomb++
                } else if (pos == real - tam) { // Checo si es la esquina inferior izquierda
                    if (cells[pos - tam].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam + 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + 1].value == Cell.bomba) countBomb++
                } else if (pos == real - 1) { // Checo si es la esquina inferior derecha
                    if (cells[pos - 1].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos - tam].value == Cell.bomba) countBomb++
                } else if (pos % tam == 0) { // Checo si es una celda lateral izquierda
                    if (cells[pos - tam].value == Cell.bomba) countBomb++
                    if (cells[pos - tam + 1].value == Cell.bomba) countBomb++
                    if (cells[pos + 1].value == Cell.bomba) countBomb++
                    if (cells[pos + tam].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam + 1)].value == Cell.bomba) countBomb++
                } else if ((pos + 1) % tam == 0) { // Checo si es una celda lateral derecha
                    if (cells[pos + tam].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos - 1].value == Cell.bomba) countBomb++
                    if (cells[pos - tam].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam - 1)].value == Cell.bomba) countBomb++
                } else if (pos in 1..7) { // Checo si es una celda lateral superior
                    if (cells[pos + tam].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam + 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + 1].value == Cell.bomba) countBomb++
                    if (cells[pos - 1].value == Cell.bomba) countBomb++
                } else if (pos in 72..80) { // Checo si es una celda lateral inferior
                    if (cells[pos - tam].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam + 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + 1].value == Cell.bomba) countBomb++
                    if (cells[pos - 1].value == Cell.bomba) countBomb++
                } else {
                    if (cells[pos + tam].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam + 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos + 1].value == Cell.bomba) countBomb++
                    if (cells[pos - 1].value == Cell.bomba) countBomb++
                    if (cells[pos - tam].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam - 1)].value == Cell.bomba) countBomb++
                    if (cells[pos - (tam + 1)].value == Cell.bomba) countBomb++
                }
            }
        }


        // else return -2;
        cells[pos].isRevelado = true
        return countBomb
    }

    /**
     * @param pos es la posicion de donde se marcó la casilla
     * @return true si la celda si tenía una mina, false si la celda no tiene mina o ya fue flagged
     */
    fun rightClickCell(pos: Int): Boolean {
        if (!cells[pos].isFlagged) {
            cells[pos].isFlagged = true
            if (cells[pos].value == Cell.bomba) return true
        }
        return false
    }

    fun getCells(): List<Cell> {
        return cells
    }

    init {
        cells = ArrayList()
        for (i in 0 until tam * tam) cells.add(Cell(Cell.vacio))
    }
}