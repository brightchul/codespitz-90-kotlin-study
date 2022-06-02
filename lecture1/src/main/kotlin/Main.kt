fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val calcExample = CalcExample();
    println(calcExample.calc("-2 * -3 + 0.4 / - 0.2"))
}