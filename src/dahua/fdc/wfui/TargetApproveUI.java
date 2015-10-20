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
 * Ŀ��ɱ�����������
 * output class name
 */
public class TargetApproveUI extends AbstractTargetApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetApproveUI.class);
    
    /**
     * output class constructor
     */
    public TargetApproveUI() throws Exception
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
    	addRow.getCell(0).setValue("�ɱ����:");
    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(1).setValue("Ӫҵ����:");
    	addRow.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(5).setValue("��Ŀ�ɱ�����");
    	addRow.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 2);
      	mergeManager.mergeBlock(0, 3, 0, 4);
      	mergeManager.mergeBlock(0, 5, 0, 6);
    	mergeManager.mergeBlock(0, 7, 0, 9);
    	
    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("��Ŀ����:");
    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 9);
    	
    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("���̵�ַ:");
    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 1, 2, 9);
    	
    	
    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("���赥λ:");
    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 1, 3, 9);

    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("���Ƶ�λ:");
    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 9);

    	
    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("���Ʋ���:");
    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("ǰ��(����)��:");
    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(5).setValue("��Ʋ�:");
    	addRowsix.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 1, 5, 2);
    	mergeManager.mergeBlock(5, 3, 5, 4);
    	mergeManager.mergeBlock(5, 5, 5, 6);
    	mergeManager.mergeBlock(5, 7, 5, 9);
    	mergeManager.mergeBlock(5, 0, 8, 0);
    	
    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("���Ʋ���:");
    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("Ӫ����:");
    	addRowseven.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(5).setValue("������:");
    	addRowseven.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(6, 1, 6, 2);
    	mergeManager.mergeBlock(6, 3, 6, 4);
    	mergeManager.mergeBlock(6, 5, 6, 6);
    	mergeManager.mergeBlock(6, 7, 6, 9);
    	mergeManager.mergeBlock(5, 0, 8, 0);



    	
    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("���Ʋ���:");
    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("���̲�:");
    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(5).setValue("�ɱ���:");
    	addRoweight.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(7, 1, 7, 2);
       	mergeManager.mergeBlock(7, 3, 7, 4);
       	mergeManager.mergeBlock(7, 5, 7, 6);
       	mergeManager.mergeBlock(7, 7, 7, 9);
       	mergeManager.mergeBlock(5, 0, 8, 0);

    	
    	
    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("���Ʋ���:");
    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("����:");
    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 5, 8, 6);
    	mergeManager.mergeBlock(8, 7, 8, 9);
    	mergeManager.mergeBlock(5, 0, 8, 0);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("�����:");
    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("��Ŀ��˾��һ������:");
    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(5).setValue("���й�˾/�����ܲ���һ������:");
    	addRowten.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 1, 9, 2);
    	mergeManager.mergeBlock(9, 3, 9, 4);
    	mergeManager.mergeBlock(9, 5, 9, 6);
    	mergeManager.mergeBlock(9, 7, 9, 9);

    	
    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("������˲���:");
    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("�ɱ���������:");
    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("��ƹ�������:");
    	addRowelev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(10, 1, 10, 2);
    	mergeManager.mergeBlock(10, 3, 10, 4);
    	mergeManager.mergeBlock(10, 5, 10, 6);
    	mergeManager.mergeBlock(10, 7, 10, 9);
    	mergeManager.mergeBlock(10, 0, 12, 0);

    	

    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("������˲���:");
    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(1).setValue("Ӫ����������:");
    	addRowtwev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(5).setValue("���̹�������:");
    	addRowtwev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 1, 11, 2);
    	mergeManager.mergeBlock(11, 3, 11, 4);
    	mergeManager.mergeBlock(11, 5, 11, 6);
    	mergeManager.mergeBlock(11, 7, 11, 9);
    	mergeManager.mergeBlock(10, 0, 12, 0);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("������˲���:");
    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("Ӫ�˹�������:");
    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(5).setValue("�����������:");
    	addRowthirt.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(12, 1, 12, 2);
    	mergeManager.mergeBlock(12, 3, 12, 4);
    	mergeManager.mergeBlock(12, 5, 12, 6);
    	mergeManager.mergeBlock(12, 7, 12, 9);
    	mergeManager.mergeBlock(10, 0, 12, 0);
  	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("����������:");
    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(2).setValue("���̳ɱ����ܲ�:");
    	addRowfout.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(13, 1, 13, 2);
    	mergeManager.mergeBlock(13, 3, 13, 9);
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	
    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("����������:");
    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(2).setValue("ִ�и��ܲ�:");
    	addRowfift.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 9);
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	

    	
    	//��ʮ����
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("����������:");
    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(1).setValue("�ܲ�:");
    	addRowsist.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(15, 1, 15, 2);
    	mergeManager.mergeBlock(15, 3, 15, 9);
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	


    	
    	//��ʮ����
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(5).setValue("(ҵ������)");
    	addRowsevent.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsevent.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(16, 0, 16, 9);
    	
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