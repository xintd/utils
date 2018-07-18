package com.entity;

import java.util.Date;

/**
* Created by yuanml on 2018-06-23
*@author yuanml
*@Description energy_emwm_consumption 实体类
*/ 


public class EnergyEmwmConsumption{
	private Long id;
	private Integer code;
	private Double current;
	private Double flow;
	private Double kwh;
	private Double pf;
	private Double power;
	private String shutdownTime;
	private Date upTime;
	private Double volt;
	private Integer warning;
	private Long deviceInfo;
	private Long energyDevice;
	public void setId(Long id){
	this.id=id;
	}
	public Long getId(){
		return id;
	}
	public void setCode(Integer code){
	this.code=code;
	}
	public Integer getCode(){
		return code;
	}
	public void setCurrent(Double current){
	this.current=current;
	}
	public Double getCurrent(){
		return current;
	}
	public void setFlow(Double flow){
	this.flow=flow;
	}
	public Double getFlow(){
		return flow;
	}
	public void setKwh(Double kwh){
	this.kwh=kwh;
	}
	public Double getKwh(){
		return kwh;
	}
	public void setPf(Double pf){
	this.pf=pf;
	}
	public Double getPf(){
		return pf;
	}
	public void setPower(Double power){
	this.power=power;
	}
	public Double getPower(){
		return power;
	}
	public void setShutdownTime(String shutdownTime){
	this.shutdownTime=shutdownTime;
	}
	public String getShutdownTime(){
		return shutdownTime;
	}
	public void setUpTime(Date upTime){
	this.upTime=upTime;
	}
	public Date getUpTime(){
		return upTime;
	}
	public void setVolt(Double volt){
	this.volt=volt;
	}
	public Double getVolt(){
		return volt;
	}
	public void setWarning(Integer warning){
	this.warning=warning;
	}
	public Integer getWarning(){
		return warning;
	}
	public void setDeviceInfo(Long deviceInfo){
	this.deviceInfo=deviceInfo;
	}
	public Long getDeviceInfo(){
		return deviceInfo;
	}
	public void setEnergyDevice(Long energyDevice){
	this.energyDevice=energyDevice;
	}
	public Long getEnergyDevice(){
		return energyDevice;
	}
}

