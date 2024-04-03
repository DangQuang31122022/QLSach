package buisinesLogic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.TaiKhoanDAO;
import jakarta.persistence.EntityManager;
import server.ProgressRunnable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginLogic {
    private Socket client;
    private LoginDao loginDao = new LoginDao();
    private EntityManager em;
    public LoginLogic(EntityManager em, Socket client) {
        this.em = em;
        this.client = client;
    }
    public boolean handleLogin(String request) {
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();
        return loginDao.checkLogin(username, password);
    }
    public boolean hanndleRole(String request) {
        TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(em);
        JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        return taiKhoanDAO.cvTaiKhoan(username);
    }
    public void checkLogin() {
        // read json request
        try {
            Scanner sc = new Scanner(client.getInputStream());
            String request = sc.nextLine();
            boolean loginSuccess = handleLogin(request);
            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            if (loginSuccess) {
                System.out.println("Login success");
                boolean isManager = hanndleRole(request);

                Map<String, Boolean> responseMap = new HashMap<>();
                responseMap.put("login", loginSuccess);
                responseMap.put("role", isManager);

                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseMap);
                pw.println(jsonResponse);
            } else {
                System.out.println("Login failed");
                Map<String, Boolean> responseMap = new HashMap<>();
                responseMap.put("login", loginSuccess);
                pw.println(new Gson().toJson(responseMap));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
