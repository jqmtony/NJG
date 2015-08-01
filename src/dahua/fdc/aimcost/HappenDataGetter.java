package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class HappenDataGetter extends CostDataGetter implements Serializable{
	public Map hasHappenMap;

	public Map conSplitMap;

	public Map changeSplitMap;

	public Map settleSplitMap;
	public Map partSettleSplitMap;

	public Map noTextSplitMap;

	public Map paySplitMap;//已完工工程量
	
	public Map paidSplitMap;//已付款

	// modify by lihaiou,2014-09-05,从直接拆分表中获取的数据存在问题，需要重原始单据中取值bug R140804-0230
	//默认为true，即遵循原来的方式从成本拆分表中取数，为false时从各自的拆分表中获取数据
	public boolean isFromCostSplit = true;
	
	public void setFromCostSplit(boolean isFromCostSplit) {
		this.isFromCostSplit = isFromCostSplit;
	}

	protected Context ctx = null;
	protected String cuId=null;
	public void clear(){
		clearMap(this.hasHappenMap);
		clearMap(this.conSplitMap);
		clearMap(this.changeSplitMap);
		clearMap(this.settleSplitMap);
		clearMap(this.noTextSplitMap);
		clearMap(this.paySplitMap);
		clearMap(this.paidSplitMap);
	}
	/**
	 * 仅供子类使用,不能用此构造函数实例化
	 */
	protected HappenDataGetter(){}
	
	/**
	 * 不执行查询操作,以供外部程序调用get方法
	 * @param ctx
	 */
	public HappenDataGetter(Context ctx){
		this.ctx=ctx;
	}

	public HappenDataGetter(String objectId, boolean isDivideChangeType,
			boolean isIncludeSettled, boolean isIncludeProduct) {
		try {
			init(objectId);
			this.initHasHappenData(objectId, isDivideChangeType,
					isIncludeSettled,isIncludeProduct);
		} catch (Exception e) {
			e.printStackTrace();
			SysUtil.abort();
		}
	}

	public HappenDataGetter(Context ctx, String objectId,
			boolean isDivideChangeType, boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException {
		try {
			this.ctx = ctx;
			init(objectId);
			this.initHasHappenData(objectId, isDivideChangeType,
					isIncludeSettled,isIncludeProduct);
		} catch (Exception e) {
//			ExceptionHandler.handle(e);
			//修改异常处理
			throw new BOSException(e);
		}
	}
	
	/**
	 * 
	 * @param ctx
	 * @param objectId
	 * @param isDivideChangeType
	 * @param isIncludeSettled
	 * @param isIncludeProduct
	 * @param isFromCostSplit
	 * @throws BOSException
	 */
	public HappenDataGetter(Context ctx, String objectId,
			boolean isDivideChangeType, boolean isIncludeSettled,boolean isIncludeProduct, boolean isFromCostSplit) throws BOSException {
		try {
			this.ctx = ctx;
			this.isFromCostSplit = isFromCostSplit;
			init(objectId);
			this.initHasHappenData(objectId, isDivideChangeType,
					isIncludeSettled,isIncludeProduct);
		} catch (Exception e) {
//			ExceptionHandler.handle(e);
			//修改异常处理
			throw new BOSException(e);
		}
	}

	protected void init(String objectId) throws Exception{
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		if(isCurProject(objectId)){
			builder.appendSql("select fcontrolUnitId from T_FDC_CurProject where fid=?");
			
		}else{
			builder.appendSql("select fcontrolUnitId from T_ORG_BaseUnit where fid=?");
		}
		builder.addParam(objectId);
		IRowSet rowSet=builder.executeQuery();
		if(rowSet.size()>0){
			rowSet.next();
			this.cuId=rowSet.getString("fcontrolUnitId");
		}
	}
	
	protected void initHasHappenData(String objectId, boolean isDivideChangeType,
			boolean isIncludeSettled, boolean isIncludeProduct)
			throws BOSException, SQLException, EASBizException {
		hasHappenMap = new HashMap();
		if(isFromCostSplit){
			// 取得合同拆分数据
			conSplitMap = getContractSplitData(objectId, isIncludeSettled, isIncludeProduct);
			// 取得变更拆分数据
			changeSplitMap = getChangeSplitData(objectId, isDivideChangeType, isIncludeSettled, isIncludeProduct);
			// 取得结算拆分数据
			settleSplitMap = getSettleSplitData(objectId, isIncludeProduct);
			// 取得无文本拆分数据
			noTextSplitMap = getNoTextSplitData(objectId, isIncludeProduct);
			// 取得付款拆分数据
			paySplitMap = getPaySplitData(objectId, isIncludeProduct);
			// 取得已付款拆分数据
			paidSplitMap = getPaidSplitData(objectId, isIncludeProduct);
			// 部分结算：合同可进行多次结算
			if (isUsePartSettle()) {
				// 取得部分结算拆分数据
				this.partSettleSplitMap = getPartSettleSplitData(objectId);
			}
		} else {
			// 取得合同拆分数据
			conSplitMap = getContractSplitData2(objectId, isIncludeSettled, isIncludeProduct);
			// 取得变更拆分数据
			changeSplitMap = getChangeSplitData(objectId, isDivideChangeType, isIncludeSettled, isIncludeProduct);
			// 取得结算拆分数据
			settleSplitMap = getSettleSplitData(objectId, isIncludeProduct);
			// 取得无文本拆分数据
			noTextSplitMap = getNoTextSplitData2(objectId, isIncludeProduct);
			// 取得付款拆分数据
			paySplitMap = getPaySplitData2(objectId, isIncludeProduct);
			// 取得已付款拆分数据
			paidSplitMap = getPaidSplitData2(objectId, isIncludeProduct);
			// 部分结算：合同可进行多次结算
			if (isUsePartSettle()) {
				// 取得部分结算拆分数据
				this.partSettleSplitMap = getPartSettleSplitData2(objectId);
			}
		}
		List acctIds = new ArrayList();
		acctIds.addAll(conSplitMap.keySet());
		acctIds.addAll(changeSplitMap.keySet());
		acctIds.addAll(settleSplitMap.keySet());
		acctIds.addAll(noTextSplitMap.keySet());
		for (int i = 0; i < acctIds.size(); i++) {
			HappenDataInfo info = new HappenDataInfo();
			String acctId = (String) acctIds.get(i);
			info.add((HappenDataInfo) conSplitMap.get(acctId));
			info.add((HappenDataInfo) changeSplitMap.get(acctId));
			info.add((HappenDataInfo) settleSplitMap.get(acctId));
			info.add((HappenDataInfo) noTextSplitMap.get(acctId));
			this.hasHappenMap.put(acctId, info);
		}
	}
	
	/**
	 * 取科目的已发生成本
	 * @param acctId
	 * @return
	 */
	public HappenDataInfo getHappenInfo(String acctId) {
		HappenDataInfo happen = (HappenDataInfo) this.hasHappenMap.get(acctId);
		return happen;
	}

	public HappenDataInfo getHasPayInfo(String acctId) {
		HappenDataInfo hasPay = (HappenDataInfo) this.paySplitMap.get(acctId);
		return hasPay;
	}
	public HappenDataInfo getPartSettleInfo(String acctId) {
		if(this.partSettleSplitMap==null){
			return null;
		}
		HappenDataInfo partSettle = (HappenDataInfo) this.partSettleSplitMap.get(acctId);
		return partSettle;
	}

	/**
	 * 描述：取得合同拆分数据
	 * 
	 * @param objectId
	 * @param isIncludeSettled
	 * @param isIncludeProduct
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 */
	public Map getContractSplitData(String objectId, boolean isIncludeSettled,boolean isIncludeProduct)
			throws BOSException, SQLException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		Map conSplitMap = new HashMap();
		/**
		 * 全项目动态成本表，如果合同已经结算了但是未拆分，成本科目的目前已发生显示为空。
		 * 而实际业务需要如果合同结算但是未作结算拆分，目前已发生应该取合同加变更的拆分
		 */
		String select = "select  a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FHasSettled ,sum(a.amount) amount from ( ";
		select += "select FCostAccountId,acc.FLongNumber,acc.FCurProject,acc.FFullOrgUnit,"
				+ "(case when con.fhassettled=1 and  exists(select 1 from T_CON_SettlementCostSplit where fcontractbillid = con.fid ) then 1 else 0 end) as FHasSettled,entry.FAmount amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentID=head.FId "
				+ "inner join T_CON_ContractBill con on head.FCostBillID=con.FId "
				+ "inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid  ";
		//		String where = "where FCostBillType='CONTRACTSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  entry.fobjectId=? ";
		String where = "where FCostBillType='CONTRACTSPLIT'  And head.FIsInvalid=0 And FIsProduct=0 and  entry.fobjectId=? ";
		if (!isIncludeSettled) {
			where += " and (con.FHasSettled=0 or con.FHasSettled is null) ";
		}
		String group = ") as a group by a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FHasSettled ";
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.CONTRACTSPLIT);
			info.setAmount(amount);
			if (isIncludeSettled) {
				info.setUserObjectId("has");
				info.setSettled(rs.getBoolean("FHasSettled"));
			}
			conSplitMap.put(info.getKey(), info);
			if(isIncludeProduct&&!info.isSettled()){
				conSplitMap.put(acctId, info);
			}
		}
		if (isIncludeProduct) {
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			//			String wherePro = "where FCostBillType='CONTRACTSPLIT' and head.FControlunitId=? And head.FIsInvalid=0 And  FIsProduct=1 and  entry.fobjectId=? ";
			String wherePro = "where FCostBillType='CONTRACTSPLIT'  And head.FIsInvalid=0 And  FIsProduct=1 and  entry.fobjectId=? ";
			wherePro += "and (con.FHasSettled=0 or con.FHasSettled is null) ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro + from + wherePro + groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (conSplitMap.containsKey(acctId)||conSplitMap.containsKey(acctId+0)) {
					HappenDataInfo info = (HappenDataInfo) conSplitMap
							.get(acctId);
					if(info==null){
						info = (HappenDataInfo) conSplitMap
						.get(acctId+0);
					}
					
					info.addProductAmount(proId, amount);
					
				}
			}
		}
		return conSplitMap;
	}
	
	/**
	 * 描述：取得合同拆分数据
	 * 
	 * @param objectId
	 * @param isIncludeSettled
	 * @param isIncludeProduct
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @modify by haiou_li
	 * @description: 从直接拆分表中获取的数据存在问题，需要重原始单据中取值
	 * 				详见bug R140804-0230
	 * @createDate 2014-9-5
	 */
	public Map getContractSplitData2(String objectId, boolean isIncludeSettled,boolean isIncludeProduct)
			throws BOSException, SQLException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		Map conSplitMap = new HashMap();
		/**
		 * 全项目动态成本表，如果合同已经结算了但是未拆分，成本科目的目前已发生显示为空。
		 * 而实际业务需要如果合同结算但是未作结算拆分，目前已发生应该取合同加变更的拆分
		 */
		String select = "select  a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FHasSettled ,sum(a.amount) amount from ( ";
		select += "select FCostAccountId,acc.FLongNumber,acc.FCurProject,acc.FFullOrgUnit,"
				+ "(case when con.fhassettled=1 and  exists(select 1 from T_CON_SettlementCostSplit where fcontractbillid = con.fid ) then 1 else 0 end) as FHasSettled,entry.FAmount amount ";
		String from = "from T_CON_ContractCostSplitEntry entry "
				+ "inner join T_CON_ContractCostSplit head on entry.FParentID=head.FId "
				+ "inner join T_CON_ContractBill con on head.Fcontractbillid=con.FId "
				+ "inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid  ";
		
		//		String where = "where FCostBillType='CONTRACTSPLIT' and  head.FControlunitId=? And head.FIsInvalid=0 And FIsProduct=0 and  entry.fobjectId=? ";
		String noProdSQL = getNotProdSQL();
		String where = "where head.FIsInvalid=0 " + noProdSQL + " and  acc.fcurproject=? ";
		if (!isIncludeSettled) {
			where += " and (con.FHasSettled=0 or con.FHasSettled is null) ";
		}
		String group = ") as a group by a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FHasSettled ";
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.CONTRACTSPLIT);
			info.setAmount(amount);
			if (isIncludeSettled) {
				info.setUserObjectId("has");
				info.setSettled(rs.getBoolean("FHasSettled"));
			}
			conSplitMap.put(info.getKey(), info);
			if(isIncludeProduct&&!info.isSettled()){
				conSplitMap.put(acctId, info);
			}
		}
		if (isIncludeProduct) {
			String selectPro = "select FCostAccountId,FProductId,sum(entry.amount) proAmount ";
			//			String wherePro = "where FCostBillType='CONTRACTSPLIT' and head.FControlunitId=? And head.FIsInvalid=0 And  FIsProduct=1 and  entry.fobjectId=? ";
			String wherePro = "where head.FIsInvalid=0  and  head.fcurprojectid=? ";
			wherePro += "and (con.FHasSettled=0 or con.FHasSettled is null) and (entry.fisleaf=1 and fproductid is not null) ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro + from + wherePro + groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (conSplitMap.containsKey(acctId)||conSplitMap.containsKey(acctId+0)) {
					HappenDataInfo info = (HappenDataInfo) conSplitMap
							.get(acctId);
					if(info==null){
						info = (HappenDataInfo) conSplitMap
						.get(acctId+0);
					}
					
					info.addProductAmount(proId, amount);
					
				}
			}
		}
		return conSplitMap;
	}
	
	public String getNotProdSQL(){
		String result = "and ((entry.fisleaf=1 and fproductid is null) " 
					+ " or (entry.fisleaf=0 and fproductid is null and fsplitType='PRODSPLIT')" 
					+ " or(entry.fisleaf=1 and fproductid is not null and (fsplitType<>'PRODSPLIT' or fsplittype is null)))";
		return result;
	}
	
	/**
	 * @description 变更金额求和
	 * @author 陈伟
	 * @createDate 2011-8-24
	 * @param isDivideChangeType
	 * @param isIncludeSettled
	 * @param objectId
	 * @return
	 * @version EAS7.0
	 * @param hasSettled
	 * @param lognumber
	 * @throws BOSException
	 * @throws SQLException
	 * @see
	 */
	private BigDecimal getChangeSplitDataAmount(boolean isDivideChangeType,
			boolean isIncludeSettled, String objectId, String curProject, String org,
			String hasSettled, String lognumber) throws BOSException, SQLException {
		BigDecimal amount = FDCHelper.ZERO;
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String select = null;

		select = "select  sum(entry.FAmount) amount ";

		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
				+ "inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID "
				+ "inner join T_CON_ContractBill con on change.FContractBillID=con.FID  ";
		//		String where = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 And  entry.fobjectid=? ";
		String where = "where FCostBillType='CNTRCHANGESPLIT'  and head.FIsInvalid=0 And  entry.fobjectid=? ";

		if (!isIncludeSettled) {
			where += "and (con.FHasSettled=0 or con.FHasSettled is null) ";
		}
		String inSql = " and con.fhasSettled = ? and  entry.FCostAccountId in (select fid from t_fdc_costaccount where  flongnumber  like ? ";
		if (curProject != null) {
			inSql += " and  FCurProject = ? ";
		} else {
			inSql += " and  FCurProject is null ";
		}
		if (org != null) {
			inSql += " and  FFullOrgUnit = ? ";
		} else {
			inSql += " and  FFullOrgUnit is null ";
		}
		inSql += " ) ";
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(inSql);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		builder.addParam(hasSettled);
		builder.addParam(lognumber + "!%");
		if (curProject != null) {
			builder.addParam(curProject);
		}
		if (org != null) {
			builder.addParam(org);
		} 
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {

			BigDecimal value = rs.getBigDecimal("amount");
			if (value == null) {
				continue;
			}
		}
		return amount;

	}
	/**
	 * 描述：取得变更拆分数据
	 * 
	 * @param objectId
	 * @param isDivideChangeType
	 * @param isIncludeSettled
	 * @param isIncludeProduct
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 */
	public Map getChangeSplitData(String objectId, boolean isDivideChangeType,
			boolean isIncludeSettled,boolean isIncludeProduct) throws BOSException, SQLException {
		FDCSQLBuilder builder=new  FDCSQLBuilder(ctx);
		Map changeSplitMap = new HashMap();
		String sql = getChangeSplitDataSQL(isDivideChangeType,isIncludeSettled);
		builder.appendSql(sql);
//		builder.appendSql(from);
//		builder.appendSql(where);
//		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			BigDecimal value = getChangeSplitDataAmount(isDivideChangeType, isIncludeSettled,
					objectId, rs
					.getString("FCurProject"), rs.getString("FFullOrgUnit"), rs
					.getString("FHasSettled"), rs.getString("FLongNumber"));
			amount = FDCHelper.add(amount, value);
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.CNTRCHANGESPLIT);
			if (isDivideChangeType) {
				String changeTypeId = rs.getString("FChangeTypeId");
				info.setUserObjectId(changeTypeId);
			}
			info.setAmount(amount);
			info.setSettled(rs.getBoolean("FHasSettled"));
			changeSplitMap.put(info.getKey(), info);
			if(isDivideChangeType&&!info.isSettled()){
				//应该做金额的累加,因为会有不能的变更类型
				HappenDataInfo oldInfo=(HappenDataInfo)changeSplitMap.get(acctId);
				if(oldInfo!=null){
					HappenDataInfo newInfo=new HappenDataInfo();
					newInfo.setUserObjectId(oldInfo.getUserObjectId());
					newInfo.setSettled(oldInfo.isSettled);
					newInfo.setAcctId(oldInfo.getAcctId());
					newInfo.setBillType(oldInfo.getBillType());
					newInfo.setAmount(oldInfo.getAmount());
					newInfo.setProductAmountMap(oldInfo.getProductAmountMap());
					newInfo.addAmount(info.getAmount());
					oldInfo=newInfo;
				}else{
					oldInfo=info;
				}
				changeSplitMap.put(acctId, oldInfo);
			}
		}

		if (isIncludeProduct) {
//			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
//			//			String wherePro = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And FIsProduct=1 and entry.fobjectId=? ";
//			String wherePro = "where FCostBillType='CNTRCHANGESPLIT'  and head.FIsInvalid=0 And FIsProduct=1 and entry.fobjectId=? ";
//			wherePro += "and  (con.FHasSettled=0 or con.FHasSettled is null) ";
//			String groupPro = "group by FCostAccountId,FProductId ";
			
			String sql2 = getChangeSplitProductSQL();
			builder.clear();
			builder.appendSql(sql2);
//			builder.appendSql(selectPro);
//			builder.appendSql(from);
//			builder.appendSql(wherePro);
//			builder.appendSql(groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				//超级陷阱把我折腾死了 by sxhong
				if (changeSplitMap.containsKey(acctId)||changeSplitMap.containsKey(acctId+0)) {
					HappenDataInfo info = (HappenDataInfo) changeSplitMap
							.get(acctId);
					if(info==null){
						info = (HappenDataInfo) changeSplitMap
						.get(acctId+0);
					}
					
					info.addProductAmount(proId, amount);
				}
			}
		}
		return changeSplitMap;
	}
	
	/**
	 * @author RD_haiou_li
	 * @date:2014-09-05
	 * @description:修改获取变更拆分数据的来源，从变更拆分单直接取数据，不从成本拆分表取数，
	 * fix bug 详见bug R140804-0230
	 * @return
	 */
	private String getChangeSplitDataSQL(boolean isDivideChangeType, boolean isIncludeSettled) {
		StringBuffer sb = new StringBuffer();
		String select =null;
		/**
		 * 全项目动态成本表，如果合同已经结算了但是未拆分，成本科目的目前已发生显示为空。
		 * 而实际业务需要如果合同结算但是未作结算拆分，目前已发生应该取合同加变更的拆分
		 */
		if (isDivideChangeType) {
			select = "select a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FChangeTypeId,a.FHasSettled,sum(a.amount) amount from ( ";
			select += "select FCostAccountId,acc.FLongNumber,acc.FCurProject,acc.FFullOrgUnit,FChangeTypeId,(case when con.fhassettled=1 and  exists(select 1 from T_CON_SettlementCostSplit where fcontractbillid = con.fid ) then 1 else 0 end) as FHasSettled,entry.FAmount amount  ";
		} else {
			select = "select a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FHasSettled,sum(a.amount) amount from ( ";
			select += "select FCostAccountId,acc.FLongNumber,acc.FCurProject,acc.FFullOrgUnit,(case when con.fhassettled=1 and  exists(select 1 from T_CON_SettlementCostSplit where fcontractbillid = con.fid ) then 1 else 0 end) as FHasSettled,entry.FAmount amount ";
		}
		String from  = getChangeSplitFromTable();
		//		String where = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 And  entry.fobjectid=? ";
		String where = "";
		if(isFromCostSplit){
			where = "where FCostBillType='CNTRCHANGESPLIT'  and head.FIsInvalid=0 And  entry.fobjectid=? ";
		} else {
			String noProdSQL = getNotProdSQL();
			where = "where head.FIsInvalid=0 "+ noProdSQL+" and  acc.fcurproject=? ";
		}

		if (!isIncludeSettled) {
			where += "and (con.FHasSettled=0 or con.FHasSettled is null) ";
		}
		String group = null;
		if (isDivideChangeType) {
			group = ") a group by a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FChangeTypeId,a.FHasSettled ";
		} else {
			group = ")  a group by a.FCostAccountId,a.FLongNumber,a.FCurProject,a.FFullOrgUnit,a.FHasSettled ";
		}
		
		return sb.append(select).append(from).append(where).append(group).toString();
	}
	
	/**
	 * 获取变更拆分的数据库查询类型
	 * @return
	 */
	private String getChangeSplitFromTable() {
		String from;
		if(isFromCostSplit){
			from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
					+ "inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID "
					+ "inner join T_CON_ContractBill con on change.FContractBillID=con.FID  "
					+ "inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid  ";
		} else {
			from = "from T_CON_ConChangeSplitEntry entry "
				+ "inner join T_CON_ConChangeSplit head on entry.FParentId=head.FId "
				+ "inner join T_CON_ContractChangeBill change on head.FContractChangeID=change.FID "
				+ "inner join T_CON_ContractBill con on change.FContractBillID=con.FID  "
				+ "inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid  ";
		}
		return from;
	}
	/**
	 * 获取变更拆分产品拆分的数据
	 * modify by lihaiou,20-14-09-05,fix bug 	 * fix bug 详见bug R140804-0230

	 * @return
	 */
	private String getChangeSplitProductSQL(){
		StringBuffer sb = new StringBuffer();
		String selectPro = "";
		if(isFromCostSplit){
			selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
		} else {
			selectPro = "select FCostAccountId,FProductId,sum(entry.FAmount) proAmount ";
		}
		//			String wherePro = "where FCostBillType='CNTRCHANGESPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And FIsProduct=1 and entry.fobjectId=? ";
		String wherePro ="";
		if(isFromCostSplit){
			wherePro = "where FCostBillType='CNTRCHANGESPLIT'  and head.FIsInvalid=0 And FIsProduct=1 and entry.fobjectId=? ";
		} else {
			wherePro = "where head.FIsInvalid=0 and (entry.fisleaf=1 and fproductid is not null) and  acc.fcurproject=? ";
		}
		wherePro += "and  (con.FHasSettled=0 or con.FHasSettled is null) ";
		String groupPro = "group by FCostAccountId,FProductId ";
		String from = getChangeSplitFromTable();
		return sb.append(selectPro).append(from).append(wherePro).append(groupPro).toString();
	}
	/**
	 * @description 结算求和
	 * @author 陈伟
	 * @createDate 2011-8-24
	 * @param objectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @version EAS7.0
	 * @param curProject
	 * @param org
	 * @param hasSettled
	 * @param lognumber
	 * @see
	 */
	private BigDecimal getSettleSplitDataAmount(String objectId, String curProject, String org,
			 String lognumber)
			throws BOSException, SQLException {
		BigDecimal amount = FDCHelper.ZERO;
		String select = "select sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
				+ "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ";
		//		String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? "
		String where = "where FCostBillType='SETTLEMENTSPLIT'  and head.FIsInvalid=0 And  entry.fobjectid=? "
				+
				// 已经结算，取最终结算数据；没有结算，取未结算数据
				"and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) " +
				")";

		String inSql = "   and  entry.FCostAccountId in (select fid from t_fdc_costaccount where  flongnumber  like ? ";
		if (curProject != null) {
			inSql += " and  FCurProject = ? ";
		} else {
			inSql += " and  FCurProject is null ";
		}
		if (org != null) {
			inSql += " and  FFullOrgUnit = ? ";
		} else {
			inSql += " and  FFullOrgUnit is null ";
		}
		inSql += " ) ";
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(inSql);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		builder.addParam(lognumber + "!%");
		if (curProject != null) {
			builder.addParam(curProject);
		}
		if (org != null) {
			builder.addParam(org);
		} 
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {

			BigDecimal value = rs.getBigDecimal("amount");
			if (value == null) {
				amount = FDCHelper.add(amount, value);
			}
		}
		return amount;

	}
	/**
	 * 描述：取得结算拆分数据
	 * 
	 * @param objectId
	 * @param isIncludeProduct
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 * @date:2014-09-05,fix bug
	 * 
	 */
	public Map getSettleSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
			SQLException {
		//结算拆分的时候合同肯定是结算了的,可以不与合同做关联,  这个地方后来改了：结算单不是最终结算，合同就没有结算 JinXP
		Map settleSplitMap = new HashMap();
		String sql = getSettleSplitDataSQL(isIncludeProduct);
		
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(sql);
//		builder.appendSql(select);
//		builder.appendSql(from);
//		builder.appendSql(where);
//		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			 BigDecimal value = getSettleSplitDataAmount(objectId, rs.getString("FCurProject"), rs
					.getString("FFullOrgUnit"), rs.getString("FLongNumber"));
			amount = FDCHelper.add(amount, value);
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);
			info.setAmount(amount);
//			info.setSettled(rs.getBoolean("FHasSettled"));
			info.setSettled(true);
			settleSplitMap.put(info.getKey(), info);
		}
		if(isIncludeProduct){
			sql = getSettlementCostSplitProductSQL();
			builder.clear();
			builder.appendSql(sql);
			//builder.appendSql(selectPro);
			//builder.appendSql(from);
			//builder.appendSql(wherePro);
			//builder.appendSql(groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (settleSplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) settleSplitMap
							.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return settleSplitMap;
	}

	/**
	 * modify by lihaiou,2014-09-05,改变拆分数据取数来源
	 * @param isIncludeProduct
	 * @return
	 */
	private String getSettleSplitDataSQL(boolean isIncludeProduct){
		StringBuffer sb = new StringBuffer();
		String select = "select FCostAccountId,acc.FLongNumber,acc.FCurProject,acc.FFullOrgUnit,sum(entry.FAmount) amount ";
		String from = getSettleSplitDataTable();
		
		//		String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? " +
		
		/* modified by zhaoqin for R131227-0045/R140106-0345 on 2014/01/08 start */
		String where = "";
		if(isFromCostSplit){
			where = "where FCostBillType='SETTLEMENTSPLIT'  and head.FIsInvalid=0 And  entry.fobjectid=? "; // +
		} else {
			String noProdSQL = getNotProdSQL();
			where = "where head.FIsInvalid=0 " + noProdSQL+" and  acc.fcurproject=? ";
		}
				//已经结算，取最终结算数据；没有结算，取未结算数据
				//"and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) " +
//				"or (settle.FIsSettled=0 and settle.FIsFinalSettle=0) " +	//不能这样取值,否则对于已实现产值会翻倍,因为未结算部分的数据从其它地方进行取值　 by sxhong 2008-11-30 21:08:39
				//")";
		/* modified by zhaoqin for R131227-0045/R140106-0345 on 2014/01/08 end */
		
		String group = " group by FCostAccountId,acc.FLongNumber,acc.FCurProject,acc.FFullOrgUnit ";
		
		return sb.append(select).append(from).append(where).append(group).toString();
	}
	
	private String getSettlementCostSplitProductSQL(){
		StringBuffer sb = new StringBuffer();
		// 取产品发生
		/*String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
		String wherePro = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 And FIsProduct=1 and  entry.fobjectid=? ";
		wherePro += "and con.FHasSettled=1 ";
		String groupPro = "group by FCostAccountId,FProductId ";*/
		String selectPro = "";
		if(isFromCostSplit){
			selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
		} else {
			selectPro = "select FCostAccountId,FProductId,sum(entry.FAmount) proAmount ";
		}
		//			String wherePro = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolunitid=? and head.FIsInvalid=0 " +
		
		/* modified by zhaoqin for R131227-0045/R140106-0345 on 2014/01/08 start */
		String wherePro = "";
		if(isFromCostSplit){
			wherePro = "where FCostBillType='SETTLEMENTSPLIT'  and head.FIsInvalid=0 " +
					"And FIsProduct=1 and  entry.fobjectid=? "; //+
		} else {
			wherePro = "where head.FIsInvalid=0 and (entry.fisleaf=1 and fproductid is not null) and  acc.fcurproject=? ";
		}
				//"and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) " +
//不能这样取值,否则对于已实现产值会翻倍,因为未结算部分的数据从其它地方进行取值　 by sxhong 2008-11-30 21:08:39
//				"or (settle.FIsSettled=0 and settle.FIsFinalSettle=0) " +
				//")";
		/* modified by zhaoqin for R131227-0045/R140106-0345 on 2014/01/08 end */
		String fromPro = getSettleSplitDataTable();
		String groupPro = "group by FCostAccountId,FProductId ";
		
		return sb.append(selectPro).append(fromPro).append(wherePro).append(groupPro).toString();
	}
	/**
	 * 
	 * @return
	 */
	private String getSettleSplitDataTable() {
		String from = "";
		if(isFromCostSplit){
			from = "from T_AIM_CostSplitEntry entry "
					+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
					+ "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID "
					+ "inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid " 
			
					/* modified by zhaoqin for R140403-0023 on 2014/04/16 */
					// "目前已发生"不应包含 不是最终结算的 结算拆分数据
					+ "inner join t_con_contractbill con on (con.fid = settle.fcontractbillid and con.fhassettled = 1) ";
		} else {
			from = "from T_CON_SettlementCostSplitentry entry "
				+ "inner join T_CON_SettlementCostSplit head on entry.FParentId=head.FId "
				+ "inner join T_CON_ContractSettlementBill settle on head.FSettlementBillID=settle.FID "
				+ "inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid " 
		
				/* modified by zhaoqin for R140403-0023 on 2014/04/16 */
				// "目前已发生"不应包含 不是最终结算的 结算拆分数据
				+ "inner join t_con_contractbill con on (con.fid = settle.fcontractbillid and con.fhassettled = 1) ";
		}
		return from;
	}
	
	/**
	 * 描述：取得部分结算拆分数据
	 * 
	 * @param objectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 */
	public Map getPartSettleSplitData(String objectId) throws BOSException, SQLException {
		// TODO 结算拆分的时候合同肯定是结算了的,可以不与合同做关联
		//审批后才能做拆分
		Map partSettleSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry " + "inner join T_AIM_CostSplit head on entry.FParentId=head.FId "
		 + "inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID ";
		//		String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? and settle.FIsSettled=0 and settle.FIsFinalSettle=0 ";//部分结算
		String where = "where FCostBillType='SETTLEMENTSPLIT'  and head.FIsInvalid=0 And  entry.fobjectid=? and settle.FIsSettled=0 and settle.FIsFinalSettle=0 ";//部分结算
		String group = "group by FCostAccountId ";

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);
			info.setAmount(amount);
			// info.setSettled(rs.getBoolean("FHasSettled"));
			info.setSettled(true);
			partSettleSplitMap.put(info.getKey(), info);
		}
		return partSettleSplitMap;
	}
	
	/**
	 * 描述：取得部分结算拆分数据
	 * 
	 * @param objectId
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 */
	public Map getPartSettleSplitData2(String objectId) throws BOSException, SQLException {
		// TODO 结算拆分的时候合同肯定是结算了的,可以不与合同做关联
		//审批后才能做拆分
		Map partSettleSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from  T_CON_SettlementCostSplit head " + "inner join  T_CON_SettlementCostSplitentry entry on entry.FParentId=head.FId "
		 + "inner join T_CON_ContractSettlementBill settle on head.FSettlementBillID=settle.FID "
		 + " inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid ";
		//		String where = "where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And  entry.fobjectid=? and settle.FIsSettled=0 and settle.FIsFinalSettle=0 ";//部分结算
		String where = "where  head.FIsInvalid=0 "+getNotProdSQL() +" and acc.fcurproject=? and settle.FIsSettled=0 and settle.FIsFinalSettle=0 ";//部分结算
		String group = "group by FCostAccountId ";

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId); 
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.SETTLEMENTSPLIT);
			info.setAmount(amount);
			// info.setSettled(rs.getBoolean("FHasSettled"));
			info.setSettled(true);
			partSettleSplitMap.put(info.getKey(), info);
		}
		return partSettleSplitMap;
	}
	/**
	 * 描述：取得无文本拆分数据
	 * 
	 * @param objectId
	 * @param isIncludeProduct
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 */
	public Map getNoTextSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
			SQLException {
		Map noTextSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
//				+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		//		String where = "where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 and entry.fobjectid=? ";
		String where = "where FCostBillType='NOTEXTCONSPLIT'  and head.FIsInvalid=0 and entry.fobjectid=? ";
		String group = "group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.NOTEXTCONSPLIT);
			info.setAmount(amount);
			noTextSplitMap.put(info.getKey(), info);
		}
		if(isIncludeProduct){
			// 取产品发生
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount ";
			//			String wherePro = " where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? And FIsProduct=1 and head.FIsInvalid=0 and entry.fobjectId=? ";
			String wherePro = " where FCostBillType='NOTEXTCONSPLIT'  And FIsProduct=1 and head.FIsInvalid=0 and entry.fobjectId=? ";
			String groupPro = " group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (noTextSplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) noTextSplitMap
							.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return noTextSplitMap;
	}
	
	
	/**
	 * 描述：取得无文本拆分数据
	 * 
	 * @param objectId
	 * @param isIncludeProduct
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 * modify by lihaiou,2014-09-05，先只讲方法复制，待验证通过之后再重构
	 */
	public Map getNoTextSplitData2(String objectId,boolean isIncludeProduct) throws BOSException,
			SQLException {
		Map noTextSplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FAmount) amount ";
		String from = " from T_FNC_PaymentSplitEntry entry  \n"
		+" inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId \n"
		+" inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid " 
		+" inner join T_CAS_PaymentBill payBill on head.FPaymentBillID = payBill.FID "
		+" inner join T_CON_ContractWithoutText wt on payBill.FContractBillID = wt.fid " ;
//				+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		//		String where = "where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 and entry.fobjectid=? ";
		String noProdSQL = getNotProdSQL();
		String where = " where  entry.FIsLeaf = 1 and head.fstate<>'9INVALID' and acc.fcurproject=? ";
		String group = " group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.NOTEXTCONSPLIT);
			info.setAmount(amount);
			noTextSplitMap.put(info.getKey(), info);
		}
		if(isIncludeProduct){
			// 取产品发生
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FAmount) proAmount ";
			//			String wherePro = " where FCostBillType='NOTEXTCONSPLIT' and head.fcontrolUnitId=? And FIsProduct=1 and head.FIsInvalid=0 and entry.fobjectId=? ";
			String wherePro = " where FProductid is not null and entry.fisleaf=1 and head.FIsInvalid=0 and head.fcurprojectid=? ";
			String groupPro = " group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (noTextSplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) noTextSplitMap
							.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return noTextSplitMap;
	}
	
	/**
	 * 
	 * 描述：根据工程项目ID，获取工程项目所在的（实体财务）组织
	 * @param objectId 工程项目或组织Id
	 * @Author：owen_wen
	 * @CreateTime：2011-12-30
	 * @see 请CurProjectControllerBean._addnew(Context ctx, IObjectValue model)的projectInfo.setFullOrgUnit(ContextUtil.getCurrentFIUnit(ctx).castToFullOrgUnitInfo())

	 */
	private String getFIOrgUnitId(String objectId) throws BOSException, SQLException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if (isCurProject(objectId)) {
			builder.appendSql("select fFullorgunit from T_FDC_CurProject where fid=?");
		} else {
			builder.appendSql("select fcontrolUnitId from T_ORG_BaseUnit where fid=?");
		}
		builder.addParam(objectId);
		IRowSet rowSet = builder.executeQuery();
		if (rowSet.size() > 0) {
			rowSet.next();
			return rowSet.getString("fFullorgunit");
		} else {
			return this.cuId;
		}
	}

	/**
	 * 取得付款拆分数据
	 * 
	 * 描述："已实现成本"取数说明：
	 * <p>
	 * <li>发票模式（FDC023_SIMPLEINVOICE或FDC318_INVOICEMGR参数为是）下，“已实现成本”取合同付款拆分和无文本合同付款拆分上“归属发票金额”。
	 * <li>工程量与付款分离流程FDC317_SEPARATEFROMPAYMENT，“已实现成本”取工程量拆分和无文本合同付款拆分上“归属成本金额”
	 * <li>工程量与付款合并流程FDC317_SEPARATEFROMPAYMENT，“已实现成本”取合同付款拆分和无文本合同付款拆分上“归属成本金额”。
	 * <li>发票金额计入成本金额模式时，不考虑工程量与付款分离情况。
	 * 
	 * @param objectId
	 *            工程项目或组织Id
	 * @param isIncludeProduct
	 *            是否包含产品
	 * @throws EASBizException
	 */
	public Map getPaySplitData(String objectId, boolean isIncludeProduct)
			throws BOSException,
			SQLException, EASBizException {
		Map paySplitMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount  \n");
		builder.appendSql("from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId \n");
		//		builder.appendSql("where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=?  \n");
		
		
		
		/**
		 * 周鹏需求，已实现成本不包含付款类型为预付款的拆分,当工程量与付款分离流程为否时，去掉预付款拆分
		 */
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("fullorgunit.id");
		CurProjectInfo curProjectInfo = null;
		if (ctx != null) {
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(objectId), sic);
		} else {
			curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objectId), sic);
		}
		boolean isSeparate = false;
		if (curProjectInfo.getFullOrgUnit() != null) {
			isSeparate = FDCUtils.getBooleanValue4FDCParamByKey(ctx, curProjectInfo.getFullOrgUnit().getId().toString(),
					FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		}
		if (!isSeparate) {
			builder.appendSql("inner join T_FNC_PaymentSplit split on split.fid= head.fsplitbillid and split.fisworkloadbill=0 \n ");
			builder.appendSql("inner join T_CAS_PaymentBill pay on pay.fid=split.fpaymentbillid \n");
			builder.appendSql(" inner join t_fdc_paymenttype  type on type.fid = pay.ffdcpaytypeid \n");
			builder.appendSql("where head.FCostBillType='" + CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE + "' \n");
			builder.appendSql("and head.FIsInvalid=0 And entry.fobjectid=?  \n");
			if (ctx != null && ctx.getLocale() != null) {
				if ("l2".equals(ctx.getLocale().toString())) {
					builder.appendSql("and type.fname_l2 !='预付款' \n ");
				} else {
					builder.appendSql("and type.fname_l3 !='預付款' \n ");
				}
			} else {
				builder.appendSql("and type.fname_l2 !='预付款' \n ");
			}
		} else {
			builder.appendSql("where head.FCostBillType='" + CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE + "' \n");
			builder.appendSql("and head.FIsInvalid=0 And entry.fobjectid=?  \n");
		}
		
		
		builder.appendSql("group by FCostAccountId  \n");
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
			info.setAmount(amount);
			paySplitMap.put(info.getKey(), info);
		}
		// 取产品发生
		if(isIncludeProduct){
			builder.clear();
			builder.appendSql("select FCostAccountId,FProductId,sum(entry.FProdAmount) proAmount from T_AIM_CostSplitEntry entry \n ");
			builder.appendSql(" inner join T_AIM_CostSplit head on entry.FParentId=head.FId \n");
			//			builder.appendSql("where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0 and head.fcontrolUnitid=? and  entry.FIsProduct=1 and entry.fobjectId=? \n ");
			builder
					.appendSql("where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0  and  entry.FIsProduct=1 and entry.fobjectId=? \n ");
			builder.appendSql("group by FCostAccountId,FProductId \n ");
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (paySplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) paySplitMap.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		
		return paySplitMap;
	}
	
	
	/**
	 * 取得付款拆分数据
	 * 
	 * 描述："已实现成本"取数说明：
	 * <p>
	 * <li>发票模式（FDC023_SIMPLEINVOICE或FDC318_INVOICEMGR参数为是）下，“已实现成本”取合同付款拆分和无文本合同付款拆分上“归属发票金额”。
	 * <li>工程量与付款分离流程FDC317_SEPARATEFROMPAYMENT，“已实现成本”取工程量拆分和无文本合同付款拆分上“归属成本金额”
	 * <li>工程量与付款合并流程FDC317_SEPARATEFROMPAYMENT，“已实现成本”取合同付款拆分和无文本合同付款拆分上“归属成本金额”。
	 * <li>发票金额计入成本金额模式时，不考虑工程量与付款分离情况。
	 * 
	 * @param objectId
	 *            工程项目或组织Id
	 * @param isIncludeProduct
	 *            是否包含产品
	 * @throws EASBizException
	 */
	public Map getPaySplitData2(String objectId, boolean isIncludeProduct)
			throws BOSException,
			SQLException, EASBizException {
		Map paySplitMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount  \n");
		builder.appendSql("from T_FNC_PaymentSplitEntry entry  \n");
		builder.appendSql("inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId \n");
		builder.appendSql("inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid   \n");
		//		builder.appendSql("where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=?  \n");
		
		
		
		/**
		 * 周鹏需求，已实现成本不包含付款类型为预付款的拆分,当工程量与付款分离流程为否时，去掉预付款拆分
		 */
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("fullorgunit.id");
		CurProjectInfo curProjectInfo = null;
		if (ctx != null) {
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(objectId), sic);
		} else {
			curProjectInfo = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objectId), sic);
		}
		boolean isSeparate = false;
		if (curProjectInfo.getFullOrgUnit() != null) {
			isSeparate = FDCUtils.getBooleanValue4FDCParamByKey(ctx, curProjectInfo.getFullOrgUnit().getId().toString(),
					FDCConstants.FDC_PARAM_SEPARATEFROMPAYMENT);
		}
		if (!isSeparate) {
			//builder.appendSql("inner join T_FNC_PaymentSplit split on split.fid= head.fsplitbillid and split.fisworkloadbill=0 \n ");
			builder.appendSql("inner join T_CAS_PaymentBill pay on pay.fid=head.fpaymentbillid \n");
			builder.appendSql(" inner join t_fdc_paymenttype  type on type.fid = pay.ffdcpaytypeid \n");
			builder.appendSql("where 1=1 "+getNotProdSQL()+" \n");
			builder.appendSql("and head.FIsInvalid=0 And acc.fcurproject=?  \n");
			if (ctx != null && ctx.getLocale() != null) {
				if ("l2".equals(ctx.getLocale().toString())) {
					builder.appendSql("and type.fname_l2 !='预付款' \n ");
				} else {
					builder.appendSql("and type.fname_l3 !='預付款' \n ");
				}
			} else {
				builder.appendSql("and type.fname_l2 !='预付款' \n ");
			}
		} else {
			builder.appendSql("where 1=1 "+getNotProdSQL()+" \n");
			builder.appendSql("and head.FIsInvalid=0 And acc.fcurproject=?  \n");
		}
		
		
		builder.appendSql("group by FCostAccountId  \n");
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
			info.setAmount(amount);
			paySplitMap.put(info.getKey(), info);
		}
		// 取产品发生
		if(isIncludeProduct){
			builder.clear();
			builder.appendSql("select FCostAccountId,FProductId,sum(entry.FAmount) proAmount from T_FNC_PaymentSplitEntry entry \n ");
			builder.appendSql(" inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId \n");
			builder.appendSql("inner join t_fdc_costaccount acc on entry.FCostAccountId = acc.fid   \n");
			//			builder.appendSql("where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0 and head.fcontrolUnitid=? and  entry.FIsProduct=1 and entry.fobjectId=? \n ");
			builder
					.appendSql("where fproductid is not null and entry.fisleaf=1 and head.FIsInvalid=0  and acc.fcurproject=? \n ");
			builder.appendSql("group by FCostAccountId,FProductId \n ");
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (paySplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) paySplitMap.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		
		return paySplitMap;
	}
	
	
	/**
	 * 描述：取得已付款拆分数据
	 * 
	 * 描述：已付款
	 * 
	 * @param objectId
	 *            工程项目或组织Id
	 * @param isIncludeProduct
	 *            是否包含产品
	 */
	public Map getPaidSplitData(String objectId,boolean isIncludeProduct) throws BOSException,
	SQLException {
		Map paySplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FPaidAmount) amount ";
		String from = "from T_AIM_CostSplitEntry entry "
				+ "inner join T_AIM_CostSplit head on entry.FParentId=head.FId ";
		//		+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		//		String where = "where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=? ";
		String where = "where FCostBillType='PAYMENTSPLIT'  and head.FIsInvalid=0 And entry.fobjectid=? ";
		String group = "group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
			info.setAmount(amount);
			paySplitMap.put(info.getKey(), info);
		}
		// 取产品发生
		if(isIncludeProduct){
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdPaidAmount) proAmount ";
			//			String wherePro = "where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0 and head.fcontrolUnitid=? and  entry.FIsProduct=1 and entry.fobjectId=? ";
			String wherePro = "where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0  and  entry.FIsProduct=1 and entry.fobjectId=? ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (paySplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) paySplitMap.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return paySplitMap;
	}
	
	
	/**
	 * 描述：取得已付款拆分数据
	 * 
	 * 描述：已付款
	 * 
	 * @param objectId
	 *            工程项目或组织Id
	 * @param isIncludeProduct
	 *            是否包含产品
	 * modify by lihaiou,2014-09-05，先只讲方法复制，待验证通过之后再重构
	 */
	public Map getPaidSplitData2(String objectId,boolean isIncludeProduct) throws BOSException,
	SQLException {
		Map paySplitMap = new HashMap();
		String select = "select FCostAccountId,sum(entry.FPayedAmt) amount ";
		String from = "from T_FNC_PaymentSplitEntry entry "
				+ "inner join T_FNC_PaymentSplit head on entry.FParentId=head.FId "
				+ "inner join T_FDC_CostAccount acct on entry.FCostAccountId=acct.FID ";
		//		String where = "where FCostBillType='PAYMENTSPLIT' and head.fcontrolUnitId=? and head.FIsInvalid=0 And entry.fobjectid=? ";
		
		String where = "where  head.FIsInvalid=0 "+getNotProdSQL()+" And acct.fcurproject=? ";
		String group = "group by FCostAccountId ";
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql(select);
		builder.appendSql(from);
		builder.appendSql(where);
		builder.appendSql(group);
		//		builder.addParam(this.cuId);
		builder.addParam(objectId);
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			String acctId = rs.getString("FCostAccountId");
			if (acctId == null) {
				continue;
			}
			BigDecimal amount = rs.getBigDecimal("amount");
			if (amount == null) {
				continue;
			}
			HappenDataInfo info = new HappenDataInfo();
			info.setAcctId(acctId);
			info.setBillType(CostSplitBillTypeEnum.PAYMENTSPLIT);
			info.setAmount(amount);
			paySplitMap.put(info.getKey(), info);
		}
		// 取产品发生
		if(isIncludeProduct){
			String selectPro = "select FCostAccountId,FProductId,sum(entry.FProdPaidAmount) proAmount ";
			//			String wherePro = "where head.FCostBillType='PAYMENTSPLIT' And head.FIsInvalid=0 and head.fcontrolUnitid=? and  entry.FIsProduct=1 and entry.fobjectId=? ";
			String wherePro = "where fproductid is not null and entry.fisleaf=1 and head.FIsInvalid=0 And acct.fcurproject=? ";
			String groupPro = "group by FCostAccountId,FProductId ";
			builder.clear();
			builder.appendSql(selectPro);
			builder.appendSql(from);
			builder.appendSql(wherePro);
			builder.appendSql(groupPro);
			//			builder.addParam(this.cuId);
			builder.addParam(objectId);
			IRowSet rsPro = builder.executeQuery();
			while (rsPro.next()) {
				String acctId = rsPro.getString("FCostAccountId");
				if (acctId == null) {
					continue;
				}
				String proId = rsPro.getString("FProductId");
				if (proId == null) {
					continue;
				}
				BigDecimal amount = rsPro.getBigDecimal("proAmount");
				if (amount == null) {
					continue;
				}
				if (paySplitMap.containsKey(acctId)) {
					HappenDataInfo info = (HappenDataInfo) paySplitMap.get(acctId);
					info.addProductAmount(proId, amount);
				}
			}
		}
		return paySplitMap;
	}
	// 部分结算：合同可进行多次结算
	private boolean isUsePartSettle = false;
	// 已经初始化
	private boolean hasInit = false;

	/**
	 * 描述：部分结算：合同可进行多次结算
	 * 
	 * @return
	 * @author skyiter_wang
	 * @createDate 2013-11-2
	 */
	private boolean isUsePartSettle() {
		if (!hasInit) {
			try {
				// 部分结算：合同可进行多次结算
				isUsePartSettle = FDCUtils.getDefaultFDCParamByKey(ctx, null, FDCConstants.FDC_PARAM_MORESETTER);
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort();
			}
			hasInit = true;
		}

		return isUsePartSettle;
	}
	
}
