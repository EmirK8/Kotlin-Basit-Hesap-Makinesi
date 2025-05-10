package com.example.hesapmakinesi

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var resultText: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultText)

        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                numberClicked((it as Button).text.toString())
            }
        }

        findViewById<Button>(R.id.btnDot).setOnClickListener { numberClicked(".") }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnDelete).setOnClickListener { deleteLast() }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { operatorClicked("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { operatorClicked("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { operatorClicked("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { operatorClicked("/") }

        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculate() }
    }

    private fun numberClicked(value: String) {
        if (isNewOperation) {
            currentInput = ""
            isNewOperation = false
        }
        currentInput += value
        resultText.text = currentInput
    }

    private fun operatorClicked(op: String) {
        firstNumber = currentInput.toDoubleOrNull() ?: 0.0
        operator = op
        currentInput = ""
    }

    private fun calculate() {
        val secondNumber = currentInput.toDoubleOrNull() ?: return
        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else {
                resultText.text = "HATA"
                return
            }
            else -> return
        }
        resultText.text = result.toString()
        currentInput = result.toString()
        isNewOperation = true
    }

    private fun clear() {
        currentInput = ""
        firstNumber = 0.0
        operator = ""
        resultText.text = "0"
        isNewOperation = true
    }

    private fun deleteLast() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            resultText.text = if (currentInput.isEmpty()) "0" else currentInput
        }
    }
}
