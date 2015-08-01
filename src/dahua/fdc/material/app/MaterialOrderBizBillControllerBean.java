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
				MaterialOrderBizBillEntryInfo entryInfo = null;//��������¼����
				MaterialEnterSumEntryInfo enterSumEntryInfo = null;//���Ͻ����ƻ����ܷ�¼����
				MaterialEnterPlanEntryInfo enterPlanEnterInfo = null;//���Ͻ����ƻ���¼����
				String id = (String)idList.get(i);
				super._audit(ctx, BOSUuid.read(id));
				MaterialOrderBizBillInfo billInfo = MaterialOrderBizBillFactory.getLocalInstance(ctx).getMaterialOrderBizBillInfo(new ObjectUuidPK(id));
				if(billInfo.getId() != null){
					//�õ���������¼����
					MaterialOrderBizBillEntryCollection OrderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where parent.id='"+billInfo.getId()+"'");
					if (UIRuleUtil.isNotNull(OrderEntrycoll) && OrderEntrycoll.size() > 0) {
						for (int j = 0;j < OrderEntrycoll.size() ; j++) {
							entryInfo = OrderEntrycoll.get(j);
							
							//�õ����Ͻ����ƻ����ܷ�¼����
							if(entryInfo.getId() != null && entryInfo.getSourceBill()!= null){
								MaterialEnterSumEntryCollection entryColl = MaterialEnterSumEntryFactory.getLocalInstance(ctx).getMaterialEnterSumEntryCollection("where id = '"+entryInfo.getSourceBill()+"'");
								if (UIRuleUtil.isNotNull(entryColl) && entryColl.size() > 0) {
									for (int k = 0; k < entryColl.size(); k++) {
										enterSumEntryInfo = entryColl.get(k);
										MaterialOrderBizBillEntryCollection orderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where sourceBill='"+enterSumEntryInfo.getId()+"'");
										if (UIRuleUtil.isNotNull(orderEntrycoll) && orderEntrycoll.size() > 0) {
											BigDecimal count = FDCHelper.ZERO;
											BigDecimal enterSumCount = enterSumEntryInfo.getQuantity();//���ܵ���¼������
											for (int l = 0;l < orderEntrycoll.size() ; l++) {
												entryInfo = orderEntrycoll.get(l);
												count = count.add(entryInfo.getQuantity());//�ۼ�IDΪ�û��ܷ�¼ID����
											}
											
											IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
											MaterialEnterSumInfo sumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(enterSumEntryInfo.getParent().getId()));
											if(sumInfo != null){
												sumInfo.setBillstate(MaterialBillStateEnum.ALL);//ȫ������
												if(!count.equals(enterSumCount)){
													sumInfo.setBillstate(MaterialBillStateEnum.PART);//���ֶ���
												}
												ienterSum.update(new ObjectUuidPK(sumInfo.getId()), sumInfo);//�޸Ķ������Ķ���״̬
											}
										}
										
										//�õ����Ͻ����ƻ���¼����
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
														BigDecimal enterPlanCount = enterPlanEnterInfo.getQuantity();//�����ƻ���¼������
														for (int l = 0;l < sumEntrycoll.size() ; l++) {
															enterSumEntryInfo = sumEntrycoll.get(l);
															count = count.add(entryInfo.getQuantity());//�ۼ�IDΪ�ò��Ͻ�����¼ID����
														}
														
														IMaterialEnterPlanBill iPlanBill = MaterialEnterPlanBillFactory.getLocalInstance(ctx);
														MaterialEnterPlanBillInfo planInfo = iPlanBill.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanEnterInfo.getParent().getId()));
														if(planInfo != null){
															planInfo.setBillState(MaterialBillStateEnum.ALL);//ȫ������
															if(!count.equals(enterPlanCount)){
																planInfo.setBillState(MaterialBillStateEnum.PART);//���ֶ���
															}
															iPlanBill.update(new ObjectUuidPK(planInfo.getId()), planInfo);//�޸Ĳ��Ͻ����Ķ���״̬
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
										sumOrderQuantity = enterSumEntryInfo.getOrderQuantity();//���Ͻ����ƻ����� ���� �Ѷ�������
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//���������� �Ѷ�������
									}
									
									BigDecimal sum = sumOrderQuantity.add(orderQuantity);
									enterSumEntryInfo.setOrderQuantity(sum);//��д���Ͻ����ƻ����ܡ����Ѷ�������
									MaterialEnterSumEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getId()),enterSumEntryInfo);
								}
								
								if(enterPlanEnterInfo != null){
									BigDecimal hasOrderQuantity = FDCHelper.ZERO;
									BigDecimal orderQuantity = FDCHelper.ZERO;
									if(enterPlanEnterInfo.getHasOrderQty() != null){
										hasOrderQuantity = enterPlanEnterInfo.getHasOrderQty();//���Ͻ����ƻ�����������
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//���������� �Ѷ�������
									}
									
									BigDecimal sumPlan = hasOrderQuantity.add(orderQuantity);
									enterPlanEnterInfo.setHasOrderQty(sumPlan);//��д���Ͻ����ƻ������Ѷ�������
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
				MaterialOrderBizBillEntryInfo entryInfo = null;//��������¼����
				MaterialEnterSumEntryInfo enterSumEntryInfo = null;//���Ͻ����ƻ����ܷ�¼����
				MaterialEnterPlanEntryInfo enterPlanEnterInfo = null;//���Ͻ����ƻ���¼����
				String id = (String)idList.get(i);
				super._unAudit(ctx, BOSUuid.read(id));
				MaterialOrderBizBillInfo billInfo = MaterialOrderBizBillFactory.getLocalInstance(ctx).getMaterialOrderBizBillInfo(new ObjectUuidPK(id));
				if(billInfo.getId() != null){
					//�õ���������¼����
					MaterialOrderBizBillEntryCollection OrderEntrycoll = MaterialOrderBizBillEntryFactory.getLocalInstance(ctx).getMaterialOrderBizBillEntryCollection("where parent.id='"+billInfo.getId()+"'");
					if (UIRuleUtil.isNotNull(OrderEntrycoll) && OrderEntrycoll.size() > 0) {
						for (int j = 0;j < OrderEntrycoll.size() ; j++) {
							entryInfo = OrderEntrycoll.get(j);
							
							//�õ����Ͻ����ƻ����ܷ�¼����
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
												count = count.add(entryInfo.getQuantity());//�ۼ�IDΪ�û��ܷ�¼ID����
											}
											
											IMaterialEnterSum ienterSum = MaterialEnterSumFactory.getLocalInstance(ctx);
											MaterialEnterSumInfo sumInfo = ienterSum.getMaterialEnterSumInfo(new ObjectUuidPK(enterSumEntryInfo.getParent().getId()));
											if(sumInfo != null){
												sumInfo.setBillstate(MaterialBillStateEnum.NONE_ARRIVED);//δ����
												ienterSum.update(new ObjectUuidPK(sumInfo.getId()), sumInfo);//�޸Ķ������Ķ���״̬
											}
										}
										
										//�õ����Ͻ����ƻ���¼����
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
															count = count.add(entryInfo.getQuantity());//�ۼ�IDΪ�ò��Ͻ�����¼ID����
														}
														
														IMaterialEnterPlanBill iPlanBill = MaterialEnterPlanBillFactory.getLocalInstance(ctx);
														MaterialEnterPlanBillInfo planInfo = iPlanBill.getMaterialEnterPlanBillInfo(new ObjectUuidPK(enterPlanEnterInfo.getParent().getId()));
														if(planInfo != null){
															planInfo.setBillState(MaterialBillStateEnum.NONE_ARRIVED);//δ����
															iPlanBill.update(new ObjectUuidPK(planInfo.getId()), planInfo);//�޸Ĳ��Ͻ����Ķ���״̬
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
										sumOrderQuantity = enterSumEntryInfo.getOrderQuantity();//���Ͻ����ƻ����� ���� �Ѷ�������
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//���������� �Ѷ�������
									}
									
									BigDecimal sum = sumOrderQuantity.subtract(orderQuantity);
									enterSumEntryInfo.setOrderQuantity(sum);//��д���Ͻ����ƻ����ܡ����Ѷ�������
									MaterialEnterSumEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getId()),enterSumEntryInfo);
								}
								
								if(enterPlanEnterInfo != null){
									BigDecimal hasOrderQty = FDCHelper.ZERO;
									BigDecimal orderQuantity = FDCHelper.ZERO;
									if(enterPlanEnterInfo.getQuantity() != null){
										hasOrderQty = enterPlanEnterInfo.getHasOrderQty();//���Ͻ����ƻ�����������
									}
									if(entryInfo.getQuantity() != null){
										orderQuantity = entryInfo.getQuantity();//���������� �Ѷ�������
									}
									
									BigDecimal sumPlan = hasOrderQty.subtract(orderQuantity);
									enterPlanEnterInfo.setHasOrderQty(sumPlan);//��д���Ͻ����ƻ������Ѷ�������
									if(enterSumEntryInfo != null){
										if(enterSumEntryInfo.getSourceBill() != null){
											MaterialEnterPlanEntryFactory.getLocalInstance(ctx).update(new ObjectUuidPK(enterSumEntryInfo.getSourceBill()),enterPlanEnterInfo);
										}
									}
									
									
//									//��д���Ͻ����ƻ�����״̬
//									BigDecimal a = FDCHelper.ZERO;
//									BigDecimal b = FDCHelper.ZERO;
//									BigDecimal c = FDCHelper.ZERO;
//									if(enterSumEntryInfo.getQuantity() != null){
//										a = enterSumEntryInfo.getQuantity();//��������
//									}
//									if(enterSumEntryInfo.getPlanQuantity() != null){
//										b = enterSumEntryInfo.getPlanQuantity();//�ƻ�����
//									}
//									c = a.subtract(b);
//									MaterialEnterPlanBillInfo planBillInfo = null;
//									MaterialEnterPlanBillCollection palnBillColl = MaterialEnterPlanBillFactory.getLocalInstance(ctx).getMaterialEnterPlanBillCollection("where id = '"+enterPlanEnterInfo.getParent().getId()+"'");
//									if(UIRuleUtil.isNotNull(palnBillColl) && palnBillColl.size() > 0){
//										for(int v = 0; v < palnBillColl.size(); v++){
//											planBillInfo = palnBillColl.get(0);
//										}
//									}
//									planBillInfo.setBillState(MaterialBillStateEnum.NONE);//δ����
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
		computeOrderQty(sourceBillIDSet, ctx); // ������ټ����Ѷ�������������ȷ
		return pk;
	}

	/**
	 * ��ȡ���ܻ�仯Դ���ݣ������ϼƻ����ܵ��ķ�¼����sourceBill���ϣ� ��һ����Ҫ�Բ��ϼƻ����ܵ��ķ�¼���Ѷ����������мƻ���
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
	 * ȡ���ȱ��浥�������ݿ��й�����Դ���� �����ƻ����ܵ���¼����ID��
	 * 
	 * @author owen_wen 2010-01-10
	 * @param billInfo
	 *            ������ĵ��ݣ����϶�������
	 * @return sourceBillIDSet Դ���� �����ƻ����ܵ���¼����ID��
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
	 * ��ȡ������ǰ�õ��������ݿ��й�����Դ���� �����ƻ����ܵ���¼����ID��
	 * 
	 * @author owen_wen 2010-01-10
	 * @param materialOrderBizBillId
	 *            �����浥�ݣ����϶���������ID
	 * @return sourceBillIDSet Դ���� �����ƻ����ܵ���¼����ID��
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
	 * ���㵱ǰ ���϶����� �ķ�¼��������Դ���ݣ����ƻ����ܵ���¼���� ������������, ��д�����ݿ���<br>
	 * compute the Order Quantity for MaterialEnterSumEntry
	 * 
	 * @author owen_wen 2010-01-10
	 * @param sourceBillIDList
	 *            ��������¼��Ӧ��sourceBill ��id��
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
		computeOrderQty(sourceBillIDSet, ctx); // �ύ���ټ����Ѷ�������������ȷ
		return pk;
	}
	
	// Added By Owen_wen 2011-01-11
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		Set sourceBillIDSet = getSourceBillIdSetFromDB(pk.toString(), ctx);	
		super._delete(ctx, pk);
		computeOrderQty(sourceBillIDSet, ctx); // ɾ�����ټ����Ѷ�������
	}
}