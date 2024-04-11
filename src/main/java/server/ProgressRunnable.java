package server;

import buisinesLogic.*;
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
                        loginLogic.checkLogin(scanner);
                        break;
                    case "forgotPassword":
                        ForgotPasswordLogic forgotPasswordLogic = new ForgotPasswordLogic(client, em);
                        forgotPasswordLogic.handleForgotPassword(scanner);
                        break;
                    case "customer":
                        CustomerLogic customerLogic = new CustomerLogic(client, em);
                        customerLogic.handleClientRequest(scanner);
                        break;
                    case "staff":
                        StaffLogic staffLogic = new StaffLogic(client, em);
                        staffLogic.handleClientRequest(scanner);
                        break;
                    case "supplier":
                        SupplierLogic supplierLogic = new SupplierLogic(client, em);
                        supplierLogic.handleClientRequest(scanner);
                        break;
                    default:
                        System.out.println("Unknown request: " + type);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


