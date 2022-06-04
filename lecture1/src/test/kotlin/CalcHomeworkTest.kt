import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

internal class CalcHomeworkTest {

    private val epsilon: Double = 0.0000000001
    private val calcHomework = CalcHomework()

    @Test
    @DisplayName("+, - 만 있는 수식")
    fun onlyPlusMinus() {
        assertEquals(calcHomework.calc("1+2+3"), 6.0)
        assertEquals(calcHomework.calc("1+2-3"), 0.0)
        assertEquals(calcHomework.calc("1-2-3"), -4.0)


        assertEquals(calcHomework.calc("0.1+0.2+0.3"), 0.6, epsilon)
        assertEquals(calcHomework.calc("0.1+0.2-0.3"), 0.0, epsilon)
        assertEquals(calcHomework.calc("0.1-0.2-0.3"), -0.4, epsilon)
    }

    @Test
    @DisplayName("*, / 만 있는 수식")
    fun onlyMultiplyDivide() {
        assertEquals(calcHomework.calc("1*2*3"), 6.0)
        assertEquals(calcHomework.calc("-1*-2*-3"), -6.0)

        assertEquals(calcHomework.calc("1*2*3/3/2"), 1.0)
        assertEquals(calcHomework.calc("1*2*3/-3/2"), -1.0)

        assertEquals(calcHomework.calc("0.1*0.2*0.3"), 0.006, epsilon)
        assertEquals(calcHomework.calc("0.1*0.2/0.4"), 0.05, epsilon)
        assertEquals(calcHomework.calc("0.1*0.2/-0.4"), -0.05, epsilon)
    }

    @Test
    @DisplayName("(, ) 포함한 연산")
    fun withBracket() {
        assertEquals(calcHomework.calc("1+ 2 + 3 + (10- 11)"), 5.0)
        assertEquals(calcHomework.calc("1+ 2 - 3 - (10 - 5)"), -5.0)
        assertEquals(calcHomework.calc("1- (2 - 3 - (10 - 5))"), 7.0)

        assertEquals(calcHomework.calc("11 * -0.1 / -(5 * 0.1)"), 2.2)
        assertEquals(calcHomework.calc("11 * -0.1 * -(5 * 0.1)"), 0.55)
    }

}