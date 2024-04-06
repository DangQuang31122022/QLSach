package buisinesLogic;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import dao.HoaDonDAO;
import entity.HoaDon;
import jakarta.persistence.EntityManager;

public class QuanLyHoaDonLogic {
	private Socket client;
    private EntityManager em;
    private HoaDonDAO hoaDonDAO;
    
    public QuanLyHoaDonLogic(EntityManager em, Socket client) {
    	this.em = em;
        this.client = client;
        hoaDonDAO = new HoaDonDAO(em);
    }
    
    public List<HoaDon> loadAllDataHD() {
    	return hoaDonDAO.getAllHoaDon();
    }
}
