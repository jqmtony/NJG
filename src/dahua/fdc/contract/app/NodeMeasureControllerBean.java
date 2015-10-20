package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryCollection;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo;
import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.eas.fdc.contract.ConChangeSplitEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryFactory;
import com.kingdee.eas.fdc.contract.NodeMeasureCollection;
import com.kingdee.eas.fdc.contract.NodeMeasureEntryCollection;
import com.kingdee.eas.fdc.contract.NodeMeasureEntryFactory;
import com.kingdee.eas.fdc.contract.NodeMeasureEntryInfo;
import com.kingdee.eas.fdc.contract.NodeMeasureInfo;
import com.kingdee.eas.fdc.contract.SettlementCostSplitEntryFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class NodeMeasureControllerBean extends AbstractNodeMeasureControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.NodeMeasureControllerBean");

    /**
     * ��ʼ������
     * @param ctx ������
     * @param contractId ��ͬid
     * @param nodeMeasureId �ڵ�Ƽ�id
     * ��nodeMeasureId Ϊ��ʱ��Ϊ�������ݣ���Ӹ������ȡ��
     * ��nodeMeasureId ��Ϊ�գ���δ����ʱ����¼�Ӹ��������ȡ�������������ݿ�ȡ��
     * ��nodeMeasureId ��Ϊ�գ���������ʱ������ȫ���ӱ�������ݿ���ȡ��
     */
	protected Map _fetchData(Context ctx, String contractId, String nodeMeasureId) throws BOSException, EASBizException {
		Map retMap=new HashMap();
		CostAccountCollection costAccts = null;
		//ȡ�ɱ���Ŀ
		try{
			costAccts = getCostAccounts(ctx,contractId);
			retMap.put("CostAccountCollection", costAccts);
			
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		NodeMeasureInfo nodeMeasure=getNodeMeasure(ctx,contractId,nodeMeasureId);
		boolean isAudit=nodeMeasure.getState().equals(FDCBillStateEnum.AUDITTED);
		if(isAudit){
			//��������� ֱ�Ӵ����ݿ���ȡֵ
			for(Iterator iter=nodeMeasure.getEntrys().iterator();iter.hasNext();){
				NodeMeasureEntryInfo entry=(NodeMeasureEntryInfo)iter.next();
				if(entry.getCostAccount() == null){
					continue;
				}
				Map rowMap=getRowMap(retMap, entry.getCostAccount().getId().toString());
				rowMap.put("unit",entry.getUnit());
				rowMap.put("contract_price", FDCHelper.add(rowMap.get("contract_price"), entry.getConPrice()));
				rowMap.put("contract_workLoad", FDCHelper.add(rowMap.get("contract_workLoad"), entry.getConWorkLoad()));
				rowMap.put("contract_amount", FDCHelper.add(rowMap.get("contract_amount"), entry.getConAmount()));
				rowMap.put("change_price", FDCHelper.add(rowMap.get("change_price"), entry.getChangePrice()));
				rowMap.put("change_workLoad", FDCHelper.add(rowMap.get("change_workLoad"), entry.getChangeWorkLoad()));
				rowMap.put("change_amount", FDCHelper.add(rowMap.get("change_amount"), entry.getChangeAmount()));
				rowMap.put("settlement_price", FDCHelper.add(rowMap.get("settle_price"), entry.getSettlePrice()));
				rowMap.put("settlement_workLoad", FDCHelper.add(rowMap.get("settle_workLoad"), entry.getSettleWorkLoad()));
				rowMap.put("settlement_amount", FDCHelper.add(rowMap.get("settle_workLoad_amount"), entry.getSettleAmount()));
				rowMap.put("nodemeasure_price", FDCHelper.add(rowMap.get("nodemeasure_price"), entry.getNodePrice()));
				rowMap.put("nodemeasure_workLoad", FDCHelper.add(rowMap.get("nodemeasure_workLoad"), entry.getNodeWorkLoad()));
				rowMap.put("nodemeasure_amount", FDCHelper.add(rowMap.get("nodemeasure_amount"), entry.getNodeAmount()));
				rowMap.put("payment_price", FDCHelper.add(rowMap.get("payment_price"), entry.getPayPrice()));
				rowMap.put("payment_workLoad", FDCHelper.add(rowMap.get("payment_workLoad"), entry.getPayWorkLoad()));
				rowMap.put("payment_amount", FDCHelper.add(rowMap.get("payment_amount"), entry.getPayAmount()));
				
				
			}
		}else{
			//���δ��������ʵʱ��������ݵ�ֵ -- ��ͬ��֡������֡������ֺ͸�����
			//��ͬ���
			buildSplitRowMap(ctx, contractId, retMap,"contract");
			//������
			buildSplitRowMap(ctx, contractId, retMap,"change");
			//������
			buildSplitRowMap(ctx, contractId, retMap,"settlement");
			//������
			buildSplitRowMap(ctx, contractId, retMap,"payment");
			
			//�ڵ����ֶ�
			buildNodeMeasureRowMap(ctx, nodeMeasure, retMap);
			
		}
		getUnitFromAccount(ctx,costAccts,retMap);
		nodeMeasure.getEntrys().clear();
		//�ڵ�Ƽ�ͷ������ʱ��
		retMap.put("NodeMeasureInfo", nodeMeasure);
		return retMap;
	}
	private void buildSplitRowMap(Context ctx, String contractId, Map retMap, String type) throws BOSException, EASBizException {
		if(type==null){
			return;
		}
		IFDCSplitBillEntry iFDCSplitBillEntry=null;
		if(type.equals("contract")){
			iFDCSplitBillEntry = ContractCostSplitEntryFactory.getLocalInstance(ctx);
		}else if(type.equals("change")){
			iFDCSplitBillEntry=ConChangeSplitEntryFactory.getLocalInstance(ctx);
		}else if(type.equals("settlement")){
			iFDCSplitBillEntry=SettlementCostSplitEntryFactory.getLocalInstance(ctx);
		}else if(type.equals("payment")){
			iFDCSplitBillEntry=PaymentSplitEntryFactory.getLocalInstance(ctx);
		}
		if(iFDCSplitBillEntry==null){
			return;
		}
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("price");
		view.getSelector().add("workLoad");
		view.getSelector().add("amount");
		view.getSelector().add("costAccount.id");
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
		view.getFilter().appendFilterItem("parent.contractBill.id", contractId);
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		//���������ս���ʱ��ֻȡ���ս����ֵ�����
		if("settlement".equals(type)){
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("hasSettled",new Integer(1)));
			filter.getFilterItems().add(new FilterItemInfo("id",contractId));
				if(ContractBillFactory.getLocalInstance(ctx).exists(filter)){
					view.getFilter().getFilterItems().add(new FilterItemInfo("parent.settlementBill.isFinalSettle",new Integer(1)));
				}
			
		}
		FDCSplitBillEntryCollection entrys=iFDCSplitBillEntry.getFDCSplitBillEntryCollection(view);
		
		for(Iterator iter=entrys.iterator();iter.hasNext();){
			FDCSplitBillEntryInfo entry=(FDCSplitBillEntryInfo)iter.next();
			if(entry.getCostAccount()==null){
				continue;
			}
			Map rowMap=getRowMap(retMap, entry.getCostAccount().getId().toString());
//			rowMap.put(type+"_price", FDCHelper.add(rowMap.get(type+"_price"), entry.getPrice()));
			rowMap.put(type+"_workLoad", FDCHelper.add(rowMap.get(type+"_workLoad"), entry.getWorkLoad()));
			rowMap.put(type+"_amount", FDCHelper.add(rowMap.get(type+"_amount"), entry.getAmount()));
			//���� = �ۼƽ��/�ۼƹ�����
			rowMap.put(type+"_price", FDCHelper.divide(rowMap.get(type+"_amount"),rowMap.get(type+"_workLoad")));
		}
	}
	/**
	 * ��Ϊ���ֹ�¼�룬ֱ�����ݿ��нڵ㹤���������ۺͺϼ۵�ֵ
	 * @param ctx
	 * @param nodeMeasure
	 * @param retMap
	 * @throws BOSException
	 */
	private void buildNodeMeasureRowMap(Context ctx, NodeMeasureInfo nodeMeasure, Map retMap) throws BOSException {
		String type="nodemeasure"; 
		for(Iterator iter=nodeMeasure.getEntrys().iterator();iter.hasNext();){
			NodeMeasureEntryInfo entry=(NodeMeasureEntryInfo)iter.next();
			if(entry.getCostAccount() == null){
				continue;
			}
			Map rowMap=getRowMap(retMap, entry.getCostAccount().getId().toString());
			rowMap.put("unit",entry.getUnit());
			rowMap.put(type+"_price", FDCHelper.add(rowMap.get(type+"_price"), entry.getNodePrice()));
			rowMap.put(type+"_workLoad", FDCHelper.add(rowMap.get(type+"_workLoad"), entry.getNodeWorkLoad()));
			rowMap.put(type+"_amount", FDCHelper.add(rowMap.get(type+"_amount"), entry.getNodeAmount()));
		}
		
	}
	/**
	 * ȡ��NodeMeasureInfo ��ͷ��Ϣ
	 * @param ctx
	 * @param contractId
	 * @param nodeMeasureId
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	private NodeMeasureInfo getNodeMeasure(Context ctx,String contractId, String nodeMeasureId) throws EASBizException, BOSException{
		NodeMeasureInfo nodeMeasure=null;
		if(nodeMeasureId==null){
			nodeMeasure=new NodeMeasureInfo();
			//��ʼnodeMeasure�Ա�������
			nodeMeasure.setId(BOSUuid.create(nodeMeasure.getBOSType()));
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("name");
			selector.add("number");
			selector.add("curProject.longNumber");
			selector.add("curProject.name");
			selector.add("curProject.number");
			
			ContractBillInfo con=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId),selector);
			nodeMeasure.setContractBill(con);
			nodeMeasure.setCurProject(con.getCurProject());
			nodeMeasure.setState(FDCBillStateEnum.SAVED);
			
			//TODO ����Ҫ��ʼ������
			nodeMeasure.setCreateTime(new Timestamp(new Date().getTime()));
			nodeMeasure.setCreator(ContextUtil.getCurrentUserInfo(ctx));
			nodeMeasure.setBizDate(DateTimeUtils.truncateDate(new Date()));
			
		}else{
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("id");
			selector.add("number");
			selector.add("bizDate");
			selector.add("createTime");
			selector.add("state");
			selector.add("amount");
			selector.add("description");
			selector.add("creator.name");
			selector.add("contractBill.id");
			selector.add("contractBill.number");
			selector.add("contractBill.name");
			selector.add("curProject.id");
			selector.add("curProject.number");
			selector.add("curProject.name");
			selector.add("entrys.*");
			
			nodeMeasure=getNodeMeasureInfo(ctx, new ObjectUuidPK(BOSUuid.read(nodeMeasureId)), selector);
		}
		return nodeMeasure;
	}
	
	
	/**
	 * ȡ��ֵĳɱ���Ŀ����Ŀ�����룬�ɱ���Ŀ����������
	 * @return
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	private CostAccountCollection getCostAccounts(Context ctx,String contractId) throws BOSException, SQLException{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct t.fcostaccountid from ( \n");
		builder.appendSql(" select fcostaccountid from T_Con_ContractCostSplitEntry where fparentid in (select fid from T_Con_ContractCostSplit where fcontractbillid=? and fstate<>?)");
		builder.appendSql(" union all ");
		builder.appendSql(" select fcostaccountid from T_Con_ConChangeSplitEntry where fparentid in (select fid from T_Con_ConChangeSplit where fcontractbillid=? and fstate<>?)");
		builder.appendSql(")t");
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		builder.addParam(contractId);
		builder.addParam(FDCBillStateEnum.INVALID_VALUE);
		IRowSet rowSet=builder.executeQuery();
		Set acctSet=new HashSet();
		while(rowSet.next()){
			acctSet.add(rowSet.getString("fcostaccountid"));
		}
		if(acctSet == null || acctSet.size() <= 0){
			return null;
		}
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("id",acctSet,CompareType.INCLUDE));
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		view.getSelector().add("isLeaf");
		view.getSelector().add("level");
		view.getSelector().add("curProject.id");
		view.getSelector().add("curProject.longNumber");
		view.getSelector().add("curProject.name");
		view.getSorter().add(new SorterItemInfo("curProject.longNumber"));
		view.getSorter().add(new SorterItemInfo("longNumber"));
		CostAccountCollection c=CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		return c;
	}
	
	/**
	 * ȡ�óɱ���Ŀ��Ӧ����Map�����û���򴴽�һ��
	 * @param retMap
	 * @param acctId
	 * @return
	 */
	private Map getRowMap(Map retMap,String acctId){
		Map rowMap=null;
		if(retMap.get(acctId)==null){
			rowMap=new HashMap();
			retMap.put(acctId, rowMap);
		}else{
			rowMap=(Map)retMap.get(acctId);
		}
		return rowMap;
	}
	/**
	 * ���ݳɱ���Ŀ��ü�����λ
	 * @param ctx
	 * @param costAccountColl
	 * @param restMap
	 * @throws BOSException
	 * @throws ESABizException
	 */
	private void getUnitFromAccount(Context ctx,CostAccountCollection costAccountColl,Map retMap) throws BOSException,EASBizException {
		if(costAccountColl == null || costAccountColl.size() <= 0){
			return;
		}
		//�ɱ���Ŀids
		Set acctIds = new HashSet();
		for(Iterator it = costAccountColl.iterator();it.hasNext();){
			CostAccountInfo costAccount = (CostAccountInfo)it.next();
			acctIds.add(costAccount.getId().toString());
		}
		//		Map unitMap = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		view.getSelector().add("unit");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("costAccount.id",acctIds,CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("head.isLastVersion",new Integer(1)));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("createTime"));
		CostEntryCollection entrys = CostEntryFactory.getLocalInstance(ctx).getCostEntryCollection(view);
		for(Iterator it = entrys.iterator();it.hasNext();){
			CostEntryInfo info = (CostEntryInfo)it.next();
			String key = info.getCostAccount().getId().toString();
			Map rowMap=getRowMap(retMap, key);
			rowMap.put("unit",info.getUnit());
		}
	}
	/**
	 * ��ѯ����
	 * 
	 */
	protected Map _fetchExecData(Context ctx, String contractId, Date startDate, Date endDate) throws BOSException, EASBizException {
		boolean hasAuditNodeMeasure = hasAuditNodeMeasure(ctx,contractId,startDate,endDate);
		if(!hasAuditNodeMeasure){
			return null;
		}
		Map retMap=new HashMap();
		handDate(startDate,endDate);
		NodeMeasureInfo nodeMeasure=new NodeMeasureInfo();
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("curProject.longNumber");
		selector.add("curProject.name");
		selector.add("curProject.number");
		ContractBillInfo con=ContractBillFactory.getLocalInstance(ctx).getContractBillInfo(new ObjectUuidPK(contractId),selector);
		nodeMeasure.setContractBill(con);
		nodeMeasure.setCurProject(con.getCurProject());
		
		//ȡ�ɱ���Ŀ
		try{
			CostAccountCollection costAccts = getCostAccounts(ctx,contractId);
			retMap.put("CostAccountCollection", costAccts);
			getUnitFromAccount(ctx,costAccts,retMap);
//			retMap.put("CostAccountCollection", getCostAccounts(ctx, contractId));
		}catch(SQLException e){
			throw new BOSException(e);
		}
		
		//��ͬ���
		buildSplitRowMap(ctx, contractId, retMap,"contract");
		//������
		buildSplitRowMap(ctx, contractId, retMap,"change");
		//������
		buildSplitRowMap(ctx, contractId, retMap,"settlement");
		//������
		buildSplitRowMap(ctx, contractId, retMap,"payment");
		
		//�ڵ����ֶ�
		buildExtNodeMeasureRowMap(ctx, contractId,startDate, endDate,retMap);

		retMap.put("NodeMeasureInfo", nodeMeasure);
		return retMap;
	
	}
	
	private void handDate(Date startDate, Date endDate) {
		if(startDate==null){
			startDate=FDCDateHelper.getFirstYearDate(1900);
		}
		if(endDate==null){
			endDate=FDCDateHelper.getFirstYearDate(3000);
		}
		startDate=DateTimeUtils.truncateDate(startDate);
		endDate=DateTimeUtils.truncateDate(endDate);
	}
	private void buildExtNodeMeasureRowMap(Context ctx, String contractId,Date startDate, Date endDate, Map retMap) throws BOSException {
		String type="nodemeasure"; 
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("costAccount.id");
		view.getSelector().add("nodeWorkLoad");
		view.getSelector().add("nodeAmount");
		view.setFilter(new FilterInfo());
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.bizDate",startDate,CompareType.GREATER_EQUALS));
		view.getFilter().getFilterItems().add(new FilterItemInfo("parent.bizDate",endDate,CompareType.LESS_EQUALS));
		view.getFilter().appendFilterItem("parent.contractBill.id", contractId);
		//ֻ������״̬�Ľڵ�Ƽ۵�
		view.getFilter().appendFilterItem("parent.state",FDCBillStateEnum.AUDITTED_VALUE);
		NodeMeasureEntryCollection nodeMeasureEntryCollection = NodeMeasureEntryFactory.getLocalInstance(ctx).getNodeMeasureEntryCollection(view);
		
		for(Iterator iter=nodeMeasureEntryCollection.iterator();iter.hasNext();){
			NodeMeasureEntryInfo entry=(NodeMeasureEntryInfo)iter.next();
			if(entry.getCostAccount()==null){
				continue;
			}
			Map rowMap=getRowMap(retMap, entry.getCostAccount().getId().toString());
			rowMap.put(type+"_workLoad", FDCHelper.add(rowMap.get(type+"_workLoad"), entry.getNodeWorkLoad()));
			rowMap.put(type+"_amount", FDCHelper.add(rowMap.get(type+"_amount"), entry.getNodeAmount()));
			rowMap.put(type+"_price", FDCHelper.divide(rowMap.get(type+"_amount"), rowMap.get(type+"_workLoad")));
		}
		
	}
	/**
	 * ���� ����fetchDataȡ�������۲������
	 */
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		NodeMeasureInfo info = getNodeMeasureInfo(ctx,new ObjectUuidPK(billId));
		info.getEntrys().clear();
		NodeMeasureEntryCollection entrys = new NodeMeasureEntryCollection();
		String contractId = info.getContractBill().getId().toString();
		Map map = _fetchData(ctx,contractId,billId.toString());
		CostAccountCollection costAccts = (CostAccountCollection) map.get("CostAccountCollection");
		for(Iterator it = costAccts.iterator();it.hasNext();){
			CostAccountInfo acct = (CostAccountInfo)it.next();
			String acctId = acct.getId().toString();
			Map rowMap  = (Map)map.get(acctId);
			NodeMeasureEntryInfo entry = new NodeMeasureEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			entry.setParent(info);
			entry.setCostAccount(acct);
			if(rowMap.get("unit") != null){
				entry.setUnit(rowMap.get("unit").toString());				
			}
			entry.setConWorkLoad((BigDecimal)rowMap.get("contract_workLoad"));
			entry.setConPrice((BigDecimal)rowMap.get("contract_price"));
			entry.setConAmount((BigDecimal)rowMap.get("contract_amount"));
			entry.setChangeWorkLoad((BigDecimal)rowMap.get("change_workLoad"));
			entry.setChangePrice((BigDecimal)rowMap.get("change_price"));
			entry.setChangeAmount((BigDecimal)rowMap.get("change_amount"));
			entry.setSettleWorkLoad((BigDecimal)rowMap.get("settlement_workLoad"));
			entry.setSettlePrice((BigDecimal)rowMap.get("settlement_price"));
			entry.setSettleAmount((BigDecimal)rowMap.get("settlement_amount"));
			entry.setNodeWorkLoad((BigDecimal)rowMap.get("nodemeasure_workLoad"));
			entry.setNodePrice((BigDecimal)rowMap.get("nodemeasure_price"));
			entry.setNodeAmount((BigDecimal)rowMap.get("nodemeasure_amount"));
			entry.setPayWorkLoad((BigDecimal)rowMap.get("payment_workLoad"));
			entry.setPayPrice((BigDecimal)rowMap.get("payment_price"));
			entry.setPayAmount((BigDecimal)rowMap.get("payment_amount"));
			
			entrys.add(entry);
			
		}
		info.setState(FDCBillStateEnum.AUDITTED);
		info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		info.setAuditTime(new Date());
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		selector.add("entrys.*");
		
		_updatePartial(ctx, info, selector);
	}
	/**
	 * �Ƿ���ڷ��������Ľڵ�Ƽ���Ϣ
	 * @param ctx
	 * @param contractId ��ͬid
	 * @param startDate ��ʼ����
	 * @param endDate ��������
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	private boolean hasAuditNodeMeasure(Context ctx,String contractId,Date startDate,Date endDate) throws BOSException,EASBizException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("state");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",startDate,CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",endDate,CompareType.LESS_EQUALS));
		view.setFilter(filter);
		NodeMeasureCollection coll = getNodeMeasureCollection(ctx,view);
		for(Iterator it = coll.iterator();it.hasNext();){
			NodeMeasureInfo info = (NodeMeasureInfo)it.next();
			if(info.getState() != null && info.getState().equals(FDCBillStateEnum.AUDITTED)){
				return true;
			}
		}
		return false;
	}

}