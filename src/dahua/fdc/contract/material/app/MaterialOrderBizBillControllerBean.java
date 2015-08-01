package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.material.IMaterialEnterPlanBill;
import com.kingdee.eas.fdc.material.IMaterialEnterSum;
import com.kingdee.eas.fdc.material.MaterialBillStateEnum;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanBillInfo;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryCollection;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryFactory;
import com.kingdee.eas.fdc.material.MaterialEnterPlanEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryCollection;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryFactory;
import com.kingdee.eas.fdc.material.MaterialEnterSumEntryInfo;
import com.kingdee.eas.fdc.material.MaterialEnterSumFactory;
import com.kingdee.eas.fdc.material.MaterialEnterSumInfo;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryCollection;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryFactory;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillEntryInfo;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillFactory;
import com.kingdee.eas.fdc.material.MaterialOrderBizBillInfo;

public class MaterialOrderBizBillControllerBean extends AbstractMaterialOrderBizBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialOrderBizBillControllerBean");

	protected boolean isUseName() {
		return false;
	}

	protected void _audit(Context ctx, List idList) throws BOSException, EASBizException {
		
		Set enterPlanIdSet =  new HashSet();
		for (int i = 0; i < idList.size(); i++) {
			
			{
				MaterialOrderBizBillEntryInfo entryInfo = null;//订货单分录对象
				MaterialEnterSumEntryInfo enterSumEntryInfo = null;//材料进场计划汇总分录对象
				MaterialEnterPlanEntryInfo enterPlanEnterInfo = null;//材料进场计划分录对象
				String id = (String)idList.get(i);
				super._audit(ctx, BOSUuid.read(id));
				MaterialOrderBizBillInfo billInfo = MaterialOrderBizBillFactory.getLocalInstance(ctx).getMaterialOrderBizBillInfo(new ObjectUuidPK(id));
				if(billInfo.getId() != null){
					//得到订货单分录集合
					MaterialOrderBizBillEntryCollection OrderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where parent.id='"+billInfo.getId()+"'");
					if (UIRuleUtil.isNotNull(OrderEntrycoll) && OrderEntrycoll.size() > 0) {
						for (int j = 0;j < OrderEntrycoll.size() ; j++) {
							entryInfo = OrderEntrycoll.get(j);
							
							//得到材料进场计划汇总分录集合
							if(entryInfo.getId() != null && entryInfo.getSourceBill()!= null){
								MaterialEnterSumEntryCollection entryColl = MaterialEnterSumEntryFactory.getLocalInstance(ctx).getMaterialEnterSumEntryCollection("where id = '"+entryInfo.getSourceBill()+"'");
								if (UIRuleUtil.isNotNull(entryColl) && entryColl.size() > 0) {
									for (int k = 0; k < entryColl.size(); k++) {
										enterSumEntryInfo = entryColl.get(k);
										MaterialOrderBizBillEntryCollection orderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where sourceBill='"+enterSumEntryInfo.getId()+"'");
										if (UIRuleUtil.isNotNull(orderEntrycoll) && orderEntrycoll.size() > 0) {
											BigDecimal count = FDCHelper.ZERO;
											BigDecimal enterSumCount = enterSumEntryInfo.getQuantity();//汇总单分录的数量
											for (int l = 0;l < orderEntrycoll.size() ; l++) {
												entryInfo = orderEntrycoll.get(l);
												count = count.add(entryInfo.getQuantity());//累计ID为该汇总分录ID数量
											}
											
											IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
											MaterialEnterSumInfo sumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(enterSumEntryInfo.getParent().getId()));
											if(sumInfo != null){
												sumInfo.setBillstate(MaterialBillStateEnum.ALL);//全部订货
												if(!count.equals(enterSumCount)){
													sumInfo.setBillstate(MaterialBillStateEnum.PART);//部分订货
												}
												ienterSum.update(new ObjectUuidPK(sumInfo.getId()), sumInfo);//修改订货单的订货状态
											}
										}
										
										//得到材料进场计划分录集合
										if(enterSumEntryInfo.getSourceBill() != null){
											MaterialEnterPlanEntryCollection enterPlanColl = MaterialEnterPlanEntryFactory.getLocalInstance(ctx).getMaterialEnterPlanEntryCollection("where id = '"+enterSumEntryInfo.getSourceBill()+"'");
											if(UIRuleUtil.isNotNull(enterPlanColl) && enterPlanColl.size() > 0){
												
												for(int t = 0; t < enterPlanColl.size(); t++){
													enterPlanEnterInfo = enterPlanColl.get(t);
													if(enterPlanEnterInfo.getParent()!=null)
														enterPlanIdSet.add(enterPlanEnterInfo.getParent().getId().toString());
													MaterialEnterSumEntryCollection sumEntrycoll = MaterialEnterSumEntryFactory.getLocalInstance(ctx).getMaterialEnterSumEntryCollection("where sourceBill='"+enterPlanEnterInfo.getId()+"'");
													if (UIRuleUtil.isNotNull(sumEntrycoll) && sumEntrycoll.size() > 0) {
														BigDecimal count = FDCHelper.ZERO;
														BigDecimal enterPlanCount = enterPlanEnterInfo.getQuantity();//进场计划分录的数量
														for (int l = 0;l < sumEntrycoll.size() ; l++) {
															enterSumEntryInfo = sumEntrycoll.get(l);
															count = count.add(entryInfo.getQuantity());//累计ID为该材料进场分录ID数量
														}
														
														IMaterialEnterPlanBill iPlanBill = MaterialEnterPlanBillFactory.getLocalInstance(ctx);
														MaterialEnterPlanBillInfo planInfo = iPlanBill.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanEnterInfo.getParent().getId()));
														if(planInfo != null){
															planInfo.setBillState(MaterialBillStateEnum.ALL);//全部订货
															if(!count.equals(enterPlanCount)){
																planInfo.setBillState(MaterialBillStateEnum.PART);//部分订货
															}
															iPlanBill.update(new ObjectUuidPK(planInfo.getId()), planInfo);//修改材料进场的订货状态
														}
													}
												}
											}
										}
									}
								}
								
								if(enterSumEntryInfo != null){
									BigDecimal sumOrderQuantity = FDCHelper.ZERO;
									BigDecimal orderQuantity = FDCHelper.ZERO;
									if(enterSumEntryInfo.getOrderQuantity() != null){
										sumOrderQuantity = enterSumEntryInfo.getOrderQuantity();//材料进场计划汇总 —— 已订购数量
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//订货单—— 已订购数量
									}
									
									BigDecimal sum = sumOrderQuantity.add(orderQuantity);
									enterSumEntryInfo.setOrderQuantity(sum);//反写材料进场计划汇总——已定货数量
									MaterialEnterSumEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getId()),enterSumEntryInfo);
								}
								
								if(enterPlanEnterInfo != null){
									BigDecimal hasOrderQuantity = FDCHelper.ZERO;
									BigDecimal orderQuantity = FDCHelper.ZERO;
									if(enterPlanEnterInfo.getHasOrderQty() != null){
										hasOrderQuantity = enterPlanEnterInfo.getHasOrderQty();//材料进场计划单——数量
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//订货单—— 已订购数量
									}
									
									BigDecimal sumPlan = hasOrderQuantity.add(orderQuantity);
									enterPlanEnterInfo.setHasOrderQty(sumPlan);//反写材料进场计划——已定货数量
									if(enterSumEntryInfo != null){
										if(enterSumEntryInfo.getSourceBill() != null){
											MaterialEnterPlanEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getSourceBill()),enterPlanEnterInfo);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",enterPlanIdSet,CompareType.INCLUDE));
		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("entrys.hasOrderQty");
		view.getSelector().add("entrys.quantity");
		MaterialEnterPlanBillCollection enterPlans = MaterialEnterPlanBillFactory.getLocalInstance(ctx).getMaterialEnterPlanBillCollection(view);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.setPrepareStatementSql("update T_PAM_MaterialEnterPlanBill set fBillState=? where fid=?");
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		boolean shouldExcute = false;
		for(Iterator it = enterPlans.iterator();it.hasNext();){
			MaterialEnterPlanBillInfo info = (MaterialEnterPlanBillInfo)it.next();
			boolean isAll = true;
			for(int i=0;i<info.getEntrys().size();i++){
				MaterialEnterPlanEntryInfo entry = info.getEntrys().get(i);
				if(FDCHelper.toBigDecimal(entry.getQuantity()).compareTo(FDCHelper.toBigDecimal(entry.getHasOrderQty()))>0){
					isAll = false;
					break;
				}
			}
			if(isAll){
				builder.addParam(MaterialBillStateEnum.ALL_VALUE);
			}else{
				builder.addParam(MaterialBillStateEnum.PART_VALUE);
			}
			builder.addParam(info.getId().toString());
			builder.addBatch();
			shouldExcute = true;
		}
		if(shouldExcute)
			builder.executeBatch();
	}

	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		
		Set enterPlanIdSet =  new HashSet();
		for (int i = 0; i < idList.size(); i++) {
			
			{
				MaterialOrderBizBillEntryInfo entryInfo = null;//订货单分录对象
				MaterialEnterSumEntryInfo enterSumEntryInfo = null;//材料进场计划汇总分录对象
				MaterialEnterPlanEntryInfo enterPlanEnterInfo = null;//材料进场计划分录对象
				String id = (String)idList.get(i);
				super._unAudit(ctx, BOSUuid.read(id));
				MaterialOrderBizBillInfo billInfo = MaterialOrderBizBillFactory.getLocalInstance(ctx).getMaterialOrderBizBillInfo(new ObjectUuidPK(id));
				if(billInfo.getId() != null){
					//得到订货单分录集合
					MaterialOrderBizBillEntryCollection OrderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where parent.id='"+billInfo.getId()+"'");
					if (UIRuleUtil.isNotNull(OrderEntrycoll) && OrderEntrycoll.size() > 0) {
						for (int j = 0;j < OrderEntrycoll.size() ; j++) {
							entryInfo = OrderEntrycoll.get(j);
							
							//得到材料进场计划汇总分录集合
							if(entryInfo.getId() != null && entryInfo.getSourceBill()!= null){
								MaterialEnterSumEntryCollection entryColl = MaterialEnterSumEntryFactory.getLocalInstance(ctx).getMaterialEnterSumEntryCollection("where id = '"+entryInfo.getSourceBill()+"'");
								if (UIRuleUtil.isNotNull(entryColl) && entryColl.size() > 0) {
									for (int k = 0; k < entryColl.size(); k++) {
										enterSumEntryInfo = entryColl.get(k);
										MaterialOrderBizBillEntryCollection orderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where sourceBill='"+enterSumEntryInfo.getId()+"'");
										if (UIRuleUtil.isNotNull(orderEntrycoll) && orderEntrycoll.size() > 0) {
											BigDecimal count = FDCHelper.ZERO;
											for (int l = 0;l < orderEntrycoll.size() ; l++) {
												entryInfo = orderEntrycoll.get(l);
												count = count.add(entryInfo.getQuantity());//累计ID为该汇总分录ID数量
											}
											
											IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
											MaterialEnterSumInfo sumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(enterSumEntryInfo.getParent().getId()));
											if(sumInfo != null){
												sumInfo.setBillstate(MaterialBillStateEnum.NONE_ARRIVED);//未订货
												ienterSum.update(new ObjectUuidPK(sumInfo.getId()), sumInfo);//修改订货单的订货状态
											}
										}
										
										//得到材料进场计划分录集合
										if(enterSumEntryInfo.getSourceBill() != null){
											MaterialEnterPlanEntryCollection enterPlanColl = MaterialEnterPlanEntryFactory.getLocalInstance(ctx).getMaterialEnterPlanEntryCollection("where id = '"+enterSumEntryInfo.getSourceBill()+"'");
											if(UIRuleUtil.isNotNull(enterPlanColl) && enterPlanColl.size() > 0){
												for(int t = 0; t < enterPlanColl.size(); t++){
													enterPlanEnterInfo = enterPlanColl.get(t);
													if(enterPlanEnterInfo.getParent()!=null)
														enterPlanIdSet.add(enterPlanEnterInfo.getParent().getId().toString());
													MaterialEnterSumEntryCollection sumEntrycoll = MaterialEnterSumEntryFactory.getLocalInstance(ctx).getMaterialEnterSumEntryCollection("where sourceBill='"+enterPlanEnterInfo.getId()+"'");
													if (UIRuleUtil.isNotNull(sumEntrycoll) && sumEntrycoll.size() > 0) {
														BigDecimal count = FDCHelper.ZERO;
														for (int l = 0;l < sumEntrycoll.size() ; l++) {
															enterSumEntryInfo = sumEntrycoll.get(l);
															count = count.add(entryInfo.getQuantity());//累计ID为该材料进场分录ID数量
														}
														
														IMaterialEnterPlanBill iPlanBill = MaterialEnterPlanBillFactory.getLocalInstance(ctx);
														MaterialEnterPlanBillInfo planInfo = iPlanBill.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanEnterInfo.getParent().getId()));
														if(planInfo != null){
															planInfo.setBillState(MaterialBillStateEnum.NONE_ARRIVED);//未订货
															iPlanBill.update(new ObjectUuidPK(planInfo.getId()), planInfo);//修改材料进场的订货状态
														}
													}
												}
											}
										}
									}
								}
								
								
								if(enterSumEntryInfo != null){
									BigDecimal sumOrderQuantity = FDCHelper.ZERO;
									BigDecimal orderQuantity = FDCHelper.ZERO;
									if(enterSumEntryInfo.getOrderQuantity() != null){
										sumOrderQuantity = enterSumEntryInfo.getOrderQuantity();//材料进场计划汇总 —— 已订购数量
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//订货单—— 已订购数量
									}
									
									BigDecimal sum = sumOrderQuantity.subtract(orderQuantity);
									enterSumEntryInfo.setOrderQuantity(sum);//回写材料进场计划汇总——已定货数量
									MaterialEnterSumEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getId()),enterSumEntryInfo);
								}
								
								if(enterPlanEnterInfo != null){
									BigDecimal hasOrderQty = FDCHelper.ZERO;
									BigDecimal orderQuantity = FDCHelper.ZERO;
									if(enterPlanEnterInfo.getQuantity() != null){
										hasOrderQty = enterPlanEnterInfo.getHasOrderQty();//材料进场计划单——数量
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//订货单—— 已订购数量
									}
									
									BigDecimal sumPlan = hasOrderQty.subtract(orderQuantity);
									enterPlanEnterInfo.setHasOrderQty(sumPlan);//回写材料进场计划——已定货数量
									if(enterSumEntryInfo != null){
										if(enterSumEntryInfo.getSourceBill() != null){
											MaterialEnterPlanEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getSourceBill()),enterPlanEnterInfo);
										}
									}
									
									
//									//反写材料进场计划订货状态
//									BigDecimal a = FDCHelper.ZERO;
//									BigDecimal b = FDCHelper.ZERO;
//									BigDecimal c = FDCHelper.ZERO;
//									if(enterSumEntryInfo.getQuantity() != null){
//										a = enterSumEntryInfo.getQuantity();//需求数量
//									}
//									if(enterSumEntryInfo.getPlanQuantity() != null){
//										b = enterSumEntryInfo.getPlanQuantity();//计划数量
//									}
//									c = a.subtract(b);
//									MaterialEnterPlanBillInfo planBillInfo = null;
//									MaterialEnterPlanBillCollection palnBillColl = MaterialEnterPlanBillFactory.getLocalInstance(ctx).getMaterialEnterPlanBillCollection("where id = '"+enterPlanEnterInfo.getParent().getId()+"'");
//									if(UIRuleUtil.isNotNull(palnBillColl) && palnBillColl.size() > 0){
//										for(int v = 0; v < palnBillColl.size(); v++){
//											planBillInfo = palnBillColl.get(0);
//										}
//									}
//									planBillInfo.setBillState(MaterialBillStateEnum.NONE);//未订货
//									MaterialEnterPlanBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterPlanEnterInfo.getParent().getId()),planBillInfo);
								}
							}
						}
					}
				}
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id",enterPlanIdSet,CompareType.INCLUDE));
		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("billState");
		view.getSelector().add("entrys.hasOrderQty");
		view.getSelector().add("entrys.quantity");
		MaterialEnterPlanBillCollection enterPlans = MaterialEnterPlanBillFactory.getLocalInstance(ctx).getMaterialEnterPlanBillCollection(view);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.setPrepareStatementSql("update T_PAM_MaterialEnterPlanBill set fBillState=? where fid=?");
		builder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		boolean shouldExcute = false;
		for(Iterator it = enterPlans.iterator();it.hasNext();){
			MaterialEnterPlanBillInfo info = (MaterialEnterPlanBillInfo)it.next();
			boolean isAll = true;
			for(int i=0;i<info.getEntrys().size();i++){
				MaterialEnterPlanEntryInfo entry = info.getEntrys().get(i);
				if(FDCHelper.toBigDecimal(entry.getQuantity()).compareTo(FDCHelper.toBigDecimal(entry.getHasOrderQty()))>0){
					isAll = false;
					break;
				}
			}
			if(MaterialBillStateEnum.PART_ARRIVED.equals(info.getState())||
					MaterialBillStateEnum.ALL_ARRIVED.equals(info.getBillState())){
				;
			}else{
				if(isAll){
					builder.addParam(MaterialBillStateEnum.ALL_VALUE);
				}else{
					builder.addParam(MaterialBillStateEnum.PART_VALUE);
				}
				builder.addParam(info.getId().toString());
				builder.addBatch();
				shouldExcute = true;
			}
		}
		if(shouldExcute)
			builder.executeBatch();
	}
    
	// Added By Owen_wen 2011-01-10
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	Set sourceBillIDSet = getSourceBillIdSet(ctx, model);    	
    	IObjectPK pk = super._save(ctx, model);
		computeOrderQty(sourceBillIDSet, ctx); // 保存后再计算已订货数量绝对正确
		return pk;
	}

	/**
	 * 获取可能会变化源单据（即材料计划汇总单的分录）的sourceBill集合， 下一步需要对材料计划汇总单的分录的已订货数量进行计划。
	 * 
	 * @author owen_wen 2010-01-10
	 * @param ctx
	 * @param model
	 * @return
	 * @throws BOSException
	 */
	private Set getSourceBillIdSet(Context ctx, IObjectValue model) throws BOSException {
		MaterialOrderBizBillInfo materialOrderBizBillInfo = (MaterialOrderBizBillInfo) model;
		String billId = "";
		if (materialOrderBizBillInfo.getId() != null)
			billId = materialOrderBizBillInfo.getId().toString();
		Set sourceBillIDSet = getSourceBillIdSetFromDB(billId, ctx);		
		sourceBillIDSet.addAll(getSourceBillIDSetFromCurrentModel(materialOrderBizBillInfo));
		return sourceBillIDSet;
	}

	/**
	 * 取出等保存单据在数据库中关联的源单据 （即计划汇总单分录）的ID集
	 * 
	 * @author owen_wen 2010-01-10
	 * @param billInfo
	 *            待保存的单据（材料订货单）
	 * @return sourceBillIDSet 源单据 （即计划汇总单分录）的ID集
	 */
	private Set getSourceBillIDSetFromCurrentModel(MaterialOrderBizBillInfo billInfo) {
		Set sourceBillIDSet = new HashSet();
		MaterialOrderBizBillEntryCollection mobbeColl = billInfo.getEntrys();
		for (int i = 0, size = mobbeColl.size(); i < size; i++) {
			MaterialOrderBizBillEntryInfo entryInfo = mobbeColl.get(i);
			if (entryInfo.getSourceBill() != null)
				sourceBillIDSet.add(entryInfo.getSourceBill());
		}		
		return sourceBillIDSet;
	}

	/**
	 * 先取出保存前该单据在数据库中关联的源单据 （即计划汇总单分录）的ID集
	 * 
	 * @author owen_wen 2010-01-10
	 * @param materialOrderBizBillId
	 *            待保存单据（材料订货单）的ID
	 * @return sourceBillIDSet 源单据 （即计划汇总单分录）的ID集
	 */
	private Set getSourceBillIdSetFromDB(String materialOrderBizBillId, Context ctx) throws BOSException {
		Set sourceBillIDSet = new HashSet();    	
		if (!StringUtils.isEmpty(materialOrderBizBillId)) {
			MaterialOrderBizBillEntryCollection mobbeColl = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx)
					.getMaterialOrderBizBillEntryCollection("where parent.id = '" + materialOrderBizBillId + "'");
			for (int i = 0; i < mobbeColl.size(); i++) {
				if (mobbeColl.get(i).getSourceBill() != null)
					sourceBillIDSet.add(mobbeColl.get(i).getSourceBill());
			}
		}
		return sourceBillIDSet;
	}

	/**
	 * 计算当前 材料订货单 的分录所关联的源单据（即计划汇总单分录）的 “订货数量”, 并写到数据库中<br>
	 * compute the Order Quantity for MaterialEnterSumEntry
	 * 
	 * @author owen_wen 2010-01-10
	 * @param sourceBillIDList
	 *            订货单分录对应的sourceBill 的id集
	 * @author owen_wen 2010-01-10
	 * @throws BOSException
	 */
	private void computeOrderQty(Set sourceBillIDSet, Context ctx) throws BOSException {
		String sql = "update T_PAM_MaterialEnterSumEntry set FOrderQuantity = "
				+ "(select sum(FQuantity) from T_PAM_MaterialOBBE where FSourceBillID = ?)" + " where fid = ?";

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
	
	// Added By Owen_wen 2011-01-10
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSet(ctx, model);
		IObjectPK pk = super._submit(ctx, model);
		computeOrderQty(sourceBillIDSet, ctx); // 提交后再计算已订货数量绝对正确
		return pk;
	}
	
	// Added By Owen_wen 2011-01-11
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSetFromDB(pk.toString(), ctx);	
		super._delete(ctx, pk);
		computeOrderQty(sourceBillIDSet, ctx); // 删除后再计算已订货数量
	}
}