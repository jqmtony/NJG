/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.IPaymentLayout;
import com.kingdee.eas.fdc.contract.PaymentLayoutCollection;
import com.kingdee.eas.fdc.contract.PaymentLayoutFactory;
import com.kingdee.eas.fdc.contract.PaymentLayoutInfo;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanNewPayByStageInfo;
import com.kingdee.eas.fdc.finance.client.ContractPayNewTashListUI;
import com.kingdee.eas.fdc.finance.client.IPayLayoutNew;
import com.kingdee.eas.fdc.schedule.ContractAndTaskRelEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;

/**
 * output class name
 */
public class PaymentLayoutEditUI extends AbstractPaymentLayoutEditUI implements IPayLayoutNew
{
    private static final Logger logger = CoreUIObject.getLogger(PaymentLayoutEditUI.class);
    
    /**
     * output class constructor
     */
    public PaymentLayoutEditUI() throws Exception
    {
        super();
        jbInit();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output btnFenqi_mouseClicked method
     */
    protected void btnFenqi_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
        super.btnFenqi_mouseClicked(e);
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
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	//У�����,�����ظ�
    	String number = txtNumber.getText();
    	String id = "addnew".equalsIgnoreCase(oprtState)?"":editData.getId().toString();
    	if(UIRuleUtil.isNotNull(number)){
    		String where = " where number='"+number+"' ";
    		if(!"".equals(id)){
    			 where = "where number='"+number+"' and id!='"+id+"'";
    		}
    		IPaymentLayout ip = PaymentLayoutFactory.getRemoteInstance();
        	PaymentLayoutCollection pc = ip.getPaymentLayoutCollection(where);
        	if(pc.size()>0){
        		FDCMsgBox.showInfo("����滮����Ѿ�����,����������.");
        		return;
        	}
    	}else{
    		FDCMsgBox.showInfo("�����븶��滮���");
    		return;
    	}
    	if(UIRuleUtil.isNull(this.txtPlanName.getText())){
    		FDCMsgBox.showInfo("�����븶��滮����");
    		return;
    	}
    	if(UIRuleUtil.isNull(this.pkBizDate.getValue())){
    		FDCMsgBox.showInfo("�����븶��滮����");
    		return;
    	}
    	if(UIRuleUtil.isNull(this.txtPlanAmount.getBigDecimalValue())){
    		FDCMsgBox.showInfo("��Լ�滮���Ϊ��,���ܱ���.");
    		return;
    	}
    	autoCalMonPlan();
        super.actionSave_actionPerformed(e);
        mergeColumn();
        if(!this.chkProgressPayout.isSelected()){
        	disabledColumn(1);
        }
        PaymentLayoutListUI listUI = getUIContext().get("UI")==null?null:(PaymentLayoutListUI)getUIContext().get("UI");
        if(listUI!=null)
        	listUI.refreshList();
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
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
        PaymentLayoutListUI listUI = getUIContext().get("UI")==null?null:(PaymentLayoutListUI)getUIContext().get("UI");
        if(listUI!=null)
        	listUI.refreshList();
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
     * output importReWu_actionPerformed
     */
    public void importReWu_actionPerformed(ActionEvent e) throws Exception
    {
        super.importReWu_actionPerformed(e);
    }

    /**
     * output addNew_actionPerformed
     */
    public void addNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.addNew_actionPerformed(e);
    }

    /**
     * output deletePlanMingxi_actionPerformed
     */
    public void deletePlanMingxi_actionPerformed(ActionEvent e) throws Exception
    {
        super.deletePlanMingxi_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.contract.PaymentLayoutFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return new com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiInfo();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.contract.PaymentLayoutInfo objectValue = new com.kingdee.eas.fdc.contract.PaymentLayoutInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
    
	public void onShow() throws Exception {
		super.onShow();
		
		this.btnLoadLink.setVisible(false);
		this.kdAddRow.setVisible(false);
		this.kdDeleteRow.setVisible(false);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initButton();
		kdtPlanMingxi_detailPanel.getAddNewLineButton().setVisible(false);
		kdtPlanMingxi_detailPanel.getInsertLineButton().setVisible(false);
		kdtPlanMingxi_detailPanel.getRemoveLinesButton().setVisible(false);
		kdtPlam_detailPanel.getAddNewLineButton().setVisible(false);
		kdtPlam_detailPanel.getInsertLineButton().setVisible(false);
		kdtPlam_detailPanel.getRemoveLinesButton().setVisible(false);
		kdtPayByStages_detailPanel.getAddNewLineButton().setVisible(false);
		kdtPayByStages_detailPanel.getInsertLineButton().setVisible(false);
		kdtPayByStages_detailPanel.getRemoveLinesButton().setVisible(false);
		kdtPlanMingxi.getColumn(0).getStyleAttributes().setLocked(true);
		kdtPlam.getColumn("leiJiMoney").setWidth(120);
		kdtPlam.getColumn("leiJiBili").setWidth(130);
		this.txtPlanAmount.setEnabled(false);
		pkBizDate.setValue(new Date());
		this.kdtPayByStages.getColumn(0).getStyleAttributes().setLocked(true);
		
		if("addnew".equalsIgnoreCase(oprtState)){
			chkProgressPayout.setSelected(true);
			initKdtPlanMingxi();
		}else{
			mergeColumn();
			if(!this.chkProgressPayout.isSelected()){
	        	disabledColumn(1);
	        }
			for(int i=0; i<this.kdtPlanMingxi.getRowCount(); i++){
				String isEdit = this.kdtPlanMingxi.getCell(i,"isEdit").getValue()==null?"":String.valueOf(this.kdtPlanMingxi.getCell(i,"isEdit").getValue());
				if("0".equals(isEdit)){
					kdtPlanMingxi.getCell(i,"planStartDate").getStyleAttributes().setLocked(true);
					kdtPlanMingxi.getCell(i,"planEndDate").getStyleAttributes().setLocked(true);
				}
			}
		}
		this.kdtPayByStages.getColumn("month").getStyleAttributes().setNumberFormat(
        "%{yyyy-MM}t");
		kdtPlanMingxi.getColumn("dayNumber").getStyleAttributes().setNumberFormat(
        "%r{#,##0}f");
		BIMUDF0006.setVisible(false);
		KDPanel controlPanel = (KDPanel) this.kdtPayByStages.getParent()
				.getParent().getComponent(0);
		for (int i = 0; i < controlPanel.getComponentCount(); i++) {
			controlPanel.getComponent(i).setVisible(false);
		}
		controlPanel = (KDPanel) this.kdtPlam.getParent().getParent()
				.getComponent(0);
		for (int i = 0; i < controlPanel.getComponentCount(); i++) {
			controlPanel.getComponent(i).setVisible(false);
		}
		controlPanel = (KDPanel) this.kdtPlanMingxi.getParent().getParent()
				.getComponent(0);
		for (int i = 0; i < controlPanel.getComponentCount(); i++) {
			controlPanel.getComponent(i).setVisible(false);
		}
		
		//����f7ѡ���
		getF7EditerFilter("com.kingdee.eas.fdc.schedule.app.FDCScheduleTaskQuery");
	}

	private void initButton(){
		this.btnAddNew.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnCopyFrom.setVisible(false);
		this.btnCopyLine.setVisible(false);
		this.btnCreateFrom.setVisible(false);
		this.btnCreateTo.setVisible(false);
		this.btnDelVoucher.setVisible(false);
		btnloadLinkAssign.setVisible(false);
	}
	/**
	 * ��ʼ���ƻ���ϸ���
	 */
	private void initKdtPlanMingxi(){
		kdtPlanMingxi.addRows(12);
		kdtPlanMingxi.getCell(0, "shixiang").setValue("�����");
		for(int i=1; i<9; i++){
			kdtPlanMingxi.getCell(i, "shixiang").setValue("���ȿ�");
		}
		kdtPlanMingxi.getCell(9, "shixiang").setValue("�����");
		kdtPlanMingxi.getCell(10, "shixiang").setValue("��֤��");
		kdtPlanMingxi.getCell(11, "shixiang").setValue("��������");
		// ��ȡ�ںϹ�����
		KDTMergeManager mm = kdtPlanMingxi.getMergeManager();
		// �ں�ָ������
		mm.mergeBlock(1, 0, 8, 0, KDTMergeManager.SPECIFY_MERGE);
	}
	/**
	 * ���ȿ����
	 */
	protected void btnFenqi_actionPerformed(ActionEvent e) throws Exception {
		super.btnFenqi_actionPerformed(e);
		Date st_temp = null;
		Date end_temp = null;
		for(int i=0; i<this.kdtPlanMingxi.getRowCount(); i++){
			IRow row = this.kdtPlanMingxi.getRow(i);
			if("���ȿ�".equals(String.valueOf(row.getCell("shixiang").getValue()).trim())){
				Date st = row.getCell("planStratDate").getValue()==null?null:(Date)row.getCell("planStratDate").getValue();
				Date end = row.getCell("planEndDate").getValue()==null?null:(Date)row.getCell("planEndDate").getValue();
				if(st!=null){
					if(st_temp!=null){
						if(st.before(st_temp)){
							st_temp=st;
						}
					}else{
						st_temp = st;
					}
				}
				if(end!=null){
					if(end_temp!=null){
						if(end.after(end_temp)){
							end_temp=end;
						}
					}else{
						end_temp = end;
					}
				}
			}
		}
		if(st_temp!=null && end_temp!=null){
			int num = getMonthNum(st_temp,end_temp);
			Calendar cal=Calendar.getInstance();
			this.kdtPayByStages.removeRows();
			for(int j=0; j<num+1; j++){
				cal.setTime(st_temp);
				cal.add(Calendar.MONTH, j);
				cal.set(Calendar.DAY_OF_MONTH, 25);
				Date temp = cal.getTime();
				IRow row = this.kdtPayByStages.addRow();
				row.getCell("month").setValue(new SimpleDateFormat("yyyy-MM").parse(temp.toLocaleString()));
//				row.getCell("payDate").setValue(new SimpleDateFormat("yyyy-M-d").parse(temp.toLocaleString()));
			}
		}
	}
	
	public static int getMonthNum(Date date1,Date date2)
	{
		Calendar cal1=Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2=Calendar.getInstance();
		cal2.setTime(date2);
		return (cal2.get(1)-cal1.get(1))*12+(cal2.get(2)-cal1.get(2));
	}

	/**
	 * �Ƿ�������ȸ���
	 */
	protected void chkProgressPayout_itemStateChanged(ItemEvent e)
			throws Exception {
		super.chkProgressPayout_itemStateChanged(e);
		if(1==e.getStateChange()){
			this.btnFenqi.setVisible(false);
			this.kdtPayByStages.setVisible(false);
			this.kdtPayByStages.getParent().getParent().setVisible(false);
			disabledColumn(0);
		}else{
			this.btnFenqi.setVisible(true);
			this.kdtPayByStages.setVisible(true);
			this.kdtPayByStages.getParent().getParent().setVisible(true);
			disabledColumn(1);
		}
	}
	/**
	 * ѡ�а�������ȸ����,������ϸ����е�ĳЩ������Ϊ������
	 */
	private void disabledColumn(int flag){
		for(int i=0; i<kdtPlanMingxi.getRowCount();i++){
			IRow row = kdtPlanMingxi.getRow(i);
			String sx = String.valueOf(row.getCell("shixiang").getValue()).trim();
			if("���ȿ�".equals(sx)){
				if(1==flag){
					row.getCell(4).getStyleAttributes().setLocked(true);
					row.getCell(5).getStyleAttributes().setLocked(true);
					row.getCell(6).getStyleAttributes().setLocked(true);
					row.getCell(7).getStyleAttributes().setLocked(true);
					row.getCell(4).setValue(null);
					row.getCell(5).setValue(null);
					row.getCell(6).setValue(null);
					row.getCell(7).setValue(null);
					row.getCell(4).getStyleAttributes().setBackground(Color.LIGHT_GRAY);
					row.getCell(5).getStyleAttributes().setBackground(Color.LIGHT_GRAY);
					row.getCell(6).getStyleAttributes().setBackground(Color.LIGHT_GRAY);
					row.getCell(7).getStyleAttributes().setBackground(Color.LIGHT_GRAY);
				}else{
					row.getCell(4).getStyleAttributes().setLocked(false);
					row.getCell(5).getStyleAttributes().setLocked(false);
					row.getCell(6).getStyleAttributes().setLocked(false);
					row.getCell(7).getStyleAttributes().setLocked(false);
					row.getCell(4).getStyleAttributes().setBackground(Color.white);
					row.getCell(5).getStyleAttributes().setBackground(Color.white);
					row.getCell(6).getStyleAttributes().setBackground(Color.white);
					row.getCell(7).getStyleAttributes().setBackground(Color.white);
				}
			}
		}
	}
	/**
	 * ��������ť(û���õ�)
	 */
	protected void btnLoadLink_actionPerformed(ActionEvent e) throws Exception {
		super.btnLoadLink_actionPerformed(e);
	}
	/**
	 * ��������ť(û���õ�)
	 */
	protected void kdAddRow_actionPerformed(ActionEvent e) throws Exception {
		super.kdAddRow_actionPerformed(e);

	}
	/**
	 * ��������ť(û���õ�)
	 */
	protected void kdDeleteRow_actionPerformed(ActionEvent e) throws Exception {
		super.kdDeleteRow_actionPerformed(e);
	}
	/**
	 * ������
	 */
	protected void btnAddRow_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddRow_actionPerformed(e);
		int aIndex = this.kdtPlanMingxi.getSelectManager().getActiveRowIndex();
		if(aIndex<0){
			FDCMsgBox.showInfo("��ѡ�������б�����Ҫ������ͬ��Ŀ��һ��.");
			return;
		}
		String xm = String.valueOf(this.kdtPlanMingxi.getCell(aIndex,0).getValue()).trim();
		if(UIRuleUtil.isNotNull(xm)){
			IRow row = this.kdtPlanMingxi.addRow(aIndex+1);
			row.getCell(0).setValue(xm);
			mergeColumn();
			if("���ȿ�".equals(xm) && !this.chkProgressPayout.isSelected()){
				disabledColumn(1);
			}
		}
	}
	/**
	 * �ں� ��������
	 */
	private void mergeColumn(){
		if(this.kdtPlanMingxi.getRowCount()>0){
			// ��ȡ�ںϹ�����
			KDTMergeManager mm = kdtPlanMingxi.getMergeManager();
			// �ں�ָ������
			String temp = String.valueOf(this.kdtPlanMingxi.getCell(0,0).getValue()).trim();
			int top = 0;
			for(int i=1; i<this.kdtPlanMingxi.getRowCount(); i++){
				String sx = String.valueOf(this.kdtPlanMingxi.getCell(i,0).getValue()).trim();
				if(!temp.equals(sx)){
					mm.mergeBlock(top, 0, i-1, 0, KDTMergeManager.SPECIFY_MERGE);
					logger.info("top:"+top+" bottom:"+(i-1));
					top = i;
					temp = sx;
				}
			}
		}
	}
	/**
	 * �����������ť
	 */
	protected void btnloadLinkAssign_actionPerformed(ActionEvent e)
			throws Exception {
		super.btnloadLinkAssign_actionPerformed(e);
		if(kdtPlanMingxi.getSelectManager().getActiveRowIndex()<0){
			FDCMsgBox.showInfo("��ѡ��һ����������.");
			return;
		}
		String ContractCd = this.ContractPlanNo.getText();
		if(ContractCd!=null && ContractCd.length()>0 ){
			UIContext uiContext = new UIContext(this);
	    	uiContext.put("UI", this);
//	    	uiContext.put("ContractCd", "vgA5SakfTuqApWdGoB4Z0A1t0fQ=");
	    	uiContext.put("ContractCd", ContractCd);
			IUIFactory uiFactory = null;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWWIN);
			IUIWindow curDialog = uiFactory.create(ContractPayNewTashListUI.class
					.getName(), uiContext, null, OprtState.VIEW);
			curDialog.show();
		}
	}
	/**
	 * �ӹ���������
	 */
	public void handleResult(ContractAndTaskRelEntryInfo entryInfo) {
		if(UIRuleUtil.isNotNull(entryInfo)){
			IRow row = kdtPlanMingxi.getRow(kdtPlanMingxi.getSelectManager().getActiveRowIndex());
			row.getCell("shixiangmx").setValue(entryInfo.getWbs());
			row.getCell("planStartDate").setValue(entryInfo.getTask().getStart());
			row.getCell("planEndDate").setValue(entryInfo.getTask().getEnd());
			row.getCell("planStartDate").getStyleAttributes().setLocked(true);
			row.getCell("planEndDate").getStyleAttributes().setLocked(true);
			row.getCell("isEdit").setValue("0");//���ɱ༭
		}
	}
	
	/**
	 * ɾ����
	 */
	protected void btnRemoveRow_actionPerformed(ActionEvent e) throws Exception {
		super.btnRemoveRow_actionPerformed(e);
		int rowIndex = this.kdtPlanMingxi.getSelectManager().getActiveRowIndex();
		if(rowIndex<0){
			FDCMsgBox.showInfo("��ѡ��Ҫɾ������.");
			return;
		}
		boolean isXm = false;
		String xm = String.valueOf(this.kdtPlanMingxi.getCell(rowIndex,0).getValue()).trim();
		for(int i=0; i<this.kdtPlanMingxi.getRowCount(); i++){
			if(i!=rowIndex){
				if(xm.equals(String.valueOf(this.kdtPlanMingxi.getCell(i,0).getValue()).trim())){
					isXm = true;
				}
			}
		}
		if(isXm){
			this.kdtPlanMingxi.removeRow(rowIndex);
		}else{
			FDCMsgBox.showInfo("��������ģ��,������ɾ��.");
			return;
		}
	}
	/**
	 * ����ƻ����༭
	 */
	protected void kdtPlanMingxi_editStopped(KDTEditEvent e) throws Exception {
		super.kdtPlanMingxi_editStopped(e);
		if(e.getOldValue()!=null && e.getOldValue().equals(e.getValue())){
			return;
		}
		IRow row = this.kdtPlanMingxi.getRow(e.getRowIndex());
		//���ݸ�����ϸ,�Զ������ƻ���ʼʱ��ͼƻ�����ʱ��
		if(1==e.getColIndex()){
			if(UIRuleUtil.isNotNull(e.getValue())){
				FDCScheduleTaskInfo info = (FDCScheduleTaskInfo)e.getValue();
				row.getCell("planStratDate").setValue(info.getStart());
				row.getCell("planEndDate").setValue(info.getEnd());
				row.getCell("isEdit").setValue("0");
				
				int dayNum = UIRuleUtil.getIntValue(row.getCell("dayNumber").getValue());
				if(UIRuleUtil.isNotNull(info.getEnd())){
					if(!this.chkProgressPayout.isSelected() && "���ȿ�".equals(String.valueOf(row.getCell("shixiang").getValue()))){
						return ;
					}
					Date planDate = info.getEnd();
					Calendar cal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������    
					cal.setTime(planDate);
					cal.add(Calendar.DAY_OF_MONTH, dayNum);//ȡ��ǰ���ڵ�ǰһ��.    
					row.getCell("planDate").setValue(cal.getTime());
				}
			}
		}
		
		if(3==e.getColIndex() || 4==e.getColIndex()){
			logger.info("source :"+e.getSource());
			int dayNum = UIRuleUtil.getIntValue(row.getCell("dayNumber").getValue());
			if(UIRuleUtil.isNotNull(row.getCell("planEndDate").getValue())){
				if(!this.chkProgressPayout.isSelected() && "���ȿ�".equals(String.valueOf(row.getCell("shixiang").getValue()))){
					return ;
				}
				Date planDate = (Date)row.getCell("planEndDate").getValue();
				Calendar cal = Calendar.getInstance();//ʹ��Ĭ��ʱ�������Ի������һ��������    
				cal.setTime(planDate);
				cal.add(Calendar.DAY_OF_MONTH, dayNum);//ȡ��ǰ���ڵ�ǰһ��.    
				row.getCell("planDate").setValue(cal.getTime());
			}
		}
		BigDecimal total = this.txtPlanAmount.getBigDecimalValue();
		if(6==e.getColIndex()){
			BigDecimal bl = UIRuleUtil.getBigDecimal(row.getCell("fukuanBili").getValue()==null?"0":row.getCell("fukuanBili").getValue()).multiply(new BigDecimal("0.01"));
			row.getCell("yingfuMoney").setValue(bl.multiply(total));
		}
		if(7==e.getColIndex()){
			BigDecimal money = UIRuleUtil.getBigDecimal(row.getCell("yingfuMoney").getValue()==null?"0":row.getCell("yingfuMoney").getValue());
			if(new BigDecimal("0").compareTo(total)!=0){
				BigDecimal bl = money.divide(total,2);
				row.getCell("fukuanBili").setValue(bl.multiply(new BigDecimal("100")));
			}
		}
	}
	/**
	 * ������(���ݼƻ������������ƻ�������)
	 */
	protected void kdtPayByStages_editStopped(KDTEditEvent e) throws Exception {
		super.kdtPayByStages_editStopped(e);
		IRow row = this.kdtPayByStages.getRow(e.getRowIndex());
		BigDecimal total = this.txtPlanAmount.getBigDecimalValue();
		if(2==e.getColIndex()){
			BigDecimal bl = UIRuleUtil.getBigDecimal(row.getCell("payBili").getValue()==null?"0":row.getCell("payBili").getValue()).multiply(new BigDecimal("0.01"));
			row.getCell("payMoney").setValue(bl.multiply(total));
		}
		if(3==e.getColIndex()){
			BigDecimal money = UIRuleUtil.getBigDecimal(row.getCell("payMoney").getValue()==null?"0":row.getCell("payMoney").getValue());
			if(new BigDecimal("0").compareTo(money)!=0){
				BigDecimal bl = money.divide(total,2);
				row.getCell("payBili").setValue(bl.multiply(new BigDecimal("100")));
			}
		}
	}
	
	/**
	 * �ڷ���И���f7
	 * @param queryStr query�İ�·��
	 * @return
	 */
	private void getF7EditerFilter(String queryStr) {
		final KDBizPromptBox kdtEntrys_material_PromptBox = new KDBizPromptBox(); 
		kdtEntrys_material_PromptBox.setQueryInfo(queryStr); 
		kdtEntrys_material_PromptBox.setVisible(true); 
		kdtEntrys_material_PromptBox.setEditable(true); 
		kdtEntrys_material_PromptBox.setDisplayFormat("$number$"); 
		kdtEntrys_material_PromptBox.setEditFormat("$number$"); 
		kdtEntrys_material_PromptBox.setCommitFormat("$number$"); 
		
		FilterInfo filter = new FilterInfo();
		if (UIRuleUtil.isNotNull(this.getUIContext().get("projectId"))) {
			logger.info("--projectId:" + getUIContext().get("projectId").toString());
			String sql = "select fid from t_sch_schedule where fprojectid="
					+ getUIContext().get("projectId").toString() + "";
			sql = "select fid from t_sch_fdcscheduletask where fscheduleid in(select fid from t_sch_fdcschedule  where " +
					" fprojectid ='"+getUIContext().get("projectId").toString()+"' and fislatestver = 1 )";
			filter.getFilterItems()
			.add(
					new FilterItemInfo("id", sql,
							CompareType.INNER));
			EntityViewInfo env = new EntityViewInfo();
			env.setFilter(filter);
			logger.info("---filter:"+filter);
			kdtEntrys_material_PromptBox.setEntityViewInfo(env);//��ʱ������
		}
		KDTDefaultCellEditor kdtEntrys_material_CellEditor = new KDTDefaultCellEditor(kdtEntrys_material_PromptBox); 
		kdtPlanMingxi.getColumn("shixiangmx").setEditor(kdtEntrys_material_CellEditor); 
		ObjectValueRender kdtEntrys_material_OVR = new ObjectValueRender(); 
		kdtEntrys_material_OVR.setFormat(new BizDataFormat("$name$")); 
		kdtPlanMingxi.getColumn("shixiangmx").setRenderer(kdtEntrys_material_OVR); 
	}
	
	/**
	 * �ڷ���И���f7
	 * @param queryStr query�İ�·��
	 * @return
	 */
	private KDTDefaultCellEditor getF7Editer(String queryStr){
	   KDBizPromptBox departmentPromptBox = new KDBizPromptBox();
	   departmentPromptBox.setQueryInfo(queryStr);
	   departmentPromptBox.setVisible(true);
	   departmentPromptBox.setEditable(true);
	   departmentPromptBox.setDisplayFormat("$name$");
	   departmentPromptBox.setEditFormat("$name$");
	   departmentPromptBox.setCommitFormat("$name$");
	   KDTDefaultCellEditor departmentCellEditor = new KDTDefaultCellEditor(departmentPromptBox);
	   return departmentCellEditor;
	}
	/**
	 * ҳǩ�л�
	 */
	public void paneBIZLayerControl21_stateChanged(javax.swing.event.ChangeEvent e){
		if(paneBIZLayerControl21.getSelectedIndex()!=1){
			return;
		}
		autoCalMonPlan();
	}
	/**
	 * �Զ������¶ȸ���ƻ�����
	 */
	private void autoCalMonPlan(){
		BigDecimal planAmount = txtPlanAmount.getBigDecimalValue();
		BigDecimal ljbl = new BigDecimal("0");
		BigDecimal ljje = new BigDecimal("0.00");
		this.kdtPlam.removeRows();
		Map map = new TreeMap();
		if(this.chkProgressPayout.isSelected()){
			map = createMonthPlan();
		}else{
			map = createMonthPlan2();
		}
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry en = (Map.Entry) it.next();
			String date = String.valueOf(en.getKey());
			BigDecimal money = UIRuleUtil.getBigDecimal(en.getValue());
			if(date.length()==7){
				IRow row = this.kdtPlam.addRow();
				row.getCell("planPayMonth").setValue(date.substring(0, 7));
				row.getCell("planPaymoney").setValue(money);
				ljje = ljje.add(money);
				row.getCell("leiJiMoney").setValue(ljje);
				//����
				if(planAmount!=null && planAmount.compareTo(new BigDecimal("0"))!=0){
					row.getCell("planPayBili").setValue(money.multiply(new BigDecimal("100")).divide(planAmount, 2));
					ljbl = ljbl.add(money.multiply(new BigDecimal("100")).divide(planAmount, 2));
					row.getCell("leiJiBili").setValue(ljbl);
				}
				
			}
		}
	}
	/**
	 * ���ݼƻ���ϸ�Զ������¶ȸ���ƻ�
	 */
	private Map createMonthPlan(){
		int rowCount = this.kdtPlanMingxi.getRowCount();
		Map map = new TreeMap();
		for(int i=0; i<rowCount; i++){
			//�ƻ���������
			Date planDate = kdtPlanMingxi.getCell(i, "planDate").getValue()==null?null:(Date)kdtPlanMingxi.getCell(i, "planDate").getValue();
			//�ƻ�������
			BigDecimal yingfuMoney = UIRuleUtil.getBigDecimal(kdtPlanMingxi.getCell(i,"yingfuMoney").getValue());
			if(UIRuleUtil.isNotNull(planDate)){
				String date = new SimpleDateFormat("yyyy-MM").format(planDate);
				if(map.containsKey(date)){
					map.put(date,UIRuleUtil.getBigDecimal(map.get(date)).add(yingfuMoney));
				}else{
					map.put(date, yingfuMoney);
				}
			}
		}
		return map;
	}
	
	/**
	 * ���ݽ��ȿ�����Զ������¶ȸ���ƻ�
	 * @return
	 */
	private Map createMonthPlan2(){
		Map map = new TreeMap();
		ContractPayPlanNewPayByStageCollection col = new ContractPayPlanNewPayByStageCollection();
		for (int i = 0; i < this.kdtPlanMingxi.getRowCount(); i++) {
			if (kdtPlanMingxi.getCell(i, "shixiang").getValue() != null
					&& !"���ȿ�".equals(kdtPlanMingxi.getCell(i, "shixiang")
							.getValue().toString().trim())) {
				ContractPayPlanNewPayByStageInfo info = new ContractPayPlanNewPayByStageInfo();
				info.setMonth(UIRuleUtil.getDateValue(kdtPlanMingxi.getCell(i, "planDate").getValue()));
				info.setPayMoney(UIRuleUtil.getBigDecimal(kdtPlanMingxi.getCell(i, "yingfuMoney").getValue()));
				col.add(info);
			}
		}
		int rowCount = this.kdtPayByStages.getRowCount();
		for(int i=0; i<rowCount; i++){
			Date planDate = kdtPayByStages.getCell(i, "month").getValue()==null?null:(Date)kdtPayByStages.getCell(i, "month").getValue();
			//�ƻ�������
			BigDecimal yingfuMoney = UIRuleUtil.getBigDecimal(kdtPayByStages.getCell(i,"payMoney").getValue());
			ContractPayPlanNewPayByStageInfo info = new ContractPayPlanNewPayByStageInfo();
			info.setMonth(planDate);
			info.setPayMoney(yingfuMoney);
			col.add(info);
		}
		for(int i=0; i<col.size(); i++){
			ContractPayPlanNewPayByStageInfo info = col.get(i);
			Date planDate = info.getMonth();
			BigDecimal yingfuMoney = info.getPayMoney();
			if(UIRuleUtil.isNotNull(planDate)){
				String date = new SimpleDateFormat("yyyy-MM").format(planDate);
				if(map.containsKey(date)){
					map.put(date,UIRuleUtil.getBigDecimal(map.get(date)).add(yingfuMoney));
				}else{
					map.put(date, yingfuMoney);
				}
			}
		}
		return map;
	}
	
	/**
     * output kdtPlanMingxi_tableClicked method
     */
    protected void kdtPlanMingxi_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	String isEdit = String.valueOf(kdtPlanMingxi.getCell(e.getRowIndex(), "isEdit").getValue());
    	if("0".equals(isEdit)){
    		if("1,2,3".indexOf(e.getColIndex())!=-1){
    			FDCMsgBox.showInfo("�ӹ��������������,�������޸�.");
    			return;
    		}
    	}
    }
    
	public void handleResult(Object o, PaymentLayoutInfo info) {
		
	}
	protected KDTable getDetailTable() {
		return null;
	}
	
	/**
     * output jbInit method
     */
    private void jbInit() throws Exception {
		this.kdtPlanMingxi.checkParsed();
		KDTextField kdtPlanMingxi_shixiang_TextField = new KDTextField();
		kdtPlanMingxi_shixiang_TextField
				.setName("kdtPlanMingxi_shixiang_TextField");
		kdtPlanMingxi_shixiang_TextField.setMaxLength(100);
		KDTDefaultCellEditor kdtPlanMingxi_shixiang_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_shixiang_TextField);
		this.kdtPlanMingxi.getColumn("shixiang").setEditor(
				kdtPlanMingxi_shixiang_CellEditor);
		
		
		KDDatePicker kdtPlanMingxi_planStratDate_DatePicker = new KDDatePicker();
		kdtPlanMingxi_planStratDate_DatePicker
				.setName("kdtPlanMingxi_planStratDate_DatePicker");
		kdtPlanMingxi_planStratDate_DatePicker.setVisible(true);
		kdtPlanMingxi_planStratDate_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtPlanMingxi_planStratDate_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_planStratDate_DatePicker);
		this.kdtPlanMingxi.getColumn("planStratDate").setEditor(
				kdtPlanMingxi_planStratDate_CellEditor);
		KDDatePicker kdtPlanMingxi_planEndDate_DatePicker = new KDDatePicker();
		kdtPlanMingxi_planEndDate_DatePicker
				.setName("kdtPlanMingxi_planEndDate_DatePicker");
		kdtPlanMingxi_planEndDate_DatePicker.setVisible(true);
		kdtPlanMingxi_planEndDate_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtPlanMingxi_planEndDate_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_planEndDate_DatePicker);
		this.kdtPlanMingxi.getColumn("planEndDate").setEditor(
				kdtPlanMingxi_planEndDate_CellEditor);
		KDFormattedTextField kdtPlanMingxi_dayNumber_TextField = new KDFormattedTextField();
		kdtPlanMingxi_dayNumber_TextField
				.setName("kdtPlanMingxi_dayNumber_TextField");
		kdtPlanMingxi_dayNumber_TextField.setVisible(true);
		kdtPlanMingxi_dayNumber_TextField.setEditable(true);
		kdtPlanMingxi_dayNumber_TextField.setHorizontalAlignment(2);
		kdtPlanMingxi_dayNumber_TextField.setDataType(1);
		kdtPlanMingxi_dayNumber_TextField
				.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
		kdtPlanMingxi_dayNumber_TextField
				.setMaximumValue(new java.math.BigDecimal("1.0E18"));
		kdtPlanMingxi_dayNumber_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlanMingxi_dayNumber_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_dayNumber_TextField);
		this.kdtPlanMingxi.getColumn("dayNumber").setEditor(
				kdtPlanMingxi_dayNumber_CellEditor);
		KDDatePicker kdtPlanMingxi_planDate_DatePicker = new KDDatePicker();
		kdtPlanMingxi_planDate_DatePicker
				.setName("kdtPlanMingxi_planDate_DatePicker");
		kdtPlanMingxi_planDate_DatePicker.setVisible(true);
		kdtPlanMingxi_planDate_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtPlanMingxi_planDate_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_planDate_DatePicker);
		this.kdtPlanMingxi.getColumn("planDate").setEditor(
				kdtPlanMingxi_planDate_CellEditor);
		KDFormattedTextField kdtPlanMingxi_fukuanBili_TextField = new KDFormattedTextField();
		kdtPlanMingxi_fukuanBili_TextField
				.setName("kdtPlanMingxi_fukuanBili_TextField");
		kdtPlanMingxi_fukuanBili_TextField.setVisible(true);
		kdtPlanMingxi_fukuanBili_TextField.setEditable(true);
		kdtPlanMingxi_fukuanBili_TextField.setHorizontalAlignment(2);
		kdtPlanMingxi_fukuanBili_TextField.setDataType(1);
		kdtPlanMingxi_fukuanBili_TextField
				.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
		kdtPlanMingxi_fukuanBili_TextField
				.setMaximumValue(new java.math.BigDecimal("1.0E18"));
		kdtPlanMingxi_fukuanBili_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlanMingxi_fukuanBili_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_fukuanBili_TextField);
		this.kdtPlanMingxi.getColumn("fukuanBili").setEditor(
				kdtPlanMingxi_fukuanBili_CellEditor);
		KDFormattedTextField kdtPlanMingxi_yingfuMoney_TextField = new KDFormattedTextField();
		kdtPlanMingxi_yingfuMoney_TextField
				.setName("kdtPlanMingxi_yingfuMoney_TextField");
		kdtPlanMingxi_yingfuMoney_TextField.setVisible(true);
		kdtPlanMingxi_yingfuMoney_TextField.setEditable(true);
		kdtPlanMingxi_yingfuMoney_TextField.setHorizontalAlignment(2);
		kdtPlanMingxi_yingfuMoney_TextField.setDataType(1);
		kdtPlanMingxi_yingfuMoney_TextField
				.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
		kdtPlanMingxi_yingfuMoney_TextField
				.setMaximumValue(new java.math.BigDecimal("1.0E18"));
		kdtPlanMingxi_yingfuMoney_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlanMingxi_yingfuMoney_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_yingfuMoney_TextField);
		this.kdtPlanMingxi.getColumn("yingfuMoney").setEditor(
				kdtPlanMingxi_yingfuMoney_CellEditor);

		KDTextField kdtPlanMingxi_isEdit_TextField = new KDTextField();
		kdtPlanMingxi_isEdit_TextField
				.setName("kdtPlanMingxi_isEdit_TextField");
		kdtPlanMingxi_isEdit_TextField.setMaxLength(1);
		KDTDefaultCellEditor kdtPlanMingxi_isEdit_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_isEdit_TextField);
		this.kdtPlanMingxi.getColumn("isEdit").setEditor(
				kdtPlanMingxi_isEdit_CellEditor);

		KDTextField kdtPlanMingxi_remark_TextField = new KDTextField();
		kdtPlanMingxi_remark_TextField
				.setName("kdtPlanMingxi_remark_TextField");
		kdtPlanMingxi_remark_TextField.setMaxLength(200);
		KDTDefaultCellEditor kdtPlanMingxi_remark_CellEditor = new KDTDefaultCellEditor(
				kdtPlanMingxi_remark_TextField);
		this.kdtPlanMingxi.getColumn("remark").setEditor(
				kdtPlanMingxi_remark_CellEditor);

		// kdtPlam
		this.kdtPlam.checkParsed();
		KDTextField kdtPlam_planPayMonth_DatePicker = new KDTextField();
		kdtPlam_planPayMonth_DatePicker
				.setName("kdtPlam_planPayMonth_DatePicker");
		kdtPlam_planPayMonth_DatePicker.setMaxLength(20);
		KDTDefaultCellEditor kdtPlam_planPayMonth_CellEditor = new KDTDefaultCellEditor(
				kdtPlam_planPayMonth_DatePicker);
		this.kdtPlam.getColumn("planPayMonth").setEditor(
				kdtPlam_planPayMonth_CellEditor);
		KDFormattedTextField kdtPlam_planPayBili_TextField = new KDFormattedTextField();
		kdtPlam_planPayBili_TextField.setName("kdtPlam_planPayBili_TextField");
		kdtPlam_planPayBili_TextField.setVisible(true);
		kdtPlam_planPayBili_TextField.setEditable(true);
		kdtPlam_planPayBili_TextField.setHorizontalAlignment(2);
		kdtPlam_planPayBili_TextField.setDataType(1);
		kdtPlam_planPayBili_TextField.setMinimumValue(new java.math.BigDecimal(
				"-1.0E18"));
		kdtPlam_planPayBili_TextField.setMaximumValue(new java.math.BigDecimal(
				"1.0E18"));
		kdtPlam_planPayBili_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlam_planPayBili_CellEditor = new KDTDefaultCellEditor(
				kdtPlam_planPayBili_TextField);
		this.kdtPlam.getColumn("planPayBili").setEditor(
				kdtPlam_planPayBili_CellEditor);
		KDFormattedTextField kdtPlam_planPaymoney_TextField = new KDFormattedTextField();
		kdtPlam_planPaymoney_TextField
				.setName("kdtPlam_planPaymoney_TextField");
		kdtPlam_planPaymoney_TextField.setVisible(true);
		kdtPlam_planPaymoney_TextField.setEditable(true);
		kdtPlam_planPaymoney_TextField.setHorizontalAlignment(2);
		kdtPlam_planPaymoney_TextField.setDataType(1);
		kdtPlam_planPaymoney_TextField
				.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
		kdtPlam_planPaymoney_TextField
				.setMaximumValue(new java.math.BigDecimal("1.0E18"));
		kdtPlam_planPaymoney_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlam_planPaymoney_CellEditor = new KDTDefaultCellEditor(
				kdtPlam_planPaymoney_TextField);
		this.kdtPlam.getColumn("planPaymoney").setEditor(
				kdtPlam_planPaymoney_CellEditor);
		KDFormattedTextField kdtPlam_leiJiMoney_TextField = new KDFormattedTextField();
		kdtPlam_leiJiMoney_TextField.setName("kdtPlam_leiJiMoney_TextField");
		kdtPlam_leiJiMoney_TextField.setVisible(true);
		kdtPlam_leiJiMoney_TextField.setEditable(true);
		kdtPlam_leiJiMoney_TextField.setHorizontalAlignment(2);
		kdtPlam_leiJiMoney_TextField.setDataType(1);
		kdtPlam_leiJiMoney_TextField.setMinimumValue(new java.math.BigDecimal(
				"-1.0E18"));
		kdtPlam_leiJiMoney_TextField.setMaximumValue(new java.math.BigDecimal(
				"1.0E18"));
		kdtPlam_leiJiMoney_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlam_leiJiMoney_CellEditor = new KDTDefaultCellEditor(
				kdtPlam_leiJiMoney_TextField);
		this.kdtPlam.getColumn("leiJiMoney").setEditor(
				kdtPlam_leiJiMoney_CellEditor);
		KDFormattedTextField kdtPlam_leiJiBili_TextField = new KDFormattedTextField();
		kdtPlam_leiJiBili_TextField.setName("kdtPlam_leiJiBili_TextField");
		kdtPlam_leiJiBili_TextField.setVisible(true);
		kdtPlam_leiJiBili_TextField.setEditable(true);
		kdtPlam_leiJiBili_TextField.setHorizontalAlignment(2);
		kdtPlam_leiJiBili_TextField.setDataType(1);
		kdtPlam_leiJiBili_TextField.setMinimumValue(new java.math.BigDecimal(
				"-1.0E18"));
		kdtPlam_leiJiBili_TextField.setMaximumValue(new java.math.BigDecimal(
				"1.0E18"));
		kdtPlam_leiJiBili_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPlam_leiJiBili_CellEditor = new KDTDefaultCellEditor(
				kdtPlam_leiJiBili_TextField);
		this.kdtPlam.getColumn("leiJiBili").setEditor(
				kdtPlam_leiJiBili_CellEditor);

		KDTextField kdtPlam_remark_TextField = new KDTextField();
		kdtPlam_remark_TextField.setName("kdtPlam_remark_TextField");
		kdtPlam_remark_TextField.setMaxLength(200);
		KDTDefaultCellEditor kdtPlam_remark_CellEditor = new KDTDefaultCellEditor(
				kdtPlam_remark_TextField);
		this.kdtPlam.getColumn("remark").setEditor(kdtPlam_remark_CellEditor);

		//kdtPayByStages
		this.kdtPayByStages.checkParsed();
		KDDatePicker kdtPayByStages_month_DatePicker = new KDDatePicker();
		kdtPayByStages_month_DatePicker
				.setName("kdtPayByStages_month_DatePicker");
		kdtPayByStages_month_DatePicker.setVisible(true);
		kdtPayByStages_month_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtPayByStages_month_CellEditor = new KDTDefaultCellEditor(
				kdtPayByStages_month_DatePicker);
		this.kdtPayByStages.getColumn("month").setEditor(
				kdtPayByStages_month_CellEditor);
		KDDatePicker kdtPayByStages_payDate_DatePicker = new KDDatePicker();
		kdtPayByStages_payDate_DatePicker
				.setName("kdtPayByStages_payDate_DatePicker");
		kdtPayByStages_payDate_DatePicker.setVisible(true);
		kdtPayByStages_payDate_DatePicker.setEditable(true);
		KDTDefaultCellEditor kdtPayByStages_payDate_CellEditor = new KDTDefaultCellEditor(
				kdtPayByStages_payDate_DatePicker);
		this.kdtPayByStages.getColumn("payDate").setEditor(
				kdtPayByStages_payDate_CellEditor);
		KDFormattedTextField kdtPayByStages_payBili_TextField = new KDFormattedTextField();
		kdtPayByStages_payBili_TextField
				.setName("kdtPayByStages_payBili_TextField");
		kdtPayByStages_payBili_TextField.setVisible(true);
		kdtPayByStages_payBili_TextField.setEditable(true);
		kdtPayByStages_payBili_TextField.setHorizontalAlignment(2);
		kdtPayByStages_payBili_TextField.setDataType(1);
		kdtPayByStages_payBili_TextField
				.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
		kdtPayByStages_payBili_TextField
				.setMaximumValue(new java.math.BigDecimal("1.0E18"));
		kdtPayByStages_payBili_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPayByStages_payBili_CellEditor = new KDTDefaultCellEditor(
				kdtPayByStages_payBili_TextField);
		this.kdtPayByStages.getColumn("payBili").setEditor(
				kdtPayByStages_payBili_CellEditor);
		KDFormattedTextField kdtPayByStages_payMoney_TextField = new KDFormattedTextField();
		kdtPayByStages_payMoney_TextField
				.setName("kdtPayByStages_payMoney_TextField");
		kdtPayByStages_payMoney_TextField.setVisible(true);
		kdtPayByStages_payMoney_TextField.setEditable(true);
		kdtPayByStages_payMoney_TextField.setHorizontalAlignment(2);
		kdtPayByStages_payMoney_TextField.setDataType(1);
		kdtPayByStages_payMoney_TextField
				.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
		kdtPayByStages_payMoney_TextField
				.setMaximumValue(new java.math.BigDecimal("1.0E18"));
		kdtPayByStages_payMoney_TextField.setPrecision(10);
		KDTDefaultCellEditor kdtPayByStages_payMoney_CellEditor = new KDTDefaultCellEditor(
				kdtPayByStages_payMoney_TextField);
		this.kdtPayByStages.getColumn("payMoney").setEditor(
				kdtPayByStages_payMoney_CellEditor);
		KDTextField kdtPayByStages_remark_TextField = new KDTextField();
		kdtPayByStages_remark_TextField
				.setName("kdtPayByStages_remark_TextField");
		kdtPayByStages_remark_TextField.setMaxLength(200);
		KDTDefaultCellEditor kdtPayByStages_remark_CellEditor = new KDTDefaultCellEditor(
				kdtPayByStages_remark_TextField);
		this.kdtPayByStages.getColumn("remark").setEditor(
				kdtPayByStages_remark_CellEditor);
	}
    
    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout() {
    	this.setBounds(new Rectangle(0, 0, 1013, 600));
        this.setLayout(null);
        kDLabelContainer1.setBounds(new Rectangle(40, 19, 270, 19));
        this.add(kDLabelContainer1, null);
        contBizDate.setBounds(new Rectangle(664, 19, 270, 19));
        this.add(contBizDate, null);
        contBIMUDF0001.setBounds(new Rectangle(347, 19, 270, 19));
        this.add(contBIMUDF0001, null);
        contBIMUDF0002.setBounds(new Rectangle(40, 47, 270, 19));
        this.add(contBIMUDF0002, null);
        contBIMUDF0003.setBounds(new Rectangle(347, 47, 270, 19));
        this.add(contBIMUDF0003, null);
        chkProgressPayout.setBounds(new Rectangle(664, 47, 270, 19));
        this.add(chkProgressPayout, null);
        contBIMUDF0005.setBounds(new Rectangle(40, 75, 893, 36));
        this.add(contBIMUDF0005, null);
        paneBIZLayerControl21.setBounds(new Rectangle(40, 121, 970, 369));
        this.add(paneBIZLayerControl21, null);
        btnFenqi.setBounds(new Rectangle(40, 501, 120, 20));
        this.add(btnFenqi, null);
        kdtPayByStages.setBounds(new Rectangle(40, 500, 970, 358));
        kdtPayByStages_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPayByStages,new com.kingdee.eas.fdc.contract.PaymentLayoutPayByStageInfo(),null,false);
        this.add(kdtPayByStages_detailPanel, null);
        kDLabelContainer2.setBounds(new Rectangle(810, 44, 246, 21));
        this.add(kDLabelContainer2, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contBIMUDF0001
        contBIMUDF0001.setBoundEditor(txtPlanName);
        //contBIMUDF0002
        contBIMUDF0002.setBoundEditor(txtPlanAmount);
        //contBIMUDF0003
        contBIMUDF0003.setBoundEditor(txtCashRate);
        //contBIMUDF0005
        contBIMUDF0005.setBoundEditor(scrollPaneBIMUDF0005);
        //scrollPaneBIMUDF0005
        scrollPaneBIMUDF0005.getViewport().add(txtRemark, null);
        //paneBIZLayerControl21
        paneBIZLayerControl21.add(BIMUDF0006, resHelper.getString("BIMUDF0006.constraints"));
        paneBIZLayerControl21.add(BIMUDF0007, resHelper.getString("BIMUDF0007.constraints"));
        //BIMUDF0006
        BIMUDF0006.setLayout(null); 
        btnAddRow.setBounds(new Rectangle(804, 2, 70, 19));
        BIMUDF0006.add(btnAddRow, null);
        btnRemoveRow.setBounds(new Rectangle(881, 2, 74, 19));
        BIMUDF0006.add(btnRemoveRow, null);
        btnloadLinkAssign.setBounds(new Rectangle(691, 2, 107, 19));
        BIMUDF0006.add(btnloadLinkAssign, null);
        kdtPlanMingxi.setBounds(new Rectangle(0, -1, 963, 340));
        kdtPlanMingxi_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPlanMingxi,new com.kingdee.eas.fdc.contract.PaymentLayoutPlanMingxiInfo(),null,false);
        BIMUDF0006.add(kdtPlanMingxi_detailPanel, null);
        
        //BIMUDF0007
        BIMUDF0007.setLayout(null);        kdtPlam.setBounds(new Rectangle(1, 0, 956, 339));
        kdtPlam_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtPlam,new com.kingdee.eas.fdc.contract.PaymentLayoutPlamInfo(),null,false);
        BIMUDF0007.add(kdtPlam_detailPanel, null);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(ContractPlanNo);

	}
}