/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIModelDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.basedata.client.ViewCostInfoUI;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryCollection;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryFactory;
import com.kingdee.eas.fdc.contract.ContractProgrammingEntryInfo;
import com.kingdee.eas.fdc.contract.ContractWithProgramFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ContractCostSplitEditUI extends AbstractContractCostSplitEditUI
{
	private boolean isClearFlag = false;
	private boolean checkAllSplit = true;
	private int viewType = 0;//��ͬ�༭��Ϣ
    private static final Logger logger = CoreUIObject.getLogger(ContractCostSplitEditUI.class);
    private boolean isWholeProject = false;
    private ProgrammingContractInfo pcinfo = null;
    
    /**
     * output class constructor
     */
    public ContractCostSplitEditUI() throws Exception
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
    
//    public void setProgrammingContractInfo(ProgrammingContractInfo info){
//    	pcinfo = info;
//    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	//�����¼����˳��
    	/*ContractCostSplitEntryInfo entry=null;    	
        for(int i=0; i<kdtEntrys.getRowCount(); i++){
    		entry = (ContractCostSplitEntryInfo)kdtEntrys.getRow(i).getUserObject();
        	entry.setIndex(i+1);
        }*/
        
    	super.actionSave_actionPerformed(e);
//    	this.btnSubmit.doClick();
    }

    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}

    public void actionViewCostInfo_actionPerformed(ActionEvent e)
	throws Exception {
// TODO Auto-generated method stub
    	super.actionViewCostInfo_actionPerformed(e);
    	if(kdtEntrys.getRowCount()==0){
			return;
		}
    	
		int[] selectedRows = KDTableUtil.getSelectedRows(kdtEntrys);
		ContractCostSplitEntryCollection c=new ContractCostSplitEntryCollection();
		for(int i=0;i<selectedRows.length;i++){
			IRow row=kdtEntrys.getRow(selectedRows[i]);
			ContractCostSplitEntryInfo entry=(ContractCostSplitEntryInfo)row.getUserObject();
			c.add(entry);
		}
		boolean isAddThisAmt = false;
    	if(getOprtState().equals(OprtState.ADDNEW) || editData.getState() == null){
    		isAddThisAmt = true;
    	}
		UIContext uiContext = new UIContext(this);
		uiContext.put("entryCollection", c);
		uiContext.put("isAddThis", Boolean.valueOf(isAddThisAmt));
		IUIWindow dlg =  UIFactory.createUIFactory(UIFactoryName.MODEL).create(ViewCostInfoUI.class.getName(), uiContext,null,OprtState.VIEW);
		dlg.show();
}
    
    
	/**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	String costBillID=null;
//    	if(getUIContext().get("costBillID")==null){
        	costBillID=editData.getContractBill().getId().toString();    		
//    	}else{
//        	costBillID = (String)getUIContext().get("costBillID");
//    	}    	
//    	getUIContext().put("costBillID",costBillID);
        checkConInWF();
		// �����ͬ���ɾ����Ҫ�ж��Ƿ��Ѿ����ɹ��������뵥
		if (PayReqUtils.isContractBill(costBillID)) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("contractId", costBillID));

			// �ú�ͬ�Ѿ����ڸ������뵥�����ܽ��д˲���
			boolean hasPayRequest = PayRequestBillFactory
					.getRemoteInstance().exists(filter);
			if (hasPayRequest) {
				MsgBox.showError(this, FDCSplitClientHelper
						.getRes("hasPayRequest"));
				SysUtil.abort();
			}
		}
		checkBeforeRemove();
        super.actionRemove_actionPerformed(e);
    }
    
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception {
    	checkConInWF();
		super.actionRemoveLine_actionPerformed(e);
	}
    private void checkConInWF() {
    	//�Ƿ�������ǰ���
    	HashMap param;
		try {
			param = FDCUtils.getDefaultFDCParam(null,editData.getContractBill().getOrgUnit().getId().toString());
			boolean splitBeforeAudit = false;
			if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
				splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
			}
			if(splitBeforeAudit && FDCUtils.isRunningWorkflow(editData.getContractBill().getId().toString())
					&& CostSplitStateEnum.ALLSPLIT.equals(editData.getSplitState())){
				MsgBox.showWarning(this,"��ͬ�ڹ�����������ɾ����ͬ��֣�");
				SysUtil.abort();
			}
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}		
    }
    
    public void actionProgrAcctSelect_actionPerformed(ActionEvent e)
    		throws Exception {
    	if(isClearFlag){
    		return;
    	}
//    	checkContractWithProgram();
//    	IRow row=null;
//		FDCSplitBillEntryInfo entry=null;
//		
//		for(int i=0; i<kdtEntrys.getRowCount(); i++){
//			row=kdtEntrys.getRow(i);
//			entry=(FDCSplitBillEntryInfo)row.getUserObject();
//			
//			if(entry.getCostBillId()!=null){
//				FDCMsgBox.showInfo(this,"�Ѿ������˺�ͬ�滮�������ظ����룡");
//				return;
//			}
//		}
//		importContractWithProgram();    	
//		setDisplay();    
ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(getContractBillId()));
    	
    	String checkIsExistProg = ContractCostSplitFactory.getRemoteInstance().checkIsExistProg(this.getContractBillId().toString());
    	checkProgSub(checkIsExistProg);
		BigDecimal allAssigned = FDCHelper.ZERO;
    	if(checkIsExistProg != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("number");
			sic.add("costEntries.*");
			sic.add("costEntries.costAccount.*");
			sic.add("costEntries.costAccount.curProject.*");
			ProgrammingContractInfo programmingContractInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(BOSUuid.read(checkIsExistProg)),sic);
//			programmingContractInfo.getCostEntries().get(key);
//			FDCSplitBillEntryCollection entrys = getEntrys();
			ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
//			for(Iterator it = costEntries.iterator();it.hasNext(); ){
////				pteco programmingContractInfo = (ProgrammingContractInfo)it.next();
//				ProgrammingContracCostInfo programmingContracCostInfo = (ProgrammingContracCostInfo)it.next();
//				programmingContracCostInfo.getCostAccount().getLongNumber();
//				programmingContracCostInfo.getCostAccount().getDisplayName();
//			}
			//if costEntries is null then abort
			if(costEntries.size() == 0){
				FDCMsgBox.showInfo("��ǰ��ͬ��Ӧ�Ŀ�ܺ�Լ�ĳɱ�����Ϊ�գ�ԭ������ݲ���");
				this.abort();
			}
			getDetailTable().removeRows(false);
	    	IRow row;
	    	ProgrammingContracCostInfo acct=null;
	    	FDCSplitBillEntryInfo entry;
	    	int groupIndex = 0;
	    	List cachList = new ArrayList();
	    	for(Iterator iter = costEntries.iterator();iter.hasNext();){
	    		acct=(ProgrammingContracCostInfo)iter.next();
	    		if (cachList.contains(acct.getCostAccount())) {
					continue;
				} else {
					cachList.add(acct.getCostAccount());
				}
	    		//�ܵ��ѷ�����
				allAssigned = allAssigned.add((acct.getContractAssign() == null ? FDCHelper.ZERO:acct.getContractAssign()));
	    	}
	    	int tempCount = 0;
	    	BigDecimal tempAll = FDCHelper.ZERO;
	    	BigDecimal splitAmount = FDCHelper.ZERO;
	    	cachList = new ArrayList();
	    	for(Iterator iter = costEntries.iterator();iter.hasNext();){
	    		acct=(ProgrammingContracCostInfo)iter.next();
	    		if (cachList.contains(acct.getCostAccount())) {
					continue;
				} else {
					cachList.add(acct.getCostAccount());
				}
				entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);
				entry.setCostAccount(acct.getCostAccount());  
				entry.setLevel(0);
				entry.setIsLeaf(true);		
//				entry.setAmount(acct.getContractAssign());
	    		tempCount++;
//	    		if(tempCount < costEntries.size()){
//	    			tempAll = FDCHelper.add(tempAll, acct.getContractAssign());
//	    		}
				//������	
				groupIndex++;
				entry.setIndex(groupIndex);
				row=addEntry(entry);
//	    		if(entry.getLevel()==0){
//					groupIndex++;				
//				}
//	    		row=getDetailTable().addRow();
//	    		loadLineFields(getDetailTable(), row, entry);
				//���µ�ǰ��Ŀ����Ҫ�ȵ���Լ�滮�Ŀ�ܺ�Լ��ɲ��ܲ��ԣ�����ע��
//	    		row = kdtEntrys.addRow();
	    		row.getCell("costAccount.curProject.number").setValue(
						acct.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
	    		row.getCell("costAccount.number").setValue(
	    				acct.getCostAccount().getLongNumber().replace('!','.'));
	    		row.getCell("costAccount.curProject.name").setValue(
						acct.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
	    		row.getCell("costAccount.id").setValue(acct.getCostAccount().getId());
	    		row.getCell("costAccount.name").setValue(
	    				acct.getCostAccount().getDisplayName().replace('_','\\'));
	    		
	    		if(tempCount == costEntries.size()){
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("splitScale").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("splitScale").setValue(FDCHelper.subtract(new BigDecimal(100), tempAll));
		    		}
	    			//���
	    			if(contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
		    			row.getCell("amount").setValue(FDCHelper.ZERO);
		    		}else{
		    			row.getCell("amount").setValue(FDCHelper.divide(contractBillInfo.getAmount().multiply(FDCHelper.subtract(new BigDecimal(100), tempAll)), new BigDecimal(100), 4, 5));
		    		}
	    		}else{
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
	    				row.getCell("splitScale").setValue(FDCHelper.ZERO);
	    			}else{
	    				row.getCell("splitScale").setValue(FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
	    			}
	    			if(contractBillInfo.getAmount().compareTo(FDCHelper.ZERO) == 0 || acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 ||allAssigned.compareTo(FDCHelper.ZERO) == 0 ){
	    				row.getCell("amount").setValue(FDCHelper.ZERO);
	    			}else{
	    				row.getCell("amount").setValue(contractBillInfo.getAmount().multiply(FDCHelper.divide(acct.getContractAssign(), allAssigned,4,5)));
	    			}
	    			if(acct.getContractAssign().compareTo(FDCHelper.ZERO) == 0 || allAssigned.compareTo(FDCHelper.ZERO) == 0){
	    				tempAll = FDCHelper.add(tempAll, FDCHelper.ZERO);
	    			}else{
	    				tempAll = FDCHelper.add(tempAll, FDCHelper.divide(FDCHelper.toBigDecimal(acct.getContractAssign()), allAssigned,4,5).multiply(new BigDecimal(100)));
	    			}
	    		}
	    		splitAmount = FDCHelper.add(FDCHelper.toBigDecimal(row.getCell("amount").getValue()), splitAmount);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.number").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.name").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.name").getStyleAttributes().setLocked(false);
				row.getCell("splitScale").getStyleAttributes().setLocked(false);
				row.getCell("amount").getStyleAttributes().setLocked(false);
				row.getCell("costAccount.curProject.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.number").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.curProject.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("costAccount.name").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("standard").getStyleAttributes().setBackground(new Color(0xF6F6BF));
				entry.setAmount(FDCHelper.toBigDecimal(row.getCell("amount").getValue()));
	    	}
//	    	setNameDisplay();
	    	setDisplay();
//	    	txtSplitedAmount.setValue(splitAmount);
	    	isClearFlag = true;
		}
    	
    	updateEntryProgramming();
    }
    private void checkProgSub(String checkIsExistProg) throws Exception {
//    	FDCSplitBillEntryInfo entry=null;
//    	for(int i=0; i<kdtEntrys.getRowCount(); i++){			
//			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
//			if(entry.getCostAccount().getId() != null){
    	if(checkIsExistProg == null){
    		FDCMsgBox.showWarning("�ú�ͬû�й�����ܺ�Լ");
			this.abort();
    	}
    		boolean costSplit = FDCUtils.isCostSplit(null, getContractBillId().toString());
    		if(!costSplit){
    			FDCMsgBox.showWarning("��ǰ��ͬ�Ƿǲųɱ����ͬ�����ܹ����滮��Ŀ");
    			this.abort();
    		}
    		if(kdtEntrys.getRowCount() > 0){
				int ret = MsgBox.showConfirm2(this,"ѡ��滮��Ŀ�����ԭ������ݣ��Ƿ�������ǣ���ղ������������񣬷������β���");
				if (ret==MsgBox.YES) {
//					changeProgSplitEntry();
				} else if(ret==MsgBox.CANCEL){
					this.abort();
				}
			}
//    	}
    }
    /**
	 * ������ǰ��ӿո���ʾ����Ч��
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent( ) {
		StringBuffer blank = new StringBuffer("");
//		for (int i = level; i > 0; i--) {
			blank.append("       ");
//		}
		return blank.toString();
	}

	/**
	 * ����������Ч����ʾ
	 */
	private void setNameDisplay() {
		int rowCount = kdtEntrys.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = kdtEntrys.getRow(i);
//			int level = row.getTreeLevel();
			Object name = row.getCell("costAccount.curProject.name").getValue();
			if (name != null && name.toString().trim().length() > 0) {
				String blank = setNameIndent();
				row.getCell("costAccount.curProject.name").setValue(blank + name.toString());
			}
		}
	}
    private void checkContractWithProgram() throws Exception{
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", this.getContractBillId()));
    	boolean isConProgram = ContractWithProgramFactory.getRemoteInstance().exists(filter);
    	if(!isConProgram){
    		FDCMsgBox.showWarning(this, "�ú�ͬû�й�����Լ�滮!");
    		this.abort();
    	}
    }
    private void importContractWithProgram() throws Exception{
    	ContractProgrammingEntryCollection entryColls = getContractWithProgamData();
    	FDCSplitBillEntryInfo entry=null;
    	BigDecimal totalAmt = FDCHelper.ZERO;
    	BigDecimal shouldSplitAmt = txtAmount.getBigDecimalValue();
    	BigDecimal balanceAmt = shouldSplitAmt;
    	int groupIndex = 0;
    	int curIndex = 0;
    	BOSUuid costBillId=BOSUuid.read(getContractBillId());

    	if(entryColls.size()==0){
			return;
		}
    	Map entryMap = new HashMap();
    	ContractProgrammingEntryCollection newColls = new ContractProgrammingEntryCollection();
		for (int i = 0; i < entryColls.size(); i++) {
			ContractProgrammingEntryInfo entryInfo = entryColls.get(i);
			totalAmt = FDCHelper.add(entryInfo.getProgrammingMoney(), totalAmt);
			//ͬһ������Ŀ��ͬһ��Ŀ�����ϲ�
			String key = entryInfo.getCostAccount().getId().toString()
					+ entryInfo.getCostAccount().getCurProject().getId()
							.toString();
			if (entryMap.containsKey(key)) {
				ContractProgrammingEntryInfo acctEntryInfo = (ContractProgrammingEntryInfo) entryMap
						.get(key);
				acctEntryInfo.setProgrammingMoney(FDCHelper
						.add(acctEntryInfo.getProgrammingMoney(), entryInfo
								.getProgrammingMoney()));
			} else {
				newColls.add(entryInfo);
				entryMap.put(key, entryInfo);
			}
		}
		for (Iterator iter = newColls.iterator(); iter.hasNext();){
			ContractProgrammingEntryInfo entryInfo = (ContractProgrammingEntryInfo)iter.next();
			
			entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);							
			entry.setCostAccount(entryInfo.getCostAccount());	
			entry.setCostBillId(costBillId);
			entry.setLevel(0);
			entry.setIsLeaf(true);
			groupIndex++;
			entry.setIndex(groupIndex);
			
			BigDecimal programAmt = FDCHelper.toBigDecimal(entryInfo.getProgrammingMoney()).setScale(5);
			// ������λ
			BigDecimal amount = FDCHelper.ZERO;
			if (totalAmt.compareTo(FDCHelper.ZERO) != 0) {
				amount = FDCHelper.divide(programAmt.multiply(shouldSplitAmt),totalAmt, 2, BigDecimal.ROUND_HALF_UP);
			}
			if(balanceAmt.compareTo(amount)>0){
				entry.setAmount(amount);
				balanceAmt = FDCHelper.subtract(balanceAmt, amount);
			}else{
				entry.setAmount(balanceAmt);
				balanceAmt=FDCHelper.ZERO;
			}
			//β�������һ����¼
			///if(curIndex==(entryColls.size()-1)&&entry!=null){
			//update by renliang
			if(curIndex==(entryColls.size()-1)){
				if(balanceAmt.compareTo(FDCHelper.ZERO)!=0){
					entry.setAmount(FDCHelper.toBigDecimal(entry.getAmount()).add(balanceAmt));
					balanceAmt = FDCHelper.ZERO;
				}
			}
			addEntry(entry);
			++curIndex;
		}
		txtSplitedAmount.setValue(shouldSplitAmt);
		txtUnSplitAmount.setValue(balanceAmt);
    }
    private ContractProgrammingEntryCollection getContractWithProgamData() throws Exception{
    	String contractId = getContractBillId();
    	ContractProgrammingEntryCollection entryColls = null;
    	
    	Set idSet = new HashSet();
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select fprogrammingid from t_con_contractwithProgram where fcontractbillid=?");
    	builder.addParam(contractId);
    	IRowSet rs = builder.executeQuery();
    	while(rs.next()){
    		idSet.add(rs.getString("fprogrammingid"));
    	}
    	if(idSet.size()==0){
    		return new ContractProgrammingEntryCollection();
    	}
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	filter.getFilterItems().add(new FilterItemInfo("parent.id", idSet, CompareType.INCLUDE));
    	view.getSelector().add("programmingMoney");
    	view.getSelector().add("costAccount.id");
    	view.getSelector().add("costAccount.longnumber");
    	view.getSelector().add("costAccount.displayName");
    	view.getSelector().add("costAccount.name");
    	view.getSelector().add("costAccount.curproject.id");
    	view.getSelector().add("costAccount.curproject.longnumber");
    	view.getSelector().add("costAccount.curproject.displayName");
    	view.getSelector().add("costAccount.curproject.name");
    	view.getSelector().add("costAccount.curproject.isLeaf");
    	SorterItemInfo sort = new SorterItemInfo("costAccount.curproject.longnumber");
    	sort.setSortType(SortType.ASCEND);
    	view.getSorter().add(sort);
    	sort = new SorterItemInfo("costAccount.longnumber");
    	sort.setSortType(SortType.ASCEND);
    	view.getSorter().add(sort);
    	entryColls = ContractProgrammingEntryFactory.getRemoteInstance().getContractProgrammingEntryCollection(view);
    	return entryColls;
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
    
    protected void checkbeforeSave() {
    	if(isImportConSplit()&&isEmpty()){
    		try {
    			splitByAimCostSplitScale();
    		} catch (Exception e) {
    			handUIExceptionAndAbort(e);
    		}
    	}
    	super.checkbeforeSave();
    	
    	if (kdtEntrys.getRowCount() > 0) {
			BigDecimal splitScale = FDCHelper.ZERO;
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				splitScale = FDCHelper.add(splitScale, kdtEntrys.getCell(i, "splitScale").getValue());
			}
			if (splitScale.compareTo(FDCHelper.ZERO) == 0) {
				FDCMsgBox.showWarning("��ͬδ��֣���������");
				abort();
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
     * output actionImpContrSplit_actionPerformed
     */
    public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception
    {
    	
    	FDCCostSplit costSplit=new FDCCostSplit(null);
    	costSplit.refreshApportionAmount(editData,null);
    	updateAmountColumn(getEntrys());
    }
             
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI.class.getName();
	}
		
	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return ContractCostSplitFactory.getRemoteInstance();
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
		//return null;
		//modified by ken_liu..���ڱ�����entrysMap..��fdcsplitbilleditui
		ContractCostSplitEntryInfo info = new com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		return info;
	}
	

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#createNewData()
	 */
	protected IObjectValue createNewData() {
		// TODO �Զ����ɷ������
		//return super.createNewData();
				
		ContractCostSplitInfo objectValue=new ContractCostSplitInfo();
		
        //objectValue.setCompany((com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentFIUnit()));
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        String costBillID=null;
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()&&this.getUIContext().get("billID")!=null){
        	costBillID=this.getUIContext().get("billID").toString();
        	getUIContext().remove(UIContext.ID);
        }else{
        	costBillID = (String)getUIContext().get("costBillID");
        }
        ContractBillInfo costBillInfo=null;
        SelectorItemCollection selectors = new SelectorItemCollection();
        //selectors.add("*");
        selectors.add("id");
        selectors.add("number");
        selectors.add("name");
        selectors.add("amount");
        selectors.add("curProject.id");
        selectors.add("curProject.longNumber");
        selectors.add(new SelectorItemInfo("state"));
        selectors.add(new SelectorItemInfo("orgUnit.id"));
        try {
        	costBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(costBillID)),selectors);
        } catch (Exception e) {
        	handUIExceptionAndAbort(e);
        }
        objectValue.setContractBill(costBillInfo);
        if(costBillInfo.getCurProject()!=null)
        	objectValue.setCurProject(costBillInfo.getCurProject());
        txtCostBillNumber.setText(costBillInfo.getNumber());
        //txtCostBillName.setText(costBillInfo());    	
        txtAmount.setValue(costBillInfo.getAmount());
        objectValue.setIsInvalid(false);
        setContractBillId(costBillInfo.getId().toString());
        objectValue.setIsConfirm(true);        
        return objectValue;
	}

	public void onLoad() throws Exception {
		/* TODO �Զ����ɷ������ */
		getDetailTable().getLayoutManager().setVSplitButtonVisible(false);
		getDetailTable().getLayoutManager().setHSplitButtonVisible(false);
		super.onLoad();
		//��ʱ�����ύ
//		this.btnSubmit.setVisible(true);
//		this.actionSubmit.setEnabled(true);
//		tHelper.getDisabledTables().add(getDetailTable());
		initWorkButton();
		setSplitBillType(CostSplitBillTypeEnum.CONTRACTSPLIT);
		
		//�Ƿ�������ǰ���
		HashMap param = FDCUtils.getDefaultFDCParam(null,editData.getContractBill().getOrgUnit().getId().toString());		
		boolean splitBeforeAudit = false;
		if(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT)!=null){
			splitBeforeAudit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SPLITBFAUDIT).toString()).booleanValue();
		}
		//����ύʱ���Ƿ����ͬ���
		if(param.get(FDCConstants.FDC_PARAM_CHECKALLSPLIT )!= null){
	    	checkAllSplit = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_CHECKALLSPLIT).toString()).booleanValue();
		}
//		String id = "";
		if(editData.getContractBill()!=null){
//			id = editData.getContractBill().getId().toString();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("state"));
			sic.add(new SelectorItemInfo("isAmtWithoutCost"));
			sic.add(new SelectorItemInfo("isCoseSplit"));
			sic.add(new SelectorItemInfo("programmingContract.id"));
			sic.add(new SelectorItemInfo("programmingContract.name"));
			sic.add(new SelectorItemInfo("programmingContract.number"));
			sic.add(new SelectorItemInfo("curProject.id"));
			sic.add(new SelectorItemInfo("curProject.isWholeAgeStage"));
			//2008-12-25 .������ò��� ���ҽ���ɱ��ģ���ťӦ������
			ContractBillInfo contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(editData.getContractBill().getId()),sic);
			if(splitBeforeAudit && !FDCBillStateEnum.SUBMITTED.equals(contractBillInfo.getState())
					&& !contractBillInfo.isIsCoseSplit()){
				super.setOprtState(OprtState.VIEW); 
			}
			isWholeProject = contractBillInfo.getCurProject().isIsWholeAgeStage();
			pcinfo = contractBillInfo.getProgrammingContract();
		}
		

//		if(splitBeforeAudit && !FDCBillStateEnum.SUBMITTED.equals(editData.getContractBill().getState())){
//			super.setOprtState(OprtState.VIEW); 
//		}
		
		/*
		for(int i=kdtEntrys.getColumnIndex("product")+1; i<kdtEntrys.getColumnCount(); i++){
			kdtEntrys.getColumn(i).getStyleAttributes().setHided(false);
		}
		*/
		
		/*
		
		if (STATUS_ADDNEW.equals(getOprtState())) {
	    	importCostSplitContract();    	
	    	//importCostSplitCntrChange();    	
			setDisplay();
			
			

			IRow row=null;
			FDCSplitBillEntryInfo entry=null;
			
			BOSUuid costBillId=null;
			BigDecimal amount=null;
			
			BOSObjectType contractBill=(new ContractBillInfo()).getBOSType();
			
			for(int i=0; i<kdtEntrys.getRowCount(); i++){
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				
				costBillId=entry.getCostBillId();
				if(costBillId!=null){
					amount=entry.getAmount();
					if(amount!=null){
						if(costBillId.getType().equals(contractBill)){
							row.getCell("contractAmount").setValue(amount);							
						}else{
							row.getCell("cntrChangeAmount").setValue(amount);
						}
						
						entry.setAmount(null);
						row.getCell("amount").setValue(null);
					}
				}
			}
		}
		*/
		 getDetailTable().getColumn("splitScale").setEditor(getScaleCellNumberEdit());
		 
		 //add by david_yang R110325-549 2011.04.28(֧�ָ���)
		 getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper._getTotalCellNumberEdit());
		 
		 //���� ��ִͬ����Ϣ ��ť �Ͳ˵�ͼƬ.
		 this.btnViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		 this.menuViewContract.setIcon(EASResource.getIcon("imgTbtn_execute"));
		 
		 String projectId = editData.getCurProject().getId().toString();
		 if(projectId != null){
			 CurProjectInfo project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(projectId));
			 if(!project.isIsWholeAgeStage())
				 kdtEntrys.getColumn("programming").getStyleAttributes().setLocked(true);
		 }
		 EntityViewInfo view = new EntityViewInfo();
		 FilterInfo filInfo = new FilterInfo();
		 filInfo.getFilterItems().add(new FilterItemInfo("programming.project.id",projectId));
//		 filInfo.getFilterItems().add(new FilterItemInfo("programming.isLatest","1"));
		 view.setFilter(filInfo);
		 
		 final KDBizPromptBox kdtEntrys_programming_PromptBox = new KDBizPromptBox();
		 kdtEntrys_programming_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ProgrammingContractQuery");
//		 kdtEntrys_programming_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.programming.app.ProgrammingContractF7Query");
		 kdtEntrys_programming_PromptBox.setVisible(true);
		 kdtEntrys_programming_PromptBox.setEditable(true);
	     kdtEntrys_programming_PromptBox.setDisplayFormat("$number$");
	     kdtEntrys_programming_PromptBox.setEditFormat("$number$");
	     kdtEntrys_programming_PromptBox.setCommitFormat("$number$");
	     kdtEntrys_programming_PromptBox.setEntityViewInfo(view);
	     KDTDefaultCellEditor kdtEntrys_programming_CellEditor = new KDTDefaultCellEditor(kdtEntrys_programming_PromptBox);
	     this.kdtEntrys.getColumn("programming").setEditor(kdtEntrys_programming_CellEditor);
	     ObjectValueRender tblEconItem_payType_OVR = new ObjectValueRender();
	     tblEconItem_payType_OVR.setFormat(new BizDataFormat("$name$"));
	     this.kdtEntrys.getColumn("programming").setRenderer(tblEconItem_payType_OVR);
	     // modify by yxl 20151103 ����ͬ��ֹ������ϵ������ť���ŵ���¼��
	     addEntrysButton();
	}
	
	private void addEntrysButton(){
		//kDContainer1
		KDWorkButton btnAcctSelect1 = new KDWorkButton();
		KDWorkButton btnSplitProj1 = new KDWorkButton();
		KDWorkButton btnSplitBotUp1 = new KDWorkButton();
		KDWorkButton btnSplitProd1 = new KDWorkButton();
		KDWorkButton btnRemoveLine1 = new KDWorkButton();
		btnAcctSelect1.setAction((IItemAction)ActionProxyFactory.getProxy(actionAcctSelect,new Class[]{IItemAction.class},getServiceContext()));
		btnSplitProj1.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitProj,new Class[]{IItemAction.class},getServiceContext()));
		btnSplitBotUp1.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitBotUp,new Class[]{IItemAction.class},getServiceContext()));
		btnSplitProd1.setAction((IItemAction)ActionProxyFactory.getProxy(actionSplitProd,new Class[]{IItemAction.class},getServiceContext()));
		btnRemoveLine1.setAction((IItemAction)ActionProxyFactory.getProxy(actionRemoveLine,new Class[]{IItemAction.class},getServiceContext()));
//		KDWorkButton btnAcctSelect1 = new KDWorkButton("�ɱ���Ŀ");
//		KDWorkButton btnSplitProj1 = new KDWorkButton("�Զ����");
//		KDWorkButton btnSplitBotUp1 = new KDWorkButton("ĩ�����");
//		KDWorkButton btnSplitProd1 = new KDWorkButton("��Ʒ���");
//		KDWorkButton btnRemoveLine1 = new KDWorkButton("ɾ����¼");
		btnAcctSelect1.setText("�ɱ���Ŀ");
		btnRemoveLine1.setIcon(btnRemoveLine.getIcon());
		kDContainer1.addButton(btnAcctSelect1);
		kDContainer1.addButton(btnSplitProj1);
		kDContainer1.addButton(btnSplitBotUp1);
		kDContainer1.addButton(btnSplitProd1);
		kDContainer1.addButton(btnRemoveLine1);
	}
	
	
	
	public void reloadCompoent(Object[] objs){
		txtCostBillNumber.setText((String)objs[0]);
    	txtCostBillName.setText((String)objs[1]);    	
    	txtAmount.setValue(objs[2]);
	}
	
	public void loadProgrammingContractInfo(ProgrammingContractInfo pcinfo){
		for(int i = 0; i < kdtEntrys.getRowCount(); i++) {
			//programming
			kdtEntrys.getCell(i,"programming").setValue(pcinfo);
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
	
	public void setOprtState(String oprtType) {
			super.setOprtState(oprtType);
			/*if(!STATUS_ADDNEW.equals(oprtType)) {
				prmtcurProject.setEnabled(false);
				
			}*/
	}
	
	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#loadFields()
	 */
	public void loadFields() {
		// TODO �Զ����ɷ������
		super.loadFields();
		if(editData.getContractBill()!=null){
			//setContractBillId(editData.getContractBill().toString()); bug....
			setContractBillId(editData.getContractBill().getId().toString());
			
			//�鿴��ʱ�����ۺ�ͬ����
			if(editData.getContractBill().isIsMeasureContract()){
				getUIContext().put("isMeasureSplit",Boolean.TRUE);
				//�ڶ��β�ֱ���ʱ����ʧeditData�е�isMeasureContract��Ϣ�������ְѺ�ͬ��дΪ�����۲�֣�����loadFieldsʱ�ֹ�д��
				this.editData.setBoolean("isMeasureContract", true);
			}
		}
		
		//reCalSplitScale(); // modified by zhaoqin for R130905-0187 on 2013/09/16
		
		setDisplay();
		
		try {
			updateEntryProgramming();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.finance.client.AbstractContractCostSplitEditUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO �Զ����ɷ������
		//return super.getSelectors();
		
		SelectorItemCollection sic=super.getSelectors();
		
		//sic.add(new SelectorItemInfo("paymentBill.contractBillId"));
		sic.add("state");
		sic.add(new SelectorItemInfo("contractBill.state"));
		sic.add(new SelectorItemInfo("contractBill.orgUnit.id"));
		sic.add(new SelectorItemInfo("contractBill.isMeasureContract"));
		return setSelectors(sic);
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#getSplitBillEntryClassName()
	 */
	protected String getSplitBillEntryClassName() {
		// TODO �Զ����ɷ������
		//return super.getSplitBillEntryClassName();
		
		return ContractCostSplitEntryInfo.class.getName();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.FDCSplitBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		// TODO �Զ����ɷ������
		super.initWorkButton();
		
		btnImpContrSplit.setVisible(false);
		actionImpContrSplit.setEnabled(true);
		btnAuditResult.setVisible(false);
		btnAuditResult.setEnabled(false);
		actionCopyLine.setVisible(false);
		actionCopyLine.setEnabled(false);
		actionProgrAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		menuItemProgrAcctSelect.setAccelerator(KeyStroke.getKeyStroke("ctrl shift P"));
		menuItemProgrAcctSelect.setText(menuItemProgrAcctSelect.getText().replaceAll("\\(P\\)", "")+"(P)");
		menuItemProgrAcctSelect.setMnemonic('P');
		
		
	}
	public void onShow() throws Exception {
		super.onShow();
		if (OprtState.VIEW.equals(getOprtState())) {
			actionProgrAcctSelect.setEnabled(false);
		}
		
		//���� ��ִͬ����Ϣ ��ť �Ͳ˵�Ϊ�ɼ�.
		this.btnViewContract.setVisible(true);
		this.btnViewContract.setEnabled(true);
		this.menuViewContract.setVisible(true);
		this.menuViewContract.setEnabled(true);
	}
	
	/**
	 * 
	 * description		��ִͬ����Ϣ		
	 * @author			��־�� <p>
	 * @createDate		2011-8-10<p>
	 *
	 * @version 		EAS 7.0
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractCostSplitEditUI#actionCostContractView_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionCostContractView_actionPerformed(ActionEvent e) throws Exception {
		try {
			if(null != this.editData.getContractBill().getId()){
				this.viewContract(this.editData.getContractBill().getId().toString());
			}
		} catch (Exception e2) {
			handUIExceptionAndAbort(e2);
		}
	}
	
	private void viewContract(String id) throws UIException {
		if (id == null)
			return;
		String editUIName = null;
		if (PayReqUtils.isContractBill(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractFullInfoUI .class.getName();

		} else if (PayReqUtils.isConWithoutTxt(id)) {
			editUIName = com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();

		}

		if (editUIName == null)
			return;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, id);
		// ����UI������ʾ
		IUIWindow windows = this.getUIWindow();
		String type = UIFactoryName.NEWTAB;
		if (windows instanceof UIModelDialog || windows == null) {
			type = UIFactoryName.MODEL;
		}
		IUIWindow contractUiWindow = UIFactory.createUIFactory(type).create(editUIName, uiContext, null, "FINDVIEW");
		if (contractUiWindow != null) {
			contractUiWindow.show();
		}
	}


	
    public void updateDetailTable(IObjectCollection entrys){
    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCSplitBillEntryInfo entry;
    	for(Iterator iter=entrys.iterator();iter.hasNext();){
    		entry=(FDCSplitBillEntryInfo)iter.next();
    		row=getDetailTable().addRow();
    		loadLineFields(getDetailTable(), row, entry);
    	}
    }
    
    public void updateAmountColumn(IObjectCollection entrys){
//    	getDetailTable().removeRows(false);
    	IRow row;
    	FDCSplitBillEntryInfo entry;
    	int rowIndex=0;
    	for(Iterator iter=entrys.iterator();iter.hasNext();rowIndex++){
    		entry=(FDCSplitBillEntryInfo)iter.next();
    		row=getDetailTable().getRow(rowIndex);
    		row.getCell("amount").setValue(entry.getAmount());
    		row.getCell("apportionValue").setValue(entry.getApportionValue());
    		row.getCell("apportionValueTotal").setValue(entry.getApportionValueTotal());
    		row.getCell("directAmountTotal").setValue(entry.getDirectAmountTotal());
    		row.getCell("directAmount").setValue(entry.getDirectAmount());
    		row.getCell("otherRatioTotal").setValue(entry.getOtherRatioTotal());
//    		row.getCell("otherRatio").setValue(entry.getOtherRatio());
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
    
    /**
	 * ��ͬ������������,�ﵽ��ͬ��ֲ��빤������������أ�ֻҪ���ź�ͬID��������������֣��Ժ���������մ�ʵ��
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
        		builder.appendSql("select top 1 fid from T_Con_ContractCostSplit where fcontractBillId=? and fstate<>?");
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
        		
        		logger.info("contractsplit costbill id="+costBillID);
        	}else{
        		logger.error("��������ȡ������ͬID��can't get contract id");
        	}
        }
	}
	
	protected void loadData() throws Exception {
		handleSplitWorkFlow();
		super.loadData();
	}
    protected boolean isMeasureContract() {
    	if(getUIContext().get("isMeasureSplit")!=null){
    		return true;
    	}
    	return false;
    }
    public KDTDefaultCellEditor getScaleCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(10);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.ONE_HUNDRED);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
    public void actionAcctSelect_actionPerformed(ActionEvent e)
	throws Exception {
//		if (isClearFlag) {
//			int ret = MsgBox.showConfirm2(this,
//					"ѡ��ɱ���Ŀ�����ԭ������ݣ��Ƿ�������ǣ���ղ������������񣬷������β���");
//			if (ret == MsgBox.YES) {
//				getDetailTable().removeRows(false);
//				super.actionAcctSelect_actionPerformed(arg0);
//				isClearFlag = false;
//			} else if (ret == MsgBox.CANCEL) {
//				this.abort();
//			}
//		} else {
			super.actionAcctSelect_actionPerformed(e);
//			loadProgrammingContractInfo(pcinfo);
//		}
	}
    
    protected IRow addEntry(IObjectValue detailData) {
    	IRow row = kdtEntrys.addRow();
        ((FDCSplitBillEntryInfo)detailData).setSeq(row.getRowIndex()+1);
        detailData.put("programmings",pcinfo);
        loadLineFields(kdtEntrys, row, detailData);
        afterAddLine(kdtEntrys, detailData);
        return row;
    }
    

    
    protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
    	super.kdtEntrys_editStopped(e);
    	final IRow row = kdtEntrys.getRow(e.getRowIndex());
		if (e.getColIndex()==kdtEntrys.getColumnIndex("programming")){
			
		}
    }
    
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if(UIRuleUtil.isNull(row.getCell("costAccount.curProject.number").getValue()))
				continue;
			if(isWholeProject && UIRuleUtil.isNull(row.getCell("programming").getValue())){
				FDCMsgBox.showWarning("��Լ�滮����Ϊ�գ�");
				this.kdtEntrys.getEditManager().editCellAt(i,this.kdtEntrys.getColumnIndex("programming"));
				SysUtil.abort();
			}
    	}
    }
    
    /**
	 * ������R130905-0187
	 *	���Բ��裺
	 *	1�����׷�XB01����¼��A��ͬ�����Ϊ10000Ԫ��
	 *	2����ͬ��֣�������ȫ��֣�
	 *	3��¼�벹���ͬA_��1�����Ϊ10000Ԫ���Ƕ������㣻
	 *	4����ͬ��ֽ��棬A��ͬ״̬Ϊ���ֲ�֣�ѡ��A������֣����ֲ�ֱ���Ϊ100%�������ȴֻ��10000��
	 *	�����Ľ����
	 *	��ֱ�����ʾΪ��50%  -> ��ֱ������ֲ��䣬2013/10/08
	 * @Author��zhaoqin
	 * @CreateTime��2013-9-16
	 */
    private void reCalSplitScale() {
    	if (null == editData.getSplitState() || editData.getSplitState() != CostSplitStateEnum.PARTSPLIT)
			return;
		if (FDCHelper.compareTo(txtAmount.getBigDecimalValue(), FDCHelper.ZERO) == 0)
			return;
  
		ContractCostSplitEntryCollection entrys = editData.getEntrys();
		if (null == entrys || entrys.size() == 0)
			return;
		// ��ǰ�ܲ�ֱ���
		BigDecimal cursplitScale = (txtSplitedAmount.getBigDecimalValue().divide(txtAmount.getBigDecimalValue(), 2, 5))
				.multiply(FDCHelper.ONE_HUNDRED);
		// ԭ�ܲ�ֱ���
		BigDecimal oldsplitScale = FDCHelper.ZERO;
		for (int i = 0; i < entrys.size(); i++) {
			ContractCostSplitEntryInfo entry = entrys.get(i);
			if (null == entry.getSplitScale() || FDCHelper.ZERO.compareTo(entry.getSplitScale()) == 0)
				continue;
			oldsplitScale = oldsplitScale.add(entry.getSplitScale());
		}
		oldsplitScale.setScale(2, 5);
		if (0 == oldsplitScale.compareTo(cursplitScale))
			return;
		
		getDetailTable().removeRows();
		// ��ͬ������۸ı��� -> �޸Ĳ�ֽ��,��ֱ�������
		for (int i = 0; i < entrys.size(); i++) {
			ContractCostSplitEntryInfo entry = entrys.get(i);
			if (null != entry.getSplitScale() && FDCHelper.ZERO.compareTo(entry.getSplitScale()) != 0) {
				BigDecimal newAmount = txtAmount.getBigDecimalValue().multiply(entry.getSplitScale().divide(FDCHelper.ONE_HUNDRED, 8, 5));
				entry.setAmount(newAmount);
				
				// ������ڲ�Ʒ���
				// ...........
			}
			addEntry(entry);
		}
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