package view.util

import org.apache.commons.lang3.math.NumberUtils


object ValidationUtil {

    fun validateText(text:String):Boolean {
        return false
    }

    fun validateNumber(text:String):Boolean {
        return !NumberUtils.isCreatable(text) || text.isBlank()
    }

}

enum class ValidationType {NUMBER, TEXT, NONE}