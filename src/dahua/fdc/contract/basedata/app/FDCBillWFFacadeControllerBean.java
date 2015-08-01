package com.kingdee.eas.fdc.basedata.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillWFAuditInfoFactory;
import com.kingdee.eas.fdc.basedata.FDCBillWFAuditInfoInfo;
import com.kingdee.eas.fdc.basedata.FDCBillWFAuditUtil;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;

public class FDCBillWFFacadeControllerBean extends AbstractFDCBillWFFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCBillWFFacadeControllerBean");
    protected void _setWFAuditNotPrint(Context ctx, BOSUuid billId, BOSUuid auditorId)throws BOSException, EASBizException
    {
    	FDCBillWFAuditInfoInfo info = new FDCBillWFAuditInfoInfo();
    	info.setId(BOSUuid.create(info.getBOSType()));
    	info.setBillId(String.valueOf(billId));
    	info.setAuditorId(String.valueOf(auditorId));
    	info.setInfoType("NOTPRINT");
    	FDCBillWFAuditInfoFactory.getLocalInstance(ctx).addnew(info);
    }

	/**
	 * 
	 * 描述：根据单据ID和审批人ID来获取当前审批人对应工作流模板中的活动节点内码
	 * @Author：owen_wen
	 * @CreateTime：2011-11-25
	 * @param billId 单据ID
	 * @param auditorId 审批人ID
	 */
    private String getMultiapproveActDefId(Context ctx, String billId, String auditorId) throws BOSException {
		StringBuffer sqlStatement = new StringBuffer();
		sqlStatement.append("select top 1 ad.factdefID from t_wfr_assigndetail ad ");
		sqlStatement.append("inner join t_bas_multiapprove ma on ad.fassignID=ma.fassignmentid ");
		sqlStatement.append("where ma.fbillid=? and ma.fcreatorid=? ");
		sqlStatement.append("order by ma.fcreateTime desc");
		IRowSet rowset = DbUtil.executeQuery(ctx, sqlStatement.toString(), new Object[] { billId, auditorId });
		String actDefId = "";
		try {
			if (rowset.next()) {
				actDefId = rowset.getString(1);
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage(), e);
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		return actDefId;
	}

	/**
	 * 只在FDCBillWFAuditFunction中的ActionSetWFAuditOrgInfo调用
	 */
    protected void _setWFAuditOrgInfo(Context ctx, BOSUuid billId, BOSUuid auditorId, String org) throws BOSException, EASBizException
    {
    	FDCBillWFAuditInfoInfo info = new FDCBillWFAuditInfoInfo();
    	info.setId(BOSUuid.create(info.getBOSType()));
    	info.setBillId(String.valueOf(billId));
    	info.setAuditorId(String.valueOf(auditorId));
    	info.setInfoType("ORGINFO");
    	info.setOrgInfo(org);

    	info.setActDefID(getMultiapproveActDefId(ctx, billId.toString(), auditorId.toString()));
    	FDCBillWFAuditInfoFactory.getLocalInstance(ctx).addnew(info);
    }
    
    /**
     * 不显示历史审批
     */
    protected List _getWFAuditResultForPrint(Context ctx, String billId) throws BOSException, EASBizException {
    	return getWFAuditResultForPrint(ctx, billId, false);
    }
    
	protected List _getWFAuditResultForPrint2(Context ctx, String billId,
			boolean showHistory) throws BOSException, EASBizException {
		return getWFAuditResultForPrint(ctx, billId, showHistory);
	}
    
	protected List getWFAuditResultForPrint(Context ctx, String billId, boolean showHistory) throws BOSException, EASBizException {
		// TODO 自动生成方法存根
		/***
		 * begin,只执行一次
		 * 测试语句，插入测试数据
		 */
//		FDCBillWFAuditInfoInfo testinfo = new FDCBillWFAuditInfoInfo();
//		testinfo.setId(BOSUuid.create(testinfo.getBOSType()));
//		testinfo.setBillId("wNfKUwEcEADgABLRwKgQsw1t0fQ=");
//		testinfo.setAuditorId("QYX/KQEWEADgAHfiqKioAxO33n8=");
//		testinfo.setInfoType("ORGINFO");
//		testinfo.setOrgInfo("0");
//    	FDCBillWFAuditInfoFactory.getLocalInstance(ctx).addnew(testinfo);
		//审批时间排序方式：0，升序；1，降序
		String sort = billId.substring(0, 1);
		billId=billId.substring(1);
    	/***
    	 * end
		 * 测试语句，插入测试数据
		 */
		
		StringBuffer sql = new StringBuffer();
		String fnameloc = "FName_" + LocaleUtils.getLocaleString(ctx.getLocale());
		String fopinionloc = "FOpinion_" + LocaleUtils.getLocaleString(ctx.getLocale());
		sql.append("select t_org_ctrlunit."+fnameloc+" as "+FDCBillWFAuditUtil.CtrlUnitName+"," +
							" T_ORG_Admin."+fnameloc+" as "+FDCBillWFAuditUtil.AdminUnitName+"," +
							" t_pm_user."+  fnameloc+" as "+FDCBillWFAuditUtil.PersonName+"," +
							" T_ORG_POSITION."+fnameloc+" as "+FDCBillWFAuditUtil.AuditPosition+","+
							" t_pm_user.fid as auditorId," +
							" T_BAS_MultiApprove."+fopinionloc+" as "+FDCBillWFAuditUtil.Opinion+"," +
							" T_BAS_MultiApprove.fcreatetime as "+FDCBillWFAuditUtil.CreateTime+"," +
							" T_BAS_MultiApprove.fassignmentid ," +
							" t_wfr_assigndetail.factdefID ," +
							" t_wfr_assigndetail.FACTDEFNAME_" +LocaleUtils.getLocaleString(ctx.getLocale())+ " as " +FDCBillWFAuditUtil.AuditNodeName+ "," +
							" T_BAS_MultiApprove.FHandlerOption as " + FDCBillWFAuditUtil.HandlerOpinion+"," +
							" T_BAS_MultiApprove.FHandlerContent as " + FDCBillWFAuditUtil.HandlerContent+"," +
							" T_BAS_MultiApprove.fispass     as " + FDCBillWFAuditUtil.IsPass +" \r\n");
		sql.append(" from \r\n");
		sql.append(" T_BAS_MultiApprove \r\n");
		sql.append(" left outer join t_pm_user on T_BAS_MultiApprove.fcreatorid=t_pm_user.fid \r\n");
		sql.append(" left outer join t_org_positionmember  on t_pm_user.fpersonid=t_org_positionmember.fpersonid and  t_org_positionmember.fisprimary=1 \r\n");
		sql.append(" left outer join T_ORG_POSITION on T_ORG_POSITION.fid=t_org_positionmember.fpositionid \r\n");
		sql.append(" left outer join T_ORG_Admin on T_ORG_Admin.fid=T_ORG_POSITION.fadminorgunitid \r\n");
		sql.append(" left outer join t_org_ctrlunit on t_org_ctrlunit.fid=t_org_positionmember.fcontrolUnitid \r\n");
		sql.append(" left outer join t_wfr_assigndetail on t_wfr_assigndetail.fassignID=T_BAS_MultiApprove.fassignmentid \r\n");
		sql.append(" where T_BAS_MultiApprove.fbillid=? ");
		
		Object[] params = null;
		if(!showHistory) {
			params = new Object[] {billId};
			/*try {//历史审批信息在另一表里。。有必要加这个过滤么？先注释。。ken_liu
				String[] assignIds = new WfUtil(ctx).getApproveHstAssignIds(billId);
				if (assignIds != null && assignIds.length > 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < assignIds.length; i++) {
						if (i > 0)
							sb.append(",");
						sb.append("'").append(assignIds[i]).append("'");
					}
					// 过滤掉历史审批信息
					sql.append(" and (T_BAS_MultiApprove.FASSIGNMENTID not IN (" + sb.toString() + "))");
				}
			} catch (Exception e1) {
				logger.info(e1.getMessage(), e1);
				throw new BOSException(e1);
			}*/
		}else {//查询历史审批信息。。
			String historySQl = sql.toString().replaceAll("T_BAS_MultiApprove", "T_BAS_MultiApproveHst");
			sql.append(" UNION ").append(historySQl);
			params = new Object[] {billId, billId};
		}
		
		sql.append(" order by createTime desc \r\n");
		IRowSet rowset = DbUtil.executeQuery(ctx, sql.toString(), params);
		
		/***
		 * 读取工作流审批信息记录里面的特殊信息
		 * FDCBillWFAuditInfo中的infoType=ORGINFO的节点信息，这个billID的审批人信息
		 * 如果有这个审批人，说明从这个审批人的节点开始，审批人的公司信息就开始换了
		 * 以及 infoType=NOTPRINT的信息，此信息表示此节点不需要打印
		 */
		StringBuffer sqlWfInfo = new StringBuffer();
		sqlWfInfo.append("select fbillid,fauditorid,finfotype,factdefID from t_fdc_fdcbillwfauditinfo ");
		sqlWfInfo.append("where  fbillid = ? ");
		IRowSet rowsetWfInfo = DbUtil.executeQuery(ctx,sqlWfInfo.toString(),new Object[]{ billId });
		Map noprintMap = new HashMap();
		Map orginfoMap = new HashMap();
		List resultList = new ArrayList();
		try {
			for (int i = 0; rowsetWfInfo != null && i < rowsetWfInfo.size(); i++) {
				rowsetWfInfo.next();
				String infoType = rowsetWfInfo.getString("finfotype");
				if (infoType.equalsIgnoreCase("NOTPRINT")) {
					noprintMap.put(rowsetWfInfo.getString("fauditorid"), Boolean.TRUE);
				} else if (infoType.equalsIgnoreCase("ORGINFO")) {
					orginfoMap.put(rowsetWfInfo.getString("factdefID"), Boolean.FALSE);
				}
			}		
		
		/****
		 * 打印的信息最终的格式为这样：
		 * 
		 * 公司A
		 * 		部门1	同意   张三  2008-10-10
		 * 		部门2	同意   李四  2008-10-10
		 * 		部门2	同意   王五  2008-10-10
		 * 		部门3	同意   陈二  2008-10-10
		 * 公司B
		 * 		上级部门1		同意   赵六  2008-10-10
		 * 		上级部门2		同意   杨七  2008-10-10
		 * 公司C
		 * 		总经理		同意   John  2008-10-10
		 * 		总裁		同意   Sam  2008-10-10
		 */

			/**
			 * 公司X的审批集，临时的集合
			 */
			List tempList   = new ArrayList();
			Map factdefIdMap = new HashMap(); // 活动定义内码->公司x的审批集
			/***
			 * 缓存所有的需要打印的信息
			 * 如果有一个人，有多个审批信息
			 * 以最后一个【且是同意】为准
			 * 当每次碰到相同的人的时候，覆盖之前缓存的数据
			 */
			Map auditInfosMap = new HashMap();
			boolean isDuplicatePrint = false; //默认重复审批人不打印，只打印最后的审批信息
			isDuplicatePrint = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_WFIsDuplicateNotPrint);
			boolean isPassIsFalseNotPrint = false;
			isPassIsFalseNotPrint = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_WFISPASSISFALSENOTPRINT);
			
			String preActDefId = "";
			while(rowset != null && rowset.next()){
				/*
				 * 碰到不同意的节点，如果参数“工作流审批信息打印时，碰到不同意，不同意之前的数据都不打印”为“是”时，
				 * 只要碰到不同意，就清空数据，即不同意之前的数据不打印
				 */
				//因为是降序排列。。。遇到一条不同意后，以后的数据都不需要执行了。ken_liu
				if (!rowset.getString("isPass").equalsIgnoreCase("true") && isPassIsFalseNotPrint) {
					break ;
				}	
				
				String auditorId = rowset.getString("auditorId");
				String factdefID = rowset.getString("factdefID");
				
				/***
				 * 缓存当前审批节点应该再的List
				 * 如果出现跳转，可以找回那个List
				 */
				if(factdefIdMap.containsKey(factdefID)){
					//找回当前审批人所在的审批信息公司分组
					tempList = (ArrayList)factdefIdMap.get(factdefID);
				} else {
					/***
					 * 如果在组织信息的map中找到这个人
					 * 把tempList加入resultList，相当于公司A的信息组合成功，
					 * 重新new一个tempList，相当于下个公司B的审批开始
					 */
					//R131121-0076  莫名的原因，factdefID为空，导致preActDefId空,
					//工作流那边的问题，为保证业务单据这边顺利打印...加个保护...null归属到同一类.. ken_liu
					if (preActDefId==null || (!preActDefId.equals(factdefID) && orginfoMap.containsKey(preActDefId))) {
						// 本次ActDefId与上次的不同，并且orginfoMap已经有了那个节点，说明出现另一部门的审批意见开始出现
						if (orginfoMap.containsKey(preActDefId)) {
							resultList.add(tempList);
							tempList = new ArrayList(); // 新部门的审批意见，用另一个List存储
							//orginfoMap.put(factdefID, Boolean.TRUE);
						}
					}
					factdefIdMap.put(factdefID, tempList);
				}
				
				preActDefId = factdefID;
				
				Map auditInfoMap = null;
				if (!auditInfosMap.containsKey(auditorId)) //从未打印的审批人
					auditInfoMap = new HashMap();
				else {
					if (!isDuplicatePrint) { //参数要求：不打印重复的审批人
//						auditInfoMap = (Map) auditInfosMap.get(auditorId);
						continue;
					} else { //参数要求：要打印重复的审批人
						auditInfoMap = new HashMap();
					}
				}
				
				auditInfoMap.put(FDCBillWFAuditUtil.ID,rowset.getString("auditorId"));
				auditInfoMap.put(FDCBillWFAuditUtil.CtrlUnitName,rowset.getString(FDCBillWFAuditUtil.CtrlUnitName));
				auditInfoMap.put(FDCBillWFAuditUtil.AdminUnitName,rowset.getString(FDCBillWFAuditUtil.AdminUnitName));
				auditInfoMap.put(FDCBillWFAuditUtil.Opinion,rowset.getString(FDCBillWFAuditUtil.Opinion));
				auditInfoMap.put(FDCBillWFAuditUtil.PersonName,rowset.getString(FDCBillWFAuditUtil.PersonName));
				auditInfoMap.put(FDCBillWFAuditUtil.CreateTime,rowset.getString(FDCBillWFAuditUtil.CreateTime));
				auditInfoMap.put(FDCBillWFAuditUtil.AuditPosition,rowset.getString(FDCBillWFAuditUtil.AuditPosition));
				auditInfoMap.put(FDCBillWFAuditUtil.AuditNodeName,rowset.getString(FDCBillWFAuditUtil.AuditNodeName));
				String passResult = com.kingdee.eas.base.multiapprove.ApproveResult.getEnum(rowset.getString(FDCBillWFAuditUtil.IsPass)).getAlias();
				auditInfoMap.put(FDCBillWFAuditUtil.IsPass, passResult);
				auditInfoMap.put(FDCBillWFAuditUtil.HandlerOpinion, String.valueOf(rowset.getInt(FDCBillWFAuditUtil.HandlerOpinion)));
				auditInfoMap.put(FDCBillWFAuditUtil.HandlerContent, rowset.getString(FDCBillWFAuditUtil.HandlerContent));
				/***
				 * 如果不打印MAP中有这个人，则不加入LIST
				 */
				if (!noprintMap.containsKey(auditorId)) {
					if (auditInfosMap.containsKey(auditorId)) {
						// 参数设置要求：重复审批人，也要全部打印
						if (isDuplicatePrint) {
							tempList.add(auditInfoMap);
							auditInfosMap.put(auditorId, auditInfoMap);
						} else { //不打印重复审批人
//							updateAuditInfosList(tempList, auditInfoMap);
//							auditInfosMap.put(auditorId, auditInfoMap);
						}
					} else { // 不是重复审批人，要打印
						tempList.add(auditInfoMap);
						auditInfosMap.put(auditorId, auditInfoMap);
					}
					
				}
				
				/*注释掉这里。。。。改为在上面判断、、、
				/*
				 * 碰到不同意的节点，如果参数“工作流审批信息打印时，碰到不同意，不同意之前的数据都不打印”为“是”时，
				 * 只要碰到不同意，就清空数据，即不同意之前的数据不打印
				 
				if (!rowset.getString("isPass").equalsIgnoreCase("true") && isPassIsFalseNotPrint) {
					resultList.clear();
					tempList.clear();
					auditInfosMap.clear();
				}			
				*/
			}// 审批结果while循环结束
			if(sort.equals("0")){
				Collections.sort(tempList, new Comparator(){
					public int compare(Object o1, Object o2) {
						Map m1 = (Map) o1;
						Map m2 = (Map) o2;
						Object object1 = m1.get(FDCBillWFAuditUtil.CreateTime);
						Object object2 = m2.get(FDCBillWFAuditUtil.CreateTime);
						return object1.toString().compareTo(object2.toString());
					}
					
				}
				);
			}

			resultList.add(tempList);
		} catch (SQLException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		for (int i = 0; i < resultList.size(); i++) {
			logger.info("部门" + i + "开始－－" + System.getProperty("line.separator"));
			for (int j = 0; j < ((List) resultList.get(i)).size(); j++) {
				logger.info("部门" + i + "开始:" + ((List) resultList.get(i)).get(j) + System.getProperty("line.separator"));
			}
		}
		
		return resultList;
	}

	/**
	 * 更新审批信息列表，按审批人的ID匹配更新
	 * 保证审批人顺序是按时间先后排序
	 * @Author：owen_wen
	 * @CreateTime：2012-8-31
	 */
	private void updateAuditInfosList(List auditInfosList, Map auditInfoMap) {
		logger.error(this.getClass().getName() + "---updateAuditInfosList---" + auditInfoMap);
		for (int i = 0; i < auditInfosList.size(); i++) {
			Map auditInfoMap2 = (Map) auditInfosList.get(i);
			if (auditInfoMap.get(FDCBillWFAuditUtil.ID).equals(auditInfoMap2.get(FDCBillWFAuditUtil.ID))) {
				auditInfosList.remove(i); //把旧的remove掉
				auditInfosList.add(auditInfoMap); //放入新的审批信息
				break;
			}
		}
	}
	
	protected Map _getWFBillLastAuditorAndTime(Context ctx, Set billIds) throws BOSException, EASBizException {
		Map result = new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		String lang=ctx.getLocale().getLanguage();
		builder.appendSql("select t.fbillid,t.fcreatetime,creator.fid as creatorid,creator.fname_"+lang+" as creatorname from (( \n");
		builder.appendSql("select t1.fbillid,t1.fcreateTime,t1.fcreatorId from T_BAS_MULTIAPPROVEHST t1  \n");
		builder.appendSql("inner join (select fbillid,max(fcreatetime) as fcreatetime from T_BAS_MULTIAPPROVEHST  where ");
		builder.appendParam("fbillid", billIds.toArray(), "varchar(44)", true);
		logger.info("the length of billIds is: " + billIds.size());
		builder.appendSql(" group by fbillid) t2 on t1.fbillid=t2.fbillid and t1.fcreatetime=t2.fcreatetime \n");
		builder.appendSql(") \n");
		builder.appendSql("union all \n");
		builder.appendSql("(select t1.fbillid,t1.fcreateTime,t1.fcreatorId from T_BAS_MULTIAPPROVE t1  \n");
		builder.appendSql("inner join (select fbillid,max(fcreatetime) as fcreatetime from T_BAS_MULTIAPPROVE  where ");
		builder.appendParam("fbillid", billIds.toArray(), "varchar(44)", true);
		builder.appendSql(" group by fbillid) t2 on t1.fbillid=t2.fbillid and t1.fcreatetime=t2.fcreatetime ");
		builder.appendSql("))t  \n");
		builder.appendSql(" inner join t_pm_user creator on creator.fid=t.fcreatorid");
		logger.info("---------------\n" + builder.getTestSql());
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				MultiApproveInfo info=new MultiApproveInfo();
				info.setCreateTime(rowSet.getTimestamp("fcreatetime"));
				UserInfo creator=new UserInfo();
				creator.setName(rowSet.getString("creatorname"));
				
				/* modified by zhaoqin for R140109-0016 on 2014/01/15 */
				creator.put("id", rowSet.getString("creatorid"));
				
				info.setCreator(creator);
				result.put(rowSet.getString("fbillid"), info);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		} finally {
			builder.releasTempTables();
		}
		return result;
	}

	
}