/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.BindingPropertyFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractCodingTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractThirdTypeEnum;
import com.kingdee.eas.fdc.basedata.ContractTypeCollection;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IContractCodingType;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;

/**
 * 描述:合同编码类型
 * 
 * @author jackwang date:2007-3-7
 * @version EAS5.3
 */
public class ContractCodingTypeEditUI extends AbstractContractCodingTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractCodingTypeEditUI.class);
    
    /**
     * output class constructor
     */
    public ContractCodingTypeEditUI() throws Exception
    {
        super();
    }
    
	public void onLoad() throws Exception {
		// 初始化控件设置
		initCtrl();

		super.onLoad();

		setTitle();
		
		setBtnStatus();
	}

	/**
	 * 描述：初始化控件设置
	 * 
	 * @throws BOSException
	 * @author：skyiter_wang
	 * @createDate：2013-11-20
	 */
	protected void initCtrl() throws Exception {
		//设置编码类型
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		// R131012-0165，张祖艳，王亮，2013-11-20：
		// 1、合同编码新增界面的“合同类型”要能选择非明细级次
		// filter.getFilterItems().add(new FilterItemInfo("isleaf", Boolean.TRUE));

		// 已经启用
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		evi.setFilter(filter);

		// 根据“长编码”排序
		evi.getSorter().add(new SorterItemInfo("longNumber"));

		ContractTypeCollection cols = ContractTypeFactory.getRemoteInstance().getContractTypeCollection(evi);
		prmtContractType.addItem("全部");
		prmtContractType.addItems(cols.toArray());
	}

	public void loadFields() {
        super.loadFields();
        
        if(editData.getContractType()!=null){
        	FDCClientHelper.setSelectObject(prmtContractType,this.editData.getContractType());
        }else{
        	prmtContractType.setSelectedIndex(0);
        }
    }
    
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {//新增状态
//			this.btnCancelCancel.setVisible(false);//启用按钮不可见
//			this.btnCancel.setVisible(false);//禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {//修改状态
//			if (this.editData.isIsEnabled()) {//如果当前为启用状态
//				this.btnCancel.setVisible(true);//禁用按钮可用    			
//				this.btnCancel.setEnabled(true);//禁用按钮可用
//				this.btnCancelCancel.setVisible(false);//启用按钮不可见
//			} else {//如果当前为禁用状态
//				this.btnCancelCancel.setVisible(true);//启用按钮可见
//				this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
//				this.btnCancel.setEnabled(false);//禁用按钮不可见    			
//			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//查看状态			
			if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
//				if (this.editData.isIsEnabled()) {//如果当前为启用状态
//					this.btnCancel.setVisible(true);//禁用按钮可用    			
//					this.btnCancel.setEnabled(true);//禁用按钮可用
//					this.btnCancelCancel.setVisible(false);//启用按钮不可见
//				} else {//如果当前为禁用状态
//					this.btnCancelCancel.setVisible(true);//启用按钮可见
//					this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
//					this.btnCancel.setEnabled(false);//禁用按钮不可见    			
//				}				
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
			}else{
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
//				this.btnCancel.setVisible(false);
//				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}			
			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
//				this.btnCancel.setVisible(false);
//				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
		}
	}
	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("contractType.*"));
        sic.add(new SelectorItemInfo("secondType"));
        sic.add(new SelectorItemInfo("thirdType"));
//		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("CU.id"));
		return sic;
	}
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.CONTRACTCODINGTYPE));
	}
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
	/**
	 * 检查输入信息
	 */
	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
//		//合同类型是否为空		
//		if (this.prmtContractType.getData()==null) {
//			prmtContractType.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.CONTRACTTYPE_IS_EMPTY);
//		}
//		//合同性质不能为空
//		if(this.comboSecondType.getSelectedItem()==null){
//			comboSecondType.requestFocus(true);
//			throw new FDCBasedataException(FDCBasedataException.CONTRACT_TYPE_ISNULL);
//		}
		IContractCodingType icct = ContractCodingTypeFactory.getRemoteInstance();	
		ContractCodingTypeCollection cctc;
		
		//合同类型
		Object obj = prmtContractType.getSelectedItem();
		String contractTypeId = null;
		if(obj instanceof ContractTypeInfo){
			 contractTypeId = ((ContractTypeInfo)obj).getId().toString();
		}
		
		if(STATUS_ADDNEW.equals(this.oprtState)){			
			cctc = icct.getContractCodingTypeCollection("select * where number = '"+this.txtNumber.getText()+"' ");
			if(cctc.size()!=0){
				throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EXIST);
			}
			
			cctc = icct.getContractCodingTypeCollection(
					"select * where contractType.id "+ (contractTypeId==null?" is null":" ='"+contractTypeId+"' ") 
					+"  and secondType = '"+((ContractSecondTypeEnum)comboSecondType.getSelectedItem()).getValue()
					+"' and thirdType = '"+((ContractThirdTypeEnum)comboThirdType.getSelectedItem()).getValue()+"' ");
			if(cctc.size()!=0){
				throw new FDCBasedataException(FDCBasedataException.CONTRACTCODINTTYPE_IS_EXIST);
			}
		}else if(STATUS_EDIT.equals(this.oprtState)){
			cctc = icct.getContractCodingTypeCollection("select * where number = '"+this.txtNumber.getText()+"' and id != '"+this.editData.getId().toString()+"' ");
			if(cctc.size()!=0){
				throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EXIST);
			}
			cctc = icct.getContractCodingTypeCollection(
					"select * where contractType.id "+ (contractTypeId==null?" is null":" ='"+contractTypeId+"' ") 
					+"  and secondType = '"+((ContractSecondTypeEnum)comboSecondType.getSelectedItem()).getValue()
					+"' and thirdType = '"+((ContractThirdTypeEnum)comboThirdType.getSelectedItem()).getValue()
					+"' and id != '"+this.editData.getId().toString()+"' ");
			if(cctc.size()!=0){
				throw new FDCBasedataException(FDCBasedataException.CONTRACTCODINTTYPE_IS_EXIST);
			}
		}
	}
    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
        super.actionEdit_actionPerformed(e);
    }
    //2009-1-14 如果合同编码类型已被编码规则引用，则不允许修改或删除
    private void checkBeforeRemove() throws BOSException,EASBizException{
    	if (editData == null || editData.getId() == null || "".equals(editData.getId().toString()))
    		return;
    	String number = editData.getNumber();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("propertyName","codeType.number"));
    	filter.getFilterItems().add(new FilterItemInfo("propertyValue",number));
    	if(BindingPropertyFactory.getRemoteInstance().exists(filter)){
    		MsgBox.showError(this,"该合同编码在编码规则中已被引用，不允许修改或删除！");
    		SysUtil.abort();
    	}
    }
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

	protected IObjectValue createNewData() {
		//
		ContractCodingTypeInfo ccti = new ContractCodingTypeInfo();
		
		ccti.setSecondType(ContractSecondTypeEnum.OLD);
		ccti.setThirdType(ContractThirdTypeEnum.NEW);
		
		this.comboThirdType.setSelectedIndex(0);
		return ccti;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return ContractCodingTypeFactory.getRemoteInstance();
	}
    /**
     * output comboSecondType_itemStateChanged method
     */
    protected void comboSecondType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboThirdType_itemStateChanged method
     */
    protected void comboThirdType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
//    	if(STATUS_ADDNEW.equals(getOprtState())){
	    	if (!ContractThirdTypeEnum.NEW_VALUE.equals(((ContractThirdTypeEnum) comboThirdType.getSelectedItem()).getValue())) {
		    		comboSecondType.removeAllItems();
		    		this.comboSecondType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ContractSecondTypeEnum").toArray());
	    	}
//    	}
    }
}