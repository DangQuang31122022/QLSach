import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import server.ThreadPoolServer;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        EntityManager em =  Persistence.createEntityManagerFactory("QLS MARIADB").createEntityManager();
//        ThreadPoolServer server = new ThreadPoolServer();
//        server.createServerSocket(5000, em);
    }
}
