package org.vinalynn.voicectrl.doo;

public class VoiceControlCommandDO {
	private java.math.BigInteger id;
	private Integer userId;
	private Integer command;
	private Integer createDate;
	private Integer isLoaded;	//is loaded by client
    
	public java.math.BigInteger getId(){
		return id;
	}
	public void setId(java.math.BigInteger id){
		this.id = id;
	}
	public Integer getUserId(){
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public Integer getCommand(){
		return command;
	}
	public void setCommand(Integer command){
		this.command = command;
	}
	public Integer getCreateDate(){
		return createDate;
	}
	public void setCreateDate(Integer createDate){
		this.createDate = createDate;
	}
	public Integer getIsLoaded(){
		return isLoaded;
	}
	public void setIsLoaded(Integer isLoaded){
		this.isLoaded = isLoaded;
	}
}