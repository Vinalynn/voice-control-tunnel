package org.vinalynn.voicectrl.doo;

public class VoiceControlUserDO {
	private java.math.BigInteger ID;
	private String CODE;
	private String NAME;
	private String PASSWORD;
	private Integer STATE;
    
	public java.math.BigInteger getID(){
		return ID;
	}
	public void setID(java.math.BigInteger ID){
		this.ID = ID;
	}
	public String getCODE(){
		return CODE;
	}
	public void setCODE(String CODE){
		this.CODE = CODE;
	}
	public String getNAME(){
		return NAME;
	}
	public void setNAME(String NAME){
		this.NAME = NAME;
	}
	public String getPASSWORD(){
		return PASSWORD;
	}
	public void setPASSWORD(String PASSWORD){
		this.PASSWORD = PASSWORD;
	}
	public Integer getSTATE(){
		return STATE;
	}
	public void setSTATE(Integer STATE){
		this.STATE = STATE;
	}
}