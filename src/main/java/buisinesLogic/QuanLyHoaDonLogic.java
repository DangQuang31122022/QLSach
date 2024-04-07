package buisinesLogic;

import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import constant.Constants;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.SanPhamDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class QuanLyHoaDonLogic {
	private Socket client;
	private EntityManager em;
	private HoaDonDAO hoaDonDAO;
//    private SanPhamDAO sanPhamDAO;
	private ChiTietHoaDonDAO cthd_DAO;

	public QuanLyHoaDonLogic(EntityManager em, Socket client) {
		this.em = em;
		this.client = client;
	}

	public List<HoaDon> loadAllDataHD() {
		hoaDonDAO = new HoaDonDAO(em);
		return hoaDonDAO.getAllHoaDon();
	}

	public double[] tinhTongTien(List<HoaDon> dshd) {
		cthd_DAO = new ChiTietHoaDonDAO(em);
		double[] listTongTien = new double[dshd.size()]; // Khởi tạo mảng listTongTien với kích thước là số lượng hóa
															// đơn
		int index = 0; // Biến index để lưu vị trí cần gán tổng tiền vào mảng listTongTien

		for (HoaDon hd : dshd) {
			double tongTien = 0; // Khởi tạo biến tongTien để tính tổng tiền của mỗi hóa đơn

			List<ChiTietHoaDon> listCTHDByID = cthd_DAO.getCTHDById(hd.getMaHD());

			for (ChiTietHoaDon cthd : listCTHDByID) {
				tongTien += cthd.thanhTien();
			}

			listTongTien[index] = tongTien; // Gán tổng tiền của hóa đơn vào mảng listTongTien tại vị trí index
			index++; // Tăng index để di chuyển đến vị trí tiếp theo của mảng listTongTien
		}
		return listTongTien;
	}

	public List<ChiTietHoaDon> getAllCTHDByID(String id) {
		cthd_DAO = new ChiTietHoaDonDAO(em);
		return cthd_DAO.getCTHDById(id);
	}

	public List<HoaDon> getDsHDByDate(Date from, Date to) {
		hoaDonDAO = new HoaDonDAO(em);
		return hoaDonDAO.getHoaDonByDate(from, to);
	}

	public HoaDon getHDByID(String id) {
		hoaDonDAO = new HoaDonDAO(em);
		return hoaDonDAO.getHoaDonByID(id);
	}

	public double tinhTongTien1HD(HoaDon hd) {
		cthd_DAO = new ChiTietHoaDonDAO(em);

		double tongTien = 0; // Khởi tạo biến tongTien để tính tổng tiền của mỗi hóa đơn

		List<ChiTietHoaDon> listCTHDByID = cthd_DAO.getCTHDById(hd.getMaHD());

		for (ChiTietHoaDon cthd : listCTHDByID) {
			tongTien += cthd.thanhTien();
		}

		return tongTien;
	}

}
