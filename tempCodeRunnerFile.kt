import kotlin.math.abs

// Lớp Phân số
data class PhanSo(var tu: Int, var mau: Int) : Comparable<PhanSo> {

    init {
        require(mau != 0) { "Mẫu số không được bằng 0." }
        chuanHoaDau()
    }

    // Đưa dấu về tử, mẫu luôn dương
    private fun chuanHoaDau() {
        if (mau < 0) {
            tu = -tu
            mau = -mau
        }
    }

    // Ước chung lớn nhất (gcd) cho tối giản
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

    // Tối giản phân số (in-place) và trả về chính nó
    fun toiGian(): PhanSo {
        if (tu == 0) { // 0/d => 0/1
            mau = 1
            return this
        }
        val g = gcd(tu, mau)
        tu /= g
        mau /= g
        chuanHoaDau()
        return this
    }

    // In phân số ra màn hình (ở dạng tối giản)
    fun inRa() {
        val p = this.copy().toiGian()
        println("${p.tu}/${p.mau}")
    }

    // So sánh với 1 phân số khác: -1, 0, 1
    override fun compareTo(other: PhanSo): Int {
        // Dùng long để tránh tràn số khi nhân chéo
        val left = this.tu.toLong() * other.mau.toLong()
        val right = other.tu.toLong() * this.mau.toLong()
        return when {
            left < right -> -1
            left > right -> 1
            else -> 0
        }
    }

    // Tính tổng với một phân số khác (trả về phân số mới, đã tối giản)
    fun cong(other: PhanSo): PhanSo {
        val nTu = this.tu * other.mau + other.tu * this.mau
        val nMau = this.mau * other.mau
        return PhanSo(nTu, nMau).toiGian()
    }

    override fun toString(): String {
        val p = this.copy().toiGian()
        return "${p.tu}/${p.mau}"
    }

    companion object {
        // Nhập 1 phân số từ bàn phím:
        // Yêu cầu nhập lại nếu TỬ SỐ *hoặc* MẪU SỐ bằng 0 (theo đề bài)
        fun nhapPhanSoTuBanPhim(): PhanSo {
            while (true) {
                try {
                    print("Nhập tử số (int): ")
                    val tu = readln().toInt()
                    print("Nhập mẫu số (int): ")
                    val mau = readln().toInt()

                    if (tu == 0 || mau == 0) {
                        println("❌ Tử số và mẫu số đều phải ≠ 0 theo yêu cầu. Vui lòng nhập lại!\n")
                        continue
                    }
                    return PhanSo(tu, mau).toiGian()
                } catch (e: Exception) {
                    println("❌ Dữ liệu không hợp lệ. Vui lòng nhập lại số nguyên!\n")
                }
            }
        }
    }
}

// ---- Chương trình chính ----
fun main() {
    println("=== CHƯƠNG TRÌNH XỬ LÝ PHÂN SỐ (Kotlin) ===")

    // Nhập số lượng phần tử mảng
    val n = run {
        var value: Int
        while (true) {
            try {
                print("Nhập số lượng phân số n (>0): ")
                value = readln().toInt()
                if (value <= 0) {
                    println("❌ n phải > 0. Nhập lại!\n")
                    continue
                }
                return@run value
            } catch (_: Exception) {
                println("❌ Dữ liệu không hợp lệ. Vui lòng nhập số nguyên!\n")
            }
        }
    }

    // Nhập mảng phân số
    val arr = MutableList(n) { idx ->
        println("\n--- Nhập phân số thứ ${idx + 1} ---")
        PhanSo.nhapPhanSoTuBanPhim()
    }

    // In ra mảng vừa nhập
    println("\n➡️ Mảng phân số vừa nhập:")
    arr.forEachIndexed { i, p -> println("[$i] $p") }

    // Tối giản các phần tử và in kết quả
    arr.forEach { it.toiGian() }
    println("\n➡️ Mảng sau khi tối giản từng phần tử:")
    arr.forEachIndexed { i, p -> println("[$i] $p") }

    // Tính tổng các phân số và in kết quả
    val tong = arr.drop(1).fold(arr.firstOrNull() ?: PhanSo(0, 1)) { acc, x -> acc.cong(x) }
    println("\n➡️ Tổng các phân số trong mảng: $tong")

    // Tìm phân số lớn nhất và in ra
    val maxPs = arr.maxOrNull()
    println("➡️ Phân số có giá trị lớn nhất: $maxPs")

    // Sắp xếp mảng theo thứ tự giảm dần và in ra
    val giamDan = arr.sortedWith(compareByDescending<PhanSo> { it }) // dùng compareTo đã cài
    println("\n➡️ Mảng sắp xếp theo thứ tự giảm dần:")
    giamDan.forEachIndexed { i, p -> println("[$i] $p") }

    println("\n=== KẾT THÚC ===")
}