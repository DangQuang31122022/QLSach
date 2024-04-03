package buisinesLogic;

import dao.LoginDao;
import dao.NhanVienDAO;
import dao.TaiKhoanDAO;
import jakarta.persistence.EntityManager;

import java.net.Socket;

public class ForgotPasswordLogic {
    private Socket client;
    private EntityManager em;
    private TaiKhoanDAO taiKhoanDAO;
    private NhanVienDAO nhanVienDAO;
    private LoginDao loginDao = new LoginDao();

    public ForgotPasswordLogic(Socket client, EntityManager em) {
        this.client = client;
        this.em = em;
    }

}
