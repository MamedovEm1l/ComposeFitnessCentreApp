package screens


import Database.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import components.*
import navigation.ProfileScreenState
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ProfileScreen() {
    val userLogin = UserViewModel.userLogin
    var user by remember { mutableStateOf<Client?>(null) }
    var coaches by remember { mutableStateOf(DatabaseOperations.getAllCoaches()) }

    LaunchedEffect(userLogin) {
        userLogin?.let { it ->
            val account = DatabaseOperations.getClientAccountByLogin(it)
            account?.let {
                user = DatabaseOperations.getClientById(it.idClient)
            }
        }
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf(ProfileScreenState.Profile) }
    val groupSessions by remember { mutableStateOf(DatabaseOperations.getAllGroupSessions()) }

    ColumnDividerTextComponent()
    RowDividerTextComponent(200)

    Column(modifier = Modifier.wrapContentSize()) {
        ProfileButtonComponent("Profile","images/user64.png",37, 15) { currentScreen = ProfileScreenState.Profile}
        ProfileButtonComponent("Schedule","images/schedule.png",105,12) { currentScreen = ProfileScreenState.Schedule }
        ProfileButtonComponent("Coaches","images/coaches.png",30,15) { currentScreen = ProfileScreenState.Coaches }
        ProfileButtonComponent("Clients","images/clients.png",30,15) { currentScreen = ProfileScreenState.Clients }
        ProfileButtonComponent("Employee","images/employees.png",30,8) { currentScreen = ProfileScreenState.Employee }

    }
    when (currentScreen) {
        ProfileScreenState.Profile -> {
            Column(modifier = Modifier.fillMaxSize().padding(start = 130.dp)) {
                TextComponent(value = "User Profile", 24, FontWeight.Bold, 120,100, 20)
                if (user != null) {
                    Column(modifier = Modifier.fillMaxSize().padding(end = 60.dp)){
                        TextComponent(value = "Name: ${user!!.name}",20, FontWeight.Bold, 50, startPadding = 150)
                        TextComponent(value = "Surname: ${user!!.surname}",20, FontWeight.Bold, 50, startPadding = 150)
                        TextComponent(value = "Phone Number: ${user!!.phoneNumber}",20, FontWeight.Bold, 50, startPadding = 150)
                        TextComponent(value = "Address: ${user!!.address}",20, FontWeight.Bold, 50, startPadding = 170)
                        TextComponent(value = "Date of Birth: ${user!!.dateOfBirth}",20, FontWeight.Bold, 50, startPadding = 160)
                        TextComponent(value = "Passport Details: ${user!!.passportDetails}",20, FontWeight.Bold, 50, startPadding = 160)
                        Row(modifier = Modifier.wrapContentSize()){
                            Spacer(modifier = Modifier.width(280.dp))
                            ProfileButtonComponent("Edit","images/pencil.png",10,30) {showEditDialog = true}
                        }

                    }
                } else {
                    Column(modifier = Modifier.fillMaxSize().padding(end = 80.dp)){
                        TextComponent(value = "User not found",20, FontWeight.Bold, 50, startPadding = 150)
                }}


                if (showEditDialog) {
                    EditProfileDialog(
                        user = user,
                        onDismissRequest = { showEditDialog = false },
                        onSave = { updatedClient ->
                            DatabaseOperations.updateClient(
                                id = updatedClient.id,
                                surname = updatedClient.surname,
                                name = updatedClient.name,
                                phoneNumber = updatedClient.phoneNumber,
                                address = updatedClient.address,
                                dateOfBirth = updatedClient.dateOfBirth,
                                passportDetails = updatedClient.passportDetails
                            )
                            user = updatedClient
                            showEditDialog = false
                        }
                    )
                }
           }
        }
        ProfileScreenState.Schedule -> {
            var sessions by remember { mutableStateOf(listOf<GroupSession>()) }

            LaunchedEffect(Unit) {
                sessions = DatabaseOperations.getAllGroupSessions()
            }
            TextComponent(value = "Schedule", 24, FontWeight.Bold, 20, startPadding = 200, topPadding = 20)

            Column(modifier = Modifier.wrapContentSize().padding(start = 350.dp, top = 100.dp)) {
                    sessions.forEach { session ->
                        RecordButtonComponent(
                            value = session.sessionName,
                            value2 = "${session.currentParticipants}/${session.maximumParticipants}",
                            resourceId = "images/email.png",
                            onClick = {
                                try {
                                    user?.let {
                                        DatabaseOperations.addSessionParticipant(it.id, session.id)
                                        println("Successfully registered for the session")

                                        // Обновляем список занятий, чтобы отобразить новые данные
                                        sessions = DatabaseOperations.getAllGroupSessions()
                                    }
                                } catch (e: IllegalStateException) {
                                    println("Error: ${e.message}")
                                } catch (e: Exception) {
                                    println("Error registering for the session: ${e.message}")
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
        }
        ProfileScreenState.Coaches -> {
            var editingCoach by remember { mutableStateOf<Coach?>(null) }
            var isEditing by remember { mutableStateOf(false) }
            TextComponent(value = "Coaches", 24, FontWeight.Bold, 20, startPadding = 200, topPadding = 20)
            Row(modifier = Modifier.wrapContentSize().padding(top = 150.dp)) {
                Spacer(modifier = Modifier.width(250.dp))
                CoachesTable(
                    coaches = coaches,
                    onEditClick = { coach ->
                        editingCoach = coach
                        isEditing = true
                    },
                    onDeleteClick = { id ->
                        DatabaseOperations.deleteCoach(id)
                    }
                )
            }

            // Диалог для редактирования тренера
            if (editingCoach != null && isEditing) {
                EditCoachDialog(
                    coach = editingCoach!!,
                    onDismissRequest = {
                        editingCoach = null
                        isEditing = false
                    },
                    onSave = { updatedCoach ->
                        DatabaseOperations.updateCoach(
                            id = updatedCoach.id,
                            name = updatedCoach.name,
                            surname = updatedCoach.surname,
                            phoneNumber = updatedCoach.phoneNumber,
                            address = updatedCoach.address,
                            dateOfBirth = updatedCoach.dateOfBirth,
                            idSpecialization = updatedCoach.idSpecialization,
                            passportDetails = updatedCoach.passportDetails,
                            salary = updatedCoach.salary,
                            genderCode = updatedCoach.genderCode
                        )
                        editingCoach = null
                        isEditing = false
                        coaches = DatabaseOperations.getAllCoaches()
                    }
                )
            }
        }
        ProfileScreenState.Clients -> {
            var clients by remember { mutableStateOf(DatabaseOperations.getAllClients()) }
            var editingClient by remember { mutableStateOf<Client?>(null) }
            var isEditing by remember { mutableStateOf(false) }

            TextComponent(value = "Clients", 24, FontWeight.Bold, 20, startPadding = 200, topPadding = 20)
            Row(modifier = Modifier.wrapContentSize().padding(top = 180.dp)) {
                Spacer(modifier = Modifier.width(200.dp))
                ClientsTable(
                    clients = clients,
                    onEditClick = { client ->
                        editingClient = client
                        isEditing = true
                    },
                    onDeleteClick = { id ->
                        DatabaseOperations.deleteClient(id)
                        clients = DatabaseOperations.getAllClients()
                    }
                )
            }

            // Диалог для редактирования клиента
            if (editingClient != null && isEditing) {
                EditClientDialog(
                    client = editingClient!!,
                    onDismissRequest = {
                        editingClient = null
                        isEditing = false
                    },
                    onSave = { updatedClient ->
                        DatabaseOperations.updateClient(
                            id = updatedClient.id,
                            surname = updatedClient.surname,
                            name = updatedClient.name,
                            phoneNumber = updatedClient.phoneNumber,
                            address = updatedClient.address,
                            dateOfBirth = updatedClient.dateOfBirth,
                            passportDetails = updatedClient.passportDetails
                        )
                        editingClient = null
                        isEditing = false
                        clients = DatabaseOperations.getAllClients()
                    }
                )
            }
        }

        ProfileScreenState.Employee -> {
            var serviceStaffList by remember { mutableStateOf(DatabaseOperations.getAllServiceStaff()) }
            var editingServiceStaff by remember { mutableStateOf<ServiceStaffEntity?>(null) }
            var isEditing by remember { mutableStateOf(false) }

            TextComponent(value = "Employee", 24, FontWeight.Bold, 20, startPadding = 200, topPadding = 20)

            Row(modifier = Modifier.wrapContentSize().padding(top = 180.dp)) {
                Spacer(modifier = Modifier.width(230.dp))
                ServiceStaffTable(
                    serviceStaffList = serviceStaffList,
                    onEditClick = { serviceStaff ->
                        editingServiceStaff = serviceStaff
                        isEditing = true
                    },
                    onDeleteClick = { id ->
                        DatabaseOperations.deleteServiceStaff(id)
                        serviceStaffList = DatabaseOperations.getAllServiceStaff()
                    }
                )
            }

// Диалог для редактирования сотрудника
            if (editingServiceStaff != null && isEditing) {
                EditServiceStaffDialog(
                    serviceStaff = editingServiceStaff,
                    onDismissRequest = {
                        editingServiceStaff = null
                        isEditing = false
                    },
                    onSave = { updatedServiceStaff ->
                        DatabaseOperations.updateServiceStaff(updatedServiceStaff)
                        serviceStaffList = DatabaseOperations.getAllServiceStaff()
                        editingServiceStaff = null
                        isEditing = false
                    }
                )
            }

        }
    }

}



@Preview
@Composable
fun DefaultPreviewOfProfileScreen() {
    ProfileScreen()
}