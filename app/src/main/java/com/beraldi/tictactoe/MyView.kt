package com.beraldi.tictactoe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.os.Handler
import android.util.Log
import org.json.JSONObject

class MyView(context: Context?) : View(context),View.OnTouchListener {


    private var winner = -1
    private var chess = IntArray(9) { -1 }


    val who = 0
    private val linepaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 10f
        style = Paint.Style.STROKE
    }


    private var dx = 0f
    private var dy = 0f


    init {
        setOnTouchListener(this)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        dx = width / 3f
        dy = height / 3f

        checkwinner()

        canvas?.drawRGB(255, 4, 244)

        Log.i("info", "onDraw: "+winner+" "+who)

        //if (winner == who) canvas?.drawRGB(0, 255, 0)
        if (winner ==who ) {
            canvas?.drawRGB(0, 255, 0)
        }

        if (winner ==1 ) {
            canvas?.drawRGB(255, 0, 0)
        }


        canvas?.drawLine(dx, 0f, dx, height.toFloat(), linepaint)
        canvas?.drawLine(2 * dx, 0f, 2 * dx, height.toFloat(), linepaint)

        canvas?.drawLine(0f, dy, width.toFloat(), dy, linepaint)
        canvas?.drawLine(0f, 2 * dy, width.toFloat(), 2 * dy, linepaint)


        for (k in 0..8) {
            if (chess[k] == 0) //draw a Circle
            {
                val i = k % 3
                val j = k / 3
                // Toast.makeText(context, ""+i+" "+j, Toast.LENGTH_SHORT).show()
                canvas?.drawCircle(dx * i.toFloat() + dx / 2, dy * j.toFloat() + dy / 2, dx / 2f, linepaint)
            }

            if (chess[k] == 1) { //draw a cross
                val i = k % 3
                val j = k / 3

                canvas?.drawLine(i * dx, j * dy, (i + 1) * dx, (j + 1) * dy, linepaint)
                canvas?.drawLine((i + 1) * dx, j * dy, i * dx, (j + 1) * dy, linepaint)
                //Toast.makeText(context, ""+i+" "+j, Toast.LENGTH_SHORT).show()

            }
        }


    }


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                //find the center of the area
                if (winner != -1) return true
                val j = (event?.x / dx).toInt()
                val i = (event?.y / dy).toInt()
                val k = 3 * i + j
                if (who == 1) chess[k] = 1
                if (who == 0) chess[k] = 0
                makeMove(k)
                invalidate()
                //invalidate()
                // Toast.makeText(context, ""+i+" "+j, Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }


    fun checkwinner() {

        var a = 0
        var b = 0
        //check horizontal lines
        for (k in 0..8) {
            if (chess[k] == 0) a += 1
            if (chess[k] == 1) b += 1
            if ((k == 2) or (k == 5) or (k == 8)) {
                if (a == 3) {
                    winner = 0;return
                }
                if (b == 3) {
                    winner = 1;return
                }
                a = 0;b = 0
            }
        }
        //check vertical lines
        for (offset in 0..2)
            for (k in listOf(0, 3, 6)) {
                if (chess[k + offset] == 0) a += 1
                if (chess[k + offset] == 1) b += 1
                if (a == 3) {
                    winner = 0;return
                }
                if (b == 3) {
                    winner = 1;return
                }
                if (k == 6) {
                    a = 0;b = 0
                }
            }
        //check diagonals
        for (k in listOf(0, 4, 8)) {
            if (chess[k] == 0) a += 1
            if (chess[k] == 1) b += 1
            if (a == 3) {
                winner = 0;return
            }
            if (b == 3) {
                winner = 1;return
            }
        }
        a = 0;b = 0
        for (k in listOf(2, 4, 6)) {
            if (chess[k] == 0) a += 1
            if (chess[k] == 1) b += 1
            if (a == 3) {
                winner = 0;return
            }
            if (b == 3) {
                winner = 1;return
            }
        }
    }
    fun makeMove(k: Int) {
        for (k in 0..8) {
            if (chess[k] == -1) {
                chess[k] = 1
                break
            }
        }
        checkwinner()
        invalidate()
    }
}