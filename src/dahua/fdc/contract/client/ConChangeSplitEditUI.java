/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.ViewCostInfoUI;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.IConChangeSplitEntry;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * �����֣��ɱ��ࣩ
 */
public class ConChangeSplitEditUI extends AbstractConChangeSplitEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ConChangeSplitEditUI.class);
    
    /**
     * output class constructor
     */
    public ConChangeSplitEditUI() throws Exception
    {
        super();
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	//�����¼����˳��
    	ConChangeSplitEntryInfo entry=null;    	
        for(int i=0; i<kdtEntrys.getRowCount(); i++){
    		entry = (ConChangeSplitEntryInfo)kdtEntrys.getRow(i).getUserObject();
        	entry.setIndex(i+1);
        }
        this.checkAcctSum();
        // �ж��Ƿ�����
        if (isIsInvalid()) {
			if (isDetailCostAccount()) { // ������ϣ���鵱ǰ�Ӻ�ͬ�����¼�Ƿ�����ϸ
				MsgBox.showWarning("������Ϻ����²��ʱ�������ֵ���Ŀ����ϸ��Ŀ�ϣ���ѡ������ϸ��Ŀ�����²�֡�");
				int count = kdtEntrys.getRowCount();
				// �����¼
				for (int i = count; i > 0; i--) {
					kdtEntrys.removeRow(i - 1);
				}
				SysUtil.abort();
			} else {
				super.actionSave_actionPerformed(e);
			}
		} else {
			super.actionSave_actionPerformed(e);
		}
        
//    	btnSubmit.doClick();
//    	ContractChangeBillFactory.getRemoteInstance().costChangeSplit(editData.getId());
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
    		IRow row = kdtEntrys.getRow(i);
    		if(FDCHelper.isEmpty(row.getCell("standard").getValue()) && FDCHelper.isEmpty(row.getCell("product").getValue())){
				FDCMsgBox.showWarning("�����Ϣ��"+(i+1)+"�еĿ�Ŀ����в�Ʒ��֣�");
				abort();
    		}
    	}
    	if(editData.getContractBill()==null||editData.getCurProject()==null||editData.getCurProject().getCostCenter()==null)
    		return;
    	String costId = editData.getCurProject().getCostCenter().getId().toString();
    	if(!ContractBillEditUI.isOpenProgramming(costId))
    		return;
    	if(!editData.getCurProject().isIsWholeAgeStage())
    		return;
    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if(UIRuleUtil.isNull(row.getCell("costAccount.curProject.number").getValue()))
				continue;
			if(UIRuleUtil.isNull(row.getCell("programming").getValue())){
				FDCMsgBox.showWarning("��Լ�滮����Ϊ�գ�");
				this.kdtEntrys.getEditManager().editCellAt(i,this.kdtEntrys.getColumnIndex("programming"));
				SysUtil.abort();
			}
    	}
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        setSplitState();
        //�����¼����˳��
    	/*ConChangeSplitEntryInfo entry=null;    	
        for(int i=0; i<kdtEntrys.getRowCount(); i++){
    		entry = (ConChangeSplitEntryInfo)kdtEntrys.getRow(i).getUserObject();
        	entry.setIndex(i+1);
        }*/
    }
    
    
    public void actionViewCostInfo_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionViewCostInfo_actionPerformed(e);
    	if(kdtEntrys.getRowCount()==0){
			return;
		}
		int[] selectedRows = KDTableUtil.getSelectedRows(kdtEntrys);
		ConChangeSplitEntryCollection c=new ConChangeSplitEntryCollection();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=kdtEntrys.getRow(selectedRows[i]);
			ConChangeSplitEntryInfo entry=(ConChangeSplitEntryInfo)row.getUserObject();
			c.add(entry);
		}
		boolean isAddThisAmt = false;
    	if(getOprtState().equals(OprtState.ADDNEW) || editData.getState() == null){
    		isAddThisAmt = true;
    	}
		UIContext uiContext = new UIContext(this);
		uiContext.put("isAddThis", Boolean.valueOf(isAddThisAmt));
		uiContext.put("src", "changeSplit");
		uiContext.put("entryCollection", c);
		IUIWindow dlg =  UIFactory.createUIFactory(UIFactoryName.MODEL).create(ViewCostInfoUI.class.getName(), uiContext,null,OprtState.VIEW);
		dlg.show();
    }
    
    
    protected void checkImp() throws Exception{
    	EntityViewInfo view = new EntityViewInfo();
		String filterField=null;
		FilterInfo filter = new FilterInfo();	
		//filterField="Parent.contractBill.id";
    	//filter.getFilterItems().add(new FilterItemInfo(filterField, getContractBillId()));
    	filter.getFilterItems().add(new FilterItemInfo("costBillId",editData.getContractChange().getId()));
    	filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
    	view.setFilter(filter);
    	AbstractObjectCollection coll=null;
    	AbstractObjectCollection collPay=null;
    	coll = SettlementCostSplitEntryFactory.getRemoteInstance().getSettlementCostSplitEntryCollection(view);
    	collPay = PaymentSplitEntryFactory.getRemoteInstance().getPaymentSplitEntryCollection(view);
		if(coll.iterator().hasNext()){
			MsgBox.showError(this,FDCSplitClientHelper.getRes("impBySett"));
			SysUtil.abort();
		}
		if(collPay.iterator().hasNext()){
			 MsgBox.showError(this,FDCSplitClientHelper.getRes("impByPay"));
			 SysUtil.abort();
		}
    }
    
    /**
     * �����ͬ���
     */
	public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception {
		checkConIsSplitedBeforeImp();
		checkParamValueBeforeImp();
		checkCostAccountntIsDuplicateBeforeImp();
		super.actionImpContrSplit_actionPerformed(e);
		setOneEntryAmt(txtChangeAmount.getBigDecimalValue());
		FDCSplitBillEntryCollection entrys = getEntrys();
		for (int i = 0; i < entrys.size(); i++) {
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) entrys.get(i);
			
			BigDecimal amount = FDCHelper.ZERO;
			if (entry.getLevel() == 0) {
				amount = FDCHelper.divide(FDCHelper.multiply(txtChangeAmount.getBigDecimalValue(), entry.getSplitScale()), FDCHelper.ONE_HUNDRED, 10, BigDecimal.ROUND_HALF_UP);
				entry.setAmount(amount);
				fdcCostSplit.apptAmount(entrys,entry);
			} 
			loadLineFields(kdtEntrys, kdtEntrys.getRow(i), entry);
		}

	}
	
	public void actionAcctSelect_actionPerformed(ActionEvent arg0)
			throws Exception {
		super.actionAcctSelect_actionPerformed(arg0);
		setOneEntryAmt(txtChangeAmount.getBigDecimalValue());
	}
	
	 /**
     * ���������һ����Ŀ����������Զ����������Ĺ���
     * �������ܻ���󵽻�����֧�����в��
     * 
     * @param shouldSplitAmt:Ӧ����
     */
	private void setOneEntryAmt(BigDecimal shouldSplitAmt) throws Exception{
		if(kdtEntrys.getRowCount()==1){
			KDTEditEvent event = new KDTEditEvent(
					kdtEntrys, null, null, 0,
					kdtEntrys.getColumnIndex("amount"), true, 1);
			final IRow row = kdtEntrys.getRow(0);
			row.getCell("amount").setValue(shouldSplitAmt);
			event.setValue(shouldSplitAmt);
			kdtEntrys_editStopped(event);
		}
	}
	
	protected void checkConIsSplitedBeforeImp() throws Exception{
		FilterInfo filter = new FilterInfo();	
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", getContractBillId()));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
    	boolean isExist=ContractCostSplitFactory.getRemoteInstance().exists(filter);
    	if(!isExist){
    		//��Ӧ�ĺ�ͬ��δ���в�֣����ܽ��д˲�����
    		MsgBox.showError(this,FDCSplitClientHelper.getRes("conNotSplit"));
			SysUtil.abort();
    	}
	}
	
    /**
     * ����Ӧ�ĺ�ͬ����Ƿ��Ѿ����������û��������
     * ���ݲ���FDC5014_NEXTSPLITISBASEONPREAUDITED�����Ƿ�Ҫ������ʾ
     * @author owen_wen 2010-11-30
     * @throws BOSException 
     * @throws EASBizException 
     * @throws BOSException 
     * @throws EASBizException 
     */
	private void checkParamValueBeforeImp() throws EASBizException, BOSException {
		FilterInfo filter = new FilterInfo();	
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", getContractBillId()));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE, CompareType.NOTEQUALS));
    	boolean isConSplitNotAudited = ContractCostSplitFactory.getRemoteInstance().exists(filter);
    	if (isConSplitNotAudited){
    		boolean paramValue = FDCUtils.getBooleanValue4FDCParamByKey(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),
    				FDCConstants.FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED);
    		if (paramValue){
	    		MsgBox.showInfo(this,FDCSplitClientHelper.getRes("conSplitNotAudit"));
				SysUtil.abort();
    		}
    	}
	}
	
	protected void checkCostAccountntIsDuplicateBeforeImp() throws Exception{
		if(this.kdtEntrys.getRowCount3() == 0){
			return;
		}
		
		FDCSplitBillEntryInfo entry=null;
		FDCSplitBillEntryCollection fDCSplitBillEntryColl = getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT);
		for(int i=0; i<kdtEntrys.getRowCount(); i++){			
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			FDCSplitBillEntryInfo entrytemp=null;		    	
			for (Iterator iter = fDCSplitBillEntryColl.iterator(); iter.hasNext();)		{
				entrytemp = (FDCSplitBillEntryInfo) iter.next();	
				if(entry.getCostAccount().getId().equals(entrytemp.getCostAccount().getId())){
					// �ɱ���Ŀ�����ظ������������ͬ��֣�
					MsgBox.showError(this,entry.getCostAccount().getName()+FDCSplitClientHelper.getRes("cannotImp")); 
					SysUtil.abort();
				}
			}
		}
	}   
    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkBeforeRemove();
    	checkImp();
        super.actionRemove_actionPerformed(e);
    }
    
    protected void checkDupBeforeSave() {
    	FDCSplitBillEntryCollection entrys = getEntrys();
    	Map dupMap = new HashMap();
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)iter.next();
    		if(entry.getCostAccount()==null){
				continue;
			}
			String key=entry.getCostAccount().getId().toString();
			String costbillid = entry.getCostBillId()==null?"":entry.getCostBillId().toString();
			key=key+costbillid;
//			if(entry.getSplitType()!=null){
//				key=key+entry.getSplitType().getValue();
//			}
			if(entry.getProduct()!=null){
				key=key+entry.getProduct().getId().toString();
			}
			FDCSplitBillEntryInfo mapEntry=(FDCSplitBillEntryInfo)dupMap.get(key);
			if(mapEntry==null){
				mapEntry=new PaymentSplitEntryInfo();
				dupMap.put(key, mapEntry);
			}else{
				FDCMsgBox.showWarning(this,"����ϸ��Ŀ��֣�ĩ����ֻ��Զ���֣���Ŀ����ϸ��Ŀ��ֿ�Ŀ��ͬ�����ܱ���!");
				this.abort();
			}
    	}
    }
    
    private void checkBeforeRemove() throws Exception {
    	if (editData == null || editData.getId() == null || editData.getId().toString().equals("")) {
    		return;
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", editData.getId().toString()));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		if (getBizInterface().exists(filter)) {
			MsgBox.showWarning(FDCSplitClientHelper.getRes("listRemove"));
			SysUtil.abort();
		}
    }
    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	checkImp();
        super.actionRemoveLine_actionPerformed(e);
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData(){
    	ConChangeSplitInfo objectValue = new ConChangeSplitInfo();
    	String contractChange = (String)this.getUIContext().get("costBillID");
    	ContractChangeBillInfo contractChangeInfo=null;
    	        
        SelectorItemCollection selectors = new SelectorItemCollection();
        selectors.add("id");
		selectors.add("number");
		selectors.add("name");
		selectors.add("amount");
		selectors.add("balanceAmount");
		selectors.add("hasSettled");
		selectors.add("contractBill.id");
		selectors.add("contractBill.number");
		selectors.add("contractBill.name");
		selectors.add("curProject.id");
		selectors.add("curProject.longNumber");
		selectors.add("contractBill.isMeasureContract");
        if(contractChange != null){
	        try {
	        	contractChangeInfo = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)),selectors);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
			objectValue.setContractChange(contractChangeInfo);
			if(contractChangeInfo.getContractBill()!=null)
				objectValue.setContractBill(contractChangeInfo.getContractBill());
			if(contractChangeInfo.getCurProject()!=null)
				objectValue.setCurProject(contractChangeInfo.getCurProject());
			this.txtCostBillNumber.setText(contractChangeInfo.getNumber());
			this.txtCostBillName.setText(contractChangeInfo.getName());
			this.txtChangeAmount.setValue(getChangeAmount(contractChangeInfo));//.setText(contractChangeInfo.getAmount()+"");
//			this.txtSplitedAmount.setValue(new BigDecimal(0));   //new BigDecimal(0)
			this.txtSplitedAmount.setValue(FDCHelper.ZERO);
//			this.txtAmount.setValue(contractChangeInfo.getAmount());
			this.txtAmount.setValue(getChangeAmount(contractChangeInfo));
			isMeasure=contractChangeInfo.getContractBill().isIsMeasureContract();
        }
		objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		//objectValue.setCreateTime(new Timestamp(System.currentTimeMillis()));
		objectValue.setIsInvalid(false);
    	return objectValue;
    }
    
    protected ICoreBase getBizInterface() throws Exception {				
		return ConChangeSplitFactory.getRemoteInstance();
	}
    
     //���� Javadoc��
    // @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#loadFields()
     
    public void loadFields() {
    	super.loadFields();
    	setSplitBillType(CostSplitBillTypeEnum.CNTRCHANGESPLIT);
		String billID = null;
		if(this.editData.getContractChange() != null){
			String contractChange = this.editData.getContractChange().getId().toString();
			SelectorItemCollection selectors = new SelectorItemCollection();
				//selectors.add("*");
	        selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("amount");
			selectors.add("balanceAmount");
			selectors.add("hasSettled");
			selectors.add("contractBill.id");
			selectors.add("contractBill.isMeasureContract");
		    try {
		        //ContractChangeBillInfo info = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)),selectors);
		    	ContractChangeBillInfo info = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(new ObjectUuidPK(BOSUuid.read(contractChange)),selectors);
		    	billID = info.getContractBill().getId().toString();
		    	this.txtCostBillNumber.setText(info.getNumber());
				this.txtCostBillName.setText(info.getName());
				this.txtChangeAmount.setValue(getChangeAmount(info));
				this.txtAmount.setValue(getChangeAmount(info));
				//���仯�����δ��ֽ��  by sxhong 2009-06-05 11:08:09
				DataChangeEvent event=new DataChangeEvent(txtSplitedAmount);
				event.setNewValue(txtSplitedAmount.getBigDecimalValue());
				event.setOldValue(null);
				txtSplitedAmount_dataChanged(event);
				
				isMeasure=info.getContractBill().isIsMeasureContract();
			} catch (EASBizException e) {
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				handUIExceptionAndAbort(e);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		setContractBillId(billID);
//		this.txtChangeAmount.setEnabled(false);
    	setDisplay();
    	
    	try {
			updateEntryProgramming();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
     //���� Javadoc��
    //@see com.kingdee.eas.fdc.finance.client.AbstractPaymentSplitEditUI#getSelectors()
     
    public SelectorItemCollection getSelectors() {
    	//return super.getSelectors();
    	SelectorItemCollection selector = super.getSelectors();   	
    	selector.add("contractChange.id"); 
    	selector.add("state");
    	selector.add("contractBill.id");
    	selector.add("entrys.programmings.id");
    	selector.add("entrys.programmings.number");
    	selector.add("entrys.programmings.name");
    	selector.add("contractBill.curProject.costCenter.id");
    	selector.add("contractBill.curProject.isWholeAgeStage");
    	return setSelectors(selector);
    }
    
    protected String getSplitBillEntryClassName() {
    	return ConChangeSplitEntryInfo.class.getName();
    }
    
    /**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
        return this.kdtEntrys;
	}
	
	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		return new ConChangeSplitEntryInfo();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper.getChangeCellNumberEdit());
		setSplitBillType(CostSplitBillTypeEnum.CNTRCHANGESPLIT);
		String contractId = FDCHelper.getId(ContractChangeBillFactory.getRemoteInstance().getContractChangeBillInfo(
				new ObjectUuidPK(editData.getContractChange().getId())).getContractBill());
		setContractBillId(contractId);
		//actionAudit.setVisible(false);
		//actionUnAudit.setVisible(false);
		//������δ������ʱ��ֱ���˳���ʾ���Ƿ񱣴桰����storeFields()һ�¡�
		if(!STATUS_VIEW.equals(this.getOprtState())){
			this.storeFields();
		}
		getDetailTable().getColumn("splitScale").setEditor(getScaleCellNumberEdit());
		
		if(getUIContext().get("AUDITBILLVIEW")!=null){
			this.kDLabelContainer8.setVisible(false);
			this.kDLabelContainer1.setVisible(false);
			this.kDLabelContainer10.setVisible(false);
			this.kDLabelContainer11.setVisible(false);
			kdtEntrys.setBounds(new Rectangle(10, 54, 993, 487));
			this.add(kdtEntrys, new KDLayout.Constraints(10, 59, 993, 532, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
	        
		}
	}
	
	/**
	 * 
	 * ���������ر���ؼ����������ʵ�֣�
	 * @return
	 * @author:liupd
	 * ����ʱ�䣺2006-10-13 <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	protected void updateButtonStatus() {
		
		super.updateButtonStatus();
		actionImpContrSplit.setVisible(true);
		actionImpContrSplit.setEnabled(true);
		actionRemove.setVisible(true);
		actionRemove.setEnabled(true);
		// ���������ɱ����ģ���������ɾ����
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionImpContrSplit.setVisible(true);
//			actionImpContrSplit.setEnabled(false);
//			//actionView.setVisible(false);
//			//actionView.setEnabled(false);
//		}
		
	}
	
	public void onShow() throws Exception
	{
		super.onShow();
		//setHideColumn();//���ض������
		if (OprtState.VIEW.equals(getOprtState())) {
			actionImpContrSplit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
		//actionRemove.setVisible(false);
	}
	
	private void setHideColumn()
	{
		//���ض������
		int column_index=getDetailTable().getColumnIndex("costAccount.curProject.id");
		for(int i=column_index;i<getDetailTable().getColumnCount();i++){
			getDetailTable().getColumn(i).getStyleAttributes().setHided(true);
		}			
	}
		
	private BigDecimal getChangeAmount(ContractChangeBillInfo info){
		if(info.isHasSettled()){
			return info.getBalanceAmount();
		}else{
			return info.getAmount();
		}
	}

    public  KDMenuItem getAttachMenuItem( KDTable table )
    {
//    	if (getTableForCommon()!=null||getDetailTable()!=null)
//    	{
//    		KDMenuItem item = new KDMenuItem();
//            item.setName("menuItemAttachment");
//            item.setAction(new ActionAttachMent());
//            return item;
//    	}
    	return null;

    }
//  ��¼����
    class ActionAttachMent extends ItemAction {
    	public ActionAttachMent() {
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource +"SubAttachMentText");
//            String _tempStr =EASResource.getString(FrameWorkClientUtils.strResource);
//            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
//            this.putValue(ItemAction.NAME, _tempStr);
         }
//        public void actionPerformed(ActionEvent e) {
//        	showSubAttacheMent(null);
//        }
    }
    
	private boolean isMeasure=false;
	protected boolean isMeasureContract() {
		return isMeasure;
	}
	
	/**
	 * ����Ŀ֮���Ƿ�Ϊ��//TODO���Ժ����Ż�
	 * @throws BOSException
	 * @throws SQLException
	 */
	private void checkAcctSum() throws BOSException ,SQLException{
		if(SysContext.getSysContext().getCurrentOrgUnit()!=null&&SysContext.getSysContext().getCurrentOrgUnit().getId()!=null	){
			//�����������Ŀ�ܺ�Ϊ���ı�����
			boolean noCtrl=false;
			String FDC_PARAM_NOCHANGESPLITACCTCTRL = FDCConstants.FDC_PARAM_NOCHANGESPLITACCTCTRL;
			String comPK=SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
			HashMap hmParamIn = new HashMap();
	        hmParamIn.put(FDC_PARAM_NOCHANGESPLITACCTCTRL, comPK);
	        try{
	        	HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
	            Object theValue = hmAllParam.get(FDC_PARAM_NOCHANGESPLITACCTCTRL);
	            if(theValue != null){
	            	noCtrl=Boolean.valueOf(theValue.toString()).booleanValue();
	    		}
	            if(noCtrl){
	            	return;
	            }
	        }catch(Exception e){
	        	this.logger.error("check acct sum err:"+e.getMessage(), e);
	        	handUIExceptionAndAbort(e);
	        }
	        
		}
		FDCSplitBillEntryCollection entrys = getEntrys();
		//���ҳ����и�����ĿȻ�������ݿ��ڵĺ�ͬ����������Ľ��֮�����Ƚ�
		Map map=new HashMap();
		Map tmpMap=new HashMap();
		Map acctMap=new HashMap();
		int i=0;
		for (Iterator iter = entrys.iterator(); iter.hasNext();i++) {
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo) iter.next();
			if((!entry.isIsLeaf()&&entry.getProduct()==null&&entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT)||(entry.isIsLeaf()&&(entry.getProduct()==null||entry.getSplitType()!=CostSplitTypeEnum.PRODSPLIT))){
				String key=entry.getCostAccount().getId().toString();
				BigDecimal tmpAmt = FDCNumberHelper.add(entry.getAmount(), tmpMap.get(key));
		
				//��ͬ��ĿҪ�ۼ�
				tmpMap.put(key, tmpAmt);
				acctMap.put(key, entry.getCostAccount());
			}
		}
		
		for(Iterator iter=tmpMap.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(FDCHelper.toBigDecimal(tmpMap.get(key)).signum()<0){
				map.put(key, tmpMap.get(key));
			}
		}
		if(map.size()==0){
			return;
		}
		String acctIds[]=new String[map.size()];
		map.keySet().toArray(acctIds);
		String contractId=this.editData.getContractBill().getId().toString();
		String changeId=this.editData.getContractChange().getId().toString();
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select fcostaccountid,sum(famount) famount from (\n");
		builder.appendSql("(select fcostaccountid,famount from T_CON_ContractCostSplitEntry where fparentid in (select fid from T_CON_ContractCostSplit conSplit where conSplit.fcontractBillId=? and fstate<>?) and ");
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		builder.appendParam("fcostaccountid", acctIds);
		builder.appendSql(" and ((fisleaf=1 and (fproductid is null or fsplitType is null or fsplitType<>? )) or (fisleaf=0 and fproductid is null and fsplitType=?))");
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.appendSql("\n) \nunion all ");
		builder.appendSql("(\nselect fcostaccountid,famount from T_CON_ConChangeSplitEntry where fparentid in (select fid from T_CON_ConChangeSplit changeSplit where changeSplit.fcontractBillId=? and fstate<>? and changeSplit.fcontractChangeid<>?) and ");
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		//changeId������ʱ=1ʹ�ò��ı�sql��ȡ������ֵ
		builder.addParam(changeId==null?"1":changeId);
		builder.appendParam("fcostaccountid", acctIds);
		builder.appendSql(" and ((fisleaf=1 and (fproductid is null or fsplitType is null or fsplitType<>? )) or (fisleaf=0 and fproductid is null and fsplitType=?))");
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.addParam(CostSplitTypeEnum.PRODSPLIT_VALUE);
		builder.appendSql(")\n)t group by fcostaccountid");
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String key = rowSet.getString("fcostaccountid");
			map.put(key, FDCNumberHelper.add(rowSet.getBigDecimal("famount"), map.get(key)));
		}
		
		
		Map treeMap=new TreeMap(new Comparator(){
			public int compare(Object arg0, Object arg1) {
				int retValue=0;
				if(arg0 instanceof CostAccountInfo &&arg1 instanceof CostAccountInfo){
					CostAccountInfo info1=(CostAccountInfo)arg0;
					CostAccountInfo info2=(CostAccountInfo)arg1;
					if(info1.getCurProject().getLongNumber()!=null&&info2.getCurProject().getLongNumber()!=null){
						retValue=info1.getCurProject().getLongNumber().compareTo(info2.getCurProject().getLongNumber());
					}
					if(retValue==0){
						if(info1.getLongNumber()!=null&&info2.getLongNumber()!=null){
							retValue=info1.getLongNumber().compareTo(info2.getLongNumber());
						}
					}
				}
				return retValue;
			}
		});
		for(Iterator iter=map.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(FDCHelper.toBigDecimal(map.get(key)).signum()<0){
				CostAccountInfo info=(CostAccountInfo)acctMap.get(key);
				String desc=info.getCurProject().getDisplayName()+" "+info.getLongNumber().replace('!', '.')+"\n";
				treeMap.put(info, desc);
			}
		}
		if(treeMap.size()>0){
			StringBuffer sb=new StringBuffer();
			for(Iterator iter=treeMap.values().iterator();iter.hasNext();){
				sb.append(iter.next());
			}
			FDCMsgBox.showDetailAndOK(this, "��ִ��ڿ�Ŀ���֮��С����", "���¿�Ŀ���֮��С����:\n"+sb.toString(), 0);
			SysUtil.abort();
		}
			
	}
	protected void loadData() throws Exception {
		handleSplitWorkFlow();
		super.loadData();
	}
	/**
	 *  ���ǩ֤ȷ�ϲ�ֹ�����������,�ﵽ���ǩ֤ȷ�ϲ�ֲ��빤������������أ�ֻҪ���ź�ͬID��������������� by Cassiel_peng 2009-9-19
	 */
	private void handleSplitWorkFlow() {
		//���ش����������⣬���������Ĳ�������ת��
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
        	if(this.getUIContext().get(UIContext.ID)!=null){
        		String costBillID=this.getUIContext().get(UIContext.ID).toString();
        		getUIContext().remove(UIContext.ID);
        		getUIContext().put("costBillID",costBillID);
        		setOprtState(OprtState.ADDNEW);
        		//�����ͬ�Ѿ���ֻ�Ҫ�ٴβ�ֵĻ�����ԭ�еĻ����Ͻ��в��
        		FDCSQLBuilder builder=new FDCSQLBuilder();
        		builder.appendSql("select top 1 fid from T_CON_ConChangeSplit where FContractChangeID=? and fstate<>?");
        		builder.addParam(costBillID);
        		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
        		try{
        			IRowSet rowSet=builder.executeQuery();
        			if(rowSet.next()){
        				String splitId=rowSet.getString("fid");
        				this.getUIContext().put(UIContext.ID,splitId);
        				setOprtState(OprtState.EDIT);
        			}
        		}catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
        		
        		logger.info("conChangeBill costbill id="+costBillID);
        	}else{
        		logger.error("��������ȡ�������ǩ֤ȷ��ID��can't get conChangeBill id");
        	}
        }
	}
	
	/**
	 * ��鵱ǰ���ǩ֤ȷ���Ƿ����Ϲ�
	 * 
	 * @return true ���� false δ����
	 */
	private boolean isIsInvalid() {
		// ��ʼfalse
		boolean isInvalid = false;
		// ��ǰ���������
		String costNumber = this.txtCostBillNumber.getText();
		// ���ǩ֤ȷ��ID����
		List isInvalidList = new ArrayList();
		String sql = "select FISinvalid from T_CON_ConChangeSplit where fcontractchangeid  in (select fid from T_CON_ContractChangeBill where fnumber = '"
				+ costNumber + "') ";
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		try {
			IRowSet set = builder.executeQuery();
			while (set.next()) {
				isInvalid = set.getBoolean(1);
				isInvalidList.add(Boolean.valueOf(isInvalid));
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		} catch (SQLException e) {
			handUIExceptionAndAbort(e);
		}
		// ����id����
		for (int i = 0; i < isInvalidList.size(); i++) {
			boolean temp = ((Boolean) isInvalidList.get(i)).booleanValue();
			// Ϊ�棬�Ǳ����Ϲ�
			if (temp) {
				isInvalid = true;
				break;
			}
		}
		return isInvalid;
	}
	/**
	 * ��鵱ǰ��Ŀ�Ƿ�����ϸ��Ŀ
	 * 
	 * @return true ����ϸ false ����ϸ
	 */
	private boolean isDetailCostAccount() {
		// ��ʼΪ����ϸ
		boolean isDetailCostAccount = false;
		IRow curRow = null;
		// �����ɱ���Ŀ
		CostAccountInfo cacInfo = null;
		// ���ǩ֤ȷ�Ϸ�¼
		ConChangeSplitEntryInfo ccseInfo = null;
		// ������ǰ��¼������
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			curRow = kdtEntrys.getRow(i);
			// �ñ��ǩ֤ȷ�ϻ�ȡ�ɱ���Ŀ
			ccseInfo = (ConChangeSplitEntryInfo) curRow.getUserObject();
			cacInfo = ccseInfo.getCostAccount();
			// �Ƿ�����ϸ
			if (!cacInfo.isIsLeaf()) {
				isDetailCostAccount = true;
			}
		}
		
		return isDetailCostAccount;
	}
	
	/**
	 * ��� �ɱ���� ���ܽ��
	 * 
	 * @author owen_wen
	 */
	protected void checkTotalCostAmt() {
		IRow row;
		FDCSplitBillEntryInfo entry;
		BigDecimal amount;

		amount = getTotalTxt().getBigDecimalValue();
		if (amount == null) {
			amount = FDCHelper.ZERO;
		}
		amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal splitedAmount = txtSplitedAmount.getBigDecimalValue();
		splitedAmount = FDCHelper.toBigDecimal(splitedAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
		if (splitedAmount == null) {
			splitedAmount = FDCHelper.ZERO;
		}
		// ��Ϊ�������������������Ϊ0ʱ���Զ���Ϊȫ����֣�����ȥ������amount.compareTo(FDCHelper.ZERO)!=0
		//������÷�Ʊģʽ�������˷������ش�����&& isInvoiceMgr()������
		//���������÷�Ʊģʽ��У��ɱ�����ͬ�����ֿ��Ա��档added by andy_liu 2012-3-28 
		if (splitedAmount.compareTo(FDCHelper.ZERO) == 0 && isInvoiceMgr()) {
		} else if (amount.compareTo(splitedAmount) > 0 || (	
				// modified by zhaoqin for R130805-0005 on 2013/08/21
				// ������Ϊ�������
				amount.compareTo(FDCHelper.ZERO) < 0 && splitedAmount.compareTo(FDCHelper.ZERO) <= 0 && amount.compareTo(splitedAmount) < 0)) {
			FDCMsgBox.showWarning(this, FDCSplitClientHelper.getRes("notAllSplit"));
			SysUtil.abort();
		} else if (amount.compareTo(splitedAmount) == 0) {
			editData.setSplitState(CostSplitStateEnum.ALLSPLIT);

			//������ϸ������Ŀ�Ŀ�Ŀ�Ƿ��Ѳ��	//Jelon 	Dec 11, 2006
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				row = kdtEntrys.getRow(i);
				entry = (FDCSplitBillEntryInfo) row.getUserObject();

				if (entry.getLevel() < 0)
					continue;//�ܼ���

				if (entry.getLevel() == 0 && entry.isIsLeaf()) {
					if (!entry.getCostAccount().getCurProject().isIsLeaf()) {
						FDCMsgBox.showWarning(this, "�����ֵ�����ϸ�Ĺ�����Ŀ�ĳɱ���Ŀ!");
						SysUtil.abort();
					}
				}
			}
		} else {
			// ���ܴ��ں�ͬ���
			FDCMsgBox.showWarning(this, FDCSplitClientHelper.getRes("moreThan"));
			SysUtil.abort();
		}
	}	
	
	protected void importCostSplitContract(){
		FDCSplitBillEntryCollection entryCollection = getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT);
		BigDecimal changeAmount = txtChangeAmount.getBigDecimalValue();
		BigDecimal amount = editData.getContractBill().getAmount();
		
		// modified by zhaoqin for R131101-0367 on 2013/11/07
		/*
		for(int i=0;i<entryCollection.size();i++){
			FDCSplitBillEntryInfo entryInfo = entryCollection.get(i);
			if(FDCHelper.compareTo(entryInfo.getDirectAmount(), FDCHelper.ZERO)!=0 || FDCHelper.compareTo(entryInfo.getDirectAmountTotal(), FDCHelper.ZERO)!=0){
				entryInfo.setDirectAmount(FDCHelper.divide(FDCHelper.multiply(entryInfo.getDirectAmount(), changeAmount), amount, 2, BigDecimal.ROUND_HALF_UP));
				entryInfo.setDirectAmountTotal(FDCHelper.divide(FDCHelper.multiply(entryInfo.getDirectAmountTotal(), changeAmount), amount, 2, BigDecimal.ROUND_HALF_UP));
			}
		}
		*/
		loadCostSplit(entryCollection);		
	}
	
	/**
	 * ������ȡ�÷�¼Map��Ӧ��Key
	 * 
	 * @param entryInfo
	 * @return
	 * @author��skyiter_wang
	 * @createDate��2013-10-31
	 */
	protected String getEntrysMapKey(FDCSplitBillEntryInfo entryInfo) {
		String key = entryInfo.getCostAccount().getId().toString() + entryInfo.getSeq();
		return key;
	}
}