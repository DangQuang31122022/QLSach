/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

//import dao.ChiTietPhieuNhapDAO;
//import dao.PhieuNhapDAO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

//    private String auto_IDPhieuNhap(){
//        //auto gen id hóa đơn dạng HDXXXXXX
//        PhieuNhapDAO phieuNhap_DAO = new PhieuNhapDAO();
//        String idPrefix = "PN";
//        int length = phieuNhap_DAO.getAllPhieuNhap().size();
//        String finalId = idPrefix + String.format("%03d", length + 1);
//        return finalId;
//
//    }
    
//    public PhieuNhap() {
//        this.maPhieuNhap = auto_IDPhieuNhap();
//    }

    public PhieuNhap() {
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
//        ChiTietPhieuNhapDAO ctpn_DAO = new ChiTietPhieuNhapDAO();
//        ArrayList<ChiTietPhieuNhap> listChiTietPhieuNhap = ctpn_DAO.getAllCTHDByHoaDon(this);
//
//        for (ChiTietPhieuNhap ctpn : listChiTietPhieuNhap) {
//            tongTien += ctpn.thanhTien();
//        }

        return tongTien;
    }
}
