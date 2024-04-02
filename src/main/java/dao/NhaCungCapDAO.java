package dao;

import constant.Constants;
import entity.NhaCungCap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;

public class NhaCungCapDAO {
    private EntityManager em;
    private EntityTransaction et;

    public NhaCungCapDAO(EntityManager em) {
        this.em = em;
        this.et = em.getTransaction();
    }
    
    public ArrayList<NhaCungCap> getAllNhaCungCap(){
        return (ArrayList<NhaCungCap>) em.createQuery("SELECT n FROM NhaCungCap n").getResultList();
    }
    
    public NhaCungCap getNhaCungCap(String id){
        return em.find(NhaCungCap.class, id);
    }

    public ArrayList<NhaCungCap> getNhaCungCapByTen(String tenNhaCungCap){
        return (ArrayList<NhaCungCap>) em.createQuery("SELECT n FROM NhaCungCap n WHERE n.tenNCC LIKE :tenNCC")
                .setParameter("tenNCC", "%" + tenNhaCungCap + "%")
                .getResultList();
    }
    
    public int updateNhaCungCap(NhaCungCap nhaCungCap){
        try {
            et.begin();
            em.merge(nhaCungCap);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return 0;
    }
    
    public int addNhaCungCap(NhaCungCap nhaCungCap){
        try {
            et.begin();
            em.persist(nhaCungCap);
            et.commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            et.rollback();
        }
        return 0;
    } 
    public NhaCungCap getNhaCungCapByName(String name){
        return (NhaCungCap) em.createQuery("SELECT n FROM NhaCungCap n WHERE n.tenNCC = :tenNCC")
                .setParameter("tenNCC", name)
                .getSingleResult();
    }
    public static NhaCungCap getNhaCungCapByGmail(String gmail){
        return (NhaCungCap) Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager().createQuery("SELECT n FROM NhaCungCap n WHERE n.email = :email")
                .setParameter("email", gmail)
                .getSingleResult();
    }
    public static NhaCungCap getNhaCungCapBySdt(String sdt) {
        return (NhaCungCap) Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager().createQuery("SELECT n FROM NhaCungCap n WHERE n.SoDienThoai = :sdt")
                .setParameter("sdt", sdt)
                .getSingleResult();
    }
}
