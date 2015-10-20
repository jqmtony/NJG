package com.kingdee.eas.fdc.contract.programming.client;

import java.util.ArrayList;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;

public class CreateProTableRow {
	
	protected DataBinder dataBinder = null;
	
	public CreateProTableRow(DataBinder dataBinder){
		this.dataBinder = dataBinder;
	}
	
	/**
     * ��ָ������������У����������һ�У�
     *
     * @param table
     */
    public void addLine(KDTable table , int level) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table , level , null);
        IRow row = table.addRow();
        loadLineFields(table, row, detailData);
    }
    
    /**
     * ��ָ������в����У��ڵ�ǰѡ����ǰ���룬�����ǰδѡ���κ��еĻ��������������һ�У�
     *
     * @param table
     */
    public void insertLine(KDTable table , int rowIndex , int level , ProgrammingContractInfo head) throws Exception
    {
        if(table == null)
        {
            return;
        }
        IObjectValue detailData = createNewDetailData(table , level , head);
        IRow row = table.addRow(rowIndex);
        loadLineFields(table, row, detailData);
    }
    
    /**
     * ��ָ�������ɾ����
     *
     * @param table
     */
    public void removeLine(KDTable table , ArrayList list) throws Exception
    {
    	if(list == null || list.size() == 0)
    		return;
    	int top = 0;
    	for(int i = list.size()-1 ; i >= 0  ; i--){
    		top = ((Integer)list.get(i)).intValue();
    		IObjectValue detailData = (IObjectValue) table.getRow(top).getUserObject();
            table.removeRow(top);
            IObjectCollection collection = (IObjectCollection) table
                    .getUserObject();
			if (collection != null){
				if (detailData != null) {
					collection.removeObject(top);
				}
			}
    	}
    }
    
    /**
     * ��ָ�������ɾ��ָ������
     * @param table
     * @param rowIndex
     * @throws Exception
     */
    public void removeLine(KDTable table , int rowIndex) throws Exception
    {
//		IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
		table.removeRow(rowIndex);
//		IObjectCollection collection = (IObjectCollection) table.getUserObject();
//		if (collection != null) {
//			if (detailData != null) {
//				collection.removeObject(rowIndex);
//			}
//		}
    }
    
    /**
     * �½������У�����һ���µķ�¼�е�Ĭ��ֵ
     */
    protected IObjectValue createNewDetailData(KDTable table , int level , ProgrammingContractInfo head)
    {
        if(table == null)
        {
            return null;
        }
        ProgrammingContractInfo newDetailInfo = new ProgrammingContractInfo();
        newDetailInfo.setId(BOSUuid.create("ECE079DB"));
        newDetailInfo.setLevel(level);
        if(head != null)
        	newDetailInfo.setParent(head);
        newDetailInfo.setBalance(FDCHelper.ZERO);
        newDetailInfo.setControlBalance(FDCHelper.ZERO);
        newDetailInfo.setAmount(FDCHelper.ZERO);
        newDetailInfo.setControlAmount(FDCHelper.ZERO);
        newDetailInfo.setSignUpAmount(FDCHelper.ZERO);
        newDetailInfo.setChangeAmount(FDCHelper.ZERO);
        newDetailInfo.setSettleAmount(FDCHelper.ZERO);
        newDetailInfo.setReservedChangeRate(FDCHelper.ZERO);
        
        return (IObjectValue) newDetailInfo;
    }
    
    /**
     * ��ʾ������
     */
    protected void loadLineFields(KDTable table, IRow row, IObjectValue obj)
    {
        dataBinder.loadLineFields(table, row, obj);
    }
}
