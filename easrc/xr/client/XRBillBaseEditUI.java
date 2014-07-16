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
 * 0.XRBillBaseEditUI()������
 * 1.createNewData()���� �����������ݡ�
 * 2.loadFields()������----���򿪽��桿������֮��
 * 3.onLoad()���������򿪽��桿
 * 4.onshow()���������򿪽��桿
 * 
 * storeFields()����������֮ǰ��
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
		//ע��������
		detachListeners();
		super.loadFields();
		//�����ϼ�����
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
			MsgBox.showWarning(this,"����״̬�Ѿ�������л�������ˣ��������ύ");
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
			MsgBox.showInfo("�˵��ݼ�¼��������������!");
			SysUtil.abort();
		}
	    super.actionEdit_actionPerformed(e);
	    if(editData.getStatus().equals(XRBillStatusEnum.SUBMITED)){
   		 actionSave.setEnabled(false);
   	}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(WorkflowXRHelper.checkInProInst(editData.getId().toString())){
			MsgBox.showInfo("�˵��ݼ�¼��������������!");
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
			MsgBox.showInfo("�˵��ݼ�¼��������������!");
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
    /**��ֹ�ύ֮����ֿհ׵���**/
    protected void afterSubmitAddNew(){
    	
   	}
	protected boolean isContinueAddNew() {
		return false;
	}
	/**
	 * ����������
	 * @throws BOSException
	 * @throws CodingRuleException
	 * @throws EASBizException
	 */
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		/*
		 * 2008-09-27��������ȡֵȡ���ˣ�Ӧ��ȡFDCBillInfo�й�����ID
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
		 * 2008-09-27�����������״̬��ֱ�ӷ���
		 * Ȼ��ֱ��жϳɱ����ĺ͵�ǰ��֯�Ƿ���ڱ������
		 */
		if(!STATUS_ADDNEW.equals(this.oprtState)){
			return;
		}
		boolean isExist = true;
		if(currentOrgId.length()==0||!iCodingRuleManager.isExist(editData, currentOrgId))
		{
			if(!iCodingRuleManager.isExist(editData, currentOrgId)) {
				//EditUI�ṩ�˷�������û�е��ã���onload����ã��Ը��ǳ�����loadfields����ĵ��ã��õ���û�д���Ϻ�ѡ��
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
						currentOrgId)) { // �˴���orgId�벽��1����orgIdʱһ�µģ��ж��û��Ƿ����öϺ�֧�ֹ���
					if (iCodingRuleManager.isUserSelect(editData, currentOrgId)) {
						// �����˶Ϻ�֧�ֹ���,ͬʱ�������û�ѡ��ϺŹ���
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								editData, currentOrgId, null, null);
						// Ҫ�ж��Ƿ���ڶϺ�,���򵯳�,���򲻵�//////////////////////////////////////////
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
	 * ���������ر���ؼ����������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	abstract protected KDTextField getNumberCtrl();
	//������
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
	 * �������������������ݣ�δ���޸ģ�ֱ�ӹر�ʱ�����ֱ�����ʾ
	 * ���ں�̨�߳�ִ�����Ż�
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
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