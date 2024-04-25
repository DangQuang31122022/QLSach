/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

//import dao.ChiTietPhieuNhapDAO;
//import dao.PhieuNhapDAO;
import constant.Constants;
import dao.ChiTietPhieuNhapDAO;
import dao.PhieuNhapDAO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author trana
 */
@Entity @Table(name = "phieunhap")
public class PhieuNhap {
    @Id
    private String maPhieuNhap;
    private Date ngayNhap;

    private String auto_IDPhieuNhap(){
        EntityManager em = Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager();
        //auto gen id hóa đơn dạng HDXXXXXX
        PhieuNhapDAO phieuNhap_DAO = new PhieuNhapDAO(em);
        String idPrefix = "PN";
        int length = phieuNhap_DAO.getAllPhieuNhap().size();
        String finalId = idPrefix + String.format("%03d", length + 1);
        return finalId;

    }
    
    public PhieuNhap() {
        this.maPhieuNhap = auto_IDPhieuNhap();
    }


    public PhieuNhap(String maPhieuNhap, Date ngayNhap) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public double tongTien() {
        double tongTien = 0;
        EntityManager em = Persistence.createEntityManagerFactory(Constants.DatabaseType).createEntityManager();
        ChiTietPhieuNhapDAO ctpn_DAO = new ChiTietPhieuNhapDAO(em);
        ArrayList<ChiTietPhieuNhap> listChiTietPhieuNhap = ctpn_DAO.getAllCTHDByHoaDon(this);

        for (ChiTietPhieuNhap ctpn : listChiTietPhieuNhap) {
            tongTien += ctpn.thanhTien();
        }

        return tongTien;
    }
}
