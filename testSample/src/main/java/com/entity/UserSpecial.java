package com.entity;

import java.util.Date;

/**
* Created by yuanml on 2018-06-21
*@author yuanml
*@Description user_special 实体类
*/ 


public class UserSpecial{
	private Integer id;
	private String mcUserId;
	private String userName;
	private String deleteState;
	private String isEnterprise;
	private Date createTime;
	private Date updateTime;
	private String billbankcard;
	private String billusername;
	private String billpassword;
	private String billsecretkey;
	public void setId(Integer id){
	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	public void setMcUserId(String mcUserId){
	this.mcUserId=mcUserId;
	}
	public String getMcUserId(){
		return mcUserId;
	}
	public void setUserName(String userName){
	this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setDeleteState(String deleteState){
	this.deleteState=deleteState;
	}
	public String getDeleteState(){
		return deleteState;
	}
	public void setIsEnterprise(String isEnterprise){
	this.isEnterprise=isEnterprise;
	}
	public String getIsEnterprise(){
		return isEnterprise;
	}
	public void setCreateTime(Date createTime){
	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setUpdateTime(Date updateTime){
	this.updateTime=updateTime;
	}
	public Date getUpdateTime(){
		return updateTime;
	}
	public void setBillbankcard(String billbankcard){
	this.billbankcard=billbankcard;
	}
	public String getBillbankcard(){
		return billbankcard;
	}
	public void setBillusername(String billusername){
	this.billusername=billusername;
	}
	public String getBillusername(){
		return billusername;
	}
	public void setBillpassword(String billpassword){
	this.billpassword=billpassword;
	}
	public String getBillpassword(){
		return billpassword;
	}
	public void setBillsecretkey(String billsecretkey){
	this.billsecretkey=billsecretkey;
	}
	public String getBillsecretkey(){
		return billsecretkey;
	}
}

