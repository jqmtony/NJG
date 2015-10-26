/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.freechart.ui.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * 
 * ��������ǩ֤����������
 * output class name
 */
public class TechnologyeconomyApproveUI extends AbstractTechnologyeconomyApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(TechnologyeconomyApproveUI.class);
    
    /**
     * output class constructor
     */
    public TechnologyeconomyApproveUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	initUI();
    }
    
    private void initUI() throws BOSException, SQLException{
    	
    	this.kDTable1.addColumns(4);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	IRow addRow1 = this.kDTable1.addRow();
    	addRow1.getCell(0).setValue("��˾����");
    	addRow1.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow1.getCell(2).setValue("��ͬ���");
    	addRow1.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("ǩ֤����");
    	addRow2.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(1, 1, 1, 3);
    	
    	
    	IRow addRow3 = this.kDTable1.addRow();
    	addRow3.getCell(0).setValue("�а���λ");
    	addRow3.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow3.getCell(2).setValue("��Ŀ���ʱ��");
    	addRow3.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("ʩ��������");
    	addRow4.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow4.getCell(2).setValue("������");
    	addRow4.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	
    	IRow addRow5 = this.kDTable1.addRow();
    	addRow5.getCell(0).setValue("ʵ�ʹ�������������");
    	addRow5.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow6 = this.kDTable1.addRow();
    	addRow6.getCell(0).setValue("ʵ�ʹ�������������");
    	addRow6.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow7 = this.kDTable1.addRow();
    	addRow7.getCell(0).setValue("ʵ�ʹ�������������");
    	addRow7.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow8 = this.kDTable1.addRow();
    	addRow8.getCell(0).setValue("ʵ�ʹ�������������");
    	addRow8.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(0).setValue("ǩ֤�걨���ã�");
    	addRow9.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow9.getCell(3).setValue("ǩ��");
//    	addRow9.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	
    	IRow addRow10 = this.kDTable1.addRow();
    	addRow10.getCell(0).setValue("��Ŀ���ܹ��̲�����Ŀ�������");
    	addRow10.getCell(0).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.LEFT);//����
    	IRow addRow11 = this.kDTable1.addRow();
    	IRow addRow12 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(9, 0, 11, 3);
    	
    	IRow addRow13 = this.kDTable1.addRow();
    	addRow13.getCell(0).setValue("��۹���ʦ���");
    	addRow13.getCell(0).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.LEFT);
    	IRow addRow14 = this.kDTable1.addRow();
    	IRow addRow15 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(12, 0, 14, 3);
    	
    	IRow addRow16 = this.kDTable1.addRow();
    	addRow16.getCell(0).setValue("���̾���");
    	addRow16.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow16.getCell(2).setValue("��Ŀ��˾�����ܾ���");
    	addRow16.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow17 = this.kDTable1.addRow();
    	addRow17.getCell(0).setValue("����ǩ��");
    	addRow17.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow17.getCell(2).setValue("ʱ��");
    	addRow17.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	this.kDTable1.getColumn(0).setWidth(200);
    	this.kDTable1.getColumn(1).setWidth(200);
    	this.kDTable1.getColumn(2).setWidth(200);
    	this.kDTable1.getColumn(3).setWidth(200);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	

    	String billId = "FrYcumfRTUq/iL87J1M2VXARYRc=";
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("  select ChangeAB.FCurProjectName ,ChangeAB.FNumber ,ChangeAB.Fname ,BaseU.Fname_l2 ,");
    	
    	//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(9, 0).setValue(apporveResultForMap.get("��Ŀ���ܹ���ʦ����Ŀ��������������"));
    	this.kDTable1.getCell(12, 0).setValue(apporveResultForMap.get("��۹���ʦ����������"));
    	this.kDTable1.getCell(15, 1).setValue(apporveResultForMap.get("���̲����������"));
    	this.kDTable1.getCell(15, 3).setValue(apporveResultForMap.get("��Ŀ���ʳ����ܾ��������"));
    	this.kDTable1.getCell(16, 1).setValue(apporveResultForMap.get("����ǩ֤�������"));
    	this.kDTable1.getCell(16, 3).setValue(apporveResultForMap.get("ʱ�䣻ʱ��"));
    	
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
    }

    protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}