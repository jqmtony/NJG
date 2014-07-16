/**
 * output package name
 */
package com.kingdee.eas.xr.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.scm.common.client.SCMClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.xr.IXRBillBase;
import com.kingdee.eas.xr.XRBillBaseInfo;
import com.kingdee.eas.xr.app.XRBillStatusEnum;
import com.kingdee.eas.xr.helper.SysPlatformXRHelper;
import com.kingdee.eas.xr.helper.WorkflowXRHelper;
import com.kingdee.eas.xr.helper.common.FDCUIWeightWorker;
import com.kingdee.eas.xr.helper.common.IFDCWork;

/**
 * 0.XRBillBaseEditUI()方法；
 * 1.createNewData()方法 ；【新增单据】
 * 2.loadFields()方法；----【打开界面】【保存之后】
 * 3.onLoad()方法；【打开界面】
 * 4.onshow()方法；【打开界面】
 * 
 * storeFields()方法【保存之前】
 */
public abstract class XRBillBaseEditUI extends AbstractXRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(XRBillBaseEditUI.class);
    
    /**
     * output class constructor
     */
    public XRBillBaseEditUI() throws Exception
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
    public void onLoad() throws Exception {
    	super.onLoad();
    	if(editData.getStatus().equals(XRBillStatusEnum.SUBMITED)){
    		 actionSave.setEnabled(false);
    	}
    }
    
    public void loadFields() {
		//注销监听器
		detachListeners();
		super.loadFields();
		//最后加上监听器
		attachListeners();
	}
    public void onShow() throws Exception {
    	super.onShow();
    }
    protected void initWorkButton()
    {
        super.initWorkButton();
        btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
        btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	XRBillBaseInfo info = ((XRBillBaseInfo) editData);
		if(XRBillStatusEnum.AUDITED.equals(info.getStatus())
				|| XRBillStatusEnum.SUBMITED.equals(info.getStatus())){
			MsgBox.showWarning(this,"单据状态已经在审核中或者已审核，不能再提交");
			SysUtil.abort();
		}
		super.actionSubmit_actionPerformed(e);
		if(!getOprtState().equals(OprtState.ADDNEW)){
			actionSave.setEnabled(false);
		}else{
			actionSave.setEnabled(true);
			handleCodingRule();
		}
		handleOldData();
        actionSave.setEnabled(false);
    	actionRemove.setEnabled(false);
    	actionAudit.setEnabled(true);
    }
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
	    if(getOprtState().equals("VIEW"))
	        checkCanEdit();
	    if(editData.getStatus().equals(XRBillStatusEnum.SUBMITED)&&WorkflowXRHelper.checkInProInst(editData.getId().toString())){
			MsgBox.showInfo("此单据记录有流程正在运行!");
			SysUtil.abort();
		}
	    super.actionEdit_actionPerformed(e);
	    if(editData.getStatus().equals(XRBillStatusEnum.SUBMITED)){
   		 actionSave.setEnabled(false);
   	}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
			MsgBox.showInfo("此单据记录有流程正在运行!");
			SysUtil.abort();
		}
		super.actionAudit_actionPerformed(e);
		IXRBillBase iXRBillBase = (IXRBillBase)getBizInterface();
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
        iXRBillBase.audit(pk);
        setDataObject(getValue(pk));
        loadFields();
        actionSave.setEnabled(false);
    	actionRemove.setEnabled(false);
    	actionSubmit.setEnabled(false);
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
			MsgBox.showInfo("此单据记录有流程正在运行!");
			SysUtil.abort();
		}
		super.actionUnAudit_actionPerformed(e);
		IXRBillBase iXRBillBase = (IXRBillBase)getBizInterface();
		ObjectUuidPK pk = new ObjectUuidPK(editData.getId());
        iXRBillBase.unAudit(pk);
        setDataObject(getValue(pk));
        loadFields();
	}
	 protected void initDataStatus()
    {
        super.initDataStatus();
        setStatus();
    }

	protected KDTable getDetailTable() {
		return null;
	}
	protected void checkCanEdit()throws Exception
	{
	    if(editData == null && editData.getId() == null)
	    {
	        SysUtil.abort();
	    } else
	    {
	    	XRBillStatusEnum baseStatus = editData.getStatus();
	        if(baseStatus != null)
	        {
	            int baseStatusValue = baseStatus.getValue();
	            if(baseStatusValue != 0 && baseStatusValue != 1 && baseStatusValue != 2)
	            {
	                MsgBox.showError(this, SCMClientUtils.getResource("BillAt") + baseStatus.getAlias() + SCMClientUtils.getResource("CantBeEdited"));
	                SysUtil.abort();
	            }
	        }
	    }
	}
	
	public void setStatus()
    {
        actionTraceUp.setEnabled(true);
        btnTraceUp.setEnabled(true);
        menuItemTraceUp.setEnabled(true);
        actionTraceDown.setEnabled(true);
        btnTraceDown.setEnabled(true);
        menuItemTraceDown.setEnabled(true);
        XRBillBaseInfo info = (XRBillBaseInfo)dataBinder.getValueObject();
        if("ADDNEW".equals(getOprtState()))
        {
            actionSave.setEnabled(true);
            actionSubmit.setEnabled(true);
            actionTraceUp.setEnabled(false);
            menuItemTraceUp.setEnabled(false);
            actionTraceDown.setEnabled(false);
            menuItemTraceDown.setEnabled(false);
//            if(isBotpBill())
//                setAddLineStatus(false);
//            else
//                setAddLineStatus(true);
            setRemoveLineStatus(true);
            setCreateFromStatus(true);
            setAuditStatus(false);
            setUnAuditStatus(false);
            btnCopy.setEnabled(false);
            btnRemove.setEnabled(false);
            menuItemCopy.setEnabled(false);
            menuItemRemove.setEnabled(false);
        } else
        if("VIEW".equals(getOprtState()) || "FINDVIEW".equals(getOprtState()))
        {
            setAddLineStatus(false);
            setRemoveLineStatus(false);
            setCreateFromStatus(false);
            if(info.getStatus() != null && info.getStatus() == XRBillStatusEnum.SUBMITED)
            {
                if("FINDVIEW".equals(getOprtState()))
                    setAuditStatus(false);
                else
                    setAuditStatus(true);
            } else
            {
                setAuditStatus(false);
            }
            if(info.getStatus() != null && info.getStatus() == XRBillStatusEnum.AUDITED)
            {
                if("FINDVIEW".equals(getOprtState()))
                    setUnAuditStatus(false);
                else
                    setUnAuditStatus(true);
            } else
            {
                setUnAuditStatus(false);
            }
            if("FINDVIEW".equals(getOprtState()))
            {
                btnCopy.setEnabled(false);
                btnRemove.setEnabled(false);
                menuItemCopy.setEnabled(false);
                menuItemRemove.setEnabled(false);
            } else
            {
                btnCopy.setEnabled(true);
                btnRemove.setEnabled(true);
                menuItemCopy.setEnabled(true);
                menuItemRemove.setEnabled(true);
            }
        } else
        if("EDIT".equals(getOprtState()))
        {
            actionSave.setEnabled(true);
            actionSubmit.setEnabled(true);
//            if(isBotpBill())
//                setAddLineStatus(false);
//            else
//                setAddLineStatus(true);
            setRemoveLineStatus(true);
            setCreateFromStatus(true);
            if(info.getStatus() != null && info.getStatus() == XRBillStatusEnum.SUBMITED)
                setAuditStatus(true);
            else
                setAuditStatus(false);
            if(info.getStatus() != null && info.getStatus() == XRBillStatusEnum.AUDITED)
                setUnAuditStatus(true);
            else
                setUnAuditStatus(false);
            btnCopy.setEnabled(true);
            btnRemove.setEnabled(true);
            menuItemCopy.setEnabled(true);
            menuItemRemove.setEnabled(true);
        }
    }

    protected void setAddLineStatus(boolean status)
    {
        actionAddLine.setEnabled(status);
        actionInsertLine.setEnabled(status);
        menuItemAddLine.setEnabled(status);
        menuItemInsertLine.setEnabled(status);
        btnAddLine.setEnabled(status);
        btnInsertLine.setEnabled(status);
    }

    protected void setRemoveLineStatus(boolean status)
    {
        actionRemoveLine.setEnabled(status);
        menuItemRemoveLine.setEnabled(status);
        btnRemoveLine.setEnabled(status);
    }

    protected void setCreateFromStatus(boolean status)
    {
        actionCreateFrom.setEnabled(status);
        menuItemCreateFrom.setEnabled(status);
        btnCreateFrom.setEnabled(status);
    }

    protected void setAuditStatus(boolean status)
    {
        actionAudit.setEnabled(status);
        btnAudit.setEnabled(status);
    }
    

    protected void setUnAuditStatus(boolean status)
    {
        actionUnAudit.setEnabled(status);
        btnUnAudit.setEnabled(status);
    }
    /**防止提交之后出现空白单据**/
    protected void afterSubmitAddNew(){
    	
   	}
	protected boolean isContinueAddNew() {
		return false;
	}
	/**
	 * 处理编码规则
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		/*
		 * 2008-09-27编码规则的取值取错了，应当取FDCBillInfo中关联的ID
		 */
		String currentOrgId = "";
		if(editData instanceof XRBillBaseInfo){
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			if(org == null){
				org = SysContext.getSysContext().getCurrentOrgUnit();
			}
			currentOrgId = org.getId().toString();
		}
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		/*
		 * 2008-09-27如果不是新增状态，直接返回
		 * 然后分别判断成本中心和当前组织是否存在编码规则
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI提供了方法，但没有调用，在onload后调用，以覆盖抽象类loadfields里面的调用（该调用没有处理断号选择）
				isExist = false;
			}
		}
		if( isExist ){
			boolean isAddView = SysPlatformXRHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView) {
				getNumberByCodingRule(editData, currentOrgId);
			}
			else {
				String number = null;

				if (iCodingRuleManager.isUseIntermitNumber(editData,
						currentOrgId)) { // 此处的orgId与步骤1）的orgId时一致的，判断用户是否启用断号支持功能
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// 要判断是否存在断号,是则弹出,否则不弹//////////////////////////////////////////
						Object object = null;
						if (iCodingRuleManager.isDHExist(editData, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					}
				}
				getNumberCtrl().setText(number);
			}
			setNumberTextEnabled();
		}
	}
	/**
	 *
	 * 描述：返回编码控件（子类必须实现）
	 * @return
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	abstract protected KDTextField getNumberCtrl();
	//监听器
	protected Map listenersMap = new HashMap();
    protected abstract void attachListeners();
    protected abstract void detachListeners();
    
    protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
		
    }
        
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    /**
	 *
	 * 描述：避免在新增单据（未作修改）直接关闭时，出现保存提示
	 * 放在后台线程执行以优化
	 * @author:liupd
	 * 创建时间：2006-10-13 <p>
	 */
	protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	protected IObjectValue createNewData() {
		return super.createNewData();
	}
	
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		editData.put("status", XRBillStatusEnum.ADD);
		super.actionCopy_actionPerformed(e);
	}
	/**
     * output actionPrint_actionPerformed method
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
    	if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    	

    /**
     * output actionPrintPreview_actionPerformed method
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        ArrayList idList = new ArrayList();
        if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
    		idList.add(editData.getString("id"));
    	}
        if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null)
            return;
        com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate data = new com.kingdee.eas.framework.util.CommonDataProvider(idList,getTDQueryPK());
        com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
        appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
    }
    protected String getTDFileName() {
    	return "/bim/xr/XRBillBase";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("");
	}
}