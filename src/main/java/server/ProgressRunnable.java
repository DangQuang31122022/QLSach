package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.TaiKhoanDAO;
import jakarta.persistence.EntityManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProgressRunnable implements Runnable {
    private Socket client;
    private EntityManager em;
    public ProgressRunnable(Socket client, EntityManager em) {
        this.client = client;
        this.em = em;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(client.getInputStream());
            String type = scanner.nextLine();
            System.out.println("Received request: " + type);
            switch (type) {
                case "login":
                    // read json request
                    Scanner sc = new Scanner(client.getInputStream());
                    String request = sc.nextLine();
                    login login = new login(request, em);
                    boolean loginSuccess = login.handleLogin(request);
                    PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
                    if (loginSuccess) {
                        System.out.println("Login success");
                        boolean isManager = login.hanndleRole(request);

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
                    break;
                default:
                    System.out.println("Unknown request: " + type);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private class login {
        private String request;
        private LoginDao loginDao = new LoginDao();
        private TaiKhoanDAO taiKhoanDAO;
        private EntityManager em;
        public login(String request, EntityManager em) {
            this.request = request;
            this.em = em;
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
    }
}


