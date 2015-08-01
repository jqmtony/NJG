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
 * ������ָ��˵��
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
	 * ��������ʼ���̶���񲿷�
	 * 
	 * @param tblMain
	 * @throws Exception 
	 */
	private void initFixPart(KDTable tblMain) throws Exception {
		int i;
//		IRow headRow = tblMain.getHeadRow(0);
//		headRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
//		headRow.getStyleAttributes().setBold(true);
//		headRow.getCell(COL_INDEX1).setValue("��Ŀ�滮ָ���ܱ�");
//		headRow.getCell(COL_MENO).setValue("ָ��˵�� ");
//
//		KDTMergeManager hm = tblMain.getHeadMergeManager();
//		hm.mergeBlock(0,0,0,2);
//
//		hm.mergeBlock(0,4,0,5);
		
		
		tblMain.addRows(30);


		//�󶨵�Ԫ��
		bindCell(tblMain, 0, 1,"����ָ��",true);
		KDTMergeManager mm = tblMain.getMergeManager();
		mm.mergeBlock(0, 1, 29, 1);
		
		i = 0;
		int colIndex = tblMain.getColumnIndex(COL_INDEX2);
		bindCell(tblMain, i ++, colIndex,IndexConstant.���õ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ܽ������,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.���ݽ������,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ݻ���,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ܿղ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.���������,true);
		bindCell(tblMain, i, colIndex,IndexConstant.����,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.�˷�������,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.��Ӫ�Է��˷�������,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.�������˷�������_����,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.�������˷�������_������,true);
		
		bindCell(tblMain, i, colIndex,IndexConstant.����,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.ȫ������,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.�������,true);
		
		bindCell(tblMain, i ++, colIndex,IndexConstant.���������,true);
		bindCell(tblMain, i, colIndex,IndexConstant.����,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.���Ͽ��������,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.���¿��������,true);
		
		bindCell(tblMain, i ++, colIndex,IndexConstant.���������ռ�ܽ����������,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.����������,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�����̵����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�̻���,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ܻ���,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��ͣ��λ,true);
		
		bindCell(tblMain, i, colIndex,IndexConstant.����,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.����ͣ��λ,true);
		bindCell(tblMain, i ++, colIndex + 1,IndexConstant.����ͣ��λ,true);
		
		bindCell(tblMain, i ++, colIndex,IndexConstant.��λ��_�ܳ�λ_����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.����ͣ��λ,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.������ͣ��λ,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��������ͣ��λ,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��λ���۱�,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.���³�λ���,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.���³�λƽ�����,true);
		

	

		
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
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���õ����,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�ܽ������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���ݽ������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�ݻ���,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�ܿղ����,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�˷�������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.��Ӫ�Է��˷�������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�������˷�������_����,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�������˷�������_������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.ȫ������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���Ͽ��������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���¿��������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���������ռ�ܽ����������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.����������,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�����̵����,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�̻���,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.�ܻ���,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.��ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.����ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.����ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.��λ��_�ܳ�λ_����,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.����ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.������ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.��������ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.��λ���۱�,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���³�λ���,false);
		setCellValue(tblMain, i ++, colIndex,valueMap,IndexConstant.���³�λƽ�����,false);
		
		i = 0;
		colIndex = tblMain.getColumnIndex(COL_ID);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���õ����,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�ܽ������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���ݽ������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�ݻ���,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�ܿղ����,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�˷�������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.��Ӫ�Է��˷�������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�������˷�������_����,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�������˷�������_������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.ȫ������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���Ͽ��������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���¿��������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���������ռ�ܽ����������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.����������,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�����̵����,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�̻���,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.�ܻ���,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.��ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.����ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.����ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.��λ��_�ܳ�λ_����,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.����ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.������ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.��������ͣ��λ,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.��λ���۱�,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���³�λ���,false);
		setCellValue(tblMain, i ++, colIndex,idMap,IndexConstant.���³�λƽ�����,false);
		
		//�ں�
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
		
		//��λ
		i = 0;
		colIndex = tblMain.getColumnIndex(COL_UNIT);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ٷֱ�,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ٷֱ�,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ٷֱ�,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,"",true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.��,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.�ٷֱ�,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		bindCell(tblMain, i ++, colIndex,IndexConstant.ƽ����,true);
		
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
		getCellValue(tblMain, i ++,coll,IndexConstant.���õ����);
		getCellValue(tblMain, i ++,coll,IndexConstant.�ܽ������);
		getCellValue(tblMain, i ++,coll,IndexConstant.���ݽ������);
		getCellValue(tblMain, i ++,coll,IndexConstant.�ݻ���);
		getCellValue(tblMain, i ++,coll,IndexConstant.�ܿղ����);
		getCellValue(tblMain, i ++,coll,IndexConstant.���������);
		getCellValue(tblMain, i ++,coll,IndexConstant.�˷�������);
		getCellValue(tblMain, i ++,coll,IndexConstant.��Ӫ�Է��˷�������);
		getCellValue(tblMain, i ++,coll,IndexConstant.�������˷�������_����);
		getCellValue(tblMain, i ++,coll,IndexConstant.�������˷�������_������);
		getCellValue(tblMain, i ++,coll,IndexConstant.ȫ������);
		getCellValue(tblMain, i ++,coll,IndexConstant.�������);
		getCellValue(tblMain, i ++,coll,IndexConstant.���������);
		getCellValue(tblMain, i ++,coll,IndexConstant.���Ͽ��������);
		getCellValue(tblMain, i ++,coll,IndexConstant.���¿��������);
		getCellValue(tblMain, i ++,coll,IndexConstant.���������ռ�ܽ����������);
		getCellValue(tblMain, i ++,coll,IndexConstant.����������);
		getCellValue(tblMain, i ++,coll,IndexConstant.�����̵����);
		getCellValue(tblMain, i ++,coll,IndexConstant.�̻���);
		getCellValue(tblMain, i ++,coll,IndexConstant.�ܻ���);
		getCellValue(tblMain, i ++,coll,IndexConstant.��ͣ��λ);
		getCellValue(tblMain, i ++,coll,IndexConstant.����ͣ��λ);
		getCellValue(tblMain, i ++,coll,IndexConstant.����ͣ��λ);
		getCellValue(tblMain, i ++,coll,IndexConstant.��λ��_�ܳ�λ_����);
		getCellValue(tblMain, i ++,coll,IndexConstant.����ͣ��λ);
		getCellValue(tblMain, i ++,coll,IndexConstant.������ͣ��λ);
		getCellValue(tblMain, i ++,coll,IndexConstant.��������ͣ��λ);
		getCellValue(tblMain, i ++,coll,IndexConstant.��λ���۱�);
		getCellValue(tblMain, i ++,coll,IndexConstant.���³�λ���);
		getCellValue(tblMain, i ++,coll,IndexConstant.���³�λƽ�����);
		
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
		setMessageText("����ɹ���");
		showMessage();
	}
	
	

}