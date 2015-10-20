/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeCollection;
import com.kingdee.eas.fdc.basedata.CounterclaimTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CompensationBillEditUI extends AbstractCompensationBillEditUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ComAndPriseResource";

	private CounterclaimTypeCollection counterclaimTypeCollection = null;
	
	private DateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * output class constructor
	 */
	public CompensationBillEditUI() throws Exception {
		super();
		jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		CompensationBillInfo info = (CompensationBillInfo) this.editData;
		info.setNumber(this.txtNumber.getText());
		info.setBreachFaichDes(this.txtBreachFaichDes.getText());
		info.setMoneyDes(this.txtMoneyDes.getText());
		info.setCompensationAccording(this.txtCompensationAccording.getText());
		info.setOtherDes(this.txtOtherDes.getText());
		info.setAmount(this.txtAmount.getBigDecimalValue());
		info.setCurrency((CurrencyInfo) this.comboCurrency.getSelectedItem());
		info.setCompensationType((CounterclaimTypeInfo) this.comboCompensationType.getValue());
		info.setName(this.txtName.getText());
		info.setOriginalAmount(this.txtOriginalAmount.getBigDecimalValue());
		info.setExRate(this.txtExRate.getBigDecimalValue());
	}

	protected IObjectValue createNewData() {
		CompensationBillInfo compensation = new CompensationBillInfo();
		
		if(counterclaimTypeCollection!=null && counterclaimTypeCollection.size()>0){
			compensation.setCompensationType(counterclaimTypeCollection.get(0));
		}
		
		String contractId = (String) this.getUIContext().get("contractBillId");
		if (contractId != null) {
			try {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add(new SelectorItemInfo("number"));
				sels.add(new SelectorItemInfo("name"));
				sels.add(new SelectorItemInfo("orgUnit.id"));
				sels.add(new SelectorItemInfo("codingNumber"));
				sels.add(new SelectorItemInfo("currency.number"));
				sels.add(new SelectorItemInfo("currency.name"));
				sels.add(new SelectorItemInfo("exRate"));
				//2009-1-6 加入工程项目的查询字段
				sels.add(new SelectorItemInfo("curProject.id"));
				sels.add(new SelectorItemInfo("curProject.name"));
				sels.add(new SelectorItemInfo("curProject.number"));
				sels.add(new SelectorItemInfo("curProject.longNumber"));
				sels.add(new SelectorItemInfo("curProject.codingNumber"));
				ContractBillInfo contract = ContractBillFactory
						.getRemoteInstance().getContractBillInfo(
								new ObjectUuidPK(BOSUuid.read(contractId)),sels);
	
				compensation.setContract(contract);
				//2009-1-6 保存时增加对工程项目的保存
				compensation.setCurProject(contract.getCurProject());
				//compensation.setCurrency(contract.getCurrency());				
				compensation.setOrgUnit(contract.getOrgUnit());
				compensation.setCurrency(contract.getCurrency());
				compensation.setExRate(contract.getExRate());
				
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			} 
		}
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		compensation.setCreator(user);
//		compensation.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			compensation.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		compensation.setAmount(FDCHelper.ZERO);
		compensation.setSourceType(SourceTypeEnum.ADDNEW);
		
		//目前不支持外币
		//compensation.setCurrency(this.baseCurrency);
		
		return compensation;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CompensationBillFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.initComboCurrency(this.comboCurrency, true);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		comboCompensationType.setEntityViewInfo(view);
		
//		counterclaimTypeCollection = CounterclaimTypeFactory
//				.getRemoteInstance().getCounterclaimTypeCollection(view);
//		comboCompensationType.addItems(counterclaimTypeCollection.toArray());
		super.onLoad();
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
//		this.actionAudit.setEnabled(false);
//		this.actionUnAudit.setEnabled(false);
//		this.actionAudit.setVisible(false);
//		this.actionUnAudit.setVisible(false);
		this.actionCopyFrom.setVisible(false);
//		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.txtBreachFaichDes.setMaxLength(200);
		this.txtCompensationAccording.setMaxLength(200);
		this.txtMoneyDes.setMaxLength(200);
		this.txtOtherDes.setMaxLength(200);
		this.txtAmount.setNegatived(false);
		this.txtAmount.setRequired(true);
		this.txtAmount.setEnabled(false);
		this.txtOriginalAmount.setRequired(true);
		this.txtExRate.setRequired(true);
		this.txtCreateDate.setEnabled(false);
		this.txtCreator.setEnabled(false);
		this.comboCurrency.setEnabled(false);
		this.txtMoneyDes.setAutoscrolls(true);
		this.txtMoneyDes.setWrapStyleWord(true);
//		this.comboCompensationType.setRequired(true);
		this.actionCopy.setVisible(false);
		actionCopy.setEnabled(false);
		this.actionCopyFrom.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		//审批人、审批时间不允许手工录入
		this.txtAuditor.setEnabled(false);
		this.txtAuditDate.setEnabled(false);
	}
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
//		super.actionCopy_actionPerformed(e);
		comboCurrency.setEnabled(false);
	}
	/**
     * 计算本币金额
     *
     */
    private void calLocalAmt() {
    	if(txtOriginalAmount.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
    		BigDecimal lAmount = txtOriginalAmount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue());
    		txtAmount.setNumberValue(lAmount);
    	}
    	else {
    		txtAmount.setNumberValue(null);
    	}
    }

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
		//检查合同是否已经结算
		CompensationBillInfo info = (CompensationBillInfo) this.editData;
		String contractId = info.getContract().getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", contractId));
		//filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.TRUE));
		
		if (ContractBillFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showError(this,"合同已经结算，不能继续增加违约金单据！");
			SysUtil.abort();
		}
		
		super.actionAddNew_actionPerformed(e);
		comboCurrency.setEnabled(false);
	}
	
	public void loadFields() {
		super.loadFields();
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.menuBiz.setVisible(false);
		}
		CompensationBillInfo info = (CompensationBillInfo) this.editData;
		if(info.getContract()!=null){
			this.txtContractName.setText(info.getContract().getName());
			this.txtContractNumber.setText(info.getContract().getNumber());
		}
		this.txtContractName.setEnabled(false);
		this.txtContractNumber.setEnabled(false);
		this.txtNumber.setText(info.getNumber());
		this.txtBreachFaichDes.setText(info.getBreachFaichDes());
		this.txtMoneyDes.setText(info.getMoneyDes());
		this.txtCompensationAccording.setText(info.getCompensationAccording());
		this.txtOtherDes.setText(info.getOtherDes());
		this.txtExRate.setValue(info.getExRate());
		this.txtAmount.setValue(info.getAmount());
		this.txtAmount.setEnabled(false);
		this.txtOriginalAmount.setValue(info.getOriginalAmount());
		this.chkFiVouchered.setSelected(info.isFiVouchered());
		
		if (info.getCreator() != null) {
			this.txtCreator.setText(info.getCreator().getName());
		}
		this.txtCreateDate.setText(formatDay.format(new Date(info
				.getCreateTime().getTime())));
		FDCClientHelper.setSelectObject(this.comboCurrency, info.getCurrency());
//		FDCClientHelper.setSelectObject(this.comboCompensationType, info.getCompensationType());
		comboCompensationType.setValue( info.getCompensationType());
		this.txtName.setText(info.getName());
		if(info.getAuditor()!=null){
			this.txtAuditor.setText(info.getAuditor().getName());
		}
		if(info.getAuditTime()!=null){
			this.txtAuditDate.setText(formatDay.format(new Date(info
				.getAuditTime().getTime())));
		}
		setSaveActionStatus();
	    if(STATUS_EDIT.equals(getOprtState()) && !this.actionAudit.isVisible()
	    		&& !this.actionUnAudit.isVisible()){
	    	this.menuBiz.setVisible(false);
	    }
		//2009-1-21 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("orgUnit.id");
		selectors.add("contract.*");
		selectors.add("contract.curProject.*");
		selectors.add("currency.name");
		selectors.add("creator.name");
		selectors.add("auditor.name");
		selectors.add("compensationType.number");
		selectors.add("compensationType.name");
		return selectors;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		this.txtContractName.setEditable(false);
		this.txtContractNumber.setEditable(false);
		this.txtCreator.setEnabled(false);
		this.txtCreateDate.setEnabled(false);
		this.comboCurrency.setEnabled(false);
		this.txtAmount.setEnabled(false);
		this.menuBiz.setVisible(false);
	}
	 /**
	 * by Cassiel_peng
	 */
	 public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }          
        CompensationBillPrintProvider data = new CompensationBillPrintProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null){
			MsgBox.showWarning(this,FDCClientUtils.getRes("cantPrint"));
			return;
        }
        CompensationBillPrintProvider data = new CompensationBillPrintProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	//获得扣款单套打对应的目录
	protected String getTDFileName() {
    	return "/bim/fdc/contract/CompensationBill";
	}
	//对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.CompensationBillForPrintQuery");
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		//2009-1-21 更改判断方式  校验时金额有可能为空
		BigDecimal amount = FDCHelper.ZERO;
		if(this.txtAmount.getBigDecimalValue() != null){
			amount = txtAmount.getBigDecimalValue();
		}
		if (amount.compareTo(FDCHelper.ZERO) == 0) {
			this.txtOriginalAmount.requestFocus(true);
			MsgBox.showInfo(getResource("AmountNoNull"));
			
			SysUtil.abort();
		}
//		if (this.comboCompensationType.getSelectedItem() == null) {
//			comboCompensationType.requestFocus(true);
//			MsgBox.showInfo(getResource("CompensationTypeNoNull"));
//			
//			SysUtil.abort();
//		}
	}

	public static String getResource(String resourceName) {
		return EASResource.getString(resourcePath, resourceName);
	}
	public void onShow() throws Exception {
		super.onShow();
		//this.txtAmount.requestFocus();
		this.comboCompensationType.requestFocus();
		actionPrint.setVisible(true);
		actionPrint.setEnabled(true);
		btnPrint.setVisible(true);
		btnPrint.setEnabled(true);
		menuItemPrint.setVisible(true);
		menuItemPrint.setVisible(true);
		actionPrintPreview.setVisible(true);
		actionPrintPreview.setEnabled(true);
		btnPrintPreview.setVisible(true);
		btnPrintPreview.setEnabled(true);
		menuItemPrintPreview.setVisible(true);
		menuItemPrintPreview.setVisible(true);
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		//this.txtAmount.requestFocus();
		this.comboCompensationType.requestFocus();
	}
	
	/**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    
    public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {
    	super.checkBeforeAuditOrUnAudit(state, warning);
    	
    	if(state != null && state == FDCBillStateEnum.AUDITTED) {
    		String id = getSelectBOID();
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("isCompensated");
    		boolean isIsCompensated = false;
    		try {
				CompensationBillInfo compInfo = CompensationBillFactory.getRemoteInstance().getCompensationBillInfo(new ObjectStringPK(id), selector);
				
				isIsCompensated = compInfo.isIsCompensated();
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			} 
    		
			if(isIsCompensated) {
				MsgBox.showWarning(this, "该违约单已经被关联，不能反审批");
			}
    	}
    }

	protected void attachListeners() {
		// TODO 自动生成方法存根
		
	}

	protected void detachListeners() {
		// TODO 自动生成方法存根
		
	}
	
    protected boolean isUseMainMenuAsTitle(){
    	return false;
    }

	protected void txtOriginalAmount_dataChanged(DataChangeEvent e) throws Exception {
		// TODO 自动生成方法存根
		calLocalAmt();
	}

	/*
	 *  在FDCBillEdit内将其改成抽象方法,以强制要求子类去实现
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	protected void txtExRate_dataChanged(DataChangeEvent e) throws Exception {
		// TODO 自动生成方法存根
		calLocalAmt();
	}
}