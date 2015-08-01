/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.account.client.AccountPromptBox;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewFactory;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysCollection;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysFactory;
import com.kingdee.eas.fdc.basedata.DeductAccountEntrysInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class BeforeAccountViewEditUI extends AbstractBeforeAccountViewEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BeforeAccountViewEditUI.class);
    boolean isAddNew = false;	//�Ƿ��Ǳ༭����������
    boolean isSaved = false;	//�Ƿ��Ѿ�����
//    boolean isDeleted = false;	//�Ƿ��Ѿ�ɾ��
    String[] uiData = null;	//���ڱ�������ϵĳ�ʼֵ
//    int addTimes = 0;	//��¼���ӷ�¼�Ĵ���
    /**
     * output class constructor
     */
    public BeforeAccountViewEditUI() throws Exception
    {
        super();
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtBeforeOtherAccount,prmtProAccount,prmtSettAccount,prmtIntendAccount,prmtOtherProAccount,prmtOtherSettAccount,prmtBeforeSettAccount,prmtBeforeDevAccount,prmtProductAccount,prmtAdminFees,prmtMarketFees,prmtCompensationAccount,prmtGuerdonAccount,prmtTempAccount}));
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	dataValueChanged();
    	DeductAccountEntrysInfo deAccEntrysInfo;
    	
    	for(int i=0;i<kdtEntrys.getRowCount();i++){
    		deAccEntrysInfo = this.editData.getDeductAccountEntrys().get(i);
    		if(deAccEntrysInfo!=null){
    			this.editData.getDeductAccountEntrys().set(i,deAccEntrysInfo);
    		}
    	}
    	super.storeFields();
//    	initOldData(editData);
//  	super.initOldData(editData);
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	String deductType=null;
    	
    	/**
    	 * �ж��Ƿ��¼�Ѿ�������ۿ����Ͷ�Ӧ�Ŀ�Ŀ
    	 */
    	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    		IRow row = this.kdtEntrys.getRow(i);
    		
    		if(row.getCell("account").getValue()==null){
    			if(deductType==null){
    				deductType = row.getCell("deductType").getValue().toString();
    			}else{
    				deductType +="��";
    				deductType += row.getCell("deductType").getValue().toString();
    			}
    		}
    		if(i==kdtEntrys.getRowCount()-1 && deductType!=null){
    			deductType += "�ۿ�����δ���ö�Ӧ�Ļ�ƿ�Ŀ�����ܱ��棡";
    			MsgBox.showInfo(this,deductType);
    			SysUtil.abort();
    		}
    	}
		
    	super.actionSubmit_actionPerformed(e);
    	
    	//����������ӵ�һ����Ŀ���ã������ʱ���Ҫ�������
    	if(this.getUIContext().get("isFirstAddNew")==null){
    		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    			IRow row = this.kdtEntrys.getRow(i);
    			row.getCell("account").setValue(null);
    		}
    		
    	}
    	uiData = null;
    	//�����Ҫ���±�������ϵ�����
    	storeUIData();
    	initOldData(editData);
    	isSaved = true;
//    	isAddNew = false;
    }

    /**
     * �жϽ����Ϸ�¼��ֵ�Ƿ��иı�
     * ����иı䣬��ִ��initOldData()����
     *
     */
    protected void dataValueChanged(){
    	/*if (OprtState.VIEW.equals(getOprtState()) || isAddNew
    			||OprtState.ADDNEW.equals(getOprtState())) {
//    		initOldData(editData);
    		return;
    	}*/
    	if(OprtState.VIEW.equals(getOprtState())){
    		return;
    	}
    	
     	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
    	sqlBuilder.appendSql("select faccountid from T_FDC_DeductAccountEntrys where fparentid=?");
    	IRowSet rowSet;
    	DeductAccountEntrysInfo deAccEntrysInfo;
    	/**
    	 * �����ж��Ƿ��������ӵ�һ����Ŀ����
    	 * ���˵�ǣ���ִ��else
    	 */
    	if(this.editData.getId()!=null && this.getUIContext().get("isFirstAddNew")!=null && !isAddNew){
    		sqlBuilder.addParam(this.editData.getId().toString());
    		try {
    			rowSet = sqlBuilder.executeQuery();
    			/**
    			 * ����������Ѿ����������ݿ��У���ȡ����
    			 * ����Ͱѽ����ϵ�����ȡ����
    			 */
    			if(rowSet.size()!=0){
    				for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    					IRow row = this.kdtEntrys.getRow(i);
    					/**
    					 * �����ϵ��������������Ѿ��������ݵ�����
    					 * ȡ�������ϵ����ݼӵ�deAccEntrysInfo��
    					 */
    					if(rowSet.next()){
//  						String id = rowSet.getString("FAccountID");
    						
//  						if((id==null && accInfo==null) ||
//  						(id!=null && accInfo!=null && id.toString().equals(accInfo.getId().toString()))){
//  						count++;
//  						}else{
    						deAccEntrysInfo = this.editData.getDeductAccountEntrys().get(i);
    						deAccEntrysInfo.setAccount((AccountViewInfo)row.getCell("account").getValue());
    						deAccEntrysInfo.setDeductType((DeductTypeInfo)row.getCell("deductType").getValue());
    						row.setUserObject(deAccEntrysInfo);
//  						}
    					}else{
//  						if(row.getCell("account").getValue()==null){
//  						count++;
//  						}else{
    						DeductAccountEntrysInfo deAccEntrysInfo2 = new DeductAccountEntrysInfo();
    						deAccEntrysInfo2.setAccount((AccountViewInfo)row.getCell("account").getValue());
    						deAccEntrysInfo2.setDeductType((DeductTypeInfo)row.getCell("deductType").getValue());
    						if(this.editData.getDeductAccountEntrys().size()<this.kdtEntrys.getRowCount()){
    							this.editData.getDeductAccountEntrys().add(deAccEntrysInfo2);
    						}
    						deAccEntrysInfo = this.editData.getDeductAccountEntrys().get(i);
    						deAccEntrysInfo.setAccount((AccountViewInfo)row.getCell("account").getValue());
    						deAccEntrysInfo.setDeductType((DeductTypeInfo)row.getCell("deductType").getValue());
    						row.setUserObject(deAccEntrysInfo);
//  						}
    					}
//  					i++;
    				}
//  				}
//  				if(count==this.kdtEntrys.getRowCount()){
//  				initOldData(editData);
//  				}
    			}else{
//  				int count = 0;
    				for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    					IRow row = this.kdtEntrys.getRow(i);
    					deAccEntrysInfo = new DeductAccountEntrysInfo ();
//  					if(row.getCell("account").getValue()==null){
//  					count++;
//  					}
    					deAccEntrysInfo.setAccount((AccountViewInfo)row.getCell("account").getValue());
    					deAccEntrysInfo.setDeductType((DeductTypeInfo)row.getCell("deductType").getValue());
    					row.setUserObject(deAccEntrysInfo);
    					if(this.editData.getDeductAccountEntrys().size()<this.kdtEntrys.getRowCount()){
    						this.editData.getDeductAccountEntrys().add(deAccEntrysInfo);
    					}
//  					if(count==this.kdtEntrys.getRowCount()){
//  					initOldData(editData);
//  					}
    				}
    			}
    			
    		} catch (Exception e) {
				handUIExceptionAndAbort(e);
    		}
    	}else{
//  		int count = 0;
    		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    			IRow row = this.kdtEntrys.getRow(i);
    			deAccEntrysInfo = new DeductAccountEntrysInfo ();
    			//			deAccEntrysInfo.getAccount();
//  			if(row.getCell("account").getValue()==null){
//  			count++;
//  			}
    			deAccEntrysInfo.setAccount((AccountViewInfo)row.getCell("account").getValue());
    			deAccEntrysInfo.setDeductType((DeductTypeInfo)row.getCell("deductType").getValue());
    			row.setUserObject(deAccEntrysInfo);
    			if(this.editData.getDeductAccountEntrys().size()<this.kdtEntrys.getRowCount()){
    				this.editData.getDeductAccountEntrys().add(deAccEntrysInfo);
				}
    		}

    	}
    	
    	//�жϽ����Ϸ�¼��ֵ�Ƿ��иı��
    	String[] uiData = storeUIData();
    	int count = 0;
    	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    		IRow row = this.kdtEntrys.getRow(i);
    		AccountViewInfo accInfo = (AccountViewInfo)row.getCell("account").getValue();
    		if(accInfo!=null){
    			if(uiData[i].equals(accInfo.getId().toString())){
    				count++;
    			}
    		}else{
    			if(uiData[i].equals("null")){
    				count++;
    			}
    		}
    	}
    	if(count==uiData.length){
    		initOldData(this.editData);
    	}
    	
    }
    
    /**
     * �ӽ�����ȡ����¼�Ķ�Ӧ��Ŀ����������������
     * @return
     */
    public String[] storeUIData(){
    	String id = "null";
    	int count = this.kdtEntrys.getRowCount();
    	if(uiData==null){
    		uiData = new String[count];
    		AccountViewInfo accInfo = null;
    		for(int i=0;i<count;i++){
    			IRow row = this.kdtEntrys.getRow(i);
    			accInfo = (AccountViewInfo) row.getCell("account").getValue();
    			if(accInfo!=null){
    				id = accInfo.getId().toString();
    			}else{
    				id = "null";
    			}
    			uiData[i] = id;
    		}
    	}
    	return uiData;
    }
    
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	//����ʱ�����ÿۿ��ĿΪ��
    	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    		IRow row = this.kdtEntrys.getRow(i);
    		row.getCell("account").setValue(null);
    	}
    	isAddNew = true;
    	super.actionAddNew_actionPerformed(e);
    	this.btnRemove.setEnabled(false);
    	this.btnEdit.setEnabled(false);
    }
    
	protected KDTextField getNumberCtrl() {
		return null;
	}
   
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return BeforeAccountViewFactory.getRemoteInstance();
    }

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable()
    {        
        return null;
	}    
	
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String company = editData.getCompany().getId().toString();
//    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
//		sqlBuilder.appendSql("select fcompanyid from T_FDC_BeforeAccountView where fid=?");
//		sqlBuilder.addParam(this.getUIContext().get("selectedValue"));
//		IRowSet rowSet = sqlBuilder.executeQuery();
//		if(rowSet.next()){
//			 company = rowSet.getString("FCOMPANYID");
//		}
    	
    	
//		String ctlUnitID = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
//		if(!ctlUnitID.equals(OrgConstants.DEF_CU_ID) && company.equals(OrgConstants.DEF_CU_ID)){
//			MsgBox.showWarning(this, "�������޸�������֯������!");
//			SysUtil.abort();
//		}
    	
    	//modified by pu_zhang  2005-5-26
    	String orgUnit = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    	if(!company.equals(orgUnit)){
    		MsgBox.showWarning(this, "�������޸�������֯������!");
			SysUtil.abort();
    	}
    	
    	//����༭״̬���Ϳ��Ա༭��¼
    	this.kdtEntrys.setEditable(true);
    	this.kdtEntrys.setEnabled(true);
    	super.actionEdit_actionPerformed(e);
    	
    	this.btnEdit.setEnabled(true);
    	this.btnSave.setEnabled(true);
    	if(this.oprtState.equals(STATUS_ADDNEW) || isAddNew){
    		this.btnRemove.setEnabled(false);
    	}
    	
    	//TODO ֻ�� CU�����޸ģ���ʱ�������п��ܻ���������֯�����޸ĵģ���������ȷ�ϣ���  by Owen_wen
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
	    	prmtBeforeOtherAccount.setEditFormat("$longName$");
			prmtBeforeDevAccount.setEnabled(false);
			prmtBeforeOtherAccount.setEnabled(false);
			prmtBeforeSettAccount.setEnabled(false);
			prmtIntendAccount.setEnabled(false);
			prmtOtherIntendAccount.setEnabled(false);
			prmtOtherProAccount.setEnabled(false);
			prmtOtherSettAccount.setEnabled(false);
			prmtProAccount.setEnabled(false);
			prmtSettAccount.setEnabled(false);
			prmtProductAccount.setEnabled(false);
			prmtAdminFees.setEnabled(false);
			prmtMarketFees.setEnabled(false);
			prmtCompensationAccount.setEnabled(false);
			prmtGuerdonAccount.setEnabled(false);
			prmtTempAccount.setEnabled(false);
		}
    	//���治�������ʾ,������������ж϶��� by hpw 2010.07.21
    	//modified by pu_zhang  2005-5-26
//    	if(!company.equals(orgUnit)){
//    		prmtBeforeOtherAccount.setEditFormat("$longName$");
//    		prmtBeforeDevAccount.setEnabled(false);
//    		prmtBeforeOtherAccount.setEnabled(false);
//    		prmtBeforeSettAccount.setEnabled(false);
//    		prmtIntendAccount.setEnabled(false);
//    		prmtOtherIntendAccount.setEnabled(false);
//    		prmtOtherProAccount.setEnabled(false);
//    		prmtOtherSettAccount.setEnabled(false);
//    		prmtProAccount.setEnabled(false);
//    		prmtSettAccount.setEnabled(false);
//    		prmtProductAccount.setEnabled(false);
//    		prmtAdminFees.setEnabled(false);
//    		prmtMarketFees.setEnabled(false);
//    		prmtCompensationAccount.setEnabled(false);
//    		prmtGuerdonAccount.setEnabled(false);
//    		prmtTempAccount.setEnabled(false);
//    	}
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
    	BeforeAccountViewInfo objectValue = new BeforeAccountViewInfo();
    	objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
    	objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
//    	objectValue.setCompany(ContextHelperFactory
//    			.getRemoteInstance().getCurrentCompany());
    	objectValue.setCompany(SysContext.getSysContext().getCurrentFIUnit());
    	return objectValue;
    }
    
    public void onLoad() throws Exception {
		super.onLoad();
		this.kdtEntrys.checkParsed();
		String company = editData.getCompany().getId().toString();
		String orgUnit = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
		
		//modify by pu_zhang    2010-5-25
//		if(OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())){
		
		if (company.equals(orgUnit)
				&& (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit() || (SysContext.getSysContext().getCurrentAdminUnit() != null && SysContext.getSysContext().getCurrentAdminUnit()
						.getLevel() == 1))) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
		}else{
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		if(STATUS_EDIT.equals(this.getOprtState())){
			this.btnEdit.setEnabled(false);
		}
		
		initF7Selector();	
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				prmtBeforeOtherAccount.requestFocusInWindow();
			}
		});
		prmtOtherIntendAccount.setRequired(false);
//		this.getFocusTraversalPolicy()
		
		//������ڴ��ڱ༭״̬�������Ѿ�������ģ�壬����Ա༭
//		if(this.oprtState.equals(STATUS_EDIT) && 
//				this.getUIContext().get("isImpedTemplate")!=null){
//			this.btnEdit.setEnabled(true);
//			prmtBeforeOtherAccount.setEditFormat("$longName$");
//			prmtBeforeDevAccount.setEnabled(false);
//			prmtBeforeOtherAccount.setEnabled(false);
//			prmtBeforeSettAccount.setEnabled(false);
//			prmtIntendAccount.setEnabled(false);
//			prmtOtherIntendAccount.setEnabled(false);
//			prmtOtherProAccount.setEnabled(false);
//			prmtOtherSettAccount.setEnabled(false);
//			prmtProAccount.setEnabled(false);
//			prmtSettAccount.setEnabled(false);
//			prmtProductAccount.setEnabled(false);
//			prmtAdminFees.setEnabled(false);
//			prmtMarketFees.setEnabled(false);
//			prmtCompensationAccount.setEnabled(false);
//			prmtGuerdonAccount.setEnabled(false);
//			prmtTempAccount.setEnabled(false);			
//		}
		
		//�鿴��ʱ��������¼
    	if(this.oprtState.equals(STATUS_VIEW)){
    		this.kdtEntrys.setEditable(false);
    		this.kdtEntrys.setEnabled(false);
    		if(this.getUIContext().get("isImpedTemplate")!=null){
    			this.btnEdit.setEnabled(true);
    		}
    	}
    	if(this.oprtState.equals(STATUS_ADDNEW)){
    		this.btnRemove.setEnabled(false);
    		this.btnEdit.setEnabled(false);
    	}
    	
    	FilterInfo filter = new FilterInfo();			
		filter.getFilterItems().add( new FilterItemInfo("isCFreeze", new Integer(0)));
		AccountPromptBox xx = new AccountPromptBox(this,(CompanyOrgUnitInfo)prmtCompany.getValue(),filter,false,true);
    	setDeductAccount(xx);
    	showAccount();
	}

    /**
     * �ۿ����Ͷ�Ӧ��ƿ�Ŀ����
     * @throws Exception 
     *
     */
    protected void setDeductAccount(AccountPromptBox accountPromptBox) throws Exception{
    	//ȡ�ۿ����ͷ�¼���ݿ��е�ֵ
    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
    	String sql = "select * from T_fdc_deductType a where " +

		//ֻ��ʾ����״̬�ģ�

				//    			"a.fid in (select distinct b.fdeducttypeid from " +
				//    			"T_FDC_DeductAccountEntrys b where a.fid=b.fdeducttypeid and b.fparentid=?) " +
				"a.fisenabled=1 order by a.fnumber";
		//				"or a.fisenabled=1 order by a.fnumber";
    	sqlBuilder.appendSql(sql);

		//    	if (this.getUIContext().get("selectedValue") == null)
		//    		sqlBuilder.addParam("null");
		//    	else
		//    		sqlBuilder.addParam(this.getUIContext().get("selectedValue"));
    	
		IRowSet rowSet = sqlBuilder.executeQuery();
		
		DeductTypeCollection deTypeColl = new DeductTypeCollection();
		DeductTypeInfo deTypeInfo;
		while(rowSet.next()){
			deTypeInfo = new DeductTypeInfo();
			deTypeInfo.setId(BOSUuid.read(rowSet.getString("fid")));
			deTypeInfo.setName(rowSet.getString("fname_l2"));
			deTypeColl.add(deTypeInfo);
			IRow curRow = this.kdtEntrys.addRow();
			curRow.getCell("deductType").setValue(deTypeInfo);
		}		
	//ȡ�ۿ����͵���ֵ
//		EntityViewInfo viewInfo;
//		FilterInfo filterInfo;
//    	viewInfo = new EntityViewInfo();
//    	filterInfo = new FilterInfo();
//    	viewInfo.getSelector().add("id");
//    	viewInfo.getSelector().add("name");
//    	viewInfo.setFilter(filterInfo);
//    	filterInfo.appendFilterItem("isEnabled","1");
//    	viewInfo.getSorter().add(new SorterItemInfo("number"));
//    	DeductTypeCollection deTypeColl = DeductTypeFactory.getRemoteInstance().getDeductTypeCollection(viewInfo);
//
//    	//�����¼�е��������ڿۿ����͵��п���
//    	if(deAccColl.size()>=deTypeColl.size()){
//    		 
//    	}else{
//    		//�ӿۿ�������ȡ������ʾ
//    		for(int i=0; i<deTypeColl.size(); i++){
//    			DeductTypeInfo deTypeInfo = deTypeColl.get(i);
//    			IRow curRow = this.kdtEntrys.addRow();
//    			curRow.getCell("deductType").setValue(deTypeInfo);
//    		}
//    	}
	
		
		
    	this.kdtEntrys.getColumn("deductType").getStyleAttributes().setLocked(true);
    	this.kdtEntrys.getColumn("account").setRequired(true);
    	
    	
//    	String companyID = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
    	IColumn colSegF7 = this.kdtEntrys.getColumn("account");
    	
    	//�ö�Ӧ��ƿ�Ŀ�ֶεı���ϵı���F7�ؼ���ʾ����
    	ObjectValueRender segNum = new ObjectValueRender();
    	segNum.setFormat(new BizDataFormat("$longName$"));
    	colSegF7.setRenderer(segNum);
    	
    	KDBizPromptBox SegNumF7 = new KDBizPromptBox();
    	SegNumF7.setRequired(true);
    	SegNumF7.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");
    	SegNumF7.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
    	KDTDefaultCellEditor f7EditorInput = new KDTDefaultCellEditor(SegNumF7);
    	colSegF7.setEditor(f7EditorInput);
    	SegNumF7.setSelector(accountPromptBox);
//    	EntityViewInfo viewInfo = new EntityViewInfo();
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("companyID.id",companyID,CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("isLeaf","1",CompareType.EQUALS));
//    	filter.getFilterItems().add(new FilterItemInfo("parent.id",null,CompareType.NOTEQUALS));
//    	viewInfo.setFilter(filter);
//    	SegNumF7.setEntityViewInfo(viewInfo);
    	//ʹ��ͳһ�ķ����������Ŀ��ȹ�������  by hpw 2010.07.21
    	SegNumF7.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		SegNumF7.setEntityViewInfo(view);
		
    	SegNumF7.setDisplayFormat("$number$");
    	SegNumF7.setEditFormat("$number$");
    	SegNumF7.setCommitFormat("$number$");
    }

    /**
     * ��ʾ�ۿ����Ͷ�Ӧ�Ŀ�Ŀ
     *
     */
    protected void showAccount(){
    	if(this.oprtState.equals(STATUS_ADDNEW)){
    		return;
    	}else{
    		try {
    			EntityViewInfo viewInfo = new EntityViewInfo();
    			FilterInfo filterInfo = new FilterInfo();
    			viewInfo.getSelector().add("id");
    			viewInfo.getSelector().add("account.*");
    			viewInfo.getSelector().add("deductType.id");
    			filterInfo.getFilterItems().add(new FilterItemInfo("parent.id",this.editData.getId().toString()));
    			viewInfo.setFilter(filterInfo);
    			DeductAccountEntrysCollection deAccColl = DeductAccountEntrysFactory.getRemoteInstance().getDeductAccountEntrysCollection(viewInfo);
    			for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    				IRow row = this.kdtEntrys.getRow(i);
    				 DeductTypeInfo deInfo = (DeductTypeInfo)row.getCell("deductType").getValue();
    				 for(int j=0;j<deAccColl.size();j++){
    					 DeductAccountEntrysInfo accInfo = deAccColl.get(j);
    					 if(accInfo!=null && deInfo.getId()!=null){
    						 if(accInfo.getDeductType().getId().equals(deInfo.getId())){
    							 row.getCell("account").setValue(accInfo.getAccount());
    							 break;
    						 }
    					 }
    				 }
    			}
    			initOldData(this.editData);
    		} catch (BOSException e) {
    			logger.error(e.getMessage(), e);
    			handUIExceptionAndAbort(e);
    		}
    		
    		storeUIData();
    	}
    }
    protected void setFieldsNull(AbstractObjectValue newData) {
    	super.setFieldsNull(newData);
    	BeforeAccountViewInfo info = (BeforeAccountViewInfo)newData;
    	info.setCompany(null);
    	
    }
	
	public void loadFields() {
		super.loadFields();
		if(editData.getCU()==null){
			editData.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		}
		prmtSettAccount.setValue(editData.getSettAccount());
    	prmtProAccount.setValue(editData.getProAccount());
    	prmtIntendAccount.setValue(editData.getIntendAccount());
    	prmtTempAccount.setValue(editData.getTempAccount());

		if (!SysContext.getSysContext().getCurrentFIUnit().isIsBizUnit()||
				((!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit())&&(SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()))) {
			//�����¿��޸�
			if (SysContext.getSysContext().getCurrentAdminUnit().getLevel() != 1) {
				actionAddNew.setEnabled(false);
				actionEdit.setEnabled(false);
				actionRemove.setEnabled(false);
				actionSave.setEnabled(false);
				actionCopy.setEnabled(false);
			}
		}
		initOldData(editData);
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
		}
		if(STATUS_EDIT.equals(this.getOprtState())){
			this.btnEdit.setEnabled(false);
		}
	}

	protected void prmtBeforeDevAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtBeforeOtherAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtBeforeSettAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtCompany_dataChanged(DataChangeEvent e) throws Exception {
		if (OprtState.VIEW.equals(getOprtState())) {
    		return;
    	}
		if(this.editData.getCompany()!=null){
			/* 
			 *  added by Owen: editData.getCompany().equals(this.prmtCompany.getValue()Ϊ�ξͲ�����F7������F7��ѯ�����Ķ��ǿյġ�
			 *  ��ע����������
			 * if(this.editData.getCompany().equals(this.prmtCompany.getValue())){
				return;
			}*/
			if ((e.getOldValue() != null) && (e.getOldValue().equals(e.getNewValue()))) {
				return;
			}
		}
		else if((e.getOldValue()!=null)&&(e.getOldValue().equals(e.getNewValue()))){
			return;
		}
		prmtBeforeDevAccount.setValue(null);
		prmtBeforeOtherAccount.setValue(null);
		prmtBeforeSettAccount.setValue(null);
		prmtIntendAccount.setValue(null);
		prmtOtherIntendAccount.setValue(null);
		prmtOtherProAccount.setValue(null);
		prmtOtherSettAccount.setValue(null);
		prmtProAccount.setValue(null);
		prmtSettAccount.setValue(null);
		prmtProductAccount.setValue(null);
		prmtTempAccount.setValue(null);
		FilterInfo filter = new FilterInfo();			
		filter.getFilterItems().add( new FilterItemInfo("isCFreeze", new Integer(0)));
		AccountPromptBox xx = new AccountPromptBox(this,(CompanyOrgUnitInfo)e.getNewValue(),filter,false,true);
		prmtBeforeDevAccount.setSelector(xx);
		prmtBeforeOtherAccount.setSelector(xx);
		prmtBeforeSettAccount.setSelector(xx);
		prmtIntendAccount.setSelector(xx);
		prmtOtherIntendAccount.setSelector(xx);
		prmtOtherProAccount.setSelector(xx);
		prmtOtherSettAccount.setSelector(xx);
		prmtProAccount.setSelector(xx);
		prmtSettAccount.setSelector(xx);
		prmtProductAccount.setSelector(xx);
		prmtTempAccount.setSelector(xx);
	}

	protected void prmtIntendAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtOtherIntendAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtOtherProAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtOtherSettAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtProAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}
	
	protected void prmtTempAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void prmtSettAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}	
	
	protected void prmtProductAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtProductAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtProductAccount.setEntityViewInfo(view);
	}

	protected void prmtProductAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		FDCClientVerifyHelper.verifyRequire(this);
	}

	protected void prmtBeforeDevAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtBeforeDevAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtBeforeDevAccount.setEntityViewInfo(view);
	}

	protected void prmtBeforeOtherAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtBeforeOtherAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtBeforeOtherAccount.setEntityViewInfo(view);
	}

	protected void prmtBeforeSettAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtBeforeSettAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtBeforeSettAccount.setEntityViewInfo(view);
	}

	protected void prmtIntendAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtIntendAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtIntendAccount.setEntityViewInfo(view);
	}

	protected void prmtOtherIntendAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtOtherIntendAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtOtherIntendAccount.setEntityViewInfo(view);
	}

	protected void prmtOtherProAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtOtherProAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtOtherProAccount.setEntityViewInfo(view);
	}

	protected void prmtOtherSettAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtOtherSettAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtOtherSettAccount.setEntityViewInfo(view);
	}
	
	protected void prmtTempAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtTempAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtTempAccount.setEntityViewInfo(view);
	}
	
	protected void prmtProAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtProAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtProAccount.setEntityViewInfo(view);
	}

	protected void prmtSettAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtSettAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtSettAccount.setEntityViewInfo(view);
	}	
	
	protected void updateButtonStatus() {
		
	}
	protected EntityViewInfo setEntityForAcc() throws Exception {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo comInfo = (CompanyOrgUnitInfo)prmtCompany.getValue();
		String strCUId = comInfo.getId().toString();//.getCU().getId().toString();
		filter.getFilterItems().add(
				new FilterItemInfo("companyID.id", strCUId));
		//δ���ÿ�Ŀ�������ʾ  by hpw 2010.11.16
		if (comInfo.getAccountTable() != null){
			filter.getFilterItems().add(
					new FilterItemInfo("accountTableID.id", comInfo.getAccountTable().getId().toString()));
		}else{
			FDCMsgBox.showWarning("����֯δ���û�δ���û�ƿ�Ŀ���������û����ã�");
			this.abort();
		}
		filter.getFilterItems().add( new FilterItemInfo("isCFreeze", new Integer(0)));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", FDCConstants.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isGFreeze", FDCConstants.FALSE));
		view.setFilter(filter);
		return view;
	}
	
	protected void prmtAdminFees_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtAdminFees.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtAdminFees.setEntityViewInfo(view);
	}
	
	protected void prmtAdminFees_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}
	
	protected void prmtMarketFees_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtMarketFees.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtMarketFees.setEntityViewInfo(view);
	}
	
	protected void prmtMarketFees_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}
	
	protected void prmtCompensationAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtCompensationAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtCompensationAccount.setEntityViewInfo(view);
	}
	
	protected void prmtCompensationAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}
	
	protected void prmtGuerdonAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
		prmtGuerdonAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtGuerdonAccount.setEntityViewInfo(view);
	}
	
	protected void prmtGuerdonAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"�������ù�˾����Ҫ���չ�˾ɸѡ��Ŀ��");
			return;
		}
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
			IRow row = this.kdtEntrys.getRow(i);
			row.getCell("account").setValue(null);
		}
		uiData = null;
		//ɾ����ҲҪ���»�ȡһ�ν�������
		storeFields();
	}
	
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		
		super.actionExitCurrent_actionPerformed(e);
	}
	public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("proAccount.id"));
        sic.add(new SelectorItemInfo("proAccount.number"));
        sic.add(new SelectorItemInfo("proAccount.name"));
        sic.add(new SelectorItemInfo("proAccount.isCFreeze"));
        sic.add(new SelectorItemInfo("proAccount.isLeaf"));
        sic.add(new SelectorItemInfo("proAccount.longName"));
        
        sic.add(new SelectorItemInfo("tempAccount.id"));
        sic.add(new SelectorItemInfo("tempAccount.number"));
        sic.add(new SelectorItemInfo("tempAccount.name"));
        sic.add(new SelectorItemInfo("tempAccount.isCFreeze"));
        sic.add(new SelectorItemInfo("tempAccount.isLeaf"));
        sic.add(new SelectorItemInfo("tempAccount.longName"));
        
        sic.add(new SelectorItemInfo("intendAccount.id"));
        sic.add(new SelectorItemInfo("intendAccount.number"));
        sic.add(new SelectorItemInfo("intendAccount.name"));
        sic.add(new SelectorItemInfo("intendAccount.longName"));
        
        sic.add(new SelectorItemInfo("otherProAccount.id"));
        sic.add(new SelectorItemInfo("otherProAccount.number"));
        sic.add(new SelectorItemInfo("otherProAccount.name"));
        sic.add(new SelectorItemInfo("otherProAccount.longName"));
                
        sic.add(new SelectorItemInfo("otherSettAccount.id"));
        sic.add(new SelectorItemInfo("otherSettAccount.number"));
        sic.add(new SelectorItemInfo("otherSettAccount.name"));
        sic.add(new SelectorItemInfo("otherSettAccount.longName"));
        
        
        sic.add(new SelectorItemInfo("otherIntendAccount.id"));
        sic.add(new SelectorItemInfo("otherIntendAccount.number"));
        sic.add(new SelectorItemInfo("otherIntendAccount.name"));
        sic.add(new SelectorItemInfo("otherIntendAccount.longName"));
        
        sic.add(new SelectorItemInfo("beforeOtherAccount.id"));
        sic.add(new SelectorItemInfo("beforeOtherAccount.number"));
        sic.add(new SelectorItemInfo("beforeOtherAccount.name"));
        sic.add(new SelectorItemInfo("beforeOtherAccount.longName"));
        
        sic.add(new SelectorItemInfo("beforeDevAccount.id"));
        sic.add(new SelectorItemInfo("beforeDevAccount.number"));
        sic.add(new SelectorItemInfo("beforeDevAccount.name"));
        sic.add(new SelectorItemInfo("beforeDevAccount.longName"));
        
        sic.add(new SelectorItemInfo("beforeSettAccount.id"));
        sic.add(new SelectorItemInfo("beforeSettAccount.number"));
        sic.add(new SelectorItemInfo("beforeSettAccount.name"));
        sic.add(new SelectorItemInfo("beforeSettAccount.longName"));
        
        sic.add(new SelectorItemInfo("settAccount.id"));
        sic.add(new SelectorItemInfo("settAccount.number"));
        sic.add(new SelectorItemInfo("settAccount.name"));
        sic.add(new SelectorItemInfo("settAccount.longName"));
        
        sic.add(new SelectorItemInfo("productAccount.id"));
        sic.add(new SelectorItemInfo("productAccount.number"));
        sic.add(new SelectorItemInfo("productAccount.name"));
        sic.add(new SelectorItemInfo("productAccount.longName"));
        
        sic.add(new SelectorItemInfo("adminFees.id"));
        sic.add(new SelectorItemInfo("adminFees.number"));
        sic.add(new SelectorItemInfo("adminFees.name"));
        sic.add(new SelectorItemInfo("adminFees.longName"));
        
        sic.add(new SelectorItemInfo("marketFees.id"));
        sic.add(new SelectorItemInfo("marketFees.number"));
        sic.add(new SelectorItemInfo("marketFees.name"));
        sic.add(new SelectorItemInfo("marketFees.longName"));
//        
        sic.add(new SelectorItemInfo("company.id"));
        sic.add(new SelectorItemInfo("company.number"));
        sic.add(new SelectorItemInfo("company.name"));
        sic.add(new SelectorItemInfo("company.longNumber"));
        sic.add(new SelectorItemInfo("company.accountTable")); // ��Ҫ���˿�Ŀ�� by hpw
        sic.add(new SelectorItemInfo("company.isBizUnit"));
        
//                
        sic.add(new SelectorItemInfo("compensationAccount.id"));
        sic.add(new SelectorItemInfo("compensationAccount.number"));
        sic.add(new SelectorItemInfo("compensationAccount.name"));
        sic.add(new SelectorItemInfo("compensationAccount.longName"));
        
        sic.add(new SelectorItemInfo("guerdonAccount.id"));
        sic.add(new SelectorItemInfo("guerdonAccount.number"));
        sic.add(new SelectorItemInfo("guerdonAccount.name"));
        sic.add(new SelectorItemInfo("guerdonAccount.longName"));
                
        return sic;
    } 
	
	/**
	 * ����ǵ��뼯��ģ�壬��ô����ͷ��F7ֻ��ѡ����ģ��Ĵ������Ŀ�Ŀ���¼���Ŀ���������ֹ�������
	 * @author owen_wen 2010-10-08
	 * @throws BOSException 
	 */
	private void initF7Selector() throws BOSException{
		boolean isImptedTemplate = editData.isIsImptedTemplate();
		setF7Selector(prmtBeforeDevAccount, isImptedTemplate);
		setF7Selector(prmtBeforeOtherAccount, isImptedTemplate);
		setF7Selector(prmtBeforeSettAccount, isImptedTemplate);
		setF7Selector(prmtIntendAccount, isImptedTemplate);
		setF7Selector(prmtOtherIntendAccount, isImptedTemplate);
		setF7Selector(prmtOtherProAccount, isImptedTemplate);
		setF7Selector(prmtOtherSettAccount, isImptedTemplate);
		setF7Selector(prmtProAccount, isImptedTemplate);
		setF7Selector(prmtSettAccount, isImptedTemplate);
		setF7Selector(prmtProductAccount, isImptedTemplate);
		setF7Selector(prmtAdminFees, isImptedTemplate);
		setF7Selector(prmtMarketFees, isImptedTemplate);
		setF7Selector(prmtCompensationAccount, isImptedTemplate);
		setF7Selector(prmtGuerdonAccount, isImptedTemplate);
		setF7Selector(prmtTempAccount, isImptedTemplate);		
	}
	
	/**
	 * ���û�ƿ�ĿF7��Selector��Ϊÿ��f7PromptBox������Ӧ��Filter������ǵ��뼯��ģ�壬����Ҫ���϶���Ĺ�������
	 * @author owen_wen 2010-10-08
	 * @param f7PromptBox �����õ�F7
	 * @param isImptedTemplate �Ƿ��뼯��ģ��
	 */
	private void setF7Selector(KDBizPromptBox f7PromptBox, boolean isImptedTemplate){
		FilterInfo filter = new FilterInfo();			
		filter.getFilterItems().add( new FilterItemInfo("isCFreeze", new Integer(0)));		
		if (isImptedTemplate==true && f7PromptBox.getValue()!=null){
			String number = ((AccountViewInfo)f7PromptBox.getValue()).getNumber();
			if (number.indexOf('.')>0)
				number = number.substring(0, number.indexOf('.'));
			filter.getFilterItems().add(new FilterItemInfo("longNumber", number));
			filter.getFilterItems().add(new FilterItemInfo("longNumber", "%" + number + "!%", CompareType.LIKE));
			filter.setMaskString("#0 and (#1 or #2)");
		}
		AccountPromptBox accountPromptBox = new AccountPromptBox(this,SysContext.getSysContext().getCurrentFIUnit(),filter,false,true);
		f7PromptBox.setSelector(accountPromptBox);
	}
}