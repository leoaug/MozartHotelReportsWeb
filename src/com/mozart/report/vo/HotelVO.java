package com.mozart.report.vo;

import java.io.Serializable;

public class HotelVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2396240847948249481L;
	
	
	private String idRedeHotel;
	private String nomeHotel;
	private String logoHotel;
	private String frontOffice;
	
	public HotelVO(){
		
	}
	
	public HotelVO(String idRedeHotel, String nomeHotel, String logoHotel, String frontOffice){
		
		this.nomeHotel = nomeHotel;
		this.logoHotel = logoHotel;
		this.frontOffice = frontOffice;
		this.idRedeHotel = idRedeHotel;
	}

	public String getNomeHotel() {
		return nomeHotel;
	}

	public void setNomeHotel(String nomeHotel) {
		this.nomeHotel = nomeHotel;
	}

	public String getLogoHotel() {
		return logoHotel;
	}

	public void setLogoHotel(String logoHotel) {
		this.logoHotel = logoHotel;
	}

	public String getFrontOffice() {
		return frontOffice;
	}

	public void setFrontOffice(String frontOffice) {
		this.frontOffice = frontOffice;
	}

	public String getIdRedeHotel() {
		return idRedeHotel;
	}

	public void setIdRedeHotel(String idRedeHotel) {
		this.idRedeHotel = idRedeHotel;
	}

}
