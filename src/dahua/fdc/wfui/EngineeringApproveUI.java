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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * ����ָ���������
 * output class name
 */
public class EngineeringApproveUI extends AbstractEngineeringApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(EngineeringApproveUI.class);
    
    /**
     * output class constructor
     */
    public EngineeringApproveUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.kDTable2.getStyleAttributes().setWrapText(true);
    	initUI();
    }
    
    private void initUI() throws BOSException, SQLException{
    	
    	this.kDTable1.addColumns(4);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	IRow addRow1 = this.kDTable1.addRow();
    	addRow1.getCell(0).setValue("��Ŀ����");
    	addRow1.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow1.getCell(2).setValue("�˶����");
    	addRow1.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("��ͬ���");
    	addRow2.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager.mergeBlock(1, 1, 1, 3);
    	
    	IRow addRow3 = this.kDTable1.addRow();
    	addRow3.getCell(0).setValue("���赥λ");
    	addRow3.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow3.getCell(2).setValue("����ͼֽ���");
    	addRow3.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("����");
    	addRow4.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow4.getCell(2).setValue("����");
    	addRow4.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow5 = this.kDTable1.addRow();
    	addRow5.getCell(0).setValue("����");
//    	mergeManager.mergeBlock(4, 0, 7, 3);
    	IRow addRow6 = this.kDTable1.addRow();
//    	mergeManager.mergeBlock(4, 0, 7, 3);
    	IRow addRow7 = this.kDTable1.addRow();
//    	mergeManager.mergeBlock(4, 0, 7, 3);
    	IRow addRow8 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 3);
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(2).setValue("���̲�ӡ�£�");
    	addRow9.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	IRow addRow10 = this.kDTable1.addRow();
    	
    	addRow10.getCell(0).setValue("������");
    	addRow10.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow10.getCell(2).setValue("���̲�����");
    	addRow10.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	
    	this.kDTable1.getColumn(0).setWidth(201);
    	this.kDTable1.getColumn(1).setWidth(201);
    	this.kDTable1.getColumn(2).setWidth(201);
    	this.kDTable1.getColumn(3).setWidth(203);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	//-----------------------------------------------------------------------------------------------------------------
    	this.kDTable2.addColumns(13);
    	KDTMergeManager mergeManager2 = kDTable2.getMergeManager();
    	
    	IRow addRow21 = this.kDTable2.addRow();
    	addRow21.getCell(0).setValue("��ͬ����");
    	addRow21.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(0, 2, 0, 9);
    	mergeManager2.mergeBlock(0, 0, 0, 1);
    	
    	IRow addRow22 = this.kDTable2.addRow();
    	addRow22.getCell(0).setValue("��ͬ���");
    	addRow22.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(1, 2, 1, 9);
    	mergeManager2.mergeBlock(1, 0, 1, 1);
    	
    	IRow addRow23 = this.kDTable2.addRow();
    	addRow23.getCell(0).setValue("�����");
    	addRow23.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(2, 0, 2, 1);
    	
    	addRow23.getCell(2).setValue("XX");
    	mergeManager2.mergeBlock(2, 2, 2, 3);
    	addRow23.getCell(4).setValue("��Ʋ�");
    	addRow23.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(5).setValue("XX");
    	addRow23.getCell(6).setValue("���̲�");
    	addRow23.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(7).setValue("XX");
    	addRow23.getCell(8).setValue("ǰ�����ײ�");
    	addRow23.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(9).setValue("XX");
    	addRow23.getCell(10).setValue("���۲�");
    	addRow23.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow23.getCell(11).setValue("XX");
    	addRow23.getCell(12).setValue("����");
    	addRow23.getCell(12).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	
    	IRow addRow24 = this.kDTable2.addRow();
    	addRow24.getCell(0).setValue("���̲�");
    	addRow24.getCell(2).setValue("����ԭ�����ݣ�");
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 5, 12);
    	IRow addRow25 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 5, 12);
    	IRow addRow26 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 5, 12);
    	
    	IRow addRow27 = this.kDTable2.addRow();
    	addRow27.getCell(0).setValue("�������");
    	addRow27.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow27.getCell(2).setValue("����");
    	mergeManager2.mergeBlock(6, 2, 6, 3);
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	addRow27.getCell(4).setValue("Ԥ������Ӱ��");
    	mergeManager2.mergeBlock(6, 4, 6, 10);
    	addRow27.getCell(11).setValue("������");
    	addRow27.getCell(12).setValue("ȷ�����");
    	
    	IRow addRow28 = this.kDTable2.addRow();
    	addRow28.getCell(2).setValue("��Ʋ�");
    	addRow28.getCell(4).setValue("��ƷƷ�ʣ�");
    	addRow28.getCell(5).setValue("XX");
    	addRow28.getCell(6).setValue("���");
    	addRow28.getCell(7).setValue("XX");
    	addRow28.getCell(8).setValue("����");
    	addRow28.getCell(9).setValue("XX");
    	addRow28.getCell(10).setValue("��Ӱ��");
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	mergeManager2.mergeBlock(7, 2, 7, 3);
    	IRow addRow29 = this.kDTable2.addRow();//--
    	addRow29.getCell(2).setValue("���̲�");
    	addRow29.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow29.getCell(4).setValue("���ڣ�");
    	addRow29.getCell(5).setValue("XX");
    	addRow29.getCell(6).setValue("����");
    	addRow29.getCell(7).setValue("XX");
    	addRow29.getCell(8).setValue("�ӳ�");
    	addRow29.getCell(9).setValue("XX");
    	addRow29.getCell(10).setValue("��Ӱ��");
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	mergeManager2.mergeBlock(8, 2, 9, 2);
    	mergeManager2.mergeBlock(8, 3, 9, 3);
    	IRow addRow210 = this.kDTable2.addRow();
    	addRow210.getCell(4).setValue("�Ƿ񷵹���");
    	addRow210.getCell(5).setValue("XX");
    	addRow210.getCell(6).setValue("��Ҫ����");
    	addRow210.getCell(7).setValue("XX");
    	addRow210.getCell(8).setValue("����Ҫ����");
    	mergeManager2.mergeBlock(6, 0, 16, 1);
    	mergeManager2.mergeBlock(8, 2, 9, 2);
    	mergeManager2.mergeBlock(8, 3, 9, 3);
    	IRow addRow211 = this.kDTable2.addRow();
    	addRow211.getCell(2).setValue("���۲�");
    	addRow211.getCell(4).setValue("���ۣ�");
    	addRow211.getCell(5).setValue("XX");
    	addRow211.getCell(6).setValue("����");
    	addRow211.getCell(7).setValue("XX");
    	addRow211.getCell(8).setValue("����");
    	addRow211.getCell(9).setValue("XX");
    	addRow211.getCell(10).setValue("��Ӱ��");
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(10, 2, 10, 3);
    	IRow addRow212 = this.kDTable2.addRow();
    	addRow212.getCell(2).setValue("ǰ�����ײ�");
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(11, 2, 11, 3);
    	
    	IRow addRow213 = this.kDTable2.addRow();
    	addRow213.getCell(2).setValue("��Լ��");
    	addRow213.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow213.getCell(4).setValue("���㹤�̷������ܼۣ�");
    	addRow213.getCell(10).setValue("��Ԫ");
    	mergeManager2.mergeBlock(12, 4, 12, 8);
    	IRow addRow214 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow214.getCell(4).setValue("���з�����ɵ�ǩ֤���ù��㣺");
    	addRow214.getCell(10).setValue("��Ԫ");
    	mergeManager2.mergeBlock(13, 4, 13, 8);
    	IRow addRow215 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow215.getCell(4).setValue("���α���ۼ�ռ��ͬ�۵İٷֱ�");
    	addRow215.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(14, 4, 14, 8);
    	IRow addRow216 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow216.getCell(4).setValue("����Ŀǰ����ۼ�ռ��ͬ�۰ٷֱ�");
    	addRow216.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(15, 4, 15, 8);
    	IRow addRow217 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(6, 0, 16, 0);
    	mergeManager2.mergeBlock(6, 1, 16, 1);
    	mergeManager2.mergeBlock(12, 2, 16, 2);
    	mergeManager2.mergeBlock(12, 3, 16, 3);
    	addRow217.getCell(4).setValue("������ͬ����Ƿ񳬳�Ŀ��ɱ��޶�");
    	addRow217.getCell(9).setValue("XX");
    	mergeManager2.mergeBlock(16, 4, 16, 8);
    	
    	IRow addRow218 = this.kDTable2.addRow();
    	addRow218.getCell(0).setValue("��˾��һ�����ˣ�");
    	addRow218.getCell(2).setValue("����Ŀ��˾�����������");
    	mergeManager2.mergeBlock(17, 2, 17, 12);
//    	mergeManager2.mergeBlock(17, 0, 18, 1);
    	IRow addRow219 = this.kDTable2.addRow();
    	
    	mergeManager2.mergeBlock(17, 0, 18, 1);
    	addRow219.getCell(2).setValue("��һ������ǩ�֣�");
    	mergeManager2.mergeBlock(18, 2, 18, 3);
    	mergeManager2.mergeBlock(18, 4, 18, 10);
    	addRow219.getCell(11).setValue("���ڣ�");
    	
    	IRow addRow220 = this.kDTable2.addRow();
    	addRow220.getCell(0).setValue("��һ������ǩ�֣�");
    	addRow220.getCell(2).setValue("���̹�����");
    	mergeManager2.mergeBlock(19, 2, 19, 3);
    	mergeManager2.mergeBlock(19, 4, 19, 10);
    	addRow220.getCell(11).setValue("��Լ���㲿��");
    	IRow addRow221 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(19, 0, 20, 1);
    	addRow221.getCell(2).setValue("���ĸ����ˣ�");
    	mergeManager2.mergeBlock(20, 2, 20, 3);
    	mergeManager2.mergeBlock(20, 4, 20, 10);
    	addRow221.getCell(11).setValue("�ֹܸ��ܲ�������");
    	
    	this.kDTable2.getColumn(0).setWidth(70);
    	this.kDTable2.getColumn(1).setWidth(20);
    	this.kDTable2.getColumn(2).setWidth(85);
    	this.kDTable2.getColumn(3).setWidth(20);
    	this.kDTable2.getColumn(4).setWidth(70);
    	this.kDTable2.getColumn(5).setWidth(50);
    	this.kDTable2.getColumn(6).setWidth(70);
    	this.kDTable2.getColumn(7).setWidth(50);
    	this.kDTable2.getColumn(8).setWidth(70);
    	this.kDTable2.getColumn(9).setWidth(50);
    	this.kDTable2.getColumn(10).setWidth(70);
    	this.kDTable2.getColumn(11).setWidth(110);
    	this.kDTable2.getColumn(12).setWidth(75);
    	this.kDTable2.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = "FrYcumfRTUq/iL87J1M2VXARYRc=";
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("  select ChangeAB.FCurProjectName ,ChangeAB.FNumber ,ChangeAB.Fname ,BaseU.Fname_l2 ,");
    	
    	
    	//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(9, 1).setValue(apporveResultForMap.get("������;�����"));
    	this.kDTable1.getCell(9, 3).setValue(apporveResultForMap.get("���̲�����;�����"));
    	this.kDTable2.getCell(17, 2).setValue(apporveResultForMap.get("��˾��һ�����ˣ����"));
    	this.kDTable2.getCell(18, 4).setValue(apporveResultForMap.get("��˾��һ�����ˣ�ǩ��"));
    	this.kDTable2.getCell(18, 12).setValue(apporveResultForMap.get("��˾��һ�����ˣ�����"));
    	this.kDTable2.getCell(19, 4).setValue(apporveResultForMap.get("���̹���;�����"));
    	this.kDTable2.getCell(19, 12).setValue(apporveResultForMap.get("��Լ���㲿;�����"));
    	this.kDTable2.getCell(20, 4).setValue(apporveResultForMap.get("���ĸ�����;�����"));
    	this.kDTable2.getCell(20, 12).setValue(apporveResultForMap.get("�ֹܸ��ܲ�����;�����"));
    	
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
    }
    protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable2_tableClicked(e);
    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
    }
    

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}