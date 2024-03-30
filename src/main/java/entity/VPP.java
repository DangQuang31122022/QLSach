package entity;

//import org.apache.xmlbeans.impl.xb.xsdschema.Public;

//import dao.VppDao;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("VPP")
public class VPP extends SanPham {
	private String xuatXu;
	private String mauSac;
	private String chatLieu;
	
	public VPP() {
		super();	
	}

	/**
	 * @param maSP
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param donGiaBan
	 * @param xuatXu
	 * @param mauSac
	 * @param chatLieu
	 */
	

	/**
	 * @param maSP
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param loaiSP
	 * @param donGiaBan
	 * @param xuatXu
	 * @param mauSac
	 * @param chatLieu
	 */
	



	/**
	 * @param tenSP
	 * @param nhaCungCap
	 * @param soLuongTK
	 * @param loaiSP
	 * @param donGiaBan
	 * @param xuatXu
	 * @param mauSac
	 * @param chatLieu
	 */
	

	

	/**
//	 * @param maSP
//	 * @param tenSP
//	 * @param nhaCungCap
//	 * @param soLuongTK
//	 * @param donGiaBan
//	 * @param hinhAnh
//	 * @param xuatXu
//	 * @param mauSac
//	 * @param chatLieu
	 */
//	public String auto_ID() {
//		VppDao vpp_dao = new VppDao();
//		String idPrefix="H";
//		int length=vpp_dao.getAllVPP().size();
//		String finalId=idPrefix +String.format("%04d",length+1);
//		return finalId;
//	}
	public VPP(String maSP, String tenSP, NhaCungCap nhaCungCap, int soLuongTK, double donGiaBan, String hinhAnh,
			String xuatXu, String mauSac, String chatLieu) {
		super(maSP, tenSP, nhaCungCap, soLuongTK, donGiaBan, hinhAnh);
		this.xuatXu = xuatXu;
		this.mauSac = mauSac;
		this.chatLieu = chatLieu;
	}
	public String getMauSac() {
		return mauSac;
	}
	public void setMauSac(String mauSac) {
		this.mauSac = mauSac;
	}

	public String getChatLieu() {
		return chatLieu;
	}

	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}
	public String getXuatXu() {
		return xuatXu;
	}
	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}
}
