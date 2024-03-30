package entity;

//import dao.NhanVienDAO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "nhanvien")
public class NhanVien {
	@Id
	private String maNhanVien;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenNhanVien;
	private boolean gioiTinh;
	@Column(columnDefinition = "nvarchar(255)")
	private String chucVu;
	private String soDienThoai;
	private String email;
	
	public NhanVien() {
		super();
	}
//	public String auto_ID(){
//	       NhanVienDAO NV_DAO = new NhanVienDAO();
//	        String idPrefix = "NV";
//	        int length = NV_DAO.getAllNhanVien().size();
//	        String finalId = idPrefix + String.format("%03d", length + 1);
//	        return finalId;
//	    }
	public NhanVien(String tenNhanVien, boolean gioiTinh, String chucVu, String soDienThoai, String email) {
		super();
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.soDienThoai = soDienThoai;
		this.email = email;
	}

    public NhanVien(String email) {
        this.email = email;
    }
	
	
	public NhanVien(String maNhanVien, String tenNhanVien, boolean gioiTinh, String chucVu, String soDienThoai,
			String email) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.soDienThoai = soDienThoai;
		this.email = email;
	}
	public NhanVien(String maNhanVien, String tenNhanVien,String soDienThoai, boolean gioiTinh, String chucVu,
			String email) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
		this.soDienThoai = soDienThoai;
		this.email = email;
	}
	
	
	public String getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}

	public String getTenNhanVien() {
		return tenNhanVien;
	}

	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}