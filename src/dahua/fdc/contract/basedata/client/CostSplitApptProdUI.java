/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.aimcost.AimCostSplitDataGetter;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.ProjProductEntriesCollection;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CostSplitApptProdUI extends AbstractCostSplitApptProdUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostSplitApptProdUI.class);
        
    private ProjProductEntriesCollection projProductEntriesCollection=null;
    
    private boolean isAimFullAppt=false;
    
    /**
     * output class constructor
     */
    public CostSplitApptProdUI() throws Exception
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
					//Ŀ��ɱ��ȣ�Ŀ��ɱ�δ��ȫ��֣�����ѡ��Ŀ��ɱ���	jeon 12/19/06
				}else{
					
					IRow row=kdtEntrys.getRow(idxRowApptSel);
					for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
						if(i==idx){
							continue;
						}
						row.getCell(i).setValue(Boolean.FALSE);
					}
					Boolean v=(Boolean)row.getCell(idx).getValue();
					if(v==null) return;
					row.getCell(idx).setValue(Boolean.valueOf(!v.booleanValue()));					
				}
				
			}
		}
    }


    /**
     * output actionSplit_actionPerformed
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
        //super.actionSplit_actionPerformed(e);
    	
    	boolean isSelected;
    	
    	//����ȡ����Ʒ���
    	//ʵ��ֻ��һ��,��֪��ʲôʱ����ж���һ���������? by sxhong 2007/10/26
    	if(costSplit.size()>1){ 
        	isSelected=false;		
    		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
        		if(kdtEntrys.getCell(i,"isSelected").getValue().equals(Boolean.TRUE)){
        			isSelected=true;
        			break;
        		}
    		}
  		    		
    	}
    	

		//����Ƿ�ѡ���̯���󣨣�
    	isSelected=false;		
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
    		if(kdtEntrys.getCell(i,"isSelected").getValue().equals(Boolean.TRUE)){
    			isSelected=true;
    			break;
    		}
		}
		if(!isSelected){
			//MsgBox.showInfo(this,"��ѡ���̯����");
			MsgBox.showWarning(this, ContractClientUtils.getRes("costSplitNoApportionObject"));
			return;
		}    		
		
		//����Ƿ�ѡ���̯����
    	isSelected=false;		
		for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
    		if(kdtEntrys.getCell(idxRowApptSel,i).getValue().equals(Boolean.TRUE)){
    			isSelected=true;
    			break;
    		}
		}
		if(!isSelected){
			//�Ƿ��ǰ�ֱ�ӷ��ý��в��
//			isSelected=false;
			for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
    			if(kdtEntrys.getCell(i,"isSelected").getValue().equals(Boolean.TRUE)){
    				// modified by zhaoqin for R130815-0252 on 2013/10/28
    				// ��ֽ��Ϊ�����������Ʒ��֣���дΪ����ֱ�ӷ���ʱ�����ܲ��
    	    		// if(FDCHelper.isPositiveBigDecimal(kdtEntrys.getCell(i,"directAmount").getValue())){
    				if(!FDCHelper.isZero(kdtEntrys.getCell(i,"directAmount").getValue())){
    	    			isSelected=true;
    	    			break;
    	    		}else{
    	    			//���Ϊ0
    	    			MsgBox.showWarning(this, "ֱ�ӷ��ò�Ϊ��0");
    					return;
    	    		}
    			}

			}
			if(isSelected){
				super.actionSplit_actionPerformed(e);
				return;
			}else{
				MsgBox.showWarning(this, ContractClientUtils.getRes("costSplitNoApportionObject"));
				return;
			}
		}
		
		//����̯�����Ƿ���Ч
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
			MsgBox.showWarning(this, "��ѡ����Ч�ķ�̯���ͣ�");
			return;
		}
		

    	
        super.actionSplit_actionPerformed(e);
    }

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#initTable()
	 */
	protected void initTable() throws Exception {
		// TODO �Զ����ɷ������
		//super.initTable();
		

		//�����
		FDCSplitBillEntryInfo topEntry=costSplit.get(0);		
		fetchData(topEntry);
		int topLevel=topEntry.getLevel();	
		//����
		txtCostObj.setText(costSplit.get(0).getCostAccount().getCurProject().getDisplayName());
		txtCostAcct.setText(costSplit.get(0).getCostAccount().getDisplayName());
		
		
		IRow row;
		IColumn col;
		ICell cell;
		
		//index
		int	idx;
		int idxRow;
		int idxCol;
				
		String rowId;
		String colId;
		

		boolean isExistAimCost=false;
		
		
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		BigDecimal amount;
		

		//-----------------------------------------------------------------
		//��һ�У���̯����ѡ���У�
		row = kdtEntrys.addRow();
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(new Color(0xFEFED3));
			

		//-----------------------------------------------------------------
		//�����У���̯����
						
		ApportionTypeCollection collAppt = (ApportionTypeCollection)dataMap.get("collAppt");
		apptTypeIds=new String[collAppt.size()];
		
		ApportionTypeInfo appt=null;
		row=kdtEntrys.getRow(0);
		idx=0;		
		for (Iterator iter = collAppt.iterator(); iter.hasNext();)
		{
			appt = (ApportionTypeInfo) iter.next();
			//appt.isForCostApportion()
						
			//��̯���Ͷ�̬������
			//col = kdtEntrys.addColumn(kdtEntrys.getColumnCount()-1);
			col = kdtEntrys.addColumn(kdtEntrys.getColumnCount());
			col.setKey(appt.getId().toString());	//����Key�󣬶�̬�в��ܽ��б�������г���
			/*col.getStyleAttributes().setNumberFormat("###,##0.00");
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);*/
			col.getStyleAttributes().setLocked(true);
			
			//����
			kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue(appt.getName());
			
			//ѡ����
			cell=row.getCell(col.getColumnIndex());
			//cell.getStyleAttributes().setNumberFormat(null);
			//cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
			//cell.getStyleAttributes().setLocked(false);
			cell.setValue(Boolean.FALSE);
			cell.getStyleAttributes().setBackground(new Color(0xFFFFFF));
					
			if(appt.getId().toString().equals(ApportionTypeInfo.aimCostType)){
				isExistAimCost=true;
			}
			
			apptTypeIds[idx]=appt.getId().toString();	
			
			idx++;											
		}			
		
		apportionTypeCollection=collAppt;
		//-----------------------------------------------------------------
		//������	����̯���󣭲�Ʒ
		
		String acctId=costSplit.get(0).getCostAccount().getId().toString();
		
		//Ŀ��ɱ�
		AimCostSplitDataGetter aimCost=(AimCostSplitDataGetter)dataMap.get("aimCost");
		//ȡ�ò�Ʒ�Ľ��map
		Map mapAimCost=new HashMap();
		isAimFullAppt=false;
		if(aimCost!=null){
			mapAimCost=aimCost.getProductMap(acctId);
			//�Ƿ���ȫ��֣���ȫ�˾����гɱ���
			isAimFullAppt=aimCost.isFullApportion(acctId);
		}
		
		ProjProductEntriesCollection collObj = (ProjProductEntriesCollection)dataMap.get("ProjProductEntriesCollection");
		if(collObj==null){
			collObj=new ProjProductEntriesCollection();
		}
		apptObjIds=new String[collObj.size()];
			
		CurProjProductEntriesInfo prod=null;
		FDCSplitBillEntryInfo entry=null;
		
		String prodTypeId=null;
		
		idx=0;
		Map prodAppts=(Map)dataMap.get("projProdAppt");
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
			
			
			//���ݣ���Ŀ�еĲ�Ʒ��̯���ݣ�
//			CurProjProEntrApporDataCollection appts=prod.getCurProjProEntrApporData();			
			CurProjProEntrApporDataCollection appts=(CurProjProEntrApporDataCollection)prodAppts.get(prod.getProductType().getId().toString());
			if(appts==null){
				appts=new CurProjProEntrApporDataCollection();
			}
			CurProjProEntrApporDataInfo apptData=null;			
			
			//����ǰ������Ŀ�Ŀ�����Ʒ�����У�û������Ŀ��ɱ������ݵ������	jelon 12/20/2006
			if(isExistAimCost){
				boolean isFound=false;
				for (Iterator iter2 = appts.iterator(); iter2.hasNext();)
				{
					apptData=(CurProjProEntrApporDataInfo)iter2.next();
					if(apptData.getApportionType().getId().toString().equals(ApportionTypeInfo.aimCostType)){
						isFound=true;
						break;
					}
				}
				if(!isFound){
					apptData=new CurProjProEntrApporDataInfo();
					appt=new ApportionTypeInfo();
					appt.setId(BOSUuid.read(ApportionTypeInfo.aimCostType));
					apptData.setApportionType(appt);
					apptData.setValue(FDCHelper.ZERO);
					appts.add(apptData);
				}				
			}
			
			for (Iterator iter2 = appts.iterator(); iter2.hasNext();)
			{
				apptData=(CurProjProEntrApporDataInfo)iter2.next();
				
				//�к�
				idxCol=0;
				for(int i=0; i<apptTypeIds.length; i++){
					if(apptData.getApportionType().getId().toString().equals(apptTypeIds[i])){
						idxCol = colBase+i;
						
						//Ŀ��ɱ��ȴ���
						if(apptData.getApportionType().getId().toString().equals(ApportionTypeInfo.aimCostType)){
							amount=FDCHelper.ZERO;
							
							if (mapAimCost.containsKey(prodTypeId)){
								amount = (BigDecimal) mapAimCost.get(prodTypeId);							
								if(amount==null){
									amount=FDCHelper.ZERO;
								}
							}
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
			

			//entry=new FDCSplitBillEntryInfo();
			entry=createNewEntryData();
			
			
			//entry.setCostAccount(costAcct);
			entry.setProduct(prod.getProductType());
						
			entry.setLevel(topLevel+1);
			entry.setIsLeaf(true);
			
			row.setUserObject(entry);
							
		}			
		
		projProductEntriesCollection=collObj;
		
		

		
		//Ŀ��ɱ��ȣ�Ŀ��ɱ�δ��ȫ��֣�����ѡ��Ŀ��ɱ���	jeon 12/19/06
		if(!isAimFullAppt){
			idxCol=0;
			for(int i=0; i<apptTypeIds.length; i++){
				if(apptTypeIds[i].equals(ApportionTypeInfo.aimCostType)){
					idxCol = colBase+i;
					
					kdtEntrys.getColumn(idxCol).getStyleAttributes().setBackground(new Color(0xE8E8E3));
					break;
				}
			}			
		}
		

		
		super.initTable();
		
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#getData()
	 */
	public FDCSplitBillEntryCollection getData() throws Exception {
		// TODO �Զ����ɷ������
		//return super.getData();
		
		FDCSplitBillEntryCollection entrys = new FDCSplitBillEntryCollection();		
		FDCSplitBillEntryInfo topEntry=costSplit.get(0);;
		
		String id;
		IRow row;
		
		BigDecimal value=FDCHelper.ZERO;
		boolean isTrue=false;
		

		int apptColIdx=0;		
		
		
		//�����
		//entry=(FDCSplitBillEntryInfo)row.getUserObject();
		
		CostAccountInfo costAcct=topEntry.getCostAccount();
		
		//��̯����	
		ApportionTypeInfo apptType=null;
    	for(int i=colBase; i<kdtEntrys.getColumnCount(); i++){
    		if(kdtEntrys.getRow(0).getCell(i).getValue().equals(Boolean.TRUE)){    			
    			apptColIdx=i;			
    			break;
    		}
    	}		
    	topEntry.setApportionType(null);//ֱ�ӷ��ò��ָ���Ϊ�� by sxhong 2008-02-46 16:40:49
		for (Iterator iter = apportionTypeCollection.iterator(); iter.hasNext()&&apptColIdx>=colBase;)
		{
			apptType = (ApportionTypeInfo) iter.next();    				
			if(apptType.getId().toString().equals(apptTypeIds[(apptColIdx-colBase)])){
				topEntry.setApportionType(apptType);
				break;
			}											
		}		
		
		entrys.add(topEntry);
		
		
		boolean isSelected;
		boolean isApportion=(apptColIdx>=colBase);
		
		
		//��ֶ���
		for(int i=rowBase; i<kdtEntrys.getRowCount(); i++){
			row=kdtEntrys.getRow(i);
			
    		//if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
			isSelected=false;		
//			isApportion=true;						
			if(row.getCell("isSelected").getValue().equals(Boolean.TRUE)){
				isSelected=true;	
			}else{
				/*
				 * ֱ�ӽ��ҲҪ�����ѡ��,���򲻿��� by sxhong 2007/10/26
				//ֱ�ӽ�����
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
				

				//���ͬ����Ŀ�Ƿ���ѡ��		Jelon	Dec 13, 2006
				if(isSelected){
					isSelected=false;
					
					for(int j=i+1; j<kdtEntrys.getRowCount(); j++){
						FDCSplitBillEntryInfo entry2=(FDCSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
						if(kdtEntrys.getRow(j).getCell("isSelected").getValue().equals(Boolean.TRUE)){
							isSelected=true;					
							break;
						}										
					}

					if(!isSelected){
						for(int j=i-1; j>=rowBase; j--){
							FDCSplitBillEntryInfo entry2=(FDCSplitBillEntryInfo)kdtEntrys.getRow(j).getUserObject();
							if(kdtEntrys.getRow(j).getCell("isSelected").getValue().equals(Boolean.TRUE)){
								isSelected=true;				
								break;
							}												
						}						
					}
				}
				
				
			*/
				
			}
			
			
			if(isSelected){
				FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)row.getUserObject();
    			    			
    			entry.setCostAccount(costAcct);
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
				//���õ���
				entry.setPrice(topEntry.getPrice());
				entrys.add(entry);
    		}
		}
		//getApportionValue(entrys,0);

		disposeUIWindow();
		setIdxApportionID(entrys);
		return entrys;
		
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#loadData()
	 */
	protected void loadData() throws Exception {
		// TODO �Զ����ɷ������
		super.loadData();
		

		colBase=4;
		
		btnRemove.setVisible(false);
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.basedata.client.CostSplitApptUI#loadPreSplit()
	 */
	protected void loadPreSplit() {
		// TODO �Զ����ɷ������
		super.loadPreSplit();
		

		//-----------------------------------------------------------------
		//��̯��������ǰ������
		if(costSplit.get(0).getApportionType()!=null){
			String id;
			
			//ѡ���̯����
	    	id=costSplit.get(0).getApportionType().getId().toString();		

			if(id.equals(ApportionTypeInfo.aimCostType)
					&& !isAimFullAppt){
				//Ŀ��ɱ��ȣ�Ŀ��ɱ�δ��ȫ��֣�����ѡ��Ŀ��ɱ���	jeon 12/20/06
			}else{
				for(int i=0; i<apptTypeIds.length; i++){
					if(apptTypeIds[i].equals(id)){
						kdtEntrys.getCell(idxRowApptSel,i+colBase).setValue(Boolean.TRUE);
		    			break;
		    		}
				}				
			}
		}
		
		//ѡ���̯����
    	for(int i=1; i<costSplit.size(); i++){
    		String id=costSplit.get(i).getProduct().getId().toString();		
    		for(int j=0; j<apptObjIds.length; j++){
    			if(apptObjIds[j].equals(id)){
    				
    				if(costSplit.get(i).isIsApportion()){
    					kdtEntrys.getCell(j+rowBase,"isSelected").setValue(Boolean.TRUE);
    				}
    				
    				if(costSplit.get(i).getDirectAmount()!=null){
    					kdtEntrys.getCell(j+rowBase,"directAmount").setValue(costSplit.get(i).getDirectAmount());
    					kdtEntrys.getCell(j+rowBase,"isSelected").setValue(Boolean.TRUE);
    				}
    				
        			break;
        		}
    		}    		
    	}
	}
	
	private Map dataMap=new HashMap();
	private void fetchData(FDCSplitBillEntryInfo topEntry) throws BOSException{
		//������Ŀ
		final CostAccountInfo costAccount = topEntry.getCostAccount();
		Map map=new HashMap();
		map.put("costAccount", costAccount);
    	String className=this.getClass().getName();
    	String sql = "delete t_fw_usercustomconfig where fuiclassname='"+className+"'";
    	map.put("sql", sql);
		this.dataMap=FDCCommonServerHelper.getCostSplitApptProdData(map);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		Object obj=getUIContext().get("entryClass");
		if (obj != null
				&& (obj
						.equals("com.kingdee.eas.fdc.contract.ConChangeSplitEntryInfo") || obj
						.equals("com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo"))) {
			this.kdtEntrys.getColumn("directAmount").setEditor(
					FDCSplitClientHelper._getCellNumberEdit());
		}
	}
}