/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
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
//    	for (int i = 0; i < kDTable1.getRowCount(); i++) {
//        	KDTableHelper.autoFitRowHeight(this.kDTable1, i);
//		}
    }


    private void initUI() throws BOSException, SQLException{
    	this.kDTable1.addColumns(12);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();

    	//��һ��
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("��������");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(10).setValue("���뵥���");
    	addRow.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 9);



    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("���÷�Χ");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwo.getCell(10).setValue("���ʱ��");
    	addRowtwo.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 9);

    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("�����");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(1).setValue("XX");
    	addRowthree.getCell(2).setValue("��Ʋ�");
    	addRowthree.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(3).setValue("XX");
    	addRowthree.getCell(4).setValue("���̲�");
    	addRowthree.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(5).setValue("XX");
    	addRowthree.getCell(6).setValue("ǰ�����ײ�");
    	addRowthree.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(7).setValue("XX");
    	addRowthree.getCell(8).setValue("���۲�");
    	addRowthree.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(9).setValue("XX");
    	addRowthree.getCell(10).setValue("����");
    	addRowthree.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 10, 2, 11);

    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("��Ʋ�");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(1).setValue("����ͼֽ");
    	addRowfour.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(3).setValue("XX");
    	addRowfour.getCell(4).setValue("��");
    	addRowfour.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(6).setValue("XX");
    	addRowfour.getCell(7).setValue("��");
    	addRowfour.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);    	
    	addRowfour.getCell(10).setValue("��ͼ���:");
    	addRowfour.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 1, 3, 2);
    	mergeManager.mergeBlock(3, 4, 3, 5);
    	mergeManager.mergeBlock(3, 7, 3, 9);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("��Ʋ�");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfive.getCell(1).setValue("���ԭ��");
    	addRowfive.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 2);
    	mergeManager.mergeBlock(4, 3, 4, 11);
    	mergeManager.mergeBlock(3, 0, 5, 0);


    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("��Ʋ�");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("������");
    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(3).setValue("�����");
    	addRowsix.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(10).setValue("����");
    	addRowsix.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 4, 5, 9);
    	mergeManager.mergeBlock(3, 0, 5, 0);

    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("��Լ��");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("������ù���:");
    	addRowseven.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(2).setValue("XX");
    	addRowseven.getCell(3).setValue("����");
    	addRowseven.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(4).setValue("XX");
    	addRowseven.getCell(5).setValue("����");
    	addRowseven.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(6).setValue("XX");
    	addRowseven.getCell(7).setValue("��");
    	addRowseven.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(8).setValue("XX");
    	addRowseven.getCell(9).setValue("�践��");
    	addRowseven.getCell(9).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(10).setValue("XX");
    	addRowseven.getCell(11).setValue("���践��");
    	addRowseven.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("��Լ��");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("���㹤�̷������ܼۣ�");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(5).setValue("��Ԫ");
    	addRoweight.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(7, 1, 7, 2);
    	mergeManager.mergeBlock(7, 3, 7, 4);
    	mergeManager.mergeBlock(7, 5, 7, 6);
    	mergeManager.mergeBlock(7, 7, 7, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("��Լ��");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("���з�����ɵ�ǩ֤���ù��㣺");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	KDTableHelper.autoFitRowHeight(this.kDTable1, 8);
    	addRownine.getCell(6).setValue("��Ԫ");
    	addRownine.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 5, 8, 6);
    	mergeManager.mergeBlock(8, 7, 8, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("��Լ��");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("���α��ռ��ͬ�۵İٷֱȣ�%����");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(5).setValue("%");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 1, 9, 3);
    	mergeManager.mergeBlock(9, 5, 9, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);

    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("��Լ��");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("��ֹĿǰ����ۼƺ�ͬ�ܼ۵İٷֱȣ�%����");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("%");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(10, 1, 10, 3);
    	mergeManager.mergeBlock(10, 5, 10, 11);
      	mergeManager.mergeBlock(6, 0, 12, 0);


    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("��Լ��");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(1).setValue("������ͬ����Ƿ񳬳�Ŀ��ɱ��޶");
    	addRowtwev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(4).setValue("XX");
    	addRowtwev.getCell(5).setValue("��");
    	addRowtwev.getCell(6).setValue("XX");
    	addRowtwev.getCell(7).setValue("��");
    	
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 1, 11, 3);
      	mergeManager.mergeBlock(6, 0, 12, 0);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("��Լ��");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("������");
    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(3).setValue("�����");
    	addRowthirt.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(10).setValue("����");
    	addRowthirt.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(12, 4, 12, 9);
     	mergeManager.mergeBlock(6, 0, 12, 0);

     	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("��˾��һ������:");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(1).setValue("����Ŀ��˾�����������");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(13, 1, 13, 10);
    	mergeManager.mergeBlock(13, 0, 14, 0);

    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("��˾��һ������:");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(1).setValue("��һ������ǩ��:");
    	addRowfift.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(10).setValue("����");
    	addRowfift.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 9);
    	mergeManager.mergeBlock(13, 0, 14, 0);

    	//��ʮ����
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("���й�˾������ܲ���һ������");
    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(1).setValue("�����й�˾������ܲ������������");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(15, 1, 15, 10);
    	mergeManager.mergeBlock(15, 0, 16, 0);

    	//��ʮ����
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("���й�˾������ܲ���һ������");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(1).setValue("��һ������ǩ��");
    	addRowsevent.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(10).setValue("����");
    	addRowsevent.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(16, 1, 16, 2);
    	mergeManager.mergeBlock(16, 3, 16, 9);
    	mergeManager.mergeBlock(15, 0, 16, 0);

    	//��ʮ����
    	IRow addRoweighteen= this.kDTable1.addRow();
    	addRoweighteen.getCell(0).setValue("��ע");
    	addRoweighteen.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(17, 1, 17, 10);

    	this.kDTable1.getColumn(0).setWidth(105);
    	this.kDTable1.getColumn(1).setWidth(85);
    	this.kDTable1.getColumn(2).setWidth(60);
    	this.kDTable1.getColumn(3).setWidth(100);
    	this.kDTable1.getColumn(4).setWidth(50);
    	this.kDTable1.getColumn(5).setWidth(40);
    	this.kDTable1.getColumn(6).setWidth(75);
    	this.kDTable1.getColumn(7).setWidth(40);
    	this.kDTable1.getColumn(8).setWidth(50);
    	this.kDTable1.getColumn(9).setWidth(60);
    	this.kDTable1.getColumn(10).setWidth(70);
    	this.kDTable1.getColumn(11).setWidth(75);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = "FrYcumfRTUq/iL87J1M2VXARYRc=";
//    	StringBuffer sb = new StringBuffer();
//    	sb.append("  select ChangeAB.FCurProjectName ,ChangeAB.FNumber ,ChangeAB.Fname ,BaseU.Fname_l2 ,");
//    	sb.append(" ChangeAB.Freadesc ,ChangeAB.CFQuality ,ChangeAB.CFTimeLi ,ChangeAB.CFSale ,CFCost ");
//    	sb.append("  from T_CON_ChangeAuditBill ChangeAB ");
//    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
//    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
//    	
//    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
//    	while(rowset.next()){
//    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(3));
//    	}
    	
    	
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