/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.finance.client.PaymentBillEditUI;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

/**
 * output class name
 */
public class PaymentFullInfoUI extends AbstractPaymentFullInfoUI {
	/**
	 * output class constructor
	 */
	public PaymentFullInfoUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		
		super.onLoad();
		this.tblMain.checkParsed();	
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("amount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("invoiceAmt").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("invoiceAmt").getStyleAttributes().setNumberFormat("####.00");
		this.tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("exchange").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("exchange").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("localAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		String formatString = "yyyy-MM-dd";
		this.tblMain.getColumn("createTime").getStyleAttributes()
				.setNumberFormat(formatString);
		
	

		String contractId = (String) this.getUIContext().get(UIContext.ID);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("creator.name");
		view.getSelector().add("auditor.name");
		view.getSelector().add("currency.*");
		view.getSelector().add("fdcPayeeName.name");
		view.getSelector().add("actFdcPayeeName.name");
		view.getSelector().add("actFdcPayeeName.name");
		view.getSelector().add("voucher.number");
		view.getSelector().add("billstatus");
		
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("contractBillId", contractId));
		PaymentBillCollection paymentBillCollection = PaymentBillFactory
				.getRemoteInstance().getPaymentBillCollection(view);
		Set numberSet = new HashSet();
		
		for (int i = 0; i < paymentBillCollection.size(); i++) {
			PaymentBillInfo info = paymentBillCollection.get(i);
			IRow row = this.tblMain.addRow();
			row.getCell("number").setUserObject(info);
			row.setUserObject(info.getId().toString());
			row.getCell("state").setValue(info.getBillStatus());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("payRequestNum").setValue(info.getFdcPayReqNumber());
			row.getCell("currency").setValue(info.getCurrency().getName());
			row.getCell("amount").setValue(info.getAmount());
			row.getCell("localAmount").getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(info.getCurrency().getPrecision()));
			row.getCell("exchange").setValue(info.getExchangeRate());
			row.getCell("localAmount").setValue(info.getLocalAmt());
			row.getCell("payDate").setValue(info.getBizDate());
			row.getCell("receiveUnit").setValue(info.getFdcPayeeName().getName());
			row.getCell("realReceiveUnit").setValue(info.getActFdcPayeeName().getName());			
			if(info.getCreator() != null) {
				//R131224-0080  FDC090_PAYMENTCREATOR禁用后，不设置制单人，（财务出纳业务需求）
				row.getCell("creator").setValue(info.getCreator().getName());
			}		
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("description").setValue(info.getSummary());
			row.getCell("receiveBank").setValue(info.getPayeeBank());
			row.getCell("receiveBankNum").setValue(info.getPayeeAccountBank());
			if(info.getBillStatus().equals(BillStatusEnum.PAYED)){
				row.getCell("paidDate").setValue(info.getPayDate());
			}
			
			if (info.getAuditor() != null) {
				row.getCell("auditor").setValue(info.getAuditor().getName());
			}
			if(info.getVoucher() != null){
				row.getCell("voucherNum").setValue(info.getVoucher().getNumber());
			}
			row.getCell("auditTime").setValue(info.getAuditDate());
			numberSet.add(info.getFdcPayReqID());
		}
		if(numberSet.size()>0){
			FDCSQLBuilder builder = new FDCSQLBuilder();
			RowSet rs = null;
			Map resultMap = new HashMap();
			builder.clear();
			builder.appendSql("select fid,FInvoiceAmt,finvoiceNumber from t_con_payrequestbill where ");
			builder.appendParam("fid", numberSet.toArray());
			rs = builder.executeQuery();
			while(rs.next()){
			   resultMap.put(rs.getString(1).trim(), new Object[]{rs.getBigDecimal(2),rs.getString(3)});
			}
			PaymentBillInfo bill = null;
			if(tblMain.getRowCount()>0){
				IRow row = null;
				Object[] invoice = null;
				for(int i=0;i<tblMain.getRowCount();i++){
					row = tblMain.getRow(i);
					bill = (PaymentBillInfo) row.getCell("number").getUserObject();
					if(resultMap.get(bill.getFdcPayReqID().toString()) != null){
						invoice = (Object[]) resultMap.get(bill.getFdcPayReqID().toString());
					}
					if(invoice[0]!=null){
						row.getCell("invoiceAmt").setValue(invoice[0]);
					}
					if(invoice[1]!=null){
						row.getCell("invoiceNum").setValue(invoice[1]);
					}
					
				}
			}
		}
		
		FDCTableHelper.apendFootRow(tblMain, new String[] { "amount", "localAmount", "invoiceAmt" });
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		if (e.getClickCount() == 2) {
			IRow row = tblMain.getRow(e.getRowIndex());
			if(row==null) return ;
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, row.getUserObject());
			uiContext.put("disableSplit", Boolean.TRUE);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(PaymentBillEditUI.class.getName(),
							uiContext, null, "FINDVIEW");
			uiWindow.show();
		}
	}
}