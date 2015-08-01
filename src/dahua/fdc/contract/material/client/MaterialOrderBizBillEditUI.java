/**
 * output package name
 */
package com.kingdee.eas.fdc.material.client;


import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxCollection;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateCollection;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.org.client.OrgInnerUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.MaterialPromptSelector;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.material.MaterialBillStateEnum;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillCollection;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory;
import com.kingdee.eas.util.AbortException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 材料订货单 编辑界面
 * 
 * @Modified By owen_wen 2011-01-10
 */
public class MaterialOrderBizBillEditUI extends AbstractMaterialOrderBizBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MaterialOrderBizBillEditUI.class);
    
    //本币金额
    private BigDecimal sumAmount = FDCHelper.ZERO;
    
    /**
     * output class constructor
     */
    public MaterialOrderBizBillEditUI() throws Exception
    {
        super();
    }
    
   
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception{
    	FDCBillStateEnum state = editData.getState();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL)) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	getAssEntryData();
        super.actionSave_actionPerformed(e);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	getAssEntryData();
        super.actionSubmit_actionPerformed(e);
        if(getOprtState().equals(OprtState.ADDNEW)){
        	KDTable table=this.kdtEntrys; //tblMain
    		IRow footRow = null;
    		if(null != table.getFootRow(0)){ //如果不存在表脚列
    			footRow = table.getFootRow(0);
    			footRow.getCell("amount").setValue(FDCHelper.ZERO);
    		}
        }
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {		
    	MaterialOrderBizBillEntryInfo entryInfo = new MaterialOrderBizBillEntryInfo();
    	try {
			entryInfo.setCurrency(getRMBCurrency());
			entryInfo.setExRate(FDCConstants.ONE); 
		} catch (BOSException e) {
			this.handleException(e);
		}
        return entryInfo;
    }

    
    public void carryMaterialName()throws Exception{
    	this.prmtsuppliers.setValue(prmtsuppliers.getValue());
		for(int i=0;i<kdtEntrys.getRowCount();i++){
		  if (kdtEntrys.getCell(i,"materialNumber").getValue()!=null) {
				  kdtEntrys.getCell(i,"materialNumber").setValue(kdtEntrys.getCell(i,"materialNumber").getValue());
				  MaterialInfo materInfo = (MaterialInfo)kdtEntrys.getCell(i,"materialNumber").getValue();
				  if(materInfo != null){
					  kdtEntrys.getCell(i,"materialName").setValue(materInfo.getName());
					  kdtEntrys.getCell(i,"materialModel").setValue(materInfo.getModel());
				  }
		  }
		}
		
	}
    
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo objectValue = new com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
//        objectValue.setNumber(getAutoCode());
        objectValue.setBizDate(new Date());
        return objectValue;
    }
    
 // 是否存在编码规则
	protected boolean isCodeRuleEnable(IObjectValue objValue) throws EASBizException, BOSException { 
		String companyId = OrgInnerUtils.getCurCompany();
		ICodingRuleManager codeRuleMgr = null;
		codeRuleMgr  = CodingRuleManagerFactory.getRemoteInstance();
		return codeRuleMgr.isExist(objValue, companyId);
	}

	// 得到自动编码
	protected String getAutoCode(IObjectValue objValue) throws EASBizException, BOSException { 
		String companyId = OrgInnerUtils.getCurCompany();
		ICodingRuleManager codeRuleMgr = null;
		codeRuleMgr = CodingRuleManagerFactory.getRemoteInstance();
		if (codeRuleMgr.isUseIntermitNumber(objValue, companyId)) {
			return codeRuleMgr.readNumber(objValue, companyId);
		} else {
			return codeRuleMgr.getNumber(objValue, companyId);
		}
	}

	
	public void onLoad() throws Exception {
		super.onLoad();	
		this.btnAudit.setVisible(false);
		this.btnUnAudit.setVisible(false);
		//by tim_gao
		if(getOprtState().equals(OprtState.EDIT) && FDCBillStateEnum.getEnum(FDCBillStateEnum.SUBMITTED_VALUE).equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		if(getOprtState().equals(OprtState.VIEW) || getOprtState().equals(OprtState.EDIT)){
			this.txtNumber.setEnabled(false);
			
		}
		if(getOprtState().equals(OprtState.VIEW) && !getOprtState().equals(OprtState.EDIT)){
			this.importMes.setEnabled(false);
		}
		setComponentValue();
		setComponentState();
		this.txtNumber.setRequired(true);
		//材料订货单的分录的“送货地点”字段。取值相对应的材料进场计划单中的项目地址，可修改。 by cassiel 2010-08-26
		this.oldData=this.getEditData();
		//tim_gao 排掉新增方法
		if(getOprtState().equals(OprtState.ADDNEW))
		{
			this.importMes.setEnabled(true);
			this.actionImportMes.setEnabled(true);
		}
		initAllAmount();
		initEntityTable();
	}
	
	//责任人是否可以选择其他部门的人员
	private boolean canSelectOtherOrgPerson = false;
	public void setComponentState(){
		btnPrice.setVisible(false);
		this.btnCopy.setVisible(false);
		this.menuView.setVisible(false);
		txtDescription.setMaxLength(255);
		if(getOprtState().equals(OprtState.ADDNEW)||getOprtState().equals(OprtState.COPYADDNEW)){
			billState.setSelectedItem(MaterialBillStateEnum.NONE_ARRIVED); //新增单据时设置状态为未到货状态
		}
		//初始化F7
	    FDCClientUtils.initSupplierF7(this, prmtsuppliers, SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());	
	    KDBizPromptBox deptPromptBox = (KDBizPromptBox)kdtEntrys.getColumn("dept").getEditor().getComponent();
	    FDCClientUtils.setRespDeptF7(deptPromptBox, this,SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
	    KDBizPromptBox contractUnitPrmt = (KDBizPromptBox)kdtEntrys.getColumn("contractUnit").getEditor().getComponent();
	    FDCClientUtils.initSupplierF7(this,contractUnitPrmt,SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
	    KDBizPromptBox planPersonPrmt = (KDBizPromptBox)kdtEntrys.getColumn("planUser").getEditor().getComponent();
	    FDCClientUtils.setPersonF7(planPersonPrmt,this,SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
	    FDCClientUtils.initCurProjectF7(kdtEntrys);
	    
		billState.setEnabled(false);
		btnWorkFlowG.setVisible(false);
		btnCreateFrom.setVisible(false);
		btnCreateTo.setVisible(false);
		btnMultiapprove.setVisible(false);
		btnNextPerson.setVisible(false);
		separatorFW7.setVisible(false);
		
		this.actionAddLine.setVisible(true);
		this.actionAddLine.setEnabled(true);
		
		this.kdtEntrys.getColumn("materialName").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("materialName").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("materialModel").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("materialModel").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("unit").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("unit").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("rate").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("rate").getStyleAttributes().setLocked(true);
		
		KDFormattedTextField qty = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		qty.setPrecision(2);
		KDTDefaultCellEditor qtyEditor = new KDTDefaultCellEditor(qty);
		this.kdtEntrys.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("quantity").setEditor(qtyEditor);
		this.kdtEntrys.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("price").setEditor(qtyEditor);
		this.kdtEntrys.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("amount").setEditor(qtyEditor);
		this.kdtEntrys.getColumn("originalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtEntrys.getColumn("originalAmount").setEditor(qtyEditor);
		
		this.kdtEntrys.getColumn("contractUnit").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("contractUnit").setRequired(false);
		this.kdtEntrys.getColumn("contractUnit").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("contractBill").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("contractBill").setRequired(false);
		this.kdtEntrys.getColumn("contractBill").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtEntrys.getColumn("materialContract").getStyleAttributes().setLocked(true);
		this.kdtEntrys.getColumn("materialContract").setRequired(false);
		this.kdtEntrys.getColumn("materialContract").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		
		
		this.kdtAssEntrys.getColumn("materialNumber").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("materialNumber").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("materialName").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("materialName").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("materialModel").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("materialModel").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("unit").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("unit").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("quantity").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("quantity").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("price").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("price").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("currency").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("currency").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("rate").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("rate").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("amount").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("amount").getStyleAttributes().setLocked(true);
		this.kdtAssEntrys.getColumn("originalAmount").getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
		this.kdtAssEntrys.getColumn("originalAmount").getStyleAttributes().setLocked(true);

		this.kdtAssEntrys_detailPanel.getAddNewLineButton().setVisible(false);
		this.kdtAssEntrys_detailPanel.getInsertLineButton().setVisible(false);
		this.kdtAssEntrys_detailPanel.getRemoveLinesButton().setVisible(false);	
		
		//隐藏行明细分录表右上角的3个按钮，因为新增和插入分录时，无法为分录设置默认的币别  Added by Owen_wen 2010-09-13
		this.kdtEntrys_detailPanel.getAddNewLineButton().setVisible(false);
		this.kdtEntrys_detailPanel.getInsertLineButton().setVisible(false);
		this.kdtEntrys_detailPanel.getRemoveLinesButton().setVisible(false);
	}
	
	public void setComponentValue()
	{		
        KDBizPromptBox kdtEntrys_materialNumber_PromptBox = new KDBizPromptBox();        
//        kdtEntrys_materialNumber_PromptBox.setQueryInfo("com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery");
        kdtEntrys_materialNumber_PromptBox.setSelector(new MaterialPromptSelector(this));  // 使用自定义左树右表的F7 Added by owen_wen 2010-8-27
        kdtEntrys_materialNumber_PromptBox.setVisible(true);
        kdtEntrys_materialNumber_PromptBox.setEditable(true);
        kdtEntrys_materialNumber_PromptBox.setDisplayFormat("$number$");
        kdtEntrys_materialNumber_PromptBox.setEditFormat("$number$");
        kdtEntrys_materialNumber_PromptBox.setCommitFormat("$number$");
        KDTDefaultCellEditor kdtEntrys_materialNumber_CellEditor = new KDTDefaultCellEditor(kdtEntrys_materialNumber_PromptBox);
        this.kdtEntrys.getColumn("materialNumber").setEditor(kdtEntrys_materialNumber_CellEditor);
	}
	
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic =super.getSelectors();
		//补充父类的sic
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("entrys.materialNumber.*"));
		sic.add(new SelectorItemInfo("entrys.materialNumber.baseUnit.*"));
		return sic;
	}
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		double quantity = 0.0;
		double price = 0.0;
		double rate = 0.0;
		// 材料订货单，手工新增分录时，选择了工程项目，自动带出该工程项目上的项目地址，可修改。 by cassiel
		if (e.getColIndex() == getDetailTable().getColumn("curProject")
				.getColumnIndex()) {
			Object o = e.getValue();
			if (o != null && o instanceof CurProjectInfo) {
				CurProjectInfo project = (CurProjectInfo) o;
				kdtEntrys.getCell(e.getRowIndex(),"ariverAddr").setValue(
						project.getProjectAddress());
			}
		}
		
    	if (e.getColIndex() == getDetailTable().getColumn(MaterialConfirmContants.MATERIAL_NUMBER).getColumnIndex()) {
				if(e.getValue()!= null){
					if(e.getValue() instanceof MaterialInfo){
						MaterialInfo materialInfo = (MaterialInfo) e.getValue();
						getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.MATERIAL_NAME).setValue(materialInfo.getName());
						getDetailTable().getCell(e.getRowIndex(),"materialModel").setValue(materialInfo.getModel());
						if(materialInfo.getBaseUnit() != null)
						getDetailTable().getCell(e.getRowIndex(),MaterialConfirmContants.UNIT).setValue(MeasureUnitFactory.getRemoteInstance().getMeasureUnitInfo(new ObjectUuidPK(materialInfo.getBaseUnit().getId())));
						MaterialOrderBizBillEntryInfo info = (MaterialOrderBizBillEntryInfo)getDetailTable().getRow(e.getRowIndex()).getUserObject();
						info.setMaterialNumber(materialInfo);
					}
				}
		}
    	if (e.getColIndex() == getDetailTable().getColumn("quantity").getColumnIndex()) {
			if(getDetailTable().getCell(e.getRowIndex(),"quantity").getValue() !=null){//订购数量改变
				quantity = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"quantity").getValue().toString());
			}
			if(getDetailTable().getCell(e.getRowIndex(),"price").getValue() !=null){
				price = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"price").getValue().toString());
			}
			
			if(getDetailTable().getCell(e.getRowIndex(),"rate").getValue() !=null){
				rate = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"rate").getValue().toString());
			}
			
			double originalAmount = quantity*price;
			BigDecimal sumTemp = new BigDecimal(originalAmount*rate);
			getDetailTable().getCell(e.getRowIndex(),"originalAmount").setValue(String.valueOf(originalAmount));
			getDetailTable().getCell(e.getRowIndex(),"amount").setValue(sumTemp);
		}
    	
		if (e.getColIndex() == getDetailTable().getColumn("price").getColumnIndex()) {//单价改变

			if(getDetailTable().getCell(e.getRowIndex(),"quantity").getValue() !=null){
				quantity = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"quantity").getValue().toString());
			}
			if(getDetailTable().getCell(e.getRowIndex(),"price").getValue() !=null){
				price = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"price").getValue().toString());
			}
			if(getDetailTable().getCell(e.getRowIndex(),"rate").getValue() !=null){
				//ExchangeRateInfo obj = (ExchangeRateInfo)getDetailTable().getCell(e.getRowIndex(),"rate").getValue();
				rate = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"rate").getValue().toString());
			}
			double originalAmount = quantity*price;
			double amount = quantity*price*rate;
			BigDecimal sumTemp = new BigDecimal(amount);
			getDetailTable().getCell(e.getRowIndex(),"originalAmount").setValue(String.valueOf(originalAmount));
			getDetailTable().getCell(e.getRowIndex(),"amount").setValue(sumTemp);
		}
		if (e.getColIndex() == getDetailTable().getColumn("currency").getColumnIndex()) {//币别改变带出汇率
						
			if(e.getValue()!= null){
				if(e.getValue() instanceof CurrencyInfo){
					CurrencyInfo currencyInfo = (CurrencyInfo) e.getValue();
					if(currencyInfo.getId() != null){
						EntityViewInfo evi = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo(); //建立过滤条件
						filterInfo.getFilterItems().add(new FilterItemInfo("sourceCurrency.id", currencyInfo.getId(),CompareType.EQUALS));
						evi.setFilter(filterInfo);
						ExchangeAuxInfo auxInfo = null;
						ExchangeAuxCollection auxColl = ExchangeAuxFactory.getRemoteInstance().getExchangeAuxCollection(evi);
						if (UIRuleUtil.isNotNull(auxColl) && auxColl.size() > 0) {
							for(int i=0;i<auxColl.size();i++){
								auxInfo = auxColl.get(i);
								EntityViewInfo evi2 = new EntityViewInfo();
								FilterInfo filterInfo2 = new FilterInfo(); //建立过滤条件
								filterInfo2.getFilterItems().add(new FilterItemInfo("exchangeAux.id", auxInfo.getId(),CompareType.EQUALS));
								evi2.setFilter(filterInfo2);
								ExchangeRateInfo rateInfo = null;
								ExchangeRateCollection rateColl = ExchangeRateFactory.getRemoteInstance().getExchangeRateCollection(evi2);
								if (UIRuleUtil.isNotNull(rateColl) && rateColl.size() > 0) {
									for(int j=0;j<auxColl.size();j++){
										rateInfo = rateColl.get(j);
										if(rateInfo != null){
											getDetailTable().getCell(e.getRowIndex(),"rate").setValue(ExchangeRateFactory.getRemoteInstance().getExchangeRateInfo(new ObjectUuidPK(rateInfo.getId())).getConvertRate());
											
											if(getDetailTable().getCell(e.getRowIndex(),"price").getValue() !=null){
												price = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"price").getValue().toString());
											}
											double rate2 = 0.0;
											rate2 = Double.parseDouble(ExchangeRateFactory.getRemoteInstance().getExchangeRateInfo(new ObjectUuidPK(rateInfo.getId())).getConvertRate().toString());
											double sum = price*rate2;
											BigDecimal sumTemp = new BigDecimal(sum);
											getDetailTable().getCell(e.getRowIndex(),"amount").setValue(sumTemp);
										}
									}
								}
							}
							
						}else{ // 如果没有定义汇率兑换关系时，无论哪种币别（包括人民币），汇率默认全部取值为1
							getDetailTable().getCell(e.getRowIndex(),"rate").setValue(FDCConstants.ONE);
						}
					}
				}
			}		


			if(getDetailTable().getCell(e.getRowIndex(),"quantity").getValue() !=null){
				quantity = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"quantity").getValue().toString());
			}
			if(getDetailTable().getCell(e.getRowIndex(),"price").getValue() !=null){
				price = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"price").getValue().toString());
			}
			if(getDetailTable().getCell(e.getRowIndex(),"rate").getValue() !=null){
				//ExchangeRateInfo obj = (ExchangeRateInfo)getDetailTable().getCell(e.getRowIndex(),"rate").getValue();
				rate = Double.parseDouble(getDetailTable().getCell(e.getRowIndex(),"rate").getValue().toString());
			}
			double originalAmount = quantity*price;
			double amount = quantity*price*rate;
			BigDecimal amountB = new BigDecimal(amount);
			getDetailTable().getCell(e.getRowIndex(),"originalAmount").setValue(String.valueOf(originalAmount));
			getDetailTable().getCell(e.getRowIndex(),"amount").setValue(amountB);
		}
		initAllAmount();
		initEntityTable();
	}

	public void actionImportMes_actionPerformed(ActionEvent e) throws Exception {
		if(this.prmtsuppliers.getValue() == null){
			MsgBox.showWarning(this, "请先选择供应商!");
			SysUtil.abort();
		}
		UIContext importUIContext = new UIContext(this);
		importUIContext.put(UIContext.OWNER, this);
		importUIContext.put("suppliers", this.prmtsuppliers.getValue());
		importUIContext.put("number", this.txtNumber.getText());
		IUIWindow importUIWindow = UIFactory.createUIFactory().create("com.kingdee.eas.fdc.material.client.MaterialOrderSumImportUI", importUIContext, null,"EDIT");
		importUIWindow.show();
	}
	
	
	private void  verifyNumber(String number){
		try {
			MaterialOrderBizBillCollection orderColl  = MaterialOrderBizBillFactory.getRemoteInstance().getMaterialOrderBizBillCollection("where number = '"+number+"'");
			if(orderColl.size()>0){
				MsgBox.showWarning(this, "单据编码已经存在!");
				SysUtil.abort();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		if(this.txtNumber.getText().length() == 0 || txtNumber.getText() == null){
			MsgBox.showWarning(this, "单据编码不能为空!");
			SysUtil.abort();
		}else{
			if(getOprtState().equals(OprtState.ADDNEW)||getOprtState().equals(OprtState.COPYADDNEW)){
				verifyNumber(this.txtNumber.getText());
			}
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);

		
		if(this.prmtsuppliers.getValue() == null){
			MsgBox.showWarning(this, "供应商不能为空!");
			SysUtil.abort();
		}
		if(kdtEntrys.getRowCount() == 0 ){
			MsgBox.showWarning(this, "必须存在一条分录信息!");
			SysUtil.abort();
		}
		//检测分录
		for(int i = 0; i < kdtEntrys.getRowCount(); ++i)
		{
			IRow row = kdtEntrys.getRow(i);
			Integer index = new Integer(i+1);
			String warning = "第" + index.toString() + "行分录 的" ;
			
			if(row.getCell("materialNumber").getValue() == null)
			{
				warning = warning + "物料编码不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("materialName").getValue() == null)
			{
				warning = warning + "物料名称不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("unit").getValue() == null)
			{
				warning = warning + "单位不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("quantity").getValue() == null)
			{
				warning = warning + "订货数量不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("price").getValue() == null)
			{
				warning = warning + "单价不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("currency").getValue() == null)
			{
				warning = warning + "币别不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("rate").getValue() == null)
			{
				warning = warning + "汇率不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("amount").getValue() == null)
			{
				warning = warning + "本币金额不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if(row.getCell("originalAmount").getValue() == null)
			{
				warning = warning + "原币金额不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			if (row.getCell("curProject").getValue() == null) {
				warning = warning + "工程项目不能为空";
				FDCMsgBox.showWarning(this, warning);
				abort();
			} else { // 处理特殊情况，从材料进场汇总导入过来的工程项目是新建的实例，数据库中是不存在的
				if (row.getCell("curProject").getValue() instanceof CurProjectInfo
						&& ((CurProjectInfo) row.getCell("curProject").getValue()).getId() == null) {
					warning = warning + "工程项目不能为空";
					FDCMsgBox.showWarning(this, warning);
					abort();
				}
			}
//			if(row.getCell("contractUnit").getValue() == null)
//			{
//				warning = warning + "施工单位不能为空";
//				FDCMsgBox.showWarning(this,warning);
//				abort();
//			}
//			if(row.getCell("contractBill").getValue() == null)
//			{
//				warning = warning + "施工合同不能为空";
//				FDCMsgBox.showWarning(this,warning);
//				abort();
//			}
//			if(row.getCell("materialContract").getValue() == null)
//			{
//				warning = warning + "材料合同不能为空";
//				FDCMsgBox.showWarning(this,warning);
//				abort();
//			}
			
		}
	}
	
	
	/**
	 * 获取人民币，以供新增分录填充默认币别所用
	 * @return 如果表中有“人民币”，则返回找到的第一个值；若没有找到，返回null
	 * @throws BOSException
	 */
	private CurrencyInfo getRMBCurrency() throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", "人民币"));
		view.setFilter(filter);
		CurrencyCollection cc = CurrencyFactory.getRemoteInstance().getCurrencyCollection(view);
		
		if (cc.size()>0){
			return cc.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 新增分录时，币别默认为人发币，汇率也要一并带出
	 */
//	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		super.actionAddLine_actionPerformed(e);
//		IRow lastRow = kdtEntrys.getRow(kdtEntrys.getRowCount()-1);
////		CurrencyInfo currencyRMB = CurrencyFactory.getRemoteInstance().getCurrencyInfo(BOSUuid.create("dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC"));
//		CurrencyInfo currencyRMB = CurrencyFactory.getRemoteInstance().getCurrencyInfo(new ObjectUuidPK("dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC"));
//		lastRow.getCell("currency").setValue("人民币");
//		lastRow.getCell("rate").setValue(new Integer(1));
//	}
	   
	 protected void paneBIZLayerControl17_mouseClicked(MouseEvent e)
		throws Exception {
		 super.paneBIZLayerControl17_mouseClicked(e);
	 }

	 private void verifyEntriesMaterial(){
		 for(int i=0; i<kdtEntrys.getRowCount(); i++){
			 IRow row = getDetailTable().getRow(i);
			 MaterialInfo materInfo =  (MaterialInfo)row.getCell("materialNumber").getValue();
			 if(materInfo == null){
				 FDCMsgBox.showError("第"+(i+1)+"行分录的物料不能为空");
				 SysUtil.abort();
			 }
		 }
	 }
	 
	protected void paneBIZLayerControl17_stateChanged(ChangeEvent e)
			throws Exception {
		if(paneBIZLayerControl17.getSelectedIndex() == 1){
			try{
				getAssEntryData();
			}catch(AbortException ex){
				paneBIZLayerControl17.setSelectedIndex(0);
			}
		}
	}
	
	private void getAssEntryData(){
		verifyEntriesMaterial();
		kdtAssEntrys.removeRows();
		Map map=new HashMap();
		
		for(int i=0; i<kdtEntrys.getRowCount(); i++){
			IRow row = getDetailTable().getRow(i);
			double amount1  = 0.0;
			double originalAmount1 = 0.0;
			BigDecimal num1 = FDCHelper.ZERO;
			BigDecimal price  = FDCHelper.ZERO;
//			ExchangeRateInfo rate = null;
			BigDecimal rate = null;
			MeasureUnitInfo unit = null;
			CurrencyInfo currency = null;
			
			
			MaterialOrderBizBillEntryInfo entryInfo=new MaterialOrderBizBillEntryInfo();
			MaterialInfo materInfo =  (MaterialInfo)row.getCell("materialNumber").getValue();
			
			if(row.getCell("quantity").getValue() != null){
				num1 = (BigDecimal) row.getCell("quantity").getValue();
			}
			if(row.getCell("amount").getValue() != null){
				amount1 =  Double.parseDouble(row.getCell("amount").getValue().toString());
			}
			if(row.getCell("originalAmount").getValue() != null){
				originalAmount1 = Double.parseDouble( row.getCell("originalAmount").getValue().toString());
			}
			if(row.getCell("unit").getValue() != null){
				unit = (MeasureUnitInfo) row.getCell("unit").getValue();
			}
			
			if(row.getCell("rate").getValue() != null){
				rate = (BigDecimal)row.getCell("rate").getValue();
			}
			
			if(row.getCell("price").getValue() != null){
				price = (BigDecimal)row.getCell("price").getValue();
			}
			
			if(row.getCell("currency").getValue() != null){
				currency =  (CurrencyInfo)row.getCell("currency").getValue();
			}
			
			entryInfo.setMaterialNumber(materInfo);
			entryInfo.setQuantity(num1);
			entryInfo.setAmount(new BigDecimal(amount1) );
			entryInfo.setOriginalAmount(new BigDecimal(originalAmount1) );
//			entryInfo.setRate(rate);
			entryInfo.setExRate(rate);
			entryInfo.setCurrency(currency);
			entryInfo.setUnit(unit);
			entryInfo.setPrice(price);
			if(map.containsKey(materInfo.getNumber())){
    			MaterialOrderBizBillEntryInfo entry = (MaterialOrderBizBillEntryInfo) map.get(materInfo.getNumber());
    			BigDecimal num=FDCHelper.ZERO;
    			BigDecimal amount=FDCHelper.ZERO;
    			BigDecimal originalAmount=FDCHelper.ZERO;
    			if(entryInfo.getQuantity() != null){
    				num = entry.getQuantity().add(entryInfo.getQuantity());//数量
    			}
    			if(entryInfo.getAmount() != null){
    				amount = entry.getAmount().add(entryInfo.getAmount());	//原币金额
    			}
    			if(entryInfo.getOriginalAmount() != null){
    				originalAmount = entry.getOriginalAmount().add(entryInfo.getOriginalAmount());	//本币金额
    			}
    			
    			entry.setQuantity(num);
    			entry.setAmount(amount);
    			entry.setOriginalAmount(originalAmount);
    			map.put(materInfo.getNumber(), entry);
    		}else{
    			map.put(materInfo.getNumber(), entryInfo);
    		}
		}
		Iterator keyIte = map.keySet().iterator();
		while(keyIte.hasNext()){
			MaterialOrderBizBillEntryInfo entryInfo = (MaterialOrderBizBillEntryInfo) map.get(keyIte.next());
			IRow row = this.kdtAssEntrys.addRow();	
			row.getCell("materialNumber").setValue(entryInfo.getMaterialNumber());
			row.getCell("materialName").setValue(entryInfo.getMaterialNumber().getName());
			row.getCell("materialModel").setValue(entryInfo.getMaterialNumber().getModel());
			row.getCell("unit").setValue(entryInfo.getUnit());
			row.getCell("currency").setValue(entryInfo.getCurrency());
			row.getCell("quantity").setValue(entryInfo.getQuantity());
			row.getCell("price").setValue(entryInfo.getPrice());
			row.getCell("rate").setValue(entryInfo.getExRate());
			row.getCell("amount").setValue(entryInfo.getAmount());
			row.getCell("originalAmount").setValue(entryInfo.getOriginalAmount());
		}
	}
	
	/**
     * @author tim_gao
     * @see 从编辑界面调用onload
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	storeFields();
    	super.actionAddNew_actionPerformed(e);
    	setComponentValue();
		setComponentState();
		this.txtNumber.setEnabled(true);
    	storeFields();
    }
    
    //add by duyu 2011-8-15  增加合计行
    public void initEntityTable(){
		IRow footRow = FDCTableHelper.generateFootRow(kdtEntrys);

		if(sumAmount!=null){
			footRow.getCell("amount").setValue(sumAmount);
		}else{
			footRow.getCell("amount").setValue(FDCHelper.ZERO);
		}
		footRow.getCell("amount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    }
    public void initAllAmount(){
    	sumAmount = FDCHelper.ZERO;
    	kdtEntrys.checkParsed();
		int count = kdtEntrys.getRowCount();
		if(count > 0){
			for(int i = 0 ; i < count ; i++){
    			    sumAmount = sumAmount.add(kdtEntrys.getRow(i).getCell("amount").getValue()!=null?(BigDecimal)kdtEntrys.getRow(i).getCell("amount").getValue():FDCHelper.ZERO);
    	    	}
		}
    }
}