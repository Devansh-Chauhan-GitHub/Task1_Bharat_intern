package com.example.calculator_app1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resulttv=  findViewById<TextView>(R.id.resulttv)
        datatv = findViewById<TextView>(R.id.datatv)
    }
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    var count=1
    lateinit var datatv: TextView
    lateinit var resulttv: TextView

    fun clearbutton(view: View)
    {
        datatv.text=""
        lastNumeric = false
        lastDot = false
    }
    fun backbutton(view: View)
    {
        datatv.text=datatv.text.toString().dropLast(1)
        try
        {
            val lastdigit = datatv.text.toString().last()
            if(lastdigit.isDigit())
            {
                lastNumeric=true
                evaluate()
            }
            else
            {
                lastNumeric=false
                resulttv.text=""
            }


        }
        catch (e: Exception)
        {
            resulttv.text=""
            resulttv.visibility=View.GONE
            Log.e("last character error",e.toString())
        }
    }
    fun equalbutton(view: View)
    {

            count = 0
            evaluate()
            val lastdigit = datatv.text.toString().last()
            if (lastdigit.isDigit()) {
                datatv.text = resulttv.text.toString().drop(1)
            }
            else{count = 1}

    }
    fun allclearbutton(view: View)
    {
        datatv.text=""
        resulttv.text=""
        resulttv.visibility=View.GONE
        lastNumeric = false
        stateError = false
        lastDot = false
    }
    fun digitbutton(view: View)
    {
        try{
        if(count==0) {
            datatv.text=""
            if (stateError)
            {
                datatv.text=(view as Button).text
                stateError=false
            } else {
                datatv.append((view as Button).text)
            }

            lastNumeric = true
            evaluate()
            count=1
        }
        else if(count==1)
        {
            if (stateError)
            {
                datatv.text=(view as Button).text
                stateError=false
            } else {
                datatv.append((view as Button).text)
            }

            lastNumeric = true
            evaluate()
        }}
        catch (e: Exception)
        {
            resulttv.text=""
            resulttv.visibility=View.GONE
            Log.e("last character error",e.toString())
        }
    }
    fun operatorbutton(view: View)
    {
        count=1
        if(lastNumeric && !stateError)
        {
            datatv.append((view as Button).text)
            evaluate()
            lastNumeric=false
            lastDot=false

        }
    }
    fun evaluate()
    {
        try{
        if(lastNumeric && !stateError)
        {
            val text = datatv.text.toString()
            val expression = ExpressionBuilder(text).build()
            try
            {
                val result = expression.evaluate()
                resulttv.text= "= " + result.toString()
                resulttv.visibility=View.VISIBLE
            }
            catch (e: ArithmeticException)
            {
               Log.e("evaluate eror",e.toString())
                resulttv.text="Error"
                stateError=true
                lastNumeric=false
            }

        }}
        catch (e: Exception)
        {
            resulttv.text=""
            resulttv.visibility=View.GONE
            Log.e("last character error",e.toString())
        }
    }

}