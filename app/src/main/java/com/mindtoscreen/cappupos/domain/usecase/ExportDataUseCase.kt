package com.mindtoscreen.cappupos.domain.usecase

import com.mindtoscreen.cappupos.domain.model.Order
import com.mindtoscreen.cappupos.domain.model.Product
import com.mindtoscreen.cappupos.domain.repository.OrderRepository
import com.mindtoscreen.cappupos.domain.repository.ProductRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.inject.Inject

/**
 * Use case untuk export data ke Excel (FR-13). 3 sheet dalam satu .xlsx:
 * Transaksi, Produk, Laporan Ringkas. XLSX = ZIP berisi XML, text-only tanpa
 * styling (DECISIONS.md [2026-09-14] poin 4 — hand-rolled via java.util.zip,
 * dependency baru ditolak).
 *
 * Menulis ke [outputDir] (cacheDir app) dan mengembalikan File hasil.
 */
class ExportDataUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository
) {
    suspend fun execute(outputDir: File): Result<File> {
        return try {
            val orders = orderRepository.getAllOrders()
            val products = productRepository.getProducts()
            val laporan = orderRepository.getLaporanAggregat(0L, System.currentTimeMillis()).getOrNull()

            val file = File(outputDir, "laporan_cappu.xlsx")
            val writer = XlsxWriter()
            writer.write(file) {
                // Sheet 1: Transaksi
                sheet(
                    "Transaksi",
                    listOf("ID", "Tanggal", "Status", "Metode Bayar", "Subtotal"),
                    orders.map { order ->
                        listOf(
                            order.id ?: "",
                            formatTanggal(order.tanggal),
                            order.status,
                            order.metodeBayar ?: "",
                            order.subtotal.toString()
                        )
                    }
                )
                // Sheet 2: Produk
                sheet(
                    "Produk",
                    listOf("ID", "Nama", "Kategori", "Harga", "Stok"),
                    products.map { product ->
                        listOf(
                            product.id ?: "",
                            product.nama,
                            product.kategoriId ?: "",
                            product.harga.toString(),
                            (product.jumlahStok ?: 0).toString()
                        )
                    }
                )
                // Sheet 3: Laporan Ringkas
                sheet(
                    "Laporan Ringkas",
                    listOf("Metrik", "Nilai"),
                    listOf(
                        listOf("Total Penjualan", (laporan?.totalPenjualan ?: 0.0).toString()),
                        listOf("Jumlah Transaksi", (laporan?.jumlahTransaksi ?: 0).toString()),
                        listOf("Metode Terpopuler", laporan?.metodeBayarTerpopuler ?: "-")
                    )
                )
            }

            Result.success(file)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun formatTanggal(millis: Long): String {
        if (millis == 0L) return "-"
        return SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("id", "ID")).format(Date(millis))
    }
}

/**
 * Writer XLSX minimal: ZIP berisi XML OOXML, text-only, tanpa styling, tanpa
 * sharedStrings (pakai inline string). Satu-satunya alasan ditaruh di file ini:
 * allowed_paths TASK-007 hanya mengizinkan `ExportDataUseCase.kt` (tanpa folder
 * util terpisah), dan dependency baru ditolak (DECISIONS.md [2026-09-14]).
 */
private class XlsxWriter {

    fun write(file: File, block: SheetBuilder.() -> Unit) {
        val builder = SheetBuilder()
        builder.block()

        ZipOutputStream(file.outputStream().buffered()).use { zip ->
            zip.put("[Content_Types].xml", CONTENT_TYPES)
            zip.put("_rels/.rels", RELS)
            zip.put("xl/workbook.xml", builder.workbookXml())
            zip.put("xl/_rels/workbook.xml.rels", builder.workbookRelsXml())
            builder.sheets.forEachIndexed { index, sheet ->
                zip.put("xl/worksheets/sheet${index + 1}.xml", sheet.toXml())
            }
        }
    }

    private fun ZipOutputStream.put(name: String, content: String) {
        putNextEntry(ZipEntry(name))
        write(content.toByteArray(Charsets.UTF_8))
        closeEntry()
    }

    private class Cell(
        val ref: String,
        val value: String,
        val isNumber: Boolean
    )

    internal class SheetData(
        val name: String,
        val rows: List<List<String>>
    ) {
        fun toXml(): String {
            val sb = StringBuilder()
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
            sb.append("<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">")
            sb.append("<sheetData>")
            rows.forEachIndexed { rowIdx, row ->
                sb.append("<row r=\"").append(rowIdx + 1).append("\">")
                row.forEachIndexed { colIdx, value ->
                    val ref = colRef(colIdx) + (rowIdx + 1)
                    if (value.isNumber()) {
                        sb.append("<c r=\"").append(ref).append("\"><v>").append(escape(value)).append("</v></c>")
                    } else {
                        sb.append("<c r=\"").append(ref).append("\" t=\"inlineStr\"><is><t>")
                            .append(escape(value)).append("</t></is></c>")
                    }
                }
                sb.append("</row>")
            }
            sb.append("</sheetData></worksheet>")
            return sb.toString()
        }
    }

    internal class SheetBuilder {
        val sheets = mutableListOf<SheetData>()

        fun sheet(name: String, header: List<String>, rows: List<List<String>>) {
            val allRows = mutableListOf(header)
            allRows.addAll(rows)
            sheets.add(SheetData(name, allRows))
        }

        fun workbookXml(): String {
            val sb = StringBuilder()
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
            sb.append("<workbook xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\" ")
            sb.append("xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">")
            sb.append("<sheets>")
            sheets.forEachIndexed { index, sheet ->
                sb.append("<sheet name=\"").append(escape(sheet.name))
                    .append("\" sheetId=\"").append(index + 1)
                    .append("\" r:id=\"rId").append(index + 1).append("\"/>")
            }
            sb.append("</sheets></workbook>")
            return sb.toString()
        }

        fun workbookRelsXml(): String {
            val sb = StringBuilder()
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
            sb.append("<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">")
            sheets.forEachIndexed { index, _ ->
                sb.append("<Relationship Id=\"rId").append(index + 1)
                    .append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" ")
                    .append("Target=\"worksheets/sheet").append(index + 1).append(".xml\"/>")
            }
            sb.append("</Relationships>")
            return sb.toString()
        }
    }

    companion object {
        private fun colRef(col: Int): String {
            var n = col + 1
            val sb = StringBuilder()
            while (n > 0) {
                val rem = (n - 1) % 26
                sb.insert(0, ('A'.code + rem).toChar())
                n = (n - 1) / 26
            }
            return sb.toString()
        }

        private fun String.isNumber(): Boolean {
            if (isEmpty()) return false
            return toDoubleOrNull() != null
        }

        private fun escape(value: String): String {
            return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;")
        }

        private val CONTENT_TYPES = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
<Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
<Default Extension="xml" ContentType="application/xml"/>
<Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/>
<Override PartName="/xl/worksheets/sheet1.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>
<Override PartName="/xl/worksheets/sheet2.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>
<Override PartName="/xl/worksheets/sheet3.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>
</Types>"""

        private val RELS = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">
<Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/>
</Relationships>"""
    }
}
