import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class ConexionNeo4j {
    private final Driver driver;

    // Constructor para conectar al servidor Neo4j
    public ConexionNeo4j(String uri, String usuario, String contrasena) {
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(usuario, contrasena)); // Establece la conexión
    }

    // Abre una nueva sesión
    public Session obtenerSesion() {
        return driver.session(); // Devuelve una sesión para ejecutar queries
    }

    // Cierra la conexión
    public void cerrar() {
        driver.close(); // Finaliza la conexión con la base de datos
    }
}
