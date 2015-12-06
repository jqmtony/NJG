package com.kingdee.eas.fdc.earlywarn;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.message.Message;
import com.kingdee.bos.service.message.agent.MessageFactory;
import com.kingdee.bos.service.message.agent.MessageSender;
import com.kingdee.bos.service.message.agent.Sender;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.message.BMCMessageFactory;
import com.kingdee.eas.base.message.BMCMessageInfo;
import com.kingdee.eas.base.message.IBMCMessage;
import com.kingdee.eas.base.message.MsgBizType;
import com.kingdee.eas.base.message.MsgPriority;
import com.kingdee.eas.base.message.MsgSourceStatus;
import com.kingdee.eas.base.message.MsgStatus;
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.receiver.BasReceiverCollection;
import com.kingdee.eas.base.receiver.BasReceiverInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.PositionMemberCollection;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;



public class DHWarnMsgFacadeControllerBean extends AbstractDHWarnMsgFacadeControllerBean
{
    private static Logger logger =Logger.getLogger("com.kingdee.eas.fdc.earlywarn.DHWarnMsgFacadeControllerBean");
    
    private IBMCMessage iBMCMessage = null;
    private UserInfo sendUserInfo = null;
    /**
     * 动态成本差异率预警
     */
    protected void _aimCostDiffWarnMsg(Context ctx, String billId)throws BOSException {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select b.CFPositionID,b.CFLevel from CT_AIM_ProjectDynamicCost a left join CT_AIM_ProjectDCEP b on b.fparentid=a.fid where a.fid='").append(billId).append("'");
		try {
			Map<Integer,Set<String>> positionMap = new HashMap<Integer, Set<String>>();
			Map<String,Set<String>> sendMap = new HashMap<String,Set<String>>();
			
			IRowSet executeQuery = DbUtil.executeQuery(ctx, sb.toString());
			while(executeQuery.next()){
				String postionId = executeQuery.getString(1);
				int Level = executeQuery.getInt(2);
				
				Set<String> set = positionMap.get(Level);
				if(set!=null){
					set.add(postionId);
					positionMap.put(Level, set);
				}else{
					set = new HashSet<String>();
					set.add(postionId);
					positionMap.put(Level, set);
				}
			}
			sb = new StringBuffer();
			sb.append(" select c.fname_l2,b.CFCostAccount,b.CFLevel from CT_AIM_ProjectDynamicCost a ");
			sb.append(" left join CT_AIM_ProjectDCEA b on b.fparentid=a.fid ");
			sb.append(" left join t_fdc_curproject c on c.fid=a.CFCurProjectID");
			sb.append(" where b.CFLevel<=3 and a.fid='"+billId+"' and b.CFAlertIndex<b.CFDiffRate *100 order by b.CFLevel ");
			executeQuery = DbUtil.executeQuery(ctx, sb.toString());
			while(executeQuery.next()){
				String projectName = executeQuery.getString(1);
				String costName = executeQuery.getString(2);
				int Level = executeQuery.getString(3)!=null?executeQuery.getInt(3):0;
				if(Level==0)
					continue;
				
				Set<String> set = positionMap.get(Level);
				if(set==null)
					continue;
				String msg = projectName+" 项目中 ["+costName+"] 成本科目的动态成本差异率超过了“警示指标”，请查看动态跟踪表，查找、分析原因。";
				for(Iterator<String> iterator=set.iterator();iterator.hasNext();)
					addForMAP(sendMap,iterator.next(),msg);
				
			}
			for(Iterator<Entry<String, Set<String>>> iterator=sendMap.entrySet().iterator();iterator.hasNext();){
    			Entry<String, Set<String>> next = iterator.next();
    			String positionId = next.getKey();
    			Set<String> msgSet = next.getValue();  
    			Set<UserInfo> userSet = getPositionPersonSet(ctx, positionId);
    			
    			String receivesIds = getReceivesIds(userSet);
    			for(Iterator<String> it=msgSet.iterator();it.hasNext();){
    				String msg = it.next(); 
    				sendBMCMsgInfo(ctx, msg, msg, receivesIds);
    			}
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 结算申报单预警
     */
    protected void _settleDeclarationWarnMsg(Context ctx) throws BOSException {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select DISTINCT cur.fname_l2,conbill.fname,e3.CFYujingrenyuanID,to_char(settDec.CFJswcsjyq,'yyyy-mm-dd') from CT_CON_SettleDeclarationBill settDec ");
    	sb.append(" left join T_CON_contractbill conbill on conbill.fid=settDec.CFContractNumberID ");
    	sb.append(" left join CT_CON_SettleDeclarationBillE3 e3 on e3.FParentID=settDec.fid ");
    	sb.append(" left join T_FDC_CurProject cur on cur.fid=conbill.FCurProjectID ");
    	sb.append(" left join T_CON_ContractSettlementBill setbill on setbill.FContractBillID=settDec.CFContractNumberID ");
    	sb.append(" where settDec.CFIsVersion=1 and settDec.CFJswcsjyq is not null and (setbill.FState='1SAVED' or setbill.fid is null) ");
    	sb.append(" and to_char(settDec.CFJswcsjyq,'yyyy-mm-dd')<to_char(sysdate,'yyyy-mm-dd') order by cur.fname_l2,conbill.fname");
    
    	Map<String,Set<String>> sendMap = new HashMap<String,Set<String>>();
    	addSettleDeclarationWarnForMap(ctx, sb.toString(), sendMap);
    	
    	for(Iterator<Entry<String, Set<String>>> iterator=sendMap.entrySet().iterator();iterator.hasNext();){
    		Entry<String, Set<String>> next = iterator.next();
    		String msg = next.getKey();
    		Set<String> personSet = next.getValue();  
    		Set<UserInfo> userSet = getPersonForUserSet(ctx, personSet);
    		
    		String receivesIds = getReceivesIds(userSet);
    		sendBMCMsgInfo(ctx, msg, msg, receivesIds);
    	}
    }
    
    /**
     * 进度项预警
     */
    protected void _dhScheduleWarnMsg(Context ctx) throws BOSException {
    }
    
    /**
     * 合约跟踪单预警
     * 1、如果审批预警则需要传入审批单据ID 且只需要预警逾期项  id in('id','ID')
     * 2、如果是后台自动生成数据预警 ID传null即可
     * 3、提前天数， 后台事务设置参数即可
     */
    protected void _programmingGZWarnMsg(Context ctx, String billId, int day)throws BOSException {
    	if(UIRuleUtil.isNotNull(billId)){
    		StringBuffer sb = new StringBuffer();
    		sb.append(" select cur.fname_l2,c.fname_l2,prog.CFyjDesignID,prog.CFyjCostID,prog.CFyjProjectID,prog.CFyjMaterialID ,b.CFSgtOverdue,b.CFCsOverdue,b.CFStartOverdue,b.CFEndOverdue,b.CFCsendOverdue ");
    		sb.append(" from CT_CON_PcontractTrackBill a  ");
    		sb.append(" left join T_FDC_CurProject cur on cur.fid=a.CFCurProjectID ");
    		sb.append(" left join CT_CON_PcontractTrackBillentry b on b.FParentID=a.fid ");
    		sb.append(" left join T_CON_ProgrammingContract c on c.fid=b.CFPcid ");
    		sb.append(" left join T_CON_Programming prog on prog.fid=c.FProgrammingID ");
    		sb.append(" where a.fid ='"+billId+"' and (b.CFSgtOverdue =1 or b.CFCsOverdue =1 or b.CFStartOverdue =1 or b.CFEndOverdue =1 or b.CFCsendOverdue=1) ");
    		sb.append(" order by cur.fname_l2 ");
    		Map<String,Set<String>> sendMap = new HashMap<String,Set<String>>();
    		addProgrammingGZWarnMsgForMap(ctx, sb.toString(), sendMap);
    		
    		for(Iterator<Entry<String, Set<String>>> iterator=sendMap.entrySet().iterator();iterator.hasNext();){
    			Entry<String, Set<String>> next = iterator.next();
    			String positionId = next.getKey();
    			Set<String> msgSet = next.getValue();  
    			Set<UserInfo> userSet = getPositionPersonSet(ctx, positionId);
    			
    			String receivesIds = getReceivesIds(userSet);
    			for(Iterator<String> it=msgSet.iterator();it.hasNext();){
    				String msg = it.next(); 
    				sendBMCMsgInfo(ctx, msg, msg, receivesIds);
    			}
    		}
    	}else{
    		//TODO 后台自动生成的数据预警 暂时不做了
    	}
    }
    
    /**
     * 合约规划预警
     */
    protected void _programmingWarnMsg(Context ctx) throws BOSException {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" /*dialect*/select cur.fname_l2,progcon.fname_l2,prog.CFyjDesignID,prog.CFyjCostID,prog.CFyjProjectID,prog.CFyjMaterialID, ");
    	sb.append(" case when to_char(trunc(progcon.CFSgtDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then to_char(progcon.CFSgtDate,'yyyy-mm-dd') else null end, ");
    	sb.append(" case when to_char(trunc(progcon.CFContSignDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then to_char(progcon.CFContSignDate,'yyyy-mm-dd') else null end, ");
	    sb.append(" case when to_char(trunc(progcon.CFStartDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then to_char(progcon.CFStartDate,'yyyy-mm-dd') else null end, ");
	    sb.append(" case when to_char(trunc(progcon.CFEndDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then to_char(progcon.CFEndDate,'yyyy-mm-dd') else null end, ");
	    sb.append(" case when to_char(trunc(progcon.CFCsendDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then to_char(progcon.CFCsendDate,'yyyy-mm-dd') else null end ");
	    sb.append(" from T_CON_ProgrammingContract progcon ");
	    sb.append(" left join T_CON_Programming prog on prog.fid=progcon.FProgrammingID ");
	    sb.append(" left join T_FDC_CurProject cur on cur.fid=prog.fprojectid ");
	    sb.append(" where prog.fisLatest=1 and prog.fstate='4AUDITTED' and ( ");
	    sb.append(" (case when to_char(trunc(progcon.CFSgtDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then progcon.CFSgtDate else null end) is not null ");
	    sb.append(" or (case when to_char(trunc(progcon.CFContSignDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then progcon.CFContSignDate else null end) is not null ");
	    sb.append(" or (case when to_char(trunc(progcon.CFStartDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then progcon.CFStartDate else null end) is not null ");
	    sb.append(" or (case when to_char(trunc(progcon.CFEndDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then progcon.CFEndDate else null end) is not null ");
	    sb.append(" or (case when to_char(trunc(progcon.CFCsendDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') then progcon.CFCsendDate else null end) is not null ");
	    sb.append(" ) order by cur.fname_l2 ");
	    Map<String,Set<String>> sendMap = new HashMap<String,Set<String>>();
    	addProgrammingWarnMsgForMap(ctx, sb.toString(), sendMap,"主");
    	
    	sb = new StringBuffer();
    	sb.append(" /*dialect*/select cur.fname_l2,progcon.fname_l2,prog.CFyjCostID,prog.CFyjDesignID,prog.CFyjMaterialID,prog.CFyjProjectID,fx.CFItemName,dep.fname_l2,to_char(fx.CFPlanDate,'yyyy-mm-dd')");
    	sb.append(" from T_CON_ProgrammingContract progcon ");
    	sb.append(" left join T_CON_Programming prog on prog.fid=progcon.FProgrammingID ");
    	sb.append(" left join T_FDC_CurProject cur on cur.fid=prog.fprojectid ");
    	sb.append(" left join CT_CON_ProgrammingFxbdEntry fx on fx.FParentID=progcon.fid ");
    	sb.append(" left join CT_CON_PcDepType dep on dep.fid=fx.CFDepTypeID ");
    	sb.append(" where prog.fisLatest=1 and prog.fstate='4AUDITTED' and fx.CFisYj=1 and fx.CFPlanDate is not null  ");
    	sb.append(" and to_char(trunc(fx.CFPlanDate)-nvl(prog.CFYjDays,0),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd') and dep.fname_l2 is not null");
    	sb.append(" order by cur.fname_l2,dep.fname_l2 ");
    	addProgrammingWarnMsgForMap(ctx, sb.toString(), sendMap,"副");
    
    	for(Iterator<Entry<String, Set<String>>> iterator=sendMap.entrySet().iterator();iterator.hasNext();){
    		Entry<String, Set<String>> next = iterator.next();
    		String positionId = next.getKey();
    		Set<String> msgSet = next.getValue();  
    		Set<UserInfo> userSet = getPositionPersonSet(ctx, positionId);
    		
    		String receivesIds = getReceivesIds(userSet);
    		for(Iterator<String> it=msgSet.iterator();it.hasNext();){
    			String msg = it.next(); 
    			sendBMCMsgInfo(ctx, msg, msg, receivesIds);
    		}
    	}
    }
    
    private void addProgrammingWarnMsgForMap(Context ctx,String sql,Map<String,Set<String>> sendMap,String type){
		try {
			IRowSet executeQuery = DbUtil.executeQuery(ctx, sql);
			if(type.equals("副")){
				while(executeQuery.next()){
					String projectName = executeQuery.getString(1);
					String programmingName = executeQuery.getString(2);
					String yjCostID = executeQuery.getString(3);
					String yjDesignID = executeQuery.getString(4);
					String yjMaterialID = executeQuery.getString(5);
					String yjProjectID = executeQuery.getString(6);
					String ItemName = executeQuery.getString(7);
					
					String depName = executeQuery.getString(8);
					String planDate = executeQuery.getString(9);
					
					String msg = projectName+" 的合约规划名称 ["+programmingName+"] 的副项中，"+ItemName+" 事项即将到完成时间  完成时间："+planDate+"，请注意确保该事项按时完成。";
					if(depName.equals("设计部")&&UIRuleUtil.isNotNull(yjDesignID)){
						addForMAP(sendMap, yjDesignID, msg);
					}
					if(depName.equals("成本管理部/组")&&UIRuleUtil.isNotNull(yjCostID)){
						addForMAP(sendMap, yjCostID, msg);
					}
					if(depName.equals("工程部")&&UIRuleUtil.isNotNull(yjProjectID)){
						addForMAP(sendMap, yjProjectID, msg);
					}
					if(depName.equals("材料管理公司")&&UIRuleUtil.isNotNull(yjMaterialID)){
						addForMAP(sendMap, yjMaterialID, msg);
					}
				}
			}else{
				while(executeQuery.next()){
					String projectName = executeQuery.getString(1);
					String programmingName = executeQuery.getString(2);
					String yjDesignID = executeQuery.getString(3);
					String yjCostID = executeQuery.getString(4);
					String yjProjectID = executeQuery.getString(5);
					String yjMaterialID = executeQuery.getString(6);
					
					String designDate = executeQuery.getString(7);
					String costDate = executeQuery.getString(8);
					String planDate = executeQuery.getString(9);
					String projectDate = executeQuery.getString(10);
					String materialDate = executeQuery.getString(11);
				
					
					if(UIRuleUtil.isNotNull(designDate)&&UIRuleUtil.isNotNull(yjDesignID)){
						String msg = projectName+" 的合约规划名称 ["+programmingName+"] 的主项中，施工图完成交接时间即将到期  到期日："+designDate+"，请注意确保该事项按时完成。";
						addForMAP(sendMap, yjDesignID, msg);
					}
					if(UIRuleUtil.isNotNull(costDate)&&UIRuleUtil.isNotNull(yjCostID)){
						String msg = projectName+" 的合约规划名称 ["+programmingName+"] 的主项中，合同签订完成时间即将到期  到期日："+costDate+"，请注意确保该事项按时完成。";
						addForMAP(sendMap, yjCostID, msg);
					}
					if((UIRuleUtil.isNotNull(planDate)||UIRuleUtil.isNotNull(projectDate))&&UIRuleUtil.isNotNull(yjProjectID)){
						if(UIRuleUtil.isNotNull(planDate)){
							String msg = projectName+" 的合约规划名称 ["+programmingName+"] 的主项中，开工时间即将到期  到期日："+planDate+"，请注意确保该事项按时完成。";
							addForMAP(sendMap, yjProjectID, msg);
						}
						if(UIRuleUtil.isNotNull(projectDate)){
							String msg = projectName+" 的合约规划名称 ["+programmingName+"] 的主项中，竣工时间即将到期  到期日："+projectDate+"，请注意确保该事项按时完成。";
							addForMAP(sendMap, yjProjectID, msg);
						}
					}
					if(UIRuleUtil.isNotNull(materialDate)&&UIRuleUtil.isNotNull(yjMaterialID)){
						String msg = projectName+" 的合约规划名称 ["+programmingName+"] 的主项中，合同签订完成时间即将到期  到期日："+materialDate+"，请注意确保该事项按时完成。";
						addForMAP(sendMap, yjMaterialID, msg);
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void addProgrammingGZWarnMsgForMap(Context ctx,String sql,Map<String,Set<String>> sendMap){
		try {
			IRowSet executeQuery = DbUtil.executeQuery(ctx, sql.toString());
			while(executeQuery.next()){
				String projectName = executeQuery.getString(1);
				String programmingName = executeQuery.getString(2);
				String yjCostID = executeQuery.getString(3);
				String yjDesignID = executeQuery.getString(4);
				String yjMaterialID = executeQuery.getString(5);
				String yjProjectID = executeQuery.getString(6);
				
				boolean isSgtOverdue = executeQuery.getBoolean(7);
				boolean isCsOverdue = executeQuery.getBoolean(8);
				boolean isStartOverdue = executeQuery.getBoolean(9);
				boolean isEndOverdue = executeQuery.getBoolean(10);
				boolean isCsendOverdue = executeQuery.getBoolean(11);
				
				if(isSgtOverdue&&UIRuleUtil.isNotNull(yjDesignID)){
					String msg = projectName+" 项目的合约规划 "+programmingName+" 下的 [施工图交接时间] 已经逾期，请前往合约跟踪单查看。";
					addForMAP(sendMap, yjDesignID, msg);
				}
				if(isCsOverdue&&UIRuleUtil.isNotNull(yjCostID)){
					String msg = projectName+" 项目的合约规划 "+programmingName+" 下的 [合同签订时间] 已经逾期，请前往合约跟踪单查看。";
					addForMAP(sendMap, yjCostID, msg);
				}
				if((isStartOverdue||isEndOverdue)&&UIRuleUtil.isNotNull(yjProjectID)){
					String msg = "";
					if(isStartOverdue)
						msg = projectName+" 项目的合约规划 "+programmingName+" 下的 [开工时间] 已经逾期，请前往合约跟踪单查看。";
					if(isEndOverdue)
						msg = projectName+" 项目的合约规划 "+programmingName+" 下的 [竣工时间] 已经逾期，请前往合约跟踪单查看。";
					addForMAP(sendMap, yjProjectID, msg);
				}
				if(isCsendOverdue&&UIRuleUtil.isNotNull(yjMaterialID)){
					String msg = projectName+" 项目的合约规划 "+programmingName+" 下的 [合同签订完成时间] 已经逾期，请前往合约跟踪单查看。";
					addForMAP(sendMap, yjMaterialID, msg);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void addSettleDeclarationWarnForMap(Context ctx,String sql,Map<String,Set<String>> sendMap){
		try {
			IRowSet executeQuery = DbUtil.executeQuery(ctx, sql.toString());
			while(executeQuery.next()){
				String projectName = executeQuery.getString(1);
				String conName = executeQuery.getString(2);
				String person = executeQuery.getString(3);
				String jssj = executeQuery.getString(4);
				
				String msg = projectName+" 项目的 ["+conName+"] 合同，已经超过结算完成时间还没有结算 ，结算要求完成时间:"+jssj+"，请尽快安排结算事宜！";
				
				addForMAP(sendMap, msg, person);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
    private void addForMAP(Map<String,Set<String>> sendMap,String postionId,String msg){
    	Set<String> set = sendMap.get(postionId);
		if(set!=null){
			set.add(msg);
			sendMap.put(postionId,set);
		}else{
			set = new HashSet<String>();
			set.add(msg);
			sendMap.put(postionId,set);
		}
    }
    
    /**
     * 获取职位下所有人员
     * @return 
     * @throws BOSException 
     */
    private Set<UserInfo> getPositionPersonSet(Context ctx,String positionId) throws BOSException{
    	String oql = "select person.id where position.id = '" + positionId + "'" ;
    	Set<UserInfo> userSet = new HashSet<UserInfo>();
		PositionMemberCollection memberCollection = PositionMemberFactory.getLocalInstance(ctx).getPositionMemberCollection(oql);
		for (int i = 0; i < memberCollection.size(); i++) {
			PositionMemberInfo memberInfo = memberCollection.get(i);
			if(memberInfo.getPerson()!=null)
				getUserInfo(ctx, memberInfo.getPerson(),userSet);
		}
		return userSet;
    }
    
    /**
     * 获取职位下所有人员
     * @return 
     * @throws BOSException 
     */
    private Set<UserInfo> getPersonForUserSet(Context ctx,Set<String> personID) throws BOSException{
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filInfo = new FilterInfo();personID.add("999");
    	filInfo.getFilterItems().add(new FilterItemInfo("person.id",personID,CompareType.INCLUDE));
    	view.setFilter(filInfo);
    	view.getSelector().add("id");
    	view.getSelector().add("number");
    	view.getSelector().add("name");
    	UserCollection userCollection = UserFactory.getLocalInstance(ctx).getUserCollection(view);
    	Set<UserInfo> userSet = new HashSet<UserInfo>();
    	for (int i = 0; i < userCollection.size(); i++) {
    		userSet.add(userCollection.get(i));
		}
		return userSet;
    }
    
    
    private void getUserInfo(Context ctx,PersonInfo personInfo,Set<UserInfo> userSet){
		try {
			String oql = "select id,name,number where person.id='"+personInfo.getId()+"'";
			UserCollection user = UserFactory.getLocalInstance(ctx).getUserCollection(oql);
			for (int i = 0; i < user.size(); i++)
				userSet.add(user.get(i));
		} catch (BOSException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * 消息类型：例如通知消息，任务消息，状态更新消息
	 * title:消息标题
	 * body:消息体
	 * Receivers:消息接收人
	 * */
	private void sendBMCMsgInfo(Context ctx, String title, String body, String receiversId){
	    try {
	    	iBMCMessage = iBMCMessage==null?BMCMessageFactory.getLocalInstance(ctx):iBMCMessage;
	    	if(sendUserInfo==null){
	    		UserInfo userInfo = new UserInfo();
	    		userInfo.setId(BOSUuid.read("00000000-0000-0000-0000-00000000000013B7DE7F"));
	    		userInfo.setName("管理员");
	    		sendUserInfo = userInfo;
	    	}
	    	Sender senderAgent = MessageSender.getInstance();  
	    	Locale locale = ctx.getLocale();  
	    	Message message = MessageFactory.newMessage("kingdee.workflow");
	    	message.setLocaleStringHeader("title", title, locale); 
	    	message.setLocaleStringHeader("sender", "预警管理员", locale);
	    	message.setLocaleStringHeader("body", body, locale);
	    	message.setIntHeader("type", MsgType.NOTICE_VALUE);
	    	message.setIntHeader("bizType", MsgBizType.FORWARN_VALUE);
	    	message.setIntHeader("sourceStatus", MsgSourceStatus.EMPTY_VALUE);  
	    	message.setIntHeader("priority", MsgPriority.MIDDLE_VALUE);  
	    	message.setStringHeader("databaseCenter", ctx.getAIS());
	    	message.setStringHeader("solution", ctx.getSolution());
	    	message.setStringHeader("receiver", receiversId);  
	    	senderAgent.sendMessage(message); 
	    } catch (BOSException e) {
	    	e.printStackTrace();
	    }
	}
	
	private String getReceivesIds(Set<UserInfo> Receivers){
		String id = "";
		for(Iterator<UserInfo> it=Receivers.iterator();it.hasNext();){
			if("".equals(id.trim()))
				id = id+((UserInfo)it.next()).getId().toString();
			else
				id = id+";"+((UserInfo)it.next()).getId().toString();
		}
		return id;
	}
}