package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryFactory;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.fdc.finance.DeductBillFactory;
import com.kingdee.eas.fdc.finance.DeductBillInfo;
import com.kingdee.eas.fdc.material.MaterialBillStateEnum;
import com.kingdee.eas.fdc.material.MaterialConfirmBillCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialConfirmBillFactory;
import com.kingdee.eas.fdc.material.MaterialConfirmBillInfo;
import com.kingdee.eas.fdc.material.MaterialException;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillCollection;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory;
import com.kingdee.eas.util.app.ContextUtil;

/**
 * 材料确认单
 * 
 * @author houpw 创建时间：2008-8-11
 * @version EAS 6.0
 */
public class MaterialConfirmBillControllerBean extends AbstractMaterialConfirmBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialConfirmBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

    	MaterialConfirmBillInfo info = computeAmountForBill(ctx, billId);
    	
    	super._audit(ctx, billId);

    	/*  这段代码太复杂，且与逻辑上也有问题，暂时注释，改用processArriveStateAfterAudit(ctx, info)方法，Added By Owen_wen 2011-1-12
		MaterialConfirmBillEntryCollection confirmBillEntryColl = info.getEntrys();
		for (int i = 0; i < confirmBillEntryColl.size(); i++) {
			MaterialConfirmBillEntryInfo entryInfo = confirmBillEntryColl.get(i);
			// Update by zhiyuan_tang 2010/07/28
			// 当entryInfo.getEntrySrcBillID()为NULL的时候，出现异常，因此添加了NULL值判断
			if (entryInfo != null && entryInfo.getEntrySrcBillID() != null) {
				MaterialOrderBizBillAssEntryCollection orderEntryAsscoll = MaterialOrderBizBillAssEntryFactory.getLocalInstance(ctx)
						.getMaterialOrderBizBillAssEntryCollection("where id='" + entryInfo.getEntrySrcBillID() + "'");
				for (int j = 0; j < orderEntryAsscoll.size(); j++) {
					MaterialOrderBizBillAssEntryInfo orderBizAssEntryInfo = orderEntryAsscoll.get(i);
					if (orderBizAssEntryInfo != null) {
						confirmBillEntryColl = MaterialConfirmBillEntryFactory.getLocalInstance(ctx).getMaterialConfirmBillEntryCollection(
								"where entrySrcBillID='" + entryInfo.getEntrySrcBillID() + "'");
						if (UIRuleUtil.isNotNull(confirmBillEntryColl)) {// 查询出所有的entrySrcBillID为订货单分录Id的材料确认单记录
							BigDecimal count = FDCHelper.ZERO;
							BigDecimal oderCount = orderBizAssEntryInfo.getQuantity();// 订货单分录的数量

							// 循环累加材料确认单
							for (int k = 0; k < confirmBillEntryColl.size(); k++) {
								MaterialConfirmBillEntryInfo confirmBillEntryInfo = confirmBillEntryColl.get(k);
								count = count.add(confirmBillEntryInfo.getQuantity());
							}

							IMaterialOrderBizBill iorderBizBill = MaterialOrderBizBillFactory.getLocalInstance(ctx);
							MaterialOrderBizBillInfo orderInfo = iorderBizBill.getMaterialOrderBizBillInfo(new ObjectUuidPK(orderBizAssEntryInfo
									.getParent().getId()));

							if (orderInfo != null) {
								orderInfo.setBillState(MaterialBillStateEnum.ALL_ARRIVED);// 全部订货
								if (!count.equals(oderCount)) {
									orderInfo.setBillState(MaterialBillStateEnum.PART_ARRIVED);// 部分订货
								}
								iorderBizBill.update(new ObjectUuidPK(orderInfo.getId()), orderInfo);// 修改订货单的订货状态
							}

							MaterialOrderBizBillEntryCollection orderBizBillEntryColl = orderInfo.getEntrys();// 得到订货单行明细分录集合
							MaterialOrderBizBillEntryInfo orderBizBillEntryInfo = null; // 行明细
							if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
								for (int index = 0; index < orderBizBillEntryColl.size(); index++) {
									orderBizBillEntryInfo = orderBizBillEntryColl.get(index);
									orderBizBillEntryColl = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
											.getMaterialOrderBizBillEntryCollection(
													"where sourceBill='" + orderBizBillEntryInfo.getSourceBill() + "'");
									if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
										BigDecimal count2 = FDCHelper.ZERO;
										// 通过订货单材料明细分录的sourceBillID得到材料进场计划汇总（
										// id为订货单材料明细分录的sourceBillID的记录）
										MaterialEnterSumEntryInfo sumEntryInfo = MaterialEnterSumEntryFactory.getLocalInstance(ctx)
												.getMaterialEnterSumEntryInfo("where id = '" + orderBizBillEntryInfo.getSourceBill() + "'");
										if (sumEntryInfo != null) {
											BigDecimal sumEntrySum = sumEntryInfo.getQuantity();

											for (int index2 = 0; index2 < orderBizBillEntryColl.size(); index2++) {
												orderBizBillEntryInfo = orderBizBillEntryColl.get(index2);
												count2 = count2.add(orderBizBillEntryInfo.getQuantity());
											}

											IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
											MaterialEnterSumInfo enterSumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(sumEntryInfo
													.getParent().getId()));

											if (enterSumInfo != null) {
												enterSumInfo.setBillstate(MaterialBillStateEnum.ALL_ARRIVED);// 全部订货
												if (!sumEntrySum.equals(count2)) {
													enterSumInfo.setBillstate(MaterialBillStateEnum.PART_ARRIVED);// 部分订货
												}
												ienterSum.update(new ObjectUuidPK(enterSumInfo.getId()), enterSumInfo);// 修改材料进场计划汇总的订货状态
											}

											MaterialEnterSumEntryInfo enterSumEntryInfo = null;
											MaterialEnterSumEntryCollection enterSumEntryColl = enterSumInfo.getEntrys();
											if (UIRuleUtil.isNotNull(enterSumEntryColl) && enterSumEntryColl.size() > 0) {
												for (int t = 0; t < enterSumEntryColl.size(); t++) {
													enterSumEntryInfo = enterSumEntryColl.get(i);
													enterSumEntryColl = MaterialEnterSumEntryFactory.getLocalInstance(ctx)
															.getMaterialEnterSumEntryCollection(
																	"where sourceBill='" + enterSumEntryInfo.getSourceBill() + "'");
													if (UIRuleUtil.isNotNull(enterSumEntryColl) && enterSumEntryColl.size() > 0) {
														BigDecimal count3 = FDCHelper.ZERO;
														// 通过材料进场计划汇总分录的sourceBillID得到材料进场计划
														// （
														// id为材料进场计划汇总分录的sourceBillID的记录
														// ）
														MaterialEnterPlanEntryInfo enterPlanBillEntryInfo = MaterialEnterPlanEntryFactory
																.getLocalInstance(ctx).getMaterialEnterPlanEntryInfo(
																		"where id = '" + enterSumEntryInfo.getSourceBill() + "'");
														if (enterPlanBillEntryInfo != null) {
															BigDecimal enterPlanEntrySum = enterPlanBillEntryInfo.getQuantity();// 材料进场计划数量

															for (int index3 = 0; index3 < enterSumEntryColl.size(); index3++) {
																enterSumEntryInfo = enterSumEntryColl.get(index3);
																count3 = count3.add(enterSumEntryInfo.getQuantity());
															}

															IMaterialEnterPlanBill iEnterPlan = MaterialEnterPlanBillFactory.getLocalInstance(ctx);
															MaterialEnterPlanBillInfo enterPlanInfo = iEnterPlan
																	.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanBillEntryInfo.getParent()
																			.getId()));

															if (enterPlanInfo != null) {
																enterPlanInfo.setBillState(MaterialBillStateEnum.ALL_ARRIVED);// 全部订货
																if (!enterPlanEntrySum.equals(count3)) {
																	enterPlanInfo.setBillState(MaterialBillStateEnum.PART_ARRIVED);// 部分订货
																}
																iEnterPlan.update(new ObjectUuidPK(enterPlanInfo.getId()), enterPlanInfo);// 修改材料进场计划汇总的订货状态
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		*/
    	
		processArriveStateAfterAudit(ctx, info);
		createDeductBill(ctx, info);
	}

	
    
	/**
	 * 自动累计材料合同 供货金额,确认金额,已付金额
	 * 
	 * @param ctx
	 * @param billId
	 * @return
	 * @throws BOSException
	 */
	private MaterialConfirmBillInfo computeAmountForBill(Context ctx, BOSUuid billId) throws BOSException {
		EntityViewInfo conView = new EntityViewInfo();
		FilterInfo conFilter = new FilterInfo();
		conFilter.getFilterItems().add(new FilterItemInfo("id", billId));
		conView.setFilter(conFilter);
		MaterialConfirmBillInfo info = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(conView).get(0);
		
		BigDecimal supplyAmt  = FDCHelper.toBigDecimal(info.getSupplyAmt());
		BigDecimal confirmAmt= FDCHelper.toBigDecimal(info.getConfirmAmt());
		BigDecimal paidAmt = FDCHelper.toBigDecimal(info.getPaidAmt());

		BigDecimal toDateSupplyAmt  = FDCHelper.ZERO;
		BigDecimal toDateConfirmAmt= FDCHelper.ZERO;
		BigDecimal toDatePaidAmt = FDCHelper.ZERO;
		
		String updateSql = "UPDATE T_PAM_MaterialConfirmBill SET FToDateSupplyAmt = ?, FToDateConfirmAmt = ?, FToDatePaidAmt = ? WHERE FID = ?";
		FDCSQLBuilder updateBuilder = new FDCSQLBuilder(ctx);
		updateBuilder.setPrepareStatementSql(updateSql);
		updateBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		
		BOSUuid mainConId = info.getMainContractBill().getId();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id", mainConId));
		filter.getFilterItems().add(new FilterItemInfo("createTime", info.getCreateTime(), CompareType.GREATER));
		view.setFilter(filter);
		MaterialConfirmBillCollection billCollection = MaterialConfirmBillFactory.getLocalInstance(ctx).getMaterialConfirmBillCollection(view);
		for(Iterator iter = billCollection.iterator(); iter.hasNext();){
			MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo) iter.next();
			
			//审批时将本单时间后所有单的累计加上当前单的金额
			toDateSupplyAmt = FDCHelper.toBigDecimal(billInfo.getToDateSupplyAmt()).add(supplyAmt);
			toDateConfirmAmt = FDCHelper.toBigDecimal(billInfo.getToDateConfirmAmt()).add(confirmAmt);
			toDatePaidAmt = FDCHelper.toBigDecimal(billInfo.getToDatePaidAmt()).add(paidAmt);
			
			updateBuilder.addParam(toDateSupplyAmt);
			updateBuilder.addParam(toDateConfirmAmt);
			updateBuilder.addParam(toDatePaidAmt);
			updateBuilder.addParam(billInfo.getId().toString());
			updateBuilder.addBatch();
		}
		updateBuilder.executeBatch();
		return info;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
		EASBizException {

		//被付款申请单关联不能反审批
		
		MaterialConfirmBillInfo info = this.getMaterialConfirmBillInfo(ctx, new ObjectUuidPK(billId));
		if(info.getPayRequestBill()!=null){
			throw new MaterialException(MaterialException.REF_NOT_UNAUDIT);
		}
		
		EntityViewInfo payReqView = new EntityViewInfo();
		payReqView.getSelector().add("materialConfirmBill.id");
		PartAConfmOfPayReqBillCollection partAConfmOfPayReqColl = 
			PartAConfmOfPayReqBillFactory.getLocalInstance(ctx).getPartAConfmOfPayReqBillCollection(payReqView);
		
		for(Iterator iter=partAConfmOfPayReqColl.iterator();iter.hasNext();){
		
			Object ref = ((PartAConfmOfPayReqBillInfo)iter.next()).getMaterialConfirmBill().getId();
			if(ref != null && ref.equals(billId)){
				throw new MaterialException(MaterialException.REF_NOT_UNAUDIT);
			}
		}
		
			
		BOSUuid mainConId = info.getMainContractBill().getId();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("confirmBill.id",billId.toString()));
		if(PayRequestBillConfirmEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new MaterialException(MaterialException.REF_NOT_UNAUDIT);
		}
		
		view.getSelector().clear();
		view.getFilter().getFilterItems().clear();
		filter.getFilterItems().add(new FilterItemInfo("mainContractBill.id",mainConId));
		filter.getFilterItems().add(new FilterItemInfo("createTime", info.getCreateTime(), CompareType.GREATER));
		view.setFilter(filter);
		
		//取当前各种金额
		BigDecimal supplyAmt  = FDCHelper.toBigDecimal(info.getSupplyAmt());
		BigDecimal confirmAmt= FDCHelper.toBigDecimal(info.getConfirmAmt());
		BigDecimal paidAmt = FDCHelper.toBigDecimal(info.getPaidAmt());

		BigDecimal toDateSupplyAmt  = FDCHelper.ZERO;
		BigDecimal toDateConfirmAmt= FDCHelper.ZERO;
		BigDecimal toDatePaidAmt = FDCHelper.ZERO;
		

		MaterialConfirmBillCollection billCollection = MaterialConfirmBillFactory.getLocalInstance(ctx)
			.getMaterialConfirmBillCollection(view);
		String updateSql = "UPDATE T_PAM_MaterialConfirmBill SET FToDateSupplyAmt = ?, FToDateConfirmAmt = ?, FToDatePaidAmt = ? WHERE FID = ?";
		FDCSQLBuilder updateBuilder = new FDCSQLBuilder(ctx);
		updateBuilder.setPrepareStatementSql(updateSql);
		updateBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		for(Iterator iter = billCollection.iterator(); iter.hasNext();){
//			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			MaterialConfirmBillInfo billInfo = (MaterialConfirmBillInfo) iter.next();

			//反审批时将本单时间后所有单的累计减去当前单的金额
			toDateSupplyAmt = FDCHelper.toBigDecimal(billInfo.getToDateSupplyAmt()).subtract(supplyAmt);
			toDateConfirmAmt = FDCHelper.toBigDecimal(billInfo.getToDateConfirmAmt()).subtract(confirmAmt);
			toDatePaidAmt = FDCHelper.toBigDecimal(billInfo.getToDatePaidAmt()).subtract(paidAmt);
			
//			builder.appendSql("update T_PAM_MaterialConfirmBill set FToDateSupplyAmt =");
//			builder.appendSql(toDateSupplyAmt+", ");
//			builder.appendSql("FToDateConfirmAmt =");
//			builder.appendSql(toDateConfirmAmt+", ");
//			builder.appendSql("FToDatePaidAmt =");
//			builder.appendSql(toDatePaidAmt+"");
//			builder.appendSql("where FID = '"+billInfo.getId().toString()+"'");
//			builder.execute();
			
			updateBuilder.addParam(toDateSupplyAmt);
			updateBuilder.addParam(toDateConfirmAmt);
			updateBuilder.addParam(toDatePaidAmt);
			updateBuilder.addParam(billInfo.getId().toString());
			updateBuilder.addBatch();
		}
		updateBuilder.executeBatch();		
		super._unAudit(ctx, billId);

		/*	这段代码太复杂，且与逻辑上也有问题，暂时注释，改用processArriveStateAfterAudit(ctx, info)方法，Added By Owen_wen 2011-1-12
		MaterialConfirmBillEntryCollection confirmBillEntryColl = info.getEntrys();
		if (UIRuleUtil.isNotNull(confirmBillEntryColl) && confirmBillEntryColl.size() > 0) {
			for (int i = 0; i < confirmBillEntryColl.size(); i++) {
				MaterialConfirmBillEntryInfo entryInfo = confirmBillEntryColl.get(i);
				// Update by zhiyuan_tang 2010/07/28
				// 当entryInfo.getEntrySrcBillID()为NULL的时候，出现异常，因此添加了NULL值判断
				// if(entryInfo != null){
				if (entryInfo != null && entryInfo.getEntrySrcBillID() != null) {
					MaterialOrderBizBillAssEntryCollection orderEntryAsscoll = MaterialOrderBizBillAssEntryFactory.getLocalInstance(ctx)
							.getMaterialOrderBizBillAssEntryCollection("where id='" + entryInfo.getEntrySrcBillID() + "'");
					if (UIRuleUtil.isNotNull(orderEntryAsscoll) && orderEntryAsscoll.size() > 0) {
						for (int j = 0; j < orderEntryAsscoll.size(); j++) {
							MaterialOrderBizBillAssEntryInfo orderBizAssEntryInfo = orderEntryAsscoll.get(i);
							if (orderBizAssEntryInfo != null) {
								confirmBillEntryColl = MaterialConfirmBillEntryFactory.getLocalInstance(ctx).getMaterialConfirmBillEntryCollection(
										"where entrySrcBillID='" + entryInfo.getEntrySrcBillID() + "'");
								if (UIRuleUtil.isNotNull(confirmBillEntryColl)) {// 查询出所有的entrySrcBillID为订货单分录Id的材料确认单记录
									IMaterialOrderBizBill iorderBizBill = com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory
											.getLocalInstance(ctx);
									MaterialOrderBizBillInfo orderInfo = iorderBizBill.getMaterialOrderBizBillInfo(new ObjectUuidPK(
											orderBizAssEntryInfo.getParent().getId()));
									orderInfo.setBillState(MaterialBillStateEnum.NONE_ARRIVED);// 未订货
									iorderBizBill.update(new ObjectUuidPK(orderInfo.getId()), orderInfo);// 修改订货单的订货状态

									MaterialOrderBizBillEntryCollection orderBizBillEntryColl = orderInfo.getEntrys();// 得到订货单行明细分录集合
									MaterialOrderBizBillEntryInfo orderBizBillEntryInfo = null; // 行明细
									if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
										for (int index = 0; index < orderBizBillEntryColl.size(); index++) {
											orderBizBillEntryInfo = orderBizBillEntryColl.get(index);
											orderBizBillEntryColl = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
													.getMaterialOrderBizBillEntryCollection(
															"where sourceBill='" + orderBizBillEntryInfo.getSourceBill() + "'");
											if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
												// 通过订货单材料明细分录的sourceBillID得到材料进场计划汇总
												// （
												// id为订货单材料明细分录的sourceBillID的记录）
												MaterialEnterSumEntryInfo sumEntryInfo = MaterialEnterSumEntryFactory.getLocalInstance(ctx)
														.getMaterialEnterSumEntryInfo("where id = '" + orderBizBillEntryInfo.getSourceBill() + "'");
												if (sumEntryInfo != null) {
													IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
													MaterialEnterSumInfo enterSumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(
															sumEntryInfo.getParent().getId()));
													if (enterSumInfo != null) {
														enterSumInfo.setBillstate(MaterialBillStateEnum.NONE_ARRIVED);// 未订货
														ienterSum.update(new ObjectUuidPK(enterSumInfo.getId()), enterSumInfo);// 修改材料进场计划汇总的订货状态
													}

													MaterialEnterSumEntryInfo enterSumEntryInfo = null;
													MaterialEnterSumEntryCollection enterSumEntryColl = enterSumInfo.getEntrys();
													if (UIRuleUtil.isNotNull(enterSumEntryColl) && enterSumEntryColl.size() > 0) {
														for (int t = 0; t < enterSumEntryColl.size(); t++) {
															enterSumEntryInfo = enterSumEntryColl.get(i);
															enterSumEntryColl = MaterialEnterSumEntryFactory.getLocalInstance(ctx)
																	.getMaterialEnterSumEntryCollection(
																			"where sourceBill='" + enterSumEntryInfo.getSourceBill() + "'");
															if (UIRuleUtil.isNotNull(enterSumEntryColl) && enterSumEntryColl.size() > 0) {
																// 通过材料进场计划汇总分录的sourceBillID得到材料进场计划
																// （
																// id为材料进场计划汇总分录的sourceBillID的记录
																// ）
																MaterialEnterPlanEntryInfo enterPlanBillEntryInfo = MaterialEnterPlanEntryFactory
																		.getLocalInstance(ctx).getMaterialEnterPlanEntryInfo(
																				"where id = '" + enterSumEntryInfo.getSourceBill() + "'");
																if (enterPlanBillEntryInfo != null) {
																	IMaterialEnterPlanBill iEnterPlan = MaterialEnterPlanBillFactory
																			.getLocalInstance(ctx);
																	MaterialEnterPlanBillInfo enterPlanInfo = iEnterPlan
																			.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanBillEntryInfo
																					.getParent().getId()));
																	if (enterPlanInfo != null) {
																		enterPlanInfo.setBillState(MaterialBillStateEnum.NONE_ARRIVED);// 未订货
																		iEnterPlan.update(new ObjectUuidPK(enterPlanInfo.getId()), enterPlanInfo);// 修改材料进场计划汇总的订货状态
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}		
		*/
		this.processArriveStateAfterUnAudit(ctx, info);
		unCreateDeductBill(ctx, info);
	}
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSet(ctx, model);  
		
		// 截断时间，保存时间的时候只显示日期，不显示具体时间
		MaterialConfirmBillInfo materialBillInfo = ((MaterialConfirmBillInfo) model);
		model.setDate("supplyDate", FDCDateHelper.truncateSqlDate((java.sql.Date) materialBillInfo.getSupplyDate()));
		IObjectPK pk = super._save(ctx, model);
		computeOrderQty(sourceBillIDSet, ctx);
		return pk;
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSet(ctx, model);
		// 截断时间，保存时间的时候只显示日期，不显示具体时间
		MaterialConfirmBillInfo materialBillInfo = ((MaterialConfirmBillInfo) model);
		model.setDate("supplyDate", FDCDateHelper.truncateSqlDate((java.sql.Date) materialBillInfo.getSupplyDate()));
		IObjectPK pk = super._submit(ctx, model);
		computeOrderQty(sourceBillIDSet, ctx);
		return pk;
	}
	
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		
		Map initMap = super._fetchInitData(ctx,paramMap);	
		String contractBillId = (String)paramMap.get("contractBillId");
		
		if(contractBillId==null&&paramMap.containsKey("ID")){
			Object id = (Object)paramMap.get("ID");
			String pk = null;
			if(id instanceof String)
				pk = String.valueOf(id);
			else if(id instanceof BOSUuid){
				pk = ((BOSUuid)id).toString();
			}
			if(pk!=null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("mainContractBill.id");
				MaterialConfirmBillInfo info = this.getMaterialConfirmBillInfo(ctx, new ObjectUuidPK(pk), sic);
				contractBillId = info.getMainContractBill().getId().toString();
			}
		}
		
		if(contractBillId!=null && initMap.get(FDCConstants.FDC_INIT_CONTRACT)==null){
			SelectorItemCollection selectors = new SelectorItemCollection();
			selectors.add("id");
			selectors.add("number");
			selectors.add("name");
			selectors.add("exRate");
			selectors.add("orgUnit.id");
			selectors.add("currency.number");
			selectors.add("currency.name");
			selectors.add("CU.id");
			selectors.add("grtRate");
			selectors.add("curProject.id");
			selectors.add("isCoseSplit");
	
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selectors);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
		}
		
		return initMap;
	}
	protected void checkNameDup(Context ctx, FDCBillInfo billInfo){
		//去掉重名检验
	}

	/**
	 * 获取可能会变化源单据（即材料订货单分录）的sourceBill集合， 下一步需要对材料订货单的分录的“已到货数量”进行计算
	 * 
	 * @author owen_wen 2010-01-10
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 */
	private Set getSourceBillIdSet(Context ctx, IObjectValue model) throws BOSException {
		MaterialConfirmBillInfo materialConfirmBillInfo = (MaterialConfirmBillInfo) model;
		String billId = "";
		if (materialConfirmBillInfo.getId() != null)
			billId = materialConfirmBillInfo.getId().toString();
		Set sourceBillIDSet = getSourceBillIdSetFromDB(billId, ctx);
		sourceBillIDSet.addAll(getSourceBillIDSetFromCurrentModel(materialConfirmBillInfo));
		return sourceBillIDSet;
	}

	/**
	 * 取出等保存单据在数据库中关联的源单据 （即材料订货单分录）的ID集
	 * 
	 * @author owen_wen 2010-01-10
	 * @param billInfo
	 *            待保存的单据（材料确认单）
	 * @return sourceBillIDSet 源单据 （即材料订货单分录）的ID集
	 */
	private Set getSourceBillIDSetFromCurrentModel(MaterialConfirmBillInfo billInfo) {
		Set sourceBillIDSet = new HashSet();
		MaterialConfirmBillEntryCollection mobbeColl = billInfo.getEntrys();
		for (int i = 0, size = mobbeColl.size(); i < size; i++) {
			MaterialConfirmBillEntryInfo entryInfo = mobbeColl.get(i);
			if (entryInfo.getEntrySrcBillID() != null)
				sourceBillIDSet.add(entryInfo.getEntrySrcBillID());
		}
		return sourceBillIDSet;
	}

	/**
	 * 先取出保存前该单据在数据库中关联的源单据 （即材料订货单的分录）的ID集
	 * 
	 * @author owen_wen 2010-01-10
	 * @param materialConfirmBillId
	 *            待保存单据（材料确认单）的ID
	 * @return sourceBillIDSet 源单据 （即材料订货单分录）的ID集
	 */
	private Set getSourceBillIdSetFromDB(String materialConfirmBillId, Context ctx) throws BOSException {
		Set sourceBillIDSet = new HashSet();
		if (!StringUtils.isEmpty(materialConfirmBillId)) {
			MaterialConfirmBillEntryCollection mobbeColl = MaterialConfirmBillEntryFactory.getLocalInstance(ctx)
					.getMaterialConfirmBillEntryCollection("where parent.id = '" + materialConfirmBillId + "'");
			for (int i = 0; i < mobbeColl.size(); i++) {
				if (mobbeColl.get(i).getEntrySrcBillID() != null)
					sourceBillIDSet.add(mobbeColl.get(i).getEntrySrcBillID());
			}
		}
		return sourceBillIDSet;
	}

	/**
	 * 计算当前 材料确认单 的分录所关联的源单据（即材料订货单的分录）的 “累计到货数量”, 并写到数据库中<br>
	 * 
	 * @author owen_wen 2010-01-10
	 * @param sourceBillIDList
	 *            订货单分录对应的sourceBill 的id集
	 * @author owen_wen 2010-01-10
	 * @throws BOSException
	 */
	private void computeOrderQty(Set sourceBillIDSet, Context ctx) throws BOSException {
		String sql = "update T_PAM_Materialobbe set FTotalRevQty = "
				+ "(select sum(FQuantity) from T_PAM_MaterialConfirmBillEntry where FEntrySrcBillID = ?)" + " where fid = ?";

		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		sqlBuilder.setPrepareStatementSql(sql);
		sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		for (Iterator it = sourceBillIDSet.iterator(); it.hasNext();) {
			String materialEnterSumEntryID = (String) it.next();
			sqlBuilder.addParam(materialEnterSumEntryID);
			sqlBuilder.addParam(materialEnterSumEntryID);
			sqlBuilder.addBatch();
		}

		if (sourceBillIDSet.size() > 0)
			sqlBuilder.executeBatch();
	}
	
	// Added By Owen_wen 2011-01-11
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSetFromDB(pk.toString(), ctx);
		super._delete(ctx, pk);
		computeOrderQty(sourceBillIDSet, ctx); // 删除后再计算 “累计到货数量”
	}
	
	// Added By Owen_wen 2011-01-12
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		Set sourceBillIDSet = new HashSet();
		for (int i = 0; i < arrayPK.length; i++) {
			sourceBillIDSet.addAll(getSourceBillIdSetFromDB(arrayPK[i].toString(), ctx));
		}
		super._delete(ctx, arrayPK);
		computeOrderQty(sourceBillIDSet, ctx); // 删除后再计算 “累计到货数量”
	}

	/**
	 * 审批材料确认单后，处理材料订货单的到货状态
	 * @author owen_wen
	 * @param matCfmBillInfo
	 *            当前审批的材料确认单
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void processArriveStateAfterAudit(Context ctx, MaterialConfirmBillInfo matCfmBillInfo) throws EASBizException, BOSException {
		MaterialConfirmBillEntryCollection matCfmEntryColl = matCfmBillInfo.getEntrys();
		Set matOrderEntryIds = new HashSet();
		for (int i = 0; i < matCfmEntryColl.size(); i++) {
			if (matCfmEntryColl.get(i).getEntrySrcBillID() != null)
				matOrderEntryIds.add(matCfmEntryColl.get(i).getEntrySrcBillID());
		}
		if (matOrderEntryIds.size() == 0)
			return;
		
		MaterialOrderBizBillEntryCollection matOrderEntrys = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
				.getMaterialOrderBizBillEntryCollection("where id in (" + getIdStrFromSet(matOrderEntryIds) + ")");
		Set matOrderBillIds = new HashSet();
		for (int i = 0; i < matOrderEntrys.size(); i++) {
			matOrderBillIds.add(matOrderEntrys.get(i).getParent().getId().toString());
		}
		
		MaterialOrderBizBillCollection matOrderBizBillColl = MaterialOrderBizBillFactory.getLocalInstance(ctx)
				.getMaterialOrderBizBillCollection(
				"where id in (" + getIdStrFromSet(matOrderBillIds) + ")");
		
		if (matOrderBizBillColl.size() == 0)
			return;
		
		for (int i = 0; i < matOrderBizBillColl.size(); i++) {
			MaterialOrderBizBillEntryCollection materialOrderBizBillEntrys = matOrderBizBillColl.get(i).getEntrys();
			int j = 0;
			for (; j < materialOrderBizBillEntrys.size(); j++) {
				BigDecimal totalRevQty = FDCHelper.toBigDecimal(materialOrderBizBillEntrys.get(j).getTotalRevQty());
				BigDecimal quantity = FDCHelper.toBigDecimal(materialOrderBizBillEntrys.get(j).getQuantity());
				if (totalRevQty.compareTo(FDCConstants.ZERO) > 0 && totalRevQty.compareTo(quantity) < 0) {
					// 只要有一条分录的“累计到货数量”未超过“订货数量”，就是部分到货，即要break，
					matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.PART_ARRIVED);
					break;
				}
			}
			if (j == materialOrderBizBillEntrys.size()) {
				matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.ALL_ARRIVED);
			}
			
			MaterialOrderBizBillFactory.getLocalInstance(ctx)
					.update(new ObjectUuidPK(matOrderBizBillColl.get(i).getId()), matOrderBizBillColl.get(i));
		}
	}
	
	private String getIdStrFromSet(Set idSet) {
		String idStr = "'null'";
		if (idSet != null) {
			Iterator iter = idSet.iterator();
			while (iter.hasNext()) {
				idStr += ",'" + (String) iter.next() + "'";
			}
		}
		return idStr;
	}
	
	/**
	 * 反审批材料确认单后，处理材料订货单的到货状态
	 * @author owen_wen
	 * @param matCfmBillInfo
	 *            当前审批的材料确认单
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private void processArriveStateAfterUnAudit(Context ctx, MaterialConfirmBillInfo matCfmBillInfo) throws EASBizException, BOSException {
		MaterialConfirmBillEntryCollection matCfmEntryColl = matCfmBillInfo.getEntrys();
		Map matOrderEntry_Id_Qty = new HashMap();
		for (int i = 0; i < matCfmEntryColl.size(); i++) {
			if (matCfmEntryColl.get(i).getEntrySrcBillID() != null)
				matOrderEntry_Id_Qty.put(matCfmEntryColl.get(i).getEntrySrcBillID(), matCfmEntryColl.get(i).getQuantity());
		}
		if (matOrderEntry_Id_Qty.size() == 0)
			return;
		
		MaterialOrderBizBillEntryCollection matOrderEntrys = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
				.getMaterialOrderBizBillEntryCollection("where id in (" + getIdStrFromSet(matOrderEntry_Id_Qty.keySet()) + ")");
		Set matOrderBillIds = new HashSet();
		for (int i = 0; i < matOrderEntrys.size(); i++) {
			matOrderBillIds.add(matOrderEntrys.get(i).getParent().getId().toString());
		}

		MaterialOrderBizBillCollection matOrderBizBillColl = MaterialOrderBizBillFactory.getLocalInstance(ctx).getMaterialOrderBizBillCollection(
				"where id in (" + getIdStrFromSet(matOrderBillIds) + ")");

		if (matOrderBizBillColl.size() == 0)
			return;

		for (int i = 0; i < matOrderBizBillColl.size(); i++) {
			MaterialOrderBizBillEntryCollection materialOrderBizBillEntrys = matOrderBizBillColl.get(i).getEntrys();
			int j = 0;
			int toZeroCount = 0; // 累计到货数量等于0的次数
			for (; j < materialOrderBizBillEntrys.size(); j++) {
				BigDecimal totalRevQty = FDCHelper.toBigDecimal(materialOrderBizBillEntrys.get(j).getTotalRevQty());
				BigDecimal quantity = FDCHelper.toBigDecimal(materialOrderBizBillEntrys.get(j).getQuantity());
				
				if (matOrderEntry_Id_Qty.get(materialOrderBizBillEntrys.get(j).getId().toString()) != null) {
					// thisBillQuantity 本次反审批要除去的数量
					BigDecimal thisBillQuantity = (BigDecimal) matOrderEntry_Id_Qty.get(materialOrderBizBillEntrys.get(j).getId().toString());
					totalRevQty = totalRevQty.subtract(thisBillQuantity);
				}
				
				if (totalRevQty.compareTo(FDCConstants.ZERO) == 0)
					toZeroCount++;
				else if ((totalRevQty.compareTo(FDCConstants.ZERO) > 0 && totalRevQty.compareTo(quantity) < 0)) {
					// 只要有一条分录的“累计到货数量”未超过“订货数量”，就是部分到货，即要break，
					matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.PART_ARRIVED);
					break;
				}
			}
			
			if (toZeroCount == materialOrderBizBillEntrys.size()) // 每条分录的到货数量都等于0
				matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.NONE_ARRIVED);

			if (j == materialOrderBizBillEntrys.size() && toZeroCount != materialOrderBizBillEntrys.size()) {
				matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.ALL_ARRIVED);
			}

			MaterialOrderBizBillFactory.getLocalInstance(ctx)
					.update(new ObjectUuidPK(matOrderBizBillColl.get(i).getId()), matOrderBizBillColl.get(i));
		}
	}

	/**
	 * 描述：材料收款单生成扣款单
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author：jian_cao
	 * @CreateTime：2013-2-4
	 */
	private void createDeductBill(Context ctx, MaterialConfirmBillInfo materialInfo) throws EASBizException, BOSException {

		String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		boolean isCreatepartadeduct = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
		if (!isCreatepartadeduct) {
			//扣款类型
			EntityViewInfo typeView = new EntityViewInfo();
			FilterInfo typeFilter = new FilterInfo();
			typeFilter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.EQUALS));
			typeView.setFilter(typeFilter);
			DeductTypeInfo typeInfo = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(typeView).get(0);

			//取甲供材 主合同列表
			ContractBillInfo mainCon = materialInfo.getMainContractBill();
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("amount");
			viewInfo.getSelector().add("partB.*");
			viewInfo.getSelector().add("curProject.*");
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("id", mainCon.getId().toString());
			viewInfo.setFilter(filterInfo);
			mainCon = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(viewInfo).get(0);
			//扣款单
			DeductBillInfo deductBillInfo = new DeductBillInfo();
			//扣款单分录
			DeductBillEntryInfo deductBillEntryInfo = new DeductBillEntryInfo();
			deductBillEntryInfo.setDeductType(typeInfo);
			deductBillEntryInfo.setHasApplied(false);
			deductBillEntryInfo.setContractId(mainCon.getId().toString());
			deductBillEntryInfo.setDeductItem(mainCon.getPartB().getName());
			deductBillEntryInfo.setRemark("材料收货单[" + materialInfo.getNumber() + "]审批自动生成");
			deductBillEntryInfo.setDeductDate(new Date());
			deductBillEntryInfo.setDeductUnit(mainCon.getPartB());
			deductBillEntryInfo.setDeductOriginalAmt(mainCon.getAmount());
			deductBillEntryInfo.setDeductAmt(mainCon.getAmount());
			deductBillEntryInfo.setExRate(mainCon.getExRate());
			deductBillEntryInfo.setParent(deductBillInfo);
			//分录设置对应的工程项目
			deductBillEntryInfo.getParent().setCurProject(mainCon.getCurProject());
			deductBillInfo.getEntrys().add(deductBillEntryInfo);
			deductBillInfo.setNumber(materialInfo.getNumber());
			deductBillInfo.setCurProject(mainCon.getCurProject());
			//扣款类型在合同之前
			deductBillInfo.setConTypeBefContr(false);
			IObjectPK pk = DeductBillFactory.getLocalInstance(ctx).submit(deductBillInfo);
			DeductBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(pk.toString()));
		}
	}
	
	/**
	 * 描述：反审批（删除扣款单）
	 * @param ctx
	 * @param materialInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author：jian_cao
	 * @CreateTime：2013-2-5
	 */
	private void unCreateDeductBill(Context ctx, MaterialConfirmBillInfo materialInfo) throws EASBizException, BOSException {
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("number", materialInfo.getNumber());
		viewInfo.setFilter(filterInfo);
		DeductBillInfo info = DeductBillFactory.getLocalInstance(ctx).getDeductBillCollection(viewInfo).get(0);
		if (info != null) {
			BOSUuid id = BOSUuid.read(info.getId().toString());
			//生成的扣款单反审批
			DeductBillFactory.getLocalInstance(ctx).unAudit(id);
			
			//删除扣款单分录
			filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("Parent", id);
			DeductBillEntryFactory.getLocalInstance(ctx).delete(filterInfo);
			//删除扣款单
			DeductBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(id));
		}
	}
}