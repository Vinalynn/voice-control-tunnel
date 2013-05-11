package org.vinalynn.voicectrl.doo;

public class StaticConfigDataDO {
	private Long id;
	private String name;
	private String value;
	private String grp;
	private Long state;
    
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}
	public String getGrp(){
		return grp;
	}
	public void setGrp(String grp){
		this.grp = grp;
	}
	public Long getState(){
		return state;
	}
	public void setState(Long state){
		this.state = state;
	}
}