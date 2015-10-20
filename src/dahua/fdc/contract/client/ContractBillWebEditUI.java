/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.CommitListener;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
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
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.commonquery.BooleanEnum;
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
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCContractParamUI;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.ConNoCostSplitCollection;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConSplitExecStateEnum;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ContractBillLinkProgContEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.fdc.finance.client.ContractPayPlanEditUI;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class ContractBillWebEditUI extends AbstractContractBillWebEditUI
{

	private static final String DETAIL_DEF_ID = "detailDef.id";

	private static final Logger logger = CoreUIObject
			.getLogger(ContractBillEditUI.class);
	
	//责任人是否可以选择其他部门的人员
	private boolean canSelectOtherOrgPerson = false;
	
	//项目合同签约总金额超过项目
	private String controlCost ;
	//控制方式
	private String controlType ;
	
	//合同审批前进行拆分
	private boolean splitBeforeAudit ;
	
	/**
	 * output class constructor
	 */
	public ContractBillWebEditUI() throws Exception {
		super();
		//放在onLoad方法中
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				prmtcontractType.requestFocus(true);
//			}
//		});
		
		jbInit();
	}

    private void jbInit() {
        titleMain = this.getUITitle();
    }

	//变化事件
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
    	
    	addDataChangeListener(comboCurrency); 
    	addDataChangeListener(contractPropert);
    	addDataChangeListener(contractSource);
    	//addDataChangeListener(comboCurrency);
    	
    	addDataChangeListener(prmtcontractType);
    	addDataChangeListener(txtamount);
    	addDataChangeListener(txtExRate);
    	addDataChangeListener(txtGrtRate);
    	addDataChangeListener(txtStampTaxRate);
    }
    
    protected void detachListeners() {
    	pkbookedDate.removeDataChangeListener(bookedDateChangeListener);
    	
    	removeDataChangeListener(comboCurrency); 
    	removeDataChangeListener(contractPropert);
    	removeDataChangeListener(contractSource);
    	
    	removeDataChangeListener(prmtcontractType);
    	removeDataChangeListener(txtamount);
    	removeDataChangeListener(txtExRate);
    	removeDataChangeListener(txtGrtRate);
    	removeDataChangeListener(txtStampTaxRate);
    }
	
	/** 是否使用不计成本的金额, 是否单据计算 = 是, 此变量值为 fasle, 是否单据计算 = 否, 此变量值 = true, 
	 * 如果详细信息没有是否单独计算, 则此变量值为 false
	 */
	protected boolean isUseAmtWithoutCost = false;
	
	/**
	 * output loadFields method
	 */
	public void loadFields() {      
		
		detachListeners();
		
		super.loadFields();
       
		isUseAmtWithoutCost = editData.isIsAmtWithoutCost();
		
		loadDetailEntries();		
		
		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
		
		//币别选择
		GlUtils.setSelectedItem(comboCurrency,editData.getCurrency());
				
		txtExRate.setValue(editData.getExRate());		
		txtLocalAmount.setValue(editData.getAmount());
		if (this.editData.getCurrency() != null) {
			if (editData.getCurrency().getId().toString().equals(baseCurrency.getId().toString())) {
				// 是本位币时将汇率选择框置灰
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
			}
		}
		
		if(editData != null && editData.getCurProject() != null) {
			
//			String projId = editData.getCurProject().getId().toString();
//			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);
			
			//解决bug BT315446 注释代码 txtProj.setText(curProject.getDisplayName());
			//txtProj.setText(curProject.getDisplayName());
			txtProj.setText(editData.getCurProject().getDisplayName());
			
			FullOrgUnitInfo costOrg = this.orgUnitInfo;
				//FDCHelper.getCostCenter(editData.getCurProject(),null).castToFullOrgUnitInfo(); 
				//FDCClientUtils.getCostOrgByProj(projId);
			
			txtOrg.setText(costOrg.getDisplayName());
			editData.setOrgUnit(costOrg);
			editData.setCU(curProject.getCU());
		}
		
		attachListeners();
		//2009-1-20 在loadFields加入设置审批、反审批按钮状态的方法，在通过上一、下一功能切换单据时，正确显示审批、反审批按钮。
		setAuditButtonStatus(this.getOprtState());
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
	
		editData.setSignDate(DateTimeUtils.truncateDate(editData.getSignDate()));
		
		editData.setIsAmtWithoutCost(isUseAmtWithoutCost);
		
		super.storeFields();
		storeDetailEntries();
		
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
        {   
        	MsgBox.showWarning(this,EASResource
    				.getString(
    						"com.kingdee.eas.fdc.basedata.client.FdcResource",
    						"cantPrint"));
        	return;
        }
        ContractBillEditDataProvider data = new ContractBillEditDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));		
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
        {   
        	MsgBox.showWarning(this,EASResource
    				.getString(
    						"com.kingdee.eas.fdc.basedata.client.FdcResource",
    						"cantPrint"));
        	return;
        
        }
        ContractBillEditDataProvider data = new ContractBillEditDataProvider(editData.getString("id"),getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
	protected String getTDFileName() {
    	return "/bim/fdc/contract/ContractBill";
	}
	protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint");
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
		return com.kingdee.eas.fdc.contract.ContractBillFactory
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

		return new ContractBillEntryInfo();
	}

	
	
	
	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		
		objectValue.setCreator(SysContext.getSysContext().getCurrentUserInfo());		
		objectValue.setCreateTime(new Timestamp(serverDate.getTime()));
		objectValue.setSignDate(serverDate);

		objectValue.setSourceType(SourceTypeEnum.ADDNEW);
		
		// 默认直接合同
		objectValue.setContractPropert(ContractPropertyEnum.DIRECT);
		objectValue.setSplitState(CostSplitStateEnum.NOSPLIT);
		BOSUuid projId = (BOSUuid) getUIContext().get("projectId");
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");
		if(projId == null) {
			projId = editData.getCurProject().getId();
		}
		
		CurProjectInfo curProjectInfo = this.curProject;
		objectValue.setCurProject(curProjectInfo);
		if(curProjectInfo.getLandDeveloper() != null)
			objectValue.setLandDeveloper(curProjectInfo.getLandDeveloper());		

		if (typeId != null) {
			ContractTypeInfo contractTypeInfo = (ContractTypeInfo)ActionCache.get("FDCBillEditUIHandler.contractTypeInfo");
			if(contractTypeInfo==null){
				try {
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("id");
					selector.add("number");
					selector.add("name");
					selector.add("longNumber");
					selector.add("isLeaf");
					selector.add("dutyOrgUnit.id");
					selector.add("dutyOrgUnit.number");
					selector.add("dutyOrgUnit.name");
					selector.add("payScale");
					selector.add("stampTaxRate");
					selector.add("isCost");
					contractTypeInfo = ContractTypeFactory.getRemoteInstance()
							.getContractTypeInfo(new ObjectUuidPK(typeId), selector);
					
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
			
			objectValue.setContractType(contractTypeInfo);
			objectValue.setRespDept(contractTypeInfo.getDutyOrgUnit());
			objectValue.setIsCoseSplit(contractTypeInfo.isIsCost());
			objectValue.setPayScale(contractTypeInfo.getPayScale());
			
			objectValue.setStampTaxRate(contractTypeInfo.getStampTaxRate());
			
		}

		//签约日期
//		objectValue.setSignDate(new Date());

		//造价性质
		objectValue.setCostProperty(CostPropertyEnum.BASE_CONFIRM);
		
		/*
		 * 变更提示比例％数值设置缺省值5.00，手工修改（可以在【预警】设置初始值）。结算提示比例％数值设置缺省值100.00，手工修改（可以在【预警】设置初始值）。付款提示比例％数值设置缺省值85.00，手工修改（可以在【预警】设置初始值）。
		 */
		objectValue.setPayPercForWarn(new BigDecimal("85"));
		objectValue.setChgPercForWarn(new BigDecimal("5"));

		// 默认委托
		objectValue.setContractSource(ContractSourceEnum.TRUST);
		
		//未结算
		objectValue.setHasSettled(false);
		
		objectValue.setGrtRate(FDCHelper.ZERO);

		objectValue.setCurrency(baseCurrency);
		objectValue.setExRate(FDCHelper.ONE);
		objectValue.setAmount(FDCHelper.ZERO);
		objectValue.setOriginalAmount(FDCHelper.ZERO);
		objectValue.setPayScale(FDCHelper.ZERO);
		objectValue.setGrtAmount(FDCHelper.ZERO);
		
		objectValue.setCU(curProjectInfo.getCU());
		objectValue.setConSplitExecState(ConSplitExecStateEnum.COMMON);
		
		//期间
		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		
		mainContractId = null;
		detailAmount = FDCHelper.ZERO;
		
		return objectValue;
	}

	private void setInviteCtrlVisibleByContractSource(
			ContractSourceEnum contractSource) {

		if (contractSource == ContractSourceEnum.TRUST) {
			setInviteCtrlVisible(false);
		} else if (contractSource == ContractSourceEnum.INVITE) {
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

		} else if (contractSource == ContractSourceEnum.DISCUSS) {
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

		}else if (contractSource == ContractSourceEnum.COOP) {
			setInviteCtrlVisible(false);
			//战略合作级别
			contCoopLevel.setVisible(true);
			// 计价方式
			contPriceType.setVisible(true);

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
	ContractPropertyEnum contractPro = null;
	protected void contractPropert_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getStateChange() == ItemEvent.SELECTED) {
			ContractPropertyEnum contractProp = (ContractPropertyEnum) e.getItem();
			contractPro = contractProp;
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
	
	/*
	 * 主合同id 
	 */
	private String mainContractId = null;
	private BigDecimal detailAmount = FDCHelper.ZERO; 

	class MyDataChangeListener implements DataChangeListener {

		public void dataChanged(DataChangeEvent eventObj) {
			String name = null;
			ContractBillInfo info = null;
			if (eventObj.getNewValue() != null) {
				info = (ContractBillInfo) eventObj.getNewValue();
				name = info.getName();
				mainContractId = info.getId().toString();
				if(isUseAmtWithoutCost&&detailAmount.compareTo(FDCHelper.ZERO)>0){
					try {
						MsgContractHasSplit();
					} catch (EASBizException e) {
						handUIExceptionAndAbort(e);
					} catch (BOSException e) {
						handUIExceptionAndAbort(e);
					}
				}
				comboCurrency.setEnabled(false);
				for(int i =0;i<comboCurrency.getItemCount();i++){
					Object o = comboCurrency.getItemAt(i);
					if(o instanceof CurrencyInfo){
						CurrencyInfo c = (CurrencyInfo)o;
						if(c.getId().equals(info.getCurrency().getId())){
							comboCurrency.setSelectedIndex(i);
						}
					}
				}
				comboCurrency.setEditable(false);
				txtExRate.setValue(info.getExRate());
				txtExRate.setEditable(false);
			}
			else {
				name = null;
			}
//			comboCurrency.setSelectedItem(info.getCurrency());
			//设置补充合同币别和汇率和主合同一致
			
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
			String amountWithOutCost = ContractClientUtils.getRes("amountWithOutCost");
			String mainContractNumber = ContractClientUtils.getRes("mainContractNumber");
			String mainContractName = ContractClientUtils.getRes("mainContractName");

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
			ICell cell = mainContractNumberRow.getCell(CONTENT_COL);
			cell.setEditor(
					prmtContractEditor);
			ObjectValueRender objectValueRender = getF7Render();
			cell.setRenderer(
					objectValueRender);

			MyDataChangeListener lis = new MyDataChangeListener();
			kDBizPromptBoxContract.addDataChangeListener(lis);

			mainContractNumberRow.getCell(DATATYPE_COL).setValue(
					DataTypeEnum.STRING);
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
	
	private void addKDTableLisener() {
		tblDetail.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e){
				comboCurrency.setEnabled(true);
				txtExRate.setEditable(true);
            	KDTSelectManager sm = tblDetail.getSelectManager();
            	if (sm.getActiveRowIndex() == getRowIndexByRowKey(LO_ROW) && sm.getActiveColumnIndex() == 2){
            		if(BeforeActionEvent.ACTION_PASTE==e.getType()){
    					e.setCancel(true);
    				}
            	}                
            }
        });
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
		filterItems.add(new FilterItemInfo("contractPropert", ContractPropertyEnum.DIRECT_VALUE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.CANCEL_VALUE, CompareType.NOTEQUALS));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.SAVED_VALUE, CompareType.NOTEQUALS));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.INVALID, CompareType.NOTEQUALS));
		//1.4.3.2.2 只能是已经审批的合同
		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		//2008-09-17 周勇 已结算的合同不需要出现
		filterItems.add(new FilterItemInfo("hasSettled",Boolean.FALSE));
		
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
		if(editData.getId() != null) {
			filterItems.add(new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS));
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
			txtGrtRate.setValue(FDCHelper.ZERO);
			txtGrtRate.setRequired(true);
			txtGrtRate.setEditable(true);
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
			//若为不独立结算的补充合同 则保修比例不允许录入
			if(contractPro == ContractPropertyEnum.SUPPLY){
				txtGrtRate.setRequired(false);
				txtGrtRate.setValue(FDCHelper.ZERO);
				txtGrtRate.setEditable(false);
			}
			if (txtamount.getNumberValue() != null) {
				cell.setValue(txtamount.getNumberValue());
				try {
					MsgContractHasSplit();
				} catch (EASBizException e) {
					handUIExceptionAndAbort(e);
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			}
			txtamount.setValue(null);
			txtLocalAmount.setValue(null);
			txtStampTaxAmt.setValue(null);
			
			setAmountRequired(false);
			
			isUseAmtWithoutCost = true;
		}
	}

	private void setAmountRequired(boolean required) {
		txtamount.setRequired(required);
		txtLocalAmount.setRequired(required);
	}
	/**
	 * 
	 * 描述：合同形成方式变化，招标/议标信息控件变化
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#contractSource_itemStateChanged(java.awt.event.ItemEvent)
	 */
	protected void contractSource_itemStateChanged(ItemEvent e)
			throws Exception {

		if (e.getItem() == null) {
			setInviteCtrlVisible(false);
			return;
		}
		if (e.getStateChange() == ItemEvent.SELECTED) {
			ContractSourceEnum contractSource = (ContractSourceEnum) e
					.getItem();

			setInviteCtrlVisibleByContractSource(contractSource);
		}

		super.contractSource_itemStateChanged(e);
	}
	
	//子类可以重载
	protected  void fetchInitParam() throws Exception{
		
		super.fetchInitParam();		
		
		Map param = (Map)ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if(param==null){
			param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());	
		}
				
		if(param.get(FDCConstants.FDC_PARAM_SELECTPERSON)!=null){
			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
		if(param.get(FDCConstants.FDC_PARAM_OUTCOST)!=null){
			controlCost = param.get(FDCConstants.FDC_PARAM_OUTCOST).toString();
		}
		if(param.get(FDCConstants.FDC_PARAM_CONTROLTYPE)!=null){
			controlType = param.get(FDCConstants.FDC_PARAM_CONTROLTYPE).toString();
		}
		
		//splitBeforeAudit
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
	}
	
	protected  void fetchInitData() throws Exception{
		super.fetchInitData();
	}

	public void onLoad() throws Exception {
		//2008-12-30 暂时屏蔽新功能 复制分录
		this.actionCopyLine.setVisible(false);
		this.actionCopyLine.setEnabled(false);
		//根据参数是否显示合同费用项目
		boolean isShowCharge = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHARGETYPE);
		if(!isShowCharge){
			this.conChargeType.setVisible(false);
			this.prmtCharge.setRequired(false);
		}else{
			this.conChargeType.setVisible(true);
			this.prmtCharge.setRequired(true);
		}
		//只显示已启用的合同费用项目
		EntityViewInfo viewInfo = this.prmtCharge.getEntityViewInfo();
		if(viewInfo == null){
			viewInfo = new EntityViewInfo();
		}
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		viewInfo.setFilter(filterInfo);
		this.prmtCharge.setEntityViewInfo(viewInfo);
		tblCost.getStyleAttributes().setLocked(true);
		tblCost.checkParsed();
		//金额靠右
		txtamount.setHorizontalAlignment(JTextField.RIGHT);
		//不允许为负
		txtamount.setNegatived(false);
		txtLocalAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtGrtRate.setEditable(true);
		txtExRate.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//		txtExRate.setPrecision(2);
		
		EntityViewInfo invView=new EntityViewInfo();
		FilterInfo invFilter=new FilterInfo();
		invView.setFilter(invFilter);
		invFilter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		prmtinviteType.setEntityViewInfo(invView);
		
		//init currency
		removeDataChangeListener(comboCurrency); 
		FDCClientHelper.initComboCurrency(comboCurrency, true);
		
		super.onLoad();
		setMaxMinValueForCtrl();
		sortPanel();
		
		//亿达需要，万科先注销
//		fillCostPanel();
		
		setInviteCtrlVisible(false);
		updateButtonStatus();		
		
		// 初始化合同类型F7
		prmtcontractType.setSelector(new ContractTypePromptSelector(this));
		prmtcontractType.setEntityViewInfo(getViewForContractTypeF7());
		prmtcontractType.setEditable(true);
		//由于甲方基础资料级别为S4，构建filter时会自动加上CU隔离 为去除默认级别filter，修改掉
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
//		HashSet set=new HashSet();
//		set.add(OrgConstants.SYS_CU_ID);
		String cuId = editData.getCU().getId().toString();
//		//
//		set.add(cuId);
//		filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
//
//		view.setFilter(filter);
//		prmtlandDeveloper.setEntityViewInfo(view);
		//去掉默认级别filter
		prmtlandDeveloper.addSelectorListener(new SelectorListener() {
		public void willShow(SelectorEvent e) {
			KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
			f7.getQueryAgent().setDefaultFilterInfo(null);
			f7.getQueryAgent().setHasCUDefaultFilter(false);
			f7.getQueryAgent().resetRuntimeEntityView();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			HashSet set=new HashSet();
			set.add(OrgConstants.SYS_CU_ID);
			String cuId = editData.getCU().getId().toString();
			set.add(cuId);
			filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
			view.setFilter(filter);
			f7.setEntityViewInfo(view);
		}
	});
		prmtlandDeveloper.addCommitListener(new CommitListener(){
		public void willCommit(CommitEvent e) {
			KDBizPromptBox f7 =(KDBizPromptBox)e.getSource();
			f7.getQueryAgent().setDefaultFilterInfo(null);
			f7.getQueryAgent().setHasCUDefaultFilter(false);
			f7.getQueryAgent().resetRuntimeEntityView();
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			HashSet set=new HashSet();
			set.add(OrgConstants.SYS_CU_ID);
			String cuId = editData.getCU().getId().toString();
			set.add(cuId);
			filter.getFilterItems().add(new FilterItemInfo("CU.id",set,CompareType.INCLUDE));
			view.setFilter(filter);
			f7.setEntityViewInfo(view);
		}
	});
		
		FDCClientUtils.setRespDeptF7(prmtRespDept, this, canSelectOtherOrgPerson?null:cuId);
		
		FDCClientUtils.setPersonF7(prmtRespPerson, this, canSelectOtherOrgPerson?null:cuId);
				
		actionAddLine.setEnabled(false);
		actionAddLine.setVisible(false);
		
		actionInsertLine.setEnabled(false);
		actionInsertLine.setVisible(false);
		
		actionRemoveLine.setEnabled(false);
		actionRemoveLine.setVisible(false);

		txtGrtAmount.setEditable(false);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);
		
		if (editData.getContractSource() != null) {
			setInviteCtrlVisibleByContractSource(editData.getContractSource());
		}		

		if(STATUS_ADDNEW.equals(getOprtState()) && prmtcontractType.getData() != null) {
			ContractTypeInfo cType = (ContractTypeInfo)prmtcontractType.getData();
			removeDetailTableRowsOfContractType();
			
			try {
				fillDetailByContractType(cType.getId().toString());
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		setContractType();
	
		if(editData != null && (editData.getState()==FDCBillStateEnum.CANCEL || editData.isIsArchived())){
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
			actionSplit.setEnabled(false);
			btnSplit.setEnabled(false);
		}

		//合同审批前进行拆分
		if(splitBeforeAudit && editData.getState()!=null && !FDCBillStateEnum.SAVED.equals(editData.getState())){
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if(!FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			actionDelSplit.setEnabled(false);
		}		
		
		if(editData.getSplitState()==null || CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())){
			this.actionViewCost.setVisible(false);
		}else{
			this.actionViewCost.setVisible(true);
		}
		
		setPrecision();
		
		//初始化供应商F7
		FDCClientUtils.initSupplierF7(this, prmtpartB, cuId);
		FDCClientUtils.initSupplierF7(this, prmtpartC, cuId);
		FDCClientUtils.initSupplierF7(this, prmtwinUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowestPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtlowerPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmtmiddlePriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthigherPriceUnit, cuId);
		FDCClientUtils.initSupplierF7(this, prmthighestPriceUni, cuId);		
		
		addKDTableLisener();
		handleOldData();
		
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);		
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		//当通过付款申请单查看合同时，可以查看到合同的附件信息。
		if (STATUS_FINDVIEW.equals(this.oprtState)) {

            this.actionAttachment.setVisible(true);
            this.actionAttachment.setEnabled(true);
            //没有启用 审批前必须拆分完全 参数时，查看不显示拆分按钮
            String prjId = editData.getOrgUnit().getId().toString();
            boolean isSplitBeforeAudit = FDCUtils.getDefaultFDCParamByKey(null,prjId,
            		FDCConstants.FDC_PARAM_SPLITBFAUDIT);
            if(!isSplitBeforeAudit){
            	this.actionSplit.setEnabled(false);
            	this.actionSplit.setVisible(false);
            }
        }
		
		getDetailInfoTable().getStyleAttributes().setWrapText(true);
		//getDetailInfoTable().getStyleAttributes().set
		this.disableTableMenus(this.getDetailInfoTable());
		//修改合同时判断是否为非独立结算的补充合同  
		if(editData.getContractPropert() == ContractPropertyEnum.SUPPLY){
			for(int i=0;i<getDetailInfoTable().getRowCount();i++){
				Object o = getDetailInfoTable().getRow(i).getCell(CONTENT_COL).getValue();
				if(o instanceof BooleanEnum){
					if((BooleanEnum)o==BooleanEnum.FALSE){
						this.txtGrtRate.setEditable(false);
					}
				}

			}
		}
		
		//业务日志判断为空时取期间中断
		if(pkbookedDate!=null&&pkbookedDate.isSupportedEmpty()){
			pkbookedDate.setSupportedEmpty(false);
		}
		//当非查看状态时才获得焦点
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(!getOprtState().equals(STATUS_VIEW)){
					prmtcontractType.requestFocus(true);
				}				
			}
		});
		
		//如果为补充合同则关联框架合约至灰
		if(mainContractId != null && BooleanEnum.FALSE.equals(getDetailInfoTable().getCell(getRowIndexByRowKey(LO_ROW), CONTENT_COL).getValue())){
			this.btnProgram.setEnabled(false);
		}else{
			this.btnProgram.setEnabled(true);
		}
		//工作流处理时修改字段
		SwingUtilities.invokeLater(new Runnable(){
		public void run() {
				editAuditProgColumn(); //在改类中实现以下方法
			}
		});
	}
	/*
	 * 工作流处理时修改字段
	 */
	private void editAuditProgColumn() {
		// 判断是否在工作流审批中
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		//调试
		logger.error(isFromWorkflow+"************************");
		logger.error(getOprtState().equals(STATUS_FINDVIEW)+"");
		logger.error(actionSave.isVisible()+"");
		logger.error((editData.getState() == FDCBillStateEnum.SUBMITTED || editData
				.getState() == FDCBillStateEnum.AUDITTING)+"");
		if (isFromWorkflow != null
				&& isFromWorkflow.toString().equals("true")
				&& getOprtState().equals(STATUS_FINDVIEW)
				&& actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.SUBMITTED || editData
						.getState() == FDCBillStateEnum.AUDITTING)) {

			// 首先锁定界面上所有的空间
			this.lockUIForViewStatus();

			// 首先给控件个访问的权限，然后设置是否可编辑就可以了！不能使用unloack方法，这样会使所用的控件处于edit的状态
			this.prmtFwContractTemp.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
			this.prmtFwContractTemp.setEditable(true);
			this.actionSave.setEnabled(true);

		}
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true")
				&& getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}

	//业务日期变化引起,期间的变化
    protected void bookedDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	//super.pkbookedDate_dataChanged(e);
    	
    	String projectId = this.editData.getCurProject().getId().toString();    	
    	fetchPeriod(e,pkbookedDate,cbPeriod, projectId,  true);
    	
    	this.comboCurrency_itemStateChanged(null);
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
    	txtcontractName.setMaxLength(80);
    	
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

		kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
		kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
		//亿达需要，万科先注销
//		kDTabbedPane1.add(pnlCost, "成本信息");		
	}
	
    /**
     * output prmtcontractType_willShow method
     */
    protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    	if(STATUS_EDIT.equals(getOprtState())){
    		MsgBox.showWarning(this, FDCClientUtils.getRes("changeContractType"));
    	}
    }
	protected void prmtcontractType_dataChanged(DataChangeEvent e)
			throws Exception {
		Object newValue = e.getNewValue();
		contractTypeChanged(newValue);

		super.prmtcontractType_dataChanged(e);
	}

	private void contractTypeChanged(Object newValue) throws Exception, BOSException, EASBizException, CodingRuleException {
		if (newValue != null && STATUS_ADDNEW.equals(getOprtState())) {
			ContractTypeInfo info = (ContractTypeInfo) newValue;
			if (!info.isIsLeaf()) {
				MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
				prmtcontractType.setData(null);
				prmtcontractType.requestFocus();
				SysUtil.abort();
			}
			///if(info!=null&&
			//update by renliang
					if((this.editData.getContractType()==null||
							!this.editData.getContractType().getId().equals(info.getId()))){
				this.editData.setContractType(info);
				this.editData.setNumber(null);
				this.txtNumber.setText(null);
				this.handleCodingRule1();
			}
			removeDetailTableRowsOfContractType();
			fillDetailByContractType(info.getId().toString());
			chkCostSplit.setSelected(info.isIsCost());
			fillInfoByContractType(info);
		} else {
			if (newValue != null) {
				// 没有完成审批(也包括归档，因为归档前提是完成审批)的合同的合同类型可以修改，合同编码也可以修改
				if (STATUS_EDIT.equals(getOprtState())) {
					if ((this.editData.getState()
							.equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))) {
						// prmtcontractType.setEnabled(false);
						actionSplit.setEnabled(true);
					} else {
						ContractTypeInfo info = (ContractTypeInfo) newValue;
						if (!info.isIsLeaf()) {
							MsgBox.showWarning(this, FDCClientUtils
									.getRes("selectLeaf"));
							prmtcontractType.setData(null);
							prmtcontractType.requestFocus();
							SysUtil.abort();
						}
						removeDetailTableRowsOfContractType();
						fillDetailByContractType(info.getId().toString());
						chkCostSplit.setSelected(info.isIsCost());
						fillInfoByContractType(info);
						if(this.editData.getContractType()!=null&&
								//info!=null&& update by renliang
								!this.editData.getContractType().getId().equals(info.getId())){
							this.editData.setContractType(info);
							this.editData.setNumber(null);
							this.txtNumber.setText(null);
							this.handleCodingRule1();
						}
					}
					
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

	private void fillDetailByContractType(String id) throws Exception {
		
		/**
		 * 其他自定义的合同类型的时候，系统没有自动显示备注
		 */
		if(!ContractClientUtils.isContractTypePre(id)) {
			IRow row = getDetailInfoTable().addRow(0);
			row.getCell(DETAIL_COL).setValue(ContractClientUtils.getRes("remark"));
			row.getCell(DATATYPE_COL).setValue(DataTypeEnum.STRING);
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractType.id", id));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("number");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		ContractDetailDefCollection contractDetailDefCollection = ContractDetailDefFactory
				.getRemoteInstance().getContractDetailDefCollection(view);
		
		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		
		for (Iterator iter = contractDetailDefCollection.iterator(); iter
				.hasNext();) {
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
			
			row.getCell(DESC_COL).setEditor(editorString);
		}
		
		
	}

	public KDTDefaultCellEditor getEditorByDataType(DataTypeEnum dataType) {
		if (dataType == DataTypeEnum.DATE) {
			//KDDatePicker datePicker = new KDDatePicker();
			return FDCClientHelper.getDateCellEditor();
		} else if (dataType == DataTypeEnum.BOOL) {
			KDComboBox booleanCombo = getBooleanCombo();
			return new KDTDefaultCellEditor(booleanCombo);
		}
		else if(dataType == DataTypeEnum.NUMBER) {
			return FDCClientHelper.getNumberCellEditor();
		}
		else if(dataType == DataTypeEnum.STRING) {
			
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
		if (STATUS_VIEW.equals(oprtType) || "FINDVIEW".equals(oprtType)) {
			actionAddLine.setEnabled(false);
			actionInsertLine.setEnabled(false);
			actionRemoveLine.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnInsertLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
			tblDetail.getStyleAttributes().setLocked(true);
//			menuBiz.setVisible(false);
		}
		
		actionViewContent.setEnabled(true);
		
		if(STATUS_FINDVIEW.equals(oprtType) && getUIContext().get("source") == null) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		}
		else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
			actionDelSplit.setEnabled(false);
			actionDelSplit.setVisible(false);
		}			

		//合同审批前进行拆分
		if(splitBeforeAudit && editData!=null && editData.getState()!=null && !FDCBillStateEnum.SAVED.equals(editData.getState())){
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
			actionDelSplit.setEnabled(true);
			actionDelSplit.setVisible(true);
		}
		if(  editData!=null && !FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			actionDelSplit.setEnabled(false);
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
			if(this.editData.getState() != null && (this.editData.getState().equals(com.kingdee.eas.fdc.basedata.FDCBillStateEnum.AUDITTED))
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
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
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
		
		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractPlan.*"));

		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.name"));
		
		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));
		
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodYear"));
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
		// dataBinder.loadLineFields(table, row, obj);
	}

	private void storeDetailEntries() {
		ContractBillEntryInfo entryInfo = null;
		int count = getDetailInfoTable().getRowCount();
		ContractBillInfo billInfo = new ContractBillInfo();
		billInfo.setId(editData.getId());

		for (int i = 0; i < editData.getEntrys().size(); i++) {
			editData.getEntrys().removeObject(i);
			i--;
		}
		for (int i = 0; i < count; i++) {
			entryInfo = new ContractBillEntryInfo();

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
			if(dataType!=null){
				entryInfo.setDataType(dataType);
			}
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
			
		}
	}

	protected void loadDetailEntries() {
		if(STATUS_ADDNEW.equals(getOprtState())) return;
		getDetailInfoTable().removeRows(false);
		getDetailInfoTable().checkParsed();
		ContractBillEntryCollection coll = editData.getEntrys();
		if (coll == null || coll.size() == 0) {
			return;
		}
		
		KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
		getDetailInfoTable().getColumn(DESC_COL).getStyleAttributes().setWrapText(true);
		getDetailInfoTable().getColumn(CONTENT_COL).getStyleAttributes().setWrapText(true);
		int size = coll.size();

		ContractBillEntryInfo entryInfo = null;
		for (int i = 0; i < size; i++) {
			entryInfo = (ContractBillEntryInfo) coll.get(i);
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
			
			KDTDefaultCellEditor editor = getEditorByDataType(dataType);

			if (editor != null) {
				row.getCell(CONTENT_COL).setEditor(editor);
			} 
			if (entryInfo.getRowKey() != null
					&& entryInfo.getRowKey().equals(NU_ROW)) {
				KDBizPromptBox kDBizPromptBoxContract = getContractF7Editor();
				KDTDefaultCellEditor prmtContractEditor = new KDTDefaultCellEditor(
						kDBizPromptBoxContract);
				row.getCell(CONTENT_COL).setEditor(prmtContractEditor);
				ObjectValueRender objectValueRender = getF7Render();
				row.getCell(CONTENT_COL).setRenderer(objectValueRender);
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(LO_ROW)) {
				KDComboBox box = (KDComboBox)row.getCell(CONTENT_COL).getEditor().getComponent();
				IsLonelyChangeListener isLonelyChangeListener = new IsLonelyChangeListener();
				box.addItemListener(isLonelyChangeListener);

				lastDispAddRows = true;
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(NU_ROW)) {
				Component component = row.getCell(CONTENT_COL).getEditor().getComponent();
				if(component instanceof KDBizPromptBox){
					KDBizPromptBox box = (KDBizPromptBox)row.getCell(CONTENT_COL).getEditor().getComponent();
					box.addDataChangeListener(new MyDataChangeListener());
				}
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(NA_ROW)) {
				row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
				
			}
			
			if(entryInfo.getRowKey() != null && entryInfo.getRowKey().equals(NU_ROW) && entryInfo.getContent() != null && entryInfo.getContent().trim().length() > 0) {
				String id =	entryInfo.getContent();
				try {
					ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(id));
					row.getCell(CONTENT_COL).setValue(contractBillInfo);
					mainContractId = id;
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
					if(STATUS_FINDVIEW.equals(this.getOprtState())||STATUS_VIEW.equals(this.getOprtState())){
						row.getCell(CONTENT_COL).getStyleAttributes().setLocked(true);
					}
					else
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
//			lastDispAddRows=false;
			row.getCell(DESC_COL).setEditor(editorString);
			
			if(dataType == DataTypeEnum.STRING){
				int height = row.getHeight();
				int lentgh1 = entryInfo.getContent()!=null?entryInfo.getContent().length():0;
				int lentgh2 = entryInfo.getDesc()!=null?entryInfo.getDesc().length():0;
				int heightsize = lentgh1/75>lentgh2/125?lentgh1/75:lentgh2/125;
				
				row.setHeight(height*(1+heightsize));				
			}				
		}
		
		if(!lastDispAddRows){
			fillDetailByPropert(editData.getContractPropert());
		}
		if(isUseAmtWithoutCost&&mainContractId!=null){
			this.comboCurrency.setEnabled(false);
		}
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
	    	ContractBillInfo info = (ContractBillInfo)newData;
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
		chkIsPartAMaterialCon.setEnabled(true);
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
    
    protected void lockUIForViewStatus() {
    	super.lockUIForViewStatus();
    	chkCostSplit.setEnabled(false);
    	chkIsPartAMaterialCon.setEnabled(false);
    }
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	super.actionAddNew_actionPerformed(e);
    	prmtcontractType.setEnabled(true);
    	kDDateCreateTime.setEnabled(false);
    	comboCurrency.setEnabled(true);
    	getDetailInfoTable().removeRows();
    	
    	lastDispAddRows = false;
    }
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		Object obj = txtExRate.getValue();
		
		//该合同已经进行了拆分，不能进行修改
		if(editData.getSplitState()!=null &&!CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())){
			MsgBox.showWarning(this, "该合同已经进行了拆分，不能进行修改");
			SysUtil.abort();
		}
		
		super.actionEdit_actionPerformed(e);
		txtCreator.setEnabled(false);
		kDDateCreateTime.setEnabled(false);
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
		comboCurrency_itemStateChanged(null);
		
		if(obj!=null){
			txtExRate.setValue(obj);
		}
		if(isUseAmtWithoutCost&&mainContractId!=null){
			this.comboCurrency.setEnabled(false);
		}
	}
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
    	//super.actionSplit_actionPerformed(e);
    	
    	if(getSelectBOID() == null) return;
    	//boolean isCostSplit=chkCostSplit.isSelected();
    	//合同拆分		jelon 12/30/2006
        String contrBillID = getSelectBOID();	 
        
        AbstractSplitInvokeStrategy invokeStra = new ContractSplitInvokeStrategy(contrBillID, this);
        invokeStra.invokeSplit();
    }

    public void actionDelSplit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkConInWF();
    	//super.com
    	if(getSelectBOID() == null) return;
    	
        ContractBillInfo costBillInfo=null;
    	//合同拆分		jelon 12/30/2006
        String contrBillID = getSelectBOID();	
        
        SelectorItemCollection selectors = new SelectorItemCollection();

        selectors.add("id");
		selectors.add("splitState");

       	costBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contrBillID)),selectors);
		
		
		//该合同已经进行了拆分，不能进行修改
		if(costBillInfo.getSplitState()==null || CostSplitStateEnum.NOSPLIT.equals(costBillInfo.getSplitState())){
			MsgBox.showWarning(this, "该合同还没有拆分");
			SysUtil.abort();
		}
		
		if (confirmRemove())
	    {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contrBillID));
			EntityViewInfo view = new EntityViewInfo();
	    	view.setFilter(filter);
	    	view.getSelector().add("id");
	    	
	    	ContractCostSplitCollection col = ContractCostSplitFactory.getRemoteInstance().getContractCostSplitCollection(view);
	    	IObjectPK[] pks = new IObjectPK[col.size()];
    		for(int i=0;i<col.size();i++){
    			pks[i] = new ObjectUuidPK(col.get(i).getId().toString());
    		}
    		
    		ContractCostSplitFactory.getRemoteInstance().delete(pks);
    		
	    	ConNoCostSplitCollection nocol = ConNoCostSplitFactory.getRemoteInstance().getConNoCostSplitCollection(view);
	    	IObjectPK[] nopks = new IObjectPK[nocol.size()];
    		for(int i=0;i<nocol.size();i++){
    			nopks[i] = new ObjectUuidPK(nocol.get(i).getId().toString());
    		}
    		
    		ConNoCostSplitFactory.getRemoteInstance().delete(nopks);
			//ContractCostSplitFactory.getRemoteInstance().delete(filter);
			//ConNoCostSplitFactory.getRemoteInstance().delete(filter);	
	    }
    }
    private void checkConInWF() {
    	if(editData == null || editData.getSplitState() == null)
    		return;
    	//是否必须审核前拆分
    	try {
			if(splitBeforeAudit && FDCUtils.isRunningWorkflow(editData.getId().toString())
					&& CostSplitStateEnum.ALLSPLIT.equals(editData.getSplitState())){
				MsgBox.showWarning(this,"合同在工作流，不能删除合同拆分！");
				SysUtil.abort();
			}
		} catch (BOSException e) {

			handUIExceptionAndAbort(e);
		} 	
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
    	txtamount.setValue(editData.getOriginalAmount());
	}
	
	protected void comboCurrency_itemStateChanged(ItemEvent e) throws Exception {

		super.comboCurrency_itemStateChanged(e);
		if(STATUS_VIEW.equals(getOprtState()) || STATUS_FINDVIEW.equals(getOprtState())) return;
		if(e==null||e.getStateChange() == ItemEvent.SELECTED) {
			CurrencyInfo currency = (CurrencyInfo)this.comboCurrency.getSelectedItem();	    	
	    	//CurrencyInfo baseCurrency = FDCClientHelper.getBaseCurrency(SysContext.getSysContext().getCurrentFIUnit());
	    	Date bookedDate = (Date)pkbookedDate.getValue();
	    	
	    	ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, currency.getId(),company,bookedDate);
	    	
	    	int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
	    	BigDecimal exRate = FDCHelper.ONE;
	    	int exPrecision = curPrecision;
	    	
	    	if(exchangeRate!=null){
	    		exRate = exchangeRate.getConvertRate();
	    		exPrecision = exchangeRate.getPrecision();
	    	}
	    		
	    	
	    	txtExRate.setPrecision(exPrecision);
	    	txtExRate.setValue(exRate);
	    	txtamount.setPrecision(curPrecision);
	    	
	    	if(baseCurrency != null && baseCurrency.getId().equals(currency.getId())) {
				txtExRate.setValue(FDCConstants.ONE);
				txtExRate.setRequired(false);
				txtExRate.setEditable(false);
				txtExRate.setEnabled(false);
	    	}
	    	else {
	    		txtExRate.setEditable(true);
				txtExRate.setRequired(true);
				txtExRate.setEnabled(true);
	    	}	    	
	    	calLocalAmt();
		}
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
    }
 

    
    /**
     * 计算印花税金额
     *
     */
    private void calStampTaxAmt() {
    	
    	//使用不计成本的金额
    	if(isUseAmtWithoutCost){
    		int rowcount = tblDetail.getRowCount();
    		Object newValue = null;
    		for(int i=0;i<rowcount;i++){
    			IRow entryRow = tblDetail.getRow(i);
    			//
    			if(AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())){
    				 newValue = entryRow.getCell(CONTENT_COL).getValue();
    				 break;
    			}
    		}
    		
        	//计算印花税
        	BigDecimal originalAmount = (BigDecimal)newValue;
    		BigDecimal stampTaxAmount = originalAmount.multiply(txtStampTaxRate.getBigDecimalValue());
    		stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
    		txtStampTaxAmt.setNumberValue(stampTaxAmount);
            
    		return ;
    	}
    	
    	//不使用不计成本的金额
    	if(txtamount.getBigDecimalValue() != null && txtStampTaxRate.getBigDecimalValue() != null) {
    		BigDecimal stampTaxAmount = txtamount.getBigDecimalValue().multiply(txtStampTaxRate.getBigDecimalValue());
    		stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
    		txtStampTaxAmt.setNumberValue(stampTaxAmount);
    	}
    	else {
    		txtStampTaxAmt.setNumberValue(null);
    	}
    }
    
    /**
     * 计算保修金额
     *
     */
    private void calGrtAmt() {
    	
    	//使用不计成本的金额,计算保修金额
    	if(isUseAmtWithoutCost){
    		int rowcount = tblDetail.getRowCount();
    		Object newValue = null;
    		for(int i=0;i<rowcount;i++){
    			IRow entryRow = tblDetail.getRow(i);
    			//
    			if(AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())){
    				 newValue = entryRow.getCell(CONTENT_COL).getValue();
    				 break;
    			}
    		}
    		
        	//计算保修金额
        	BigDecimal originalAmount = (BigDecimal)newValue;
    		BigDecimal grtAmount = originalAmount.multiply(txtGrtRate.getBigDecimalValue());
    		grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
    		txtGrtAmount.setNumberValue(grtAmount);
            
    		return ;
    	}
    	
    	//不使用不计成本的金额
    	if(txtamount.getBigDecimalValue() != null && txtGrtRate.getBigDecimalValue() != null) {
    		BigDecimal grtAmount = txtamount.getBigDecimalValue().multiply(txtGrtRate.getBigDecimalValue());
    		grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
    		txtGrtAmount.setNumberValue(grtAmount);
    	}
    	else {
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
    }
    
    protected void txtGrtRate_dataChanged(DataChangeEvent e) throws Exception {
    	
    	super.txtGrtRate_dataChanged(e);
    	calLocalAmt();
    	calGrtAmt();
    }
    
    protected void verifyInputForSave() throws Exception {
    	
    	FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
    	
        super.verifyInputForSave();

        FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);
        checkProjStatus();
    }
    
    public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
    	super.actionViewContent_actionPerformed(e);
    	
    	ContractClientUtils.viewContent(this, getSelectBOID());
    }
    
    public void actionViewCost_actionPerformed(ActionEvent e) throws Exception
    {
		String selectBOID = getSelectBOID();
		if(selectBOID == null) {
			MsgBox.showWarning(this, "合同还未保存");
			SysUtil.abort();
		}
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, selectBOID);
		uiContext.put(FDCConstants.FDC_INIT_PROJECT, curProject);
		
		ContractCostInfoUI.showEditUI(this, uiContext, getOprtState());
    }
    
    protected void tblDetail_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent evt) throws Exception
    {
        KDTable table = (KDTable) evt.getSource();
        IRow entryRow = table.getRow(evt.getRowIndex());
        //ICell cell = entryRow.getCell(evt.getColIndex());

        Object newValue = evt.getValue();
        IColumn col = table.getColumn(evt.getColIndex());
        String colKey = col.getKey();
        
        //内容发生修改
        if(colKey.equals("content") && ( newValue instanceof BigDecimal)
        		&& txtStampTaxRate.getBigDecimalValue()!=null){
        	
        	
        	if(isUseAmtWithoutCost && AM_ROW.equals(entryRow.getCell(ROWKEY_COL).getValue())){
        		//
        		if(mainContractId != null){
        			MsgContractHasSplit();
        		}
        		detailAmount = (BigDecimal)newValue;
            	//计算印花税
            	BigDecimal originalAmount = (BigDecimal)newValue;
        		BigDecimal stampTaxAmount = originalAmount.multiply(txtStampTaxRate.getBigDecimalValue());
        		stampTaxAmount = stampTaxAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
        		txtStampTaxAmt.setNumberValue(stampTaxAmount);
        		
        		//计算保修金额
        		BigDecimal grtAmount = originalAmount.multiply(txtGrtRate.getBigDecimalValue());
        		grtAmount = grtAmount.divide(FDCConstants.B100, BigDecimal.ROUND_HALF_UP);
        		txtGrtAmount.setNumberValue(grtAmount);
        	}
        }
        
    }
    public void MsgContractHasSplit() throws EASBizException, BOSException{
    	if(this.checkContractHasSplit(mainContractId))
			MsgBox.showInfo(this,"该非单独计算的补充合同金额审批后会追加到对应主合同签约金额中，如主合同拆分已被引用请先删除或者作废该合同拆分。");
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
			MsgBox.showWarning(this, ContractClientUtils.getRes("noRemove"));
			SysUtil.abort();
		}
    }
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionCopy_actionPerformed(e);
    	chkCostSplit.setEnabled(true);
    	editData.setSplitState(null);
    	this.pkbookedDate.setValue(this.bookedDate);
    	editData.setSourceType(SourceTypeEnum.ADDNEW);
    	//this.cbPeriod.setValue(this.curPeriod);
    }
    
    protected void updateButtonStatus() {
    	super.updateButtonStatus();
    	
//		 如果是虚体成本中心，则不能拆分
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.menuItemViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
    	this.btnViewContent.setIcon(EASResource.getIcon("imgTbtn_seeperformance"));
		this.menuItemSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		this.btnContractPlan.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
		btnSplit.setIcon(FDCClientHelper.ICON_SPLIT);
		btnDelSplit.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		menuItemViewContent.setText(menuItemViewContent.getText()+"(V)");
		menuItemViewContent.setMnemonic('V');
		menuItemSplit.setText(menuItemSplit.getText()+"(S)");
		menuItemSplit.setMnemonic('S');
    }
    
    protected void checkRef(String id)  throws Exception {
    	super.checkRef(id);
    	
    	ContractClientUtils.checkContractBillRef(this, id);
    	
    	if(!splitBeforeAudit){    		
    		boolean hasSettleSplit = checkContractHasSplit(id);
    		if (hasSettleSplit) {
    			MsgBox.showWarning("合同已经拆分,不能反审批!");
    			SysUtil.abort();
    			return;
    		}
    	}
    }
    private boolean checkContractHasSplit(String id) throws EASBizException, BOSException{
    	FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("contractBill.id", id));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = false;
		if (ContractCostSplitFactory.getRemoteInstance().exists(filterSett)
				|| ConNoCostSplitFactory.getRemoteInstance()
				.exists(filterSett)) {
			hasSettleSplit = true;
		}
		return hasSettleSplit;
    }
 
    /**
     * 覆盖抽象类处理编码规则的行为,统一在FDCBillEditUI.handCodingRule方法中处理
     */
    protected void setAutoNumberByOrg(String orgType) {
    	
    }
    
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {

		if(STATUS_ADDNEW.equals(this.oprtState)){
			handleCodingRule1();
		}
	}
	private void handleCodingRule1(){
		String orgId = this.orgUnitInfo.getId().toString(); 
		String bindingProperty = "codeType.number";
		try {
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				// 当前用户所属组织不存在时，缺省实现是用集团的
				orgId = OrgConstants.DEF_CU_ID;
			}			
			//设置编码类型
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			ContractTypeInfo cti = editData.getContractType();
			if(editData.getContractType()!=null){
				if(cti.getLevel()!=1){
					//取一级合同类别
					String number = editData.getContractType().getLongNumber();
					if(number.indexOf("!") != -1) {
						number = number.substring(0,number.indexOf("!"));
					}
					
					cti = ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select id where longNumber = '" + number + "'");
				}
			}
			ContractCodingTypeCollection cctc = null;
			if(cti!=null){
				filter.getFilterItems().add(new FilterItemInfo("contractType.id", cti.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));//所有合同,因为现在尚不知合同性质
				filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));//合同属于新增状态
				evi.setFilter(filter);
				cctc = ContractCodingTypeFactory.getRemoteInstance().getContractCodingTypeCollection(evi);
			}
			
			
			ContractCodingTypeInfo codingType = null;
			ContractCodingTypeInfo codingTypeAll = null;
			if(cctc!=null && cctc.size()>0)
				codingType = cctc.get(0);
			
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractType.id", null));
			//新建的话，所有合同
			filter.getFilterItems().add(new FilterItemInfo("secondType", ContractSecondTypeEnum.OLD_VALUE));
			//新增状态
			filter.getFilterItems().add(new FilterItemInfo("thirdType", ContractThirdTypeEnum.NEW_VALUE));
			evi.setFilter(filter);
			cctc = ContractCodingTypeFactory.getRemoteInstance().getContractCodingTypeCollection(evi);
			if(cctc.size()>0)
				codingTypeAll = cctc.get(0); 
			
			/************
			 * 对于合同的编码规则，可以按照合同类型设置编码规则，还包括一个"全部"的编码类型
			 * 如果同时设置了全部，和单个类型的编码规则，会有冲突
			 * 所以在这里加入了下面这段代码
			 * 当合同的编码类型为某一个具体的类型时，上面的代码先取这个具体的类型对应的编码规则，如果没有取到[说明是没有设置编码规则，或没有启用]
			 * 则再取一遍"全部"类型对应的编码规则
			 * 即: 当明细的合同类型，没有设置编码规则的时候，取全部类型对应的编码规则[当在合同修改界面，修改合同类型时,会走此处]
			 */
			
			/****
			 * 成本中心，一级类型有对应的编码规则
			 */
			if(codingType!=null){
				editData.setCodeType(codingType);
				if(setNumber( iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			/***
			 * 成本中心，全部类型对应有编码规则
			 */
			if(codingTypeAll!=null){
				editData.setCodeType(codingTypeAll);
				if(setNumber( iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			orgId = this.company.getId().toString();
			/****
			 * 财务组织，一级类型有对应的编码规则
			 */
			if(codingType!=null){
				editData.setCodeType(codingType);
				if(setNumber( iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			/***
			 * 财务组织，全部类型对应有编码规则
			 */
			if(codingTypeAll!=null){
				editData.setCodeType(codingTypeAll);
				if(setNumber( iCodingRuleManager, orgId, bindingProperty))
					return;
			}
			this.txtNumber.setEnabled(true);
			this.txtNumber.setText(null);
			
		}catch (Exception err) {
			// 把放编码规则的TEXT控件的设置为可编辑状态，让用户可以输入
			setNumberTextEnabled();
			handUIExceptionAndAbort(err);
		}
	}
	
	protected boolean setNumber(ICodingRuleManager iCodingRuleManager,String orgId,String bindingProperty) throws CodingRuleException, EASBizException, BOSException{

			if (iCodingRuleManager.isExist(this.editData, orgId, bindingProperty)) {
				String number = "";	
				if(editData.getNumber()==null){
					if (iCodingRuleManager.isUseIntermitNumber(editData, orgId, bindingProperty)) {		// 编码规则中不允许断号					
						// 编码规则中启用了“断号支持”功能，此时只是读取当前最新编码，真正的抢号在保存时
						
						number = iCodingRuleManager.readNumber(editData, orgId, bindingProperty, "");	
//						 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
						 prepareNumber(editData, number);		
					} else if (iCodingRuleManager.isAddView(editData, orgId, bindingProperty)){			// 编码规则中启用了“新增显示”			
						number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
//						 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
						prepareNumber(editData, number);		
					} else {				
						this.txtNumber.setEnabled(false);
					}	
				}	
				if (iCodingRuleManager.isModifiable(editData, orgId, bindingProperty)) {
					// 如果启用了用户可修改
					setNumberTextEnabled();
				}else{
					this.txtNumber.setEnabled(false);
				}
				return true;
			}
		return false;
	}
	
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
		
		if(STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
			ContractClientUtils.checkSplitedForAmtChange(this, getSelectBOID());
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
				
				MsgBox.showWarning(this, ContractClientUtils.getRes("splitedNotChangeIsCSplit"));
				
				SysUtil.abort();
			}
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {

		//如果启用财务一体化,必须录入期间
		if(this.isIncorporation && cbPeriod.getValue()==null){
			MsgBox.showConfirm2(this,"启用成本月结,必须录入期间");
			SysUtil.abort();
		}

    	FDCClientVerifyHelper.verifyEmpty(this, prmtcontractType);
        super.verifyInputForSave();
        FDCClientVerifyHelper.verifyEmpty(this, txtcontractName);
        
		super.verifyInput(e);
//		FDCClientVerifyHelper.verifyEmpty(this, prmtlandDeveloper);
//		FDCClientVerifyHelper.verifyEmpty(this, prmtpartB);
		
		/*
		 * 补充合同必须录入主合同编码
		 */
		if(editData.getContractPropert() == ContractPropertyEnum.SUPPLY) {
			ContractBillEntryCollection entrys = editData.getEntrys();
			boolean hasMainContNum = false;
			for (Iterator iter = entrys.iterator(); iter.hasNext();) {
				ContractBillEntryInfo element = (ContractBillEntryInfo) iter.next();
				String rowKey = element.getRowKey();
				if(rowKey != null && rowKey.equals(NU_ROW) && element.getContent() != null && element.getContent().length() > 0) {
					hasMainContNum = true;
					break;
				}
			}
			
			if(!hasMainContNum) {
				MsgBox.showWarning(this, "补充合同必须录入主合同编码(在合同详细信息中录入)");
				SysUtil.abort();
			}
		}
		
		if(!chkCostSplit.isSelected()){
			MsgBox.showInfo(this,ContractClientUtils.getRes("NotEntryCost"));
		}
		
		checkStampMatch();
	
		checkProjStatus();
		
	}

	protected boolean checkCanSubmit() throws Exception {		
		if(isIncorporation && ((FDCBillInfo)editData).getPeriod()==null){
			MsgBox.showWarning(this,"启用成本月结期间不能为空，请在基础资料维护期间后，重新选择业务日期");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	
	protected void verifyInputForSubmint()throws Exception {
		
		checkAmout();
		//预算控制
		checkMbgCtrlBalance();
		super.verifyInputForSubmint();
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
						MsgBox.showWarning(this, "该合同所在的工程项目已经处于关闭状态，不能进入动态成本，请取消\"进入动态成本\"的选择再保存/提交");
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
		BigDecimal amount = editData.getAmount() == null ? FDCHelper.ZERO : editData.getAmount();
		BigDecimal stampTaxRate = editData.getStampTaxRate() == null ? FDCHelper.ZERO : editData.getStampTaxRate();
		if(editData.getStampTaxAmt() != null && editData.getAmount() != null && stampTaxAmt.compareTo(amount.multiply(stampTaxRate).divide(FDCConstants.B100, 2, BigDecimal.ROUND_HALF_UP)) != 0) {
			int result = MsgBox.showConfirm2(this, ContractClientUtils.getRes("stampNotMatchAmt"));
			if(result == MsgBox.NO || result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
	}
	
    /**
     * 检查金额
     * @throws BOSException 
     * @throws EASBizException 
     *
     */
    private void checkAmout() throws EASBizException, BOSException {
    	String projectId = editData.getCurProject().getId().toString();
    	
    	if(editData.isIsCoseSplit() && (FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)
    			|| FDCContractParamUI.RD_CONTROL.equalsIgnoreCase(controlType) ) ){
    		if(editData.getAmount()==null){
    			editData.setAmount(FDCConstants.ZERO);
    		}
    		BigDecimal amiCost  = null;
    		//获取成本控制金额
    		String msg = null;
    		if(!FDCContractParamUI.RD_DYMIC.equalsIgnoreCase(controlCost)){
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getAimCost(projectId);
    			msg = "目标成本";
    		}else{
    			amiCost  = FDCCostRptFacadeFactory.getRemoteInstance().getDynCost(projectId);
    			msg = "动态成本";
    		}
    		if(amiCost==null) amiCost = FDCConstants.ZERO;
    		
    		//获取项目合同签约总金额，审核状态的数据
    		String id = editData.getId()!=null? editData.getId().toString():null;
    		BigDecimal signAmount  = ContractFacadeFactory.getRemoteInstance().getProjectAmount(projectId,id); 
    		if(signAmount==null) signAmount = FDCConstants.ZERO;
    		
    		if(amiCost.compareTo(signAmount.add(editData.getAmount()))<0){
    			//提示  严格控制
    			FDCMsgBox.showWarning(this,"项目合同签约总金额已经超过了"+msg);
	    		if(!FDCContractParamUI.RD_MSG.equalsIgnoreCase(controlType)){
	    			SysUtil.abort();
	    		}
    		}
    		
    	}
    	
    }
	
	public void actionContractPlan_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionContractPlan_actionPerformed(e);
		String selectBOID = getSelectBOID();
		if(selectBOID == null) {
			MsgBox.showWarning(this, "合同还未保存，请先保存合同，再维护合同付款计划");
			SysUtil.abort();
		}
		ContractPayPlanEditUI.showEditUI(this, selectBOID, getOprtState());
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		//保存前反写所关联的框架合约“是否引用”字段
		updateOldProg();
		//维护源ID用于动态规划
		if(this.editData.getProgrammingContract() != null){
			this.editData.setSrcProID(this.editData.getProgrammingContract().toString());
		}
		super.actionSave_actionPerformed(e);
		//保存后反写写所关联的框架合约状态
		updateNewProg();
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null 
				&& isFromWorkflow.toString().equals("true") 
				&& getOprtState().equals(STATUS_EDIT)){
			btnSubmit.setEnabled(false);
			btnCopy.setEnabled(false);
			btnRemove.setEnabled(false);
			btnAddLine.setEnabled(false);
			btnRemoveLine.setEnabled(false);
		}
	}
	/**
	 * 检查合同是否关联框架合约
	 * @return
	 * @throws Exception
	 */
	private String checkReaPre() throws Exception {
		String billId = null;
		if(editData.getId() != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("programmingContract");
			ContractBillInfo conInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getId()),sic);
			if(conInfo.getProgrammingContract() != null){
				billId = conInfo.getProgrammingContract().getId().toString();
			}
		}
//		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql("select fprogrammingContract from T_CON_CONTRACTBILL where 1=1 and ");
//		builder.appendParam("fid", editData.getId().toString());
//		IRowSet rowSet = builder.executeQuery();
//		while (rowSet.next()) {
//			billId = rowSet.getString("fprogrammingContract");
//		}
		return billId;
		
	}
	/**
	 * 更新老框架合约是否被引用
	 * @throws Exception
	 */
	private void updateOldProg() throws Exception{
		String checkReaPre = checkReaPre();
		while (checkReaPre != null) {
			int count = 0;// 关联合约数
			int linkInviteProject = isCitingByInviteProject(checkReaPre);// 本合约关联招标立项数
			int linkContractBill = isCitingByContractBill(checkReaPre);// 本合约关联合同数
			int linkContractWithoutText = isCitingByContractWithoutText(checkReaPre);// 本合约关联无文本合同招立项数
			count = linkInviteProject + linkContractBill
					+ linkContractWithoutText;
			boolean isCiting = preVersionProg(checkReaPre);
			if (count <= 1 && !isCiting) {
				updateProgrammingContract(checkReaPre, 0);
			}
			checkReaPre = isUpdateNextProgState(checkReaPre) ;
		}
		updateNewProg();
		
	}
	private String isUpdateNextProgState(String progId)throws Exception{
		String flag = null;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select fid from t_con_programmingContract where ");
		builder.appendParam("fSrcId", progId);
		IRowSet rowSet = builder.executeQuery();
		while(rowSet.next()){
			flag = rowSet.getString("fid").toString();
		}
		return flag;
	}
	/**
	 * 更新新框架合约是否被引用
	 * @throws Exception
	 */
	private void updateNewProg() throws Exception{
		if(editData.getProgrammingContract() != null){
			String proId = editData.getProgrammingContract().getId().toString();
			while(proId != null){
				updateProgrammingContract(proId,1);
				proId = isUpdateNextProgState(proId) ;
			}
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {

		super.actionSubmit_actionPerformed(e);
		if(this.getOprtState().equals("ADDNEW")){
			this.actionSave.setEnabled(true);
		}
		handleOldData();
	}
	   // 提交时，控制预算余额
    private void checkMbgCtrlBalance() throws BOSException, EASBizException {
		//根据参数是否显示合同费用项目
		boolean isShowCharge = false;
		try {
			isShowCharge = FDCUtils.getDefaultFDCParamByKey(null,null,FDCConstants.FDC_PARAM_CHARGETYPE);
		} catch (EASBizException e1) {
			handUIExceptionAndAbort(e1);
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
        if (!isShowCharge || editData.getConChargeType() == null) {
           return;
        }
        StringBuffer buffer = new StringBuffer("");
        editData.setBizDate((Date) this.pkbookedDate.getValue());
		IBgControlFacade iCtrl = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(
				FDCConstants.ContractBill, null, editData);

		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection
						.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if (balance != null
						&& balance.compareTo(bgCtrlResultInfo.getReqAmount()) < 0) {
					buffer.append(
							bgCtrlResultInfo.getItemName() + "("
									+ bgCtrlResultInfo.getOrgUnitName() + ")")
							.append(
									EASResource.getString(
											FDCConstants.VoucherResource,
											"BalanceNotEnagh")
											+ "\r\n");
				}
			}
		}

		if (buffer.length() > 0) {
			int result = MsgBox.showConfirm2(this, buffer.toString()
					+ "\r\n"
					+ EASResource.getString(FDCConstants.VoucherResource,
							"isGoOn"));
			if (result == MsgBox.CANCEL) {
				SysUtil.abort();
			}
		}
    }
	protected boolean isUseMainMenuAsTitle(){
    	return false;
    }
	
	protected void fillCostPanel()  throws Exception{		
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
			public void run() {
				try {
					fillCostPanelByAcct();
				} catch (Exception e) {
					handUIExceptionAndAbort(e);
				}
			}
		});
		
	}
	ContractCostHelper helper = null;
	//惰性加载costPanel
	protected void fillCostPanelByAcct()  throws Exception{
		
		if(editData.getSplitState()==null || CostSplitStateEnum.NOSPLIT.equals(editData.getSplitState())){
			return ;
		}
		//aimCost hasHappen intendingHappen dynamicCost
		FDCHelper.formatTableNumber(tblCost, "aimCost");
		FDCHelper.formatTableNumber(tblCost, "hasHappen");
		FDCHelper.formatTableNumber(tblCost, "intendingHappen");
		FDCHelper.formatTableNumber(tblCost, "dynamicCost");
		FDCHelper.formatTableNumber(tblCost, "chayi");
		
		Map acctMap = new HashMap();
		//获取当前合同的拆分科目			
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id",editData.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("parent.isInvalid",new Integer(0)));
		view.setFilter(filter);
		ContractCostSplitEntryCollection col = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
		for (Iterator iter = col.iterator(); iter.hasNext();) {
			ContractCostSplitEntryInfo element = (ContractCostSplitEntryInfo) iter.next();
			
			String costAcctId = element.getCostAccount().getId().toString();
			if(acctMap.containsKey(costAcctId)){
				continue ;
			}	
			acctMap.put(costAcctId,element);
		}		
		//使用新接口  by sxhong 2009-06-11 09:13:59	
		helper = new ContractCostHelper(tblCost,editData.getId().toString());
		helper.fillCostTable() ;
	}
	
    protected void tblCost_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if(helper==null) return ;
    	helper.tblCost_tableClicked(this,e);
    }
    
	/*
	 *  在FDCBillEdit内将其改成抽象方法,以强制要求子类去实现
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#getDetailTable()
	 */
	protected KDTable getDetailTable() {
		return null;
	}
	
	/**
	 * RPC改造，任何一次事件只有一次RPC
	 */
	
	public boolean isPrepareInit() {
		
    	return true;
    }
	
	public boolean isPrepareActionSubmit() {
		//FDCClientVerifyHelper.verifyRequire(this);
    	return false;
    }
	
	public boolean isPrepareActionSave() {
		//FDCClientVerifyHelper.verifyRequire(this);
    	return false;
    }
	
	public RequestContext prepareActionSubmit(IItemAction itemAction)
	throws Exception {
		RequestContext request = super.prepareActionSubmit(itemAction);
		
		return request;
	}
	
    /**
     * EditUI提供的初始化handler参数方法
     */
    protected void PrepareHandlerParam(RequestContext request)
    {
        super.PrepareHandlerParam(request);
        

    }
  public void actionProgram_actionPerformed(ActionEvent e) throws Exception {
		showProgramContract();
		// 关联框架合约的相关信息自动带入到合同中
		autoProgToCon();
  }
  	/**
	 * 关联框架合约的相关信息自动带入到合同中
	 */
	private void autoProgToCon() {
		ProgrammingContractInfo pcInfo = (ProgrammingContractInfo) this.editData.getProgrammingContract();
		if (pcInfo != null) {
			this.txtcontractName.setText(pcInfo.getName());
			this.txtNumber.setText(pcInfo.getNumber());
			this.txtamount.setValue(pcInfo.getControlBalance());
		}
	}
	/**
	 * 关联框架合约
	 * @throws Exception 
	 */
	private void showProgramContract() throws Exception {
		ProgrammingContractInfo pc = (ProgrammingContractInfo) this.editData
				.getProgrammingContract();
		String temp = this.textFwContract.getText();
		if (pc != null && this.editData.getId() != null) {
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select con.fid from t_con_contractbill con ");
			builder
					.appendSql(" inner join T_INV_AcceptanceLetter accep on accep.fid = con.fsourcebillid");
			builder
					.appendSql(" inner join t_inv_inviteProject invite on invite.fid = accep.FInviteProjectID where ");
			builder
					.appendSql("  con.fprogrammingcontract = invite.fprogrammingcontractid and ");
			builder.appendParam("con.fid", this.editData.getId().toString());
			IRowSet rowSet = builder.executeQuery();
			if (rowSet.next()) {
				FDCMsgBox.showInfo("框架合约由招标通知书带入，不可修改");
				this.abort();
			}
		}
		IUIWindow uiWindow = null;
		UIContext uiContext = new UIContext(this);
		ContractBillInfo contractBillInfo = editData;
		if(pc != null){
			SelectorItemCollection sicw = new SelectorItemCollection();
			sicw.add("*");
			pc = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(pc.getId()), sicw);
			contractBillInfo.setProgrammingContract(pc);
		}
		uiContext.put("contractBillInfo", contractBillInfo);
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				ContractBillLinkProgContEditUI.class.getName(), uiContext,
				null, OprtState.EDIT);
		uiWindow.show();
		if (contractBillInfo.getProgrammingContract() != null) {
			this.textFwContract.setText(contractBillInfo
					.getProgrammingContract().getName());
		}
	}
	/*
	 * 查看合同是否关联框架合约
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillEditUI#actionViewProgramCon_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionViewProgramCon_actionPerformed(ActionEvent e)
			throws Exception {
		//TODO 合同关联
		if(this.editData.getProgrammingContract() == null){
			MsgBox.showInfo(this, "该合同没有关联框架合约!");
			this.abort();
		}
		
		UIContext uiContext = new UIContext(this);
    	IUIWindow uiWindow = null;
    	ContractBillInfo contractBillInfo = (ContractBillInfo) editData;
    	ProgrammingContractInfo proContInfo = getProContInfo(contractBillInfo.getProgrammingContract().getId().toString());
    	contractBillInfo.setProgrammingContract(proContInfo);
    	uiContext.put("programmingContract", proContInfo);
    	uiContext.put("programmingContractTemp","programmingContractTemp");
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProgrammingContractEditUI.class.getName(), uiContext, null,
				OprtState.VIEW);
		uiWindow.show();
	}
	/**
	 * 获取所关联的规划合约
	 * 
	 * @param id
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private ProgrammingContractInfo getProContInfo(String id) throws EASBizException, BOSException {
		SelectorItemCollection selItemCol = new SelectorItemCollection();
		selItemCol.add("*");
		selItemCol.add("costEntries.*");
		selItemCol.add("costEntries.costAccount.*");
		selItemCol.add("costEntries.costAccount.curProject.*");
		selItemCol.add("economyEntries.*");
		selItemCol.add("economyEntries.paymentType.*");
		ProgrammingContractInfo pcInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(id),
				selItemCol);
		return pcInfo;
	}
	/**
	 * 找出招标立项所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByInviteProject(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_INV_InviteProject ");
		buildSQL.appendSql("where FProgrammingContractId = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 找出合同所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByContractBill(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_CON_ContractBill ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 找出无文本合同所关联的框架合约的记录数
	 * 
	 * @param proContId
	 * @return
	 */
	private int isCitingByContractWithoutText(String proContId) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("select count(*) count from T_CON_ContractWithoutText ");
		buildSQL.appendSql("where FProgrammingContract = '" + proContId + "' ");
		int count = 0;
		try {
			IRowSet iRowSet = buildSQL.executeQuery();
			if (iRowSet.next()) {
				count = iRowSet.getInt("count");
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		return count;
	}

	/**
	 * 更新规划合约"是否被引用"字段
	 * 
	 * @param proContId
	 * @param isCiting
	 */
	private void updateProgrammingContract(String proContId, int isCiting) {
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql("update T_CON_ProgrammingContract set FIsCiting = " + isCiting + " ");
		buildSQL.appendSql("where FID = '" + proContId + "' ");
		try {
			buildSQL.executeUpdate();
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	private boolean preVersionProg(String progId) throws BOSException, SQLException{
		boolean isCityingProg = false;
		int tempIsCiting = 0;
		FDCSQLBuilder buildSQL = new FDCSQLBuilder();
		buildSQL.appendSql(" select t1.FIsCiting isCiting from t_con_programmingContract t1 where t1.fid = (");
		buildSQL.appendSql(" select t2.FSrcId from t_con_programmingContract t2 where t2.fid = '"+progId+"')");
		IRowSet rowSet = buildSQL.executeQuery();
		while(rowSet.next()){
			tempIsCiting = rowSet.getInt("isCiting");
		}
		if(tempIsCiting > 0 ){
			isCityingProg = true;
		}
		return isCityingProg;
	}
}