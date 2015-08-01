package com.kingdee.eas.fdc.contract.programming.client;

import java.util.ArrayList;

import com.kingdee.bos.appframework.databinding.DataBinder;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo;

public class CreateTableRow {
	
	protected DataBinder dataBinder = null;
	
	public CreateTableRow(DataBinder dataBinder){
		this.dataBinder = dataBinder;
	}
	
	/**
     * 在指定表格中新增行（新增到最后一行）
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
     * 在指定表格中插入行（在当前选中行前插入，如果当前未选中任何行的话，则新增到最后一行）
     *
     * @param table
     */
    public void insertLine(KDTable table , int rowIndex , int level , ProgrammingTemplateEntireInfo head) throws Exception
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
     * 在指定表格中删除行
     *
     * @param table
     */
    public void removeLine(KDTable table , ArrayList list) throws Exception
    {
    	if(list == null || list.size() == 0)
    		return;
    	int top = 0;
    	IObjectCollection collection = new ProgrammingTemplateEntireCollection();
		for (int i = 0; i < table.getRowCount(); i++) {
			collection.addObject((IObjectValue) table.getRow(i).getUserObject());
		}
    	for(int i = list.size()-1 ; i >= 0  ; i--){
    		top = ((Integer)list.get(i)).intValue();
    		IObjectValue detailData = (IObjectValue) table.getRow(top).getUserObject();
            table.removeRow(top);
			if (collection != null){
				if (detailData != null) {
					collection.removeObject(top);
				}
			}
    	}
    }
    
    /**
     * 在指定表格中删除指定的行
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
     * 新建单据行，返回一个新的分录行的默认值
     */
    protected IObjectValue createNewDetailData(KDTable table , int level , ProgrammingTemplateEntireInfo head)
    {
        if(table == null)
        {
            return null;
        }
        ProgrammingTemplateEntireInfo newDetailInfo = new ProgrammingTemplateEntireInfo();
        newDetailInfo.setId(BOSUuid.create("B9EEC6B4"));
        newDetailInfo.setLevel(level);
        if(head != null)
        	newDetailInfo.setHead(head);
        return (IObjectValue) newDetailInfo;
    }
    
    /**
     * 显示单据行
     */
    protected void loadLineFields(KDTable table, IRow row, IObjectValue obj)
    {
        dataBinder.loadLineFields(table, row, obj);
    }
}
