package com.kingdee.eas.fdc.dahuaschedule.schedule.app;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleEntryInfo;
import com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleFacadeFactory;
import com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleFactory;
import com.kingdee.eas.fdc.dahuaschedule.schedule.DahuaScheduleInfo;
import com.kingdee.jdbc.rowset.IRowSet;

import java.lang.String;
import java.math.BigDecimal;

public class DahuaScheduleFacadeControllerBean extends AbstractDahuaScheduleFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.dahuaschedule.schedule.app.DahuaScheduleFacadeControllerBean");
    String tempTable = "t_sch_temp";
    
    /**
     * 生成中间表数据
     */
    protected String[] _createTempData(Context ctx, String xml)
    		throws BOSException {
    	try {
    		FDCSQLBuilder bilder = new FDCSQLBuilder(ctx);
			Document document = DocumentHelper.parseText(xml);
			Element rootElement = document.getRootElement();
			List<Element> tempDatas = rootElement.elements("tempData");
			
			Iterator<Element> iterator = tempDatas.iterator();
			
			while(iterator.hasNext()) {
				Element tempData = iterator.next();
				Element billHead = tempData.element("billHead");
				String number = billHead.elementTextTrim("number");
				String name = billHead.elementTextTrim("name");
				String version = billHead.elementTextTrim("version");
				String projectNumber = billHead.elementTextTrim("projectNumber");
				String projectName = billHead.elementTextTrim("projectName");
				String bizDate = billHead.elementTextTrim("bizDate");
				
				Element billEntrys = tempData.element("billEntrys");
				List<Element> entrys = billEntrys.elements("entry");
				
				Iterator<Element> itEntry = entrys.iterator();
				while(itEntry.hasNext()) {
					Element entry = itEntry.next();
					String seq = entry.elementTextTrim("seq");
					String taskNumber = entry.elementTextTrim("taskNumber");
					String taskName = entry.elementTextTrim("taskName");
					String longNumber = entry.elementTextTrim("longNumber");
					String isLeaf = entry.elementTextTrim("isLeaf");
					String level = entry.elementTextTrim("level");
					String times = entry.elementTextTrim("times");
					String completeRate = entry.elementTextTrim("completeRate");
					String startDate = entry.elementTextTrim("startDate");
					String endDate = entry.elementTextTrim("endDate");
					String manager = entry.elementTextTrim("manager");
					String department = entry.elementTextTrim("department");
					String actStartDate = entry.elementTextTrim("actStartDate");
					String actEndDate = entry.elementTextTrim("actEndDate");
					
					StringBuilder insertSql = new StringBuilder("");
					insertSql.append(" insert into "+tempTable+"");
					insertSql.append("(");
					insertSql.append("FNumber,FName,FVersion,FProjectNumber,FprojectName,FbizDate,FSeq,FTaskNumber,FTaskName,FLongNumber,FIsLeaf,");
					insertSql.append("FLevel,FTimes,FcompleteRate,FstartDate,FendDate,Fmanager, Fdepartment,FactStartDate,FactEndDate");
					insertSql.append(")");
					insertSql.append(" values");
					insertSql.append(" (");
					insertSql.append("'"+number+"','"+name+"','"+version+"','"+projectNumber+"','"+projectName+"','"+bizDate+"',");
					insertSql.append("'"+seq+"','"+taskNumber+"','"+taskName+"','"+longNumber+"','"+isLeaf+"','"+level+"','"+times+"','"+completeRate+"',");
					insertSql.append("'"+startDate+"','"+endDate+"','"+manager+"','"+department+"','"+actStartDate+"','"+actEndDate+"'");
					insertSql.append(" )");
					
					bilder.appendSql(insertSql.toString());
				}
				bilder.execute();
				bilder.clear();
			}
		} catch (DocumentException e) {
			return new String[] {"N", "xml文件有误"};
		}
    	return new String[] {"Y"};
    }
    
    /**
     * 创建进度单据
     */
    protected String[] _createScheduleBill(Context ctx) throws BOSException {
    	try {
			Map scheduleData = getScheduleData(ctx);
			Set keySet = scheduleData.keySet();
			Iterator iterator = keySet.iterator();
			
			while(iterator.hasNext()) {
				Map map = (Map) scheduleData.get(iterator.next().toString());
				
				String number = (String) map.get("FNUMBER");
				String name = (String) map.get("FNAME");
				BigDecimal version = (BigDecimal) map.get("FVERSION");
				String projectNumber = (String) map.get("FPROJECTNUMBER");
				String projectName = (String) map.get("FPROJECTNAME");
				String bizDate = (String) map.get("FBIZDATE");
				
				DahuaScheduleInfo info = new DahuaScheduleInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setNumber(number);
				info.setName(name);
				info.setVersion(version);
				
				CurProjectInfo curProjectInfo = getCurProjectInfo(ctx, projectNumber, projectName);
				if(curProjectInfo == null) {
					return new String[] {"N", "编码为"+projectNumber+"名称为"+projectName+"的工程项目未找到!"};
				}
				info.setProject(curProjectInfo);
				info.setBizDate(parseCustomDateString(bizDate, "yyyy-MM-dd"));
				
				
				List<Object[]> list = (List<Object[]>) map.get("billEntry");
				for(int i = 0; i < list.size(); i++) {
					Object[] entry = list.get(i);
					DahuaScheduleEntryInfo entryInfo = new DahuaScheduleEntryInfo();
					entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
					entryInfo.setSeq((Integer)entry[0]);
					entryInfo.setNumber((String) entry[1]); //任务编码
					entryInfo.setTask((String) entry[2]); //任务名称
					entryInfo.setLongNumber((String) entry[3]); //长编码
					entryInfo.setIsLeaf((Boolean) entry[4]);//是否子节点
					entryInfo.setLevel((Integer) entry[5]); //级次
					entryInfo.setTimes((BigDecimal) entry[6]);//工期
					entryInfo.setCompleteRate((BigDecimal) entry[7]); //完成率
					
					entryInfo.setStartDate(parseCustomDateString((String) entry[8], "yyyy-MM-dd")); //计划开始日期
					entryInfo.setEndDate(parseCustomDateString((String) entry[9], "yyyy-MM-dd")); //计划结束日期
					entryInfo.setManagerText((String) entry[10]); //责任人
					entryInfo.setDepartmentText((String) entry[11]); //责任部门
					entryInfo.setActStartDate(parseCustomDateString((String) entry[12], "yyyy-MM-dd")); //实际开始日期
					entryInfo.setActCompleteDate(parseCustomDateString((String) entry[13], "yyyy-MM-dd"));//实际完成日期
					
					info.getEntrys().add(entryInfo);
				}
				DahuaScheduleFactory.getLocalInstance(ctx).save(info);
			}
			
			//更新中间表数据
			Iterator iterator2 = keySet.iterator();
			StringBuilder numberSql = new StringBuilder("");
			while(iterator2.hasNext()) {
				numberSql.append("'"+iterator2.next()+"',");
			}
			if(!numberSql.toString().trim().equals("")) {
				numberSql.deleteCharAt(numberSql.length()-1);
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				StringBuilder sb = new StringBuilder("");
				sb.append(" update "+tempTable+" set fflag='1' where fnumber in ("+numberSql+")");
				builder.appendSql(sb.toString());
				builder.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    	return new String[] {"Y"};
    }
    
    /**
     * 抓取临时表未生成过进度单据的数据
     * @param ctx
     * @return
     * @throws BOSException
     * @throws SQLException
     */
    private Map getScheduleData(Context ctx) throws BOSException, SQLException {
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	StringBuilder sb = new StringBuilder("");
    	sb.append(" select * from "+tempTable+" where fflag is null order by fnumber, fseq");
    	builder.appendSql(sb.toString());
    	IRowSet rowSet = builder.executeQuery();

    	while(rowSet.next()) {
    		if(map.get(rowSet.getString("FNUMBER")) == null) {
    			Map billHead = new HashMap();
    			billHead.put("FNUMBER", rowSet.getString("FNUMBER"));
    			billHead.put("FNAME", rowSet.getString("FNAME"));
    			billHead.put("FVERSION", rowSet.getBigDecimal("FVERSION"));
    			billHead.put("FPROJECTNUMBER", rowSet.getString("FPROJECTNUMBER"));
    			billHead.put("FPROJECTNAME", rowSet.getString("FPROJECTNAME"));
    			billHead.put("FBIZDATE", rowSet.getString("FBIZDATE"));
    			
    			List<Object[]> entryList = new ArrayList<Object[]>();
    			Object[] entry = new Object[14];
    			entry[0] = rowSet.getInt("FSEQ");
    			entry[1] = rowSet.getString("FTASKNUMBER");
    			entry[2] = rowSet.getString("FTASKNAME");
    			entry[3] = rowSet.getString("FLongNumber");
    			entry[4] = rowSet.getBoolean("FIsLeaf");
    			entry[5] = rowSet.getInt("FLevel");
    			entry[6] = rowSet.getBigDecimal("FTimes");
    			entry[7] = rowSet.getBigDecimal("FcompleteRate");
    			entry[8] = rowSet.getString("FstartDate");
    			entry[9] = rowSet.getString("FendDate");
    			entry[10] = rowSet.getString("Fmanager");
    			entry[11] = rowSet.getString("Fdepartment");
    			entry[12] = rowSet.getString("FactStartDate");
    			entry[13] = rowSet.getString("FactEndDate");
    			entryList.add(entry);
    			
    			billHead.put("billEntry", entryList);
    			map.put(rowSet.getString("FNUMBER"), billHead);
    		} else {
    			Map billMap = (Map) map.get(rowSet.getString("FNUMBER"));
    			List<Object[]> entryList = (ArrayList<Object[]>) billMap.get("billEntry");
    			Object[] entry = new Object[14];
    			entry[0] = rowSet.getInt("FSEQ");
    			entry[1] = rowSet.getString("FTASKNUMBER");
    			entry[2] = rowSet.getString("FTASKNAME");
    			entry[3] = rowSet.getString("FLongNumber");
    			entry[4] = rowSet.getBoolean("FIsLeaf");
    			entry[5] = rowSet.getInt("FLevel");
    			entry[6] = rowSet.getBigDecimal("FTimes");
    			entry[7] = rowSet.getBigDecimal("FcompleteRate");
    			entry[8] = rowSet.getString("FstartDate");
    			entry[9] = rowSet.getString("FendDate");
    			entry[10] = rowSet.getString("Fmanager");
    			entry[11] = rowSet.getString("Fdepartment");
    			entry[12] = rowSet.getString("FactStartDate");
    			entry[13] = rowSet.getString("FactEndDate");
    			entryList.add(entry);
    		}
    	}

    	return map;
    }
    
    /**
     * String格式转换成Date日期 
     * @param sdate 字符日期2013-08-16
     * @param format yyyy-MM-dd
     */
    public static Date parseCustomDateString(String sDate, String format)
    {
    	DateFormat dateFormat = new SimpleDateFormat(format);
    	Date d = null;
    	try {
    		d = dateFormat.parse(sDate);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return d;
    }
    
    /**
     * 根据名称编码获取工程项目
     * @param ctx
     * @param number
     * @param name
     * @return
     */
    private CurProjectInfo getCurProjectInfo(Context ctx, String number, String name) {
    	CurProjectInfo project  = null;
    	try {
//    		String oql = "where number='"+number+"' and name='"+name+"'";
//			ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
//			if(iCurProject.exists(oql)) 
//				project = iCurProject.getCurProjectInfo(oql);
    		String sql = "select fid from t_fdc_curProject where fnumber='"+number+"' and fname_l2='"+name+"'";
    		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    		builder.appendSql(sql);
    		IRowSet rowSet = builder.executeQuery();
    		
    		while(rowSet.next()) {
    			ICurProject iCurProject = CurProjectFactory.getLocalInstance(ctx);
    			String id = rowSet.getString("fid");
    			SelectorItemCollection sic = new SelectorItemCollection();
    			sic.add(new SelectorItemInfo("id"));
    			sic.add(new SelectorItemInfo("number"));
    			sic.add(new SelectorItemInfo("name"));
    			project = iCurProject.getCurProjectInfo(new ObjectUuidPK(id), sic);
    			break;
    		}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return project;
    }
}