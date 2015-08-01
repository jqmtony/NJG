/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ApportionTypeEnum;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MeasureIndexFactory;
import com.kingdee.eas.fdc.basedata.MeasureIndexInfo;
import com.kingdee.eas.fdc.basedata.StandardIndexEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class MeasureIndexEditUI extends AbstractMeasureIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MeasureIndexEditUI.class);
    
    /**
     * output class constructor
     */
    public MeasureIndexEditUI() throws Exception
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
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionEdit_actionPerformed(e);
        setBtnStatus();
    	setTitle();
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }
    private Rectangle leftRec =null;
    public void initUIContentLayout() {
		super.initUIContentLayout();
		
    }
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		MeasureIndexInfo objectValue = new MeasureIndexInfo();
		objectValue.setIsEnabled(isEnabled);
		comboType.setSelectedIndex(0);
		objectValue.setType((ApportionTypeEnum)comboType.getSelectedItem());
		objectValue.setId(BOSUuid.create(objectValue.getBOSType()));
		
		//默认
		prmtApportionType.setVisible(false);
    	comboStandard.setRequired(true);
		prmtApportionType.setRequired(false);
		
		return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return MeasureIndexFactory.getRemoteInstance();
	}
	
	/**
     * 判断 阶段顺序 为数字
     */
	/* (non-Javadoc)
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBaseDataEditUI#verifyInput(java.awt.event.ActionEvent)
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, comboType);
		
		//清除空格并设置使保存 by hpw
		this.txtNumber.setText(FDCBaseDataClientUtils.clearEmpty(txtNumber.getText()));
		this.editData.setNumber(txtNumber.getText());
		Object type = comboType.getSelectedItem();
    	if (type instanceof ApportionTypeEnum) {
			if ((ApportionTypeEnum) type == ApportionTypeEnum.STANDARD) {
				FDCClientVerifyHelper.verifyEmpty(this, comboStandard);
			} else {
				FDCClientVerifyHelper.verifyEmpty(this, prmtApportionType);
			}
		}
	}
	
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		setTitle();
		
		//非集团不能操作
		if (!OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString())) {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
			this.btnCancel.setEnabled(false);
			this.btnCancelCancel.setEnabled(false);
		}
		setBtnStatus();
		bizDesc.setMaxLength(255);
		txtNumber.setMaxLength(80);
		txtNumber.setRequired(true);
	}
	
	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("standardIndex.*"));
        sic.add(new SelectorItemInfo("name"));
        return sic;
    }   
	
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.MEASUREINDEX));
	}
	
	 /**
     * output comboType_itemStateChanged method
     */
    protected void comboType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	setBtnStatus();
    }

	private void setBtnStatus() {
		//默认
    	prmtApportionType.setVisible(false);
    	comboStandard.setRequired(true);
		prmtApportionType.setRequired(false);
		
    	Object type = comboType.getSelectedItem();
    	if (type instanceof ApportionTypeEnum) {
			if ((ApportionTypeEnum) type == ApportionTypeEnum.STANDARD) {
				StandardIndexEnum standardType = (StandardIndexEnum) comboStandard
						.getSelectedItem();
				if (standardType != null) {
					this.editData.setName(standardType.getAlias());
					bizName.setDefaultLangItemData(editData.getName());
				}else{
					this.editData.setName(null);
					bizName.setDefaultLangItemData(null);
				}
				this.editData.setApportionType(null);
				contName.setEnabled(true);
				contName.setVisible(true);
				comboStandard.setVisible(true);
				comboStandard.setEnabled(true);

				contApportionType.setVisible(false);
				prmtApportionType.setVisible(false);
				prmtApportionType.setValue(null);
				prmtApportionType.setEnabled(false);
				
				comboStandard.setRequired(true);
				prmtApportionType.setRequired(false);
				txtNumber.setEnabled(true);
			} else {
				contName.setVisible(false);
				comboStandard.setVisible(false);
				comboStandard.setEnabled(false);
				comboStandard.setSelectedIndex(-1);

				contApportionType.setVisible(true);
				prmtApportionType.setVisible(true);
				prmtApportionType.setEnabled(true);

				this.editData.setStandardIndex(null);
				
				comboStandard.setRequired(false);
				prmtApportionType.setRequired(true);
				txtNumber.setEnabled(false);
				
				ApportionTypeInfo typeInfo = (ApportionTypeInfo) prmtApportionType
						.getValue();
				if (typeInfo != null) {
					this.editData.setName(typeInfo.getName());
					this.editData.setNumber(typeInfo.getNumber());
					txtNumber.setText(typeInfo.getNumber());
					bizName.setDefaultLangItemData(editData.getName());
				}else{
					this.editData.setName(null);
					this.editData.setNumber(null);
					txtNumber.setText(null);
					bizName.setDefaultLangItemData(null);
				}

			}

		}
	}

    /**
     * output comboStandard_itemStateChanged method
     */
    protected void comboStandard_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		StandardIndexEnum standardType = (StandardIndexEnum) comboStandard
				.getSelectedItem();
		if (standardType != null) {
			this.editData.setName(standardType.getAlias());
			this.editData.setApportionType(null);
			bizName.setDefaultLangItemData(editData.getName());
		}

	}

    /**
     * output prmtApportionType_dataChanged method
     */
    protected void prmtApportionType_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		ApportionTypeInfo typeInfo = (ApportionTypeInfo) prmtApportionType
				.getValue();
		if (typeInfo != null) {
			this.editData.setName(typeInfo.getName());
			this.editData.setNumber(typeInfo.getNumber());
			txtNumber.setText(typeInfo.getNumber());
			bizName.setDefaultLangItemData(editData.getName());
		}
	}
    
    protected void setIsEnable(boolean flag) throws Exception {
		//TODO 后续将数据操作方法移到服务端
		super.setIsEnable(flag);
		//记录日志用
		if(flag){
			MeasureIndexFactory.getRemoteInstance().enabled(null);
		}else{
			MeasureIndexFactory.getRemoteInstance().disEnabled(null);
		}
	}
}