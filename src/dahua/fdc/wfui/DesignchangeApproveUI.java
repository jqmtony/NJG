/**
 * output package name
 */
package com.kingdee.eas.fdc.wfui;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditEntryCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

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
    	this.kDTable1.getStyleAttributes().setWrapText(true);
    	initUI();
    	
    	KDTableHelper.autoFitRowHeight(this.kDTable1, 4);
    	kDTable1.getStyleAttributes().setLocked(true);
    	if (getOprtState().equals("��Ʋ��޸�")){//������Զ���״̬��
//    		//���Ϊ������ʾ
//    		if(1!=1){
//    			FDCMsgBox.showInfo("��Ʋ��޸�");
//    			SysUtil.abort();
//    		}
    		kDTable1.getRow(6).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("���̲��޸�")){
    		kDTable1.getRow(7).getStyleAttributes().setLocked(false);
//    		kDTable1.getRow(8).getStyleAttributes().setLocked(false);
    		kDTable1.getRow(9).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("Ӫ�����޸�")){
    		kDTable1.getRow(10).getStyleAttributes().setLocked(false);
    		kDTable1.getRow(11).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("ǰ�����ײ��޸�")){
    		kDTable1.getRow(12).getStyleAttributes().setLocked(false);
    	}
    	if (getOprtState().equals("�ɱ����޸�")){
    		kDTable1.getRow(13).getStyleAttributes().setLocked(false);
    		kDTable1.getRow(14).getStyleAttributes().setLocked(false);

    	}
    }

    private void initUI() throws BOSException, SQLException{
    	

    	//���������ؼ�
    	KDCheckBox cb = new KDCheckBox();
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(cb);
    	
    	this.kDTable1.addColumns(13);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();
    	
    	//��һ��
    	IRow addRow = this.kDTable1.addRow();
    	addRow.getCell(0).setValue("��������:");

    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 12);

    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("���÷�Χ:");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 12);
    	
    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("�����:");
    	addRowthree.getCell(1).setEditor(editor);
    	addRowthree.getCell(1).setValue(Boolean.FALSE);
//    	addRowthree.getCell(1).getStyleAttributes().setHorizontalAlign(Styles.HorizontalAlignment.RIGHT);//����
    	addRowthree.getCell(2).setValue("��Ʋ�");
//    	addRowthree.getCell(3).setValue("XX");
    	addRowthree.getCell(3).setEditor(editor);
    	addRowthree.getCell(3).setValue(Boolean.FALSE);
    	addRowthree.getCell(4).setValue("���̲�");
//    	addRowthree.getCell(5).setValue("XX");
    	addRowthree.getCell(5).setEditor(editor);
    	addRowthree.getCell(5).setValue(Boolean.FALSE);
    	addRowthree.getCell(6).setValue("ǰ�����ײ�");
//    	addRowthree.getCell(7).setValue("XX");
    	addRowthree.getCell(7).setEditor(editor);
    	addRowthree.getCell(7).setValue(Boolean.FALSE);
    	addRowthree.getCell(8).setValue("���۲�");
//    	addRowthree.getCell(9).setValue("XX");
    	addRowthree.getCell(9).setEditor(editor);
    	addRowthree.getCell(9).setValue(Boolean.FALSE);
    	addRowthree.getCell(10).setValue("����");    
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 0, 3, 0);
    	mergeManager.mergeBlock(2, 10,2, 12);

    	
    	
    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("�����");
    	addRowfour.getCell(2).setValue("������:");
    	addRowfour.getCell(3).setValue("��Ա");
    	addRowfour.getCell(6).setValue("�����:");   
    	addRowfour.getCell(7).setValue("��Ա");   
    	addRowfour.getCell(10).setValue("���ʱ��:");
    	//�ں�(1)-(3)���� 2-4����
//    	mergeManager.mergeBlock(3, 1, 3, 3);
    	mergeManager.mergeBlock(3, 3, 3, 5);
    	mergeManager.mergeBlock(3, 7, 3, 9);
    	mergeManager.mergeBlock(3, 11, 3, 12);
    	mergeManager.mergeBlock(2, 0, 3, 0);

    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("���ԭ�򼰽��鷽��");
    	
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(4, 1, 4, 12);

    	
    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(1).setValue("����");
    	addRowsix.getCell(5).setValue("Ԥ������Ӱ��");
    	addRowsix.getCell(11).setValue("������ǩ��");
    	addRowsix.getCell(12).setValue("����");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 2, 5, 10);
    	
    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(1).setValue("��Ʋ�");
    	addRowseven.getCell(2).setValue("��ƷƷ��");
    	addRowseven.getCell(3).setEditor(editor);
    	addRowseven.getCell(3).setValue(Boolean.FALSE);
    	addRowseven.getCell(4).setValue("���");
    	addRowseven.getCell(5).setEditor(editor);
    	addRowseven.getCell(5).setValue(Boolean.FALSE);
    	addRowseven.getCell(6).setValue("����");
    	addRowseven.getCell(7).setEditor(editor);
    	addRowseven.getCell(7).setValue(Boolean.FALSE);
    	addRowseven.getCell(8).setValue("��Ӱ��");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(6, 8, 6, 10);


    	
    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(1).setValue("���̲�");
    	addRoweight.getCell(2).setValue("����");
//    	addRoweight.getCell(3).setValue("XX");
    	addRoweight.getCell(3).setEditor(editor);
    	addRoweight.getCell(3).setValue(Boolean.FALSE);
    	addRoweight.getCell(4).setValue("����");
    	addRoweight.getCell(5).setEditor(editor);
    	addRoweight.getCell(5).setValue(Boolean.FALSE);
    	addRoweight.getCell(6).setValue("�ӳ�");
    	addRoweight.getCell(7).setEditor(editor);
    	addRoweight.getCell(7).setValue(Boolean.FALSE);
    	addRoweight.getCell(8).setValue("��Ӱ��");
    	
//    	mergeManager.mergeBlock(5, 0, 10, 0);
    	
    	
    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(2).setValue("�Ƿ񷵹�");
    	addRownine.getCell(3).setEditor(editor);
    	addRownine.getCell(3).setValue(Boolean.FALSE);
    	addRownine.getCell(4).setValue("��Ҫ����");
    	addRownine.getCell(5).setEditor(editor);
    	addRownine.getCell(5).setValue(Boolean.FALSE);
    	addRownine.getCell(6).setValue("���践��");

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(2).setValue("�Ƿ�Ӱ����������Ͻڵ�");
    	addRowten.getCell(5).setEditor(editor);
    	addRowten.getCell(5).setValue(Boolean.FALSE);
    	addRowten.getCell(6).setValue("��");
    	addRowten.getCell(7).setEditor(editor);
    	addRowten.getCell(7).setValue(Boolean.FALSE);
    	addRowten.getCell(8).setValue("��");
    	mergeManager.mergeBlock(9, 2, 9, 4);
    	mergeManager.mergeBlock(7, 1, 9, 1);

    	
    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("�������");
    	addRowelev.getCell(1).setValue("Ӫ����");
    	addRowelev.getCell(2).setValue("��    ��");
    	addRowelev.getCell(3).setEditor(editor);
    	addRowelev.getCell(3).setValue(Boolean.FALSE);
    	addRowelev.getCell(4).setValue("����");
    	addRowelev.getCell(5).setEditor(editor);
    	addRowelev.getCell(5).setValue(Boolean.FALSE);
    	addRowelev.getCell(6).setValue("����");
    	addRowelev.getCell(7).setEditor(editor);
    	addRowelev.getCell(7).setValue(Boolean.FALSE);
    	addRowelev.getCell(8).setValue("��Ӱ��");


    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	
    	addRowtwev.getCell(2).setValue("���۳�ŵ");
    	addRowtwev.getCell(3).setEditor(editor);
    	addRowtwev.getCell(3).setValue(Boolean.FALSE);
    	addRowtwev.getCell(4).setValue("Ӱ��");
    	addRowtwev.getCell(5).setEditor(editor);
    	addRowtwev.getCell(5).setValue(Boolean.FALSE);
    	addRowtwev.getCell(6).setValue("��Ӱ��");
    	mergeManager.mergeBlock(10, 1, 11, 1);
    	
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	
    	
    	addRowthirt.getCell(1).setValue("ǰ�����ײ�");
    	addRowthirt.getCell(2).setValue("����ָ��");
    	addRowthirt.getCell(3).setEditor(editor);
    	addRowthirt.getCell(3).setValue(Boolean.FALSE);
    	addRowthirt.getCell(4).setValue("Ӱ��");
    	addRowthirt.getCell(5).setEditor(editor);
    	addRowthirt.getCell(5).setValue(Boolean.FALSE);
    	addRowthirt.getCell(6).setValue("��Ӱ��");
    	
  	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(1).setValue("�ɱ���");
    	addRowfout.getCell(2).setValue("��    ��");
    	addRowfout.getCell(3).setEditor(editor);
    	addRowfout.getCell(3).setValue(Boolean.FALSE);
    	addRowfout.getCell(4).setValue("����");
    	addRowfout.getCell(5).setEditor(editor);
    	addRowfout.getCell(5).setValue(Boolean.FALSE);
    	addRowfout.getCell(6).setValue("����");
    	addRowfout.getCell(7).setEditor(editor);
    	addRowfout.getCell(7).setValue(Boolean.FALSE);
    	addRowfout.getCell(8).setValue("�����");
    	
    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(1).setValue("�ɱ���");
    	addRowfift.getCell(2).setValue("����");
    	addRowfift.getCell(5).setValue("��Ԫ");
    	mergeManager.mergeBlock(14, 3, 14, 4);
    	mergeManager.mergeBlock(13, 1, 14, 1);
    	mergeManager.mergeBlock(5, 0, 14, 0);
    	//��Ԫ���趨ֻ��������
    	KDFormattedTextField kdtEntrys_pointValue_TextField = new KDFormattedTextField();
		kdtEntrys_pointValue_TextField.setHorizontalAlignment(2);
        kdtEntrys_pointValue_TextField.setDataType(1);
        kdtEntrys_pointValue_TextField.setMinimumValue(new BigDecimal("-1.0E26"));
    	kdtEntrys_pointValue_TextField.setMaximumValue(new BigDecimal("1.0E26"));
    	kdtEntrys_pointValue_TextField.setPrecision(2);
    	KDTDefaultCellEditor kdtEntrys_pointValue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pointValue_TextField);
    	addRowfift.getCell(3).setEditor(kdtEntrys_pointValue_CellEditor);
    	
    	
    	IRow addRow16= this.kDTable1.addRow();
    	addRow16.getCell(0).setValue("��˾��һ������:");
    	
    	IRow addRow17= this.kDTable1.addRow();
    	IRow addRow18= this.kDTable1.addRow();
    	addRow18.getCell(8).setValue("��һ������ǩ��:");
    	addRow18.getCell(11).setValue("����");
    	mergeManager.mergeBlock(15, 1, 16, 12);
    	mergeManager.mergeBlock(17, 1, 17, 7);
    	mergeManager.mergeBlock(17, 8, 17, 9);
    	mergeManager.mergeBlock(15, 0, 17, 0);
    	IRow addRow19= this.kDTable1.addRow();
    	addRow19.getCell(0).setValue("���й�˾������ܲ���һ������");

    	IRow addRow20= this.kDTable1.addRow();
    	IRow addRow21= this.kDTable1.addRow();
    	addRow21.getCell(8).setValue("��һ������ǩ��:");
    	addRow21.getCell(11).setValue("����");
    	mergeManager.mergeBlock(18, 1, 19, 12);
    	mergeManager.mergeBlock(20, 1, 20, 7);
    	mergeManager.mergeBlock(20, 8, 20, 9);
    	mergeManager.mergeBlock(18, 0, 20, 0);
    	
    	
    	//�п�
    	int i;
    	for(i=0;i<kDTable1.getRowCount();i++)
    	{
    		kDTable1.getRow(i).setHeight(27);
    	}
//    	kDTable1.getRow(kDTable1.getRowCount()-1).setHeight(150);
    	//�п�
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(80);
    	}
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	
    	

    	String billId = editData.getId()!=null?editData.getId().toString():"FrYcumfRTUq/iL87J1M2VXARYRc=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ChangeAB.FCurProjectName ��Ŀ����1 ,ChangeAB.FNumber ������1 ,ChangeAB.Fname ��������1,BaseU.Fname_l2 ������� , ");
    	sb.append(" ChangeAB.Freadesc ���÷�Χ1 ,entry.FChangeContent,u.Fname_l2,u.Fname_l2,to_char(ChangeAB.FCreateTime,'yyyy-mm-dd'),entry.FIsBack isBack");
    	sb.append(" from T_CON_ChangeAuditBill ChangeAB ");
    	sb.append(" left join T_ORG_BaseUnit BaseU on BaseU.fid=ChangeAB.FConductDeptID");
    	sb.append(" left join T_CON_ChangeAuditEntry entry on entry.FParentID = ChangeAB.fid ");
    	sb.append(" left join T_PM_User u on u.fid = ChangeAB.FCreatorID");
    	sb.append(" left join T_PM_User u on u.fid =ChangeAB.FAuditorID");
    	sb.append(" where ChangeAB.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	StringBuffer Yuanyi = new StringBuffer();
    	//����
    	String Bm = null;
    	//����
    	boolean fg = false;
    	while(rowset.next()){
    		this.kDTextField1.setText(rowset.getString(1));
    		this.kDTextField2.setText(rowset.getString(2));
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(3));
    		Bm = rowset.getString(4);
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(5));
    		Yuanyi.append(rowset.getString(6)+"\n");
    		this.kDTable1.getCell(3, 3).setValue(rowset.getString(7));
    		this.kDTable1.getCell(3, 7).setValue(rowset.getString(8));
    		this.kDTable1.getCell(3, 11).setValue(rowset.getString(9));
    		if(rowset.getBoolean("isBack"))
    			fg = true;
    	}
    	
    	this.kDTable1.getCell(4, 1).setValue(Yuanyi.toString());
    	this.kDTable1.getCell(4, 1).getStyleAttributes().setWrapText(true);
    	if(Bm != null && Bm.indexOf("���")!=-1)
    	{
    		addRowthree.getCell(1).setValue(Boolean.TRUE);
    	}
    	else if(Bm.indexOf("����")!=-1)
    	{
    		addRowthree.getCell(3).setValue(Boolean.TRUE);
    	}
    	else if(Bm.indexOf("����")!=-1)
    	{
    		addRowthree.getCell(5).setValue(Boolean.TRUE);
    	}
    	else if(Bm.indexOf("����")!=-1)
    	{
    		addRowthree.getCell(7).setValue(Boolean.TRUE);
    	}
    	else 
    	{
    		addRowthree.getCell(9).setValue(Boolean.TRUE);
    	}
    	//����
    	if(fg){
    		this.kDTable1.getCell(8, 3).setValue(Boolean.TRUE);
    	}
    	else{
    		this.kDTable1.getCell(8, 5).setValue(Boolean.TRUE);
    	}
    	//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForMap(billId);
    	if(apporveResultForMap.get("��Ʋ�") != null){
    		String result = apporveResultForMap.get("��Ʋ�");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(6, 11).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(6, 12).setValue(date);
    	}
    	if(apporveResultForMap.get("���̲�") != null){
    		String result = apporveResultForMap.get("���̲�");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(7, 11).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(7, 12).setValue(date);
    	}
    	if(apporveResultForMap.get("���۲�") != null){
    		String result = apporveResultForMap.get("���۲�");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(8, 11).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(8, 12).setValue(date);
    	}
    	if(apporveResultForMap.get("ǰ�����ײ�") != null){
    		String result = apporveResultForMap.get("ǰ�����ײ�");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(9, 11).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(9, 12).setValue(date);
    	}
    	if(apporveResultForMap.get("��Լ��") != null){
    		String result = apporveResultForMap.get("��Լ��");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		this.kDTable1.getCell(10, 11).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(10, 12).setValue(date);
    	}
    	if(apporveResultForMap.get("��˾��һ������") != null){
    		String result = apporveResultForMap.get("��˾��һ������");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		String	yijan = result.substring(result.indexOf("!"), result.indexOf("@"));
    		this.kDTable1.getCell(11, 1).setValue(yijan);
    		this.kDTable1.getCell(12, 3).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(12, 12).setValue(date);
    	}
    	if(apporveResultForMap.get("���й�˾��һ������") != null){
    		String result = apporveResultForMap.get("���й�˾��һ������");
    		String person = result.substring(0,result.indexOf("!"));
    		String date = result.substring(result.indexOf("@")+1);
    		String	yijan = result.substring(result.indexOf("!"), result.indexOf("@"));
    		this.kDTable1.getCell(13, 1).setValue(yijan);
    		this.kDTable1.getCell(14, 3).setValue(person);
    		if(!"".equals(date))
    			this.kDTable1.getCell(14, 12).setValue(date);
    	}
    	else {
    		String result = apporveResultForMap.get("�����ܲ���һ������");
    		if(result != null){
    			String person = result.substring(0,result.indexOf("!"));
    			String date = result.substring(result.indexOf("@")+1);
    			String	yijan = result.substring(result.indexOf("!"), result.indexOf("@"));
    			this.kDTable1.getCell(13, 1).setValue(yijan);
    			this.kDTable1.getCell(14, 3).setValue(person);
    			if(!"".equals(date))
    				this.kDTable1.getCell(14, 12).setValue(date);
    		}
    	}
    
    }
    
//    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
//    	super.kDTable1_tableClicked(e);
//    	FDCMsgBox.showInfo("�У�"+e.getRowIndex()+"\n�У�"+e.getColIndex());
//    }
  
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	super.actionSave_actionPerformed(e);
    }
    

	
    protected void verifyInput(ActionEvent actionevent) throws Exception {
    	super.verifyInput(actionevent);
    	int i= 0;
    	//��Ʋ���д
    	if(getOprtState().equals("��Ʋ��޸�")){
    		if((Boolean)kDTable1.getCell(6, 3).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(6, 5).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(6, 7).getValue())
    			i++;   			
    		if(i == 0){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    		}	
    		if((Boolean)kDTable1.getCell(6, 3).getValue())
    			editData.setQuality("���");
    		else if((Boolean)kDTable1.getCell(6, 5).getValue())
    			editData.setQuality("����");
    		else if((Boolean)kDTable1.getCell(6, 7).getValue())
    			editData.setQuality("��Ӱ��");
    	}
    	//���̲���д
    	if(getOprtState().equals("���̲��޸�")){
    		i= 0;
    		if((Boolean)kDTable1.getCell(7, 3).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(7, 5).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(7, 7).getValue())
    			i++;   			
    		if(i == 0){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    		}	
    		if((Boolean)kDTable1.getCell(7, 3).getValue())
    			editData.setTimeLi("����");
    		else if((Boolean)kDTable1.getCell(7, 5).getValue())
    			editData.setTimeLi("�ӳ�");
    		else if((Boolean)kDTable1.getCell(7, 7).getValue())
    			editData.setTimeLi("��Ӱ��");
    		
    		if((Boolean)kDTable1.getCell(9, 5).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(9, 7).getValue())
    			i++;
    		if(i == 1){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 2){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    		}		
    		if((Boolean)kDTable1.getCell(9, 5).getValue()){
    			editData.setSfejjd(true);
    		}
    		else if((Boolean)kDTable1.getCell(9, 7).getValue()) 		
    			editData.setSfejjd(false);
    	}
    	//Ӫ������д
    	if(getOprtState().equals("Ӫ�����޸�")){
    		i= 0;
    		if((Boolean)kDTable1.getCell(10, 3).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(10, 5).getValue())
    			i++;			
    		if((Boolean)kDTable1.getCell(10, 7).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    		}	
    		if((Boolean)kDTable1.getCell(10, 3).getValue())
    			editData.setSale("����");
    		else if((Boolean)kDTable1.getCell(10, 5).getValue())
    			editData.setSale("����");
    		else if((Boolean)kDTable1.getCell(10, 7).getValue())
    			editData.setSale("��Ӱ��");

    		if((Boolean)kDTable1.getCell(11, 3).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(11, 5).getValue())
    			i++;
    		if(i == 1){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 2){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    		}		
    		if((Boolean)kDTable1.getCell(11, 3).getValue()){
    			editData.setXscn(true);
    		}
    		else if((Boolean)kDTable1.getCell(11, 5).getValue()) 		
    			editData.setXscn(false);
    	}
    	//ǰ�����ײ���д
    	if(getOprtState().equals("ǰ�����ײ��޸�")){
    		i= 0;
    		if((Boolean)kDTable1.getCell(12, 3).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(12, 5).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    			if((Boolean)kDTable1.getCell(11, 3).getValue()){
    				editData.setBjzb(true);
    			}
    			else if((Boolean)kDTable1.getCell(11, 5).getValue()) 		
    				editData.setBjzb(false);
    		}	
    	}	
    	//�ɱ�����д
    	if(getOprtState().equals("�ɱ����޸�")){

    		if((Boolean)kDTable1.getCell(13, 3).getValue())
    			i++;
    		if((Boolean)kDTable1.getCell(13, 5).getValue())
    			i++;			
    		if((Boolean)kDTable1.getCell(13, 7).getValue())
    			i++;			
    		if(i == 0){
    			FDCMsgBox.showInfo("�㲢û�й�ѡ");
    			SysUtil.abort();
    		}else if(i > 1){
    			FDCMsgBox.showInfo("��ֻ�ܹ�ѡһ��");
    			SysUtil.abort();
    			if((Boolean)kDTable1.getCell(13, 3).getValue()){
    				editData.setCost("����");
    			}
    			else if((Boolean)kDTable1.getCell(13, 5).getValue()) 		
    				editData.setCost("����");
    			else if((Boolean)kDTable1.getCell(13, 7).getValue()) 		
    				editData.setCost("��Ӱ��");
    		}
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