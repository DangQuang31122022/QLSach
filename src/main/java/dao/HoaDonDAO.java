package dao;

import entity.HoaDon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.sql.Date;
import java.util.ArrayList;

public class HoaDonDAO {
    private EntityManager em;
    private EntityTransaction et;
    private NhanVienDAO nhanVien_DAO = new NhanVienDAO(em);
    private KhachHangDAO khachHang_DAO = new KhachHangDAO(em);

    public HoaDonDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }

    public ArrayList<HoaDon> getAllHoaDon() {
        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd", HoaDon.class).getResultList();
    }

    public ArrayList<HoaDon> getAllHoaDon(String maId) {
        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.maHD like :maId", HoaDon.class)
                .setParameter("maId", "%" + maId + "%")
                .getResultList();
    }
    
    public ArrayList<HoaDon> getAllHoaDon(String tenKhachHang, String tenNhanVien) {
        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.khachHang.tenKhachHang like :tenKhachHang and hd.nhanVien.tenNhanVien like :tenNhanVien", HoaDon.class)
                .setParameter("tenKhachHang", "%" + tenKhachHang + "%")
                .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
                .getResultList();
    }
    
    public ArrayList<HoaDon> getAllHoaDon(String tenKhachHang, String tenNhanVien, Date tuNgay, Date denNgay) {
        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.khachHang.tenKhachHang like :tenKhachHang and hd.nhanVien.tenNhanVien like :tenNhanVien and hd.ngayLapHD between :tuNgay and :denNgay", HoaDon.class)
                .setParameter("tenKhachHang", "%" + tenKhachHang + "%")
                .setParameter("tenNhanVien", "%" + tenNhanVien + "%")
                .setParameter("tuNgay", tuNgay)
                .setParameter("denNgay", denNgay)
                .getResultList();
    }

    public ArrayList<HoaDon> getHoaDonByDate(Date ngayBatDau, Date ngayKetThuc) {
        return (ArrayList<HoaDon>) em.createQuery("SELECT hd FROM HoaDon hd WHERE hd.ngayLapHD between :ngayBatDau and :ngayKetThuc", HoaDon.class)
                .setParameter("ngayBatDau", ngayBatDau)
                .setParameter("ngayKetThuc", ngayKetThuc)
                .getResultList();
    }

    public HoaDon getHoaDonById(String id) {
        return em.find(HoaDon.class, id);
    }
    
    public int addHoaDon(HoaDon hoaDon) {
        try {
            et.begin();
            em.persist(hoaDon);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return 0;
    }
}