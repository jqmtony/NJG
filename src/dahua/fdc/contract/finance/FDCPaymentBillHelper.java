package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.IPayRequestBill;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class FDCPaymentBillHelper {

	
	/**
	 * @param ctx
	 * @param paymentBill
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 */
	public static void recountInvoiceAmt(Context ctx, PaymentBillInfo paymentBill) throws BOSException, EASBizException,
			UuidException {
		//反写其他房地产付款单的发票累计金额
		FDCSQLBuilder builder = null;
		if (ctx == null) {
			builder = new FDCSQLBuilder();
		} else {
			builder = new FDCSQLBuilder(ctx);
		}
		
		//取申请单下的所有的付款单的发票金额的合计
		BigDecimal allInvoiceAmt = FDCHelper.ZERO;
		BigDecimal allInvoiceOriAmt = FDCHelper.ZERO;
		
		BigDecimal allInvoiceWithOutSave = FDCHelper.ZERO;
		BigDecimal allInvoiceOriWithOutSave = FDCHelper.ZERO;
		//本付款单对应的付款申请单的值
		BigDecimal thisPayInvoice = FDCHelper.ZERO;
		BigDecimal thisPayInvoiceOri = FDCHelper.ZERO;
		//by tim_gao 问过需求 发票累计是合同下的所以不应该只取一个付款申请单的
//		builder.appendSql("select sum(isnull(FInvoiceAmt, 0)) as allInvoiceAmt,sum(isnull(FInvoiceOriAmt, 0)) as allInvoiceOriAmt from T_FNC_FDCPaymentBill where FPaymentBillID in ( select fid from T_CAS_PaymentBill where FFdcPayReqID = ?) ");
//		builder.addParam(paymentBill.getFdcPayReqID());
		
		
//		builder.appendSql("select sum(isnull(FInvoiceAmt, 0)) as allInvoiceAmt,sum(isnull(FInvoiceOriAmt, 0)) as allInvoiceOriAmt from T_FNC_FDCPaymentBill where FPaymentBillID in ( select fid from T_CAS_PaymentBill where Fcontractbillid = ? and fbillstatus != 10) ");
		
		builder.appendSql("select fpb.FInvoiceAmt as InvoiceAmt,fpb.FInvoiceOriAmt as InvoiceOriAmt, pb.fbillstatus as state , pb.FFdcPayReqID as fpayreq from  T_FNC_FDCPaymentBill fpb"+
 " left join T_CAS_PaymentBill pb on   fpb.FPaymentBillID = pb.fid "+
"where  pb.Fcontractbillid = ? ");
		
		builder.addParam(paymentBill.getContractBillId());
		IRowSet rowSet = builder.executeQuery();
		
				try {
					while(rowSet.next()){
						BigDecimal tempInvoiceAmt = rowSet.getBigDecimal("InvoiceAmt");
						BigDecimal tempInvoiceOriAmt = rowSet.getBigDecimal("InvoiceOriAmt");
						String payReqId = rowSet.getString("fpayreq");
						int check = 0;
						check = rowSet.getInt("state");
						//by tim_gao 取不到的时候直接从付款单上得,因为保存状态的时候就没有
						if(null!=tempInvoiceAmt){
							allInvoiceAmt = allInvoiceAmt.add(tempInvoiceAmt);
							//当为保存状态的不加入计算
							if(10!=check){
								allInvoiceWithOutSave =allInvoiceWithOutSave.add(rowSet.getBigDecimal("InvoiceAmt"));
								
							}
							//本付款单对应付款申请单的值反写
							if(paymentBill.getFdcPayReqID().equals(payReqId)){
								thisPayInvoice=thisPayInvoice.add(tempInvoiceAmt);
							}
						}
						if(null!=tempInvoiceOriAmt){
							allInvoiceOriAmt =allInvoiceOriAmt.add(tempInvoiceOriAmt);
							//当为保存状态的不加入计算
							if(10!=check){
								allInvoiceOriWithOutSave = allInvoiceOriWithOutSave.add(rowSet.getBigDecimal("InvoiceOriAmt"));
								
							}
							//本付款单对应付款申请单的值反写
							if(paymentBill.getFdcPayReqID().equals(payReqId)){
								thisPayInvoiceOri = thisPayInvoiceOri.add(tempInvoiceOriAmt);
							}
							
						}
						
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//这里调用很多,临时改不行,所以没有抛异常
					
				}
		
		
		//更新房地产付款单中间表的发票金额合计
		builder.clear();
		builder.appendSql("update T_FNC_FDCPaymentBill set FAllInvoiceAmt = ? ,FallInvoiceOriAmt = ? where FPaymentBillID in ( select fid from T_CAS_PaymentBill where FFdcPayReqID = ?)");
		builder.addParam(allInvoiceAmt);
		builder.addParam(allInvoiceOriAmt);
		builder.addParam(paymentBill.getFdcPayReqID());
		builder.executeUpdate();
		
		
//		//反写付款申请单的发票金额 by tim_gao 保存时不反写
		if(!com.kingdee.eas.fi.cas.BillStatusEnum.SAVE.equals(paymentBill.getBillStatus())){
			builder.clear();
			builder.appendSql("update T_CON_PayRequestBill set FInvoiceAmt = ? ,FinvoiceOriAmt = ?,FAllInvoiceAmt = ?,FallInvoiceOriAmt=? where fid  = ?");
			builder.addParam(thisPayInvoice);
			builder.addParam(thisPayInvoiceOri);
			builder.addParam(allInvoiceWithOutSave);
			builder.addParam(allInvoiceOriWithOutSave);
			builder.addParam(paymentBill.getFdcPayReqID());
			builder.executeUpdate();
		}
		
		
		//重算合同的付款申请的对应的累计发票金额
		IPayRequestBill iPayRequestBill = null;
		if (ctx == null) {
			iPayRequestBill = PayRequestBillFactory.getRemoteInstance();
		} else {
			iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
		}
		PayRequestBillInfo payReqInfo = new PayRequestBillInfo();
		payReqInfo = iPayRequestBill.getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(paymentBill.getFdcPayReqID())));
		FDCPaymentBillHelper.recountReqInvoiceAmt(ctx, payReqInfo.getContractId());
	}
	
	/**
	 * @param ctx
	 * @param fdcPayment
	 * @param paymentBill
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 */
	public static void handleFDCPaymentBillInvoice(Context ctx, IObjectValue fdcPayment, PaymentBillInfo paymentBill)
			throws BOSException, EASBizException, UuidException {
		FDCPaymentBillInfo fdcPaymentBill = (FDCPaymentBillInfo)fdcPayment;
		//先判读是否存在房地产付款单，不存在则新增
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", paymentBill.getId()));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		
		IFDCPaymentBill iFDCPaymentBill = null;
		IPayRequestBill iPayRequestBill = null;
		if (ctx == null) {
			iFDCPaymentBill = FDCPaymentBillFactory.getRemoteInstance();
			iPayRequestBill = PayRequestBillFactory.getRemoteInstance();
		} else {
			iFDCPaymentBill = FDCPaymentBillFactory.getLocalInstance(ctx);
			iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
		}
		if(!iFDCPaymentBill.exists(filter)) {
			if (fdcPaymentBill == null) {
				fdcPaymentBill = new FDCPaymentBillInfo();
			}
			fdcPaymentBill.setPaymentBill(paymentBill);
			fdcPaymentBill.setIsRespite(false);
			// 一些必要信息
			fdcPaymentBill.setCreateTime(paymentBill.getCreateTime());
			fdcPaymentBill.setCreator(paymentBill.getCreator());
			fdcPaymentBill.setLastUpdateTime(paymentBill.getLastUpdateTime());
			fdcPaymentBill.setLastUpdateUser(paymentBill.getLastUpdateUser());
			
			// 期间设置为付款申请单的期间
			String payReqBillId = paymentBill.getFdcPayReqID();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("period.id");
			selector.add("orgUnit.id");
			PayRequestBillInfo payReqBill = iPayRequestBill.getPayRequestBillInfo(new ObjectUuidPK(BOSUuid.read(payReqBillId)),selector);
			fdcPaymentBill.setPeriod(payReqBill.getPeriod());
			fdcPaymentBill.setBookedDate(payReqBill.getBookedDate());
			fdcPaymentBill.setOrgUnit(payReqBill.getOrgUnit());

			iFDCPaymentBill.addnew(fdcPaymentBill);
		} else {
			if (fdcPaymentBill != null) {
				FDCPaymentBillCollection col  = iFDCPaymentBill.getFDCPaymentBillCollection(view);
				if (col != null && col.size() > 0) {
					FDCPaymentBillInfo oldInfo = col.get(0);
					oldInfo.setInvoiceAmt(fdcPaymentBill.getInvoiceAmt());
					oldInfo.setInvoiceNumber(fdcPaymentBill.getInvoiceNumber());
					oldInfo.setAllInvoiceAmt(fdcPaymentBill.getAllInvoiceAmt());
					oldInfo.setInvoiceDate(fdcPaymentBill.getInvoiceDate());
					oldInfo.setInvoiceOriAmt(fdcPaymentBill.getInvoiceOriAmt());
					oldInfo.setAllInvoiceOriAmt(fdcPaymentBill.getAllInvoiceOriAmt());
					iFDCPaymentBill.update(new ObjectUuidPK(oldInfo.getId()), oldInfo);
				}
			}
		}
	}
	
	
	public static void recountReqInvoiceAmt(Context ctx, String contractId) throws BOSException, EASBizException {
		if (contractId == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractId", contractId));
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.ASCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("createTime");
		view.getSelector().add("invoiceAmt");
		view.getSelector().add("invoiceOriAmt");
		view.getSelector().add("allInvoiceAmt");
		view.getSelector().add("allInvoiceOriAmt");
		
		IPayRequestBill iPayRequestBill = null;
		if (ctx == null) {
			iPayRequestBill = PayRequestBillFactory.getRemoteInstance();
		} else {
			iPayRequestBill = PayRequestBillFactory.getLocalInstance(ctx);
		}
		
		PayRequestBillCollection c = iPayRequestBill.getPayRequestBillCollection(view);
		PayRequestBillInfo info = null;

		for (int i = 0; i < c.size(); i++) {
			info = c.get(i);
			if (i == 0) {
				// 等于0
				info.setAllInvoiceAmt(info.getInvoiceAmt());
				info.setAllInvoiceOriAmt(info.getInvoiceOriAmt());
			} else {
				// 等于上期累计＋本期发生
				if (c.get(i - 1).getAllInvoiceAmt() instanceof BigDecimal && info.getInvoiceAmt() instanceof BigDecimal) {
					info.setAllInvoiceAmt(c.get(i - 1).getAllInvoiceAmt().add(info.getInvoiceAmt()));
				}
				if (c.get(i - 1).getAllInvoiceOriAmt() instanceof BigDecimal && info.getInvoiceOriAmt() instanceof BigDecimal) {
					info.setAllInvoiceOriAmt(c.get(i - 1).getAllInvoiceOriAmt().add(info.getInvoiceOriAmt()));
				}
			}
			iPayRequestBill.updatePartial(info, view.getSelector());
		}
	}
}
