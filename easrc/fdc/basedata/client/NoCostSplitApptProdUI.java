/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCNoCostSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjProductEntriesCollection;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class NoCostSplitApptProdUI extends AbstractNoCostSplitApptProdUI
{
    private static final Logger logger = CoreUIObject.getLogger(NoCostSplitApptProdUI.class);        
    private ProjProductEntriesCollection projProductEntriesCollection=null;
    
    private boolean isAimFullAppt=false;
    
    public NoCostSplitApptProdUI() throws Exception
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

    /**
     * output kdtEntrys_editStopped method
     */
    protected void kdtEntrys_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        super.kdtEntrys_editStopped(e);
    }

    /**
     * output kdtEntrys_editValueChanged method
     */
    protected void kdtEntrys_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        super.kdtEntrys_editValueChanged(e);
    }

    /**
     * output kdtEntrys_tableClicked method
     */
    protected void kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.kdtEntrys_tableClicked(e);
		
		if(e.getRowIndex()==idxRowApptSel){
			int idx=e.getColIndex();
			if(idx>=colBase && idx<kdtEntrys.getColumnCount()){
				
				if(!isAimFullAppt && apptTypeIds[idx-colBase].equals(ApportionTypeInfo.aimCostType)){
					//目标成本比，目标成本未完全拆分，不能选择目标成本比	jeon 12/19/06
				}else{
					
					IRow row=kdtEntrys.getRow(idxRowApptSel);
					for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
						row.getCell(i).setValue(Boolean.FALSE);
					}
					row.getCell(idx).setValue(Boolean.TRUE);					
				}
				
			}
		}
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
    	
    	boolean isSelected;
    	
    	//允许取消产品拆分
    	if(costSplit.size()>1){
        	isSelected=false;		
    		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
        		if(kdtEntrys.getCell(i,"isSelected").getValue().equals(Boolean.TRUE)){
        			isSelected=true;
        			break;
        		}
    		}
    		if(!isSelected){    	
    	        super.actionSplit_actionPerformed(e);
    	        
    			return;
    		}    		    		
    	}
    	

		//检查是否选择分摊对象（）
    	isSelected=false;		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
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
		
		//检查是否选择分摊类型
    	isSelected=false;		
		for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
    		if(kdtEntrys.getCell(idxRowApptSel,i).getValue().equals(Boolean.TRUE)){
    			isSelected=true;
    			break;
    		}
		}
		if(!isSelected){
			MsgBox.showWarning(this, ContractClientUtils.getRes("costSplitNoApportionType"));
			return;
		}
		
		//检查分摊类型是否有效
		BigDecimal value=FDCHelper.ZERO;
		
    	isSelected=false;		
		for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
    		if(kdtEntrys.getCell(idxRowApptSel,i).getValue().equals(Boolean.TRUE)){
    			
    			for(int j=rowBase; j<kdtEntrys.getRowCount(); j++){
    	    		if(kdtEntrys.getCell(j,"isSelected").getValue().equals(Boolean.TRUE)){
    	    			
    	    			value=(BigDecimal)kdtEntrys.getCell(j,i).getValue();
    	    			if(value==null){
    	    				value=FDCHelper.ZERO;
    	    			}		
    	    			
    	    			if(value.compareTo(FDCHelper.ZERO)!=0){
        	    			isSelected=true;
        	    			break;    	    				
    	    			}
    	    			
    	    		}
    			}
    		}
		}
		if(!isSelected){
			/*if (!MsgBox.isYes(MsgBox
	                .showConfirm2(this,"当前选择的指标数据没有进行设置，是否继续进行拆分？"))){
				return;
			}*/	

			MsgBox.showWarning(this, "请选择有效的分摊类型！");
			return;
		}
		

    	
        super.actionSplit_actionPerformed(e);
    }

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#initTable()
	 */
	protected void initTable() throws Exception {
		//拆分行
		FDCNoCostSplitBillEntryInfo topEntry=costSplit.get(0);		
		int topLevel=topEntry.getLevel();	
		
		//标题
		txtCostObj.setText(costSplit.get(0).getCurProject().getDisplayName());
		txtCostAcct.setText(costSplit.get(0).getAccountView().getDisplayName());
		
		IRow row;
		IColumn col;
		ICell cell;
		
		//index
		int	idx;
		int idxCol;


		boolean isExistAimCost=false;
		
		
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		BigDecimal amount;
		

		//-----------------------------------------------------------------
		//第一行：分摊类型选择（行）
		row = kdtEntrys.addRow();
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(new Color(0xFEFED3));
			

		//-----------------------------------------------------------------
		//数据列：分摊类型
		view = new EntityViewInfo();
		filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("forCostApportion", Boolean.TRUE));
    	view.setFilter(filter);
    	view.getSelector().add("id");
    	view.getSelector().add("name");
    	view.getSorter().add(new SorterItemInfo("number"));
							
		ApportionTypeCollection collAppt = ApportionTypeFactory.getRemoteInstance().getApportionTypeCollection(view);
		apptTypeIds=new String[collAppt.size()];
		
		ApportionTypeInfo appt=null;
		row=kdtEntrys.getRow(0);
		idx=0;		
		for (Iterator iter = collAppt.iterator(); iter.hasNext();)
		{
			appt = (ApportionTypeInfo) iter.next();
			//appt.isForCostApportion()
			if(appt.getId().toString().equals(ApportionTypeInfo.aimCostType)){
				continue;
			}		
			//分摊类型动态增加列
			//col = kdtEntrys.addColumn(kdtEntrys.getColumnCount()-1);
			col = kdtEntrys.addColumn(kdtEntrys.getColumnCount());
			col.setKey(appt.getId().toString());	//设置Key后，动态列才能进行表格设置中出现
			/*col.getStyleAttributes().setNumberFormat("###,##0.00");
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);*/
			col.getStyleAttributes().setLocked(true);
			
			//标题
			kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue(appt.getName());
			
			//选择行
			cell=row.getCell(col.getColumnIndex());
			//cell.getStyleAttributes().setNumberFormat(null);
			//cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			//cell.getStyleAttributes().setLocked(false);
			cell.setValue(Boolean.FALSE);
			cell.getStyleAttributes().setBackground(new Color(0xFFFFFF));
					
			apptTypeIds[idx]=appt.getId().toString();	
			
			idx++;											
		}			
		
		apportionTypeCollection=collAppt;
		
		
		
		//-----------------------------------------------------------------
		//数据行	：分摊对象－产品
		
		String acctId=costSplit.get(0).getAccountView().getId().toString();
		
		//目标成本
//		AimCostSplitDataGetter aimCost=new AimCostSplitDataGetter(
//				costSplit.get(0).getCurProject().getId().toString());		
		//取得产品的金额map
//		Map mapAimCost=aimCost.getProductMap(acctId);
//		//是否完全拆分，完全了就是有成本比
//		isAimFullAppt=aimCost.isFullApportion(acctId);
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("curProject.id", costSplit.get(0).getCurProject().getId().toString()));
    	filter.getFilterItems().add(new FilterItemInfo("productType.isEnabled", Boolean.TRUE));
    	filter.getFilterItems().add(new FilterItemInfo("isAccObj", Boolean.TRUE));		//是否核算对象
    	view.setFilter(filter);
    	//产品类型
    	view.getSelector().add("productType.id");
    	view.getSelector().add("productType.name");
    	//分摊数据
    	view.getSelector().add("curProjProEntrApporData.apportionType.id");
    	view.getSelector().add("curProjProEntrApporData.value");
    	  	
    	
		ProjProductEntriesCollection collObj = CurProjProductEntriesFactory.getRemoteInstance().getProjProductEntriesCollection(view);
		apptObjIds=new String[collObj.size()];
			
		CurProjProductEntriesInfo prod=null;
		FDCNoCostSplitBillEntryInfo entry=null;
		
		String prodTypeId=null;
		
		idx=0;
		for (Iterator iter = collObj.iterator(); iter.hasNext();)
		{
			prod = (CurProjProductEntriesInfo) iter.next();
			
			row = kdtEntrys.addRow();
			row.getCell(0).setValue(prod.getProductType().getId());
			row.getCell(1).setValue(Boolean.FALSE);
			row.getCell(2).setValue(prod.getProductType().getName());
			
			prodTypeId=prod.getProductType().getId().toString();
			apptObjIds[idx]=prodTypeId;
			idx++;	
			
			
			//数据（项目中的产品分摊数据）
			CurProjProEntrApporDataCollection appts=prod.getCurProjProEntrApporData();			
			CurProjProEntrApporDataInfo apptData=null;			
			
			for (Iterator iter2 = appts.iterator(); iter2.hasNext();)
			{
				apptData=(CurProjProEntrApporDataInfo)iter2.next();
				
				//列号
				idxCol=0;
				for(int i=0; i<apptTypeIds.length; i++){
					if(apptData.getApportionType().getId().toString().equals(apptTypeIds[i])){
						idxCol = colBase+i;
						
						//目标成本比处理
						if(apptData.getApportionType().getId().toString().equals(ApportionTypeInfo.aimCostType)){
							amount=FDCHelper.ZERO;
							
//							if (mapAimCost.containsKey(prodTypeId)){
//								amount = (BigDecimal) mapAimCost.get(prodTypeId);							
//								if(amount==null){
//									amount=FDCHelper.ZERO;
//								}
//							}
							apptData.setValue(amount);										
						}
						
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
			}		
			

			//entry=new FDCNoCostSplitBillEntryInfo();
			entry=createNewEntryData();
			
			//entry.setCostAccount(costAcct);
			entry.setProduct(prod.getProductType());
						
			entry.setLevel(topLevel+1);
			entry.setIsLeaf(true);
			
			row.setUserObject(entry);
							
		}			
		
		projProductEntriesCollection=collObj;

		super.initTable();
		
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#getData()
	 */
	public FDCNoCostSplitBillEntryCollection getData() throws Exception {
		// TODO 自动生成方法存根
		//return super.getData();
		
		FDCNoCostSplitBillEntryCollection entrys = new FDCNoCostSplitBillEntryCollection();		
		FDCNoCostSplitBillEntryInfo entry=null;
		
		String id;
		IRow row;
		
		BigDecimal value=FDCHelper.ZERO;
		boolean isTrue=false;
		

		int apptColIdx=0;		
		
		
		//拆分行
		//entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
		entry=costSplit.get(0);
		
//		CostAccountInfo costAcct=entry.getCostAccount();
		AccountViewInfo acct=entry.getAccountView();
		
		//分摊类型	
		ApportionTypeInfo apptType=null;
    	for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
    		if(kdtEntrys.getRow(0).getCell(i).getValue().equals(Boolean.TRUE)){    			
    			apptColIdx=i;			
    			break;
    		}
    	}		
		for (Iterator iter = apportionTypeCollection.iterator(); iter.hasNext();)
		{
			apptType = (ApportionTypeInfo) iter.next();    				
			if(apptType.getId().toString().equals(apptTypeIds[(apptColIdx-colBase)])){
				entry.setApportionType(apptType);
				break;
			}											
		}		
		
		entrys.add(entry);
		
		
		boolean isSelected;
		boolean isApportion;
		
		
		//拆分对象
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			
    		//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
			isSelected=false;		
			isApportion=true;						
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
					isApportion=false;			
				}
				

				//检查同级科目是否有选择		Jelon	Dec 13, 2006
				if(isSelected){
					isSelected=false;
					
					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						FDCNoCostSplitBillEntryInfo entry2=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
						if(kdtEntrys.getRow(j).getCell("isSelected").getValue().equals(Boolean.TRUE)){
							isSelected=true;					
							break;
						}										
					}

					if(!isSelected){
						for(int j=i-1; j>=rowBase; j--){
							FDCNoCostSplitBillEntryInfo entry2=(FDCNoCostSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
							if(kdtEntrys.getRow(j).getCell("isSelected").getValue().equals(Boolean.TRUE)){
								isSelected=true;				
								break;
							}												
						}						
					}
				}
				
				
			}
			
			
			if(isSelected){
    			entry=(FDCNoCostSplitBillEntryInfo)row.getUserObject();
    			    			
    			entry.setAccountView(acct);
    			entry.setCurProject(costSplit.get(0).getCurProject());
				entry.setIsApportion(isApportion);	
    			
				if(isApportion){
	    			value=(BigDecimal)row.getCell(apptColIdx).getValue();
	    			if(value==null){
	    				value=FDCHelper.ZERO;
	    			}					
				}else{
					value=FDCHelper.ZERO;
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
		//getApportionValue(entrys,0);

		disposeUIWindow();
		
		return entrys;
		
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#loadData()
	 */
	protected void loadData() throws Exception {
		// TODO 自动生成方法存根
		super.loadData();
		

		colBase=4;
		
		btnRemove.setVisible(false);
	}

	/* （非 Javadoc）
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#loadPreSplit()
	 */
	protected void loadPreSplit() {
		// TODO 自动生成方法存根
		super.loadPreSplit();
		

		//-----------------------------------------------------------------
		//分摊方案（当前方案）
		if(costSplit.get(0).getApportionType()!=null){
			String id;
			
			//选择分摊类型
	    	id=costSplit.get(0).getApportionType().getId().toString();		

			if(id.equals(ApportionTypeInfo.aimCostType)
					&& !isAimFullAppt){
				//目标成本比，目标成本未完全拆分，不能选择目标成本比	jeon 12/20/06
			}else{
				for(int i=0; i<apptTypeIds.length; i++){
					if(apptTypeIds[i].equals(id)){
						kdtEntrys.getCell(idxRowApptSel,i+colBase).setValue(Boolean.TRUE);
		    			break;
		    		}
				}				
			}
			
			//选择分摊对象
	    	for(int i=1; i<costSplit.size(); i++){
	    		id=costSplit.get(i).getProduct().getId().toString();		
	    		for(int j=0; j<apptObjIds.length; j++){
	    			if(apptObjIds[j].equals(id)){
	    				
	    				if(costSplit.get(i).isIsApportion()){
	    					kdtEntrys.getCell(j+rowBase,"isSelected").setValue(Boolean.TRUE);
	    				}
	    				
	    				if(costSplit.get(i).getDirectAmount()!=null){
	    					kdtEntrys.getCell(j+rowBase,"directAmount").setValue(costSplit.get(i).getDirectAmount());
	    				}
	    				
	        			break;
	        		}
	    		}    		
	    	}	
			
		}
	}

}