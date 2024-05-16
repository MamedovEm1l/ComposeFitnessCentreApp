package screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import components.*

@Composable
fun LoginScreen(onNavigate: () -> Unit) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    fun navigateToSignUp() {
        onNavigate()
    }
    fun navigateToSignIn() {
        onNavigate()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextComponent(value = "Hey there,", 24, FontWeight.Normal, 40)
            TextComponent(value = "Welcome Back", 30, FontWeight.Bold, 0)
            Spacer(modifier = Modifier.height(15.dp))
            MyTextFilledComponent(labelValue = "Email", resourceId = "images/email.png"){
                    newValue -> emailState.value = newValue
            }
            PasswordTextFilledComponent(labelValue = "Password", resourceId = "images/lock.png"){
                    newValue -> passwordState.value = newValue
            }
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent("Login") { navigateToSignIn()}
            ButtonComponent("Sign Up") {navigateToSignUp()}

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
}