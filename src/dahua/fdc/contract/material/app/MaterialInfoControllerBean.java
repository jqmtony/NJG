/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.freechart.data.time.Day;
import com.kingdee.bos.ctrl.freechart.data.time.TimeSeries;
import com.kingdee.bos.ctrl.freechart.data.time.TimeSeriesCollection;
import com.kingdee.bos.ctrl.freechart.data.xy.DefaultXYDataset;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialFactory;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.basedata.master.material.UsedStatusEnum;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.material.MaterialInfoCollection;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.fdc.material.MaterialInfoInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述：通用DAO类<p>
 * @author luoxiaolong
 * @version EAS 7.0
 * @see <>
 */
public class MaterialInfoControllerBean extends AbstractMaterialInfoControllerBean
{
	/**缺省版本标识 */
	private static final long serialVersionUID = 1L;
	
	/**日志*/
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialInfoControllerBean");
	
	/**声明资源文件位置*/
	private static final String MATERIALINFO_RESOURCEPATH = "com.kingdee.eas.fdc.material.MaterialInfoResource";
    
	/**
	 * 
	 * @description	获取分布图数据
	 * @author			刘一珉		
	 * @createDate		2010-12-2
	 * @param	 		materialName 材料名  materalId材料ID
	 * @return			XYDataset jfreechar 输入数据对象			
	 *	
	 * @version		EAS7.0
	 * @see
	 */
	protected XYDataset _getDotDataSet(Context ctx, String materialName,
			String materalId) throws BOSException {
    	/*定义jfreechar接收的数据变量*/
    	DefaultXYDataset dataSet = new DefaultXYDataset();
    	/*创建sqlBuilder*/
    	FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
    	
    	String cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
    	
		sqlBuilder.appendSql("select FQuoteTime, FPrice  from T_MTR_MaterialInfo ");
		sqlBuilder.appendSql("where FMaterialID=? and FMstate='VALID' and FIsEnabled=1 ");
		sqlBuilder.appendSql("and FState = '4AUDITTED' and FControlUnitID=? ");
		sqlBuilder.appendSql("order by FQuoteTime"); 
		
		sqlBuilder.addParam(materalId);
		sqlBuilder.addParam(cuId);

		IRowSet rowSet  = sqlBuilder.executeQuery();
		
		int size = rowSet.size();
		/*定义2维数组*/
		double[][] db = new double[2][size];
    	
    	int i = 0;
    	
		try {//遍历查询结果集
			while (rowSet.next()) {
				/*报价时间*/
				String quoteTime = rowSet.getString("FQuoteTime");
				/*报价*/
				double price = Double.valueOf(rowSet.getString("FPrice")).doubleValue();
				/*转换时间格式*/	
				long timeLong = getTimeMill(quoteTime);
				
				db[0][i] = timeLong;
				
				db[1][i] = (double) price;
			
				i++;
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MsgBox.showError(EASResource.getString(MATERIALINFO_RESOURCEPATH, "mapDataErro"));
		}
    	
    	dataSet.addSeries(materialName,  db);
    	
    	return dataSet;
	}



	/**
	 * 
	 * @description		获取折线图数据
	 * @author				刘一珉		
	 * @createDate			2010-12-2
	 * @param	 			name 材料名  materalId材料ID
	 * @return				XYDataset jfreechar 输入数据对象			
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected XYDataset _getLineDataSet(Context ctx, String name,
			String materalId) throws BOSException {
		
		TimeSeries timeseries = new TimeSeries(name,Day.class);
    	
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		
		String cuId = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
		
		//根据材料ID取图标对应数据
		sqlBuilder
		.appendSql("select distinct to_date(to_char(FQuoteTime,'YYYY-MM-DD')) as dayTime, avg(FPrice) as avgPrice from T_MTR_MaterialInfo"
				+ "	where FMaterialID=? and FMstate='VALID' and FIsEnabled = 1 and FState = '4AUDITTED' and FControlUnitID=? Group by to_date(to_char(FQuoteTime,'YYYY-MM-DD'))");  
		
		sqlBuilder.addParam(materalId);
		sqlBuilder.addParam(cuId);
		sqlBuilder.getTestSql();
		IRowSet rowSet  = sqlBuilder.executeQuery();
		
		//遍历数据集生成jfreeChar数据类型
		try {
			while (rowSet.next()) {
				/*报价时间*/
				String quoteTime = rowSet.getString("dayTime");
				/*平均报价*/
				Double avgPrice = Double.valueOf(rowSet.getString("avgPrice"));
			    String yearString  = quoteTime.substring(0, 4);
			    String monthString  = quoteTime.substring(5, 7);
			    String dayString   = quoteTime.substring(8, 10);
			    int year = Integer.parseInt(yearString);
			    int month = Integer.parseInt(monthString);
			    int day = Integer.parseInt(dayString);
			    timeseries.add(new Day(day,month,year), avgPrice );
			
			}
		
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
			MsgBox.showError(EASResource.getString(MATERIALINFO_RESOURCEPATH, "mapDataErro"));
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			MsgBox.showError(EASResource.getString(MATERIALINFO_RESOURCEPATH, "mapDataErro"));
		}
		
		timeseriescollection.addSeries(timeseries);
        
    	return timeseriescollection;
	}
	
	/**
	 * 
	 * @description		时间转化为格林威治时间
	 * @author				刘一珉	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	private long getTimeMill(String dateTime) throws ParseException {
    	
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
    	
		Calendar lastDate = Calendar.getInstance();   
		
		Date tmpDate= sdf.parse(dateTime);
		
		lastDate.setTime(tmpDate);
		
		return lastDate.getTimeInMillis();

	}

	/**
	 * 
	 * @description		向数据库里添加材料信息数据
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected void _addMaterialData(Context ctx, List params, String sql)
			throws BOSException, EASBizException {
		
		/**PartAMaterialImportorListUI 创建一条SQL语句，插入材料信息表*/
		StringBuffer partAMaterialImportSql = new StringBuffer();
		partAMaterialImportSql.append("INSERT INTO T_MTR_MaterialInfo(FID,FMaterialID,FProjectID,FContractBillID")
		   .append(" ,FPrice,FQuoteTime,FSupplierID,FCreatorID,FCreateTime")
		   .append(" ,FValidDate,FIsLatest,FIsEnabled,FName,FState,FMState")
		   .append(" ,FControlUnitID,FOrgUnitID)")
		   .append(" values(?,?,?,?")
		   .append(" ,?,{ts ?},?,?,{ts ?}")
		   .append(" ,{ts ?},?,?,?,?,?")
		   .append(" ,?,?);");
		
		/**MaterialInfoUI 创建一条SQL语句，插入材料信息表*/
		StringBuffer importMaterialInfoSql = new StringBuffer();
		
		importMaterialInfoSql.append("INSERT INTO T_MTR_MaterialInfo(FID,FMaterialID,FProjectID,FContractBillID")
		   .append(" ,FPrice,FQuoteTime,FSupplierID,FCreatorID,FCreateTime")
		   .append(" ,FValidDate,FIsLatest,FIsEnabled,FName,FState,FMState")
		   .append(" ,FControlUnitID,FOrgUnitID)")
		   .append(" values(?,?,?,?")
		   .append(" ,?,{ts ?},?,?,{ts ?}")
		   .append(" ,{ts ?},?,?,?,?,?")
		   .append(" ,?,?);");
		
		/**MaterialInfoUI 创建一条SQL语句，插入特性指标的值*/
		StringBuffer importMaterialIndexInfoSql = new StringBuffer();
		
		importMaterialIndexInfoSql.append("INSERT INTO T_MTR_MaterialIndexValue(FID,FMaterialID,FMaterialIndexID,Fvalue)")
		      .append(" values(?,?,?,?);");
		
		/**声明一个SQL构建器*/
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		String newSql = null;
		
		if(sql.equals("partAMaterialImportSql")){
			newSql = partAMaterialImportSql.toString();
		}else if(sql.equals("importMaterialInfoSql")){
			newSql = importMaterialInfoSql.toString();
		}else if(sql.equals("importMaterialIndexInfoSql")){
			newSql = importMaterialIndexInfoSql.toString();
		}
		
		/*传入SQL*/
		builder.appendSql(newSql);
		
		/*循环传入参数*/
		for(int i = 0; i < params.size(); i ++){
			builder.appendParam(params.get(i));
		}
		String sql2 = builder.getTestSql();
		
		
		/**获得SQL语句*/
		String perparedStatementSql = builder.getTestSql();
		perparedStatementSql = perparedStatementSql.substring(0, perparedStatementSql.lastIndexOf(";"));
		builder.setPrepareStatementSql(perparedStatementSql);
		String testSql2 = builder.getTestSql();
		
		/*执行命令*/
		builder.executeUpdate();

	}



	/**
	 * 
	 * @description		通用查询
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected List _getMaterialData(Context ctx, Map filters, String tableName,
			List fields) throws BOSException, EASBizException {
		List list = new ArrayList();
		
		/**声明一个SQL构建器*/
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		StringBuffer newSql = new StringBuffer();
		newSql.append("SELECT ");
		
		for(int i = 0; i < fields.size(); i ++){
			if(i < fields.size()-1){
				newSql.append(fields.get(i).toString() + ", ");
			}else{
				newSql.append(fields.get(i).toString());
			}
		}
		
		newSql.append(" FROM ");
		newSql.append(tableName);
		newSql.append(" WHERE 1=1");
		
		Map map = filters;
		Object[] obj = map.keySet().toArray();
				
		for(int i = 0; i < obj.length; i ++){
				newSql.append(" and " + obj[i].toString() + " = ?");
		}
		
		newSql.append(";");
		
		/*传入SQL*/
		builder.appendSql(newSql.toString());

		/*传入参数*/
		for(int i = 0; i < obj.length; i ++){
			builder.appendParam(map.get(obj[i].toString()));
		}
		
		String sql = builder.getTestSql();
		sql = sql.substring(0,sql.indexOf(";"));
		builder.setPrepareStatementSql(sql);
		try {
			/*执行命令*/
			IRowSet rowset = builder.executeQuery();
			while(rowset.next()){
				Map newMap = new HashMap();
				for(int i = 0; i < fields.size(); i ++){
					newMap.put(fields.get(i).toString(),rowset.getString(fields.get(i).toString()));
				}
				list.add(newMap);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return list;
	}



	/**
	 * 
	 * @description		根据bosType，查询bosId
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected String _getBOSID(Context ctx, String bosType) throws BOSException {
		
		/**声明一个返回值变量*/
		String bosID = null;
		/**声明一个SQL构造器*/
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		/*传入SQL*/
		builder.appendSql("SELECT newbosid("+"'" + bosType  + "'" + ")");
		try {
			/*执行*/
			IRowSet rowset = builder.executeQuery();
			
			if(rowset.next()){
				bosID = rowset.getString(1);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return bosID;
	}



	/**
	 * 
	 * @description		获得物料id
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected String _getMaterialID(Context ctx, String id) throws BOSException {
		/**声明一个返回值变量*/
		String materialID = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT")
		   .append(" \"PARTAMATERIAL\".FID AS \"MaterialID\", ")
		   .append(" \"CONTRACTBILL\".FExRate AS \"CONTRACTBILL.EXRATE\", ")
		   .append(" \"CONTRACTBILL\".FMainContractNumber AS \"CONTRACTBILL.MAINCONTRACTNUMBER\", ")
		   .append(" \"CONTRACTBILL\".FCodingNumber AS \"CONTRACTBILL.CODINGNUMBER\", ")
		   .append(" \"CONTRACTBILL\".FState AS \"CONTRACTBILL.STATE\", ")
		   .append(" \"CONTRACTBILL\".FName AS \"CONTRACTBILL.NAME\", ")
		   .append(" \"CONTRACTBILL\".FNumber AS \"CONTRACTBILL.NUMBER\",")
		   .append(" \"PARTAMATERIAL\".FContractBillID AS \"CONTRACTBILL.ID\", ")
		   .append(" \"PARTAMATERIAL\".FCreatorID AS \"CREATOR.ID\", ")
		   .append(" \"CREATOR\".FNumber AS \"CREATOR.NUMBER\", ")
		   .append(" \"CREATOR\".FName_l2 AS \"CREATOR.NAME\", ")
		   .append(" \"ENTRYS\".FID AS \"ENTRYS.ID\",")
		   .append(" \"ENTRYS\".FSeq AS \"ENTRYS.SEQ\", ")
		   .append(" \"ENTRYS\".FMainContractBillId AS \"MAINCONTRACTBILL.ID\",")
		   .append(" \"MAINCONTRACTBILL\".FName AS \"MAINCONTRACTBILL.NAME\", ")
		   .append(" \"MAINCONTRACTBILL\".FNumber AS \"MAINCONTRACTBILL.NUMBER\", ")
		   .append(" \"ENTRYS\".FMaterialId AS \"MATERIAL.ID\",")
		   .append(" \"MATERIAL\".FID AS \"MFID\", ")
		   .append(" \"MATERIAL\".FNumber AS \"MATERIAL.NUMBER\", ")
		   .append(" \"MATERIAL\".FName_l2 AS \"MATERIAL.NAME\",")
		   .append(" \"MATERIAL\".FModel AS \"MATERIAL.MODEL\", ")
		   .append(" \"MATERIAL\".FBaseUnit AS \"BASEUNIT.ID\", ")
		   .append(" \"BASEUNIT\".FNumber AS \"BASEUNIT.NUMBER\",")
		   .append(" \"BASEUNIT\".FName_l2 AS \"BASEUNIT.NAME\",")
		   .append(" \"ENTRYS\".FOriginalAmount AS \"ENTRYS.ORIGINALAMOUNT\",")
		   .append(" \"ENTRYS\".FAmount AS \"ENTRYS.AMOUNT\", ")
		   .append(" \"ENTRYS\".FQuantity AS \"ENTRYS.QUANTITY\",")
		   .append(" \"ENTRYS\".FDescription_l2 AS \"ENTRYS.DESCRIPTION\",")
		   .append(" \"ENTRYS\".FArriveDate AS \"ENTRYS.ARRIVEDATE\",")
		   .append(" \"ENTRYS\".FOriginalPrice AS \"ENTRYS.ORIGINALPRICE\",")
		   .append(" \"ENTRYS\".FPrice AS \"ENTRYS.PRICE\", ")
		   .append(" \"CONTRACTBILL\".FCurrencyID AS \"CURRENCY.ID\",")
		   .append(" \"CURRENCY\".FNumber AS \"CURRENCY.NUMBER\",")
		   .append(" \"CURRENCY\".FName_l2 AS \"CURRENCY.NAME\"")
		   .append(" FROM T_PAM_PartAMaterial AS \"PARTAMATERIAL\"")
		   .append(" LEFT OUTER JOIN T_CON_ContractBill AS \"CONTRACTBILL\"")
		   .append(" ON \"PARTAMATERIAL\".FContractBillID = \"CONTRACTBILL\".FID")
		   .append(" LEFT OUTER JOIN T_PM_User AS \"CREATOR\"")
		   .append(" ON \"PARTAMATERIAL\".FCreatorID = \"CREATOR\".FID")
		   .append(" LEFT OUTER JOIN T_PAM_PartAMaterialEntry AS \"ENTRYS\"")
		   .append(" ON \"PARTAMATERIAL\".FID = \"ENTRYS\".FParentId")
		   .append(" LEFT OUTER JOIN T_BD_Currency AS \"CURRENCY\"")
		   .append(" ON \"CONTRACTBILL\".FCurrencyID = \"CURRENCY\".FID")
		   .append(" LEFT OUTER JOIN T_BD_Material AS \"MATERIAL\"")
		   .append(" ON \"ENTRYS\".FMaterialId = \"MATERIAL\".FID")
		   .append(" LEFT OUTER JOIN T_CON_ContractBill AS \"MAINCONTRACTBILL\"")
		   .append(" ON \"ENTRYS\".FMainContractBillId = \"MAINCONTRACTBILL\".FID")
		   .append(" LEFT OUTER JOIN T_BD_MeasureUnit AS \"BASEUNIT\"")
		   .append(" ON \"MATERIAL\".FBaseUnit = \"BASEUNIT\".FID")
		   .append(" WHERE \"PARTAMATERIAL\".FID=?;");
		
		/**创建SQL构造器*/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*传入SQL语句*/
		builder.appendSql(sql.toString());
		/*传入参数*/
		builder.appendParam(id);
		try {
			String testSql = builder.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf(";"));
			builder.setPrepareStatementSql(testSql);
			/*执行*/
			IRowSet rowset = builder.executeQuery();
			/*取值*/
			while(rowset.next()){
				if(null != rowset.getString("MFID") && !"".equals(rowset.getString("MFID"))){
					materialID = rowset.getString("MFID");
					break ;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			materialID = null;
		}
		return materialID;
	}

	/**
	 * 
	 * @description		根据物料ID查询单价、报价时间、供应商
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return				String	
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected List _getPriceAndDateAndSupplierByMaterialId(Context ctx,
			String materialId) throws BOSException {
		List materialInfoList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select TB.FName_l2 as FName,TS.FPrice,TS.FQuoteTime,TS.FMaterialID from T_BD_Supplier as TB")
		   .append(" right join")
		   .append(" (select TMM.FPrice,TMM.FQuoteTime,TMM.FSupplierID,TMM.FMaterialID from T_MTR_MaterialInfo as TMM) as TS")
		   .append("  on TB.FID=TS.FSupplierID");
		/**创建SQL构造器*/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*传入SQL语句*/
		builder.appendSql(sql.toString());
		/*传入参数*/
		builder.appendParam(materialId);
		try {
			String testSql = builder.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf(";"));
			builder.setPrepareStatementSql(testSql);
			/*执行*/
			IRowSet rowset = builder.executeQuery();
			/*取值*/
			while(rowset.next()){
				Map map = new HashMap();
				map.put("FName", rowset.getString("FName"));
				map.put("FPrice", rowset.getString("FPrice"));
				map.put("FQuoteTime", rowset.getString("FQuoteTime"));
				materialInfoList.add(map);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return materialInfoList;
	}

	/**
	 * 
	 * @description		根据树ID 查询材料并且将材料下的最新信息查询出来
	 * @author				
	 * @createDate			2010-12-2
	 * @param	
	 * @return				String	
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected Map _getMaterialInfoMsg(Context ctx,
			IObjectPK materialID) throws BOSException {
		Map resultMap = new HashMap();
		

		/*
		 * 取出所有的物料信息
		 * 1 利用ORMMaping拼装SQL
		 */
	  EntityViewInfo view  = new EntityViewInfo();
	  SelectorItemCollection sic = new SelectorItemCollection();
	  sic.add(new SelectorItemInfo("*"));
	  sic.add(new SelectorItemInfo("baseUnit.name"));
      view.setSelector(sic);
      FilterInfo filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("materialGroup.id",materialID));
      //R110218-051：未核准物料可在材料信息库查询到。只过滤出核准的材料
      filter.getFilterItems().add(new FilterItemInfo("status", UsedStatusEnum.APPROVED));
      filter.getFilterItems().add(new FilterItemInfo("CU.id",ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()));
      
      view.setFilter(filter);
      /**调用本地方法，得到对象集合*/
      MaterialCollection cols = MaterialFactory.getLocalInstance(ctx).getMaterialCollection(view);
      Set materialIdS = new HashSet();
      MaterialInfo ma = null;
      /*
       * 使用迭代器遍历对象
       */
      for(Iterator it = cols.iterator();it.hasNext();){
    	  ma = (MaterialInfo) it.next();
    	  materialIdS.add(ma.getId().toString());
      }
      resultMap.put("materialInfo",cols);
      /**取出上面所有物料信息的报价信息和供应商信息*/
      view = new EntityViewInfo();
      sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("*"));
      sic.add(new SelectorItemInfo("supplier.name"));
      view.setSelector(sic);
      filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("material.id",materialIdS,CompareType.INCLUDE));
      filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED.getValue()));
      view.setFilter(filter);
      /**得到物料信息报价信息对象*/
      Map map2 = new HashMap();
	  MaterialInfoCollection matreailInfoCols = MaterialInfoFactory.getLocalInstance(ctx).getMaterialInfoCollection(view);
	  /** 遍历报价信息对象*/
	  for(Iterator it1= materialIdS.iterator();it1.hasNext();){
		  String minfoId =it1.next().toString();
		  MaterialInfoInfo minfo = getmatreailInfoValue(minfoId,matreailInfoCols);
		  map2.put(minfoId, minfo);
	  }
		resultMap.put("priceInfo",map2);
		return resultMap;
	}

	/**
	 * 
	 * @description		描述：查询最新报价，用于填充物料的最新信息
	 * @author				
	 * @createDate			2010-12-2
	 * @param	
	 * @return				String	
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	private MaterialInfoInfo getmatreailInfoValue(String id,MaterialInfoCollection matreailInfoCols) {
		 Date date = null;
		 MaterialInfoInfo infos =null;
		 /**遍历传入的报价信息对象*/
		  for(Iterator it1= matreailInfoCols.iterator();it1.hasNext();){
			  MaterialInfoInfo info  =(MaterialInfoInfo) it1.next();
			  /**如果报价信息对象中的物料ID和传入的ID相同将得到的报价信息赋值给报价信息对象*/
			  if(info.getMaterial().getId().toString().equals(id)){
				  /**如果时间为空,将从报价信息中取的的时间赋值给时间对象*/
				  if(date==null){
				    date=info.getQuoteTime();
				    infos =info;
				    /**当前得到的时间早与报价时间中的时间,重新给时间和报价信息对象赋值*/
				  }else if(date.before( info.getQuoteTime())){
					  date=info.getQuoteTime();
					  infos =info;
				  }
			  }
		  }
	  return infos;
	}

	/**
	 * 
	 * @description		查询最新报价时间
	 * @author				
	 * @createDate			2010-12-2
	 * @param	
	 * @return				String	
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected List _getLastestQuoteTime(Context ctx) throws BOSException,
			EASBizException {
		List lastestQuoteTime = new ArrayList();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select max(FQuoteTime) as FMaxDate,FSupplierID from T_MTR_MaterialInfo group by FSupplierID");
		try{
			IRowSet rowset = builder.executeQuery();
			while(rowset.next()){
				Map map = new HashMap();
				map.put("FMaxDate", rowset.getString("FMaxDate").toString());
				map.put("FSupplierID", rowset.getString("FSupplierID").toString());
				lastestQuoteTime.add(map);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return lastestQuoteTime;
	}
	
	/**
	 * @description		查询特性指标
	 * @author				顾蛟
	 * @createDate			2010-11-18
	 * @param				traitIndex特性指标标识字符串，tblMaterialID材料ID
	 * @return				IRowSet返回得到的记录集合		
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IRowSet _selectTraitIndex(Context ctx, String traitIndex,
			IObjectPK tblMaterialID) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		/*查询T_MTR_MaterialIndex表得到特性指标值和名称*/
		sqlBuilder
		.appendSql("select FID,FName_l2 from T_MTR_MaterialIndex where FSource = ? and FMaterialGroupID = ? and FIsEnabled = 1" );
		sqlBuilder.addParam(traitIndex);
		sqlBuilder.addParam(tblMaterialID.toString().trim());
		IRowSet rowSet  = sqlBuilder.executeQuery();
		return rowSet;
	}

	/**
	 * @description		验证材料信息表中是否有重复记录
	 * @author				顾蛟
	 * @createDate			2010-11-18
	 * @param				supplier供应商ID，price价格，pkQuoteTime最新报价时间，materialId材料ID
	 * @return				IRowSet返回查询到的记录集合		
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IRowSet _requiredFields(Context ctx, String supplier,
			BigDecimal price, String pkQuoteTime, String materialId) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);		
		/*查询材料信息表中是否已存在同样的记录*/
		String sqlString = "select FID from T_MTR_MaterialInfo where FSupplierID=? and FPrice=? and CONCAT(CONCAT(CONCAT(CONCAT(to_char(YEAR(FQuoteTime)),'-'),to_char(MONTH(FQuoteTime))),'-'),to_char(DAYOFMONTH(FQuoteTime)))= ? and FMaterialID = ?";
		sqlBuilder.appendSql(sqlString);
		sqlBuilder.addParam(supplier);
		sqlBuilder.addParam(price);
		sqlBuilder.addParam(pkQuoteTime);
		sqlBuilder.addParam(materialId);
		IRowSet rowSet = sqlBuilder.executeQuery();
		if(rowSet.size()==0){
			return null;
		}
		return rowSet;
	}



	/**
	 * @description		删除材料信息表中的记录和相应的特性指标值记录
	 * @author				顾蛟
	 * @createDate			2010-11-18
	 * @param				ID材料信息表的ID
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void _deleteMaterialInfoRecord(Context ctx, IObjectPK ID)
			throws BOSException {
		FDCSQLBuilder builder0 = new FDCSQLBuilder(ctx);
		String sql = "delete from T_MTR_MaterialIndexValue where FMaterialID = ? ";
		builder0.appendSql(sql);
		builder0.addParam(ID.toString().trim());
		builder0.execute();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sqlString = "delete from T_MTR_MaterialInfo where FID=?";
		builder.appendSql(sqlString);
		builder.addParam(ID.toString().trim());
		builder.execute();
	}



	/**
	 * @description		材料信息表插入新记录时，将新记录FIsLatest属性设为1（最新）
	 * @author				顾蛟
	 * @createDate			2010-11-19
	 * @param				materialInfoId材料信息表的ID
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void _updateIsLatest(Context ctx, String materialInfoId)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder0 = new FDCSQLBuilder(ctx);
		String sql = "select FID from T_MTR_MaterialInfo where FSupplierID= ? ";
		builder0.appendSql(sql);
		builder0.addParam(materialInfoId);
		IRowSet rowSet = builder0.executeQuery();
		if(rowSet!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	    	String sqlString = "update T_MTR_MaterialInfo set FIsLatest=0 where FIsLatest=1 and FSupplierID= ? ";
	    	builder.appendSql(sqlString);
	    	builder.addParam(materialInfoId);
	    	builder.execute(); 
		}
		
	}
	
	/**
	 * @description		查询T_MTR_MaterialIndex表得到特性指标值和名称
	 * @author				顾蛟		
	 * @createDate			2010-11-22
	 * @param	
	 * @return				rowSet返回查询到的记录集合		
	 * @version			EAS7.0
	 * @see						
	 */
	protected IRowSet _selectMaterialIndexValue(Context ctx, String traitIndex,
			IObjectPK tblMaterialID) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		/*查询T_MTR_MaterialIndex表得到特性指标值和名称*/
		sqlBuilder
		.appendSql("SELECT a.FName_l2,b.FValue,b.FID  FROM T_MTR_MaterialIndex a,T_MTR_MaterialIndexValue b where  a.FID = b.FMaterialIndexID and a.FSource= ? and FMaterialID= ? and a.FIsEnabled = 1" );
		sqlBuilder.addParam(traitIndex);
		sqlBuilder.addParam(tblMaterialID.toString().trim());
		IRowSet rowSet  = sqlBuilder.executeQuery();
		return rowSet;
	}

	/**
	 * @description		更新特性指标值
	 * @author				顾蛟		
	 * @createDate			2010-11-22
	 * @param		
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected int _updateTraitIndexValue(Context ctx, String id, String value)
			throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("UPDATE T_MTR_MaterialIndexValue SET FValue=? WHERE FID=?;");
		builder.appendParam(value);
		builder.appendParam(id);
		int result = -1;
		try{
			String testSql = builder.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf(";"));
			builder.setPrepareStatementSql(testSql);
			builder.executeUpdate();
			result = 1;
		}catch(Exception ex){
			result = 0;
			logger.error(ex.getMessage());
		}
		return result;
	}
	
	/**
	 * @description		根据materialId和materialIndexId删除指标信息
	 * @author				luoxiaolong		
	 * @createDate			2010-11-22
	 * @param		
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected int _deleteMaterialIndexValue(Context ctx, String materialInfoId,
			String materialIndexId) throws BOSException, EASBizException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("delete from T_MTR_MaterialIndexValue where FMaterialID=? and FMaterialIndexID=?;");
		builder.appendParam(materialInfoId);
		builder.appendParam(materialIndexId);
		
		String testSql = builder.getTestSql();
		testSql = testSql.substring(0,testSql.indexOf(";"));
		builder.setPrepareStatementSql(testSql);
		try{
			builder.execute();
		}catch(Exception e){
			return 0;
		}
		return 1;
	}
	
	/**
	 * @description		获得物料信息集合
	 * @author					
	 * @createDate			2010-11-22
	 * @param		
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	public MaterialInfoCollection getMaterialInfoCollection(Context ctx,
			EntityViewInfo view) throws BOSException {
		return super.getMaterialInfoCollection(ctx, view);
	}

	/**
	 * @description 获得物料组的特性指标和上级指标
	 * @author Owen_wen
	 * @createDate 2011-02-24
	 */
	protected IRowSet _selectTraitAndSuperIndex(Context ctx, String materialGroupLongNumber) throws BOSException, EASBizException {
		// 先获得该物料组的上一级编码
		String preLevelMatGroupNumber = materialGroupLongNumber;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		if (materialGroupLongNumber.lastIndexOf("!") > 0) {
			preLevelMatGroupNumber = preLevelMatGroupNumber.substring(0, materialGroupLongNumber.indexOf("!"));
			/* 查询T_MTR_MaterialIndex表得到特性指标ID和name */
			sqlBuilder.appendSql("select mi.FID, mi.FName_l2 from T_MTR_MaterialIndex mi ");
			sqlBuilder.appendSql("inner join t_bd_materialGroup mg on mg.fid = mi.FMaterialGroupID ");
			sqlBuilder.appendSql(" where mi.FSource <>'BASEINDEX' and mi.FIsEnabled = 1 and (mg.FLongNumber = ? or mg.FLongNumber = ?)");
			sqlBuilder.addParam(materialGroupLongNumber);
			sqlBuilder.addParam(preLevelMatGroupNumber);
		} else { // 物料组如果是根节点
			sqlBuilder.appendSql("select mi.FID, mi.FName_l2 from T_MTR_MaterialIndex mi ");
			sqlBuilder.appendSql("inner join t_bd_materialGroup mg on mg.fid = mi.FMaterialGroupID ");
			sqlBuilder.appendSql(" where mi.FSource <>'BASEINDEX' and mi.FIsEnabled = 1 and mg.FLongNumber = ?");
			sqlBuilder.addParam(materialGroupLongNumber);
		}
		IRowSet rowSet = sqlBuilder.executeQuery();
		return rowSet;
	}

}