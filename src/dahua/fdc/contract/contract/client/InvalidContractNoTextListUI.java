/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.TraceOldSplitVoucherFacadeFactory;
import com.kingdee.eas.fdc.finance.client.FDCAdjustBillListUI;
import com.kingdee.eas.fdc.finance.client.PaymentBillClientUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvalidContractNoTextListUI extends AbstractInvalidContractNoTextListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvalidContractNoTextListUI.class);
    
    /**
     * output class constructor
     */
    public InvalidContractNoTextListUI() throws Exception
    {
        super();
    }

    protected ICoreBase getRemoteInterface() throws BOSException {
		return ContractWithoutTextFactory.getRemoteInstance();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ContractWithoutTextFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {

	}

	protected void unAudit(List ids) throws Exception {

	}

	protected String getKeyFieldName() {
		return super.getKeyFieldName();
	}

	public void onLoad() throws Exception {
		if ((!SysContext.getSysContext().getCurrentOrgUnit()
				.isIsCompanyOrgUnit())
				&& (SysContext.getSysContext().getCurrentOrgUnit()
						.isIsCostOrgUnit())) {
			MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
			SysUtil.abort();
		}
		if(!FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			MsgBox.showWarning(this, "此财务组织未启用财务成本一体化！");
			SysUtil.abort();
		}
		super.onLoad();
		FDCClientHelper.setActionEnable(new ItemAction[] { actionAudit,
				actionUnAudit, actionWorkFlowG, actionAttachment, actionAddNew,actionEdit,actionRemove }, false);
		menuWorkFlow.setVisible(false);
		if(FDCUtils.isAdjustVourcherModel(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			this.actionViewAdjust.setVisible(true);
			this.actionViewAdjust.setEnabled(true);
		}else{
			this.actionViewAdjust.setVisible(false);
			this.actionViewAdjust.setEnabled(false);
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length > 1) {
			MsgBox.showWarning(this, FDCSplitClientHelper
					.getRes("multiRowSelected"));
			SysUtil.abort();
		} else if (selectRows.length == 1) {
			Object obj = getMainTable().getCell(selectRows[0], "id").getValue();
			if (obj != null) {
				String id = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, id);
				IUIWindow testUI = UIFactory.createUIFactory(
						UIFactoryName.NEWTAB).create(
						ContractWithoutTextEditUI.class.getName(), uiContext, null,
						"FINDVIEW");
				testUI.show();
			}
		}
	}

	public void actionTrace_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedIdValues(getMainTable(),
				getKeyFieldName());
		int size = idList.size();
		for (int i = 0; i < size; i++) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("paymentBill.contractBillId", idList.get(i)));
//			filter.getFilterItems().add(
//					new FilterItemInfo("splitState",
//							CostSplitStateEnum.ALLSPLIT_VALUE));
			filter.getFilterItems().add(
					new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
							CompareType.NOTEQUALS));
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("isCostSplit");
			selector.add("number");
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance()
					.getContractWithoutTextInfo(
							new ObjectUuidPK(BOSUuid.read(idList.get(i)
									.toString())));
			if (info.isIsCostSplit()) {
				if (!PaymentSplitFactory.getRemoteInstance()
						.exists(filter)) {
					MsgBox.showError(this, "合同编号为:" + info.getNumber()
							+ "的无文本合同付款未拆分！");
					SysUtil.abort();
				}
			} else {
				if (!PaymentNoCostSplitFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showError(this, "合同编号为:" + info.getNumber()
							+ "的合同付款未拆分！");
					SysUtil.abort();
				}
			}
		}
		TraceOldSplitVoucherFacadeFactory.getRemoteInstance().traceContractNoText(idList);
		MsgBox.showInfo(this, "操作成功！");
		refresh(e);
	}
	
	protected void initTable() {
		
	}

	public void actionViewAdjust_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(FDCAdjustBillListUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
		refresh(e);
	}
	
	protected String[] getLocateNames() {
		return new String[] {"number", "name", "receiveUnit.name", "signDate", };
	}
}