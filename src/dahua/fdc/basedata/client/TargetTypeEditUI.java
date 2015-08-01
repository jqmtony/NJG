/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.ITargetType;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.fdc.basedata.TargetTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class TargetTypeEditUI extends AbstractTargetTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetTypeEditUI.class);

	private TargetTypeInfo parentInfo;

	private String parentNumber = null;

	private String strTemp = null;
    /**
     * output class constructor
     */
    public TargetTypeEditUI() throws Exception
    {
        super();
    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("parent.*"));
        sic.add(new SelectorItemInfo("*"));
        return sic;
    }     
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
	protected IObjectValue createNewData() {
		TargetTypeInfo targetTypeInfo = new TargetTypeInfo();
//		targetTypeInfo.setIsEnabled(true);
		return targetTypeInfo;
	}
    public void onShow() throws Exception
    {
        super.onShow();
        this.txtLongNumber.requestFocus();
    }
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		parentInfo = (TargetTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		this.setDataObject(editData);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			strTemp = parentInfo.getLongNumber();
			strTemp = strTemp.replace('!', '.');
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if(this.editData.isIsEnabled()){
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);
					
				}else{
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if(this.editData.isIsEnabled()){
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);
					
				}else{
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			}
		}else{
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
//				this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
//				strTemp = this.editData.getLongNumber();
//				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
//				this.txtLongNumber.setText(strTemp);
				if(this.editData.isIsEnabled()){
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);
					
				}else{
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if(this.editData.isIsEnabled()){
					this.btnCancel.setVisible(true);
					this.btnCancel.setEnabled(true);
					this.btnCancelCancel.setVisible(false);
					
				}else{
					this.btnCancel.setVisible(false);
					this.btnCancelCancel.setVisible(true);
					this.btnCancelCancel.setEnabled(true);
				}
			}
		}
	}

	/**
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		if (this.txtLongNumber.getText() == null || this.txtLongNumber.getText().trim().equals("")) {
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		} else {
			if (getUIContext().get(UIContext.PARENTNODE) != null) {
				if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态
					String a = txtLongNumber.getText();
					if ((a.equals(strTemp + ".")) || (a.length() < strTemp.length() + 1)) {
						txtLongNumber.requestFocus(true);
						// 编码不完整
						throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_2);
					}
					if ((a.substring(strTemp.length() + 1, a.length()).indexOf(".") > 0)// 用户打了点
							|| (!a.substring(0, strTemp.length()).equals(strTemp))) {// 初始父编码被修改
						txtLongNumber.requestFocus(true);
						// 编码不符合规范
						throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
					}
					this.editData.setNumber(this.txtLongNumber.getText().substring(this.txtLongNumber.getText().lastIndexOf(".") + 1, this.txtLongNumber.getText().length()));
					// if((txtLongNumber.getText().length()>(parentNumber.length()+1))
					// &&((this.parentNumber+".").equals(txtLongNumber.getText().substring(0,this.parentNumber.length()+1)))){
					// this.editData.setNumber(this.txtLongNumber.getText()
					// .substring(this.parentNumber.length()+1,this.txtLongNumber.getText().length()));
					// }
					// else{
					// throw new
					// FDCBasedataException(FDCBasedataException.NUMBER_CHECK);
					// }
					// }else if(STATUS_EDIT.equals(getOprtState())){//修改状态
					// if((txtLongNumber.getText().length()>=(parentNumber.length()+1))
					// &&((this.parentNumber+".").equals(txtLongNumber.getText().substring(0,this.parentNumber.length()+1)))){
					// this.editData.setNumber(this.txtLongNumber.getText()
					// .substring(this.parentNumber.length()+1,this.txtLongNumber.getText().length()));
					// }else{
					// throw new
					// FDCBasedataException(FDCBasedataException.NUMBER_CHECK);
					// }
				}else{
					this.editData.setNumber(this.txtLongNumber.getText().substring(this.txtLongNumber.getText().toString().lastIndexOf(".") + 1, this.txtLongNumber.getText().toString().length()));
				}
			} else {
				if (!(this.txtLongNumber.getText().indexOf(".") < 0)) {
					txtLongNumber.requestFocus(true);
					// 编码不符合规范
					throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
				}
				this.editData.setNumber(this.txtLongNumber.getText());
				
			}
			String temp = this.txtLongNumber.getText().toString();
			temp = temp.replace('.', '!');
			this.editData.setLongNumber(temp);

		}
		// 名称是否为空
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		if (getOprtState().equals(OprtState.ADDNEW))
		FDCBaseTypeValidator.validate(((TargetTypeListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtLongNumber, bizName, getSelectBOID());

	}

	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.TARGETTYPE));
	}

	public void onLoad() throws Exception {
		super.onLoad();
		setTitle();
		setBtnStatus();
		
		
		// 上级编码不允许改

		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				TargetTypeInfo parent = (TargetTypeInfo) getUIContext().get(UIContext.PARENTNODE);
				if (parent == null)
					return;
				String longNumber = parent.getLongNumber().replace('!', '.') + '.';
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}

			public void keyTyped(KeyEvent e) {
			}
		});
	}

	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态
			this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			this.btnCancel.setVisible(false);// 禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改状态
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.btnCancel.setVisible(true);// 禁用按钮可用
				this.btnCancel.setEnabled(true);// 禁用按钮可用
				this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			} else {// 如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);// 启用按钮可见
				this.btnCancelCancel.setEnabled(true);// 启用按钮可用
				this.btnCancel.setEnabled(false);// 禁用按钮不可见
			}
			if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// 查看状态
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				if (this.editData.isIsEnabled()) {// 如果当前为启用状态
					this.btnCancel.setVisible(true);// 禁用按钮可用
					this.btnCancel.setEnabled(true);// 禁用按钮可用
					this.btnCancelCancel.setVisible(false);// 启用按钮不可见
				} else {// 如果当前为禁用状态
					this.btnCancelCancel.setVisible(true);// 启用按钮可见
					this.btnCancelCancel.setEnabled(true);// 启用按钮可用
					this.btnCancel.setEnabled(false);// 禁用按钮不可见
				}
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				this.menuItemRemove.setEnabled(true);
			} else {
				this.btnAddNew.setEnabled(false);
				this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.btnCancel.setVisible(false);
				this.btnCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
		}
	}

	protected void initOldData(IObjectValue dataObject) {
		parentInfo = (TargetTypeInfo) getUIContext().get(UIContext.PARENTNODE);
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// 新增状态
				String strTemp = parentInfo.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				((TargetTypeInfo) dataObject).setLongNumber(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				String strTemp = ((TargetTypeInfo) dataObject).getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				((TargetTypeInfo) dataObject).setLongNumber(strTemp);
			}
		}
		super.initOldData(dataObject);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		super.actionCancel_actionPerformed(e);
		// this.editData.setIsEnabled(false);
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add(new SelectorItemInfo("isEnabled"));
		// this.getBizInterface().updatePartial(this.editData, selector);
		if(this.editData!=null&&this.editData.getId()!=null){
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((ITargetType) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
	//			this.btnCancelCancel.setVisible(true);
	//			this.btnCancelCancel.setEnabled(true);
	//			this.btnCancel.setVisible(false);
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
		        loadFields();
				setSave(true);
		        setSaved(true);
			}
		}
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
//		super.actionCancelCancel_actionPerformed(e);
		// this.editData.setIsEnabled(true);
		// SelectorItemCollection selector = new SelectorItemCollection();
		// selector.add(new SelectorItemInfo("isEnabled"));
		// this.getBizInterface().updatePartial(this.editData, selector);
		if(this.editData!=null&&this.editData.getId()!=null){
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((ITargetType) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
	//			this.btnCancel.setVisible(true);
	//			this.btnCancel.setEnabled(true);
	//			this.btnCancelCancel.setVisible(false);
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
		        loadFields();
				setSave(true);
		        setSaved(true);
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
        this.txtLongNumber.requestFocus();
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
        super.actionEdit_actionPerformed(e);
        if(OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())){				
			this.btnRemove.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
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

	protected ICoreBase getBizInterface() throws Exception {
		// TODO 自动生成方法存根
		return TargetTypeFactory.getRemoteInstance();
	}

}