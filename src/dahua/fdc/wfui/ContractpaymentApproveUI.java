/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.*;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * ��ͬ�������뵥
 * output class name
 */
public class ContractpaymentApproveUI extends AbstractContractpaymentApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractpaymentApproveUI.class);
    
    /**
     * output class constructor
     */
    public ContractpaymentApproveUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initUI();
    }

    
    private void initUI() throws BOSException, SQLException{
    	this.kDTable1.addColumns(10);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	//��һ��
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("���벿��");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(5).setValue("������");
    	addRow.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 4);
    	mergeManager.mergeBlock(0, 6, 0, 9);
    	
    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("�տλ");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 9);
    	
    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("��Ŀ����");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 1, 2, 9);
    	
    	
    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("��ͬ����");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 1, 3, 9);

    	
    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("��ͬ���");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 9);

    	
    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("��ͬ�ܼ�(��д)");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(6).setValue("��");
    	addRowsix.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 1, 5, 5);
    	mergeManager.mergeBlock(5, 7, 5, 9);
    	
    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("��ͬ����(��д)");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(6).setValue("��");
    	addRowseven.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(6, 1, 6, 5);
    	mergeManager.mergeBlock(6, 7, 6, 9);
    	
    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("��ͬ�����ܼ�(��д)");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(6).setValue("��");
    	addRoweight.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(7, 1, 7, 5);
    	mergeManager.mergeBlock(7, 7, 7, 9);
    	
    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("���κ˶�����(��д)");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(6).setValue("��");
    	addRownine.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(8, 1, 8, 5);
    	mergeManager.mergeBlock(8, 7, 8, 9);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("�ۼƺ˶�����(��д)");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(6).setValue("��");
    	addRowten.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 1, 9, 5);
    	mergeManager.mergeBlock(9, 7, 9, 9);
    	
    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("��ǰ���ۼ�֧��(��д)");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(6).setValue("��");
    	addRowelev.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(10, 1, 10, 5);
    	mergeManager.mergeBlock(10, 7, 10, 9);

    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("��������֧��(��д)");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(6).setValue("��");
    	addRowtwev.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 1, 11, 5);
    	mergeManager.mergeBlock(11, 7, 11, 9);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("�ۼ�֧��(��д)");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(6).setValue("��");
    	addRowthirt.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(12, 1, 12, 5);
    	mergeManager.mergeBlock(12, 7, 12, 9);

    	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("��ͬ�ܼ�(�����)���(��д)");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(6).setValue("��");
    	addRowfout.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(13, 1, 13, 5);
    	mergeManager.mergeBlock(13, 7, 13, 9);
    	
    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("ҵ�񾭰���");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(3).setValue("���첿�Ÿ�����");
    	addRowfift.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 4);
    	mergeManager.mergeBlock(14, 5, 14, 9);
    	
    	//��ʮ����
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("��ز�������");
    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(3).setValue("�����Ÿ�����;");
    	addRowsist.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(15, 1, 15, 2);
    	mergeManager.mergeBlock(15, 3, 15, 4);
    	mergeManager.mergeBlock(15, 5, 15, 9);

    	
    	//��ʮ����
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("�����Ÿ�����");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getCell(3).setValue("��λ��(��)�ܸ�����");
    	addRowsevent.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(16, 1, 16, 2);
    	mergeManager.mergeBlock(16, 3, 16, 4);
    	mergeManager.mergeBlock(16, 5, 16, 9);
    	
    	//��ʮ����
    	IRow addRoweighteen= this.kDTable1.addRow();
    	addRoweighteen.getCell(0).setValue("��λ������");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweighteen.getCell(3).setValue("���Ź������ĸ�����");
    	addRowsevent.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(17, 1, 17, 2);
    	mergeManager.mergeBlock(17, 3, 17, 4);
    	mergeManager.mergeBlock(17, 5, 17, 9);
    	
    	//��ʮ����
    	IRow addRowninteen= this.kDTable1.addRow();
    	addRowninteen.getCell(0).setValue("�ֹܸ��ܲ�");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowninteen.getCell(3).setValue("ִ�и��ܲ�");
    	addRowsevent.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(18, 1, 18, 2);
    	mergeManager.mergeBlock(18, 3, 18, 4);
    	mergeManager.mergeBlock(18, 5, 18, 9);
    	
    	//�ڶ�ʮ��
    	IRow addRowtwenty= this.kDTable1.addRow();
    	addRowtwenty.getCell(0).setValue("�����ܲ�");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(19, 1, 19, 9);
    	
    	//�ڶ�ʮһ��
    	IRow addRowtwentyO= this.kDTable1.addRow();
    	addRowtwentyO.getCell(0).setValue("��ע");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(20, 1, 20, 9);
    	
    	//�ڶ�ʮ����
    	IRow addRowtwentyT= this.kDTable1.addRow();
    	addRowtwentyT.getCell(0).setValue("��׼���");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(21, 1, 21, 9);
    	
    	//�ڶ�ʮ����
    	IRow addRowtwentyTh= this.kDTable1.addRow();
    	addRowtwentyTh.getCell(0).setValue("");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwentyTh.getCell(3).setValue("���벿����׼��ǩ��");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(22, 1, 22, 2);
    	mergeManager.mergeBlock(22, 3, 22, 4);   	
    	mergeManager.mergeBlock(22, 5, 22, 9);
    	
    	//�ڶ�ʮ����
    	IRow addRowtwentyF= this.kDTable1.addRow();
    	addRowtwentyF.getCell(0).setValue("");
    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwentyF.getCell(3).setValue("����");
    	addRowsevent.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(23, 1, 23, 2);
    	mergeManager.mergeBlock(23, 3, 23, 4);
    	mergeManager.mergeBlock(23, 5, 23, 9);
    	
    	
    	
    	
  	
    	this.kDTable1.getColumn(0).setWidth(100);
    	this.kDTable1.getColumn(1).setWidth(120);
    	this.kDTable1.getColumn(2).setWidth(50);
    	this.kDTable1.getColumn(3).setWidth(75);
    	this.kDTable1.getColumn(4).setWidth(80);
    	this.kDTable1.getColumn(5).setWidth(75);
    	this.kDTable1.getColumn(6).setWidth(75);
    	this.kDTable1.getColumn(7).setWidth(50);
    	this.kDTable1.getColumn(8).setWidth(50);
    	this.kDTable1.getColumn(9).setWidth(50);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    }

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

}