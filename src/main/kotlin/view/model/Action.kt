package view.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.filled.MultilineChart
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.File

data class Action(
    val type : ActionType,
    var fileName : String? = null,
    var filePath : File? = null,
    var separatorTitle:String? = null,
    var separatorLinesBf:Int? = null,
    var separatorLinesAf:Int? = null,
    var separatorRowLength:Int? = null,
) {

    fun getTitle() = when(type) {
        ActionType.ACTION_ADD_SEC_SEP -> "Section Separator"
        ActionType.ACTION_ADD_FILE_SEP -> "File Separator"
        ActionType.ACTION_ADD_FILE -> fileName
    }

    fun getSubtitle() = when(type) {
        ActionType.ACTION_ADD_SEC_SEP -> separatorTitle
        ActionType.ACTION_ADD_FILE -> filePath?.path
        ActionType.ACTION_ADD_FILE_SEP -> ""
    }

    fun getIcon() : ImageVector = when(type) {
        ActionType.ACTION_ADD_SEC_SEP -> Icons.Filled.MultilineChart
        ActionType.ACTION_ADD_FILE -> Icons.Filled.FilePresent
        ActionType.ACTION_ADD_FILE_SEP -> Icons.Filled.HorizontalRule
    }

}



enum class ActionType {ACTION_ADD_FILE, ACTION_ADD_SEC_SEP, ACTION_ADD_FILE_SEP}