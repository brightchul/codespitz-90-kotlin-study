fun main(args: Array<String>) {
    println("Hello World!")

    val calcExample = CalcHomework();
    print("-2 * -3 + 0.4 / - 0.2 = ")
    println(calcExample.calc("-2 * -3 + 0.4 / - 0.2"))
    print("-2 -3 + 0.4 = ")
    println(calcExample.calc("-2 -3 + 0.4"))
}