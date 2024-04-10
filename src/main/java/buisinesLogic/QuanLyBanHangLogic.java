package buisinesLogic;

import dao.*;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import jakarta.persistence.EntityManager;

import java.net.Socket;
import java.util.List;

public class QuanLyBanHangLogic {
    private Socket socket;
    private EntityManager em;
    private NhanVienDAO nhanVienDAO;
    private KhachHangDAO khachHangDAO;
    private SanPhamDAO sanPhamDAO;
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
    
    public QuanLyBanHangLogic(EntityManager em, Socket socket) {
		this.em = em;
		this.socket = socket;
	}
    
    public NhanVien getNVByID(String id) {
    	nhanVienDAO = new NhanVienDAO(em);
    	return nhanVienDAO.getNhanVienByID(id);
    }
    
    public String getIDHDAutoGen() {
    	hoaDonDAO = new HoaDonDAO(em);
    	return hoaDonDAO.auto_IDPHoaDon();
    }
    
    public List<SanPham> getAllSanPham(){
    	sanPhamDAO = new SanPhamDAO(em);
    	return sanPhamDAO.getAllListSanPham();
    }
    
    public SanPham getSPByID(String id) {
    	sanPhamDAO = new SanPhamDAO(em);
    	return sanPhamDAO.getSanPhamById(id);
    }
    
    public KhachHang getKHBySDT(String sdt) {
    	khachHangDAO = new KhachHangDAO(em);
    	return khachHangDAO.getKhachHangBySdt(sdt);
    }
    
    public int addHoaDon(HoaDon hd) {
    	hoaDonDAO = new HoaDonDAO(em);
    	return hoaDonDAO.addHoaDon(hd);
    }
    
    public int addCTHD(ChiTietHoaDon cthd) {
    	chiTietHoaDonDAO = new ChiTietHoaDonDAO(em);
    	return chiTietHoaDonDAO.addChiTietHoaDon(cthd);
    }
    
    public int updateSP(SanPham sp) {
    	sanPhamDAO = new SanPhamDAO(em);
    	return sanPhamDAO.capNhatSoLuong(sp);
    }
    
    public List<ChiTietHoaDon> getAllCTHDByIDhd(String idHD){
    	chiTietHoaDonDAO = new ChiTietHoaDonDAO(em);
    	return chiTietHoaDonDAO.getCTHDById(idHD);
    }
}
