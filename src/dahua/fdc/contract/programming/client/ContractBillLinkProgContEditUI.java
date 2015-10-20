/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;

/**
 * output class name
 */
public class ContractBillLinkProgContEditUI extends AbstractContractBillLinkProgContEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillLinkProgContEditUI.class);
	private ProgrammingContractInfo pcInfo = null;
	private ContractBillInfo contractBillInfo = null;
    
    /**
     * output class constructor
     */
    public ContractBillLinkProgContEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	init();
    	
    	ProgrammingContractPromptBox selector=new ProgrammingContractPromptBox(this);
		prmtContract.setEnabledMultiSelection(false);
		prmtContract.setQueryInfo(null);
		prmtContract.setEditFormat("$longNumber$");
		prmtContract.setDisplayFormat("$name$");
		prmtContract.setCommitFormat("$longNumber$");
		prmtContract.setSelector(selector);

		this.prmtContract.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				Object newValue = eventObj.getNewValue();
				if (newValue == null) {
					prmtContract.setValue(null);
					txtAmount.setNumberValue(null);
					txtControlAmount.setNumberValue(null);
					txtControlBalance.setNumberValue(null);
					return;
				}
				if (newValue instanceof ProgrammingContractInfo) {
					ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) newValue;
					prmtContract.setValue(pcInfo);
					txtAmount.setNumberValue(pcInfo.getAmount());
					txtControlAmount.setNumberValue(pcInfo.getControlAmount());
					txtControlBalance.setNumberValue(pcInfo.getControlBalance());
				}
			}
		});
    }
	/**
	 * 关联有框架合约时，在查看或编辑时把数据写入
	 * 
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void init() throws EASBizException, BOSException {
		Map uiContext = this.getUIContext();
		contractBillInfo = (ContractBillInfo) uiContext.get("contractBillInfo");
		setTextFormat(txtAmount);
		setTextFormat(txtControlAmount);
		setTextFormat(txtControlBalance);
		
		if (contractBillInfo.getCurProject() != null) {
			EntityViewInfo entityView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			String pro = contractBillInfo.getCurProject().getId().toString();
			Set set = new HashSet();
			set.add(pro);
			filter.getFilterItems().add(new FilterItemInfo("project.id", set, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("programming.isLatest", new Integer(1), CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("programming.state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.EQUALS));
			entityView.setFilter(filter);
			prmtContract.setEntityViewInfo(entityView);
		}

		if (this.getOprtState().equals(OprtState.ADDNEW) || this.getOprtState().equals(OprtState.EDIT)) {
			this.btnConfirm.setEnabled(true);
			this.btnCancel.setEnabled(true);
			this.prmtContract.setEnabled(true);
		} else {
			this.prmtContract.setEnabled(false);
		}
		if (contractBillInfo.getProgrammingContract() != null) {
			ProgrammingContractInfo pcInfo = contractBillInfo.getProgrammingContract();
			if (pcInfo != null) {
				this.prmtContract.setValue(pcInfo);
				this.txtAmount.setNumberValue(pcInfo.getAmount());
				this.txtControlAmount.setNumberValue(pcInfo.getControlAmount());
				this.txtControlBalance.setNumberValue(pcInfo.getControlBalance());
			}
		}
	}
	
	/**
	 * 初始化KDFormattedTextField的相关基础属性txtControlBalance
	 * */
	private static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}
    /**
     * output actionConfirm_actionPerformed
     */
    public void actionConfirm_actionPerformed(ActionEvent e) throws Exception
    {
    	pcInfo = (ProgrammingContractInfo) this.prmtContract.getValue();
		contractBillInfo.setProgrammingContract(pcInfo);
		destroyWindow();
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	getUIContext().put("cancel", "cancel");
    	destroyWindow();
    }

}