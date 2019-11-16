package com.huacheng.huiservers.ui.shop.bean;

import java.util.List;

public class ConfirmBean {

	private String merchant_id;
	private String merchant_name;
	private String merchant_telphone;
	private String merchant_address;
	private String half_amount;
	private String number;
	private List<BannerBean> img;
	/**
	 * half_amount : 1
	 * number : 1
	 * weight : 0
	 * delivers : [{"name":"到店自提","sign":"ddzt","is_default":0,"dis_fee":0},{"name":"快递物流","sign":"kdwl","is_default":1,"dis_fee":"1.00"}]
	 */
	private String weight;
	private List<DeliversBean> delivers;

	private DeliversBean deliversBean_selected; //选中的bean  这个需要自己拼接


	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getMerchant_telphone() {
		return merchant_telphone;
	}
	public void setMerchant_telphone(String merchant_telphone) {
		this.merchant_telphone = merchant_telphone;
	}
	public String getMerchant_address() {
		return merchant_address;
	}
	public void setMerchant_address(String merchant_address) {
		this.merchant_address = merchant_address;
	}
	public String getHalf_amount() {
		return half_amount;
	}
	public void setHalf_amount(String half_amount) {
		this.half_amount = half_amount;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public List<BannerBean> getImg() {
		return img;
	}
	public void setImg(List<BannerBean> img) {
		this.img = img;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public List<DeliversBean> getDelivers() {
		return delivers;
	}

	public void setDelivers(List<DeliversBean> delivers) {
		this.delivers = delivers;
	}


	public static class DeliversBean {
		/**
		 * name : 到店自提
		 * sign : ddzt
		 * is_default : 0
		 * dis_fee : 0
		 */

		private String name;
		private String sign;
		private int is_default;
		private String dis_fee;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public int getIs_default() {
			return is_default;
		}

		public void setIs_default(int is_default) {
			this.is_default = is_default;
		}

		public String getDis_fee() {
			return dis_fee;
		}

		public void setDis_fee(String dis_fee) {
			this.dis_fee = dis_fee;
		}
	}

	public DeliversBean getDeliversBean_selected() {
		return deliversBean_selected;
	}

	public void setDeliversBean_selected(DeliversBean deliversBean_selected) {
		this.deliversBean_selected = deliversBean_selected;
	}

}
