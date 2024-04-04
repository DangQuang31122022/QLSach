package server;

import buisinesLogic.ForgotPasswordLogic;
import buisinesLogic.LoginLogic;
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
            while (true) {
                if (!scanner.hasNextLine()) break;
                String type = scanner.nextLine();
                System.out.println("Received request: " + type);
                switch (type) {
                    case "login":
                        LoginLogic loginLogic = new LoginLogic(em, client);
                        loginLogic.checkLogin();
                        break;
                    case "forgotPassword":
                        ForgotPasswordLogic forgotPasswordLogic = new ForgotPasswordLogic(client, em);
                        forgotPasswordLogic.handleForgotPassword();
                        break;
                    default:
                        System.out.println("Unknown request: " + type);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    private class login {
//        private LoginDao loginDao = new LoginDao();
//        private EntityManager em;
//        public login(EntityManager em) {
//            this.em = em;
//        }
//        public boolean handleLogin(String request) {
//            JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
//            String username = jsonObject.get("username").getAsString();
//            String password = jsonObject.get("password").getAsString();
//            return loginDao.checkLogin(username, password);
//        }
//        public boolean hanndleRole(String request) {
//            TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO(em);
//            JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
//            String username = jsonObject.get("username").getAsString();
//            return taiKhoanDAO.cvTaiKhoan(username);
//        }
//    }
}


