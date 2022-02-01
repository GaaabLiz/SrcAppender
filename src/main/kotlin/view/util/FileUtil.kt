package view.util

import androidx.compose.ui.text.toUpperCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import view.compose.DialogCardType
import view.model.Action
import view.model.ActionType
import view.util.AppConfig.SEP_CHAR_1
import view.util.AppConfig.SEP_CHAR_2
import view.util.AppConfig.SEP_CHAR_SPACE
import java.awt.FileDialog
import java.awt.Frame
import java.io.*
import java.lang.StringBuilder
import java.nio.file.*


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

    private fun openDialogAndGetSaveDir() : File? {
        val dialog = FileDialog(null as Frame?, "Select output destination")
        dialog.mode = FileDialog.SAVE
        dialog.isMultipleMode = false
        dialog.isVisible = true
        return try {
            return File(dialog.directory + "/" + dialog.file)
        }catch (e : NoSuchElementException) {
            null
        }
    }

    suspend fun createOutputFile(
        acList:List<Action>,
        showBanner: () -> Unit,
        hideBanner: () -> Unit,
        changeStatusText: (String) -> Unit,
        callDialog: (Boolean, DialogCardType, String?) -> Unit,
        onEnded : () -> Unit
    ) {

        /* get save dir */
        val f = openDialogAndGetSaveDir()
        println("output dir = '${f?.path}'")

        /* show banner */
        showBanner()
        delay(500)

        /* checking file object */
        changeStatusText("Checking output dir...")
        if(f == null) {
            callDialog(true, DialogCardType.ERROR, "Unable to retrive output file location path.")
            return
        }

        /* loading things */
        delay(1000)

        /* creating and opening output file */
        var fw : FileWriter
        try {
            withContext(Dispatchers.IO){
                fw = FileWriter(f, true)
                fw.close()
            }
        }catch (e : IOException) {
            callDialog(true, DialogCardType.ERROR, "An error occurred while opening output director.")
            return
        }


        /* scanning file types */
        acList.forEach{
            if(it.type == ActionType.ACTION_ADD_FILE) {

                /* log */
                println("Start checking file '${it.filePath}'")
                changeStatusText("Checking '${it.fileName}'...")
                delay(500)

                /* checking file */
                if(it.filePath == null) {
                    callDialog(true, DialogCardType.ERROR, "An error occurred while checking file '${it.fileName}'. This file will be skipped.")
                    return@forEach
                }
                if(!it.filePath!!.exists()) {
                    callDialog(true, DialogCardType.ERROR, "The '${it.fileName}' doesn't exists!. This file will be skipped.")
                    return@forEach
                }
                if(!it.filePath!!.canRead()) {
                    callDialog(true, DialogCardType.ERROR, "The file '${it.fileName}' cannot be read. This file will be skipped.")
                    return@forEach
                }

                /* reading file lines */
                println("Start reading file '${it.filePath}'")
                changeStatusText("Reading '${it.fileName}'...")
                delay(500)
                val fileLines = it.filePath!!.readLines()
                println("File '${it.fileName}' has ${fileLines.size} lines.")

                /* for each line append to the output file */
                println("Start writing file '${it.filePath}' to the output file.")
                changeStatusText("Appending '${it.fileName}' to the output file...")
                fw = FileWriter(f, true)
                fileLines.forEach { line ->
                    println("I'm writing line -> {$line}.")
                    fw.write("$line\n")
                }
                fw.close()
                delay(500)
                println("File '${it.filePath}' was appended to the output file.")

            } else {

                /* log */
                changeStatusText("Generating separator...")
                delay(500)

                /* cheking numbers */
                val cond1 = it.separatorLinesBf == null
                val cond2 = it.separatorLinesAf == null
                val cond3 = it.separatorTitle == null
                val cond4 = it.separatorRowLength == null
                if(cond1 || cond2 || cond3 || cond4) {
                    callDialog(true, DialogCardType.ERROR, "Error occurred while checking a separator. Try delete and create a new one. This will be skipped.")
                    return@forEach
                }

                /* generating list of lines sep */
                val lines = getSepLinesArray(
                    it.separatorLinesBf!!,
                    it.separatorRowLength!!,
                    it.separatorLinesAf!!,
                    it.separatorTitle.toString()
                )

                /* appending sep line to output file */
                changeStatusText("Appending separator to the output file...")
                fw = FileWriter(f, true)
                lines.forEach { line ->
                    println("I'm writing line -> {$line}.")
                    fw.write(line + "\n")
                }
                fw.close()
            }
        }

        /* hide banner */
        hideBanner()
        onEnded()

        /* show dialog */
        callDialog(true, DialogCardType.SUCCESS, "All the file are successfully appended to the output file.")
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

    private fun generateSepLines(linesBefore:Int, rowLenght:Int, linesAfter:Int, sepTitle : String = "") : List<String> {
        val list = ArrayList<String>()
        var sep : StringBuilder = StringBuilder()

        /* generating lines before sep */
        for (i in 0 until linesBefore) {
            list.add("\n")
        }

        /* line 1 */
        sep.append("\\")
        for (i in 0 until rowLenght -2) {
            sep.append("*")
        }
        sep.append("\\")
        list.add(sep.toString() + "\n")

        /* line 2 */
        sep = StringBuilder()
        sep.append("\\")
        sep.append(sepTitle)
        for (i in 0 until  (rowLenght - 2 - sepTitle.length - 1) ) {
            sep.append(" ")
        }
        sep.append("*\\")
        list.add(sep.toString() + "\n")

        /* line 3 */
        sep.append("\\")
        for (i in 0 until rowLenght -2) {
            sep.append("*")
        }
        sep.append("\\")
        list.add(sep.toString() + "\n")

        return list
    }

    private fun getSepLinesArray(linesBefore:Int, rowLenght:Int, linesAfter:Int, sepTitle : String = "") : List<String> {
        val list = ArrayList<String>()
        for (i in 0 until linesBefore) {
            list.add("")
        }
        list.add(genSepDelimitierLine(rowLenght))
        list.add(getSepTitleLine(sepTitle, rowLenght))
        list.add(genSepDelimitierLine(rowLenght))
        for (i in 0 until linesAfter) {
            list.add("")
        }
        return list
    }

    private fun genSepDelimitierLine(rowLength: Int):String {
        val s = StringBuilder()
        s.append(SEP_CHAR_1)
        for (i in 0 until  (rowLength - 2) ) {
            s.append(SEP_CHAR_2)
        }
        s.append(SEP_CHAR_1)
        return s.toString()
    }

    private fun getSepTitleLine(sepTitle: String, rowLength: Int) : String {
        val s = StringBuilder()
        s.append(SEP_CHAR_1)
        s.append(SEP_CHAR_2)
        s.append(SEP_CHAR_SPACE)
        s.append(sepTitle.toUpperCase())
        for (i in 0 until  (rowLength - 5 - sepTitle.length) ) {
            s.append(SEP_CHAR_SPACE)
        }
        s.append(SEP_CHAR_2)
        s.append(SEP_CHAR_1)
        return s.toString()
    }

}