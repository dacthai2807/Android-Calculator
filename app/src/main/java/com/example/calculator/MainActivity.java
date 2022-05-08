package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity
{

    TextView workingsTV;
    TextView resultsTV;

    String workings = "";
    String formula = "";
    String tempFormula = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTextViews();
    }

    private void initTextViews()
    {
        workingsTV = (TextView)findViewById(R.id.workingsTextView);
        resultsTV = (TextView)findViewById(R.id.resultTextView);
    }

    private void setWorkings(String givenValue)
    {
        workings = workings + givenValue;
        workingsTV.setText(workings);
    }

    public void equalsOnClick(View view)
    {
        if (!workings.equals(""))
        {
            Double result = null;
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
            checkForPowerOf();

            try
            {
                result = (double) engine.eval(formula);
            }
            catch (ScriptException e)
            {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
            }

            if (result != null)
                resultsTV.setText(String.valueOf(result.doubleValue()));
        }
    }

    private void checkForPowerOf()
    {
        ArrayList<Integer> indexOfPowers = new ArrayList<>();
        for(int i = 0; i < workings.length(); i++)
        {
            if (workings.charAt(i) == '^')
                indexOfPowers.add(i);
        }

        formula = workings;
        tempFormula = workings;
        for(Integer index: indexOfPowers)
        {
            changeFormula(index);
        }
        formula = tempFormula;
    }

    private void changeFormula(Integer index)
    {
        String numberLeft = "";
        String numberRight = "";

        for(int i = index + 1; i< workings.length(); i++)
        {
            if(isNumeric(workings.charAt(i)))
                numberRight = numberRight + workings.charAt(i);
            else
                break;
        }

        for(int i = index - 1; i >= 0; i--)
        {
            if(isNumeric(workings.charAt(i)))
                numberLeft = numberLeft + workings.charAt(i);
            else
                break;
        }

        String original = numberLeft + "^" + numberRight;
        String changed = "Math.pow("+numberLeft+","+numberRight+")";
        tempFormula = tempFormula.replace(original,changed);
    }

    private boolean isNumeric(char c)
    {
        if((c <= '9' && c >= '0') || c == '.')
            return true;

        return false;
    }


    public void clearOnClick(View view)
    {
        workingsTV.setText("");
        workings = "";
        resultsTV.setText("");
        leftBracket = true;
    }

    public void clearEntryOnClick(View view)
    {
        resultsTV.setText("");
        if (!workings.equals(""))
        {
            workings = workings.substring(0, workings.length()-1);
            workingsTV.setText(workings);
        }
    }

    private String getSubstring(int l, int r, String str) {
        if (l > r) return "";
        if (l < 0) l = 0;
        if (r > str.length() - 1) r = str.length() - 1;
        return str.substring(l, r + 1);
    }

    public void oppositeOnClick(View view)
    {
        resultsTV.setText("");
        int id = -1;
        for(int i = workings.length()-1; i >= 0; --i)
        {
            if((workings.codePointAt(i) < 48 || workings.codePointAt(i) > 57) && (workings.charAt(i) != '.'))
            {
                id = i;
                break;
            }
        }

        if(!workings.equals("") && id >= 0 &&  workings.charAt(id) == '+')
        {
            workings = getSubstring(0, id - 1, workings) + "-" + getSubstring(id + 1, workings.length()-1, workings);
        }
        else
        if(!workings.equals("") && id >= 0 && workings.charAt(id) == '-')
        {
            workings = getSubstring(0, id - 1, workings) + "+" + getSubstring(id + 1, workings.length()-1, workings);
        }
        else {
            workings = getSubstring(0, id, workings) + "-" + getSubstring(id + 1, workings.length() - 1, workings);
        }

        workingsTV.setText(workings);
    }

    boolean leftBracket = true;

    public void bracketsOnClick(View view)
    {
        resultsTV.setText("");
        if(leftBracket)
        {
            setWorkings("(");
            leftBracket = false;
        }
        else
        {
            setWorkings(")");
            leftBracket = true;
        }
    }

    public void powerOfOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("^");
    }

    public void divisionOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("/");
    }

    public void sevenOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("7");
    }

    public void eightOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("8");
    }

    public void nineOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("9");
    }

    public void timesOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("*");
    }

    public void fourOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("4");
    }

    public void fiveOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("5");
    }

    public void sixOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("6");
    }

    public void minusOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("-");
    }

    public void oneOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("1");
    }

    public void twoOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("2");
    }

    public void threeOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("3");
    }

    public void plusOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("+");
    }

    public void decimalOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings(".");
    }

    public void zeroOnClick(View view)
    {
        resultsTV.setText("");
        setWorkings("0");
    }

}