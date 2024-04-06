package buisinesLogic;

import dao.*;
import jakarta.persistence.EntityManager;

import java.net.Socket;

public class QuanLyBanHangLogic {
    private Socket socket;
    private EntityManager em;
    private NhanVienDAO nhanVienDAO;
    private KhachHangDAO khachHangDAO;
    private SanPhamDAO sanPhamDAO;
    private HoaDonDAO hoaDonDAO;
    private ChiTietHoaDonDAO chiTietHoaDonDAO;
}
