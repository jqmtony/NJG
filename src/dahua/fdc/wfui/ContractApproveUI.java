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
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��ͬ��������������
 * output class name
 */
public class ContractApproveUI extends AbstractContractApproveUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractApproveUI.class);
    
    /**
     * output class constructor
     */
    public ContractApproveUI() throws Exception
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
    	addRow.getCell(0).setValue("���赥λ");
//    	addRow.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRow.getCell(5).setValue("��Ŀ����");
//    	addRow.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(0, 1, 0, 4);
    	mergeManager.mergeBlock(0, 6, 0, 9);
    	
    	//�ڶ���
    	IRow addRowtwo = this.kDTable1.addRow();
    	addRowtwo.getCell(0).setValue("��ͬ����");
//    	addRowtwo.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwo.getCell(5).setValue("��ͬ���");
//    	addRowtwo.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 4);
    	mergeManager.mergeBlock(1, 6, 1, 9);
    	
    	//������
    	IRow addRowthree = this.kDTable1.addRow();
    	addRowthree.getCell(0).setValue("��ͬ����");
//    	addRowthree.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowthree.getCell(3).setValue("��ͬ�ܼ� ");
//    	addRowthree.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(2, 1, 2, 2);
    	mergeManager.mergeBlock(2, 4, 2, 9);
    	
    	
    	//������
    	IRow addRowfour = this.kDTable1.addRow();
    	addRowfour.getCell(0).setValue("��Լ�滮���");
//    	addRowfour.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(3).setValue("�۸�ָ��");
//    	addRowfour.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfour.getCell(4).setValue("ƽ�����ָ��");
//    	addRowfour.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 0, 4, 0);
    	mergeManager.mergeBlock(3, 1, 4, 2);
    	mergeManager.mergeBlock(3, 3, 4, 3);
    	mergeManager.mergeBlock(3, 5, 3, 9);
    	
    	//������
    	IRow addRowfive = this.kDTable1.addRow();
    	addRowfive.getCell(0).setValue("��Լ�滮���");
//    	addRowfive.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfive.getCell(3).setValue("�۸�ָ��");
//    	addRowfive.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowfive.getCell(4).setValue("�ۺϵ���");
//    	addRowfive.getCell(4).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(3, 0, 4, 0);
    	mergeManager.mergeBlock(3, 1, 4, 2);
    	mergeManager.mergeBlock(3, 3, 4, 3);
    	mergeManager.mergeBlock(4, 5, 4, 9);
    	
    	//������
    	IRow addRowsix = this.kDTable1.addRow();
    	addRowsix.getCell(0).setValue("�걨��λ");
//    	addRowsix.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowsix.getCell(1).setValue("�ɱ���");
//    	addRowsix.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 0, 8, 0);
    	mergeManager.mergeBlock(5, 1, 5, 2);
    	mergeManager.mergeBlock(5, 3, 5, 9);
    	
    	//������
    	IRow addRowseven = this.kDTable1.addRow();
    	addRowseven.getCell(0).setValue("�걨��λ");
//    	addRowseven.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowseven.getCell(1).setValue("���̲�/ǰ��(����)��");
//    	addRowseven.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 0, 8, 0);
    	mergeManager.mergeBlock(6, 1, 6, 2);
    	mergeManager.mergeBlock(6, 3, 6, 9);
    	
    	//�ڰ���
    	IRow addRoweight = this.kDTable1.addRow();
    	addRoweight.getCell(0).setValue("�걨��λ");
//    	addRoweight.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRoweight.getCell(1).setValue("����");
//    	addRoweight.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 0, 8, 0);
    	mergeManager.mergeBlock(7, 1, 7, 2);
    	mergeManager.mergeBlock(7, 3, 7, 9);
    	
    	
    	//��Ʋ�
    	IRow addRowSj = this.kDTable1.addRow();
    	addRowSj.getCell(0).setValue("�걨��λ");
//    	addRowSj.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowSj.getCell(1).setValue("��Ʋ�");
//    	addRowSj.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(5, 0, 8, 0);
    	mergeManager.mergeBlock(8, 1, 8, 2);
    	mergeManager.mergeBlock(8, 3, 8, 9);
    	
    	
    	//�ھ���
    	IRow addRownine = this.kDTable1.addRow();
    	addRownine.getCell(0).setValue("���赥λ");
//    	addRownine.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRownine.getCell(1).setValue("��Ŀ��˾��һ������");
//    	addRownine.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 0, 10, 0);
    	mergeManager.mergeBlock(9, 1, 9, 2);
    	mergeManager.mergeBlock(9, 3, 9, 9);

    	//��ʮ��
    	IRow addRowten = this.kDTable1.addRow();
    	addRowten.getCell(0).setValue("���赥λ");
//    	addRowten.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowten.getCell(1).setValue("���й�˾/������һ������");
//    	addRowten.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(9, 0, 10, 0);
    	mergeManager.mergeBlock(10, 1, 10, 2);
    	mergeManager.mergeBlock(10, 3, 10, 9);
    	
    	//��ʮһ��
    	IRow addRowelev = this.kDTable1.addRow();
    	addRowelev.getCell(0).setValue("�ɱ���������");
//    	addRowelev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowelev.getCell(1).setValue("��Լ���㲿");
//    	addRowelev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 0, 12, 0);
    	mergeManager.mergeBlock(11, 1, 11, 2);
    	mergeManager.mergeBlock(11, 3, 11, 9);

    	//��ʮ����
    	IRow addRowtwev= this.kDTable1.addRow();
    	addRowtwev.getCell(0).setValue("�ɱ���������");
//    	addRowtwev.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	addRowtwev.getCell(1).setValue("��һ������");
//    	addRowtwev.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(11, 0, 12, 0);
    	mergeManager.mergeBlock(12, 1, 12, 2);
    	mergeManager.mergeBlock(12, 3, 12, 9);
    	
    	//���̹�������
    	IRow addRowGcgl= this.kDTable1.addRow();
    	addRowGcgl.getCell(0).setValue("���̹�������");
//    	addRowGcgl.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(13, 0, 13, 2);
    	mergeManager.mergeBlock(13, 3, 13, 9);
    	
    	//��ʮ����
    	IRow addRowthirt= this.kDTable1.addRow();
    	addRowthirt.getCell(0).setValue("���̳ɱ����ܲ�");
//    	addRowthirt.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(14, 0, 14, 2);
    	mergeManager.mergeBlock(14, 3, 14, 9);

    	
    	//��ʮ����
    	IRow addRowfout= this.kDTable1.addRow();
    	addRowfout.getCell(0).setValue("ִ�и��ܲ�");
//    	addRowfout.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(15, 0, 15, 2);
    	mergeManager.mergeBlock(15, 3, 15, 9);
    	
    	//��ʮ����
    	IRow addRowfift= this.kDTable1.addRow();
    	addRowfift.getCell(0).setValue("�ܲ�");
//    	addRowfift.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(16, 0, 16, 2);
    	mergeManager.mergeBlock(16, 3, 16, 9);
    	
    	//��ʮ����
    	IRow addRowsist= this.kDTable1.addRow();
    	addRowsist.getCell(0).setValue("��Լ��֤��");
//    	addRowsist.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//���������ؼ�
    	KDCheckBox cb = new KDCheckBox();
    	//�����ؼ�
    	KDTDefaultCellEditor editor = new KDTDefaultCellEditor(cb);
    	addRowsist.getCell(3).setEditor(editor);
    	addRowsist.getCell(3).setValue(Boolean.FALSE);
//    	addRowsist.getCell(3).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	addRowsist.getCell(4).setValue("����ȡ;");
    	addRowsist.getCell(5).setEditor(editor);
    	addRowsist.getCell(5).setValue(Boolean.FALSE);
    	addRowsist.getCell(6).setValue("��ȡ:");
    	//��Ԫ���趨ֻ��������
//    	KDFormattedTextField kdtEntrys_pointValue_TextField = new KDFormattedTextField();
//		kdtEntrys_pointValue_TextField.setHorizontalAlignment(2);
//        kdtEntrys_pointValue_TextField.setDataType(1);
//        kdtEntrys_pointValue_TextField.setMinimumValue(new BigDecimal("-1.0E26"));
//    	kdtEntrys_pointValue_TextField.setMaximumValue(new BigDecimal("1.0E26"));
//    	kdtEntrys_pointValue_TextField.setPrecision(2);
//    	KDTDefaultCellEditor kdtEntrys_pointValue_CellEditor = new KDTDefaultCellEditor(kdtEntrys_pointValue_TextField);
//    	addRowsist.getCell(7).setEditor(kdtEntrys_pointValue_CellEditor);

    	addRowsist.getCell(9).setValue("Ԫ��");
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(17, 0, 17, 2);
    	mergeManager.mergeBlock(17, 8, 17, 9);

    	
    	//��ʮ����
    	IRow addRowsevent= this.kDTable1.addRow();
    	addRowsevent.getCell(0).setValue("��ע");
//    	addRowsevent.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(18, 0, 18, 2);
    	mergeManager.mergeBlock(18, 3, 18, 9);
    	//�п�
    	int i;
    	for(i=0;i<kDTable1.getRowCount()-1;i++)
    	{
    		kDTable1.getRow(i).setHeight(27);
    	}
    	kDTable1.getRow(kDTable1.getRowCount()-1).setHeight(150);
    	//�п�
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(80);
    	}
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"ksSILFqtQuqFtwN92GBRjA1t0fQ=";
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select fdc.Fname_l2,ject.Fname_l2 ,con.Fname,con.FCodingNumber,bd.Fname_l2,con.FAmount,act.FAmount,con.FCurProjectID,con.FGrtAmount,entry.FContent,con.CFIsFiveClass ");
    	sb.append(" from T_CON_ContractBill con  ");
    	sb.append(" left join T_FDC_LandDeveloper fdc on fdc.fid = con.FLandDeveloperID  ");
    	sb.append(" left join T_FDC_CurProject ject on ject.fid = con.FCurProjectID ");
    	sb.append(" left join T_BD_Supplier bd on bd.fid =  con.FPartBID ");
    	sb.append(" left join T_CON_ProgrammingContract act  on act.fid = con.FProgrammingContract ");
    	sb.append(" left join T_CON_ContractBillEntry entry on entry.FParentID=con.fid and entry.FDetail='��ע' ");
    	sb.append(" where con.fid = '").append(billId).append("'");
    	//�Ƿ��������
    	boolean isFiveClass = false;
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	if(rowset.next()){
    		this.kDTable1.getCell(0, 1).setValue(rowset.getString(1));
    		this.kDTable1.getCell(0, 6).setValue(rowset.getString(2));//��Ŀ����
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(3));//��ͬ����
    		this.kDTable1.getCell(1, 6).setValue(rowset.getString(4));
    		this.kDTable1.getCell(2, 1).setValue(rowset.getString(5));
    		this.kDTable1.getCell(2, 4).setValue(rowset.getString(6));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(7));
    		if("1".equals(rowset.getString(8)))
    			isFiveClass = true;
        	//��ȡĿ��ɱ��ܽ���������
    		BigDecimal buildArea = FDCHelper.getApportionValue(rowset.getString("FCurProjectID"),ApportionTypeInfo.buildAreaType, ProjectStageEnum.AIMCOST);
    		this.kDTable1.getCell(3, 5).setValue(FDCHelper.divide(buildArea, this.kDTable1.getCell(2, 4).getValue()));//����
    		
    		//�жϱ�֤����Ƿ�Ϊ��,��ѡ��������
    		this.kDTable1.getCell(17, 7).setValue(rowset.getString(9));
    		BigDecimal Bzj = UIRuleUtil.getBigDecimal(this.kDTable1.getCell(17, 7).getValue());
    		if( Bzj != null)
    		{
    			addRowsist.getCell(5).setValue(Boolean.TRUE);//��ȡ
    		}
    		else
    		{
    			addRowsist.getCell(3).setValue(Boolean.TRUE);//����ȡ
    		}
    	
    		this.kDTable1.getCell(18, 3).setValue(rowset.getString(10));
    	}
    	
    	

 		//�������������
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForPerson(billId);
    	this.kDTable1.getCell(5, 3).setValue(apporveResultForMap.get("�ɱ���"));
    	if(apporveResultForMap.get("���̲�") != null){
    		this.kDTable1.getCell(6, 3).setValue(apporveResultForMap.get("���̲�"));
    	}
    	else {
    		this.kDTable1.getCell(6, 3).setValue(apporveResultForMap.get("ǰ�ڣ����ף���"));
    	}
//    	this.kDTable1.getCell(6, 3).setValue();
    	this.kDTable1.getCell(7, 3).setValue(apporveResultForMap.get("����"));
    	if(isFiveClass){
    		this.kDTable1.getCell(8, 3).setValue(apporveResultForMap.get("��Ʋ�"));
    		this.kDTable1.getRow(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	}
    	else
    	{
    		this.kDTable1.getRow(8).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
    	}
    	
    	this.kDTable1.getCell(9, 3).setValue(apporveResultForMap.get("��Ŀ��˾��һ������"));
    	this.kDTable1.getCell(10, 3).setValue(apporveResultForMap.get("������һ������"));
    	this.kDTable1.getCell(11, 3).setValue(apporveResultForMap.get("��Լ���㲿"));
    	this.kDTable1.getCell(12, 3).setValue(apporveResultForMap.get("��һ������"));
    	this.kDTable1.getCell(13, 3).setValue(apporveResultForMap.get("���̹�������"));
    	this.kDTable1.getCell(14, 3).setValue(apporveResultForMap.get("���̳ɱ����ܲ�"));
    	this.kDTable1.getCell(15, 3).setValue(apporveResultForMap.get("ִ�и��ܲ�"));
    	this.kDTable1.getCell(16, 3).setValue(apporveResultForMap.get("�ܲ�"));
    	
    	
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
//    	if(getOprtState().equals("�Զ���")){//������Զ���״̬��
//    		//���Ϊ������ʾ
//    		if(1!=1){
//    			FDCMsgBox.showInfo("�Զ���״̬");
//    			SysUtil.abort();
//    		}
//    		editData.setNumber("1111λ");
//    	}
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
		return ContractBillFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new ContractBillInfo();
	}
}