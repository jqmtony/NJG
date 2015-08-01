/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionEvent;
import com.kingdee.bos.ctrl.kdf.table.event.BeforeActionListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTPropertyChangeListener;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CellBinder;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.NodeMeasureEntryCollection;
import com.kingdee.eas.fdc.contract.NodeMeasureEntryInfo;
import com.kingdee.eas.fdc.contract.NodeMeasureFactory;
import com.kingdee.eas.fdc.contract.NodeMeasureInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class NodeMeasureEditUI extends AbstractNodeMeasureEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(NodeMeasureEditUI.class);
    
    private static final Color NOEDITORCOLOR = new Color(232, 232, 227);
    
    //是否变化
    boolean isChange = true;
    //初始化数据
    private Map initData = null;
    //成本科目名称
    private static final String COL_ACCOUNT = "accountName";
    //计量单位
    private static final String COL_UNIT = "unit";
    //工程量
    private static final String COL_TOTAL = "Total";
    //单价
    private static final String COL_PRICE = "Price";
    //值
    private static final String COL_VALUE = "Value";
    
    /**
     * output class constructor
     */
    public NodeMeasureEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	editData.setName(this.txtNumber.getText()+new Date().getTime());
    	editData.setAmount(this.txtAmount.getBigDecimalValue());
    	editData.setDescription(this.txtDesc.getText());
    	editData.setNumber(this.txtNumber.getText());
    	editData.setBizDate(DateTimeUtils.truncateDate((Date)this.pkDate.getValue()));
    	editData.getEntrys().clear();
    	NodeMeasureEntryCollection entrys = getEntrys();
    	editData.getEntrys().addCollection(entrys);
        super.storeFields();
    }


    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		fillTable();
		isChange = false;
	}

	/**
     * 保存
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
        isChange = false;
    }

    /**
     * 提交
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
        isChange = false;
    }

    /**
     * 修改
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {

        super.actionEdit_actionPerformed(e);
        NodeMeasureInfo info = (NodeMeasureInfo)initData.get("NodeMeasureInfo");
        if(info.getState().equals(FDCBillStateEnum.SUBMITTED)){
        	this.actionSave.setEnabled(false);
        }
        tblMain.setEnabled(true);
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.actionRemove_actionPerformed(e);
		this.actionAudit.setVisible(false);
	}

	/**
     * 流程图
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

 
	protected IObjectValue createNewData() {
		NodeMeasureInfo info = new NodeMeasureInfo();
//		String contractBillId = (String)getUIContext().get("contractBillId");
//		
//		ContractBillInfo contractBillInfo = null;
//		try {
//			contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillId), new ContractBillEditUI().getSelectors());			
//		} catch (Exception e1) {
//			handUIException(e1);
//		}
//		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//		info.setCreateTime(new Timestamp(new Date().getTime()));
//		info.setBizDate(new Date());
//		info.setContractBill(contractBillInfo);
//		info.setCurProject(contractBillInfo.getCurProject());
//		info.setState(FDCBillStateEnum.SAVED);
		isChange = false;
		return info;
	}

	protected void attachListeners() {

	}

	protected void detachListeners() {
		
	}

	//应用编码规则的控件
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		//初始化控件
		initCtrl();
		//填充数据

		
		initCtrlListener();
		NodeMeasureInfo info = (NodeMeasureInfo)initData.get("NodeMeasureInfo");
		if(info.getState().equals(FDCBillStateEnum.SUBMITTED)){
			this.actionSave.setEnabled(false);
		}
    	if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
    		this.actionEdit.setEnabled(false);
    	}
    	isChange = false;
    	tblMain_editStopped(null);
    	isChange = false;
	}

	//初始化数据 重载掉
	protected void fetchInitData() throws Exception {

	}
	private void fetchData()  {
		String contractBillId = (String)getUIContext().get("contractBillId");
		String nodeMeasureId = null;
		if(!this.getOprtState().equals(STATUS_ADDNEW)){
			if(editData != null && editData.getId() != null){
				nodeMeasureId = editData.getId().toString();
			}else{
				nodeMeasureId = (String)getUIContext().get(com.kingdee.eas.common.client.UIContext.ID);							
			}
		}
		//如果id为null，则是新增状态
		if(nodeMeasureId == null || nodeMeasureId.equals("")){
			
			try {
				initData = NodeMeasureFactory.getRemoteInstance()
					.fetchData(contractBillId,null);
			} catch (EASBizException e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			}
		}else{
			try {
				initData = NodeMeasureFactory.getRemoteInstance()
					.fetchData(contractBillId,nodeMeasureId);
			} catch (EASBizException e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
				handUIExceptionAndAbort(e);
			}	
		}
//		editData =  (NodeMeasureInfo)initData.get("NodeMeasureInfo");
	}
	protected ICoreBase getBizInterface() throws Exception {

		return NodeMeasureFactory.getRemoteInstance();
	}
	//初始化控件
	private void initCtrl() {
		this.tblMain.checkParsed();
		this.actionAddLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionDelVoucher.setVisible(false);
		this.actionExitCurrent.setVisible(false);
		this.actionExport.setVisible(false);
		this.actionExportSelected.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemoveLine.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionStartWorkFlow.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionViewDoProccess.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.menuWorkflow.setEnabled(false);
		this.menuWorkflow.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuTable1.setEnabled(false);
		this.menuBiz.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuView.setEnabled(false);
		this.menuView.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		this.menuSubmitOption.setEnabled(false);
		
//		this.actionAttachment.setVisible(false);
		KDFormattedTextField quantityTextField = new KDFormattedTextField(
				KDFormattedTextField.BIGDECIMAL_TYPE);
		quantityTextField.setPrecision(2);
		quantityTextField.setRemoveingZeroInDispaly(true);
		quantityTextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		quantityTextField.setSupportedEmpty(false);
		quantityTextField.setNegatived(false);
		quantityTextField.setMinimumValue(FDCHelper.MIN_VALUE);
		quantityTextField.setMaximumValue(FDCHelper.MAX_VALUE);
		quantityTextField.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		ICellEditor quantityEditor = new KDTDefaultCellEditor(quantityTextField);
		quantityEditor=CellBinder.getCellNumberEdit();
		tblMain.getColumn("node"+COL_TOTAL).setEditor(quantityEditor);
		tblMain.getColumn("node"+COL_PRICE).setEditor(quantityEditor);
		tblMain.getColumn("node"+COL_VALUE).setEditor(quantityEditor);
		tblMain.getColumn("con"+COL_TOTAL).getStyleAttributes().setNumberFormat("#,##0.00");
		tblMain.getColumn("con"+COL_PRICE).getStyleAttributes().setNumberFormat("#,##0.00");
		tblMain.getColumn("con"+COL_VALUE).getStyleAttributes().setNumberFormat("#,##0.00");
		FDCHelper.formatTableNumber(tblMain,"con"+COL_TOTAL);
		FDCHelper.formatTableNumber(tblMain,"change"+COL_TOTAL);
		FDCHelper.formatTableNumber(tblMain,"settle"+COL_TOTAL);
		FDCHelper.formatTableNumber(tblMain,"node"+COL_TOTAL);
		FDCHelper.formatTableNumber(tblMain,"pay"+COL_TOTAL);
		FDCHelper.formatTableNumber(tblMain,"con"+COL_PRICE);
		FDCHelper.formatTableNumber(tblMain,"change"+COL_PRICE);
		FDCHelper.formatTableNumber(tblMain,"settle"+COL_PRICE);
		FDCHelper.formatTableNumber(tblMain,"node"+COL_PRICE);
		FDCHelper.formatTableNumber(tblMain,"pay"+COL_PRICE);
		FDCHelper.formatTableNumber(tblMain,"con"+COL_VALUE);
		FDCHelper.formatTableNumber(tblMain,"change"+COL_VALUE);
		FDCHelper.formatTableNumber(tblMain,"settle"+COL_VALUE);
		FDCHelper.formatTableNumber(tblMain,"node"+COL_VALUE);
		FDCHelper.formatTableNumber(tblMain,"pay"+COL_VALUE);
		//设置tblMain表格拷贝模式为值拷贝
		FDCTableHelper.setPaseMode(tblMain);
		txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtAmount.setPrecision(2);
	}
	//重载掉编码规则
	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		super.handleCodingRule();
	}
	//填充表头
	protected void fillHead()  {
		if(initData == null || initData.size() == 0){
			return;
		}
		NodeMeasureInfo info = (NodeMeasureInfo)initData.get("NodeMeasureInfo");
		this.prmtContract.setValue(info.getContractBill());
		this.prmtProject.setValue(info.getCurProject());
		this.prmtCreator.setValue(info.getCreator());
		this.pkDate.setValue(info.getBizDate());
		this.pkCreateTime.setValue(info.getCreateTime());
		if(info.getNumber() != null){
			this.txtNumber.setText(info.getNumber());			
		}
		this.txtAmount.setValue(info.getAmount());
		this.txtDesc.setText(info.getDescription());
	}

	//填充分录
	protected void fillTable() {
		fetchData();
		fillHead();
		if(initData == null || initData.size() == 0){
			return;
		}
		tblMain.removeRows();
		//获得所有科目
		tblMain.getStyleAttributes().setLocked(true);
		CostAccountCollection costAccountColl = (CostAccountCollection)initData.get("CostAccountCollection");
		NodeMeasureInfo info = (NodeMeasureInfo)initData.get("NodeMeasureInfo");
		boolean isAudit = false;
		if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
			isAudit = true;
		}
		tblMain.setEnabled(false);
		for(Iterator it = costAccountColl.iterator();it.hasNext();){
			CostAccountInfo costAccount = (CostAccountInfo)it.next();
			String acctId = costAccount.getId().toString();
			Map rowMap  = (Map)initData.get(acctId);
			if(rowMap == null || rowMap.size() <= 0)
				continue;
			IRow row = this.tblMain.addRow();
			row.setUserObject(costAccount);
			row.getCell(COL_ACCOUNT).setValue(costAccount.getName());
			row.getCell(COL_UNIT).setValue(rowMap.get("unit"));
			row.getCell("con"+COL_TOTAL).setValue(rowMap.get("contract_workLoad"));
			row.getCell("con"+COL_PRICE).setValue(rowMap.get("contract_price"));
			row.getCell("con"+COL_VALUE).setValue(rowMap.get("contract_amount"));
			
			
			row.getCell("change"+COL_TOTAL).setValue(rowMap.get("change_workLoad"));
			row.getCell("change"+COL_PRICE).setValue(rowMap.get("change_price"));
			row.getCell("change"+COL_VALUE).setValue(rowMap.get("change_amount"));
			row.getCell("settle"+COL_TOTAL).setValue(rowMap.get("settlement_workLoad"));
			row.getCell("settle"+COL_PRICE).setValue(rowMap.get("settlement_price"));
			row.getCell("settle"+COL_VALUE).setValue(rowMap.get("settlement_amount"));
			row.getCell("pay"+COL_TOTAL).setValue(rowMap.get("payment_workLoad"));
			row.getCell("pay"+COL_PRICE).setValue(rowMap.get("payment_price"));
			row.getCell("pay"+COL_VALUE).setValue(rowMap.get("payment_amount"));
			
			row.getCell("node"+COL_TOTAL).setValue(rowMap.get("nodemeasure_workLoad"));
			row.getCell("node"+COL_PRICE).setValue(rowMap.get("nodemeasure_price"));
			row.getCell("node"+COL_VALUE).setValue(rowMap.get("nodemeasure_amount"));
			row.getCell("node"+COL_TOTAL).getStyleAttributes().setLocked(false);
			row.getCell("node"+COL_PRICE).getStyleAttributes().setLocked(false);
			row.getCell("node"+COL_VALUE).getStyleAttributes().setLocked(false);

			BigDecimal total = FDCHelper.toBigDecimal(row.getCell("node"+COL_TOTAL).getValue());
			BigDecimal price = FDCHelper.toBigDecimal(row.getCell("node"+COL_PRICE).getValue());
			BigDecimal value = FDCHelper.toBigDecimal(row.getCell("node"+COL_VALUE).getValue());
			if((total != null&&total.compareTo(FDCHelper.ZERO) != 0 )
					|| (price != null && price.compareTo(FDCHelper.ZERO)!=0)){
				value = FDCHelper.multiply(total,price);
				row.getCell("node"+COL_VALUE).setValue(value);
				row.getCell("node"+COL_VALUE).getStyleAttributes().setLocked(true);
				row.getCell("node"+COL_VALUE).getStyleAttributes().setBackground(NOEDITORCOLOR);
			}
			else if(total.compareTo(FDCHelper.ZERO)==0&& price.compareTo(FDCHelper.ZERO) ==0
					&& value.compareTo(FDCHelper.ZERO) != 0){
				row.getCell("node"+COL_TOTAL).getStyleAttributes().setLocked(true);
				row.getCell("node"+COL_PRICE).getStyleAttributes().setLocked(true);
				row.getCell("node"+COL_TOTAL).getStyleAttributes().setBackground(NOEDITORCOLOR);
				row.getCell("node"+COL_PRICE).getStyleAttributes().setBackground(NOEDITORCOLOR);
			}else{
				row.getCell("node"+COL_VALUE).getStyleAttributes().setLocked(false);
				row.getCell("node"+COL_TOTAL).getStyleAttributes().setLocked(false);
				row.getCell("node"+COL_PRICE).getStyleAttributes().setLocked(false);
				row.getCell("node"+COL_TOTAL).getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("node"+COL_PRICE).getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("node"+COL_VALUE).getStyleAttributes()
				.setBackground(UIManager.getColor("TextField.requiredBackground"));
			}
			
		}
		if(!isAudit && (STATUS_EDIT.equals(getOprtState()) ||STATUS_ADDNEW.equals(getOprtState()))){
			tblMain.setEnabled(true);
		}else{
			tblMain.setEnabled(false);
		}
	}
	
	public void loadFields() {
		tblMain.checkParsed();
		super.loadFields();
		fillTable();
		if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.setUITitle("节点计价--新增");
		}else if(STATUS_EDIT.equals(this.getOprtState())){
			this.setUITitle("节点计价--编辑");
		}else if(STATUS_VIEW.equals(this.getOprtState())){
			this.setUITitle("节点计价--查看");
		}
	}

	protected void tblMain_editStopping(KDTEditEvent e) throws Exception {
		int colIndex = e.getColIndex();
		int rowIndex = e.getRowIndex();
		IRow row = tblMain.getRow(rowIndex);
		BigDecimal newValue = (BigDecimal)e.getValue();
		//先设置值
		if(colIndex==tblMain.getColumnIndex("node"+COL_TOTAL)){
			row.getCell("node"+COL_TOTAL).setValue(newValue);
		}else if(colIndex==tblMain.getColumnIndex("node"+COL_PRICE)){
			row.getCell("node"+COL_PRICE).setValue(newValue);
		}else if(colIndex==tblMain.getColumnIndex("node"+COL_VALUE)){
			row.getCell("node"+COL_VALUE).setValue(newValue);
		}
		
		
	}
	
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		if(e==null)
			return;
		//先tblMain_editStopping设置值，再判断，否则取值不是最新的  by hpw
		IRow row = tblMain.getRow(e.getRowIndex());
		BigDecimal total = FDCHelper.toBigDecimal(row.getCell("node"+COL_TOTAL).getValue());
		BigDecimal price = FDCHelper.toBigDecimal(row.getCell("node"+COL_PRICE).getValue());
		BigDecimal value = FDCHelper.toBigDecimal(row.getCell("node"+COL_VALUE).getValue());
		if((total != null&&total.compareTo(FDCHelper.ZERO) != 0 )
				|| (price != null && price.compareTo(FDCHelper.ZERO)!=0)){
			value = FDCHelper.multiply(total,price);
			row.getCell("node"+COL_VALUE).setValue(value);
		}
		else if(total.compareTo(FDCHelper.ZERO)==0&& price.compareTo(FDCHelper.ZERO) ==0
				&& value.compareTo(FDCHelper.ZERO) != 0){
			row.getCell("node"+COL_TOTAL).getStyleAttributes().setLocked(true);
			row.getCell("node"+COL_PRICE).getStyleAttributes().setLocked(true);
			row.getCell("node"+COL_TOTAL).getStyleAttributes().setBackground(NOEDITORCOLOR);
			row.getCell("node"+COL_PRICE).getStyleAttributes().setBackground(NOEDITORCOLOR);
		}else{
			row.getCell("node"+COL_VALUE).getStyleAttributes().setLocked(false);
			row.getCell("node"+COL_TOTAL).getStyleAttributes().setLocked(false);
			row.getCell("node"+COL_PRICE).getStyleAttributes().setLocked(false);
			row.getCell("node"+COL_TOTAL).getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("node"+COL_PRICE).getStyleAttributes().setBackground(Color.WHITE);
			row.getCell("node"+COL_VALUE).getStyleAttributes()
				.setBackground(UIManager.getColor("TextField.requiredBackground"));
		}
		
		BigDecimal amount = FDCHelper.ZERO;
		for(int i = 0; i < tblMain.getRowCount();i++){
			IRow row2 = tblMain.getRow(e.getRowIndex());
			amount = FDCHelper.add(amount,FDCHelper.toBigDecimal(row2.getCell("node"+COL_VALUE).getValue()));
		}
		this.txtAmount.setValue(amount);
		isChange = true;
	}
	
	private NodeMeasureEntryCollection getEntrys(){
		NodeMeasureEntryCollection entrys = new NodeMeasureEntryCollection();
		
		for(int i = 0 ; i < tblMain.getRowCount(); i++){
			IRow row = tblMain.getRow(i);
			NodeMeasureEntryInfo entry = new NodeMeasureEntryInfo();
			entry.setParent(editData);
			entry.setCostAccount((CostAccountInfo)row.getUserObject());
			if(row.getCell(COL_UNIT).getValue() == null){
				entry.setUnit(null);				
			}else{
				entry.setUnit(row.getCell(COL_UNIT).getValue().toString());
			}
			entry.setConWorkLoad(FDCHelper.toBigDecimal(row.getCell("con"+COL_TOTAL).getValue()));
			entry.setConPrice(FDCHelper.toBigDecimal(row.getCell("con"+COL_PRICE).getValue()));
			entry.setConAmount(FDCHelper.toBigDecimal(row.getCell("con"+COL_VALUE).getValue()));
			entry.setChangeWorkLoad(FDCHelper.toBigDecimal(row.getCell("change"+COL_TOTAL).getValue()));
			entry.setChangePrice(FDCHelper.toBigDecimal(row.getCell("change"+COL_PRICE).getValue()));
			entry.setChangeAmount(FDCHelper.toBigDecimal(row.getCell("change"+COL_VALUE).getValue()));
			entry.setSettleWorkLoad(FDCHelper.toBigDecimal(row.getCell("settle"+COL_TOTAL).getValue()));
			entry.setSettlePrice(FDCHelper.toBigDecimal(row.getCell("settle"+COL_PRICE).getValue()));
			entry.setSettleAmount(FDCHelper.toBigDecimal(row.getCell("settle"+COL_VALUE).getValue()));
			entry.setNodeWorkLoad(FDCHelper.toBigDecimal(row.getCell("node"+COL_TOTAL).getValue()));
			entry.setNodePrice(FDCHelper.toBigDecimal(row.getCell("node"+COL_PRICE).getValue()));
			entry.setNodeAmount(FDCHelper.toBigDecimal(row.getCell("node"+COL_VALUE).getValue()));
			entry.setPayWorkLoad(FDCHelper.toBigDecimal(row.getCell("pay"+COL_TOTAL).getValue()));
			entry.setPayPrice(FDCHelper.toBigDecimal(row.getCell("pay"+COL_PRICE).getValue()));
			entry.setPayAmount(FDCHelper.toBigDecimal(row.getCell("pay"+COL_VALUE).getValue()));
			
			entrys.add(entry);
		}
		
		return entrys;
	}
    public boolean isModify() {
    	if(!isChange){
    		return false;
    	}
		return super.isModify();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, txtAmount);
		FDCClientVerifyHelper.verifyEmpty(this, pkDate);
		for(int i = 0 ; i < tblMain.getRowCount(); i++){
			IRow row = tblMain.getRow(i);
			Object obj = row.getCell("node"+COL_VALUE).getValue();
			if(obj == null){
				MsgBox.showWarning(this,"节点计价量合价不能为空！");
				SysUtil.abort();
			}else if(obj instanceof BigDecimal){
				BigDecimal temp = (BigDecimal)obj;
				if(FDCHelper.ZERO.compareTo(temp)==0){
					MsgBox.showWarning(this,"节点计价量合价不能为0！");
					SysUtil.abort();
				}
			}
		}
		super.verifyInput(e);
	}

	protected void initCtrlListener(){
		//处理键盘delete事件
		tblMain.setBeforeAction(new BeforeActionListener(){
			public void beforeAction(BeforeActionEvent e)
			{
				if(BeforeActionEvent.ACTION_DELETE==e.getType()){
					for (int i = 0; i < tblMain.getSelectManager().size(); i++)
					{
						KDTSelectBlock block = tblMain.getSelectManager().get(i);
						for (int rowIndex = block.getBeginRow(); rowIndex <= block.getEndRow(); rowIndex++)
						{
							for(int colIndex=block.getBeginCol();colIndex<=block.getEndCol();colIndex++){
								int workLoad_index=tblMain.getColumnIndex("node"+COL_TOTAL);
								int price_index=tblMain.getColumnIndex("node"+COL_PRICE);
								int amount_index=tblMain.getColumnIndex("node"+COL_VALUE);
								if((colIndex!=workLoad_index  && colIndex!= price_index
										&&colIndex!=amount_index)||(tblMain.getCell(rowIndex, colIndex).getStyleAttributes().isLocked())) {
									e.setCancel(true);
									continue;
								}
								try
								{
									tblMain.getCell(rowIndex, colIndex).setValue(FDCHelper.ZERO);
									tblMain_editStopped(new KDTEditEvent(e.getSource(), null, FDCHelper.ZERO, 
											rowIndex, colIndex,false,1));
								} catch (Exception e1)
								{
									handUIExceptionAndAbort(e1);
								}
							}
//							e.setCancel(true);
						}
					}

				}
				else if(BeforeActionEvent.ACTION_PASTE==e.getType()){
/*					int col=getDetailTable().getSelectManager().getActiveColumnIndex();
					int row=getDetailTable().getSelectManager().getActiveRowIndex();
					if(col<0||row<0||getDetailTable().getCell(row, col).getStyleAttributes().isLocked()){
						e.setCancel(true);
					}*/
//					e.setCancel(true);
					tblMain.putClientProperty("ACTION_PASTE", "ACTION_PASTE");
				}
			}
		});
		
		tblMain.setAfterAction(new BeforeActionListener() {
			public void beforeAction(BeforeActionEvent e) {
				if (BeforeActionEvent.ACTION_PASTE == e.getType()) {
					tblMain.putClientProperty("ACTION_PASTE", null);
				}

			}
		});
		/*
		 * KDTable的KDTEditListener仅在编辑的时候触发，
		 * KDTPropertyChangeListener则是在删除，粘贴等导致单元格value发生变化的时候都会触发。
		 */
		tblMain.addKDTPropertyChangeListener(new KDTPropertyChangeListener(){
			public void propertyChange(KDTPropertyChangeEvent evt) {
			    // 表体单元格值发生变化
			    if ((evt.getType() == KDTStyleConstants.BODY_ROW) && (evt.getPropertyName().equals(KDTStyleConstants.CELL_VALUE)))
			    {
			    	if(tblMain.getClientProperty("ACTION_PASTE")!=null){
			    		//触发editStop事件
			    		int rowIndex = evt.getRowIndex();
			    		int colIndex = evt.getColIndex();
			    		KDTEditEvent event=new KDTEditEvent(tblMain);
			    		event.setColIndex(colIndex);
			    		event.setRowIndex(rowIndex);
			    		event.setOldValue(null);
			    		ICell cell = tblMain.getCell(rowIndex,colIndex);
			    		if(cell==null){
			    			return;
			    		}
			    		event.setValue(cell.getValue());
			    		try {
			    			tblMain_editStopped(event);
			    			
			    		} catch (Exception e1) {
			    			handUIExceptionAndAbort(e1);
			    		}
			    	}
			    }
			}
		});
	}

	protected KDTable getDetailTable() {
		return null;
	}

	public SelectorItemCollection getSelectors() {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("curProject.*"));
        sic.add(new SelectorItemInfo("contractBill.*"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("state"));
        return sic;
	}
	
}