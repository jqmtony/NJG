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
import com.kingdee.bos.appframework.stateManage.OprationState;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustFactory;
import com.kingdee.eas.fdc.aimcost.AimAimCostAdjustInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * Ŀ��ɱ����������棨�����棩
 * output class name
 */
public class TargetCostUI extends AbstractTargetCostUI
{
    private static final Logger logger = CoreUIObject.getLogger(TargetCostUI.class);
    
    /**
     * output class constructor
     */
    public TargetCostUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    

    public void onLoad() throws Exception {
    	super.onLoad();
    	initui();
    }
    
    private void initui()  throws BOSException, SQLException{
//    	if(oprtState.equals("fi"))
//    	{
//    		
//    	}else if(oprtState.equals("fi")){
//    		
//    	}
//    	else if(oprtState.equals("fi")){
//    		
//    	}
    	kDTable1.addColumns(9);
    	KDTMergeManager mergeManager = kDTable1.getMergeManager();	//�ںϹ�������
    	this.kDTable1.getIndexColumn().getStyleAttributes().setHided(true);//�����к�
    	
    	//��һ��
    	IRow addrow1 = kDTable1.addRow();
    	addrow1.getCell(0).setValue("�ɱ����:");
    	addrow1.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow1.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow1.getCell(1).setValue("Ӫҵ����:");
    	addrow1.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow1.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow1.getCell(3).setValue("��Ŀ�����ܶ�:");
    	addrow1.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow1.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow1.getCell(5).setValue("��ҵ����˰:");
    	addrow1.getCell(5).getStyleAttributes().setLocked(true);
//    	addrow1.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow1.getCell(7).setValue("��Ŀ������:");
    	addrow1.getCell(7).getStyleAttributes().setLocked(true);
//    	addrow1.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	
    	//�ڶ���
    	IRow addrow2 = kDTable1.addRow();
    	addrow2.getCell(0).setValue("��Ŀ����:");
    	addrow2.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow2.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	//�ں�(1)-(3)���� 2-4����
    	mergeManager.mergeBlock(1, 1, 1, 8); 	//�ڶ��У�1����Ԫ���ںϣ���һ�У�4����Ԫ���ں�
    	
    	//������
    	IRow addrow3 = kDTable1.addRow();
    	addrow3.getCell(0).setValue("���̵�ַ:");
    	addrow3.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow3.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(2, 1, 2, 8);
    	
    	//������
    	IRow addrow4 = kDTable1.addRow();
    	addrow4.getCell(0).setValue("���赥λ:");
    	addrow4.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow4.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(3, 1, 3, 8);
    	
    	//������
    	IRow addrow5 = kDTable1.addRow();
    	addrow5.getCell(0).setValue("���Ƶ�λ:");
    	addrow5.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow5.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(4, 1, 4, 8);
    	
    	//������
    	IRow addrow6 = kDTable1.addRow();
    	addrow6.getCell(0).setValue("���Ʋ���:");
    	addrow6.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow6.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow6.getCell(1).setValue("ǰ�ڲ�:");
    	addrow6.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow6.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow6.getCell(3).setValue("Ӫ����:");
    	addrow6.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow6.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow6.getCell(5).setValue("���̲�:");
    	addrow6.getCell(5).getStyleAttributes().setLocked(true);
//    	addrow6.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow6.getCell(7).setValue("��Ʋ�:");
    	addrow6.getCell(7).getStyleAttributes().setLocked(true);
//    	addrow6.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(5, 0, 6, 0);  //�����е��ھ����ں�
    	
    	
    	//������
    	IRow addrow7 = kDTable1.addRow();
    	addrow7.getCell(0).setValue("���Ʋ���:");
    	addrow7.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow7.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow7.getCell(1).setValue("������:");
    	addrow7.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow7.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow7.getCell(3).setValue("���ײ�:");
    	addrow7.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow7.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow7.getCell(5).setValue("�ɱ���:");
    	addrow7.getCell(5).getStyleAttributes().setLocked(true);
//    	addrow7.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow7.getCell(7).setValue("����:");
    	addrow7.getCell(7).getStyleAttributes().setLocked(true);
//    	addrow7.getCell(7).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(5, 0, 6, 0);  //�����е��ھ����ں�
    	//�ڰ���
    	IRow addrow8 = kDTable1.addRow();
    	addrow8.getCell(0).setValue("�����:");
    	addrow8.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow8.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow8.getCell(1).setValue("��˾������:");
    	addrow8.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow8.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow8.getCell(3).setValue("���й�˾������:");
    	addrow8.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow8.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow8.getCell(5).setValue("�����ܲ�������:");
    	addrow8.getCell(5).getStyleAttributes().setLocked(true);
//    	addrow8.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	//�ھ���
    	IRow addrow9 = kDTable1.addRow();
    	addrow9.getCell(0).setValue("������˲���:");
    	addrow9.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow9.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow9.getCell(1).setValue("�ɱ���������:");
    	addrow9.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow9.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow9.getCell(3).setValue("��ƹ�������:");
    	addrow9.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow9.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow9.getCell(5).setValue("Ӫ����������:");
    	addrow9.getCell(5).getStyleAttributes().setLocked(true);
//    	addrow9.getCell(5).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(8, 0, 9, 0);  //�����е��ھ����ں�
    	
    	//��ʮ��
    	IRow addrow10 = kDTable1.addRow();
    	addrow10.getCell(0).setValue("������˲���:");
    	addrow10.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow10.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow10.getCell(1).setValue("���̹�������:");
    	addrow10.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow10.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow10.getCell(3).setValue("��Ӫ��������:");
    	addrow10.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow10.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow10.getCell(3).setValue("�����������:");
    	addrow10.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow10.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	mergeManager.mergeBlock(8, 0, 9, 0);  //�����е��ھ����ں�
    	
    	//��ʮһ��
    	IRow addrow11 = kDTable1.addRow();
    	addrow11.getCell(0).setValue("����������:");
    	addrow11.getCell(0).getStyleAttributes().setLocked(true);
//    	addrow11.getCell(0).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow11.getCell(1).setValue("���̳ɱ����ܲ�:");
    	addrow11.getCell(1).getStyleAttributes().setLocked(true);
//    	addrow11.getCell(1).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	addrow11.getCell(3).setValue("ִ�и��ܲ�:");
    	addrow11.getCell(3).getStyleAttributes().setLocked(true);
//    	addrow11.getCell(3).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);//�ϱ���ɫ
    	//��ʮһ��
    	IRow addrow12 = kDTable1.addRow();
    	mergeManager.mergeBlock(11, 0, 11, 8);  //��1�е��ھ����ں�
    	
    	//�п�
    	int i;
    	for(i=0;i<kDTable1.getRowCount();i++)
    	{
    		kDTable1.getRow(i).setHeight(45);
    	}

    	//�п�
    	for(i=0;i<kDTable1.getColumnCount();i++)
    	{
    		kDTable1.getColumn(i).setWidth(90);
    	}    	
    	
    	String billId = editData.getId()!=null?editData.getId().toString():"wgnhf9P3R26Ot1IJWj08opkZNJQ="; //��������ID
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select b.fname_l2,c.fname_l2,a.CFaddress,to_char(a.FBizDate,'yyyy-mm-dd') ���ʱ�� ");
    	sb.append(" from  T_AIM_MeasureCost  a  ");
    	sb.append(" left join T_FDC_CurProject  b on a.FProjectID=b.fid ");
    	sb.append(" left join T_ORG_BaseUnit  c on b.FFullOrgUnit=c.fid ");
    	sb.append(" where a.fid = '").append(billId).append("'");
    	
    	IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
    	while(rowset.next()){
    		this.kDTable1.getCell(1, 1).setValue(rowset.getString(1));
    		this.kDTable1.getCell(2, 1).setValue(rowset.getString(3));
    		this.kDTable1.getCell(3, 1).setValue(rowset.getString(2));
    		this.kDTable1.getCell(4, 1).setValue(rowset.getString(2));
    		this.kDTable1.getCell(11, 1).setValue(rowset.getString(4));
    		
    	}
    	
    	//�������е��ֶ�
    	Map<String, String> apporveResultForMap = WFResultApporveHelper.getApporveResultForPerson(billId);
    	this.kDTable1.getCell(5, 2).setValue(apporveResultForMap.get("ǰ�ڲ�"));
    	this.kDTable1.getCell(5, 4).setValue(apporveResultForMap.get("Ӫ����"));
    	this.kDTable1.getCell(5, 6).setValue(apporveResultForMap.get("���̲�"));
    	this.kDTable1.getCell(5, 8).setValue(apporveResultForMap.get("��Ʋ�"));
    	this.kDTable1.getCell(6, 2).setValue(apporveResultForMap.get("������"));
    	this.kDTable1.getCell(6, 4).setValue(apporveResultForMap.get("���ײ�"));
    	this.kDTable1.getCell(6, 6).setValue(apporveResultForMap.get("�ɱ���"));
    	this.kDTable1.getCell(6, 8).setValue(apporveResultForMap.get("����"));
    	this.kDTable1.getCell(7, 2).setValue(apporveResultForMap.get("��˾������"));
    	this.kDTable1.getCell(7, 4).setValue(apporveResultForMap.get("���й�˾������"));
    	this.kDTable1.getCell(7, 6).setValue(apporveResultForMap.get("�����ܲ�������"));
    	this.kDTable1.getCell(8, 2).setValue(apporveResultForMap.get("�ɱ���������"));
    	this.kDTable1.getCell(8, 4).setValue(apporveResultForMap.get("��ƹ�������"));
    	this.kDTable1.getCell(8, 6).setValue(apporveResultForMap.get("Ӫ����������"));
    	this.kDTable1.getCell(9, 2).setValue(apporveResultForMap.get("���̹�������"));
    	this.kDTable1.getCell(9, 4).setValue(apporveResultForMap.get("��Ӫ��������"));
    	this.kDTable1.getCell(9, 6).setValue(apporveResultForMap.get("�����������"));
    	this.kDTable1.getCell(10, 2).setValue(apporveResultForMap.get("���̳ɱ����ܲ�"));
    	this.kDTable1.getCell(10, 4).setValue(apporveResultForMap.get("ִ�и��ܲ�"));
    	
	}
    
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
		return MeasureCostFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return new MeasureCostInfo();
	}
    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudShare_actionPerformed
     */
    public void actionCloudShare_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudShare_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */


    /**
     * output actionSubmit_actionPerformed
     */


    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }


}