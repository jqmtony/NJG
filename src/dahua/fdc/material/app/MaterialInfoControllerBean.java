/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
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
 * ������ͨ��DAO��<p>
 * @author luoxiaolong
 * @version EAS 7.0
 * @see <>
 */
public class MaterialInfoControllerBean extends AbstractMaterialInfoControllerBean
{
	/**ȱʡ�汾��ʶ */
	private static final long serialVersionUID = 1L;
	
	/**��־*/
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.material.app.MaterialInfoControllerBean");
	
	/**������Դ�ļ�λ��*/
	private static final String MATERIALINFO_RESOURCEPATH = "com.kingdee.eas.fdc.material.MaterialInfoResource";
    
	/**
	 * 
	 * @description	��ȡ�ֲ�ͼ����
	 * @author			��һ��		
	 * @createDate		2010-12-2
	 * @param	 		materialName ������  materalId����ID
	 * @return			XYDataset jfreechar �������ݶ���			
	 *	
	 * @version		EAS7.0
	 * @see
	 */
	protected XYDataset _getDotDataSet(Context ctx, String materialName,
			String materalId) throws BOSException {
    	/*����jfreechar���յ����ݱ���*/
    	DefaultXYDataset dataSet = new DefaultXYDataset();
    	/*����sqlBuilder*/
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
		/*����2ά����*/
		double[][] db = new double[2][size];
    	
    	int i = 0;
    	
		try {//������ѯ�����
			while (rowSet.next()) {
				/*����ʱ��*/
				String quoteTime = rowSet.getString("FQuoteTime");
				/*����*/
				double price = Double.valueOf(rowSet.getString("FPrice")).doubleValue();
				/*ת��ʱ���ʽ*/	
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
	 * @description		��ȡ����ͼ����
	 * @author				��һ��		
	 * @createDate			2010-12-2
	 * @param	 			name ������  materalId����ID
	 * @return				XYDataset jfreechar �������ݶ���			
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
		
		//���ݲ���IDȡͼ���Ӧ����
		sqlBuilder
		.appendSql("select distinct to_date(to_char(FQuoteTime,'YYYY-MM-DD')) as dayTime, avg(FPrice) as avgPrice from T_MTR_MaterialInfo"
				+ "	where FMaterialID=? and FMstate='VALID' and FIsEnabled = 1 and FState = '4AUDITTED' and FControlUnitID=? Group by to_date(to_char(FQuoteTime,'YYYY-MM-DD'))");  
		
		sqlBuilder.addParam(materalId);
		sqlBuilder.addParam(cuId);
		sqlBuilder.getTestSql();
		IRowSet rowSet  = sqlBuilder.executeQuery();
		
		//�������ݼ�����jfreeChar��������
		try {
			while (rowSet.next()) {
				/*����ʱ��*/
				String quoteTime = rowSet.getString("dayTime");
				/*ƽ������*/
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
	 * @description		ʱ��ת��Ϊ��������ʱ��
	 * @author				��һ��	
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
	 * @description		�����ݿ�����Ӳ�����Ϣ����
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
		
		/**PartAMaterialImportorListUI ����һ��SQL��䣬���������Ϣ��*/
		StringBuffer partAMaterialImportSql = new StringBuffer();
		partAMaterialImportSql.append("INSERT INTO T_MTR_MaterialInfo(FID,FMaterialID,FProjectID,FContractBillID")
		   .append(" ,FPrice,FQuoteTime,FSupplierID,FCreatorID,FCreateTime")
		   .append(" ,FValidDate,FIsLatest,FIsEnabled,FName,FState,FMState")
		   .append(" ,FControlUnitID,FOrgUnitID)")
		   .append(" values(?,?,?,?")
		   .append(" ,?,{ts ?},?,?,{ts ?}")
		   .append(" ,{ts ?},?,?,?,?,?")
		   .append(" ,?,?);");
		
		/**MaterialInfoUI ����һ��SQL��䣬���������Ϣ��*/
		StringBuffer importMaterialInfoSql = new StringBuffer();
		
		importMaterialInfoSql.append("INSERT INTO T_MTR_MaterialInfo(FID,FMaterialID,FProjectID,FContractBillID")
		   .append(" ,FPrice,FQuoteTime,FSupplierID,FCreatorID,FCreateTime")
		   .append(" ,FValidDate,FIsLatest,FIsEnabled,FName,FState,FMState")
		   .append(" ,FControlUnitID,FOrgUnitID)")
		   .append(" values(?,?,?,?")
		   .append(" ,?,{ts ?},?,?,{ts ?}")
		   .append(" ,{ts ?},?,?,?,?,?")
		   .append(" ,?,?);");
		
		/**MaterialInfoUI ����һ��SQL��䣬��������ָ���ֵ*/
		StringBuffer importMaterialIndexInfoSql = new StringBuffer();
		
		importMaterialIndexInfoSql.append("INSERT INTO T_MTR_MaterialIndexValue(FID,FMaterialID,FMaterialIndexID,Fvalue)")
		      .append(" values(?,?,?,?);");
		
		/**����һ��SQL������*/
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		String newSql = null;
		
		if(sql.equals("partAMaterialImportSql")){
			newSql = partAMaterialImportSql.toString();
		}else if(sql.equals("importMaterialInfoSql")){
			newSql = importMaterialInfoSql.toString();
		}else if(sql.equals("importMaterialIndexInfoSql")){
			newSql = importMaterialIndexInfoSql.toString();
		}
		
		/*����SQL*/
		builder.appendSql(newSql);
		
		/*ѭ���������*/
		for(int i = 0; i < params.size(); i ++){
			builder.appendParam(params.get(i));
		}
		String sql2 = builder.getTestSql();
		
		
		/**���SQL���*/
		String perparedStatementSql = builder.getTestSql();
		perparedStatementSql = perparedStatementSql.substring(0, perparedStatementSql.lastIndexOf(";"));
		builder.setPrepareStatementSql(perparedStatementSql);
		String testSql2 = builder.getTestSql();
		
		/*ִ������*/
		builder.executeUpdate();

	}



	/**
	 * 
	 * @description		ͨ�ò�ѯ
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
		
		/**����һ��SQL������*/
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
		
		/*����SQL*/
		builder.appendSql(newSql.toString());

		/*�������*/
		for(int i = 0; i < obj.length; i ++){
			builder.appendParam(map.get(obj[i].toString()));
		}
		
		String sql = builder.getTestSql();
		sql = sql.substring(0,sql.indexOf(";"));
		builder.setPrepareStatementSql(sql);
		try {
			/*ִ������*/
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
	 * @description		����bosType����ѯbosId
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected String _getBOSID(Context ctx, String bosType) throws BOSException {
		
		/**����һ������ֵ����*/
		String bosID = null;
		/**����һ��SQL������*/
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		/*����SQL*/
		builder.appendSql("SELECT newbosid("+"'" + bosType  + "'" + ")");
		try {
			/*ִ��*/
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
	 * @description		�������id
	 * @author				luoxiaolong	
	 * @createDate			2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected String _getMaterialID(Context ctx, String id) throws BOSException {
		/**����һ������ֵ����*/
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
		
		/**����SQL������*/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*����SQL���*/
		builder.appendSql(sql.toString());
		/*�������*/
		builder.appendParam(id);
		try {
			String testSql = builder.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf(";"));
			builder.setPrepareStatementSql(testSql);
			/*ִ��*/
			IRowSet rowset = builder.executeQuery();
			/*ȡֵ*/
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
	 * @description		��������ID��ѯ���ۡ�����ʱ�䡢��Ӧ��
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
		/**����SQL������*/
		FDCSQLBuilder builder = new FDCSQLBuilder();
		/*����SQL���*/
		builder.appendSql(sql.toString());
		/*�������*/
		builder.appendParam(materialId);
		try {
			String testSql = builder.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf(";"));
			builder.setPrepareStatementSql(testSql);
			/*ִ��*/
			IRowSet rowset = builder.executeQuery();
			/*ȡֵ*/
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
	 * @description		������ID ��ѯ���ϲ��ҽ������µ�������Ϣ��ѯ����
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
		 * ȡ�����е�������Ϣ
		 * 1 ����ORMMapingƴװSQL
		 */
	  EntityViewInfo view  = new EntityViewInfo();
	  SelectorItemCollection sic = new SelectorItemCollection();
	  sic.add(new SelectorItemInfo("*"));
	  sic.add(new SelectorItemInfo("baseUnit.name"));
      view.setSelector(sic);
      FilterInfo filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("materialGroup.id",materialID));
      //R110218-051��δ��׼���Ͽ��ڲ�����Ϣ���ѯ����ֻ���˳���׼�Ĳ���
      filter.getFilterItems().add(new FilterItemInfo("status", UsedStatusEnum.APPROVED));
      filter.getFilterItems().add(new FilterItemInfo("CU.id",ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()));
      
      view.setFilter(filter);
      /**���ñ��ط������õ����󼯺�*/
      MaterialCollection cols = MaterialFactory.getLocalInstance(ctx).getMaterialCollection(view);
      Set materialIdS = new HashSet();
      MaterialInfo ma = null;
      /*
       * ʹ�õ�������������
       */
      for(Iterator it = cols.iterator();it.hasNext();){
    	  ma = (MaterialInfo) it.next();
    	  materialIdS.add(ma.getId().toString());
      }
      resultMap.put("materialInfo",cols);
      /**ȡ����������������Ϣ�ı�����Ϣ�͹�Ӧ����Ϣ*/
      view = new EntityViewInfo();
      sic = new SelectorItemCollection();
      sic.add(new SelectorItemInfo("*"));
      sic.add(new SelectorItemInfo("supplier.name"));
      view.setSelector(sic);
      filter = new FilterInfo();
      filter.getFilterItems().add(new FilterItemInfo("material.id",materialIdS,CompareType.INCLUDE));
      filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED.getValue()));
      view.setFilter(filter);
      /**�õ�������Ϣ������Ϣ����*/
      Map map2 = new HashMap();
	  MaterialInfoCollection matreailInfoCols = MaterialInfoFactory.getLocalInstance(ctx).getMaterialInfoCollection(view);
	  /** ����������Ϣ����*/
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
	 * @description		��������ѯ���±��ۣ�����������ϵ�������Ϣ
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
		 /**��������ı�����Ϣ����*/
		  for(Iterator it1= matreailInfoCols.iterator();it1.hasNext();){
			  MaterialInfoInfo info  =(MaterialInfoInfo) it1.next();
			  /**���������Ϣ�����е�����ID�ʹ����ID��ͬ���õ��ı�����Ϣ��ֵ��������Ϣ����*/
			  if(info.getMaterial().getId().toString().equals(id)){
				  /**���ʱ��Ϊ��,���ӱ�����Ϣ��ȡ�ĵ�ʱ�丳ֵ��ʱ�����*/
				  if(date==null){
				    date=info.getQuoteTime();
				    infos =info;
				    /**��ǰ�õ���ʱ�����뱨��ʱ���е�ʱ��,���¸�ʱ��ͱ�����Ϣ����ֵ*/
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
	 * @description		��ѯ���±���ʱ��
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
	 * @description		��ѯ����ָ��
	 * @author				����
	 * @createDate			2010-11-18
	 * @param				traitIndex����ָ���ʶ�ַ�����tblMaterialID����ID
	 * @return				IRowSet���صõ��ļ�¼����		
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IRowSet _selectTraitIndex(Context ctx, String traitIndex,
			IObjectPK tblMaterialID) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		/*��ѯT_MTR_MaterialIndex��õ�����ָ��ֵ������*/
		sqlBuilder
		.appendSql("select FID,FName_l2 from T_MTR_MaterialIndex where FSource = ? and FMaterialGroupID = ? and FIsEnabled = 1" );
		sqlBuilder.addParam(traitIndex);
		sqlBuilder.addParam(tblMaterialID.toString().trim());
		IRowSet rowSet  = sqlBuilder.executeQuery();
		return rowSet;
	}

	/**
	 * @description		��֤������Ϣ�����Ƿ����ظ���¼
	 * @author				����
	 * @createDate			2010-11-18
	 * @param				supplier��Ӧ��ID��price�۸�pkQuoteTime���±���ʱ�䣬materialId����ID
	 * @return				IRowSet���ز�ѯ���ļ�¼����		
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IRowSet _requiredFields(Context ctx, String supplier,
			BigDecimal price, String pkQuoteTime, String materialId) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);		
		/*��ѯ������Ϣ�����Ƿ��Ѵ���ͬ���ļ�¼*/
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
	 * @description		ɾ��������Ϣ���еļ�¼����Ӧ������ָ��ֵ��¼
	 * @author				����
	 * @createDate			2010-11-18
	 * @param				ID������Ϣ���ID
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
	 * @description		������Ϣ������¼�¼ʱ�����¼�¼FIsLatest������Ϊ1�����£�
	 * @author				����
	 * @createDate			2010-11-19
	 * @param				materialInfoId������Ϣ���ID
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
	 * @description		��ѯT_MTR_MaterialIndex��õ�����ָ��ֵ������
	 * @author				����		
	 * @createDate			2010-11-22
	 * @param	
	 * @return				rowSet���ز�ѯ���ļ�¼����		
	 * @version			EAS7.0
	 * @see						
	 */
	protected IRowSet _selectMaterialIndexValue(Context ctx, String traitIndex,
			IObjectPK tblMaterialID) throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		/*��ѯT_MTR_MaterialIndex��õ�����ָ��ֵ������*/
		sqlBuilder
		.appendSql("SELECT a.FName_l2,b.FValue,b.FID  FROM T_MTR_MaterialIndex a,T_MTR_MaterialIndexValue b where  a.FID = b.FMaterialIndexID and a.FSource= ? and FMaterialID= ? and a.FIsEnabled = 1" );
		sqlBuilder.addParam(traitIndex);
		sqlBuilder.addParam(tblMaterialID.toString().trim());
		IRowSet rowSet  = sqlBuilder.executeQuery();
		return rowSet;
	}

	/**
	 * @description		��������ָ��ֵ
	 * @author				����		
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
	 * @description		����materialId��materialIndexIdɾ��ָ����Ϣ
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
	 * @description		���������Ϣ����
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
	 * @description ��������������ָ����ϼ�ָ��
	 * @author Owen_wen
	 * @createDate 2011-02-24
	 */
	protected IRowSet _selectTraitAndSuperIndex(Context ctx, String materialGroupLongNumber) throws BOSException, EASBizException {
		// �Ȼ�ø����������һ������
		String preLevelMatGroupNumber = materialGroupLongNumber;
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		if (materialGroupLongNumber.lastIndexOf("!") > 0) {
			preLevelMatGroupNumber = preLevelMatGroupNumber.substring(0, materialGroupLongNumber.indexOf("!"));
			/* ��ѯT_MTR_MaterialIndex��õ�����ָ��ID��name */
			sqlBuilder.appendSql("select mi.FID, mi.FName_l2 from T_MTR_MaterialIndex mi ");
			sqlBuilder.appendSql("inner join t_bd_materialGroup mg on mg.fid = mi.FMaterialGroupID ");
			sqlBuilder.appendSql(" where mi.FSource <>'BASEINDEX' and mi.FIsEnabled = 1 and (mg.FLongNumber = ? or mg.FLongNumber = ?)");
			sqlBuilder.addParam(materialGroupLongNumber);
			sqlBuilder.addParam(preLevelMatGroupNumber);
		} else { // ����������Ǹ��ڵ�
			sqlBuilder.appendSql("select mi.FID, mi.FName_l2 from T_MTR_MaterialIndex mi ");
			sqlBuilder.appendSql("inner join t_bd_materialGroup mg on mg.fid = mi.FMaterialGroupID ");
			sqlBuilder.appendSql(" where mi.FSource <>'BASEINDEX' and mi.FIsEnabled = 1 and mg.FLongNumber = ?");
			sqlBuilder.addParam(materialGroupLongNumber);
		}
		IRowSet rowSet = sqlBuilder.executeQuery();
		return rowSet;
	}

}