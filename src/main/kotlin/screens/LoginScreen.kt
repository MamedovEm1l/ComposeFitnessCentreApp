package screens

import Database.ClientAccounts
import Database.DatabaseOperations
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
import navigation.FitnessCentreAppRouter
import navigation.Screen

@Composable
fun LoginScreen() {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val errorTextEmail = remember { mutableStateOf("") }
    val errorTextPassword = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }


    val navigateToSignIn = when {
        !InputValidator.isValidEmail(emailState.value) -> false
        !InputValidator.isValidPassword(passwordState.value) -> false
        else -> true
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
            MyTextFilledComponent(
                labelValue = "Email",
                resourceId = "images/email.png",
                onValueChanged = { newValue ->
                    emailState.value = newValue
                    errorTextEmail.value = if (!InputValidator.isValidEmail(newValue)) "Invalid email format" else ""
                },
                errorText = errorTextEmail.value
            )
            PasswordTextFilledComponent(
                labelValue = "Password",
                resourceId = "images/lock.png",
                onValueChanged = { newValue ->
                    passwordState.value = newValue
                    errorTextPassword.value = if (!InputValidator.isValidPassword(newValue)) "Password must be \nat least 8 characters" else ""
                },
                errorText = errorTextPassword.value
            )
            Spacer(modifier = Modifier.height(20.dp))
            ButtonComponent("Login", onClick = {
                val result = DatabaseOperations.authorizeUser(emailState.value, passwordState.value)
                if (result.userId != null && !result.isGuest) {
                    // Пользователь успешно аутентифицирован
                    UserViewModel.userLogin = emailState.value // Сохраните логин пользователя
                    FitnessCentreAppRouter.navigateTo(Screen.ProfileScreen)
                } else {
                    // Пользователь не найден или введен неверный пароль
                    errorMessage.value = "Invalid login or password"
                }
            })


            ButtonComponent("Sign Up") {
                FitnessCentreAppRouter.navigateTo(Screen.SignUpScreen)
            }
            if (errorMessage.value.isNotEmpty()) {
                TextComponent2(value = errorMessage.value, color = Color.Red)
            }

        }
    }
}


@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen()
}