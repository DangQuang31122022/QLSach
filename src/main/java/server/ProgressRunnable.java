package server;

import buisinesLogic.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.LoginDao;
import dao.TaiKhoanDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
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
//			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			while (true) {
				if (!scanner.hasNextLine())
					break;
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
				case "loadTblHoaDon":
					QuanLyHoaDonLogic loadTblHD = new QuanLyHoaDonLogic(em, client);
					List<HoaDon> dshd = loadTblHD.loadAllDataHD();
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
				case "QLBanHang":
					QuanLyBanHangLogic banHangLogic = new QuanLyBanHangLogic(em, client);
					String idHDAutoGen = banHangLogic.getIDHDAutoGen();
					out.writeObject(idHDAutoGen);
					String idNV = scanner.nextLine();
					NhanVien nv = banHangLogic.getNVByID(idNV);
					out.writeObject(nv);
					break;
				case "loadTblSachQLBH":
					QuanLyBanHangLogic loadTblSachQLBH = new QuanLyBanHangLogic(em, client);
					List<SanPham> listSachQLBH = loadTblSachQLBH.getAllSanPham();
					out.writeObject(listSachQLBH);
					break;
				case "loadTblVPPQLBH":
					QuanLyBanHangLogic loadTblVPPQLBH = new QuanLyBanHangLogic(em, client);
					List<SanPham> listVPPQLBH = loadTblVPPQLBH.getAllSanPham();
					out.writeObject(listVPPQLBH);
					break;
				case "btnThemQLBH":
					QuanLyBanHangLogic btnThemQLBH = new QuanLyBanHangLogic(em, client);
					String idSPbtnThemQLBH = scanner.nextLine();
					SanPham spThemQLBH = btnThemQLBH.getSPByID(idSPbtnThemQLBH);
					out.writeObject(spThemQLBH);
					break;
				case "loadImgQLBH":
					QuanLyBanHangLogic loadImgQLBH = new QuanLyBanHangLogic(em, client);
					String idSPloadImgQLBH = scanner.nextLine();
					SanPham sploadImgQLBH = loadImgQLBH.getSPByID(idSPloadImgQLBH);
					out.writeObject(sploadImgQLBH);
					break;
				case "btnTimBySDTQLBH":
					QuanLyBanHangLogic btnTimBySDTQLBH = new QuanLyBanHangLogic(em, client);
					String sdtBTNTimBySDTQLBH = scanner.nextLine();
					KhachHang khBTNTimBySDTQLBH = btnTimBySDTQLBH.getKHBySDT(sdtBTNTimBySDTQLBH);
					out.writeObject(khBTNTimBySDTQLBH);
					break;
				case "btnThanhToanQLBH":
					QuanLyBanHangLogic btnThanhToanQLBH = new QuanLyBanHangLogic(em, client);
					String idNVbtnThanhToanQLBH = scanner.nextLine();
					NhanVien nvBTNThanhToanQLBH = btnThanhToanQLBH.getNVByID(idNVbtnThanhToanQLBH);
					out.writeObject(nvBTNThanhToanQLBH);
					String sdtBTNThanhToanQLBH = scanner.nextLine();
					KhachHang khBTNThanhToanQLBH = btnThanhToanQLBH.getKHBySDT(sdtBTNThanhToanQLBH);
					out.writeObject(khBTNThanhToanQLBH);

					try {
						// this
						ObjectInputStream in = new ObjectInputStream(client.getInputStream());
						HoaDon hdBTNThanhToanQLBH = (HoaDon) in.readObject();
						System.out.println(hdBTNThanhToanQLBH);
						if (btnThanhToanQLBH.addHoaDon(hdBTNThanhToanQLBH) == 1) {
							out.writeObject("true");
						} else {
							out.writeObject("false");
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					try {
						// this
						ObjectInputStream in = new ObjectInputStream(client.getInputStream());
						int rowJTable_DSSPBan = (int) in.readObject();
						for (int i = 0; i < rowJTable_DSSPBan; i++) {
							String idSPbtnThanhToanQLBH = scanner.nextLine();
							SanPham spBTNThanhToanQLBH = btnThanhToanQLBH.getSPByID(idSPbtnThanhToanQLBH);
							out.writeObject(spBTNThanhToanQLBH);

							try {
								ChiTietHoaDon cthdBTNthanhToanQLBH = (ChiTietHoaDon) in.readObject();
								if (btnThanhToanQLBH.addCTHD(cthdBTNthanhToanQLBH) == 1) {
									out.writeObject("true");
								} else {
									out.writeObject("false");
								}
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}

							try {
								SanPham spUpdateSLTQLBH = (SanPham) in.readObject();
								if (btnThanhToanQLBH.updateSP(spUpdateSLTQLBH) == 1) {
									out.writeObject("true");
								} else {
									out.writeObject("false");
								}
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					String idHDbtnThanhToanQLBH = scanner.nextLine();
					List<ChiTietHoaDon> listCTHDQLBH = btnThanhToanQLBH.getAllCTHDByIDhd(idHDbtnThanhToanQLBH);
					out.writeObject(listCTHDQLBH);

					String idHDAutoGenQLBH = btnThanhToanQLBH.getIDHDAutoGen();
					out.writeObject(idHDAutoGenQLBH);
					break;
				case "btnTimKiemQLBH":
					QuanLyBanHangLogic btnTimKiemQLBH = new QuanLyBanHangLogic(em, client);
					List<SanPham> listSPbtnTimKiemQLBH = btnTimKiemQLBH.getAllSanPham();
					out.writeObject(listSPbtnTimKiemQLBH);
					break;
				case "btnXacNhanQLBH":
					QuanLyBanHangLogic btnXacNhanQLBH = new QuanLyBanHangLogic(em, client);
					List<SanPham> listSPXacNhanQLBH = btnXacNhanQLBH.getAllSanPham();
					out.writeObject(listSPXacNhanQLBH);
				default:
					System.out.println("Unknown request: " + type);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
    

   