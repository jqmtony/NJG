/**
 * output package name
 */
package com.kingdee.eas.fdc.photomanager.client;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.client.ForecastChangeVisEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.photomanager.PhotoAuditCollection;
import com.kingdee.eas.fdc.photomanager.PhotoAuditFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.rpts.snapshot.manage.client.SnapshotRunUI;
import com.kingdee.eas.rpts.snapshot.manage.exception.SnapshotException;
import com.kingdee.eas.rpts.snapshot.manage.service.ISnapshotRelativeService;
import com.kingdee.eas.rpts.snapshot.manage.service.SnapshotRelativeService;
import com.kingdee.eas.rpts.sumreport.util.ExtMsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDLayout;

/**
 * output class name
 */
public class PhotoAuditEditUI extends AbstractPhotoAuditEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PhotoAuditEditUI.class);
    private final static String CANTAUDIT = "cantAudit";
	private static final String CANTEDIT = "cantEdit";
	
	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	private SnapshotRunUI rptUI = null;
	private ISnapshotRelativeService _serv = SnapshotRelativeService.getInst();
    /**
     * output class constructor
     */
    public PhotoAuditEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initControl();
    }
    
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		this.btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		this.btnAddNew.setIcon(EASResource.getIcon("imgTbtn_referbatch"));
		ForecastChangeVisEditUI.setEnableAcion(new ItemAction[]{actionPrint,actionPrintPreview,actionCreateTo,actionCreateFrom,actionAddNew,actionCreateTo,actionCopy,actionLast,actionFirst,actionNext,actionPre});
    }
    
    private void initControl() throws SnapshotException, UIException{
    	this.kDComboBox1.addItems(new String[]{"方案板","执行版"});
    	this.kDComboBox1.setRequired(true);
    	if(getOprtState().equals(OprtState.ADDNEW))
    		this.kDComboBox1.setSelectedItem(null);
    	this.setUITitle(editData.getNumber());
    	String snapshotID = editData.getSourceBillId();
    	byte data[] = _serv.getSnapshotData(snapshotID);
    	
    	if(data == null || data.length == 0){
    		ExtMsgBox.showInfo(this, "\u5FEB\u7167\u6570\u636E\u5DF2\u88AB\u5220\u9664");
    		return;
        }
        Map uiContext = new UIContext(this);
        uiContext.put("MainMenuName","呵呵"); 
        uiContext.put("SNAPSHOT_DATA", data);
        
        rptUI = (SnapshotRunUI)UIFactoryHelper.initUIObject(SnapshotRunUI.class.getName(), uiContext, null, "VIEW");
        rptUI.runEXT();
        kDPanel1.add(rptUI,BorderLayout.CENTER);
        kDPanel1.updateUI();
        
        if(UIRuleUtil.getString(editData.getReportName()).equals("目标成本")){
        	this.contstage.setVisible(true);
        }else{
        	kDPanel1.setBounds(new Rectangle(3, 3, 1007, 625));
        	this.add(kDPanel1, new KDLayout.Constraints(3, 3, 1007, 625, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        	this.contstage.setVisible(false);
        }
    }
    
    public boolean checkBeforeWindowClosing() {
    	if(rptUI!=null)
    		rptUI.destroyWindow();
    	return super.checkBeforeWindowClosing();
    }
    
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
    	super.actionAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
    	super.actionUnAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
    }
    
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
	
	public void storeFields() {
		editData.setStage(this.kDComboBox1.getSelectedItem()!=null?this.kDComboBox1.getSelectedItem().toString():"");
		super.storeFields();
	}
	
	public void loadFields() {
		super.loadFields();
		if(UIRuleUtil.isNotNull(editData.getStage())){
			this.kDComboBox1.setSelectedItem(editData.getStage());
		}
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(UIRuleUtil.getString(editData.getReportName()).equals("目标成本")){
			if(this.kDComboBox1.getSelectedItem()==null){
				FDCMsgBox.showWarning("请填写成本所属阶段！");
				SysUtil.abort();
			}
		}
		super.actionSubmit_actionPerformed(e);
		this.lockUIForViewStatus();
    	this.setOprtState("VIEW");
	}
	
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	checkBeforeEditOrRemove(CANTEDIT);
    	super.actionEdit_actionPerformed(e);
    	setSaveActionStatus();
    }
    
	protected void checkBeforeEditOrRemove(String warning) throws BOSException {
		FDCBillStateEnum state = editData.getStatus();
		if (state != null
				&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED || state == FDCBillStateEnum.CANCEL )) {
			MsgBox.showWarning(this, ContractClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}
	
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception{
		//检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null
				&& editData.getStatus() != null
				&& editData.getStatus().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		 if(getOprtState().equals(STATUS_EDIT)) {
			 String warn = null;
			 if(state.equals(FDCBillStateEnum.AUDITTED)) {
				 warn = CANTUNAUDITEDITSTATE;
			 }
			 else {
				 warn = CANTAUDITEDITSTATE;
			 }
			 MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			 SysUtil.abort();
		 }
	}
	
	protected void setSaveActionStatus() {
		if (editData.getStatus() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
		setButtonStatus();
	}
	
	protected void setButtonStatus() {
		boolean flse = (getOprtState().equals("ADDNEW")||getOprtState().equals("EDIT"))?true:false;
	}
     
	protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
    
	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.photomanager.PhotoAuditFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	if(getUIContext().get("rptId")==null){
    		FDCMsgBox.showWarning("请先选择要发起的快照！");
    		SysUtil.abort();
    	}
    	String rptId = getUIContext().get("rptId").toString();
    	String oql = "select reportName,CU.id,CU.number,CU.name,number,id,sourceBillId where sourceBillId='"+rptId+"'";
    	PhotoAuditCollection photoAuditCollection = null;
		try {
			photoAuditCollection = PhotoAuditFactory.getRemoteInstance().getPhotoAuditCollection(oql);
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	if(photoAuditCollection.size()>0)
    		return photoAuditCollection.get(0);
    	else{
    		com.kingdee.eas.fdc.photomanager.PhotoAuditInfo objectValue = new com.kingdee.eas.fdc.photomanager.PhotoAuditInfo();
    		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    		objectValue.setSourceBillId(rptId);
    		objectValue.setNumber(getUIContext().get("rptName").toString());
    		objectValue.setStatus(FDCBillStateEnum.SAVED);
    		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    		objectValue.setReportName(getUIContext().get("rptType").toString());
    		return objectValue;
    	}
    }

}