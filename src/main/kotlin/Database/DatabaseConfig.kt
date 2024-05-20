import org.ktorm.database.Database

object DatabaseConfig {
    val database: Database = Database.connect(
        url = "jdbc:postgresql://localhost:5432/Gym",
        driver = "org.postgresql.Driver",
        user = "administrator",
        password = "Admin2005"
    )
}
