package Database

import org.ktorm.dsl.*
import java.math.BigDecimal
import java.sql.Types
import java.time.LocalDate
import java.time.LocalTime

object DatabaseOperations {
    private val db = DatabaseConfig.database
    fun addCoach(
        name: String, surname: String, phoneNumber: String, address: String,
        dateOfBirth: LocalDate, idSpecialization: Int, passportDetails: String,
        salary: BigDecimal, genderCode: String
    ) {
        db.insert(Coaches) {
            set(it.name, name)
            set(it.surname, surname)
            set(it.phoneNumber, phoneNumber)
            set(it.address, address)
            set(it.dateOfBirth, dateOfBirth)
            set(it.idSpecialization, idSpecialization)
            set(it.passportDetails, passportDetails)
            set(it.salary, salary)
            set(it.genderCode, genderCode)
        }
    }

    fun updateCoach(
        id: Int, name: String, surname: String, phoneNumber: String, address: String,
        dateOfBirth: LocalDate, idSpecialization: Int, passportDetails: String,
        salary: BigDecimal, genderCode: String
    ) {
        db.update(Coaches) {
            set(it.name, name)
            set(it.surname, surname)
            set(it.phoneNumber, phoneNumber)
            set(it.address, address)
            set(it.dateOfBirth, dateOfBirth)
            set(it.idSpecialization, idSpecialization)
            set(it.passportDetails, passportDetails)
            set(it.salary, salary)
            set(it.genderCode, genderCode)
            where { it.id eq id }
        }
    }

    fun deleteCoach(id: Int) {
        // Удаление всех связанных индивидуальных сессий тренера
        val coachIndividualSessions = db.from(IndividualSessions)
            .select()
            .where { IndividualSessions.idCoach eq id }
            .map { it[IndividualSessions.id] }
        coachIndividualSessions.forEach { sessionId ->
            db.delete(ClientsIndividualSessions) { it.idIndividualSession eq sessionId!! }
        }
        db.delete(IndividualSessions) { it.idCoach eq id }

        // Удаление всех связанных групповых сессий тренера
        val coachGroupSessions = db.from(GroupSessions)
            .select()
            .where { GroupSessions.idCoach eq id }
            .map { it[GroupSessions.id] }
        coachGroupSessions.forEach { groupId ->
            db.delete(SessionParticipants) { it.idGroupSession eq groupId!! }
        }
        db.delete(GroupSessions) { it.idCoach eq id }

        // Удаление тренера
        db.delete(Coaches) { it.id eq id }
    }


    // Client operations
    fun addClient(
        surname: String,
        name: String,
        phoneNumber: String,
        address: String,
        dateOfBirth: LocalDate,
        passportDetails: String
    ) {
        db.insert(Clients) {
            set(it.surname, surname)
            set(it.name, name)
            set(it.phoneNumber, phoneNumber)
            set(it.address, address)
            set(it.dateOfBirth, dateOfBirth)
            set(it.passportDetails, passportDetails)
        }
    }

    fun getClientById(id: Int): Client? {
        return db.from(Clients)
            .select()
            .where { Clients.id eq id }
            .mapNotNull { row ->
                Client(
                    id = row[Clients.id] ?: return@mapNotNull null,
                    surname = row[Clients.surname] ?: return@mapNotNull null,
                    name = row[Clients.name] ?: return@mapNotNull null,
                    phoneNumber = row[Clients.phoneNumber] ?: return@mapNotNull null,
                    address = row[Clients.address] ?: return@mapNotNull null,
                    dateOfBirth = row[Clients.dateOfBirth] ?: return@mapNotNull null,
                    passportDetails = row[Clients.passportDetails] ?: return@mapNotNull null
                )
            }
            .firstOrNull()
    }


    fun getAllClients(): List<Client> {
        return db.from(Clients)
            .select()
            .map { row ->
                Client(
                    id = row[Clients.id] ?: 0,
                    surname = row[Clients.surname] ?: "",
                    name = row[Clients.name] ?: "",
                    phoneNumber = row[Clients.phoneNumber] ?: "",
                    address = row[Clients.address] ?: "",
                    dateOfBirth = row[Clients.dateOfBirth] ?: LocalDate.now(),
                    passportDetails = row[Clients.passportDetails] ?: ""
                )
            }
    }


    fun updateClient(
        id: Int,
        surname: String,
        name: String,
        phoneNumber: String,
        address: String,
        dateOfBirth: LocalDate,
        passportDetails: String
    ) {
        db.update(Clients) {
            set(it.surname, surname)
            set(it.name, name)
            set(it.phoneNumber, phoneNumber)
            set(it.address, address)
            set(it.dateOfBirth, dateOfBirth)
            set(it.passportDetails, passportDetails)
            where { it.id eq id }
        }
    }

    fun deleteClient(id: Int) {
        db.delete(Clients) {
            it.id eq id
        }
    }

    // Client Account operations
    fun addClientAccount(login: String, password: String, idClient: Int) {
        val existingAccount = db.from(ClientAccounts)
            .select()
            .where { ClientAccounts.idClient eq idClient }
            .map { it[ClientAccounts.idClient] }
            .firstOrNull()

        if (existingAccount != null) {
            throw IllegalArgumentException("Account with this client ID already exists")
        }

        val newId = (db.from(ClientAccounts)
            .select(ClientAccounts.id)
            .map { it[ClientAccounts.id] }
            .maxOrNull { it!! } ?: 0) + 1

        db.insert(ClientAccounts) {
            set(it.id, newId)
            set(it.login, login)
            set(it.password, password)
            set(it.idClient, idClient)
        }
    }


    fun getClientAccountByLogin(login: String): ClientAccount? {
        return db.from(ClientAccounts)
            .select()
            .where { ClientAccounts.login eq login }
            .mapNotNull { row ->
                ClientAccount(
                    id = row[ClientAccounts.id] ?: return@mapNotNull null,
                    login = row[ClientAccounts.login] ?: return@mapNotNull null,
                    password = row[ClientAccounts.password] ?: return@mapNotNull null,
                    idClient = row[ClientAccounts.idClient] ?: return@mapNotNull null
                )
            }
            .firstOrNull()
    }

    fun getClientIdByPhoneNumber(phoneNumber: String): Int? {
        return db.from(Clients)
            .select(Clients.id)
            .where { Clients.phoneNumber eq phoneNumber }
            .mapNotNull { row -> row[Clients.id] }
            .firstOrNull()
    }


    fun getAllCoaches(): List<Coach> {
        return db.from(Coaches).select().map { row ->
            Coach(
                id = row[Coaches.id] ?: 0,
                name = row[Coaches.name] ?: "",
                surname = row[Coaches.surname] ?: "",
                phoneNumber = row[Coaches.phoneNumber] ?: "",
                address = row[Coaches.address] ?: "",
                dateOfBirth = row[Coaches.dateOfBirth] ?: LocalDate.now(),
                idSpecialization = row[Coaches.idSpecialization] ?: 0,
                passportDetails = row[Coaches.passportDetails] ?: "",
                salary = row[Coaches.salary] ?: BigDecimal.ZERO,
                genderCode = row[Coaches.genderCode] ?: ""
            )
        }
    }


    // Group Session operations
    fun addGroupSession(
        sessionName: String,
        description: String,
        date: LocalDate,
        time: LocalTime,
        idHall: Int,
        idCoach: Int,
        maximumParticipants: Int,
        currentParticipants: Int
    ) {
        db.insert(GroupSessions) {
            set(it.sessionName, sessionName)
            set(it.description, description)
            set(it.date, date)
            set(it.time, time)
            set(it.idHall, idHall)
            set(it.idCoach, idCoach)
            set(it.maximumParticipants, maximumParticipants)
            set(it.currentParticipants, currentParticipants)
        }
    }

    fun getAllGroupSessions(): List<GroupSession> {
        return db.from(GroupSessions)
            .select()
            .map { row ->
                GroupSession(
                    id = row[GroupSessions.id] ?: 0,
                    sessionName = row[GroupSessions.sessionName] ?: "",
                    description = row[GroupSessions.description] ?: "",
                    date = row[GroupSessions.date] ?: LocalDate.now(),
                    time = row[GroupSessions.time] ?: LocalTime.now(),
                    idHall = row[GroupSessions.idHall] ?: 0,
                    idCoach = row[GroupSessions.idCoach] ?: 0,
                    maximumParticipants = row[GroupSessions.maximumParticipants] ?: 0,
                    currentParticipants = row[GroupSessions.currentParticipants] ?: 0
                )
            }
    }


    fun updateGroupSession(
        id: Int,
        sessionName: String,
        description: String,
        date: LocalDate,
        time: LocalTime,
        idHall: Int,
        idCoach: Int,
        maximumParticipants: Int,
        currentParticipants: Int
    ) {
        db.update(GroupSessions) {
            set(it.sessionName, sessionName)
            set(it.description, description)
            set(it.date, date)
            set(it.time, time)
            set(it.idHall, idHall)
            set(it.idCoach, idCoach)
            set(it.maximumParticipants, maximumParticipants)
            set(it.currentParticipants, currentParticipants)
            where { it.id eq id }
        }
    }

    fun addSessionParticipant(userId: Int, sessionId: Int) {
        // Проверка на двойную запись
        val existingParticipant = db.from(SessionParticipants)
            .select()
            .where { (SessionParticipants.idClient eq userId) and (SessionParticipants.idGroupSession eq sessionId) }
            .map { it[SessionParticipants.id] }
            .firstOrNull()

        if (existingParticipant != null) {
            throw IllegalStateException("User is already registered for this session")
        }

        val session = db.from(GroupSessions)
            .select(GroupSessions.maximumParticipants, GroupSessions.currentParticipants)
            .where { GroupSessions.id eq sessionId }
            .map {
                Pair(it[GroupSessions.maximumParticipants] ?: 0, it[GroupSessions.currentParticipants] ?: 0)
            }
            .firstOrNull()

        if (session != null && session.second >= session.first) {
            throw IllegalStateException("This session is already full")
        }

        db.useTransaction {
            // Добавление участника на занятие
            db.insert(SessionParticipants) {
                set(it.idClient, userId)
                set(it.idGroupSession, sessionId)
            }

            // Обновление количества текущих участников
            db.update(GroupSessions) {
                set(it.currentParticipants, it.currentParticipants + 1)
                where { it.id eq sessionId }
            }
        }
    }

    fun authorizeUser(login: String, password: String): AuthorizationResult {
        var userId: Int? = null
        var isGuest: Boolean? = null

        db.useConnection { conn ->
            conn.prepareCall("CALL authorize_user(?, ?, ?, ?) ").use { stmt ->
                stmt.setString(1, login)
                stmt.setString(2, password)
                stmt.registerOutParameter(3, Types.INTEGER)
                stmt.registerOutParameter(4, Types.BOOLEAN)
                stmt.execute()

                userId = stmt.getInt(3)
                isGuest = stmt.getBoolean(4)
            }

        }

        return AuthorizationResult(userId ?: 0, isGuest ?: true)
    }

    fun getSessionParticipants(idGroupSession: Int): List<SessionParticipant> {
        return db.from(SessionParticipants)
            .select()
            .where { SessionParticipants.idGroupSession eq idGroupSession }
            .map { row ->
                SessionParticipant(
                    id = row[SessionParticipants.id] ?: return@map null,
                    idClient = row[SessionParticipants.idClient] ?: return@map null,
                    idGroupSession = row[SessionParticipants.idGroupSession] ?: return@map null
                )
            }.filterNotNull()
    }

    fun hashPassword(password: String): String? {
        var hashedPassword: String? = null
        val sql = "SELECT hash_password(?)"

        db.useConnection { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, password)
                val resultSet = stmt.executeQuery()
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString(1)
                }
            }
        }

        return hashedPassword
    }

    fun deleteGroupSession(id: Int) {
        db.delete(GroupSessions) {
            it.id eq id
        }
    }

    // Service Staff operations
    fun addServiceStaff(
        surname: String,
        name: String,
        phoneNumber: String,
        passportDetails: String,
        idPosition: Int,
        dateOfBirth: LocalDate,
        address: String,
        genderCode: String
    ) {
        db.insert(ServiceStaff) {
            set(it.surname, surname)
            set(it.name, name)
            set(it.phoneNumber, phoneNumber)
            set(it.passportDetails, passportDetails)
            set(it.idPosition, idPosition)
            set(it.dateOfBirth, dateOfBirth)
            set(it.address, address)
            set(it.genderCode, genderCode)
        }
    }

    fun getAllServiceStaff(): List<ServiceStaffEntity> {
        return db.from(ServiceStaff).select().map { row ->
            ServiceStaffEntity(
                id = row[ServiceStaff.id] ?: 0,
                surname = row[ServiceStaff.surname] ?: "",
                name = row[ServiceStaff.name] ?: "",
                phoneNumber = row[ServiceStaff.phoneNumber] ?: "",
                passportDetails = row[ServiceStaff.passportDetails] ?: "",
                idPosition = row[ServiceStaff.idPosition] ?: 0,
                dateOfBirth = row[ServiceStaff.dateOfBirth] ?: LocalDate.now(),
                address = row[ServiceStaff.address] ?: "",
                genderCode = row[ServiceStaff.genderCode] ?: ""
            )
        }
    }

    fun updateServiceStaff(updatedServiceStaff: ServiceStaffEntity) {
        db.update(ServiceStaff) {
            set(it.surname, updatedServiceStaff.surname)
            set(it.name, updatedServiceStaff.name)
            set(it.phoneNumber, updatedServiceStaff.phoneNumber)
            set(it.passportDetails, updatedServiceStaff.passportDetails)
            set(it.idPosition, updatedServiceStaff.idPosition)
            set(it.dateOfBirth, updatedServiceStaff.dateOfBirth)
            set(it.address, updatedServiceStaff.address)
            set(it.genderCode, updatedServiceStaff.genderCode)
            where { it.id eq updatedServiceStaff.id }
        }
    }

    fun deleteServiceStaff(id: Int) {
        db.delete(ServiceStaff) {
            it.id eq id
        }
    }
}

    private fun <E> List<E>.maxOrNull(selector: (E) -> Int): Int? {
    var max: Int? = null
    for (element in this) {
        val value = selector(element)
        if (max == null || value > max) {
            max = value
        }
    }
    return max
}
