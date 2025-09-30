import kotlin.math.abs

class Fraction(var num: Int, var den: Int) : Comparable<Fraction> {

    init {
        require(den != 0) { "Mẫu số không được bằng 0" }
        normalizeSign()
    }

    private fun normalizeSign() {
        if (den < 0) {
            num = -num
            den = -den
        }
    }

    private fun gcd(a: Int, b: Int): Int {
        var x = abs(a)
        var y = abs(b)
        if (x == 0 && y == 0) return 1
        while (y != 0) {
            val t = x % y
            x = y
            y = t
        }
        return if (x == 0) 1 else x
    }

    // Tối giản (rút gọn)
    fun simplify(): Fraction {
        val g = gcd(num, den)
        if (g != 0) {
            num /= g
            den /= g
        }
        normalizeSign()
        return this
    }

    // Cộng với 1 phân số khác
    fun plus(other: Fraction): Fraction {
        // (a/b) + (c/d) = (ad + bc) / bd
        val a = this.num.toLong()
        val b = this.den.toLong()
        val c = other.num.toLong()
        val d = other.den.toLong()
        val n = a * d + b * c
        val m = b * d
        // ép về Int an toàn (bài tập phạm vi số vừa phải)
        return Fraction(n.toInt(), m.toInt()).simplify()
    }

    // So sánh (-1, 0, 1)
    override fun compareTo(other: Fraction): Int {
        // So sánh a/b ? c/d -> so sánh ad ? cb (dùng Long để tránh tràn)
        val left = this.num.toLong() * other.den.toLong()
        val right = other.num.toLong() * this.den.toLong()
        return when {
            left < right -> -1
            left > right -> 1
            else -> 0
        }
    }

    override fun toString(): String {
        // Hiển thị đã tối giản nếu muốn: gọi simplify() trước khi in
        return "$num/$den"
    }

    companion object {
        // Nhập phân số từ bàn phím: nếu tử hoặc mẫu = 0 thì yêu cầu nhập lại
        fun readFromKeyboard(index: Int? = null): Fraction {
            while (true) {
                try {
                    if (index != null) println("Nhập phân số thứ ${index + 1}:")
                    print("  Tử số (≠ 0): ")
                    val n = readln().toInt()
                    print("  Mẫu số (≠ 0): ")
                    val d = readln().toInt()
                    if (n == 0 || d == 0) {
                        println("  ❌ Tử số hoặc mẫu số bằng 0. Vui lòng nhập lại!")
                        continue
                    }
                    return Fraction(n, d)
                } catch (e: NumberFormatException) {
                    println("  ❌ Vui lòng nhập số nguyên hợp lệ!")
                } catch (e: IllegalArgumentException) {
                    println("  ❌ ${e.message}")
                }
            }
        }
    }
}

fun main() {
    // Ví dụ: nhập sẵn 4 phân số
    val arr = mutableListOf(
        Fraction(2, 4),
        Fraction(1, 3),
        Fraction(7, 5),
        Fraction(-4, 6)
    )

    println("=== MẢNG PHÂN SỐ VỪA NHẬP ===")
    printFractionList(arr)

    // Tối giản
    arr.forEach { it.simplify() }
    println("\n=== MẢNG SAU KHI TỐI GIẢN ===")
    printFractionList(arr)

    // Tổng
    var sum = Fraction(0, 1)
    for (f in arr) sum = sum.plus(f)
    println("\n=== TỔNG CÁC PHÂN SỐ ===")
    println("Tổng = ${sum.simplify()}")

    // Max
    val maxFrac = arr.maxOrNull()
    println("\n=== PHÂN SỐ LỚN NHẤT ===")
    println("Max = $maxFrac")

    // Sắp xếp giảm dần
    val sortedDesc = arr.sortedDescending()
    println("\n=== MẢNG SẮP XẾP GIẢM DẦN ===")
    printFractionList(sortedDesc)
}


// Hỗ trợ: đọc kích thước mảng hợp lệ
fun readSize(): Int {
    while (true) {
        try {
            print("Nhập số lượng phân số n (>0): ")
            val n = readln().toInt()
            if (n <= 0) {
                println("  ❌ n phải > 0.")
                continue
            }
            return n
        } catch (e: NumberFormatException) {
            println("  ❌ Vui lòng nhập số nguyên hợp lệ!")
        }
    }
}

// Hỗ trợ: in danh sách phân số
fun printFractionList(list: List<Fraction>) {
    if (list.isEmpty()) {
        println("(rỗng)")
        return
    }
    println(list.joinToString(separator = ", ") { it.toString() })
}