/*
 * @(#)MaterialIndexEditUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import org.apache.jackrabbit.uuid.UUID;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.material.MaterialIndexEnum;
import com.kingdee.eas.fdc.material.MaterialIndexFactory;
import com.kingdee.eas.fdc.material.MaterialIndexInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
/**
 * @description		材料特性指标的编辑界面<p>
 * @author  		杜仕超
 * @version 		EAS 7.0
 * @see 
 */
public class MaterialIndexEditUI extends AbstractMaterialIndexEditUI {
	 
	private static final long serialVersionUID = 2320885763748426960L;
	/**定义树节点对象*/
	private MaterialGroupInfo materialGroupInfo = null;
	/**资源文件路径*/
	private static final String RESOURC_FILE_PATH = "com/kingdee/eas/fdc/material/MaterialIndexResource"; 
	 
	 /**
	  * @description    读取资源文件并定义方法  以被调用
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	private String setResourc(String name){
		return EASResource.getString(RESOURC_FILE_PATH, name);
	}
	/**
	 * output class constructor
	 */
	public MaterialIndexEditUI() throws Exception {
		super();
	}
	
	 /**
	  * @description    验正输入值的长度
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	protected void verifyInput(ActionEvent e) throws Exception {
		/*分别获得文本框和文本域的值*/
		String txtValue = (this.txtName.getSelectedItemData()).toString().trim();
		String AreaValue = this.descriptionTextArea.getSelectedItemData().toString().trim();
		if (null == txtValue || "".equals(txtValue)) {
			FDCMsgBox.showWarning(this.setResourc("nameNotNull"));
			abort();
		} else {
			if (txtValue.length() > 40) {
				FDCMsgBox.showWarning(this.setResourc("nameNot40"));
				abort();
			}
			if (AreaValue.length() > 200) {
				FDCMsgBox.showWarning(this.setResourc("remarkNot200"));
				abort();
			}
		}
		/*super.verifyInput(e);*/
	}
 
	 /**
	  * @description    保存提交
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
 
		/*调用手动绑定的数据 方法 且 判断点击按钮状态 */ 
		this.materialGroupInfo = editData.getMaterialGroup();
		super.actionSubmit_actionPerformed(e);
       
	} 
 
	 /**
	  * @description    onShow() 初始化按钮状态  设置输入框长度
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	public void onShow() throws Exception {

		super.onShow();
		/*设置指标名称为必录项*/
		this.txtName.setRequired(true);
		/*设置输入框长度不能大于40  200字符*/
		this.txtName.setMaxLength(40);
		this.descriptionTextArea.setMaxLength(200);
		/*灰掉启用  禁用 按钮*/
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		/*判断点击是否为查看状态 并灰按钮*/
		if(this.getOprtState().equals(OprtState.VIEW)){
			this.actionSave.setEnabled(false);
			this.actionSubmit.setEnabled(false);
		}
	}
 
	 /**
	  * @description    手动插入UI界面上没有显示控件的值
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	protected IObjectValue createNewData() {
        MaterialIndexInfo info = new MaterialIndexInfo();
        /*设置编码且不能重复*/
        info.setNumber(UUID.randomUUID().toString());
        /*设置来源的值*/
        info.setSource(MaterialIndexEnum.TRAITINDEX);
        /*设置创建者  默认为登录人*/
        info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
        /*设置状态 默认为启用*/
        info.setIsEnabled(true);
        try {
        	/*设置为服务器日期时间*/
			info.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			handUIException(e);
		}
		/*获得树节点ID (对象) 并插入进去*/
        if(getUIContext().get("MaterialGroupInfo") != null){
        	this.materialGroupInfo = (MaterialGroupInfo) getUIContext().get("MaterialGroupInfo");
        	info.setMaterialGroup(materialGroupInfo);
        }else if(materialGroupInfo != null){
        	info.setMaterialGroup(materialGroupInfo);
        }
		return info;
	}

	 /**
	  * @description    连接远程接口
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	protected ICoreBase getBizInterface() throws Exception {
		return MaterialIndexFactory.getRemoteInstance();
	}
	
   	 /**
	  * @description    获得名称
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}
	
	 /**
	  * @description    获得编码
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	protected KDTextField getNumberCtrl() {
		KDTextField kf = new KDTextField();
		kf.setText(UUID.randomUUID().toString());
		return kf;
	}
	
     /**
	  * @description    添加到数据库
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	public SelectorItemCollection getSelectors() {
		 
		SelectorItemCollection sic = super.getSelectors();
		/*分别是 编码,名称,来源,树节点对象,状态,创建者,创建日期*/
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("source"));
		sic.add(new SelectorItemInfo("materialGroup.*"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("createTime"));
		
		return sic;
	}
	
	 /**
	  * @deprecated
	  * @description     
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version	    EAS7.0
	  * @see
	  */
	public SelectorItemCollection saveMaterilIndex() {

		SelectorItemCollection selectorItemCollection = new	SelectorItemCollection();

		/*editData.setSource(MaterialIndexEnum.TRAITINDEX);
		editData.setIsEnabled(true);
		try {
			 materialGroupInfo =

			(MaterialGroupInfo) getUIContext().get("MaterialGroupInfo");
			editData.setMaterialGroup(materialGroupInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		editData.setCreator(SysContext.getSysContext().getCurrentUserInfo());

		try {
			editData.setCreateTime

			(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		selectorItemCollection.add(new SelectorItemInfo("materialGroup"));
		selectorItemCollection.add(new SelectorItemInfo("source"));
		selectorItemCollection.add(new SelectorItemInfo("isEnabled"));
		selectorItemCollection.add(new SelectorItemInfo("creator.name"));
		selectorItemCollection.add(new SelectorItemInfo("createTime"));
		logger.info(editData);*/
		return selectorItemCollection;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields(); 
		if(materialGroupInfo != null){
			editData.setMaterialGroup(this.materialGroupInfo);
		} 
	} 
	
	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws

	Exception {
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
	public void actionOnLoad_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionOnLoad_actionPerformed(e);
	}
	
	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)

	throws

	Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception

	{

		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed 
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws

	Exception {
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
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws

	Exception {
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
	public void actionAddNew_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception

	{
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionSubmitOption_actionPerformed
	 */
	public void actionSubmitOption_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionSubmitOption_actionPerformed(e);
	}

	/**
	 * output actionReset_actionPerformed
	 */
	public void actionReset_actionPerformed(ActionEvent e) throws Exception

	{
		super.actionReset_actionPerformed(e);
	}

	/**
	 * output actionMsgFormat_actionPerformed
	 */
	public void actionMsgFormat_actionPerformed(ActionEvent e) throws

	Exception {
		super.actionMsgFormat_actionPerformed(e);
	}
	/**
	 * output actiongetEditData_actionPerformed
	 */
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	 
}