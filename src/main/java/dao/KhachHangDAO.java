package dao;

import entity.KhachHang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;

public class KhachHangDAO {
    private EntityManager em;
    private EntityTransaction et;

    public KhachHangDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }

    public ArrayList<KhachHang> getAllKhachHang() {
        return (ArrayList<KhachHang>) em.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class).getResultList();
    }

    public ArrayList<KhachHang> getKhachHangByName(String tenKhachHang){
        return (ArrayList<KhachHang>) em.createQuery("SELECT kh FROM KhachHang kh WHERE kh.tenKhachHang LIKE :tenKhachHang", KhachHang.class)
                .setParameter("tenKhachHang", "%" + tenKhachHang + "%")
                .getResultList();
    }
    public KhachHang getKhachHangById(String id){
        return em.find(KhachHang.class, id);
    }
    
    public KhachHang getKhachHangBySdt(String sdt){
        return em.createQuery("SELECT kh FROM KhachHang kh WHERE kh.soDienThoai = :sdt", KhachHang.class)
                .setParameter("sdt", sdt)
                .getSingleResult();
    }
    
    public int updateKhachHang(KhachHang khachHang){
        try {
            et.begin();
            em.merge(khachHang);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return -1;
    }
    
    public int addKhachHang(KhachHang khachHang){
        try {
            et.begin();
            em.persist(khachHang);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return -1;
    } 
}


