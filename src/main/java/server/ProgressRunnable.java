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
import entity.ChiTietHoaDon;
import entity.HoaDon;
import jakarta.persistence.EntityManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Date;
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
                    case "loadTblHoaDon":
                    	QuanLyHoaDonLogic loadTblHD = new QuanLyHoaDonLogic(em, client);
                    	List<HoaDon> dshd =  loadTblHD.loadAllDataHD();
                    	out.writeObject(dshd);
                    	double[] listTongTien = loadTblHD.tinhTongTien(dshd);
                    	out.writeObject(listTongTien);
                    	break;
                    case "loadTblChiTietHoaDon":
                    	QuanLyHoaDonLogic loadTblCTHD = new QuanLyHoaDonLogic(em, client);
                    	String hdID = scanner.nextLine();
                    	List<ChiTietHoaDon> dsCTHD = loadTblCTHD.getAllCTHDByID(hdID);                    	
                    	out.writeObject(dsCTHD);
                    	break;
                    case "btnTimKiemHD":
                    	QuanLyHoaDonLogic btnTimKiemHD = new QuanLyHoaDonLogic(em, client);
                    	String from = scanner.nextLine();
                    	String to = scanner.nextLine();
                    	Date ngayBatDau = Date.valueOf(from);
            			Date ngayKetThuc = Date.valueOf(to);
                    	List<HoaDon> dsHDTK = btnTimKiemHD.getDsHDByDate(ngayBatDau, ngayKetThuc);
                    	out.writeObject(dsHDTK);
                    	double[] listTongTienTK = btnTimKiemHD.tinhTongTien(dsHDTK);
                    	out.writeObject(listTongTienTK);
                    	break;
                    case "btnXuatChiTietHoaDon":
                    	QuanLyHoaDonLogic btnXuatCTHD = new QuanLyHoaDonLogic(em, client);
                    	String IDbtnXuatCTHD = scanner.nextLine();
                    	HoaDon hdBtnXuatCTHD = btnXuatCTHD.getHDByID(IDbtnXuatCTHD);
                    	out.writeObject(hdBtnXuatCTHD);
                    	double tongTienBtnXuatCTHD = btnXuatCTHD.tinhTongTien1HD(hdBtnXuatCTHD);
                    	out.writeDouble(tongTienBtnXuatCTHD);
                    	List<ChiTietHoaDon> cthdBtnXuatCTHD = btnXuatCTHD.getAllCTHDByID(IDbtnXuatCTHD);
                    	out.writeObject(cthdBtnXuatCTHD);
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


