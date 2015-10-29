/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��Ʊ����������������
 * output class name
 */
public class DesignApproveUI extends AbstractDesignApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(DesignApproveUI.class);
          
    /**
     * output class constructor
     */
    public DesignApproveUI() throws Exception
    {
        super();
    }  
    public void onLoad() throws Exception {
    	this.kDTable1.getStyleAttributes().setWrapText(true);
    	super.onLoad();
    	initUI();
//    	KDTableHelper.autoFitRowHeight(this.kDTable1, 1);
//    	for (int i = 0; i < kDTable1.getRowCount(); i++) {
//        	KDTableHelper.autoFitRowHeight(this.kDTable1, i);
//		}
    }


    private void initUI() throws BOSException, SQLException{
    	
    	//���������ؼ�
    	KDCheckBox cb = new KDCheckBox();
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(cb);
    	
    	this.kDTable1.addColumns(12);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();

    	//��һ��
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("��������");
    	addRow.getCell(6).setValue("���뵥���");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 5);
    	mergeManager.mergeBlock(0, 7, 0, 11);



    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("���÷�Χ");
    	addRowtwo.getCell(6).setValue("���ʱ��");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 5);
    	mergeManager.mergeBlock(1, 7, 1, 11);

    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("�����");
    	addRowthree.getCell(1).setEditor(editor);
    	addRowthree.getCell(1).setValue(Boolean.FALSE);
    	addRowthree.getCell(2).setValue("��Ʋ�");
    	addRowthree.getCell(3).setEditor(editor);
    	addRowthree.getCell(3).setValue(Boolean.FALSE);
    	addRowthree.getCell(4).setValue("���̲�");
    	addRowthree.getCell(5).setEditor(editor);
    	addRowthree.getCell(5).setValue(Boolean.FALSE);
    	addRowthree.getCell(6).setValue("ǰ�����ײ�");
    	addRowthree.getCell(7).setEditor(editor);
    	addRowthree.getCell(7).setValue(Boolean.FALSE);
    	addRowthree.getCell(8).setValue("���۲�");
    	addRowthree.getCell(9).setEditor(editor);
    	addRowthree.getCell(9).setValue(Boolean.FALSE);
    	addRowthree.getCell(10).setValue("����");
//    	mergeManager.mergeBlock(2, 10, 2, 11);

    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("��Ʋ�");
    	addRowfour.getCell(1).setValue("����ͼֽ");
    	addRowfour.getCell(2).setEditor(editor);
    	addRowfour.getCell(2).setValue(Boolean.FALSE);
    	addRowfour.getCell(3).setValue("��");
    	addRowfour.getCell(4).setEditor(editor);
    	addRowfour.getCell(4).setValue(Boolean.FALSE);
    	addRowfour.getCell(5).setValue("��");
    	addRowfour.getCell(6).setValue("��ͼ���:");
    	//�ں�(1)-(3)���� 2-4����
//    	mergeManager.mergeBlock(3, 1, 3, 2);
//    	mergeManager.mergeBlock(3, 4, 3, 5);
    	mergeManager.mergeBlock(3, 7, 3, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(1).setValue("���ԭ��");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 2);
    	mergeManager.mergeBlock(4, 3, 4, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);


    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(1).setValue("������");
    	addRowsix.getCell(3).setValue("�����");
    	addRowsix.getCell(6).setValue("����");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 4, 5, 5);
    	mergeManager.mergeBlock(5, 7, 5, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(1).setValue("������ù���:");
    	addRowseven.getCell(2).setValue("XX");
    	addRowseven.getCell(3).setValue("����");
//    	addRowseven.getCell(4).setValue("XX");
    	addRowseven.getCell(4).setEditor(editor);
    	addRowseven.getCell(4).setValue(Boolean.FALSE);
    	addRowseven.getCell(5).setValue("����");
//    	addRowseven.getCell(6).setValue("XX");
    	addRowseven.getCell(6).setEditor(editor);
    	addRowseven.getCell(6).setValue(Boolean.FALSE);
    	addRowseven.getCell(7).setValue("��");
//    	addRowseven.getCell(8).setValue("XX");
    	addRowseven.getCell(8).setEditor(editor);
    	addRowseven.getCell(8).setValue(Boolean.FALSE);
    	addRowseven.getCell(9).setValue("�践��");
//    	addRowseven.getCell(10).setValue("XX");
    	addRowseven.getCell(10).setEditor(editor);
    	addRowseven.getCell(10).setValue(Boolean.FALSE);
    	addRowseven.getCell(11).setValue("���践��");
    	//�ں�(1)-(3)���� 2-4����
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(1).setValue("���㹤�̷������ܼۣ�");
    	addRoweight.getCell(5).setValue("��Ԫ");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(7, 1, 7, 2);
    	mergeManager.mergeBlock(7, 3, 7, 4);
    	mergeManager.mergeBlock(7, 5, 7, 6);
    	mergeManager.mergeBlock(7, 7, 7, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(1).setValue("���з�����ɵ�ǩ֤���ù��㣺");
    	KDTableHelper.autoFitRowHeight(this.kDTable1, 8);
    	addRownine.getCell(6).setValue("��Ԫ");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 5, 8, 6);
    	mergeManager.mergeBlock(8, 7, 8, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(1).setValue("���α��ռ��ͬ�۵İٷֱȣ�%����");
    	addRowten.getCell(5).setValue("%");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 1, 9, 3);
    	mergeManager.mergeBlock(9, 5, 9, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(1).setValue("��ֹĿǰ����ۼƺ�ͬ�ܼ۵İٷֱȣ�%����");
    	addRowelev.getCell(5).setValue("%");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(10, 1, 10, 3);
    	mergeManager.mergeBlock(10, 5, 10, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);


    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(1).setValue("������ͬ����Ƿ񳬳�Ŀ��ɱ��޶");
//    	addRowtwev.getCell(4).setValue("XX");
    	addRowtwev.getCell(4).setEditor(editor);
    	addRowtwev.getCell(4).setValue(Boolean.FALSE);
    	addRowtwev.getCell(5).setValue("��");
//    	addRowtwev.getCell(6).setValue("XX");
    	addRowtwev.getCell(6).setEditor(editor);
    	addRowtwev.getCell(6).setValue(Boolean.FALSE);
    	addRowtwev.getCell(7).setValue("��");
    	
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 1, 11, 3);
      	mergeManager.mergeBlock(6, 0, 12, 0);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("�ɱ���");
    	addRowthirt.getCell(1).setValue("������");
    	addRowthirt.getCell(3).setValue("�����");
    	addRowthirt.getCell(10).setValue("����");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(12, 4, 12, 9);
     	mergeManager.mergeBlock(6, 0, 12, 0);

     	
    	IRow addRowfout= this.kDTable1.addRow();
    	IRow addRowfift= this.kDTable1.addRow();
    	mergeManager.mergeBlock(13, 1, 14, 11);
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("��˾��һ������:");
    	addRowsist.getCell(6).setValue("��һ������ǩ�֣�");
    	addRowsist.getCell(10).setValue("���ڣ�");
    	mergeManager.mergeBlock(15, 6, 15, 7);
    	mergeManager.mergeBlock(15, 8, 15, 9);
    	mergeManager.mergeBlock(15, 1, 15, 5);
    	mergeManager.mergeBlock(13, 0, 15, 0);

    	IRow addRowsevent= this.kDTable1.addRow();

    	IRow addRoweighteen= this.kDTable1.addRow();
    	mergeManager.mergeBlock(16, 1, 17, 11);
    	IRow addRow19= this.kDTable1.addRow();
    	addRow19.getCell(0).setValue("���й�˾������ܲ���һ������");
    	addRow19.getCell(6).setValue("��һ������ǩ�֣�");
    	addRow19.getCell(10).setValue("���ڣ�");
    	mergeManager.mergeBlock(18, 6, 18, 7);
    	mergeManager.mergeBlock(18, 8, 18, 9);
    	mergeManager.mergeBlock(18, 1, 18, 5);
    	mergeManager.mergeBlock(16, 0, 18, 0);
    	
    	IRow addRow20= this.kDTable1.addRow();
    	addRow20.getCell(1).setValue("���̹�������");
    	mergeManager.mergeBlock(19, 2, 19, 11);
    	IRow addRow21= this.kDTable1.addRow();
    	addRow21.getCell(1).setValue("Ӫ����������");
    	mergeManager.mergeBlock(20, 2, 20, 11);
    	IRow addRow22= this.kDTable1.addRow();
    	addRow22.getCell(1).setValue("��ҵ��������");
    	mergeManager.mergeBlock(21, 2, 21, 11);
    	IRow addRow23= this.kDTable1.addRow();
    	addRow23.getCell(1).setValue("�ɱ���������");
    	mergeManager.mergeBlock(22, 2, 22, 11);
    	IRow addRow24= this.kDTable1.addRow();
    	addRow24.getCell(1).setValue("��Ӫ��������");
    	mergeManager.mergeBlock(23, 2, 23, 11);
    	IRow addRow25= this.kDTable1.addRow();
    	addRow25.getCell(0).setValue("������");
    	addRow25.getCell(1).setValue("��ƹ�������");
    	mergeManager.mergeBlock(24, 2, 24, 11);
    	mergeManager.mergeBlock(19, 0, 24, 0);
    	
    	IRow addRow26= this.kDTable1.addRow();
    	addRow26.getCell(1).setValue("���Ӫ�����ܲ�");
    	mergeManager.mergeBlock(25, 2, 25, 11);
    	IRow addRow27= this.kDTable1.addRow();
    	addRow27.getCell(1).setValue("���̳ɱ����ܲ�");
    	mergeManager.mergeBlock(26, 2, 26, 11);
    	IRow addRow28= this.kDTable1.addRow();
    	addRow28.getCell(1).setValue("��Ӫ�̹ܸ��ܲ�");
    	mergeManager.mergeBlock(27, 2, 27, 11);
    	IRow addRow29= this.kDTable1.addRow();
    	addRow29.getCell(1).setValue("ִ�и��ܲ�");
    	mergeManager.mergeBlock(28, 2, 28, 11);
    	IRow addRow30= this.kDTable1.addRow();
    	addRow30.getCell(0).setValue("������");
    	addRow30.getCell(1).setValue("�ܲ�");
    	mergeManager.mergeBlock(29, 2, 29, 11);
    	mergeManager.mergeBlock(25, 0, 29, 0);
    	IRow addRow31= this.kDTable1.addRow();
    	addRow31.getCell(0).setValue("��   ע");
    	mergeManager.mergeBlock(30, 1, 30, 11);
    	
    	

    	this.kDTable1.getColumn(0).setWidth(105);
    	this.kDTable1.getColumn(1).setWidth(95);
    	this.kDTable1.getColumn(2).setWidth(80);
    	this.kDTable1.getColumn(3).setWidth(70);
    	this.kDTable1.getColumn(4).setWidth(50);
    	this.kDTable1.getColumn(5).setWidth(40);
    	this.kDTable1.getColumn(6).setWidth(75);
    	this.kDTable1.getColumn(7).setWidth(40);
    	this.kDTable1.getColumn(8).setWidth(50);
    	this.kDTable1.getColumn(9).setWidth(60);
    	this.kDTable1.getColumn(10).setWidth(50);
    	this.kDTable1.getColumn(11).setWidth(75);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = "q7emQGR4RXO5s86vNyJxJXARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FCurProjectName ��Ŀ����1 ,ChangeAB.FNumber ������2 , ChangeAB.Freadesc ���÷�Χ ,BaseU.Fname_l2 ������� ,ChangeAB.CFPutForwardTime ���ʱ��5,");
    	sb.append(" FTotalCost ������ù���,ChangeAE.FIsBack �������,ChangeAB.FTotalCost ���㹤�̷������ܼ�8,ChangeAB.CFreworkVisa ���㹤�̷ѽ��,ChangeAB.CFcontractAmpro ռ��ͬ�۱���10,");
    	sb.append(" ChangeAB.CFtotalChangeAmount �ۼƱ��ռ��ͬ��");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB ");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry ChangeAE on ChangeAB.fid=ChangeAE.FParentID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	while(rowset.next()){
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
    		this.kDTable1.getCell(0, 7).setValue(rowset.getString(2));
//    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(4));
    		this.kDTable1.getCell(1, 7).setValue(rowset.getString(4));
    		this.kDTable1.getCell(7, 3).setValue(rowset.getString(8));
    		this.kDTable1.getCell(9, 4).setValue(rowset.getString(10));
    	}
    	
    	
    	//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(5, 2).setValue(apporveResultForMap.get("��Ʋ�������;�����"));
    	this.kDTable1.getCell(5, 4).setValue(apporveResultForMap.get("��Ʋ������;�����"));
    	this.kDTable1.getCell(5, 11).setValue(apporveResultForMap.get("����;�������"));
    	this.kDTable1.getCell(12, 2).setValue(apporveResultForMap.get("��Լ�������ˣ������"));
    	this.kDTable1.getCell(12, 4).setValue(apporveResultForMap.get("��Լ������ˣ������"));
    	this.kDTable1.getCell(12, 11).setValue(apporveResultForMap.get("��Լ�����ڣ��������"));
    	this.kDTable1.getCell(13, 1).setValue(apporveResultForMap.get("��˾��һ�����ˣ����"));
    	this.kDTable1.getCell(14, 3).setValue(apporveResultForMap.get("��˾��һ�����ˣ�ǩ��"));
    	this.kDTable1.getCell(14, 11).setValue(apporveResultForMap.get("��˾��һ�����ˣ�����"));
    	this.kDTable1.getCell(15, 1).setValue(apporveResultForMap.get("���й�˾������ܲ���һ�����ˣ����"));
    	this.kDTable1.getCell(16, 3).setValue(apporveResultForMap.get("���й�˾������ܲ���һ�����ˣ�ǩ��"));
    	this.kDTable1.getCell(16, 11).setValue(apporveResultForMap.get("���й�˾������ܲ���һ�����ˣ�����"));
    	
    	
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