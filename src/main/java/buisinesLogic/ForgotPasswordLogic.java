package buisinesLogic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ForgotPasswordLogic {
    private Socket client;
    private EntityManager em;
    private TaiKhoanDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;

    public ForgotPasswordLogic(Socket client, EntityManager em) {
        this.client = client;
        this.em = em;
    }
    public boolean checkMail() {
        try {
            Scanner sc = new Scanner(client.getInputStream());
            String email = sc.nextLine();
            nhanVienDAO = new NhanVienDAO(em);
            if (nhanVienDAO.dieuKienQuenMatkhau(email)) {
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean changePassword() {
        try {
            taiKhoanDAO = new TaiKhoanDAO(em);
            Scanner sc = new Scanner(client.getInputStream());
            String request = sc.nextLine();
            JsonObject jsonObject = new JsonParser().parse(request).getAsJsonObject();
            String email = jsonObject.get("email").getAsString();
            String password = jsonObject.get("password").getAsString();
            TaiKhoan taiKhoan = taiKhoanDAO.timTaiKhoanByEmail(email);
            if (taiKhoan != null) {
                taiKhoanDAO.doiMatKhauTaiKhoan(taiKhoan, password);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void handleForgotPassword() {
        try {
            Scanner sc = new Scanner(client.getInputStream());
            String request = sc.nextLine();
            if (request.equals("checkMail")) {
                boolean checkMail = checkMail();
                if (checkMail) {
                    System.out.println("Check mail success");
                    client.getOutputStream().write(1);
                } else {
                    System.out.println("Check mail failed");
                    client.getOutputStream().write(0);
                }
            } else if (request.equals("changePassword")) {
                boolean changePassword = changePassword();
                if (changePassword) {
                    System.out.println("Change password success");
                    client.getOutputStream().write(1);
                } else {
                    System.out.println("Change password failed");
                    client.getOutputStream().write(0);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
