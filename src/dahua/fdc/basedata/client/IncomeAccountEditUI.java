/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.IIncomeAccount;
import com.kingdee.eas.fdc.basedata.IncomeAccountFactory;
import com.kingdee.eas.fdc.basedata.IncomeAccountInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ObjectValueForEditUIUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * ����:�����Ŀ�༭
 * output class name
 */
public class IncomeAccountEditUI extends AbstractIncomeAccountEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(IncomeAccountEditUI.class);
    
    private FullOrgUnitInfo addFullOrgUnitCache = null;
    private CurProjectInfo addCurProjectCache = null;
    private IncomeAccountInfo addParentInfo = null; 
    
    /**
     * output class constructor
     */
    public IncomeAccountEditUI() throws Exception
    {
        super();
    }

    private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.INCOMEACCOUNT));
	}
	public void onLoad() throws Exception {
		super.onLoad();

		FDCClientHelper.setActionEnable(actionAddNew, false);
		
		setTitle();
		setBtnStatus();		
		if (STATUS_ADDNEW.equals(this.getOprtState())) {
			this.chkIsEnabled.setEnabled(true);
			// //����parent
			if(getUIContext().get("upId") != null){
				IncomeAccountInfo IncomeAccountInfo = IncomeAccountFactory.getRemoteInstance().getIncomeAccountInfo(new ObjectStringPK(getUIContext().get("upId").toString()));
//				IncomeAccountInfo.setId(BOSUuid.read(getUIContext().get("parentID").toString()));
				if(IncomeAccountInfo.getFullOrgUnit()!=null){
					this.editData.setFullOrgUnit(IncomeAccountInfo.getFullOrgUnit());
					this.addFullOrgUnitCache = IncomeAccountInfo.getFullOrgUnit();
				}else if(IncomeAccountInfo.getCurProject()!=null){
					this.editData.setCurProject(IncomeAccountInfo.getCurProject());
					this.addCurProjectCache = IncomeAccountInfo.getCurProject();
				}
				this.editData.setParent(IncomeAccountInfo);
				if(IncomeAccountInfo.isIsEnabled()){
					this.chkIsEnabled.setSelected(true);
					this.editData.setIsEnabled(true);		
					((IncomeAccountInfo)oldData).setIsEnabled(true);
				}else{
					this.chkIsEnabled.setSelected(false);
					this.editData.setIsEnabled(false);
					((IncomeAccountInfo)oldData).setIsEnabled(false);
				}

				this.addParentInfo = IncomeAccountInfo;
				String longNumber = IncomeAccountInfo.getLongNumber();
				longNumber = longNumber.replace('!', '.');
				this.txtParentNumber.setText(longNumber);				
			}else{
				if (getUIContext().get("source") != null) {
					if (getUIContext().get("source") instanceof FullOrgUnitInfo) {
						FullOrgUnitInfo fullOrgUnitInfo = new FullOrgUnitInfo();
						fullOrgUnitInfo.setId(BOSUuid.read(((FullOrgUnitInfo)getUIContext().get("source")).getId().toString()));
						this.editData.setFullOrgUnit(fullOrgUnitInfo);
						this.addFullOrgUnitCache = fullOrgUnitInfo;
					} else if (getUIContext().get("source") instanceof CurProjectInfo) {		
						this.editData.setCurProject((CurProjectInfo) getUIContext().get("source"));
						this.addCurProjectCache = (CurProjectInfo) getUIContext().get("source");
					}
				}
				this.editData.setParent(null);
			}
		}else {
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
	        loadFields();
//			 ���ñ༭�Ͳ鿴״̬�½�����ϼ�����ؼ��ͱ�������ؼ�
			String longNumber = editData.getLongNumber();
			if(longNumber!=null){
				longNumber = longNumber.replace('!', '.');
				this.txtLongNumber.setText(longNumber);
				if (longNumber.indexOf(".") > 0) {
					this.txtParentNumber.setText(longNumber.substring(0, longNumber.lastIndexOf(".")));
				}
			}
			
//			�����ǰ����֯���Ǽ���
			if((SysContext.getSysContext().getCurrentFIUnit()!=null&&(!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					||(SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())){
				if(!this.editData.isIsLeaf()){
					this.btnAddNew.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
				}
			}else{//�����ǰ����֯�Ǽ��ţ����ҵ�ǰѡ�еĽڵ㲻�Ǽ��ţ���ô����������
				if(this.editData.getFullOrgUnit()!=null){
					if(!OrgConstants.DEF_CU_ID.equals(this.editData.getFullOrgUnit().getId().toString())){
						this.btnAddNew.setEnabled(false);
						this.btnEdit.setEnabled(false);
						this.btnRemove.setEnabled(false);
						this.menuItemAddNew.setEnabled(false);
						this.menuItemEdit.setEnabled(false);
						this.menuItemRemove.setEnabled(false);
					}else{
						this.btnAddNew.setEnabled(true);
						this.btnEdit.setEnabled(true);
						this.btnRemove.setEnabled(true);
						this.menuItemAddNew.setEnabled(true);
						this.menuItemEdit.setEnabled(true);
						this.menuItemRemove.setEnabled(true);
					}
				}else{
					this.btnAddNew.setEnabled(false);
					this.btnEdit.setEnabled(false);
					this.btnRemove.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
					this.menuItemEdit.setEnabled(false);
					this.menuItemRemove.setEnabled(false);
				}
			}
			if(STATUS_EDIT.equals(this.getOprtState())){
				this.btnEdit.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
			}
			if(this.editData.isAssigned()){//�ѷ���Ĳ�����ɾ��
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}			
			if(!this.editData.isIsSource()){//���������Ĳ������޸�ɾ��
				this.btnEdit.setEnabled(false);
				this.menuItemEdit.setEnabled(false);
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}
			if(STATUS_VIEW.equals(this.getOprtState())){
		        this.addCurProjectCache = editData.getCurProject();
		        this.addFullOrgUnitCache = editData.getFullOrgUnit();
		        this.addParentInfo = editData.getParent();
			}
		}		
		storeFields();
		initOldData(editData);
	}
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {//����״̬
			this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			this.btnCancel.setVisible(false);//���ð�ť���ɼ�
		} else if (STATUS_EDIT.equals(getOprtState())) {//�޸�״̬
			if (this.editData.isIsEnabled()) {//�����ǰΪ����״̬
				this.btnCancel.setVisible(true);//���ð�ť����    			
				this.btnCancel.setEnabled(true);//���ð�ť����
				this.btnCancelCancel.setVisible(false);//���ð�ť���ɼ�
			} else {//�����ǰΪ����״̬
				this.btnCancelCancel.setVisible(true);//���ð�ť�ɼ�
				this.btnCancelCancel.setEnabled(true);//���ð�ť����    			
				this.btnCancel.setEnabled(false);//���ð�ť���ɼ�    			
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//�鿴״̬			
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
				this.menuItemAddNew.setEnabled(true);
				this.menuItemEdit.setEnabled(true);
				this.menuItemRemove.setEnabled(true);
				this.btnCancel.setVisible(false);//
				this.btnCancelCancel.setVisible(false);//	
		}
	}
	/**
	 * У��ֵ����ĺϷ���
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// �����Ƿ�Ϊ��
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// �����Ƿ�Ϊ��
		boolean flag = FDCBaseDataClientUtils.isMultiLangBoxInputNameEmpty(bizName, this.editData, "name");
		if (flag) {
			bizName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
		if (!(this.txtNumber.getText().indexOf(".") < 0) || !(this.txtNumber.getText().indexOf("!") < 0)) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_CHECK_3);
		}
		
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	/**
	 * ����
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {		
		IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
		if (((IIncomeAccount) getBizInterface()).disable(pk)) {
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			this.btnCancelCancel.setVisible(true);
			this.btnCancelCancel.setEnabled(true);
			this.btnCancel.setVisible(false);		
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
	        loadFields();
			setSave(true);
	        setSaved(true);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
		super.actionEdit_actionPerformed(e);
    }
	/**
	 * ����
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		IObjectPK pk = new ObjectStringPK(this.editData.getId().toString());
		if (((IIncomeAccount) getBizInterface()).enable(pk)) {
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			this.btnCancel.setVisible(true);
			this.btnCancel.setEnabled(true);
			this.btnCancelCancel.setVisible(false);
			setDataObject(getValue(new ObjectUuidPK(editData.getId())));
	        loadFields();
			setSave(true);
	        setSaved(true);
		}
	}
    /**
     * output storeFields method
     * @author xiaobin_li
     * @work debug the bug that the value is not consistent when change the old value
     */
    public void storeFields()
    {
    	super.storeFields(); 
    	
    	if (this.txtNumber.getText() != null) {
			if (this.txtParentNumber.getText() != null && (!"".equals(this.txtParentNumber.getText().trim()))) {
				this.txtLongNumber.setText(this.txtParentNumber.getText() + "." + this.txtNumber.getText());
			} else {
				this.txtLongNumber.setText(this.txtNumber.getText());
			}
	    }
   
    	if(STATUS_EDIT.equals(this.getOprtState())){
    	    String longNumber = this.txtLongNumber.getText();
    		longNumber = longNumber.replace('.','!');
    		this.editData.setLongNumber(longNumber);
    		((IncomeAccountInfo)oldData).setLongNumber(longNumber);
    	} 	 
    }

	protected IObjectValue createNewData() {

		IncomeAccountInfo IncomeAccountInfo = new IncomeAccountInfo();
		IncomeAccountInfo.setFullOrgUnit(this.addFullOrgUnitCache);
		IncomeAccountInfo.setCurProject(this.addCurProjectCache);	
		IncomeAccountInfo.setParent(this.addParentInfo);
		IncomeAccountInfo.setIsSource(true);
		return IncomeAccountInfo;
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
			logger.error(exc.getMessage(), exc);
            return false;
        }

        return !ObjectValueForEditUIUtil.objectValueEquals(oldData, editData);
    }
	protected ICoreBase getBizInterface() throws Exception {
		return IncomeAccountFactory.getRemoteInstance();
	}
    /**
     * output txtNumber_focusLost method
     */
    protected void txtNumber_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    	if (this.txtNumber.getText() != null) {
			if (this.txtParentNumber.getText() != null && (!"".equals(this.txtParentNumber.getText().trim()))) {
				this.txtLongNumber.setText(this.txtParentNumber.getText() + "." + this.txtNumber.getText());
			} else {
				this.txtLongNumber.setText(this.txtNumber.getText());
			}
	    }
    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("isSource"));
        sic.add(new SelectorItemInfo("assigned"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("type"));
        sic.add(new SelectorItemInfo("isLeaf"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("longNumber"));        
        sic.add(new SelectorItemInfo("parent.id"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("fullOrgUnit.*"));
        return sic;
    }  

}