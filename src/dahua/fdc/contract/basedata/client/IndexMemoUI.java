/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.IndexMemoCollection;
import com.kingdee.eas.fdc.basedata.IndexMemoFactory;
import com.kingdee.eas.fdc.basedata.IndexMemoInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.client.EASResource;

/**
 * 描述：指标说明
 * 
 * @author weiqiang_chen
 */
public class IndexMemoUI extends AbstractIndexMemoUI {
	
	private static final Logger logger = CoreUIObject.getLogger(IndexMemoUI.class);
	
	protected static String COL_ID = "id";

	protected static String COL_INDEX1 = "index1";
	
	protected static String COL_INDEX2 = "index2";
	
	protected static String COL_INDEX3 = "index3";
	
	protected static String COL_UNIT = "unit";
	
	protected static String COL_MENO = "memo";
	
	public IndexMemoUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		initTable();
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		
		actionSave.putValue(Action.SMALL_ICON, EASResource
				.getIcon("imgTbtn_save"));
		actionSave.setEnabled(true);
	}
	
	private void initTable() throws Exception {
		tblMain.checkParsed();
		initFixPart(tblMain);
	}
	
	/**
	 * 描述：初始化固定表格部分
	 * 
	 * @param tblMain
	 * @throws Exception 
	 */
	private void initFixPart(KDTable tblMain) throws Exception {
		int i;
//		IRow headRow = tblMain.getHeadRow(0);
//		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
//		headRow.getStyleAttributes().setBold(true);
//		headRow.getCell(COL_INDEX1).setValue("项目规划指标总表");
//		headRow.getCell(COL_MENO).setValue("指标说明 ");
//
//		KDTMergeManager hm = tblMain.getHeadMergeManager();
//		hm.mergeBlock(0,0,0,2);
//
//		hm.mergeBlock(0,4,0,5);
		
		
		tblMain.addRows(30);


		//绑定单元格
		bindCell(tblMain, 0, 1,"总体指标",true);
		KDTMergeManager mm = tblMain.getMergeManager();
		mm.mergeBlock(0, 1, 29, 1);
		
		i = 0;
		int colIndex = tblMain.getColumnIndex(COL_INDEX2);
		bindCell(tblMain, i ++, colIndex,IndexConstant.总用地面积,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.总建筑面积,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.计容建筑面积,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.容积率,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.架空层面积,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.地下室面积,true);
		bindCell(tblMain, i, colIndex,IndexConstant.其中,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.人防地下室,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.经营性非人防地下室,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.其他非人防地下室_赠送,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.其他非人防地下室_非赠送,true);
		
		bindCell(tblMain, i, colIndex,IndexConstant.其中,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.全地下室,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.半地下室,true);
		
		bindCell(tblMain, i ++, colIndex,IndexConstant.可租售面积,true);
		bindCell(tblMain, i, colIndex,IndexConstant.其中,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.地上可租售面积,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.地下可租售面积,true);
		
		bindCell(tblMain, i ++, colIndex,IndexConstant.可租售面积占总建筑面积比例,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.建筑覆盖率,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.公共绿地面积,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.绿化率,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.总户数,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.总停车位,true);
		
		bindCell(tblMain, i, colIndex,IndexConstant.其中,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.地上停车位,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.地下停车位,true);
		
		bindCell(tblMain, i ++, colIndex,IndexConstant.车位比_总车位_户数,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.可售停车位,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.不可售停车位,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.安排销售停车位,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.车位销售比,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.地下车位面积,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.地下车位平均面积,true);
		

	

		
		IndexMemoCollection col = IndexMemoFactory.getRemoteInstance().getIndexMemoCollection();
		Map valueMap = new HashMap();
		Map idMap = new HashMap();
		for(int j = 0;col != null && j < col.size();j ++){
			IndexMemoInfo info = col.get(j);
			valueMap.put(info.getKey(), info.getMemo());
			idMap.put(info.getKey(),info.getId().toString());
		}
		
		i = 0;
		colIndex = tblMain.getColumnIndex(COL_MENO);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.总用地面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.总建筑面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.计容建筑面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.容积率,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.架空层面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地下室面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.人防地下室,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.经营性非人防地下室,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.其他非人防地下室_赠送,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.其他非人防地下室_非赠送,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.全地下室,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.半地下室,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.可租售面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地上可租售面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地下可租售面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.可租售面积占总建筑面积比例,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.建筑覆盖率,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.公共绿地面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.绿化率,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.总户数,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.总停车位,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地上停车位,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地下停车位,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.车位比_总车位_户数,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.可售停车位,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.不可售停车位,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.安排销售停车位,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.车位销售比,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地下车位面积,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.地下车位平均面积,false);
		
		i = 0;
		colIndex = tblMain.getColumnIndex(COL_ID);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.总用地面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.总建筑面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.计容建筑面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.容积率,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.架空层面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地下室面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.人防地下室,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.经营性非人防地下室,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.其他非人防地下室_赠送,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.其他非人防地下室_非赠送,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.全地下室,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.半地下室,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.可租售面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地上可租售面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地下可租售面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.可租售面积占总建筑面积比例,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.建筑覆盖率,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.公共绿地面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.绿化率,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.总户数,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.总停车位,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地上停车位,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地下停车位,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.车位比_总车位_户数,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.可售停车位,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.不可售停车位,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.安排销售停车位,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.车位销售比,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地下车位面积,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.地下车位平均面积,false);
		
		//融合
		i = 0;
		int rowIndex = tblMain.getColumnIndex(COL_INDEX2);
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i + 3, rowIndex); i +=4;
		mm.mergeBlock(i, rowIndex, i + 1, rowIndex); i +=2;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i + 1, rowIndex); i +=2;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i + 1, rowIndex); i +=2;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		mm.mergeBlock(i, rowIndex, i, rowIndex + 1); i ++;
		
		//单位
		i = 0;
		colIndex = tblMain.getColumnIndex(COL_UNIT);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.百分比,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.百分比,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.百分比,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.户,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.个,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.个,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.个,true);
		bindCell(tblMain, i ++, colIndex,"",true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.个,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.个,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.个,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.百分比,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.平方米,true);
		
	}
	
	private void bindCell(KDTable tblMain, int rowIndex, int colIndex, String value, boolean locked) {
		ICell cell = tblMain.getCell(rowIndex, colIndex);
		if(cell != null){
			cell.setValue(value);
			StyleAttributes sa = cell.getStyleAttributes();
			sa.setLocked(locked);
			sa.setHorizontalAlign(HorizontalAlignment.CENTER);
			sa.setBackground(new Color(0xF0EDD9));
		}
		
	}
	
	private void setCellValue(KDTable tblMain, int rowIndex, int colIndex, Map map,
			String key, boolean locked) {
		ICell cell = tblMain.getCell(rowIndex, colIndex);
		if(cell != null){
			cell.setValue(map.get(key));
			StyleAttributes sa = cell.getStyleAttributes();
			sa.setLocked(locked);
			if(locked){
				sa.setBackground(new Color(0xF0EDD9));
			}
			sa.setHorizontalAlign(HorizontalAlignment.CENTER);
			cell.setEditor(getEditor());
		}
	}
	

	KDTDefaultCellEditor editor = null;
	
	public KDTDefaultCellEditor getEditor() {
		if(editor == null){
			KDTextField txt = new KDTextField();
			txt.setMaxLength(200);
			editor = new KDTDefaultCellEditor(txt);
		}
		return editor;
	}

	private CoreBaseCollection storeTableFields() {
		
		int i = 0;
		CoreBaseCollection coll = new CoreBaseCollection();
		getCellValue(tblMain, i ++,coll,IndexConstant.总用地面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.总建筑面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.计容建筑面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.容积率);
		getCellValue(tblMain, i ++,coll,IndexConstant.架空层面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.地下室面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.人防地下室);
		getCellValue(tblMain, i ++,coll,IndexConstant.经营性非人防地下室);
		getCellValue(tblMain, i ++,coll,IndexConstant.其他非人防地下室_赠送);
		getCellValue(tblMain, i ++,coll,IndexConstant.其他非人防地下室_非赠送);
		getCellValue(tblMain, i ++,coll,IndexConstant.全地下室);
		getCellValue(tblMain, i ++,coll,IndexConstant.半地下室);
		getCellValue(tblMain, i ++,coll,IndexConstant.可租售面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.地上可租售面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.地下可租售面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.可租售面积占总建筑面积比例);
		getCellValue(tblMain, i ++,coll,IndexConstant.建筑覆盖率);
		getCellValue(tblMain, i ++,coll,IndexConstant.公共绿地面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.绿化率);
		getCellValue(tblMain, i ++,coll,IndexConstant.总户数);
		getCellValue(tblMain, i ++,coll,IndexConstant.总停车位);
		getCellValue(tblMain, i ++,coll,IndexConstant.地上停车位);
		getCellValue(tblMain, i ++,coll,IndexConstant.地下停车位);
		getCellValue(tblMain, i ++,coll,IndexConstant.车位比_总车位_户数);
		getCellValue(tblMain, i ++,coll,IndexConstant.可售停车位);
		getCellValue(tblMain, i ++,coll,IndexConstant.不可售停车位);
		getCellValue(tblMain, i ++,coll,IndexConstant.安排销售停车位);
		getCellValue(tblMain, i ++,coll,IndexConstant.车位销售比);
		getCellValue(tblMain, i ++,coll,IndexConstant.地下车位面积);
		getCellValue(tblMain, i ++,coll,IndexConstant.地下车位平均面积);
		
		return coll;
	}

	private void getCellValue(KDTable tblMain, int i, CoreBaseCollection coll, String key) {
		Object value = tblMain.getCell(i, tblMain.getColumnIndex(COL_ID)).getValue();
		
		IndexMemoInfo info = new IndexMemoInfo();
		info.setKey(key);
		if(value != null && value.toString().length() > 0){
			info.setId(BOSUuid.read(value.toString()));
		}
		
		
		value = tblMain.getCell(i, tblMain.getColumnIndex(COL_MENO)).getValue();
		
		info.setMemo(value == null?null:value.toString());
		
		coll.add(info);
	}
	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		
		CoreBaseCollection col = storeTableFields();
		IndexMemoFactory.getRemoteInstance().saveBatchData(col);
		setMessageText("保存成功！");
		showMessage();
	}
	
	

}