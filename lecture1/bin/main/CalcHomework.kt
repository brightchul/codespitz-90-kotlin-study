import java.util.Arrays

class CalcHomework {

    private val trimRegex: Regex = """[^.\d-+*/]""".toRegex()

    private fun trim(v: String): String {
        return v.replace(trimRegex, "")
    }

    private val splitRegex: Regex = """[\-]*[.\d]+|[\+\-\*\/\(\)]""".toRegex()

    private val getSingleMinusBeforeNumberRegex = """(?<=\d)\-(?=\d)""".toRegex()

    private fun replaceMinusToPlusMinus(v: String) = v.replace(getSingleMinusBeforeNumberRegex, "+-")


    private fun convertPostfix(v: String): MutableList<Element> {
        val postfixNotationStack = mutableListOf<Element>()
        val operatorStack = mutableListOf<Symbol>()
        val splitSequence = splitRegex.findAll(v)

        splitSequence.forEach { v ->
            when (val symbol = getSymbolObject(v.value)) {
                is Operand -> postfixNotationStack.add(symbol)
                is OperationCommand -> pushOperatorList(operatorStack, postfixNotationStack, symbol)
            }
        }

        while (operatorStack.isNotEmpty()) {
            postfixNotationStack.add(operatorStack.removeLast())
        }

        return postfixNotationStack
    }

    private fun getSymbolObject(value: String): Element {
        return when (value) {
            "+" -> Plus()
            "-" -> Minus()
            "*" -> Multiply()
            "/" -> Divide()
            else -> Operand(value.toDouble())
        }
    }

    private fun pushOperatorList(operatorStack: MutableList<Symbol>, postfixNotationStack: MutableList<Element>, target: Symbol) {
        while (operatorStack.isNotEmpty()) {
            if (operatorStack.last() < target) {
                break
            }
            postfixNotationStack.add(operatorStack.removeLast())
        }
        operatorStack.add(target)
    }

    fun calc(v: String): Double {
        val postfixList = convertPostfix(replaceMinusToPlusMinus(trim(v)))
        val operandStack = mutableListOf<Operand>()

        while (postfixList.isNotEmpty()) {
            when (val element = postfixList.removeFirst()) {
                is Operand -> operandStack.add(element)
                is OperationCommand -> {
                    val op2 = operandStack.removeLast()
                    val op1 = operandStack.removeLast()
                    val result = element.operate(op1, op2)

                    operandStack.add(result)
                }
            }
        }

        return operandStack.removeLast().value
    }
}

interface Element

data class Operand(val value: Double) : Element

interface Symbol : Element, Comparable<Symbol> {
    val priority: Int

    override fun compareTo(other: Symbol): Int {
        return compareValuesBy(this, other) { it.priority }
    }
}

interface OperationCommand : Symbol {
    override val priority: Int

    fun operate(op1: Operand, op2: Operand): Operand
}

class Plus : OperationCommand {
    override val priority: Int = 1

    override fun operate(op1: Operand, op2: Operand): Operand {
        return Operand(op1.value + op2.value)
    }
}

class Minus : OperationCommand {
    override val priority: Int = 1

    override fun operate(op1: Operand, op2: Operand): Operand {
        return Operand(op1.value - op2.value)
    }
}

class Multiply : OperationCommand {
    override val priority: Int = 3

    override fun operate(op1: Operand, op2: Operand): Operand {
        return Operand(op1.value * op2.value)
    }
}

class Divide : OperationCommand {
    override val priority: Int = 3

    override fun operate(op1: Operand, op2: Operand): Operand {
        return Operand(op1.value / op2.value)
    }
}



