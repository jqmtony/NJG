package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.client.f7.CompanyF7;
import com.kingdee.eas.bpm.common.StringUtilBPM;
import com.kingdee.eas.bpmdemo.JBrowserHelper.JFrameBrowser;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.BPMServiceForERPSoap;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.EASLoginProxyServiceLocator;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSContext;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxy;
import com.kingdee.eas.bpmdemo.webservers.serviceclient.WSgetInfoFacadeSrvProxyServiceLocator;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextBgEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.remk.CRMHelper;
import com.kingdee.eas.fdc.remk.client.CRMClientHelper;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.client.NewBgItemDialog;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractWithoutTextEditUIPIEx extends ContractWithoutTextEditUI{
	private ContractTypeInfo typeInfo;
	private boolean isShiGong = false;
	private boolean isFenBao = false;
	private boolean isZongBao = false;
	
	public ContractWithoutTextEditUIPIEx() throws Exception {
		super();
	}
    private void InitButton()
    {
    	this.btnAttachment.setText("撤销BPM流程");
    	this.btnAttachment.setToolTipText("撤销BPM流程");
    	this.btnSubmit.setText("提交BPM流程");
    	this.btnSubmit.setToolTipText("提交BPM流程");
    	
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
	   	this.chkMenuItemSubmitAndAddNew.setEnabled(false);
	   	this.btnPrint.setVisible(false);
	   	this.btnPrintPreview.setVisible(false);
	   	this.btnPre.setVisible(false);
	   	this.btnNext.setVisible(false);
	   	this.btnLast.setVisible(false);
	   	this.btnFirst.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionMultiapprove.setVisible(false);
    	this.actionNextPerson.setVisible(false);
    	this.btnWorkFlowG.setVisible(false);
    	if(editData.getId()==null||editData.getId().equals(""))
    	{
         if(editData.getState()==null)
         {
    	  this.btnAuditResult.setEnabled(false);
    	  this.btnAttachment.setEnabled(false);
         }
         else if("保存".equals(editData.getState().getAlias()))
         {
        	 this.btnAuditResult.setEnabled(true);
       	     this.btnAttachment.setEnabled(false);
         }
    	}    	
    	else if(editData.getId()!=null||editData.getState()==null)
     	{   
    		if("审批中".equals(editData.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(true);
           	    this.btnAttachment.setEnabled(true);
    		}
    		else if("已审批".equals(editData.getState().getAlias()))
    		{
    			this.btnAuditResult.setEnabled(true);
           	    this.btnAttachment.setEnabled(false);
    		}
    		else if("保存".equals(editData.getState().getAlias()))
    	         {
    	        	 this.btnAuditResult.setEnabled(true);
    	       	     this.btnAttachment.setEnabled(false);
    	         }
    		else
    		{
     		this.btnAuditResult.setEnabled(false);
       	    this.btnAttachment.setEnabled(false);
    		}
     	}

    }
    
	protected boolean isContinueAddNew() {
		return false;
	}
	
	protected void afterSubmitAddNew() {
	}
	
	
	/*
	 * 新增
	 * */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		this.btnAttachment.setEnabled(false);
    	this.btnAuditResult.setEnabled(false);
	}
	/*
	 * 删除
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能删除！");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	
	/*
	 * 修改
	 * */
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);
		if(editData.getId()!=null){
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能修改！");
				SysUtil.abort();
			}
		}
    	this.btnRemove.setVisible(false);
    	this.btnRemove.setEnabled(false);
	}
	

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()==null)
			actionSave_actionPerformed(e);
		if(editData.getId()!=null)
		{  
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
		   if("审批中".equals(info.getState().getAlias()) && info.getDescription()!=null)
		   {
			   MsgBox.showInfo("该单据在审批流程中，不能再次提交！");
		   }
		   else if("保存".equals(info.getState().getAlias()) && info.getDescription()=="BPM拒绝")
		   {
			   MsgBox.showInfo("该单据被拒绝，不能再次提交！");
		   }
		   else{
			   super.actionSubmit_actionPerformed(e);
			   String sql = " update t_con_contractwithouttext set fState='1SAVED' where fid='"+editData.getId()+"'";
			   FDCSQLBuilder bu = new FDCSQLBuilder();
			   bu.appendSql(sql);
			   bu.executeUpdate();
//			   String [] str1 = new String[3];
//			   	//EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://10.130.12.34:7888/ormrpc/services/EASLogin"));
//			   EASLoginProxy login = new EASLoginProxyServiceLocator().getEASLogin(new URL("http://127.0.0.1:56898/ormrpc/services/EASLogin"));
//			   	WSContext  ws = login.login("kd-user", "kduser", "eas", "test1113", "l2", 1);
//			    if(ws.getSessionId()!=null){
//			    	//WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://10.130.12.34:7888/ormrpc/services/WSgetInfoFacade"));
//			    	WSgetInfoFacadeSrvProxy pay = new WSgetInfoFacadeSrvProxyServiceLocator().getWSgetInfoFacade(new URL("http://127.0.0.1:56898/ormrpc/services/WSgetInfoFacade"));
//			    	str1 = pay.getbillInfo("", editData.getId().toString());
//			    	MsgBox.showInfo(str1[0] + str1[1] + str1[2]);
//			    	String url = "http://10.130.12.20/BPMStart.aspx?bsid=ERP&boid="+editData.getId().toString()+"&btid=WWBHT01";
//			    	str1 = pay.submitResult("", editData.getId().toString(), true, 1,url, editData.getId().toString());
//			    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//			    	str1 = pay.approveClose("", editData.getId().toString(), 1, "1", "",null);
//			    	MsgBox.showInfo(str1[0]+str1[1]+str1[2]);
//			    }
			   String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=WWBHT01&userid="+SysContext.getSysContext().getUserName()+"";
			   creatFrame(url);
		   }
		}
	}
	
	/*
	 * 撤销流程
	 * */
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		String result = "";
		if(editData.getId()!=null){
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
			if("审批中".equals(info.getState().getAlias()))
			{
				BPMServiceForERPSoap  login = new BPMServiceForERPLocator().getBPMServiceForERPSoap();
				result = login.withdraw("WWBHT01", info.getId().toString(), info.getSourceFunction());
			}else{
				MsgBox.showInfo("该单据不在审批流程中，无需撤销流程！");
			}
		}
	}
	/*
	 * 审批
	 * */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
	    	String url = info.getDescription();
			if("审批中".equals(info.getState().getAlias()) && ("".equals(info.getDescription())||info.getDescription()==null))
			{
				super.actionAudit_actionPerformed(e);
			}else{
				if("审批中".equals(info.getState().getAlias())){
					MsgBox.showInfo("该单据在审批流程中，不能进行人工审批！");
				}else {
					MsgBox.showInfo("该单据状态为:"+info.getState().getAlias()+",不能审批！");
				}
			}
		}
		
	}


	/*
	 * 查看审批结果
	 * */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			ContractWithoutTextInfo info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(editData.getId()));
			// String url = StringUtilBPM.getBPMServerURL()+"?bsid=ERP&boid="+editData.getId().toString()+"&btid=WWBHT01&userid="+SysContext.getSysContext().getUserName()+"";
			String url=info.getDescription();
			if("已审批".equals(info.getState().getAlias())||"审批中".equals(info.getState().getAlias()))
			{
				creatFrame(url);
			}
			else if(info.getDescription()!=null&&"保存".equals(info.getState().getAlias())&&info.getDescription().contains("http"))
			{
				creatFrame(url);
			}
			else if("保存".equals(info.getState().getAlias()))
			{
				MsgBox.showInfo("该单据未发起审批流程,没有对应流程！");
			}
			else{
				MsgBox.showInfo("该单据未发起审批流程，或者已撤销流程，没有对应流程！");
			}
		}
	}
	
	private void creatFrame(String url)
    {
    	//获取MD5加密
    	JFrameBrowser jf = new JFrameBrowser();
    	jf.setJBrowserSize(720, 1200);
    	jf.setJBrwserOpenUrl(url);
    	jf.setTitle("BPM");
    	jf.OpenJBrowser(this);
    }
	public void onLoad() throws Exception {
		super.onLoad();
		InitButton();
		
		initBgEntryTable();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
        this.prmtCostedDept.setRequired(true);
        
        view=new EntityViewInfo();
		filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		view.setFilter(filter);
        this.prmtCostedCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyOrgUnitQuery");
        CompanyF7 comp = new CompanyF7(this);
        this.prmtCostedCompany.setEntityViewInfo(view);
        this.prmtCostedCompany.setSelector(comp);
        this.prmtCostedCompany.setDisplayFormat("$name$");
        this.prmtCostedCompany.setEditFormat("$number$");
        this.prmtCostedCompany.setCommitFormat("$number$");
        
        this.prmtCostedCompany.setRequired(true);
        this.prmtCostedCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtCostedDept.setRequired(true);
        this.prmtCostedDept.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCostedDept_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        setBgEditState();
	}
	public void onShow()throws Exception{
		super.onShow();
		setBgEditState();
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		setBgEditState();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic=super.getSelectors();
		sic.add("bgEntry.*");
		sic.add("bgEntry.bgItem.*");
		sic.add("bgEntry.currency.*");
		return sic;
	}
	protected void loadBgEntryTable(){
		ContractWithoutTextBgEntryCollection col=editData.getBgEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtBgEntry.removeRows();
		for(int i=0;i<col.size();i++){
			ContractWithoutTextBgEntryInfo entry=col.get(i);
			IRow row=this.kdtBgEntry.addRow();
			row.setUserObject(entry);
			row.getCell("bgItem").setValue(entry.getBgItem());
			row.getCell("currency").setValue(entry.getCurrency());
			row.getCell("rate").setValue(entry.getRate());
			row.getCell("amount").setValue(entry.getAmount());
			row.getCell("requestAmount").setValue(entry.getRequestAmount());
			row.getCell("bgBalance").setValue(entry.getBgBalance());
			row.getCell("remark").setValue(entry.getRemark());
		}
	}
	protected void storeBgEntryTable(){
		editData.getBgEntry().clear();
    	for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
    		IRow row = this.kdtBgEntry.getRow(i);
    		ContractWithoutTextBgEntryInfo entry=(ContractWithoutTextBgEntryInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setBgItem((BgItemInfo)row.getCell("bgItem").getValue());
    		entry.setCurrency((CurrencyInfo)row.getCell("currency").getValue());
    		entry.setRate((BigDecimal)row.getCell("rate").getValue());
    		entry.setAmount((BigDecimal)row.getCell("amount").getValue());
    		entry.setRequestAmount((BigDecimal)row.getCell("requestAmount").getValue());
    		entry.setBgBalance((BigDecimal)row.getCell("bgBalance").getValue());
    		entry.setRemark((String)row.getCell("remark").getValue());
    		editData.getBgEntry().add(entry);
    	}
	}
	protected void initBgEntryTable(){
		KDWorkButton btnAddRowinfo=new KDWorkButton();
    	KDWorkButton btnInsertRowinfo=new KDWorkButton();
    	KDWorkButton btnDeleteRowinfo=new KDWorkButton();
    	
    	this.actionAddLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionAddLine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionInsertLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionInsertLine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		this.actionRemoveLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)this.contBgEntry.add(this.actionRemoveLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtBgEntry.checkParsed();
		
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtBgEntry.getColumn("requestAmount").setEditor(weight);
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBgEntry.getColumn("requestAmount").getStyleAttributes().setLocked(true);
		
		this.kdtBgEntry.getColumn("amount").setEditor(weight);
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBgEntry.getColumn("amount").setRequired(true);
		
		this.kdtBgEntry.getColumn("rate").setEditor(weight);
		this.kdtBgEntry.getColumn("rate").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("rate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBgEntry.getColumn("rate").getStyleAttributes().setLocked(true);
		
		this.kdtBgEntry.getColumn("bgBalance").setEditor(weight);
		this.kdtBgEntry.getColumn("bgBalance").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtBgEntry.getColumn("bgBalance").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtBgEntry.getColumn("bgBalance").getStyleAttributes().setLocked(true);
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		NewBgItemDialog bgItemDialog=new NewBgItemDialog(this);
		bgItemDialog.setMulSelect(false);
		bgItemDialog.setSelectCombinItem(false);
		f7Box.setSelector(bgItemDialog);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("bgItem").setEditor(f7Editor);
		this.kdtBgEntry.getColumn("bgItem").setRequired(true);
		
		f7Box = new KDBizPromptBox();
		f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7CurrencyQuery");
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$name$");
		f7Box.setCommitFormat("$name$");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtBgEntry.getColumn("currency").setEditor(f7Editor);
		this.kdtBgEntry.getColumn("currency").setRequired(true);
		
		this.kdtBgEntry.getColumn("remark").setRequired(true);
	}
	protected void kdtBgEntry_tableSelectChanged(KDTSelectEvent e) throws Exception {
		getBgAmount();
	}
	protected void kdtBgEntry_editStopped(KDTEditEvent e) throws Exception {
		if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("bgItem")){
			if(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue() instanceof BgItemObject){
				BgItemObject bgItem=(BgItemObject) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").getValue();
				if(bgItem!=null){
					if(!bgItem.getResult().get(0).isIsLeaf()){
						FDCMsgBox.showWarning(this,"请选择明细预算项目！");
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(null);
					}else{
						this.kdtBgEntry.getRow(e.getRowIndex()).getCell("bgItem").setValue(bgItem.getResult().get(0));
					}
				}
			}
		}else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("amount")){
			this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").setValue(FDCHelper.multiply(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("rate").getValue(), e.getValue()));
			BigDecimal amount =CRMClientHelper.getColumnValueSum(this.kdtBgEntry, "requestAmount");
			this.txtamount.setValue(amount);
		}else if(this.kdtBgEntry.getColumnKey(e.getColIndex()).equals("currency")){
			CurrencyInfo currency = (CurrencyInfo) this.kdtBgEntry.getRow(e.getRowIndex()).getCell("currency").getValue();
			BigDecimal rate=FDCHelper.ZERO;
			if(currency!=null){
				if(currency.getId().toString().equals(baseCurrency.getId().toString())){
					rate=FDCConstants.ONE;
				}else{
					Date bookedDate = (Date) this.pkbookedDate.getValue();
					ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,currency.getId(), company, bookedDate);
					if(exchangeRate!=null){
						rate=exchangeRate.getConvertRate();
					}
				}
			}
			this.kdtBgEntry.getRow(e.getRowIndex()).getCell("rate").setValue(rate);
			this.kdtBgEntry.getRow(e.getRowIndex()).getCell("requestAmount").setValue(FDCHelper.multiply(this.kdtBgEntry.getRow(e.getRowIndex()).getCell("rate").getValue(), this.kdtBgEntry.getRow(e.getRowIndex()).getCell("amount").getValue()));
	
			BigDecimal amount =CRMClientHelper.getColumnValueSum(this.kdtBgEntry, "requestAmount");
			this.txtamount.setValue(amount);
		}
		getBgAmount();
		CRMClientHelper.getFootRow(this.kdtBgEntry,new String[]{"amount","requestAmount"});
	}
	protected void getBgAmount() throws EASBizException, BOSException, SQLException{
		if(!getOprtState().equals(OprtState.VIEW)&&!isLoad){
			CostCenterOrgUnitInfo costedDept=(CostCenterOrgUnitInfo) this.prmtCostedDept.getValue();
			CompanyOrgUnitInfo costedCompany=(CompanyOrgUnitInfo) this.prmtCostedCompany.getValue();
			for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
				IRow row=this.kdtBgEntry.getRow(i);
				BgItemInfo bgItem = (BgItemInfo) row.getCell("bgItem").getValue();
				BigDecimal balance=FDCHelper.ZERO;
				if(bgItem!=null&&costedDept!=null&&costedCompany!=null){
					IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
					BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill());
					if (coll.size() > 0) {
						for (int k = 0; k < coll.size(); k++) {
							if (bgItem.getNumber().equals(coll.get(k).getItemCombinNumber())) {
								BigDecimal balanceAmount = FDCHelper.ZERO;
								if (coll.get(k).getBalance() != null) {
									balanceAmount = coll.get(k).getBalance();
								}
								balance = balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(), true));
								break;
							}
						}
					}
				}
				row.getCell("bgBalance").setValue(balance);
			}
			CRMClientHelper.getFootRow(this.kdtBgEntry,new String[]{"bgBalance"});
		}
	}
	protected BigDecimal getAccActOnLoadBgAmount(String bgItemNumber,boolean isFromWorkFlow) throws BOSException, SQLException{
		if(this.prmtCostedDept.getValue()==null&&this.pkbookedDate.getValue()==null) return FDCHelper.ZERO;
		
//		String bgComItem="";
//		Set bgComItemSet=new HashSet();
//		bgComItemSet.add(bgItemNumber);
//		FDCSQLBuilder _builder = new FDCSQLBuilder();
//		_builder.appendSql(" select distinct t2.fformula fformula from T_BG_BgTemplateCtrlSetting t1 left join T_BG_BgTemplateCtrlSetting t2 on t1.fgroupno=t2.fgroupno where t1.FOrgUnitId ='"+((CostCenterOrgUnitInfo)this.prmtCostedDept.getValue()).getId().toString()+"'");
//		_builder.appendSql(" and t1.fgroupno!='-1' and t2.fgroupno!='-1' and t1.fformula like '%"+bgItemNumber+"%' and t2.fformula not like '%"+bgItemNumber+"%'");
//		IRowSet rowSet = _builder.getTestSql().executeQuery();
//		
//		while(rowSet.next()){
//			if(rowSet.getString("fformula")!=null){
//				String formula=rowSet.getString("fformula");
//				bgComItemSet.add(formula.substring(9, formula.indexOf(",")-1));
//			}
//		}
//		Object[] bgObject = bgComItemSet.toArray();
//    	for(int i=0;i<bgObject.length;i++){
//        	if(i==0){
//        		bgComItem=bgComItem+"'"+bgObject[i].toString()+"'";
//        	}else{
//        		bgComItem=bgComItem+",'"+bgObject[i].toString()+"'";
//        	}
//        }
		Calendar cal = Calendar.getInstance();
		cal.setTime((Date) this.pkbookedDate.getValue());
		int year=cal.get(Calendar.YEAR);
		
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select sum(entry.frequestAmount-isnull(entry.factPayAmount,0))payAmount from T_CON_PayRequestBill bill left join T_CON_PayRequestBillBgEntry entry on entry.fheadid=bill.fid ");
		_builder.appendSql(" left join T_BG_BgItem bgItem on bgItem.fid=entry.fbgitemid ");
		_builder.appendSql(" where bill.FCostedDeptId='"+((CostCenterOrgUnitInfo)this.prmtCostedDept.getValue()).getId().toString()+"' and bgItem.fnumber in('"+bgItemNumber+"')");
		if(isFromWorkFlow){
			_builder.appendSql(" and bill.fstate in('3AUDITTING','4AUDITTED') ");
		}else{
			_builder.appendSql(" and bill.fstate in('2SUBMITTED','3AUDITTING','4AUDITTED') ");
		}
		_builder.appendSql(" and bill.fhasClosed=0 and bill.famount is not null and year(fbookedDate)="+year);
		
		if(editData.getId()!=null){
			_builder.appendSql(" and bill.fcontractid!='"+editData.getId().toString()+"'");
		}
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			if(rowSet.getBigDecimal("payAmount")!=null)
				return rowSet.getBigDecimal("payAmount");
		}
		return FDCHelper.ZERO;
	}
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		IRow row=this.kdtBgEntry.addRow();
		ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
		row.setUserObject(entry);
	}
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception {
		 IRow row = null;
		 if(this.kdtBgEntry.getSelectManager().size() > 0){
			 int top = this.kdtBgEntry.getSelectManager().get().getTop();
			 if(isTableColumnSelected(this.kdtBgEntry))
				 row = this.kdtBgEntry.addRow();
			 else
				 row = this.kdtBgEntry.addRow(top);
	     }else{
	    	 row = this.kdtBgEntry.addRow();
	     }
		 ContractWithoutTextBgEntryInfo entry=new ContractWithoutTextBgEntryInfo();
		 row.setUserObject(entry);
	}
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		if(this.kdtBgEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtBgEntry)){
            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
            return;
        }
        if(FDCMsgBox.isYes(FDCMsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")))){
            int top = this.kdtBgEntry.getSelectManager().get().getBeginRow();
            int bottom = this.kdtBgEntry.getSelectManager().get().getEndRow();
            for(int i = top; i <= bottom; i++){
                if(this.kdtBgEntry.getRow(top) == null)
                {
                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
                    return;
                }
                this.kdtBgEntry.removeRow(top);
                BigDecimal amount =CRMClientHelper.getColumnValueSum(this.kdtBgEntry, "requestAmount");
    			this.txtamount.setValue(amount);
    			txtamount_dataChanged(null);
    			CRMClientHelper.getFootRow(this.kdtBgEntry,new String[]{"requestAmount"});
            }
        }
	}
	protected void prmtCostedCompany_dataChanged(DataChangeEvent e) throws Exception {
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isBizUnit",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isSealUp",Boolean.FALSE));
		CompanyOrgUnitInfo com=(CompanyOrgUnitInfo)this.prmtCostedCompany.getValue();
		if(com!=null){
			if(!com.isIsLeaf()){
				FDCMsgBox.showWarning(this,"请选择明细预算承担公司！");
				this.prmtCostedCompany.setValue(null);
				return;
			}
			Set id=getCostedDeptIdSet(com);
			if(this.prmtCostedDept.getValue()!=null){
				if(id.contains(((CostCenterOrgUnitInfo)prmtCostedDept.getValue()).getId().toString())){
					return;
				}else{
					this.prmtCostedDept.setValue(null);
				}
			}
			filter.getFilterItems().add(new FilterItemInfo("id",getCostedDeptIdSet(com),CompareType.INCLUDE));
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setValue(null);
			this.prmtCostedDept.setEnabled(false);
		}
		view.setFilter(filter);
        this.prmtCostedDept.setQueryInfo("com.kingdee.eas.basedata.org.app.CostCenterOrgUnitQuery");
        this.prmtCostedDept.setEntityViewInfo(view);
        this.prmtCostedDept.setEditFormat("$number$");
        this.prmtCostedDept.setDisplayFormat("$name$");
        this.prmtCostedDept.setCommitFormat("$number$");
	}
	protected Set getCostedDeptIdSet(CompanyOrgUnitInfo com) throws EASBizException, BOSException{
		if(com==null) return null;
		Set id=new HashSet();
		String longNumber=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(com.getId())).getLongNumber();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber",longNumber+"!%",CompareType.LIKE));
		view.setFilter(filter);
		FullOrgUnitCollection col=FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitCollection(view);
		for(int i=0;i<col.size();i++){
			if(col.get(i).getPartCostCenter()!=null){
				id.add(col.get(i).getId().toString());
			}
		}
		return id;
	}
	protected void prmtCostedDept_dataChanged(DataChangeEvent e) throws Exception {
        getBgAmount();
	}
	protected PaymentBillInfo createTempPaymentBill() throws BOSException{
		PaymentBillInfo pay=new PaymentBillInfo();
		for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
			PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
			BigDecimal requestAmount=(BigDecimal) this.kdtBgEntry.getRow(i).getCell("requestAmount").getValue();
			
			entry.setAmount(requestAmount);
			entry.setLocalAmt(requestAmount);
            entry.setActualAmt(requestAmount);
            entry.setActualLocAmt(requestAmount);
            entry.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
            BgItemInfo bgItem=(BgItemInfo) this.kdtBgEntry.getRow(i).getCell("bgItem").getValue();
            if(bgItem!=null){
            	entry.setOutBgItemId(bgItem.getId().toString());
            	entry.setOutBgItemName(bgItem.getName());
            	entry.setOutBgItemNumber(bgItem.getNumber());
            }
            entry.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
            pay.getEntries().add(entry);
		}
		pay.setCompany((CompanyOrgUnitInfo)this.prmtCostedCompany.getValue());
		pay.setCostCenter((CostCenterOrgUnitInfo) this.prmtCostedDept.getValue());
		pay.setBizDate((Date) this.pkbookedDate.getValue());
		pay.setCurrency((CurrencyInfo) this.prmtcurrency.getValue());
		
		return pay;
	}
	protected void pkbookedDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkbookedDate_dataChanged(e);
		getBgAmount();
		if(!getOprtState().equals(OprtState.VIEW)&&!isLoad){
			for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
				CurrencyInfo currency = (CurrencyInfo) this.kdtBgEntry.getRow(i).getCell("currency").getValue();
				BigDecimal rate=FDCHelper.ZERO;
				if(currency!=null){
					if(currency.getId().toString().equals(baseCurrency.getId().toString())){
						rate=FDCConstants.ONE;
					}else{
						Date bookedDate = (Date) this.pkbookedDate.getValue();
						ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,currency.getId(), company, bookedDate);
						if(exchangeRate!=null){
							rate=exchangeRate.getConvertRate();
						}
					}
				}
				this.kdtBgEntry.getRow(i).getCell("rate").setValue(rate);
				this.kdtBgEntry.getRow(i).getCell("requestAmount").setValue(FDCHelper.multiply(this.kdtBgEntry.getRow(i).getCell("rate").getValue(), this.kdtBgEntry.getRow(i).getCell("amount").getValue()));
		
				BigDecimal amount =CRMClientHelper.getColumnValueSum(this.kdtBgEntry, "requestAmount");
				this.txtamount.setValue(amount);
			}
		}
	}
	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		PayRequestBillInfo pr=(PayRequestBillInfo) editData.get("PayRequestBillInfo");
		pr.getBgEntry().clear();
    	for(int i=0;i<this.kdtBgEntry.getRowCount();i++){
    		IRow row = this.kdtBgEntry.getRow(i);
    		PayRequestBillBgEntryInfo entry=new PayRequestBillBgEntryInfo();
    		entry.setSeq(i);
    		entry.setRequestAmount((BigDecimal)row.getCell("requestAmount").getValue());
    		entry.setBgItem((BgItemInfo)row.getCell("bgItem").getValue());
    		pr.getBgEntry().add(entry);
    	}
    	pr.setCostedCompany((CompanyOrgUnitInfo) prmtCostedCompany.getValue());
    	pr.setCostedDept((CostCenterOrgUnitInfo) prmtCostedDept.getValue());
    	this.editData.put("PayRequestBillInfo",pr);
	}
	protected void attachListeners(){
		super.attachListeners();
		addDataChangeListener(this.prmtCostedCompany);
		addDataChangeListener(this.prmtCostedDept);
	}
	protected void detachListeners(){
		super.detachListeners();
		removeDataChangeListener(this.prmtCostedCompany);
		removeDataChangeListener(this.prmtCostedDept);
	}
	boolean isLoad=false;
	public void loadFields() {
		isLoad=true;
		super.loadFields();
		this.loadBgEntryTable();
		CRMClientHelper.getFootRow(this.kdtBgEntry,new String[]{"requestAmount","amount","bgBalance"});
		isLoad=false;
	}
	public void storeFields() {
		this.storeBgEntryTable();
		super.storeFields();
	}
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionCopy_actionPerformed(e);
		setBgEditState();
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		setBgEditState();
	}
	public void setBgEditState(){
		if (getOprtState().equals(OprtState.VIEW)){
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		}else{
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
			
			this.txtamount.setEnabled(false);
		}
		
		if(this.prmtCostedCompany.getValue()!=null){
			this.prmtCostedDept.setEnabled(true);
		}else{
			this.prmtCostedDept.setEnabled(false);
		}
	}
	protected void verifyInputForSubmint()throws Exception {
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedCompany);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCostedDept);
		if(this.kdtBgEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"费用清单不能为空！");
			SysUtil.abort();
		}
		IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
		BgCtrlResultCollection coll = iBgControlFacade.getBudget("com.kingdee.eas.fi.cas.app.PaymentBill", new BgCtrlParamCollection(), createTempPaymentBill());
		Map bgItemMap=new HashMap();
		boolean isWarning=true;
		for (int i = 0; i < this.kdtBgEntry.getRowCount(); i++) {
			IRow row = this.kdtBgEntry.getRow(i);
			if (row.getCell("bgItem").getValue() == null) {
				FDCMsgBox.showWarning(this,"费用清单预算项目不能为空！");
				this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("bgItem"));
				SysUtil.abort();
			}
			if (row.getCell("requestAmount").getValue() == null) {
				FDCMsgBox.showWarning(this,"费用清单申请金额不能为空！");
				this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
				SysUtil.abort();
			}
			if (((BigDecimal)row.getCell("requestAmount").getValue()).compareTo(FDCHelper.ZERO)<=0) {
				FDCMsgBox.showWarning(this,"费用清单申请金额必须大于0！");
				this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("requestAmount"));
				SysUtil.abort();
			}
			if (row.getCell("remark").getValue() == null||"".equals(row.getCell("remark").getValue().toString().trim())) {
				FDCMsgBox.showWarning(this, "费用清单备注不能为空！");
				this.kdtBgEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtBgEntry.getColumnIndex("remark"));
				SysUtil.abort();
			}
			BgItemInfo bgItem=(BgItemInfo) row.getCell("bgItem").getValue();
			for (int j = 0; j < coll.size(); j++) {
				if (bgItem.getNumber().equals(coll.get(j).getItemCombinNumber())) {
					if(BgCtrlTypeEnum.NoCtrl.equals(coll.get(j).getCtrlType())){
						break;
					}
					if (getUIContext().get("isFromWorkflow") != null && getUIContext().get("isFromWorkflow").toString().equals("true") &&getUIContext().get("approveIsPass")!=null&&getUIContext().get("approveIsPass").toString().equals("false")&& getOprtState().equals(OprtState.EDIT)) {
						break;
					}
					BigDecimal balanceAmount=FDCHelper.ZERO;
					BigDecimal requestAmount=(BigDecimal)row.getCell("requestAmount").getValue();
					if(coll.get(j).getBalance() != null){
						balanceAmount=coll.get(j).getBalance();
					}
					if(bgItemMap.containsKey(bgItem.getNumber())){
						BigDecimal sumAmount=(BigDecimal) bgItemMap.get(bgItem.getNumber());
						balanceAmount=balanceAmount.subtract(sumAmount);
						bgItemMap.put(bgItem.getNumber(), sumAmount.add(requestAmount));
					}else{
						bgItemMap.put(bgItem.getNumber(), requestAmount);
					}
					BigDecimal balance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber().toString(),true));
					if(requestAmount.compareTo(balance)>0){
						FDCMsgBox.showWarning(this, bgItem.getName()+"超过预算余额！");
						SysUtil.abort();
					}
					if (isWarning&&(getUIContext().get("isFromWorkflow") == null || getUIContext().get("isFromWorkflow").toString().equals("false"))) {
						BigDecimal endBalance=balanceAmount.subtract(getAccActOnLoadBgAmount(bgItem.getNumber(),false));
						if(requestAmount.compareTo(endBalance)>0){
							if(FDCMsgBox.showConfirm2(this, "你发起的单据已申请（已确认+在途）的累计金额已超过预算；\n本次申请有可能不被通过，请确认是否提交？")!= FDCMsgBox.YES){
								SysUtil.abort();
							}else{
								isWarning=false;
							}
						}
					}
				}
			}
		}
	}
}
