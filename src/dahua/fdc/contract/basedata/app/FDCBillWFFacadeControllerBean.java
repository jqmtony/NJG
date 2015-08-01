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
	 * ���������ݵ���ID��������ID����ȡ��ǰ�����˶�Ӧ������ģ���еĻ�ڵ�����
	 * @Author��owen_wen
	 * @CreateTime��2011-11-25
	 * @param billId ����ID
	 * @param auditorId ������ID
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
	 * ֻ��FDCBillWFAuditFunction�е�ActionSetWFAuditOrgInfo����
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
     * ����ʾ��ʷ����
     */
    protected List _getWFAuditResultForPrint(Context ctx, String billId) throws BOSException, EASBizException {
    	return getWFAuditResultForPrint(ctx, billId, false);
    }
    
	protected List _getWFAuditResultForPrint2(Context ctx, String billId,
			boolean showHistory) throws BOSException, EASBizException {
		return getWFAuditResultForPrint(ctx, billId, showHistory);
	}
    
	protected List getWFAuditResultForPrint(Context ctx, String billId, boolean showHistory) throws BOSException, EASBizException {
		// TODO �Զ����ɷ������
		/***
		 * begin,ִֻ��һ��
		 * ������䣬�����������
		 */
//		FDCBillWFAuditInfoInfo testinfo = new FDCBillWFAuditInfoInfo();
//		testinfo.setId(BOSUuid.create(testinfo.getBOSType()));
//		testinfo.setBillId("wNfKUwEcEADgABLRwKgQsw1t0fQ=");
//		testinfo.setAuditorId("QYX/KQEWEADgAHfiqKioAxO33n8=");
//		testinfo.setInfoType("ORGINFO");
//		testinfo.setOrgInfo("0");
//    	FDCBillWFAuditInfoFactory.getLocalInstance(ctx).addnew(testinfo);
		//����ʱ������ʽ��0������1������
		String sort = billId.substring(0, 1);
		billId=billId.substring(1);
    	/***
    	 * end
		 * ������䣬�����������
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
			/*try {//��ʷ������Ϣ����һ������б�Ҫ���������ô����ע�͡���ken_liu
				String[] assignIds = new WfUtil(ctx).getApproveHstAssignIds(billId);
				if (assignIds != null && assignIds.length > 0) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < assignIds.length; i++) {
						if (i > 0)
							sb.append(",");
						sb.append("'").append(assignIds[i]).append("'");
					}
					// ���˵���ʷ������Ϣ
					sql.append(" and (T_BAS_MultiApprove.FASSIGNMENTID not IN (" + sb.toString() + "))");
				}
			} catch (Exception e1) {
				logger.info(e1.getMessage(), e1);
				throw new BOSException(e1);
			}*/
		}else {//��ѯ��ʷ������Ϣ����
			String historySQl = sql.toString().replaceAll("T_BAS_MultiApprove", "T_BAS_MultiApproveHst");
			sql.append(" UNION ").append(historySQl);
			params = new Object[] {billId, billId};
		}
		
		sql.append(" order by createTime desc \r\n");
		IRowSet rowset = DbUtil.executeQuery(ctx, sql.toString(), params);
		
		/***
		 * ��ȡ������������Ϣ��¼�����������Ϣ
		 * FDCBillWFAuditInfo�е�infoType=ORGINFO�Ľڵ���Ϣ�����billID����������Ϣ
		 * �������������ˣ�˵������������˵Ľڵ㿪ʼ�������˵Ĺ�˾��Ϣ�Ϳ�ʼ����
		 * �Լ� infoType=NOTPRINT����Ϣ������Ϣ��ʾ�˽ڵ㲻��Ҫ��ӡ
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
		 * ��ӡ����Ϣ���յĸ�ʽΪ������
		 * 
		 * ��˾A
		 * 		����1	ͬ��   ����  2008-10-10
		 * 		����2	ͬ��   ����  2008-10-10
		 * 		����2	ͬ��   ����  2008-10-10
		 * 		����3	ͬ��   �¶�  2008-10-10
		 * ��˾B
		 * 		�ϼ�����1		ͬ��   ����  2008-10-10
		 * 		�ϼ�����2		ͬ��   ����  2008-10-10
		 * ��˾C
		 * 		�ܾ���		ͬ��   John  2008-10-10
		 * 		�ܲ�		ͬ��   Sam  2008-10-10
		 */

			/**
			 * ��˾X������������ʱ�ļ���
			 */
			List tempList   = new ArrayList();
			Map factdefIdMap = new HashMap(); // ���������->��˾x��������
			/***
			 * �������е���Ҫ��ӡ����Ϣ
			 * �����һ���ˣ��ж��������Ϣ
			 * �����һ��������ͬ�⡿Ϊ׼
			 * ��ÿ��������ͬ���˵�ʱ�򣬸���֮ǰ���������
			 */
			Map auditInfosMap = new HashMap();
			boolean isDuplicatePrint = false; //Ĭ���ظ������˲���ӡ��ֻ��ӡ����������Ϣ
			isDuplicatePrint = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_WFIsDuplicateNotPrint);
			boolean isPassIsFalseNotPrint = false;
			isPassIsFalseNotPrint = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), FDCConstants.FDC_PARAM_WFISPASSISFALSENOTPRINT);
			
			String preActDefId = "";
			while(rowset != null && rowset.next()){
				/*
				 * ������ͬ��Ľڵ㣬���������������������Ϣ��ӡʱ��������ͬ�⣬��ͬ��֮ǰ�����ݶ�����ӡ��Ϊ���ǡ�ʱ��
				 * ֻҪ������ͬ�⣬��������ݣ�����ͬ��֮ǰ�����ݲ���ӡ
				 */
				//��Ϊ�ǽ������С���������һ����ͬ����Ժ�����ݶ�����Ҫִ���ˡ�ken_liu
				if (!rowset.getString("isPass").equalsIgnoreCase("true") && isPassIsFalseNotPrint) {
					break ;
				}	
				
				String auditorId = rowset.getString("auditorId");
				String factdefID = rowset.getString("factdefID");
				
				/***
				 * ���浱ǰ�����ڵ�Ӧ���ٵ�List
				 * ���������ת�������һ��Ǹ�List
				 */
				if(factdefIdMap.containsKey(factdefID)){
					//�һص�ǰ���������ڵ�������Ϣ��˾����
					tempList = (ArrayList)factdefIdMap.get(factdefID);
				} else {
					/***
					 * �������֯��Ϣ��map���ҵ������
					 * ��tempList����resultList���൱�ڹ�˾A����Ϣ��ϳɹ���
					 * ����newһ��tempList���൱���¸���˾B��������ʼ
					 */
					//R131121-0076  Ī����ԭ��factdefIDΪ�գ�����preActDefId��,
					//�������Ǳߵ����⣬Ϊ��֤ҵ�񵥾����˳����ӡ...�Ӹ�����...null������ͬһ��.. ken_liu
					if (preActDefId==null || (!preActDefId.equals(factdefID) && orginfoMap.containsKey(preActDefId))) {
						// ����ActDefId���ϴεĲ�ͬ������orginfoMap�Ѿ������Ǹ��ڵ㣬˵��������һ���ŵ����������ʼ����
						if (orginfoMap.containsKey(preActDefId)) {
							resultList.add(tempList);
							tempList = new ArrayList(); // �²��ŵ��������������һ��List�洢
							//orginfoMap.put(factdefID, Boolean.TRUE);
						}
					}
					factdefIdMap.put(factdefID, tempList);
				}
				
				preActDefId = factdefID;
				
				Map auditInfoMap = null;
				if (!auditInfosMap.containsKey(auditorId)) //��δ��ӡ��������
					auditInfoMap = new HashMap();
				else {
					if (!isDuplicatePrint) { //����Ҫ�󣺲���ӡ�ظ���������
//						auditInfoMap = (Map) auditInfosMap.get(auditorId);
						continue;
					} else { //����Ҫ��Ҫ��ӡ�ظ���������
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
				 * �������ӡMAP��������ˣ��򲻼���LIST
				 */
				if (!noprintMap.containsKey(auditorId)) {
					if (auditInfosMap.containsKey(auditorId)) {
						// ��������Ҫ���ظ������ˣ�ҲҪȫ����ӡ
						if (isDuplicatePrint) {
							tempList.add(auditInfoMap);
							auditInfosMap.put(auditorId, auditInfoMap);
						} else { //����ӡ�ظ�������
//							updateAuditInfosList(tempList, auditInfoMap);
//							auditInfosMap.put(auditorId, auditInfoMap);
						}
					} else { // �����ظ������ˣ�Ҫ��ӡ
						tempList.add(auditInfoMap);
						auditInfosMap.put(auditorId, auditInfoMap);
					}
					
				}
				
				/*ע�͵������������Ϊ�������жϡ�����
				/*
				 * ������ͬ��Ľڵ㣬���������������������Ϣ��ӡʱ��������ͬ�⣬��ͬ��֮ǰ�����ݶ�����ӡ��Ϊ���ǡ�ʱ��
				 * ֻҪ������ͬ�⣬��������ݣ�����ͬ��֮ǰ�����ݲ���ӡ
				 
				if (!rowset.getString("isPass").equalsIgnoreCase("true") && isPassIsFalseNotPrint) {
					resultList.clear();
					tempList.clear();
					auditInfosMap.clear();
				}			
				*/
			}// �������whileѭ������
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
			logger.info("����" + i + "��ʼ����" + System.getProperty("line.separator"));
			for (int j = 0; j < ((List) resultList.get(i)).size(); j++) {
				logger.info("����" + i + "��ʼ:" + ((List) resultList.get(i)).get(j) + System.getProperty("line.separator"));
			}
		}
		
		return resultList;
	}

	/**
	 * ����������Ϣ�б��������˵�IDƥ�����
	 * ��֤������˳���ǰ�ʱ���Ⱥ�����
	 * @Author��owen_wen
	 * @CreateTime��2012-8-31
	 */
	private void updateAuditInfosList(List auditInfosList, Map auditInfoMap) {
		logger.error(this.getClass().getName() + "---updateAuditInfosList---" + auditInfoMap);
		for (int i = 0; i < auditInfosList.size(); i++) {
			Map auditInfoMap2 = (Map) auditInfosList.get(i);
			if (auditInfoMap.get(FDCBillWFAuditUtil.ID).equals(auditInfoMap2.get(FDCBillWFAuditUtil.ID))) {
				auditInfosList.remove(i); //�Ѿɵ�remove��
				auditInfosList.add(auditInfoMap); //�����µ�������Ϣ
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