package com.citics.cagent.data.model.response.tham_dinh

import android.os.Parcelable
import com.citics.cagent.data.model.response.CongTrinh
import com.citics.cagent.data.model.response.GroupPhotos
import com.citics.cagent.data.model.response.HuongTiepGiapDTO
import com.citics.cagent.data.model.response.KeyTitleDTO
import com.citics.cagent.data.model.response.LandDTO
import com.citics.cagent.data.model.response.PhapLyDTO
import com.citics.cagent.data.model.response.SliderItemResponse
import kotlinx.parcelize.Parcelize

/**
 * Created by ChinhQT on 29/12/2022.
 */
@Parcelize
open class Properties(
    var id: String? = "",
    var loai_tai_san: String? = "",
    var kinh_do: Double? = null,
    var vi_do: Double? = null,
    var gia_rao_ban: Long? = 0,
    var gia_tri_sau_noi_that: Long? = 0,
    var thoi_diem_mo_ban: Long? = null,
    var danh_sach_hinh_anh: List<SliderItemResponse>? = null,
    var ten_du_an: String? = "",
    var toa_do: String? = "",
    var ma_du_an: String? = "",
    var cap_nhat_cach_day: String? = null,
    var ngay_cap_nhat_gia: Long? = null,
    var ngay_cap_nhat_gia_thuong_luong: Long? = null,
    var huong: String? = "",
    var phap_ly: String? = "",
    var tinh_trang: String? = null,
    var chu_dau_tu: String? = "",
    var gia_mo_ban_thap_nhat: Long? = null,
    var gia_mo_ban_cao_nhat: Long? = null,
    var mo_ta: String? = "",
    var dien_tich_khuon_vien: Float? = null,
    var mat_do_xay_dung: String? = "",
    var tien_nghi: List<String>? = null,
    var nam_khoi_cong: Int? = null,
    var so_block: String? = null,
    var so_tang: String? = null,
    var don_gia_trung_binh: Long? = null,
    var ngay_cap_nhat_don_gia_trung_binh: Long? = null,
    var dien_tich_nho_nhat: Float? = null,
    var dien_tich_lon_nhat: Float? = null,
    var ghi_chu_phap_ly: String? = null,
    var vi_tri_theo_ubnd: String? = null,
    var link: String? = null,
    var ten_nguoi_lien_he: String? = null,
    var sdt_lien_he: String? = null,
    var nhom_hinh_phap_ly: List<PhapLyDTO>? = null,
    var tinh_trang_giao_dich: String? = null,
    var tinh_trang_phap_ly: String? = null,
    var gia_thuong_luong: Long? = null,
    var thoi_diem_giao_dich: Long? = null,
    var huong_tiep_giap: HuongTiepGiapDTO? = HuongTiepGiapDTO(),
    var nhom_hinh_anh: List<GroupPhotos>? = null,
    var so_nha: String? = null,
    var vi_tri_duong: String? = null,
    var chu_so_huu: String? = null,
    var phuong: String? = null,
    var ma_phuong: String? = null,
    var quan: String? = null,
    var ma_quan: String? = null,
    var thanh_pho: String? = null,
    var ma_thanh_pho: String? = null,
    var dia_chi: String? = null,
    var dia_chi1: String? = null,
    var dia_chi2: String? = null,
    var dien_tich: String? = null,
    var gia_mo_ban_text: String? = null,
    var ignore_landing_value: Boolean? = null,
    var k_factor_upper_limit: String? = null,
    var qhsdd: QhsddDTO? = null,
    var qhxd: QhxdDTO? = null,
    //region Only with NhaDat
    var so_to: String? = "",
    var so_thua: String? = "",
    var vi_tri_can_goc: String? = "",
    var hinh_thuc_su_dung: String? = "",
    var mat_tien_tiep_giap: String? = "",
    var ghi_chu_khac_ve_thua_dat: String? = "",
    var khoang_cach_ra_duong_chinh: Float? = null,
    var chieu_dai: Float? = null,
    var chieu_rong: Float? = null,
    var so_sanh_thuc_te_yeu_to_khac: String? = "",
    var yeu_to_khac: MutableList<String>? = null,
    var yeu_to_khac_khac: String? = null,
    var hien_trang_su_dung: List<String>? = null,
    var hien_trang_su_dung_khac: String? = null,
    var ranh_gioi: List<List<Double>>? = null,
    var do_rong_hem_nho_nhat: Float? = null,
    var do_rong_hem_truoc_nha: Float? = null,
    var hinh_dang: String? = "",
    var dien_tich_dc_cong_nhan: Float? = null,
    var dien_tich_khong_dc_cong_nhan: Float? = null,
    var lands: MutableList<LandDTO>? = null,
    var cong_trinh: MutableList<CongTrinh>? = mutableListOf(),
    var ket_cau_duong: String? = null,
    var quy_hoach_du_kien: Double? = null,
    var ket_noi_giao_thong: String? = null,
    var quan_he_kh_chu_so_huu: String? = null,
    var trang_thai_thua_dat: KeyTitleDTO? = null,
    var nguon: String? = null,
    var dien_giai_do_tin_cay: String? = null,
    var do_tin_cay: String? = null,
    var khach_hang_la_chu_so_huu: Boolean? = null,
    //endregion

    //region Only with CanHo
    var nam_hoan_cong: Int? = null,
    var so_phong_ngu: Int? = null,
    var loai_can_ho: String? = null,
    var mo_ta_loai_can_ho: String? = null,
    var dien_tich_tim_tuong: Float? = null,
    var dien_tich_thong_thuy: Float? = null,
    var don_gia: Long? = null,
    var thap: String? = "",
    var ma_can: String? = "",
    var tang: String? = "",
    var link_du_an: String? = null,
    var canh_quan: String? = "",
    var vi_tri: String? = null,
    var vi_tri_goc: String? = null,
    var address: String? = null,
    var so_can: String? = null,
    var so_wc: Int? = null,
    var link_chu_dau_tu: String? = null,
    var ngan_hang_lien_ket: String? = null,
    var so_phong_khach: String? = null,
    var so_phong_bep: String? = null,
    var chat_luong_noi_that: String? = null,
    var gia_thuyet: String? = null,
    var ghi_chu_khac: String? = null,
    var ban_cong: String? = null,
    var lo_gia: String? = null,
    var noi_that: String? = null,
    var loai_du_an: String? = null,
    var thong_tin_du_an: ThongTinDuAn? = null,
    var gia_tri_noi_that: Long? = null,
    var thoi_han_su_dung_dat: String? = null
    //endregion
) : Parcelable {

    val listYeuToKhacTotal: MutableList<String?>
        get() {
            val lsYtk = mutableListOf(yeu_to_khac_khac)
            lsYtk.addAll(yeu_to_khac ?: mutableListOf())
            return lsYtk
        }

    @Parcelize
    data class ThongTinDuAn(
        var dien_tich_khuon_vien: Float? = null,
        var mat_do_xay_dung: String? = "",
        var so_block: String? = null,
        var so_can: String? = null,
        var nam_khoi_cong: Int? = null,
        var nam_hoan_cong: Int? = null,
        var so_tang: String? = null,
        var dien_tich_nho_nhat: Float? = null,
        var dien_tich_lon_nhat: Float? = null,
        var loai_can_ho: String? = null,
        var loai_du_an: String? = null,
        var thoi_diem_mo_ban: Long? = null,
        var phap_ly: String? = "",
        var tinh_trang: String? = "",
        var toa_do: String? = "",
        var chu_dau_tu: String? = "",
        var link_chu_dau_tu: String? = "",
        var dia_chi1: String? = "",
        var dia_chi2: String? = "",
        var tien_nghi: List<String>? = null,
        var gia_mo_ban_text: String? = "",
        var don_gia_trung_binh: Long? = null,
        var ngay_cap_nhat_don_gia_trung_binh: Long? = null,
        var nhom_hinh_anh: List<GroupPhotos>? = null,
        var kinh_do: Double = 0.0,
        var vi_do: Double = 0.0,
        var ten_du_an: String? = "",
        var id: String? = ""
    ) : Parcelable
}