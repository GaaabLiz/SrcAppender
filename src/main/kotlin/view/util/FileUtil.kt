package view.util

import kotlinx.coroutines.delay
import view.model.Action
import view.model.ActionType
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.lang.RuntimeException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path


object FileUtil {

    fun openDialogAndGetDir() : File? {
        val dialog = FileDialog(null as Frame?, "Select File to Open")
        dialog.mode = FileDialog.LOAD
        dialog.isMultipleMode = true
        dialog.isVisible = true
        return try {
            val f = dialog.files
            println(f.first().path)
            f.first()
        }catch (e : NoSuchElementException) {
            null
        }
    }

    suspend fun createOutputFile(
        acList:List<Action>,
        showBanner: () -> Unit,
        hideBanner: () -> Unit,
    ) {

        /* show banner */
        showBanner()

        /* loading things */
        delay(1000)

        /* scanning file types */
        acList.forEach{
            if(it.type == ActionType.ACTION_ADD_FILE) {

            }
        }

        /* hide banner */
        hideBanner()
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun isBinaryFile(f: File?): Boolean {
        val `in` = FileInputStream(f)
        var size = `in`.available()
        if (size > 1024) size = 1024
        val data = ByteArray(size)
        `in`.read(data)
        `in`.close()
        var ascii = 0
        var other = 0
        for (i in data.indices) {
            val b = data[i]
            if (b < 0x09) return true
            if (b.toInt() == 0x09 || b.toInt() == 0x0A || b.toInt() == 0x0C || b.toInt() == 0x0D) ascii++ else if (b >= 0x20 && b <= 0x7E) ascii++ else other++
        }
        return if (other == 0) false else 100 * other / (ascii + other) > 95
    }

}