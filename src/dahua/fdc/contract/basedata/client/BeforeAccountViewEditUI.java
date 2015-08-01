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
    boolean isAddNew = false;	//是否是编辑界面点击新增
    boolean isSaved = false;	//是否已经保存
//    boolean isDeleted = false;	//是否已经删除
    String[] uiData = null;	//用于保存界面上的初始值
//    int addTimes = 0;	//记录增加分录的次数
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
    	 * 判断是否分录已经设置完扣款类型对应的科目
    	 */
    	for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    		IRow row = this.kdtEntrys.getRow(i);
    		
    		if(row.getCell("account").getValue()==null){
    			if(deductType==null){
    				deductType = row.getCell("deductType").getValue().toString();
    			}else{
    				deductType +="、";
    				deductType += row.getCell("deductType").getValue().toString();
    			}
    		}
    		if(i==kdtEntrys.getRowCount()-1 && deductType!=null){
    			deductType += "扣款类型未设置对应的会计科目，不能保存！";
    			MsgBox.showInfo(this,deductType);
    			SysUtil.abort();
    		}
    	}
		
    	super.actionSubmit_actionPerformed(e);
    	
    	//如果是新增加的一个科目设置，保存的时候就要清空数据
    	if(this.getUIContext().get("isFirstAddNew")==null){
    		for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    			IRow row = this.kdtEntrys.getRow(i);
    			row.getCell("account").setValue(null);
    		}
    		
    	}
    	uiData = null;
    	//保存后，要重新保存界面上的数据
    	storeUIData();
    	initOldData(editData);
    	isSaved = true;
//    	isAddNew = false;
    }

    /**
     * 判断界面上分录的值是否有改变
     * 如果有改变，则不执行initOldData()方法
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
    	 * 这里判断是否是新增加的一个科目设置
    	 * 如果说是，则执行else
    	 */
    	if(this.editData.getId()!=null && this.getUIContext().get("isFirstAddNew")!=null && !isAddNew){
    		sqlBuilder.addParam(this.editData.getId().toString());
    		try {
    			rowSet = sqlBuilder.executeQuery();
    			/**
    			 * 如果有数据已经保存在数据库中，则取出来
    			 * 否则就把界面上的数据取出来
    			 */
    			if(rowSet.size()!=0){
    				for(int i=0;i<this.kdtEntrys.getRowCount();i++){
    					IRow row = this.kdtEntrys.getRow(i);
    					/**
    					 * 界面上的数据条数大于已经保存数据的条数
    					 * 取出界面上的数据加到deAccEntrysInfo中
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
    	
    	//判断界面上分录的值是否有改变过
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
     * 从界面上取出分录的对应科目，并保存在数组中
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
    	//新增时，设置扣款科目为空
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
//			MsgBox.showWarning(this, "不允许修改其他组织的数据!");
//			SysUtil.abort();
//		}
    	
    	//modified by pu_zhang  2005-5-26
    	String orgUnit = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
    	if(!company.equals(orgUnit)){
    		MsgBox.showWarning(this, "不允许修改其他组织的数据!");
			SysUtil.abort();
    	}
    	
    	//如果编辑状态，就可以编辑分录
    	this.kdtEntrys.setEditable(true);
    	this.kdtEntrys.setEnabled(true);
    	super.actionEdit_actionPerformed(e);
    	
    	this.btnEdit.setEnabled(true);
    	this.btnSave.setEnabled(true);
    	if(this.oprtState.equals(STATUS_ADDNEW) || isAddNew){
    		this.btnRemove.setEnabled(false);
    	}
    	
    	//TODO 只有 CU才能修改（暂时这样，有可能还有其它组织不能修改的，待和李涛确认），  by Owen_wen
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
    	//上面不相等已提示,下面代码又做判断多余 by hpw 2010.07.21
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
		
		//如果处于处于编辑状态，并且已经导入了模板，则可以编辑
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
		
		//查看的时候，锁定分录
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
     * 扣款类型对应会计科目设置
     * @throws Exception 
     *
     */
    protected void setDeductAccount(AccountPromptBox accountPromptBox) throws Exception{
    	//取扣款类型分录数据库中的值
    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
    	String sql = "select * from T_fdc_deductType a where " +

		//只显示启用状态的？

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
	//取扣款类型单的值
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
//    	//如果分录中的条数大于扣款类型单中可用
//    	if(deAccColl.size()>=deTypeColl.size()){
//    		 
//    	}else{
//    		//从扣款类型中取数据显示
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
    	
    	//让对应会计科目字段的表格上的编码F7控件显示编码
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
    	//使用统一的方法，处理科目表等过滤条件  by hpw 2010.07.21
    	SegNumF7.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		SegNumF7.setEntityViewInfo(view);
		
    	SegNumF7.setDisplayFormat("$number$");
    	SegNumF7.setEditFormat("$number$");
    	SegNumF7.setCommitFormat("$number$");
    }

    /**
     * 显示扣款类型对应的科目
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
			//集团下可修改
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
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtBeforeOtherAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtBeforeSettAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtCompany_dataChanged(DataChangeEvent e) throws Exception {
		if (OprtState.VIEW.equals(getOprtState())) {
    		return;
    	}
		if(this.editData.getCompany()!=null){
			/* 
			 *  added by Owen: editData.getCompany().equals(this.prmtCompany.getValue()为何就不设置F7，导致F7查询出来的都是空的。
			 *  先注释下面两行
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
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtOtherIntendAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtOtherProAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtOtherSettAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtProAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}
	
	protected void prmtTempAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}

	protected void prmtSettAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}	
	
	protected void prmtProductAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtProductAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtProductAccount.setEntityViewInfo(view);
	}

	protected void prmtProductAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
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
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtBeforeDevAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtBeforeDevAccount.setEntityViewInfo(view);
	}

	protected void prmtBeforeOtherAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtBeforeOtherAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtBeforeOtherAccount.setEntityViewInfo(view);
	}

	protected void prmtBeforeSettAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtBeforeSettAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtBeforeSettAccount.setEntityViewInfo(view);
	}

	protected void prmtIntendAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtIntendAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtIntendAccount.setEntityViewInfo(view);
	}

	protected void prmtOtherIntendAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtOtherIntendAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtOtherIntendAccount.setEntityViewInfo(view);
	}

	protected void prmtOtherProAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtOtherProAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtOtherProAccount.setEntityViewInfo(view);
	}

	protected void prmtOtherSettAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtOtherSettAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtOtherSettAccount.setEntityViewInfo(view);
	}
	
	protected void prmtTempAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtTempAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtTempAccount.setEntityViewInfo(view);
	}
	
	protected void prmtProAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtProAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtProAccount.setEntityViewInfo(view);
	}

	protected void prmtSettAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
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
		//未启用科目表给出提示  by hpw 2010.11.16
		if (comInfo.getAccountTable() != null){
			filter.getFilterItems().add(
					new FilterItemInfo("accountTableID.id", comInfo.getAccountTable().getId().toString()));
		}else{
			FDCMsgBox.showWarning("该组织未设置或未启用会计科目表，请先设置或启用！");
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
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtAdminFees.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtAdminFees.setEntityViewInfo(view);
	}
	
	protected void prmtAdminFees_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}
	
	protected void prmtMarketFees_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtMarketFees.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtMarketFees.setEntityViewInfo(view);
	}
	
	protected void prmtMarketFees_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}
	
	protected void prmtCompensationAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtCompensationAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtCompensationAccount.setEntityViewInfo(view);
	}
	
	protected void prmtCompensationAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
	}
	
	protected void prmtGuerdonAccount_willCommit(CommitEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
			return;
		}
		prmtGuerdonAccount.getQueryAgent().resetRuntimeEntityView();
		EntityViewInfo view = setEntityForAcc();
		prmtGuerdonAccount.setEntityViewInfo(view);
	}
	
	protected void prmtGuerdonAccount_willShow(SelectorEvent e) throws Exception {
		if(prmtCompany.getValue()==null){
			e.setCanceled(true);
			MsgBox.showWarning(this,"请先设置公司，需要按照公司筛选科目！");
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
		//删除后也要重新获取一次界面数据
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
        sic.add(new SelectorItemInfo("company.accountTable")); // 需要过滤科目表 by hpw
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
	 * 如果是导入集团模板，那么单据头的F7只能选择集团模板的带过来的科目的下级科目，加上这种过滤条件
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
	 * 设置会计科目F7的Selector，为每个f7PromptBox传入相应的Filter。如果是导入集团模板，则需要加上额外的过滤条件
	 * @author owen_wen 2010-10-08
	 * @param f7PromptBox 待设置的F7
	 * @param isImptedTemplate 是否导入集团模板
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