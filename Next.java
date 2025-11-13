"use client"

import { useState, useEffect, useCallback } from "react"
import { Button } from "@/cpackage com.example.myjavaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // واجهة بسيطة من كود Java فقط
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#E3F2FD"));
        layout.setGravity(Gravity.CENTER);

        TextView textView = new TextView(this);
        textView.setText("مرحبًا بك في تطبيق Java على أندرويد ☕️");
        textView.setTextSize(24);
        textView.setTextColor(Color.parseColor("#1565C0"));
        textView.setGravity(Gravity.CENTER);

        layout.addView(textView);
        setContentView(layout);
    }
}
omponents/ui/button"

export default function Calculator() {
  const [display, setDisplay] = useState("0")
  const [firstOperand, setFirstOperand] = useState(null)
  const [operator, setOperator] = useState(null)
  const [waitingForSecondOperand, setWaitingForSecondOperand] = useState(false)

  const clearAll = useCallback(() => {
    setDisplay("0")
    setFirstOperand(null)
    setOperator(null)
    setWaitingForSecondOperand(false)
  }, [])

  const inputDigit = useCallback(
    digit => {
      if (waitingForSecondOperand) {
        setDisplay(digit)
        setWaitingForSecondOperand(false)
      } else {
        setDisplay(display === "0" ? digit : display + digit)
      }
    },
    [display, waitingForSecondOperand]
  )

  const inputDecimal = useCallback(() => {
    if (waitingForSecondOperand) {
      setDisplay("0.")
      setWaitingForSecondOperand(false)
      return
    }

    if (!display.includes(".")) {
      setDisplay(display + ".")
    }
  }, [display, waitingForSecondOperand])

  const handleOperator = useCallback(
    nextOperator => {
      const inputValue = parseFloat(display)

      if (firstOperand === null) {
        setFirstOperand(inputValue)
      } else if (operator) {
        const result = calculate(firstOperand, inputValue, operator)
        setDisplay(String(result))
        setFirstOperand(result)
      }

      setWaitingForSecondOperand(true)
      setOperator(nextOperator)
    },
    [display, firstOperand, operator]
  )

  const calculate = (first, second, op) => {
    switch (op) {
      case "+":
        return first + second
      case "-":
        return first - second
      case "×":
        return first * second
      case "÷":
        return second !== 0 ? first / second : NaN
      default:
        return second
    }
  }

  const handleEquals = useCallback(() => {
    if (firstOperand === null || operator === null) return

    const inputValue = parseFloat(display)
    const result = calculate(firstOperand, inputValue, operator)

    if (isNaN(result)) {
      setDisplay("Error")
    } else {
      setDisplay(String(result))
    }

    setFirstOperand(null)
    setOperator(null)
    setWaitingForSecondOperand(true)
  }, [display, firstOperand, operator])

  // Keyboard support
  useEffect(() => {
    const handleKeyDown = e => {
      if (e.key >= "0" && e.key <= "9") {
        inputDigit(e.key)
      } else if (e.key === ".") {
        inputDecimal()
      } else if (e.key === "+") {
        handleOperator("+")
      } else if (e.key === "-") {
        handleOperator("-")
      } else if (e.key === "*") {
        handleOperator("×")
      } else if (e.key === "/") {
        e.preventDefault()
        handleOperator("÷")
      } else if (e.key === "Enter" || e.key === "=") {
        handleEquals()
      } else if (e.key === "Escape") {
        clearAll()
      } else if (e.key === "Backspace") {
        setDisplay(prev => (prev.length > 1 ? prev.slice(0, -1) : "0"))
      }
    }

    window.addEventListener("keydown", handleKeyDown)
    return () => window.removeEventListener("keydown", handleKeyDown)
  }, [inputDigit, inputDecimal, handleOperator, handleEquals, clearAll])

  const buttonClass =
    "h-16 text-xl font-medium rounded-lg transition-all duration-150 active:scale-95"
  const numberButtonClass = `${buttonClass} bg-gray-100 hover:bg-gray-200 text-gray-800`
  const operatorButtonClass = `${buttonClass} bg-blue-500 hover:bg-blue-600 text-white`
  const equalsButtonClass = `${buttonClass} bg-green-500 hover:bg-green-600 text-white`
  const clearButtonClass = `${buttonClass} bg-red-500 hover:bg-red-600 text-white`
  const functionButtonClass = `${buttonClass} bg-gray-300 hover:bg-gray-400 text-gray-800`

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-indigo-50 to-purple-100 p-4">
      <div className="w-full max-w-sm rounded-2xl bg-white p-6 shadow-xl">
        <div className="mb-6">
          <h1 className="text-center text-2xl font-bold text-gray-800">
            Calculator
          </h1>
          <p className="text-center text-sm text-gray-500">
            Supports keyboard input
          </p>
        </div>

        {/* Display */}
        <div className="mb-4 overflow-hidden rounded-lg bg-gray-900 p-4 text-right">
          <div className="text-4xl font-bold text-white truncate">
            {display}
          </div>
        </div>

        {/* Keypad */}
        <div className="grid grid-cols-4 gap-3">
          <Button
            onClick={clearAll}
            className={`${clearButtonClass} col-span-2`}
          >
            Clear
          </Button>
          <Button
            onClick={() =>
              setDisplay(prev => (prev.length > 1 ? prev.slice(0, -1) : "0"))
            }
            className={functionButtonClass}
          >
            ⌫
          </Button>
          <Button
            onClick={() => handleOperator("÷")}
            className={operatorButtonClass}
          >
            ÷
          </Button>

          <Button onClick={() => inputDigit("7")} className={numberButtonClass}>
            7
          </Button>
          <Button onClick={() => inputDigit("8")} className={numberButtonClass}>
            8
          </Button>
          <Button onClick={() => inputDigit("9")} className={numberButtonClass}>
            9
          </Button>
          <Button
            onClick={() => handleOperator("×")}
            className={operatorButtonClass}
          >
            ×
          </Button>

          <Button onClick={() => inputDigit("4")} className={numberButtonClass}>
            4
          </Button>
          <Button onClick={() => inputDigit("5")} className={numberButtonClass}>
            5
          </Button>
          <Button onClick={() => inputDigit("6")} className={numberButtonClass}>
            6
          </Button>
          <Button
            onClick={() => handleOperator("-")}
            className={operatorButtonClass}
          >
            -
          </Button>

          <Button onClick={() => inputDigit("1")} className={numberButtonClass}>
            1
          </Button>
          <Button onClick={() => inputDigit("2")} className={numberButtonClass}>
            2
          </Button>
          <Button onClick={() => inputDigit("3")} className={numberButtonClass}>
            3
          </Button>
          <Button
            onClick={() => handleOperator("+")}
            className={operatorButtonClass}
          >
            +
          </Button>

          <Button
            onClick={() => inputDigit("0")}
            className={`${numberButtonClass} col-span-2`}
          >
            0
          </Button>
          <Button onClick={inputDecimal} className={numberButtonClass}>
            .
          </Button>
          <Button onClick={handleEquals} className={equalsButtonClass}>
            =
          </Button>
        </div>

        <div className="mt-6 text-center text-xs text-gray-500">
          <p>Keyboard shortcuts: Numbers, +, -, *, /, Enter, Backspace, Esc</p>
        </div>
      </div>
    </div>
  )
}
