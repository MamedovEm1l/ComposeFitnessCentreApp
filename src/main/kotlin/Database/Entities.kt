package Database

import org.ktorm.schema.*

object Clients : Table<Nothing>("clients") {
    val id = int("IdClients").primaryKey()
    val surname = varchar("SurName")
    val name = varchar("Name")
    val phoneNumber = varchar("PhoneNumber")
    val address = varchar("Address")
    val dateOfBirth = date("DateOfBirth")
    val passportDetails = varchar("PassportDetails")
}

object ClientAccounts : Table<Nothing>("clients_accounts") {
    val id = int("IdClientLogin").primaryKey()
    val login = varchar("login")
    val password = varchar("Password")
    val idClient = int("IdClient")
}

object Coaches : Table<Nothing>("coaches") {
    val id = int("IdCoach").primaryKey()
    val name = varchar("Name")
    val surname = varchar("SurName")
    val phoneNumber = varchar("PhoneNumber")
    val address = varchar("Address")
    val dateOfBirth = date("DateOfBirth")
    val idSpecialization = int("IdSpecialization")
    val passportDetails = varchar("PassportDetails")
    val salary = decimal("Salary")
    val genderCode = varchar("GenderCode")
}

object GroupSessions : Table<Nothing>("group_sessions") {
    val id = int("IdGroupSession").primaryKey()
    val sessionName = varchar("SessionName")
    val description = text("Description")
    val date = date("Date")
    val time = time("Time")
    val idHall = int("IdHall")
    val idCoach = int("IdCoach")
    val maximumParticipants = int("MaximumParticipants")
    val currentParticipants = int("CurrentParticipants")
}

object SessionParticipants : Table<Nothing>("session_participants") {
    val id = int("idparticipant").primaryKey()
    val idClient = int("idclient")
    val idGroupSession = int("idgroupsession")
}

object ServiceStaff : Table<Nothing>("service_staff") {
    val id = int("IdEmployee").primaryKey()
    val surname = varchar("SurName")
    val name = varchar("Name")
    val phoneNumber = varchar("PhoneNumber")
    val passportDetails = varchar("PassportDetails")
    val idPosition = int("IdPosition")
    val dateOfBirth = date("DateOfBirth")
    val address = varchar("Address")
    val genderCode = varchar("GenderCode")
}


object IndividualSessions : Table<Nothing>("individual_sessions") {
    val id = int("IdIndividualSessions").primaryKey()
    val description = varchar("Description")
    val price = decimal("Price")
    val idCoach = int("IdCoach")
}

object ClientsIndividualSessions : Table<Nothing>("clientsindividual_sessions") {
    val idClient = int("IdClients").primaryKey()
    val idIndividualSession = int("IdIndividualSessions").primaryKey()
    val date = date("Date").primaryKey()
}