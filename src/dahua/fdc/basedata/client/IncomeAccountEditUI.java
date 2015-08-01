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
 * 描述:收入科目编辑
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
			// //设置parent
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
//			 设置编辑和查看状态下界面的上级编码控件和本级简码控件
			String longNumber = editData.getLongNumber();
			if(longNumber!=null){
				longNumber = longNumber.replace('!', '.');
				this.txtLongNumber.setText(longNumber);
				if (longNumber.indexOf(".") > 0) {
					this.txtParentNumber.setText(longNumber.substring(0, longNumber.lastIndexOf(".")));
				}
			}
			
//			如果当前的组织不是集团
			if((SysContext.getSysContext().getCurrentFIUnit()!=null&&(!SysContext.getSysContext().getCurrentFIUnit().isIsOnlyUnion()))
					||(SysContext.getSysContext().getCurrentCostUnit()!=null&&SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit())){
				if(!this.editData.isIsLeaf()){
					this.btnAddNew.setEnabled(false);
					this.menuItemAddNew.setEnabled(false);
				}
			}else{//如果当前的组织是集团，并且当前选中的节点不是集团，那么不允许新增
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
			if(this.editData.isAssigned()){//已分配的不允许删除
				this.btnRemove.setEnabled(false);
				this.menuItemRemove.setEnabled(false);
			}			
			if(!this.editData.isIsSource()){//分配下来的不允许修改删除
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
		if (STATUS_ADDNEW.equals(getOprtState())) {//新增状态
			this.btnCancelCancel.setVisible(false);//启用按钮不可见
			this.btnCancel.setVisible(false);//禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {//修改状态
			if (this.editData.isIsEnabled()) {//如果当前为启用状态
				this.btnCancel.setVisible(true);//禁用按钮可用    			
				this.btnCancel.setEnabled(true);//禁用按钮可用
				this.btnCancelCancel.setVisible(false);//启用按钮不可见
			} else {//如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);//启用按钮可见
				this.btnCancelCancel.setEnabled(true);//启用按钮可用    			
				this.btnCancel.setEnabled(false);//禁用按钮不可见    			
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {//查看状态			
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
	 * 校验值对象的合法性
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		// 编码是否为空
		if (this.txtNumber.getText() == null || this.txtNumber.getText().trim().equals("")) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		// 名称是否为空
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
	 * 禁用
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
	 * 启用
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
     * 克隆旧值，用来检查是否修改
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
     * 判断当前编辑的数据是否发生变化
     */
    public boolean isModify() {

        //如果是Onload时的中断处理，则不会进行数据比较。2006-8-22 by psu_s
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
			// 工作流需要知道是否发生了异常
			//wfContext.setThrowException(true);

			abort();
        }

        //        return false;
        /* 去掉这个判断。没有什么性能损耗。 2006-9-21
        if(isSave())
        {
            return false;
        }*/
        //查看状态不判断是否修改
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