import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

class DatabaseConnection {
    private val url = "jdbc:postgresql://localhost:5432/Gym"
    private val user = "administrator"
    private val password = "Admin2005"
    private var connection: Connection? = null

    init {
        connection = DriverManager.getConnection(url, user, password)
    }

    fun queryTable(tableName: String): ResultSet? {
        val statement = connection?.createStatement()
        val query = "SELECT * FROM public.$tableName"
        return statement?.executeQuery(query)
    }

    fun closeConnection() {
        connection?.close()
    }
}