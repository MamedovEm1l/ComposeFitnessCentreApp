import androidx.compose.animation.Crossfade
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import navigation.FitnessCentreAppRouter
import screens.LoginScreen
import navigation.Screen
import screens.ProfileScreen
import screens.SignUpScreen
import java.awt.Toolkit


@Composable
@Preview
fun App() {
    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.White){
        Crossfade(targetState = FitnessCentreAppRouter.currentScree){ currentState->
            when (currentState.value){
                is Screen.SignUpScreen ->{
                    SignUpScreen()
                }
                is Screen.LoginScreen ->{
                    LoginScreen()
                }
                is Screen.ProfileScreen ->{
                    ProfileScreen()
                }
            }
        }

    }
}


fun main() = application {
    val screenSize = Toolkit.getDefaultToolkit().screenSize

    val windowWidth = screenSize.width / 5
    val windowHeight = screenSize.height / 7

    val windowState = rememberWindowState(
        width = 800.dp,
        height = 600.dp,
        position = WindowPosition(windowWidth.dp,windowHeight.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "Fitness Centre App",
        state = windowState
        ) {
        App()
    }
}
