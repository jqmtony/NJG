/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;

/**
 * output class name
 */
public class FullDyPayInfoUI extends AbstractFullDyPayInfoUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FullDyPayInfoUI.class);

	/**
	 * output class constructor
	 */
	public FullDyPayInfoUI() throws Exception {
		super();
	}

	private KDTable getMainTable() {
		return kDTable1;
	}

	protected void kDTable1_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// 单击
		if (e.getClickCount() == 1) {
			// ...do nothing
		}
		// 双击
		else if (e.getClickCount() == 2) {
			viewPay();
		}
	}

	private void viewPay() throws Exception {

		int selectRows[] = KDTableUtil.getSelectedRows(getMainTable());
		if (selectRows.length == 1) {

			Object obj = getMainTable().getCell(selectRows[0], "id").getValue();
			if (obj != null) {
				String objId = obj.toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, objId);
				IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL)
						.create(PaymentBillEditUI.class.getName(), uiContext,
								null, OprtState.VIEW);
				ui.show();
			}
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();

		initTable();
		fillData();
	}

	/**
	 * 初始化表格属性
	 */
	private void initTable() {
		getMainTable().checkParsed();
		getMainTable().getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		FDCTableHelper.setColumnMoveable(getMainTable(), true);
		FDCHelper.formatTableNumber(getMainTable(), new String[] { "payAmt",
				"splitAmt", "finvoiceOriAmt", "finvoiceAmt" });
		getMainTable().getColumn("id").getStyleAttributes().setHided(true);
		//by tim_gao 隐藏掉原币项,但是这种解决不满意
		getMainTable().getColumn("finvoiceOriAmt").getStyleAttributes().setHided(true);
		getMainTable().getHeadRow(0).getCell("finvoiceOriAmt").setValue("发票金额");
		getMainTable().getStyleAttributes().setLocked(true);
	}

	/**
	 * 填充表格数据
	 * 
	 * @throws BOSException
	 * @throws SQLException
	 */
	private void fillData() throws BOSException, SQLException {
		// by tim_gao 这里要用treeSet 会有顺序,乱序的话融合会有问题
    	Set mergeAmountSet = new TreeSet();
		Map dataMap = (Map) getUIContext().get("dataMap");
		String acctId = (String) dataMap.get("acctId");
		ResultSet rs = getRowSetByAcountID(acctId);
		
		// 付款金额合计
		BigDecimal amtTotal = FDCHelper.ZERO;
		// 拆分金额合计
		BigDecimal splitAmtTotal = FDCHelper.ZERO;
		// 发票金额合计
		BigDecimal amtInvoiceTotal = FDCHelper.ZERO;
		// 发票原币金额合计
		BigDecimal amtInvoiceOriTotal = FDCHelper.ZERO;
		
		// 是否有数据，有则添加合计行
		boolean isHasRow = false;

		// 合同融合用起始结束行标志
		int beginRow = 0;
		int endRow = 0;
		String oldNumber = null;

		// 付款融合用起始结束标志（一个合同可以包含多个付款，分别融合）
		int beginPay = 0;
		int endPay = 0;
		String oldPayNum = null;
		
		// 融合管理器
		KDTMergeManager mm = getMainTable().getMergeManager();
		
		// 以下3列按合同进行行融合
		int idCol = getMainTable().getColumnIndex("id");
		int numberCol = getMainTable().getColumnIndex("number");
		int nameCol = getMainTable().getColumnIndex("name");
		// 以下5列按付款进行行融合
		int payNumberCol = getMainTable().getColumnIndex("payNumber");
		int amountCol = getMainTable().getColumnIndex("payAmt");
		int payDateCol = getMainTable().getColumnIndex("payDate");
		int invoiceOriAmtCol = getMainTable().getColumnIndex("finvoiceOriAmt");
		int invoiceAmtCol = getMainTable().getColumnIndex("finvoiceAmt");
		
		int productCol = getMainTable().getColumnIndex("productType");
		
		while (rs.next()) {
			isHasRow = true;
			// 取得一行数据
			String number = rs.getString("number");
			String name = rs.getString("name");
			String payNumber = rs.getString("payNumber");
			String id = rs.getString("payId");
			String payDate = rs.getString("payDate");
			BigDecimal amount = rs.getBigDecimal("amount");
			BigDecimal splitAmt = rs.getBigDecimal("splitAmt");
			BigDecimal finOriAmountrs = rs.getBigDecimal("finvoiceoriamt") != null ? rs
					.getBigDecimal("finvoiceoriamt")
					: FDCHelper.ZERO;
			BigDecimal finAmountrs = rs.getBigDecimal("finvoiceamt") != null ? rs
					.getBigDecimal("finvoiceamt")
					: FDCHelper.ZERO;
			String product = rs.getString("product");
			// 产品类型不为空，则显示这列，否则默认隐藏
			if (!FDCHelper.isEmpty(product)) {
				getMainTable().getColumn("productType").getStyleAttributes()
						.setHided(false);
			}
			
			// 填充
			IRow row = getMainTable().addRow();
			row.getCell("number").setValue(number);
			row.getCell("name").setValue(name);
			row.getCell("payNumber").setValue(payNumber);
			row.getCell("id").setValue(id);
			if (payDate != null) {
				payDate = payDate.substring(0, 10);
			}
			row.getCell("payDate").setValue(payDate);
			row.getCell("payAmt").setValue(amount);
			row.getCell("splitAmt").setValue(splitAmt);
			row.getCell("productType").setValue(product);
			row.getCell("finvoiceOriAmt").setValue(finOriAmountrs);
			row.getCell("finvoiceAmt").setValue(finAmountrs);
			
			if (splitAmt != null) {
				splitAmtTotal = splitAmtTotal.add(splitAmt);
			}
			
			//by tim_gao 当不等于记录
    		if(!FDCHelper.isEmpty(oldPayNum) && !FDCHelper.isEmpty(payNumber)
    				&&!(oldPayNum.equals(payNumber))){
				if(row.getRowIndex()>=1){
					mergeAmountSet.add(new Integer(row.getRowIndex()));
				}
    		}
			// 融合合同
			if (!number.equals(oldNumber)) {
				// 索引从0开始，故减1；编码不一样时，已经是另外的单据了，故再减1
				endRow = getMainTable().getRowCount() - 1 - 1;
				if (endRow > beginRow) {
					mm.mergeBlock(beginRow, numberCol, endRow, numberCol,
							KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow, nameCol, endRow, nameCol,
							KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginRow, idCol, endRow, idCol,
							KDTMergeManager.SPECIFY_MERGE);
				}
				oldNumber = number;
				// 索引从0开始，故减1
				beginRow = getMainTable().getRowCount() - 1;
			}
			// 融合付款
			if (!((FDCHelper.isEmpty(oldPayNum) && FDCHelper.isEmpty(payNumber)) || (!FDCHelper
					.isEmpty(oldPayNum) && oldPayNum.equals(payNumber)))) {
				endPay = getMainTable().getRowCount() - 1 - 1;
				if (endPay > beginPay) {
					mm.mergeBlock(beginPay, payNumberCol, endPay, payNumberCol,
							KDTMergeManager.SPECIFY_MERGE);
//					mm.mergeBlock(beginPay, amountCol, endPay, amountCol,
//							KDTMergeManager.SPECIFY_MERGE);
					mm.mergeBlock(beginPay, payDateCol, endPay, payDateCol,
							KDTMergeManager.SPECIFY_MERGE);
					
				}
				oldPayNum = payNumber;
				beginPay = getMainTable().getRowCount() - 1;
				if (amount != null) {
					amtTotal = amtTotal.add(amount);
					amtInvoiceTotal = amtInvoiceTotal.add(finAmountrs);
					amtInvoiceOriTotal = amtInvoiceOriTotal.add(finOriAmountrs);
				}
			}
		}
		
		//by tim_gao根据number 融合
    	int tempTop= 0 ;
    	Iterator it = mergeAmountSet.iterator();
    	while(it.hasNext()){
    		Integer ita = (Integer) it.next();
    	if(tempTop!=ita.intValue()||tempTop!=ita.intValue()-1){
    		mm.mergeBlock(tempTop, amountCol, ita.intValue()-1, amountCol,
					KDTMergeManager.FREE_MERGE);
    		mm.mergeBlock(tempTop,payDateCol, ita.intValue()-1,payDateCol,KDTMergeManager.FREE_MERGE);
    		// by tim_gao 
			mm.mergeBlock(tempTop, invoiceOriAmtCol,  ita.intValue()-1,
					invoiceOriAmtCol, KDTMergeManager.FREE_MERGE);
			mm.mergeBlock(tempTop, invoiceAmtCol, ita.intValue()-1,
					invoiceAmtCol, KDTMergeManager.FREE_MERGE);
    	}
    		tempTop =ita.intValue();
    	}if(!it.hasNext()){//没有不同的,全部融合
    		if(this.getMainTable().getRowCount()>1){
    			int size =  this.getMainTable().getRowCount()-1;
    			mm.mergeBlock(tempTop, amountCol,size, amountCol,
    					KDTMergeManager.FREE_MERGE);
    			mm.mergeBlock(tempTop,payDateCol, size,payDateCol,KDTMergeManager.FREE_MERGE);
    			// by tim_gao 
    			mm.mergeBlock(tempTop, invoiceOriAmtCol, size,
    					invoiceOriAmtCol, KDTMergeManager.FREE_MERGE);
    			mm.mergeBlock(tempTop, invoiceAmtCol, size,
    					invoiceAmtCol, KDTMergeManager.FREE_MERGE);
    		}
    	}
    	
		// 如果最后几行需要融合，上面逻辑无法处理，在此处理
		endRow = getMainTable().getRowCount() - 1;// 索引从0开始，故减1
		if (endRow > beginRow) {
			mm.mergeBlock(beginRow, numberCol, endRow, numberCol,
					KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow, nameCol, endRow, nameCol,
					KDTMergeManager.SPECIFY_MERGE);
			// by tim_gao  以前的付款单融合问题,新改自由融合  和errorpatch的 代码好象不同不
			mm.mergeBlock(beginRow, payNumberCol, endRow, payNumberCol,
					KDTMergeManager.FREE_MERGE);
//			mm.mergeBlock(beginRow, amountCol, endRow, amountCol,
//					KDTMergeManager.FREE_MERGE);
//			mm.mergeBlock(beginRow, payDateCol, endRow, payDateCol,
//					KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow, idCol, endRow, idCol,
					KDTMergeManager.SPECIFY_MERGE);
		}
		// 合计
		if (isHasRow) {
			IRow totalRow = getMainTable().addRow();
			totalRow.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			totalRow.getCell("number").setValue("合计");
			totalRow.getCell("payAmt").setValue(amtTotal);
			totalRow.getCell("splitAmt").setValue(splitAmtTotal);
			totalRow.getCell("finvoiceAmt").setValue(amtInvoiceTotal);
			totalRow.getCell("finvoiceOriAmt").setValue(amtInvoiceOriTotal);
		}
	}

	/**
	 * 通过科目ID取得数据集合
	 * 
	 * @param acctId
	 * @return
	 * @throws BOSException
	 */
	private ResultSet getRowSetByAcountID(String acctId) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		// update by duyu at 2011-8-16 增加法票金额原币本币
		// 在外包增加发票金额基础上再加上产品类型 by emanon
		
		//by tim_gao 2011-11-04
		//在这里取发票金额,finvoiceoriamt ,finvoiceamt 是有问题的,应该取得是根据所选科目上
		// 本应该修改为付款单拆分明细的发票金额,而不是一张付款单上的发票金额总额 
		// 但是鉴于需求没有好所以,融合发票金额为收款单对应金额 ,但是目前 单据上收款单对应金额不在
		// 收款单上也不是这里取的付款申请单上而是 fdcpaymentbill 上 
		builder
				.appendSql("select tt.payId,tt.paynumber,tt.number,tt.name,tt.amount,tt.payDate,tt.splitamt,sum(tt.finvoiceoriamt) finvoiceoriamt ,sum(tt.finvoiceamt) finvoiceamt ,tt.product product from ( \n");
		builder
				.appendSql("select distinct(pay.FID) payId, pay.fnumber payNumber, con.FNumber number, con.FName name, pay.FLocalAmount amount, pay.FPayDate payDate, entry.FPayedAmt splitAmt ,fpb.finvoiceoriamt finvoiceoriamt ,fpb.finvoiceamt finvoiceamt ,prd.FName_l2 product from T_FNC_PaymentSplitEntry entry \n ");
		builder
				.appendSql("inner join T_FNC_PaymentSplit head on head.FID=entry.FParentID \n");
		builder
				.appendSql("inner join T_CAS_PaymentBill pay on pay.fid=head.FPaymentBillID \n");
		builder
				.appendSql("left join T_CON_PayRequestBill prb on pay.ffdcPayReqID = prb.fid \n");
		// by tim _gao 从 T_fnc_fdcpaymentbill 取得发票金额
		builder
				.appendSql("left join t_fnc_fdcpaymentbill fpb on pay.fid = fpb.fpaymentbillid\n");
		builder
				.appendSql("inner join T_Con_ContractBill con on con.FID = pay.FContractBillID \n");
		builder
				.appendSql("left join T_FDC_ProductType  prd on prd.FID = entry.fproductID \n");
		builder
				.appendSql("where entry.fisleaf=1 and head.fstate<>'9INVALID' and entry.FCostAccountID = \n");
		builder.appendParam(acctId);
		builder
				.appendSql(") tt group by tt.payId,tt.paynumber,tt.number,tt.name,tt.amount,tt.payDate,tt.splitamt,tt.product \n");
		builder.appendSql("order by tt.Number,tt.PayNumber");
		
		ResultSet set = builder.executeQuery();
		return set;
	}

	/**
	 * 增加累计发票金额 取数公式： 已付款状态的付款单的累计发票金额。
	 */
	// public BigDecimal[] getFinvoiceAmt(String acctId){
	// FDCSQLBuilder builder = new FDCSQLBuilder();
	// BigDecimal finAmountrs = FDCHelper.ZERO;
	// BigDecimal finOriAmountrs = FDCHelper.ZERO;
	// builder.appendSql(
	// "select isnull(sum(isnull(finvoiceAmt,0)),0),isnull(sum(isnull(finvoiceOriAmt,0)),0) from t_con_payrequestbill where fnumber in (select FFdcPayReqNumber from T_CAS_PaymentBill where fbillstatus=15) and fcontractid = ?"
	// );
	// builder.addParam(acctId);
	// RowSet rs;
	// try {
	// rs = builder.executeQuery();
	// while(rs.next()){
	// //本币
	// finAmountrs = rs.getBigDecimal(1);
	// //原币
	// finOriAmountrs = rs.getBigDecimal(2);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// BigDecimal finArr[] = new BigDecimal[2];
	// finArr[0] = finAmountrs;
	// finArr[1] = finOriAmountrs;
	// return finArr;
	// }
}