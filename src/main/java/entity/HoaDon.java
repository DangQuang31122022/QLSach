package entity;

import java.sql.Date;
import java.util.ArrayList;

//import dao.ChiTietHoaDonDAO;
//import dao.HoaDonDAO;
import jakarta.persistence.*;

@Entity
@Table(name = "hoadon")
public class HoaDon {
    @Id
    private String maHD;
    private Date ngayLapHD;
    @ManyToOne
    @JoinColumn(name = "maNV")
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "maKH")
    private KhachHang khachHang;

//    private String auto_IDPHoaDon() {
//        //auto gen id hóa đơn dạng HDXXXXXX
//        HoaDonDAO phieuNhap_DAO = new HoaDonDAO();
//        String idPrefix = "HD";
//        int length = phieuNhap_DAO.getAllHoaDon().size();
//        String finalId = idPrefix + String.format("%05d", length + 1);
//        return finalId;
//
//    }

//    public HoaDon() {
//        this.maHD = auto_IDPHoaDon();
//    }

    public HoaDon(HoaDon hd) {
        this.ngayLapHD = hd.ngayLapHD;
        this.nhanVien = hd.nhanVien;
        this.khachHang = hd.khachHang;
    }

    public HoaDon(Date ngayLapHD, NhanVien nhanVien, KhachHang khachHang) {
        super();
        this.ngayLapHD = ngayLapHD;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    public HoaDon(String maHD, Date ngayLapHD, NhanVien nhanVien, KhachHang khachHang) {
        super();
        this.maHD = maHD;
        this.ngayLapHD = ngayLapHD;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public Date getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(Date ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

//    public double tongTien() {
//        double tongTien = 0;
//        ChiTietHoaDonDAO cthd_DAO = new ChiTietHoaDonDAO();
//        ArrayList<ChiTietHoaDon> listChiTietHoaDon = cthd_DAO.getAllCTHDByHoaDon(this);
//
//        for (ChiTietHoaDon cthd : listChiTietHoaDon) {
//            tongTien += cthd.thanhTien();
//        }
//
//        return tongTien;
//    }
}
