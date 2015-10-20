/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.NodeMeasureFactory;
import com.kingdee.eas.fdc.contract.NodeMeasureInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class NodeMeasureFullListUI extends AbstractNodeMeasureFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(NodeMeasureFullListUI.class);
    
	private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
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
	
	//查询数据
	private Map initData = null;
    /**
     * output class constructor
     */
    public NodeMeasureFullListUI() throws Exception
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
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		//不显示默认过滤面板
		commonQueryDialog.setShowFilter(false);
		//commonQueryDialog.setShowSorter(false);
		return commonQueryDialog;
	}
	protected boolean isShowAttachmentAction() {
		
		return true;
	}
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new NodeMeasureFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				handUIExceptionAndAbort(e);
			}
		}
		return this.filterUI;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return NodeMeasureFactory.getRemoteInstance();
	}
	
	protected boolean initDefaultFilter() {
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initCtrl();
//		fillTable();
	}

	private void initCtrl(){
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionLocate.setEnabled(false);
		this.actionCreateTo.setEnabled(false);
		this.actionTraceDown.setEnabled(false);
		this.actionTraceUp.setEnabled(false);
		this.actionAuditResult.setEnabled(false);
		this.actionCopyTo.setEnabled(false);
		this.actionMultiapprove.setEnabled(false);
		this.actionNextPerson.setEnabled(false);
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionAuditResult.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionView.setVisible(false);
		
		this.menuBiz.setEnabled(false);
		this.menuBiz.setVisible(false);
		this.menuWorkFlow.setEnabled(false);
		this.menuWorkFlow.setVisible(false);
		this.menuView.setEnabled(false);
		this.menuView.setVisible(false);
		this.menuEdit.setVisible(false);
		this.menuEdit.setEnabled(false);
//		this.actionTraceUp.setVisible(false);
//		this.actionTraceDown.setVisible(false);
//		this.actionAddNew.setVisible(false);
//		this.actionCreateTo.setVisible(false);
//		this.actionAuditResult.setVisible(false);
//		this.actionEdit.setVisible(false);
//		this.actionRemove.setVisible(false);
//		this.actionLocate.setVisible(false);
		txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtAmount.setPrecision(2);
		txtAmount.setRemoveingZeroInDispaly(false);
		FDCHelper.formatTableNumber(tblMain,2,16);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
	}
	public static final String CONTRACTID = "contractBill.id";
	public static final String DATEFROM = "bizDate";
	public static final String DATETO = "createTime";
	
	protected void fetchData(){
		CustomerParams param = getFilterUI().getCustomerParams();
		FDCCustomerParams cp=new FDCCustomerParams(param);
		if(cp.getString(CONTRACTID) == null 
				&& mainQuery != null && mainQuery.getFilter()!=null){
			cp = new FDCCustomerParams();
			FilterInfo filter = mainQuery.getFilter();
			for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
				FilterItemInfo item=(FilterItemInfo)iter.next();
				if(item.getPropertyName().equals(CONTRACTID)){
					if(item.getCompareValue() instanceof String){
						cp.add(CONTRACTID,(String)item.getCompareValue());
					}
				}
				if(item.getPropertyName().equals(DATEFROM)){
					if(item.getCompareValue() instanceof Timestamp){
						cp.add(DATEFROM,new Date(((Timestamp)item.getCompareValue()).getTime()));
					}
				}
				if(item.getPropertyName().equals(DATETO)){
					if(item.getCompareValue() instanceof Timestamp){
						cp.add(DATETO,new Date(((Timestamp)item.getCompareValue()).getTime()));
					}
				}
			}
		}
		String contractId = cp.getString(CONTRACTID);
		Date dateFrom = cp.getDate(DATEFROM);
		Date dateTo = cp.getDate(DATETO);
		try {
			initData = NodeMeasureFactory.getRemoteInstance().fetchExecData(contractId,dateFrom,dateTo);
		} catch (EASBizException e) {
			handUIExceptionAndAbort(e);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		
	}
	protected void fillTable() {
		fetchData();
		if(initData == null || initData.size() == 0){
			return;
		}
		NodeMeasureInfo info = (NodeMeasureInfo)initData.get("NodeMeasureInfo");
		this.prmtProject.setValue(info.getCurProject());
		this.prmtContract.setValue(info.getContractBill());
		tblMain.removeRows();
		tblMain.getStyleAttributes().setLocked(true);
		CostAccountCollection costAccountColl = (CostAccountCollection)initData.get("CostAccountCollection");
		BigDecimal amount = FDCHelper.ZERO;
		for(Iterator it = costAccountColl.iterator();it.hasNext();){
			CostAccountInfo costAccount = (CostAccountInfo)it.next();
			String acctId = costAccount.getId().toString();
			Map rowMap  = (Map)initData.get(acctId);
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
			
			amount = FDCHelper.add(amount,rowMap.get("nodemeasure_amount"));
		}
		this.txtAmount.setValue(amount);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	protected void execQuery() {
		super.execQuery();
		fillTable();
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}
    protected String getKeyFieldName()
    {
        return COL_ACCOUNT;
    }

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {

	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
	}

	protected void initListener() {

	}

	public void initTableListner(KDTable table) {

	}
    
}