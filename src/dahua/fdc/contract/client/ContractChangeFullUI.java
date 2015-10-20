/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;

/**
 * 变更信息
 * 
 * modified by Owen_wen 不再显示“保存”、“提交”、“审批中”状态的单据。2013-03-26
 */
public class ContractChangeFullUI extends AbstractContractChangeFullUI {
	private static final Logger logger = CoreUIObject.getLogger(ContractChangeFullUI.class);

	public ContractChangeFullUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.checkParsed();
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("changeType.name");
		view.getSelector().add("changeReason.name");
		view.getSelector().add("conductDept.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED_VALUE, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING_VALUE, CompareType.NOTEQUALS));
		ContractChangeBillCollection contractChangeBillCollection = ContractChangeBillFactory
				.getRemoteInstance().getContractChangeBillCollection(view);
		for (int i = 0; i < contractChangeBillCollection.size(); i++) {
			ContractChangeBillInfo info = contractChangeBillCollection.get(i);
			IRow row = this.tblMain.addRow();
			row.setUserObject(info.getId().toString());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
			if(info.isHasSettled()){
				row.getCell("amount").setValue(info.getBalanceAmount());
			}else{
				row.getCell("amount").setValue(info.getAmount());
			}
			row.getCell("type").setValue(info.getChangeType());
			row.getCell("reason").setValue(info.getChangeReason());
			row.getCell("department").setValue(info.getConductDept());
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) return ;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, row.getUserObject());
			uiContext.put("disableSplit", Boolean.TRUE);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractChangeBillEditUI.class.getName(), uiContext,
					null, "FINDVIEW");
			uiWindow.show();
		}
	}

}