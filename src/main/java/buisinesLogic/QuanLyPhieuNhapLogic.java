package buisinesLogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

import dao.ChiTietPhieuNhapDAO;
import dao.PhieuNhapDAO;
import entity.ChiTietPhieuNhap;
import entity.NhanVien;
import jakarta.persistence.EntityManager;

public class QuanLyPhieuNhapLogic {
	private Socket socket;
	private EntityManager em; 
	private PhieuNhapDAO phieuNhapDao;
	private ChiTietPhieuNhapDAO chiTietPhieuNhapDao;
	
	 public QuanLyPhieuNhapLogic(Socket socket, EntityManager em) {
		this.socket = socket;
		this.em = em;
		this.phieuNhapDao = new PhieuNhapDAO(em);
		this.chiTietPhieuNhapDao = new ChiTietPhieuNhapDAO(em);
	}
	public void handleClientRequest(Scanner scanner) {
	        String action = scanner.nextLine();
	        switch (action) {
	            case "getAll":
	                getAll();
	                break;
	            default:
	                System.out.println("Unknown action: " + action);
	        }
	    }
//	    public void getById(Scanner scanner) {
//	        String id = scanner.nextLine();
//	        NhanVien nhanVien = nhanVienDAO.getNhanVienByID(id);
//	        try {
//	            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//	            printWriter.println(new Gson().toJson(nhanVien));
//	            printWriter.flush();
//	        } catch (IOException e) {
//	            throw new RuntimeException(e);
//	        }
//
//	    }
	    public void getAll() {
	        try {
	            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
	            printWriter.println(new Gson().toJson(phieuNhapDao.getAllPhieuNhap()));
	            printWriter.flush();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
//	    public void add(Scanner scanner) {
//	        String json = scanner.nextLine();
//	        NhanVien nhanVien = new Gson().fromJson(json, NhanVien.class);
//	        // check email exists
//	        if (nhanVienDAO.getNhanVienByGmail(nhanVien.getEmail()) != null) {
//	            try {
//	                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//	                printWriter.println("Email exists");
//	                printWriter.flush();
//	            } catch (IOException e) {
//	                throw new RuntimeException(e);
//	            }
//	            return;
//	        }
//	        // check phone exists
//	        if (nhanVienDAO.getNhanVienBySdt(nhanVien.getSoDienThoai()) != null) {
//	            try {
//	                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//	                printWriter.println("Phone exists");
//	                printWriter.flush();
//	            } catch (IOException e) {
//	                throw new RuntimeException(e);
//	            }
//	            return;
//	        }
//	        nhanVienDAO.addNhanVien(nhanVien);
//	        try {
//	            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//	            printWriter.println("Add success");
//	            printWriter.flush();
//	        } catch (IOException e) {
//	            throw new RuntimeException(e);
//	        }
//	    }
//	    public void update(Scanner scanner) {
//	        String json = scanner.nextLine();
//	        NhanVien nhanVien = new Gson().fromJson(json, NhanVien.class);
//	        nhanVienDAO.updateNhanVien(nhanVien);
//	        try {
//	            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//	            printWriter.println("Update success");
//	            printWriter.flush();
//	        } catch (IOException e) {
//	            throw new RuntimeException(e);
//	        }
//	    }
//	    public void generateId() {
//	        try {
//	            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//	            printWriter.println(nhanVienDAO.generateID());
//	            printWriter.flush();
//	        } catch (IOException e) {
//	            throw new RuntimeException(e);
//	        }
//	    }
}
