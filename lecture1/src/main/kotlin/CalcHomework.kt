class CalcHomework {
    private val trimRegex: Regex = """[^.\d-+*\(\)/]""".toRegex()
    private val splitRegex: Regex = """[\-]*[.\d]+|[\+\-\*\/\(\)]""".toRegex()
    private val getSingleMinusBeforeNumberRegex = """(?<=\d)\-(?=\d)""".toRegex()

    private fun trim(v: String): String = v.replace(trimRegex, "")

    private fun replaceMinusToPlusMinus(v: String) = v.replace(getSingleMinusBeforeNumberRegex, "+-")

    private fun operateBracket(operatorStack: MutableList<Symbol>, postfixNotationStack: MutableList<Element>, bracket: Bracket) {
        when (bracket) {
            is OpenBracket -> operatorStack.add(bracket)
            is CloseBracket -> {
                while (operatorStack.last() !is OpenBracket) {
                    postfixNotationStack.add(operatorStack.removeLast())
                }
                operatorStack.removeLast()
            }
        }
    }

    // 후위표기식으로 변환합니다.
    private fun convertPostfix(v: String): MutableList<Element> {

        // 후위표기식 스택입니다. 반환되는 결과값입니다.
        val postfixNotationStack = mutableListOf<Element>()

        // 후위표기식으로 변환 과정중에 잠시 연산자를 저장하는 스택입니다.
        val operatorStack = mutableListOf<Symbol>()

        splitRegex.findAll(v).forEach { v ->
            when (val symbol = getSymbolObject(v.value)) {      // 피연산자, 연산자, 괄호를 객체로 생성합니다.
                is Operand -> postfixNotationStack.add(symbol)
                is OperationCommand -> pushOperatorList(operatorStack, postfixNotationStack, symbol)
                is Bracket -> operateBracket(operatorStack, postfixNotationStack, symbol)
            }
        }

        // 마지막 남은 연산자들을 붙여줍니다.
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
            "(" -> OpenBracket()
            ")" -> CloseBracket()
            else -> Operand(value.toDouble())
        }
    }

    // 해당 연산자를 연산자 우선순위에 맞춰서 후위표기식에 반영합니다.
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
        // 후위표기식으로 먼저 변환
        val postfixList = convertPostfix(replaceMinusToPlusMinus(trim(v)))
        val operandStack = mutableListOf<Operand>()

        // 후위 표기식으로 변환된 값을 연산
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

interface Bracket : Symbol

class OpenBracket(override val priority: Int = 0) : Bracket

class CloseBracket(override val priority: Int = 0) : Bracket

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



