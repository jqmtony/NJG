/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.AcceptanceLetterInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HeYueDanEditUI extends AbstractHeYueDanEditUI
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(HeYueDanEditUI.class);
    
    /**
     * output class constructor
     */
    public HeYueDanEditUI() throws Exception
    {
        super();
    }
    
    private IObjectPK conPK = null ;

    public void onLoad() throws Exception {
		super.onLoad();
		prmtConContrarct.setQueryInfo(
				"com.kingdee.eas.fdc.contract.programming.app.ProgramContractQuery");
		
		Map uiCtx = this.getUIContext();
		if(uiCtx.get(UIContext.ID)!= null){
			conPK = new ObjectUuidPK(uiCtx.get(UIContext.ID).toString()) ;
			
		}
		String STATUS_ADDNEW = "ADDNEW";
		IContractBill service = ContractBillFactory.getRemoteInstance();
		ContractBillInfo contract = service.getContractBillInfo(conPK,getSelectors());
		
		if(!(FDCBillStateEnum.SAVED.equals(contract.getState()) || 
				FDCBillStateEnum.SUBMITTED.equals(contract.getState()))){
			prmtConContrarct.setEditable(false);
			
		}else{
			prmtConContrarct.setEditable(true);
		}
		
		//当合同由招标通知书直接生成但招标通知书未关联框架合约、以及新增合同时用户可以维护该界面的“框架合约名称”字段
		if(uiCtx.get("state") != null){
			if(STATUS_ADDNEW.equals(uiCtx.get("state"))){
				prmtConContrarct.setEditable(true);
			}else{
				prmtConContrarct.setEditable(false);
			}
		}
		
		AcceptanceLetterInfo alInfo = new AcceptanceLetterInfo();
		String srcBillId = contract.getSourceBillId();
		BOSUuid bosId = BOSUuid.read(srcBillId);
		//当合同由招标通知书直接生成但招标通知书未关联框架合约
		if(alInfo.getBOSType().equals(bosId.getType())){
			
			
		}
		
		this.btnOK.setEnabled(true);
		this.btnCal.setEnabled(true);
		
		
    }
    
	public void actionCal_actionPerformed(ActionEvent e) throws Exception {
		this.actionExitCurrent_actionPerformed(e);
		
	}

	public void actionOK_actionPerformed(ActionEvent e) throws Exception {
		//关联合同与框架合约
		ProgrammingContractInfo pcInfo =  (ProgrammingContractInfo) this.prmtConContrarct.getData();
		if(pcInfo != null){
			IContractBill service = ContractBillFactory.getRemoteInstance();
			if(!service.exists(conPK)){
				MsgBox.showInfo(this, "先保存合同再关联框架合同！");
				abort();
			}
			ContractBillInfo contract = service.getContractBillInfo(conPK);
			contract.setProgrammingContract(pcInfo);
			service.update(conPK, contract);
		}else{
			MsgBox.showInfo(this, "先选择框架合同！");
			abort();
		}
		super.actionOK_actionPerformed(e);
	}

	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.precision"));

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractType.isLeaf"));
		sic.add(new SelectorItemInfo("contractType.level"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longnumber"));
		sic.add(new SelectorItemInfo("contractType.name"));
		
		sic.add(new SelectorItemInfo("codeType.id"));
		sic.add(new SelectorItemInfo("codeType.name"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.thirdType"));
		sic.add(new SelectorItemInfo("codeType.secondType"));
		
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractPlan.*"));

		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));

		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		
		sic.add(new SelectorItemInfo("payItems.*"));
		sic.add(new SelectorItemInfo("bail.*"));
		sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
		sic.add(new SelectorItemInfo("bail.entry.bailAmount"));
		sic.add(new SelectorItemInfo("bail.entry.prop"));
		sic.add(new SelectorItemInfo("bail.entry.bailDate"));
		sic.add(new SelectorItemInfo("bail.entry.desc"));
		
		sic.add(new SelectorItemInfo("auditor.id"));
		
		sic.add(new SelectorItemInfo("sourceBillId"));
		
		sic.add(new SelectorItemInfo("mainContract.*"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("information"));
		
		sic.add(new SelectorItemInfo("overRate"));
		sic.add(new SelectorItemInfo("bizDate"));
		sic.add(new SelectorItemInfo("bookDate"));
		
		sic.add(new SelectorItemInfo("programmingContract.*"));
		sic.add(new SelectorItemInfo("*"));
		
		return sic;
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		//return ContractBillFactory.getRemoteInstance();
		return null;
	}
	
	protected void prmtConContrarct_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) e.getNewValue();
		if(pcInfo != null){
			pcInfo.getAmount();
			pcInfo.getControlAmount();
			pcInfo.getControlBalance();
			
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			this.conBalance.setText(pcInfo.getBalance().toString());
			this.conControlAmt.setText(pcInfo.getControlAmount().toString());
			this.conControlBalance.setText(pcInfo.getControlBalance().toString());
		}else{
			this.conBalance.setText("");
			this.conControlAmt.setText("");
			this.conControlBalance.setText("");
		}
		
    }
	

}