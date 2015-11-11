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
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

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
    	
    	//���������ؼ�
    	KDCheckBox cb = new KDCheckBox();
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(cb);
    	
    	this.kDTable1.addColumns(4);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	IRow addRow1 = this.kDTable1.addRow();
    	addRow1.getCell(0).setValue("��Ŀ����");
    	addRow1.getCell(2).setValue("�˶����");
    	
    	IRow addRow2 = this.kDTable1.addRow();
    	addRow2.getCell(0).setValue("��ͬ���");
    	mergeManager.mergeBlock(1, 1, 1, 3);
    	
    	IRow addRow3 = this.kDTable1.addRow();
//    	addRow3.getCell(0).setValue("���赥λ");
//    	addRow3.getCell(2).setValue("����ͼֽ���");
    	
    	IRow addRow4 = this.kDTable1.addRow();
    	addRow4.getCell(0).setValue("����");
    	addRow4.getCell(2).setValue("����");
    	
    	IRow addRow5 = this.kDTable1.addRow();
    	addRow5.getCell(0).setValue("����");
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
//    	mergeManager.mergeBlock(4, 4, 7, );
    	IRow addRow6 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	IRow addRow7 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	IRow addRow8 = this.kDTable1.addRow();
    	mergeManager.mergeBlock(4, 0, 7, 0);
    	mergeManager.mergeBlock(4, 1, 7, 3);
    	
    	IRow addRow9 = this.kDTable1.addRow();
    	addRow9.getCell(2).setValue("���̲�ӡ�£�");
    	mergeManager.mergeBlock(8, 0, 8, 1);
    	
    	IRow addRow10 = this.kDTable1.addRow();
    	addRow10.getCell(0).setValue("������");
    	addRow10.getCell(2).setValue("���̲�����");
    	
    	
    	this.kDTable1.getColumn(0).setWidth(201);
    	this.kDTable1.getColumn(1).setWidth(201);
    	this.kDTable1.getColumn(2).setWidth(201);
//    	this.kDTable1.getColumn(3).setWidth(203);
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	//-----------------------------------------------------------------------------------------------------------------
    	this.kDTable2.addColumns(13);
    	KDTMergeManager mergeManager2 = kDTable2.getMergeManager();
    	
    	IRow addRow21 = this.kDTable2.addRow();
    	addRow21.getCell(0).setValue("��ͬ����");
    	mergeManager2.mergeBlock(0, 2, 0, 12);
    	mergeManager2.mergeBlock(0, 0, 0, 1);
    	
    	IRow addRow22 = this.kDTable2.addRow();
    	addRow22.getCell(0).setValue("��ͬ���");
    	mergeManager2.mergeBlock(1, 2, 1, 9);
    	mergeManager2.mergeBlock(1, 0, 1, 1);
    	addRow22.getCell(10).setValue("���ʱ��");
    	mergeManager2.mergeBlock(1, 11, 1, 12);
    	
    	IRow addRow23 = this.kDTable2.addRow();
    	addRow23.getCell(0).setValue("�����");
    	mergeManager2.mergeBlock(2, 0, 2, 1);
    	addRow23.getCell(2).setEditor(editor);
    	addRow23.getCell(2).setValue(Boolean.FALSE);
    	mergeManager2.mergeBlock(2, 2, 2, 3);
    	
    	addRow23.getCell(4).setValue("��Ʋ�");
//    	addRow23.getCell(5).setValue("XX");
    	addRow23.getCell(5).setEditor(editor);
    	addRow23.getCell(5).setValue(Boolean.FALSE);
    	addRow23.getCell(6).setValue("���̲�");
//    	addRow23.getCell(7).setValue("XX");
    	addRow23.getCell(7).setEditor(editor);
    	addRow23.getCell(7).setValue(Boolean.FALSE);
    	addRow23.getCell(8).setValue("ǰ�����ײ�");
//    	addRow23.getCell(9).setValue("XX");
    	addRow23.getCell(9).setEditor(editor);
    	addRow23.getCell(9).setValue(Boolean.FALSE);
    	addRow23.getCell(10).setValue("���۲�");
//    	addRow23.getCell(11).setValue("XX");
    	addRow23.getCell(11).setEditor(editor);
    	addRow23.getCell(11).setValue(Boolean.FALSE);
    	addRow23.getCell(12).setValue("����");
    	
    	IRow addRow24 = this.kDTable2.addRow();
    	addRow24.getCell(0).setValue("���̲�");
    	addRow24.getCell(2).setValue("����ԭ�����ݣ�");
//    	mergeManager2.mergeBlock(3, 0, 5, 0);
//    	mergeManager2.mergeBlock(3, 2, 3, 4);
//    	mergeManager2.mergeBlock(3, 5, 3, 12);
    	
    	IRow addRow25 = this.kDTable2.addRow();
//    	addRow25.getCell(2).setValue("����ԭ�����ݣ�");
//    	mergeManager2.mergeBlock(3, 0, 5, 0);
//    	mergeManager2.mergeBlock(4, 2, 4, 4);
//    	mergeManager2.mergeBlock(4, 5, 4, 12);
    	IRow addRow26 = this.kDTable2.addRow();
    	addRow26.getCell(2).setValue("�Ƿ�Ӱ����������Ͻڵ㣺");
    	mergeManager2.mergeBlock(3, 0, 5, 0);
    	mergeManager2.mergeBlock(5, 2, 5, 4);
    	addRow26.getCell(5).setEditor(editor);
    	addRow26.getCell(5).setValue(Boolean.FALSE);
    	addRow26.getCell(6).setValue("��");
    	addRow26.getCell(7).setEditor(editor);
    	addRow26.getCell(7).setValue(Boolean.FALSE);
    	addRow26.getCell(8).setValue("��");
    	mergeManager2.mergeBlock(5, 8, 5, 12);
    	
    	mergeManager2.mergeBlock(3, 0, 5, 1);
    	mergeManager2.mergeBlock(3, 2, 4, 4);
    	mergeManager2.mergeBlock(3, 5, 4, 12);
    	
    	IRow addRow27 = this.kDTable2.addRow();
    	addRow27.getCell(0).setValue("�������");
    	addRow27.getCell(2).setValue("����");
    	mergeManager2.mergeBlock(6, 2, 6, 3);
    	addRow27.getCell(4).setValue("Ԥ������Ӱ��");
    	mergeManager2.mergeBlock(6, 4, 6, 10);
    	addRow27.getCell(11).setValue("������");
    	addRow27.getCell(12).setValue("ȷ�����");
    	
    	IRow addRow28 = this.kDTable2.addRow();
    	addRow28.getCell(2).setValue("��Ʋ�");
    	addRow28.getCell(4).setValue("��ƷƷ�ʣ�");
//    	addRow28.getCell(5).setValue("XX");
    	addRow28.getCell(5).setEditor(editor);
    	addRow28.getCell(5).setValue(Boolean.FALSE);
    	addRow28.getCell(6).setValue("���");
//    	addRow28.getCell(7).setValue("XX");
    	addRow28.getCell(7).setEditor(editor);
    	addRow28.getCell(7).setValue(Boolean.FALSE);
    	addRow28.getCell(8).setValue("����");
//    	addRow28.getCell(9).setValue("XX");
    	addRow28.getCell(9).setEditor(editor);
    	addRow28.getCell(9).setValue(Boolean.FALSE);
    	addRow28.getCell(10).setValue("��Ӱ��");
    	mergeManager2.mergeBlock(7, 2, 7, 3);
    	IRow addRow29 = this.kDTable2.addRow();//--
    	addRow29.getCell(2).setValue("���̲�");
    	addRow29.getCell(4).setValue("���ڣ�");
//    	addRow29.getCell(5).setValue("XX");
    	addRow29.getCell(5).setEditor(editor);
    	addRow29.getCell(5).setValue(Boolean.FALSE);
    	addRow29.getCell(6).setValue("����");
//    	addRow29.getCell(7).setValue("XX");
    	addRow29.getCell(7).setEditor(editor);
    	addRow29.getCell(7).setValue(Boolean.FALSE);
    	addRow29.getCell(8).setValue("�ӳ�");
//    	addRow29.getCell(9).setValue("XX");
    	addRow29.getCell(9).setEditor(editor);
    	addRow29.getCell(9).setValue(Boolean.FALSE);
    	addRow29.getCell(10).setValue("��Ӱ��");
    	mergeManager2.mergeBlock(8, 2, 9, 3);
    	IRow addRow210 = this.kDTable2.addRow();
    	addRow210.getCell(4).setValue("�Ƿ񷵹���");
//    	addRow210.getCell(5).setValue("XX");
    	addRow210.getCell(5).setEditor(editor);
    	addRow210.getCell(5).setValue(Boolean.FALSE);
    	addRow210.getCell(6).setValue("��Ҫ����");
//    	addRow210.getCell(7).setValue("XX");
    	addRow210.getCell(7).setEditor(editor);
    	addRow210.getCell(7).setValue(Boolean.FALSE);
    	addRow210.getCell(8).setValue("����Ҫ����");
    	mergeManager2.mergeBlock(8, 2, 9, 3);
    	
    	IRow addRow211 = this.kDTable2.addRow();
    	addRow211.getCell(2).setValue("���۲�");
    	addRow211.getCell(4).setValue("���ۣ�");
//    	addRow211.getCell(5).setValue("XX");
    	addRow211.getCell(5).setEditor(editor);
    	addRow211.getCell(5).setValue(Boolean.FALSE);
    	addRow211.getCell(6).setValue("����");
    	addRow211.getCell(7).setValue("XX");
    	addRow211.getCell(7).setEditor(editor);
    	addRow211.getCell(7).setValue(Boolean.FALSE);
    	addRow211.getCell(8).setValue("����");
    	mergeManager2.mergeBlock(10, 2, 10, 3);
    	
    	IRow addRow212 = this.kDTable2.addRow();
    	addRow212.getCell(4).setValue("���۳�ŵ");
    	addRow212.getCell(5).setEditor(editor);
    	addRow212.getCell(5).setValue(Boolean.FALSE);
    	addRow212.getCell(6).setValue("Ӱ��");
    	addRow212.getCell(7).setEditor(editor);
    	addRow212.getCell(7).setValue(Boolean.FALSE);
    	addRow212.getCell(8).setValue("��Ӱ��");
    	
    	

    	mergeManager2.mergeBlock(10, 2, 11, 3);
    	
    	IRow addRow213 = this.kDTable2.addRow();
    	
    	addRow213.getCell(2).setValue("ǰ�����ײ�");
    	addRow213.getCell(4).setValue("����ָ��");
    	addRow213.getCell(5).setEditor(editor);
    	addRow213.getCell(5).setValue(Boolean.FALSE);
    	addRow213.getCell(6).setValue("Ӱ��");
    	addRow213.getCell(7).setEditor(editor);
    	addRow213.getCell(7).setValue(Boolean.FALSE);
    	addRow213.getCell(8).setValue("��Ӱ��");
    	mergeManager2.mergeBlock(12, 2, 12, 3);
    	
    	
    	IRow addRow214 = this.kDTable2.addRow();
    	addRow214.getCell(2).setValue("�� �� ��");
    	addRow214.getCell(4).setValue("���㹤�̷������ܼۣ�");
    	addRow214.getCell(10).setValue("��Ԫ");
    	mergeManager2.mergeBlock(13, 4, 13, 8);
    	
    	
    	
    	IRow addRow215 = this.kDTable2.addRow();
    	addRow215.getCell(4).setValue("���з�����ɵ�ǩ֤���ù��㣺");
    	addRow215.getCell(10).setValue("��Ԫ");
    	mergeManager2.mergeBlock(14, 4, 14, 8);
    	IRow addRow216 = this.kDTable2.addRow();
    	addRow216.getCell(4).setValue("���α���ۼ�ռ��ͬ�۵İٷֱ�");
    	addRow216.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(15, 4, 15, 8);
    	
    	IRow addRow217 = this.kDTable2.addRow();
    	addRow217.getCell(4).setValue("����Ŀǰ����ۼ�ռ��ͬ�۰ٷֱ�");
    	addRow217.getCell(10).setValue("%");
    	mergeManager2.mergeBlock(16, 4, 16, 8);
    	
    	IRow addRow218 = this.kDTable2.addRow();
    	addRow218.getCell(4).setValue("������ͬ����Ƿ񳬳�Ŀ��ɱ��޶�");
    	addRow218.getCell(7).setEditor(editor);
    	addRow218.getCell(7).setValue(Boolean.FALSE);
    	addRow218.getCell(8).setValue("��");
    	addRow218.getCell(9).setEditor(editor);
    	addRow218.getCell(9).setValue(Boolean.FALSE);
    	addRow218.getCell(10).setValue("��");
    	mergeManager2.mergeBlock(17, 4, 17, 6);
    	
    	mergeManager2.mergeBlock(13, 2, 17, 3);
    	mergeManager2.mergeBlock(6, 0, 17, 1);
    	
    	
    	
    	IRow addRow219 = this.kDTable2.addRow();
    	addRow219.getCell(0).setValue("��˾��һ������:");
    	
    	IRow addRow220 = this.kDTable2.addRow();
    	mergeManager2.mergeBlock(18, 2, 19, 12);
    	
    	IRow addRow221 = this.kDTable2.addRow();
    	
    	addRow221.getCell(8).setValue("��һ������ǩ��:");
    	addRow221.getCell(11).setValue("����");
    	
    	mergeManager2.mergeBlock(20, 2, 20, 7);
    	mergeManager2.mergeBlock(20, 8, 20, 9);
    	mergeManager2.mergeBlock(18, 0, 20, 1);
//    	
    	IRow addRow222 = this.kDTable2.addRow();
    	addRow222.getCell(2).setValue("���̹�������");
    	mergeManager2.mergeBlock(21, 4, 21, 12);
    	IRow addRow223 = this.kDTable2.addRow();
    	addRow223.getCell(2).setValue("�ɱ���������");
    	mergeManager2.mergeBlock(22, 4, 22, 12);
    	IRow addRow224 = this.kDTable2.addRow();
    	addRow224.getCell(2).setValue("Ӫ����������");
    	mergeManager2.mergeBlock(23, 4, 23, 12);
    	IRow addRow225 = this.kDTable2.addRow();
    	addRow225.getCell(2).setValue("��ҵ��������");
    	mergeManager2.mergeBlock(24, 4, 24, 12);
    	IRow addRow226 = this.kDTable2.addRow();
    	addRow226.getCell(0).setValue("������");
    	addRow226.getCell(2).setValue("��Ӫ��������");
    	mergeManager2.mergeBlock(25, 4, 25, 12);
    	mergeManager2.mergeBlock(21, 0, 25, 1);
    	
    	IRow addRow227 = this.kDTable2.addRow();
    	addRow227.getCell(2).setValue("���̳ɱ����ܲ�");
    	mergeManager2.mergeBlock(26, 4, 26, 12);
    	IRow addRow228 = this.kDTable2.addRow();
    	addRow228.getCell(2).setValue("ִ�и��ܲ�");
    	mergeManager2.mergeBlock(27, 4, 27, 12);
    	IRow addRow229 = this.kDTable2.addRow();
    	addRow229.getCell(0).setValue("������");
    	addRow229.getCell(2).setValue("�ܲ�");
    	mergeManager2.mergeBlock(28, 4, 28, 12);
    	mergeManager2.mergeBlock(26, 0, 28, 1);
    	
    	
    	
    	//�п�
    	int i;
    	for(i=0;i<kDTable1.getRowCount();i++)
    	{
    		kDTable1.getRow(i).setHeight(50);
    	}
    	//�п�
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(200);
    	}
    	
    	//�п�
    	for(i=0;i<kDTable2.getRowCount();i++)
    	{
    		kDTable2.getRow(i).setHeight(35);
    	}
    	//�п�
    	for(i=0;i<kDTable2.getColumnCount();i++)
    	{
    		kDTable2.getColumn(i).setWidth(61);
    	}
    	this.kDTable2.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"NuDk97fJRYGlkjRCTg9zcnARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FCurProjectName ��Ŀ����1,ChangeAB.FNumber ������2 ,to_char(ChangeAB.CFPutForwardTime,'yyyy-mm-dd') ���ʱ��, ChangeAB.Freadesc ����,BaseU.Fname_l2 �������,");
    	sb.append(" contractB.fname ��ͬ����6,contractB.fnumber ��ͬ���7,ChangeAB.cfzs,ChangeAB.cfcs,ChangeAE.FChangeContent");
//    	sb.append(" ChangeAB.CFQuality ��ƷƷ�� ,ChangeAB.CFTimeLi ���� ,ChangeAB.CFSale ���� ,CFCost �ɱ�");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry ChangeAE on ChangeAB.fid=ChangeAE.FParentID");
    	sb.append(" left join T_CON_ChangeSupplierEntry ChangeSE on ChangeAB.fid=ChangeSE.FParentID");
    	sb.append(" left join T_CON_ContractBill contractB on contractB.fid=ChangeSE.FContractBillID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	while(rowset.next()){
    		kDTextField1.setText(rowset.getString(1));
    		kDTextField2.setText(rowset.getString(2));
    		
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
//    		this.kDTable1.getCell(0, 3).setValue(rowset.getString(2));
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(7));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(8));
    		this.kDTable1.getCell(3, 3).setValue(rowset.getString(9));
    		this.kDTable1.getCell(4, 1).setValue(rowset.getString(10));
    		
    		this.kDTable2.getCell(0, 2).setValue(rowset.getString(6));
    		this.kDTable2.getCell(1, 2).setValue(rowset.getString(7));
    		this.kDTable2.getCell(1, 11).setValue(rowset.getString(3));
    	}
    	
    	
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
    
//    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable1_tableClicked(e);
//    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
//    }
//    protected void kDTable2_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable2_tableClicked(e);
//    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
//    }
    

    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    
    
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	if(getOprtState().equals("�Զ���")){//������Զ���״̬��
    		//���Ϊ������ʾ
    		if(1!=1){
    			FDCMsgBox.showInfo("�Զ���״̬");
    			SysUtil.abort();
    		}
//    		SelectorItemCollection sic = new SelectorItemCollection();
//    		sic.add("number");
    		editData.setNumber("1111λ");
//    		AimAimCostAdjustFactory.getRemoteInstance().updatePartial(editData, sic);
    	}
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSubmit_actionPerformed(e);
    }

	protected IObjectValue createNewDetailData(KDTable kdtable) {
		return null;
	}

	protected KDTable getDetailTable() {
		return kDTable1;
	}


	protected ICoreBase getBizInterface() throws Exception {
		return ChangeAuditBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new ChangeAuditBillInfo();
	}


}