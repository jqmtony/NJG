/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 违约金序时簿
 */
public class CompensationBillListUI extends AbstractCompensationBillListUI {

	public CompensationBillListUI() throws Exception {
		super();
	}

	protected String getEditUIName() {
		return CompensationBillEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CompensationBillFactory.getRemoteInstance();
	}

	/**
	 * 
	 * 描述：获取当前单据的Table（子类必须实现）
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-2
	 *               <p>
	 */
	protected KDTable getBillListTable() {
		return this.tblCompensation;
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkSelected(getMainTable());
		String contractId = this.getSelectContractId();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", contractId));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.TRUE));
		
		if (ContractBillFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showError(this,"合同已经结算，不能继续增加违约金单据！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}

//	审批时加上数据互斥
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
//		获取用户选择的块
		List idList =ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		boolean hasMutex = false;
		try {			
			FDCClientUtils.requestDataObjectLock(this, idList, "Audit");
			super.actionAudit_actionPerformed(e);	
		}
		catch (Throwable e1) {
			this.handUIException(e1);
			hasMutex = FDCClientUtils.hasMutexed(e1);
		}
		finally
		{
			if (!hasMutex) {
				try {
					FDCClientUtils.releaseDataObjectLock(this, idList);
				} catch (Throwable e1) {
					this.handUIException(e1);
				}
			}	
		}
	}

	protected boolean isHasBillTable() {
		return true;
	}

	protected void displayBillByContract(EntityViewInfo view)
			throws BOSException {
		if(view==null){
			return ;
		}
		
		tblCompensation.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblCompensation.getColumn("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblCompensation.getColumn("moneyDes").getStyleAttributes().setHided(true);
		tblCompensation.getColumn("breachFaichDes").getStyleAttributes().setHided(true);
		tblCompensation.getColumn("compensationAccording").getStyleAttributes().setHided(true);
		tblCompensation.getColumn("otherDes").getStyleAttributes().setHided(true);
		String formatString = "yyyy-MM-dd";
		tblCompensation.getColumn("createDate").getStyleAttributes().setNumberFormat(formatString);
		String contractId = getSelectContractId();
		EntityViewInfo conView = new EntityViewInfo();
		conView.getSelector().add(new SelectorItemInfo("*"));
		conView.getSelector().add(new SelectorItemInfo("currency.*"));
		conView.getSelector().add(new SelectorItemInfo("creator.*"));
		conView.getSelector().add(new SelectorItemInfo("compensationType.*"));
		FilterInfo filter = new FilterInfo();
		conView.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("contract.Id", contractId));
		CompensationBillCollection coll = CompensationBillFactory
				.getRemoteInstance().getCompensationBillCollection(conView);

		for (int i = 0; i < coll.size(); i++) {
			CompensationBillInfo info = coll.get(i);
			IRow row = tblCompensation.addRow();
			row.setUserObject(info);
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("state").setValue(info.getState());
			row.getCell("isCompensated").setValue(
					new Boolean(info.isIsCompensated()));
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
			row.getCell("compensationType")
					.setValue(info.getCompensationType());
			row.getCell("amount").setValue(info.getAmount());
			row.getCell("currency").setValue(info.getCurrency());
			row.getCell("moneyDes").setValue(info.getMoneyDes());
			row.getCell("breachFaichDes").setValue(info.getBreachFaichDes());
			row.getCell("compensationAccording").setValue(
					info.getCompensationAccording());
			row.getCell("otherDes").setValue(info.getOtherDes());
			row.getCell("deductType").setValue(
					CompensationBillEditUI.getResource("settleDeduct"));
			if (info.getCreator() != null) {
				row.getCell("creator").setValue(info.getCreator().getName());
			}
			row.getCell("createDate").setValue(info.getCreateTime());
			row.getCell("fiVouchered").setValue(Boolean.valueOf(info.isFiVouchered()));
		}

	}

	private String getSelectContractId() {
		int rowIndex = this.getMainTable().getSelectManager()
				.getActiveRowIndex();
		String contractId = (String) getMainTable().getCell(rowIndex,
				getKeyFieldName()).getValue();
		return contractId;
	}

	protected String getKeyFieldName() {
		return "id";
	}

	// 数据对象变化时，刷新界面状态
	protected void setActionState() {

	}

	public void onLoad() throws Exception {
		super.onLoad();
		actionTraceDown.setVisible(true);
    	actionTraceDown.setEnabled(true);
    	actionQuery.setVisible(true);
		actionQuery.setEnabled(true);
	}

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return CompensationBillFactory.getRemoteInstance();
	}

	protected void audit(List ids) throws Exception {
		CompensationBillFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		CompensationBillFactory.getRemoteInstance().unAudit(ids);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		for (int i = 0; i < idList.size(); i++) {
			String id = (String) idList.get(i);
			CompensationBillInfo info = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(
					new ObjectUuidPK(BOSUuid.read(id)));
			if (info.isIsCompensated()) {
				MsgBox.showInfo("已经违约的单据,不能反审批!");
				return;
			}
		}
		super.actionUnAudit_actionPerformed(e);
	}
	protected boolean isFootVisible() {
		return false;
	}

	protected void fetchInitData() throws Exception {
		Map param = new HashMap();
		Map initData = ((IFDCBill)getRemoteInterface()).fetchInitData(param);
		
		//获得当前组织
		orgUnit = (FullOrgUnitInfo)initData.get(FDCConstants.FDC_INIT_ORGUNIT);
	}
	
	protected SelectorItemCollection genBillQuerySelector() {
		return null;
	}
	
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		List idList = ContractClientUtils.getSelectedIdValues(tblCompensation, "id");
		FDCSQLBuilder builder = new FDCSQLBuilder();

		builder
				.appendSql("update t_con_compensationbill set faccountviewid =(select top 1 bav.FCompensationAccountID from T_FDC_BeforeAccountView bav where bav.fcompanyid = ?) where ");
		builder.addParam(companyID);
		builder.appendParam("fid", idList.toArray());
		builder.executeUpdate();

		super.actionVoucher_actionPerformed(e);
	}
	protected void tblCompensation_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		changeButtonStatus();
	}
	
	protected void changeButtonStatus() throws EASBizException, BOSException {
		checkSelected();
		int rowIndex = FDCClientHelper.getSelectedRow(tblCompensation);
		IRow row = tblCompensation.getRow(rowIndex);
		String billStatus =row.getCell("state").getValue().toString();
		boolean fiVouchered = ((Boolean)row.getCell("fiVouchered").getValue()).booleanValue();
		if(FDCBillStateEnum.AUDITTED.toString().equals(billStatus)){
			if(fiVouchered){
				actionVoucher.setEnabled(false);
				actionDelVoucher.setEnabled(true);
			}else{
				actionVoucher.setEnabled(true);
				actionDelVoucher.setEnabled(false);
			}
		} else {
			actionVoucher.setEnabled(false);
			actionDelVoucher.setEnabled(false);
		}
	}
	
	/**
	 * 返回定位字段的集合<p>
	 * 增加定位字段：合同原币金额，本位币金额 Modified By Owen_wen 2010-09-06
	 * @author zhiyuan_tang 2010/07/12
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", "signDate", "originalAmount", "amount",};
	}

	protected boolean isOnlyQueryAudited() {
		return true;
	}
}