package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTEditHelper;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTTransferAction;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.PreChangeEvent;
import com.kingdee.bos.ctrl.swing.event.PreChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.EconomicIndexEntryCollection;
import com.kingdee.eas.fdc.basedata.EconomicIndexEntryInfo;
import com.kingdee.eas.fdc.basedata.EconomicIndicatorsInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.PropertyRightsInfo;
import com.kingdee.eas.util.client.KDTableUtil;

public class EconomicIndexTable {
	private KDTable table=null;
	private CellBinder binder=null;
	private int dynRowBase=5;//old:4 modified by src 2012-03-06 16:33
	private EconomicIndicatorsInfo economicIndicatorsInfo=null;
	
//	public EconomicIndexTable(EconomicIndicatorsInfo economicIndicatorsInfo) throws EASBizException, BOSException{
//		table=new KDTable(16,1,0);//src old:18 new:16
//		initCtrlListener();
//		binder=new CellBinder();
//		initTable(economicIndicatorsInfo);
//		this.economicIndicatorsInfo=economicIndicatorsInfo;
//	}
//	protected void initCtrlListener(){
//		table.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
//			public void editStopped(
//					com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
//				table_editStopped(e);
//			}
//		});
//		table.addKDTMouseListener(new KDTMouseListener(){
//			public void tableClicked(KDTMouseEvent e) {
//				table_tableClicked(e);
//			}
//		});
//		table.setBeforeAction(new BeforeActionListener(){
//			public void beforeAction(BeforeActionEvent e) {
//
//				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
//					int colIndex=table.getSelectManager().getActiveColumnIndex();
//					if(colIndex==1){
//						e.setCancel(true);
//					}
//				}
//
//			
//			}
//		});
//		
//		table.setAfterAction(new BeforeActionListener(){
//			public void beforeAction(BeforeActionEvent e) {
//
//				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
//					KDTEditEvent event=new KDTEditEvent(table);
//					int activeColumnIndex = table.getSelectManager().getActiveColumnIndex();
//					int activeRowIndex =table.getSelectManager().getActiveRowIndex();
//					event.setColIndex(activeColumnIndex);
//					event.setRowIndex(activeRowIndex);
//					event.setOldValue(FDCHelper.ONE);
//					event.setValue(null);
//					table_editStopped(event);
//				}
//
//			
//			}
//		});
//	}
	public KDTable getTable(){
		return table;
	}
	
//	public void initTable(EconomicIndicatorsInfo info){
//		((KDTTransferAction) table.getActionMap().get(KDTAction.PASTE)).setPasteMode(KDTEditHelper.VALUE);
//		table.getColumn(3).setWidth(150);
//		table.getColumn(4).setWidth(150);
//		table.getColumn(5).setWidth(150);
//		table.getColumn(6).setWidth(150);
//		table.getColumn(7).setWidth(150);
//		table.getColumn(8).setWidth(150);
//		table.getColumn(10).setWidth(150);
//		table.getColumn(11).setWidth(150);
//		table.getColumn(12).setWidth(150);
//		table.getColumn(13).setWidth(150);
//		initFixTable(info);
//		initDynTable(info);
//		ICellEditor f7Editor = new KDTDefaultCellEditor(getF7productType());
//		table.getColumn(1).setEditor(f7Editor);
//		//src begin modified 2012-03-28 17:07
//		ICellEditor f7PropertyRightsEditor = new KDTDefaultCellEditor(getF7PropertyRights());
//		table.getColumn(2).setEditor(f7PropertyRightsEditor);
//		//src end
//		table.getCell(table.getRowCount()-1, 0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
//		KDTextField textField=new  KDTextField();
//		textField.setMaxLength(255);
//		table.getColumn(15).setEditor(new KDTDefaultCellEditor(textField));//src old:17 new:15
//		for(int i = 0; i<table.getColumnCount();i++){
//			//src begin modified 2012-03-06 17:46
//			if(i>7){//old:2 new:7
//				table.getCell(3, i).getStyleAttributes().setLocked(true);
//				table.getCell(3, i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//				if(i>9){
//					table.getCell(2, i).getStyleAttributes().setLocked(true);
//					table.getCell(2, i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//				}
//				if(i>13){
//					table.getCell(1, i).getStyleAttributes().setLocked(true);
//					table.getCell(1, i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//				}
//			}
//			//src end
//		}
//		for (int i=0;i<4;i++){//old:3 new:4 modified by src
//			for(int j=16;j<table.getColumnCount();j++){//old:14 new:16
//				table.getCell(i, j).getStyleAttributes().setLocked(true);
//				table.getCell(i, j).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			}
//			
//		}
//
//		reSetExpressions();
//	}
	
	private void initFixTable(EconomicIndicatorsInfo info){
		table.addRows(5);
		//�󶨵�Ԫ��		
		//src begin modified 2012-03-06 16:33
		binder.bindCell(table, 0, 1,"�õ����(m2)"	,"totaleArea",true);
		binder.bindCell(table, 0, 4,"�õ�����"	,"landProperties",false);
		binder.bindCell(table, 0, 6,"�����õ����(m2)"	,"replaceUseArea",true);
		binder.bindCell(table, 0, 8,"�����õ����(m2)"	,"buildingUseArea",true);
		binder.bindCell(table, 0, 10,"סլ�õ����(m2)"	,"houseUseArea",true);
		binder.bindCell(table, 0, 12,"�̰��õ����(m2)"	,"businessUseArea",true);
		binder.bindCell(table, 0, 14,"�����õ����(m2)"	,"otherUseArea",true);
		binder.bindCell(table, 1, 1,"�ݻ���"	,"cubageRate",true);
		binder.bindCell(table, 1, 4,"�����ܶ�"	,"buildingDimension",true);
		binder.bindCell(table, 1, 6,"�̵���"	,"greenRate",true);
		binder.bindCell(table, 1, 8,"���ݽ������(m2)(����)"	,"cubageRateArea",true);
		binder.bindCell(table, 1, 10,"�����޸�(m)"	,"buildLimitHigh",true);
		binder.bindCell(table, 1, 12,"ͣ��λ���Ҫ��"	,"carportMatch",false);
		binder.bindCell(table, 2, 1,"�������(m2)"	,"totalBuildArea",true);
		binder.bindCell(table, 2, 4,"���ݽ������(m2)(ʵ��)"	,"cubageRateAreaActual",true);
		binder.bindCell(table, 2, 6,"���Ͻ������(m2)"	,"groundFloorArea",true);
		binder.bindCell(table, 2, 8,"���½������(m2)"	,"underArea",true);
		binder.bindCell(table, 3, 1,"�������(m2)"	,"totalSaleArea",true);
		binder.bindCell(table, 3, 4,"����ܽ������(m2)"	,"surveyToalBuildArea",true);
		binder.bindCell(table, 3, 6,"���۱�"	,"canSaleRatioNew",false);
		//src end
		
		KDTMergeManager mm = table.getMergeManager();
		for(int i=0;i<4;i++){//old:3 modified by src 2012-03-06 16:50 
			mm.mergeBlock(i, 0, i, 1, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(i, 3, i, 4, KDTMergeManager.SPECIFY_MERGE);
		}
		table.getCell(4, 0).setValue("��Ʒ����");//old:3 new:4 modified by src
		table.getCell(4, 1).setValue("��Ʒ����");
		table.getCell(4, 2).setValue("��Ȩ����");
		table.getCell(4, 3).setValue("���Ͻ������(m2)");
		table.getCell(4, 4).setValue("���½������(m2)");
		table.getCell(4, 5).setValue("�������(m2)");
		table.getCell(4, 6).setValue("�������(m2)");
		table.getCell(4, 7).setValue("��Ʒ����");
		table.getCell(4, 8).setValue("ƽ��ÿ�����(m2)");
		table.getCell(4, 9).setValue("����");
		table.getCell(4, 10).setValue("����(����/����)");
		table.getCell(4, 11).setValue("���(����/����)");
		table.getCell(4, 12).setValue("����");
		table.getCell(4, 13).setValue("��Ԫ/������");
		table.getCell(4, 14).setValue("����/����");
		table.getCell(4, 15).setValue("��ע");
		StyleAttributes sa = table.getRow(4).getStyleAttributes();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		sa.setLocked(true);
		sa.setBackground(new Color(0xF0EDD9));
		//modified by src 2012-03-06 17:11
//		table.getCell(0, 2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(0, 5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(0, 9).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//		table.getCell(0, 11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		//src end
		if(info!=null)
			binder.setCellsValue(info);
	}
	
//	private void initDynTable(EconomicIndicatorsInfo info){
//		int base=dynRowBase;
//		int rows=base;
//		EconomicIndexEntryCollection economicColl = null;
//		PlanIndexTypeEnum lastType=null;
//		KDTMergeManager mm = table.getMergeManager();
//		if(info==null||info.getEntrys()==null||info.getEntrys().size()==0){
//			info=new EconomicIndicatorsInfo();
//			EconomicIndexEntryInfo entry=new EconomicIndexEntryInfo();
//			entry.setType(PlanIndexTypeEnum.house);
//			info.getEntrys().add(entry);
//			
//			entry=new EconomicIndexEntryInfo();
//			entry.setType(PlanIndexTypeEnum.business);
//			info.getEntrys().add(entry);
//			
//			entry=new EconomicIndexEntryInfo();
//			entry.setType(PlanIndexTypeEnum.publicBuild);
//			info.getEntrys().add(entry);
//			
//			entry=new EconomicIndexEntryInfo();
//			entry.setType(PlanIndexTypeEnum.parking);
//			info.getEntrys().add(entry);
//			economicColl = info.getEntrys();
//		}else{
//			//��ȡ����ָ���Ʒ�����ݼ�
//			economicColl = new EconomicIndexEntryCollection();
//			if(info.getEntrys().size()>0){
//				economicColl=info.getEntrys();
//				CRMHelper.sortCollection(economicColl, "index", true);
//		    }
//	     }
//		
//		for(int i=0;i<economicColl.size();i++){
//			EconomicIndexEntryInfo entry  = economicColl.get(i);
//			IRow row=table.addRow(rows);
//			if(entry.getType()!=lastType&&lastType!=null){
//				row.getCell(0).setValue(lastType);
//				row.getCell(1).setValue("С��");
//				loadRow(null, row);
//				
//				row=table.addRow(++rows);
//				
//				if(entry.getType()==PlanIndexTypeEnum.parking){
//					mm.mergeBlock(rows, 0, rows, 1);
//					//src begin modified 2012-03-07 14:38
//					row.getCell(2).setValue("��Ȩ����");
//					row.getCell(3).setValue("���Ͻ������(m2)");
//					row.getCell(4).setValue("���½������(m2)");
//					row.getCell(5).setValue("�������(m2)");
//					row.getCell(6).setValue("�������(m2)");
//					row.getCell(7).setValue("��λ��(��)");
//					row.getCell(8).setValue("���۳�λ��(��)");
//					row.getCell(9).setValue("��λ���۱�");
//					StyleAttributes sa = row.getStyleAttributes();
//					sa.setHorizontalAlign(HorizontalAlignment.CENTER);
//					sa.setLocked(true);
//					sa.setBackground(new Color(0xF0EDD9));
//					row=table.addRow(++rows);
//				}
//			}
//			loadRow(entry, row);
//			lastType=entry.getType();
//			rows++;
//		}
//		
//		IRow row=table.addRow(rows);
//		row.getCell(0).setValue(lastType);
//		row.getCell(1).setValue("С��");
//		loadRow(null, row);
//		
//		row=table.addRow(++rows);
//		row.getCell(0).setValue("�ϼ�");
//		row.getStyleAttributes().setHided(true);
//		loadRow(null, row);
//	
//		mm.mergeBlock(base, 0, rows-1, 0, KDTMergeManager.FREE_ROW_MERGE);
//		mm.mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), 1);
//
//	}
	
//	private void loadRow(EconomicIndexEntryInfo entry,IRow row){
//		StyleAttributes sa = row.getStyleAttributes();
//		if(isSubTotalRow(row)){
//			sa.getNumberFormat();
//			sa.setNumberFormat("#,##0.00;-#,##0.00");
//			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
//			sa.setLocked(true);
//			sa.setBackground(FDCTableHelper.yearTotalColor);
//			row.getCell(0).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//			row.getCell(1).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
//			//��������Ԫ�������������ݲ���Ҫ������λС����ʽ
//			row.getCell(14).getStyleAttributes().setNumberFormat("");
//			row.getCell(15).getStyleAttributes().setNumberFormat("");
//			row.getCell(13).getStyleAttributes().setNumberFormat("");
//			row.getCell(12).getStyleAttributes().setNumberFormat("");
//			if(row.getCell(0).getValue().equals(PlanIndexTypeEnum.parking)){
//				row.getCell(7).getStyleAttributes().setNumberFormat("");
//				row.getCell(8).getStyleAttributes().setNumberFormat("");
//			}
//			return;
//		}else if(isTotalRow(row)){
//			sa.setNumberFormat("#,##0.00;-#,##0.00");
//			sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
//			sa.setLocked(true);
//			sa.setBackground(FDCHelper.KDTABLE_TOTAL_BG_COLOR);
//			return;
//		}
//		if(entry==null){
//			//src modified 2012-03-07 16:06
////			row.getCell(16).setValue(Boolean.FALSE);
//			row.getCell(0).getStyleAttributes().setLocked(true);
//		}else{
//			row.getCell(0).setValue(entry.getType());
//			row.getCell(0).getStyleAttributes().setLocked(true);
//		    row.getCell(1).setValue(entry.getProductType());
//		    //src begin modified 2012-03-28 16:39
//			//row.getCell(2).setValue(entry.getPropertyRights());
//		    row.getCell(2).setValue(entry.getPropertyRightsNew());
//		    //src end
//			row.getCell(3).setValue(entry.getGroundFloorArea());
//			row.getCell(4).setValue(entry.getUnderArea());
//			row.getCell(5).setValue(entry.getConstructionArea());
//			row.getCell(6).setValue(entry.getSaleableArea());
//			
//			if(entry.getType()!=PlanIndexTypeEnum.parking){
//				row.getCell(7).setValue(entry.getProductRatio());
//				row.getCell(8).setValue(entry.getAverageArea());
//				row.getCell(9).setValue(entry.getElevator());
//				row.getCell(10).setValue(entry.getLayers());
//				row.getCell(11).setValue(entry.getStorey());
//				row.getCell(12).setValue(entry.getBuildings());
//				row.getCell(13).setValue(entry.getUnitNumber());
//				row.getCell(14).setValue(entry.getHouseholds());
//				row.getCell(15).setValue(entry.getRemark());
//			}else{
//				row.getCell(7).setValue(entry.getBuildings());
//				row.getCell(8).setValue(entry.getUnitNumber());
//				row.getCell(9).setValue(entry.getHouseholds());
//			}
//		}
//		
//
//		boolean isPark=false;
//		if(row.getCell(0)!=null&&row.getCell(0).getValue() instanceof PlanIndexTypeEnum){
//			PlanIndexTypeEnum type=(PlanIndexTypeEnum)row.getCell(0).getValue();
//			if(type==PlanIndexTypeEnum.parking){
//				isPark=true;
//			}
//		}
///**********************************************************��ʽ���ؼ�����**************************************/
//		//����ͣ��
//		if(!isPark){
//			//src begin modified 2012-03-07 15:23 old:11 new:7
//			row.getCell(7).getStyleAttributes().setLocked(true);
//			row.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			//��ʽ��table��Ԫ��Ϊ���ֿؼ�
//			for(int i=3;i<table.getColumnCount()-1;i++){//src old:-2 new:-1
//				//src begin modified 2012-03-08 10:02
//				//if(i==7){
//				//	continue;
//				//}
//				sa=row.getCell(i).getStyleAttributes();
//				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
//				//��ͣ����������������Ԫ�������ݶ��ø�ʽ��������
//				if(i==9||(i>=12&& i<=15)||i==10){//src add i==9
//					row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
//				}else{
//					row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
//					sa.setNumberFormat("#,##0.00;-#,##0.00");
//				}
//			}
//		}else{
//			//��ʽ��table��Ԫ��Ϊ���ֿؼ�
//			for(int i=3;i<table.getColumnCount()-2;i++){
//				sa=row.getCell(i).getStyleAttributes();
//				sa.setHorizontalAlign(HorizontalAlignment.RIGHT);
//				//ͣ��7-8�и�ʽ��������
//				if(i>=7&&i<=8){
//					row.getCell(i).setEditor(CellBinder.getCellIntegerNumberEdit());
//				}else{
//					row.getCell(i).setEditor(CellBinder.getCellNumberEdit());
//					sa.setNumberFormat("#,##0.00;-#,##0.00");
//				}
//			}
//			for(int i=10;i<table.getColumnCount();i++){//src old:12 new:10
//				row.getCell(i).getStyleAttributes().setLocked(true);
//				row.getCell(i).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//			}
//		}
//		//src begin modified 2012-03-07 15:18
//		row.getCell(5).getStyleAttributes().setLocked(true);
//		row.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//	}
	
	public KDTDefaultCellEditor getCellEditor(){
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor();
		KDFormattedTextField formatText = new KDFormattedTextField();
		formatText.setPrecision(8);
		cellEditor = new KDTDefaultCellEditor(formatText);
		return cellEditor;
	}
	
	
//	public void addRow(ActionEvent e){
//		//��С����֮ǰ���룬Ȼ������ͳ����
//		boolean hasAdd=false;
//		int index = table.getSelectManager().getActiveRowIndex();
//		if (index == -1) {
//			return;
//		}
//		IRow selectRow = table.getRow(index);
//		if(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum){
//			for(int i=index;i<table.getRowCount()-1;i++){
//				IRow row=table.getRow(i);
//				if(isSubTotalRow(row)){
//					row=table.addRow(i);
////					table.getScriptManager().removeAll();
//					row.getCell(0).setValue(selectRow.getCell(0).getValue());
//					loadRow(null, row);
//					hasAdd=true;
//					break;
//				}
//			}
//		}
//		if(hasAdd){
//			reSetExpressions();
//		}else{
//			FDCMsgBox.showWarning("��ѡ���Ʒ���ɽ��������в�����");
//		}
//		
//	}
	
//	public void deleteRow(ActionEvent e){
//		//ɾ���к�����ͳ����
//		boolean hasDelete=false;
//		KDTSelectManager selectManager = table.getSelectManager();
//		if (selectManager == null || selectManager.size() == 0) {
//			return;
//		}
//		//�ж��Ƿ����ɾ����ÿһ�ֲ�Ʒ���ɶ���ʼ��һ�У�ɾ��Ҳ���뱣��һ��
//		boolean mustAdd=false;
//		for (int i = 0; i < selectManager.size(); i++) {
//			KDTSelectBlock selectBlock = selectManager.get(i);
//			for (int j = selectBlock.getTop(); j <= selectBlock.getBottom(); j++) {
//				IRow selectRow = table.getRow(j);
//				if (selectRow == null) {
//					continue;
//				}
//				if(selectRow.getCell(0).getValue() instanceof PlanIndexTypeEnum){
//					if(selectRow.getCell(1).getValue()!=null&&selectRow.getCell(1).getValue().equals("С��")){
//						continue;
//					}
//					
//					if((table.getCell(j-1, 1).getValue()!=null&&table.getCell(j-1, 1).getValue().equals("С��"))
//							&&(table.getCell(j+1, 1).getValue()!=null&&table.getCell(j+1, 1).getValue().equals("С��"))){
//						mustAdd=true;
//					}else{
//						if((((table.getCell(j-1, 2).getValue()!=null&&table.getCell(j-1, 2).getValue().equals("��Ȩ����")))||
//								(table.getCell(j-1, 1).getValue()!=null&&table.getCell(j-1, 1).getValue().equals("��Ʒ����")))
//								&&(table.getCell(j+1, 1).getValue()!=null&&table.getCell(j+1, 1).getValue().equals("С��"))){
//							mustAdd=true;
//						}
//					}
////					table.getScriptManager().removeAll();
//					if(mustAdd){
//						addRow(e);
//					}
//					table.getScriptManager().setAutoRun(false);
//					table.getScriptManager().isAutoRun();
//					table.getScriptManager().removeAll();
//					table.removeRow(selectRow.getRowIndex());
//					hasDelete=true;
//				}
//				break;//ֻ֧�ֵ���
//			}
//		}
//		if(hasDelete){
//			reSetExpressions();
//			table.getScriptManager().setAutoRun(true);
//			table.getScriptManager().isAutoRun();
//		}else{
//			FDCMsgBox.showWarning("��ѡ���Ʒ���ɽ���ɾ���в�����");
//		}
//		
//		KDTEditEvent event=new KDTEditEvent(table);
//		int activeColumnIndex = 4;
//		int activeRowIndex =table.getSelectManager().getActiveRowIndex();
//		event.setColIndex(activeColumnIndex);
//		event.setRowIndex(activeRowIndex);
//		event.setOldValue(FDCHelper.ONE);
//		event.setValue(null);
//		table_editStopped(event);
//		
//
//	}
	
//	private void reSetExpressions1(){
//		//�õ�סլС�Ƶ��к�;
//		int houseSubIndex=dynRowBase;
//		int businessSubIndex=dynRowBase;
//		int publicSubIndex=dynRowBase;
//		int parkingSubIndex=getTable().getRowCount()-2;
//		for(int i=dynRowBase;i<getTable().getRowCount()-1;i++){
//			IRow row=getTable().getRow(i);
//			if(row.getCell(0).getValue()==PlanIndexTypeEnum.house
//					&&isSubTotalRow(row)){
//				houseSubIndex=i;
//			}
//			if(row.getCell(0).getValue()==PlanIndexTypeEnum.business
//					&&isSubTotalRow(row)){
//				businessSubIndex=i;
//			}
//			if(row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild
//					&&isSubTotalRow(row)){
//				publicSubIndex=i;
//				break;
//			}
//		}
//		//סլ
//		if(houseSubIndex>dynRowBase){
//			IRow houseRow=getTable().getRow(houseSubIndex);
//			for(int j=3;j<getTable().getColumnCount()-1;j++){//src old:-2 new:-1
//				if(j>=9&&j<=13){//src old:7,9,10 new:6,8,9
//					continue;
//				}
//				char c=(char)('A'+j);
//				String exp=null;				
//				exp="=sum("+c+(dynRowBase+1)+":"+c+(houseSubIndex)+")";
//				houseRow.getCell(j).setExpressions(exp);
//			}
//			
//			
//		}else{
//			IRow houseRow=getTable().getRow(houseSubIndex);
//			for(int j=2;j<getTable().getColumnCount();j++){
//				houseRow.getCell(j).setValue(null);
//				houseRow.getCell(j).setExpressions("");
//			}
//		}
//		//��ҵ
//		if(businessSubIndex>(houseSubIndex+1)){
//			IRow businessRow=getTable().getRow(businessSubIndex);
//			for(int j=3;j<getTable().getColumnCount()-1;j++){//src old:-2 new:-1
//				if(j>=9&&j<=13){//src old:7,9,10 new:6,8,9
//					continue;
//				}
//				char c=(char)('A'+j);
//				String exp=null;				
//				exp="=sum("+c+(houseSubIndex+2)+":"+c+(businessSubIndex)+")";
//				businessRow.getCell(j).setExpressions(exp);
//			}
//		}else{
//			IRow businessRow=getTable().getRow(businessSubIndex);
//			for(int j=2;j<getTable().getColumnCount();j++){
//				businessRow.getCell(j).setValue(null);
//				businessRow.getCell(j).setExpressions("");
//			}
//		
//		}
//		//����
//		if(publicSubIndex>(businessSubIndex+1)){
//			IRow publicRow=getTable().getRow(publicSubIndex);
//			for(int j=3;j<getTable().getColumnCount()-1;j++){//src old:-2 new:-1
//				if(j>=9&&j<=13){//src old:7,9,10 new:6,8,9
//					continue;
//				}
//				char c=(char)('A'+j);
//				String exp=null;				
//				exp="=sum("+c+(businessSubIndex+2)+":"+c+(publicSubIndex)+")";
//				publicRow.getCell(j).setExpressions(exp);
//			}
//			
//		}else{
//			IRow publicRow=getTable().getRow(publicSubIndex);
//			for(int j=2;j<getTable().getColumnCount();j++){
//				publicRow.getCell(j).setValue(null);
//				publicRow.getCell(j).setExpressions("");
//			}
//		}
//		//ͣ��
//		if(parkingSubIndex>(publicSubIndex+2)){
//			IRow parkRow=getTable().getRow(parkingSubIndex);
//			for(int j=3;j<10;j++){//src old:13 new:10
////				if(j==6||j==7||j==8){//src old:1,9,11 new:6,7,8
////					continue;
////				}
//				char c=(char)('A'+j);
//				String exp=null;				
//				exp="=sum("+c+(publicSubIndex+3)+":"+c+(parkingSubIndex)+")";//src old:+2 new:+3
//				parkRow.getCell(j).setExpressions(exp);
//			}
//		}else{
//			IRow parkRow=getTable().getRow(parkingSubIndex);
//			for(int j=2;j<6;j++){
//				parkRow.getCell(j).setValue(null);
//				parkRow.getCell(j).setExpressions("");
//			}
//		}
//		
////		
////		//���ڰ�С����ͳ��
////		String exp="=ROUND(sum(";
////		String expSellArea="=ROUND(sum(";
////		boolean isPark=false;
////		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
////			if(table.getCell(i, 1).getValue()!=null&&table.getCell(i, 1).getValue().equals("С��")){
////				continue;
////			}				
////			if(table.getCell(i, 2).getValue()!=null&&table.getCell(i, 2).getValue().equals("ռ�����")){
////				isPark=true;
////				continue;
////			}
////
////			exp=exp+"A"+(i+1)+",";
////			expSellArea=expSellArea+"A"+(i+1)+",";
////
////		}
////		
////		exp=exp.substring(0,exp.length()-1)+"),2)"	;
////		expSellArea=expSellArea.substring(0,expSellArea.length()-1)+"),2)"	;
//		
//	}
//	private void reSetExpressions(){
//		//�õ�סլС�Ƶ��к�;
//		int houseSubIndex=dynRowBase;//סլС���к�
//		int businessSubIndex=dynRowBase;//��ҵС���к�
//		int publicSubIndex=dynRowBase;//�������׽���С���к�
//		int parkingSubIndex=getTable().getRowCount()-2;//ͣ��С���к�
//		int upAreaColumnIndex =3;//���Ͻ������
//		int downAreaColumnIndex =4;//���½������
//		int buildAreaColummnIndex=5;//�������
//		int sellAreaColummnIndex=6;//�������
//		int ratioColummnIndex=7;//��Ʒ����
//		int avgHuColummnIndex=8;//ƽ��ÿ�����
//		int elevatorColumnIndex=9;//��λ���۱�
//		int huColummnIndex=14;//����
//		
//		for(int i=dynRowBase;i<getTable().getRowCount()-1;i++){
//			IRow row=getTable().getRow(i);
//			if(row.getCell(0).getValue()==PlanIndexTypeEnum.house
//					&&isSubTotalRow(row)){
//				houseSubIndex=i;
//			}
//			if(row.getCell(0).getValue()==PlanIndexTypeEnum.business
//					&&isSubTotalRow(row)){
//				businessSubIndex=i;
//			}
//			if(row.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild
//					&&isSubTotalRow(row)){
//				publicSubIndex=i;
//				break;
//			}
//		}
//		//סլ
//		Map sumMap=new HashMap();
//		for(int m =dynRowBase;m<houseSubIndex;m++){
//			sumMap.put("groundFloorArea", FDCNumberHelper.add(sumMap.get("groundFloorArea"), table.getCell(m, upAreaColumnIndex).getValue()));
//			sumMap.put("underArea", FDCNumberHelper.add(sumMap.get("underArea"), table.getCell(m, downAreaColumnIndex).getValue()));
//			sumMap.put("constructionArea", FDCNumberHelper.add(sumMap.get("constructionArea"), table.getCell(m, buildAreaColummnIndex).getValue()));
//			sumMap.put("saleableArea", FDCNumberHelper.add(sumMap.get("saleableArea"), table.getCell(m, sellAreaColummnIndex).getValue()));
//			sumMap.put("productRatio", FDCNumberHelper.add(sumMap.get("productRatio"), table.getCell(m, ratioColummnIndex).getValue()));
//			sumMap.put("averageArea", FDCNumberHelper.add(sumMap.get("averageArea"), table.getCell(m, avgHuColummnIndex).getValue()));
//			sumMap.put("households", FDCNumberHelper.add(sumMap.get("households"), table.getCell(m, huColummnIndex).getValue()));
//		}
//		table.getCell(houseSubIndex, upAreaColumnIndex).setValue(sumMap.get("groundFloorArea")!=null?sumMap.get("groundFloorArea"):(new BigDecimal("0.00")));
//		table.getCell(houseSubIndex, downAreaColumnIndex).setValue(sumMap.get("underArea")!=null?sumMap.get("underArea"):(new BigDecimal("0.00")));
//		table.getCell(houseSubIndex, buildAreaColummnIndex).setValue(sumMap.get("constructionArea")!=null?sumMap.get("constructionArea"):(new BigDecimal("0.00")));
//		table.getCell(houseSubIndex, sellAreaColummnIndex).setValue(sumMap.get("saleableArea")!=null?sumMap.get("saleableArea"):(new BigDecimal("0.00")));
//		table.getCell(houseSubIndex, ratioColummnIndex).setValue(sumMap.get("productRatio")!=null?sumMap.get("productRatio"):(new BigDecimal("0.00")));
//		table.getCell(houseSubIndex, avgHuColummnIndex).setValue(sumMap.get("averageArea")!=null?sumMap.get("averageArea"):(new BigDecimal("0.00")));
//		table.getCell(houseSubIndex, huColummnIndex).setValue(sumMap.get("households")!=null?sumMap.get("households"):(new Integer("0")));
//		
//		//��ҵ
//		Map sumMap2 =new HashMap();
//		for(int m =houseSubIndex+1;m<businessSubIndex;m++){
//			sumMap2.put("groundFloorArea", FDCNumberHelper.add(sumMap2.get("groundFloorArea"), table.getCell(m, upAreaColumnIndex).getValue()));
//			sumMap2.put("underArea", FDCNumberHelper.add(sumMap2.get("underArea"), table.getCell(m, downAreaColumnIndex).getValue()));
//			sumMap2.put("constructionArea", FDCNumberHelper.add(sumMap2.get("constructionArea"), table.getCell(m, buildAreaColummnIndex).getValue()));
//			sumMap2.put("saleableArea", FDCNumberHelper.add(sumMap2.get("saleableArea"), table.getCell(m, sellAreaColummnIndex).getValue()));
//			sumMap2.put("productRatio", FDCNumberHelper.add(sumMap2.get("productRatio"), table.getCell(m, ratioColummnIndex).getValue()));
//			sumMap2.put("averageArea", FDCNumberHelper.add(sumMap2.get("averageArea"), table.getCell(m, avgHuColummnIndex).getValue()));
//			sumMap2.put("households", FDCNumberHelper.add(sumMap2.get("households"), table.getCell(m, huColummnIndex).getValue()));
//		}
//		table.getCell(businessSubIndex, upAreaColumnIndex).setValue(sumMap2.get("groundFloorArea")!=null?sumMap2.get("groundFloorArea"):(new BigDecimal("0.00")));
//		table.getCell(businessSubIndex, downAreaColumnIndex).setValue(sumMap2.get("underArea")!=null?sumMap2.get("underArea"):(new BigDecimal("0.00")));
//		table.getCell(businessSubIndex, buildAreaColummnIndex).setValue(sumMap2.get("constructionArea")!=null?sumMap2.get("constructionArea"):(new BigDecimal("0.00")));
//		table.getCell(businessSubIndex, sellAreaColummnIndex).setValue(sumMap2.get("saleableArea")!=null?sumMap2.get("saleableArea"):(new BigDecimal("0.00")));
//		table.getCell(businessSubIndex, ratioColummnIndex).setValue(sumMap2.get("productRatio")!=null?sumMap2.get("productRatio"):(new BigDecimal("0.00")));
//		table.getCell(businessSubIndex, avgHuColummnIndex).setValue(sumMap2.get("averageArea")!=null?sumMap2.get("averageArea"):(new BigDecimal("0.00")));
//		table.getCell(businessSubIndex, huColummnIndex).setValue(sumMap2.get("households")!=null?sumMap2.get("households"):(new Integer("0")));
//		//����
//		Map sumMap3=new HashMap();
//		for(int m =businessSubIndex+1;m<publicSubIndex;m++){
//			sumMap3.put("groundFloorArea", FDCNumberHelper.add(sumMap3.get("groundFloorArea"), table.getCell(m, upAreaColumnIndex).getValue()));
//			sumMap3.put("underArea", FDCNumberHelper.add(sumMap3.get("underArea"), table.getCell(m, downAreaColumnIndex).getValue()));
//			sumMap3.put("constructionArea", FDCNumberHelper.add(sumMap3.get("constructionArea"), table.getCell(m, buildAreaColummnIndex).getValue()));
//			sumMap3.put("saleableArea", FDCNumberHelper.add(sumMap3.get("saleableArea"), table.getCell(m, sellAreaColummnIndex).getValue()));
//			sumMap3.put("productRatio", FDCNumberHelper.add(sumMap3.get("productRatio"), table.getCell(m, ratioColummnIndex).getValue()));
//			sumMap3.put("averageArea", FDCNumberHelper.add(sumMap3.get("averageArea"), table.getCell(m, avgHuColummnIndex).getValue()));
//			sumMap3.put("households", FDCNumberHelper.add(sumMap3.get("households"), table.getCell(m, huColummnIndex).getValue()));
//		}
//		table.getCell(publicSubIndex, upAreaColumnIndex).setValue(sumMap3.get("groundFloorArea")!=null?sumMap3.get("groundFloorArea"):(new BigDecimal("0.00")));
//		table.getCell(publicSubIndex, downAreaColumnIndex).setValue(sumMap3.get("underArea")!=null?sumMap3.get("underArea"):(new BigDecimal("0.00")));
//		table.getCell(publicSubIndex, buildAreaColummnIndex).setValue(sumMap3.get("constructionArea")!=null?sumMap3.get("constructionArea"):(new BigDecimal("0.00")));
//		table.getCell(publicSubIndex, sellAreaColummnIndex).setValue(sumMap3.get("saleableArea")!=null?sumMap3.get("saleableArea"):(new BigDecimal("0.00")));
//		table.getCell(publicSubIndex, ratioColummnIndex).setValue(sumMap3.get("productRatio")!=null?sumMap3.get("productRatio"):(new BigDecimal("0.00")));
//		table.getCell(publicSubIndex, avgHuColummnIndex).setValue(sumMap3.get("averageArea")!=null?sumMap3.get("averageArea"):(new BigDecimal("0.00")));
//		table.getCell(publicSubIndex, huColummnIndex).setValue(sumMap3.get("households")!=null?sumMap3.get("households"):(new Integer("0")));
//		//ͣ��
//		Map sumMap4=new HashMap();
//		for(int m =publicSubIndex+2;m<parkingSubIndex;m++){
//			sumMap4.put("groundFloorArea", FDCNumberHelper.add(sumMap4.get("groundFloorArea"), table.getCell(m, upAreaColumnIndex).getValue()));
//			sumMap4.put("underArea", FDCNumberHelper.add(sumMap4.get("underArea"), table.getCell(m, downAreaColumnIndex).getValue()));
//			sumMap4.put("constructionArea", FDCNumberHelper.add(sumMap4.get("constructionArea"), table.getCell(m, buildAreaColummnIndex).getValue()));
//			sumMap4.put("saleableArea", FDCNumberHelper.add(sumMap4.get("saleableArea"), table.getCell(m, sellAreaColummnIndex).getValue()));
//			sumMap4.put("productRatio", FDCNumberHelper.add(sumMap4.get("productRatio"), table.getCell(m, ratioColummnIndex).getValue()));
//			sumMap4.put("averageArea", FDCNumberHelper.add(sumMap4.get("averageArea"), table.getCell(m, avgHuColummnIndex).getValue()));
//			sumMap4.put("elevator", FDCNumberHelper.add(sumMap4.get("elevator"), table.getCell(m, elevatorColumnIndex).getValue()));
//		}
//		table.getCell(parkingSubIndex, upAreaColumnIndex).setValue(sumMap4.get("groundFloorArea")!=null?sumMap4.get("groundFloorArea"):(new BigDecimal("0.00")));
//		table.getCell(parkingSubIndex, downAreaColumnIndex).setValue(sumMap4.get("underArea")!=null?sumMap4.get("underArea"):(new BigDecimal("0.00")));
//		table.getCell(parkingSubIndex, buildAreaColummnIndex).setValue(sumMap4.get("constructionArea")!=null?sumMap4.get("constructionArea"):(new BigDecimal("0.00")));
//		table.getCell(parkingSubIndex, sellAreaColummnIndex).setValue(sumMap4.get("saleableArea")!=null?sumMap4.get("saleableArea"):(new BigDecimal("0.00")));
//		table.getCell(parkingSubIndex, ratioColummnIndex).setValue(sumMap4.get("productRatio")!=null?sumMap4.get("productRatio"):(new Integer("0")));
//		table.getCell(parkingSubIndex, avgHuColummnIndex).setValue(sumMap4.get("averageArea")!=null?sumMap4.get("averageArea"):(new Integer("0")));
//		table.getCell(parkingSubIndex, elevatorColumnIndex).setValue(sumMap4.get("elevator")!=null?sumMap4.get("elevator"):(new BigDecimal("0.00")));
//		
//	}
	/**
	 * ȡ�ù滮ָ�������Ĳ�Ʒ
	 * @return
	 */
	public Set getProductIdSet(){
		Set idSet=new HashSet();
		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
			IRow row=table.getRow(i);
			Object value = row.getCell(1).getValue();
			if(value instanceof ProductTypeInfo){
				idSet.add(((ProductTypeInfo)value).getId().toString());
			}
		}
		return idSet;
	}
	
//	public EconomicIndicatorsInfo getEconomicIndicatorsInfo(){
//		if(economicIndicatorsInfo==null){
//			economicIndicatorsInfo = new EconomicIndicatorsInfo();
//		}
//		economicIndicatorsInfo.getEntrys().clear();
//		binder.setObjectValue(economicIndicatorsInfo);
//		
//		//���÷�¼��ֵ
//		for(int i=dynRowBase;i<table.getRowCount()-1;i++){
//			IRow row=table.getRow(i);
//			if(row.getCell(0).getValue() instanceof PlanIndexTypeEnum){
//				Object value = row.getCell(1).getValue();
////				if(value==null||value.toString().trim().length()<1){
////					continue;
////				}
//				if("С��".equals(value)){
////					//����һ����
////					EconomicIndexEntryInfo entry=new EconomicIndexEntryInfo();
////					entry.setType((PlanIndexTypeEnum)row.getCell(0).getValue());
////					economicIndicatorsInfo.getEntrys().add(entry);
//				}else{
//					boolean isPark =false;
//					EconomicIndexEntryInfo entry=new EconomicIndexEntryInfo();
//					PlanIndexTypeEnum type=(PlanIndexTypeEnum)row.getCell(0).getValue();
//					entry.setType(type);
//					if(type==PlanIndexTypeEnum.parking){
//						isPark=true;
//					}
//					//��Ʒ
//					if(value instanceof ProductTypeInfo){
//						entry.setProductType((ProductTypeInfo)value);
//					}else{
////						//���˿հ�
////						if(FDCHelper.isEmpty(value)){
////							boolean isEmpty=true;
////							for(int h=2;h<table.getColumnCount()-2;h++){
////								ICell cell = row.getCell(h);
////								if(cell!=null&&FDCHelper.toBigDecimal(cell.getValue()).compareTo(FDCHelper.ZERO)==0){
////									isEmpty=true;
////								}else{
////									isEmpty=false;
////									break;
////								}
////							}
////							if(isEmpty){
////								continue;
////							}
////						}
//						
//					}
//					if(!isPark){
//						if(FDCHelper.toBigDecimal(row.getCell(3).getValue()) instanceof BigDecimal){
//							entry.setGroundFloorArea(FDCHelper.toBigDecimal(row.getCell(3).getValue()));
//						}
//						if(row.getCell(4).getValue() instanceof BigDecimal){
//							entry.setUnderArea((BigDecimal)row.getCell(4).getValue());
//						}
//						if(row.getCell(5).getValue() instanceof BigDecimal){
//							entry.setConstructionArea((BigDecimal)row.getCell(5).getValue());
//						}
//						if(row.getCell(6).getValue() instanceof BigDecimal){
//							entry.setSaleableArea((BigDecimal)row.getCell(6).getValue());
//						}
//						if(row.getCell(7).getValue() instanceof BigDecimal){
//							entry.setProductRatio((BigDecimal)row.getCell(7).getValue());
//						}
//						if(row.getCell(8).getValue() instanceof BigDecimal){
//							entry.setAverageArea((BigDecimal)row.getCell(8).getValue());
//						}
//						if(FDCHelper.toBigDecimal(row.getCell(9).getValue()) instanceof BigDecimal){
//							entry.setElevator(FDCHelper.toBigDecimal(row.getCell(9).getValue()));
//						}
//						if(FDCHelper.toBigDecimal(row.getCell(10).getValue()) instanceof BigDecimal){
//							entry.setLayers (FDCHelper.toBigDecimal(row.getCell(10).getValue()));
//						}
//	
//						if(FDCHelper.toBigDecimal(row.getCell(11).getValue()) instanceof BigDecimal){
//							entry.setStorey (FDCHelper.toBigDecimal(row.getCell(11).getValue()));
//						}
//						if(FDCHelper.toBigDecimal(row.getCell(12).getValue()) instanceof BigDecimal){
//							entry.setBuildings  (FDCHelper.toBigDecimal(row.getCell(12).getValue()));
//						}
//						if(FDCHelper.toBigDecimal(row.getCell(13).getValue()) instanceof BigDecimal){
//							entry.setUnitNumber (FDCHelper.toBigDecimal(row.getCell(13).getValue()));
//						}
//						
//						if(FDCHelper.toBigDecimal(row.getCell(14).getValue()) instanceof BigDecimal){
//							entry.setHouseholds (FDCHelper.toBigDecimal(row.getCell(14).getValue()));
//						}		
//						//src modified 2012-03-07 15:42
//						if(row.getCell(2).getValue() instanceof PropertyRightsInfo){
//							entry.setPropertyRightsNew((PropertyRightsInfo)row.getCell(2).getValue());
//						}
//						//src modified 2012-03-07 15:42
//						entry.setRemark((String)row.getCell(15).getValue());//src old:17 new:15
//					}else{
//						//ͣ��
//						if(row.getCell(2).getValue() instanceof PropertyRightsInfo){
//							entry.setPropertyRightsNew((PropertyRightsInfo)row.getCell(2).getValue());
//						}
//						
//						if(FDCHelper.toBigDecimal(row.getCell(3).getValue()) instanceof BigDecimal){
//							entry.setGroundFloorArea(FDCHelper.toBigDecimal(row.getCell(3).getValue()));
//						}
//						if(row.getCell(4).getValue() instanceof BigDecimal){
//							entry.setUnderArea((BigDecimal)row.getCell(4).getValue());
//						}
//						if(row.getCell(5).getValue() instanceof BigDecimal){
//							entry.setConstructionArea((BigDecimal)row.getCell(5).getValue());
//						}
//						if(row.getCell(6).getValue() instanceof BigDecimal){
//							entry.setSaleableArea((BigDecimal)row.getCell(6).getValue());
//						}
//						//��λ�������ڴ�����
//						if(FDCHelper.toBigDecimal(row.getCell(7).getValue()) instanceof BigDecimal){
//							entry.setBuildings(FDCHelper.toBigDecimal(row.getCell(7).getValue()));
//						}
//						//���۳�λ�����������ڿ��������
//						if(FDCHelper.toBigDecimal(row.getCell(8).getValue()) instanceof BigDecimal){
//							entry.setUnitNumber(FDCHelper.toBigDecimal(row.getCell(8).getValue()));
//						}
//						//��λ���۱ȴ��ڵ�Ԫ����
//						if(FDCHelper.toBigDecimal(row.getCell(9).getValue()) instanceof BigDecimal){
//							entry.setHouseholds(FDCHelper.toBigDecimal(row.getCell(9).getValue()));
//						}
//					}
//					entry.setParent(economicIndicatorsInfo);
//					entry.setIndex(i);
//					economicIndicatorsInfo.getEntrys().add(entry);
//				}
//			}
//			
//			
//		}
//		return economicIndicatorsInfo;
//		
//	}
	public EconomicIndexEntryInfo getEconomicIndexEntryInfo(String productId){
		if(economicIndicatorsInfo==null||productId==null){
			return null;
		}
		for(int i=0;i<economicIndicatorsInfo.getEntrys().size();i++){
			EconomicIndexEntryInfo entry = economicIndicatorsInfo.getEntrys().get(i);
			if(entry.getProductType()!=null&&entry.getProductType().getId().toString().equals(productId)){
				return entry;
			}
		}
		return null;
	}
	
//	protected void table_editStopped(KDTEditEvent e) {
//		if(table==null){
//			return;
//		}
//		Object objOld=e.getOldValue();
//		Object objNew=e.getValue();
//		if(objOld==null&&objNew==null){
//			return;
//		}
//		if(objOld!=null&&objNew!=null&&objOld.equals(objNew)){
//			return;
//		}
//		if(e.getRowIndex()>=dynRowBase){
//			if(e.getColIndex()==3||e.getColIndex()==4){//src old:5,6 new:3,4
//				IRow row = table.getRow(e.getRowIndex());
//				BigDecimal upArea = new BigDecimal("0.00");
//				BigDecimal downArea = new BigDecimal("0.00");
//				if(row.getCell(3).getValue()!=null)
//					upArea = FDCHelper.toBigDecimal(row.getCell(3).getValue());
//				if(row.getCell(4).getValue()!=null)
//					downArea=FDCHelper.toBigDecimal(row.getCell(4).getValue());
//				row.getCell(5).setValue(upArea.add(downArea));
//				if(table.getRowCount()-1>dynRowBase){
//					BigDecimal buildingAreaSum = new BigDecimal("0.00");
//					int min =dynRowBase;
//					int max =dynRowBase;
//					boolean beginFlag = false;
//					for(int k=dynRowBase;k<table.getRowCount()-1;k++){
//						IRow rowSub = getTable().getRow(k);
//						if(row.getCell(0).getValue() == PlanIndexTypeEnum.house){
//							if(beginFlag==false){min = k;beginFlag=true;}
//							if(rowSub.getCell(5).getValue()!=null&&rowSub.getCell(0).getValue()==PlanIndexTypeEnum.house){
//								if(isSubTotalRow(rowSub)){max = k;}else{
//									buildingAreaSum = buildingAreaSum.add(FDCHelper.toBigDecimal(rowSub.getCell(5).getValue()));
//								}
//							}
//						}
//						if(row.getCell(0).getValue() == PlanIndexTypeEnum.business&&rowSub.getCell(0).getValue()==PlanIndexTypeEnum.business){
//							if(beginFlag==false){min = k;beginFlag=true;}
//							if(rowSub.getCell(5).getValue()!=null){
//								if(isSubTotalRow(rowSub)){max = k;}else{
//									buildingAreaSum = buildingAreaSum.add(FDCHelper.toBigDecimal(rowSub.getCell(5).getValue()));
//								}
//							}
//						}
//						if(row.getCell(0).getValue() == PlanIndexTypeEnum.publicBuild&&rowSub.getCell(0).getValue()==PlanIndexTypeEnum.publicBuild){
//							if(beginFlag==false){min = k;beginFlag=true;}
//							if(rowSub.getCell(5).getValue()!=null){
//								if(isSubTotalRow(rowSub)){max = k;}else{
//									buildingAreaSum = buildingAreaSum.add(FDCHelper.toBigDecimal(rowSub.getCell(5).getValue()));
//								}
//							}
//						}
//					}
//					BigDecimal rates = new BigDecimal("0.00");
//					for(int l=max-1;l>=min;l--){
//						IRow rowType = getTable().getRow(l);
//						if(buildingAreaSum.compareTo(new BigDecimal("0.00"))>0){
//							if(rowType.getCell(5).getValue()!=null){
//								if(l!=min){
//									BigDecimal rowProductRate = ((BigDecimal)rowType.getCell(5).getValue()).divide(buildingAreaSum,2,BigDecimal.ROUND_HALF_UP);
//									rates = rates.add(rowProductRate);
//									rowType.getCell(7).setValue(rowProductRate);
//								}else{
//									rowType.getCell(7).setValue((new BigDecimal("1.00")).subtract(rates));
//								}
//							}
//						}
//					}
//				}
//			}
//			reSetExpressions();
////			if(e.getColIndex()==5||e.getColIndex()==14){
////				IRow row = table.getRow(e.getRowIndex());
////				BigDecimal upArea = new BigDecimal("0.00");
////				BigDecimal households = new BigDecimal("0.00");
////				if(row.getCell(5).getValue()!=null)
////					upArea = (BigDecimal)row.getCell(5).getValue();
////				if(row.getCell(14).getValue()!=null){
////					households =new BigDecimal(((Integer)row.getCell(14).getValue()).intValue());
////				}
////				if(row.getCell(14).getValue()!=null&&households.compareTo(new BigDecimal("0.00"))>0){
////					row.getCell(11).setValue(FDCHelper.divide(upArea,households));
////				}
////			}
////			//������ռ�����
////			if(e.getColIndex()==3){
////				BigDecimal countArea = new BigDecimal("0.00");
////				for(int i = dynRowBase;i<table.getRowCount();i++){
////					IRow row = table.getRow(i);
////					if(isSubTotalRow(row)||row.getCell(3).getValue()==null||!(row.getCell(3).getValue() instanceof BigDecimal)){
////						continue;
////					}
////					countArea = countArea.add((BigDecimal)row.getCell(3).getValue());
////				}
////				table.getCell(0, 2).setValue(countArea);
////			}
////			//�����ܽ������
////			if(e.getColIndex()==5||e.getColIndex()==6){
////				BigDecimal countArea = new BigDecimal("0.00");
////				for(int i = dynRowBase;i<table.getRowCount();i++){
////					IRow row = table.getRow(i);
////					if(isSubTotalRow(row)||row.getCell(4).getValue()==null||!(row.getCell(4).getValue() instanceof BigDecimal)){
////						continue;
////					}
////					countArea = countArea.add((BigDecimal)row.getCell(4).getValue());
////
////				}
////				table.getCell(0, 5).setValue(countArea);
////			}
//		}
////		
////		//������Ͻ������
////		if(e.getColIndex()==5){
////			BigDecimal upArea = new BigDecimal("0.00");
////			for(int i = dynRowBase;i<table.getRowCount();i++){
////				IRow row = table.getRow(i);
////				if(isSubTotalRow(row)||row.getCell(5).getValue()==null||!(row.getCell(5).getValue() instanceof BigDecimal)){
////					continue;
////				}
////				upArea = upArea.add((BigDecimal)row.getCell(5).getValue());
////
////			}
////			table.getCell(0, 9).setValue(upArea);
////		}
////		//������Ͻ������
////		if(e.getColIndex()==6){
////			BigDecimal downArea = new BigDecimal("0.00");
////			for(int i = dynRowBase;i<table.getRowCount();i++){
////				IRow row = table.getRow(i);
////				if(isSubTotalRow(row)||row.getCell(6).getValue()==null||!(row.getCell(6).getValue() instanceof BigDecimal)){
////					continue;
////				}
////				downArea = downArea.add((BigDecimal)row.getCell(6).getValue());
////
////			}
////			table.getCell(0, 11).setValue(downArea);
////		}
//		
//	}
	private KDBizPromptBox f7productType=null; 
	public KDBizPromptBox  getF7productType(){
		f7productType = new KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				// TODO Auto-generated method stub
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
				 
			}
		};
		f7productType
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		f7productType.setEntityViewInfo(view);
		f7productType.setEditable(true);
		f7productType.setDisplayFormat("$name$");
		f7productType.setEditFormat("$number$");
		f7productType.setCommitFormat("$number$");
		f7productType.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
			}
		});
		f7productType.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				int rowIndex=KDTableUtil.getSelectedRow(table);
				
				Object objOld=table.getCell(rowIndex,1).getValue();//e.getOldValue();
				Object objNew=e.getNewValue();
				if(objOld==null&&objNew==null) return;
				if(objOld!=null&&objOld.equals(objNew)) return;
				if(objNew!=null&&objNew.equals(f7productType.getUserObject())){
					return;
				}
			}
		});
		
		return f7productType;
	}

	private KDBizPromptBox f7PropertyRights=null; 
	public KDBizPromptBox  getF7PropertyRights(){
		f7PropertyRights = new KDBizPromptBox()
		{
			protected Object stringToValue(String t) {
				// TODO Auto-generated method stub
				 Object obj= super.stringToValue(t);
				 if(obj instanceof  IObjectValue){
					 return obj;
				 }else{
					 return t;
				 }
				 
			}
		};
		f7PropertyRights
				.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PropertyRightsQuery");
		EntityViewInfo view = new EntityViewInfo();
		f7PropertyRights.setEntityViewInfo(view);
		f7PropertyRights.setEditable(true);
		f7PropertyRights.setDisplayFormat("$name$");
		f7PropertyRights.setEditFormat("$number$");
		f7PropertyRights.setCommitFormat("$number$");
		f7PropertyRights.addPreChangeListener(new PreChangeListener(){
			public void preChange(PreChangeEvent e) {
			}
		});
		f7PropertyRights.addDataChangeListener(new DataChangeListener(){
			public void dataChanged(DataChangeEvent e) {
				int rowIndex=KDTableUtil.getSelectedRow(table);
				
				Object objOld=table.getCell(rowIndex,2).getValue();//e.getOldValue();
				Object objNew=e.getNewValue();
				if(objOld==null&&objNew==null) return;
				if(objOld!=null&&objOld.equals(objNew)) return;
				if(objNew!=null&&objNew.equals(f7PropertyRights.getUserObject())){
					return;
				}
			}
		});
		
		return f7PropertyRights;
	}
	
	private void table_tableClicked(KDTMouseEvent e){
//		if(e.getColIndex()==16&&e.getRowIndex()>=4){
//			ICell cell = table.getCell(e.getRowIndex(), 0);
//			if(cell.getValue()!=null&&cell.getValue() instanceof PlanIndexTypeEnum){
//				if(cell.getValue()==PlanIndexTypeEnum.house){
//					return;
//				}
//			}
//			cell = table.getCell(e.getRowIndex(), e.getColIndex());
//			if(cell.getValue() instanceof Boolean){
//				cell.setValue(Boolean.valueOf(!((Boolean)cell.getValue()).booleanValue()));
//			}
	}

	private boolean isSubTotalRow(IRow row){
		boolean isSubTotalRow=false;
		if(row.getCell(1).getValue()!=null&&row.getCell(1).getValue().equals("С��")){
			isSubTotalRow=true;
		}
		return isSubTotalRow;
	}
	
	private boolean isTotalRow(IRow row){
		boolean isTotalRow=false;
		Object value = row.getCell(0).getValue();
		if(value!=null&&value.equals("�ϼ�")){
			isTotalRow=true;
		}
		return isTotalRow;
	}

	
	private KDTable constrTable=null;
	public KDTable getConstructTable(){
		return constrTable;
	}

	/**
	 * �õ�һ��-1.0E17����1.0E17��BigDecimal CellEditor
	 * @author sxhong  		Date 2006-10-26
	 * @return
	 */
	public static KDTDefaultCellEditor getCellNumberEdit(int Precision){
		KDFormattedTextField kdc = new KDFormattedTextField();
        kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
        kdc.setPrecision(Precision);
        kdc.setRequired(true);
        kdc.setMinimumValue(FDCHelper.ZERO);
        kdc.setMaximumValue(FDCHelper.MAX_VALUE);
        kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
        kdc.setSupportedEmpty(true);
        kdc.setVisible(true);
        kdc.setEnabled(true);
        KDTDefaultCellEditor editor = new KDTDefaultCellEditor(kdc);
        return editor;
	}
	
	private int constructAreaColumnIndex=4;
	
}
