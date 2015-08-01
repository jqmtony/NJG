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
		// ����
		if (e.getClickCount() == 1) {
			// ...do nothing
		}
		// ˫��
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
	 * ��ʼ���������
	 */
	private void initTable() {
		getMainTable().checkParsed();
		getMainTable().getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT);
		FDCTableHelper.setColumnMoveable(getMainTable(), true);
		FDCHelper.formatTableNumber(getMainTable(), new String[] { "payAmt",
				"splitAmt", "finvoiceOriAmt", "finvoiceAmt" });
		getMainTable().getColumn("id").getStyleAttributes().setHided(true);
		//by tim_gao ���ص�ԭ����,�������ֽ��������
		getMainTable().getColumn("finvoiceOriAmt").getStyleAttributes().setHided(true);
		getMainTable().getHeadRow(0).getCell("finvoiceOriAmt").setValue("��Ʊ���");
		getMainTable().getStyleAttributes().setLocked(true);
	}

	/**
	 * ���������
	 * 
	 * @throws BOSException
	 * @throws SQLException
	 */
	private void fillData() throws BOSException, SQLException {
		// by tim_gao ����Ҫ��treeSet ����˳��,����Ļ��ںϻ�������
    	Set mergeAmountSet = new TreeSet();
		Map dataMap = (Map) getUIContext().get("dataMap");
		String acctId = (String) dataMap.get("acctId");
		ResultSet rs = getRowSetByAcountID(acctId);
		
		// ������ϼ�
		BigDecimal amtTotal = FDCHelper.ZERO;
		// ��ֽ��ϼ�
		BigDecimal splitAmtTotal = FDCHelper.ZERO;
		// ��Ʊ���ϼ�
		BigDecimal amtInvoiceTotal = FDCHelper.ZERO;
		// ��Ʊԭ�ҽ��ϼ�
		BigDecimal amtInvoiceOriTotal = FDCHelper.ZERO;
		
		// �Ƿ������ݣ�������Ӻϼ���
		boolean isHasRow = false;

		// ��ͬ�ں�����ʼ�����б�־
		int beginRow = 0;
		int endRow = 0;
		String oldNumber = null;

		// �����ں�����ʼ������־��һ����ͬ���԰����������ֱ��ںϣ�
		int beginPay = 0;
		int endPay = 0;
		String oldPayNum = null;
		
		// �ںϹ�����
		KDTMergeManager mm = getMainTable().getMergeManager();
		
		// ����3�а���ͬ�������ں�
		int idCol = getMainTable().getColumnIndex("id");
		int numberCol = getMainTable().getColumnIndex("number");
		int nameCol = getMainTable().getColumnIndex("name");
		// ����5�а�����������ں�
		int payNumberCol = getMainTable().getColumnIndex("payNumber");
		int amountCol = getMainTable().getColumnIndex("payAmt");
		int payDateCol = getMainTable().getColumnIndex("payDate");
		int invoiceOriAmtCol = getMainTable().getColumnIndex("finvoiceOriAmt");
		int invoiceAmtCol = getMainTable().getColumnIndex("finvoiceAmt");
		
		int productCol = getMainTable().getColumnIndex("productType");
		
		while (rs.next()) {
			isHasRow = true;
			// ȡ��һ������
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
			// ��Ʒ���Ͳ�Ϊ�գ�����ʾ���У�����Ĭ������
			if (!FDCHelper.isEmpty(product)) {
				getMainTable().getColumn("productType").getStyleAttributes()
						.setHided(false);
			}
			
			// ���
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
			
			//by tim_gao �������ڼ�¼
    		if(!FDCHelper.isEmpty(oldPayNum) && !FDCHelper.isEmpty(payNumber)
    				&&!(oldPayNum.equals(payNumber))){
				if(row.getRowIndex()>=1){
					mergeAmountSet.add(new Integer(row.getRowIndex()));
				}
    		}
			// �ںϺ�ͬ
			if (!number.equals(oldNumber)) {
				// ������0��ʼ���ʼ�1�����벻һ��ʱ���Ѿ�������ĵ����ˣ����ټ�1
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
				// ������0��ʼ���ʼ�1
				beginRow = getMainTable().getRowCount() - 1;
			}
			// �ںϸ���
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
		
		//by tim_gao����number �ں�
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
    	}if(!it.hasNext()){//û�в�ͬ��,ȫ���ں�
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
    	
		// ����������Ҫ�ںϣ������߼��޷������ڴ˴���
		endRow = getMainTable().getRowCount() - 1;// ������0��ʼ���ʼ�1
		if (endRow > beginRow) {
			mm.mergeBlock(beginRow, numberCol, endRow, numberCol,
					KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow, nameCol, endRow, nameCol,
					KDTMergeManager.SPECIFY_MERGE);
			// by tim_gao  ��ǰ�ĸ���ں�����,�¸������ں�  ��errorpatch�� �������ͬ��
			mm.mergeBlock(beginRow, payNumberCol, endRow, payNumberCol,
					KDTMergeManager.FREE_MERGE);
//			mm.mergeBlock(beginRow, amountCol, endRow, amountCol,
//					KDTMergeManager.FREE_MERGE);
//			mm.mergeBlock(beginRow, payDateCol, endRow, payDateCol,
//					KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(beginRow, idCol, endRow, idCol,
					KDTMergeManager.SPECIFY_MERGE);
		}
		// �ϼ�
		if (isHasRow) {
			IRow totalRow = getMainTable().addRow();
			totalRow.getStyleAttributes().setBackground(
					FDCHelper.KDTABLE_TOTAL_BG_COLOR);
			totalRow.getCell("number").setValue("�ϼ�");
			totalRow.getCell("payAmt").setValue(amtTotal);
			totalRow.getCell("splitAmt").setValue(splitAmtTotal);
			totalRow.getCell("finvoiceAmt").setValue(amtInvoiceTotal);
			totalRow.getCell("finvoiceOriAmt").setValue(amtInvoiceOriTotal);
		}
	}

	/**
	 * ͨ����ĿIDȡ�����ݼ���
	 * 
	 * @param acctId
	 * @return
	 * @throws BOSException
	 */
	private ResultSet getRowSetByAcountID(String acctId) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		// update by duyu at 2011-8-16 ���ӷ�Ʊ���ԭ�ұ���
		// ��������ӷ�Ʊ���������ټ��ϲ�Ʒ���� by emanon
		
		//by tim_gao 2011-11-04
		//������ȡ��Ʊ���,finvoiceoriamt ,finvoiceamt ���������,Ӧ��ȡ���Ǹ�����ѡ��Ŀ��
		// ��Ӧ���޸�Ϊ��������ϸ�ķ�Ʊ���,������һ�Ÿ���ϵķ�Ʊ����ܶ� 
		// ���Ǽ�������û�к�����,�ںϷ�Ʊ���Ϊ�տ��Ӧ��� ,����Ŀǰ �������տ��Ӧ����
		// �տ��Ҳ��������ȡ�ĸ������뵥�϶��� fdcpaymentbill �� 
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
		// by tim _gao �� T_fnc_fdcpaymentbill ȡ�÷�Ʊ���
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
	 * �����ۼƷ�Ʊ��� ȡ����ʽ�� �Ѹ���״̬�ĸ�����ۼƷ�Ʊ��
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
	// //����
	// finAmountrs = rs.getBigDecimal(1);
	// //ԭ��
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