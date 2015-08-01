package com.kingdee.eas.fdc.basedata;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;

public class CellBinder {
	private HashMap bindCellMap=null;
	public CellBinder(){
		this.bindCellMap=new HashMap();
	}
	
	public HashMap getBindCellMap(){
		return bindCellMap;
	}
	public ICell getCell(String key){
		return key==null?null:(ICell)getBindCellMap().get(key);
	}
	public Object getCellValue(String key){
		return getCell(key)==null?null:getCell(key).getValue();
	}
	/**
	 * Description:
	 * 		绑定单元格及要存储的值,其值所在的单元格为名称所对应的单元格的右边一个格
	 *
	 * @author sxhong  		Date 2006-9-4
	 * @param table
	 * @param rowIndex
	 * @param colIndex		
	 * @param name			要显示的名称
	 * @param key 		值,可以用对象及其属性,如auditor.name
	 * @param bindCellMap
	 */
	public void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		/*
		 * 值表格
		 */
		cell=table.getCell(rowIndex, colIndex+1);
//		cell.getStyleAttributes().setBackground(Color.LIGHT_GRAY);
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	/**
	 * Description:
	 *		绑定对象到单元格
	 * @author sxhong  		Date 2006-9-4
	 * @param cell
	 * @param valueName
	 * @param valueProperty
	 * @param bindCellMap
	 */
	public void bindCell(ICell cell,String key){
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	
	/**
	 * 绑定单元格到指定对象，并为单元设置一个表示数值的Editor，Key为对象名，该单元格
	 * 前面有一个描述信息name
	 * @author sxhong  		Date 2006-10-26
	 * @param table
	 * @param rowIndex
	 * @param colIndex
	 * @param name
	 * @param key
	 * @param bindCellMap
	 * @param numberEditor
	 */
	public void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,boolean numberEditor){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		StyleAttributes sa = cell.getStyleAttributes();
		sa.setLocked(true);
		sa.setHorizontalAlign(HorizontalAlignment.LEFT);
		sa.setBackground(new Color(0xF0EDD9));
		/*
		 * 值表格
		 */
		cell=table.getCell(rowIndex, colIndex+1);
		bindCell(cell, key, numberEditor);
	}
	
	public void bindCell(KDTable table,int rowIndex,int colIndex,String name,String key,boolean isDown,boolean numberEditor){
		ICell cell=table.getCell(rowIndex, colIndex);
		cell.setValue(name);
		StyleAttributes sa = cell.getStyleAttributes();
		sa.setLocked(true);
		sa.setHorizontalAlign(HorizontalAlignment.LEFT);
		sa.setBackground(new Color(0xF0EDD9));
		/*
		 * 值表格
		 */
		if(isDown){
			cell=table.getCell(rowIndex+1, colIndex);
		}else{
			cell=table.getCell(rowIndex, colIndex+1);
		}
		bindCell(cell, key, numberEditor);
	}
	
	/**
	 * 绑定单元格到指定对象，并为单元设置一个表示数值的Editor，Key为对象名
	 * @author sxhong  		Date 2006-10-26
	 * @param cell
	 * @param key
	 * @param bindCellMap
	 * @param numberEditor
	 */
	public void bindCell(ICell cell,String key,boolean numberEditor){
		if(numberEditor){
			cell.setEditor(getCellNumberEdit());
			cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			cell.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
		}
		//绑定对应关系
		bindCellMap.put(key, cell);
	}
	
	
	/**
	 * 从单元格取值到值对象 支持关联属性
	 * @author sxhong  		Date 2006-10-26
	 * @param info
	 * @param map
	 * @return
	 */
	public boolean setObjectValue(IObjectValue info){
		if(info==null||bindCellMap==null) return false;
		Set keys = bindCellMap.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			Object cell=bindCellMap.get(key);
			if(cell instanceof ICell){
				/*	
				 * 因IObjectValue的方法内屏蔽了值为null的key故不能通过key来判断
				 *  info是否有该属性
				 */
				int index = key.indexOf('.');
				if(index>0) {
					//ObjectValue
					String infoKey=key.substring(0,index);
					String propKey=key.substring(index+1);
					Object subinfo=info.get(infoKey);
					if (subinfo instanceof IObjectValue)
					{
						((IObjectValue) subinfo).put(propKey, ((ICell)cell).getValue());
					}				
				}else{
					//非ObjectValue
					info.put(key,((ICell)cell).getValue());
				}
			}
		}
		
		return true;
	}
	/**
	 * 将值对象的属性值设置到Cell中展示，支持值对象的关联属性。
	 * @author sxhong  		Date 2006-10-26
	 * @param info
	 * @param bindCellMap
	 * @return
	 * @throws Exception
	 */
	public boolean setCellsValue(IObjectValue info){
		if(info==null||bindCellMap==null) return false;
		Set keys = bindCellMap.keySet();
		String key=null;
		Iterator iter=keys.iterator();
		while(iter.hasNext()){
			key=(String)iter.next();
			Object cell=bindCellMap.get(key);
			int index = key.indexOf('.');
			Object value;
			if(index>0) {
				//objectValue
				String infoKey=key.substring(0,index);
				String propKey=key.substring(index+1);					
				Object subinfo=info.get(infoKey);
				if (subinfo instanceof IObjectValue)
				{
					value=((IObjectValue) subinfo).get(propKey);
					((ICell)cell).setValue(value);
				}
			}else{
				//非ObjectValue
				value=info.get(key);
				((ICell)cell).setValue(value);
			}
		}
		
		return true;
	}
	
	/**
	 * 得到一个-1.0E17－－1.0E17的BigDecimal CellEditor
	 * @author sxhong  		Date 2006-10-26
	 * @return
	 */
	public static KDTDefaultCellEditor getCellNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(2);
//        kdc.setMinimumValue(FDCHelper.MIN_VALUE);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
//        kdc.setRequired(false);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	/**
	 * 得到一个-1.0E17－－1.0E17的BigDecimal CellEditor
	 * @author sxhong  		Date 2006-10-26
	 * @return
	 */
	public static KDTDefaultCellEditor getCellIntegerNumberEdit(){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.INTEGER_TYPE);
        kdc.setPrecision(0);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
}
