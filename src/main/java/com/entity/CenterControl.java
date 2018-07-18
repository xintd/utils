package com.entity;

import java.util.Date;

/**
* Created by yuanml on 2018-05-08
*@Description center_control 实体类
*/ 


public class CenterControl{
	private Long id;
	private String address;
	private Long area;
	private Long city;
	private Date createTime;
	private Long gateNum;
	private String name;
	private Long province;
	private String status;
	private Long contractInfo;
	private Long activateGateNum;
	private String deviceId;
	private String macAddress;
	private String version;
	private Long user;
	private Long relateGateNum;
	private Long roomNum;
	private String addStatus;
	private Long inGateNum;
	private Date deviceTime;
	private String remoteAddr;
	private String hasDelete;
	private String remotePort;
	private String provider;
	private Date offlineTime;
	private Integer online;
	private Date onlineTime;
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setAddress(String address){
	this.address=address;
	}
	public String getAddress(){
		return address;
	}
	public void setArea(Long area){
	this.area=area;
	}
	public Long getArea(){
		return area;
	}
	public void setCity(Long city){
	this.city=city;
	}
	public Long getCity(){
		return city;
	}
	public void setCreateTime(Date createTime){
	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setGateNum(Long gateNum){
	this.gateNum=gateNum;
	}
	public Long getGateNum(){
		return gateNum;
	}
	public void setName(String name){
	this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setProvince(Long province){
	this.province=province;
	}
	public Long getProvince(){
		return province;
	}
	public void setStatus(String status){
	this.status=status;
	}
	public String getStatus(){
		return status;
	}
	public void setContractInfo(Long contractInfo){
	this.contractInfo=contractInfo;
	}
	public Long getContractInfo(){
		return contractInfo;
	}
	public void setActivateGateNum(Long activateGateNum){
	this.activateGateNum=activateGateNum;
	}
	public Long getActivateGateNum(){
		return activateGateNum;
	}
	public void setDeviceId(String deviceId){
	this.deviceId=deviceId;
	}
	public String getDeviceId(){
		return deviceId;
	}
	public void setMacAddress(String macAddress){
	this.macAddress=macAddress;
	}
	public String getMacAddress(){
		return macAddress;
	}
	public void setVersion(String version){
	this.version=version;
	}
	public String getVersion(){
		return version;
	}
	public void setUser(Long user){
	this.user=user;
	}
	public Long getUser(){
		return user;
	}
	public void setRelateGateNum(Long relateGateNum){
	this.relateGateNum=relateGateNum;
	}
	public Long getRelateGateNum(){
		return relateGateNum;
	}
	public void setRoomNum(Long roomNum){
	this.roomNum=roomNum;
	}
	public Long getRoomNum(){
		return roomNum;
	}
	public void setAddStatus(String addStatus){
	this.addStatus=addStatus;
	}
	public String getAddStatus(){
		return addStatus;
	}
	public void setInGateNum(Long inGateNum){
	this.inGateNum=inGateNum;
	}
	public Long getInGateNum(){
		return inGateNum;
	}
	public void setDeviceTime(Date deviceTime){
	this.deviceTime=deviceTime;
	}
	public Date getDeviceTime(){
		return deviceTime;
	}
	public void setRemoteAddr(String remoteAddr){
	this.remoteAddr=remoteAddr;
	}
	public String getRemoteAddr(){
		return remoteAddr;
	}
	public void setHasDelete(String hasDelete){
	this.hasDelete=hasDelete;
	}
	public String getHasDelete(){
		return hasDelete;
	}
	public void setRemotePort(String remotePort){
	this.remotePort=remotePort;
	}
	public String getRemotePort(){
		return remotePort;
	}
	public void setProvider(String provider){
	this.provider=provider;
	}
	public String getProvider(){
		return provider;
	}
	public void setOfflineTime(Date offlineTime){
	this.offlineTime=offlineTime;
	}
	public Date getOfflineTime(){
		return offlineTime;
	}
	public void setOnline(Integer online){
	this.online=online;
	}
	public Integer getOnline(){
		return online;
	}
	public void setOnlineTime(Date onlineTime){
	this.onlineTime=onlineTime;
	}
	public Date getOnlineTime(){
		return onlineTime;
	}
}

