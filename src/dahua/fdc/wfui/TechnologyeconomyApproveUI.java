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
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

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
    	
    	this.kDTable1.addColumns(5);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	IRow addRow1 = this.kDTable1.addRow();
    	addRow1.getCell(0).setValue("��������");
    	addRow1.getCell(2).setValue("��ͬ���");
    	mergeManager.mergeBlock(0, 3, 0, 4);
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("ǩ֤����");
    	mergeManager.mergeBlock(1, 1, 1, 4);
    	
    	
    	IRow addRow3 = this.kDTable1.addRow();
    	addRow3.getCell(0).setValue("�а���λ");
    	addRow3.getCell(2).setValue("��Ŀ���ʱ��");
    	mergeManager.mergeBlock(2, 3, 2, 4);
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("ʩ��������");
    	addRow4.getCell(2).setValue("������");
    	mergeManager.mergeBlock(3, 3, 3, 4);
    	
    	IRow addRow5 = this.kDTable1.addRow();
    	IRow addRow6 = this.kDTable1.addRow();
    	IRow addRow7 = this.kDTable1.addRow();
    	
    	IRow addRow8 = this.kDTable1.addRow();
    	addRow8.getCell(0).setValue("ʵ�ʹ�������������");
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 4);
    	
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(0).setValue("ǩ֤�걨���ã�");
    	mergeManager.mergeBlock(8, 1, 8, 4);
    	
    	IRow addRow10 = this.kDTable1.addRow();
    	addRow10.getCell(1).setValue("�����ˣ�");
    	addRow10.getCell(3).setValue("�����ˣ�");
    	mergeManager.mergeBlock(8, 0, 9, 0);
    	
    	IRow addRow11 = this.kDTable1.addRow();
    	addRow11.getCell(0).setValue("���̲������");
    	IRow addRow12 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(10, 1, 11, 4);
    	IRow addRow13 = this.kDTable1.addRow();
    	addRow13.getCell(1).setValue("���̲������ˣ�");
    	addRow13.getCell(3).setValue("���̲������ˣ�");
    	mergeManager.mergeBlock(10, 0, 12, 0);
    	
    	IRow addRow14 = this.kDTable1.addRow();
    	addRow14.getCell(0).setValue("�ɱ��������");
    	IRow addRow15 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(13, 1, 14, 4);
    	IRow addRow16 = this.kDTable1.addRow();
    	addRow16.getCell(1).setValue("�ɱ��������ˣ�");
    	addRow16.getCell(3).setValue("�ɱ��������ˣ�");
    	mergeManager.mergeBlock(13, 0, 15, 0);
    	
    	IRow addRow17 = this.kDTable1.addRow();
    	addRow17.getCell(0).setValue("��Ŀ��˾��һ�����ˣ�");
    	IRow addRow18 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(16, 0, 17, 0);
    	mergeManager.mergeBlock(16, 1, 17, 4);
    	
    	IRow addRow19 = this.kDTable1.addRow();
    	addRow19.getCell(0).setValue("�ɱ��������ģ�");
    	mergeManager.mergeBlock(18, 1, 18, 4);
    	IRow addRow20 = this.kDTable1.addRow();
    	addRow20.getCell(0).setValue("���̳ɱ����ܲã�");
    	mergeManager.mergeBlock(19, 1, 19, 4);
    	IRow addRow21 = this.kDTable1.addRow();
    	addRow21.getCell(0).setValue("����ǩ��");
    	mergeManager.mergeBlock(20, 1, 20, 2);
    	addRow21.getCell(3).setValue("����");
    	
    	//�п�
    	int i;
    	for(i=0;i<kDTable1.getRowCount();i++)
    	{
    		kDTable1.getRow(i).setHeight(30);
    	}
    	//�п�
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(157);
    	}
    	
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	

    	String billId = "IgM7C7CKRhi8GKmLgp61dHARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FNumber ���ݱ��1,contractB.fnumber ��ͬ���2,ChangeAB.Freadesc ǩ֤����3,");
    	sb.append(" ChangeAB.CFcompDate ��Ŀ���ʱ��4,ChangeAB.CFconstructionHead ʩ��������5,");
    	sb.append(" ChangeAB.CFBIMUDF0052ID ������6,ChangeAB.CFworkNote ʵ�ʹ�����������7,ject.fName_l2 ��������");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB ");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry ChangeAE on ChangeAB.fid=ChangeAE.FParentID");
    	sb.append(" left join T_CON_ChangeSupplierEntry ChangeSE on ChangeAB.fid=ChangeSE.FParentID");
    	sb.append(" left join T_CON_ContractBill contractB on contractB.fid=ChangeSE.FContractBillID");
    	sb.append(" left join T_BD_Supplier supplier on supplier.fid=ChangeAB.FConstrUnitID");
    	sb.append(" left join T_FDC_CurProject ject on ject.fid = ChangeAB.FCurProjectID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	while(rowset.next()){
    		this.kDTextField1.setText(rowset.getString(1));
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(8));
    		this.kDTable1.getCell(0, 3).setValue(rowset.getString(2));
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(3));
//    		this.kDTable1.getCell(2, 1).setValue(rowset.getString(4));
    		this.kDTable1.getCell(2, 3).setValue(rowset.getString(4));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(5));
    		this.kDTable1.getCell(3, 3).setValue(rowset.getString(6));
    		this.kDTable1.getCell(4, 1).setValue(rowset.getString(7));
    		this.kDTable1.getCell(9, 2).setValue(rowset.getString(6));
    		this.kDTable1.getCell(9, 4).setValue(rowset.getString(5));
    	}
    	
    	//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	this.kDTable1.getCell(9, 0).setValue(apporveResultForMap.get("��Ŀ���ܹ���ʦ����Ŀ��������������"));
    	this.kDTable1.getCell(12, 0).setValue(apporveResultForMap.get("��۹���ʦ����������"));
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

	@Override
	protected IObjectValue createNewDetailData(KDTable kdtable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

}