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
 * ����ȷ�ϵ�
 * 
 * @author houpw ����ʱ�䣺2008-8-11
 * @version EAS 6.0
 */
public class MaterialConfirmBillControllerBean extends AbstractMaterialConfirmBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialConfirmBillControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {

    	MaterialConfirmBillInfo info = computeAmountForBill(ctx, billId);
    	
    	super._audit(ctx, billId);

    	/*  ��δ���̫���ӣ������߼���Ҳ�����⣬��ʱע�ͣ�����processArriveStateAfterAudit(ctx, info)������Added By Owen_wen 2011-1-12
		MaterialConfirmBillEntryCollection confirmBillEntryColl = info.getEntrys();
		for (int i = 0; i < confirmBillEntryColl.size(); i++) {
			MaterialConfirmBillEntryInfo entryInfo = confirmBillEntryColl.get(i);
			// Update by zhiyuan_tang 2010/07/28
			// ��entryInfo.getEntrySrcBillID()ΪNULL��ʱ�򣬳����쳣����������NULLֵ�ж�
			if (entryInfo != null && entryInfo.getEntrySrcBillID() != null) {
				MaterialOrderBizBillAssEntryCollection orderEntryAsscoll = MaterialOrderBizBillAssEntryFactory.getLocalInstance(ctx)
						.getMaterialOrderBizBillAssEntryCollection("where id='" + entryInfo.getEntrySrcBillID() + "'");
				for (int j = 0; j < orderEntryAsscoll.size(); j++) {
					MaterialOrderBizBillAssEntryInfo orderBizAssEntryInfo = orderEntryAsscoll.get(i);
					if (orderBizAssEntryInfo != null) {
						confirmBillEntryColl = MaterialConfirmBillEntryFactory.getLocalInstance(ctx).getMaterialConfirmBillEntryCollection(
								"where entrySrcBillID='" + entryInfo.getEntrySrcBillID() + "'");
						if (UIRuleUtil.isNotNull(confirmBillEntryColl)) {// ��ѯ�����е�entrySrcBillIDΪ��������¼Id�Ĳ���ȷ�ϵ���¼
							BigDecimal count = FDCHelper.ZERO;
							BigDecimal oderCount = orderBizAssEntryInfo.getQuantity();// ��������¼������

							// ѭ���ۼӲ���ȷ�ϵ�
							for (int k = 0; k < confirmBillEntryColl.size(); k++) {
								MaterialConfirmBillEntryInfo confirmBillEntryInfo = confirmBillEntryColl.get(k);
								count = count.add(confirmBillEntryInfo.getQuantity());
							}

							IMaterialOrderBizBill iorderBizBill = MaterialOrderBizBillFactory.getLocalInstance(ctx);
							MaterialOrderBizBillInfo orderInfo = iorderBizBill.getMaterialOrderBizBillInfo(new ObjectUuidPK(orderBizAssEntryInfo
									.getParent().getId()));

							if (orderInfo != null) {
								orderInfo.setBillState(MaterialBillStateEnum.ALL_ARRIVED);// ȫ������
								if (!count.equals(oderCount)) {
									orderInfo.setBillState(MaterialBillStateEnum.PART_ARRIVED);// ���ֶ���
								}
								iorderBizBill.update(new ObjectUuidPK(orderInfo.getId()), orderInfo);// �޸Ķ������Ķ���״̬
							}

							MaterialOrderBizBillEntryCollection orderBizBillEntryColl = orderInfo.getEntrys();// �õ�����������ϸ��¼����
							MaterialOrderBizBillEntryInfo orderBizBillEntryInfo = null; // ����ϸ
							if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
								for (int index = 0; index < orderBizBillEntryColl.size(); index++) {
									orderBizBillEntryInfo = orderBizBillEntryColl.get(index);
									orderBizBillEntryColl = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
											.getMaterialOrderBizBillEntryCollection(
													"where sourceBill='" + orderBizBillEntryInfo.getSourceBill() + "'");
									if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
										BigDecimal count2 = FDCHelper.ZERO;
										// ͨ��������������ϸ��¼��sourceBillID�õ����Ͻ����ƻ����ܣ�
										// idΪ������������ϸ��¼��sourceBillID�ļ�¼��
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
												enterSumInfo.setBillstate(MaterialBillStateEnum.ALL_ARRIVED);// ȫ������
												if (!sumEntrySum.equals(count2)) {
													enterSumInfo.setBillstate(MaterialBillStateEnum.PART_ARRIVED);// ���ֶ���
												}
												ienterSum.update(new ObjectUuidPK(enterSumInfo.getId()), enterSumInfo);// �޸Ĳ��Ͻ����ƻ����ܵĶ���״̬
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
														// ͨ�����Ͻ����ƻ����ܷ�¼��sourceBillID�õ����Ͻ����ƻ�
														// ��
														// idΪ���Ͻ����ƻ����ܷ�¼��sourceBillID�ļ�¼
														// ��
														MaterialEnterPlanEntryInfo enterPlanBillEntryInfo = MaterialEnterPlanEntryFactory
																.getLocalInstance(ctx).getMaterialEnterPlanEntryInfo(
																		"where id = '" + enterSumEntryInfo.getSourceBill() + "'");
														if (enterPlanBillEntryInfo != null) {
															BigDecimal enterPlanEntrySum = enterPlanBillEntryInfo.getQuantity();// ���Ͻ����ƻ�����

															for (int index3 = 0; index3 < enterSumEntryColl.size(); index3++) {
																enterSumEntryInfo = enterSumEntryColl.get(index3);
																count3 = count3.add(enterSumEntryInfo.getQuantity());
															}

															IMaterialEnterPlanBill iEnterPlan = MaterialEnterPlanBillFactory.getLocalInstance(ctx);
															MaterialEnterPlanBillInfo enterPlanInfo = iEnterPlan
																	.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanBillEntryInfo.getParent()
																			.getId()));

															if (enterPlanInfo != null) {
																enterPlanInfo.setBillState(MaterialBillStateEnum.ALL_ARRIVED);// ȫ������
																if (!enterPlanEntrySum.equals(count3)) {
																	enterPlanInfo.setBillState(MaterialBillStateEnum.PART_ARRIVED);// ���ֶ���
																}
																iEnterPlan.update(new ObjectUuidPK(enterPlanInfo.getId()), enterPlanInfo);// �޸Ĳ��Ͻ����ƻ����ܵĶ���״̬
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
	 * �Զ��ۼƲ��Ϻ�ͬ �������,ȷ�Ͻ��,�Ѹ����
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
			
			//����ʱ������ʱ������е����ۼƼ��ϵ�ǰ���Ľ��
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

		//���������뵥�������ܷ�����
		
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
		
		//ȡ��ǰ���ֽ��
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

			//������ʱ������ʱ������е����ۼƼ�ȥ��ǰ���Ľ��
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

		/*	��δ���̫���ӣ������߼���Ҳ�����⣬��ʱע�ͣ�����processArriveStateAfterAudit(ctx, info)������Added By Owen_wen 2011-1-12
		MaterialConfirmBillEntryCollection confirmBillEntryColl = info.getEntrys();
		if (UIRuleUtil.isNotNull(confirmBillEntryColl) && confirmBillEntryColl.size() > 0) {
			for (int i = 0; i < confirmBillEntryColl.size(); i++) {
				MaterialConfirmBillEntryInfo entryInfo = confirmBillEntryColl.get(i);
				// Update by zhiyuan_tang 2010/07/28
				// ��entryInfo.getEntrySrcBillID()ΪNULL��ʱ�򣬳����쳣����������NULLֵ�ж�
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
								if (UIRuleUtil.isNotNull(confirmBillEntryColl)) {// ��ѯ�����е�entrySrcBillIDΪ��������¼Id�Ĳ���ȷ�ϵ���¼
									IMaterialOrderBizBill iorderBizBill = com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory
											.getLocalInstance(ctx);
									MaterialOrderBizBillInfo orderInfo = iorderBizBill.getMaterialOrderBizBillInfo(new ObjectUuidPK(
											orderBizAssEntryInfo.getParent().getId()));
									orderInfo.setBillState(MaterialBillStateEnum.NONE_ARRIVED);// δ����
									iorderBizBill.update(new ObjectUuidPK(orderInfo.getId()), orderInfo);// �޸Ķ������Ķ���״̬

									MaterialOrderBizBillEntryCollection orderBizBillEntryColl = orderInfo.getEntrys();// �õ�����������ϸ��¼����
									MaterialOrderBizBillEntryInfo orderBizBillEntryInfo = null; // ����ϸ
									if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
										for (int index = 0; index < orderBizBillEntryColl.size(); index++) {
											orderBizBillEntryInfo = orderBizBillEntryColl.get(index);
											orderBizBillEntryColl = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
													.getMaterialOrderBizBillEntryCollection(
															"where sourceBill='" + orderBizBillEntryInfo.getSourceBill() + "'");
											if (UIRuleUtil.isNotNull(orderBizBillEntryColl) && orderBizBillEntryColl.size() > 0) {
												// ͨ��������������ϸ��¼��sourceBillID�õ����Ͻ����ƻ�����
												// ��
												// idΪ������������ϸ��¼��sourceBillID�ļ�¼��
												MaterialEnterSumEntryInfo sumEntryInfo = MaterialEnterSumEntryFactory.getLocalInstance(ctx)
														.getMaterialEnterSumEntryInfo("where id = '" + orderBizBillEntryInfo.getSourceBill() + "'");
												if (sumEntryInfo != null) {
													IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
													MaterialEnterSumInfo enterSumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(
															sumEntryInfo.getParent().getId()));
													if (enterSumInfo != null) {
														enterSumInfo.setBillstate(MaterialBillStateEnum.NONE_ARRIVED);// δ����
														ienterSum.update(new ObjectUuidPK(enterSumInfo.getId()), enterSumInfo);// �޸Ĳ��Ͻ����ƻ����ܵĶ���״̬
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
																// ͨ�����Ͻ����ƻ����ܷ�¼��sourceBillID�õ����Ͻ����ƻ�
																// ��
																// idΪ���Ͻ����ƻ����ܷ�¼��sourceBillID�ļ�¼
																// ��
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
																		enterPlanInfo.setBillState(MaterialBillStateEnum.NONE_ARRIVED);// δ����
																		iEnterPlan.update(new ObjectUuidPK(enterPlanInfo.getId()), enterPlanInfo);// �޸Ĳ��Ͻ����ƻ����ܵĶ���״̬
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
		
		// �ض�ʱ�䣬����ʱ���ʱ��ֻ��ʾ���ڣ�����ʾ����ʱ��
		MaterialConfirmBillInfo materialBillInfo = ((MaterialConfirmBillInfo) model);
		model.setDate("supplyDate", FDCDateHelper.truncateSqlDate((java.sql.Date) materialBillInfo.getSupplyDate()));
		IObjectPK pk = super._save(ctx, model);
		computeOrderQty(sourceBillIDSet, ctx);
		return pk;
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSet(ctx, model);
		// �ض�ʱ�䣬����ʱ���ʱ��ֻ��ʾ���ڣ�����ʾ����ʱ��
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
		//ȥ����������
	}

	/**
	 * ��ȡ���ܻ�仯Դ���ݣ������϶�������¼����sourceBill���ϣ� ��һ����Ҫ�Բ��϶������ķ�¼�ġ��ѵ������������м���
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
	 * ȡ���ȱ��浥�������ݿ��й�����Դ���� �������϶�������¼����ID��
	 * 
	 * @author owen_wen 2010-01-10
	 * @param billInfo
	 *            ������ĵ��ݣ�����ȷ�ϵ���
	 * @return sourceBillIDSet Դ���� �������϶�������¼����ID��
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
	 * ��ȡ������ǰ�õ��������ݿ��й�����Դ���� �������϶������ķ�¼����ID��
	 * 
	 * @author owen_wen 2010-01-10
	 * @param materialConfirmBillId
	 *            �����浥�ݣ�����ȷ�ϵ�����ID
	 * @return sourceBillIDSet Դ���� �������϶�������¼����ID��
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
	 * ���㵱ǰ ����ȷ�ϵ� �ķ�¼��������Դ���ݣ������϶������ķ�¼���� ���ۼƵ���������, ��д�����ݿ���<br>
	 * 
	 * @author owen_wen 2010-01-10
	 * @param sourceBillIDList
	 *            ��������¼��Ӧ��sourceBill ��id��
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
		computeOrderQty(sourceBillIDSet, ctx); // ɾ�����ټ��� ���ۼƵ���������
	}
	
	// Added By Owen_wen 2011-01-12
	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {
		Set sourceBillIDSet = new HashSet();
		for (int i = 0; i < arrayPK.length; i++) {
			sourceBillIDSet.addAll(getSourceBillIdSetFromDB(arrayPK[i].toString(), ctx));
		}
		super._delete(ctx, arrayPK);
		computeOrderQty(sourceBillIDSet, ctx); // ɾ�����ټ��� ���ۼƵ���������
	}

	/**
	 * ��������ȷ�ϵ��󣬴�����϶������ĵ���״̬
	 * @author owen_wen
	 * @param matCfmBillInfo
	 *            ��ǰ�����Ĳ���ȷ�ϵ�
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
					// ֻҪ��һ����¼�ġ��ۼƵ���������δ���������������������ǲ��ֵ�������Ҫbreak��
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
	 * ����������ȷ�ϵ��󣬴�����϶������ĵ���״̬
	 * @author owen_wen
	 * @param matCfmBillInfo
	 *            ��ǰ�����Ĳ���ȷ�ϵ�
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
			int toZeroCount = 0; // �ۼƵ�����������0�Ĵ���
			for (; j < materialOrderBizBillEntrys.size(); j++) {
				BigDecimal totalRevQty = FDCHelper.toBigDecimal(materialOrderBizBillEntrys.get(j).getTotalRevQty());
				BigDecimal quantity = FDCHelper.toBigDecimal(materialOrderBizBillEntrys.get(j).getQuantity());
				
				if (matOrderEntry_Id_Qty.get(materialOrderBizBillEntrys.get(j).getId().toString()) != null) {
					// thisBillQuantity ���η�����Ҫ��ȥ������
					BigDecimal thisBillQuantity = (BigDecimal) matOrderEntry_Id_Qty.get(materialOrderBizBillEntrys.get(j).getId().toString());
					totalRevQty = totalRevQty.subtract(thisBillQuantity);
				}
				
				if (totalRevQty.compareTo(FDCConstants.ZERO) == 0)
					toZeroCount++;
				else if ((totalRevQty.compareTo(FDCConstants.ZERO) > 0 && totalRevQty.compareTo(quantity) < 0)) {
					// ֻҪ��һ����¼�ġ��ۼƵ���������δ���������������������ǲ��ֵ�������Ҫbreak��
					matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.PART_ARRIVED);
					break;
				}
			}
			
			if (toZeroCount == materialOrderBizBillEntrys.size()) // ÿ����¼�ĵ�������������0
				matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.NONE_ARRIVED);

			if (j == materialOrderBizBillEntrys.size() && toZeroCount != materialOrderBizBillEntrys.size()) {
				matOrderBizBillColl.get(i).setBillState(MaterialBillStateEnum.ALL_ARRIVED);
			}

			MaterialOrderBizBillFactory.getLocalInstance(ctx)
					.update(new ObjectUuidPK(matOrderBizBillColl.get(i).getId()), matOrderBizBillColl.get(i));
		}
	}

	/**
	 * �����������տ���ɿۿ
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @Author��jian_cao
	 * @CreateTime��2013-2-4
	 */
	private void createDeductBill(Context ctx, MaterialConfirmBillInfo materialInfo) throws EASBizException, BOSException {

		String companyId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		boolean isCreatepartadeduct = FDCUtils.getBooleanValue4FDCParamByKey(ctx, companyId, FDCConstants.FDC_PARAM_CREATEPARTADEDUCT);
		if (!isCreatepartadeduct) {
			//�ۿ�����
			EntityViewInfo typeView = new EntityViewInfo();
			FilterInfo typeFilter = new FilterInfo();
			typeFilter.getFilterItems().add(new FilterItemInfo("id", DeductTypeInfo.partAMaterialType, CompareType.EQUALS));
			typeView.setFilter(typeFilter);
			DeductTypeInfo typeInfo = DeductTypeFactory.getLocalInstance(ctx).getDeductTypeCollection(typeView).get(0);

			//ȡ�׹��� ����ͬ�б�
			ContractBillInfo mainCon = materialInfo.getMainContractBill();
			EntityViewInfo viewInfo = new EntityViewInfo();
			viewInfo.getSelector().add("amount");
			viewInfo.getSelector().add("partB.*");
			viewInfo.getSelector().add("curProject.*");
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("id", mainCon.getId().toString());
			viewInfo.setFilter(filterInfo);
			mainCon = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(viewInfo).get(0);
			//�ۿ
			DeductBillInfo deductBillInfo = new DeductBillInfo();
			//�ۿ��¼
			DeductBillEntryInfo deductBillEntryInfo = new DeductBillEntryInfo();
			deductBillEntryInfo.setDeductType(typeInfo);
			deductBillEntryInfo.setHasApplied(false);
			deductBillEntryInfo.setContractId(mainCon.getId().toString());
			deductBillEntryInfo.setDeductItem(mainCon.getPartB().getName());
			deductBillEntryInfo.setRemark("�����ջ���[" + materialInfo.getNumber() + "]�����Զ�����");
			deductBillEntryInfo.setDeductDate(new Date());
			deductBillEntryInfo.setDeductUnit(mainCon.getPartB());
			deductBillEntryInfo.setDeductOriginalAmt(mainCon.getAmount());
			deductBillEntryInfo.setDeductAmt(mainCon.getAmount());
			deductBillEntryInfo.setExRate(mainCon.getExRate());
			deductBillEntryInfo.setParent(deductBillInfo);
			//��¼���ö�Ӧ�Ĺ�����Ŀ
			deductBillEntryInfo.getParent().setCurProject(mainCon.getCurProject());
			deductBillInfo.getEntrys().add(deductBillEntryInfo);
			deductBillInfo.setNumber(materialInfo.getNumber());
			deductBillInfo.setCurProject(mainCon.getCurProject());
			//�ۿ������ں�֮ͬǰ
			deductBillInfo.setConTypeBefContr(false);
			IObjectPK pk = DeductBillFactory.getLocalInstance(ctx).submit(deductBillInfo);
			DeductBillFactory.getLocalInstance(ctx).audit(BOSUuid.read(pk.toString()));
		}
	}
	
	/**
	 * ��������������ɾ���ۿ��
	 * @param ctx
	 * @param materialInfo
	 * @throws EASBizException
	 * @throws BOSException
	 * @Author��jian_cao
	 * @CreateTime��2013-2-5
	 */
	private void unCreateDeductBill(Context ctx, MaterialConfirmBillInfo materialInfo) throws EASBizException, BOSException {
		
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("number", materialInfo.getNumber());
		viewInfo.setFilter(filterInfo);
		DeductBillInfo info = DeductBillFactory.getLocalInstance(ctx).getDeductBillCollection(viewInfo).get(0);
		if (info != null) {
			BOSUuid id = BOSUuid.read(info.getId().toString());
			//���ɵĿۿ������
			DeductBillFactory.getLocalInstance(ctx).unAudit(id);
			
			//ɾ���ۿ��¼
			filterInfo = new FilterInfo();
			filterInfo.appendFilterItem("Parent", id);
			DeductBillEntryFactory.getLocalInstance(ctx).delete(filterInfo);
			//ɾ���ۿ
			DeductBillFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(id));
		}
	}
}