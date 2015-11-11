/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.web.components.util.EditDataGridHelper;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingContractEditUI;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * Ŀ��ɱ�������������������
 * output class name
 */
public class TargetcostApproveUI extends AbstractTargetcostApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetcostApproveUI.class);
    /**
     * output class constructor
     */
    public TargetcostApproveUI() throws Exception
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
    	addRow.getCell(0).setValue("��˾����");
//    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 9);
    	
    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("��Ŀ����");
//    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 9);
    	
    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("�����������");
//    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 1, 2, 9);
    	mergeManager.mergeBlock(2, 0, 4, 0);
    	mergeManager.mergeBlock(2, 1, 4, 9);
    	
    	
    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("�����������");
//    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 1, 3, 9);
    	mergeManager.mergeBlock(2, 0, 4, 0);
    	mergeManager.mergeBlock(2, 1, 4, 9);
    	
    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("�����������");
//    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 9);
    	mergeManager.mergeBlock(2, 0, 4, 0);
    	mergeManager.mergeBlock(2, 1, 4, 9);
    	
    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("�ɱ�����");
//    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 1, 5, 9);
    	
    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(8).setValue("��ǩ");
    	addRowseven.getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
//    	addRowseven.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(6, 0, 6, 9);

    	
    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("�걨��λ");
//    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("Ӫ����������");
//    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(7, 1, 7, 2);
       	mergeManager.mergeBlock(7, 3, 7, 9);
    	mergeManager.mergeBlock(7, 0, 10, 0);

    	
    	
    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("�걨��λ");
//    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("Ӫ����");
//    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(5).setValue("�ɱ�����(��)");
//    	addRownine.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 4);
    	mergeManager.mergeBlock(8, 6, 8, 9);
    	mergeManager.mergeBlock(7, 0, 10, 0);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("�걨��λ");
//    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("��Ʋ�");
//    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(5).setValue("����");
//    	addRowten.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 1, 9, 2);
    	mergeManager.mergeBlock(9, 3, 9, 4);
    	mergeManager.mergeBlock(9, 6, 9, 9);
    	mergeManager.mergeBlock(7, 0, 10, 0);
    	
    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("�걨��λ");
//    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("��Ŀ��˾��һ������");
//    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(5).setValue("���й�˾/�����ܲ���һ������");
//    	addRowelev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(10, 1, 10, 2);
    	mergeManager.mergeBlock(10, 3, 10, 4);
    	mergeManager.mergeBlock(10, 6, 10, 9);
    	mergeManager.mergeBlock(7, 0, 10, 0);

    	

    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("�����ܲ�");
//    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(1).setValue("Ӫ����������");
//    	addRowtwev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(5).setValue("�ɱ���������");
//    	addRowtwev.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 1, 11, 2);
    	mergeManager.mergeBlock(11, 3, 11, 4);
    	mergeManager.mergeBlock(11, 5, 11, 6);
    	mergeManager.mergeBlock(11, 7, 11, 9);
    	mergeManager.mergeBlock(11, 0, 15, 0);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("�����ܲ�");
//    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(1).setValue("��ƹ�������");
//    	addRowthirt.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthirt.getCell(5).setValue("�����������");
//    	addRowthirt.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(12, 1, 12, 2);
    	mergeManager.mergeBlock(12, 3, 12, 4);
    	mergeManager.mergeBlock(12, 5, 12, 6);
    	mergeManager.mergeBlock(12, 7, 12, 9);
    	mergeManager.mergeBlock(11, 0, 15, 0);
  	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("�����ܲ�");
//    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfout.getCell(2).setValue("���̳ɱ����ܲ�");
//    	addRowfout.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(13, 1, 13, 2);
    	mergeManager.mergeBlock(13, 3, 13, 9);
    	mergeManager.mergeBlock(11, 0, 15, 0);
    	
    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("�����ܲ�");
//    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfift.getCell(2).setValue("ִ�и��ܲ�");
//    	addRowfift.getCell(2).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(14, 1, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 9);
    	mergeManager.mergeBlock(11, 0, 15, 0);

    	
    	//��ʮ����
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("�����ܲ�");
//    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsist.getCell(1).setValue("�ܲ�;");
//    	addRowsist.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(15, 1, 15, 2);
    	mergeManager.mergeBlock(15, 3, 15, 9);
    	mergeManager.mergeBlock(11, 0, 15, 0);


    	
    	//��ʮ����
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("��ע");
//    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(16, 1, 16, 9);
    	
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
    	
    	//�п�
    	int i;
    	for(i=0;i<kDTable1.getRowCount()-1;i++)
    	{
    		kDTable1.getRow(i).setHeight(30);
    	}
    	kDTable1.getRow(kDTable1.getRowCount()-1).setHeight(150);
    	//�п�
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(80);
    	}
//    	wangsz
    	
//    	String billId = "7v36HV4ES6+HQ7TfX3B27QTHsvM=";
    	String billId = editData.getId()!=null?editData.getId().toString():"7v36HV4ES6+HQ7TfX3B27QTHsvM=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select distinct fdc.Fname_l2,org.Fname_l2,try.FChangeReason,try.FAdjustAmt,try.FDescription from T_AIM_AimAimCostAdjust atm ");
    	sb.append(" left join T_FDC_CurProject fdc on fdc.fid =atm.FCurProjectID ");
    	sb.append(" left join T_ORG_BaseUnit org on org.fid = fdc.FFullOrgUnit  ");
    	sb.append(" left join T_AIM_AimAimCostAdjustEntry try on try.FParentID = atm.fid ");
    	sb.append(" where atm.fid = '").append(billId).append("'");
    	
    	
    	//sqlȡֵ��������
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	StringBuffer yuanYin = new StringBuffer();
    	BigDecimal sum = BigDecimal.ZERO;
    	StringBuffer beiZu = new StringBuffer();
    	while(rowset.next()){
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(2));
    		yuanYin.append(rowset.getString(3)+"\n");
    		sum= sum.add(rowset.getBigDecimal(4));
    		beiZu.append(rowset.getString(5)+"\n");
    	}  	
    	this.kDTable1.getCell(2, 1).setValue(yuanYin.toString());
    	this.kDTable1.getCell(2, 1).getStyleAttributes().setWrapText(true);
    	this.kDTable1.getCell(5, 1).setValue(sum);
    	this.kDTable1.getCell(16, 1).setValue(beiZu.toString());
    	this.kDTable1.getCell(16, 1).getStyleAttributes().setWrapText(true);
    	
    	

 		//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(7, 3).setValue(apporveResultForMap.get("Ӫ����������"));
    	this.kDTable1.getCell(8, 3).setValue(apporveResultForMap.get("Ӫ����"));
    	this.kDTable1.getCell(8, 6).setValue(apporveResultForMap.get("�ɱ�����"));
    	this.kDTable1.getCell(9, 3).setValue(apporveResultForMap.get("��Ʋ�"));
    	this.kDTable1.getCell(9, 6).setValue(apporveResultForMap.get("����"));
    	this.kDTable1.getCell(10, 3).setValue(apporveResultForMap.get("��Ŀ��˾��һ������"));
    	this.kDTable1.getCell(10, 6).setValue(apporveResultForMap.get("�����ܲ���һ������"));
    	this.kDTable1.getCell(11, 3).setValue(apporveResultForMap.get("Ӫ����������"));
    	this.kDTable1.getCell(11, 7).setValue(apporveResultForMap.get("�ɱ���������"));
    	this.kDTable1.getCell(12, 3).setValue(apporveResultForMap.get("��ƹ�������"));
    	this.kDTable1.getCell(12, 7).setValue(apporveResultForMap.get("�����������"));
    	this.kDTable1.getCell(13, 3).setValue(apporveResultForMap.get("���̳ɱ����ܲ�"));
    	this.kDTable1.getCell(14, 3).setValue(apporveResultForMap.get("ִ�и��ܲ�"));
    	this.kDTable1.getCell(15, 3).setValue(apporveResultForMap.get("�ܲ�"));
    	
    	

    } 
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	super.kDTable1_tableClicked(e);
    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
//    	UIContext uiContext = new UIContext(this);
//		IUIWindow uiWindow = null;
//		uiContext.put("ID", "7v36HV4ES6+HQ7TfX3B27QTHsvM=");
////		uiContext.put("ID", "++e5/LBdTYWVTKE8baOBXA1t0fQ=");
//		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TargetcostApproveUI.class.getName(), uiContext, null, OprtState.VIEW);
////		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ContractBillEditUI.class.getName(), uiContext, null, OprtState.VIEW);
//		uiWindow.show();
    }

	protected IObjectValue createNewDetailData(KDTable kdtable) {
		return null;
	}

	protected KDTable getDetailTable() {
		return kDTable1;
	}


	protected ICoreBase getBizInterface() throws Exception {
		return AimAimCostAdjustFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new AimAimCostAdjustInfo();
	}

}