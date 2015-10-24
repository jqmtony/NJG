/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.util.FDCSplitBillUtil;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCAutoSplitHelper;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCostSplit;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.ConChangeSplitCollection;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitFactory;
import com.kingdee.eas.fdc.contract.ConChangeSplitInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.SettlementCostSplitCollection;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitInfo;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContracCost;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFacadeFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.client.PaymentSplitListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillEditUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadConfirmBillListUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadSplitListUI;
import com.kingdee.eas.fm.common.client.AbstractHidedMenuItem;
import com.kingdee.eas.framework.AbstractBillEntryBaseInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * FDC ��ֻ���
 */
public abstract class FDCSplitBillEditUI extends AbstractFDCSplitBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCSplitBillEditUI.class);
    
    private String contractBillId=null;
    
    private CostSplitBillTypeEnum splitBillType=null;
    
    protected FDCCostSplit fdcCostSplit=new FDCCostSplit(null);
    
    private int groupIndex=0;
    private boolean hasRemove=false;
    private IUIWindow acctUI=null;
    private boolean isLocked = false;
    private List oldCostAccountLongNumber = new ArrayList();
	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";

	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
    
	/** һ�廯����ֵ */
	protected Map initParam = null;
	private Map parentMap = new HashMap();

	/**
	 * kdtEntrys ��¼���ϵĲ�����ݼ���ʹ���ӳټ��ؽ����������
	 */
	private FDCSplitBillEntryCollection entrys = null;
	
	//��¼bosid-->��¼����...added by ken_liu..���ڵ��߼����û����entrys,������ٱ༭��������⣬�����map�Ա��ڱ༭ʱ�һػ��������޸�
	private HashMap entrysMap = new HashMap(); 
	
    public FDCSplitBillEditUI() throws Exception
    {
        super();
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	checkbeforeSave();
    	dealWithEmptyDataBeforeSave();
    	boolean isAddNew=this.getOprtState()!=null&&this.getOprtState().equals(OprtState.ADDNEW);
    	//�����¼����˳��
    	FDCSplitBillEntryInfo entry=null;    	
    	FDCSplitBillEntryInfo[] entrys= new FDCSplitBillEntryInfo[kdtEntrys.getRowCount()] ;
        for(int i=0; i<kdtEntrys.getRowCount(); i++){
    		entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
    		entrys[i]=entry;
        	entry.setIndex(i+1);
			for (int j = i + 1; j < kdtEntrys.getRowCount(); j++) {
				FDCSplitBillEntryInfo subEntry = (FDCSplitBillEntryInfo) kdtEntrys.getRow(j).getUserObject();;
				if (entry.getLevel() > subEntry.getLevel() - 1) {
					break;
				}
				if (entry.getLevel() == subEntry.getLevel() - 1) {
					if(entry.getApportionType()!=null&&entry.getApportionType().getId()!=null){
						subEntry.setIdxApportionId(entry.getApportionType().getId().toString());
					}
				}
			}
        }    	
        
        if (editData instanceof ContractCostSplitInfo 
				|| editData instanceof ConChangeSplitInfo 
				|| editData instanceof SettlementCostSplitInfo
			) {
			FDCSplitBillUtil.checkFDCSplitBillData(editData, getEntrys(), true);
		}
        if (this.toString().indexOf("PaymentSplitWithoutTxtConEditUI") > 0) {
			FDCSplitBillUtil.checkFDCSplitBillData(editData, getEntrys(), false);
		}
        
        editData.setHasInitIdx(true);
//    	��ʽ������
        Timestamp createTime = editData.getCreateTime();
		if(createTime!=null){
//        	editData.setCreateTime(DateTimeUtils.format(editData.getCreateTime(), "yyyy-MM-dd "));
        	createTime.setNanos(0);
        	editData.setCreateTime(createTime);
        }

//        if(editData.getAuditTime()!=null){
//        	editData.getAuditTime().set;
//        }
        	
        super.actionSave_actionPerformed(e);
        actionRemove.setEnabled(true);
        if(isAddNew&&editData.getId()!=null){
        	//�ͷ�����������Ӷ�����
        	String costBillID = (String)getUIContext().get("costBillID");
        	if(costBillID!=null){
        		java.util.List list=new ArrayList();
        		list.add(costBillID);
        		try {
					FDCClientUtils.releaseDataObjectLock(this, list);
					String id=editData.getId().toString();
					list.clear();
					list.add(id);
					FDCClientUtils.requestDataObjectLock(this, list, OprtState.EDIT);
				} catch (Throwable e1) {
					// @AbortException
					logger.error(e1.getMessage(), e1);
				}
        	}
        }
    }

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkbeforeSave();
		dealWithEmptyDataBeforeSave();
		boolean isAddNew = this.getOprtState() != null && this.getOprtState().equals(OprtState.ADDNEW);
		//�����¼����˳��
		FDCSplitBillEntryInfo entry = null;
		FDCSplitBillEntryInfo[] entrys = new FDCSplitBillEntryInfo[kdtEntrys.getRowCount()];
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			entry = (FDCSplitBillEntryInfo) kdtEntrys.getRow(i).getUserObject();
			entrys[i] = entry;
			entry.setIndex(i + 1);
			for (int j = i + 1; j < kdtEntrys.getRowCount(); j++) {
				FDCSplitBillEntryInfo subEntry = (FDCSplitBillEntryInfo) kdtEntrys.getRow(j).getUserObject();
				;
				if (entry.getLevel() > subEntry.getLevel() - 1) {
					break;
				}
				if (entry.getLevel() == subEntry.getLevel() - 1) {
					if (entry.getApportionType() != null && entry.getApportionType().getId() != null) {
						subEntry.setIdxApportionId(entry.getApportionType().getId().toString());
					}
				}
			}
		}

		if (editData instanceof ContractCostSplitInfo || editData instanceof ConChangeSplitInfo
				|| editData instanceof SettlementCostSplitInfo) {
			FDCSplitBillUtil.checkFDCSplitBillData(editData, getEntrys(), true);
		}
		if (this.toString().indexOf("PaymentSplitWithoutTxtConEditUI") > 0) {
			FDCSplitBillUtil.checkFDCSplitBillData(editData, getEntrys(), false);
		}

		editData.setHasInitIdx(true);
		//    	��ʽ������
		Timestamp createTime = editData.getCreateTime();
		if (createTime != null) {
			//        	editData.setCreateTime(DateTimeUtils.format(editData.getCreateTime(), "yyyy-MM-dd "));
			createTime.setNanos(0);
			editData.setCreateTime(createTime);
		}

		//        if(editData.getAuditTime()!=null){
		//        	editData.getAuditTime().set;
		//        }

		super.actionSubmit_actionPerformed(e);
		actionRemove.setEnabled(true);
		if (isAddNew && editData.getId() != null) {
			//�ͷ�����������Ӷ�����
			String costBillID = (String) getUIContext().get("costBillID");
			if (costBillID != null) {
				java.util.List list = new ArrayList();
				list.add(costBillID);
				try {
					FDCClientUtils.releaseDataObjectLock(this, list);
					String id = editData.getId().toString();
					list.clear();
					list.add(id);
					FDCClientUtils.requestDataObjectLock(this, list, OprtState.EDIT);
				} catch (Throwable e1) {
					// @AbortException
					logger.error(e1.getMessage(), e1);
				}
			}
		}
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	//�򵥵�ɾ��
//        super.actionRemove_actionPerformed(e);
//        if(true) return;
    	hasRemove=false;
        if(!confirmRemove()){
      	  return;
        }
        String tempState = this.getOprtState();
        this.setOprtState("REMOVE");
        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;
        getUIContext().put("CURRENT.VO",null) ;
        setDataObject(val) ;

        try
        {
        	IObjectPK pk = new ObjectUuidPK(this.editData.getId());
            this.getBizInterface().delete(pk);
            hasRemove=true;
        }
        finally
        {
            //�ָ�״̬��
            this.setOprtState(tempState);
        }
        setSave(true);
        setSaved(true);
//        actionExitCurrent_actionPerformed(null);

    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	if(!actionRemoveLine.isEnabled()||!actionRemoveLine.isVisible()) return;
        //if ((kdtEntrys.getSelectManager().size() == 0) || isTableColumnSelected(kdtEntrys))
    	if ((kdtEntrys.getSelectManager().size() == 0))
        {
            FDCMsgBox.showInfo(this, EASResource
                    .getString(FrameWorkClientUtils.strResource
                            + "Msg_NoneEntry"));

            //FDCMsgBox.showInfo(this,"û��ѡ�з�¼���޷�ɾ����");
            return;
        }

        //[begin]����ɾ����¼����ʾ����
        if(confirmRemove())
        {
            int top = kdtEntrys.getSelectManager().get().getBeginRow();
            int bottom = kdtEntrys.getSelectManager().get().getEndRow();
            
            int idx=0;
            int idx1,idx2;
            
            boolean isTrue=false;
            FDCSplitBillEntryInfo entry=null;
            
            for(int i =bottom ;i>=top ;i--)
            {
            	idx=i;
            	
            	idx1=idx;
            	idx2=idx;
            	
            	//�������һ��
            	isTrue=false;
            	for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
            		entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			idx2=j-1;
            			isTrue=true;
            			break;
            		}
            	}
            	if(!isTrue){
            		idx2=kdtEntrys.getRowCount()-1;
            	}
            	if(idx2<idx){
            		idx2=idx;
            	}
            	
            	//�����һ����ǰɾ����ֱ��Level=0
            	for(int j=idx2; j>=0; j--){
            		idx1=j;
            		
            		entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
            		if(entry.getLevel()==0){
            			removeEntry(j);
            			break;
            		}else{
            			removeEntry(j);
            		}
            	}
            	
            	//i=idx1-1;
            	i=idx1;
            }            
        }

        
        if(kdtEntrys.getRowCount()>0){
        	calcAmount(0);
        }else{
        	txtSplitedAmount.setValue(FDCHelper.ZERO);        	
        }        	
        

		//������		jelon 12/28/2006
		int idx=0;
		FDCSplitBillEntryInfo entry=null;
		for(int i=0; i<kdtEntrys.getRowCount(); i++){	
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionAudit_actionPerformed(e);
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SAVED, "cantAudit");
		String id = getSelectBOID();
		if (id != null) {
			((IFDCSplitBill) getBizInterface()).audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		editData.setState(FDCBillStateEnum.AUDITTED);
		actionAudit.setEnabled(false);
		actionUnAudit.setEnabled(true);
		actionAudit.setVisible(true);
		actionUnAudit.setVisible(true);
		bizPromptAuditor.setValue(SysContext.getSysContext().getCurrentUserInfo());
		dateAuditTime.setValue(DateTimeUtils.truncateDate(new Date()));
		editData.setAuditor(SysContext.getSysContext().getCurrentUserInfo());
		editData.setAuditTime(DateTimeUtils.truncateDate(new Date()));
    }

	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		//��鵥���Ƿ��ڹ�������
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

	

		 if (getOprtState().equals(STATUS_EDIT)) {
			String warn = null;
			if (state.equals(FDCBillStateEnum.AUDITTED)) {
				warn = CANTUNAUDITEDITSTATE;
			} else {
				warn = CANTAUDITEDITSTATE;
			}
			MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			SysUtil.abort();
		}
	}
    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, "cantUnAudit");
		String id = getSelectBOID();
		if (id != null) {
			((IFDCSplitBill) getBizInterface()).unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		editData.setState(FDCBillStateEnum.SAVED);
		actionUnAudit.setEnabled(false);
		actionAudit.setEnabled(true);
		actionUnAudit.setVisible(true);
		actionAudit.setVisible(true);
		bizPromptAuditor.setValue(null);
		dateAuditTime.setValue(null);
		editData.setAuditor(null);
		editData.setAuditTime(null);
/*		loadFields();
		this.storeFields();
		this.initOldData(this.editData);*/
    }

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	
	
	protected void setSplitState(){        

    	BigDecimal amount;    	  	
    	if(txtAmount.getValue()==null){
    		amount=FDCHelper.ZERO;
    	}else{
    		amount=new BigDecimal(txtAmount.getValue().toString());
    	}
    	
    	BigDecimal amtSplit;    	
    	if(txtSplitedAmount.getValue()==null){
    		amtSplit=FDCHelper.ZERO;
    	}else{
    		amtSplit=new BigDecimal(txtSplitedAmount.getValue().toString());
    	}
    	amtSplit = amtSplit.setScale(2,BigDecimal.ROUND_HALF_UP);
    	amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    	if(amtSplit.compareTo(amount)==0){
    		editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
    	}else{
        	if (amtSplit.compareTo(FDCHelper.ZERO)==0){
        		editData.setSplitState(CostSplitStateEnum.NOSPLIT);
        	}else{
        		editData.setSplitState(CostSplitStateEnum.PARTSPLIT);
        	}
    	}
		
	}

	public void actionAcctSelect_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionAcctSelect_actionPerformed(arg0);
		CostAccountCollection accts=null;
		
		//ѡ���Ŀ
		/*UIContext uiContext = new UIContext(this); 
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
				ContractCostSplitAcctUI.class.getName(),	uiContext, null , null);       
		uiWin.show();
						
		if (((ContractCostSplitAcctUI) uiWin.getUIObject()).isOk()) {	
			accts=((ContractCostSplitAcctUI) uiWin.getUIObject()).getData();
		}else{
			return;
		}*/
		

/*		UIContext uiContext = new UIContext(this); 
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
				CostSplitAcctUI.class.getName(),	uiContext, null , null);       
		uiWin.show();*/
		if(acctUI==null){
			Map map = getUIContext();
			//��UIContext�л�õ�ǰID
			String costBillId = (String)map.get("ID");
			CurProjectInfo curProjectInfo = null;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("curProject.id");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",costBillId));
			view.setFilter(filter);
			//���Ϊ��ֺ�ͬID
			ContractCostSplitCollection coll = ContractCostSplitFactory
				.getRemoteInstance().getContractCostSplitCollection(view);
			CurProjectInfo curProject = null;
			if(coll.size()==1){
				ContractCostSplitInfo splitInfo = coll.get(0);
				curProject = splitInfo.getCurProject();
			}
			//���Ϊ���ı���ͬ������ID
			if(coll.size()==0){
				PaymentSplitCollection colls = PaymentSplitFactory
					.getRemoteInstance().getPaymentSplitCollection(view);
				if(colls.size()==1){
					PaymentSplitInfo withoutInfo = colls.get(0);
					curProject = withoutInfo.getCurProject();
				}else if(colls.size()==0){
					//���Ϊ��ͬ������ID
					ConChangeSplitCollection collc = ConChangeSplitFactory
						.getRemoteInstance().getConChangeSplitCollection(view);
					if(collc.size()==1){
						ConChangeSplitInfo changeInfo = collc.get(0);
						curProject = changeInfo.getCurProject();
					}
				}
			}

			/* modified by zhaoqin for R130927-0088 on 2013/12/23 start */
			if(this.editData.getBOSType().equals(new SettlementCostSplitInfo().getBOSType())) {
				curProject = (CurProjectInfo)map.get("curProject");
			}
			/* modified by zhaoqin for R130927-0088 on 2013/12/23 end */			
			
			//��ñ���ͬ������ڹ�����Ϣ������UIContext��������ѡ���Ŀ
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",curProject);
			if (contractBillId != null) {
				uiContext.put("contractBillId", contractBillId);
			}
			/************* ���Ϻ�ͬ���²�� *************/
			uiContext.put("txtCostBillNumber", txtCostBillNumber.getText());
			/************* ���Ϻ�ͬ���²�� *************/
			// ����ģʽ���������븶���ֲ���ʾ�ɲ��ѡ��
			if (isFinacial()) {
				uiContext.put("isFinacial", Boolean.TRUE);
			}
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)map.get("node");
			//���Ϊ��������������ֱ�Ӵ�CURRENT.VO�л��CurProjectInfo
			if(costBillId==null){
				if(map.get("CURRENT.VO") instanceof ConChangeSplitInfo){
					ConChangeSplitInfo info = (ConChangeSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof ContractCostSplitInfo){
					ContractCostSplitInfo info = (ContractCostSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				if(map.get("CURRENT.VO") instanceof PaymentSplitInfo){
					PaymentSplitInfo info = (PaymentSplitInfo)map.get("CURRENT.VO");
					CurProjectInfo curProj = info.getCurProject();
					uiContext.put("curProject",curProj);
				}
				
			}
//			//��uiContext�л����FDCSplitListUI�б���Ľڵ���Ϣ
//			if(node!=null && (node.getUserObject() instanceof OrgStructureInfo)){
//				OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
//			if(node!=null && node.getUserObject() instanceof CurProjectInfo){
//				CurProjectInfo info = (CurProjectInfo)node.getUserObject();
//				uiContext.put("curProject",info);
//			}
			
			
//			uiContext.put("curProject",editData.getCurProject());
			uiContext.put("isMeasureSplit", isMeasureContract()?Boolean.TRUE:null);
			acctUI=UIFactory.createUIFactory(UIFactoryName.MODEL).	create(
					com.kingdee.eas.fdc.basedata.client.CostSplitAcctUI.class.getName(),	uiContext, null , null);       
		}else{
			((CostSplitAcctUI) acctUI.getUIObject()).actionNoneSelect_actionPerformed(null);
		}
		acctUI.show();
		IUIWindow uiWin=acctUI;
		
		if (((CostSplitAcctUI) uiWin.getUIObject()).isOk()) {	
			accts=((CostSplitAcctUI) uiWin.getUIObject()).getData();
			parentMap = ((CostSplitAcctUI) uiWin.getUIObject()).getParentIdMap();
		}else{
			return;
		}
		

		CostAccountInfo acct=null;
		
		FDCSplitBillEntryInfo entry=null;
		IRow row=null;
		boolean isExist=false;
		// �ڲ���һ�廯����ģʽ�����˲��� ɾ������ϸ��Ŀ
		removeParentCostAccount(accts);
		
		for(Iterator iter=accts.iterator(); iter.hasNext();){
			acct = (CostAccountInfo)iter.next();
			
			//�жϿ�Ŀ�Ƿ����
			isExist=false;
			for(int i=0; i<kdtEntrys.getRowCount(); i++){			
				entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
								
				//����ѡ����������ַ������Ѵ��ڵĿ�Ŀ		jelon 12/6/06
				//if(entry.getCostAccount().getId().equals(acct.getId())){
				if(entry.getLevel()==0 && entry.getCostAccount().getId().equals(acct.getId())){
					isExist=true;
					break;
				}
			}
			if(!isExist){
				
				//entry=new FDCSplitBillEntryInfo();
				entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);
				entry.setCostAccount(acct);  
				entry.setLevel(0);
				entry.setIsLeaf(true);		//Jelon 	Dec 11, 2006
				entry.setAmount(FDCHelper.ZERO);
				
				//������	jelon 12/27/2006
				groupIndex++;
				entry.setIndex(groupIndex);
				
				row=addEntry(entry);
				setDisplay(row.getRowIndex());

			}				
		}
		setMenuSplitState();
		// ��ֱ�ӽ��ı���ɫ���óɰ�ɫ
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName())
				|| className.equals(PaymentSplitListUI.class.getName())) {
			for (int k = kdtEntrys.getRowCount() - 1; k > 0; k--) {
				if (kdtEntrys.getRow(k) != null && kdtEntrys.getRow(k).getCell("directAmt") != null)
				kdtEntrys.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
				if (kdtEntrys.getRow(k) != null
						&& kdtEntrys.getRow(k).getCell("directPayedAmt") != null)
				kdtEntrys.getRow(k).getCell("directPayedAmt").getStyleAttributes().setBackground(
						new Color(0xffffff));
			}
		} else if (className.equals(WorkLoadConfirmBillListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			for (int k = kdtEntrys.getRowCount() - 1; k > 0; k--) {
				if (kdtEntrys.getRow(k) != null && kdtEntrys.getRow(k).getCell("directAmt") != null)
				kdtEntrys.getRow(k).getCell("directAmt").getStyleAttributes().setBackground(new Color(0xffffff));
			}
		}
		updateEntryProgramming();
	}
    public void removeParentCostAccount(CostAccountCollection accts) {
    	try {
    		String tmpNumber = null;
			String curNumber = null;
			// �����ò���һ�廯����ģʽ�������ж�
			if (FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId()
					.toString())) {
				String className = getUIContext().get("Owner").getClass().getName();
				if (className.equals(WorkLoadSplitListUI.class.getName())
						|| className.equals(PaymentSplitListUI.class.getName())
						|| className.equals(WorkLoadConfirmBillListUI.class.getName()) || className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
					if (kdtEntrys.getRowCount() > 0) {
						for (int i = kdtEntrys.getRowCount() - 1; i > 0; i--) {
							Object obj = kdtEntrys.getRow(i).getUserObject();
							if ((obj instanceof PaymentSplitEntryInfo)
									&& ((PaymentSplitEntryInfo) obj).getLevel() > -1) {
								PaymentSplitEntryInfo entry = (PaymentSplitEntryInfo) obj;
								tmpNumber = entry.getCostAccount().getLongNumber();
								for (int j = 0; j < accts.size(); j++) {
									curNumber = accts.get(j).getLongNumber();
									if (!entry.getCostAccount().isIsLeaf()) {
										if (curNumber.startsWith(tmpNumber)) {
											kdtEntrys.removeRow(i);
										}
									}
								}
							}
						}
					}
				}
			}
    	} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
	}
    /**
	 * �����Զ�ƥ���ֺͰ�������ְ�ť״̬
	 * 
	 * @description
	 * @author ������
	 * @createDate 2011-9-28
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void setMenuSplitState() {
		// �µĳɱ���Ŀ���뼯��
		List newCostAccountLongNumber = new ArrayList();
		// �ж��Ƿ񹤳�����ֺ͸����ִ�
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillListUI.class.getName()) || className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			// �������γɱ������µĳɱ���Ŀ����
			String longNumber = null;
			PaymentSplitEntryInfo info = null;
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				info = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
				// �жϵ�ǰ�Ƿ�ɱ���Ŀ
				if (info.getCostAccount() instanceof CostAccountInfo) {
					newCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
				}
			}
			// �жϾɳɱ���Ŀ������³ɱ���Ŀ�����Ƿ�һ��
			if (!oldCostAccountLongNumber.containsAll(newCostAccountLongNumber)) {
				this.getUIContext().put("isCanEnable", Boolean.FALSE);
				PaymentSplitEntryInfo tmpInfo = null;
				// �����µı�������ж��Ƿ�ȫ��������ϸ��Ŀ
				for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
					tmpInfo = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					// �жϵ�ǰ���Ƿ�ɱ���Ŀ
					if (tmpInfo.getCostAccount() instanceof CostAccountInfo) {
						// �ж��Ƿ�����ϸ�ɱ���Ŀ��������Ǿ����ð�ť�ɱ༭״̬����������ֺ͸�����
						if (!tmpInfo.getCostAccount().isIsLeaf()) {
							this.getUIContext().put("isCanEnable", Boolean.FALSE);
							return;
						} 
					}
				}
			}
			
		}

	}
	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitBotUp_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitBotUp_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSplitBotUp_actionPerformed(arg0);
		splitCost(CostSplitTypeEnum.BOTUPSPLIT);
		updateEntryProgramming();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitProd_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitProd_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSplitProd_actionPerformed(arg0);
		

		splitCost(CostSplitTypeEnum.PRODSPLIT);
		updateEntryProgramming();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitProj_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitProj_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSplitProj_actionPerformed(arg0);
		
		splitCost(CostSplitTypeEnum.PROJSPLIT);
		updateEntryProgramming();
	}
	
	
	/* ���� Javadoc��
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#initWorkButton()
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		
		actionAcctSelect.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_evaluatecortrol"));
		actionSplitProj.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showparent"));
		actionSplitBotUp.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_showsubflow"));
		actionSplitProd.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_citetree"));
		actionImpContrSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_collect"));
		actionAcctSelect.setEnabled(true);
		actionSplitProj.setEnabled(true);
		actionSplitProd.setEnabled(true);
		actionSplitBotUp.setEnabled(true);
		actionImpContrSplit.setEnabled(false);//ֻ�б��������õ�
		actionImpContrSplit.setVisible(false);

		
		//btnRemove.setVisible(false);
		//�Ѹ����������ص�--��������
//		actionAttachment.setEnabled(false);
//		actionAttachment.setVisible(false);
		menuItemAcctSelect.setAccelerator(KeyStroke.getKeyStroke("ctrl shift C"));
		menuItemAcctSelect.setText(menuItemAcctSelect.getText().replaceAll("\\(C\\)", "")+"(C)");
		menuItemAcctSelect.setMnemonic('C');
		
		menuItemSplitProj.setAccelerator(KeyStroke.getKeyStroke("ctrl shift A"));
		menuItemSplitProj.setText(menuItemSplitProj.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemSplitProj.setMnemonic('A');
		
		
		menuItemSplitBotUp.setAccelerator(KeyStroke.getKeyStroke("ctrl shift L"));
		menuItemSplitBotUp.setText(menuItemSplitBotUp.getText().replaceAll("\\(L\\)", "")+"(L)");
		menuItemSplitBotUp.setMnemonic('L');
		
		menuItemSplitProd.setAccelerator(KeyStroke.getKeyStroke("alt shift P"));
		menuItemSplitProd.setText(menuItemSplitProd.getText().replaceAll("\\(P\\)", "")+"(P)");
		menuItemSplitProd.setMnemonic('P');
		
		menuItemImpContrSplit.setAccelerator(KeyStroke.getKeyStroke("alt shift I"));
		menuItemImpContrSplit.setText(menuItemImpContrSplit.getText().replaceAll("\\(I\\)", "")+"(I)");
		menuItemImpContrSplit.setMnemonic('I');
		actionAddNew.setEnabled(false);
		actionInsertLine.setEnabled(false);
		actionAddLine.setEnabled(false);		
	}

    protected IRow addEntry(IObjectValue detailData)
    {
    	/*
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table);
        */
    	
        IRow row = kdtEntrys.addRow();
        ((FDCSplitBillEntryInfo)detailData).setSeq(row.getRowIndex()+1);
        loadLineFields(kdtEntrys, row, detailData);
        afterAddLine(kdtEntrys, detailData);
        
        return row;
    }
    
    
    protected IRow insertEntry(int rowIndex, IObjectValue detailData)
    {
    	/*
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table);
        */    	
        IRow row = null;
        
        /*
        if (table.getSelectManager().size() > 0)
        {
            int top = table.getSelectManager().get().getTop();

            if (isTableColumnSelected(table))
            {
                row = table.addRow();
            }
            else
            {
                row = table.addRow(top);
            }
        }
        else
        {
            row = table.addRow();
        }
        */        
        row = kdtEntrys.addRow(rowIndex);

        loadLineFields(kdtEntrys, row, detailData);
        //afterInsertLine(table, detailData);
        
        return row;
    }

    protected void removeEntry(int idxRow)
    {    	
        IObjectValue detailData = (IObjectValue) kdtEntrys.getRow(idxRow).getUserObject();
        kdtEntrys.removeRow(idxRow);
        
        IObjectCollection collection = (IObjectCollection) kdtEntrys.getUserObject();
        if (collection == null)
        {
            //logger.error("collection not be binded to table");
        	return;
        }
        else
        {
            if( detailData != null ) {
                collection.removeObject(detailData);
            }
        }
        
    }

	/**
	 * ���������÷�̯��׼��ȫ����
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    protected void setDisplay(){
    	FDCSplitBillEntryInfo entry=null;
    	IRow row=null;
    	initDirectMap.clear();
		for(int i=0; i<kdtEntrys.getRowCount(); i++){	
			row=kdtEntrys.getRow(i);
			entry = (FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()==0){
				setOneTreeDisplay(i);
				//�����ͬ��ֱ���ʱ�����ͷ�Ѳ��
				calcAmount(i);
			}
		}
		initDirectAssign();
    }
    
    private void setDisplay(int rowIndex){
    	initDirectMap.clear();
    	setOneTreeDisplay(rowIndex);
    	initDirectAssign();
    }

    private void setOneTreeDisplay(int rowIndex){
    	FDCSplitBillEntryInfo topEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();    	
    	int topLevel=topEntry.getLevel();		
        CostAccountInfo topAcct=topEntry.getCostAccount();

        FDCSplitBillEntryInfo entry=null;
        IRow row=null;
        ICell cell=null;
        String display=null;
        int level=0;
        
        CostAccountInfo acct=null;
        CurProjectInfo proj=null;
        
        

    	NodeClickListener nodeClickListener = new NodeClickListener(){
    		public void doClick(CellTreeNode source, ICell cell, int type)	{
    			//��Ŀչ������Ʒչ��Ӧ�ý������� by sxhong 2009/02/05
    			if(source!=null&&!source.isCollapse()&&source.isHasChildren()){
    				//�����¼����е�+���-
    				int level=source.getTreeLevel();
    				for(int i=cell.getRowIndex()+1;i<getDetailTable().getRowCount();i++){
    					if(cell.getColumnIndex()==getDetailTable().getColumnIndex("costAccount.curProject.name")){
    						ICell cell2 = getDetailTable().getCell(i, "costAccount.curProject.name");
    						if(cell2.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell2.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    						ICell cell3 = getDetailTable().getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							node.setCollapse(false);
    						}
    					}else if(cell.getColumnIndex()==getDetailTable().getColumnIndex("costAccount.name")){
    						ICell cell3 = getDetailTable().getCell(i, "costAccount.name");
    						if(cell3.getValue() instanceof CellTreeNode){
    							CellTreeNode node=(CellTreeNode)cell3.getValue();
    							if(node.getTreeLevel()<=level){
    								return;
    							}
    							node.setCollapse(false);
    						}
    					}
    				}
    			}
     		}
    	};
        
        for(int i=rowIndex; i<kdtEntrys.getRowCount(); i++){
        	row=kdtEntrys.getRow(i);   
			entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			
			row.getCell("directAmount").setValue(entry.getDirectAmount());
			
			level=entry.getLevel();
			
			acct=entry.getCostAccount();
			if(acct==null){
				proj=null;
			}else{
				proj=acct.getCurProject();
			}
				
			if(level>=topLevel){
				if(level==topLevel && i!=rowIndex){
					//��һ��������
					break;	
				}
				
				//���롢����
				if(entry.getCostAccount().getCurProject()!=null){
					//����
					row.getCell("costAccount.curProject.number").setValue(
							entry.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
					row.getCell("costAccount.number").setValue(
							entry.getCostAccount().getLongNumber().replace('!','.'));

					//����
					if(level==0){
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getDisplayName().replace('_','\\'));
						
					}else if(entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT && entry.isIsLeaf()){
						//��Ʒ�����ϸ
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						row.getCell("costAccount.name").setValue("");
						
					}else if(entry.isIsAddlAccount()){
						//���ӿ�Ŀ��ֱ�ӷ���
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						//row.getCell("costAccount.number").setValue(entry.getCostAccount().getLongNumber());
						row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());
						
					}else{
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getName());
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getName());	
					}		
					
					
					//��������
					if(level>=topLevel){
						CellTreeNode node = new CellTreeNode();
						node.addClickListener(nodeClickListener);			
						cell=row.getCell("costAccount.curProject.name");			
						// �ڵ��ֵ
						node.setValue(cell.getValue());
						// �Ƿ����ӽڵ�
						//if(entry.getCostAccount().getLongNumber().equals(topAcct.getLongNumber()) && !entry.getCostAccount().getCurProject().isIsLeaf()){
						/*if(entry.getCostAccount().getLongNumber().replace('!','.').equals(topAcct.getLongNumber().replace('!','.')) 
								&& !entry.getCostAccount().getCurProject().isIsLeaf()
								&& !isProdSplitLeaf(entry)){*/
						/*
						 * ���������Ƿ��к��ӽڵ� by 29 // if (!entry.isIsLeaf() // &&
						 * !entry.getCostAccount().getCurProject().isIsLeaf() //
						 * && //
						 * entry.getCostAccount().getLongNumber().replace('!',
						 * // '.').equals( //
						 * topAcct.getLongNumber().replace('!', '.'))) { //
						 * node.setHasChildren(true); // } else { // //
						 * node.setHasChildren(false); // }
						 */
						
						//node.setHasChildren(!entry.isIsAddlAccount());
						// �ڵ��������
						node.setTreeLevel(entry.getLevel());
						cell.getStyleAttributes().setLocked(false);
						cell.setValue(node);
						
						if(level!=topLevel){
							node = new CellTreeNode();
							node.addClickListener(nodeClickListener);			
							cell=row.getCell("costAccount.name");			
							// �ڵ��ֵ
							node.setValue(cell.getValue());
							/*
							 * ���������Ƿ��к��ӽڵ� by 29// �Ƿ����ӽڵ� //
							 * node.setHasChildren(!entry.getCostAccount(). //
							 * isIsLeaf() // || (!entry.isIsLeaf() && //
							 * entry.getCostAccount().isIsLeaf() && //
							 * entry.getSplitType()!=null && //
							 * entry.getSplitType() //
							 * .equals(CostSplitTypeEnum.PRODSPLIT)) );
							 */
							// �ڵ��������
							//node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel()+1);	
							}else{
								node.setTreeLevel(entry.getCostAccount().getLevel()-topAcct.getLevel());	
							}
											
							cell.getStyleAttributes().setLocked(false);
							cell.setValue(node);							
						}
						//end
					}
				}
								
								
				//��ɫ
				if(level==0){
					row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
					row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));					
					row.getCell("splitScale").getStyleAttributes().setBackground(new Color(0xFFFFFF));
				}else{
					if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT) && entry.getProduct()==null){
						row.getStyleAttributes().setBackground(new Color(0xF5F5E6));
					}else{
						row.getStyleAttributes().setBackground(new Color(0xE8E8E3));
					}					
					row.getCell("amount").getStyleAttributes().setLocked(true);
					//�ǿ�Ŀ�в��ܱ༭ by hpw 2010-06-25
					row.getCell("splitScale").getStyleAttributes().setLocked(true);
					
					//���ӿ�Ŀ��������¼���
					/*
					if(entry.isIsAddlAccount() && entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
						if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)){						
						}else{
							row.getCell("amount").getStyleAttributes().setLocked(false);
							row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));									
						}					
					}*/
					if(entry.isIsAddlAccount() 
							&& proj!=null && proj.isIsLeaf()
							&& acct!=null && acct.isIsLeaf()
							&& !isProdSplitLeaf(entry)){
						row.getCell("amount").getStyleAttributes().setLocked(false);
						row.getCell("amount").getStyleAttributes().setBackground(new Color(0xFFFFFF));								
					}
				}					
				if(isMeasureContract()){
//					row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
//					row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					setMeasureCtrl(row);
				}
				//ֱ�ӹ���
				initDirectAssign(row);		
				
			}else{
				break;
			}
			
        }
        for(int i=rowIndex;i<kdtEntrys.getRowCount();i++){
        	row=kdtEntrys.getRow(i);
			entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			if(entry.getLevel()==0&&i!=rowIndex){
				break;
			}
			IRow rowNext = kdtEntrys.getRow(i + 1);
			// ȡ�¼��зǿ������Ҫ�ж�
			if (rowNext == null) {
				continue;
			}
			if (!entry.isIsLeaf() && rowNext.getStyleAttributes().isHided()) {
				Object obj = row.getCell("costAccount.name").getValue();
				CellTreeNode node=null;
				if(obj instanceof CellTreeNode){
					node=(CellTreeNode)obj;
					node.setCollapse(true);
				}
				
			}
        }
        //������׼
        setStandard(rowIndex);
    }
    
    
	/**
	 * ���������÷�̯��׼
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void setStandard(int index){
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(index).getUserObject();    
    	
    	int level=curEntry.getLevel();	
    	
    	//1. ��ָ��ݽڵ㣬ʹ�ò��������Ϊ������׼
		if(level==0){
			//Jelon Dec 13, 2006			
			/*if(curEntry.getSplitType()!=null && curEntry.getSplitType()!=CostSplitType.MANUALSPLIT){
				kdtEntrys.getRow(index).getCell("standard").setValue(curEntry.getSplitType());			
			}*/
			if(curEntry.getSplitType()==null || curEntry.getSplitType()==CostSplitTypeEnum.MANUALSPLIT){
				kdtEntrys.getRow(index).getCell("standard").setValue("");	
			}else{
				kdtEntrys.getRow(index).getCell("standard").setValue(curEntry.getSplitType().toString());			
			}
		}
    	
		//2. ������ֽ�㣬ʹ�ø����ķ�̯������Ϊ������׼
    	String apptType=null;
    	if(curEntry.getApportionType()!=null){
    		apptType=curEntry.getApportionType().getName();
    	}
		FDCSplitBillEntryInfo entry=null;
		IRow row=null;
		
		for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry = (FDCSplitBillEntryInfo)row.getUserObject();
			
						
			if(entry.getLevel()==level+1){	
				if(entry.isIsAddlAccount()){
					if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						row.getCell("standard").setValue(apptType);
					}else{
						row.getCell("standard").setValue("ֱ�ӷ���");
					}
				}else{
					row.getCell("standard").setValue(apptType);
				}
				
				if(!entry.isIsLeaf()){
					setStandard(i);
				}
			}
			else if(entry.getLevel()<=level){
				break;
			}		
			
		}	   					
	}
	
    private Map initDirectMap=new HashMap();
    private void  initDirectAssign(){
    	if(initDirectMap==null||initDirectMap.size()==0){
    		return;
    	}
    	
    	Map projProdMap=new HashMap();
		//��Ʒ����		
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",new HashSet(initDirectMap.values()),CompareType.INCLUDE));
    	//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("IsAccObj", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("productType.*"); 		//ʹ�á�*���������б��е����ݺͷ�¼�е�����ƥ��
    	view.getSorter().add(new SorterItemInfo("productType.number"));
		try {   	    	    
			CurProjProductEntriesCollection c=CurProjProductEntriesFactory.getRemoteInstance().getCurProjProductEntriesCollection(view);
			for(int i=0;i<c.size();i++){
				String prjId=c.get(i).getCurProject().getId().toString();
				CurProjProductEntriesCollection temp=(CurProjProductEntriesCollection)projProdMap.get(prjId);
				if(temp==null){
					temp=new CurProjProductEntriesCollection();
				}
				temp.add(c.get(i));
				projProdMap.put(prjId, temp);
			}
		}catch(BOSException e){
			handUIExceptionAndAbort(e);
		}
    	for(Iterator iter=initDirectMap.keySet().iterator();iter.hasNext();	){
    		Integer idx=(Integer)iter.next();
    		String prjId=(String)initDirectMap.get(idx);
    		if(idx==null){
    			continue;
    		}
    		IRow row=getDetailTable().getRow(idx.intValue());
    		CurProjProductEntriesCollection coll=(CurProjProductEntriesCollection)projProdMap.get(prjId);
			if(coll==null){
				coll=new CurProjProductEntriesCollection();
			}
			ProductTypeCollection collProd=new ProductTypeCollection();
			//����
			ProductTypeInfo prod=new ProductTypeInfo();
			prod.setName(null);
			//prod.setName("��");
	        collProd.insertObject(-1,prod);		
	        
	        //��ǰ��Ŀȫ����Ʒ
			for (Iterator iter2 = coll.iterator(); iter2.hasNext();)
			{
				prod = ((CurProjProductEntriesInfo)iter2.next()).getProductType();	        
				if(prod!=null){
					collProd.add(prod);
				}
	        }

			KDComboBox cbo = new KDComboBox();    	    	    	
	        cbo.addItems(collProd.toArray());			
	        row.getCell("product").setEditor(new KDTDefaultCellEditor(cbo));  
    	}
    }

    public void initDirectAssign(IRow row){
    	FDCSplitBillEntryInfo entry;
		entry = (FDCSplitBillEntryInfo)row.getUserObject();
		
		
		boolean isTrue=false;
		isTrue=isProdSplitEnabled(entry);
    	
		if(!isTrue || !entry.isIsLeaf()){
			row.getCell("product").getStyleAttributes().setLocked(true);
			return;
		}else{
			row.getCell("product").getStyleAttributes().setBackground(new Color(0xFFFFFF));
			initDirectMap.put(new Integer(row.getRowIndex()), entry.getCostAccount().getCurProject().getId().toString());
		}
    }

	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		final IRow row = kdtEntrys.getRow(e.getRowIndex());
		if (e.getColIndex()==kdtEntrys.getColumnIndex("amount")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount=FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				FDCSplitBillEntryInfo entry;
				entry = (FDCSplitBillEntryInfo)row.getUserObject();
				String key = getEntrysMapKey(entry);
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...������˵��
				if (entrysMap.get(key) != null) {//modified by ken_liu...������˵��
					// modified by zhaoqin on 2013/11/09 start, ¼����ʱ��NullPointException
					// entry = (FDCSplitBillEntryInfo) entrysMap.get(String.valueOf(entry.getSeq()));
					entry = (FDCSplitBillEntryInfo) entrysMap.get(key);
					// modified by zhaoqin on 2013/11/09 end
				}
				//amount=new BigDecimal(kdtEntrys.getRow(e.getRowIndex()).getCell("amount").getValue().toString());
				Object cellVal=row.getCell("amount").getValue();
				if(cellVal!=null){
					amount=new BigDecimal(cellVal.toString());
				}
				entry.setAmount(amount);
				//��ֱ���
				if(entry.getLevel()==0){
					if(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						splitScale=FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), txtAmount.getBigDecimalValue(),10,BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
					row.getCell("splitScale").setValue(splitScale);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				//��̯
				/*CostSplitType splitType=entry.getSplitType();
				calcApportionAmount(e.getRowIndex(),splitType);*/				
				apptAmount(e.getRowIndex());	
				
				
				//����
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}else if(entry.isIsLeaf() && isAddlAcctLeaf(entry)){
					//������Ĵ����滻��,�۲쿴��û������
					/*
					
					//������ϸ��Ŀ¼�������бȽ�ת��		Jelon	Dec 12, 2006
					
					int idx=e.getRowIndex();
					int idx1=idx;
					int idx2=idx;
					
					int level=entry.getLevel();
					
					IRow row=null;
					
					//���һ��
					for(int i=idx+1; i<kdtEntrys.getRowCount(); i++){
						row=kdtEntrys.getRow(i);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						
						if(entry.getLevel()==level){
							idx2=i;						
							
						}else if(entry.getLevel()<level){
							break;
						}
					}
					
					//��ǰ����������
					amount=new BigDecimal(0);
					
					for(int i=idx2; i>=0; i--){
						row=kdtEntrys.getRow(i);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						
						if(entry.getLevel()==level){
							if(entry.getAmount()!=null){
								amount=amount.add(entry.getAmount());							
							}
							idx1=i;
							
						}else if(entry.getLevel()==level-1){
							idx=i;
							
							BigDecimal amountTotal=new BigDecimal(0);
							if(entry.getAmount()!=null){
								amountTotal=entry.getAmount();
							}

							//�����ϸ��Ŀ���Ƿ�����ϼ���Ŀ�Ľ��
							if(amountTotal.compareTo(amount)==0){
								
								//��̯�ϼ�Ϊ100
								amount=new BigDecimal(100);								
								if(amountTotal.compareTo(FDCHelper.ZERO)==0){
									amount=FDCHelper.ZERO;	
								}else{
									amountTotal=amountTotal.divide(new BigDecimal(100),4,BigDecimal.ROUND_HALF_EVEN);									
								}
								entry.setOtherRatioTotal(amount);
								row.getCell("otherRatioTotal").setValue(amount);
											
								//����̯��ϸ�İٷֱ�
								BigDecimal apptValue=null;								
								for(int j=idx1; j<=idx2; j++){
									row=kdtEntrys.getRow(j);
									entry=(FDCSplitBillEntryInfo)row.getUserObject();
									
									if(entry.getAmount()!=null){
										amount=entry.getAmount();							
									}else{
										amount=new BigDecimal(0);
									}
									
									apptValue=FDCHelper.ZERO;
									if(amountTotal.compareTo(FDCHelper.ZERO)!=0){
										apptValue=amount.divide(amountTotal,2,BigDecimal.ROUND_HALF_EVEN);
									}				
									
									entry.setApportionValue(apptValue);									
									row.getCell("apportionValue").setValue(apptValue);
								}
								
							}
							
							break;
						}
					}
					
					
					
					
					
				*/}
				
				
			}
		}
		
		if (e.getColIndex()==kdtEntrys.getColumnIndex("splitScale")){
			if (e.getValue()!=e.getOldValue()){
				
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal splitScale = FDCHelper.ZERO;
				FDCSplitBillEntryInfo entry;
				entry = (FDCSplitBillEntryInfo)row.getUserObject();
				
				// modified by zhaoqin on 2013/11/09, Ӧ����ͳһ�ķ���
				// String key = entry.getCostAccount().getId().toString() + String.valueOf(entry.getSeq());
				String key = getEntrysMapKey(entry);
								
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...������˵��
				if (entrysMap.get(key) != null) {//modified by ken_liu...������˵��
					entry = (FDCSplitBillEntryInfo) entrysMap.get(key);
				}
				Object cellVal=row.getCell("splitScale").getValue();
				if(cellVal!=null){
					splitScale=FDCHelper.toBigDecimal(cellVal);
				}
				if(entry.getLevel()==0){
					entry.setSplitScale(splitScale);
					amount = FDCHelper.divide(FDCHelper.multiply(txtAmount.getBigDecimalValue(), splitScale),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
					row.getCell("amount").setValue(amount);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				
				apptAmount(e.getRowIndex());				
				//����
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}
			}
		}
		
		//���ӿ�Ŀ����
		if (e.getColIndex()==kdtEntrys.getColumnIndex("amount")){
			BigDecimal value=e.getValue()==null?FDCHelper.ZERO:(BigDecimal)e.getValue();
			BigDecimal oldValue=e.getOldValue()==null?FDCHelper.ZERO:(BigDecimal)e.getOldValue();
			BigDecimal changeAmt=value.subtract(oldValue);
			if (changeAmt.compareTo(FDCHelper.ZERO)!=0){
				FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
				if(entry.isIsLeaf()&&entry.isIsAddlAccount()){
					totAddlAcct(entry.getCostAccount().getCurProject(), entry.getCostAccount(), changeAmt, e.getRowIndex());
					entry.setApportionValue(value);
					row.getCell("apportionValue").setValue(value);
				}
			}
		}
		//���Ӳ�Ʒ
		if (e.getColIndex()==kdtEntrys.getColumnIndex("product")){
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)row.getUserObject();
			Object product = row.getCell("product").getValue();
			//if(product!=null&&product instanceof ProductTypeInfo && ((ProductTypeInfo)product).getId()!=null ){
			if(product!=null&& product.toString()!=null ){
				entry.setProduct((ProductTypeInfo)product);
			}else{
				entry.setProduct(null);
			}
		}
		//������������ĩ����ֵ���ɱ���Ŀʱ����������¼�룬���½�����
		if(editData.getBoolean("isMeasureSplit")){
			handleMeasureCalc(e, row);
		}
	}

	/**
	 * ���ۺ�ͬ�����߼�
	 * @param e
	 * @param row
	 */
	protected void handleMeasureCalc(KDTEditEvent e, final IRow row) {
		//���ۻ���
		if (e.getColIndex()==kdtEntrys.getColumnIndex("workLoad")
			||e.getColIndex()==kdtEntrys.getColumnIndex("price")){
			FDCSplitBillEntryInfo entry= (FDCSplitBillEntryInfo)row.getUserObject();
			BigDecimal oldAmt=entry.getAmount();
			BigDecimal amount = FDCHelper.multiply(row.getCell("workLoad").getValue(), row.getCell("price").getValue());
			row.getCell("amount").setValue(amount);
			entry.setWorkLoad((BigDecimal)row.getCell("workLoad").getValue());
			entry.setPrice((BigDecimal)row.getCell("price").getValue());
			entry.setAmount(amount);
			try{
				kdtEntrys_editStopped(new KDTEditEvent(e.getSource(), oldAmt, amount, 
					row.getRowIndex(), row.getCell("amount").getColumnIndex(),false,1));
			}catch (Exception e1) {
				logger.error(e1.getMessage(),e1);
				handUIExceptionAndAbort(e1);
			}
			calcAmount(0);
		}

		setMeasureCtrl(row);
	}

	/**
	 * ���ۺ�ͬ�����߼�
	 * @param row
	 */
	protected void setMeasureCtrl(final IRow row) {
		Color cantEditColor=new Color(0xF5F5E6);
		row.getCell("price").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		row.getCell("workLoad").getStyleAttributes().setBackground(new Color(0xFFFFFF));
		BigDecimal amount=FDCHelper.toBigDecimal(row.getCell("amount").getValue());
		BigDecimal price=FDCHelper.toBigDecimal(row.getCell("price").getValue());
		BigDecimal workLoad=FDCHelper.toBigDecimal(row.getCell("workLoad").getValue());
		if(price.signum()!=0||workLoad.signum()!=0){
			row.getCell("amount").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("amount").getStyleAttributes().setLocked(true);
		}else if (amount.signum()!=0){
			row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
			row.getCell("price").getStyleAttributes().setLocked(true);
			row.getCell("workLoad").getStyleAttributes().setLocked(true);
		}else{
			row.getCell("amount").getStyleAttributes().setLocked(false);
			row.getCell("price").getStyleAttributes().setLocked(false);
			row.getCell("workLoad").getStyleAttributes().setLocked(false);
			row.getCell("amount").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("price").getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("workLoad").getStyleAttributes().setBackground(Color.WHITE);
		}
		if(row.getUserObject() instanceof FDCSplitBillEntryInfo){
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(isProdSplitLeaf(entry)){
				row.getCell("price").setValue(entry.getPrice());	
				row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				row.getCell("price").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("workLoad").getStyleAttributes().setBackground(cantEditColor);
				row.getCell("price").getStyleAttributes().setLocked(true);
				row.getCell("workLoad").getStyleAttributes().setLocked(true);
			}
		}
	}


	/**
	 * ��������̯������FDCCostSplit�ӿڣ�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    protected void apptAmount(int rowIndex){
    	FDCSplitBillEntryInfo topEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();
    	
    	//�޸ĵ��ýӿ�	jelon 12/26/2006
		/*CostSplitType splitType=entry.getSplitType();
		calcApportionAmount(rowIndex,splitType);
		
		//�������
		adjustAmount(rowIndex);*/
		
				
		//fdcCostSplit.apptAmount((IObjectCollection)editData.get("entrys"),entry);
		fdcCostSplit.apptAmount(getEntrys(),topEntry);
		
		int level=topEntry.getLevel();
		IRow row=null;
		boolean isMeasureContract=isMeasureContract();
		
		Object value = kdtEntrys.getCell(rowIndex, "amount").getValue();
		//�ѷ�̯�ܽ��
		BigDecimal totalAmt = FDCHelper.ZERO;
		for(int i=rowIndex+1; i<kdtEntrys.getRowCount(); i++){				
			row=kdtEntrys.getRow(i);
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level){
				BigDecimal amount=entry.getAmount();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("amount").setValue(amount);
				if (entry.getLevel() == level + 1) {
					totalAmt = FDCHelper.add(totalAmt, amount);
				}
				if (i == kdtEntrys.getRowCount() - 1 && FDCHelper.compareTo(value, totalAmt) != 0) {
					row.getCell("amount").setValue(
							FDCHelper.add(row.getCell("amount").getValue(), FDCHelper.subtract(value, totalAmt)));
				}
				
				if(isMeasureContract&&isProdSplitLeaf(entry)){
					entry.setPrice(topEntry.getPrice());
					row.getCell("price").setValue(topEntry.getPrice());	
					row.getCell("workLoad").setValue(FDCHelper.divide(entry.getAmount(), entry.getPrice()));
				}
			}else{
				break;
			}
		}
		


    }


	/**
	 * ���������÷�̯��׼��ȫ����
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-10-19 <p>
	 */
    private void setStandard(){
    	FDCSplitBillEntryInfo entry=null;
    	IRow row=null;
    	
		for(int i=0; i<kdtEntrys.getRowCount(); i++){	
			row=kdtEntrys.getRow(i);
			entry = (FDCSplitBillEntryInfo)row.getUserObject();
			
			if(entry.getLevel()==0){
				//row.getCell("standard").setValue(entry.getSplitType().toString());				
				
				setStandard(i);
			}
		}
    }
    

    
    
    protected void calcAmount(int rowIndex){
		BigDecimal amountTotal=FDCHelper.ZERO;
		
		BigDecimal amount = FDCHelper.ZERO;
		
		FDCSplitBillEntryInfo entry = null;
		
		//�������ܽ��
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			/*if (kdtEntrys.getRow(i).getCell(COLAMOUNT).getValue()!=null){
				amount = amount.add(new BigDecimal(kdtEntrys.getRow(i).getCell(COLAMOUNT).getValue().toString()));
			}*/
			entry = (FDCSplitBillEntryInfo) kdtEntrys.getRow(i).getUserObject();
			
			if (entry.getLevel() == 0) {
				amount = entry.getAmount();
				if (amount != null) {
					amountTotal = amountTotal.add(amount);
				}
			}
		}
		amountTotal = amountTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
		if (txtSplitedAmount.getBigDecimalValue() != null
				&& amountTotal.compareTo(FDCHelper.toBigDecimal(txtSplitedAmount.getBigDecimalValue())
						.setScale(2, BigDecimal.ROUND_HALF_UP)) == 0) {
			try {
				txtSplitedAmount_dataChanged(null);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		} else {
			txtSplitedAmount.setValue(amountTotal);
		}    	
    } 
	
	protected void txtSplitedAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(e==null) return;
		super.txtSplitedAmount_dataChanged(e);
		
		
		/*
		 * if (e.getNewValue()!=null && txtAmount.getValue()!=null){
		 * //���0����������0����δ��ֲ���ʾ
		 * 
		 * BigDecimal amount=new BigDecimal(txtAmount.getValue().toString());
		 * 
		 * BigDecimal amtSplited=new BigDecimal(e.getNewValue().toString());
		 * 
		 * txtUnSplitAmount.setValue(amount.subtract(amtSplited)); }
		 */

		BigDecimal amount = FDCHelper.toBigDecimal(txtAmount.getValue());

		BigDecimal amtSplited = FDCHelper.toBigDecimal(e.getNewValue());

		txtUnSplitAmount.setValue(FDCHelper.subtract(amount, amtSplited));
	}

	private void splitCost(CostSplitTypeEnum costSplitType) throws Exception {

		//----------------------------------------------------------------------------------------
		//ѡ����

        if ((kdtEntrys.getSelectManager().size() == 0)
                || isTableColumnSelected(kdtEntrys))
        {
            FDCMsgBox.showInfo(this, "û��ѡ�з�¼���޷����ò�ַ�����");
            return;
        }
		
		
		int topIdx=-1;		
		int[] selectRows = KDTableUtil.getSelectedRows(kdtEntrys);        
        if(selectRows.length >0){
        	topIdx = selectRows[0];
        }
        if(!(topIdx>=0)){
        	return;
        }        	        
        

		//----------------------------------------------------------------------------------------
        //��ֶ���
        IRow topRow=kdtEntrys.getRow(topIdx);         
		//FDCSplitBillEntryInfo selectEntry=editData.getEntrys().get(selectIdx);
        FDCSplitBillEntryInfo topEntry=(FDCSplitBillEntryInfo)topRow.getUserObject();
        
        

		int topLevel=topEntry.getLevel();			
		BOSUuid topId=topEntry.getId();		
		CostAccountInfo topAcct=topEntry.getCostAccount();			
		if(topAcct==null){
			return;
		}
		String topAcctNo=topEntry.getCostAccount().getLongNumber();
		        
        //�������
		CostSplitTypeEnum splitType=topEntry.getSplitType();
		
		
		boolean isTrue=true;	
		
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){			//��Ʒ���	
			if(!isProdSplitEnabled(topEntry)){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷����ò�Ʒ��̯������");			
				FDCMsgBox.showWarning(this,"��ѡ��¼�����ϲ�Ʒ�������,��ѡ����ϸ��¼���в���");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)){	//�Զ����	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT)){					
					isTrue=false;					
				}
			}
			
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue=false;
			}
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷������Զ���ַ�����");		
				FDCMsgBox.showWarning(this, "��ѡ��¼�������Զ��������,��ѡ��һ������ϸ��¼���в���");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	//ĩ�����	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				//if(topEntry.getSplitType().equals(CostSplitType.PROJSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
					//isTrue=false;
										
					//����ǰ���Զ����ת����ĩ�����	jelon 12/6/06
					if(topEntry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) && topEntry.getLevel()==0){						
						if (!FDCMsgBox.isYes(FDCMsgBox
				                .showConfirm2(this,FDCSplitClientHelper.getRes("sure")))){
							return;
						}							
					}else{
						isTrue=false;
					}
				}
			}
			
			
			if(topEntry.getLevel()!=0){
				isTrue=false;
			}
			
			if(topAcct.isIsLeaf() && topAcct.getCurProject().isIsLeaf()){
				isTrue=false;
			}
			if ((!parentMap.containsKey(topEntry.getCostAccount().getId().toString()) || topAcct.isIsLeaf()) && topAcct.getCurProject().isIsLeaf()) {
				isTrue = false;
			}
			if(!isTrue){
				//FDCMsgBox.showInfo(this,"��ǰ��¼�޷�����ĩ����ַ�����");		
				FDCMsgBox.showWarning(this, "��ѡ��¼������ĩ���������,��ѡ��һ������ϸ��¼���в���");
				return;
			}
		}
				
		//topEntry.setSplitType(costSplitType);
        
        int level=0;

		
		//----------------------------------------------------------------------------------------
		//׼������
		FDCSplitBillEntryCollection entrys=new FDCSplitBillEntryCollection();
		entrys.add(topEntry);
				
		FDCSplitBillEntryInfo entry=null;
		for(int i=topIdx+1; i<kdtEntrys.getRowCount(); i++){
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();		
			
			if(entry.getLevel()>topLevel){
				entrys.add(entry);
			}else{
				break;
			}
		}
				

		//----------------------------------------------------------------------------------------
		//�������UI
		UIContext uiContext = new UIContext(this); 
		//uiContext.put("costSplit", editData.getEntrys());		
		uiContext.put("costSplit", entrys);			
		uiContext.put("splitType", costSplitType);		
		uiContext.put("entryClass", getSplitBillEntryClassName());		
		uiContext.put("parentMap", parentMap);
		String apptUiName;
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			apptUiName=CostSplitApptProdUI.class.getName();
		}else{
			apptUiName=CostSplitApptProjUI.class.getName();
		}
		
		IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				apptUiName,	uiContext, null ,STATUS_ADDNEW );       
		uiWin.show();	
			
		if (!((CostSplitApptUI) uiWin.getUIObject()).isOk()) {
			return;
		}

		//����ֵ
		entrys =(FDCSplitBillEntryCollection) ((CostSplitApptUI) uiWin.getUIObject()).getData() ;

		//		for (int i = 0; i < entrys.size(); i++) {
		//			if (entrys.get(i).getLevel() > 1) {
		//				entrys.get(i).setLevel(1);
		//			}
		//		}

		//----------------------------------------------------------------------------------------
		//ɾ��ԭ���Ĳ��
		int index=0;
		for(int i=topIdx+1; i<kdtEntrys.getRowCount(); i++){
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			if(entry.getLevel()>topLevel){
				index=i;
			}else{
				break;
			}			
		}
		for(int i=index; i>topIdx ; i--){
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			if(entry.getLevel()==topLevel){
				break;
			}
			else
			{
				removeEntry(i);
			}
		}
				
				
		
		//----------------------------------------------------------------------------------------
		
		//�ɱ���Ŀ
		CostAccountInfo acct=null;
		acct=entrys.get(0).getCostAccount();
					
		//�������
		splitType=costSplitType;	//CostSplitType.BOTUPSPLIT;
				
		//��̯����
		ApportionTypeInfo apportionType;
		apportionType = entrys.get(0).getApportionType();  
		
		//���ӿ�Ŀ
		boolean isAddlAcct=entrys.get(0).isIsAddlAccount();
		
		topEntry.setSplitType(splitType);
		topEntry.setApportionType(apportionType);	
		topEntry.setIsLeaf(false);			
		topEntry.setProduct(null);
		
		topRow.getCell("standard").setValue(splitType.toString());
		topRow.getCell("product").setValue("");
		topRow.getCell("product").getStyleAttributes().setLocked(true);
		
		//���ԡ�begin
		if(apportionType!=null){
			topRow.getCell("apportionType.name").setValue(apportionType.getName());
		}
		topRow.getCell("splitType").setValue(splitType);
		//���ԡ�end
		
		IRow row;				
		
		//��Ʒ��֣�ɾ��ȫ�������
		if(entrys.size()==1){	
			topEntry.setIsLeaf(true);
			
			if(topEntry.getLevel()==0){
				topEntry.setSplitType(CostSplitTypeEnum.MANUALSPLIT);
				topEntry.setApportionType(null);
				
				topRow.getCell("splitType").setValue(CostSplitTypeEnum.MANUALSPLIT);
				//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
				

				//topRow.getCell("standard").setValue("");
				setDisplay(topIdx);
				
			}else{
				for(int i=topIdx-1; i>=0; i--){
					row=kdtEntrys.getRow(i);
					entry=(FDCSplitBillEntryInfo) row.getUserObject();
					
					if(entry.getLevel()==topEntry.getLevel()-1){
						topEntry.setSplitType(entry.getSplitType());
						topEntry.setApportionType(null);

						topRow.getCell("splitType").setValue(entry.getSplitType());
						//topRow.getCell("apportionType").setValue(new ApportionTypeInfo());
						
						setDisplay(i);
						
						break;
					}
				}
				
			}
			
			
			return;
		}
		
		
		
		
		//�����µĲ����
		int idxCurr=topIdx;
		
		for(int i=1; i<entrys.size(); i++){
			entry=entrys.get(i);				

			//������	jelon 12/27/2006
			entry.setIndex(topEntry.getIndex());
						
			//entry.setSplitType(splitType);
			if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
				//��Ŀ����а����Ĳ�Ʒ���
			}else{
				entry.setSplitType(splitType);
			}
			
			
			if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
				//��Ŀ����а����Ĳ�Ʒ���
				entry.setIsAddlAccount(isAddlAcct);
			}
									
			idxCurr++;
			row=insertEntry(idxCurr,entry);			
			
			row.getCell("costAccount.curProject.name").setValue(entry.getCostAccount().getCurProject().getName());
			row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());	
		}
		
		//----------------------------------------------------------------------------------------
		
		//���������	
		//calcApportionData(topIdx,costSplitType);	//ʹ���½ӿڡ�jelon 12/26/2006
		totApptValue(topIdx);

		//��̯�ɱ�
		//calcApportionAmount(topIdx,costSplitType);	//ʹ���½ӿڡ�jelon 12/26/2006
		apptAmount(topIdx);
			

		//������ʾ
		index=topIdx;
		
		//��Ʒ��֣��Ӳ�����ĸ��ڵ㿪ʼ����
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
			row=kdtEntrys.getRow(topIdx);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()!=0){
				for(int i=topIdx-1; i>=0; i--){
					row=kdtEntrys.getRow(i);
					entry=(FDCSplitBillEntryInfo)row.getUserObject();
					if(entry.getLevel()==0){
						index=i;
						break;
					}
				}
			}
		}
		setDisplay(index);		
	}

    /**
	 * ���������ܷ�ָ̯�꣨����FDCCostSplit�ӿڣ�
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    private void totApptValue(int rowIndex){
    	FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();

    	//�޸ĵ��ýӿ�	jelon 12/26/2006
		/*CostSplitType splitType=entry.getSplitType();
		calcApportionData(rowIndex,splitType);*/
		

		//fdcCostSplit.totApptValue((IObjectCollection)editData.get("entrys"),entry);
		fdcCostSplit.totApptValue(getEntrys(),entry);
						
		int level=entry.getLevel();
		IRow row=null;
		BigDecimal amount=null;
		
		for(int i=rowIndex; i<kdtEntrys.getRowCount(); i++){				
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
			if(entry.getLevel()>level
					|| (entry.getLevel()==level && i==rowIndex)){
				amount=entry.getApportionValueTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("apportionValueTotal").setValue(amount);   	 

				amount=entry.getDirectAmountTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("directAmountTotal").setValue(amount); 

				amount=entry.getOtherRatioTotal();
				if(amount==null){
					amount=FDCHelper.ZERO;
				}				
				row.getCell("otherRatioTotal").setValue(amount);
				
			}else{
				break;
			}
		}
    }

	private boolean isProdSplitEnabled(FDCSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		/*
		if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitType.MANUALSPLIT)){
			if(entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
    			isTrue=true;
    		}
		}else if(entry.getSplitType().equals(CostSplitType.PROJSPLIT) || entry.getSplitType().equals(CostSplitType.BOTUPSPLIT)){
    		if(entry.getCostAccount().isIsLeaf() && entry.getCostAccount().getCurProject().isIsLeaf()){
    			isTrue=true;
    		}
    	}else if(entry.getSplitType().equals(CostSplitType.PRODSPLIT)){
    		if(!entry.isIsLeaf()){
    			isTrue=true;
    		}
    	}
		*/
		// entry.getCostAccount().isIsLeaf() ȡ�� ���Է���ϸ�ɽ��в�Ʒ��֡�
		if (entry != null && entry.getCostAccount() != null && entry.getCostAccount().getCurProject() != null && entry.getCostAccount().getCurProject().isIsLeaf()) {
			
			if(entry.getSplitType()==null || entry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				isTrue=true;
			}else if(entry.getSplitType().equals(CostSplitTypeEnum.PROJSPLIT) || entry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
				isTrue=true;
	    	}else if(entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
	    		if(!entry.isIsLeaf()){
	    			isTrue=true;
	    		}
	    	}			
		}
		
		return isTrue;
	}

	private boolean isProdSplitParent(FDCSplitBillEntryInfo entry){		
		boolean isTrue=false;
		
		if(!entry.isIsLeaf()
				&& entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
			isTrue=true;
		}		
		return isTrue;
	}
	

	protected SelectorItemCollection setSelectors(SelectorItemCollection sic) {
		SelectorItemCollection selector = setSelectorsEntry(sic,false);
		//���ݵĵ�һ������ȫ������
		selector.add("*");
		selector.add("contractBill.id");
		return selector;
	}	
	
	protected SelectorItemCollection setSelectorsEntry(SelectorItemCollection sic, boolean isEntry) {
		return fdcCostSplit.setSelectorsEntry(sic, isEntry);
	}	
	
	protected String getSplitBillEntryClassName(){
		return null;
	}

	public void actionImpContrSplit_actionPerformed(ActionEvent e) throws Exception {		
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		
		for(int i=0; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
			if(entry.getCostBillId()!=null){
				FDCMsgBox.showInfo(this,"�Ѿ������˺�ͬ��֣������ظ����룡");
				return;
			}
		}
		    	
    	importCostSplitContract();    	
		setDisplay();
		
		updateEntryProgramming();
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
	
	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#onLoad()
	 */
	public void onLoad() throws Exception {
		if (getUIContext().get("contractBillId") != null) {
			contractBillId = getUIContext().get("contractBillId").toString();
		}
		super.onLoad();	
		registerMeasureDefaultSplitTypeSetKey();
		//����������ĸ�ʽ��ֻ����������
		getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper.getTotalCellNumberEdit());
		getDetailTable().getColumn("price").setEditor(getCellNumberEdit());
		getDetailTable().getColumn("workLoad").setEditor(getCellNumberEdit());
        
		//getDetailTable().getColumn("directAmount").setEditor(FDCSplitClientHelper.getCellNumberEdit());
		
//		FDCTableHelper.setPaseMode(getDetailTable());
		((KDTTransferAction) getDetailTable().getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		//���㵽�����һ��ʱ�����Զ�������
		disableAutoAddLine(kdtEntrys);	 
		initCtrlListener();
		
		this.actionViewCostInfo.setVisible(false);
		
		//������		jelon 12/27/2006
		int idx=0;
		FDCSplitBillEntryInfo entry=null;
		for(int i=0; i<kdtEntrys.getRowCount(); i++){	
			entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();			
			if(entry.getLevel()==0){
				if(entry.getIndex()>idx){
					idx=entry.getIndex();
				}
			}
		}
		groupIndex=idx;
		if(getOprtState().equals(OprtState.ADDNEW)){
			actionRemove.setEnabled(false);
		}
		
		disableAutoAddLine(getDetailTable());
		disableAutoAddLineDownArrow(getDetailTable());
		disableEnterFocusTravel();
		Object[] listeners = getDetailTable().getListenerList().getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == KDTSelectListener.class)
			{
				getDetailTable().getSelectManager().removeKDTSelectListener(((KDTSelectListener) listeners[i + 1]));
			}
		}
		actionInsertLine.setEnabled(false);
		actionAddLine.setEnabled(false);
		
		boolean isAddNew=this.getOprtState()!=null&&this.getOprtState().equals(OprtState.ADDNEW);
        if(isAddNew){
        	//�����
        	String costBillID = (String)getUIContext().get("costBillID");
        	if(costBillID!=null){
        		java.util.List list=new ArrayList();
        		list.add(costBillID);
        		try {
					FDCClientUtils.requestDataObjectLock(this, list, "edit");
        		}catch (Throwable e) {
        			this.handUIException(e);
        			SysUtil.abort();
				}
        		
        	}
        }
		//�޸Ĳ�ֵ�С��λ����������С��λ���²�ֲ�����ȫ��Ӱ�췶Χȫ�����,���ʵ��ֻ֧����λС��
		getTotalTxt().setValue(FDCHelper.toBigDecimal(getTotalTxt().getBigDecimalValue(),2));
		this.editData.setAmount(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(this.editData.getAmount(),2)));
		/** ����ԭ�еĸ����ַ�¼ */
		String longNumber = null;
		PaymentSplitEntryInfo info = null;
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			String className = getUIContext().get("Owner").getClass().getName();
			if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())) {
				info = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
				if (info.getCostAccount() instanceof CostAccountInfo) {
					oldCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
				}
			}
		}
	}
	
	public boolean destroyWindow() {
		boolean destroyWindow = super.destroyWindow();
		if(destroyWindow){
			boolean isAddNew = this.editData.getId() == null;
			if (isAddNew && isLocked) {
				// �����
	        	String costBillID = (String)getUIContext().get("costBillID");
	        	if(costBillID!=null){
	        		java.util.List list=new ArrayList();
	        		list.add(costBillID);
	        		try {
						FDCClientUtils.releaseDataObjectLock(this, list);
	        		}catch (Throwable e) {
	        			// @AbortException
						logger.error(e.getMessage(), e);
					}	        		
	        	}
	        }
		}
		return destroyWindow;
	}
	public void onShow() throws Exception
	{
		Object obj=SysContext.getSysContext().getProperty("splitDedug");
		getDetailTable().getColumn("price").getStyleAttributes().setHided(!isMeasureContract());
		getDetailTable().getColumn("workLoad").getStyleAttributes().setHided(!isMeasureContract());
		this.editData.setBoolean("isMeasureContract", isMeasureContract());

		if(obj instanceof Boolean){
			if(((Boolean)obj).booleanValue()){
				setDisplayHideColumn(); //���Ե�ʱ���
			}
		}
//		setDisplayHideColumn(); //���Ե�ʱ���
		kdtEntrys.setColumnMoveable(true);
		super.onShow();
    	if (OprtState.VIEW.equals(getOprtState())) {
    		//�鿴ʱ��һЩ״̬�趨
    		//��¼���ɱ༭
//    		getDetailTable().setEditable(false);
//    		getDetailTable().setEnabled(false);
    		getDetailTable().getStyleAttributes().setLocked(true);
    		
    		//��ť��״̬
        	actionSplitBotUp.setEnabled(false);
        	btnSplitBotUp.setEnabled(false);
        	actionSplitProd.setEnabled(false);
        	btnSplitProd.setEnabled(false);
        	actionSplitProj.setEnabled(false);
        	btnSplitProj.setEnabled(false);
        	actionAcctSelect.setEnabled(false);
        	btnAcctSelect.setEnabled(false);
//        	actionAudit.setEnabled(false);
//        	actionUnAudit.setEnabled(false);
        	actionRemoveLine.setEnabled(false);
        	btnRemoveLine.setEnabled(false);
        	if(editData!=null&&editData.getState()!=null&&editData.getState().equals(FDCBillStateEnum.INVALID)){
        		actionAudit.setEnabled(false);
        		actionUnAudit.setEnabled(false);
        	}
    	}
    	if (OprtState.ADDNEW.equals(getOprtState())) {
    		txtUnSplitAmount.setValue(txtAmount.getValue());
    	}
    	
    	if(getOprtState()==STATUS_FINDVIEW){
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    		actionRemoveLine.setEnabled(true);
    		actionRemove.setEnabled(true);
    		actionSave.setEnabled(true);
    	}
    	addDebugWin();
    	txtSplitedAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
    	txtUnSplitAmount.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE);
    	
    	
		//�������˵���ʱ����
		actionWorkFlowG.setVisible(false);
		actionWorkFlowG.setEnabled(false);
		actionWorkflowList.setVisible(false);
		actionWorkflowList.setEnabled(false);
		actionMultiapprove.setVisible(false);
		actionMultiapprove.setEnabled(false);
		actionNextPerson.setVisible(false);
		actionNextPerson.setEnabled(false);
		actionAuditResult.setVisible(false);
		actionAuditResult.setEnabled(false);
		handleOldData();
		if (oldCostAccountLongNumber.size() < 1) {
			/** ����ԭ�еĸ����ַ�¼ */
			String longNumber = null;
			PaymentSplitEntryInfo info = null;
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				String className = getUIContext().get("Owner").getClass().getName();
				if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())) {
					info = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					if (info.getCostAccount() instanceof CostAccountInfo) {
						oldCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
					}
				}
			}
		}
	}
    /**
     * ��ʾ���ص��У����ڵ���
     * �����Ԫ���������������أ����ڴ��������������صĻ�����ڱ�������ҳ��
     * ��ʾ���ص���������ͨ����Ԫ��������������Ȼ��ͨ����ʾ������ʾ�����еķ�
     * ʽ���ﵽ���Ե�Ŀ��
     * @author sxhong  		Date 2006-12-1
     */
    protected void setDisplayHideColumn()
	{
    	//���ض������
    	int column_index=getDetailTable().getColumnIndex("costAccount.curProject.id");
    	for(int i=column_index;i<getDetailTable().getColumnCount();i++){
    		getDetailTable().getColumn(i).getStyleAttributes().setHided(false);
    	}
		
	}
	protected FDCSplitBillEntryCollection getCostSplitEntryCollection(CostSplitBillTypeEnum splitBillType){
/*		String csotBillId=contractBillId;
		BOSUuid costBillUuId=BOSUuid.read(csotBillId);*/
		//��ͬ�ѱ�����Ĭ��ֵ
		return getCostSplitEntryCollection(splitBillType, null);
	}
	
	protected void loadCostSplit(FDCSplitBillEntryCollection entrys){
		FDCSplitBillEntryInfo entry=null;
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCSplitBillEntryInfo) iter.next();	
			
/*			//ֱ�ӷ���
			entry.setDirectAmount(amount);
			entry.setDirectAmountTotal(amount);*/
			
			//�������֣��������ý��
			if(splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)){
				entry.setAmount(FDCHelper.ZERO);
				entry.setPrice(FDCHelper.ZERO);
				entry.setWorkLoad(FDCHelper.ZERO);
			}
			BigDecimal amount = FDCHelper.ZERO;
			if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT) || splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT) ) {
				if(entry.getLevel()==0){
					amount = FDCHelper.divide(FDCHelper.multiply(txtAmount.getBigDecimalValue(), entry.getSplitScale()),FDCHelper.ONE_HUNDRED,10,BigDecimal.ROUND_HALF_UP);
					entry.setAmount(amount);
				}else{
					entry.setAmount(FDCHelper.ZERO);
				}
			}
			//������		jelon 12/28/2006
			if(entry.getLevel()==0){
				groupIndex++;				
			}
			entry.setIndex(groupIndex);		
			
			addEntry(entry);	
		}		
	}
	
	protected FDCSplitBillEntryCollection getSplitEntryCollectionContract(CostSplitBillTypeEnum splitBillType){
		String csotBillId=contractBillId;
		BOSUuid costBillUuId=BOSUuid.read(csotBillId);
		
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		FDCSplitBillEntryCollection coll=null;
    	ContractCostSplitEntryCollection collContr=null;
		
			
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
		
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(sic,true);
    	
    	view.getSorter().add(new SorterItemInfo("seq"));
    	
	
		/*
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(sic,true);
    	
    	view.getSorter().add(new SorterItemInfo("seq"));*/
    	
    	filter.getFilterItems().add(new FilterItemInfo("Parent.contractBill.id", contractBillId));
    	view.setFilter(filter);
    	
		try {
			collContr = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}		
		
		FDCSplitBillEntryCollection entrys=new FDCSplitBillEntryCollection();
    	FDCSplitBillEntryInfo item=null;
    	ContractCostSplitEntryInfo itemContr=null;

		for (Iterator iter = collContr.iterator(); iter.hasNext();)		{
			itemContr = (ContractCostSplitEntryInfo) iter.next();	
			entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);			
			entry.putAll(itemContr);	
			entry.setCostBillId(costBillUuId);
			//row=addEntry(entry);
			entrys.add(entry);
		}				
		return entrys;		
	}
	
	/**
	 * ͨ������Ĳ�ֵ��ݵ�����(�����֣������ֵ�)����Ӧ�ĺ�ͬID�õ���ַ�¼
	 * @param splitBillType	
	 * @param costBillUuId	��ֵ��ݵ�CostBillUuid�������BOSUuid�����BOSUuid��
	 * @return
	 */
	protected FDCSplitBillEntryCollection getCostSplitEntryCollection(CostSplitBillTypeEnum splitBillType, BOSUuid costBillUuId){

		String costBillId=null;
		if(costBillUuId==null){
			costBillUuId=BOSUuid.read(getContractBillId());
		}
		costBillId=costBillUuId.toString();

		if(costBillId==null){
			return new FDCSplitBillEntryCollection();
		}
		AbstractObjectCollection coll=null;
		EntityViewInfo view = new EntityViewInfo();
		String filterField=null;
		FilterInfo filter = new FilterInfo();	
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(sic,true);
    	view.getSelector().add("parent.id");
    	view.getSorter().add(new SorterItemInfo("seq"));

    	try {
			if (splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
				filterField = "parent.contractBill.id";
				filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId));
				filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				view.setFilter(filter);
				coll = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
			} else if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
				filterField = "parent.contractChange.id";
				filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId));
				filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				view.setFilter(filter);
				coll = ConChangeSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
			} else if (splitBillType.equals(CostSplitBillTypeEnum.SETTLEMENTSPLIT)) {
				filterField = "parent.settlementBill.id";
				filter.getFilterItems().add(new FilterItemInfo(filterField, costBillId));
				filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				view.setFilter(filter);
				coll = SettlementCostSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
			} else {
				//������ֵ�,�Ժ��ṩ֧��
				return new FDCSplitBillEntryCollection();
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
    	
		FDCSplitBillEntryCollection entrys = encapsulateSettlementSplitEntrys(costBillUuId, coll);					
				
		return entrys;		
	}

	/**
	 * ��AbstractObjectCollection��װΪFDCSplitBillEntryCollection����
	 * @param costBillUuId ��ֵ��ݵ�id
	 * @param coll AbstractObjectCollection����
	 * @return ��װ���FDCSplitBillEntryCollection
	 * @Author��owen_wen
	 * @CreateTime��2012-11-15
	 */
	private FDCSplitBillEntryCollection encapsulateSettlementSplitEntrys(BOSUuid costBillUuId, AbstractObjectCollection coll) {
		AbstractBillEntryBaseInfo item;
		FDCSplitBillEntryCollection entrys=new FDCSplitBillEntryCollection();
		FDCSplitBillEntryInfo entry=null;
  	
		for (Iterator iter = coll.iterator(); iter.hasNext();)		{
			item = (AbstractBillEntryBaseInfo) iter.next();	
			item.setId(null);
			//item.setSeq(null);
			entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);							
			entry.putAll(item);	
			
			//costBillUuId=item.get(costBillIdField)
			BOSUuid splitBillId = item.getObjectValue("parent")==null?null:item.getObjectValue("parent").getBOSUuid("id");
			if(splitBillId!=null){
				entry.setSplitBillId(splitBillId);
			}
			entry.setCostBillId(costBillUuId);
			entrys.add(entry);
		}
		return entrys;
	}
	
	/**
	 * ͨ������Ĳ�ֵ��ݵ�����(�����֣������ֵ�)����Ӧ�Ĳ��ID�õ���ַ�¼
	 * @param splitBillType	
	 * @param costBillUuId	��ֵ��ݵ�CostBillUuid�������BOSUuid�����BOSUuid��
	 * @return
	 */
	protected FDCSplitBillEntryCollection getCostSplitEntryCollection(CostSplitBillTypeEnum splitBillType, BOSUuid splitBillUuId, BOSUuid costBillUuId){

		String splitBillId = splitBillUuId.toString();

		if(splitBillId==null){
			return new FDCSplitBillEntryCollection();
		}
		AbstractObjectCollection coll=null;
		AbstractBillEntryBaseInfo item=null;  
		EntityViewInfo view = new EntityViewInfo();
		String filterField=null;
		FilterInfo filter = new FilterInfo();	
    	SelectorItemCollection sic=view.getSelector();  
    	setSelectorsEntry(sic,true);
    	view.getSorter().add(new SorterItemInfo("seq"));
    	   	
    	try {
			if (splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)) {
				filterField = "parent.id";
				filter.getFilterItems().add(new FilterItemInfo(filterField, splitBillId));
				view.setFilter(filter);
				coll = ContractCostSplitEntryFactory.getRemoteInstance().getContractCostSplitEntryCollection(view);
			} else if (splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)) {
				filterField = "parent.id";
				filter.getFilterItems().add(new FilterItemInfo(filterField, splitBillId));
				view.setFilter(filter);
				coll = ConChangeSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
			} else if (splitBillType.equals(CostSplitBillTypeEnum.SETTLEMENTSPLIT)) {
				filterField = "parent.id";
				filter.getFilterItems().add(new FilterItemInfo(filterField, splitBillId));
				view.setFilter(filter);
				coll = SettlementCostSplitEntryFactory.getRemoteInstance().getFDCSplitBillEntryCollection(view);
			} else {
				//������ֵ�,�Ժ��ṩ֧��
				return new FDCSplitBillEntryCollection();
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
				   	
		FDCSplitBillEntryCollection entrys=new FDCSplitBillEntryCollection();
		FDCSplitBillEntryInfo entry=null;
  	
		for (Iterator iter = coll.iterator(); iter.hasNext();)		{
			item = (AbstractBillEntryBaseInfo) iter.next();	
			item.setId(null);
			
			entry=(FDCSplitBillEntryInfo)createNewDetailData(kdtEntrys);							
			entry.putAll(item);	
			
			if(splitBillId!=null){
				entry.setSplitBillId(splitBillUuId);
			}
			entry.setCostBillId(costBillUuId);
			entrys.add(entry);
		}					
				
		return entrys;		
	}

	protected void importCostSplitContract(){
		loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT));		
	}
	
	
	/**
	 *����������� 
	 */
	protected void importCostSplitCntrChange(){

		//loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CONTRACTSPLIT));
		

//		BOSUuid costBillUuId=null;	//BOSUuid.read(costBillId);
				
		ConChangeSplitCollection coll=null;
		ConChangeSplitInfo item=null;  
		
			
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();		
    	filter.getFilterItems().add(new FilterItemInfo("contractChange.contractBill.id", contractBillId));
    	view.setFilter(filter);
		
    	SelectorItemCollection sic=view.getSelector();  
        sic.add(new SelectorItemInfo("contractChange.contractBill.id"));
    	
    	//view.getSorter().add(new SorterItemInfo("seq"));
    		    	
		try {
			if(splitBillType.equals(CostSplitBillTypeEnum.CNTRCHANGESPLIT)){
				coll = ConChangeSplitFactory.getRemoteInstance().getConChangeSplitCollection(view);
			}
			if (coll == null || coll.size() == 0) {
				return;
			}

			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				item = (ConChangeSplitInfo) iter.next();
				loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.CNTRCHANGESPLIT, item.getContractChange().getId()));
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}		
	}
	
	/**
	 * ������㵥���
	 * @author sxhong  		Date 2006-11-17
	 */
	protected void importCostSplitSettlement(){
		SettlementCostSplitCollection coll=null;
		SettlementCostSplitInfo item=null;  
			
		EntityViewInfo view = new EntityViewInfo();
		
		FilterInfo filter = new FilterInfo();		
    	filter.getFilterItems().add(new FilterItemInfo("settlementBill.contractBill.id", contractBillId));
    	view.setFilter(filter);
		
    	SelectorItemCollection sic=view.getSelector();  
        sic.add(new SelectorItemInfo("settlementBill.contractBill.id"));
		try {
			coll = SettlementCostSplitFactory.getRemoteInstance().getSettlementCostSplitCollection(view);
			if (coll == null || coll.size() == 0) {
				return;
			}

			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				item = (SettlementCostSplitInfo) iter.next();
				loadCostSplit(getCostSplitEntryCollection(CostSplitBillTypeEnum.SETTLEMENTSPLIT, item.getSettlementBill().getId()));
			}						
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}		
	}
	
	protected void setContractBillId(String billId){
		contractBillId=billId;
	}
	protected String getContractBillId(){
		return contractBillId;
	}
	
	protected void setSplitBillType(CostSplitBillTypeEnum type){
		splitBillType=type;
	}
	
	protected KDFormattedTextField getTotalTxt(){
		return txtAmount;
	}
	
	protected void splitByAimCostSplitScale() throws Exception {
    	Map dataMap = new HashMap();
    	dataMap.put("totalAmount", getTotalTxt().getBigDecimalValue());
		FDCSplitBillEntryCollection entrys = FDCAutoSplitHelper.splitByAimCostSplitScale(getEntrys(), dataMap);
		for(int i=0;i<entrys.size();i++){//���ϲ�����˳������
			FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)entrys.get(i);
			BigDecimal amount = entry.getAmount();
			kdtEntrys.getCell(i, "amount").setValue(amount);
			int rowIndex = i;
			int colIndex = kdtEntrys.getColumnIndex("amount");
			KDTEditEvent event=new KDTEditEvent(getDetailTable());
			event.setColIndex(colIndex);
			event.setRowIndex(rowIndex);
			event.setOldValue(null);
			event.setValue(amount);
			kdtEntrys_editStopped(event);
		}
	}
	
	/**
	 * �ж��Ƿ��ֹ�
	 * @return
	 */
	protected boolean isEmpty(){
		boolean isEmpty = true;
		for(int i=0;isEmpty&&i<getDetailTable().getRowCount();i++){
			IRow row = getDetailTable().getRow(i);
			BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue());
			if(amount.signum()!=0){
				isEmpty = false;
			}
		}
		return isEmpty;
	}
    
	/**
	 *  �������ռ�¼����(������ͬ����������ı�����ɱ���ǳɱ��඼Ҫ����)
	 */
	protected void dealWithEmptyDataBeforeSave(){
		if (CostSplitBillTypeEnum.CONTRACTSPLIT.equals(splitBillType)
				|| CostSplitBillTypeEnum.CNTRCHANGESPLIT.equals(splitBillType)
				|| (CostSplitBillTypeEnum.PAYMENTSPLIT.equals(splitBillType)&&editData.getBoolean("isConWithoutText"))) {
			boolean isEmpty = false;
			for(int i=0;!isEmpty&&i<getDetailTable().getRowCount();i++){
				IRow row = getDetailTable().getRow(i);
				BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue());
				if(amount.signum()==0){
					isEmpty = true;
				}
			}
			if(isEmpty){
				int ok = MsgBox.showConfirm2(this,"���ڹ������Ϊ  0  �ķ�¼���Ƿ�ɾ��֮�󱣴� ��");
				if(MsgBox.OK==ok){
					for(int i=getDetailTable().getRowCount()-1;i>=0;i--){
						IRow row = getDetailTable().getRow(i);
						BigDecimal amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue());
						if(amount.signum()==0){
							getDetailTable().removeRow(i);
						}
					}
				}
			}
		}		
	}
	
	protected void checkbeforeSave(){
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		BigDecimal amount;		
		
		//�����¼�ڵ����ӽ���ֶ�
		for(int i=0;i<getDetailTable().getRowCount();i++){
			row=getDetailTable().getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()!=0){
				continue;
			}
			
			amount=entry.getAmount();
			Object obj=row.getCell("amount").getValue();
			if(!(obj instanceof BigDecimal)&&isLimitCost){
	    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("mustInput"));
	    		SysUtil.abort();
			}
		}
		
		//��鸽�ӿ�Ŀ�Ļ��ܽ��ֹ�¼�룩�Ƿ����ϼ�������		
		BigDecimal amountTotal=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;		
		int level=0;
		
		for(int i=0; i<kdtEntrys.getRowCount()-1; i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()<0) continue;//�ܼ���
			//��ϸ������Ŀ
			if(entry.getCostAccount().getCurProject().isIsLeaf()
					&& !entry.getCostAccount().isIsLeaf()){		
				acct=entry.getCostAccount();
				proj=acct.getCurProject();				
				level=acct.getLevel();
								
				//���ܽ��
				if(entry.getAmount()!=null){
					amount=entry.getAmount();					
				}else{
					amount=FDCHelper.ZERO;
				}
				
				//��һ��Ŀ����Ϊ������ϸ��Ŀ
				row=kdtEntrys.getRow(i+1);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();				
				if(isAddlAcctLeaf(entry)){
					
					//����ۼ�
					amountTotal=FDCHelper.ZERO;

					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();	
						
						//ͬһ������Ŀ
						if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())){
							//��һ���ɱ���Ŀ
							if(entry.getCostAccount().getLevel()>level){
								if(entry.getCostAccount().getLevel()==level+1
										&& !isProdSplitLeaf(entry)
										&& entry.getAmount()!=null){
									amountTotal=amountTotal.add(entry.getAmount());									
								}
								
							}else{
								break;
							}
						}else{
							break;
						}						
					}
/*����������
				//���ȵĴ���
					BigDecimal subtract = amountTotal.subtract(amount);
					BigDecimal compareAmt1=new BigDecimal("0.1");
//					BigDecimal compareAmt2=new BigDecimal("-0.1");
*/					
//					if(subtract.abs().compareTo(compareAmt1)>0){
					if(amountTotal.compareTo(amount)!=0){
			    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("mustEqu"));
			    		SysUtil.abort();						
					}
				}								
			}
		}				
		checkTotalCostAmt();    	
    	editData.setState(FDCBillStateEnum.SAVED);
	}
	public void checkbeforeForChangeSave(){
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		BigDecimal amount;		
		
		//�����¼�ڵ����ӽ���ֶ�
		for(int i=0;i<getDetailTable().getRowCount();i++){
			row=getDetailTable().getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()!=0){
				continue;
			}
			
			amount=entry.getAmount();
			Object obj=row.getCell("amount").getValue();
			if(!(obj instanceof BigDecimal)&&isLimitCost){
				FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("mustInput"));
				SysUtil.abort();
			}
		}
		
		//��鸽�ӿ�Ŀ�Ļ��ܽ��ֹ�¼�룩�Ƿ����ϼ�������		
		BigDecimal amountTotal=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;		
		int level=0;
		
		for(int i=0; i<kdtEntrys.getRowCount()-1; i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()<0) continue;//�ܼ���
			//��ϸ������Ŀ
			if(entry.getCostAccount().getCurProject().isIsLeaf()
					&& !entry.getCostAccount().isIsLeaf()){		
				acct=entry.getCostAccount();
				proj=acct.getCurProject();				
				level=acct.getLevel();
				
				//���ܽ��
				if(entry.getAmount()!=null){
					amount=entry.getAmount();					
				}else{
					amount=FDCHelper.ZERO;
				}
				
				//��һ��Ŀ����Ϊ������ϸ��Ŀ
				row=kdtEntrys.getRow(i+1);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();				
				if(isAddlAcctLeaf(entry)){
					
					//����ۼ�
					amountTotal=FDCHelper.ZERO;
					
					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();	
						
						//ͬһ������Ŀ
						if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())){
							//��һ���ɱ���Ŀ
							if(entry.getCostAccount().getLevel()>level){
								if(entry.getCostAccount().getLevel()==level+1
										&& !isProdSplitLeaf(entry)
										&& entry.getAmount()!=null){
									amountTotal=amountTotal.add(entry.getAmount());									
								}
								
							}else{
								break;
							}
						}else{
							break;
						}						
					}
					/*����������
				//���ȵĴ���
					BigDecimal subtract = amountTotal.subtract(amount);
					BigDecimal compareAmt1=new BigDecimal("0.1");
//					BigDecimal compareAmt2=new BigDecimal("-0.1");
					 */					
//					if(subtract.abs().compareTo(compareAmt1)>0){
					if(amountTotal.compareTo(amount)!=0){
						FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("mustEqu"));
						SysUtil.abort();						
					}
				}								
			}
		}				
		checkTotalCostAmt();    	
		editData.setState(FDCBillStateEnum.SAVED);
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
		
		amount=getTotalTxt().getBigDecimalValue();
		if(amount==null){
			amount=FDCHelper.ZERO;
		}
		amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    	BigDecimal splitedAmount=txtSplitedAmount.getBigDecimalValue();
    	splitedAmount = FDCHelper.toBigDecimal(splitedAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
    	if(splitedAmount==null){
    		splitedAmount=FDCHelper.ZERO;
    	}
    	// ��Ϊ�������������������Ϊ0ʱ���Զ���Ϊȫ����֣�����ȥ������amount.compareTo(FDCHelper.ZERO)!=0
		//������÷�Ʊģʽ�������˷������ش�����&& isInvoiceMgr()������
		//���������÷�Ʊģʽ��У��ɱ�����ͬ�����ֿ��Ա��档added by andy_liu 2012-3-28 
		if (splitedAmount.compareTo(FDCHelper.ZERO) == 0 && isInvoiceMgr()) {
    		FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("notSplited"));
    		SysUtil.abort();
    	}else if(amount.compareTo(splitedAmount)>0){
    		if(editData instanceof ContractCostSplitInfo){
    			editData.setSplitState(CostSplitStateEnum.PARTSPLIT);
    		}else{
    			FDCMsgBox.showWarning(this,FDCSplitClientHelper.getRes("notAllSplit"));
        		SysUtil.abort();
    		}
    	}else if(amount.compareTo(splitedAmount)==0){
    		editData.setSplitState(CostSplitStateEnum.ALLSPLIT);
    		
    		//������ϸ������Ŀ�Ŀ�Ŀ�Ƿ��Ѳ��	//Jelon 	Dec 11, 2006
    		for(int i=0; i<kdtEntrys.getRowCount(); i++){
    			row=kdtEntrys.getRow(i);
    			entry=(FDCSplitBillEntryInfo)row.getUserObject();
    			
    			if(entry.getLevel()<0) continue;//�ܼ���
    			
    			if(entry.getLevel()==0 && entry.isIsLeaf()){
    				if(!entry.getCostAccount().getCurProject().isIsLeaf()){
    					
    					if(splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)){
        					editData.setSplitState(CostSplitStateEnum.PARTSPLIT);    
        					break;						
    					}else{
    						FDCMsgBox.showWarning(this,"�����ֵ�����ϸ�Ĺ�����Ŀ�ĳɱ���Ŀ!");
    		        		SysUtil.abort();
    					}
    				}
    			}    			
    		}
    	}else{
			// ���ܴ��ں�ͬ���
			FDCMsgBox.showWarning(this, FDCSplitClientHelper.getRes("moreThan"));
			SysUtil.abort();
    	}
    	editData.setState(FDCBillStateEnum.SAVED);
	}	

    private void setOtherRatioByAmount(int index){
    	FDCSplitBillEntryInfo topEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(index).getUserObject();    	
    	int level=topEntry.getLevel();		
        CostAccountInfo topAcct=topEntry.getCostAccount();

        FDCSplitBillEntryInfo entry=null;
        IRow row=null;
        ICell cell=null;
        String display=null;
        //int level=0;
        
        int idxTop=0;
        for(int i=index; i<kdtEntrys.getRowCount(); i++){
        	entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
        	
        	if(entry.getLevel()==level+1){
        		
        	}
        }

        for(int i=index; i<kdtEntrys.getRowCount(); i++){
        }        
    }
    
	/**
	 * �������Ƿ��Ʒ�����ϸ
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-30 <p>
	 */
    protected boolean isProdSplitLeaf(FDCSplitBillEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    

    
	/**
	 * �������Ƿ񸽼ӿ�Ŀ��ϸ�����Ƿ�����ϸ���̵���ϸ���ӿ�Ŀ
	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-11-30 <p>
	 */
    protected boolean isAddlAcctLeaf(FDCSplitBillEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsAddlAccount() 
    			&& entry.getCostAccount()!=null && entry.getCostAccount().isIsLeaf()
    			&& entry.getCostAccount().getCurProject()!=null && entry.getCostAccount().getCurProject().isIsLeaf()
    			&& !isProdSplitLeaf(entry)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }

	/* ���� Javadoc��
	 * @see com.kingdee.eas.framework.client.EditUI#removeByPK(com.kingdee.bos.dao.IObjectPK)
	 */
	protected void removeByPK(IObjectPK pk) throws Exception {
		super.removeByPK(pk);
/*//		destroyWindow();
//		this.getUIWindow().close();
//		setCursorOfDefault();
		actionExitCurrent_actionPerformed(null);
		if(true) return;
		
        // �쳣����Ҫɾ������Ŀ�Ѿ��������ˣ�����ʾ�û���
        // 2004-9-10 by Jerry
        
        // �ж��Ƿ������绥�⣬������ǰ״̬��Ȼ������ɾ��״̬��
        String tempState = this.getOprtState();
        this.setOprtState("REMOVE");
        IObjectValue val = (IObjectValue)getUIContext().get("CURRENT.VO") ;
        getUIContext().put("CURRENT.VO",null) ;
        setDataObject(val) ;

        try
        {
            this.getBizInterface().delete(pk);
        }
        finally
        {
            //�ָ�״̬��
            this.setOprtState(tempState);
        }
        this.idList.remove(pk.toString(), false);
        
        this.setOprtState(STATUS_ADDNEW);
        setDataObject(createNewData());
        loadFields();
        this.setOprtState(STATUS_VIEW);
        actionEdit.setEnabled(false);
        lockUIForViewStatus();
        setSave(true);
        setSaved(true);
        actionExitCurrent_actionPerformed(null);
        
        by jelon
    	if (idList.size() > 0)
        {
            if (actionNext.isEnabled())
            {
                actionNext_actionPerformed(null);
            }
            else
            {
                actionPre_actionPerformed(null);
            }
        }
        else
        {
        	//�������Զ�����	Jelon
            
            //editData.clear();
            this.setOprtState(STATUS_ADDNEW);
            setDataObject(createNewData());
            loadFields();
            this.setOprtState(STATUS_VIEW);
            if(idList.size() == 0)
            {
                actionEdit.setEnabled(false);
            }
            lockUIForViewStatus();
            
        	//disposeUIWindow();
        	//SysUtil.abort();
        	//this.disposeUIWindow();
        	this.setOprtState(STATUS_VIEW);
//        	this.getUIWindow().close();
        	return;
        }
        setSave(true);
        setSaved(true);
*/
	}
    
	
    private void addDebugWin(){
        menuBiz.add(new AbstractHidedMenuItem("ctrl shift alt F11") {
            public void action_actionPerformed() {
                try {
            		KDDialog diag=new KDDialog();
            		diag.setSize(400,300);
            		diag.setLocation(300, 100);
            		KDTextArea txt=new KDTextArea();
            		txt.setLineWrap(false);
            		diag.getContentPane().add(new JScrollPane(txt));
            		String s="id:"+editData.getId();
            		txt.setText(s+"\n");
//            		txt.select(3, s.length());
            		diag.setVisible(true);
            		logger.info(s);
            		txt.append("������ϸ��Ϣ:\n\n");
            		Enumeration keys = editData.keys();
            		for(;keys.hasMoreElements();){
            			Object e = keys.nextElement();
            			logger.info(e+":"+editData.getString(e.toString()));
            			txt.append(e+":"+editData.getString(e.toString()));
            			txt.append("\n");
            		}
            		

                } catch (Exception e) {
                    SysUtil.abort(e);
                }
//                setDisplayHideColumn(); //���Ե�ʱ���
            }
        });
        
        menuBiz.add(new AbstractHidedMenuItem("ctrl shift alt F12") {
            public void action_actionPerformed() {
                try {
                	Object obj=SysContext.getSysContext().getProperty("splitDedug");
                	if(obj instanceof Boolean){
                		boolean b=((Boolean)obj).booleanValue();
                		SysContext.getSysContext().setProperty("splitDedug", Boolean.valueOf(!b));
                	}else{
                		SysContext.getSysContext().setProperty("splitDedug", Boolean.TRUE);
                	}

                } catch (Exception e) {
                    SysUtil.abort(e);
                }
//                setDisplayHideColumn(); //���Ե�ʱ���
            }
        });
    }
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sic=super.getSelectors();
    	setSelectorsEntry(sic,false);
    	return sic;
    }

	/**
	 * ��������ַ�¼����
	 * ���ڸ����ֵ��ø÷�������Ƶ��������Ӱ�����ܣ����Ը�Ϊ����ķ�ʽ��
	 * ��δ����༭�����������˷�¼����ɾ����¼���¼����δ��ĳ��� Added by Owen_wen 2012-11-15
	 * 
	 * ���û��������⣬�������bug���˴����û���������Ѿ��������ͻ��ᵥ���⣬δ������ȫ�������ٴ��޸�
	 * 
	 *������bug�� ��ͬ��ֽ��棬ѡ���Զ���֣���ʱ��ֽ���������ڶ���ѡ��ĩ����֡���ʱ���ֲ�ֽ��Ϊ�ա�

	 * @return
	 * @author:jelon 
	 * ����ʱ�䣺2006-12-26 <p>
	 */
    protected FDCSplitBillEntryCollection getEntrys(){
		entrysMap.clear();
		entrys = new FDCSplitBillEntryCollection();
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			FDCSplitBillEntryInfo info = (FDCSplitBillEntryInfo) kdtEntrys.getRow(i).getUserObject();
			// entrysMap.put(String.valueOf(info.getSeq()), info);
			String key = getEntrysMapKey(info);
			entrysMap.put(key, info);
			entrys.add(info);
		}
    	
    	return entrys;
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
		String key = entryInfo.getSeq() + "";

		return key;
	}

	/**
	 * ����¼���ʱ�򸽼ӿ�Ŀ�����ϻ���
	 * @author sxhong  		Date 2007-4-4
	 * @param prj	Ҫ���ܵĿ�Ŀ�Ĺ�����Ŀ
	 * @param acct	���ܳɱ���Ŀ
	 * @param amount	�䶯���
	 * @param end		�䶯(�ֹ�¼��)�����е�λ��
	 */
    protected void totAddlAcct(CurProjectInfo prj,CostAccountInfo acct,BigDecimal amount,int end) {
		IRow row=null;
    	CurProjectInfo curPrj=null;
		CostAccountInfo curAcct=null;
		BigDecimal sum=null; 
    	for (int i = end-1; i >=0 ; i--) {
			row = getDetailTable().getRow(i);
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)(row.getUserObject());
			if(entry.getLevel()==0){
				break;
			}
			if(!entry.isIsAddlAccount()){
				continue;
			}
			
			curAcct=entry.getCostAccount();
			curPrj=entry.getCostAccount().getCurProject();
			//�����ϼ�������Ŀ����ͬ��Ŀ,ע:�ó��������ж�
			if(prj.getParent()!=null&&prj.getParent().getId().equals(curPrj.getId())
					&&acct.getLongNumber().equals(curAcct.getLongNumber())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
			}
//			������ͬ������Ŀ���ϼ���Ŀ,���ݹ鴦��
			if(prj.getId().equals(curPrj.getId())
					&&acct.getParent()!=null
					&&acct.getParent().getId().equals(curAcct.getId())){
				if(entry.getAmount()==null){
					sum=FDCHelper.ZERO;
				}else{
					sum=amount.add(entry.getAmount());
				}
				entry.setAmount(sum);
				row.getCell("amount").setValue(sum);
				
				totAddlAcct(curPrj,curAcct,amount,i);
			}
		}

	}
    public void afterActionPerformed(ActionEvent e) {
    	super.afterActionPerformed(e);
    	if(e.getSource()==btnRemove||e.getSource()==menuItemRemove){
    		if(hasRemove){
        		try {
        			setOprtState(OprtState.VIEW);
    				actionExitCurrent_actionPerformed(null);
    			} catch (Exception e1) {
    				handUIExceptionAndAbort(e1);
    			}
    		}
    	}
    }
    
    protected void initCtrlListener(){
		//�������delete�¼�
		getDetailTable().setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					for (int i = 0; i < getDetailTable().getSelectManager().size(); i++)
					{
						KDTSelectBlock block = getDetailTable().getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int amount_index=getDetailTable().getColumnIndex("amount");
								int directAmt_index=getDetailTable().getColumnIndex("directAmt");
								int price_index=getDetailTable().getColumnIndex("price");
								int workLoad_index=getDetailTable().getColumnIndex("workLoad");
								//����в���������л��ߵ�Ԫ�������˵Ļ�����ȡ���¼�
								if((colIndex!=amount_index&&colIndex!=directAmt_index&&colIndex!=price_index&&colIndex!=workLoad_index)||(getDetailTable().getCell(rowIndex, colIndex).getStyleAttributes().isLocked())) {
									e.setCancel(true);
									continue;
								}
								try
								{
									getDetailTable().getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									kdtEntrys_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, 
											rowIndex, colIndex,false,1));
								} catch (Exception e1)
								{
									handUIExceptionAndAbort(e1);
								}
							}
//							e.setCancel(true);
						}
					}

				}
				else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
/*					int col=getDetailTable().getSelectManager().getActiveColumnIndex();
					int row=getDetailTable().getSelectManager().getActiveRowIndex();
					if(col<0||row<0||getDetailTable().getCell(row, col).getStyleAttributes().isLocked()){
						e.setCancel(true);
					}*/
//					e.setCancel(true);
					getDetailTable().putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
		getDetailTable().setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					getDetailTable().putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable��KDTEditListener���ڱ༭��ʱ�򴥷���
		 * KDTPropertyChangeListener������ɾ����ճ���ȵ��µ�Ԫ��value�����仯��ʱ�򶼻ᴥ����
		 */
		getDetailTable().addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // ���嵥Ԫ��ֵ�����仯
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(getDetailTable().getClientProperty("ACTION_PASTE")!=null){
			    		//����editStop�¼�
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		KDTEditEvent event=new KDTEditEvent(getDetailTable());
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = getDetailTable().getCell(rowIndex,colIndex);
			    		if(cell==null){
			    			return;
			    		}
			    		event.setValue(cell.getValue());
			    		try {
			    			kdtEntrys_editStopped(event);			    			
			    		} catch (Exception e1) {
			    			handUIExceptionAndAbort(e1);
			    		}
			    	}
			    }
			}
		});
//		getDetailTable().getSortMange().setEnableSortable(false);
	}
    

	public IObjectPK runSave() throws Exception {
		return getBizInterface().save(editData);
	}

	protected void attachListeners() {
		addDataChangeListener(txtSplitedAmount);		
	}

	protected void detachListeners() {
		removeDataChangeListener(txtSplitedAmount);
	}
	protected void initListener() {
		//��ֲ�ʹ��ѡ��selectchange�Լ���ͷ����Ĺ���
//		super.initListener();
	}	
	protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		actionAudit.setVisible(true);
    		actionUnAudit.setVisible(true);
    		actionAudit.setEnabled(true);
    		actionUnAudit.setEnabled(true);
    		
    		FDCBillInfo bill = (FDCBillInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getState())){
    	    		actionUnAudit.setVisible(true);
    	    		actionUnAudit.setEnabled(true);   
    	    		actionAudit.setVisible(false);
    	    		actionAudit.setEnabled(false);
    			}else{
    	    		actionUnAudit.setVisible(false);
    	    		actionUnAudit.setEnabled(false);   
    	    		actionAudit.setVisible(true);
    	    		actionAudit.setEnabled(true);
    			}
    		}
    	}else {
    		actionAudit.setVisible(false);
    		actionUnAudit.setVisible(false);
    		actionAudit.setEnabled(false);
    		actionUnAudit.setEnabled(false);
    	}
    	if(!actionAudit.isVisible()&&!actionUnAudit.isVisible()){
    		this.menuBiz.setVisible(false);
    	}else{
    		this.menuBiz.setVisible(true);
    	}
    	btnAudit.setVisible(actionAudit.isVisible());
    	btnUnAudit.setVisible(actionUnAudit.isVisible());
    }
	
	protected void updateButtonStatus() {
		
		super.updateButtonStatus();
		// ���������ɱ����ģ���������ɾ����
		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
			actionAddNew.setVisible(false);
			actionEdit.setVisible(false);
			actionRemove.setVisible(false);
		}
		
	}
	
	protected boolean isMeasureContract(){
		return false;
	}

	private boolean isAdjustVourcherMode=false;
	/***
	 * ��ģʽ����ۿ��ΥԼ
	 */
	private boolean isSimpleFinancialExtend = false;
	/***
	 * ��ģʽ
	 */
	private boolean isSimpleFinancial = false;
	/**
	 * ��ģʽ����Ʊ
	 */
	private boolean isSimpleInvoice = false;
	/**
	 * ����ģʽ
	 */
	private boolean isFinacial = false;
	
	/**
	 * �������븶�����
	 */
	private boolean isWorkLoadSeparate=false;
	/**
	 * ��ν���
	 */
	private boolean isMoreSetter = false;
	/**
	 * ֻ��ʹ�ð��������
	 */
	private boolean isSplitBaseOnProp = false;
	
	private boolean isImportConSplit=false;
	protected  void fetchInitData() throws Exception{		
		super.fetchInitData();
		//���񼶲���
		String companyId = company.getId().toString();
		HashMap paramMap = FDCUtils.getDefaultFDCParam(null, companyId);
		isAdjustVourcherMode=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_ADJUSTVOURCHER);
		isSimpleFinancial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIAL);
		isSimpleFinancialExtend = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEFINACIALEXTEND);
		isSimpleInvoice = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SIMPLEINVOICE);
		isFinacial = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_FINACIAL);
		isWorkLoadSeparate = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		invoiceMgr = FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_INVOICEMRG);
		isMoreSetter=FDCUtils.getParamValue(paramMap, FDCConstants.FDC_PARAM_MORESETTER);
		
		//�ɱ�������
		String orgUnitId = orgUnitInfo.getId().toString();
		isLimitCost = FDCUtils.getBooleanValue4FDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_LIMITCOST);
		isSplitBaseOnProp = FDCUtils.getBooleanValue4FDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_SPLITBASEONPROP);
		isImportConSplit = FDCUtils.getBooleanValue4FDCParamByKey(null, orgUnitId, FDCConstants.FDC_PARAM_IMPORTCONSPLIT); 
	}
	
	protected boolean isImportConSplit(){
		return isImportConSplit;
	}
	protected boolean isSplitBaseOnProp(){
		return isSplitBaseOnProp;
	}
	protected boolean isSimpleFinancialExtend(){
		return isSimpleFinancialExtend;
	}
	protected boolean isSimpleFinancial(){
		return isSimpleFinancial;
	}
	protected boolean isSimpleInvoice(){
		return isSimpleInvoice;
	}
	protected boolean isAdjustVourcherModel(){
		return isAdjustVourcherMode;
	}
	protected boolean isFinacial(){
		return isFinacial;
	}
	/**
	 * �������븶�����
	 * @return
	 */
	protected boolean isWorkLoadSeparate(){
		return isWorkLoadSeparate;
	}
	
	/**
	 * ���÷�Ʊ����
	 */
	private boolean invoiceMgr=false;
	/**
	 * ���÷�Ʊ����
	 */
	protected boolean isInvoiceMgr(){
		return invoiceMgr;	
	}
	protected boolean isMoreSetter(){
		return isMoreSetter;
	}
	/**
	 * �����ֿ�Ŀ�Ľ���ܶ�Ӧ��Ŀ�Ѳ�ֳɱ���������
	 */
	private boolean isLimitCost=true;
	protected boolean isLimitCost(){
		return isLimitCost;
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		//��ֲ��ñ������
	}
	
	private void registerMeasureDefaultSplitTypeSetKey() {
		String actionName="MeasureDefaultSplitTypeSetUI";
		this.getActionMap().put(actionName, new javax.swing.AbstractAction(){
			public void actionPerformed(ActionEvent e) {
                	actionSave.setVisible(true);
                	actionSave.setEnabled(true);
                	setOprtState(OprtState.EDIT);
                	setCursorOfDefault();
            }
		});
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ctrl shift alt F10"), actionName);
		
	}
	

	
	//by tim_gao 2012-03-12 ��Ϊ�ڴ��� ��ͬ �����Ӧ�ɿ�Ŀ�ķ�¼���������ʱ��
	                       //�������п���һ����һ����ͣѭ�������⣬�����ٴ�����Щ�����ʱ��Ҫ����������
	                       //����һ��һ���ڿͻ����ߵ��á�������ܣ��ȶԿ�Ŀ�ķ�¼����ֵ���Լ�֧��������ѡ���ֶ���ת���ã��������±仯
	
	//�Ƿ���ݿ�Ŀ���ӽ���Ӧ��Ŀ
	protected boolean isMerge = false;
	
	
	public boolean isMerge() {
		return isMerge;
	}

	public void setMerge(boolean isMerge) {
		this.isMerge = isMerge;
	}

	private Map enumEntrySelecMap  = null;
	//����������Բ�ͬ�ĺ�ͬ������ͣ��õ���Ӧ���ݷ�¼����Ҫ��ֵ��MAP  
	/*  key->costBillTypeEnum : value ->Map (fdcentrykey ,entrykey)  */

	public Map getEnumEntrySelecMap() {
		return enumEntrySelecMap;
	}

	public void setEnumEntrySelecMap(Map enumEntrySelecMap) {
		this.enumEntrySelecMap = enumEntrySelecMap;
	}
	protected void addEnumEntrySelecMap(CostSplitBillTypeEnum type,Map map){
		if(null==enumEntrySelecMap){
			enumEntrySelecMap = new HashMap();
		}
		if(null!=enumEntrySelecMap.get(type)){
			removeEnumEntrySelec(type);
		}
		enumEntrySelecMap.put(type, map);
	}
	
	protected void removeEnumEntrySelec(CostSplitBillTypeEnum type){
		if(null!=enumEntrySelecMap){
			enumEntrySelecMap.remove(type);
		}
	}
	
	private Map enumContractIdsMap  = null;
	//����������Բ�ͬ�ĺ�ͬ������ͣ��õ���Ӧ��ͬID��S  
	/*  key->costBillTypeEnum : value ->Map (costSplitId,contractId)  */
	public Map getEnumContractIdsMap() {
		return enumContractIdsMap;
	}

	public void setEnumContractIdsMap(Map enumContractIdsMap) {
		this.enumContractIdsMap = enumContractIdsMap;
	}
	protected void addEnumContractIdsMapWithRemove(CostSplitBillTypeEnum type,Set sets){
		if(null==enumContractIdsMap){
			enumContractIdsMap = new HashMap();
		}
		if(null!=enumContractIdsMap.get(type)){
			removeEnumContractIdsMap(type);
		}
		enumContractIdsMap.put(type, sets);
	}
	
	protected void addEnumContractIdsMap(CostSplitBillTypeEnum type,Set sets){
		if(null==enumContractIdsMap){
			enumContractIdsMap = new HashMap();
		}
		Set temp = (Set)enumContractIdsMap.get(type);
		if(null!=temp){
			for(Iterator it = sets.iterator();it.hasNext();){
				String id = it.next().toString();
				temp.add(id);
			}
			removeEnumContractIdsMap(type);
		}else{
			temp = sets;
			
		}
		enumContractIdsMap.put(type, temp);
	}
	
	protected void removeEnumContractIdsMap(CostSplitBillTypeEnum type){
		if(null!=enumContractIdsMap){
			enumContractIdsMap.remove(type);
		}
	}
	
	private Map splitIdsMap  = null;
	//����������Բ�ͬ�ĺ�ͬ������ͣ��õ���Ӧ��ͬID��S  
	/*  key->costBillTypeEnum : value ->Map (costSplitId,contractId)  */
	public Map getSplitIdsMap() {
		return splitIdsMap;
	}

	public void setSplitIdsMap(Map splitIdsMap) {
		this.splitIdsMap = splitIdsMap;
	}
	protected void addSplitIdsMapWithRemove(CostSplitBillTypeEnum type,Set sets){
		if(null==splitIdsMap){
			splitIdsMap = new HashMap();
		}
		if(null!=splitIdsMap.get(type)){
			removeSplitIdsMap(type);
		}
		splitIdsMap.put(type, sets);
	}
	
	protected void addSplitIdsMap(CostSplitBillTypeEnum type,Set sets){
		if(null==splitIdsMap){
			splitIdsMap = new HashMap();
		}
		Set temp = (Set)splitIdsMap.get(type);
		if(null!=temp){
			for(Iterator it = sets.iterator();it.hasNext();){
				String id = it.next().toString();
				temp.add(id);
			}
			removeSplitIdsMap(type);
		}else{
			temp = sets;
			
		}
		splitIdsMap.put(type, temp);
	}
	
	protected void removeSplitIdsMap(CostSplitBillTypeEnum type){
		if(null!=splitIdsMap){
			splitIdsMap.remove(type);
		}
	}
	
	/**
	 * ���������Ϊ��װ��Ҫ��ת�ͷ�¼���ֵ,��Ӧ�ں�̨ȡ�ĸ�ֵװ�ؽ�ȥ
	 * д�ڵ��ú�̨��ǰ�����󴫽�ȥ,һ��Ҫ��д,����Բ�ͬ�ķ�¼װ��ֵ
	 * 
	 * 
	 * ��ʵ�ǰ�ԭ�в�ֵ�loadCostSplit�жӶ�Ӧ���߼���д����map
	 * �������Ҫ�ڲ�ͬ�Ĳ���а�ֵ��׼�����һ��Ҫ��д
	 */
	protected void setTransSelector(){
		
		Map temp = this.getEnumEntrySelecMap();
		Map transSelector = new HashMap();
		if(temp==null){
			temp =new  HashMap();
		}
		transSelector.put("amount", null);
		transSelector.put("price", null);
		transSelector.put("workLoad", null);
		temp.put(CostSplitBillTypeEnum.CNTRCHANGESPLIT, transSelector);
		temp.put(CostSplitBillTypeEnum.CONTRACTSPLIT, transSelector);
		this.setEnumEntrySelecMap(temp);
	}
	/**
	 * ʹ��ǰҪ��װ����MAP
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	protected void loadCostSplitBatch(boolean isMerge,SelectorItemCollection entrySel) throws EASBizException, BOSException{
		setTransSelector();
		
		loadCostSplit(getCostSplitEntryColsBatchIds(getEnumContractIdsMap(),isMerge,entrySel,getSplitIdsMap(),getEnumEntrySelecMap(),createNewDetailData(this.kdtEntrys)));
	}
	
	public void loadFields() {
		super.loadFields();
		
		try {
			updateEntryProgramming();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * 
    * <p>Title: </p>
    * <p>Description: </p>
    * <p>Company: </p> 
    * @author 
     * @throws BOSException 
    * @date
     */
    public void updateEntryProgramming() throws BOSException{
    	IProgrammingContracCost IProgrammingContracCost = ProgrammingContracCostFactory.getRemoteInstance();
    	for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			
			Object value = row.getCell("costAccount.id").getValue();
			if(UIRuleUtil.isNull(value)){
				continue;
			}
			row.getCell("programming").getStyleAttributes().setBackground(row.getCell("costAccount.name").getStyleAttributes().getBackground());
			if(UIRuleUtil.isNull(row.getCell("costAccount.curProject.number").getValue())){
				row.getCell("programming").getStyleAttributes().setLocked(true);
				row.getCell("programming").setValue("");
			}else{
				row.getCell("programming").getStyleAttributes().setBackground(Color.white);
			}
			String oql = "select contract.id,contract.name,contract.number where costAccount.id='"+value+"' and contract.programming.isLatest=1 and contract.programming.state='4AUDITTED'";
			
			Set<String> idset = new HashSet<String>();
			idset.add("999");
			ProgrammingContractInfo info = null;
			ProgrammingContracCostCollection programmCostColl = IProgrammingContracCost.getProgrammingContracCostCollection(oql);
			for (int j = 0; j < programmCostColl.size(); j++) {
				ProgrammingContracCostInfo costInfo = programmCostColl.get(j);
				ProgrammingContractInfo contract = costInfo.getContract();
				
				if(contract==null)
					continue;
				info = contract;
				idset.add(info.getId().toString());
			}
			if(row.getCell("programming").getValue() instanceof ProgrammingContractInfo){
				ProgrammingContractInfo value2 = (ProgrammingContractInfo)row.getCell("programming").getValue();
				if(UIRuleUtil.isNull(value2)){
					row.getCell("programming").setValue(info);
				}
//				else{
//					if(!idset.contains(value2.getId().toString()))
//						row.getCell("programming").setValue(null);
//				}
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("id",idset,CompareType.INCLUDE));
			view.setFilter(filInfo);
			
			KDBizPromptBox kdtEntrys_programming_PromptBox = new KDBizPromptBox();
			kdtEntrys_programming_PromptBox.setQueryInfo("com.kingdee.eas.fdc.contract.app.F7ProgrammingContractQuery");
			kdtEntrys_programming_PromptBox.setVisible(true);
			kdtEntrys_programming_PromptBox.setEditable(true);
		    kdtEntrys_programming_PromptBox.setDisplayFormat("$number$");
		    kdtEntrys_programming_PromptBox.setEditFormat("$number$");
		    kdtEntrys_programming_PromptBox.setCommitFormat("$number$");
		    kdtEntrys_programming_PromptBox.setEntityViewInfo(view);
		    KDTDefaultCellEditor kdtEntrys_programming_CellEditor = new KDTDefaultCellEditor(kdtEntrys_programming_PromptBox);
			row.getCell("programming").setEditor(kdtEntrys_programming_CellEditor);
		}
    }
	
	/**
	 * ������ ȡ�óɱ���ַ�¼����
	 * 
	 * @param enumContractIdsMap
	 * @param flag
	 * @param sels
	 * @param splitIdsMap
	 * @param tranSelector
	 * @param Class
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @author RD_skyiter_wang
	 * @createDate 2014-1-9
	 */
	protected FDCSplitBillEntryCollection getCostSplitEntryColsBatchIds(Map enumContractIdsMap,
			boolean flag,SelectorItemCollection sels,Map splitIdsMap,Map tranSelector,IObjectValue Class) throws EASBizException, BOSException{
		// ////////////////////////////////////////////////////////////////////////////

		logger.info("===========================================================");
		logger.info("FDCSplitBillEditUI.getCostSplitEntryColsBatchIds(),start");

		// ////////////////////////////////////////////////////////////////////////////

		if (sels == null) {
			sels = setSelectorsEntry(null, true);
		}

		// ////////////////////////////////////////////////////////////////////////////

		long startTime = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		long startUsedMemory = runtime.totalMemory() - runtime.freeMemory();

		// ////////////////////////////////////////////////////////////////////////////

		FDCSplitBillEntryCollection fDCSplitBillEntryCollection = (FDCSplitBillEntryCollection) PaymentSplitFacadeFactory
				.getRemoteInstance().getPaymentSplitEntryByParam(enumContractIdsMap, isMerge(), sels, splitIdsMap,
						tranSelector, Class);

		// ////////////////////////////////////////////////////////////////////////////

		long endTime = System.currentTimeMillis();
		double exeTime = 1.0 * (endTime - startTime) / 1000;

		long endUsedMemory = runtime.totalMemory() - runtime.freeMemory();
		double exeUsedMemory = 1.0 * (endUsedMemory - startUsedMemory) / (8 * 1024);

		logger.info("enumContractIdsMap:" + enumContractIdsMap);
		logger.info("coll.size:" + fDCSplitBillEntryCollection.size());
		logger.info("exeTime:" + exeTime + " ��");
		logger.info("exeUsedMemory:" + exeUsedMemory + " KB");

		// ////////////////////////////////////////////////////////////////////////////

		logger.info("FDCSplitBillEditUI.getCostSplitEntryColsBatchIds(),end");
		logger.info("===========================================================");

		return fDCSplitBillEntryCollection;
	}
}