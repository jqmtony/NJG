/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.costkf.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTColumns;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSEntryInfo;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSFactory;
import com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * output class name
 */
public class CQGSEditUI extends AbstractCQGSEditUI
{	
	private static final Logger logger = CoreUIObject.getLogger(CQGSEditUI.class);

	/**
	 * output class constructor
	 */
	public CQGSEditUI() throws Exception
	{
		super();   
	}
	/**
	 * output loadFields method
	 */
	public void loadFields()
	{
		super.loadFields();
		formateTableSum();
	}
	@Override
	public void onLoad() throws Exception {
		txtTotalArea.setEditable(false);
		State.setEnabled(false);
		txtVersion.setEditable(false);
		txtredarea.setVisible(true);
		chklasted.setEnabled(false);
		super.onLoad();
		chkMenuItemSubmitAndAddNew.setSelected(false); //�����������ò��ɱ༭
		chkMenuItemSubmitAndAddNew.setEnabled(false);
		btnAudit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		
		this.kDContainer1.getContentPane().add(kdtEntrys,BorderLayout.CENTER); 
		
		initTableButton(kdtEntrys_detailPanel);
		getFootRow(kdtEntrys, new String[]{"BuidlingArea","SaleArea","BuildingFloorArea"}); //�ϼ�
		
		IColumn column = kdtEntrys.getColumn("BuildingName");
		column.getStyleAttributes().setLocked(true);
		
		
	}
	
	public void onShow() throws Exception {
		super.onShow();
		
		KDTSortManager sortMange = kdtEntrys.getSortMange();	//���������
		sortMange.setSortAuto(false);
	}
	

	private void initTableButton(DetailPanel detailPanel){
		KDWorkButton addNewLineButton = detailPanel.getAddNewLineButton(); //�õ���尴ť
		KDWorkButton insertLineButton = detailPanel.getInsertLineButton();
		KDWorkButton removeLinesButton = detailPanel.getRemoveLinesButton();

		addNewLineButton.setText("��������");	//��ť���ñ���
		removeLinesButton.setText("ɾ������");
		insertLineButton.setVisible(false); //�������ò��ɼ�

		kDContainer1.addButton(addNewLineButton);//��ť�ӵ������
		kDContainer1.addButton(removeLinesButton);


		addNewLineButton.removeActionListener(addNewLineButton.getActionListeners()[0]);	//ɾ��ԭ���ļ���
		removeLinesButton.removeActionListener(removeLinesButton.getActionListeners()[0]);

		addNewLineButton.addActionListener(new ActionListener(){ //����     ��ť����

			public void actionPerformed(ActionEvent e) {
				addNewLineButton_actionPerformed(e);
			}
		});
		removeLinesButton.addActionListener(new ActionListener(){ //���� ��ť ɾ�� ����

			public void actionPerformed(ActionEvent e) {
				removeLinesButton_actionPerformed(e);
			}
		});
	}
	
	private void formateTableSum(){//����Ҳ���ڵ���ɫ��
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			
			Object value = row.getCell("BuildingName").getValue();
			
			if(value==null){
				StyleAttributes styleAttributes = row.getStyleAttributes();
				styleAttributes.setLocked(true);
				styleAttributes.setBackground(Color.cyan);
				
				if(i==this.kdtEntrys.getRowCount()-1)
					setReder(row, "������С��:");
				else
					setReder(row, "����С��:");
			}
		}
	}

	private void setReder(IRow row,final String text){
		row.getCell("BuildingName").setRenderer(new ObjectValueRender(){ //��������
 	    	public String getFormattedText(Object object) {
 	    		return text;
 	    	}
 	    });
	}

	private void addNewLineButton_actionPerformed(ActionEvent e) { //
		
		KDBizPromptBox prmtProduct = new KDBizPromptBox(); //new��F7�ؼ�
		prmtProduct.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ProductTypeQuery");//����queryinfo��query
		prmtProduct.setDisplayFormat("$name$");
		prmtProduct.setEditFormat("$number$");
		prmtProduct.setEnabledMultiSelection(true);//���ÿ��Զ�ѡ
		prmtProduct.setDataBySelector();//չʾF7ѡ��ҳ��
		
		Object[] productTypeArr = (Object[]) prmtProduct.getData();//F7�����ֵǿת������
		if(prmtProduct.getData()==null||productTypeArr.length==0) //�жϼ����Ƿ�Ϊ�գ�1��null
			return;
		Map map =  new HashMap();	//��������
		for(int i = 0; i < kdtEntrys.getRowCount(); i++){//ѭ����¼���õ���Ʒ���ͱ���
			IRow row = kdtEntrys.getRow(i);//�õ��ж���
			
			Object value = row.getCell("BuildingName").getValue();
			if(value==null || !(value instanceof ProductTypeInfo)) //��Ԫ���ֵΪ�ջ� ����������
				continue;
			ProductTypeInfo  productTypeInfo = (ProductTypeInfo) row.getCell("BuildingName").getValue();
			String number = productTypeInfo.getNumber();
			map.put(number, number); //����������
		}
		for(int i = 0; i < productTypeArr.length; i++) {//ѭ������
			ProductTypeInfo productInfo = (ProductTypeInfo) productTypeArr[i];
			if(productInfo==null || map.get(productInfo.getNumber()) != null) { //���ϲ�Ϊ������ѭ���������ظ�ѡ��
				continue;
			}
			String number = productInfo.getNumber();
			if(number.indexOf(".") != -1)
				number = number.substring(0,number.indexOf(".")); 	//	��ȡ��С���������
			
			if(Integer.parseInt(number)<=10199){ //�ж�С��010199   Integer.parseInt �ַ���תΪint��
				IRow addRow = kdtEntrys.addRow(0); //��ȡ�ж���
				addRow.getCell("BuildingName").setValue(productTypeArr[i]);//������ֵ����BuildingName
			}else{
				IRow addRow = kdtEntrys.addRow(kdtEntrys.getRowCount()-1); //��ȡ�ж���
				addRow.getCell("BuildingName").setValue(productTypeArr[i]);//������ֵ����BuildingName
			}
		}
		
	}
	private void subtotal(){   //С�Ʒ���
		BigDecimal KSbuidlingArea = BigDecimal.ZERO;
		BigDecimal KSsaleArea = BigDecimal.ZERO;
		BigDecimal KSbuildingFloorArea = BigDecimal.ZERO;
		
		BigDecimal WKSbuidlingArea = BigDecimal.ZERO;
		BigDecimal WKSsaleArea = BigDecimal.ZERO;
		BigDecimal WKSbuildingFloorArea = BigDecimal.ZERO;
		
		for(int i = 0;i<kdtEntrys.getRowCount();i++){
			IRow row = kdtEntrys.getRow(i);
			
			if(row.getCell("BuildingName").getValue()==null||!(row.getCell("BuildingName").getValue() instanceof ProductTypeInfo)) //�ջ�string����
				continue;
			ProductTypeInfo productInfo = (ProductTypeInfo) row.getCell("BuildingName").getValue();
			if(productInfo.getNumber().startsWith("01")){
				KSbuidlingArea = FDCHelper.add(KSbuidlingArea, row.getCell("BuidlingArea").getValue());
				KSsaleArea = FDCHelper.add(KSsaleArea, row.getCell("SaleArea").getValue());
				KSbuildingFloorArea = FDCHelper.add(KSbuildingFloorArea, row.getCell("BuildingFloorArea").getValue());
			}else{
				WKSbuidlingArea = FDCHelper.add(WKSbuidlingArea, row.getCell("BuidlingArea").getValue());
				WKSsaleArea = FDCHelper.add(WKSsaleArea, row.getCell("SaleArea").getValue());
				WKSbuildingFloorArea = FDCHelper.add(WKSbuildingFloorArea, row.getCell("BuildingFloorArea").getValue());
			}
		}
		
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) { //ѭ��
			IRow row = this.kdtEntrys.getRow(i);
			
			Object value = row.getCell("BuildingName").getValue();
			
			if(value==null){
				
				if(i==this.kdtEntrys.getRowCount()-1){//������С��
					row.getCell("BuidlingArea").setValue(WKSbuidlingArea);
					row.getCell("SaleArea").setValue(WKSsaleArea);
					row.getCell("BuildingFloorArea").setValue(WKSbuildingFloorArea);
				}else{
					row.getCell("BuidlingArea").setValue(KSbuidlingArea);
					row.getCell("SaleArea").setValue(KSsaleArea);
					row.getCell("BuildingFloorArea").setValue(KSbuildingFloorArea);
				}
			}
		}
		getFootRow(kdtEntrys, new String[]{"BuidlingArea","SaleArea","BuildingFloorArea"});   //�ϼ�
		KDTFootManager footRowManager = kdtEntrys.getFootManager();	//�ϼ��п�����
		if(footRowManager!=null){   //��������Ϊ�գ������ж��У�
			IRow footRow = footRowManager.getFootRow(0);
			BigDecimal buidlingArea = UIRuleUtil.getBigDecimal(footRow.getCell("BuidlingArea").getValue());
			BigDecimal saleArea = UIRuleUtil.getBigDecimal(footRow.getCell("SaleArea").getValue());
			BigDecimal buildingFloorArea = UIRuleUtil.getBigDecimal(footRow.getCell("BuildingFloorArea").getValue());
			
			BigDecimal totall = FDCHelper.add(FDCHelper.add(buidlingArea, saleArea),buildingFloorArea);
			txtTotalArea.setText(totall.toString());
		}
	}
	
	@Override
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception { //��¼ֵ�ı��¼�
		// TODO Auto-generated method stub
		super.kdtEntrys_editStopped(e);
		subtotal();
	}
	private void removeLinesButton_actionPerformed(ActionEvent e) {//
		KDTSelectBlock sb = kdtEntrys.getSelectManager().get();// ѡ���
		int size = kdtEntrys.getSelectManager().size(); // ��ȡѡ�����ܸ���
		for (int i = size-1; i >=0 ; i--)
		{
			// ��ȡ��i��ѡ���
			sb = kdtEntrys.getSelectManager().get(i);
			// ����ÿ��ѡ����������
			for (int j = sb.getBottom(); j >=sb.getTop(); j--)
			{
				IRow row = kdtEntrys.getRow(j);
				
				if(!row.getStyleAttributes().getBackground().equals(Color.cyan))
					kdtEntrys.removeRow(j);
			}
		}
		subtotal();//����С��
	}

	public void ControlState(){
		if(editData.getState().equals(FDCBillStateEnum.SAVED)){
			MsgBox.showWarning("�����ύ�������");
			abort();
		}

	}

	//    ��˷����
	public void actionAduit_actionPerformed(ActionEvent e) throws Exception {
		ControlState();
		super.actionAduit_actionPerformed(e);
		loadData();
	}
	@Override
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionUnAudit_actionPerformed(e);
		loadData();
		btnSubmit.setEnabled(true);
	}
	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
	{
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
	{
		super.menuItemEnterToNextRow_itemStateChanged(e);
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
	public void actionSave_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSave_actionPerformed(e);
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSubmit_actionPerformed(e);
	}

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
		checkSelected(); //����Ƿ�ѡ��
		String id = getSelectedKeyForAll();
		CQGSInfo cqgsinfo = CQGSFactory.getRemoteInstance().getCQGSInfo(new ObjectUuidPK(id));
		if(cqgsinfo.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showWarning("����˲����޸�");
			abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{	
		checkSelected(); //����Ƿ�ѡ��
		String id = getSelectedKeyForAll();
		CQGSInfo cqgsinfo = CQGSFactory.getRemoteInstance().getCQGSInfo(new ObjectUuidPK(id));
		if(cqgsinfo.getState().equals(FDCBillStateEnum.AUDITTED)){
			MsgBox.showWarning("����˲���ɾ��");
			abort();

		}
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

	/**
	 * output actionAddLine_actionPerformed
	 */
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionAddLine_actionPerformed(e);
	}

	/**
	 * output actionCopyLine_actionPerformed
	 */
	public void actionCopyLine_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionCopyLine_actionPerformed(e);
	}

	/**
	 * output actionInsertLine_actionPerformed
	 */
	public void actionInsertLine_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionInsertLine_actionPerformed(e);
	}

	/**
	 * output actionRemoveLine_actionPerformed
	 */
	public void actionRemoveLine_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionRemoveLine_actionPerformed(e);
	}

	/**
	 * output actionCreateFrom_actionPerformed
	 */
	public void actionCreateFrom_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionCreateFrom_actionPerformed(e);
	}

	/**
	 * output actionCopyFrom_actionPerformed
	 */
	public void actionCopyFrom_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionCopyFrom_actionPerformed(e);
	}

	/**
	 * output actionAuditResult_actionPerformed
	 */
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionAuditResult_actionPerformed(e);
	}

	/**
	 * output actionTraceUp_actionPerformed
	 */
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionTraceUp_actionPerformed(e);
	}

	/**
	 * output actionTraceDown_actionPerformed
	 */
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionTraceDown_actionPerformed(e);
	}

	/**
	 * output actionViewSubmitProccess_actionPerformed
	 */
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionViewSubmitProccess_actionPerformed(e);
	}

	/**
	 * output actionViewDoProccess_actionPerformed
	 */
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionViewDoProccess_actionPerformed(e);
	}

	/**
	 * output actionMultiapprove_actionPerformed
	 */
	public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionMultiapprove_actionPerformed(e);
	}

	/**
	 * output actionNextPerson_actionPerformed
	 */
	public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionNextPerson_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionVoucher_actionPerformed
	 */
	public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionVoucher_actionPerformed(e);
	}

	/**
	 * output actionDelVoucher_actionPerformed
	 */
	public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionDelVoucher_actionPerformed(e);
	}

	/**
	 * output actionWorkFlowG_actionPerformed
	 */
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionWorkFlowG_actionPerformed(e);
	}

	/**
	 * output actionCreateTo_actionPerformed
	 */
	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionCreateTo_actionPerformed(e);
	}

	/**
	 * output actionSendingMessage_actionPerformed
	 */
	public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSendingMessage_actionPerformed(e);
	}

	/**
	 * output actionSignature_actionPerformed
	 */
	public void actionSignature_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSignature_actionPerformed(e);
	}

	/**
	 * output actionWorkflowList_actionPerformed
	 */
	public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionWorkflowList_actionPerformed(e);
	}

	/**
	 * output actionViewSignature_actionPerformed
	 */
	public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionViewSignature_actionPerformed(e);
	}

	/**
	 * output actionSendMail_actionPerformed
	 */
	public void actionSendMail_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionSendMail_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionNumberSign_actionPerformed
	 */
	public void actionNumberSign_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionNumberSign_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
	{
		return com.kingdee.eas.fdc.aimcost.costkf.CQGSFactory.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table)
	{

		return null;
	}
	
	/**
	 * ���úϼ���
	 * ���ԶԶ�����ͣ�
	 * @param columnName����������
	 * **/
	public static void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
		KDTFootManager footRowManager = tblMain.getFootManager();
		if (footRowManager == null) {
			String total = EASResource
					.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
			footRowManager = new KDTFootManager(tblMain);
			footRowManager.addFootView();
			tblMain.setFootManager(footRowManager);
			footRow = footRowManager.addFootRow(0);
			footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
			tblMain.getIndexColumn().setWidthAdjustMode((short) 1);
			tblMain.getIndexColumn().setWidth(30);
			footRowManager.addIndexText(0, total);
		} else {
			footRow = footRowManager.getFootRow(0);
		}
		int columnCount = tblMain.getColumnCount();
		for (int c = 0; c < columnCount; c++) {
			String fieldName = tblMain.getColumn(c).getKey();
			for (int i = 0; i < columnName.length; i++) {
				String colName = (String) columnName[i];
				if (colName.equalsIgnoreCase(fieldName)) {
					ICell cell = footRow.getCell(c);
					cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
					cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
					cell.setValue(getColumnValueSum(tblMain, colName));
				}
			}

		}
		footRow.getStyleAttributes().setBackground(
				new java.awt.Color(246, 246, 191));
	}
	
    public static BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	IRow row = table.getRow(i);
        	if(row.getStyleAttributes().getBackground().equals(Color.cyan)){
        		continue;
        	}
        	if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal)
        		sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        	else if(table.getRow(i).getCell(columnName).getValue()!=null 
        			&& table.getRow(i).getCell(columnName).getValue() instanceof String){
        		String value = (String)table.getRow(i).getCell(columnName).getValue();
        		value = value.replaceAll(",", "");
        		sum = sum.add(new BigDecimal(value));
        	}
        }
        return sum;
    }

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData()
	{
		if(getUIContext().get("CQGSInfo")!=null){
			CQGSInfo info = (CQGSInfo)getUIContext().get("CQGSInfo");
			info.setId(null);
			
			info.setState(FDCBillStateEnum.SAVED);
			info.setVersion(info.getVersion()+1);
			info.setLasted(false);
			for (int i = 0; i < info.getEntrys().size(); i++) 
				info.getEntrys().get(i).setId(null);
			return info;
		}else{
			com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo objectValue = new com.kingdee.eas.fdc.aimcost.costkf.CQGSInfo();
			objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
			
			objectValue.setCompany(SysContext.getSysContext().getCurrentFIUnit().getName());//��������ʱ������ǰ��˾
			
			CurProjectInfo projInfo = (CurProjectInfo)getUIContext().get("project"); 
			if(projInfo==null){
				MsgBox.showWarning("��ѡ��ڵ�");
				abort();
			}else{
					objectValue.setProjectName(projInfo);
			}

			FilterInfo filInfo = new FilterInfo();
			filInfo.getFilterItems().add(new FilterItemInfo("ProjectName.id",objectValue.getProjectName().getId()));
			
			try {
				if(CQGSFactory.getRemoteInstance().exists(filInfo)){
					MsgBox.showWarning("���е��ݲ�������");
					abort();
				}
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CQGSEntryInfo cqgsEntryInfo = new CQGSEntryInfo(); //newһ��������2������
			objectValue.getEntrys().add(new CQGSEntryInfo());	
			objectValue.getEntrys().add(new CQGSEntryInfo());	//������
			
			objectValue.setVersion(1); 
			return objectValue;
		}
	}

}