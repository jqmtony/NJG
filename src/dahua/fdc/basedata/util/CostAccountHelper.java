package com.kingdee.eas.fdc.basedata.util;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.query.ISQLExecutor;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.BackdropColorCollection;
import com.kingdee.eas.fdc.basedata.BackdropColorFactory;
import com.kingdee.eas.fdc.basedata.BackdropColorInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IBackdropColor;
import com.kingdee.eas.fdc.basedata.QueryFieldConditionEnum;
import com.kingdee.jdbc.rowset.IRowSet;
import com.sun.xml.messaging.saaj.util.ByteInputStream;
import common.Logger;

public class CostAccountHelper {
	
	private static Logger logger = Logger.getLogger(CostAccountHelper.class);
	
	public static Map getAcctMap(Context ctx, String objectId) throws BOSException {
		//create Acct Map
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())){
			view.getFilter().appendFilterItem("curProject.id", objectId);
		}else{
			view.getFilter().appendFilterItem("fullOrgUnit.id", objectId);
		}
//		view.getFilter().appendFilterItem("isLeaf", Boolean.TRUE);
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		CostAccountCollection c = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			CostAccountInfo info = (CostAccountInfo) iter.next();
			map.put(info.getLongNumber(), info);
		}
		return map;
	}
	
	public static Map getAcctStageMap(Context ctx, String objectId, String stageId) throws BOSException {
		Map acctStageMap = AccountStageHelper.initAccoutStageMap(ctx, objectId, stageId);
		Map map = new TreeMap();
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(new FilterInfo());
		if(new CurProjectInfo().getBOSType().equals(BOSUuid.read(objectId).getType())){
			view.getFilter().getFilterItems().add(new FilterItemInfo("curProject.id", objectId));
		}else{
			view.getFilter().getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		CostAccountCollection c = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(view);
		for (Iterator iter = c.iterator(); iter.hasNext();) {
			CostAccountInfo info = (CostAccountInfo) iter.next();
			if(acctStageMap.containsKey(info.getId().toString())){
				map.put(info.getLongNumber(), info);
			}
		}
		return map;
	}
	
	/**
	 * ��costAcctMap ���ҵ�longNumber��Ӧ��Ŀ����ϸ��Ŀ
	 * �߼����£�
	 * 1.���costAcctMap ����longnumber��Ӧ�Ŀ�Ŀ������ϸ��Ŀ�Ļ���ֱ�ӷ���
	 * 2.���costAcctMap ����longnumber��Ӧ�Ŀ�Ŀ��������ϸ��Ŀ�Ļ����ҵ����ĵ�һ����ϸ��Ŀ
	 * 2.���costAcctMap ��û��longnumber��Ӧ�Ŀ�Ŀ����longnumber���ϼ���Ӧ�Ŀ�Ŀ����ϸ��Ŀ
	 * @param costAcctMap ��longnumberΪ����TreeMap
	 * @param longnumber
	 * @return
	 */
	public static CostAccountInfo getCostAccount(Map costAcctMap,String longnumber){
		TreeMap treeMap=null;
		if(!(costAcctMap instanceof TreeMap)){
			treeMap=new TreeMap(costAcctMap);
		}else{
			treeMap=(TreeMap)costAcctMap;
		}
		CostAccountInfo acct=null;
		for(String number=longnumber;number!=null;number=number.substring(0,number.lastIndexOf('!'))){
			acct=(CostAccountInfo)costAcctMap.get(number);
			//�ҵ���Ŀ����
			if(acct!=null){
				break;
			}
			//���ϼ�����
			if(number.lastIndexOf('!')<0){
				break;
			}
		}
		
		if(acct==null){
			//not find
			return null;
		}

		if (!acct.isIsLeaf()) {
			// ������ϸ����ϸ�ں����Map���棬���·����ں����Map�����ҵ���ϸ
			SortedMap tailMap = treeMap.tailMap(acct.getLongNumber());
			for (Iterator iter = tailMap.values().iterator(); iter.hasNext();) {
				CostAccountInfo temp = (CostAccountInfo) iter.next();
				if (temp.isIsLeaf()) {
					acct = temp;
					break;
				}
			}
		}

		return acct;
	}
	
	/**
	 * 
	 * @param costAcctMap
	 * @param longnumber
	 * @return
	 */
	public static CostAccountInfo getCostAccount1(Map costAcctMap, String longnumber) {
		TreeMap treeMap = null;
		if (!(costAcctMap instanceof TreeMap)) {
			treeMap = new TreeMap(costAcctMap);
		} else {
			treeMap = (TreeMap) costAcctMap;
		}
		CostAccountInfo acct = null;
		boolean parentFlag = false;// �Ӽ��ɱ���Ŀ�����Ҳ���Ϊfalse���
		for (String number = longnumber; number != null; number = number.substring(0, number.lastIndexOf('!'))) {
			acct = (CostAccountInfo) costAcctMap.get(number);
			int size = 0; // �Ӽ��ɱ���Ŀ����
			if (number.lastIndexOf('!') == -1) {
				return null;
			}
			String parentLongNumber = number.substring(0, number.lastIndexOf('!'));
			// �ҵ���Ŀ����
			if (acct != null) {
				break;
			} else {// �Ҳ�����Ŀ,��
				if (costAcctMap.get(parentLongNumber) == null) {// �����ǰ��Ŀ�������ϼ��ɱ���Ŀ������������һ��
					if (parentLongNumber.lastIndexOf('!') == -1) {// ��������Ϊ���ڵ㣬�򷵻ؿ�
						return null;
					}
					continue;
				}
				// �󸸼������ڣ������Ӽ��ҵ��Ƿ����Ӽ����������������Ӽ���ģ���Ӧ����parentFlag��Ϊtrue
				SortedMap tailMap = treeMap.tailMap(parentLongNumber);
				for (Iterator iter = tailMap.values().iterator(); iter.hasNext();) {
					CostAccountInfo temp = (CostAccountInfo) iter.next();
					if (temp.isIsLeaf() && temp.getLongNumber().length() > parentLongNumber.length()
							&& temp.getLongNumber().startsWith(parentLongNumber)) {
						size++;// ���Ӽ�
						if (longnumber.equals(temp.getLongNumber())) {
							parentFlag = true; // ���Ӽ�����ģ��Ķ�Ӧ
						}
					}
				}
			}
			if (size == 0) {
				return (CostAccountInfo) costAcctMap.get(parentLongNumber);
			}
			if (!parentFlag) {
				return null;// �ڳɱ���Ŀ�����Ҳ�����Ӧģ�����õĳɱ���Ŀ
			}
			// ���ϼ�����
			if (number.lastIndexOf('!') < 0) {
				break;
			}
		}

		if (acct == null) {
			// not find
			return null;
		}

		if (!acct.isIsLeaf()) {
			// ������ϸ����ϸ�ں����Map���棬���·����ں����Map�����ҵ���ϸ
			SortedMap tailMap = treeMap.tailMap(acct.getLongNumber());
			for (Iterator iter = tailMap.values().iterator(); iter.hasNext();) {
				CostAccountInfo temp = (CostAccountInfo) iter.next();
				if (temp.isIsLeaf() && temp.getLongNumber().startsWith(acct.getLongNumber())) {
					acct = temp;
					break;
				}
			}
		}
		return acct;
	}

	/**
	 * ����������ϸ������Ŀ���ҵ��빤����Ŀ��ͬ�Ŀ�Ŀ������
	 * @param costAcctMap
	 * @param longnumber
	 * @return
	 */
	public static CostAccountInfo getCostAccount2(Map costAcctMap, String longnumber) {
		CostAccountInfo acct=null;
		for(String number=longnumber;number!=null;number=number.substring(0,number.lastIndexOf('!'))){
			acct=(CostAccountInfo)costAcctMap.get(number);
			//�ҵ���Ŀ����
			if(acct!=null){
				break;
			}
			//���ϼ�����
			if(number.lastIndexOf('!')<0){
				break;
			}
		}
		
		if(acct==null){
			//not find
			return null;
		}
		return acct;
	}

	public static Color getColorByLevel(int level){
		Map colors = BytesToObject();
		return  (Color)colors.get(String.valueOf(level));
		
	}
	
    public static Map BytesToObject(){
    	Map colorSetting = new HashMap();
    	
    	try {
    		IBackdropColor iface = null;
			
    		iface = BackdropColorFactory.getRemoteInstance();
			
    		BackdropColorCollection colorSetCollection = iface.getBackdropColorCollection("select level,color");
			for(int i=0;i<colorSetCollection.size();i++) {
				BackdropColorInfo setInfo = colorSetCollection.get(i);
				int level = setInfo.getLevel();
				byte[] b = setInfo.getColor();
				ObjectInputStream objectInputStream = new ObjectInputStream(new ByteInputStream(b, 0, b.length));
				Color settingColor = (Color)objectInputStream.readObject();
				colorSetting.put(String.valueOf(level), settingColor);
			}
		} catch (Exception e) {
			// @AbortException
			logger.error(e.getMessage(), e);
		}
		return colorSetting ;
    }

    
    private static final String SQL_WHERE_STR =" where " ;
    
    /**
     * @param orgList
     * @param projectList
     * @param isassige
     * @return
     */
    public static IRowSet getCostAccountList(Set orgList ,Set projectList ,boolean isassige ,QueryFieldConditionEnum queryContion ,String condition){
    	
    	CompanyOrgUnitInfo companyInfo = SysContext.getSysContext().getCurrentFIUnit();
    	if(companyInfo == null){
    		return null ;
    	}

    	StringBuffer assignedSql = new StringBuffer();
    	
    	boolean isFrist = false ;
    	
    	String filterString = " " ;
    	
    	if(queryContion == null){
    		filterString = "  " ;
    	}else if(QueryFieldConditionEnum.NAME_QUERY.equals(queryContion)){
    		filterString = " and a.fname_l2 like  '%"+condition+"%'" ;
    	}else if(QueryFieldConditionEnum.NUM_QUERY.equals(queryContion)&&!FDCHelper.isEmpty(condition)){
    		filterString = " and a.fnumber like  '%"+condition+"%' " ;
    	}else if(QueryFieldConditionEnum.NUM_NAME_QUEYR.equals(queryContion)){
    		filterString = " and ( a.fnumber like  '%"+condition+"%' or "   ;
    		filterString += " a.fname_l2 like  '%"+condition+"%' )" ;
    	}
    	
    	
    	if(isassige){
    		assignedSql.append(" select distinct a.fid fid , a.flongNumber FlongNumber," );
    		assignedSql.append(" a.fname_l2  fname_l2, b.fname_l2 FcreateOrg," );
    		assignedSql.append(" a.fisCanadd  fisCanadd," );
    		assignedSql.append(" a.flevel flevel, a.fisleaf fisleaf" );
    		assignedSql.append(" from t_fdc_costaccount a" );
    		assignedSql.append(" left join T_ORG_BaseUnit b on a.FCreateOrg = b.fid");
    		assignedSql.append(" where  FIsEnable = 1 ");
    		assignedSql.append( filterString);
    		//�����������Ϊ�˹��˵�ǰ���Ż������֯������Ŀ�Ŀ�����ǵ��ڼ���ʱ������������֯����Ŀ�Ŀ���ڲ�����֯Ҳ���������ŷ���Ŀ�Ŀ������ע�� by hpw
//    		assignedSql.append(" and FFullOrgUnit = '"+companyInfo.getId().toString()+"'" );
    		
    		//�������֯�Ŀ�Ŀ�������ڼ��ż�������֯������������
    		if(!orgList.isEmpty()){
    			assignedSql.append(" and " );
    			assignedSql.append(" ( " );
    			assignedSql.append(" exists (select fid " );
    			assignedSql.append(" from t_fdc_costaccount t1 ");
    			assignedSql.append(" where t1.flongnumber = a.flongnumber" );
    			assignedSql.append(" and  t1.fsrccostaccountid = a.fid " );
    			assignedSql.append(" and  t1.fsrccostaccountid is not null " );//Ϊ����Ϊ��֯�Լ������Ŀ�Ŀ
    			
    			assignedSql.append(" and FFullOrgUnit in "+SetConvertToString(orgList)+") " );
    			
    			isFrist = true ;
    			
    		}
    		//�������Ŀ�Ŀ�Ŀ�������ڼ��ż�������֯������������
    		if(!projectList.isEmpty()){
    			
    			if(!isFrist){
    				assignedSql.append(" and" );
    				assignedSql.append(" ( " );
    			}else{
    				assignedSql.append(" or" );
    			}
    			
    			assignedSql.append(" exists (select fid " );
    			assignedSql.append(" from t_fdc_costaccount t2 " );
    			assignedSql.append(" where t2.flongnumber = a.flongnumber " );
    			assignedSql.append(" and  t2.fsrccostaccountid = a.fid " );
    			assignedSql.append(" and  t2.fsrccostaccountid is not null " );//Ϊ����Ϊ��֯�Լ������Ŀ�Ŀ
    			assignedSql.append(" and FCurProject in "+SetConvertToString(projectList)+" )");
    			
    		}
    		if(!orgList.isEmpty() || !projectList.isEmpty() ){
    			assignedSql.append(" ) " );
    		}
    		
    	}else{
    		assignedSql.append(" select distinct a.fid fid, a.flongNumber FlongNumber," );
    		assignedSql.append(" a.fname_l2  fname_l2, b.fname_l2 FcreateOrg," );
    		assignedSql.append(" a.fisCanadd  fisCanadd," );
    		assignedSql.append(" a.flevel flevel, a.fisleaf fisleaf" );
    		assignedSql.append(" from t_fdc_costaccount a" );
    		assignedSql.append(" left join T_ORG_BaseUnit b on a.FCreateOrg = b.fid");
    		assignedSql.append(" where  FIsEnable = 1 ");
    		assignedSql.append( filterString);
    		assignedSql.append(" and FFullOrgUnit = '"+companyInfo.getId().toString()+"'" );
    		
    		if(!orgList.isEmpty()){
    			assignedSql.append(" and " );
    			assignedSql.append(" ( " );
    			
    			assignedSql.append(getQueryStrByOrgSet(orgList));
    			
    			isFrist = true ;
    			
    		}
    		if(!projectList.isEmpty()){

    			if(!isFrist){
    				assignedSql.append(" and" );
    				assignedSql.append(" ( " );
    			}else{
    				assignedSql.append(" or" );
    			}
    			
    			assignedSql.append(getQueryStrByProjectSet(projectList));
    			
    		}
    		if(!orgList.isEmpty() || !projectList.isEmpty() ){
    			assignedSql.append(" ) " );
    		}
    	}
    	assignedSql.append(" order by a.flongNumber,a.fisCanadd ");
    	
		try {
			ISQLExecutor exe = SQLExecutorFactory.getRemoteInstance(assignedSql.toString());
			IRowSet rowSetList = exe.executeSQL();

			return rowSetList;
			
		} catch (BOSException e) {
			// @AbortException
			logger.error(e.getMessage(), e);
			return null ;
		}
    }
    
    
    public static IRowSet getAllCostAccountList(){
    	
    	CompanyOrgUnitInfo companyInfo = SysContext.getSysContext().getCurrentFIUnit();
    	if(companyInfo == null){
    		return null ;
    	}

    	StringBuffer assignedSql = new StringBuffer();
   	
    	assignedSql.append(" select distinct a.fid fid , a.flongNumber FlongNumber," );
    	assignedSql.append(" a.fname_l2  fname_l2, b.fname_l2 FcreateOrg," );
    	assignedSql.append(" a.fisCanadd  fisCanadd," );
    	assignedSql.append(" a.flevel flevel, a.fisleaf fisleaf" );
    	assignedSql.append(" from t_fdc_costaccount a" );
    	assignedSql.append(" left join T_ORG_BaseUnit b on a.FCreateOrg = b.fid");
    	assignedSql.append(" where  FIsEnable = 1 ");
    	assignedSql.append(" and FFullOrgUnit = '"+companyInfo.getId().toString()+"'" );
    	assignedSql.append(" order by a.flongNumber ");
    	
		try {
			ISQLExecutor exe = SQLExecutorFactory.getRemoteInstance(assignedSql.toString());
			IRowSet rowSetList = exe.executeSQL();

			return rowSetList;
			
		} catch (BOSException e) {
			// @AbortException
			logger.error(e.getMessage(), e);
			return null ;
		}
    }
    
    private static String getQueryStrByOrgSet(Set orgIdsSet){
    	Iterator iter = orgIdsSet.iterator();
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" ( ");
    	int frist = 0 ;
    	
    	while(iter.hasNext()){
    		String id = (String) iter.next();
    		if(frist > 0){
    			sb.append(" or ");
    			
    		}
    		sb.append(getOrgQueryString(id));
    		frist ++ ;
    		
    	}
    	sb.append(" ) ");
    	
    	return sb.toString();
    }
    
    private static String getQueryStrByProjectSet(Set projectIdsSet){
    	Iterator iter = projectIdsSet.iterator();
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" ( ");
    	int frist = 0 ;
    	
    	while(iter.hasNext()){
    		String id = (String) iter.next();
    		if(frist > 0){
    			sb.append(" or ");
    			
    		}
    		sb.append(getProjectQueryString(id));
    		frist ++ ;
    		
    	}
    	sb.append(" ) ");
    	
    	return sb.toString();
    }
    
    
    private static String getOrgQueryString(String fullOrgUnitId){
    	String sql = "(not exists (select fsrccostaccountid " +
    	" from t_fdc_costaccount t1 "+
    	" where t1.flongnumber = a.flongnumber " +
    	" and fsrccostaccountid is not null "+
    	//" and t1.fsrccostaccountid = a.fid "+
    	" and FFullOrgUnit = '"+fullOrgUnitId+"'))" ;
    	
    	return sql ;
    }
    
    private static String getProjectQueryString(String projectId){
    	String sql = "(not exists (select fsrccostaccountid " +
    	" from t_fdc_costaccount t1 "+
    	" where t1.flongnumber = a.flongnumber " +
    	" and fsrccostaccountid is not null "+
    	//" and t1.fsrccostaccountid = a.fid "+
    	" and fcurproject = '"+projectId+"'))" ;
    	
    	return sql ;
    }
    
    
	public static String SetConvertToString(Set idSet){
		
		if(idSet == null || idSet.isEmpty()){
			return "" ;
		}
		StringBuffer filter = new StringBuffer();
		filter.append("( ");
		Iterator iter = idSet.iterator();
		int i=0;
		int size = idSet.size();
		while(iter.hasNext()){
			String id = (String)iter.next();
			filter.append("'").append(id).append("'");
			if(i<size-1){
				filter.append(",");
			}
			i++;
		}
		filter.append(" ) ");
		return filter.toString();
		
	}
    
	
}
