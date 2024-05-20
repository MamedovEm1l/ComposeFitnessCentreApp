
package components

import Database.Client
import Database.Coach
import Database.ServiceStaff
import Database.ServiceStaffEntity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import componentShapes
import org.jetbrains.skia.FontWidth
import ui.theme.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TextComponent(value: String, fontSize: Int, fontWeight: FontWeight, heightIn: Int, startPadding: Int = 0, topPadding: Int = 0, endPadding: Int = 0){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = heightIn.dp)
            .padding(start = startPadding.dp, top = topPadding.dp, end = endPadding.dp),
        style = TextStyle(
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}
@Composable
fun loadImageResource(resourceId: String) = painterResource(resourceId)

@Composable
fun MyTextFilledComponent(labelValue: String, resourceId: String, onValueChanged: (String) -> Unit, errorText: String = ""){
    val imagePainter = loadImageResource(resourceId)

    val textValue = remember{
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
    ) {
    OutlinedTextField(
        modifier = Modifier
            .align(Alignment.Center)
            .clip(componentShapes.small)
        ,
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(painter = imagePainter, "", modifier = Modifier.size(20.dp).padding(top = 5.dp))
        }
        )
        if (errorText.isNotEmpty()) {
            Text(
                errorText,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 30.dp, bottom = 22.dp)
            )
        }
    }
}
@Composable
fun TextComponent2(value: String, color: Color = Color.Black) {
    Text(text = value, color = color)
}
@Composable
fun PasswordTextFilledComponent(labelValue: String, resourceId: String, onValueChanged: (String) -> Unit, errorText: String = ""){
    val imagePainter = loadImageResource(resourceId)

    val password = remember{
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp)
    ) {
    OutlinedTextField(
        modifier = Modifier
            .align(Alignment.Center)
            .clip(componentShapes.small)
        ,
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            backgroundColor = BgColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(painter = imagePainter, "", modifier = Modifier.size(20.dp).padding(top = 5.dp))
        },
        trailingIcon = {
            val iconImage = if (passwordVisible.value) {
                loadImageResource("images/eye.png")
            } else {
                loadImageResource("images/hide.png")
            }

            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(
                onClick = { passwordVisible.value = !passwordVisible.value },
            ) {
                Icon(iconImage, contentDescription = description,Modifier.size(20.dp).padding(top = 5.dp))
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
        )
        if (errorText.isNotEmpty()) {
            Text(
                errorText,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 30.dp, bottom = 22.dp)
            )
        }
    }
}

@Composable
fun ButtonComponent(value: String,errorText: String = "", onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 15.dp),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(300.dp)
                .height(48.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            elevation = ButtonDefaults.elevation(0.dp),
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,

                )

            }

        }
        if (errorText.isNotEmpty()) {
            Text(
                errorText,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 30.dp)
            )
        }
    }

}

@Composable
fun ProfileButtonComponent(value: String, resourceId: String, paddingTop: Int, paddingStart: Int, onClick: () -> Unit) {
    val imagePainter = loadImageResource(resourceId)
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(top = paddingTop.dp, start = 10.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(180.dp)
                .height(50.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            elevation = ButtonDefaults.elevation(0.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.horizontalGradient(listOf(Secondary, Primary))
                            )
                            .border(1.dp, Color.White, CircleShape)
                            .padding(10.dp)
                    )
                    Text(
                        text = value,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp, start = paddingStart.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RecordButtonComponent(value: String, value2: String, resourceId: String, onClick: () -> Unit) {
    val imagePainter = loadImageResource(resourceId)
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(70.dp)
            .padding(start = 10.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Secondary, Primary)),
                shape = RoundedCornerShape(10.dp)
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterStart)
        ) {
//            Icon(
//                painter = imagePainter,
//                contentDescription = null,
//                modifier = Modifier
//                    .size(40.dp)
//                    .clip(CircleShape)
//                    .background(
//                        brush = Brush.horizontalGradient(listOf(Secondary, Primary))
//                    )
//                    .border(1.dp, Color.White, CircleShape)
//                    .padding(10.dp)
//            )
//            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = value,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = value2,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.wrapContentSize().padding(top = 5.dp)) {
                Button(
                    onClick = onClick,
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp),
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Record",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Composable
fun EditClientDialog(
    client: Client,
    onDismissRequest: () -> Unit,
    onSave: (Client) -> Unit
) {
    var name by remember { mutableStateOf(client.name) }
    var surname by remember { mutableStateOf(client.surname) }
    var phoneNumber by remember { mutableStateOf(client.phoneNumber) }
    var address by remember { mutableStateOf(client.address) }
    var dateOfBirth by remember { mutableStateOf(client.dateOfBirth.format(DateTimeFormatter.ISO_DATE)) }
    var passportDetails by remember { mutableStateOf(client.passportDetails) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Box(modifier = Modifier.wrapContentSize().padding(start = 150.dp)) { Text(text = "Edit Client") } },
        text = {
            Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                    TextField(value = surname, onValueChange = { surname = it }, label = { Text("Surname") })
                    TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                    TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                    TextField(value = dateOfBirth, onValueChange = { dateOfBirth = it }, label = { Text("Date of Birth (YYYY-MM-DD)") })
                    TextField(value = passportDetails, onValueChange = { passportDetails = it }, label = { Text("Passport Details") })
                }
            }
        },
        confirmButton = {
            Box(modifier = Modifier.wrapContentSize().padding(top = 10.dp)) {
                ButtonComponent(onClick = {
                    val updatedClient = client.copy(
                        name = name,
                        surname = surname,
                        phoneNumber = phoneNumber,
                        address = address,
                        dateOfBirth = LocalDate.parse(dateOfBirth),
                        passportDetails = passportDetails
                    )
                    onSave(updatedClient)
                }, value = "Save")
            }
        },
        dismissButton = {
            Box(modifier = Modifier.wrapContentSize()) {
                ButtonComponent(onClick = onDismissRequest, value = "Cancel")
            }
        }
    )
}
@Composable
fun ClientsTable(
    clients: List<Client>,
    onEditClick: (Client) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    // Объявляем состояние для списка клиентов
    val updatedClientsState = rememberUpdatedState(clients)

    // Контейнер для вертикального скроллинга
    Column(
        modifier = Modifier
            .width(500.dp)
            .padding()
            .verticalScroll(rememberScrollState())
    ) {
        // Заголовки столбцов
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxSize()) {
            Column {
                // Заголовки столбцов
                Row(Modifier.background(Color.LightGray).padding(8.dp)) {
                    Text("Name", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 20.dp))
                    Text("Surname", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 20.dp))
                    Text("Phone Number", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 20.dp))
                    Text("Address", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 20.dp))
                    Text("Date of Birth", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 20.dp))
                    Text("Passport Details", fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 20.dp))
                    Text("Actions", fontWeight = FontWeight.Bold)
                }


                // Тело таблицы с данными клиентов
                updatedClientsState.value.forEach { client ->
                    Row(modifier = Modifier.wrapContentHeight().padding(top = 10.dp)) {
                        Text(client.name, modifier = Modifier.padding(end = 20.dp))
                        Text(client.surname, modifier = Modifier.padding(end = 20.dp))
                        Text(client.phoneNumber, modifier = Modifier.padding(end = 20.dp))
                        Text(client.address, modifier = Modifier.padding(end = 20.dp))
                        Text(client.dateOfBirth.toString(), modifier = Modifier.padding(end = 20.dp))
                        Text(client.passportDetails, modifier = Modifier.padding(end = 20.dp))
                        IconButton(onClick = { onEditClick(client) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { onDeleteClick(client.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CoachesTable(
    coaches: List<Coach>,
    onEditClick: (Coach) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    val updatedCoachesState = rememberUpdatedState(coaches)

    // Контейнер для вертикального скроллинга
    Column(
        modifier = Modifier
            .width(500.dp)
            .padding()
            .verticalScroll(rememberScrollState())
    ) {

        // Контейнер для горизонтального скроллинга
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxSize()) {
            Column {
                // Заголовки столбцов
                Row(Modifier.background(Color.LightGray).padding(8.dp)) {
                    Text("ID", Modifier.width(120.dp))
                    Text("Name", Modifier.width(120.dp))
                    Text("Surname", Modifier.width(120.dp))
                    Text("Phone Number", Modifier.width(120.dp))
                    Text("Address", Modifier.width(120.dp))
                    Text("Date of Birth", Modifier.width(120.dp))
                    Text("Specialization ID", Modifier.width(120.dp))
                    Text("Passport Details", Modifier.width(120.dp))
                    Text("Salary", Modifier.width(120.dp))
                    Text("Gender Code", Modifier.width(120.dp))
                    Text("Actions", Modifier.width(120.dp))
                }

                // Тело таблицы с данными тренеров
                updatedCoachesState.value.forEach { coach ->
                    Row(Modifier.padding(8.dp)) {
                        Text(coach.id.toString(), Modifier.width(120.dp))
                        Text(coach.name, Modifier.width(120.dp))
                        Text(coach.surname, Modifier.width(120.dp))
                        Text(coach.phoneNumber, Modifier.width(120.dp))
                        Text(coach.address, Modifier.width(120.dp))
                        Text(coach.dateOfBirth.toString(), Modifier.width(120.dp))
                        Text(coach.idSpecialization.toString(), Modifier.width(120.dp))
                        Text(coach.passportDetails, Modifier.width(120.dp))
                        Text(coach.salary.toString(), Modifier.width(120.dp))
                        Text(coach.genderCode, Modifier.width(120.dp))
                        Row(Modifier.width(120.dp)) {
                            IconButton(onClick = { onEditClick(coach) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = { onDeleteClick(coach.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun EditCoachDialog(
    coach: Coach,
    onDismissRequest: () -> Unit,
    onSave: (Coach) -> Unit
) {
    var name by remember { mutableStateOf(coach.name) }
    var surname by remember { mutableStateOf(coach.surname) }
    var phoneNumber by remember { mutableStateOf(coach.phoneNumber) }
    var address by remember { mutableStateOf(coach.address) }
    var dateOfBirth by remember { mutableStateOf(coach.dateOfBirth.format(DateTimeFormatter.ISO_DATE)) }
    var idSpecialization by remember { mutableStateOf(coach.idSpecialization) }
    var passportDetails by remember { mutableStateOf(coach.passportDetails) }
    var salary by remember { mutableStateOf(coach.salary) }
    var genderCode by remember { mutableStateOf(coach.genderCode) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Edit Coach") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = surname, onValueChange = { surname = it }, label = { Text("Surname") })
                TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                TextField(value = dateOfBirth, onValueChange = { dateOfBirth = it }, label = { Text("Date of Birth (YYYY-MM-DD)") })
                TextField(value = idSpecialization.toString(), onValueChange = { idSpecialization = it.toIntOrNull() ?: 0 }, label = { Text("Specialization ID") })
                TextField(value = passportDetails, onValueChange = { passportDetails = it }, label = { Text("Passport Details") })
                TextField(value = salary.toString(), onValueChange = { salary = it.toBigDecimalOrNull() ?: BigDecimal.ZERO }, label = { Text("Salary") })
                TextField(value = genderCode, onValueChange = { genderCode = it }, label = { Text("Gender Code") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedCoach = coach.copy(
                    name = name,
                    surname = surname,
                    phoneNumber = phoneNumber,
                    address = address,
                    dateOfBirth = LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_DATE),
                    idSpecialization = idSpecialization,
                    passportDetails = passportDetails,
                    salary = salary,
                    genderCode = genderCode
                )
                onSave(updatedCoach)
                // Обновляем состояние после сохранения изменений
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}



@Composable
fun UnderLinedNormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = 10.dp),
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight. Normal,
            fontStyle = FontStyle.Normal
        ), color = GrayColor,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline
    )
}

@Composable
fun IconComponent(resourceId: String){
    val imagePainter = loadImageResource(resourceId)
    Box(
        modifier = Modifier
            .padding(28.dp) // Обратный паддинг для компенсации
    ) {
        Icon(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, Color.Black, CircleShape)
                .padding(10.dp)
        )
    }
}

@Composable
fun ColumnDividerTextComponent() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 200.dp)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), // Отступ слева
            color = Color.Gray,
            thickness = 1.dp // Толщина линии
        )
    }
}

@Composable
fun RowDividerTextComponent(width: Int) {
    Row(
        modifier = Modifier
            .width(width.dp)
            .padding(top = 125.dp)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), // Отступ слева
            color = Color.Gray,
            thickness = 1.dp // Толщина линии
        )
    }
}

@Composable
fun EditProfileDialog(
    user: Client?,
    onDismissRequest: () -> Unit,
    onSave: (Client) -> Unit
) {
    var name by remember { mutableStateOf(user?.name ?: "") }
    var surname by remember { mutableStateOf(user?.surname ?: "") }
    var phoneNumber by remember { mutableStateOf(user?.phoneNumber ?: "") }
    var address by remember { mutableStateOf(user?.address ?: "") }
    var dateOfBirth by remember { mutableStateOf(user?.dateOfBirth?.format(DateTimeFormatter.ISO_DATE) ?: "") }
    var passportDetails by remember { mutableStateOf(user?.passportDetails ?: "") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {Box(modifier = Modifier.wrapContentSize().padding(start = 150.dp)){ Text(text = "Edit Profile") }},
        text = {
            Box(modifier = Modifier.fillMaxSize().padding(top = 50.dp)) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                    TextField(value = surname, onValueChange = { surname = it }, label = { Text("Surname") })
                    TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                    TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                    TextField(value = dateOfBirth, onValueChange = { dateOfBirth = it }, label = { Text("Date of Birth (YYYY-MM-DD)") })
                    TextField(value = passportDetails, onValueChange = { passportDetails = it }, label = { Text("Passport Details") })
                }
            }
        },
        confirmButton = {
            Box(modifier = Modifier.wrapContentSize().padding(top = 10.dp)){
                ButtonComponent(onClick = {
                    val updatedClient = user?.copy(
                        name = name,
                        surname = surname,
                        phoneNumber = phoneNumber,
                        address = address,
                        dateOfBirth = LocalDate.parse(dateOfBirth),
                        passportDetails = passportDetails
                    )
                    if (updatedClient != null) {
                        onSave(updatedClient)
                    }
                }, value = "Save")
            }
        },
        dismissButton = {
            Box(modifier = Modifier.wrapContentSize()){
                ButtonComponent(onClick = onDismissRequest, value = "Cancel")
            }
        }
    )
}
@Composable
fun EditServiceStaffDialog(
    serviceStaff: ServiceStaffEntity?,
    onDismissRequest: () -> Unit,
    onSave: (ServiceStaffEntity) -> Unit
) {
    var surname by remember { mutableStateOf(serviceStaff?.surname ?: "") }
    var name by remember { mutableStateOf(serviceStaff?.name ?: "") }
    var phoneNumber by remember { mutableStateOf(serviceStaff?.phoneNumber ?: "") }
    var passportDetails by remember { mutableStateOf(serviceStaff?.passportDetails ?: "") }
    var idPosition by remember { mutableStateOf(serviceStaff?.idPosition ?: 0) }
    var dateOfBirth by remember { mutableStateOf(serviceStaff?.dateOfBirth?.format(DateTimeFormatter.ISO_DATE) ?: "") }
    var address by remember { mutableStateOf(serviceStaff?.address ?: "") }
    var genderCode by remember { mutableStateOf(serviceStaff?.genderCode ?: "") }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Edit Service Staff") },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        ServiceStaffEntity(
                            id = serviceStaff?.id ?: 0,
                            surname = surname,
                            name = name,
                            phoneNumber = phoneNumber,
                            passportDetails = passportDetails,
                            idPosition = idPosition,
                            dateOfBirth = LocalDate.parse(dateOfBirth),
                            address = address,
                            genderCode = genderCode
                        )
                    )
                    onDismissRequest()
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        text = {
            Column {
                TextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Surname") }
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") }
                )
                TextField(
                    value = passportDetails,
                    onValueChange = { passportDetails = it },
                    label = { Text("Passport Details") }
                )
                TextField(
                    value = idPosition.toString(),
                    onValueChange = { idPosition = it.toIntOrNull() ?: 0 },
                    label = { Text("Position ID") }
                )
                TextField(
                    value = dateOfBirth,
                    onValueChange = { dateOfBirth = it },
                    label = { Text("Date of Birth") }
                )
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") }
                )
                TextField(
                    value = genderCode,
                    onValueChange = { genderCode = it },
                    label = { Text("Gender Code") }
                )
            }
        }
    )
}

@Composable
fun ServiceStaffTable(
    serviceStaffList: List<ServiceStaffEntity>,
    onEditClick: (ServiceStaffEntity) -> Unit,
    onDeleteClick: (Int) -> Unit
) {
    // Контейнер для вертикального скроллинга
    Column(
        modifier = Modifier
            .width(500.dp)
            .padding()
            .verticalScroll(rememberScrollState())
    ) {

        Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxSize()) {
            Column {
                // Заголовки столбцов
                Row(Modifier.background(Color.LightGray).padding(8.dp)) {
                    Text("ID", Modifier.width(120.dp))
                    Text("Name", Modifier.width(120.dp))
                    Text("Surname", Modifier.width(120.dp))
                    Text("Phone Number", Modifier.width(120.dp))
                    Text("Passport Details", Modifier.width(120.dp))
                    Text("Position ID", Modifier.width(120.dp))
                    Text("Date of Birth", Modifier.width(120.dp))
                    Text("Address", Modifier.width(120.dp))
                    Text("Gender Code", Modifier.width(120.dp))
                    Text("Actions", Modifier.width(120.dp))
                }

                // Тело таблицы с данными сотрудников
                serviceStaffList.forEach { staff ->
                    Row(Modifier.padding(8.dp)) {
                        Text(staff.id.toString(), Modifier.width(120.dp))
                        Text(staff.name, Modifier.width(120.dp))
                        Text(staff.surname, Modifier.width(120.dp))
                        Text(staff.phoneNumber, Modifier.width(120.dp))
                        Text(staff.passportDetails, Modifier.width(120.dp))
                        Text(staff.idPosition.toString(), Modifier.width(120.dp))
                        Text(staff.dateOfBirth.toString(), Modifier.width(120.dp))
                        Text(staff.address, Modifier.width(120.dp))
                        Text(staff.genderCode, Modifier.width(120.dp))
                        Row(Modifier.width(120.dp)) {
                            IconButton(onClick = { onEditClick(staff) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                            }
                            IconButton(onClick = { onDeleteClick(staff.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
