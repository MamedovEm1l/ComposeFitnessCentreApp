import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object UserViewModel {
    var userLogin by mutableStateOf<String?>(null)
}
