package com.example.androidimageoptimization
import org.junit.Test
import java.io.File
import org.junit.Ignore

class GenerateProductsTest {
    // Sau này 2 dựng server local thì chỉ cần đổi dòng này
    // ví dụ: "http://10.0.2.2:8000"
    private val baseUrl = "https://picsum.photos/seed"

    private val names = listOf(
        "Áo thun", "Giày thể thao", "Balo", "Đồng hồ", "Tai nghe",
        "Kính mát", "Túi xách", "Mũ lưỡi trai", "Ốp lưng", "Bình nước"
    )
    @Ignore("Chi chay thu cong khi can sinh lai du lieu")
    @Test
    fun generateProductsJson() {
        val total = 1000
        val sb = StringBuilder()
        sb.append("[\n")
        for (i in 1..total) {
            val name = "${names[i % names.size]} mẫu $i"
            val description = "Mô tả ngắn cho sản phẩm số $i"
            val thumbUrl = "$baseUrl/$i/300/200"
            val imageUrl = "$baseUrl/$i/800/600"

            sb.append("  {")
            sb.append("\"id\": $i, ")
            sb.append("\"name\": \"$name\", ")
            sb.append("\"description\": \"$description\", ")
            sb.append("\"thumbUrl\": \"$thumbUrl\", ")
            sb.append("\"imageUrl\": \"$imageUrl\"")
            sb.append("}")
            if (i < total) sb.append(",")
            sb.append("\n")
        }
        sb.append("]\n")

        val file = File("src/main/assets/products.json")
        file.parentFile?.mkdirs()
        file.writeText(sb.toString(), Charsets.UTF_8)
        println("Da tao: ${file.absolutePath}")
    }
}