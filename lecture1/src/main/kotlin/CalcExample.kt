class CalcExample {

    val trim: Regex = """[^.\d-+*/]""".toRegex()

    fun trim(v: String): String {
        return v.replace(trim, "")
    }

    // fun 함수명(인자): 반환형 = 반환식
    // fun 함수형(인자)(:반환형) = 반환식의 형
    fun replaceMinusToPlusMinus(v: String): String = v.replace("-", "+-")

    // () capture group,
    // (?:) non capture group
    // (..|..) alternative
    // ? zero or one
    // + one or unlimited
    val groupMultiplicationDivision: Regex = """((?:\+|\+-)?[.\d]+)([*/])((?:\+|\+-)?[.\d]+)""".toRegex()

    fun foldGroup(v: String): Double = groupMultiplicationDivision
        .findAll(v)     // findAll
        .fold(0.0) { acc, curr ->       // fold
            val (_, left, op, right) = curr.groupValues
            // List<String>(전체, 그룹1, 그룹2, 그룹3...)
            val leftValue = left.replace("+", "").toDouble()
            val rightValue = right.replace("+", "").toDouble()
            val result = when (op) {        // when
                "*" -> leftValue * rightValue
                "/" -> leftValue / rightValue
                else -> throw Throwable("invalid operator $op")
            }
            acc + result
        }


    fun calc(v: String) = foldGroup(replaceMinusToPlusMinus(trim(v)))
}
