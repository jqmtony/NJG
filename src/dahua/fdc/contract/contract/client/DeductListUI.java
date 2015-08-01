/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.CompensationBillCollection;
import com.kingdee.eas.fdc.contract.CompensationBillFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryCollection;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.fdc.finance.ShowDeductOfPartABill;
import com.kingdee.eas.fdc.finance.ShowDeductOfPartABillFactory;
import com.kingdee.eas.fdc.finance.ShowDeductOfPartABillInfo;
import com.kingdee.eas.fdc.finance.client.DeductBillEditUI;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * ��������
 */
public class DeductListUI extends AbstractDeductListUI
{
	/** ���οۿ��� �� */
    private static final String COL_THIS_TIME_DEDUCT_AMT = "thisTimeDeductAmt";
    /** ѡ�� �� */
	private static final String COL_HAS_APPLIED = "hasApplied";
	
	private static final String resourePath = "com.kingdee.eas.fdc.contract.client.ContractResource";
	
	private static final Logger logger = CoreUIObject.getLogger(DeductListUI.class);
	private boolean okClicked = false;
	private BigDecimal amount = null;
	private BigDecimal compensatAmount = null;
	private BigDecimal partADeductAmt = null;
	private BigDecimal partAConfmAmt = null;
    private BigDecimal exRate = FDCHelper.ZERO;
    
    private boolean param = FDCUtils.getDefaultFDCParamByKey(null, SysContext
			.getSysContext().getCurrentFIUnit().getId().toString(),
			FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
    
    /**
     * output class constructor
     */
    public DeductListUI() throws Exception
    {
        super();
    }

    public void actionView_actionPerformed(ActionEvent e) throws Exception {
    	if(tabPanel.getSelectedIndex()==1){
    		tblMainGuerdon_tableClicked(null);
    		return;
    	}
    	
    	if(tabPanel.getSelectedIndex()==2){
    		tblCompensation_tableClicked(null);
    		return;
    	}
    	
        checkSelected();
        UIContext uiContext = new UIContext(this);
        uiContext.put(UIContext.ID, getSelectedKeyValue());
        
        // �����ඨ��Ҫ���ݵ�EditUI����չ������

        IUIWindow uiWin = null;
        if (SwingUtilities.getWindowAncestor(this) != null
                && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
        	uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
                    getEditUIName(), uiContext, null, OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);

        } else {
            // ����UI������ʾ
        	uiWin = UIFactory.createUIFactory(getEditUIModal()).create(
                    getEditUIName(), uiContext, null, OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
        }
        if(uiWin!=null){
//        	((EditUI)(uiWin.getUIObject())).getUIToolBar().setVisible(false);
        	uiWin.show();
        }
    }
    
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
    	beforeExcutQuery(null);
    	execQuery();
    }

    public void actionOK_actionPerformed(ActionEvent e) throws Exception
    {
        storeDeduct();
        storeGuerdon();
        storeCompensation();
        
        if(!PayRequestBillEditUI.isPartACon && PayReqUtils.isContractBill(getUIContext().get("contractBillId").toString())){
        	verifiyInputForTblPartA();
			if(param){
				storePartADeductBill();
			}else{
				storeMaterialConfirm();
			}
        }
        String payRequestBillId=getUIContext().get("payRequestBillId").toString();
        PayRequestBillFactory.getRemoteInstance().adjustPayment(new ObjectUuidPK(payRequestBillId),null);
    }

	/**
	 * У�顰�׹��ۿҳǩ�����룺ѡ�����У���û�����뱾�οۿ���Ҫ�������ʾ
	 */
	private void verifiyInputForTblPartA() {
		for (int i = 0; i < tblPartAMaterial.getRowCount(); i++) {
			boolean hasApplied = ((Boolean) tblPartAMaterial.getCell(i, COL_HAS_APPLIED).getValue()).booleanValue();
			if (hasApplied) {
				BigDecimal thisTimeDeductAmt = FDCHelper.toBigDecimal(tblPartAMaterial.getCell(i, COL_THIS_TIME_DEDUCT_AMT).getValue());
				if (FDCHelper.ZERO.compareTo(thisTimeDeductAmt) == 0) {
					String detailMsg = EASResource.getString(resourePath, "thisTimeDeductAmtCantEmptyDtl");
					FDCMsgBox.showDetailAndOK(this, EASResource.getString(resourePath, "thisTimeDeductAmtCantEmpty"), 
							FDCClientHelper.formatMessage(detailMsg, new String[] { String.valueOf(i + 1) }), 2);
					this.abort();
				}
			}
		}
	}

	/**
     * output actionbtnCancel_actionPerformed
     */
    public void actionbtnCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionbtnCancel_actionPerformed(e);
        //ֻ�رմ���
        disposeUIWindow();
    }

	protected ICoreBase getBizInterface() throws Exception
	{
		return DeductBillFactory.getRemoteInstance();
	}

	protected String getEditUIName()
	{
		return DeductBillEditUI.class.getName();
	}
	
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}
	
	public void onLoad() throws Exception
	{
		getUIContext().put("defaultFilter", getDeductFilter());
		super.onLoad();
//		
        tblMain.getStyleAttributes().setLocked(false);
        tblMain.getDataRequestManager().setDataRequestMode(
                KDTDataRequestManager.VIRTUAL_MODE_PAGE);
        tblMain.getSelectManager().setSelectMode(
                KDTSelectManager.ROW_SELECT);
        FDCHelper.formatTableNumber(tblMain, "entrys.deductAmt");
        tblMain.getColumn("entrys.deductDate").getStyleAttributes().setLocked(true);
        tblMainGuerdon.getStyleAttributes().setLocked(false);
/*        tblMainGuerdon.getDataRequestManager().setDataRequestMode(
                KDTDataRequestManager.VIRTUAL_MODE_PAGE);*/
        tblMainGuerdon.getSelectManager().setSelectMode(
                KDTSelectManager.ROW_SELECT);
        
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.menuBar.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionVoucher.setEnabled(false);
		this.actionDelVoucher.setVisible(false);
		this.actionDelVoucher.setEnabled(false);
		
		actionView.setEnabled(false);
		this.setSize(400,300);
		
		tblMainGuerdon.addKDTMouseListener(
				new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			        public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
			            try {
			            	tblMainGuerdon_tableClicked(e);
			            } catch (Exception exc) {
			            	handUIExceptionAndAbort(exc);
			            } finally {
			            }
			        }
        });
		tblCompensation.addKDTMouseListener(
				new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			        public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
			            try {
			            	tblCompensation_tableClicked(e);
			            } catch (Exception exc) {
			            	handUIExceptionAndAbort(exc);
			            } finally {
			            }
			        }
        });
		
		//�׹���ͬ����ʾ,�Ǽ׹���ͬ��ʾ
		if(PayRequestBillEditUI.isPartACon){
			this.tabPanel.remove(this.panel4);
		}
		
		exRate = FDCHelper.toBigDecimal(getUIContext().get("exRate"));
	}
	
	/**
	 * ��̬���ɼ׹����б�<br>
	 * ����Ϊ�ǣ���ý�����ʾ��Ϊ������ɵĿۿ���б�<br>
	 * @throws BOSException 
	 */
	private void setPartADeductBillTableList(KDTable table) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = getPartAFilter();
		view.setFilter(filter);
		view.getSelector().add(COL_HAS_APPLIED);
		view.getSelector().add("Parent.number");
		view.getSelector().add("contractId");
		view.getSelector().add("deductItem");
		view.getSelector().add("deductAmt");
		view.getSelector().add("hasDeductAmt");
		view.getSelector().add("Parent.createTime");
		view.getSelector().add("Parent.creator.name");
		view.getSelector().add("remark");
		view.getSorter().add(new SorterItemInfo("Parent.number"));
		view.getSorter().add(new SorterItemInfo("Parent.createTime"));
		final DeductBillEntryCollection col = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view);
		for(int i=0;i<col.size();i++){
			setPartADeductBillRow(table,col.get(i));
		}
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
//		table.getStyleAttributes().setLocked(true); 
//		FDCHelper.formatTableNumber(table,"deductAmt");
//		FDCHelper.formatTableDate(table, "createTime");
//		table.getColumn("id").getStyleAttributes().setHided(true);
		formatPartADeductTable(table);
		
		Object obj = getUIContext().get("billState");
		if(obj!=null&&!obj.equals(OprtState.VIEW) && !obj.equals("FINDVIEW")){
			table.getColumn(COL_HAS_APPLIED).getStyleAttributes().setLocked(false);
		}
	}
	
	private void setPartADeductBillRow(KDTable table,DeductBillEntryInfo info){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("curProject.name");
		view.getSelector().add("curProject.number");
		view.getSelector().add("currency.name");
		filter.getFilterItems().add(new FilterItemInfo("id",info.getContractId()));
		view.setFilter(filter);
		ContractBillInfo con = null;
		try {
			con = ContractBillFactory.getRemoteInstance().getContractBillCollection(view).get(0);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		if(con==null){
			return;
		}
		IRow row = table.addRow();
		row.getCell(COL_HAS_APPLIED).setValue(Boolean.valueOf(info.isHasApplied()));
		row.getCell("prjNumber").setValue(con.getCurProject().getNumber());
		row.getCell("prjName").setValue(con.getCurProject().getName());
		row.getCell("conNumber").setValue(con.getNumber());
		row.getCell("conName").setValue(con.getName());
		row.getCell("deductNubmer").setValue(info.getParent().getNumber());
		row.getCell("supplerUnit").setValue(info.getDeductItem());
		row.getCell("deductAmt").setValue(info.getDeductAmt());
		row.getCell("currency").setValue(con.getCurrency().getName());
		row.getCell("remark").setValue(info.getRemark());
		row.getCell("createTime").setValue(info.getParent().getCreateTime());
		row.getCell("creator").setValue(info.getParent().getCreator().getName());
		row.getCell("id").setValue(info.getId());
		ShowDeductOfPartABillInfo showBill = getShowDeductDetails(info.getId().toString());
		BigDecimal hasDeductAmount = showBill.getHasDeductAmount();
		if (!showBill.isIsRelated()) {//����δ��������˵�����µĸ������뵥�������ۿ
			row.getCell(COL_THIS_TIME_DEDUCT_AMT).setValue(null);
			//���ۿ���� = �ܶ�-�ѿۿ���
			BigDecimal stayDeductAmount = FDCHelper.subtract(FDCHelper.toBigDecimal(info.getDeductAmt()), FDCHelper.toBigDecimal(hasDeductAmount));
			row.getCell("stayDeductAmt").setValue(stayDeductAmount);
		}else{//�ѱ������������ұ��浽�����ݿ���ߵ�
			row.getCell(COL_THIS_TIME_DEDUCT_AMT).setValue(showBill.getThisTimeDeductAmount());
			row.getCell("stayDeductAmt").setValue( FDCHelper.subtract(FDCHelper.toBigDecimal(info.getDeductAmt()), FDCHelper.add(hasDeductAmount, showBill.getThisTimeDeductAmount())));
		}
		row.getCell("hasDeductAmt").setValue(hasDeductAmount);
	}
	
	/**
	 * ȡ��ǰ�������뵥֮ǰ�����и������뵥�Ŀۿ���֮��, ���ڽ��������ݵ�չʾ��ԭ����getNewstHasAmountOfShowBill��ȡ���еĿۿ����ݣ�����У�� by zhiyuan_tang
	 * �˴��ĳ��������ڽ��������ۼ�ʵ������Ҫ�������ŵ���֮ǰ�Ŀۿ���
	 * 
	 */
	private BigDecimal getNewstHasAmountOfShowBill(String deductEntryID) {
		BigDecimal maxHasDeductAmount = FDCHelper.ZERO;
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		PayRequestBillInfo payRequestBill = null;
		try {
			payRequestBill = PayRequestBillFactory.getRemoteInstance().getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId));
		} catch (EASBizException e1) {
			handUIExceptionAndAbort(e1);
		} catch (BOSException e1) {
			handUIExceptionAndAbort(e1);
		}
		
		if (payRequestBill == null) {
			return maxHasDeductAmount;
		}
		
		FDCSQLBuilder builder = new FDCSQLBuilder();
//		builder.appendSql("select sum(FHasDeductAmount) theMaxValue from t_fnc_showdeductofpartabill where FDeductEntryID=?  ");
		builder.appendSql("select sum(deductbill.FThisTimeDeductAmount) theMaxValue from t_fnc_showdeductofpartabill deductbill ");
		builder.appendSql(" inner join t_con_payrequestbill payreq on deductbill.FPayReqID = payreq.FID ");
		builder.appendSql(" where deductbill.FDeductEntryID=?  ");
		builder.addParam(deductEntryID);
		builder.appendSql(" and payreq.FLastUpdateTime <  ");
		builder.appendSql("{ts '");
		DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.appendSql(FORMAT_TIME.format(payRequestBill.getLastUpdateTime()));
		builder.appendSql("' }");
		try {
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.next()){
				maxHasDeductAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("theMaxValue"));
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return maxHasDeductAmount;
	}
	
	/**
	 * ���ݿۿ��¼ID�ҵ���Ӧ�������ѿۿ��� (���˱��ε�) by cassiel
	 * 
	 */
	private BigDecimal getAllHasAmountOfShowBill(String deductEntryID) {
		BigDecimal maxHasDeductAmount = FDCHelper.ZERO;
		String payRequestBillId=getUIContext().get("payRequestBillId").toString();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(FThisTimeDeductAmount) theMaxValue from t_fnc_showdeductofpartabill where FDeductEntryID=?  ");
		builder.addParam(deductEntryID);
		builder.appendSql(" and FPayReqID <> ?  ");
		builder.addParam(payRequestBillId);
		try {
			IRowSet rowSet = builder.executeQuery();
			if(rowSet.next()){
				maxHasDeductAmount = FDCHelper.toBigDecimal(rowSet.getBigDecimal("theMaxValue"));
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return maxHasDeductAmount;
	}
	
	/**
	 * ���ݸ������뵥ID�Ϳۿ��¼ID�ҵ���Ӧ�Ĺ�����ϸ��¼ by cassiel
	 * 
	 * ��ȡ�ѿۿ����� by hpw 2010.9.21
	 */
	private ShowDeductOfPartABillInfo getShowDeductDetails(String deductEntryID) {
		String payReqId= getUIContext().get("payRequestBillId").toString();
		ShowDeductOfPartABillInfo showBill = new ShowDeductOfPartABillInfo();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select * from t_fnc_showdeductofpartabill where FDeductEntryID=?  ");
		builder.addParam(deductEntryID);
		try {
			IRowSet rowSet = builder.executeQuery();
			BigDecimal hasDeductAmt = FDCHelper.ZERO;
			if (rowSet != null) {
				while (rowSet.next()) {
					String id = rowSet.getString("fid");
					String reqId = rowSet.getString("FPayReqID");
					BigDecimal thisTimeDeductAmount = rowSet.getBigDecimal("FThisTimeDeductAmount");
					if (payReqId.equals(reqId)) {
						showBill.setId(BOSUuid.read(id));
						showBill.setThisTimeDeductAmount(thisTimeDeductAmount);
						showBill.setPayReqID(rowSet.getString("FPayReqID"));
						showBill.setIsRelated(rowSet.getBoolean("FIsRelated"));
						showBill.setDeductAmount(rowSet.getBigDecimal("FDeductAmount"));
						showBill.setDeductEntryID(rowSet.getString("FDeductEntryID"));

					} else {
						hasDeductAmt = FDCHelper.add(hasDeductAmt, thisTimeDeductAmount);
					}
				}
			}
			showBill.setHasDeductAmount(hasDeductAmt);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return showBill;
	}
	
	//TODO ÿ�༭һ�ζ��������ݿ⣬���Ż�
	protected void tblPartAMaterial_editStopped(KDTEditEvent e) throws Exception {
		if (e.getColIndex() == 0) { // ����༭���ǡ�ѡ����
			boolean hasApplied = ((Boolean) tblPartAMaterial.getCell(e.getRowIndex(), COL_HAS_APPLIED).getValue()).booleanValue();
			if (hasApplied)
				tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
			else
				tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT).getStyleAttributes().setBackground(Color.WHITE);
		}
		
		if(param){//����Ϊ�ǣ�������ɵĿۿ
			if (tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT) != null&&e.getColIndex()==tblPartAMaterial.getColumnIndex(COL_THIS_TIME_DEDUCT_AMT)) {
				BigDecimal thisTimeDeductAmount = FDCHelper.toBigDecimal(tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT).getValue());//���οۿ�
				// �ѿۿ���ӽ���ȡ����׼ȷ,���ǵô����ݿ����ȡ
				String id = tblPartAMaterial.getCell(e.getRowIndex(), "id").getValue().toString();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("deductAmt");
				DeductBillEntryInfo entry = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryInfo(new ObjectUuidPK(id),selector);
				ShowDeductOfPartABillInfo showBill = getShowDeductDetails(tblPartAMaterial.getCell(e.getRowIndex(), "id").getValue().toString());
				BigDecimal hasDeductAmount = showBill.getHasDeductAmount();//�ѿۿ���
				
				
				//���οۿ���+�ۼƿۿ���<=�׹��Ŀۿ��ܽ��
				BigDecimal temp = FDCHelper.add(thisTimeDeductAmount,hasDeductAmount);
				if(temp.compareTo(entry.getDeductAmt())>0){
					FDCMsgBox.showWarning("���οۿ�������ۼƿۿ���ܴ��ڼ׹��Ŀۿ��ܽ�");
					//��ʾ֮�����ݻָ���ԭ����״̬
					tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT).setValue(showBill.getThisTimeDeductAmount());
					tblPartAMaterial.getCell(e.getRowIndex(), "stayDeductAmt").setValue(FDCHelper.subtract(entry.getDeductAmt(), FDCHelper.add(showBill.getThisTimeDeductAmount(), hasDeductAmount)));
					tblPartAMaterial.getCell(e.getRowIndex(), "hasDeductAmt").setValue(showBill.getHasDeductAmount());
					SysUtil.abort();
				}
				
				tblPartAMaterial.getCell(e.getRowIndex(), "hasDeductAmt").setValue(hasDeductAmount);
				tblPartAMaterial.getCell(e.getRowIndex(), "stayDeductAmt").setValue(FDCHelper.subtract(entry.getDeductAmt(), FDCHelper.add(thisTimeDeductAmount, hasDeductAmount)));
			}
		}else{//����Ϊ�񣺲���ȷ�ϵ�
			if (tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT) != null&&e.getColIndex()==tblPartAMaterial.getColumnIndex(COL_THIS_TIME_DEDUCT_AMT)) {
				BigDecimal thisTimeDeductAmount = FDCHelper.toBigDecimal(tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT).getValue());//���οۿ�
				// �ѿۿ���ӽ���ȡ����׼ȷ,���ǵô����ݿ����ȡ
				String id = tblPartAMaterial.getCell(e.getRowIndex(), "id").getValue().toString();
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("confirmAmt");
				MaterialConfirmBillInfo confirm = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillInfo(new ObjectUuidPK(id));
				ShowDeductOfPartABillInfo showBill = getShowDeductDetails(tblPartAMaterial.getCell(e.getRowIndex(), "id").getValue().toString());
				BigDecimal allDeductAmount = FDCHelper.toBigDecimal(getAllHasAmountOfShowBill(id));//�ѿۿ���
				BigDecimal beforDeductAmount = FDCHelper.toBigDecimal(getNewstHasAmountOfShowBill(id));//�ѿۿ���
				
				
				//���οۿ���+�ۼƿۿ���<=�׹��Ŀۿ��ܽ��
				BigDecimal temp = FDCHelper.ZERO;
				temp = FDCHelper.add(thisTimeDeductAmount,allDeductAmount);
				if (temp.compareTo(FDCHelper.toBigDecimal(confirm.getConfirmAmt())) > 0) {
					FDCMsgBox.showWarning("���οۿ�������ۼƿۿ���ܴ��ڼ׹��Ŀۿ��ܽ�");
					//��ʾ֮�����ݻָ���ԭ����״̬
					tblPartAMaterial.getCell(e.getRowIndex(), COL_THIS_TIME_DEDUCT_AMT).setValue(showBill.getThisTimeDeductAmount());
					tblPartAMaterial.getCell(e.getRowIndex(), "stayDeductAmt").setValue(FDCHelper.subtract(confirm.getConfirmAmt(), FDCHelper.add(showBill.getThisTimeDeductAmount(), beforDeductAmount)));
					tblPartAMaterial.getCell(e.getRowIndex(), "hasDeductAmt").setValue(beforDeductAmount);
					SysUtil.abort();
				}
				
				tblPartAMaterial.getCell(e.getRowIndex(), "hasDeductAmt").setValue(beforDeductAmount);
				tblPartAMaterial.getCell(e.getRowIndex(), "stayDeductAmt").setValue(FDCHelper.subtract(confirm.getConfirmAmt(), FDCHelper.add(thisTimeDeductAmount, beforDeductAmount)));
			}
		
		}
	}

	private void formatPartADeductTable(KDTable table) {
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getColumn("id").getStyleAttributes().setHided(true);
		FDCHelper.formatTableNumber(table,"deductAmt");
		FDCHelper.formatTableNumber(table,"stayDeductAmt");
		FDCHelper.formatTableNumber(table,COL_THIS_TIME_DEDUCT_AMT);
		FDCHelper.formatTableNumber(table,"hasDeductAmt");
		
		FDCHelper.formatTableDate(table, "createTime");
		
		table.getColumn(COL_HAS_APPLIED).getStyleAttributes().setLocked(true);
		table.getColumn("prjNumber").getStyleAttributes().setLocked(true);
		table.getColumn("prjName").getStyleAttributes().setLocked(true);
		table.getColumn("conNumber").getStyleAttributes().setLocked(true);
		table.getColumn("conName").getStyleAttributes().setLocked(true);
		table.getColumn("deductNubmer").getStyleAttributes().setLocked(true);
		table.getColumn("supplerUnit").getStyleAttributes().setLocked(true);
		table.getColumn("deductAmt").getStyleAttributes().setLocked(true);
		table.getColumn("stayDeductAmt").getStyleAttributes().setLocked(true);
		table.getColumn("hasDeductAmt").getStyleAttributes().setLocked(true);
		table.getColumn("currency").getStyleAttributes().setLocked(true);
		table.getColumn("remark").getStyleAttributes().setLocked(true);
		table.getColumn("createTime").getStyleAttributes().setLocked(true);
		table.getColumn("creator").getStyleAttributes().setLocked(true);
		
		KDFormattedTextField txtThisTimeDeductAmt = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		txtThisTimeDeductAmt.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtThisTimeDeductAmt.setSupportedEmpty(false);
		txtThisTimeDeductAmt.setPrecision(2);
//		txtThisTimeDeductAmt.setNegatived(false);// ����¼�븺������������֮ǰ��ۿ���
		ICellEditor thisTimeDeductAmtEditor = new KDTDefaultCellEditor(txtThisTimeDeductAmt);
		table.getColumn(COL_THIS_TIME_DEDUCT_AMT).setEditor(thisTimeDeductAmtEditor);
	}

	
	/**
	 * ��̬���ɼ׹����б�<br>
	 * ����Ϊ����ý�����ʾ��Ϊ����ȷ�ϵ���
	 * @throws BOSException 
	 */
	private void setMaterialConfirmTableList(KDTable table) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = getPartAConfmFilter();
		view.setFilter(filter);
		view.getSelector().add(COL_HAS_APPLIED);
		view.getSelector().add("mainContractBill.curProject.number");
		view.getSelector().add("mainContractBill.curProject.name");
		view.getSelector().add("mainContractBill.number");
		view.getSelector().add("mainContractBill.name");
		view.getSelector().add("number");
		view.getSelector().add("materialContractBill.partB.name");
		view.getSelector().add("confirmAmt");
		view.getSelector().add("mainContractBill.currency.name");
		view.getSelector().add("description");
		view.getSelector().add("createTime");
		view.getSelector().add("creator.name");
		
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSorter().add(new SorterItemInfo("createTime"));
		
		MaterialConfirmBillCollection coll = MaterialConfirmBillFactory.getRemoteInstance().getMaterialConfirmBillCollection(view);
		for(int i=0;i<coll.size();i++){
			setMaterialConfirmRow(table,coll.get(i));
		}
		
		formatPartADeductTable(table);
		
		Object obj = getUIContext().get("billState");
		if(obj!=null&&!obj.equals(OprtState.VIEW) && !obj.equals("FINDVIEW")){
			table.getColumn(COL_HAS_APPLIED).getStyleAttributes().setLocked(false);
		}
		
	}
	
	private void setMaterialConfirmRow(KDTable table,MaterialConfirmBillInfo info){
		
		IRow row = table.addRow();
		row.getCell(COL_HAS_APPLIED).setValue(Boolean.valueOf(info.isHasApplied()));
		row.getCell("prjNumber").setValue(info.getMainContractBill().getCurProject().getNumber());
		row.getCell("prjName").setValue(info.getMainContractBill().getCurProject().getName());
		row.getCell("conNumber").setValue(info.getMainContractBill().getNumber());
		row.getCell("conName").setValue(info.getMainContractBill().getName());
		row.getCell("deductNubmer").setValue(info.getNumber());
		row.getCell("supplerUnit").setValue(info.getMaterialContractBill().getPartB().getName());
		row.getCell("deductAmt").setValue(info.getConfirmAmt());
		row.getCell("currency").setValue(info.getMainContractBill().getCurrency().getName());
		row.getCell("remark").setValue(info.getDescription());
		row.getCell("createTime").setValue(info.getCreateTime());
		row.getCell("creator").setValue(info.getCreator().getName());
		row.getCell("id").setValue(info.getId());
		
		ShowDeductOfPartABillInfo showBill = getShowDeductDetails(info.getId().toString());
		BigDecimal maxHasDeductAmount = getNewstHasAmountOfShowBill(info.getId().toString());
		if (!showBill.isIsRelated()) {//����δ��������˵�����µĸ������뵥�������ۿ
			row.getCell(COL_THIS_TIME_DEDUCT_AMT).setValue(null);
			//���ۿ���� = �ܶ�-�ѿۿ���
			BigDecimal stayDeductAmount = FDCHelper.subtract(FDCHelper.toBigDecimal(info.getConfirmAmt()), FDCHelper.toBigDecimal(maxHasDeductAmount));
//			BigDecimal stayDeductAmount = FDCHelper.subtract(FDCHelper.toBigDecimal(info.getDeductAmt()), FDCHelper.toBigDecimal(info.getHasDeductAmt()));
			row.getCell("stayDeductAmt").setValue(stayDeductAmount);
		}else{//�ѱ������������ұ��浽�����ݿ���ߵ�
			row.getCell(COL_THIS_TIME_DEDUCT_AMT).setValue(showBill.getThisTimeDeductAmount());
			row.getCell("stayDeductAmt").setValue( FDCHelper.subtract(FDCHelper.toBigDecimal(info.getConfirmAmt()), FDCHelper.add(showBill.getThisTimeDeductAmount(), maxHasDeductAmount)));
		}
		row.getCell("hasDeductAmt").setValue(maxHasDeductAmount);
	}
	
	protected void tblMainGuerdon_tableClicked(KDTMouseEvent e) throws Exception{
		setCursorOfWair();
		if(e==null||e.getClickCount()==2){
			if (tblMainGuerdon.getRowCount() == 0 || tblMainGuerdon.getSelectManager().size() == 0)
			{
				MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
				SysUtil.abort();
			}
			if(e!=null&&e.getType()==KDTStyleConstants.HEAD_ROW) {
				return;
			}
			int[] selectedRows = KDTableUtil.getSelectedRows(tblMainGuerdon);
			String id=tblMainGuerdon.getRow(selectedRows[0]).getCell("id").getValue().toString();
			if(id==null) {
				return;
			}
			
	        UIContext uiContext = new UIContext(this);
	        uiContext.put(UIContext.ID, id);
	        
	        // �����ඨ��Ҫ���ݵ�EditUI����չ������

	        IUIWindow uiWin = null;
	        if (SwingUtilities.getWindowAncestor(this) != null
	                && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
	        	uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
	                    com.kingdee.eas.fdc.contract.client.GuerdonBillEditUI.class.getName(), 
	                    uiContext, null, "FINDVIEW",WinStyle.SHOW_ONLYLEFTSTATUSBAR);

	        } else {
	            // ����UI������ʾ
	        	uiWin = UIFactory.createUIFactory(getEditUIModal()).create(
	        			com.kingdee.eas.fdc.contract.client.GuerdonBillEditUI.class.getName(), 
	        			uiContext, null, "FINDVIEW",WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	        }
	        if(uiWin!=null){
//	        	((EditUI)(uiWin.getUIObject())).getUIToolBar().setVisible(false);
	        	uiWin.show();
	        }
			
		}
		setCursorOfDefault();
	}

	protected void tblCompensation_tableClicked(KDTMouseEvent e) throws Exception{
		setCursorOfWair();
		if(e==null||e.getClickCount()==2){
			if (tblCompensation.getRowCount() == 0 || tblCompensation.getSelectManager().size() == 0)
			{
				MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
				SysUtil.abort();
			}
			if(e!=null&&e.getType()==KDTStyleConstants.HEAD_ROW) {
				return;
			}
			int[] selectedRows = KDTableUtil.getSelectedRows(tblCompensation);
			String id=tblCompensation.getRow(selectedRows[0]).getCell("id").getValue().toString();
			if(id==null) {
				return;
			}
			
	        UIContext uiContext = new UIContext(this);
	        uiContext.put(UIContext.ID, id);
	        
	        // �����ඨ��Ҫ���ݵ�EditUI����չ������

	        IUIWindow uiWin = null;
	        if (SwingUtilities.getWindowAncestor(this) != null
	                && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
	        	uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
	                    com.kingdee.eas.fdc.contract.client.CompensationBillEditUI.class.getName(), 
	                    uiContext, null, "FINDVIEW",WinStyle.SHOW_ONLYLEFTSTATUSBAR);

	        } else {
	            // ����UI������ʾ
	        	uiWin = UIFactory.createUIFactory(getEditUIModal()).create(
	        			com.kingdee.eas.fdc.contract.client.CompensationBillEditUI.class.getName(), 
	        			uiContext, null, "FINDVIEW",WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	        }
	        if(uiWin!=null){
//	        	((EditUI)(uiWin.getUIObject())).getUIToolBar().setVisible(false);
	        	uiWin.show();
	        }
			
		}
		setCursorOfDefault();
	}
	
	private void setGuerdonTableList(KDTable table) throws Exception {
		tblMainGuerdon.removeRows(false);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=getGuerdoFilter();
		view.setFilter(filter);
		
		view.getSelector().add("isGuerdoned");
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("createTime");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("contract.name");
		view.getSelector().add("contract.number");
		view.getSelector().add("contract.curProject.number");
		view.getSelector().add("contract.curProject.name");
		view.getSelector().add("creator.name");
		
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSorter().add(new SorterItemInfo("createTime"));
		final GuerdonBillCollection c = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
		for(int i=0;i<c.size();i++){
			setGuerdonTableRow(table,c.get(i));
		}
		table.getStyleAttributes().setLocked(true);
		
    	Object obj = getUIContext().get("billState");
    	if(obj!=null&&!obj.equals(OprtState.VIEW) && !obj.equals("FINDVIEW")){
    		table.getColumn("isGuerdoned").getStyleAttributes().setLocked(false);
    	}
    	
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableNumber(table, "amount");
		table.getColumn("createTime").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
	}


	private void setGuerdonTableRow(KDTable table,GuerdonBillInfo info) {
		IRow row=table.addRow();
		row.getCell("isGuerdoned").setValue(Boolean.valueOf(info.isIsGuerdoned()));
		row.getCell("curProject.number").setValue(info.getContract().getCurProject().getNumber());
		row.getCell("curProject.name").setValue(info.getContract().getCurProject().getName());
		row.getCell("contract.number").setValue(info.getContract().getNumber());
		row.getCell("contract.name").setValue(info.getContract().getName());
		row.getCell("number").setValue(info.getNumber());
		row.getCell("name").setValue(info.getName());
		row.getCell("amount").setValue(info.getAmount());
		row.getCell("originalAmount").setValue(info.getOriginalAmount());
		row.getCell("createTime").setValue(info.getCreateTime());
		row.getCell("creator.name").setValue(info.getCreator().getName());
		row.getCell("id").setValue(info.getId().toString());
	}

	public void beforeActionPerformed(ActionEvent e)
	{
		super.beforeActionPerformed(e);
		if(e.getSource().equals(btnOK)){
			setOkClicked(true);
		}else{
			setOkClicked(false);
		}
	}
	/**
	 * �ڲ�ѯǰ���ù���ѡ�� 
	 * 1. selectSet Ҫ�򹴵�
	 * 2. notIncludeSet ����ʾ��
	 * @author sxhong  		Date 2006-10-19
	 * @param ev
	 * @see com.kingdee.eas.framework.client.ListUI#beforeExcutQuery(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected void beforeExcutQuery(EntityViewInfo ev)
	{
		//�ڶ��δ��������ʱ������,��ʵ��guerdontable��compensationTable�ļ�ʱˢ��
		if(ev==null)	{
			ev=getMainQuery();
			try {
				setGuerdonTableList(tblMainGuerdon);
				setCompensationTableList(tblCompensation);
		    	Object obj = getUIContext().get("billState");
		    	if(obj!=null&&(obj.equals(OprtState.VIEW)||obj.equals("FINDVIEW"))){
		    		btnOK.setEnabled(false);
		    		btncancel.setEnabled(false);
		    		tblMain.getColumn(0).getStyleAttributes().setLocked(true);
		    		tblMainGuerdon.getColumn(0).getStyleAttributes().setLocked(true);
		    		tblCompensation.getColumn(0).getStyleAttributes().setLocked(true);
		    	}
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		super.beforeExcutQuery(ev);
		ev.setFilter(getFilterInfo());


	}

    protected void execQuery()
    {
    	super.execQuery();
    	if(getUIContext().get("selectSet") instanceof Set){
    		Set set=(Set)getUIContext().get("selectSet");
    		//����ѡ����
    		IRow row;
    		for(int i=getMainTable().getRowCount()-1;i>=0;i--){
    			row = getMainTable().getRow(i);
    			if(set.contains(row.getCell("entrys.id").getValue()))
				{
					row.getCell("entrys.hasApplied").setValue(Boolean.TRUE);
				}
    		}
    		
    	}
    }
    // ���������ظ��෽��������CU����
    protected FilterInfo getDefaultFilterForQuery() {
        return null;
    }

	protected boolean isAllowDefaultSolutionNull() {
        return true;
    }

	/**
	 * 
	 * ��ʼ��Ĭ�Ϲ�������
	 * 
	 * @return ��������ˣ������˳�ʼ�����������뷵��true;Ĭ�Ϸ���false;
	 */
	protected boolean initDefaultFilter() {
		return false;
	}

	/**
	 * ��ѡ�����Ϣ���д洢���ڵ��ȷ����ť�����Ϊ
	 * @author sxhong  		Date 2006-10-19
	 */
	private void storeDeduct(){
    	if(getUIContext().get("selectSet") instanceof Set){
    		Set set=(Set)getUIContext().get("selectSet");
    		HashMap map=new HashMap();
    		FilterInfo filter;
			/*
			 * �õ���ǰ�������뵥��Ӧ��DeductOfPayReqBill�ڵ�Info����,�Ա㱣����ѡ�������
			 */
			String payRequestBillId=getUIContext().get("payRequestBillId").toString();
			EntityViewInfo view=new EntityViewInfo();
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",payRequestBillId));
			view.setFilter(filter);
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			try
			{
				DeductOfPayReqBillCollection c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
				DeductOfPayReqBillInfo info;
				for(int i=0;i<c.size();i++){
					info=c.get(i);
					map.put(info.getDeductType().getId().toString(), info);
				}
			} catch (BOSException e)
			{
				handUIExceptionAndAbort(e);
			}
			if(map.size()<=0) {
				disposeUIWindow();
				return;
			}
    		//������ѡ����,�����Ǳ��浽DeductOfReqBillEntry��
    		IRow row;
			Set deleteSet=new HashSet();
    		for(int i=getMainTable().getRowCount()-1;i>=0;i--){
    			row = getMainTable().getRow(i);
    			Object hasApplied = row.getCell("entrys.hasApplied").getValue();
				Object entrysId = row.getCell("entrys.id").getValue();
				String id=entrysId.toString();
    			
   /*
    * ��һ�ִ���ʽ,
    * 1 ɾ��ԭ����������û��ѡ�����
	* 2 ����ԭ��û��������ѡ�����,
    * 
    */
    			if(set.contains(entrysId))
				{
    				if(!hasApplied.equals(Boolean.TRUE)){
    					deleteSet.add(id);
     					set.remove(entrysId);
    				}

				}else{
    				if(hasApplied.equals(Boolean.TRUE)){
    					//����
        				String deductTypeId=row.getCell("entrys.deductType.id").getValue().toString();
        				DeductOfPayReqBillInfo deductOfPayReqBillInfo=(DeductOfPayReqBillInfo)map.get(deductTypeId);
        				DeductOfPayReqBillEntryInfo entryInfo=new DeductOfPayReqBillEntryInfo();
    					entryInfo.setParent(deductOfPayReqBillInfo);
    					try{
    						entryInfo.setDeductBillEntry(DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryInfo(new ObjectUuidPK(BOSUuid.read(id))));
    						DeductOfPayReqBillEntryFactory.getRemoteInstance().addnew(entryInfo);
    					}catch(Exception e){
    						handUIExceptionAndAbort(e);
    					}
    					set.add(entrysId);
    				}
				}
    		}
    		
			/*
			 * ɾ��δѡ���
			 */
			if(deleteSet.size()>0){
				try
				{
					filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("deductBillEntry.id",deleteSet,CompareType.INCLUDE));
					DeductOfPayReqBillEntryFactory.getRemoteInstance().delete(filter);
				} catch (Exception e)
				{
					handUIExceptionAndAbort(e);
				}
			}
			
			//���¿ۿ��¼�������־
			try{
				FDCSQLBuilder builder=new FDCSQLBuilder();
				if(deleteSet.size()>0){
					builder.appendSql("update T_FNC_DeductBillEntry set fhasApplied=? where ");
					builder.addParam(Boolean.FALSE);
					builder.appendParam("fid",deleteSet.toArray());
					builder.executeUpdate();
					builder.clear();
				}
				if(set.size()>0){
					builder.appendSql("update T_FNC_DeductBillEntry set fhasApplied=? where ");
					builder.addParam(Boolean.TRUE);
					builder.appendParam("fid",set.toArray());
					builder.executeUpdate();
				}
			}catch(BOSException e){
				handUIExceptionAndAbort(e);
			}
//			reCalcAmount();
    	}
    	disposeUIWindow();
	}
	public void onShow() throws Exception
	{
		super.onShow();
		/*
		 * ��һ�μ���ʱ�Ŀ���
		 */
    	if(getUIContext().get("selectSet") instanceof Set){
    		Set set=(Set)getUIContext().get("selectSet");
    		//����ѡ����
    		IRow row;
    		for(int i=getMainTable().getRowCount()-1;i>=0;i--){
    			row = getMainTable().getRow(i);
    			if(set.contains(row.getCell("entrys.id").getValue()))
				{
					row.getCell("entrys.hasApplied").setValue(Boolean.TRUE);
				}
    		}
    		
    	}
    	SwingUtilities.invokeLater(new Runnable(){
    		public void run() {
    			try{
    				setGuerdonTableList(tblMainGuerdon);
    				setCompensationTableList(tblCompensation);
    				if(!PayRequestBillEditUI.isPartACon && PayReqUtils.isContractBill((String)getUIContext().get("contractBillId"))){
	    				if(param){
							setPartADeductBillTableList(tblPartAMaterial);
						}else{
							setMaterialConfirmTableList(tblPartAMaterial);
						}
    				}
    			}catch (Exception e) {
    				handUIExceptionAndAbort(e);
				}
    		}
    	});
    	tabPanel.setSelectedIndex(0);
    	Object obj = getUIContext().get("billState");
    	if(obj!=null&&(obj.equals(OprtState.VIEW)||obj.equals("FINDVIEW"))){
    		btnOK.setEnabled(false);
    		btncancel.setEnabled(false);
    		tblMain.getColumn(0).getStyleAttributes().setLocked(true);
	    	tblMainGuerdon.getColumn(0).getStyleAttributes().setLocked(true);
    		tblCompensation.getColumn(0).getStyleAttributes().setLocked(true);
    	}
	}

	public boolean isOkClicked()
	{
		return okClicked;
	}

	public void setOkClicked(boolean okClicked)
	{
		this.okClicked = okClicked;
	}

	public FilterInfo getFilterInfo()
	{
		if(getUIContext().get("defaultFilter") instanceof FilterInfo){
			FilterInfo defaultFilter = (FilterInfo)getUIContext().get("defaultFilter");
			FilterInfo filter=(FilterInfo)defaultFilter.clone();
			String contractBillId = (String) getUIContext().get("contractBillId");
			String payRequestBillId=(String)getUIContext().get("payRequestBillId");
			/*
			 * ���˳�DeductOfPayReqBillEntry������ͬ�ĺ�ͬ����
			 * ���ڱ����뵥�Ŀۿ����notIncludeSet
			 * �Լ��ڱ����뵥�ڵĿۿ��Ϊѡ����selectSet
			 */
			Set notIncludeSet=new HashSet();
			Set selectSet=new HashSet(); //�����뱾���뵥������Ŀۿ�ķ�¼ID��
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter2=new FilterInfo();
			FilterItemCollection items2 = filter2.getFilterItems();
			items2.add(new FilterItemInfo("parent.PayRequestBill.contractId",contractBillId,CompareType.EQUALS));
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("deductBillEntry.id");
			view.setFilter(filter2);
			
			try
			{//Ҫ���˵ļ���
				DeductOfPayReqBillEntryCollection c;
				c = DeductOfPayReqBillEntryFactory.getRemoteInstance().getDeductOfPayReqBillEntryCollection(view);
				DeductOfPayReqBillEntryInfo info;
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					if(payRequestBillId.equals(info.getParent().getPayRequestBill().getId().toString())){
						selectSet.add(info.getDeductBillEntry().getId().toString());
					}else{
						notIncludeSet.add(info.getDeductBillEntry().getId().toString());
					}
				}
			} catch (BOSException e1)
			{
				handUIExceptionAndAbort(e1);
			}
			
			if(notIncludeSet.size()>0){
				//�ų����Ѿ�ѡ���˵�
				FilterItemInfo notInclude_Item = new FilterItemInfo("entrys.id",notIncludeSet,CompareType.NOTINCLUDE);
				filter.getFilterItems().add(notInclude_Item);
			}
			if(selectSet.size()>0){
				FilterInfo tempFilter=new FilterInfo();
				tempFilter.getFilterItems().add(new FilterItemInfo("entrys.id",selectSet,CompareType.INCLUDE));
				try {
					filter.mergeFilter(tempFilter, "OR");
				} catch (BOSException e) {
					handUIExceptionAndAbort(e);
				}
			}
			getUIContext().put("selectSet", selectSet);
			return filter;
		}
		return super.getFilterInfo();
	}
	public void onGetRowSet(IRowSet rowSet)
	{
		try{
			rowSet.beforeFirst();
			while(rowSet.next()){
				String contractId=rowSet.getString("entrys.contractId");
				BOSObjectType type = BOSUuid.read(contractId).getType();
				FDCBillInfo info;
				if(type.equals(new ContractBillInfo().getBOSType())){
					info = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				}else{
					info = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
				}
				if(info!=null){
					rowSet.updateString("contractBill.number",info.getNumber());
					rowSet.updateString("contractBill.contractName",info.getName());
				}
			}

			rowSet.beforeFirst();
		}catch (Exception exc) {
			handUIExceptionAndAbort(exc);
		}
		super.onGetRowSet(rowSet);
		
	}
	
	
	/**
	 * ��������ۿ���Ĺ�������
	 * @author sxhong  		Date 2007-3-14
	 * @return
	 */
	private FilterInfo getDeductFilter() {
		
//		Timestamp createTime=(Timestamp)getUIContext().get("createTime");
		String contractBillId = (String) getUIContext().get("contractBillId");
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		items.add(new FilterItemInfo("entrys.contractId",contractBillId,CompareType.EQUALS));
	    items.add(new FilterItemInfo("entrys.hasApplied",Boolean.FALSE,CompareType.EQUALS));
		//����hasApplied���ܵ���NULL���ò����ڽ����ų�����
//		items.add(new FilterItemInfo("entrys.hasApplied",Boolean.TRUE,CompareType.NOTEQUALS));
		items.add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		items.add(new FilterItemInfo("entrys.deductType.id", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
		//�����뵥��ͬ���·�֮ǰ��
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(createTime);
//		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
//		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
//		items.add(new FilterItemInfo("createTime",createTime,CompareType.LESS_EQUALS));
		return filter;
	}
	
	/**
	 * �������������Ĺ�������
	 * @author sxhong  		Date 2007-3-14
	 * @return 
	 */
	private FilterInfo getGuerdoFilter() {
//		Timestamp createTime=(Timestamp)getUIContext().get("createTime");
		String contractBillId = (String) getUIContext().get("contractBillId");
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		items.add(new FilterItemInfo("contract.id",contractBillId,CompareType.EQUALS));
//	    	items.add(new FilterItemInfo("entrys.hasApplied",Boolean.FALSE,CompareType.EQUALS));
		//����hasApplied���ܵ���NULL���ò����ڽ����ų�����,BT��Boolean.TRUE��Ȼ����
//		items.add(new FilterItemInfo("isGuerdoned",Boolean.TRUE,CompareType.NOTEQUALS));
		items.add(new FilterItemInfo("isGuerdoned",String.valueOf("1"),CompareType.NOTEQUALS));
		items.add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		//�����뵥��ͬ���·�֮ǰ��
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(createTime);
//		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
//		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
//		items.add(new FilterItemInfo("createTime",createTime,CompareType.LESS_EQUALS));
		
		//TODO ���˵��Ѹ����
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo myfilter=new FilterInfo();
//		myfilter.getFilterItems().add(new FilterItemInfo("payRequestBill.id", payRequestBillId,CompareType.NOTEQUALS));
		myfilter.appendFilterItem("payRequestBill.contractId", contractBillId);
		view.setFilter(myfilter);
		view.getSelector().add("payRequestBill.id");
		view.getSelector().add("guerdon.id");
		
		try{
			final GuerdonOfPayReqBillCollection c = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
			if(c.size()>0){
				HashSet notInSet=new HashSet();
				HashSet inSet=new HashSet();
				for (int i = 0; i < c.size(); i++) {
					GuerdonOfPayReqBillInfo info = c.get(i);
					if(info.getPayRequestBill().getId().toString().equals(payRequestBillId)){
						inSet.add(info.getGuerdon().getId());
					}else{
						notInSet.add(info.getGuerdon().getId());
					}
				}
				if(notInSet.size()>0){
					items.add(new FilterItemInfo("id",notInSet,CompareType.NOTINCLUDE));
				}
				
				if(inSet.size()>0){
					//������ѡ��ĵ���
					FilterInfo tempFilter=new FilterInfo();
					tempFilter.getFilterItems().add(new FilterItemInfo("id",inSet,CompareType.INCLUDE));
					filter.mergeFilter(tempFilter, "OR");
				}
			}
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		return filter;
	}
	
	/**
	 * �洢������
	 * @author sxhong  		Date 2007-3-15
	 */
	private synchronized void storeGuerdon() throws Exception {
		String contractBillId = (String) getUIContext().get("contractBillId");
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		
		CoreBaseCollection collection=new CoreBaseCollection();
		
		//��ѡ������Ӧ��GuerdonBill��isGuerdoned
		GuerdonBillInfo info=new GuerdonBillInfo();
		IRow row=null;
		String id=null;
		//		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal originalAmount = FDCHelper.ZERO;
		BigDecimal sum=FDCHelper.ZERO;
		
		boolean isGuerdoned=false;
		SelectorItemCollection selector=new  SelectorItemCollection();
		selector.add("id");
		selector.add("isGuerdoned");
//		selector.add("amount");
		for(int i=0;i<tblMainGuerdon.getRowCount();i++){
			row=tblMainGuerdon.getRow(i);
			id=row.getCell("id").getValue().toString();
			isGuerdoned=((Boolean)row.getCell("isGuerdoned").getValue()).booleanValue();
			amount = FDCHelper.toBigDecimal(row.getCell("amount").getValue());
			originalAmount = FDCHelper.toBigDecimal(row.getCell("originalAmount").getValue());
			
			info=new GuerdonBillInfo();
			info.setId(BOSUuid.read(id));
			info.setIsGuerdoned(isGuerdoned);
			
//			info.setIsGuerdoned(true);
//			info.setAmount(FDCHelper.ONE_HUNDRED_MILLION);
			GuerdonBillFactory.getRemoteInstance().updatePartial(info, selector);
			//д�뵽GuerdonOfPayReqBill����
			if(isGuerdoned){
				GuerdonOfPayReqBillInfo item=new GuerdonOfPayReqBillInfo();
				item.setAmount(amount);
				item.setOriginalAmount(originalAmount);
				final PayRequestBillInfo payRequestBillInfo = new PayRequestBillInfo();
				payRequestBillInfo.setId(BOSUuid.read(payRequestBillId));
				item.setPayRequestBill(payRequestBillInfo);
				item.setGuerdon((GuerdonBillInfo)info.clone());
				collection.add(item);
				
				if(amount!=null){
					sum=amount.add(sum);
				}
			}
			
		}
		//���仯д�뵽GuerdonOfPayReqBill��
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("payRequestBill.id", payRequestBillId);
		GuerdonOfPayReqBillFactory.getRemoteInstance().delete(filter);
		
		GuerdonOfPayReqBillFactory.getRemoteInstance().addnew(collection);
		
		this.amount=sum;
	}

	public BigDecimal getGuerdonData(){
		return this.amount;
	}	
	
	private void setCompensationTableList(KDTable table) throws Exception {
		tblCompensation.removeRows(false);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=getCompensationFilter();
		view.setFilter(filter);
		
		view.getSelector().add("isCompensated");
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("createTime");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("contract.name");
		view.getSelector().add("contract.number");
		view.getSelector().add("contract.curProject.number");
		view.getSelector().add("contract.curProject.name");
		view.getSelector().add("creator.name");
		
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSorter().add(new SorterItemInfo("createTime"));
		final CompensationBillCollection c = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
		for(int i=0;i<c.size();i++){
			setCompensationTableRow(table,c.get(i));
		}
		table.getStyleAttributes().setLocked(true);
		Object obj = getUIContext().get("billState");
		if(obj!=null&&!obj.equals(OprtState.VIEW) && !obj.equals("FINDVIEW")){
			table.getColumn("isCompensated").getStyleAttributes().setLocked(false);
		}
		
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		FDCHelper.formatTableNumber(table, "amount");
		table.getColumn("createTime").getStyleAttributes().setNumberFormat(FDCHelper.KDTABLE_DATE_FMT);
//		table.getColumn("createTime").getStyleAttributes().setLocked(true);
//		tblMain.getSelectManager().setSelectMode(
//                KDTSelectManager.ROW_SELECT);
		
	}


	private void setCompensationTableRow(KDTable table,CompensationBillInfo info) {
		IRow row=table.addRow();
		row.getCell("isCompensated").setValue(Boolean.valueOf(info.isIsCompensated()));
		row.getCell("curProject.number").setValue(info.getContract().getCurProject().getNumber());
		row.getCell("curProject.name").setValue(info.getContract().getCurProject().getName());
		row.getCell("contract.number").setValue(info.getContract().getNumber());
		row.getCell("contract.name").setValue(info.getContract().getName());
		row.getCell("number").setValue(info.getNumber());
		row.getCell("name").setValue(info.getName());
		row.getCell("amount").setValue(info.getAmount());
		row.getCell("originalAmount").setValue(info.getOriginalAmount());
		row.getCell("createTime").setValue(info.getCreateTime());
		row.getCell("creator.name").setValue(info.getCreator().getName());
		row.getCell("id").setValue(info.getId().toString());
	}
	
	/**
	 * �������������Ĺ�������
	 * @author sxhong  		Date 2007-3-14
	 * @return 
	 */
	private FilterInfo getCompensationFilter() {
//		Timestamp createTime=(Timestamp)getUIContext().get("createTime");
		String contractBillId = (String) getUIContext().get("contractBillId");
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		items.add(new FilterItemInfo("contract.id",contractBillId,CompareType.EQUALS));
//	    	items.add(new FilterItemInfo("entrys.hasApplied",Boolean.FALSE,CompareType.EQUALS));
		//����hasApplied���ܵ���NULL���ò����ڽ����ų�����
		items.add(new FilterItemInfo("isCompensated",Boolean.TRUE,CompareType.NOTEQUALS));
		items.add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE,CompareType.EQUALS));
		//�����뵥��ͬ���·�֮ǰ��
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(createTime);
//		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
//		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
//		items.add(new FilterItemInfo("createTime",createTime,CompareType.LESS_EQUALS));
		
		//TODO ���˵��Ѹ����
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo myfilter=new FilterInfo();
//		myfilter.getFilterItems().add(new FilterItemInfo("payRequestBill.id", payRequestBillId,CompareType.NOTEQUALS));
		myfilter.appendFilterItem("payRequestBill.contractId", contractBillId);
		view.setFilter(myfilter);
		view.getSelector().add("compensation.id");
		view.getSelector().add("payRequestBill.id");
		try{
			final CompensationOfPayReqBillCollection c = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
			if(c.size()>0){
				HashSet notInSet=new HashSet();
				HashSet inSet=new HashSet();
				for (int i = 0; i < c.size(); i++) {
					CompensationOfPayReqBillInfo info = c.get(i);
					if(info.getPayRequestBill().getId().toString().equals(payRequestBillId)){
						inSet.add(info.getCompensation().getId());
					}else{
						notInSet.add(info.getCompensation().getId());
					}
				}
				if(notInSet.size()>0){
					items.add(new FilterItemInfo("id",notInSet,CompareType.NOTINCLUDE));
				}
				
				if(inSet.size()>0){
					//������ѡ��ĵ���
					FilterInfo tempFilter=new FilterInfo();
					tempFilter.getFilterItems().add(new FilterItemInfo("id",inSet,CompareType.INCLUDE));
					filter.mergeFilter(tempFilter, "OR");
				}
			}
			
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		return filter;
	}
	
	/**
	 * �洢������
	 * @author sxhong  		Date 2007-3-15
	 */
	private synchronized void storeCompensation() throws Exception {
		String contractBillId = (String) getUIContext().get("contractBillId");
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		
		CoreBaseCollection collection=new CoreBaseCollection();
		
		//��ѡ������Ӧ��CompensationBill��isCompensationed
		CompensationBillInfo info=new CompensationBillInfo();
		IRow row=null;
		String id=null;
		//		BigDecimal amount = FDCHelper.ZERO;
		BigDecimal originalAmount = FDCHelper.ZERO;
		BigDecimal sum = FDCHelper.ZERO;
		
		boolean isCompensationed=false;
		SelectorItemCollection selector=new  SelectorItemCollection();
		selector.add("id");
		selector.add("isCompensated");
//		selector.add("amount");
		for(int i=0;i<tblCompensation.getRowCount();i++){
			row=tblCompensation.getRow(i);
			id=row.getCell("id").getValue().toString();
			isCompensationed=((Boolean)row.getCell("isCompensated").getValue()).booleanValue();
			amount=FDCHelper.toBigDecimal(row.getCell("amount").getValue());
			originalAmount = FDCHelper.toBigDecimal(row.getCell("originalAmount").getValue());
			
			info=new CompensationBillInfo();
			info.setId(BOSUuid.read(id));
			info.setIsCompensated(isCompensationed);
//			info.setIsCompensationed(true);
//			info.setAmount(FDCHelper.ONE_HUNDRED_MILLION);
			CompensationBillFactory.getRemoteInstance().updatePartial(info, selector);
			//д�뵽CompensationOfPayReqBill����
			if(isCompensationed){
				CompensationOfPayReqBillInfo item=new CompensationOfPayReqBillInfo();
				item.setAmount(amount);
				item.setOriginalAmount(originalAmount);
				final PayRequestBillInfo payRequestBillInfo = new PayRequestBillInfo();
				payRequestBillInfo.setId(BOSUuid.read(payRequestBillId));
				item.setPayRequestBill(payRequestBillInfo);
				item.setCompensation((CompensationBillInfo)info.clone());
				collection.add(item);
				
				if(amount!=null){
					sum=amount.add(sum);
				}
			}
			
		}
		//���仯д�뵽CompensationOfPayReqBill��
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("payRequestBill.id", payRequestBillId);
		CompensationOfPayReqBillFactory.getRemoteInstance().delete(filter);
		
		CompensationOfPayReqBillFactory.getRemoteInstance().addnew(collection);
		
		this.compensatAmount=sum;
	}

	public BigDecimal getCompensationData(){
		return this.compensatAmount;
	}
	
	/**
	 * �洢�׹���ȷ�ϵ�
	 */
    private synchronized void storeMaterialConfirm() throws Exception{
    	String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		
		CoreBaseCollection collection=new CoreBaseCollection();
		
		//��ѡ������Ӧ���׹�ȷ�ϵ�MaterialConfirmBill��hasApplied
		MaterialConfirmBillInfo info = null;
		IRow row=null;
		String id=null;
//		BigDecimal paidAmt=null;
		BigDecimal thisTimeDeductAmt=null;
//		BigDecimal hasDeductAmt=null;
		BigDecimal sum=FDCHelper.ZERO;
		
		boolean hasApplied=false;
		SelectorItemCollection selector=new  SelectorItemCollection();
		selector.add("id");
		selector.add(COL_HAS_APPLIED);
		for(int i=0;i<tblPartAMaterial.getRowCount();i++){
			row=tblPartAMaterial.getRow(i);
			id=row.getCell("id").getValue().toString();
			hasApplied=((Boolean)row.getCell(COL_HAS_APPLIED).getValue()).booleanValue();
//			paidAmt=FDCHelper.toBigDecimal(row.getCell("deductAmt").getValue());
			thisTimeDeductAmt = FDCHelper.toBigDecimal(row.getCell(COL_THIS_TIME_DEDUCT_AMT).getValue());//���οۿ���
//			hasDeductAmt =  FDCHelper.toBigDecimal(row.getCell("hasDeductAmt").getValue());//�ѿۿ���
			
			info=new MaterialConfirmBillInfo();
			info.setId(BOSUuid.read(id));
			info.setHasApplied(hasApplied);
			MaterialConfirmBillFactory.getRemoteInstance().updatePartial(info, selector);
			//д�뵽PartAConfmOfPayReqBill����
			if(hasApplied){
				//���ۿ������ϸ���ݱ�����������Ϊÿ�Ÿ������뵥����Ҫ������ʱ���ʱ������Ŀۿ�Ŀۿ���ϸ�������ǿ������¿ۿ����ݼ��� by cassiel 
//				BigDecimal stayDeductAmt = FDCHelper.toBigDecimal(row.getCell("stayDeductAmt").getValue());
				BigDecimal deductAmt = FDCHelper.toBigDecimal(row.getCell("deductAmt").getValue());
				ShowDeductOfPartABillInfo showBill = new ShowDeductOfPartABillInfo();
				showBill.setId(BOSUuid.create(new ShowDeductOfPartABill().getType()));
				showBill.setPayReqID(payRequestBillId);
				showBill.setDeductEntryID(id);
				showBill.setThisTimeDeductAmount(thisTimeDeductAmt);//���οۿ���
//				showBill.setStayDeductAmount(stayDeductAmt);//���ۿ���
				showBill.setDeductAmount(deductAmt);
//				showBill.setHasDeductAmount(hasDeductAmt);
				showBill.setIsRelated(true);
				//�������޸Ļ�����������"�˵���"ɾ��������һ���µ���
				FilterInfo _filter = new FilterInfo();
				_filter.getFilterItems().add(new FilterItemInfo("payReqID",payRequestBillId));
				_filter.getFilterItems().add(new FilterItemInfo("deductEntryID",id));
				ShowDeductOfPartABillFactory.getRemoteInstance().delete(_filter);
				ShowDeductOfPartABillFactory.getRemoteInstance().addnew(showBill);
				
				PartAConfmOfPayReqBillInfo item=new PartAConfmOfPayReqBillInfo();
				item.setAmount(thisTimeDeductAmt);
				item.setOriginalAmount(thisTimeDeductAmt.divide(exRate,5, BigDecimal.ROUND_HALF_UP));	
				final PayRequestBillInfo payRequestBillInfo = new PayRequestBillInfo();
				payRequestBillInfo.setId(BOSUuid.read(payRequestBillId));
				item.setPayRequestBill(payRequestBillInfo);
				item.setMaterialConfirmBill((MaterialConfirmBillInfo)info.clone());
				item.setHasPaid(false);
				collection.add(item);
				
				if(thisTimeDeductAmt!=null){
					sum=thisTimeDeductAmt.add(sum);
				}
			}
		}
		//���仯д�뵽PartAOfPayReqBill��
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("payRequestBill.id", payRequestBillId);
		PartAConfmOfPayReqBillFactory.getRemoteInstance().delete(filter);
		
		PartAConfmOfPayReqBillFactory.getRemoteInstance().addnew(collection);
		
		this.partAConfmAmt=sum;
	}
    
    private FilterInfo getPartAConfmFilter(){
    	
//    	Timestamp createTime=(Timestamp)getUIContext().get("createTime");
		String contractBillId = (String) getUIContext().get("contractBillId");
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		//����hasApplied���ܵ���NULL���ò����ڽ����ų�����
//		items.add(new FilterItemInfo("hasApplied", Boolean.valueOf(true), CompareType.NOTEQUALS));
		items.add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		items.add(new FilterItemInfo("mainContractBill.id", contractBillId));
		items.add(new FilterItemInfo("materialContractBill.isCoseSplit", Boolean.FALSE));
		items.add(new FilterItemInfo("materialContractBill.isPartAMaterialCon", Boolean.TRUE));
		//�����뵥��ͬ���·�֮ǰ��
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(createTime);
//		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
//		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
//		items.add(new FilterItemInfo("createTime",createTime,CompareType.LESS_EQUALS));
		//TODO ���˵��Ѹ����
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo myfilter=new FilterInfo();
		myfilter.appendFilterItem("payRequestBill.contractId", contractBillId);
		view.setFilter(myfilter);
		view.getSelector().add("materialConfirmBill.id");
		view.getSelector().add("payRequestBill.id");
		try{
			final PartAConfmOfPayReqBillCollection c = PartAConfmOfPayReqBillFactory.getRemoteInstance().getPartAConfmOfPayReqBillCollection(view);
			if(c.size()>0){
				HashSet inSet=new HashSet();
				for (int i = 0; i < c.size(); i++) {
					PartAConfmOfPayReqBillInfo info = c.get(i);
					if(info.getPayRequestBill().getId().toString().equals(payRequestBillId)){
						inSet.add(info.getMaterialConfirmBill().getId());
					}
				}
				
				if(inSet.size()>0){
					//������ѡ��ĵ���
					FilterInfo tempFilter=new FilterInfo();
					tempFilter.getFilterItems().add(new FilterItemInfo("id",inSet,CompareType.INCLUDE));
					filter.mergeFilter(tempFilter, "OR");
				}
			}
			
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		
		return filter;
    }
    
    public BigDecimal getPartAConfmData(){
    	return this.partAConfmAmt;
    }

    /**
     * �洢�׹��Ŀۿ
     */
	private synchronized void storePartADeductBill() throws Exception{
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		
		CoreBaseCollection collection=new CoreBaseCollection();
		
		//��ѡ������Ӧ���׹�deductBillEntry��hasApplied
		DeductBillEntryInfo entry = null;
		IRow row=null;
		String id=null;
		BigDecimal thisTimeDeductAmt=null;
//		BigDecimal hasDeductAmt=null;
		BigDecimal sum=FDCHelper.ZERO;
		
		boolean hasApplied=false;
		SelectorItemCollection selector = new  SelectorItemCollection();
		selector.add("id");
		selector.add(COL_HAS_APPLIED);
//		selector.add("hasDeductAmt");
		for(int i=0;i<tblPartAMaterial.getRowCount();i++){
			row=tblPartAMaterial.getRow(i);
			id=row.getCell("id").getValue().toString();
			hasApplied=((Boolean)row.getCell(COL_HAS_APPLIED).getValue()).booleanValue();
			thisTimeDeductAmt = FDCHelper.toBigDecimal(row.getCell(COL_THIS_TIME_DEDUCT_AMT).getValue());//���οۿ���
//			hasDeductAmt =  FDCHelper.toBigDecimal(row.getCell("hasDeductAmt").getValue());//�ѿۿ���
			
			entry=new DeductBillEntryInfo();
			entry.setId(BOSUuid.read(id));
			entry.setHasApplied(hasApplied);
//			entry.setHasDeductAmt(hasDeductAmt);
			
			DeductBillEntryFactory.getRemoteInstance().updatePartial(entry, selector);
			//д�뵽PartAOfPayReqBill����
			if(hasApplied){
				//���ۿ������ϸ���ݱ�����������Ϊÿ�Ÿ������뵥����Ҫ������ʱ���ʱ������Ŀۿ�Ŀۿ���ϸ�������ǿ������¿ۿ����ݼ��� by cassiel 
//				BigDecimal stayDeductAmt = FDCHelper.toBigDecimal(row.getCell("stayDeductAmt").getValue());
				BigDecimal deductAmt = FDCHelper.toBigDecimal(row.getCell("deductAmt").getValue());
				ShowDeductOfPartABillInfo showBill = new ShowDeductOfPartABillInfo();
				showBill.setId(BOSUuid.create(new ShowDeductOfPartABill().getType()));
				showBill.setPayReqID(payRequestBillId);
				showBill.setDeductEntryID(id);
				showBill.setThisTimeDeductAmount(thisTimeDeductAmt);//���οۿ���
//				showBill.setStayDeductAmount(stayDeductAmt);//�����ֶΣ����ۿ���=�ܿ�-�ѿ�-���ο�
				showBill.setDeductAmount(deductAmt);
//				showBill.setHasDeductAmount(hasDeductAmt);//�����ֶλ�Ҫά��,�鷳
				showBill.setIsRelated(true);
				//�������޸Ļ�����������"�˵���"ɾ��������һ���µ���
				FilterInfo _filter = new FilterInfo();
				_filter.getFilterItems().add(new FilterItemInfo("payReqID",payRequestBillId));
				_filter.getFilterItems().add(new FilterItemInfo("deductEntryID",id));
				ShowDeductOfPartABillFactory.getRemoteInstance().delete(_filter);
				ShowDeductOfPartABillFactory.getRemoteInstance().addnew(showBill);
				
				PartAOfPayReqBillInfo item=new PartAOfPayReqBillInfo();
				item.setAmount(thisTimeDeductAmt);
				item.setOriginalAmount(thisTimeDeductAmt.divide(exRate, 5,BigDecimal.ROUND_HALF_UP));
				final PayRequestBillInfo payRequestBillInfo = new PayRequestBillInfo();
				payRequestBillInfo.setId(BOSUuid.read(payRequestBillId));
				item.setPayRequestBill(payRequestBillInfo);

				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id",id));
				view.setFilter(filter);
				DeductBillEntryInfo en = DeductBillEntryFactory.getRemoteInstance().getDeductBillEntryCollection(view).get(0);
				DeductBillInfo deduct = new DeductBillInfo();
				deduct.setId(en.getParent().getId());
				deduct.getEntrys().add(entry);
				entry.setParent(deduct);
				
				item.setDeductBill((DeductBillInfo)deduct.clone());
				item.setHasPaid(false);
				collection.add(item);
				
				if(thisTimeDeductAmt!=null){
					sum=thisTimeDeductAmt.add(sum);
				}
			}else{
				//δѡ����ɾ��
				FilterInfo _filter = new FilterInfo();
				_filter.getFilterItems().add(new FilterItemInfo("payReqID",payRequestBillId,CompareType.INCLUDE));
				ShowDeductOfPartABillFactory.getRemoteInstance().delete(_filter);
			}
		}
		//���仯д�뵽PartAOfPayReqBill��
		FilterInfo filter=new FilterInfo();
		filter.appendFilterItem("payRequestBill.id", payRequestBillId);
		PartAOfPayReqBillFactory.getRemoteInstance().delete(filter);
		
		PartAOfPayReqBillFactory.getRemoteInstance().addnew(collection);
		
		this.partADeductAmt=sum;
	}
	
	public BigDecimal getPartAData(){
    	return this.partADeductAmt;
    }
    
	/**
	 * ��������������׹��ۿ��������
	 * @return filter
	 */
	private FilterInfo getPartAFilter(){
		
//		Timestamp createTime=(Timestamp)getUIContext().get("createTime");
		String contractBillId = (String) getUIContext().get("contractBillId");
		String payRequestBillId=(String)getUIContext().get("payRequestBillId");
		FilterInfo filter=new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
//		items.add(new FilterItemInfo("hasApplied", Boolean.valueOf(true), CompareType.NOTEQUALS));
		items.add(new FilterItemInfo("contractId", contractBillId, CompareType.EQUALS));
		items.add(new FilterItemInfo("deductType.id", DeductTypeInfo.partAMaterialType));
		items.add(new FilterItemInfo("Parent.state", FDCBillStateEnum.AUDITTED_VALUE));
		//�����뵥��ͬ���·�֮ǰ��
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(createTime);
//		createTime.setDate(cal.getActualMaximum(Calendar.DATE));
//		createTime.setHours(cal.getActualMaximum(Calendar.HOUR_OF_DAY)); //24Сʱ��
//		createTime.setMinutes(cal.getActualMaximum(Calendar.MINUTE));
//		createTime.setSeconds(cal.getActualMaximum(Calendar.SECOND));
//		items.add(new FilterItemInfo("Parent.createTime",createTime,CompareType.LESS_EQUALS));
		
		//TODO ���˵��Ѹ����
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo myfilter=new FilterInfo();
		myfilter.appendFilterItem("payRequestBill.contractId", contractBillId);
		view.setFilter(myfilter);
		view.getSelector().add("deductBill.entrys.id");
		view.getSelector().add("payRequestBill.id");
		view.getSelector().add("deductBill.entrys.contractId");
		try{
			final PartAOfPayReqBillCollection c = PartAOfPayReqBillFactory.getRemoteInstance().getPartAOfPayReqBillCollection(view);
			if(c.size()>0){
				HashSet inSet=new HashSet();
				for (int i = 0; i < c.size(); i++) { 
					PartAOfPayReqBillInfo info = c.get(i);
					if(info.getDeductBill().getEntrys()==null || info.getDeductBill().getEntrys().size()==0){
						continue;
					}
					int deductEntrySize = info.getDeductBill().getEntrys().size();
					for(int j=0;j<deductEntrySize;j++){
						DeductBillEntryInfo entryInfo = info.getDeductBill().getEntrys().get(j);
						if(entryInfo.getContractId().equals(contractBillId)){
							if(info.getPayRequestBill().getId().toString().equals(payRequestBillId)){
								inSet.add(entryInfo.getId());
							}
						}
					}
				}
				
				if(inSet.size()>0){
					//������ѡ��ĵ���
					FilterInfo tempFilter=new FilterInfo();
					tempFilter.getFilterItems().add(new FilterItemInfo("id",inSet,CompareType.INCLUDE));
					filter.mergeFilter(tempFilter, "OR");
				}
			}
			
		}catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		return filter;
	}
}