package com.entity;

import java.util.Date;

/**
* Created by yuanml on 2018-06-21
*@author yuanml
*@Description mc_user 实体类
*/ 


public class McUser{
	private Long ID;
	private String USERNAME;
	private String PASSWORD;
	private String NICKNAME;
	private String MOBILEPHONE;
	private String STATUS;
	private Date EDITTIME;
	private String EMAIL;
	private Date LASTLOGIN;
	private Integer LOGINCOUNT;
	private String REALNAME;
	private Long EDITOR;
	private Date editTime;
	private Date lastLogin;
	private Long EDITTOR;
	private String companyType;
	private String faceImg;
	private String AREA;
	private String CITY;
	private String PROVINCE;
	private String STREET;
	private Long SUPERIOR;
	private Long parentid;
	private Integer utype;
	public void setID(Long ID){
	this.ID=ID;
	}
	public Long getID(){
		return ID;
	}
	public void setUSERNAME(String USERNAME){
	this.USERNAME=USERNAME;
	}
	public String getUSERNAME(){
		return USERNAME;
	}
	public void setPASSWORD(String PASSWORD){
	this.PASSWORD=PASSWORD;
	}
	public String getPASSWORD(){
		return PASSWORD;
	}
	public void setNICKNAME(String NICKNAME){
	this.NICKNAME=NICKNAME;
	}
	public String getNICKNAME(){
		return NICKNAME;
	}
	public void setMOBILEPHONE(String MOBILEPHONE){
	this.MOBILEPHONE=MOBILEPHONE;
	}
	public String getMOBILEPHONE(){
		return MOBILEPHONE;
	}
	public void setSTATUS(String STATUS){
	this.STATUS=STATUS;
	}
	public String getSTATUS(){
		return STATUS;
	}
	public void setEDITTIME(Date EDITTIME){
	this.EDITTIME=EDITTIME;
	}
	public Date getEDITTIME(){
		return EDITTIME;
	}
	public void setEMAIL(String EMAIL){
	this.EMAIL=EMAIL;
	}
	public String getEMAIL(){
		return EMAIL;
	}
	public void setLASTLOGIN(Date LASTLOGIN){
	this.LASTLOGIN=LASTLOGIN;
	}
	public Date getLASTLOGIN(){
		return LASTLOGIN;
	}
	public void setLOGINCOUNT(Integer LOGINCOUNT){
	this.LOGINCOUNT=LOGINCOUNT;
	}
	public Integer getLOGINCOUNT(){
		return LOGINCOUNT;
	}
	public void setREALNAME(String REALNAME){
	this.REALNAME=REALNAME;
	}
	public String getREALNAME(){
		return REALNAME;
	}
	public void setEDITOR(Long EDITOR){
	this.EDITOR=EDITOR;
	}
	public Long getEDITOR(){
		return EDITOR;
	}
	public void setEditTime(Date editTime){
	this.editTime=editTime;
	}
	public Date getEditTime(){
		return editTime;
	}
	public void setLastLogin(Date lastLogin){
	this.lastLogin=lastLogin;
	}
	public Date getLastLogin(){
		return lastLogin;
	}
	public void setEDITTOR(Long EDITTOR){
	this.EDITTOR=EDITTOR;
	}
	public Long getEDITTOR(){
		return EDITTOR;
	}
	public void setCompanyType(String companyType){
	this.companyType=companyType;
	}
	public String getCompanyType(){
		return companyType;
	}
	public void setFaceImg(String faceImg){
	this.faceImg=faceImg;
	}
	public String getFaceImg(){
		return faceImg;
	}
	public void setAREA(String AREA){
	this.AREA=AREA;
	}
	public String getAREA(){
		return AREA;
	}
	public void setCITY(String CITY){
	this.CITY=CITY;
	}
	public String getCITY(){
		return CITY;
	}
	public void setPROVINCE(String PROVINCE){
	this.PROVINCE=PROVINCE;
	}
	public String getPROVINCE(){
		return PROVINCE;
	}
	public void setSTREET(String STREET){
	this.STREET=STREET;
	}
	public String getSTREET(){
		return STREET;
	}
	public void setSUPERIOR(Long SUPERIOR){
	this.SUPERIOR=SUPERIOR;
	}
	public Long getSUPERIOR(){
		return SUPERIOR;
	}
	public void setParentid(Long parentid){
	this.parentid=parentid;
	}
	public Long getParentid(){
		return parentid;
	}
	public void setUtype(Integer utype){
	this.utype=utype;
	}
	public Integer getUtype(){
		return utype;
	}
}

