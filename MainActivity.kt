package com.example.calculator

import android.annotation.SuppressLint
import kotlin.math.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var tvCalcDisplay: TextView? = null
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCalcDisplay = findViewById(R.id.tvCalcDisplay)
    }
    fun onDigit(view: View) {
        tvCalcDisplay?.append((view as Button).text)
        lastNumeric = true
        lastDot = false


    }

    fun onSqRt(view: View) {
        val inputText = tvCalcDisplay?.text?.toString()
        if (inputText.isNullOrEmpty()) {
            // If the input text is null or empty, we can't calculate the square root
            return
        }
        val inputValue = inputText.toDoubleOrNull() ?: return
        val squareRoot = sqrt(inputValue)
        tvCalcDisplay?.text = squareRoot.toString()
    }



    fun onClear(view:View){
        tvCalcDisplay?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvCalcDisplay?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun onOperator(view: View) {
        tvCalcDisplay?.text?.let {


            if (lastNumeric && !isOperatorAdded(it.toString()))
                tvCalcDisplay?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvCalcDisplay?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvCalcDisplay?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvCalcDisplay?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvCalcDisplay?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvCalcDisplay?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : (String) {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0,result.length - 2)
        return value
    }
    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")

        }
    }
}