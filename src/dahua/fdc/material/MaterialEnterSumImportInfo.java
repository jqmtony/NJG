package com.kingdee.eas.fdc.material;

import java.math.BigDecimal;
import java.util.Date;

import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.contract.ContractBillInfo;

/**
 * 从材料进场计划导入实体类
 * */
public class MaterialEnterSumImportInfo {

	private CurProjectInfo curProject;
	private SupplierInfo supplier ;
	private MaterialInfo material;
	private String model ;
	private MeasureUnitInfo unit ;
	private BigDecimal quantity; //需求数量
	private BigDecimal orderQuantity; //已经订货数量
	private Date enterTime;
    private SupplierInfo contractUnit ;//施工单位
    private ContractBillInfo contractBill ;//施工合同
    private ContractBillInfo materialBill ;//材料合同

    private String entryID ;//此条记录的分录ID
	public String getEntryID() {
		return entryID;
	}
	public void setEntryID(String entryID) {
		this.entryID = entryID;
	}

    private String sourceBillId ;//源单ID

	public CurProjectInfo getCurProject() {
		return curProject;
	}
	public void setCurProject(CurProjectInfo curProject) {
		this.curProject = curProject;
	}
	public SupplierInfo getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierInfo supplier) {
		this.supplier = supplier;
	}
	public MaterialInfo getMaterial() {
		return material;
	}
	public void setMaterial(MaterialInfo material) {
		this.material = material;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public SupplierInfo getContractUnit() {
		return contractUnit;
	}
	public void setContractUnit(SupplierInfo contractUnit) {
		this.contractUnit = contractUnit;
	}
	public ContractBillInfo getContractBill() {
		return contractBill;
	}
	public void setContractBill(ContractBillInfo contractBill) {
		this.contractBill = contractBill;
	}
	public ContractBillInfo getMaterialBill() {
		return materialBill;
	}
	public void setMaterialBill(ContractBillInfo materialBill) {
		this.materialBill = materialBill;
	}
	public MeasureUnitInfo getUnit() {
		return unit;
	}
	public void setUnit(MeasureUnitInfo unit) {
		this.unit = unit;
	}
	public String getSourceBillId() {
		return sourceBillId;
	}
	public void setSourceBillId(String sourceBillId) {
		this.sourceBillId = sourceBillId;
	}
    
    
}
