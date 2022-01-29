package view.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilePresent
import androidx.compose.material.icons.filled.Maximize
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
) {

    fun getTitle() = when(type) {
        ActionType.ACTION_ADD_SEP -> "Separator"
        ActionType.ACTION_ADD_FILE -> fileName
    }

    fun getSubtitle() = when(type) {
        ActionType.ACTION_ADD_SEP -> separatorTitle
        ActionType.ACTION_ADD_FILE -> filePath?.path
    }

    fun getIcon() : ImageVector = when(type) {
        ActionType.ACTION_ADD_SEP -> Icons.Filled.MultilineChart
        ActionType.ACTION_ADD_FILE -> Icons.Filled.FilePresent
    }

}



enum class ActionType {ACTION_ADD_FILE, ACTION_ADD_SEP}