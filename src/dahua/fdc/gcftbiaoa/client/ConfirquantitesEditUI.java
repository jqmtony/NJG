/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesInfo;
import com.kingdee.eas.fdc.wfui.WFResultApporveHelper;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ConfirquantitesEditUI extends AbstractConfirquantitesEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ConfirquantitesEditUI.class);
	private final static String CANTAUDIT = "cantAudit";
    private int groupIndex=0;
	private static final String CANTEDIT = "cantEdit";
	private IUIWindow acctUI=null;
    private String contractBillId=null;
	private Map parentMap = new HashMap();
    private List oldCostAccountLongNumber = new ArrayList();
	private HashMap entrysMap = new HashMap(); 
    public com.kingdee.eas.fdc.aimcost.ForecastChangeVisInfo editData2;

	private final static String CANTUNAUDIT = "cantUnAudit";

	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
    /**
     * output class constructor
     */
    public ConfirquantitesEditUI() throws Exception
    {
        super();
    }

    
    public void onLoad() throws Exception {
    	this.prmtcontractNumber.setEnabled(false);
    	this.contprojrct.setEnabled(false);
    	
        this.txtChangeAmount.setHorizontalAlignment(2);		
        this.txtChangeAmount.setDataType(1);		
        this.txtChangeAmount.setSupportedEmpty(true);		
        this.txtChangeAmount.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtChangeAmount.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtChangeAmount.setPrecision(2);	
        
        this.txtBanane.setHorizontalAlignment(2);		
        this.txtBanane.setDataType(1);		
        this.txtBanane.setSupportedEmpty(true);		
        this.txtBanane.setMinimumValue( new java.math.BigDecimal("-1.0E26"));		
        this.txtBanane.setMaximumValue( new java.math.BigDecimal("1.0E26"));		
        this.txtBanane.setPrecision(2);		
        this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
    	super.onLoad();
    	initButtonStatus();
    	initTable();
    	contractBillId = editData.getContractNumber().getId().toString();
    	
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
    	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
    	
    	this.txtversion.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtChangeAmount.setHorizontalAlignment(JTextField.RIGHT);
    	this.txtBanane.setHorizontalAlignment(JTextField.RIGHT);
    }
    
     
    protected void initWorkButton() {
    	super.initWorkButton();
    	this.btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		this.btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		
		
		setEnableAcion(new ItemAction[]{actionAddNew,actionCreateTo,actionCopy,actionLast,actionFirst,actionNext,actionPre});
		
		this.actionAcctSelect.setVisible(false);
		this.actionSplitProj.setVisible(false);
		this.actionSplitProd.setVisible(false);
		this.actionSplitBotUp.setVisible(false);
		
    }
    
    public static void setEnableAcion(ItemAction action[]){
    	for (int i = 0; i < action.length; i++) {
    		ItemAction ACTION = action[i];
    		if(ACTION==null)
    			continue;
    		ACTION.setEnabled(false);
    		ACTION.setVisible(false);
		}
    }
    
    private void initButtonStatus(){
    	this.txtNumber.setRequired(true);
    	this.pkBizDate.setRequired(true);
    	this.actionBananZreo.setVisible(false);
    }
    
    
    
    
    
    private void initTable(){
    	
    }
    
    protected void initCtrlListener(){
	}
    
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED_MILLION);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	public KDTDefaultCellEditor getScaleCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(10);
        kdc.setMinimumValue(FDCHelper._ONE_HUNDRED_MILLION);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED_MILLION);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
//    	FDCClientVerifyHelper.verifyEmpty(this, pkBizDate);
    	
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
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
	
	public boolean isModify() {
		if (STATUS_VIEW.equals(getOprtState())) {
			return false;
		}
		return super.isModify();
	}
    
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        String billId = null;
        if(editData.getId()!= null){
        	billId = editData.getId().toString();
           	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
           	fuzhi(apporveResultForMap, "申报工作量", txtworking, "working", txtcontractorPerosn, "contractorPerosn");
           	fuzhi(apporveResultForMap, "工程部审核", txtengineeringAudit, "engineeringAudit", txtengineeringPerosn, "engineeringPerosn");
           	fuzhi(apporveResultForMap, "成本管理部审核", txtcostAudit, "costAudit", txtcostPerosn, "costPerosn");
           	fuzhi(apporveResultForMap, "项目公司第一负责人审核", txtprojectFirstAudit, "projectFirstAudit", txtfirstPerosn, "firstPerosn");
    	}
        setSaveActionStatus();
    }
    public void fuzhi(Map<String, String> map,String nodeName,KDTextField yjField,String yjkey,KDTextField pField,String pkey){
      	if(map.get(nodeName) != null){
       		String result = map.get(nodeName);	
       		String person = result.substring(0,result.indexOf("!"));
       		yjField.setText(result);
       		editData.put(yjkey, result);
       		pField.setText(person);
       		editData.put(pkey, person);
      	}
    }
    
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	if(!editData.isIsLast()){
    		FDCMsgBox.showInfo("不是最新版，不能反审批！");
			this.abort();
    	}
    	checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
    	super.actionUnAudit_actionPerformed(e);
    	FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
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
	
	protected void setSaveActionStatus() {
		if (editData.getStatus() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
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
    
	
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.gcftbiaoa.ConfirquantitesFactory.getRemoteInstance();
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
    	if(getUIContext().get("ForInfo")!=null){
    		ConfirquantitesInfo info = (ConfirquantitesInfo) getUIContext().get("ForInfo");
    		info.setVersion(getUIContext().get("verson").toString());
    	     info.getProjrct().getId();
    		info.setId(null);
    		info.setIsLast(false);
    		return info;
    	}else{
    		ConfirquantitesInfo objectValue = new ConfirquantitesInfo();
    		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    		if(getUIContext().get("contractInfo")==null){
    			MsgBox.showWarning("合同不能为空！");
    			SysUtil.abort();
    		}
    		ContractBillInfo conInfo = (ContractBillInfo)getUIContext().get("contractInfo");
    		objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
    		objectValue.setContractNumber(conInfo);
    		objectValue.setVersion("1");
    		objectValue.setProjrct(conInfo.getCurProject());
    		objectValue.setContractorUnit(conInfo.getPartB());
    		objectValue.setEndTime(new Date());
    		return objectValue;
    	}
    }

}