/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.util.ParamManager;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractBailEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillReviseEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillReviseEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillReviseInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractReviseBailEntryCollection;
import com.kingdee.eas.fdc.contract.ContractReviseBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractReviseBailInfo;
import com.kingdee.eas.fdc.contract.ContractRevisePayItemCollection;
import com.kingdee.eas.fdc.contract.ContractRevisePayItemInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * 合同修订 编辑界面
 */
public class ContractBillReviseEditUI extends AbstractContractBillReviseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractBillReviseEditUI.class);
    
    private boolean isCanModifyContractNumber = false;
    
    private boolean isFirstLoad = false;
    
	//责任人是否可以选择其他部门的人员
	private boolean canSelectOtherOrgPerson = false;

	/**
	 * 是否进入动态成本
	 */
	private boolean isCoseSplit = false;
	/**
	 * 当前光标激活的Table
	 */
	private KDTable table=tblEconItem;
	
    /**
     * output class constructor
     */
    public ContractBillReviseEditUI() throws Exception
    {
        super();
    	jbInit() ;
	}

	private void jbInit() {	    
		titleMain = getUITitle();
	}

    private static final String DETAIL_DEF_ID = "detailDef.id";


	ItemListener contractPropertItemlistener = null;
	
    protected void controlDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,ControlDateChangeListener listener) throws Exception
    {
    	if("bookedDate".equals(listener.getShortCut())){
    		bookedDate_dataChanged(e);
    	}
    }
    
    //业务日期变化事件
    ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener("bookedDate");
    
    protected void attachListeners() {   	
    	pkbookedDate.addDataChangeListener(bookedDateChangeListener);
    }
    
    protected void detachListeners() {
    	pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
    }
	
	/** 是否使用不计成本的金额, 是否单据计算 = 是, 此变量值为 fasle, 是否单据计算 = 否, 此变量值 = true, 
	 * 如果详细信息没有是否单独计算, 则此变量值为 false
	 */
	protected boolean isUseAmtWithoutCost = false;
	
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		
		//loadFields 最好注销监听器,以免loadFields触发事件
		isFirstLoad = true;
		detachListeners();
		
		super.loadFields();
		
		isUseAmtWithoutCost = editData.isIsAmtWithoutCost();		
		loadDetailEntries();

		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
		
		txtExRate.setValue(editData.getExRate());
		//BT246767:合同修订后原合同数据出现错误，保存后此处重新加载了合同的旧数据，导致审批时把旧的本位币写入合同
		//txtLocalAmount.setValue(editData.getLocalAmount());
		
		if(editData != null && editData.getCurProject() != null) {
			
			String projId = editData.getCurProject().getId().toString();
			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);
			
			txtProj.setText(curProjectInfo.getDisplayName());
			
			FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(projId);
			
			txtOrg.setText(costOrg.getDisplayName());
			editData.setOrgUnit(costOrg);
			editData.setCU(curProjectInfo.getCU());
			
//		    if(editData.isIsAmtWithoutCost()){
//		    	txtamount.setValue(null);
//		    }		    
		}		
		// 加载履约保证金及返还的内容
		loadBailEntries();
	    attachListeners() ;
	    this.comboCoopLevel.setSelectedItem(editData.getCoopLevel());
	    //附件查看
		fillAttachmentList();
	    
	    isFirstLoad = false;
	    
	    detailTableAutoFitRowHeight(tblEconItem);
		detailTableAutoFitRowHeight(tblBail);
		detailTableAutoFitRowHeight(tblDetail);
	    
	}
	
	public void actionViewAttachment_actionPerformed(ActionEvent e) throws Exception {
		String attachId = null;
		if (this.cmbAttachment.getSelectedItem() != null && this.cmbAttachment.getSelectedItem() instanceof AttachmentInfo) {
			attachId = ((AttachmentInfo) this.cmbAttachment.getSelectedItem()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			acm.viewAttachment(attachId);
		}
	}

	/**
	 * 填充“附件列表” 控件ComboBox，boID用当前正在编辑的业务单据的ID
	 */
	public void fillAttachmentList() {
		boolean isHasAttachment = false;
		this.cmbAttachment.removeAllItems();

		if (this.editData.getId() != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", editData.getId().toString()));

			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			}
			if (cols != null && cols.size() > 0) {
				for (Iterator it = cols.iterator(); it.hasNext();) {
					AttachmentInfo attachment = ((BoAttchAssoInfo) it.next()).getAttachment();
					this.cmbAttachment.addItem(attachment);
				}
				isHasAttachment = true;
			} else {
				isHasAttachment = false;
			}
		}
		this.btnViewAttachment.setEnabled(isHasAttachment);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		editData.setSignDate(DateTimeUtils.truncateDate(editData.getSignDate()));
		editData.setIsAmtWithoutCost(isUseAmtWithoutCost);
		super.storeFields();
		storeDetailEntries();
		storeBailEntries();
		
	}

	/**
	 * 
	 */
	private void storeBailEntries() {
		// 保存履约保证金及返还部分
		ContractReviseBailInfo contractReviseBailInfo = this.editData.getBail();
		if (contractReviseBailInfo == null) {
			contractReviseBailInfo = new ContractReviseBailInfo();
			contractReviseBailInfo.setId(BOSUuid.create(contractReviseBailInfo.getBOSType()));
			this.editData.setBail(contractReviseBailInfo);
		}
		contractReviseBailInfo.setAmount(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(), 2));
		contractReviseBailInfo.setProp(FDCHelper.toBigDecimal(txtBailRate.getBigDecimalValue(), 2));
		ContractReviseBailEntryCollection coll = contractReviseBailInfo.getEntry();
		coll.clear();
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			IRow row = tblBail.getRow(i);
			ContractReviseBailEntryInfo bailEntryInfo = (ContractReviseBailEntryInfo) row.getUserObject();
			if (bailEntryInfo == null) {
				continue;
			}
			bailEntryInfo.setParent(contractReviseBailInfo);
			// 此处最好还是处理一下有分录行但其实用户并没有真正输入值的情况
			if (row.getCell("bailAmount").getValue() != null) {
				bailEntryInfo.setAmount(FDCHelper.toBigDecimal(row.getCell("bailAmount").getValue(), 2));
			}
			if (row.getCell("bailRate").getValue() != null) {
				bailEntryInfo.setProp(FDCHelper.toBigDecimal(row.getCell("bailRate").getValue(), 2));
			}
			if (row.getCell("bailDate").getValue() != null) {
				bailEntryInfo.setBailDate(row.getCell("bailDate").getValue() == null ? DateTimeUtils.truncateDate(new Date()) : (Date) row.getCell(
						"bailDate").getValue());
			}
			bailEntryInfo.setBailConditon((String) row.getCell("bailCondition").getValue());
			bailEntryInfo.setDesc((String) row.getCell("desc").getValue());
			coll.add(bailEntryInfo);
		}
	}

	
	
	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractBillReviseFactory
				.getRemoteInstance();
	}



	/**
	 * 
	 * 描述：合同详细信息Table
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-26
	 *               <p>
	 */
	private KDTable getDetailInfoTable() {
		return tblDetail;
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return new ContractBillReviseEntryInfo();
	}

	
	
	
	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractBillReviseInfo objectValue = new ContractBillReviseInfo();
		
		String contractBillId = (String)getUIContext().get("contractBillId");
		
		ContractBillInfo contractBillInfo = null;
		try {
			contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillId), new ContractBillEditUI().getSelectors());			
		} catch (Exception e1) {
			handUIExceptionAndAbort(e1);
		}
		
		ContractBillReviseEntryCollection reCol = new ContractBillReviseEntryCollection();
		ContractBillEntryCollection entrys = contractBillInfo.getEntrys();
		ContractBillReviseEntryInfo entryInfo = null;
		if(entrys != null && entrys.size() > 0) {
			objectValue.getEntrys().clear();
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillEntryInfo element = (ContractBillEntryInfo) iter.next();
				entryInfo = new ContractBillReviseEntryInfo();
				entryInfo.putAll(element);
				entryInfo.setId(null);
				entryInfo.setParent(null);
				reCol.add(entryInfo);
			}
		}
		
		contractBillInfo.put("entrys", null);
		
		// 处理付款事项 add by zhiyuan_tang 2010/01/007
		ContractRevisePayItemCollection rePayCol = new ContractRevisePayItemCollection();
		ContractPayItemCollection payCol = contractBillInfo.getPayItems();
		ContractRevisePayItemInfo rePayInfo = null;
		if(payCol != null && payCol.size() > 0) {
			objectValue.getPayItems().clear();
			for (Iterator iter = payCol.iterator(); iter.hasNext();) {
				ContractPayItemInfo element = (ContractPayItemInfo) iter.next();
				rePayInfo = new ContractRevisePayItemInfo();
				rePayInfo.putAll(element);
				rePayInfo.setId(null);
				rePayInfo.setContractbillRevise(objectValue);
				rePayCol.add(rePayInfo);
			}
		}
		contractBillInfo.put("payItems", null);
		
		
		//处理履约保证金及返还部分
		ContractReviseBailInfo reBailInfo = null;
		if (contractBillInfo.getBail() != null) {
			reBailInfo = new ContractReviseBailInfo();
			reBailInfo.setProp(contractBillInfo.getBail().getProp());
			reBailInfo.setAmount(contractBillInfo.getBail().getAmount());
			//处理分录
			ContractBailEntryCollection bailEntryCol = contractBillInfo.getBail().getEntry();
			ContractReviseBailEntryInfo reBailEntryInfo = null;
			if(bailEntryCol != null && bailEntryCol.size() > 0) {
				for (Iterator iter = bailEntryCol.iterator(); iter.hasNext();) {
					ContractBailEntryInfo element = (ContractBailEntryInfo) iter.next();
					reBailEntryInfo = new ContractReviseBailEntryInfo();
					reBailEntryInfo.putAll(element);
					reBailEntryInfo.setId(null);
					reBailEntryInfo.setParent(null);
					reBailInfo.getEntry().add(reBailEntryInfo);
				}
			}
			contractBillInfo.setBail(null);
		}
		
		objectValue.putAll(contractBillInfo);
		
		objectValue.put("entrys", reCol);
		objectValue.put("payItems", rePayCol);
		objectValue.setBail(reBailInfo);
		objectValue.setId(null);
		objectValue.setLastUpdateTime(null);
		objectValue.setLastUpdateUser(null);
		objectValue.setState(FDCBillStateEnum.SAVED);
		objectValue.setPeriod(contractBillInfo.getPeriod());
		objectValue.setBookedDate(contractBillInfo.getBookedDate());

		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//		objectValue.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		objectValue.setContractBill(contractBillInfo);
		
		objectValue.setIsPartAMaterialCon(contractBillInfo.isIsPartAMaterialCon());
		
		//修订后原币和本币默认带出合同的原币和本币
		//objectValue.setRevAmount(contractBillInfo.getOriginalAmount());
		//objectValue.setRevLocalAmount(contractBillInfo.getAmount());
		
		
		/*
		 * 修改点： 1、合同修订编辑界面，将“原原币金额”改为“原签约金额原币”，取合同录入编辑界面“签约金额原币”，不可修改；
		 * 将“原本币金额”改为“原签约金额本币”，取合同录入编辑界面“签约金额本币”，不可修改；
		 * 将“修订后原币金额”改为“修订后签约金额原币”，默认等于“原签约金额原币”，可修改；
		 * “修订后本币金额”改为“修订后签约金额本币”，“修订后签约金额原币”修改后，该字段值需要重新计算。
		 * 2、合同修改审批通过后，反写合同录入编辑界面，合同录入编辑界面“签约金额原币”更新为合同修订编辑界面的“修订后签约金额原币”；
		 * “签约金额本币”自动计算（签约金额原币汇率）；
		 * 原币金额更新为“修订后签约金额原币”+该合同对应的非单独计算的补充合同金额；“本币金额”自动计算。
		 * 3、合同修订序时薄界面中合同修订列表中“原原币金额”改为“原签约金额原币”，
		 * “原本币金额”改为“原签约金额本币，“修订后原币金额”改为“修订后签约金额原币”，“修订后本位币金额”改为“修订后签约金额本币”。
		 */
		objectValue.setOriginalAmount(contractBillInfo.getCeremonyb());
		objectValue.setAmount(contractBillInfo.getCeremonybb());
		objectValue.setRevAmount(contractBillInfo.getCeremonyb());
		objectValue.setRevLocalAmount(contractBillInfo.getCeremonybb());
		isCoseSplit = contractBillInfo.isIsCoseSplit();
		objectValue.setIsCoseSplit(isCoseSplit);
		return objectValue;
	}

	private void setInviteCtrlVisibleByContractSource(
			ContractSourceInfo contractSource) {
		if(contractSource==null)
			return;
		String sourceId = contractSource.getId().toString();
		if (ContractSourceInfo.TRUST_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
		} else if (ContractSourceInfo.INVITE_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(true);
			// 二标价
			contsecondPrice.setVisible(false);
			// 底价
			contbasePrice.setVisible(false);

			//备注
			contRemark.setVisible(false);
			//			战略合作级别
			contCoopLevel.setVisible(false);
			// 计价方式
			contPriceType.setVisible(false);
			
			chkIsSubMainContract.setVisible(false);
			conMainContract.setVisible(false);
			conEffectiveEndDate.setVisible(false);
			conEffectiveStartDate.setVisible(false);
			conInformation.setVisible(false);
			kDScrollPane2.setVisible(false);
			txtInformation.setVisible(false);

		} else if (ContractSourceInfo.DISCUSS_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			// 中标价
			contwinPrice.setVisible(true);
			// 中标单位
			contwinUnit.setVisible(true);
			// 二标价
			contsecondPrice.setVisible(true);
			// 底价
			contbasePrice.setVisible(true);
			
			//备注
			contRemark.setVisible(true);

		}else if (ContractSourceInfo.COOP_VALUE.equals(sourceId)) {
			setInviteCtrlVisible(false);
			//战略合作级别
			contCoopLevel.setVisible(true);
			// 计价方式
//			contPriceType.setVisible(true);
			chkIsSubMainContract.setVisible(true);
			conEffectiveEndDate.setVisible(true);
			conEffectiveStartDate.setVisible(true);
			conMainContract.setVisible(true);
			conInformation.setVisible(true);
			kDScrollPane2.setVisible(true);
			txtInformation.setVisible(true);

		}
		else{
			setInviteCtrlVisible(false);
		}

	}

	private void setInviteCtrlVisible(boolean visible) {
		Component[] components = pnlInviteInfo.getComponents();
		for (int i = 0; i < components.length; i++) {
			components[i].setVisible(visible);
		}
	}

	/**
	 * 
	 * 描述：合同性质变化，合同详细信息的字段变化
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#contractPropert_itemStateChanged(java.awt.event.ItemEvent)
	 */
	protected void contractPropert_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getStateChange() == ItemEvent.SELECTED) {
			ContractPropertyEnum contractProp = (ContractPropertyEnum) e
					.getItem();
			contractPropertChanged(contractProp);

		}

		super.contractPropert_itemStateChanged(e);

	}

	private void contractPropertChanged(ContractPropertyEnum contractProp) {
		if (contractProp != null) {
			
		
		fillDetailByPropert(contractProp);

		if (contractProp == ContractPropertyEnum.THREE_PARTY) {
			prmtpartC.setRequired(true);
		} else {
			prmtpartC.setRequired(false);
		}

//		txtamount.setRequired(true);
		
		}
	}

	/*
	 * 表列名
	 */
	private static final String DETAIL_COL = ContractClientUtils.CON_DETAIL_DETAIL_COL;

	private static final String CONTENT_COL = ContractClientUtils.CON_DETIAL_CONTENT_COL;

	private static final String ROWKEY_COL = ContractClientUtils.CON_DETIAL_ROWKEY_COL;

	private static final String DESC_COL = ContractClientUtils.CON_DETIAL_DESC_COL;

	private static final String DATATYPE_COL = ContractClientUtils.CON_DETIAL_DATATYPE_COL;

	/*
	 * 行标识
	 */
	private static final String NA_ROW = ContractClientUtils.MAIN_CONTRACT_NAME_ROW;

	private static final String NU_ROW = ContractClientUtils.MAIN_CONTRACT_NUMBER_ROW;

	private static final String AM_ROW = ContractClientUtils.AMOUNT_WITHOUT_COST_ROW;

	private static final String LO_ROW = ContractClientUtils.IS_LONELY_CAL_ROW;

	class MyDataChangeListener implements DataChangeListener {

		public void dataChanged(DataChangeEvent eventObj) {
			String name = null;
			if (eventObj.getNewValue() != null) {
				ContractBillInfo info = (ContractBillInfo) eventObj
						.getNewValue();

				name = info.getName();
				comboCurrency.setEnabled(false);
				for (int i = 0; i < comboCurrency.getItemCount(); i++) {
					Object o = comboCurrency.getItemAt(i);
					if (o instanceof CurrencyInfo) {
						CurrencyInfo c = (CurrencyInfo) o;
						if (c.getId().equals(info.getCurrency().getId())) {
							comboCurrency.setSelectedIndex(i);
						}
					}
				}
				comboCurrency.setEditable(false);
				txtExRate.setValue(info.getExRate());
				txtExRate.setEditable(false);
			} else {
				name = null;
			}
			
			getDetailInfoTable().getCell(getRowIndexByRowKey(NA_ROW),
					CONTENT_COL).setValue(name);
		}

	}

	/**
	 * 
	 * 描述：根据行标识获取行Index
	 * 
	 * @param rowKey
	 * @return
	 * @author:liupd 创建时间：2006-7-27
	 *               <p>
	 */
	private int getRowIndexByRowKey(String rowKey) {
		int rowIndex = 0;

		int rowCount = getDetailInfoTable().getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String key = (String) getDetailInfoTable().getCell(i, ROWKEY_COL)
					.getValue();
			if (key != null && key.equals(rowKey)) {
				rowIndex = i;
				break;
			}
		}

		return rowIndex;
	}

	// 是否刚刚显示四条附加记录
	boolean lastDispAddRows = false;

	JButton btnAdd1;
	JButton btnRemove1;
	JButton btnAdd2;
	JButton btnRemove2;
	
	
	/**
	 * 
	 * 描述：如果合同性质为三方合同、补充合同，则合同详细信息要多显示4个字段
	 * 
	 * @param contractProp
	 * @author:liupd 创建时间：2006-7-26
	 *               <p>
	 */
	private void fillDetailByPropert(ContractPropertyEnum contractProp) {

		if (contractProp == ContractPropertyEnum.THREE_PARTY
				|| contractProp == ContractPropertyEnum.SUPPLY) {

			if (lastDispAddRows) {
				
				ICell cell = getDetailInfoTable().getRow(getRowIndexByRowKey(NU_ROW)).getCell(CONTENT_COL);
				if(contractProp == ContractPropertyEnum.SUPPLY) {
					cell.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
					if(cell.getEditor()!=null&&cell.getEditor().getComponent() instanceof KDBizPromptBox){
						KDBizPromptBox box=(KDBizPromptBox)cell.getEditor().getComponent();
						box.setRequired(true);
					}
				}else{
					cell.getStyleAttributes().setBackground(Color.WHITE);
					if(cell.getEditor()!=null&&cell.getEditor().getComponent() instanceof KDBizPromptBox){
						KDBizPromptBox box=(KDBizPromptBox)cell.getEditor().getComponent();
						box.setRequired(false);
					}
				}
				
				return;
			}

			/*
			 * 内容
			 */
			String isLonelyCal = ContractClientUtils.getRes("isLonelyCal");
			String amountWithOutCost = ContractClientUtils
					.getRes("amountWithOutCost");
			String mainContractNumber = ContractClientUtils
					.getRes("mainContractNumber");
			String mainContractName = ContractClientUtils
					.getRes("mainContractName");

			/*
			 * 是否单独计算
			 */
			IRow isLonelyCalRow = getDetailInfoTable().addRow();
			isLonelyCalRow.getCell(ROWKEY_COL).setValue(LO_ROW);
			isLonelyCalRow.getCell(DETAIL_COL).setValue(isLonelyCal);
			KDComboBox isLonelyCalCombo = getBooleanCombo();
			KDTDefaultCellEditor isLonelyCalComboEditor = new KDTDefaultCellEditor(
					isLonelyCalCombo);
			isLonelyCalRow.getCell(CONTENT_COL).setEditor(
					isLonelyCalComboEditor);
			isLonelyCalRow.getCell(CONTENT_COL).setValue(BooleanEnum.TRUE); // 默认是

			isLonelyCalCombo.addItemListener(new IsLonelyChangeListener());

			isLonelyCalRow.getCell(DATATYPE_COL).setValue(DataTypeEnum.BOOL);

			/*
			 * 不计成本的金额
			 */
			IRow amountWithOutCostRow = getDetailInfoTable().addRow();
			amountWithOutCostRow.getCell(ROWKEY_COL).setValue(AM_ROW);
			amountWithOutCostRow.getCell(DETAIL_COL)
					.setValue(amountWithOutCost);

			
			amountWithOutCostRow.getCell(CONTENT_COL).setEditor(FDCClientHelper.getNumberCellEditor()
					);
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			// 如果为是，则“不计成本的金额”不允许录入
			amountWithOutCostRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			amountWithOutCostRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.NUMBER);

			/*
			 * 对应主合同编码
			 */
			IRow mainContractNumberRow = getDetailInfoTable().addRow();
			mainContractNumberRow.getCell(ROWKEY_COL).setValue(NU_ROW);
			mainContractNumberRow.getCell(DETAIL_COL).setValue(
					mainContractNumber);
			KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
			KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(
					kDBizPromptBoxContract);
			mainContractNumberRow.getCell(CONTENT_COL).setEditor(
					prmtContractEditor);
			ObjectValueRender objectValueRender = getF7Render();
			mainContractNumberRow.getCell(CONTENT_COL).setRenderer(
					objectValueRender);

			MyDataChangeListener lis = new MyDataChangeListener();
			kDBizPromptBoxContract.addDataChangeListener(lis);

			mainContractNumberRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
//			if(contractProp == ContractPropertyEnum.SUPPLY) {
//				mainContractNumberRow.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//			}
			
			ICell cell = mainContractNumberRow.getCell(CONTENT_COL);
			if(contractProp == ContractPropertyEnum.SUPPLY) {
				cell.getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				kDBizPromptBoxContract.setRequired(true);
			}else{
				cell.getStyleAttributes().setBackground(Color.WHITE);
				kDBizPromptBoxContract.setRequired(false);
			}
			
			/*
			 * 对应主合同名称
			 */
			IRow mainContractNameRow = getDetailInfoTable().addRow();
			mainContractNameRow.getCell(ROWKEY_COL).setValue(NA_ROW);
			mainContractNameRow.getCell(DETAIL_COL).setValue(mainContractName);
			mainContractNameRow.getCell(CONTENT_COL).getStyleAttributes()
					.setLocked(true);

			mainContractNameRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);

			lastDispAddRows = true;
		} else {
			if (lastDispAddRows) {
				isLonelyChanged(BooleanEnum.TRUE);
				getDetailInfoTable().removeRow(getRowIndexByRowKey(LO_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(AM_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NU_ROW));
				getDetailInfoTable().removeRow(getRowIndexByRowKey(NA_ROW));
			}

			lastDispAddRows = false;
		}
		
		isUseAmtWithoutCost = false;
	}

	private ObjectValueRender getF7Render() {
		ObjectValueRender objectValueRender = new ObjectValueRender();
		BizDataFormat format = new BizDataFormat("$number$");
		objectValueRender.setFormat(format);
		return objectValueRender;
	}

	private KDBizPromptBox getContractF7Editor() {
		KDBizPromptBox kDBizPromptBoxContract = new KDBizPromptBox();
		kDBizPromptBoxContract
				.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7SimpleQuery");
		kDBizPromptBoxContract.setDisplayFormat("$number$");
		kDBizPromptBoxContract.setEditFormat("$number$");
		kDBizPromptBoxContract.setCommitFormat("$number$");
		kDBizPromptBoxContract.setEditable(true);
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
//		filterItems.add(new FilterItemInfo("contractPropert", ContractPropertyEnum.DIRECT_VALUE));
		//		2009-01-13 yx 支持补充合同的主合同为三方合同(财富联合 R081201-122)
		HashSet set = new HashSet();
		set.add(ContractPropertyEnum.DIRECT_VALUE);
		set.add(ContractPropertyEnum.THREE_PARTY_VALUE);
		filterItems.add(new FilterItemInfo("contractPropert", set,
				CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("isAmtWithoutCost", Boolean.FALSE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		//filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.CANCEL_VALUE, CompareType.NOTEQUALS));
		//1.4.3.2.2 只能是已经审批的合同
		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		//R110412-116：修订合同性质（‘直接合同’修订为‘补充合同’且为‘非独立结算’）时，不可以选择到已结算合同
		filterItems.add(new FilterItemInfo("hasSettled", Boolean.FALSE));
		
		String projId = null;
		if(editData.getCurProject() != null && editData.getCurProject().getId() != null) {
			projId = editData.getCurProject().getId().toString();
		}
		else if(getUIContext().get("projectId") != null) {
			projId = ((BOSUuid)getUIContext().get("projectId")).toString();
		}
		if(projId != null) {
			filterItems.add(new FilterItemInfo("curProject.id", projId.toString()));
		}
		if(editData.getContractBill().getId() != null) {
			filterItems.add(new FilterItemInfo("id", editData.getContractBill().getId().toString(), CompareType.NOTEQUALS));
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		kDBizPromptBoxContract.setEntityViewInfo(view);
		return kDBizPromptBoxContract;
	}

	private KDComboBox getBooleanCombo() {
		KDComboBox isLonelyCalCombo = new KDComboBox();
		isLonelyCalCombo.addItems(BooleanEnum.getEnumList().toArray());
		return isLonelyCalCombo;
	}

	class IsLonelyChangeListener implements ItemListener {

		/*
		 * ? 是否单独计算：布尔型，下拉列表，枚举值：是、否。必录项，缺省为是。不可编辑。
		 * 如果为是，则“不计成本的金额”不允许录入，在“合同金额”录入。 如果为否，则“不计成本的金额”需要录入，“合同金额”不允许录入。
		 * 如果在“合同金额”里录入了数据，“是否单独计算”又改为否，则系统自动将“合同金额”里的数据写到“不计成本的金额”里；反之也一样。
		 */
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				BooleanEnum b = (BooleanEnum) e.getItem();
				isLonelyChanged(b);
			}

		}

		

	}
	
	/**
	 * 是否单独计算发生改变
	 * @param b
	 */
	private void isLonelyChanged(BooleanEnum b) {
		ICell cell = getDetailInfoTable().getRow(
				getRowIndexByRowKey(AM_ROW)).getCell(CONTENT_COL);
		
		if (b == BooleanEnum.TRUE) {
			cell.getStyleAttributes().setLocked(true);
			txtamount.setEditable(true);
			if (cell.getValue() != null
					&& cell.getValue() instanceof Number) {
				txtamount.setNumberValue((Number) cell.getValue());
				calLocalAmt();
				cell.setValue(null);
			}
			setAmountRequired(true);
			
			isUseAmtWithoutCost = false;
			
		} else {
			cell.getStyleAttributes().setLocked(false);
			txtamount.setEditable(false);
			if (txtamount.getNumberValue() != null) {
				cell.setValue(txtamount.getNumberValue());
			}
			txtamount.setValue(null);
			txtLocalAmount.setValue(null);
			txtStampTaxAmt.setValue(null);
			
			setAmountRequired(false);
			
			isUseAmtWithoutCost = true;
			calStampTaxAmt();
			calGrtAmt();
		}
	}

	private void setAmountRequired(boolean required) {
		txtamount.setRequired(required);
		txtLocalAmount.setRequired(required);
	}
	/**
     * 描述：合同形成方式变化，招标/议标信息控件变化
     */
    protected void contractSource_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	Object newValue = e.getNewValue();
    	setInviteCtrlVisibleByContractSource((ContractSourceInfo)newValue);
    	super.contractSource_dataChanged(e);
    }

    /**
     * output contractSource_willShow method
     */
    protected void contractSource_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

	public void onLoad() throws Exception {

		
		//金额靠右
		txtamount.setHorizontalAlignment(JTextField.RIGHT);
		// modified by zhaoqin for R130905-0190 on 2013/09/12 start
		// 当 “修订后签约金额原币” 改变后，修改 “补充协议金额”的值
		txtamount.addDataChangeListener(new DataChangeListener() {
			public void dataChanged(DataChangeEvent eventObj) {
				txtAmount_dataChanged(eventObj);
			}
		});
		// modified by zhaoqin for R130905-0190 on 2013/09/12 end
		
		txtLocalAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setHorizontalAlignment(JTextField.RIGHT);
		
		txtExRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtExRate.setPrecision(2);
		
		tblDetail.checkParsed();
		
		actionContractPlan.setVisible(false);
		actionContractPlan.setEnabled(false);
//		init currency
		FDCClientHelper.initCurrency(comboCurrency);
		
		txtcontractName.setMaxLength(200);////此句代码必须在super之前,否则在加载保存好的数据的时候只会截取前80个字符显示 by Cassiel_peng
		handleEconTab();	// modified by zhaoqin for R130916-0218 on 2013/9/26
		super.onLoad();
//		this.txtNumber.setEnabled(false);
		setMaxMinValueForCtrl();
		initEcoEntryTableStyle();
		sortPanel();
		setInviteCtrlVisible(false);
		EntityViewInfo viewContractSource = new EntityViewInfo();
		FilterInfo filterSource = new FilterInfo();
		filterSource.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		viewContractSource.setFilter(filterSource);
		this.contractSource.setEntityViewInfo(viewContractSource);
		updateButtonStatus();
	
		
		// 初始化合同类型F7
		prmtcontractType.setSelector(new ContractTypePromptSelector(this));
		prmtcontractType.setEntityViewInfo(getViewForContractTypeF7());
		prmtcontractType.setEditable(true);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		HashSet set=new HashSet();
		set.add(OrgConstants.SYS_CU_ID);
		String cuId = editData.getCU().getId().toString();
		//
		set.add(cuId);
		filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));

		view.setFilter(filter);
		prmtlandDeveloper.setEntityViewInfo(view);
		
		//责任部门F7 ,通过参数控制选择范围
		FDCClientUtils.setRespDeptF7(prmtRespDept, this, canSelectOtherOrgPerson?null:cuId);
		//责任人F7
		FDCClientUtils.setPersonF7(prmtRespPerson, this, canSelectOtherOrgPerson?null:cuId);	
				
		//合同修订增加附件功能      by Cassiel_peng
		this.btnAttachment.setVisible(true);
		this.btnAttachment.setEnabled(true);
		actionAttachment.setVisible(true);
		actionAttachment.setEnabled(true);
		
		
		actionAddLine.setEnabled(false);
		actionAddLine.setVisible(false);
		
		actionInsertLine.setEnabled(false);
		actionInsertLine.setVisible(false);
		
		actionRemoveLine.setEnabled(false);
		actionRemoveLine.setVisible(false);

		txtGrtAmount.setEditable(false);
		
		FDCClientHelper.setActionEnable(actionAddNew, false);
		chkMenuItemSubmitAndAddNew.setSelected(false);
		if (editData.getContractSource() != null) {
			setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		}
		
		FDCClientHelper.setSelectObject(comboCurrency, editData.getCurrency());
//		if(STATUS_ADDNEW.equals(getOprtState()) && prmtcontractType.getData() != null) {
//			ContractTypeInfo cType = (ContractTypeInfo)prmtcontractType.getData();
//			removeDetailTableRowsOfContractType();
//			
//			try {
//				fillDetailByContractType(cType.getId().toString());
//			} catch (Exception e) {
//				handUIException(e);
//			}
//		}
		setContractType();
		
		setPrecision();
		
		if(editData != null && (editData.getState()==FDCBillStateEnum.CANCEL || editData.isIsArchived())){
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionSplit.setEnabled(false);
			btnSplit.setEnabled(false);
		}
		
		txtCreator.setAccessAuthority(0);
	
		
		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, prmtpartB, cuId);
		FDCClientUtils.initSupplierF7(this, prmtpartC, cuId);
		FDCClientUtils.initSupplierF7(this, prmtwinUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowestPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowerPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtmiddlePriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthigherPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthighestPriceUni, cuId);

		// modified by zhaoqin on 2013/9/30 start
		this.isCoseSplit = editData.isIsCoseSplit();
		this.chkCostSplit.setSelected(isCoseSplit);
		// modified by zhaoqin on 2013/9/30 end
		handleOldData();
		if(!isCanModifyContractNumber){
			txtNumber.setEditable(false);
			txtcontractName.setEditable(false);
		}
//		
		txtOldAmount.setEditable(false);
		txtCreator.setEditable(false);
		txtExRate.setEditable(false);
		
		FDCClientHelper.setActionEnable(actionAudit, false);
		FDCClientHelper.setActionEnable(actionUnAudit, false);
//		FDCClientHelper.setActionEnable(actionAttachment, false);嘿嘿，铜陵建材要求在合同修订增加"附件管理"功能
//		FDCClientHelper.setActionEnable(actionWorkFlowG, false);
		FDCClientHelper.setActionEnable(actionViewContent, false);
		FDCClientHelper.setActionEnable(actionCalculator, false);
		
		FDCClientHelper.setActionEnable(actionAddNew, false);
		FDCClientHelper.setActionEnable(actionRemove, false);
		FDCClientHelper.setActionEnable(actionCopy, false);
		FDCClientHelper.setActionEnable(actionEdit, false);
		
		FDCClientHelper.setActionEnable(actionFirst, false);
		FDCClientHelper.setActionEnable(actionLast, false);
		FDCClientHelper.setActionEnable(actionPre, false);
		FDCClientHelper.setActionEnable(actionNext, false);
		
		menuEdit.setVisible(false);
		menuBiz.setVisible(false);
		menuWorkflow.setVisible(true);
		
		comboCurrency.setEnabled(false);
		
		//查看状态下，别给人看到修订后原币金额
//		if(isUseAmtWithoutCost)txtamount.setValue(null);
		
		//业务日志判断为空时取期间中断
		if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
			pkbookedDate.setSupportedEmpty(false);
		}
		setInviteCtrlVisibleByContractSource(editData.getContractSourceId());
		//2009-1-12 修订时，暂控制不允许修改“是否进入动态成本”等，避免出现由进入动态成本修订为不进入动态成本很多逻辑问题 
		this.chkCostSplit.setEnabled(false);
		this.chkIsPartAMaterialCon.setEnabled(false);
		//不允许修改啦
		this.contcontractPropert.setEnabled(false);
		
		//在修订合同时，能够修改合同类型。 by Cassiel_peng
		this.prmtcontractType.setEnabled(true);

		this.chkIsSubMainContract.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(chkIsSubMainContract.isSelected()){
					prmtMainContract.setEnabled(true);
					prmtMainContract.setRequired(true);
				}else{
					prmtMainContract.setEnabled(false);
					prmtMainContract.setDataNoNotify(null);
				}
			}});
	    this.prmtMainContract.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				// TODO Auto-generated method stub
				if(eventObj.getNewValue() != null){
					ContractBillInfo info = (ContractBillInfo) eventObj.getNewValue();
					if(info.getEffectiveEndDate() != null)
					kdpEffectiveEndDate.setValue(info.getEffectiveEndDate());
					if(info.getEffectiveStartDate() != null )
					kdpEffectiveStartDate.setValue(info.getEffectiveStartDate());
					if(info.getCoopLevel() != null){
						comboCoopLevel.setSelectedItem(info.getCoopLevel());
					}
					Date now = null;
					try {
						 now = FDCCommonServerHelper.getServerTime();
					} catch (BOSException e) {
						handUIExceptionAndAbort(e);
					} 
					if(now != null && info.getEffectiveEndDate() != null && info.getEffectiveStartDate() != null){
						if(now.after(info.getEffectiveEndDate()) || now.before(info.getEffectiveStartDate())){
							int n = FDCMsgBox.showConfirm2New(null, "当前战略合同已过期，继续使用该合同吗？");
							if(JOptionPane.NO_OPTION == n){
								prmtMainContract.setDataNoNotify(null);
								abort();
							}
						}
					}
					
				}
			}});
	    
	    this.prmtMainContract.addSelectorListener(new SelectorListener(){

			public void willShow(SelectorEvent e) {
				EntityViewInfo view = null;
				
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isSubContract",Boolean.FALSE));
				filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED.getValue()));
				filter.getFilterItems().add(new FilterItemInfo("contractSourceId.id",ContractSourceInfo.COOP_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("curProject.id",editData.getCurProject().getId().toString()));
				if(prmtMainContract.getQueryAgent().getEntityViewInfo() != null){
					view = prmtMainContract.getQueryAgent().getEntityViewInfo();
					view.setFilter(filter);
				}else{
					view = new EntityViewInfo();
					view.setFilter(filter);
				}
				prmtMainContract.setEntityViewInfo(view);
				prmtMainContract.getQueryAgent().resetRuntimeEntityView();
				
			}
	    	
	    });
	    
	    this.tblDetail.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblDetail_editStopping(e);
                } catch(Exception exc) {
                	handUIExceptionAndAbort(exc);
                }
            }
        });
	    
	    handConDetailDef();
	    
	    // Add by zhiyuan_tang 增加“新增分录”，“删除分录”按钮和方法
		//handleEconTab();	// modified by zhaoqin for R130916-0218 on 2013/9/26 start
		//	    
		// if (getOprtState() == STATUS_EDIT) {
		// EcoItemHelper.setPayItemRowBackColorWhenInit(this.tblEconItem);
		// EcoItemHelper.setBailRowBackColorWhenInit(this.tblBail,
		// txtBailOriAmount, txtBailRate);
		// }
		this.btnPrint.setVisible(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		this.menuItemPrint.setVisible(true);
		this.menuItemPrintPreview.setVisible(true);
		this.btnPrint.setVisible(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.menuItemPrint.setVisible(true);
		this.menuItemPrintPreview.setVisible(true);
		//this.isCoseSplit = editData.isIsCoseSplit();	// modified by zhaoqin on 2013/9/30
		this.chkCostSplit.setSelected(isCoseSplit);
	}
	
	/**
	 * 处理合同经济条款的控件初始化
	 * @author zhiyuan_tang
	 */
	private void handleEconTab() {
		// modified by zhaoqin for R130916-0218 on 2013/9/25 start
		// this.kDContainer1.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contPayItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		this.contBailItem.setContainerType(KDContainer.DISEXTENDSIBLE_STYLE);
		/*
		JButton btnAdd = this.kDContainer1.add(this.actionAddLine);
		JButton btnRemove = this.kDContainer1.add(this.actionRemoveLine);
		this.kDContainer1.setVisibleOfExpandButton(true);
		this.actionAddLine.setVisible(true);
		this.actionAddLine.setEnabled(true);
		this.actionRemoveLine.setVisible(true);
		this.actionRemoveLine.setEnabled(true);
		btnAdd.setRequestFocusEnabled(false);
		btnRemove.setRequestFocusEnabled(false);
		btnAdd.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemove.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		*/
		
		btnAdd1 = contPayItem.add(new ItemAction() {
			public void actionPerformed(ActionEvent arg0) {
				addLine(tblEconItem);
				appendFootRow(tblEconItem);
				EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			}
		});
		btnRemove1 = contPayItem.add(new ItemAction() {
			public void actionPerformed(ActionEvent arg0) {
				tblEconItem.checkParsed();
				int index = -1;
				index = tblEconItem.getSelectManager().getActiveRowIndex();
				if (index != -1) {
					tblEconItem.removeRow(index);
				}
			}
		});

		this.contPayItem.setVisibleOfExpandButton(true);
		btnAdd1.setText("新增分录");
		btnRemove1.setText("删除分录");
		btnAdd1.setRequestFocusEnabled(false);
		btnRemove1.setRequestFocusEnabled(false);
		btnAdd1.setEnabled(false);
		btnRemove1.setEnabled(false);
		btnAdd1.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemove1.setIcon(EASResource.getIcon("imgTbtn_deleteline"));

		btnAdd2 = contBailItem.add(new ItemAction() {
			public void actionPerformed(ActionEvent arg0) {
				addLine(tblBail);
				appendFootRow(tblBail);
				EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount, txtBailRate);
			}

		});
		btnRemove2 = contBailItem.add(new ItemAction() {
			public void actionPerformed(ActionEvent arg0) {
				tblBail.checkParsed();
				int index = -1;
				index = tblBail.getSelectManager().getActiveRowIndex();

				if (index != -1) {
					tblBail.removeRow(index);
					if (tblBail.getRowCount() <= 0) {
						txtBailOriAmount.setRequired(false);
						txtBailRate.setRequired(false);
					}
				}
			}
		});
		btnAdd2.setText("新增分录");
		btnRemove2.setText("删除分录");
		this.contBailItem.setVisibleOfExpandButton(true);
		btnAdd2.setRequestFocusEnabled(false);
		btnRemove2.setRequestFocusEnabled(false);
		btnAdd2.setEnabled(false);
		btnRemove2.setEnabled(false);
		btnAdd2.setIcon(EASResource.getIcon("imgTbtn_addline"));
		btnRemove2.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		// modified by zhaoqin for R130916-0218 on 2013/9/25 end
	}
	
	// added by zhaoqin for R130916-0218 on 2013/9/26
	protected void initDataStatus() {
		super.initDataStatus();
		if (!STATUS_EDIT.equals(getOprtState()) && !STATUS_ADDNEW.equals(getOprtState())) {
			btnAdd1.setEnabled(false);
			btnRemove1.setEnabled(false);
			btnAdd2.setEnabled(false);
			btnRemove2.setEnabled(false);
		} else {
			btnAdd1.setEnabled(true);
			btnRemove1.setEnabled(true);
			btnAdd2.setEnabled(true);
			btnRemove2.setEnabled(true);
		}
	}
	
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....在工作流中是不能新增或者是删除分录的，故。。。
		}
		else{
			if (this.tblEconItem.isFocusOwner()) {
				table=this.tblEconItem;
			} 
			if (this.tblBail.isFocusOwner()) {
				table=this.tblBail;
			}
			addLine(getDetailTable());
			// appendFootRow(getDetailTable());
			// EcoItemHelper.setPayItemRowBackColor(tblEconItem);
			// EcoItemHelper.setBailRowBackColor(tblBail, txtBailOriAmount,
			// txtBailRate);
		}
	}
	
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		if(STATUS_VIEW.equals(getOprtState())){
			//.....do nothing.....
		}else if((getOprtState()==STATUS_VIEW||getOprtState()==STATUS_FINDVIEW)&&this.editData.getId()!=null&&FDCUtils.isRunningWorkflow(this.editData.getId().toString())){
			//.....do nothing.....因为在工作流审批时需要将"查看正文"的所有按钮功能都放开(在有权限的前提下)故将界面操作状态设置为了STATUS.EDIT..但在工作路中是不能新增或者是删除分录的，故。。。
		}else{
			if (this.tblEconItem.isFocusOwner()) {
				this.tblEconItem.checkParsed();
				int index = -1;
				index = this.tblEconItem.getSelectManager().getActiveRowIndex();
				if (index != -1) {
					tblEconItem.removeRow(index);
				} else {
			
				}
			} else if (this.tblBail.isFocusOwner()) {
				this.tblBail.checkParsed();
				int index = -1;
				index = this.tblBail.getSelectManager().getActiveRowIndex();

				if (index != -1) {
					tblBail.removeRow(index);
					if(this.tblBail.getRowCount()<=0){
						this.txtBailOriAmount.setRequired(false);
						this.txtBailRate.setRequired(false);
					}
				} else {
					
				}
			}
		}
	}
	
	//业务日期变化引起,期间的变化
    protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	String projectId = this.editData.getCurProject().getId().toString();    	
    	fetchPeriod(e,pkbookedDate,cbPeriod, projectId,  true);
    }
    

	private void setMaxMinValueForCtrl() {
		FDCClientHelper.setValueScopeForPercentCtrl(txtchgPercForWarn);
		FDCClientHelper.setValueScopeForPercentCtrl(txtpayPercForWarn);
		
		FDCClientHelper.setValueScopeForPercentCtrl(txtPayScale);
		FDCClientHelper.setValueScopeForPercentCtrl(txtStampTaxRate);
		FDCClientHelper.setValueScopeForPercentCtrl(txtGrtRate);
				
		this.txtamount.setMinimumValue(FDCHelper.MIN_VALUE);
		this.txtamount.setMaximumValue(FDCHelper.MAX_VALUE);
		this.txtLocalAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		this.txtLocalAmount.setMinimumValue(FDCHelper.MIN_TOTAL_VALUE2);
		txtExRate.setMaximumValue(FDCHelper.ONE_THOUSAND);
		txtExRate.setMinimumValue(FDCHelper.ZERO);
		
		txtNumber.setMaxLength(80);
//    	txtcontractName.setMaxLength(80);
    	txtcontractName.setMaxLength(200);
    	txtReviseReason.setMaxLength(500);
    	KDTextField tblDetail_desc_TextField = new KDTextField();
        tblDetail_desc_TextField.setName("tblDetail_desc_TextField");
        tblDetail_desc_TextField.setMaxLength(80);
        KDTDefaultCellEditor tblDetail_desc_CellEditor = new KDTDefaultCellEditor(tblDetail_desc_TextField);
        tblDetail.getColumn("desc").setEditor(tblDetail_desc_CellEditor);
	}

	/**
	 * 
	 * 描述：合同类型F7的过滤条件
	 * 
	 * @return
	 * @author:liupd 创建时间：2006-8-16
	 *               <p>
	 */
	protected EntityViewInfo getViewForContractTypeF7() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);

		return view;
	}

	/**
	 * 
	 * 描述：保证Tab的顺序
	 * 
	 * @author:liupd 创建时间：2006-7-19
	 *               <p>
	 */
	private void sortPanel() {

		kDTabbedPane1.removeAll();
		// kDTabbedPane1.add(com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(dataBinder,tblDetail,new
		// com.kingdee.eas.fdc.contract.ContractBillReviseEntryInfo(),null,false),
		// ContractClientUtils.getRes("tblDetailConstraints"));
		kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
		kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
        
//		kDTabbedPane1.add(tblDetail, ContractClientUtils
//				.getRes("tblDetailConstraints"));
//		kDTabbedPane1.add(pnlInviteInfo, resHelper
//				.getString("pnlInviteInfo.constraints"));
		// kDTabbedPane1.add(com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(dataBinder,tblContractPlan,new
		// com.kingdee.eas.fdc.contract.ContractBillContractPlanInfo(),null,false),
		// ContractClientUtils.getRes("tblContractPlanConstraints"));
//		kDTabbedPane1.add(tblContractPlan, ContractClientUtils
//				.getRes("tblContractPlanConstraints"));
	}
    /**
     * output prmtcontractType_willShow method
     */
    protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	if(STATUS_EDIT.equals(getOprtState()) || STATUS_ADDNEW.equals(getOprtState())){
    		FDCMsgBox.showWarning(this, FDCClientUtils.getRes("changeContractType"));
    	}
    }
	protected void prmtcontractType_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		contractTypeChanged(newValue);

		super.prmtcontractType_dataChanged(e);
	}

	private void contractTypeChanged(Object newValue) throws Exception, BOSException, EASBizException, CodingRuleException {
		if(newValue == null) {
			removeDetailTableRowsOfContractType();
			return;
		}

		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			ContractTypeInfo info = (ContractTypeInfo) newValue;
			if (!info.isIsLeaf()) {
				FDCMsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
				prmtcontractType.setData(null);
				prmtcontractType.requestFocus();
				SysUtil.abort();
			}

			removeDetailTableRowsOfContractType();
			fillDetailByContractType(info.getId().toString());
			chkCostSplit.setSelected(info.isIsCost());
			
			fillInfoByContractType(info);
			if(this.editData.getContractType()!=info){
				this.editData.setContractType(info);
//				this.handleCodingRule();
			}
		}else{
//			没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
			if(STATUS_EDIT.equals(getOprtState())){
				if ((this.editData.getState().equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))
						){
//					prmtcontractType.setEnabled(false);
					actionSplit.setEnabled(true);
				}else{
					ContractTypeInfo info = (ContractTypeInfo) newValue;
					if (!info.isIsLeaf()) {
						FDCMsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
						prmtcontractType.setData(null);
						prmtcontractType.requestFocus();
						SysUtil.abort();
					}
					removeDetailTableRowsOfContractType();
					fillDetailByContractType(info.getId().toString());
					chkCostSplit.setSelected(info.isIsCost());					
					fillInfoByContractType(info);
				}
			}
		}
	}

	/**
	 * 根据合同类型填充带过来的数据
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void fillInfoByContractType(ContractTypeInfo info) throws BOSException, EASBizException {
		if(info.getDutyOrgUnit() != null) {
			BOSUuid id = info.getDutyOrgUnit().getId();
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("isLeaf");
			CoreBaseInfo value = AdminOrgUnitFactory.getRemoteInstance().getValue(new ObjectUuidPK(id), selectors);
			prmtRespDept.setValue(value);
		}
		else {
			prmtRespDept.setValue(null);
		}
		
		txtPayScale.setNumberValue(info.getPayScale());
		
		txtStampTaxRate.setNumberValue(info.getStampTaxRate());
	}

	private void removeDetailTableRowsOfContractType() {

		for (int i = 0; i < getDetailInfoTable().getRowCount(); i++) {
			String rowKey = (String) getDetailInfoTable()
					.getCell(i, ROWKEY_COL).getValue();
			if (rowKey == null || rowKey.length() == 0) {
				getDetailInfoTable().removeRow(i);
				i--;
			}
		}
	}

	
	protected  void fetchInitData() throws Exception{
		super.fetchInitData();
		
		try {
			HashMap param = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
			if(param.get(FDCConstants.FDC_PARAM_SELECTPERSON)!=null){
				canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
			}
			if(param.get(FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER)!=null){
				isCanModifyContractNumber = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CANMODIFYCONTRACTNUMBER).toString()).booleanValue();
			}
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	}
	
	//根据合同类型填充详细信息
	private void fillDetailByContractType(String id) throws Exception {		
	
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", id));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		ContractDetailDefCollection contractDetailDefCollection = ContractDetailDefFactory
				.getRemoteInstance().getContractDetailDefCollection(view);
		
		/**
		 * 其他自定义的合同类型的时候，系统没有自动显示备注
		 */
		if(!ContractClientUtils.isContractTypePre(id)) {
			boolean hasReMark = false;
			String remark = ContractClientUtils.getRes("remark");
			//如果自定义类型已经有备注,也不显示备注
			for (Iterator iter = contractDetailDefCollection.iterator(); iter.hasNext();) {				
				ContractDetailDefInfo element = (ContractDetailDefInfo) iter.next();
				if(remark.equals(element.getName())){
					hasReMark = true ;
				}				
			}
			
			if(!hasReMark){
				IRow row = getDetailInfoTable().addRow(0);
				row.getCell(DETAIL_COL).setValue(ContractClientUtils.getRes("remark"));
				row.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
			}
		}
		
		for (Iterator iter = contractDetailDefCollection.iterator(); iter.hasNext();) {
			ContractDetailDefInfo element = (ContractDetailDefInfo) iter.next();
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(element.getName());
			KDTDefaultCellEditor editor = getEditorByDataType(element
					.getDataTypeEnum());
			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			}
			if(element.getDataTypeEnum() == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).setValue(FDCHelper.getCurrentDate());
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
			}
			else if(element.getDataTypeEnum() == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			row.getCell(DATATYPE_COL).setValue(element.getDataTypeEnum());
			row.getCell(DETAIL_DEF_ID).setValue(element.getId().toString());
			
			// 如果合同详细定义为必录项，则背景色要显示为必录色  added by owen_wen 2010-09-15
			if (element.isIsMustInput()){
				this.setRequiredBGColor(row);
			}
		}
	}
	private void fillConDetails(KDTDefaultCellEditor editorString, ContractDetailDefInfo detail) {
		IRow appendRow = this.tblDetail.addRow();
		appendRow.getCell(DETAIL_COL).setValue(detail.getName());
		KDTDefaultCellEditor _editor = getEditorByDataType(detail
				.getDataTypeEnum());
		if (_editor != null) {
			appendRow.getCell(CONTENT_COL).setEditor(_editor);
		}
		if (detail.getDataTypeEnum() == DataTypeEnum.DATE) {
			appendRow.getCell(CONTENT_COL).setValue(
					FDCHelper.getCurrentDate());
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setNumberFormat("%r{yyyy-M-d}t");
		} else if (detail.getDataTypeEnum() == DataTypeEnum.NUMBER) {
			appendRow.getCell(CONTENT_COL).getStyleAttributes()
					.setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		appendRow.getCell(DATATYPE_COL).setValue(
				detail.getDataTypeEnum());
		appendRow.getCell(DETAIL_DEF_ID).setValue(
				detail.getId().toString());

		appendRow.getCell(DESC_COL).setEditor(editorString);

		// 如果合同详细定义为必录项，则背景色要显示为必录色 added by owen_wen 2010-09-10
		if (detail.isIsMustInput()) {
			this.setRequiredBGColor(appendRow);
		}
	}
	private ContractDetailDefCollection getConDetailsDef(String conTypeId) {
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("id"));
		selector.add(new SelectorItemInfo("dataTypeEnum"));
		selector.add(new SelectorItemInfo("isMustInput"));
		selector.add(new SelectorItemInfo("name"));
		selector.add(new SelectorItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id",conTypeId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		view.setSelector(selector);
		ContractDetailDefCollection detailColls = null;
		try {
			detailColls = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefCollection(view);
		} catch (BOSException e2) {
			handUIExceptionAndAbort(e2);
		}
		return detailColls;
	}
	public KDTDefaultCellEditor getEditorByDataType(DataTypeEnum dataType) {
		if (dataType == DataTypeEnum.DATE) {
			KDDatePicker datePicker = new KDDatePicker();
			return new KDTDefaultCellEditor(datePicker);
		} else if (dataType == DataTypeEnum.BOOL) {
			KDComboBox booleanCombo = getBooleanCombo();
			return new KDTDefaultCellEditor(booleanCombo);
		}
		else if(dataType == DataTypeEnum.NUMBER) {
			return FDCClientHelper.getNumberCellEditor();
		}else if(dataType == DataTypeEnum.STRING) {
			
			KDTextArea indexValue_TextField = new KDTextArea();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setMaxLength(1000);
			indexValue_TextField.setColumns(10);
			indexValue_TextField.setWrapStyleWord(true);
			indexValue_TextField.setLineWrap(true);
			indexValue_TextField.setAutoscrolls(true);
			
			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			return indexValue_CellEditor;
		}
		return null;
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(oprtType)) {
			prmtcontractType.setEnabled(true);
			actionSplit.setEnabled(false);
		} else {
			if(this.editData!=null){
				setContractType();
			}else{
//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			}			
		}
		if (STATUS_VIEW.equals(oprtType)) {
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnInsertLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
			tblDetail.getStyleAttributes().setLocked(true);
		}
		
		// modified by zhaoqin for R130916-0218 on 2013/9/26 start
		actionAddLine.setVisible(false);
		actionRemoveLine.setVisible(false);
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		// modified by zhaoqin for R130916-0218 on 2013/9/26 end
		actionViewContent.setEnabled(true);
		
		if(STATUS_FINDVIEW.equals(oprtType) && getUIContext().get("source") == null) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
		}
		else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
		
		if(oprtType.equals(STATUS_ADDNEW)) {
			actionContractPlan.setEnabled(false);
		}
		else {
			actionContractPlan.setEnabled(true);
		}
		
		txtGrtAmount.setEditable(false);

	}
	/*
	 * 20070315 jack 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
	 */
    private void setContractType(){		
		if(STATUS_EDIT.equals(getOprtState())){
			if (this.editData.getState() != null
					&& (this.editData.getState().equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))
					){
//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			}else{
				prmtcontractType.setEnabled(true);
				actionSplit.setEnabled(false);
			}
		}
    }
	

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractType.isLeaf"));
		sic.add(new SelectorItemInfo("contractType.level"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.name"));
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractBill.id"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));
		sic.add(new SelectorItemInfo("codeType.*"));
		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));
		
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("isCostSplit"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("mainContract.*"));
		
		sic.add("curProject.id");
		sic.add("curProject.displayName");
		sic.add("curProject.CU.number");
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		
		sic.add(new SelectorItemInfo("isPartAMaterialCon"));
		sic.add(new SelectorItemInfo("payItems.*"));
		sic.add(new SelectorItemInfo("bail"));
		sic.add(new SelectorItemInfo("bail.entry"));
		
		return sic;
	}

	

	/**
	 * 
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	/**
	 * 显示单据行
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
//		dataBinder.loadLineFields(table, row, obj);
	}

	private void storeDetailEntries() {
		ContractBillReviseEntryInfo entryInfo = null;
		int count = getDetailInfoTable().getRowCount();
		ContractBillReviseInfo billInfo = new ContractBillReviseInfo();
		billInfo.setId(editData.getId());

		for (int i = 0; i < editData.getEntrys().size(); i++) {
			editData.getEntrys().removeObject(i);
			i--;
		}
		for (int i = 0; i < count; i++) {
			entryInfo = new ContractBillReviseEntryInfo();

			entryInfo.setParent(billInfo);
			String detail = (String) getDetailInfoTable()
					.getCell(i, DETAIL_COL).getValue();
			DataTypeEnum dataType = (DataTypeEnum) getDetailInfoTable()
			.getCell(i, DATATYPE_COL).getValue();
			Object contentValue = getDetailInfoTable().getCell(i, CONTENT_COL)
					.getValue();
			String content = null;
			
			if(contentValue != null) {
				if(contentValue instanceof CoreBaseInfo) {
					content = ((CoreBaseInfo)contentValue).getId().toString();
					
				}
				else if (contentValue instanceof Date) {
					Date date = (Date) contentValue;
					content = DateFormat.getDateInstance().format(date);
					
				}
				else {
					content = contentValue.toString();
				}
			}
			
			String rowKey = (String) getDetailInfoTable()
					.getCell(i, ROWKEY_COL).getValue();
			String desc = (String) getDetailInfoTable().getCell(i, DESC_COL)
					.getValue();
			String detailDefId = (String) getDetailInfoTable().getCell(i, DETAIL_DEF_ID)
			.getValue();
			
			entryInfo.setDetail(detail);
			
			entryInfo.setContent(content);
			entryInfo.setRowKey(rowKey);
			entryInfo.setDesc(desc);
			entryInfo.setDataType(dataType);
			entryInfo.setDetailDefID(detailDefId);
			editData.getEntrys().add(entryInfo);
			
			/*
			 * 如果有主合同编码,且不是直接合同,设置单据头上的主合同编码
			 */
			if(rowKey != null && rowKey.equals(NU_ROW) && editData.getContractPropert() != ContractPropertyEnum.DIRECT) {
				if(contentValue != null && contentValue instanceof CoreBillBaseInfo) {
					String number = ((CoreBillBaseInfo)contentValue).getNumber();
					editData.setMainContractNumber(number);
				}
			}
			
			
			if(isUseAmtWithoutCost&&rowKey!=null&&rowKey.equals(AM_ROW)){
				//R110506-0418：非单独结算的补充合同不计成本金额取数口径不一致  By zhiyuan_tang
//				editData.setAmount(FDCHelper.toBigDecimal(content));
				editData.setRevAmount(FDCHelper.toBigDecimal(content));
				editData.setRevLocalAmount(FDCHelper.multiply(FDCHelper.toBigDecimal(content), txtExRate.getBigDecimalValue()));
			}
		}
	}

	protected void loadDetailEntries() {
//		if(STATUS_ADDNEW.equals(getOprtState())) return;
		getDetailInfoTable().removeRows(false);
		ContractBillReviseEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}
		
		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		String conTypeId = this.editData.getContractType().getId().toString();
		ContractDetailDefCollection detailColls = getConDetailsDef(conTypeId);
		int size = coll.size();
		ContractBillReviseEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillReviseEntryInfo) coll.get(i);
			IRow row = getDetailInfoTable().addRow();
			row.getCell(DETAIL_COL).setValue(entryInfo.getDetail());

			DataTypeEnum dataType = entryInfo.getDataType();
			if(dataType == DataTypeEnum.DATE) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r{yyyy-M-d}t");
			}
			else if(dataType == DataTypeEnum.NUMBER) {
				row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat("%r-[ ]0.2n");
				row.getCell(CONTENT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			}
			
			//详细信息定义ID可能为空， 此处需添加判断。 modify by owen_wen at 2010-10-07
			if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
				ContractDetailDefInfo cddi = null;
				try {
					cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(
							new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
				} catch (Exception e1) {
					logger.warn(e1.getCause(), e1);
					handUIExceptionAndAbort(e1);
				} 
				if (cddi != null && cddi.isIsMustInput()) {
					setRequiredBGColor(row);
				}
			}
			
			KDTDefaultCellEditor editor = getEditorByDataType(dataType);

			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			} else if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NU_ROW)) {
				KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
				KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(
						kDBizPromptBoxContract);
				row.getCell(CONTENT_COL).setEditor(prmtContractEditor);
				ObjectValueRender objectValueRender = getF7Render();
				row.getCell(CONTENT_COL).setRenderer(objectValueRender);
				
				//如果是补充合同，主合同编码必录
				if(editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
					row.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
				}
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(LO_ROW)) {
				KDComboBox box = (KDComboBox)row.getCell(CONTENT_COL).getEditor().getComponent();
				IsLonelyChangeListener isLonelyChangeListener = new IsLonelyChangeListener();
				box.addItemListener(isLonelyChangeListener);
				
//				BooleanEnum isL = BooleanEnum.TRUE;
//				
//				if(isUseAmtWithoutCost) isL = BooleanEnum.FALSE;
//
//				isLonelyChanged(isL);
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(NU_ROW)) {
				//初始化“主合同编码”编辑器为KDBizPrompBox
				KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
				KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(
						kDBizPromptBoxContract);
				row.getCell(CONTENT_COL).setEditor(prmtContractEditor);
				ObjectValueRender objectValueRender = getF7Render();
				row.getCell(CONTENT_COL).setRenderer(objectValueRender);
				KDBizPromptBox box = (KDBizPromptBox)row.getCell(CONTENT_COL).getEditor().getComponent();
				box.addDataChangeListener(new MyDataChangeListener());
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(NA_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
				
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(NU_ROW) && entryInfo.getContent() != null && entryInfo.getContent().trim().length() > 0) {
				String id =	entryInfo.getContent();
				try {
					ContractBillInfo contractBillInfo = null;
					if (null != id) {
						// 取得合同对象
						contractBillInfo = getContractBillInfo(id);
					}
					row.getCell(CONTENT_COL).setValue(contractBillInfo);
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
				
			}
			else if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(AM_ROW)) {
				if(entryInfo.getContent() != null && entryInfo.getContent().trim().length() > 0) {
					BigDecimal decm = new BigDecimal(entryInfo.getContent());
					row.getCell(CONTENT_COL).setValue(decm);
				}
				
				if(isUseAmtWithoutCost) {
					row.getCell(CONTENT_COL).getStyleAttributes().setLocked(false);
					txtamount.setEditable(false);
					setAmountRequired(false);
				}
				else {
					row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
					txtamount.setEditable(true);
					setAmountRequired(true);
				}
			}
			else if(dataType == DataTypeEnum.BOOL) {
				BooleanEnum be = BooleanEnum.FALSE;
				if(entryInfo.getContent() != null && entryInfo.getContent().equals(BooleanEnum.TRUE.getAlias()))  {
					be = BooleanEnum.TRUE;
					
				}
				row.getCell(CONTENT_COL).setValue(be);
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
			}
			else if(dataType == DataTypeEnum.DATE && !FDCHelper.isEmpty(entryInfo.getContent())) {
				DateFormat df = DateFormat.getDateInstance();
				
				Date date = null;
				try {
					date = df.parse(entryInfo.getContent());
				} catch (ParseException e) {
					handUIExceptionAndAbort(e);
				}
				row.getCell(CONTENT_COL).setValue(date);
			}
			else {
				row.getCell(CONTENT_COL).setValue(entryInfo.getContent());
			}
			
			
			row.getCell(ROWKEY_COL).setValue(entryInfo.getRowKey());
			row.getCell(DESC_COL).setValue(entryInfo.getDesc());
			row.getCell(DATATYPE_COL).setValue(dataType);
			row.getCell(DETAIL_DEF_ID).setValue(entryInfo.getDetailDefID());
			
			row.getCell(DESC_COL).setEditor(editorString);			
			if(dataType == DataTypeEnum.STRING){
				int height = row.getHeight();
				int lentgh1 = entryInfo.getContent()!=null?entryInfo.getContent().length():0;
				int lentgh2 = entryInfo.getDesc()!=null?entryInfo.getDesc().length():0;
				int heightsize = lentgh1/75>lentgh2/125?lentgh1/75:lentgh2/125;
				
				row.setHeight(height*(1+heightsize));				
			}
			if(STATUS_EDIT.equals(getOprtState())){
				//在保存时候已经有的合同详细信息从 detailColls 中移除，detailColls 中剩余的就是需要重新追加到表格尾部的记录了。
				if(detailColls!=null&&detailColls.size()>0){
					for(int j = 0;j<detailColls.size();j++){
						ContractDetailDefInfo detail = (ContractDetailDefInfo)detailColls.get(j);
						String detailId = detail.getId().toString();
						if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
							if(entryInfo.getDetailDefID().equals(detailId)){
								detailColls.remove(detail);
							}
						}
					}
				}
			}
		}
		//追加到表格尾部
		if (STATUS_EDIT.equals(getOprtState())) {
			if (detailColls != null && detailColls.size() > 0) {
				for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) iter.next();
					fillConDetails(editorString, detail);
				}
			}
		}
	}
	
	/**
	 * 描述：取得合同对象
	 * 
	 * @param id
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-11
	 */
	private ContractBillInfo getContractBillInfo(String id) {
		ContractBillInfo contractBillInfo = null;

		FDCSQLBuilder builder = new FDCSQLBuilder();
		// 注意：字段是FIsCostSplit，而不是FIsCoseSplit
		builder.appendSql(" SELECT FID, FNumber, FName FROM T_CON_ContractBill WHERE FID = ? ");
		builder.addParam(id.toString());

		try {
			IRowSet executeQuery = builder.executeQuery();
			if (executeQuery.next()) {
				contractBillInfo = new ContractBillInfo();

				contractBillInfo.setId(BOSUuid.read(id));
				contractBillInfo.setNumber(executeQuery.getString("FNumber"));
				contractBillInfo.setName(executeQuery.getString("FName"));
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}

		// try {
		// contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id));
		// } catch (EASBizException e) {
		// this.handUIExceptionAndAbort(e);
		// } catch (BOSException e) {
		// this.handUIExceptionAndAbort(e);
		// }

		return contractBillInfo;
	}

	private void loadBailEntries() {
		tblBail.checkParsed();
		if (tblBail.getColumnCount() > 0) {
			
			tblBail.removeRows();
			tblBail.getColumn("bailCondition").getStyleAttributes().setWrapText(true);
			tblBail.getColumn("desc").getStyleAttributes().setWrapText(true);
			if (this.editData.getBail() != null && this.editData.getBail().getEntry() != null) {
				for (int i = 0; i < this.editData.getBail().getEntry().size(); i++) {
					ContractReviseBailEntryInfo entry = this.editData.getBail().getEntry().get(i);
					IRow row = tblBail.addRow();
					row.setUserObject(entry);
					row.getCell("bailAmount").setValue(FDCHelper.toBigDecimal(entry.getAmount(), 2));
					row.getCell("bailDate").setValue(entry.getBailDate());
					row.getCell("bailRate").setValue(entry.getProp());
					row.getCell("bailCondition").setValue(entry.getBailConditon());
					row.getCell("desc").setValue(entry.getDesc());
				}
			}
			
		}
	}
	
	public void onShow() throws Exception {
		super.onShow();
		detailTableAutoFitRowHeight(tblEconItem);
		detailTableAutoFitRowHeight(tblBail);
		detailTableAutoFitRowHeight(tblDetail);
		
		// modified by zhaoqin for R130916-0218 on 2013/9/26 start
		actionAddLine.setVisible(false);
		actionRemoveLine.setVisible(false);
		btnAddLine.setVisible(false);
		btnRemoveLine.setVisible(false);
		// modified by zhaoqin for R130916-0218 on 2013/9/26 end
	}
	
	private static final String PAYDATE_COL = "payDate";

	private static final String PAYAMOUNT_COL = "payAmount";

	protected void beforeStoreFields(ActionEvent e) throws Exception {

		super.beforeStoreFields(e);

//		for (int i = 0; i < getDetailTable().getRowCount(); i++) {
//			Object o1 = getDetailTable().getCell(i, PAYDATE_COL).getValue();
//			Object o2 = getDetailTable().getCell(i, PAYAMOUNT_COL).getValue();
//			if (o1 == null && o2 == null) {
//				getDetailTable().removeRow(i);
//				i--;
//			}
//		}

	}
	
	
	  protected void setFieldsNull(AbstractObjectValue newData) {
	    	super.setFieldsNull(newData);
	    	ContractBillReviseInfo info = (ContractBillReviseInfo)newData;
	    	info.setCurProject(editData.getCurProject());
	    	info.setContractType(editData.getContractType());
	    	info.setIsArchived(false);
	    }
	/*
     * 增加编辑控件的解锁。
     */
    protected void unLockUI()
    {
    	super.unLockUI();
    	getDetailInfoTable().getStyleAttributes().setLocked(false);
		chkCostSplit.setEnabled(true);
		
		/*
		 * 原币金额是否可写由是否使用不计成本的金额来控制
		 */
		int rowIndex = getRowIndexByRowKey(AM_ROW);
		IRow row = getDetailInfoTable().getRow(
						rowIndex);
		if(row != null) {
			ICell cell = row.getCell(CONTENT_COL);
			if(isUseAmtWithoutCost) {
				if(rowIndex > 0) cell.getStyleAttributes().setLocked(false);
				txtamount.setEditable(false);
			}
			else {
				if(rowIndex > 0) cell.getStyleAttributes().setLocked(true);
				txtamount.setEditable(true);
			}
		}
		
		int conNameRowIdx = getRowIndexByRowKey(NA_ROW);
		
		if(conNameRowIdx > 0) {
			getDetailInfoTable().getRow(conNameRowIdx).getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
		}
   }
    protected boolean isShowAttachmentAction() {
    	return false;
    }
    protected void lockUIForViewStatus() {
    	super.lockUIForViewStatus();
    	chkCostSplit.setEnabled(false);
//    	cmbAttachment.setEnabled(true);
    }
    protected void lockContainer(Container container) {
		if ("lblAttachmentContainer".equals(container.getName())) {
			return;
		}
		super.lockContainer(container);
	}
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAddNew_actionPerformed(e);
    	prmtcontractType.setEnabled(true);
//    	this.handleCodingRule();
    }
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
//		没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
		if(STATUS_EDIT.equals(getOprtState())){
			if ((this.editData.getState().equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))
					){
//				prmtcontractType.setEnabled(false);
				actionSplit.setEnabled(true);
			}else{
				prmtcontractType.setEnabled(true);
//				actionSplit.setEnabled(true);
			}
		}
		handConDetailDef();
	}

	private void handConDetailDef() {
		ContractBillReviseEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}

		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();
		String conTypeId = this.editData.getContractType().getId().toString();
		ContractDetailDefCollection detailColls = getConDetailsDef(conTypeId);
		ContractBillReviseEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillReviseEntryInfo) coll.get(i);
			// 在保存时候已经有的合同详细信息从 detailColls 中移除，detailColls
			// 中剩余的就是需要重新追加到表格尾部的记录了。
			if (detailColls != null && detailColls.size() > 0) {
				for (int j = 0; j < detailColls.size(); j++) {
					ContractDetailDefInfo detail = (ContractDetailDefInfo) detailColls
							.get(j);
					String detailId = detail.getId().toString();
					if (!StringUtils.isEmpty(entryInfo.getDetailDefID())) {
						if (entryInfo.getDetailDefID().equals(detailId)) {
							detailColls.remove(detail);
						}
					}
				}
			}
		}
	// 追加到表格尾部
		if (detailColls != null && detailColls.size() > 0) {
			for (Iterator iter = detailColls.iterator(); iter.hasNext();) {
				ContractDetailDefInfo detail = (ContractDetailDefInfo) iter
						.next();
				fillConDetails(editorString, detail);
			}
		}
	}
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSplit_actionPerformed(e);
    	
    	if(getSelectBOID() == null) return;
    	boolean isCostSplit=chkCostSplit.isSelected();
    	//合同拆分		jelon 12/30/2006
        String contrBillID = getSelectBOID();	 
        
        AbstractSplitInvokeStrategy invokeStra = new ContractSplitInvokeStrategy(contrBillID, this);
        invokeStra.invokeSplit();
    }

	public static void showSplitUI(CoreUIObject parentUI, String billId, String billPropName, String bosType, boolean isCostSplit) throws BOSException, UIException {
		String splitBillID=null;
        
        FDCBillInfo billInfo=null;
        CoreBaseCollection coll=null;
        
        String editName=null;
        

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(billPropName, billId));
    	view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("state");
        
        SelectorItemCollection selectors = new SelectorItemCollection();
        selectors.add("id");
        if(isCostSplit){
//        	coll = ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
        	editName=com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
        	coll = ContractCostSplitFactory.getRemoteInstance().getCollection(view);
        }else{
            editName=com.kingdee.eas.fdc.contract.client.ConNoCostSplitEditUI.class.getName();
            coll = ConNoCostSplitFactory.getRemoteInstance().getCollection(view);
        }
    	        
    	boolean isSplited=false;
    	boolean isAudited=false;
    	
    	if(coll.size()>0){
    		billInfo=(FDCBillInfo)coll.get(0);
    		splitBillID=billInfo.getId().toString();
    		isSplited=true;
    		
    		if(billInfo.getState().equals(FDCBillStateEnum.AUDITTED)){
    			isAudited=true;
    		}
    	}

        UIContext uiContext = new UIContext(parentUI);
    	String oprtState;
    	
    	if(isSplited){
            uiContext.put(UIContext.ID, splitBillID);
    		
    		if(isAudited){
        		oprtState=OprtState.VIEW;    			
    		}else{
        		oprtState=OprtState.EDIT;    			
    		}
    	}else{
			uiContext.put("costBillID", billId);
    		oprtState=OprtState.ADDNEW;
    	}

        	
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				editName, uiContext , null , oprtState);
        
		uiWin.show();
	}
    
	protected void comboCurrency_itemStateChanged(ItemEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.comboCurrency_itemStateChanged(e);
		
//		if(e.getStateChange() == ItemEvent.SELECTED) {
//			CurrencyInfo currency = (CurrencyInfo)e.getItem();
//	    	
//	    	CurrencyInfo baseCurrency = FDCClientHelper.getBaseCurrency(SysContext.getSysContext().getCurrentFIUnit());
//	    	
//	    	BigDecimal exRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId());
//	    	txtExRate.setValue(exRate);
//	    	txtamount.setPrecision(FDCClientHelper.getPrecOfCurrency(currency.getId()));
//	    	
//	    	if(baseCurrency != null && baseCurrency.getId().equals(currency.getId())) {
//	    		txtExRate.setEditable(false);
//	    	}
//	    	else {
//	    		txtExRate.setEditable(true);
//	    	}
//	    	
//	    	calLocalAmt();
//		}
	}
    
	//设置精度
	protected void setPrecision(){
		CurrencyInfo currency = editData.getCurrency();	    	
    	Date bookedDate = (Date)editData.getBookedDate();
    	
    	ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),company,bookedDate);
		} catch (Exception e) {			
			txtExRate.setPrecision(2);
			handUIExceptionAndAbort(e);
		} 
    	
    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
    	int exPrecision = curPrecision;
    	
    	if(exchangeRate!=null){
    		exPrecision = exchangeRate.getPrecision();
    	}
    		
    	txtExRate.setPrecision(exPrecision);
    	BigDecimal exRate =  editData.getExRate();    	
		txtExRate.setValue(exRate);
    	txtamount.setPrecision(curPrecision);
    	txtamount.setValue(editData.getRevAmount());
	}
	
    
    protected void txtExRate_dataChanged(DataChangeEvent e) throws Exception {
    	super.txtExRate_dataChanged(e);
    	calLocalAmt();
    }
    
    /**
     * 计算本币金额
     *
     */
    private void calLocalAmt() {
    	if(txtamount.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
    		BigDecimal lAmount = txtamount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue());
    		txtLocalAmount.setNumberValue(lAmount);
    	}
    	else {
    		txtLocalAmount.setNumberValue(null);
    	}
    	
    	calStampTaxAmt();
    	calGrtAmt();
    }
    
    /**
     * 计算印花税金额
     *
     */
    private void calStampTaxAmt() {
    	//使用不计成本的金额
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}

			//计算印花税
			BigDecimal originalAmount = (BigDecimal) newValue;
			BigDecimal stampTaxAmount = FDCHelper.multiply(originalAmount,txtExRate.getBigDecimalValue()).multiply(txtStampTaxRate
					.getBigDecimalValue());
			stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtStampTaxAmt.setNumberValue(stampTaxAmount);

			return;
		}

		//不使用不计成本的金额
		if (txtamount.getBigDecimalValue() != null
				&& txtStampTaxRate.getBigDecimalValue() != null) {
			BigDecimal stampTaxAmount = FDCHelper.multiply(txtamount.getBigDecimalValue(),txtExRate.getBigDecimalValue())
					.multiply(txtStampTaxRate.getBigDecimalValue());
			stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtStampTaxAmt.setNumberValue(stampTaxAmount);
		} else {
			txtStampTaxAmt.setNumberValue(null);
		}
    }
    
    /**
     * 计算保修金额
     *
     */
    private void calGrtAmt() {
    	//使用不计成本的金额,计算保修金额
		if (isUseAmtWithoutCost) {
			int rowcount = tblDetail.getRowCount();
			Object newValue = null;
			for (int i = 0; i < rowcount; i++) {
				IRow entryRow = tblDetail.getRow(i);
				//
				if (AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
					newValue = entryRow.getCell(CONTENT_COL).getValue();
					break;
				}
			}

			//计算保修金额
			BigDecimal originalAmount = (BigDecimal) newValue;
			if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
				originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
			}
			BigDecimal grtAmount = originalAmount.multiply(txtGrtRate
					.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);

			return;
		}

		//不使用不计成本的金额
		if (txtamount.getBigDecimalValue() != null
				&& txtGrtRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {
			BigDecimal grtAmount = txtamount.getBigDecimalValue().multiply(txtExRate.getBigDecimalValue()).multiply(
					txtGrtRate.getBigDecimalValue());
			grtAmount = grtAmount.divide(FDCConstants.B100,
					BigDecimal.ROUND_HALF_UP);
			txtGrtAmount.setNumberValue(grtAmount);
		} else {
			txtGrtAmount.setNumberValue(null);
		}
    }

    protected void txtStampTaxRate_dataChanged(DataChangeEvent e) throws Exception {
    	super.txtStampTaxRate_dataChanged(e);
    	calStampTaxAmt();
    }
    
    protected void txtamount_dataChanged(DataChangeEvent e) throws Exception {
    	super.txtamount_dataChanged(e);
    	calLocalAmt();
    	calStampTaxAmt();
    	calGrtAmt();
    	calPayItemAmt();
		calBailAmt();
    }
    
    protected void txtGrtRate_dataChanged(DataChangeEvent e) throws Exception {
    	
    	super.txtGrtRate_dataChanged(e);
    	calLocalAmt();
    	calGrtAmt();
    	calPayItemAmt();
		calBailAmt();
    }
    
    protected void verifyInputForSubmint() throws Exception {
    	this.verifyInputForContractDetail();
    	super.verifyInputForSubmint();
    	
    	// 校验合同经济条款 by zhiyuan_tang
    	if (tblBail.getRowCount() > 0) {
			FDCClientVerifyHelper.verifyEmpty(this, txtBailOriAmount);
			FDCClientVerifyHelper.verifyEmpty(this, txtBailRate);
		}
    	FDCClientVerifyHelper.verifyEmpty(this, prmtRespDept);	// modified by zhaoqin for R130916-0370 on 2013/9/27
		EcoItemHelper.checkVeforeSubmit(tblEconItem, tblBail);
		checkBailOriAmount();
		
		vefySettleAmount();
    }
    
    /**
     * 校验修订金额是否超过合约规划预留控制金额
     */
    private void vefySettleAmount() {
    	String org = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		 String paramValue;
		try {
			paramValue = ParamManager.getParamValue(null, new ObjectUuidPK(org), "FDC228_ISSTRICTCONTROL");
			//严格控制时校验
			if(editData.getContractBill()!=null && "0".equals(paramValue)){
				
				BOSUuid id = editData.getContractBill().getId();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("programmingContract.controlBalance");
				
				ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id), selector);
				
				if(contractBillInfo.getProgrammingContract()!=null && FDCHelper.compareTo(editData.getRevAmount(), FDCHelper.add(contractBillInfo.getProgrammingContract().getControlBalance(), editData.getAmount()))>0){
					FDCMsgBox.showWarning("合同修订金额不能大于（合约规划的控制余额"+contractBillInfo.getProgrammingContract().getControlBalance().setScale(2)+"+合同原签约金额"+editData.getAmount().setScale(2)+")");
					abort();
				}
			}
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}

	}
    
    protected void verifyInputForSave()throws Exception  {
    	super.verifyInputForSave();
    	FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
    	FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);
    	
    	this.verifyInputForContractDetail();
    	
    	checkProjStatus();
    	
    	if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("合同摘要信息大于系统约定长度(255)！");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("录入战略子合同时，战略主合同编码不能为空！");
				abort();
			}
		}
    }
    
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
    	super.actionViewContent_actionPerformed(e);
    	
    	ContractClientUtils.viewContent(this, getSelectBOID());
    }
    
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
    	super.actionRemove_actionPerformed(e);
    }

    protected void checkBeforeRemove() throws Exception{
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill", editData.getId()));
		view.setFilter(filter);
		view.getSelector().add("id");
		CoreBillBaseCollection coll = ContractCostSplitFactory.getRemoteInstance().getCoreBillBaseCollection(view);
		Iterator iter = coll.iterator(); 
		if(iter.hasNext()) {	
			FDCMsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
    }
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionCopy_actionPerformed(e);
    	chkCostSplit.setEnabled(true);
    	
    }
    
    protected void updateButtonStatus() {
    	super.updateButtonStatus();
    	
//		 如果是虚体成本中心，则不能拆分
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
		
		FDCClientHelper.setActionEnable(actionAttachment, false);
		menuView.setVisible(false);
		
		txtCreator.setEditable(false);
		
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
    	this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		this.btnContractPlan.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
    }
    
    protected void checkRef(String id)  throws Exception {
    	super.checkRef(id);
    	
    	ContractClientUtils.checkContractBillRef(this, id);
    }
 
    /**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {}
	protected void setNumberTextEnabled() {
		
//		if(getNumberCtrl() != null) {
//			CostCenterOrgUnitInfo currentCostUnit = SysContext.getSysContext().getCurrentCostUnit();
//			
//			if(currentCostUnit != null) {
//				boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, currentCostUnit.getId().toString());
//				
//				getNumberCtrl().setEnabled(isAllowModify);
//				getNumberCtrl().setEditable(isAllowModify);
//			}
//		}
	}
	boolean amtWarned = false;
	protected void txtamount_focusGained(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtamount_focusGained(e);
		
		if(!STATUS_VIEW.equals(getOprtState()) && !amtWarned) {
			String contractBillId = (String)getUIContext().get("contractBillId");
			ContractClientUtils.checkSplitedForAmtChange(this, contractBillId);
			amtWarned = true;
		}
	}

	/**
	 * 拆分后，不允许修改“是否成本拆分”字段
	 */
	protected void chkCostSplit_mouseClicked(MouseEvent e) throws Exception {
		super.chkCostSplit_mouseClicked(e);
		
		if(STATUS_EDIT.equals(getOprtState())) {
			
			if(editData.getSplitState() != null && editData.getSplitState() != CostSplitStateEnum.NOSPLIT) {
				
				
				boolean b = chkCostSplit.isSelected();
				
				chkCostSplit.getModel().setSelected(!b);
				
				FDCMsgBox.showWarning(this, ContractClientUtils.getRes("splitedNotChangeIsCSplit"));
				
				SysUtil.abort();
			}
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {

		if(!chkCostSplit.isSelected()){
			FDCMsgBox.showInfo(this, ContractClientUtils.getRes("NotEntryCost"));
		}
		
		super.verifyInput(e);
		if(txtamount.getNumberValue() instanceof BigDecimal){
			
			//不能小于0
			if(((BigDecimal)txtamount.getNumberValue()).signum()<0){
				FDCMsgBox.showError(this, ContractClientUtils.getRes("cantLessZero"));
				SysUtil.abort();
			}
			//大于等于0时，需要判断是否大于实付款：如果小于实付款给出提示“合同修订金额不能小于合同实付款！”
			else {
				checkPrjPriceInContract();
			}			
		}
		
		/*
		 * 补充合同必须录入主合同编码
		 */
		if(editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ContractBillReviseEntryCollection entrys = editData.getEntrys();
			boolean hasMainContNum = false;
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillReviseEntryInfo element = (ContractBillReviseEntryInfo) iter.next();
				String rowKey = element.getRowKey();
				if(rowKey != null && rowKey.equals(NU_ROW) && element.getContent() != null && element.getContent().length() > 0) {
					hasMainContNum = true;
					break;
				}
			}
			
			if(!hasMainContNum) {
				FDCMsgBox.showWarning(this, ContractClientUtils.getRes("mustAddMainNumber"));
				SysUtil.abort();
			}
		}
		
		checkStampMatch();
	
		checkProjStatus();
		
		if(editData.getInformation() != null && editData.getInformation().length() > 255){
			FDCMsgBox.showError("合同摘要信息大于系统约定长度(255)！");
			abort();
		}
		
		if(this.prmtMainContract.isRequired()){
			if(prmtMainContract.getData() == null){
				FDCMsgBox.showError("录入战略子合同时，战略主合同编码不能为空！");
				abort();
			}
		}
	}
	/**
	 * 合同修订后的金额不能小于合同的累积实付款=已关闭的付款申请单对应的付款单合同内工程款合计 + 未关闭的付款申请单合同内工程款合计<p>
	 * 系统之前的逻辑是:合同修订的时候应该控制合同修订后的金额大于付款申请单的累计金额 是在后台做的校验，现放到前台做校验，并且校验逻辑发生变化<p>
	 * by cassiel_peng  2009-12-06<p>
	 */
	private void checkPrjPriceInContract() {
		String contractId=this.editData.getContractBill().getId().toString();//原合同ID
		BigDecimal prjPriceInContract=FDCHelper.toBigDecimal(ContractClientUtils.getPayAmt(contractId),2);
		BigDecimal reviseAmt=FDCHelper.toBigDecimal(this.txtLocalAmount.getBigDecimalValue(),2);
		if(reviseAmt.compareTo(prjPriceInContract) == -1){
			// 提示"合同修订金额不能小于合同实付款！"
			FDCMsgBox.showError(this,ContractClientUtils.getRes("cantLessActPay"));
			SysUtil.abort();
		}
	}

	private void checkProjStatus() {
//		如果项目状态已关闭，则不能选择是否成本拆分 
		if(editData != null && editData.getCurProject() != null) {
			BOSUuid id = editData.getCurProject().getId();
			
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("projectStatus");
			CurProjectInfo curProjectInfo = null;
			try {
				curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(id), selectors);
				
			
			}catch (Exception ex) {
				handUIExceptionAndAbort(ex);
			}
			
			if(curProjectInfo.getProjectStatus() != null) {
				String closedState = ProjectStatusInfo.closeID;
				String projStateId = curProjectInfo.getProjectStatus().getId().toString();
				if(projStateId != null && projStateId.equals(closedState)) {
					if(chkCostSplit.isSelected()) {
						FDCMsgBox.showWarning(this, ContractClientUtils.getRes("prjHasClosed"));
						SysUtil.abort();
					}
				}
			}
		}
	}
	
	private void checkStampMatch() {
		/**
		 * 印花税金额不等于合同金额*印花税率，提示
		 */
		BigDecimal stampTaxAmt = editData.getStampTaxAmt() == null ? FDCHelper.ZERO : editData.getStampTaxAmt();
		BigDecimal amount = editData.getRevAmount() == null ? FDCHelper.ZERO : editData.getRevAmount();
		amount = editData.getExRate() == null ? FDCHelper.ZERO : amount.multiply(editData.getExRate());
		BigDecimal stampTaxRate = editData.getStampTaxRate() == null ? FDCHelper.ZERO : editData.getStampTaxRate();
		if(editData.getStampTaxAmt() != null && editData.getRevAmount() != null && stampTaxAmt.compareTo(amount.multiply(stampTaxRate).divide(FDCConstants.B100, 2, BigDecimal.ROUND_HALF_UP)) != 0) {
			int result = FDCMsgBox.showConfirm2(this, ContractClientUtils.getRes("stampNotMatchAmt"));
			if (result == FDCMsgBox.NO || result == FDCMsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}
	
	public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception {
		super.actionContractPlan_actionPerformed(e);
		String selectBOID = getSelectBOID();
		if(selectBOID == null) {
			FDCMsgBox.showWarning(this, ContractClientUtils.getRes("saveContract"));
			SysUtil.abort();
		}
		ContractPayPlanEditUI.showEditUI(this, selectBOID, getOprtState());
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(isUseAmtWithoutCost){
			final int rowCount = tblDetail.getRowCount();
			Object value = tblDetail.getCell(rowCount-3,2).getValue();
			txtamount.setValue(value);
		}
		if(txtamount.getNumberValue()==null){
			txtamount.setValue(FDCHelper.ZERO);
		}
				
	    super.actionSubmit_actionPerformed(e);
	    if(isUseAmtWithoutCost){
//	    	txtamount.setValue(null);
	    	handleOldData();
	    }
	    this.btnAttachment.setEnabled(true);
	    this.btnAttachment.setVisible(true);
	    this.chkCostSplit.setSelected(isCoseSplit);	// modified by zhaoqin on 2013/9/30
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if(isUseAmtWithoutCost){
			final int rowCount = tblDetail.getRowCount();
			Object value = tblDetail.getCell(rowCount-3,2).getValue();
			txtamount.setValue(value);
		}
		if(txtamount.getNumberValue()==null){
			txtamount.setValue(FDCHelper.ZERO);
		}
	    super.actionSave_actionPerformed(e);
	    if(isUseAmtWithoutCost){
//	    	txtamount.setValue(null);
	    	handleOldData();
	    }
	    
	    this.btnAttachment.setEnabled(true);
	    this.btnAttachment.setVisible(true);
	    this.chkCostSplit.setSelected(isCoseSplit);	// modified by zhaoqin on 2013/9/30
	}
	/*
	 *  在FDCBillEdit内将其改成抽象方法,以强制要求子类去实现
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return table;
	}
	
	/**
	 * 设置表格单元为背景为必录色
	 * @author owen_wen 2010-09-15
	 */
	private void setRequiredBGColor(IRow row){
		row.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
		//建发不要必录 去掉颜色 added by andy_liu 2012-8-29
		//row.getCell(DESC_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	}
	
	/**
	 * 校验 “合同详细信息” 中的必录项：如果必录项为空，给出提示
	 * @author owen_wen 2010-09-10
	 */
	private void verifyInputForContractDetail(){
		ContractBillReviseEntryCollection coll = editData.getEntrys();
		
		for (int i = 0, size = coll.size(); i < size ; i++){
			ContractBillReviseEntryInfo entryInfo = coll.get(i);
			
			ContractDetailDefInfo cddi = null;
			
			// 有可能是补充合同加上的分录，这里不作判断
			if (coll.get(i).getDetailDefID() == null)
				continue;
			
			try {
				cddi = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(new ObjectUuidPK(BOSUuid.read(entryInfo.getDetailDefID())));
			} catch (EASBizException e1) {
				handUIExceptionAndAbort(e1);
			} catch (BOSException e1) {
				handUIExceptionAndAbort(e1);
			} catch (UuidException e1) {
				handUIExceptionAndAbort(e1);
			}
			//合同详细信息，描述不做判空，与合同修改一致。
			if (cddi!=null && cddi.isIsMustInput()){
				//				if (entryInfo.getContent() == null || entryInfo.getDesc() == null){
				//					String info = ContractClientUtils.getRes("conDtlCantEmpty");
				//					String[] args = new String[] {entryInfo.getDetail(), entryInfo.getContent()==null? "内容":"描述"};
				//					FDCMsgBox.showInfo(this,FDCClientHelper.formatMessage(info, args));
				//					SysUtil.abort();
				//					return;
				//				}
				if (entryInfo.getContent() == null) {
					String info = ContractClientUtils.getRes("conDtlCantEmpty");
					String[] args = new String[] { entryInfo.getDetail(), "内容" };
					FDCMsgBox.showInfo(this,FDCClientHelper.formatMessage(info, args));
					SysUtil.abort();
					return;
				}
			}			
		}
	}
	
	protected void txtBailOriAmount_dataChanged(DataChangeEvent e) throws Exception {
		if (isFirstLoad) {
			return;
		}
		EventListener[] listeners = txtBailRate.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			txtBailRate.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		try {
			BigDecimal bailRate = FDCHelper.ZERO;
			BigDecimal bailOriAmount = FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(), 2);
			BigDecimal contractAmount = FDCHelper.toBigDecimal(txtamount.getNumberValue(), 2);
			if (contractAmount.compareTo(FDCHelper.ZERO) != 0) {
				bailRate = bailOriAmount.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if (bailRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					FDCMsgBox.showWarning("履约保证金比例大于100%");
					txtBailOriAmount.setValue(null);
					this.abort();
				}
				this.txtBailRate.setValue(bailRate);
			}
		} finally {
			for (int i = 0; i < listeners.length; i++) {
				txtBailRate.addDataChangeListener((DataChangeListener) listeners[i]);
			}
		}
		// 如果履约保证金发生改变的话，相应的分录也应该发生改变
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if (this.tblBail.getRow(i).getCell("bailRate").getValue() != null && txtamount.getNumberValue() != null) {
				BigDecimal bailAmount = FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(), FDCHelper
						.toBigDecimal(txtBailOriAmount.getBigDecimalValue(), 2)), FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if (this.tblBail.getRow(i).getCell("bailRate").getValue() == null && txtamount.getNumberValue() != null
					&& this.tblBail.getRow(i).getCell("bailAmount").getValue() != null) {
				// 反算比例
				BigDecimal bailRate = FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE),
						FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(), 2));
				if (bailRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					FDCMsgBox.showWarning("返回比例大于100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					this.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
	}

	protected void txtBailRate_dataChanged(DataChangeEvent e) throws Exception {
		if (isFirstLoad) {
			return;
		}
		EventListener[] listeners = txtBailOriAmount.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			txtBailOriAmount.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		try {
			BigDecimal bailOriAmount = FDCHelper.ZERO;
			BigDecimal bailRate = FDCHelper.toBigDecimal(txtBailRate.getNumberValue(), 2);
			BigDecimal contractAmount = FDCHelper.toBigDecimal(txtamount.getNumberValue(), 2);
			bailOriAmount = FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(bailRate, contractAmount), FDCHelper.ONE_HUNDRED));
			this.txtBailOriAmount.setValue(bailOriAmount);

		} finally {
			for (int i = 0; i < listeners.length; i++) {
				txtBailOriAmount.addDataChangeListener((DataChangeListener) listeners[i]);
			}
		}
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if (this.tblBail.getRow(i).getCell("bailRate").getValue() != null && txtamount.getNumberValue() != null) {
				BigDecimal bailAmount = FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailRate").getValue(), FDCHelper
						.toBigDecimal(txtBailOriAmount.getBigDecimalValue(), 2)), FDCHelper.ONE_HUNDRED);
				this.tblBail.getRow(i).getCell("bailAmount").setValue(bailAmount);
			}
			if (this.tblBail.getRow(i).getCell("bailRate").getValue() == null && txtamount.getNumberValue() != null
					&& this.tblBail.getRow(i).getCell("bailAmount").getValue() != null) {
				// 反算比例
				BigDecimal bailRate = FDCHelper.divide(FDCHelper.multiply(this.tblBail.getRow(i).getCell("bailAmount").getValue(), FDCHelper.ONE),
						FDCHelper.toBigDecimal(txtBailOriAmount.getNumberValue(), 2));
				if (bailRate.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					FDCMsgBox.showWarning("返回比例大于100%");
					this.tblBail.getRow(i).getCell("bailRate").setValue(null);
					this.abort();
				}
				this.tblBail.getRow(i).getCell("bailRate").setValue(bailRate);
			}
		}
	}

	/**
	 * 计算返还金额时应该用履约保证金额*返还比例
	 */
	protected void tblBail_editStopped(KDTEditEvent e) throws Exception {

		// BigDecimal
		// contractAmount=FDCHelper.toBigDecimal(this.txtamount.getNumberValue
		// (),2);
		BigDecimal bailOrgAmount = FDCHelper.toBigDecimal(this.txtBailOriAmount.getNumberValue(), 2);

		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn bailAmountCol = this.tblBail.getColumn("bailAmount");
		IColumn bailRateCol = this.tblBail.getColumn("bailRate");
		if (colIndex == bailAmountCol.getColumnIndex()) {
			BigDecimal cellBailRateValue = FDCHelper.ZERO;
			BigDecimal cellBailAmountValue = FDCHelper.toBigDecimal(e.getValue(), 2);
			if (bailOrgAmount.compareTo(FDCHelper.ZERO) != 0) {
				cellBailRateValue = cellBailAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(bailOrgAmount, 2, BigDecimal.ROUND_HALF_UP);
				if (cellBailRateValue.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					FDCMsgBox.showWarning("返还比例大于100%");
					this.tblBail.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblBail.getCell(rowIndex, "bailRate").setValue(cellBailRateValue);
			}
		}
		if (colIndex == bailRateCol.getColumnIndex()) {
			BigDecimal cellBailAmountValue = FDCHelper.ZERO;
			BigDecimal cellBailRateValue = FDCHelper.toBigDecimal(e.getValue(), 2);
			cellBailAmountValue = FDCHelper.divide(FDCHelper.multiply(cellBailRateValue, bailOrgAmount), FDCHelper.ONE_HUNDRED, 2,
					BigDecimal.ROUND_HALF_UP);
			this.tblBail.getCell(rowIndex, "bailAmount").setValue(cellBailAmountValue);
		}
		detailTableAutoFitRowHeight(tblBail);
	}
	
	protected void tblEconItem_editStopped(KDTEditEvent e) throws Exception {

		BigDecimal contractAmount = FDCHelper.toBigDecimal(this.txtamount.getNumberValue(), 2);

		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IColumn payAmountCol = this.tblEconItem.getColumn("payAmount");
		IColumn payRateCol = this.tblEconItem.getColumn("payRate");
		if (colIndex == payAmountCol.getColumnIndex()) {
			BigDecimal cellPayRateValue = FDCHelper.ZERO;
			BigDecimal cellPayAmountValue = FDCHelper.toBigDecimal(e.getValue(), 2);
			if (contractAmount.compareTo(FDCHelper.ZERO) != 0) {// 除数不能为0
				cellPayRateValue = cellPayAmountValue.multiply(FDCHelper.ONE_HUNDRED).divide(contractAmount, 2, BigDecimal.ROUND_HALF_UP);
				if (cellPayRateValue.compareTo(FDCHelper.ONE_HUNDRED) == 1) {
					MsgBox.showWarning("付款比例大于100%");
					this.tblEconItem.getCell(e.getRowIndex(), e.getColIndex()).setValue(null);
					SysUtil.abort();
				}
				this.tblEconItem.getCell(rowIndex, "payRate").setValue(cellPayRateValue);
			}
		}
		if (colIndex == payRateCol.getColumnIndex()) {
			BigDecimal cellPayAmountValue = FDCHelper.ZERO;
			BigDecimal cellPayRateValue = FDCHelper.toBigDecimal(e.getValue(), 2);
			cellPayAmountValue = FDCHelper.toBigDecimal(FDCHelper.divide(FDCHelper.multiply(contractAmount, cellPayRateValue), FDCHelper.ONE_HUNDRED,
					2, BigDecimal.ROUND_HALF_UP), 2);
			this.tblEconItem.getCell(rowIndex, "payAmount").setValue(cellPayAmountValue);
		}
		detailTableAutoFitRowHeight(tblEconItem);
	}

	/**
	 * 设置"付款事项"、"履约保证金及返还部分"表格的相关样式设置
	 */
	private void initEcoEntryTableStyle() {
		((KDTTransferAction) tblEconItem.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		((KDTTransferAction) tblBail.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		tblEconItem.getColumn("payAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("payRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblEconItem.getColumn("date").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);

		tblBail.getColumn("bailAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblBail.getColumn("bailDate").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
		txtBailOriAmount.setRemoveingZeroInDispaly(false);
		this.txtBailOriAmount.setRequestFocusEnabled(true);
		this.txtBailOriAmount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailOriAmount.setPrecision(2);
		this.txtBailOriAmount.setNegatived(false);
		this.txtBailRate.setMaximumValue(FDCHelper.MAX_DECIMAL);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailOriAmount.setHorizontalAlignment(JTextField.RIGHT);
		// this.txtBailRate.setPercentDisplay(true);
		this.txtBailRate.setRequestFocusEnabled(true);
		this.txtBailRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		this.txtBailRate.setPrecision(2);
		this.txtBailRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		this.txtBailRate.setMinimumValue(FDCHelper.ZERO);
		this.txtBailRate.setNegatived(false);
		this.txtBailRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBailRate.setRemoveingZeroInDispaly(false);
		
		KDTextField conditionTextField = new KDTextField();
		conditionTextField.setName("conditionTextField");
		conditionTextField.setMaxLength(200);
		KDTDefaultCellEditor conditionCellEditor = new KDTDefaultCellEditor(conditionTextField);
		tblEconItem.getColumn("payCondition").getStyleAttributes().setWrapText(true);
		tblEconItem.getColumn("payCondition").setEditor(conditionCellEditor);
		tblBail.getColumn("bailCondition").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("bailCondition").setEditor(conditionCellEditor);

		KDTextField descTextField = new KDTextField();
		descTextField.setName("descTextField");
		descTextField.setMaxLength(250);
		tblEconItem.getColumn("desc").getStyleAttributes().setWrapText(true);
		tblBail.getColumn("desc").getStyleAttributes().setWrapText(true);
		
		// 设置日期，付款类型F7
		EcoItemHelper.setEntryTableCtrl(this.tblEconItem, this.tblBail);
	}
	
	/**
	 * 提交时校验:履约保证金金额必须等于履约保证金及返还列表分录中返回金额之和 by Cassiel_peng 2008-8-26
	 */
	private void checkBailOriAmount() {
		BigDecimal itemAmtSum = FDCHelper.ZERO;
		for (int i = 0; i < tblEconItem.getRowCount(); i++) {
			itemAmtSum = FDCHelper.add(itemAmtSum, tblEconItem.getRow(i).getCell("payAmount").getValue());
		}
		if (itemAmtSum.compareTo(FDCHelper.toBigDecimal(txtamount.getBigDecimalValue(), 2)) == 1) {
			MsgBox.showWarning("合同经济条款付款金额之和不能大于合同修订后原币金额！");
			SysUtil.abort();
		}
		BigDecimal bailAmountSum = FDCHelper.ZERO;
		for (int i = 0; i < this.tblBail.getRowCount(); i++) {
			if (this.tblBail.getRow(i).getCell("bailAmount") != null) {
				bailAmountSum = FDCHelper.add(bailAmountSum, this.tblBail.getRow(i).getCell("bailAmount").getValue());
			}
		}
		if (bailAmountSum.compareTo(FDCHelper.toBigDecimal(txtBailOriAmount.getBigDecimalValue(), 2)) != 0) {
			MsgBox.showWarning("履约保证金金额必须等于履约保证金及返还列表分录中返回金额之和");
			SysUtil.abort();
		}
	}

	/**
	 * 计算付款事项的原币金额或者比例
	 */
	private void calPayItemAmt() {
		EcoItemHelper.calPayItemAmt(this.tblEconItem, this.tblBail, txtamount);
	}

	/**
	 * 计算履约保证金及返还部分的原币金额或者比例, 当合同金额发生改变时，应该以履约保证金额*返还比率
	 */
	private void calBailAmt() {
		if (isFirstLoad) {
			return;
		}
		EcoItemHelper.calBailAmt(tblBail, txtamount, txtBailOriAmount, txtBailRate);
	}
	
	protected void tblDetail_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent evt) throws Exception {
		KDTable table = (KDTable) evt.getSource();
		IRow entryRow = table.getRow(evt.getRowIndex());

		Object newValue = evt.getValue();
		IColumn col = table.getColumn(evt.getColIndex());
		String colKey = col.getKey();

		//内容发生修改
		if (colKey.equals("content") && (newValue instanceof BigDecimal)
				&& txtStampTaxRate.getBigDecimalValue() != null && txtExRate.getBigDecimalValue() != null) {

			if (isUseAmtWithoutCost && AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())) {
				// 计算印花税
				BigDecimal originalAmount = (BigDecimal) newValue;
				if (originalAmount != null && txtExRate.getBigDecimalValue() != null) {
					originalAmount = originalAmount.multiply(txtExRate.getBigDecimalValue());
				}
				BigDecimal stampTaxAmount = originalAmount.multiply(txtStampTaxRate.getBigDecimalValue());
				stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtStampTaxAmt.setNumberValue(stampTaxAmount);

				// 计算保修金额
				BigDecimal grtAmount = originalAmount.multiply(txtGrtRate.getBigDecimalValue());
				grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
				txtGrtAmount.setNumberValue(grtAmount);
			}
		}
		
		detailTableAutoFitRowHeight(tblDetail);

	}
	
	public boolean printCheck(){
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return false ;
		}
		return true;
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if(printCheck()){
			Map queryAndFilter = new HashMap();
			String billId = editData.getString("id");
			
			FilterInfo filter = new FilterInfo();//合同修订主
			filter.getFilterItems().add(new FilterItemInfo("id",billId, CompareType.EQUALS));
			queryAndFilter.put("ContractBillReviseForPrintQuery",filter);
			
			filter = new FilterInfo();//合同修订付款事项
			filter.getFilterItems().add(new FilterItemInfo("contractbillRevise.id",billId));
			queryAndFilter.put("ContractRevisePayItemForPrintQuery",filter);
			
			filter = new FilterInfo();//合同履约保证金返还
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getBail()!=null?editData.getBail().getId().toString():null));
			queryAndFilter.put("ContractReviseBailPrintQuery",filter);
			
			ContractBillRevisePrintDataProvider dataPvd = new ContractBillRevisePrintDataProvider(
					editData.getString("id"), getTDQueryPK(), getCommonQueryPackage(), queryAndFilter);
		
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		}else{
			return;
		}
	
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if(printCheck()){
			String billId = editData.getString("id");
			
			ContractBillRevisePrintDataProvider dataPvd = new ContractBillRevisePrintDataProvider(
					editData.getString("id"), getTDQueryPK());
			dataPvd.setPackagStr(getCommonQueryPackage());
			
			FilterInfo filter = new FilterInfo();//合同修订主
			filter.getFilterItems().add(new FilterItemInfo("id",billId, CompareType.EQUALS));
			dataPvd.addQueryAndFilter("ContractBillReviseForPrintQuery", filter);
			
			filter = new FilterInfo();//合同修订付款事项
			filter.getFilterItems().add(new FilterItemInfo("contractbillRevise.id",billId));
			dataPvd.addQueryAndFilter("ContractRevisePayItemForPrintQuery", filter);
			
			filter = new FilterInfo();//合同履约保证金返还
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getBail()!=null?editData.getBail().getId().toString():null));
			dataPvd.addQueryAndFilter("ContractReviseBailPrintQuery", filter);
			
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
					.getWindowAncestor(this));
		}else{
			return;
		}
		
	}

	protected String getCommonQueryPackage() {
		return "com.kingdee.eas.fdc.contract.app.";
	}
	
	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractBillRevise";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBillReviseForPrintQuery");
	}
	
	private void detailTableAutoFitRowHeight(KDTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			KDTableHelper.autoFitRowHeight(table, i);
		}
	}
	protected void afterSubmitAddNew() {
		super.afterSubmitAddNew();
		detailTableAutoFitRowHeight(tblEconItem);
		detailTableAutoFitRowHeight(tblBail);
		detailTableAutoFitRowHeight(tblDetail);
	}

	/**
	 * R130905-0190:  当"修订后签约金额原币"改变后,修改"补充协议金额"的值
	 * @param eventObj
	 * @Author：zhaoqin
	 * @CreateTime：2013-9-12
	 */
	private void txtAmount_dataChanged(DataChangeEvent eventObj) {
		if (!getOprtState().equals(STATUS_EDIT) && !getOprtState().equals(STATUS_ADDNEW))
			return;
		
		// 如果不是 补充合同，则返回
		if (!editData.getContractPropert().equals(ContractPropertyEnum.SUPPLY))
			return;
		
		for (int i = 0; i < getDetailInfoTable().getRowCount(); i++) {
			IRow row = getDetailInfoTable().getRow(i);
			ICell cell = row.getCell(ROWKEY_COL);
			if (null != cell && null != cell.getValue() && AM_ROW.equals(cell.getValue().toString())) { // //如果存在 ‘补充协议金额’行
				row.getCell(CONTENT_COL).setValue(eventObj.getNewValue());
				break;
			}
		}
	}
	
}