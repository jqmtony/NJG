/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewCollection;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NoCostSplitApptProjUI extends AbstractNoCostSplitApptProjUI
{
    private static final Logger logger = CoreUIObject.getLogger(NoCostSplitApptProjUI.class);        
    //成本科目
    private AccountViewCollection costAccountCollection=null;
    //分摊数据（建筑面积、可售面积等）
    private CurProjectCollection curProjectCollection=null;
    
    public NoCostSplitApptProjUI() throws Exception
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
    public void onShow() throws Exception
    {
//    	tHelper.getDisabledTables().add(kdtEntrys);
    	super.onShow();
    }
    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        super.kdtEntrys_editStopped(e);

		if(e.getColIndex()==kdtEntrys.getColumnIndex("directAmount")){
			IRow row=kdtEntrys.getRow(e.getRowIndex());
			FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			if(entry.isIsAddlAccount()){
				BigDecimal directAmount;
				if(row.getCell("directAmount").getValue()!=null){
					directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
				}else{
					directAmount=FDCHelper.ZERO;
				}
				if(directAmount.compareTo(FDCHelper.ZERO)!=0
						&& !row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
					row.getCell("isSelected").setValue(Boolean.TRUE);
					setSelected(e.getRowIndex(),true);			
				}
			}
		}
    }

    /**
     * output kdtEntrys_editValu`eChanged method
     */
    protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        super.kdtEntrys_editValueChanged(e);
		

		//分摊对象选择，根据上下级关系控制选择
		if(e.getColIndex()==kdtEntrys.getColumnIndex("isSelected")){

			Boolean isSelected=(Boolean)e.getValue();
			setSelected(e.getRowIndex(),isSelected.equals(Boolean.TRUE));			
		}		
		
    }

    /**
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.kdtEntrys_tableClicked(e);
    }

    /**
     * output btnCancel2_actionPerformed method
     */
    protected void btnCancel2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnCancel2_actionPerformed(e);
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.btnConfirm_actionPerformed(e);
    }

    /**
     * output actionSelectAll_actionPerformed
     */
    public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSelectAll_actionPerformed(e);
    }

    /**
     * output actionSelectNone_actionPerformed
     */
    public void actionSelectNone_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSelectNone_actionPerformed(e);
    }

    /**
     * output actionSplit_actionPerformed
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
        //super.actionSplit_actionPerformed(e);
        
    	boolean isSelected=false;		
		for(int i=1; i<kdtEntrys.getRowCount(); i++){
    		if(kdtEntrys.getCell(i,"isSelected").getValue().equals(Boolean.TRUE)){
    			isSelected=true;
    			break;
    		}
		}
		if(!isSelected){
			//MsgBox.showInfo(this,"请选择分摊对象！");
			MsgBox.showWarning(this, ContractClientUtils.getRes("costSplitNoApportionObject"));
			return;
		}
		
    	
    	IRow row=null;
    	row=kdtEntrys.getRow(0);
    	
    	ApportionTypeInfo apptType=null;
    	apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
    	if(apptType==null){
			//MsgBox.showInfo(this,"请选择分摊类型！");
			MsgBox.showWarning(this, ContractClientUtils.getRes("costSplitNoApportionType"));
    		return;
    	}
    	
    	
    	
    	FDCNoCostSplitBillEntryInfo entry=null;
    	BigDecimal directAmount=FDCHelper.ZERO;
    	
    	 
    	//boolean isTrue=false;
    	
    	// TODO 明细分摊对象
			
		//分摊类型
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);			
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			if(row.getCell("directAmount").getValue()!=null){
				directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
			}else{
				directAmount=FDCHelper.ZERO;
			}
			
			if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) 
					|| (directAmount.compareTo(FDCHelper.ZERO)!=0) && !entry.isIsAddlAccount()){				
    			  
    			if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){
    				//末级拆分时，自动使用上级的分摊类型
    				if(!entry.isIsLeaf()){
    					//entry.setApportionType(apptType);
    				}
    				
    			}else{
    				//项目拆分 
    				if(!entry.getCurProject().isIsLeaf() && !entry.isIsAddlAccount()){
    					apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
    					
	        			if(apptType==null){  
	        				
	    					isSelected=false;
	    					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
	    						row=kdtEntrys.getRow(j);
	    						FDCNoCostSplitBillEntryInfo entry2=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
	    						
	    						if(entry2.getLevel()==entry.getLevel()+1){
	    							if(entry2.getAccountView().getNumber().equals(entry.getAccountView().getNumber())){
	    								if(row.getCell("directAmount").getValue()!=null){
	    									directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
	    								}else{
	    									directAmount=FDCHelper.ZERO;
	    								}
	    								if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)
	    										|| directAmount.compareTo(FDCHelper.ZERO)!=0){
	    									isSelected=true;
	    									break;
	    								}
	    							}
	    						}else if(entry2.getLevel()<=entry.getLevel()){
	    							break;
	    						}
	    					}
	    					
	    					if(isSelected){
    	        				MsgBox.showWarning(this, ContractClientUtils.getRes("costSplitNoApportionType"));
    	        				return;    	        						    						
	    					} 	        				
	        			}
	        			
	        			
	        			
    				}
    				
    			}    			
			}
		}
		
		
		

		//检查同一个分摊类型的指标是否存在有效的指标值		Jelon	Dec 11,2006
		String acctNo=null;
		
		for(int i=0; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);			
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			if(isSelectedEntry(i)
					&& !entry.getCurProject().isIsLeaf() && !entry.isIsAddlAccount()){
				
				//分摊类型
				if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){
					if(i==0){
						apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
					}
				}else{
					apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
				}

				isSelected=false;
				for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
					row=kdtEntrys.getRow(j);
					FDCNoCostSplitBillEntryInfo entry2=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
					
					if(entry2.getLevel()==entry.getLevel()+1){
						if(entry2.getAccountView().getNumber().equals(entry.getAccountView().getNumber())
								&& isSelectedEntry(j)){
							if(getApportionValue(entry2,apptType).compareTo(FDCHelper.ZERO)!=0
									|| entry2.isIsAddlAccount()){
								isSelected=true;
								break;
							}
						}
					}else if(entry2.getLevel()<=entry.getLevel()){
						break;
					}
				}
				
				if(!isSelected){
					MsgBox.showWarning(this, "请选择有效的分摊类型！");
					return;    	        						    						
				} 	        	
				
			}			
			
		}		
		
    	    	
    	
		
		super.actionSplit_actionPerformed(e);
    }
    
    

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#initTable()
	 */
	protected void initTable() throws Exception {
		//拆分行
		FDCNoCostSplitBillEntryInfo topEntry=costSplit.get(0);
		AccountViewInfo topAcct=topEntry.getAccountView();
		
		//标题
		txtCostObj.setText(topEntry.getCurProject().getDisplayName());
		txtCostAcct.setText(topEntry.getAccountView().getDisplayName());
		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		IRow row;
		IColumn col;
		ICell cell;
		
		//index
		int	idx;
		int idxRow;
		int idxCol;
				
		String rowId;
		String colId;
		
		boolean isTrue=false;
		
		
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		BigDecimal amount;
		

		//-----------------------------------------------------------------
		//第一行：
		row = kdtEntrys.addRow();
		row.getStyleAttributes().setLocked(true);
		//row.getCell("apportionType").getStyleAttributes().setLocked(false);
		row.getCell("apportionObj.number").setValue(topEntry.getCurProject().getLongNumber().replace('!','.'));
		row.getCell("apportionObj.name").setValue(topEntry.getCurProject().getName());
		row.getCell("accountView.number").setValue(topEntry.getAccountView().getNumber());
		row.getCell("accountView.name").setValue(topEntry.getAccountView().getName());
		row.setUserObject(topEntry);
//		row.getCell("isSelected").setValue("");	

		//-----------------------------------------------------------------
		//数据列：分摊类型
		view = new EntityViewInfo();
		filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("forCostApportion", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("targetType.isEnabled", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSorter().add(new SorterItemInfo("number"));

		ApportionTypeInfo appt=null;
		ApportionTypeCollection collAppt = ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(view);
		
		//删除目标成本比（目标成本比只适用于产品拆分）		Jelon 12/14/06
		for (Iterator iter = collAppt.iterator(); iter.hasNext();)		{
			appt = (ApportionTypeInfo) iter.next();
			if(appt.getId().toString().equals(ApportionTypeInfo.aimCostType)){
				collAppt.remove(appt);
				break;
			}
		}		
		
		apptTypeIds=new String[collAppt.size()];
		apportionTypeCollection=collAppt;
		
		
		
		row=kdtEntrys.getRow(0);
		idx=0;		
		for (Iterator iter = collAppt.iterator(); iter.hasNext();)
		{
			appt = (ApportionTypeInfo) iter.next();
						
			//分摊类型动态增加列
			//col = kdtEntrys.addColumn(kdtEntrys.getColumnCount()-1);
			col = kdtEntrys.addColumn(kdtEntrys.getColumnCount());
			col.setKey(appt.getId().toString());	//设置Key后，动态列才能进行表格设置中出现
			/*col.getStyleAttributes().setNumberFormat("###,##0.00");
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);*/
			col.getStyleAttributes().setLocked(true);
			
			//标题
			kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue(appt.getName());
			
						
			apptTypeIds[idx]=appt.getId().toString();
			
			idx++;											
		}			
		
		KDComboBox cbo = new KDComboBox(); 
        cbo.addItems(collAppt.toArray());		
        kdtEntrys.getColumn("apportionType").setEditor(new KDTDefaultCellEditor(cbo));  
		
			
		
		
		//-----------------------------------------------------------------
		//数据行	：分摊对象－工程项目+成本科目
		
		boolean isLeaf=false;
		
        int level=0;
		int topLevel=topEntry.getLevel();	
		int topAcctLevel=topEntry.getAccountView().getLevel();
		int topProjLevel=topEntry.getCurProject().getLevel();
		
		
		//工程项目
		view = new EntityViewInfo();
		filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("longNumber", 
    			topEntry.getCurProject().getLongNumber().replace('.','!')+"!%", CompareType.LIKE));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	view.setFilter(filter);
/*    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSelector().add("longNumber");
*/    	
    	view.getSelector().add("parent.id");
    	view.getSelector().add("*");
    	//view.getSelector().add("curProjCostEntries.apportionType.id");
    	//view.getSelector().add("curProjCostEntries.value");
    	 	
    	
    	view.getSorter().add(new SorterItemInfo("longNumber"));
    	view.getSorter().add(new SorterItemInfo("sortNo"));
    	
		CurProjectCollection curProjs = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
		//apptObjIds=new String[apptObjs.size()];
		curProjectCollection=curProjs;

		//会计科目,所有的科目都是一样的
    	AccountViewCollection acctViewCollection = new AccountViewCollection();
    	acctViewCollection.add(topEntry.getAccountView());
    	costAccountCollection=acctViewCollection;
		apptObjIds=new String[acctViewCollection.size()];
   	  			
		CurProjectInfo curProj=null;
		AccountViewInfo acctView=null;
		AccountViewInfo acctView3=null;
		boolean isFirstAcct=false;
		//IRow rowFirst=null;
		

		boolean isAddlAcct=false;
		
		idx=0;
		//工程项目
		for (Iterator iter = curProjs.iterator(); iter.hasNext();)
		{
			curProj = (CurProjectInfo) iter.next();

			isFirstAcct=true;
		
			//成本科目，当前工程项目中符合条件的所有成本科目
			for(Iterator iter2=acctViewCollection.iterator(); iter2.hasNext(); ){
				acctView=(AccountViewInfo)iter2.next();				
//				if(acctView.getCurProject().getId().equals(curProj.getId())){
					
					if(isFirstAcct){
						//当前工程项目第一个成本科目，需处理指标值
						row = kdtEntrys.addRow();
						//rowFirst=row;
						
						//处理无相应科目的情况	jelon 12/26/2006
						entry=createNewEntryData();
						row.setUserObject(entry);
						
						row.getCell("apportionObj.number").setValue(curProj.getLongNumber().replace('!', '.'));
						row.getCell("apportionObj.name").setValue(curProj.getName());
														
						//工程项目分摊数据（项目的分摊数据）
						CurProjCostEntriesCollection apptDatas=curProj.getCurProjCostEntries();	
						CurProjCostEntriesInfo apptData=null;			
						for (Iterator iter3 = apptDatas.iterator(); iter3.hasNext();)
						{
							apptData=(CurProjCostEntriesInfo)iter3.next();
							if(apptData.getApportionType()!=null){
								//列号
								idxCol=0;
								for(int i=0; i<apptTypeIds.length; i++){
									if(apptData.getApportionType().getId().toString().equals(apptTypeIds[i])){
										idxCol = colBase+i;
										break;
									}
								}
								if(idxCol>0){				
									amount = apptData.getValue();
									if(amount==null){
										amount=FDCHelper.ZERO;
									}
									//row.getCell(idxCol).setValue(amount);
									cell=row.getCell(idxCol);
									cell.getStyleAttributes().setNumberFormat("###,##0.00");
									cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
									cell.setValue(amount);
								}					
							}else{
								amount=FDCHelper.ZERO;
							}
						}			
						
						
					}else{
						row = kdtEntrys.addRow();
						
						entry=createNewEntryData();
					}
					
					row.getCell("id").setValue(curProj.getId());
					row.getCell("isSelected").setValue(Boolean.FALSE);
					//row.getCell("apportionObj.number").setValue(curProj.getLongNumber().replace('!', '.'));
					//row.getCell("apportionObj.name").setValue(curProj.getName());
					
					row.getCell("accountView.number").setValue(acctView.getNumber());
					row.getCell("accountView.name").setValue(acctView.getName());
				
					entry.setAccountView(acctView);
					entry.setCurProject(curProj);
					level=topLevel;
					level+=entry.getCurProject().getLevel() - topProjLevel;
					entry.setLevel(level);

					if(entry.getCurProject().isIsLeaf()){
						isLeaf=true;
						row.getCell("apportionType").getStyleAttributes().setLocked(true);
					}else{
						isLeaf=false;
					}
					entry.setIsLeaf(isLeaf);
					
					//附加的子科目
					isAddlAcct=false;
					if(isAddlAccount(acctView)){
						isAddlAcct=true;		
						
						row.getCell("apportionType").setValue("直接分配");
						row.getCell("apportionType").getStyleAttributes().setLocked(true);					
					}
					entry.setIsAddlAccount(isAddlAcct);		

					if(isFirstAcct){
						isFirstAcct=false;
					}else{
						row.setUserObject(entry);
					}
		
			}
			
		}			
				
		
		

		//-----------------------------------------------------------------
		//界面控制
		
		String topAcctNo=topAcct.getNumber();
		
		for(int i=0; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
						
			// TODO 测试树形
			if(i==0){
				//topAcct=entry.getAccountView();
			}else if(entry!=null && entry.getAccountView()!=null){
				CellTreeNode node = new CellTreeNode();
				cell=row.getCell("apportionObj.name");			
				// 节点的值
				node.setValue(cell.getValue());
				// 是否有子节点
				if(entry.getAccountView().getNumber().equals(topAcctNo) 
						&& !entry.getCurProject().isIsLeaf()){
					node.setHasChildren(true);
				}else{
					node.setHasChildren(false);				
				}			
				// 节点的树级别
				node.setTreeLevel(entry.getLevel()-1);
				cell.getStyleAttributes().setLocked(false);
				cell.setValue(node);
			}
			//end				
			
			if(i==0){
				row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("apportionType").getStyleAttributes().setLocked(false);
				row.getCell("apportionType").getStyleAttributes().setBackground(new Color(0xFFFFFF));	
			}else{
				row.getStyleAttributes().setBackground(new Color(0xFEFED3));	
				row.getCell("isSelected").getStyleAttributes().setBackground(new Color(0xFFFFFF));	
				//row.getCell("directAmount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
				
				//自动拆分，非明细拆分对象可以设置分摊标准
				/*if(costSplitType.equals(CostSplitType.PROJSPLIT)
						&& (entry!=null && !entry.isIsLeaf()) ){	*/
				if(costSplitType.equals(CostSplitTypeEnum.PROJSPLIT)
						&& (entry!=null && !entry.getCurProject().isIsLeaf()) && !entry.isIsAddlAccount()){	
					row.getCell("apportionType").getStyleAttributes().setBackground(new Color(0xFFFFFF));	
				}else{
					row.getCell("apportionType").getStyleAttributes().setLocked(true);					
				}
				
				//直接费用处理
				/*if(costSplitType.equals(CostSplitType.PROJSPLIT)){
					row.getCell("directAmount").getStyleAttributes().setLocked(false);	
					row.getCell("directAmount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
				}else{
					row.getCell("directAmount").getStyleAttributes().setLocked(true);	
				}*/
				row.getCell("directAmount").getStyleAttributes().setLocked(true);	
				
				if(entry!=null && entry.getAccountView()!=null && entry.getCurProject()!=null
						&& entry.getCurProject().isIsLeaf()){
					if(entry.getAccountView().getNumber().equals(topAcctNo) || entry.getAccountView().isIsLeaf()){
						row.getCell("directAmount").getStyleAttributes().setLocked(false);			
						row.getCell("directAmount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						
						//if(entry.getAccountView().isIsLeaf()){
						if(entry.isIsAddlAccount()){
							row.getCell("directAmount").getStyleAttributes().setFontColor(new Color(0xE74B00));							
						}
					}
				}			
					
			}			
		}
		
		
		
		super.initTable();
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#getData()
	 */
	public FDCNoCostSplitBillEntryCollection getData() throws Exception {
		FDCNoCostSplitBillEntryCollection entrys = new FDCNoCostSplitBillEntryCollection();		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		String id;
		IRow row;
		int colIdx;
		BigDecimal directAmount=FDCHelper.ZERO;
		boolean isTrue=false;
		
		AccountViewInfo costAcct=null;
		ApportionTypeInfo apptType=null;
		
		boolean isSelected=false;
		int level=0;
		
			
		//分摊类型	
		row=kdtEntrys.getRow(0);
		entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
		apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
		entry.setApportionType(apptType);
		entry.setIsLeaf(false);
		entrys.add(entry);
		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			if(row.getCell("directAmount").getValue()!=null){
				directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
			}else{
				directAmount=FDCHelper.ZERO;
			}
					
			//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || directAmount.compareTo(FDCHelper.ZERO)!=0){
			/*if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || 
					(directAmount.compareTo(FDCHelper.ZERO)!=0) && !entry.isIsAddlAccount()){*/
			isSelected=false;
			if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || 
					(directAmount.compareTo(FDCHelper.ZERO)!=0) && !entry.isIsAddlAccount()){
				
				
				
				if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
					isSelected=true;
					
				}else{
					//检查同级科目是否有选择		Jelon	Dec 13, 2006
					level=entry.getLevel();

					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						FDCNoCostSplitBillEntryInfo entry2=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
						if(entry2.getLevel()==level){
							if(kdtEntrys.getRow(j).getCell("isSelected").getValue().equals(Boolean.TRUE)){
								isSelected=true;					
								break;
							}		
						}else if(entry2.getLevel()>level){
							//
						}else{
							break;
						}							
					}
					
					if(!isSelected){
						for(int j=i-1; j>=rowBase; j--){
							FDCNoCostSplitBillEntryInfo entry2=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
							if(entry2.getLevel()==level){
								if(kdtEntrys.getRow(j).getCell("isSelected").getValue().equals(Boolean.TRUE)){
									isSelected=true;				
									break;
								}			
							}else if(entry2.getLevel()>level){
								//
							}else{
								break;
							}							
						}						
					}
				}
			}
			
			if(isSelected){
    			//是否参与分摊
				if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
					entry.setIsApportion(true);
				}else{
					entry.setIsApportion(false);
				}
    			    			    			
    			//分摊类型
				if(!entry.isIsAddlAccount()){
	    			if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){
	    				//末级拆分，自动使用上级的分摊类型
	    				if(!entry.isIsLeaf()){
	    					entry.setApportionType(apptType);
	    				}
	    			}else{
	    				//项目拆分，附加科目无分摊类型  
	    	    		if(!entry.getCurProject().isIsLeaf() && !entry.isIsAddlAccount()){
    						apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
    	        			entry.setApportionType(apptType);
	    				}	    				
	    			}    									
				}
    			
				//直接费用
    			/*directAmount=FDCHelper.ZERO;
				if(row.getCell("directAmount").getValue()!=null){
					directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
				}*/
				entry.setDirectAmount(directAmount);
				
				boolean isAddlAcct=entry.isIsAddlAccount();
				if(isAddlAcct){
					entry.setApportionValue(entry.getDirectAmount());
					entry.setDirectAmount(FDCHelper.ZERO);					
				}
				
				//entrys.add(entry);				
				//1.项目拆分时，对其中包含的产品拆分的处理（add当前行及包含的产品拆分行）					
				BOSUuid prjid=entry.getCurProject().getId();
				isTrue=false;
				for(int j=0; j<costSplit.size(); j++){
					FDCNoCostSplitBillEntryInfo split=costSplit.get(j);			
					
					if(split.getSplitType()!=null && split.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						//BOSUuid acctid=prodEntry.getAccountView().getId();
						
						if(split.getCurProject().getId().equals(prjid)){
							isTrue=true;
							
							if(split.isIsLeaf()){	//产品分摊明细
								//split.setIsAddlAccount(entry.isIsAddlAccount());
								/*entry=(FDCNoCostSplitBillEntryInfo)split.clone();
								entry.setIsAddlAccount(isAddlAcct);
								entrys.add(entry);	*/
								entrys.add(split);
							}else{
								entry.setSplitType(CostSplitTypeEnum.PRODSPLIT);
								//产品分摊类型
								entry.setApportionType(split.getApportionType());
								entry.setIsLeaf(false);	
								entrys.add(entry);		
							}
						}
					}		
				}				
				//2.没有产品拆分，直接add当前行即可
				if(!isTrue){
					entrys.add(entry);
				}
				
    		}
		}
		
		//设置分摊值
		getApportionValue(entrys,0);
		
				
				
		disposeUIWindow();
		
		return entrys;
		
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#loadData()
	 */
	protected void loadData() throws Exception {
		// TODO 自动生成方法存根
		//super.loadData();
		
		
		colBase=8;
		
		super.loadData();
		

		btnRemove.setVisible(false);
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#loadPreSplit()
	 */
	protected void loadPreSplit() {
		// TODO 自动生成方法存根
		super.loadPreSplit();
		
		String id;
		IRow row;
		
		row=kdtEntrys.getRow(0);
		row.getCell("apportionType").setValue(costSplit.get(0).getApportionType());


		FDCNoCostSplitBillEntryInfo entry=null;
		FDCNoCostSplitBillEntryInfo split=null;
		
		ProductTypeInfo prod=null;
		
		//for(int i=0; i<kdtEntrys.getRowCount(); i++){
		for(int i=1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			
			if(i==0){
				/*row.getStyleAttributes().setBackground(new Color(0xF6F6BF));
				row.getCell("apportionType").getStyleAttributes().setLocked(false);
				row.getCell("apportionType").getStyleAttributes().setBackground(new Color(0xFFFFFF));	*/
			}else{
				row.getStyleAttributes().setBackground(new Color(0xFEFED3));
				
				if(entry==null){	
					//锁定
					row.getCell("isSelected").setValue(Boolean.FALSE);		
					row.getStyleAttributes().setLocked(true);			
					
				}else{
					
					row.getCell("isSelected").getStyleAttributes().setBackground(new Color(0xFFFFFF));	
					//row.getCell("directAmount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
					
					if(row.getCell("id").getValue()==null){
						continue;
					}
					
					id=row.getCell("id").getValue().toString();			
					for(int j=1; j<costSplit.size(); j++){
						split=costSplit.get(j);
						if(split.getCurProject().getId().toString().equals(id)){
							//是否参与分摊
							if(split.isIsApportion()){
								row.getCell("isSelected").setValue(Boolean.TRUE);
							}
							
							//直接费用
							if(split.isIsAddlAccount()){
								if(split.getApportionValue()!=null && split.getApportionValue().compareTo(FDCHelper.ZERO)!=0){
									row.getCell("directAmount").setValue(split.getApportionValue());									
								}
							}else{
								if(split.getDirectAmount()!=null && split.getDirectAmount().compareTo(FDCHelper.ZERO)!=0){
									row.getCell("directAmount").setValue(split.getDirectAmount());
								}
							}
							
							
							if(!costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){
								if(!entry.isIsLeaf() && costSplit.get(j).getApportionType()!=null){									
									row.getCell("apportionType").setValue(costSplit.get(j).getApportionType());
								}								
							}
							
							//直接归属，保留之前拆分的产品归属		jelon 12/26/2006
							prod=split.getProduct();
							if(prod!=null && prod.getId()!=null){
								entry.setProduct(prod);
							}
							
							
							break;
							
						}
					}
				}
				
			}			
		}
		
	}

    

	
	

	protected void getApportionValue(FDCNoCostSplitBillEntryCollection entrys, int index){
/*		for(int i=index+1; i<entrys.size(); i++){
			FDCNoCostSplitBillEntryInfo entry=entrys.get(i);
			CurProjCostEntriesCollection projCosts=entry.getCurProject().getCurProjCostEntries();
			
			for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
				CurProjCostEntriesInfo projCost=(CurProjCostEntriesInfo)iter3.next();
				
				if(projCost.getApportionType()!=null &&entry.getApportionType()!=null
						&& projCost.getApportionType().getId().equals(entry.getApportionType().getId())){
					entry.setApportionValue(projCost.getValue());
					
					break;
				}
			}
			
//			entry.setApportionValue(getApportionValue(entry, entry.getApportionType()));
		}*/
			
				
		
		BigDecimal value=FDCHelper.ZERO;
		
		FDCNoCostSplitBillEntryInfo entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		
		int projLevel=entry.getCurProject().getLevel();
		
		CurProjectInfo proj=entry.getCurProject();
		boolean isAddlAcct=entry.isIsAddlAccount();
		
		//分摊类型，根据父级的分摊类型设置直接下级的分摊指标数据
		ApportionTypeInfo apptType=entry.getApportionType();
				
		CurProjectInfo	curProj=null;
		CurProjCostEntriesInfo projCost=null;
		CurProjCostEntriesCollection projCosts=null;
		
		boolean isTrue=false;
		for(int i=index+1; i<entrys.size(); i++){
			entry=entrys.get(i);
			
			isTrue=false;
			if(isAddlAcct){
				//当前工程的直接下级科目	
				if(entry.getCurProject().getId().equals(proj.getId())){
					isTrue=true;
				}				
			}else{		
				//直接下级工程的相同科目
				if(entry.getCurProject().getLevel()==projLevel+1){
					isTrue=true;
				}				
			}
				
			if(isTrue){
				
				if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)
						&& entry.isIsLeaf()){
					//自动拆分和末级拆分中的产品拆分，不用设置分摊值
								
				}else if(entry.isIsAddlAccount()){
					//附加科目，将直接费用作为分摊值
					entry.setApportionValue(entry.getDirectAmount());
					entry.setDirectAmount(FDCHelper.ZERO);
					
				}else if(!entry.isIsApportion()){
					//不参与分摊，只有直接费用
					entry.setApportionValue(FDCHelper.ZERO);
					
				}else{
					//使用工程项目的分摊指标作为分摊值
					//工程项目集
					for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
						curProj=(CurProjectInfo)iter2.next();
						
						if(curProj.getId()!=null 
								///&& entry!=null new by update
								&& entry.getCurProject()!=null
								&& entry.getCurProject().getId()!=null
								&& curProj.getId().equals(entry.getCurProject().getId())){
							//工程项目分摊值集
							projCosts=curProj.getCurProjCostEntries();
							
							for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
								projCost=(CurProjCostEntriesInfo)iter3.next();
								
								if(projCost.getApportionType()!=null 
										&& apptType!=null		//科目下级为附加科目，apptType==null
										&& projCost.getApportionType().getId().equals(apptType.getId())){
									value=projCost.getValue();
									entry.setApportionValue(value);
									
									break;
								}
							}
							
							break;						
						}					
					}
					
				}
				
				//递归调用
				getApportionValue(entrys,i);
			}				
			
			
		}
		
		
		
	}
	
	
	
	protected void getApportionValue(FDCNoCostSplitBillEntryCollection entrys, int index, boolean addlAcct){

		BigDecimal value=FDCHelper.ZERO;
		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		
		int level=entry.getLevel();
		int acctLevel=entry.getAccountView().getLevel();
		int projLevel=entry.getCurProject().getLevel();
		
		AccountViewInfo parAcct=entry.getAccountView();
		CurProjectInfo parProj=entry.getCurProject();
		boolean isAddlAcct;
		if(addlAcct){
			isAddlAcct=addlAcct;
		}else{
			isAddlAcct=entry.isIsAddlAccount();
		}
		
		//分摊类型，根据父级的分摊类型设置直接下级的分摊指标数据
		ApportionTypeInfo apptType=entry.getApportionType();
				
		CurProjectInfo	curProj=null;
		CurProjCostEntriesInfo projCost=null;
		CurProjCostEntriesCollection projCosts=null;
		
		boolean isTrue=false;
		for(int i=index+1; i<entrys.size(); i++){
			entry=entrys.get(i);
			
			isTrue=false;
			if(isAddlAcct){
				//当前工程的直接下级科目	
				if(entry.getCurProject().getId().equals(parProj.getId())){
					if(entry.getAccountView().getLevel()==acctLevel+1
							&& entry.getLevel()==level){
						isTrue=true;
					}
				}else{
					break;
				}
			}else{		
				/*//直接下级工程的相同科目
				if(entry.getCurProject().getLevel()==projLevel+1
						&& entry.getAccountView().getLongNumber().replace('!','.').equals(costSplitAcctNo)){
					isTrue=true;
				}		*/		
				isTrue=true;
			}
				
			if(isTrue){
				
				if(entry.isIsLeaf() && entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
					//自动拆分和末级拆分中的产品拆分，不用设置分摊值
								
				}else if(entry.isIsAddlAccount()){
					//附加科目，将直接费用作为分摊值
					entry.setApportionValue(entry.getDirectAmount());
					entry.setDirectAmount(FDCHelper.ZERO);
					
				}else if(!entry.isIsApportion()){
					//不参与分摊，只有直接费用
					entry.setApportionValue(FDCHelper.ZERO);
					
				}else{
					//使用工程项目的分摊指标作为分摊值
					//工程项目集
					for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
						curProj=(CurProjectInfo)iter2.next();
						
						if(curProj.getId().equals(entry.getCurProject().getId())){
							//工程项目分摊值集
							projCosts=curProj.getCurProjCostEntries();
							
							for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
								projCost=(CurProjCostEntriesInfo)iter3.next();
								
								if(projCost.getApportionType().getId().equals(apptType.getId())){
									value=projCost.getValue();
									entry.setApportionValue(value);
									
									break;
								}
							}
							
							break;						
						}					
					}
					
				}
				
				//递归调用
				getApportionValue(entrys,i,false);
								
			}				
			
		}
		
		if(!isAddlAcct && !parAcct.isIsLeaf()){
			//getApportionValue(entrys,index,true);						
		}
		
		
		/*
		BigDecimal value=FDCHelper.ZERO;
		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		
		int acctLevel=entry.getAccountView().getLevel();
		int projLevel=entry.getCurProject().getLevel();
		
		CurProjectInfo proj=entry.getCurProject();
		boolean isAddlAcct=entry.isIsAddlAccount();
		
		//分摊类型，根据父级的分摊类型设置直接下级的分摊指标数据
		ApportionTypeInfo apptType=entry.getApportionType();
				
		CurProjectInfo	curProj=null;
		CurProjCostEntriesInfo projCost=null;
		CurProjCostEntriesCollection projCosts=null;
		
		boolean isTrue=false;
		for(int i=index+1; i<entrys.size(); i++){
			entry=entrys.get(i);
			
			isTrue=false;
			if(isAddlAcct){
				//当前工程的直接下级科目	
				if(entry.getCurProject().getId().equals(proj.getId())
						&& entry.getAccountView().getLevel()==acctLevel+1){
					isTrue=true;
				}				
			}else{		
				//直接下级工程的相同科目
				if(entry.getCurProject().getLevel()==projLevel+1
						&& entry.getAccountView().getLongNumber().replace('!','.').equals(costSplitAcctNo)){
					isTrue=true;
				}				
			}
				
			if(isTrue){
				
				if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT)){
					//自动拆分和末级拆分中的产品拆分，不用设置分摊值
								
				}else if(entry.isIsAddlAccount()){
					//附加科目，将直接费用作为分摊值
					entry.setApportionValue(entry.getDirectAmount());
					entry.setDirectAmount(FDCHelper.ZERO);
					
				}else if(!entry.isIsApportion()){
					//不参与分摊，只有直接费用
					entry.setApportionValue(FDCHelper.ZERO);
					
				}else{
					//使用工程项目的分摊指标作为分摊值
					//工程项目集
					for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
						curProj=(CurProjectInfo)iter2.next();
						
						if(curProj.getId().equals(entry.getCurProject().getId())){
							//工程项目分摊值集
							projCosts=curProj.getCurProjCostEntries();
							
							for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
								projCost=(CurProjCostEntriesInfo)iter3.next();
								
								if(projCost.getApportionType().getId().equals(apptType.getId())){
									value=projCost.getValue();
									entry.setApportionValue(value);
									
									break;
								}
							}
							
							break;						
						}					
					}
					
				}
				
				//递归调用
				getApportionValue(entrys,i);
			}				
			
			
		}
		
		*/
		
		
		
		
		
		
		/*BigDecimal value=FDCHelper.ZERO;
		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		int level=entry.getLevel();
		int acctLevel=entry.getAccountView().getLevel();
		int projLevel=entry.getCurProject().getLevel();
		
		//分摊类型
		ApportionTypeInfo apptType=entry.getApportionType();
		

		CurProjectInfo	curProj=null;
		CurProjCostEntriesInfo projCost=null;
		CurProjCostEntriesCollection projCosts=null;
		
		for(int i=index+1; i<entrys.size(); i++){
			entry=entrys.get(i);
			
			//直接下级
			if(entry.getLevel()==level+1){		
				if(entry.getSplitType()!=null && entry.getSplitType().equals(CostSplitType.PRODSPLIT) && entry.isIsLeaf()){
					//自动拆分和末级拆分中的产品拆分，不用设置分摊值
								
				}else if(entry.isIsAddlAccount()){
					//附加科目，将直接费用作为分摊值
					entry.setApportionValue(entry.getDirectAmount());
					entry.setDirectAmount(FDCHelper.ZERO);
					
				}else if(!entry.isIsApportion()){
					//不参与分摊，只有直接费用
					entry.setApportionValue(FDCHelper.ZERO);
					
				}else{
					//工程项目集
					for (Iterator iter2 = curProjectCollection.iterator(); iter2.hasNext();){
						curProj=(CurProjectInfo)iter2.next();
						
						if(curProj.getId().equals(entry.getCurProject().getId())){
							//工程项目分摊值集
							projCosts=curProj.getCurProjCostEntries();
							
							for (Iterator iter3 = projCosts.iterator(); iter3.hasNext();){
								projCost=(CurProjCostEntriesInfo)iter3.next();
								
								if(projCost.getApportionType().getId().equals(apptType.getId())){
									value=projCost.getValue();
									entry.setApportionValue(value);
									
									break;
								}
							}
							
							break;						
						}					
					}
					
				}
				
				//递归调用
				getApportionValue(entrys,i);	
				
			}else if(entry.getLevel()<=level){
				break;
			}
			
		}*/
		
		
		
    	tHelper.getDisabledTables().add(kdtEntrys);
		
	}
	
	
	private boolean isAddlAccount(AccountViewInfo costAcct){
		return false;
	}

	
	private boolean hasAddlAccount(AccountViewInfo costAcct){
		String topAcctNo=costSplit.get(0).getAccountView().getLongNumber();
		/*
		if(costAcct.getLongNumber().equals(topAcctNo) && costAcct.isIsLeaf()){
			return false;
		}else{
			return true;
		}*/

		if(costAcct.isIsLeaf()){
			return false;
		}else{
			return true;
		}
		
		/*
		if(costAcct.isIsLeaf()){
			return false;
		}
		
		BOSUuid projId=null;
		if(costAcct.getCurProject().getParent()==null){
			return false;
		}else{
			projId=costAcct.getCurProject().getParent().getId();
		}
		
		String acctNo=null;
		acctNo=costAcct.getLongNumber();
		
		AccountViewInfo acct=null;
		for(Iterator iter=costAccountCollection.iterator(); iter.hasNext(); ){
			acct=(AccountViewInfo)iter.next();
			
			if(acct.getCurProject().getId().equals(projId) && acct.getLongNumber().equals(acctNo)){
				if(acct.isIsLeaf()){
					return true;
				}else{
					return false;
				}
			}
		}
		
		return false;*/
	}
	
	


	private void setEntrySelected(int index, boolean isSelected){

		Boolean valSelect=Boolean.FALSE;
		if(isSelected){
			valSelect=Boolean.TRUE;
		}
		
		kdtEntrys.getRow(index).getCell("isSelected").setValue(valSelect);
	}

	private void setSelected(int index, boolean isSelected){				
		FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(index).getUserObject();	
		if(entry.getAccountView()==null){
			return;
		}
		
		//本级科目及下级科目
		/*setSelectedAccountDown(index,isSelected);
		
		setSelectedAccountUp(index,isSelected);*/
		
		if(isSelected){
			setIsSelected(index);
		}else{
			setNoSelected(index);			
		}
	}

	private boolean isSelectedEntry(int index){
		if(index==0){
			return true;
		}
				
		boolean isSelected=false;
		
		IRow row=kdtEntrys.getRow(index);
				
		if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
			isSelected=true;
		}else{
			BigDecimal directAmount;			
			if(row.getCell("directAmount").getValue()!=null){
				directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
			}else{
				directAmount=FDCHelper.ZERO;
			}
			if(directAmount.compareTo(FDCHelper.ZERO)!=0){
				isSelected=true;
			}
		}
		
		return isSelected;
	}
	
	
	private BigDecimal getApportionValue(FDCNoCostSplitBillEntryInfo entry, ApportionTypeInfo approtionType){	
		BigDecimal value=FDCHelper.ZERO;

		if(approtionType==null){
			return value;
		}
		
		BOSUuid projId=null;
		if(entry!=null 
				&& entry.getAccountView()!=null 
				&& entry.getCurProject()!=null){
			projId=entry.getCurProject().getId();
		}
		if(projId==null){
			return value;
		}
		
		CurProjectInfo curProj=null;
		CurProjCostEntriesCollection projCosts=null;
		CurProjCostEntriesInfo projCost=null;
		
		for (Iterator iter = curProjectCollection.iterator(); iter.hasNext();){
			curProj=(CurProjectInfo)iter.next();
			
			if(curProj.getId()!=null 
					&& curProj.getId().equals(projId)){
				
				//工程项目分摊值集
				projCosts=curProj.getCurProjCostEntries();				
				for (Iterator iter2 = projCosts.iterator(); iter2.hasNext();){
					projCost=(CurProjCostEntriesInfo)iter2.next();
					
					if(projCost.getApportionType()!=null
							&& projCost.getApportionType().getId().equals(approtionType.getId())){
						value=projCost.getValue();
						if(value==null){
							value=FDCHelper.ZERO;
						}
						
						break;
					}
				}
				
				break;						
			}					
		}
		
		return value;
	}
	
	

	private void setIsSelected(int index){
		IRow row=kdtEntrys.getRow(index);
		FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
		CurProjectInfo selProj=entry.getCurProject();		
		CurProjectInfo proj=null;
		
		//设置上级工程
		int projLevel=selProj.getLevel()-1;
		for(int j=index-1; j>=rowBase; j-- ){
			row=kdtEntrys.getRow(j);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			proj=entry.getCurProject();
			
			if(proj.getLevel()==projLevel){
				setEntrySelected(j,true);
				projLevel--;								
			}						
		}
		
		projLevel=selProj.getLevel()+1;//子级
		//TODO 有问题
		for(int j=index+1; j<kdtEntrys.getRowCount(); j++){
			row=kdtEntrys.getRow(j);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			proj=entry.getCurProject();
			//本级以下的全部设置成已选
			if(proj.getLevel()>=projLevel){
				setEntrySelected(j,true);
			}else{
				break;
			}
		}
	}


	private void setNoSelected(int index){
		IRow row=kdtEntrys.getRow(index);
		FDCNoCostSplitBillEntryInfo entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
		int level=entry.getLevel();
		//下级全部设置成未选
		for(int i=index+1;i<kdtEntrys.getRowCount();i++){
			FDCNoCostSplitBillEntryInfo tmpEntry=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(i).getUserObject();
			if(tmpEntry.getLevel()>level){
				setEntrySelected(i, false);
			}else{
				break;
			}
			
		}
		
		//上级,若没有被选中的下级,则上级设置成未选,否则跳出
		setParentNoSelect(index,level);
		
	}
	
	private void setParentNoSelect(int index,int level){
		FDCNoCostSplitBillEntryInfo entry=null;
		IRow row=null;
		//向后的同级
		for(int i=index+1;i<kdtEntrys.getRowCount();i++){
			row = kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()==level&&((Boolean)row.getCell("isSelected").getValue()).booleanValue()){
				return;
			}else if(entry.getLevel()<level){
				break;
			}
		}
		
		//向前的同级及递归父级
		for(int i=index-1;i>=rowBase;i--){
			row = kdtEntrys.getRow(i);
			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
			if(entry.getLevel()==level&&((Boolean)row.getCell("isSelected").getValue()).booleanValue()){
				return;
			}else if(entry.getLevel()==(level-1)){
				setEntrySelected(i, false);
				setParentNoSelect(i, entry.getLevel());
				break;
			}
		}
		
	}
	
	
}