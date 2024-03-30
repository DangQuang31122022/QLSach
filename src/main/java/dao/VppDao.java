package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connectDB.ConnectDB;
import entity.NhaCungCap;
import entity.VPP;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class VppDao {
	private EntityManager em;
	private EntityTransaction et;
	public VppDao(EntityManager em) {
		this.em = em;
		this.et = em.getTransaction();
		
	}
	public ArrayList<VPP> getAllVPP(){
//		ArrayList<VPP> listVPP = new ArrayList<VPP>();
//		NhaCungCapDAO nhaCungCapDao = new NhaCungCapDAO();
//		ConnectDB.getInstance();
//		Connection con =ConnectDB.getConnection();
//		try {
//			String sql ="select * from SanPham where maSP LIKE 'H%'";
//			Statement sta =con.createStatement();
//			ResultSet rs =sta.executeQuery(sql);
//			while(rs.next()) {
//				 String maSP = rs.getString(1);
//	              String tenSP = rs.getString(2);
//	              String maNCC = rs.getString(3);
//	              NhaCungCap nhaCungCap = nhaCungCapDao.getNhaCungCap(maNCC);
//	              int soLuong = rs.getInt(4);
//	              String hinhAnh =rs.getString(15);
//	              double donGiaBan = rs.getDouble(6);
//	              String xuatXu=rs.getString(7);
//	              String mauSac=rs.getString(8);
//	              String chatLieu=rs.getString(9);
//	              VPP vpp = new VPP(maSP, tenSP, nhaCungCap, soLuong, donGiaBan, hinhAnh, xuatXu, mauSac, chatLieu);
//	              listVPP.add(vpp);
//			}
//		}catch (SQLException ex) {
//            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listVPP;
		return (ArrayList<VPP>) em.createQuery("SELECT v FROM VPP v", VPP.class).getResultList();
	}
	public VPP timVPPTheoTen(String ten) {
		return em.createQuery("SELECT v FROM VPP v WHERE v.tenSP = :ten", VPP.class)
				.setParameter("ten", ten)
				.getSingleResult();
	}
	public VPP getVPPByID(String id) {
		return em.find(VPP.class, id);
	}
	public int addVPP(VPP vpp) {
		try {
			et.begin();
			em.persist(vpp);
			et.commit();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}
	public int updateVPP(VPP vpp) {
		try {
			et.begin();
			em.merge(vpp);
			et.commit();
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}
		return -1;
	}
}

