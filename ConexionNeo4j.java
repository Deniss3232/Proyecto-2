import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class ConexionNeo4j {
    private final Driver driver;

    public ConexionNeo4j(String uri, String usuario, String contrasena) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(usuario, contrasena));
    }

    public Session obtenerSesion() {
        return driver.session();
    }

    public void cerrar() {
        if (driver != null) {
            driver.close();
        }
    }
}
