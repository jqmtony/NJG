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
 * FDC 拆分基类
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
    
	/** 一体化参数值 */
	protected Map initParam = null;
	private Map parentMap = new HashMap();

	/**
	 * kdtEntrys 分录表上的拆分数据集，使用延迟加载解决性能问题
	 */
	private FDCSplitBillEntryCollection entrys = null;
	
	//分录bosid-->分录对象...added by ken_liu..现在的逻辑是用缓存的entrys,保存后再编辑会出现问题，因此用map以便在编辑时找回缓存数据修改
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
    	//处理分录保存顺序
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
//    	格式化日期
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
        	//释放新增锁，添加对象锁
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
		//处理分录保存顺序
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
		//    	格式化日期
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
			//释放新增锁，添加对象锁
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
    	//简单的删除
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
            //恢复状态。
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

            //FDCMsgBox.showInfo(this,"没有选中分录，无法删除！");
            return;
        }

        //[begin]进行删除分录的提示处理。
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
            	
            	//查找最后一行
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
            	
            	//从最后一行向前删除，直至Level=0
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
        

		//拆分组号		jelon 12/28/2006
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
		//检查单据是否在工作流中
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
		
		//选择科目
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
			//从UIContext中获得当前ID
			String costBillId = (String)map.get("ID");
			CurProjectInfo curProjectInfo = null;
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("curProject.id");
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",costBillId));
			view.setFilter(filter);
			//如果为拆分合同ID
			ContractCostSplitCollection coll = ContractCostSplitFactory
				.getRemoteInstance().getContractCostSplitCollection(view);
			CurProjectInfo curProject = null;
			if(coll.size()==1){
				ContractCostSplitInfo splitInfo = coll.get(0);
				curProject = splitInfo.getCurProject();
			}
			//如果为无文本合同付款拆分ID
			if(coll.size()==0){
				PaymentSplitCollection colls = PaymentSplitFactory
					.getRemoteInstance().getPaymentSplitCollection(view);
				if(colls.size()==1){
					PaymentSplitInfo withoutInfo = colls.get(0);
					curProject = withoutInfo.getCurProject();
				}else if(colls.size()==0){
					//如果为合同变更拆分ID
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
			
			//获得本合同拆分所在工程信息，放入UIContext，传递至选择科目
			UIContext uiContext = new UIContext(this); 
			uiContext.put("curProject",curProject);
			if (contractBillId != null) {
				uiContext.put("contractBillId", contractBillId);
			}
			/************* 作废合同重新拆分 *************/
			uiContext.put("txtCostBillNumber", txtCostBillNumber.getText());
			/************* 作废合同重新拆分 *************/
			// 复杂模式：工程量与付款拆分不显示可拆分选项
			if (isFinacial()) {
				uiContext.put("isFinacial", Boolean.TRUE);
			}
//			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)map.get("node");
			//如果为变更新增，则可以直接从CURRENT.VO中获得CurProjectInfo
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
//			//从uiContext中获得在FDCSplitListUI中保存的节点信息
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
		// 在财务一体化复杂模式下做此操作 删除非明细科目
		removeParentCostAccount(accts);
		
		for(Iterator iter=accts.iterator(); iter.hasNext();){
			acct = (CostAccountInfo)iter.next();
			
			//判断科目是否存在
			isExist=false;
			for(int i=0; i<kdtEntrys.getRowCount(); i++){			
				entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
								
				//允许选择在其他拆分方案中已存在的科目		jelon 12/6/06
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
				
				//拆分组号	jelon 12/27/2006
				groupIndex++;
				entry.setIndex(groupIndex);
				
				row=addEntry(entry);
				setDisplay(row.getRowIndex());

			}				
		}
		setMenuSplitState();
		// 将直接金额的背景色设置成白色
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
			// 在启用财务一体化复杂模式下做此判断
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
	 * 设置自动匹配拆分和按比例拆分按钮状态
	 * 
	 * @description
	 * @author 向晓帆
	 * @createDate 2011-9-28
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	public void setMenuSplitState() {
		// 新的成本科目编码集合
		List newCostAccountLongNumber = new ArrayList();
		// 判断是否工程量拆分和付款拆分打开
		String className = getUIContext().get("Owner").getClass().getName();
		if (className.equals(WorkLoadSplitListUI.class.getName()) || className.equals(PaymentSplitListUI.class.getName())
				|| className.equals(WorkLoadConfirmBillListUI.class.getName()) || className.equals(WorkLoadConfirmBillEditUI.class.getName())) {
			// 遍历新形成表格存入新的成本科目编码
			String longNumber = null;
			PaymentSplitEntryInfo info = null;
			for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
				info = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
				// 判断当前是否成本科目
				if (info.getCostAccount() instanceof CostAccountInfo) {
					newCostAccountLongNumber.add(info.getCostAccount().getLongNumber());
				}
			}
			// 判断旧成本科目编码和新成本科目编码是否一致
			if (!oldCostAccountLongNumber.containsAll(newCostAccountLongNumber)) {
				this.getUIContext().put("isCanEnable", Boolean.FALSE);
				PaymentSplitEntryInfo tmpInfo = null;
				// 遍历新的表格用于判断是否全都是最明细科目
				for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
					tmpInfo = (PaymentSplitEntryInfo) kdtEntrys.getRow(i).getUserObject();
					// 判断当前行是否成本科目
					if (tmpInfo.getCostAccount() instanceof CostAccountInfo) {
						// 判断是否最明细成本科目，如果不是就设置按钮可编辑状态到工程量拆分和付款拆分
						if (!tmpInfo.getCostAccount().isIsLeaf()) {
							this.getUIContext().put("isCanEnable", Boolean.FALSE);
							return;
						} 
					}
				}
			}
			
		}

	}
	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitBotUp_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitBotUp_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSplitBotUp_actionPerformed(arg0);
		splitCost(CostSplitTypeEnum.BOTUPSPLIT);
		updateEntryProgramming();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitProd_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitProd_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSplitProd_actionPerformed(arg0);
		

		splitCost(CostSplitTypeEnum.PRODSPLIT);
		updateEntryProgramming();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.AbstractFDCSplitBillEditUI#actionSplitProj_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSplitProj_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSplitProj_actionPerformed(arg0);
		
		splitCost(CostSplitTypeEnum.PROJSPLIT);
		updateEntryProgramming();
	}
	
	
	/* （非 Javadoc）
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
		actionImpContrSplit.setEnabled(false);//只有变更拆分内用到
		actionImpContrSplit.setVisible(false);

		
		//btnRemove.setVisible(false);
		//把附件管理隐藏掉--不隐藏了
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
	 * 描述：设置分摊标准（全部）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
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
				//引入合同拆分比例时计算表头已拆分
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
    			//项目展开跟产品展开应该进行区分 by sxhong 2009/02/05
    			if(source!=null&&!source.isCollapse()&&source.isHasChildren()){
    				//将其下级所有的+变成-
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
					//下一个分配树
					break;	
				}
				
				//编码、名称
				if(entry.getCostAccount().getCurProject()!=null){
					//编码
					row.getCell("costAccount.curProject.number").setValue(
							entry.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
					row.getCell("costAccount.number").setValue(
							entry.getCostAccount().getLongNumber().replace('!','.'));

					//名称
					if(level==0){
						row.getCell("costAccount.curProject.name").setValue(
								entry.getCostAccount().getCurProject().getDisplayName().replace('_','\\'));
						row.getCell("costAccount.name").setValue(
								entry.getCostAccount().getDisplayName().replace('_','\\'));
						
					}else if(entry.getSplitType()==CostSplitTypeEnum.PRODSPLIT && entry.isIsLeaf()){
						//产品拆分明细
						row.getCell("costAccount.curProject.number").setValue("");
						row.getCell("costAccount.number").setValue("");
						row.getCell("costAccount.curProject.name").setValue("");
						row.getCell("costAccount.name").setValue("");
						
					}else if(entry.isIsAddlAccount()){
						//附加科目，直接分配
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
					
					
					//测试树形
					if(level>=topLevel){
						CellTreeNode node = new CellTreeNode();
						node.addClickListener(nodeClickListener);			
						cell=row.getCell("costAccount.curProject.name");			
						// 节点的值
						node.setValue(cell.getValue());
						// 是否有子节点
						//if(entry.getCostAccount().getLongNumber().equals(topAcct.getLongNumber()) && !entry.getCostAccount().getCurProject().isIsLeaf()){
						/*if(entry.getCostAccount().getLongNumber().replace('!','.').equals(topAcct.getLongNumber().replace('!','.')) 
								&& !entry.getCostAccount().getCurProject().isIsLeaf()
								&& !isProdSplitLeaf(entry)){*/
						/*
						 * 屏蔽设置是否有孩子节点 by 29 // if (!entry.isIsLeaf() // &&
						 * !entry.getCostAccount().getCurProject().isIsLeaf() //
						 * && //
						 * entry.getCostAccount().getLongNumber().replace('!',
						 * // '.').equals( //
						 * topAcct.getLongNumber().replace('!', '.'))) { //
						 * node.setHasChildren(true); // } else { // //
						 * node.setHasChildren(false); // }
						 */
						
						//node.setHasChildren(!entry.isIsAddlAccount());
						// 节点的树级别
						node.setTreeLevel(entry.getLevel());
						cell.getStyleAttributes().setLocked(false);
						cell.setValue(node);
						
						if(level!=topLevel){
							node = new CellTreeNode();
							node.addClickListener(nodeClickListener);			
							cell=row.getCell("costAccount.name");			
							// 节点的值
							node.setValue(cell.getValue());
							/*
							 * 屏蔽设置是否有孩子节点 by 29// 是否有子节点 //
							 * node.setHasChildren(!entry.getCostAccount(). //
							 * isIsLeaf() // || (!entry.isIsLeaf() && //
							 * entry.getCostAccount().isIsLeaf() && //
							 * entry.getSplitType()!=null && //
							 * entry.getSplitType() //
							 * .equals(CostSplitTypeEnum.PRODSPLIT)) );
							 */
							// 节点的树级别
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
								
								
				//颜色
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
					//非科目行不能编辑 by hpw 2010-06-25
					row.getCell("splitScale").getStyleAttributes().setLocked(true);
					
					//附加科目处理（允许录入金额）
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
				//直接归属
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
			// 取下级有非空情况需要判断
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
        //归属标准
        setStandard(rowIndex);
    }
    
    
	/**
	 * 描述：设置分摊标准
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
	 */
    private void setStandard(int index){
    	FDCSplitBillEntryInfo curEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(index).getUserObject();    
    	
    	int level=curEntry.getLevel();	
    	
    	//1. 拆分根据节点，使用拆分类型作为归属标准
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
    	
		//2. 其他拆分结点，使用父级的分摊类型作为归属标准
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
						row.getCell("standard").setValue("直接分配");
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
		//产品类型		
		EntityViewInfo view = new EntityViewInfo();		
		FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id",new HashSet(initDirectMap.values()),CompareType.INCLUDE));
    	//filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("IsAccObj", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("curProject.id");
    	view.getSelector().add("productType.*"); 		//使用“*”，用于列表中的数据和分录中的数据匹配
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
			//空行
			ProductTypeInfo prod=new ProductTypeInfo();
			prod.setName(null);
			//prod.setName("否");
	        collProd.insertObject(-1,prod);		
	        
	        //当前项目全部产品
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
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...见变量说明
				if (entrysMap.get(key) != null) {//modified by ken_liu...见变量说明
					// modified by zhaoqin on 2013/11/09 start, 录入金额时，NullPointException
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
				//拆分比例
				if(entry.getLevel()==0){
					if(FDCHelper.toBigDecimal(txtAmount.getBigDecimalValue()).compareTo(FDCHelper.ZERO)!=0){
						splitScale=FDCHelper.divide(FDCHelper.multiply(amount, FDCHelper.ONE_HUNDRED), txtAmount.getBigDecimalValue(),10,BigDecimal.ROUND_HALF_UP);
					}
					entry.setSplitScale(splitScale);
					row.getCell("splitScale").setValue(splitScale);
				}else{
					row.getCell("splitScale").setValue(null);
				}
				//分摊
				/*CostSplitType splitType=entry.getSplitType();
				calcApportionAmount(e.getRowIndex(),splitType);*/				
				apptAmount(e.getRowIndex());	
				
				
				//汇总
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}else if(entry.isIsLeaf() && isAddlAcctLeaf(entry)){
					//用下面的代码替换了,观察看有没有问题
					/*
					
					//附加明细科目录入金额后进行比较转换		Jelon	Dec 12, 2006
					
					int idx=e.getRowIndex();
					int idx1=idx;
					int idx2=idx;
					
					int level=entry.getLevel();
					
					IRow row=null;
					
					//最后一项
					for(int i=idx+1; i<kdtEntrys.getRowCount(); i++){
						row=kdtEntrys.getRow(i);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						
						if(entry.getLevel()==level){
							idx2=i;						
							
						}else if(entry.getLevel()<level){
							break;
						}
					}
					
					//向前遍历到父级
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

							//检查明细科目和是否等于上级科目的金额
							if(amountTotal.compareTo(amount)==0){
								
								//分摊合计为100
								amount=new BigDecimal(100);								
								if(amountTotal.compareTo(FDCHelper.ZERO)==0){
									amount=FDCHelper.ZERO;	
								}else{
									amountTotal=amountTotal.divide(new BigDecimal(100),4,BigDecimal.ROUND_HALF_EVEN);									
								}
								entry.setOtherRatioTotal(amount);
								row.getCell("otherRatioTotal").setValue(amount);
											
								//各分摊明细的百分比
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
				
				// modified by zhaoqin on 2013/11/09, 应调用统一的方法
				// String key = entry.getCostAccount().getId().toString() + String.valueOf(entry.getSeq());
				String key = getEntrysMapKey(entry);
								
				//if (entrysMap.get(String.valueOf(entry.getSeq())) != null) {//modified by ken_liu...见变量说明
				if (entrysMap.get(key) != null) {//modified by ken_liu...见变量说明
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
				//汇总
				if(entry.getLevel()==0){
					row.setUserObject(entry); // modified by zhaoqin for R130910-0152 on 2013/9/22
					calcAmount(0);
					
				}
			}
		}
		
		//附加科目汇总
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
		//附加产品
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
		//加条件，否则末级拆分点击成本科目时归属金额可以录入，导致金额错误
		if(editData.getBoolean("isMeasureSplit")){
			handleMeasureCalc(e, row);
		}
	}

	/**
	 * 量价合同计算逻辑
	 * @param e
	 * @param row
	 */
	protected void handleMeasureCalc(KDTEditEvent e, final IRow row) {
		//量价汇总
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
	 * 量价合同控制逻辑
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
	 * 描述：分摊金额（调用FDCCostSplit接口）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    protected void apptAmount(int rowIndex){
    	FDCSplitBillEntryInfo topEntry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();
    	
    	//修改调用接口	jelon 12/26/2006
		/*CostSplitType splitType=entry.getSplitType();
		calcApportionAmount(rowIndex,splitType);
		
		//调整金额
		adjustAmount(rowIndex);*/
		
				
		//fdcCostSplit.apptAmount((IObjectCollection)editData.get("entrys"),entry);
		fdcCostSplit.apptAmount(getEntrys(),topEntry);
		
		int level=topEntry.getLevel();
		IRow row=null;
		boolean isMeasureContract=isMeasureContract();
		
		Object value = kdtEntrys.getCell(rowIndex, "amount").getValue();
		//已分摊总金额
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
	 * 描述：设置分摊标准（全部）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-10-19 <p>
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
		
		//计算拆分总金额
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
		 * //变更0，变更结算非0导致未拆分不显示
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
		//选择行

        if ((kdtEntrys.getSelectManager().size() == 0)
                || isTableColumnSelected(kdtEntrys))
        {
            FDCMsgBox.showInfo(this, "没有选中分录，无法设置拆分方案！");
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
        //拆分对象
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
		        
        //拆分类型
		CostSplitTypeEnum splitType=topEntry.getSplitType();
		
		
		boolean isTrue=true;	
		
		if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){			//产品拆分	
			if(!isProdSplitEnabled(topEntry)){
				//FDCMsgBox.showInfo(this,"当前分录无法设置产品分摊方案！");			
				FDCMsgBox.showWarning(this,"所选分录不符合产品拆分条件,请选择明细分录进行操作");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)){	//自动拆分	
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
				//FDCMsgBox.showInfo(this,"当前分录无法设置自动拆分方案！");		
				FDCMsgBox.showWarning(this, "所选分录不符合自动拆分条件,请选择一级非明细分录进行操作");
				return;
			}
			
		}else if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){	//末级拆分	
			if(topEntry.getSplitType()!=null && !topEntry.getSplitType().equals(CostSplitTypeEnum.MANUALSPLIT)){
				//if(topEntry.getSplitType().equals(CostSplitType.PROJSPLIT)){
				if(!topEntry.getSplitType().equals(CostSplitTypeEnum.BOTUPSPLIT)){
					//isTrue=false;
										
					//将当前的自动拆分转换成末级拆分	jelon 12/6/06
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
				//FDCMsgBox.showInfo(this,"当前分录无法设置末级拆分方案！");		
				FDCMsgBox.showWarning(this, "所选分录不符合末级拆分条件,请选择一级非明细分录进行操作");
				return;
			}
		}
				
		//topEntry.setSplitType(costSplitType);
        
        int level=0;

		
		//----------------------------------------------------------------------------------------
		//准备参数
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
		//拆分设置UI
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

		//返回值
		entrys =(FDCSplitBillEntryCollection) ((CostSplitApptUI) uiWin.getUIObject()).getData() ;

		//		for (int i = 0; i < entrys.size(); i++) {
		//			if (entrys.get(i).getLevel() > 1) {
		//				entrys.get(i).setLevel(1);
		//			}
		//		}

		//----------------------------------------------------------------------------------------
		//删除原来的拆分
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
		
		//成本科目
		CostAccountInfo acct=null;
		acct=entrys.get(0).getCostAccount();
					
		//拆分类型
		splitType=costSplitType;	//CostSplitType.BOTUPSPLIT;
				
		//分摊类型
		ApportionTypeInfo apportionType;
		apportionType = entrys.get(0).getApportionType();  
		
		//附加科目
		boolean isAddlAcct=entrys.get(0).isIsAddlAccount();
		
		topEntry.setSplitType(splitType);
		topEntry.setApportionType(apportionType);	
		topEntry.setIsLeaf(false);			
		topEntry.setProduct(null);
		
		topRow.getCell("standard").setValue(splitType.toString());
		topRow.getCell("product").setValue("");
		topRow.getCell("product").getStyleAttributes().setLocked(true);
		
		//调试　begin
		if(apportionType!=null){
			topRow.getCell("apportionType.name").setValue(apportionType.getName());
		}
		topRow.getCell("splitType").setValue(splitType);
		//调试　end
		
		IRow row;				
		
		//产品拆分：删除全部拆分项
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
		
		
		
		
		//插入新的拆分行
		int idxCurr=topIdx;
		
		for(int i=1; i<entrys.size(); i++){
			entry=entrys.get(i);				

			//拆分组号	jelon 12/27/2006
			entry.setIndex(topEntry.getIndex());
						
			//entry.setSplitType(splitType);
			if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
				//项目拆分中包含的产品拆分
			}else{
				entry.setSplitType(splitType);
			}
			
			
			if(costSplitType.equals(CostSplitTypeEnum.PRODSPLIT)){
				//项目拆分中包含的产品拆分
				entry.setIsAddlAccount(isAddlAcct);
			}
									
			idxCurr++;
			row=insertEntry(idxCurr,entry);			
			
			row.getCell("costAccount.curProject.name").setValue(entry.getCostAccount().getCurProject().getName());
			row.getCell("costAccount.name").setValue(entry.getCostAccount().getName());	
		}
		
		//----------------------------------------------------------------------------------------
		
		//计算汇总数	
		//calcApportionData(topIdx,costSplitType);	//使用新接口　jelon 12/26/2006
		totApptValue(topIdx);

		//分摊成本
		//calcApportionAmount(topIdx,costSplitType);	//使用新接口　jelon 12/26/2006
		apptAmount(topIdx);
			

		//设置显示
		index=topIdx;
		
		//产品拆分，从拆分树的根节点开始设置
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
	 * 描述：汇总分摊指标（调用FDCCostSplit接口）
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
	 */
    private void totApptValue(int rowIndex){
    	FDCSplitBillEntryInfo entry = (FDCSplitBillEntryInfo)kdtEntrys.getRow(rowIndex).getUserObject();

    	//修改调用接口	jelon 12/26/2006
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
		// entry.getCostAccount().isIsLeaf() 取消 ，对飞明细可进行产品拆分。
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
		//单据的第一层属性全部加上
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
				FDCMsgBox.showInfo(this,"已经引入了合同拆分，不能重复引入！");
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
	
	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillEditUI#onLoad()
	 */
	public void onLoad() throws Exception {
		if (getUIContext().get("contractBillId") != null) {
			contractBillId = getUIContext().get("contractBillId").toString();
		}
		super.onLoad();	
		registerMeasureDefaultSplitTypeSetKey();
		//控制输入金额的格式，只能输入数字
		getDetailTable().getColumn("amount").setEditor(FDCSplitClientHelper.getTotalCellNumberEdit());
		getDetailTable().getColumn("price").setEditor(getCellNumberEdit());
		getDetailTable().getColumn("workLoad").setEditor(getCellNumberEdit());
        
		//getDetailTable().getColumn("directAmount").setEditor(FDCSplitClientHelper.getCellNumberEdit());
		
//		FDCTableHelper.setPaseMode(getDetailTable());
		((KDTTransferAction) getDetailTable().getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
		//焦点到了最后一行时，不自动新增行
		disableAutoAddLine(kdtEntrys);	 
		initCtrlListener();
		
		this.actionViewCostInfo.setVisible(false);
		
		//拆分组号		jelon 12/27/2006
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
        	//添加锁
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
		//修改拆分的小数位，以免由于小数位导致拆分不能完全，影响范围全部拆分,拆分实际只支持两位小数
		getTotalTxt().setValue(FDCHelper.toBigDecimal(getTotalTxt().getBigDecimalValue(),2));
		this.editData.setAmount(FDCHelper.toBigDecimal(FDCHelper.toBigDecimal(this.editData.getAmount(),2)));
		/** 保存原有的付款拆分分录 */
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
				// 添加锁
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
				setDisplayHideColumn(); //调试的时候打开
			}
		}
//		setDisplayHideColumn(); //调试的时候打开
		kdtEntrys.setColumnMoveable(true);
		super.onShow();
    	if (OprtState.VIEW.equals(getOprtState())) {
    		//查看时的一些状态设定
    		//分录不可编辑
//    		getDetailTable().setEditable(false);
//    		getDetailTable().setEnabled(false);
    		getDetailTable().getStyleAttributes().setLocked(true);
    		
    		//按钮的状态
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
    	
    	
		//工作流菜单暂时隐藏
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
			/** 保存原有的付款拆分分录 */
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
     * 显示隐藏的列，用于调试
     * 如果在元数据内设置了隐藏，而在代码内又设置隐藏的话则会在表格的属性页内
     * 显示隐藏的列名，故通过在元数据内设置隐藏然后通过显示设置显示隐藏列的方
     * 式来达到调试的目的
     * @author sxhong  		Date 2006-12-1
     */
    protected void setDisplayHideColumn()
	{
    	//隐藏多余的列
    	int column_index=getDetailTable().getColumnIndex("costAccount.curProject.id");
    	for(int i=column_index;i<getDetailTable().getColumnCount();i++){
    		getDetailTable().getColumn(i).getStyleAttributes().setHided(false);
    	}
		
	}
	protected FDCSplitBillEntryCollection getCostSplitEntryCollection(CostSplitBillTypeEnum splitBillType){
/*		String csotBillId=contractBillId;
		BOSUuid costBillUuId=BOSUuid.read(csotBillId);*/
		//合同已被当做默认值
		return getCostSplitEntryCollection(splitBillType, null);
	}
	
	protected void loadCostSplit(FDCSplitBillEntryCollection entrys){
		FDCSplitBillEntryInfo entry=null;
		for (Iterator iter = entrys.iterator(); iter.hasNext();) {
			entry = (FDCSplitBillEntryInfo) iter.next();	
			
/*			//直接费用
			entry.setDirectAmount(amount);
			entry.setDirectAmountTotal(amount);*/
			
			//金额：变更拆分，不用设置金额
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
			//拆分组号		jelon 12/28/2006
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
	 * 通过传入的拆分单据的类型(变更拆分，结算拆分等)及对应的合同ID得到拆分分录
	 * @param splitBillType	
	 * @param costBillUuId	拆分单据的CostBillUuid，如结算BOSUuid，变更BOSUuid等
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
				//其它拆分单,以后提供支持
				return new FDCSplitBillEntryCollection();
			}
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
    	
		FDCSplitBillEntryCollection entrys = encapsulateSettlementSplitEntrys(costBillUuId, coll);					
				
		return entrys;		
	}

	/**
	 * 将AbstractObjectCollection封装为FDCSplitBillEntryCollection返回
	 * @param costBillUuId 拆分单据的id
	 * @param coll AbstractObjectCollection数据
	 * @return 封装后的FDCSplitBillEntryCollection
	 * @Author：owen_wen
	 * @CreateTime：2012-11-15
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
	 * 通过传入的拆分单据的类型(变更拆分，结算拆分等)及对应的拆分ID得到拆分分录
	 * @param splitBillType	
	 * @param costBillUuId	拆分单据的CostBillUuid，如结算BOSUuid，变更BOSUuid等
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
				//其它拆分单,以后提供支持
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
	 *引入变更单拆分 
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
	 * 引入结算单拆分
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
		for(int i=0;i<entrys.size();i++){//集合不存在顺序问题
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
	 * 判断是否拆分过
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
	 *  描述：空记录处理(包括合同，变更，无文本付款，成本类非成本类都要处理)
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
				int ok = MsgBox.showConfirm2(this,"存在归属金额为  0  的分录，是否删除之后保存 ？");
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
		
		//处理分录内的增加金额字段
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
		
		//检查附加科目的汇总金额（手工录入）是否与上级金额相等		
		BigDecimal amountTotal=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;		
		int level=0;
		
		for(int i=0; i<kdtEntrys.getRowCount()-1; i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()<0) continue;//总计行
			//明细工程项目
			if(entry.getCostAccount().getCurProject().isIsLeaf()
					&& !entry.getCostAccount().isIsLeaf()){		
				acct=entry.getCostAccount();
				proj=acct.getCurProject();				
				level=acct.getLevel();
								
				//汇总金额
				if(entry.getAmount()!=null){
					amount=entry.getAmount();					
				}else{
					amount=FDCHelper.ZERO;
				}
				
				//后一科目必须为附加明细科目
				row=kdtEntrys.getRow(i+1);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();				
				if(isAddlAcctLeaf(entry)){
					
					//金额累加
					amountTotal=FDCHelper.ZERO;

					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();	
						
						//同一工程项目
						if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())){
							//下一级成本科目
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
/*代码有问题
				//精度的处理
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
		
		//处理分录内的增加金额字段
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
		
		//检查附加科目的汇总金额（手工录入）是否与上级金额相等		
		BigDecimal amountTotal=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;		
		int level=0;
		
		for(int i=0; i<kdtEntrys.getRowCount()-1; i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()<0) continue;//总计行
			//明细工程项目
			if(entry.getCostAccount().getCurProject().isIsLeaf()
					&& !entry.getCostAccount().isIsLeaf()){		
				acct=entry.getCostAccount();
				proj=acct.getCurProject();				
				level=acct.getLevel();
				
				//汇总金额
				if(entry.getAmount()!=null){
					amount=entry.getAmount();					
				}else{
					amount=FDCHelper.ZERO;
				}
				
				//后一科目必须为附加明细科目
				row=kdtEntrys.getRow(i+1);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();				
				if(isAddlAcctLeaf(entry)){
					
					//金额累加
					amountTotal=FDCHelper.ZERO;
					
					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();	
						
						//同一工程项目
						if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())){
							//下一级成本科目
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
					/*代码有问题
				//精度的处理
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
	 * 检查 成本拆分 汇总金额
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
    	// 因为加了新需求，如果变更金额为0时，自动变为全部拆分，所以去掉条件amount.compareTo(FDCHelper.ZERO)!=0
		//如果启用发票模式不会进入此方法。特此增加&& isInvoiceMgr()条件。
		//这样不启用发票模式，校验成本，合同付款拆分可以保存。added by andy_liu 2012-3-28 
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
    		
    		//检查非明细工程项目的科目是否已拆分	//Jelon 	Dec 11, 2006
    		for(int i=0; i<kdtEntrys.getRowCount(); i++){
    			row=kdtEntrys.getRow(i);
    			entry=(FDCSplitBillEntryInfo)row.getUserObject();
    			
    			if(entry.getLevel()<0) continue;//总计行
    			
    			if(entry.getLevel()==0 && entry.isIsLeaf()){
    				if(!entry.getCostAccount().getCurProject().isIsLeaf()){
    					
    					if(splitBillType.equals(CostSplitBillTypeEnum.CONTRACTSPLIT)){
        					editData.setSplitState(CostSplitStateEnum.PARTSPLIT);    
        					break;						
    					}else{
    						FDCMsgBox.showWarning(this,"必须拆分到最明细的工程项目的成本科目!");
    		        		SysUtil.abort();
    					}
    				}
    			}    			
    		}
    	}else{
			// 不能大于合同金额
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
	 * 描述：是否产品拆分明细
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
	 */
    protected boolean isProdSplitLeaf(FDCSplitBillEntryInfo entry){
    	boolean isTrue=false;
    	
    	if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
    		isTrue=true;
    	}
    	
    	return isTrue;
    }
    

    
	/**
	 * 描述：是否附加科目明细，即是否是明细工程的明细附加科目
	 * @return
	 * @author:jelon 
	 * 创建时间：2006-11-30 <p>
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

	/* （非 Javadoc）
	 * @see com.kingdee.eas.framework.client.EditUI#removeByPK(com.kingdee.bos.dao.IObjectPK)
	 */
	protected void removeByPK(IObjectPK pk) throws Exception {
		super.removeByPK(pk);
/*//		destroyWindow();
//		this.getUIWindow().close();
//		setCursorOfDefault();
		actionExitCurrent_actionPerformed(null);
		if(true) return;
		
        // 异常处理：要删除的项目已经不存在了，则提示用户。
        // 2004-9-10 by Jerry
        
        // 判断是否有网络互斥，保存以前状态，然后设置删除状态。
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
            //恢复状态。
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
        	//不允许自动新增	Jelon
            
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
            		txt.append("单据详细信息:\n\n");
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
//                setDisplayHideColumn(); //调试的时候打开
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
//                setDisplayHideColumn(); //调试的时候打开
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
	 * 描述：拆分分录集。
	 * 由于付款拆分调用该方法过于频繁，严重影响性能，所以改为缓存的方式。
	 * 但未处理编辑过程中增加了分录，又删除分录后分录总数未变的场景 Added by Owen_wen 2012-11-15
	 * 
	 * 调用缓存有问题，详见建发bug，此处采用缓存的问题已经多次引起客户提单问题，未测试完全不建议再次修改
	 * 
	 *《建发bug： 合同拆分界面，选择自动拆分，此时拆分结果正常。第二次选择末级拆分。此时发现拆分结果为空》

	 * @return
	 * @author:jelon 
	 * 创建时间：2006-12-26 <p>
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
	 * 描述：取得分录Map对应的Key
	 * 
	 * @param entryInfo
	 * @return
	 * @author：skyiter_wang
	 * @createDate：2013-10-31
	 */
	protected String getEntrysMapKey(FDCSplitBillEntryInfo entryInfo) {
		String key = entryInfo.getSeq() + "";

		return key;
	}

	/**
	 * 用于录入的时候附加科目的向上汇总
	 * @author sxhong  		Date 2007-4-4
	 * @param prj	要汇总的科目的工程项目
	 * @param acct	汇总成本科目
	 * @param amount	变动金额
	 * @param end		变动(手工录入)金额的行的位置
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
			//设置上级工程项目的相同科目,注:用长编码来判断
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
//			设置相同工程项目的上级科目,并递归处理
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
		//处理键盘delete事件
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
								//如果列不是上面的列或者单元格锁定了的话，则取消事件
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
		 * KDTable的KDTEditListener仅在编辑的时候触发，
		 * KDTPropertyChangeListener则是在删除，粘贴等导致单元格value发生变化的时候都会触发。
		 */
		getDetailTable().addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // 表体单元格值发生变化
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(getDetailTable().getClientProperty("ACTION_PASTE")!=null){
			    		//触发editStop事件
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
		//拆分不使用选择selectchange以及表头排序的功能
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
		// 如果是虚体成本中心，则不能增、删、改
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
	 * 简单模式处理扣款和违约
	 */
	private boolean isSimpleFinancialExtend = false;
	/***
	 * 简单模式
	 */
	private boolean isSimpleFinancial = false;
	/**
	 * 简单模式处理发票
	 */
	private boolean isSimpleInvoice = false;
	/**
	 * 复杂模式
	 */
	private boolean isFinacial = false;
	
	/**
	 * 工程量与付款分离
	 */
	private boolean isWorkLoadSeparate=false;
	/**
	 * 多次结算
	 */
	private boolean isMoreSetter = false;
	/**
	 * 只能使用按比例拆分
	 */
	private boolean isSplitBaseOnProp = false;
	
	private boolean isImportConSplit=false;
	protected  void fetchInitData() throws Exception{		
		super.fetchInitData();
		//财务级参数
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
		
		//成本级参数
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
	 * 工程量与付款分离
	 * @return
	 */
	protected boolean isWorkLoadSeparate(){
		return isWorkLoadSeparate;
	}
	
	/**
	 * 启用发票管理
	 */
	private boolean invoiceMgr=false;
	/**
	 * 启用发票管理
	 */
	protected boolean isInvoiceMgr(){
		return invoiceMgr;	
	}
	protected boolean isMoreSetter(){
		return isMoreSetter;
	}
	/**
	 * 付款拆分科目的金额受对应科目已拆分成本金额的限制
	 */
	private boolean isLimitCost=true;
	protected boolean isLimitCost(){
		return isLimitCost;
	}
	
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		//拆分不用编码规则
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
	

	
	//by tim_gao 2012-03-12 因为在处理 合同 与其对应可科目的分录这种问题的时候
	                       //经常是有可能一个套一个不停循环的问题，所以再处理这些问题的时候要批量来处理
	                       //不能一个一个在客户端走调用。针对性能，比对科目的分录增加值，以及支持在子类选择字段做转型用，做了以下变化
	
	//是否根据科目叠加金额到对应科目
	protected boolean isMerge = false;
	
	
	public boolean isMerge() {
		return isMerge;
	}

	public void setMerge(boolean isMerge) {
		this.isMerge = isMerge;
	}

	private Map enumEntrySelecMap  = null;
	//用来放入针对不同的合同拆分类型，得到相应单据分录所需要的值的MAP  
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
	//用来放入针对不同的合同拆分类型，得到相应合同ID的S  
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
	//用来放入针对不同的合同拆分类型，得到相应合同ID的S  
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
	 * 这个方法是为了装载要到转型分录里的值,对应在后台取哪个值装载进去
	 * 写在调用后台的前面的最后传进去,一定要重写,以针对不同的分录装载值
	 * 
	 * 
	 * 其实是把原有拆分的loadCostSplit中队对应的逻辑，写入了map
	 * 所以如果要在不同的拆分中把值对准，这个一定要重写
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
	 * 使用前要填装两个MAP
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
	 * 描述： 取得成本拆分分录集合
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
		logger.info("exeTime:" + exeTime + " 秒");
		logger.info("exeUsedMemory:" + exeUsedMemory + " KB");

		// ////////////////////////////////////////////////////////////////////////////

		logger.info("FDCSplitBillEditUI.getCostSplitEntryColsBatchIds(),end");
		logger.info("===========================================================");

		return fDCSplitBillEntryCollection;
	}
}