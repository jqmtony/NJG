/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjCostEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostSplitApptProjUI extends AbstractCostSplitApptProjUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostSplitApptProjUI.class);
        
    //成本科目
    private CostAccountCollection costAccountCollection=null;
    //分摊数据（建筑面积、可售面积等）
    private CurProjectCollection curProjectCollection=null;
    private Map curProjCostEntries=null;
    private Map parentMap = null;
    /**
     * output class constructor
     */
    public CostSplitApptProjUI() throws Exception
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
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
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

    	FDCSplitBillEntryInfo entry=null;
    	BigDecimal directAmount=FDCHelper.ZERO;

    	//boolean isTrue=false;
    	
    	// TODO 明细分摊对象
			
		//分摊类型
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);			
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
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
    				if(!entry.getCostAccount().getCurProject().isIsLeaf() && !entry.isIsAddlAccount()){
    					apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
    					
	        			if(apptType==null){  
	        				
	    					isSelected=false;
	    					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
	    						row=kdtEntrys.getRow(j);
	    						FDCSplitBillEntryInfo entry2=(FDCSplitBillEntryInfo)row.getUserObject();
	    						
	    						if(entry2.getLevel()==entry.getLevel()+1){
	    							if(entry2.getCostAccount().getLongNumber().equals(entry.getCostAccount().getLongNumber())){
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
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
			if(isSelectedEntry(i)
					&& !entry.getCostAccount().getCurProject().isIsLeaf() && !entry.isIsAddlAccount()){
				
				//分摊类型
				if(costSplitType.equals(CostSplitTypeEnum.BOTUPSPLIT)){
					if(i==0){
						apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
					}
				}else{
					apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
				}
				
				// modified by zhaoqin for R131101-0367 on 2013/11/05 start
				if(apptType.getId().toString().equals(ApportionTypeInfo.appointType))	// 指定分摊
					break;
				// modified by zhaoqin for R131101-0367 on 2013/11/05 end
				
				isSelected=false;
				for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
					row=kdtEntrys.getRow(j);
					FDCSplitBillEntryInfo entry2=(FDCSplitBillEntryInfo)row.getUserObject();
					
					if(entry2.getLevel()>=entry.getLevel()+1){
						if(entry2.getCostAccount().getLongNumber().equals(entry.getCostAccount().getLongNumber().replace('.','!'))
								&& isSelectedEntry(j)) {
							if(getApportionValue(entry2,apptType).compareTo(FDCHelper.ZERO)!=0
									|| entry2.isIsAddlAccount()
									){
								isSelected=true;
//								break;
							}else{
								isSelected=false;
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
		FDCSplitBillEntryInfo topEntry=costSplit.get(0);
		CostAccountInfo topAcct=topEntry.getCostAccount();
		fetchData(topEntry);
		//标题
		txtCostObj.setText(topEntry.getCostAccount().getCurProject().getDisplayName());
		txtCostAcct.setText(topEntry.getCostAccount().getDisplayName());
		
		FDCSplitBillEntryInfo entry=null;
		
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
		row.getCell("apportionObj.number").setValue(topEntry.getCostAccount().getCurProject().getLongNumber().replace('!','.'));
		row.getCell("apportionObj.name").setValue(topEntry.getCostAccount().getCurProject().getName());
		row.getCell("costAccount.number").setValue(topEntry.getCostAccount().getLongNumber().replace('!','.'));
		row.getCell("costAccount.name").setValue(topEntry.getCostAccount().getName());
		row.setUserObject(topEntry);
			

		//-----------------------------------------------------------------
		//数据列：分摊类型
		ApportionTypeInfo appt=null;
		ApportionTypeCollection collAppt = (ApportionTypeCollection)dataMap.get("collAppt");
		
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
			
			// modified by zhaoqin for R131101-0367 on 2013/11/05 start
			// 指定分摊不用显示
			if(appt.getId().toString().equals(ApportionTypeInfo.appointType))
				continue;
			// modified by zhaoqin for R131101-0367 on 2013/11/05 end
						
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
		int topAcctLevel=topEntry.getCostAccount().getLevel();
		int topProjLevel=topEntry.getCostAccount().getCurProject().getLevel();
		
		
		//工程项目
   	
		curProjectCollection = (CurProjectCollection)dataMap.get("curProjs");
		if(curProjectCollection==null||curProjectCollection.size()==0){
			return;
		}
    	costAccountCollection=(CostAccountCollection)dataMap.get("costAccts");
    	this.curProjCostEntries=(Map)dataMap.get("curProjCostEntries");
		apptObjIds=new String[costAccountCollection.size()];
				    	  			
		CurProjectInfo curProj=null;
		CostAccountInfo costAcct=null;
		boolean isFirstAcct=false;
		//IRow rowFirst=null;
		

		boolean isAddlAcct=false;
		
		idx=0;
		//工程项目
		for (Iterator iter = curProjectCollection.iterator(); iter.hasNext();)
		{
			curProj = (CurProjectInfo) iter.next();

			isFirstAcct=true;
			
			
			/*工程项目没有对应的成本科目时，在分摊设置时不显示		jelon 12/27/2006
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
			for (Iterator iter2 = apptDatas.iterator(); iter2.hasNext();)
			{
				apptData=(CurProjCostEntriesInfo)iter2.next();
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
			*/		
			
			
			
			//成本科目，当前工程项目中符合条件的所有成本科目
			for(Iterator iter2=costAccountCollection.iterator(); iter2.hasNext(); ){
				costAcct=(CostAccountInfo)iter2.next();				
				if(costAcct.getCurProject().getId().equals(curProj.getId())){
					
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
//						CurProjCostEntriesCollection apptDatas=curProj.getCurProjCostEntries();	
						CurProjCostEntriesCollection apptDatas=(CurProjCostEntriesCollection)this.curProjCostEntries.get(curProj.getId().toString());	
						if(apptDatas==null){
							apptDatas=new  CurProjCostEntriesCollection();
						}
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
						/*** 修改物理非明细，而是过滤后的可拆分科目，不带出子集 ***/
						// row = kdtEntrys.addRow();
						//						
						// entry=createNewEntryData();
						continue;
					}
					
					row.getCell("id").setValue(costAcct.getId());
					row.getCell("isSelected").setValue(Boolean.FALSE);
					//row.getCell("apportionObj.number").setValue(curProj.getLongNumber().replace('!', '.'));
					//row.getCell("apportionObj.name").setValue(curProj.getName());
					
					row.getCell("costAccount.number").setValue(costAcct.getLongNumber().replace('!', '.'));
					row.getCell("costAccount.name").setValue(costAcct.getName());

					apptObjIds[idx]=costAcct.getId().toString();

					idx++;	
									
					//entry=new FDCSplitBillEntryInfo();
					//entry=createNewEntryData();
					
					entry.setCostAccount(costAcct);
					
					level=topLevel;
					level+=entry.getCostAccount().getCurProject().getLevel() - topProjLevel;
					level+=entry.getCostAccount().getLevel() - topAcctLevel;
					entry.setLevel(level);

					if(costAcct.isIsLeaf() && costAcct.getCurProject().isIsLeaf()){
						isLeaf=true;
						row.getCell("apportionType").getStyleAttributes().setLocked(true);
					}else{
						isLeaf=false;
					}
					entry.setIsLeaf(isLeaf);
					
					//附加的子科目
					isAddlAcct=false;
					if(isAddlAccount(costAcct)){
						isAddlAcct=true;		
						
						row.getCell("apportionType").setValue("直接分配");
						row.getCell("apportionType").getStyleAttributes().setLocked(true);					
					}
					entry.setIsAddlAccount(isAddlAcct);		
					
					//设置上级项目
					/*if(isAddlAcct){
						entry.setParentItem(costAcct.getParent().getId());
					}else{						
						//上级工程的科目ID
						
						if(level==1){
							entry.setParentItem(costSplit.get(0).getCostAccount().getId());
						}else{
							for(Iterator iter3=costAccts.iterator(); iter3.hasNext(); ){
								costAcct3=(CostAccountInfo)iter3.next();
								
								if(curProj.getParent()!=null){
									if(costAcct3.getCurProject().getId().equals(curProj.getParent().getId())
											&& costAcct3.getLongNumber().replace('!','.').equals(costSplitAcctNo)){
										entry.setParentItem(costAcct3.getId());
										break;
									}
								}else{
									break;
								}
							}							
						}
					}*/
									
					//row.setUserObject(entry);

					if(isFirstAcct){
						isFirstAcct=false;
					}else{
						row.setUserObject(entry);
					}
					
				}else{
					//break;
				}				
			}
			
		}			
				
		
		

		//-----------------------------------------------------------------
		//界面控制
		
		String topAcctNo=topAcct.getLongNumber().replace('.', '!');
		
		for(int i=0; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
						
			// TODO 测试树形
			if(i==0){
				//topAcct=entry.getCostAccount();
			//}else{
			}else if(entry!=null && entry.getCostAccount()!=null){
				CellTreeNode node = new CellTreeNode();
				cell=row.getCell("apportionObj.name");			
				// 节点的值
				node.setValue(cell.getValue());
				// 是否有子节点
				if(entry.getCostAccount().getLongNumber().equals(topAcctNo) 
						&& !entry.getCostAccount().getCurProject().isIsLeaf()){
					node.setHasChildren(true);
				}else{
					node.setHasChildren(false);				
				}			
				// 节点的树级别
				node.setTreeLevel(entry.getLevel()-1);
				cell.getStyleAttributes().setLocked(false);
				cell.setValue(node);
				

				node = new CellTreeNode();
				cell=row.getCell("costAccount.name");			
				// 节点的值
				node.setValue(cell.getValue());
				// 是否有子节点
				node.setHasChildren(!entry.getCostAccount().isIsLeaf());
				// 节点的树级别
				node.setTreeLevel(entry.getCostAccount().getLevel()-topAcctLevel);
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
						&& (entry!=null && !entry.getCostAccount().getCurProject().isIsLeaf()) && !entry.isIsAddlAccount()){	
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
				
				if(entry!=null && entry.getCostAccount()!=null && entry.getCostAccount().getCurProject()!=null
						&& entry.getCostAccount().getCurProject().isIsLeaf()){
					if(entry.getCostAccount().getLongNumber().equals(topAcctNo) || entry.getCostAccount().isIsLeaf()){
						row.getCell("directAmount").getStyleAttributes().setLocked(false);			
						row.getCell("directAmount").getStyleAttributes().setBackground(new Color(0xFFFFFF));
						
						//if(entry.getCostAccount().isIsLeaf()){
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
	public FDCSplitBillEntryCollection getData() throws Exception {
		// TODO 自动生成方法存根
		//return super.getData();
				

		FDCSplitBillEntryCollection entrys = new FDCSplitBillEntryCollection();		
		FDCSplitBillEntryInfo entry=null;
		
		String id;
		IRow row;
		int colIdx;
		BigDecimal directAmount=FDCHelper.ZERO;
		boolean isTrue=false;
		
		CostAccountInfo costAcct=null;
		ApportionTypeInfo apptType=null;
		
		boolean isSelected=false;
		int level=0;
		
			
		//分摊类型	
		row=kdtEntrys.getRow(0);
		entry=(FDCSplitBillEntryInfo)row.getUserObject();
		apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
		entry.setApportionType(apptType);
		entry.setIsLeaf(false);
		entrys.add(entry);
		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
			if(row.getCell("directAmount").getValue()!=null){
				directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
			}else{
				directAmount=FDCHelper.ZERO;
			}
					
			//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || directAmount.compareTo(FDCHelper.ZERO)!=0){
			/*if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || 
					(directAmount.compareTo(FDCHelper.ZERO)!=0) && !entry.isIsAddlAccount()){*/
			isSelected=false;
			
			// modified by zhaoqin for R131101-0367 on 2013/11/05 start
			//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || 
					//(directAmount.compareTo(FDCHelper.ZERO)!=0) && !entry.isIsAddlAccount()){
			if(row.getCell("isSelected").getValue().equals(Boolean.TRUE) || !entry.isIsAddlAccount()){	
				if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
					isSelected=true;
					
				}else{
					/*
					//检查同级科目是否有选择		Jelon	Dec 13, 2006
					level=entry.getLevel();

					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						FDCSplitBillEntryInfo entry2=(FDCSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
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
							FDCSplitBillEntryInfo entry2=(FDCSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
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
					}*/
					isSelected=false;
					// modified by zhaoqin for R131101-0367 on 2013/11/05 end
				}
			}
			
			if (isSelected) {
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
	    	    		if(!entry.getCostAccount().getCurProject().isIsLeaf() && !entry.isIsAddlAccount()){
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
				BOSUuid acctid=entry.getCostAccount().getId();
				isTrue=false;
				for(int j=0; j<costSplit.size(); j++){
					FDCSplitBillEntryInfo split=costSplit.get(j);			
					
					if(split.getSplitType()!=null && split.getSplitType().equals(CostSplitTypeEnum.PRODSPLIT)){
						//BOSUuid acctid=prodEntry.getCostAccount().getId();
						
						if(split.getCostAccount().getId().equals(acctid)){
							isTrue=true;
							
							if(split.isIsLeaf()){	//产品分摊明细
								//split.setIsAddlAccount(entry.isIsAddlAccount());
								/*entry=(FDCSplitBillEntryInfo)split.clone();
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
		setIdxApportionID(entrys);
				
				
		disposeUIWindow();
		
		return entrys;
		
		
		/*
		FDCSplitBillEntryCollection entrys = new FDCSplitBillEntryCollection();
		
		FDCSplitBillEntryInfo entry=null;
		
		String id;
		IRow row;
		int colIdx;
		BigDecimal value=FDCHelper.ZERO;
		boolean isTrue=false;
		
		CostAccountInfo costAcct=null;
		ApportionTypeInfo apptType=null;
		
		
		entry=new FDCSplitBillEntryInfo();		
		//分摊类型	
		row=kdtEntrys.getRow(0);
		apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
		entry.setApportionType(apptType);
		entrys.add(entry);
		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			
    		if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
    			entry=new FDCSplitBillEntryInfo();		
    			
    			//分摊对象    			
    			id=row.getCell("id").getValue().toString();
    			isTrue=false;
    			for (Iterator iter = costAccountCollection.iterator(); iter.hasNext();){
    				costAcct=(CostAccountInfo)iter;
    				if(costAcct.getId().toString().equals(id)){
    					isTrue=true;
    					break;
    				}
    			}
    			for(int j=0; j<costAccountCollection.size(); j++){
    				costAcct=costAccountCollection.get(j);
    				if(costAcct.getId().toString().equals(id)){
    					isTrue=true;
    					break;   					
    				}
    			}
    			if(isTrue){
    				entry.setCostAccount(costAcct);    				
    			}
    						
    			//分摊类型
    			apptType=(ApportionTypeInfo)row.getCell("apportionType").getValue();
    			entry.setApportionType(apptType);
    			
    			//分摊数据
    			id=apptType.getId().toString();
    			colIdx=0;
    			for(int j=0; j<apptTypeIds.length; j++){
    				if(apptTypeIds[j].equals(id)){
    					colIdx=colBase+j;
    					break;
    				}
    			}
    			
    			value=FDCHelper.ZERO;
    			if(colIdx>0){
    				if(row.getCell(colIdx).getValue()!=null){
    					value=new BigDecimal(row.getCell(colIdx).getValue().toString());
    				}
    			}		
    			entry.setApportionValue(value);
    			
    			value=FDCHelper.ZERO;
				if(row.getCell("directAmount").getValue()!=null){
					value=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
				}
				entry.setDirectAmount(value);
				
				entrys.add(entry);
    		}
		}
		

		disposeUIWindow();
		
		return entrys;*/
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

		FDCSplitBillEntryInfo entry=null;
		FDCSplitBillEntryInfo split=null;
		
		ProductTypeInfo prod=null;
		
		//for(int i=0; i<kdtEntrys.getRowCount(); i++){
		for(int i=1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			
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
						if(split.getCostAccount().getId().toString().equals(id)){
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

    

	
	

	protected void getApportionValue(FDCSplitBillEntryCollection entrys, int index){

				
		
		BigDecimal value=FDCHelper.ZERO;
		
		FDCSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		
		int acctLevel=entry.getCostAccount().getLevel();
		int projLevel=entry.getCostAccount().getCurProject().getLevel();
		
		CurProjectInfo proj=entry.getCostAccount().getCurProject();
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
				if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())
						&& entry.getCostAccount().getLevel()==acctLevel+1){
					isTrue=true;
				}				
			}else{		
				//直接下级工程的相同科目
				if(entry.getCostAccount().getCurProject().getLevel()==projLevel+1
						&& entry.getCostAccount().getLongNumber().replace('!','.').equals(costSplitAcctNo)){
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
								&& entry!=null && entry.getCostAccount()!=null && entry.getCostAccount().getCurProject()!=null
								&& entry.getCostAccount().getCurProject().getId()!=null
								&& curProj.getId().equals(entry.getCostAccount().getCurProject().getId())){
							//工程项目分摊值集
//							projCosts=curProj.getCurProjCostEntries();
							projCosts=(CurProjCostEntriesCollection)this.curProjCostEntries.get(curProj.getId().toString());
							if(projCosts==null){
								continue;
							}
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
	
	
	
	protected void getApportionValue(FDCSplitBillEntryCollection entrys, int index, boolean addlAcct){

		BigDecimal value=FDCHelper.ZERO;
		
		FDCSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		
		int level=entry.getLevel();
		int acctLevel=entry.getCostAccount().getLevel();
		int projLevel=entry.getCostAccount().getCurProject().getLevel();
		
		CostAccountInfo parAcct=entry.getCostAccount();
		CurProjectInfo parProj=entry.getCostAccount().getCurProject();
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
				if(entry.getCostAccount().getCurProject().getId().equals(parProj.getId())){
					if(entry.getCostAccount().getLevel()==acctLevel+1
							&& entry.getLevel()==level){
						isTrue=true;
					}
				}else{
					break;
				}
			}else{		
				/*//直接下级工程的相同科目
				if(entry.getCostAccount().getCurProject().getLevel()==projLevel+1
						&& entry.getCostAccount().getLongNumber().replace('!','.').equals(costSplitAcctNo)){
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
						
						if(curProj.getId().equals(entry.getCostAccount().getCurProject().getId())){
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
		
		FDCSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		
		int acctLevel=entry.getCostAccount().getLevel();
		int projLevel=entry.getCostAccount().getCurProject().getLevel();
		
		CurProjectInfo proj=entry.getCostAccount().getCurProject();
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
				if(entry.getCostAccount().getCurProject().getId().equals(proj.getId())
						&& entry.getCostAccount().getLevel()==acctLevel+1){
					isTrue=true;
				}				
			}else{		
				//直接下级工程的相同科目
				if(entry.getCostAccount().getCurProject().getLevel()==projLevel+1
						&& entry.getCostAccount().getLongNumber().replace('!','.').equals(costSplitAcctNo)){
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
						
						if(curProj.getId().equals(entry.getCostAccount().getCurProject().getId())){
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
		
		FDCSplitBillEntryInfo entry=null;
		
		entry=entrys.get(index);		
		if(entry.isIsLeaf()){
			return;
		}
		int level=entry.getLevel();
		int acctLevel=entry.getCostAccount().getLevel();
		int projLevel=entry.getCostAccount().getCurProject().getLevel();
		
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
						
						if(curProj.getId().equals(entry.getCostAccount().getCurProject().getId())){
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
	
	
	private boolean isAddlAccount(CostAccountInfo costAcct){
		String topAcctNo=costSplit.get(0).getCostAccount().getLongNumber().replace('.','!');

		if(costAcct.getLongNumber().equals(topAcctNo)){
			return false;
		}else{
			return true;
		}
		
		/*
		BOSUuid projId=null;
		if(costAcct.getCurProject().getParent()==null){
			return false;
		}else{
			projId=costAcct.getCurProject().getParent().getId();
		}
		
		String acctNo=null;
		acctNo=costAcct.getLongNumber();
		
		CostAccountInfo acct=null;
		for(Iterator iter=costAccountCollection.iterator(); iter.hasNext(); ){
			acct=(CostAccountInfo)iter.next();
			
			if(acct.getCurProject().getId().equals(projId) && acct.getLongNumber().equals(acctNo)){
				return false;
			}
		}
		
		return true;*/
	}

	
	private boolean hasAddlAccount(CostAccountInfo costAcct){
		String topAcctNo=costSplit.get(0).getCostAccount().getLongNumber();
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
		
		CostAccountInfo acct=null;
		for(Iterator iter=costAccountCollection.iterator(); iter.hasNext(); ){
			acct=(CostAccountInfo)iter.next();
			
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
		FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)kdtEntrys.getRow(index).getUserObject();	
		if(entry.getCostAccount()==null){
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
	

	/*
	private void setSelectedProject(int index, boolean isSelected){
		setSelectedAccountDown(index,isSelected);
		int curIdx=0;	//=setSelectedAccountUp(index,isSelected);
		
		FDCSplitBillEntryInfo curEntry=(FDCSplitBillEntryInfo) kdtEntrys.getRow(curIdx).getUserObject();
		if(curEntry.isIsLeaf()){
			return;
		}
		CostAccountInfo curAcct=curEntry.getCostAccount();
		if(curAcct==null){
			return;
		}
		CurProjectInfo curProj=curAcct.getCurProject();
		
		int level=curEntry.getLevel();
		int projLevel=curProj.getLevel();
		
		FDCSplitBillEntryInfo entry=null;
		IRow row=null;
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		
		//下级工程项目
		for(int i=curIdx+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();				
			
			if(entry.getCostAccount().getCurProject().getLevel()==projLevel+1){
				if(acct.getLongNumber().equals(curAcct.getLongNumber())){
					row.getCell("isSelected").setValue(Boolean.FALSE);

					setSelectedProject(i,isSelected);
					
				}
			}else if(entry.getLevel()<level){
				break;
			}
		}
		
	}*/
	
	/*
	//本级科目及下级科目
	private void setSelectedAccountDown(int index, boolean isSelected){
		FDCSplitBillEntryInfo entry=null;		
		IRow row=null;
		
		entry=(FDCSplitBillEntryInfo) kdtEntrys.getRow(index).getUserObject();
		CostAccountInfo selAcct=entry.getCostAccount();
		CurProjectInfo selProj=selAcct.getCurProject();
		int selProjLevel=selProj.getLevel();
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		Boolean valSelect=null;
		if(isSelected){
			valSelect=Boolean.TRUE;
		}else{
			valSelect=Boolean.FALSE;
		}
		
		for(int i=index; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();		
			
			//下级工程项目
			if(proj.getLevel()>selProjLevel
					|| proj.getId().equals(selProj.getId())){
				
				//科目编码相同及下级科目
				if(acct.getLongNumber().startsWith(selAcct.getLongNumber())){					
					//row.getCell("isSelected").setValue(valSelect);
					setEntrySelected(index,isSelected);
				}
				
			}else{
				break;
			}			
		}
	}*/

	/*
	//上级科目
	private void setSelectedAccountUp(int index, boolean isSelected){		
		Boolean valSelect=Boolean.FALSE;
		if(isSelected){
			valSelect=Boolean.TRUE;
		}
		
		FDCSplitBillEntryInfo entry=null;		
		IRow row=null;
		
		entry=(FDCSplitBillEntryInfo) kdtEntrys.getRow(index).getUserObject();
		CostAccountInfo selAcct=entry.getCostAccount();
		CurProjectInfo selProj=selAcct.getCurProject();
		int selAcctLevel=selAcct.getLevel();
		int selProjLevel=selProj.getLevel();
		
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		boolean isChanged=false;
		
		IRow curRow=null;
		CostAccountInfo curAcct=null;
		CurProjectInfo curProj=null;
		int curProjLevel=0;
		int curAcctLevel=0;

		
		//当前工程项目内，根据科目级次向上循环
		curAcctLevel=selAcctLevel;
		for(int i=index; i>=rowBase; i--){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();		
			
			
			if(proj.getId().equals(selProj.getId())){
				
				if(acct.getLevel()==curAcctLevel){
					curRow=row;
					curAcct=acct;					
					
					//取消选择
					//检查当前科目是否有选中的子科目，如果有则取消操作（isChanged=false）
					if(!isSelected){
						isChanged=true;
						for(int j=i; j<kdtEntrys.getRowCount(); j++){
							row=kdtEntrys.getRow(j);
							entry=(FDCSplitBillEntryInfo)row.getUserObject();
							acct=entry.getCostAccount();
							if(acct==null){
								return;
							}
							proj=acct.getCurProject();
							
							if(proj.getId().equals(curAcct.getCurProject().getId())){
								if(acct.getLevel()==curAcct.getLevel()+1){
									//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
									if(isSelected(j)){
										isChanged=false;
										break;
									}
								}
							}else{
								break;
							}							
						}
						if(!isChanged){
							break;
						}						
					}					
					
					
					//设置当前科目选择状态
					//curRow.getCell("isSelected").setValue(valSelect);
					setEntrySelected(i,isSelected);
					
					
					//设置下级工程项目相应科目的选择状态
					for(int j=index; j<kdtEntrys.getRowCount(); j++){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						acct=entry.getCostAccount();
						if(acct==null){
							return;
						}
						proj=acct.getCurProject();

						if(proj.getLevel()>selProjLevel
								|| proj.getId().equals(selProj.getId())){
							if(acct.getLongNumber().equals(curAcct.getLongNumber())){
								//row.getCell("isSelected").setValue(valSelect);
								setEntrySelected(j,isSelected);
							}
						}else{
							break;
						}
					}
					
					curAcctLevel--;
				}		
					
			}else{
				break;
			}
			
		}
		
		
		

		//当前工程项目内，根据科目级次向上循环
		curAcctLevel=selAcctLevel;
		for(int i=index; i>=rowBase; i--){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();		
			
			if(proj.getId().equals(selProj.getId())){
				
				if(acct.getLevel()==curAcctLevel){
					curAcct=acct;					
					
					//检查当前科目是否有选中的子科目，如果有则取消操作
					if(!isSelected){
						isChanged=true;
						for(int j=rowBase; j<kdtEntrys.getRowCount(); j++){
							row=kdtEntrys.getRow(j);
							entry=(FDCSplitBillEntryInfo)row.getUserObject();
							acct=entry.getCostAccount();
							if(acct==null){
								return;
							}
							proj=acct.getCurProject();
							
							if(proj.getParent().getId().equals(curAcct.getCurProject().getParent().getId())){
								if(acct.getLongNumber().equals(curAcct.getLongNumber())){
									//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
									if(isSelected(j)){
										isChanged=false;
										break;
									}
									
								}
							}else{
								//break;
							}							
						}
						if(!isChanged){
							break;
						}						
					}
					
					
					//row.getCell("isSelected").setValue(valSelect);								
					setEntrySelected(i,isSelected);
					
					//上级工程
					curProjLevel=selProjLevel;						
					for(int j=index; j>=rowBase; j--){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						if(entry==null){
							return;
						}
						acct=entry.getCostAccount();
						if(acct==null){
							return;
						}
						proj=acct.getCurProject();

						if(proj.getLevel()==curProjLevel
								&& acct.getLongNumber().equals(curAcct.getLongNumber())){
							//row.getCell("isSelected").setValue(valSelect);
							setEntrySelected(j,isSelected);
							
							curProjLevel--;
						}else{
							//break;
						}
					}
					
					
					curAcctLevel--;
				}		
					
			}else{
				break;
			}
			
		}		
	}*/
	
	private boolean isSelected(int index){
		//return false;
				
		boolean isSelected=false;
		
		IRow row=kdtEntrys.getRow(index);
				
		if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
			isSelected=true;
		}else{
			/*BigDecimal directAmount;			
			if(row.getCell("directAmount").getValue()!=null){
				directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
			}else{
				directAmount=FDCHelper.ZERO;
			}
			if(directAmount.compareTo(FDCHelper.ZERO)!=0){
				isSelected=true;
			}*/
		}
		
		return isSelected;
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
			// modified by zhaoqin for R131101-0367 on 2013/11/05 start
			isSelected = false;
			/*
			BigDecimal directAmount;			
			if(row.getCell("directAmount").getValue()!=null){
				directAmount=new BigDecimal(row.getCell("directAmount").getValue().toString());    					
			}else{
				directAmount=FDCHelper.ZERO;
			}
			if(directAmount.compareTo(FDCHelper.ZERO)!=0){
				isSelected=true;
			}
			*/
			// modified by zhaoqin for R131101-0367 on 2013/11/05 end
		}
		
		return isSelected;
	}
	
	
	private BigDecimal getApportionValue(FDCSplitBillEntryInfo entry, ApportionTypeInfo approtionType){	
		BigDecimal value=FDCHelper.ZERO;

		if(approtionType==null){
			return value;
		}
		
		BOSUuid projId=null;
		if(entry!=null 
				&& entry.getCostAccount()!=null 
				&& entry.getCostAccount().getCurProject()!=null){
			projId=entry.getCostAccount().getCurProject().getId();
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
//				projCosts=curProj.getCurProjCostEntries();		
				projCosts=(CurProjCostEntriesCollection)this.curProjCostEntries.get(curProj.getId().toString());
				if(projCosts==null){
					continue;
				}
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
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;

		row=kdtEntrys.getRow(index);
		entry=(FDCSplitBillEntryInfo)row.getUserObject();

		CostAccountInfo selAcct=entry.getCostAccount();
		if(selAcct==null){
			return;
		}
		CurProjectInfo selProj=selAcct.getCurProject();		
		String acctNo=selAcct.getLongNumber();
		
		//setIsSelectedByProj(selProj.getId().toString(), acctNo);
				
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		int acctLevel=0;
		int projLevel=0;
		
		

		//设置上级工程的相应科目
		projLevel=selProj.getLevel()-1;
		for(int j=index-1; j>=rowBase; j-- ){
			row=kdtEntrys.getRow(j);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			
			if(proj.getLevel()==projLevel){
				if(acct.getLongNumber().equals(acctNo)){
					setEntrySelected(j,true);
					projLevel--;								
				}
			}						
		}		

			
		
		//设置当前工程的上级科目，以及上级工程项目的相应科目
		acctLevel=selAcct.getLevel()-1;
		for(int i=index-1; i>=rowBase; i--){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			acctNo=acct.getLongNumber();
			
			if(proj.getId().equals(selProj.getId())){
				if(acct.getLevel()==acctLevel){
					setEntrySelected(i,true);
					acctLevel--;
					
					//设置上级工程的相应科目
					projLevel=selProj.getLevel()-1;
					for(int j=i-1; j>=rowBase; j-- ){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						acct=entry.getCostAccount();
						if(acct==null){
							return;
						}
						proj=acct.getCurProject();
						
						if(proj.getLevel()==projLevel){
							if(acct.getLongNumber().equals(acctNo)){
								setEntrySelected(j,true);
								projLevel--;								
							}
						}						
					}		
					
				}
			}else{
				break;
			}							
		}

		//设置当前工程的下级科目
		for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			acctNo=acct.getLongNumber();
			
			if(proj.getId().equals(selProj.getId())){
				if(acct.getLevel()>selAcct.getLevel()){
					setEntrySelected(i,true);
					

					//设置上级工程的相应科目
					projLevel=selProj.getLevel()-1;
					for(int j=i-1; j>=rowBase; j-- ){
						row=kdtEntrys.getRow(j);
						entry=(FDCSplitBillEntryInfo)row.getUserObject();
						acct=entry.getCostAccount();
						if(acct==null){
							return;
						}
						proj=acct.getCurProject();
						
						if(proj.getLevel()==projLevel){
							if(acct.getLongNumber().equals(acctNo)){
								setEntrySelected(j,true);
								projLevel--;								
							}
						}						
					}			
					
					
				}else{
					break;
				}
			}else{
				break;
			}							
		}
		
		
		
		//设置下级工程的相应科目
		projLevel=selProj.getLevel();
		acctNo=selAcct.getLongNumber();
		
		for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();	
			
			//不处理当前工程项目包含的附加科目		jelon 12/30/2006
			if(proj.getId().equals(selProj.getId())){
				continue;
			}
			
			if(proj.getLevel()>projLevel){
				if(acct.getLongNumber().equals(acctNo)){
					setIsSelectedByProjDown(proj.getId().toString(), acctNo);				
				}
			}else{
				break;
			}							
		}		
	}


	private void setNoSelected(int index){	
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;

		row=kdtEntrys.getRow(index);
		entry=(FDCSplitBillEntryInfo)row.getUserObject();

		CostAccountInfo topAcct=entry.getCostAccount();
		if(topAcct==null){
			return;
		}
		CurProjectInfo topProj=topAcct.getCurProject();
				
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		int level=0;
		boolean isFound;
		
		
		//当前工程的下级科目
		setNoSelectedByProjDown(index,topProj.getId().toString(),topAcct.getLevel());
		
		//当前工程的上级工程
		setNoSelectedByProjUp(index, topProj.getLevel(), topAcct.getLongNumber());
		
		
		
		
		//在当前工程取得最上级的取消科目			
		boolean isTrue=true;
		while(isTrue){
			isTrue=false;

			isFound=false;

			int idx1=index;
			int idx2=index;
			
			for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				acct=entry.getCostAccount();
				if(acct==null){
					//return;
					break;
				}
				proj=acct.getCurProject();
				
				if(proj.getId().equals(topProj.getId())){
					if(acct.getLevel()==topAcct.getLevel()){
						idx2=i;
					}
				}else{
					break;
				}							
			}

			for(int i=index-1; i>=rowBase; i--){
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				acct=entry.getCostAccount();
				if(acct==null){
					//return;
					break;
				}
				proj=acct.getCurProject();
				
				if(proj.getId().equals(topProj.getId())){
					if(acct.getLevel()==topAcct.getLevel()){
						idx1=i;
					}
				}else{
					break;
				}							
			}		
			
			for(int i=idx1; i<=idx2; i++){
				if(i==index){
					continue;
				}
				
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				acct=entry.getCostAccount();
				if(acct==null){
					return;
				}
				proj=acct.getCurProject();
							
				if(proj.getId().equals(topProj.getId())){
					if(acct.getLevel()==topAcct.getLevel()){
						if(isSelected(i)){
							isFound=true;
							break;
						}
					}
				}else{
					break;
				}							
			}	
			

			if(!isFound){
				for(int i=index-1; i>=rowBase; i--){
					row=kdtEntrys.getRow(i);
					entry=(FDCSplitBillEntryInfo)row.getUserObject();
					acct=entry.getCostAccount();
					if(acct==null){
						return;
					}
					proj=acct.getCurProject();
					
					if(proj.getId().equals(topProj.getId())){
						if(acct.getLevel()==topAcct.getLevel()-1){
							setEntrySelected(i,false);
							
							index=i;							
							topAcct=acct;
							topProj=proj;
														

							setNoSelectedByProjUp(index, topProj.getLevel(), topAcct.getLongNumber());
							
							isTrue=true;
						}
					}else{
						break;
					}							
				}		
				
			}
		}
		
		

		//下级工程项目
		level=topProj.getLevel();
		for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();		
			
			//不处理当前工程项目包含的附加科目		jelon 12/30/2006
			if(proj.getId().equals(topProj.getId())){
				continue;
			}
			
			if(proj.getLevel()>level){
				
				//科目编码相同及下级科目
				if(acct.getLongNumber().startsWith(topAcct.getLongNumber())){	
					setEntrySelected(i,false);

					setNoSelectedByProjUp(i, proj.getLevel(), acct.getLongNumber());
				}
				
			}else{
				break;
			}				
		}
		
	}
	
	
	

	//下级科目
	private void setNoSelectedByProjDown(int index, String projId, int acctLevel){
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
				
		for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();		
			
			if(proj.getId().toString().equals(projId)){				
				//科目编码相同及下级科目
				if(acct.getLevel()>acctLevel){
					setEntrySelected(i,false);
				}else{
					break;
				}
				
			}else{
				break;
			}			
		}		
	}
	
	

	//设置上级工程的相应科目	
	private void setNoSelectedByProjUp(int rowIndex, int projLevel, String acctNo){
		int index=rowIndex;
		int level=projLevel;
		
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		boolean isFound;

		boolean isTrue=true;
		while(isTrue){		
			isTrue=false;
			
			//检查同级是否有选择
			isFound=false;
			
			
			int idx1=index;
			int idx2=index;
			
			for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				acct=entry.getCostAccount();
				if(acct==null){
					//return;
					break;
				}
				proj=acct.getCurProject();
				
				if(proj.getLevel()>=level){
						idx2=i;
				}else{
					break;
				}							
			}

			for(int i=index-1; i>=rowBase; i--){
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				acct=entry.getCostAccount();
				if(acct==null){
					//return;
					break;
				}
				proj=acct.getCurProject();
				
				if(proj.getLevel()>=level){
						idx1=i;
				}else{
					break;
				}							
			}		
			
			
			//for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			for(int i=idx1; i<=idx2; i++){	
				if(i==index){
					continue;
				}
				
				row=kdtEntrys.getRow(i);
				entry=(FDCSplitBillEntryInfo)row.getUserObject();
				acct=entry.getCostAccount();
				if(acct==null){
					return;
				}
				proj=acct.getCurProject();
				
				
				if(proj.getLevel()==level
						&& acct.getLongNumber().equals(acctNo)){
					if(isSelected(i)){
						isFound=true;
						break;						
					}
				}			
			}
			
			//同级没有选择，则取消上级的选择
			if(!isFound){
				level--;		

				for(int i=index-1; i>=rowBase; i--){
					row=kdtEntrys.getRow(i);
					entry=(FDCSplitBillEntryInfo)row.getUserObject();
					acct=entry.getCostAccount();
					if(acct==null){
						return;
					}
					proj=acct.getCurProject();
					
					
					if(proj.getLevel()==level
							&& acct.getLongNumber().equals(acctNo)){
						setEntrySelected(i,false);						
						setNoSelectedByProjDown(i,proj.getId().toString(),acct.getLevel());
						
						index=i;
						isTrue=true;
						
						break;
					}			
				}
				
			}
			
		}
	}
	
	
	
	//设置上级工程的相应科目	
	private void setIsSelectedByProjUp(int index, int projLevel, String acctNo){
		int level=projLevel;
		
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		for(int j=index-1; j>=rowBase; j-- ){
			row=kdtEntrys.getRow(j);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			
			if(proj.getLevel()==level){
				if(acct.getLongNumber().equals(acctNo)){
					setEntrySelected(j,true);
					level--;								
				}
			}						
		}		
	}
	
	
	private void setIsSelectedByProjDown(String projId, String acctNo){
		IRow row=null;
		FDCSplitBillEntryInfo entry=null;
		CostAccountInfo acct=null;
		CurProjectInfo proj=null;
		
		CostAccountInfo selAcct=null;
		CurProjectInfo selProj=null;
		
		int index=-1;

		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			
			if(proj.getId().toString().equals(projId) && acct.getLongNumber().equals(acctNo)){
				index=i;
				
				selAcct=acct;
				selProj=proj;
				setEntrySelected(i,true);
				
				break;
			}
		}		
		
		if(index<0){
			return;
		}
		
		
		int level=0;
		

		//设置当前工程的上级科目
		level=selAcct.getLevel()-1;
		for(int i=index-1; i>=rowBase; i--){			;
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			
			if(proj.getId().equals(selProj.getId())){
				if(acct.getLevel()==level){
					setEntrySelected(i,true);
					level--;
				}
			}else{
				break;
			}							
		}

		//设置当前工程的上级科目
		for(int i=index+1; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			entry=(FDCSplitBillEntryInfo)row.getUserObject();
			acct=entry.getCostAccount();
			if(acct==null){
				return;
			}
			proj=acct.getCurProject();
			
			if(proj.getId().equals(selProj.getId())){
				if(acct.getLevel()>selAcct.getLevel()){
					setEntrySelected(i,true);
				}else{
					break;
				}
			}else{
				break;
			}							
		}
	}
	
	private Map dataMap=new HashMap();
	private void fetchData(FDCSplitBillEntryInfo topEntry) throws BOSException{
		//工程项目
		final CostAccountInfo costAccount = topEntry.getCostAccount();
		Map map=new HashMap();
		map.put("costAccount", costAccount);
		
		//清空表格设置
    	String className=this.getClass().getName();
    	String sql = "delete t_fw_usercustomconfig where fuiclassname='"+className+"'";
    	map.put("sql", sql);
    	this.dataMap=FDCCommonServerHelper.getCostSplitApptProjData(map);
	}
}