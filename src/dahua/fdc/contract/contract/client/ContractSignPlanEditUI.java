/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.ContractSignPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractSignPlanFactory;
import com.kingdee.eas.fdc.contract.ContractSignPlanInfo;
import com.kingdee.eas.fdc.contract.client.ContracClientHepler.SumRowBase;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractSignPlanEditUI extends AbstractContractSignPlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractSignPlanEditUI.class);
    
    private final String ACCTNUMBER_COL = "acctNumber";
    private final String ACCTNAME_COL = "acctName";
    private final String AIMCOST_COL = "aimCost";
    
    private final String ACCAMOUNT_COL = "accAmount";
    private final String NUMBER_COL = "number";
    private final String NAME_COL   = "name" ;
    
    private final String SIGNAMOUNT_COL = "signAmount";
    private final String SIGNDATE_COL   = "signDate";
    private final String TYPE_COL       = "type";
    
    private final String LEVEL_COL = "level";
    private final String ISLEAF_COL = "isLeaf";
    private final String ACCOUNT_COL = "account";
    
    private final String ACCTID_COL = "acctID";
    
    private static Color preTax = new Color(0xD2E3CA);
	private static Color aftTax = new Color(0xE3EFDE);
    
    private int MAX_LEVEL = 1 ;
    
    CurProjectInfo project = null ;
    AimCostCollection aimCostCols = null;
    /**
     * output class constructor
     */
    public ContractSignPlanEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        
        for(int i = 0; i < kdtEntry.getRowCount(); ++i)
        {
        	IRow row = kdtEntry.getRow(i);
        	
        	/**
        	 * 标记标记标记标记标记标记标记标记标记标记标记
        	 * @author ling_peng
        	 */
        	Integer level = (Integer)(row.getCell(this.LEVEL_COL).getValue());
        	
        	
        	
        	if(row.getCell(this.NUMBER_COL).getValue() == null)
        	{
        		row.getCell(this.ACCOUNT_COL).setValue(Boolean.valueOf(true));
        	}
        	else
        	{
        		row.getCell(this.ACCOUNT_COL).setValue(Boolean.valueOf(false));
        	}
        	/*
        	 * @author ling_peng
        	 * 此处有可能抛出 NullPointException 异常:当用户没有选择分录中的任何一行的时候，上文标记处的
        	 * level 变量就是一个Null。
        	 */
        	row.setTreeLevel(level.intValue()-1);
        	Boolean isRootRow = (Boolean)(row.getCell(this.ACCOUNT_COL).getValue());
        	if(isRootRow.booleanValue())
        	{
        		row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
        	}
        }
        
        kdtEntry.getTreeColumn().setDepth(MAX_LEVEL);
        
        loadAimCost(editData.getProject());
        
        this.actionAddLine.setEnabled(false);
    }

    /**
     * output actionAddLine_actionPerformed
     */
    public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddLine_actionPerformed(e);
        int index = kdtEntry.getRowCount()-1;
        
        afterAddOrInsertRow(index);
    }
    /**
     * output actionInsertLine_actionPerformed
     */
    public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
    {
    	/**
    	 * @author ling_peng
    	 * 在插入分录的时候如果是用户没有指定一个插入位置的话就会默认地按照基类的处理新增到最后一行去，
    	 * 可是这样一来就会有很多的问题。首先是“this.level”为Null（见111行附近）然后是新的一行的所有
    	 * 单元格得到的值都是Null，这肯定是不行的。程序到处都报 NullPointException ，故在插入分录之前
    	 * 进行判断，只要是没有指定位置的插入都不被允许。事实上师父也说了，沿用基类的 actionInsertLine_actionPerformed
    	 * 方法的话（如果是用户没有指定一个插入位置的话就会默认地按照基类的处理新增到最后一行去）也只是
    	 * 针对我们的分录KDTable没有一条记录来说的。所以我按照如下途径进行处理。
    	 */
    	
    	int activeRowIndex=this.kdtEntry.getSelectManager().getActiveRowIndex();
    	if(activeRowIndex==-1){
    		MsgBox.showWarning("行插入失败，请指定要插入行的位置！");
    		SysUtil.abort();
    	}
    	
        super.actionInsertLine_actionPerformed(e);
        int index = kdtEntry.getSelectManager().getActiveRowIndex()+1;
        
        afterAddOrInsertRow(index);
    }

    /**
     * output actionRemoveLine_actionPerformed
     */
    public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
    {
    	int index = kdtEntry.getSelectManager().getActiveRowIndex();
    	
    	IRow row = kdtEntry.getRow(index);
    	if(Boolean.valueOf(true).equals((Boolean)(row.getCell(this.ACCOUNT_COL).getValue())))
    	{
    		MsgBox.showWarning("固有科目行不能删除");
    		SysUtil.abort();
    	}
    	
    	if(row.getCell(this.SIGNAMOUNT_COL).getValue() != null)
    	{
    		BigDecimal changeValue = (BigDecimal)(row.getCell(this.SIGNAMOUNT_COL).getValue());
    		changeValue = FDCHelper.ZERO.subtract(changeValue);
    		
    		updateSignAmount(index, changeValue, Boolean.valueOf(false));
    	}
        
        upDateTable();
        
        super.actionRemoveLine_actionPerformed(e);
       
    }

    protected ICoreBase getBizInterface() throws Exception 
    {
		return ContractSignPlanFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() 
	{
		return this.kdtEntry;
	}

	protected void attachListeners() 
	{
		kdtEntry.addKDTEditListener(kdtEntryAdapter);
	}

	protected void detachListeners() 
	{
		kdtEntry.removeKDTEditListener(kdtEntryAdapter);
	}

	protected KDTextField getNumberCtrl() 
	{
		return this.txtNumber;
	}
	protected IObjectValue createNewData() 
	{
		ContractSignPlanInfo info = new ContractSignPlanInfo();
		
		info.setOrgUnit((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit());
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		if(getUIContext().get("curProject") != null)
		{
			info.setProject((CurProjectInfo)(getUIContext().get("curProject")));
			project = (CurProjectInfo)(getUIContext().get("curProject"));
		}
		info.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
		
		try {
			info.setCreateTime(FDCDateHelper.getServerTimeStamp());
			info.setLastUpdateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) 
	{
		ContractSignPlanEntryInfo entryInfo = new ContractSignPlanEntryInfo();
		return entryInfo;
	}
	protected void initWorkButton() 
	{
		super.initWorkButton();
	
		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setEnabled(false);
		this.actionCopyFrom.setVisible(false);
		
		this.actionAttachment.setEnabled(false);
		this.actionAttachment.setVisible(false);
		this.actionWorkFlowG.setEnabled(false);
		this.actionWorkFlowG.setVisible(false);
		
		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);
		
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);
		this.actionLast.setEnabled(false);
		this.actionLast.setVisible(false);
		
		this.actionWorkflowList.setEnabled(false);
		this.actionWorkflowList.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCreateFrom.setVisible(false);
		
		this.actionCreateTo.setEnabled(false);
		this.actionCreateTo.setVisible(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		
		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		this.actionCalculator.setEnabled(false);
		this.actionCalculator.setVisible(false);
		
		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);
	}
	public void onLoad() throws Exception 
	{
		super.onLoad();
		initHeadStyle();
		initTableStyle();
		
		chkMenuItemSubmitAndAddNew.setEnabled(false);
		chkMenuItemSubmitAndAddNew.setVisible(false);
		
		if(STATUS_ADDNEW.equals(getOprtState()))
		{
			loadCostAccountToTable(getDetailTable());
			setAllRowsTreeLevel();
			loadAimCost(this.project);
		}
		else
		{
			setAllRowsTreeLevel();
			loadAimCost(editData.getProject());
			displayInsertContract();
		}
	
		setSubStractAmount();
		
		kdtEntry.getTreeColumn().setDepth(MAX_LEVEL);
	}
	protected void initHeadStyle()
	{
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		
		this.contProject.setEnabled(false);
		this.prmtProject.setEnabled(false);
		this.contOrgUnit.setEnabled(false);
		this.prmtOrgUnit.setEnabled(false);
	
		this.contCreator.setEnabled(false);
		this.prmtCreator.setEditable(false);
		this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEditable(false);
		
		this.contLastUpdateUser.setEnabled(false);
		this.prmtLastUpdateUser.setEnabled(false);
		this.contLastUpdateTime.setEnabled(false);
		this.pkLastUpdateTime.setEnabled(false);
		
		this.contAuditor.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.contAuditTime.setEnabled(false);
		this.pkAuditTime.setEnabled(false);
		
		remove(btnAddLine);
		remove(btnInsertLine);
		remove(btnRemoveLine);
		
		this.contkdEntry.removeAllButton();
		
		this.contkdEntry.addButton(btnAddLine);
		this.contkdEntry.addButton(btnInsertLine);
		this.contkdEntry.addButton(btnRemoveLine);
		
		this.actionAddLine.setEnabled(false);
		
		if(STATUS_VIEW.equals(getOprtState()))
		{
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
	}
	protected void initTableStyle()
	{
		this.kdtEntry.checkParsed();
		this.kdtEntry.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		kdtEntry.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		
		getDetailTable().getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_MERGE);
		FDCHelper.formatTableNumber(getDetailTable(), this.AIMCOST_COL);
		FDCHelper.formatTableNumber(getDetailTable(), this.SIGNAMOUNT_COL);
		FDCHelper.formatTableNumber(getDetailTable(), this.ACCAMOUNT_COL);
		
		KDDatePicker sign = new KDDatePicker();
		KDTDefaultCellEditor dateCell = new KDTDefaultCellEditor(sign);
		getDetailTable().getColumn(this.SIGNDATE_COL).setEditor(dateCell);
		
		FDCHelper.formatTableDate(getDetailTable(),this.SIGNDATE_COL);
		
		//设置合同类型列为F7多选，且显示编码和名称
		IColumn colType = this.kdtEntry.getColumn(this.TYPE_COL);
		colType.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		
		KDBizPromptBox contractType = new KDBizPromptBox();
		
		contractType.setDisplayFormat("$number$ $name$");
		contractType.setEditFormat("$name$");
		contractType.setCommitFormat("$name$");
		contractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");
		
		KDTDefaultCellEditor cellColType = new KDTDefaultCellEditor(contractType);
		colType.setEditor(cellColType);
		
		kdtEntry.getColumn(this.NUMBER_COL).setRequired(true);
		kdtEntry.getColumn(this.NAME_COL).setRequired(true);
		kdtEntry.getColumn(this.SIGNAMOUNT_COL).setRequired(true);
		kdtEntry.getColumn(this.SIGNDATE_COL).setRequired(true);
		
		kdtEntry.getColumn(this.ACCAMOUNT_COL).getStyleAttributes().setLocked(true);
		
		KDFormattedTextField amount = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		amount.setPrecision(2);
		amount.setNegatived(false);
		amount.setSupportedEmpty(true);
		KDTDefaultCellEditor amountCell = new KDTDefaultCellEditor(amount);
		
		kdtEntry.getColumn(this.AIMCOST_COL).setEditor(amountCell);
		kdtEntry.getColumn(this.SIGNAMOUNT_COL).setEditor(amountCell);
		kdtEntry.getColumn(this.ACCAMOUNT_COL).setEditor(amountCell);
		
		kdtEntry.getColumn(this.AIMCOST_COL).getStyleAttributes().setLocked(true);
		kdtEntry.getColumn(this.ACCOUNT_COL).getStyleAttributes().setLocked(true);
		
		kdtEntry.getColumn(this.ACCTNUMBER_COL).getStyleAttributes().setLocked(true);
		kdtEntry.getColumn(this.ACCTNAME_COL).getStyleAttributes().setLocked(true);
		
		kdtEntry.getColumn("id").getStyleAttributes().setHided(true);
		kdtEntry.getColumn(this.ACCTID_COL).getStyleAttributes().setHided(true);
		kdtEntry.getColumn(this.ISLEAF_COL).getStyleAttributes().setHided(true);
		kdtEntry.getColumn(this.LEVEL_COL).getStyleAttributes().setHided(true);
		
		kdtEntry.getColumn(this.ACCOUNT_COL).getStyleAttributes().setHided(true);
	}
	private EntityViewInfo getCostAccountFilter()
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		FullOrgUnitInfo  tempOrg = (FullOrgUnitInfo)(SysContext.getSysContext().getCurrentOrgUnit());
		FilterItemInfo filterOrg = new FilterItemInfo("fullOrgUnit.id", tempOrg.getId());
		filter.getFilterItems().add(filterOrg);	 
		

		FilterItemInfo filterIsEnable = new FilterItemInfo("isEnabled", Boolean.valueOf(true));
		filter.getFilterItems().add(filterIsEnable);
		
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("longNumber"));
		
		return view ;
	}
	private TblEditAdapter kdtEntryAdapter = new TblEditAdapter();
	/**
	 * 分录表格事件监听
	 * @author xiaobin_li
	 *
	 */
	protected class TblEditAdapter extends KDTEditAdapter
	{
		public void editStopped(KDTEditEvent evt)
		{
			
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception 
	{
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
	
		if(newValue == null)
		{
			return ;
		}
		KDTable tblDetail = (KDTable) e.getSource();
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		afterEditStopped(tblDetail, oldValue, newValue, colIndex, rowIndex);
	}
	/**
	 * 分录表格编辑事件
	 * 用于表格之间的相关数据计算
	 * @throws Exception
	 */
	protected void afterEditStopped(KDTable table, Object oldValue,Object newValue, int colIndex, int rowIndex) throws Exception 
	{
		String key = table.getColumnKey(colIndex);
		
		if(key.equals(this.SIGNAMOUNT_COL))
		{
			//签约金额汇总
			IRow row = kdtEntry.getRow(rowIndex);
			if(row.getCell(this.SIGNAMOUNT_COL).getValue() == null)
			{
				return ;
			}
			
			BigDecimal changeValue = FDCHelper.ZERO;
			if(newValue != null)
			{
				BigDecimal newV = (BigDecimal)newValue ;
				if(oldValue == null)
				{
					changeValue = newV;
				}
				else
				{
					BigDecimal old = (BigDecimal)oldValue;
					changeValue =  newV.subtract(old);
				}
			}
			else
			{
				if(oldValue != null)
				{
					BigDecimal old = (BigDecimal)oldValue;
					changeValue =  changeValue.subtract(old);
				}
			}
			
			Boolean isAddRow = (Boolean)(row.getCell(this.ACCOUNT_COL).getValue());
		
			updateSignAmount(rowIndex, changeValue, isAddRow);
			
			upDateTable();
		} 
	}
	private void updateSignAmount(int rowIndex, BigDecimal changeValue, Boolean isAddRow)
	{
		IRow row = kdtEntry.getRow(rowIndex);
		
		int startRowIndex = rowIndex ;
		
		if(!isAddRow.booleanValue())
		{
			int flag = rowIndex ;
			while(true)
			{
				IRow tempRow = kdtEntry.getRow(flag) ;
				Boolean isRootRow = (Boolean)(tempRow.getCell(this.ACCOUNT_COL).getValue());
				if(isRootRow.booleanValue())
				{
					if(tempRow.getCell(this.SIGNAMOUNT_COL).getValue() != null)
					{
						BigDecimal tempRowSignAmount = (BigDecimal)(tempRow.getCell(this.SIGNAMOUNT_COL).getValue());
						tempRowSignAmount = tempRowSignAmount.add(changeValue);
						
						tempRow.getCell(this.SIGNAMOUNT_COL).setValue(tempRowSignAmount);
					}
					else
					{
						tempRow.getCell(this.SIGNAMOUNT_COL).setValue(row.getCell(this.SIGNAMOUNT_COL).getValue());
					}
					
					startRowIndex = flag ;
					break; 
				}
				else
				{
					flag = flag -1 ;
				}
			}
		}
		
		//跟新该行上级的所有数据
		IRow startRow = kdtEntry.getRow(startRowIndex);
		
		String strFlag = startRow.getCell(this.ACCTNUMBER_COL).getValue().toString();
		
		strFlag = getUpLevelCodingNumber(strFlag, '.');
		
		for(int i = startRowIndex-1; i >= 0; --i)
		{
			IRow tempRow = kdtEntry.getRow(i);
			Boolean isRootRow = (Boolean)(tempRow.getCell(this.ACCOUNT_COL).getValue());
			if(isRootRow.booleanValue())
			{
				String codingNum = tempRow.getCell(this.ACCTNUMBER_COL).getValue().toString();
				
				if(codingNum.equals(strFlag))
				{
					if(tempRow.getTreeLevel() == 0)
					{
						if(tempRow.getCell(this.SIGNAMOUNT_COL).getValue() != null)
						{
							BigDecimal tempRowValue = (BigDecimal)(tempRow.getCell(this.SIGNAMOUNT_COL).getValue());
							tempRowValue = tempRowValue.add(changeValue);
							tempRow.getCell(this.SIGNAMOUNT_COL).setValue(tempRowValue);
						}
						else
						{
							tempRow.getCell(this.SIGNAMOUNT_COL).setValue(changeValue);
						}
						
						break ;
					}
					else
					{
						if(tempRow.getCell(this.SIGNAMOUNT_COL).getValue() != null)
						{
							BigDecimal tempRowValue = (BigDecimal)(tempRow.getCell(this.SIGNAMOUNT_COL).getValue());
							tempRowValue = tempRowValue.add(changeValue);
							tempRow.getCell(this.SIGNAMOUNT_COL).setValue(tempRowValue);
						}
						else
						{
							tempRow.getCell(this.SIGNAMOUNT_COL).setValue(changeValue);
						}
						
						strFlag = getUpLevelCodingNumber(strFlag, '.');
					}
				}
			}
		}
	}
	protected void loadCostAccountToTable(KDTable table)
	{
		try 
		{
			CostAccountCollection accountCols = CostAccountFactory.getRemoteInstance().getCostAccountCollection(getCostAccountFilter());
			Iterator accIter = accountCols.iterator();
		
			Set accountIDs = new HashSet();
			while(accIter.hasNext())
			{
				CostAccountInfo info = (CostAccountInfo)accIter.next();
				
				accountIDs.add(info.getId());
				
				IRow row = kdtEntry.addRow();
				String tmpLongNumber = info.getLongNumber();
				tmpLongNumber = tmpLongNumber.replace('!','.');
				
				row.getCell(this.ACCTNUMBER_COL).setValue(info.getCodingNumber());
				row.getCell(this.ACCTNAME_COL).setValue(info.getName());
				row.getCell(this.LEVEL_COL).setValue(new Integer(info.getLevel()));
				row.getCell(this.ISLEAF_COL).setValue(Boolean.valueOf(info.isIsLeaf()));
				
				row.getCell(this.ACCOUNT_COL).setValue(Boolean.valueOf(true));
				row.getCell(this.ACCTID_COL).setValue(info);
				row.setTreeLevel(info.getLevel()-1);
				
				row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
				
				if(info.getLevel() > MAX_LEVEL)
				{
					MAX_LEVEL = info.getLevel();
				}
			}

		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
	}
	protected void initListener() 
	{
		return ;
	}
	private void afterAddOrInsertRow(int index)
	{
		if(index == 0)
		{
			kdtEntry.removeRow(index);
			MsgBox.showWarning("行插入失败，请指定要插入行的位置！");
			SysUtil.abort();
		}
		int fatherRowIndex = index - 1 ;
		
		IRow fatherRow = kdtEntry.getRow(fatherRowIndex);
		IRow newRow = kdtEntry.getRow(index);
		
		CostAccountInfo tempAccount = (CostAccountInfo)(fatherRow.getCell(this.ACCTID_COL).getValue());
		
		newRow.getCell(this.ACCTID_COL).setValue(tempAccount);
//		newRow.getCell(this.ACCTNUMBER_COL).setValue(fatherRow.getCell(this.ACCTNUMBER_COL).getValue());
//		newRow.getCell(this.ACCTNAME_COL).setValue(fatherRow.getCell(this.ACCTNAME_COL).getValue());
		
		newRow.getCell(this.ISLEAF_COL).setValue(fatherRow.getCell(this.ISLEAF_COL).getValue());
		newRow.getCell(this.LEVEL_COL).setValue(fatherRow.getCell(this.LEVEL_COL).getValue());
		newRow.getCell(this.ACCOUNT_COL).setValue(Boolean.valueOf(false));
		
		newRow.setTreeLevel(fatherRow.getTreeLevel());
		
//		newRow.getStyleAttributes().setBackground(this.preTax);
	}
	protected void kdtEntry_editStopping(KDTEditEvent e) throws Exception 
	{
		Object oldValue = e.getOldValue();
		Object newValue = e.getValue();
	
		if(newValue == null)
		{
			return ;
		}
		
		if(oldValue != null && (oldValue.equals(newValue)))
		{
			return ;
		}
		
		KDTable tblDetail = (KDTable) e.getSource();
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		
		afterEditStopping(tblDetail, oldValue, newValue, colIndex, rowIndex);
	}
	protected void afterEditStopping(KDTable table, Object oldValue,Object newValue, int colIndex, int rowIndex) throws Exception
	{
		String key = table.getColumnKey(colIndex);
		
		if(key.equals(this.NUMBER_COL))
		{
			IRow row = kdtEntry.getRow(rowIndex);
			
			String contractNumber = newValue.toString();
			row.getCell(this.NUMBER_COL).setValue(contractNumber);
			for(int i = 0; i < kdtEntry.getRowCount(); ++i)
			{
				if(i != rowIndex)
				{
					IRow tempRow = kdtEntry.getRow(i);
					if(tempRow.getCell(this.NUMBER_COL).getValue() != null && contractNumber.equals(tempRow.getCell(this.NUMBER_COL).getValue().toString()))
					{	
						//tempRow.getCell(this.SIGNAMOUNT_COL).getValue()是newData.
						//row.getCell(this.SIGNAMOUNT_COL).getValue()是oldData.
						BigDecimal changeValue = FDCHelper.ZERO;
						if(tempRow.getCell(this.SIGNAMOUNT_COL).getValue() != null)
						{
							BigDecimal newData = (BigDecimal)(tempRow.getCell(this.SIGNAMOUNT_COL).getValue());
							if(row.getCell(this.SIGNAMOUNT_COL).getValue() != null)
							{
								BigDecimal oldData = (BigDecimal)(row.getCell(this.SIGNAMOUNT_COL).getValue());
								changeValue = newData.subtract(oldData);
							}
							else
							{
								changeValue = newData ;
							}
						}
						else
						{
							if(row.getCell(this.SIGNAMOUNT_COL).getValue() != null)
							{
								BigDecimal oldData = (BigDecimal)(row.getCell(this.SIGNAMOUNT_COL).getValue());
								changeValue = changeValue.subtract(oldData);
							}
						}
							
						row.getCell(this.NAME_COL).setValue(tempRow.getCell(this.NAME_COL).getValue());
						row.getCell(this.SIGNAMOUNT_COL).setValue(tempRow.getCell(this.SIGNAMOUNT_COL).getValue());
						row.getCell(this.SIGNDATE_COL).setValue(tempRow.getCell(this.SIGNDATE_COL).getValue());
						
						row.getCell(this.TYPE_COL).setValue(tempRow.getCell(this.TYPE_COL).getValue());
						
						
						updateSignAmount(rowIndex, changeValue, Boolean.valueOf(false));
						
						setSubStractAmount();
						
						return ;
					}
				}
			}
		}
	}
	
	 /**
     * 汇总
     * @param table
     */
    private void setUnionData(String columKey, KDTable table)
    {
    	List sumCols=new ArrayList();
    	sumCols.add(columKey);
    	ContracClientHepler.setUnionData(table,sumCols,getSumRowBase());
    }
    private boolean isLeafRow(IRow row) 
    {
    	return ((Boolean)(row.getCell(this.ISLEAF_COL).getValue())).booleanValue();
	}
    private ContracClientHepler.SumRowBase sumRowBase = null;
	/**
	 * 返回汇总递归基
	 * @return
	 */
	private SumRowBase getSumRowBase() 
	{
		if(sumRowBase==null){
			sumRowBase=new ContracClientHepler.SumRowBase(){
	    		boolean isLeaf(IRow row) {
	    			return isLeafRow(row);
	    		}
	    	};
		}
    	return sumRowBase;
	}
	
	/**
     * 在指定表格中插入行（在当前选中行前插入，如果当前未选中任何行的话，则新增到最后一行）
     *
     * @param table
     */
    protected void insertLine(KDTable table)
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table);
        IRow row = null;

        if (table.getSelectManager().size() > 0)
        {
            int top = table.getSelectManager().get().getTop();

            if (isTableColumnSelected(table))
            {
                row = table.addRow();
            }
            else
            {
                row = table.addRow(top+1);
            }
        }
        else
        {
            row = table.addRow();
        }

        loadLineFields(table, row, detailData);
        afterInsertLine(table, detailData);
    }
    protected void loadAimCost(CurProjectInfo tmpProject)
    {
    	String projectID = tmpProject.getId().toString();
		
		EntityViewInfo aimCostView = new EntityViewInfo();
		FilterInfo aimCostFilter = new FilterInfo();
		
		aimCostView.getSelector().add("id");
		aimCostView.getSelector().add("*");
		aimCostView.getSelector().add("costEntry.*");
		aimCostView.getSelector().add("costEntry.costAccount.*");
		
		aimCostFilter.appendFilterItem("orgOrProId", projectID);
		aimCostFilter.appendFilterItem("isLastVersion", Boolean.valueOf(true));
		
		aimCostView.setFilter(aimCostFilter);
		
		try {
			if(aimCostCols == null)
			{
				aimCostCols = AimCostFactory.getRemoteInstance().getAimCostCollection(aimCostView);
			}
				
			if(aimCostCols.size() == 1)
			{
				CostEntryCollection costEntry = ((AimCostInfo)aimCostCols.get(0)).getCostEntry();
				Iterator entryIter = costEntry.iterator();
				while(entryIter.hasNext())
				{
					CostEntryInfo entryInfo = (CostEntryInfo)entryIter.next();
					
					for(int rowIndex = 0; rowIndex < kdtEntry.getRowCount(); ++rowIndex)
					{
						IRow tmpRow = kdtEntry.getRow(rowIndex);
						
						if(tmpRow.getCell(this.ACCTNUMBER_COL).getValue() != null)
						{
							String codingNumber = tmpRow.getCell(this.ACCTNUMBER_COL).getValue().toString();
							
							if(codingNumber.equals(entryInfo.getCostAccount().getCodingNumber()))
							{
								tmpRow.getCell(this.AIMCOST_COL).setValue(entryInfo.getCostAmount());
								break ;
							}
						}
					}
				}
			}
			
			setUnionData(this.AIMCOST_COL, kdtEntry);
			
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
    }
    private void setAllRowsTreeLevel()
    {
    	for(int i = 0; i < kdtEntry.getRowCount(); ++i)
    	{
    		IRow row = kdtEntry.getRow(i);
    		int level = ((Integer)(row.getCell(this.LEVEL_COL).getValue())).intValue();
    		row.setTreeLevel(level-1);
    	
    		if(level > MAX_LEVEL)
    		{
    			MAX_LEVEL = level;
    		}
    	}
    }
    private void displayInsertContract()
    {
    	for(int i = 0; i < kdtEntry.getRowCount(); ++ i)
    	{
    		IRow row = kdtEntry.getRow(i);
    		if(row.getCell(this.NUMBER_COL).getValue() == null)
    		{
    			row.getStyleAttributes().setLocked(true);
				row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
				
				row.getCell(this.ACCOUNT_COL).setValue(Boolean.valueOf(true));
    		}
    		else
    		{
    			row.getCell(this.ACCTNUMBER_COL).setValue(null);
    			row.getCell(this.ACCTNAME_COL).setValue(null);
    			row.getCell(this.ACCOUNT_COL).setValue(Boolean.valueOf(false));
    		}
    	}
    }
    
    private void setSubStractAmount()
    {
    	for(int i = 0; i < kdtEntry.getRowCount(); ++i)
		{
			IRow row = kdtEntry.getRow(i);
			
			Boolean isRootRow = (Boolean)(row.getCell(this.ACCOUNT_COL).getValue());
			if(!isRootRow.booleanValue())
			{
				continue ;
			}
			if(row.getCell(this.AIMCOST_COL).getValue() != null)
			{
				if(row.getCell(this.SIGNAMOUNT_COL).getValue() == null)
				{
					row.getCell(this.ACCAMOUNT_COL).setValue(row.getCell(this.AIMCOST_COL).getValue());
				}
				else
				{
					BigDecimal aimCost = (BigDecimal)(row.getCell(this.AIMCOST_COL).getValue());
					BigDecimal signAmount = (BigDecimal)(row.getCell(this.SIGNAMOUNT_COL).getValue());
					
					BigDecimal subValue = aimCost.subtract(signAmount);
					row.getCell(this.ACCAMOUNT_COL).setValue(subValue);
					
				}
			}
			else
			{
				if(row.getCell(this.SIGNAMOUNT_COL).getValue() != null)
				{
					BigDecimal aimCost = FDCHelper.ZERO;
					BigDecimal signAmount = (BigDecimal)(row.getCell(this.SIGNAMOUNT_COL).getValue());
					
					BigDecimal subValue = aimCost.subtract(signAmount);
					row.getCell(this.ACCAMOUNT_COL).setValue(subValue);
				}
			}
		}
    }
    
    private void upDateTable()
    {
		setSubStractAmount();
    }
    private String getUpLevelCodingNumber(String codingNumber, char splitChar)
    {
    	String upLevelCodingNumber = null ;
    	if(codingNumber != null)
    	{
    		int flag = 0 ;
    		char[] temp = codingNumber.toCharArray();
    		for(int i = codingNumber.length()-1; i >= 0; --i)
    		{
    			if(temp[i] == splitChar)
    			{
    				flag = i ;
    				break ;
    			}
    		}
    		
    		upLevelCodingNumber = codingNumber.substring(0, flag);
    	}
    	return upLevelCodingNumber ;
    }
    protected void verifyInputForSave() throws Exception 
    {
    	super.verifyInputForSave();
    	
    	for(int i = 0; i < kdtEntry.getRowCount(); ++i)
    	{
    		IRow row = kdtEntry.getRow(i);
    		Boolean isRootRow = (Boolean)(row.getCell(this.ACCOUNT_COL).getValue());
    		if(isRootRow.booleanValue())
    		{
    			continue ;
    		}
    		Integer rowIndex = new Integer(i+1);
    		String warning = "第" + rowIndex.toString();
    		
    		if(row.getCell(this.NUMBER_COL).getValue() == null)
    		{
    			warning = warning + "行合同编码不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    		
    		if(row.getCell(this.NAME_COL).getValue() == null)
    		{
    			warning = warning + "行合同名称不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    		
    		if(row.getCell(this.SIGNAMOUNT_COL).getValue() == null)
    		{
    			warning = warning + "行合同预计签约金额不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    		
    		if(row.getCell(this.SIGNDATE_COL).getValue() == null)
    		{
    			warning = warning + "行合同预计签约日期不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    	}
    }
    
    protected void verifyInputForSubmint() throws Exception 
    {
    	super.verifyInputForSubmint();
    	
    	for(int i = 0; i < kdtEntry.getRowCount(); ++i)
    	{
    		IRow row = kdtEntry.getRow(i);
    		Integer rowIndex = new Integer(i+1);
    		
    		Boolean isRootRow = (Boolean)(row.getCell(this.ACCOUNT_COL).getValue());
    		if(isRootRow.booleanValue())
    		{
    			continue ;
    		}
    		
    		String warning = "第" + rowIndex.toString();
    		
    		if(row.getCell(this.NUMBER_COL).getValue() == null)
    		{
    			warning = warning + "行合同编码不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    		
    		if(row.getCell(this.NAME_COL).getValue() == null)
    		{
    			warning = warning + "行合同名称不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    		
    		if(row.getCell(this.SIGNAMOUNT_COL).getValue() == null)
    		{
    			warning = warning + "行合同预计签约金额不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    		
    		if(row.getCell(this.SIGNDATE_COL).getValue() == null)
    		{
    			warning = warning + "行合同预计签约日期不能为空";
    			MsgBox.showWarning(warning);
    			SysUtil.abort();
    		}
    	}
    }
    
}