/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractMoveHistoryFactory;
import com.kingdee.eas.fdc.contract.ContractMoveHistoryInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 合同交底编辑界面
 */
public class ContractMoveEditUI extends AbstractContractMoveEditUI {
	private static final String CONTRACT_ID = "contractID";

	private static final Logger logger = CoreUIObject
			.getLogger(ContractMoveEditUI.class);

	private String contractBillID;
	
	private String cuId ;
	
	private boolean isConfirm;
	
	//责任人是否可以选择其他部门的人员
	private boolean canSelectOtherOrgPerson = false;
	/**
	 * output class constructor
	 */
	public ContractMoveEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		fetchInitData() ;
		
		super.onLoad();
		
		this.contractBillID = (String) getUIContext().get(CONTRACT_ID);
		loadFields();
		toolBar.setVisible(false);
		menuBar.setVisible(false);
		txtOldRespDept.setEditable(false);
		txtOldRespPerson.setEditable(false);
		
		FDCClientUtils.setRespDeptF7(prmtRespDept, this, canSelectOtherOrgPerson?null:cuId);		
		FDCClientUtils.setPersonF7(prmtRespPerson, this, canSelectOtherOrgPerson?null:cuId);
	}
	
	protected  void fetchInitData() throws Exception{
		
		try {
			HashMap param = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if(param.get(FDCConstants.FDC_PARAM_SELECTPERSON)!=null){
				canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
			}
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}

	public void loadFields() {
		ContractBillInfo contractBillInfo = null;
		try {
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("respDept.name");
			selectors.add("respPerson.name");
			selectors.add("CU.id");
			
			contractBillInfo = ContractBillFactory.getRemoteInstance()
					.getContractBillInfo(new ObjectUuidPK(contractBillID),selectors);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		} 
		
		if(contractBillInfo.getRespDept() != null) {
			txtOldRespDept.setText(contractBillInfo.getRespDept().getName());
			txtOldRespDept.setUserObject(contractBillInfo.getRespDept().getId().toString());
		}
		if(contractBillInfo.getRespPerson() != null) {
			txtOldRespPerson.setText(contractBillInfo.getRespPerson().getName());
			txtOldRespPerson.setUserObject(contractBillInfo.getRespPerson().getId().toString());
		}
		
		cuId = contractBillInfo.getCU().getId().toString();
	}

	private void checkBeforeConfirm() {
		//校验必录字段
		FDCClientVerifyHelper.verifyRequire(this);
		
		//责任人和责任部门不能与合同的原责任人和责任部门相同
		String respDeptId = FDCHelper.getF7Id(prmtRespDept);
		String respPersonId = FDCHelper.getF7Id(prmtRespPerson);
		String oldRespDeptId = (String)txtOldRespDept.getUserObject();
		String oldRespPersonId = (String)txtOldRespPerson.getUserObject();
		
		if(respDeptId.equals(oldRespDeptId) && respPersonId.equals(oldRespPersonId)) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("oldRespDupWithNew"));
			SysUtil.abort();
		}
	}
	/**
	 * output btnConfirm_actionPerformed method
	 */
	protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		
		checkBeforeConfirm();

		//更新合同
		AdminOrgUnitInfo respDept = (AdminOrgUnitInfo) prmtRespDept.getData();

		PersonInfo respPerson = (PersonInfo) prmtRespPerson.getData();

		ContractBillInfo contractBillInfo = new ContractBillInfo();
		contractBillInfo.setId(BOSUuid.read(contractBillID));
		contractBillInfo.setRespDept(respDept);
		contractBillInfo.setRespPerson(respPerson);

		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("respDept");
		selectors.add("respPerson");

		ContractBillFactory.getRemoteInstance().updatePartial(contractBillInfo,
				selectors);
		
		//保存交底记录
		ContractMoveHistoryInfo cmh = new ContractMoveHistoryInfo();
		cmh.setRespDept(respDept);
		cmh.setRespPerson(respPerson);
		cmh.setMoveDate((Date)pkMoveDate.getValue());
		cmh.setRemark(txtRemark.getText());
		cmh.setContractBillID(contractBillID);
		
		ContractMoveHistoryFactory.getRemoteInstance().addnew(cmh);
		
		setConfirm(true);
		destroyWindow();
	}

	/**
	 * output btnCanc_actionPerformed method
	 */
	protected void btnCanc_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		
		setConfirm(false);
		
		destroyWindow();
	}
	
	

	public static boolean showMe(String contractId, IUIObject owner) throws Exception {

		UIContext uiContext = new UIContext(owner);
		uiContext.put(CONTRACT_ID, contractId);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ContractMoveEditUI.class.getName(), uiContext, null,
						OprtState.EDIT);

		uiWindow.show();
		
		return ((ContractMoveEditUI)uiWindow.getUIObject()).isConfirm();
	}

	public boolean isConfirm() {
		return isConfirm;
	}

	private void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}


}