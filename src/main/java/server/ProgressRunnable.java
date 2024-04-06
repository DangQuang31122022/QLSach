package server;

import buisinesLogic.ForgotPasswordLogic;
import buisinesLogic.LoginLogic;
import buisinesLogic.QuanLyHoaDonLogic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.TaiKhoanDAO;
import entity.HoaDon;
import jakarta.persistence.EntityManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
//            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
//            String type = "";
            while (true) {
                if (!scanner.hasNextLine()) break;
                String type = scanner.nextLine();
                System.out.println("Received request: " + type);
//            	type = in.readUTF();
                switch (type) {
                    case "login":
                        LoginLogic loginLogic = new LoginLogic(em, client);
                        loginLogic.checkLogin();
                        break;
                    case "forgotPassword":
                        ForgotPasswordLogic forgotPasswordLogic = new ForgotPasswordLogic(client, em);
                        forgotPasswordLogic.handleForgotPassword();
                        break;
                    case "loadTblHoaDon":
                    	QuanLyHoaDonLogic loadAllDataHD = new QuanLyHoaDonLogic(em, client);
                    	List<HoaDon> dshd =  loadAllDataHD.loadAllDataHD();
                    	dshd.forEach(System.out::println);
                    	out.writeObject(dshd);
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


