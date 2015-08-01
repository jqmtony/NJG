/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IInvalidCostReason;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueForEditUIUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * ��Ч�ɱ�ԭ��   �༭����
 */
public class InvalidCostReasonEditUI extends AbstractInvalidCostReasonEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvalidCostReasonEditUI.class);
    
    private InvalidCostReasonInfo parentInfo;
	private String parentNumber = null;
    private String strTemp = null;
    /**
     * output class constructor
     */
    public InvalidCostReasonEditUI() throws Exception
    {
        super();
    }

	/**
	 * output getSelectors method
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("longNumber"));
		sic.add(new SelectorItemInfo("isEnabled"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("parent.*"));
		sic.add(new SelectorItemInfo("CU.id"));
		return sic;
	}
	
	protected IObjectValue createNewData() {
		InvalidCostReasonInfo invalidCostReasonInfo = new InvalidCostReasonInfo();
				
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			invalidCostReasonInfo.setParent(parentInfo);
			strTemp = parentInfo.getLongNumber();
			strTemp = strTemp.replace('!', '.');
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				invalidCostReasonInfo.setNumber(strTemp + ".");
			} 
		}
		
		return invalidCostReasonInfo;
	}

	private AbstractObjectValue oldData = null;
    /**
     * ��¡��ֵ����������Ƿ��޸�
     *
     * @param dataObject
     */
    protected void initOldData(IObjectValue dataObject) 
    {
        AbstractObjectValue objectValue = (AbstractObjectValue) dataObject;
        //Begin
        if (objectValue instanceof IObjectValueAgent) {
            oldData = (AbstractObjectValue)AgentUtility.deepCopyAgentValue((IObjectValueAgent)objectValue);
        } else {
        	if (objectValue != null) {
				oldData = (AbstractObjectValue) objectValue.clone();
			}else{
				this.destroyWindow();
			}
        }
        //End
    }
	 /**
     * �жϵ�ǰ�༭�������Ƿ����仯
     */
    public boolean isModify() {

        //�����Onloadʱ���жϴ����򲻻�������ݱȽϡ�2006-8-22 by psu_s
//        if(isOnLoadExceptionAbort)
//        {
//            return false;
//        }
        try
        {
            com.kingdee.bos.ctrl.common.util.ControlUtilities.checkFocusAndCommit();
        }
        catch (ParseException e)
        {
			handleControlException();
			// ��������Ҫ֪���Ƿ������쳣
			//wfContext.setThrowException(true);

			abort();
        }

        //        return false;
        /* ȥ������жϡ�û��ʲô������ġ� 2006-9-21
        if(isSave())
        {
            return false;
        }*/
        //�鿴״̬���ж��Ƿ��޸�
        if (OprtState.VIEW.equals(getOprtState())) {
            return false;
        }

        try {
            storeFields();
        } catch (Exception exc) {
        	// @AbortException
            return false;
        }

        return !ObjectValueForEditUIUtil.objectValueEquals(oldData, editData);
    }
	protected ICoreBase getBizInterface() throws Exception {
		return InvalidCostReasonFactory.getRemoteInstance();
	}
	
	public void onLoad() throws Exception {
		
		parentInfo = (InvalidCostReasonInfo) getUIContext().get(UIContext.PARENTNODE);
		
		super.onLoad();
		setTitle();
		setBtnStatus();			

		this.chkIsEnabled.setSelected(this.editData.isIsEnabled());
		
		if (parentInfo != null && parentInfo.isIsEnabled()) {
			this.chkIsEnabled.setSelected(true);
		} else if (parentInfo != null && !parentInfo.isIsEnabled()) {
			this.chkIsEnabled.setSelected(false);
		} 
		
		//�ϼ����벻�����
		txtLongNumber.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				InvalidCostReasonInfo parent = (InvalidCostReasonInfo) getUIContext().get(UIContext.PARENTNODE);
				if (parent == null)
					return;
				String longNumber = parent.getLongNumber().replace('!', '.') + '.';
				if (!txtLongNumber.getText().startsWith(longNumber)) {
					txtLongNumber.setText(longNumber);
					txtLongNumber.setSelectionStart(longNumber.length());
				}
			}
			public void keyTyped(KeyEvent e) {}
		});
		
		storeFields();
		initOldData(editData);
	}
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.INVALIDCOSTREASON));
	}
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// ����״̬
			this.actionCancelCancel.setVisible(false);// ���ð�ť���ɼ�
			this.actionCancel.setVisible(false);// ���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {// �޸�״̬
			if (this.editData.isIsEnabled()) {// �����ǰΪ����״̬
				this.actionCancel.setVisible(true);// ���ð�ť����
				this.actionCancel.setEnabled(true);// ���ð�ť����
				this.actionCancelCancel.setVisible(false);// ���ð�ť���ɼ�
			} else {// �����ǰΪ����״̬
				this.actionCancelCancel.setVisible(true);// ���ð�ť�ɼ�
				this.actionCancelCancel.setEnabled(true);// ���ð�ť����
				this.actionCancel.setEnabled(false);// ���ð�ť���ɼ�
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnRemove.setEnabled(false);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// �鿴״̬
			if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
				if (this.editData.isIsEnabled()) {// �����ǰΪ����״̬
					this.actionCancel.setVisible(true);// ���ð�ť����
					this.actionCancel.setEnabled(true);// ���ð�ť����
					this.actionCancelCancel.setVisible(false);// ���ð�ť���ɼ�
				} else {// �����ǰΪ����״̬
					this.actionCancelCancel.setVisible(true);// ���ð�ť�ɼ�
					this.actionCancelCancel.setEnabled(true);// ���ð�ť����
					this.actionCancel.setEnabled(false);// ���ð�ť���ɼ�
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
				this.actionCancel.setVisible(false);
				this.actionCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
				this.btnAddNew.setEnabled(false);
				// this.btnEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.actionCancel.setVisible(false);
				this.actionCancelCancel.setVisible(false);
				this.menuItemAddNew.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}

		}
	}
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();

		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			strTemp = parentInfo.getLongNumber();
			strTemp = strTemp.replace('!', '.');
			parentNumber = strTemp;
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.actionCancel.setVisible(true);
					this.actionCancel.setEnabled(true);
					this.actionCancelCancel.setVisible(false);

				} else {
					this.actionCancel.setVisible(false);
					this.actionCancelCancel.setVisible(true);
					this.actionCancelCancel.setEnabled(true);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				// parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.actionCancel.setVisible(true);
					this.actionCancel.setEnabled(true);
					this.actionCancelCancel.setVisible(false);

				} else {
					this.actionCancel.setVisible(false);
					this.actionCancelCancel.setVisible(true);
					this.actionCancelCancel.setEnabled(true);
				}

			}
		} else {
			if ((STATUS_ADDNEW.equals(getOprtState()))) {// ����״̬
				// this.txtLongNumber.setText(strTemp + ".");
			} else if (STATUS_EDIT.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.actionCancel.setVisible(true);
					this.actionCancel.setEnabled(true);
					this.actionCancelCancel.setVisible(false);

				} else {
					this.actionCancel.setVisible(false);
					this.actionCancelCancel.setVisible(true);
					this.actionCancelCancel.setEnabled(true);
				}
				if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
					this.btnRemove.setEnabled(false);
				}
			} else if (STATUS_VIEW.equals(getOprtState())) {
				strTemp = this.editData.getLongNumber();
				strTemp = strTemp.replace('!', '.');
				if(strTemp.lastIndexOf(".")>=0){
					parentNumber = strTemp.substring(0,strTemp.lastIndexOf("."));
				}
				this.txtLongNumber.setText(strTemp);
				if (this.editData.isIsEnabled()) {
					this.actionCancel.setVisible(true);
					this.actionCancel.setEnabled(true);
					this.actionCancelCancel.setVisible(false);

				} else {
					this.actionCancel.setVisible(false);
					this.actionCancelCancel.setVisible(true);
					this.actionCancelCancel.setEnabled(true);
				}

			}
		}
	}

	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		String longNumber = this.txtLongNumber.getText().trim();
		if (longNumber == null || longNumber.trim().length() < 1 || longNumber.lastIndexOf(".")+1==longNumber.length() || longNumber.indexOf(".")==0
				 || longNumber.lastIndexOf("!")+1==longNumber.length() || longNumber.indexOf("!")==0 ) {
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		if (getOprtState().equals(OprtState.ADDNEW)){
			FDCBaseTypeValidator.validate(((InvalidCostReasonListUI) getUIContext().get(UIContext.OWNER)).getMainTable(), txtLongNumber, bizName, getSelectBOID());
		
			if (parentNumber != null && (longNumber.equals(parentNumber + ".") || longNumber.length() < parentNumber.length() + 1)) {
				// ���벻����
				txtLongNumber.requestFocus(true);
				throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_2);
			}
		}
		if (parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('.') >= 0)// �û����˵�
				|| (!longNumber.substring(0, parentNumber.length()).equals(parentNumber)))) {// ��ʼ�����뱻�޸�
			// ���벻���Ϲ淶
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		
		//�û�����̾�Ż����޸��˳�ʼ�����룬���׳����벻���Ϲ淶�쳣
		if(parentNumber != null && ((!longNumber.equalsIgnoreCase(parentNumber) && longNumber.substring(parentNumber.length() + 1, longNumber.length()).indexOf('!') >= 0)
				||(!longNumber.substring(0, parentNumber.length()).equals(parentNumber))) )
		{
			txtLongNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		this.editData.setNumber(longNumber.substring(longNumber.lastIndexOf(".") + 1,longNumber.length()));
		
		longNumber = longNumber.replace('.', '!');
		this.editData.setLongNumber(longNumber);

		// �����Ƿ�Ϊ��
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}

	}

	/**
	 * ����
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IInvalidCostReason) getBizInterface()).disEnabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				this.editData.setIsEnabled(false);
				((InvalidCostReasonInfo)oldData).setIsEnabled(false);
				setSaved(true);
				
				loadFields();
			}
		}
	}
	
	/**
	 * ����
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData != null && this.editData.getId() != null) {
			IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
			if (((IInvalidCostReason) getBizInterface()).enabled(pk)) {
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				this.editData.setIsEnabled(true);
				((InvalidCostReasonInfo)oldData).setIsEnabled(true);
				setSaved(true);
				loadFields();
			}
		}
	}

	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}


	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if (OrgConstants.SYS_CU_ID.equals(this.editData.getCU().getId().toString())) {
			this.btnRemove.setEnabled(false);
		}
		
		storeFields();
		initOldData(editData);
	}
	
	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);

		storeFields();
		initOldData(editData);
	}
}