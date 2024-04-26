package buisinesLogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.s;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.NhanVienDAO;
import dao.SachDao;
import dao.SanPhamDAO;
import dao.VppDao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhanVien;
import entity.Sach;
import entity.SanPham;
import entity.VPP;
import jakarta.persistence.EntityManager;

public class QuanLySanPham {
	private Socket socket;
	private EntityManager em;
	private SachDao sachDao;
	private VppDao vppDao;
	private SanPhamDAO sanPhamDAO;
	private HoaDonDAO hoaDonDAO;
	private ChiTietHoaDonDAO chiTietHoaDonDAO;
	private Sach sach;
	public QuanLySanPham(Socket socket, EntityManager em) {
		super();
		this.socket = socket;
		this.em = em;
		sachDao = new SachDao(em);
		vppDao = new VppDao(em);
		
	}
	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "getSachID":
			getSachID(scanner);
			break;
		case "getVppID":
			getVppID(scanner);
			break;
		case "addSach":
			addSach(scanner);
			break;
		case "addVpp":
		addVpp(scanner);
			break;
		case "updateVpp":
			updateVpp(scanner);
			break;
		case "updateSach":
			updateSach(scanner);
			break;
		case "autoIDSach":
			generateIDsach();
		break;
		case "autoIDvpp":
		generateIDvpp();
		break;
		default:
			System.out.println("Unknown action: " + action);
		}
		}
	public void getSachID(Scanner sc) {
		sachDao = new SachDao(em);
		String id = sc.nextLine();
		Sach s = sachDao.getSachByID(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void getVppID(Scanner sc) {
		vppDao = new VppDao(em);
		String id = sc.nextLine();
		VPP vpp = vppDao.getVPPByID(id);
		String response = new Gson().toJson(vpp);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void addSach(Scanner scanner) {
        String json = scanner.nextLine();
       Sach s = new Gson().fromJson(json, Sach.class);
        // check email exists
        if (sachDao.getSachByID(s.getMaSP()) != null) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("Exist");
                printWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        // check phone exists
        if (sachDao.timSachTheoTenSach(s.getTenSP()) != null) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("Ten sach da ton tai");
                printWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
       sachDao.themSach(s);
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Add success");
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public void addVpp(Scanner scanner) {
        String json = scanner.nextLine();
       VPP vpp = new Gson().fromJson(json, VPP.class);
        // check email exists
        if (vppDao.getVPPByID(vpp.getMaSP()) != null) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("Exist");
                printWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        // check phone exists
        if (vppDao.timVPPTheoTen(vpp.getTenSP()) != null) {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("San pham da ton tai");
                printWriter.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        vppDao.addVPP(vpp);
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Add success");
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public void updateVpp(Scanner scanner) {
        String json = scanner.nextLine();
        VPP vpp = new Gson().fromJson(json, VPP.class);
        vppDao.updateVPP(vpp);
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Update success vpp");
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public void updateSach(Scanner scanner) {
        String json = scanner.nextLine();
       Sach s= new Gson().fromJson(json, Sach.class);
        sachDao.capNhat(s);
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("Update success sach");
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generateIDsach() {
  
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(sachDao.generateId());
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void generateIDvpp() {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(vppDao.generateId());
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void getSachTheoTen(Scanner sc) {
		sachDao = new SachDao(em);
		String id = sc.nextLine();
		Sach s = sachDao.timSachTheoTenSach(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
    public void getVppTheoTen(Scanner sc) {
		vppDao = new VppDao(em);
		String id = sc.nextLine();
		VPP vpp = vppDao.timVPPTheoTen(id);
		String response = new Gson().toJson(vpp);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
   
    
}
