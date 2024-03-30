package dao;

import entity.NhaCungCap;
import entity.SanPham;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SanPhamDAO {
    private EntityManager em;
    private EntityTransaction et;

    public SanPhamDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }

    public ArrayList<SanPham> getAllSanPham() {
        NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO(em);

//        ArrayList<SanPham> listSanPham = new ArrayList<>();
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//        try {
//            String sql = "Select * from SanPham";
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                String maSP = rs.getString(1);
//                String tenSP = rs.getString(2);
//                String maNCC = rs.getString(3);
//                NhaCungCap nhaCungCap = nhaCungCapDao.getNhaCungCap(maNCC);
//                int soLuong = rs.getInt(4);
//                String loaiSP = rs.getString(5);
//                double donGiaBan = rs.getDouble(6);
//
//                SanPham sanPham = new SanPham(maSP, tenSP, nhaCungCap, soLuong, loaiSP, donGiaBan);
//                listSanPham.add(sanPham);
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listSanPham;
        // dont get all field just SanPham(maSP, tenSP, nhaCungCap, soLuong, loaiSP, donGiaBan);
        return (ArrayList<SanPham>) em.createQuery("SELECT sp FROM SanPham sp", SanPham.class).getResultList();
    }
    
    public int capNhatSoLuong(SanPham sanPham){
        try {
            et.begin();
            em.merge(sanPham);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return -1;
    }
    
    public int capNhatGiaBan(SanPham sanPham){
        try {
            et.begin();
            em.merge(sanPham);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return -1;
    }
    
    public SanPham getSanPhamById(String id) {
//        NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
//
//        ConnectDB.getInstance();
//        Connection con = ConnectDB.getConnection();
//
//        try {
//            String sql = "select * from SanPham where maSP = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, id);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String maSP = rs.getString(1);
//                String tenSP = rs.getString(2);
//                String maNCC = rs.getString(3);
//                NhaCungCap nhaCungCap = nhaCungCapDao.getNhaCungCap(maNCC);
//                int soLuong = rs.getInt(4);
//                String loaiSP = rs.getString(5);
//                double donGiaBan = rs.getDouble(6);
//
//                SanPham sanPham = new SanPham(maSP, tenSP, nhaCungCap, soLuong, loaiSP, donGiaBan);
//                return sanPham;
//            }
//
//        } catch (SQLException e) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return null;
        return em.find(SanPham.class, id);
    }


    
    public ArrayList<SanPham>topNSanPhamBanChay(){
//        ArrayList<SanPham>listSanPham = new ArrayList<>();
//        ConnectDB.getInstance();
//        Connection conn = ConnectDB.getConnection();
//        SanPhamDAO sp_DAO = new SanPhamDAO();
//        String sql = "SELECT TOP 10     SanPham.maSP, sum(ChiTietHoaDon.soLuong) as tongSoLuong \n" +
//                        "FROM        ChiTietHoaDon INNER JOIN\n" +
//                        "                  HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon INNER JOIN\n" +
//                        "                  sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n" +
//                        "\n" +
//                        "group by sanPham.maSP, sanPham.tenSP\n" +
//                        "order by tongSoLuong desc";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                SanPham sp = sp_DAO.getSanPhamById(rs.getString(1));
//                sp.setSoLuongTK(rs.getInt(2));
//                listSanPham.add(sp);
//
//            }
//            return listSanPham;
//        } catch (SQLException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;

//        String sql = "SELECT TOP 10     SanPham.maSP, sum(ChiTietHoaDon.soLuong) as tongSoLuong \n" +
//                        "FROM        ChiTietHoaDon INNER JOIN\n" +
//                        "                  HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon INNER JOIN\n" +
//                        "                  sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n" +
//                        "\n" +
//                        "group by sanPham.maSP, sanPham.tenSP\n" +
//                        "order by tongSoLuong desc";
        String jpql = "SELECT sp, SUM(cthd.soLuong) as tongSoLuong " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "JOIN cthd.sanPham sp " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY tongSoLuong DESC";
        Query query = em.createQuery(jpql);
        query.setMaxResults(10);
        return (ArrayList<SanPham>) query.getResultList();
    }
    
    public ArrayList<SanPham>topNSanPhamBanCham(){
//        ArrayList<SanPham>listSanPham = new ArrayList<>();
//        ConnectDB.getInstance();
//        Connection conn = ConnectDB.getConnection();
//        SanPhamDAO sp_DAO = new SanPhamDAO();
//        String sql = "SELECT TOP 10     SanPham.maSP, sum(ChiTietHoaDon.soLuong) as tongSoLuong \n" +
//                        "FROM        ChiTietHoaDon INNER JOIN\n" +
//                        "                  HoaDon ON ChiTietHoaDon.maHoaDon = HoaDon.maHoaDon INNER JOIN\n" +
//                        "                  sanPham ON ChiTietHoaDon.maSP = sanPham.maSP\n" +
//                        "\n" +
//                        "group by sanPham.maSP, sanPham.tenSP\n" +
//                        "order by tongSoLuong asc";
//        try {
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while(rs.next()){
//                SanPham sp = sp_DAO.getSanPhamById(rs.getString(1));
//                sp.setSoLuongTK(rs.getInt(2));
//                listSanPham.add(sp);
//
//            }
//            return listSanPham;
//        } catch (SQLException ex) {
//            Logger.getLogger(SanPhamDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
        String jpql = "SELECT sp, SUM(cthd.soLuong) as tongSoLuong " +
                "FROM ChiTietHoaDon cthd " +
                "JOIN cthd.hoaDon hd " +
                "JOIN cthd.sanPham sp " +
                "GROUP BY sp.maSP, sp.tenSP " +
                "ORDER BY tongSoLuong ASC";
        Query query = em.createQuery(jpql);
        query.setMaxResults(10);
        return (ArrayList<SanPham>) query.getResultList();
    }
}
