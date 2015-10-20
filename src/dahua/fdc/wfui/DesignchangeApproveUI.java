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
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;

/**
 * ��Ʊ�����뵥��������
 * output class name
 */
public class DesignchangeApproveUI extends AbstractDesignchangeApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(DesignchangeApproveUI.class);
    
    /**
     * output class constructor
     */
    public DesignchangeApproveUI() throws Exception
    {
        super();
    }

    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	initUI();
    }
   
    private void initUI() throws BOSException, SQLException{
    	this.kDTable1.addColumns(13);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	//��һ��
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("��������:");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);

    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 12);

    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("���÷�Χ:");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor); 	
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 12);
    	
    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("�����:");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(1).setValue("XX");
    	addRowthree.getCell(2).setValue("��Ʋ�");
    	addRowthree.getCell(3).setValue("XX");
    	addRowthree.getCell(4).setValue("���̲�");
    	addRowthree.getCell(5).setValue("XX");
    	addRowthree.getCell(6).setValue("ǰ�����ײ�");
    	addRowthree.getCell(7).setValue("XX");
    	addRowthree.getCell(8).setValue("���۲�");
    	addRowthree.getCell(9).setValue("XX");
    	addRowthree.getCell(10).setValue("����");    	
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 0, 3, 0);
    	mergeManager.mergeBlock(2, 10,2, 12);

    	
    	
    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("�����");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(1).setValue("������:��Ա");
    	addRowfour.getCell(4).setValue("�����:��Ա:");   
    	addRowfour.getCell(7).setValue("���ʱ��:");
    	addRowfour.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 1, 3, 3);
    	mergeManager.mergeBlock(3, 4, 3, 6);
    	mergeManager.mergeBlock(3, 7, 3, 8);
    	mergeManager.mergeBlock(3, 9, 3, 12);
    	mergeManager.mergeBlock(2, 0, 3, 0);

    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("���ԭ�򼰽��鷽��");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 12);

    	
    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("�������");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("����");
    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(5).setValue("Ԥ������Ӱ��");
    	addRowsix.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(11).setValue("������ǩ��");
    	addRowsix.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(12).setValue("����");
    	addRowsix.getCell(12).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 2, 5, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);
    	
    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("�������");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("��Ʋ�");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(2).setValue("��ƷƷ��");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(3).setValue("XX");
    	addRowseven.getCell(4).setValue("���");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(5).setValue("XX");
    	addRowseven.getCell(6).setValue("����");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(7).setValue("XX");
    	addRowseven.getCell(8).setValue("��Ӱ��");
    	addRowseven.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(6, 8, 6, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);


    	
    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("�������");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("���̲�:");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(2).setValue("����");
    	addRoweight.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(3).setValue("XX");
    	addRoweight.getCell(4).setValue("����");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(5).setValue("�Ƿ񷵹�");
    	addRoweight.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(7).setValue("XX");
    	addRoweight.getCell(8).setValue("�践��");
    	addRoweight.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(9).setValue("XX");
    	addRoweight.getCell(10).setValue("���践��");
    	addRoweight.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(7, 5, 7, 6);
    	mergeManager.mergeBlock(5, 0, 10, 0);
    	
    	
    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("�������");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("���۲�:");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(2).setValue("����");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(3).setValue("XX");
    	addRownine.getCell(4).setValue("����");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(5).setValue("XX");
    	addRownine.getCell(6).setValue("����");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(7).setValue("XX");
    	addRownine.getCell(8).setValue("��Ӱ��");
    	addRownine.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(8, 8, 8, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("�������");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("ǰ�����ײ�");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 2, 9, 10);
    	mergeManager.mergeBlock(5, 0, 10, 0);

    	
    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("�������");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("��Լ��");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(2).setValue("�ɱ�");
    	addRowelev.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(3).setValue("XX");
    	addRowelev.getCell(4).setValue("����");
    	addRowelev.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("XX");
    	addRowelev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(6).setValue("����");
    	addRowelev.getCell(6).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(7).setValue("XX");
    	addRowelev.getCell(8).setValue("��");
    	addRowelev.getCell(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(9).setValue("���ù��ۣ�");
    	addRowelev.getCell(10).setValue("��Ԫ");
    	addRoweight.getCell(10).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 0, 10, 0);

    	

    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("��˾��һ������:");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 1, 11, 12);
    	mergeManager.mergeBlock(11, 0, 12, 0);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("��˾��һ������:");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("��һ������ǩ��:");
    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(11).setValue("����");
    	addRowthirt.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(12, 1, 12, 2);
    	mergeManager.mergeBlock(12, 3, 12, 10);
    	mergeManager.mergeBlock(11, 0, 12, 0);
  	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("���й�˾������ܲ���һ������");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(13, 1, 13, 12);
    	mergeManager.mergeBlock(13, 0, 14, 0);
    	
    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("���й�˾������ܲ���һ������");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(1).setValue("��һ������ǩ��");
    	addRowfift.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(11).setValue("����");
    	addRowfift.getCell(11).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 10);
    	mergeManager.mergeBlock(13, 0, 14, 0);
    	
    	
    	
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