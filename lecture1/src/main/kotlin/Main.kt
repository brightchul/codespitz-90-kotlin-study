fun main(args: Array<String>) {
    val calcExample = CalcHomework();

    println(calcExample.calc("-2 * -3 + 0.4 / - 0.2"))
    println(calcExample.calc("-2 -3 + 0.4"))
    println(calcExample.calc("-2 * (-3 + 0.4) / - 0.2"))
    println(calcExample.calc("-2 * -(-3 + 0.4) / -(-3 + -5) * 10 - 0.2"))
    println(calcExample.calc("-1 + 2 -3 + 4 * -(1 / 10) / -(10 * 0.2) - (100 / 20)"))
}