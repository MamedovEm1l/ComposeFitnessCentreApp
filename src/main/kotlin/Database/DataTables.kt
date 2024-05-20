package Database

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

data class ClientAccount(
    val id: Int,
    val login: String,
    val password: String,
    val idClient: Int
)

data class SessionParticipant(
    val id: Int,
    val idClient: Int,
    val idGroupSession: Int
)

data class Client(
    val id: Int,
    val surname: String,
    val name: String,
    val phoneNumber: String,
    val address: String,
    val dateOfBirth: LocalDate,
    val passportDetails: String
)

data class GroupSession(
    val id: Int,
    val sessionName: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime,
    val idHall: Int,
    val idCoach: Int,
    val maximumParticipants: Int,
    val currentParticipants: Int
)
data class AuthorizationResult(
    val userId: Int?,
    val isGuest: Boolean
)
data class Coach(
    val id: Int,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val address: String,
    val dateOfBirth: LocalDate,
    val idSpecialization: Int,
    val passportDetails: String,
    val salary: BigDecimal,
    val genderCode: String
)

data class ServiceStaffEntity(
    val id: Int,
    val surname: String,
    val name: String,
    val phoneNumber: String,
    val passportDetails: String,
    val idPosition: Int,
    val dateOfBirth: LocalDate,
    val address: String,
    val genderCode: String
)
