/*
 * @(#)PartAMaterialImportorListUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.fdc.material.PartAMaterialCollection;
import com.kingdee.eas.fdc.material.PartAMaterialEntryInfo;
import com.kingdee.eas.fdc.material.PartAMaterialFactory;
import com.kingdee.eas.fdc.material.PartAMaterialInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：这个类主要实现材料明细单的导入
 * <p>
 * 
 * @author luoxiaolong
 * @version EAS 6.0
 * @see MaterialInfoUI MaterialInfoControllerBean
 */

public class PartAMaterialImportorListUI extends AbstractPartAMaterialImportorListUI
{
	/** 缺省版本标识 */
	private static final long serialVersionUID = 1L;
	
	/** 日志 */
    private static final Logger logger = CoreUIObject.getLogger(PartAMaterialImportorListUI.class);
    
    /** 获得父UI中,右上角表选中的行ID */
    private String parentUIRightUpTableSelectedRowId = null;
    
    /** 获得表T_MTR_MaterialInfo所有的信息 */
    private List getMaterialInfos = null;
   
    /** 声明资源文件位置 */
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
    
    /** 设置最大精度 */
    private int maxBillsPrecision = 2;
    
    /* 是否进入treeValue */
    private boolean isEnterTreeValue = false;

	/**
	 * 描述: <取消导入材料信息,并关闭当前窗口>
	 * 
	 * @param <e>
	 * @return <>
	 * @throws Exception
	 */
    public void cancel_actionPerformed(ActionEvent e) throws Exception {
    	this.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * 描述：<确定导入材料信息>
	 * 
	 * @param <e>
	 * @return <>
	 * @throws Exception
	 */
	public void confirmImportData_actionPerformed(ActionEvent e)
			throws Exception {
		
		/** 判断在父UI中是否选中行 */
		parentUIRightUpTableSelectedRowId = MaterialInfoUI.rightUpTableSelectedRowId;
		if(parentUIRightUpTableSelectedRowId == null || "".equals(parentUIRightUpTableSelectedRowId)){
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ParentRow"));
			return;
		}
		
		/** 判断是否选中明细行 */
		if(null == getSelectedAllRowData()){
			return ;
		}
		
		/* 存入父UI中,右下角表的数据 */
		List getParentUIAllRowData = getParentUIAllRowData();
		
		/* 判断从父UI中取，右下角表的数据是否出错 null表示出错 */
		if(null == getParentUIAllRowData){
			return ;
		}

		// /*判断父UI中,右下角表中是否有数据，若没有数据就不存在有重复行的问题，直接将数据导入数据库*/
//		if(getParentUIAllRowData.size() == 0){
		// /*判断是否导入成功，导入成功则关闭窗口，否则就给出提示*/
//			if(insertData(getSelectedAllRowData()) >= 1){
//				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataSuccess"));
//				this.actionExitCurrent_actionPerformed(e);
//			}else{
//				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail"));
//			}
//			return ;
//		}
		
		/* 判断是否有重复的行，是否类型不一致 */
		int repeatData = isExistRepeatData(getSelectedAllRowData());
		
		/* 判断在待导入数据中是否存在已有数据行 */
		if(repeatData == -1 || repeatData == 1){
			return ;
		}
		
		/* 向数据库里导入数据,判断是否导入成功，导入成功则关闭窗口，否则就给出提示 */
		if(insertData(getSelectedAllRowData()) >= 1){
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataSuccess"));
			this.actionExitCurrent_actionPerformed(e);
		}else{
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail"));
		}
	}

	/**
	 * 描述：<向数据库里插入数据>
	 * 
	 * @param <dataList>
	 * @return <int>
	 * @throws ParseException
	 */
	private int insertData(List dataList) throws ParseException{
		
		/** 声明一个返回值变量 */
		int result = 0;
		
		/** 设置有郊日期为一个极限时间 */
		Timestamp terminalDate = Timestamp.valueOf("2050-12-31 00:00:00");
		String terminalDate2 = "2050-12-31";
		
		/** 设置有效无效 0无效 1有效 */
		int isValidDate = 0;
		if(terminalDate.getTime() > new java.util.Date().getTime()){
			isValidDate = 1;
		}
		
		/* 根据供应商查询出，供应商所对应的最新报价时间 */
    	List getLastestQuoteTime = getLastestQuoteTime();
    	
		/** 设置是否最新 0不是最新 1最新 */
		int isLatest = 1;
		
		/* 循环获取数据 */
		for(int i = 0; i < dataList.size(); i ++){
			Map materInfoData = (Map) dataList.get(i);
		
			/** 取得主键ID */
			String bosId = getBOSID("9D390CBB");
			
			/** 取出合同ID */
			String contractId = materInfoData.get("contractId").toString();
			
			/** 取出工程项目ID */
			String currentProjectId = materInfoData.get("currentProjectId").toString();

			/** 取出供应商ID */
			String supplierID = materInfoData.get("supplierID").toString();
			
			/** 取出报价时间 */
			String time = materInfoData.get("quoteTime").toString();
			
			/** 取出名称 */
			String materialName = materInfoData.get("FName").toString();
			
			/** 取出编号 */
			String materialNumber = String.valueOf(materInfoData.get("materialNumber") == null ? "" : materInfoData.get("materialNumber").toString());
			
			/** 取出fCreatorID */
			String fCreatorID = String.valueOf(materInfoData.get("fCreatorID") == null ? "" : materInfoData.get("fCreatorID").toString());
			
			/** 取出单价,并做类型转换 */
			double price = Double.parseDouble(materInfoData.get("price").toString());
		
			/* 用正则表达式验证时间的格式 */
			String timeFormat = "^([0-9]{4})-(([0-9])|([0-9][0-9]))-(([0-9])|([0-9][0-9]))$";
			Pattern pTime = Pattern.compile(timeFormat);
			Matcher mTime = pTime.matcher(time);
			
			DateFormat sdf = null;
			if(mTime.matches()){
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}else{
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				time = sdf.format(time);
			}
			
			Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
			/* 根据物料名称和编码确定materialId */
			if(tDouble.matcher(materialNumber).matches()){
				materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
			}
			if(tDouble.matcher(materialName).matches()){
				materialName = materialName.substring(0, materialName.indexOf("."));
			}
			Map materialMap = new HashMap();
			materialMap.put("FName_l2", materialName.trim());
			materialMap.put("FNumber", materialNumber.trim());
			String materialId = getMaterialIDByFNameAndFNumber("T_BD_Material","FID",materialMap);

			
			/** 将参数存入list中 */
			List params = new ArrayList();
			params.add(bosId);
			params.add(materialId);
			params.add(currentProjectId);
			params.add(contractId);
			params.add(new Double(price));
			params.add(time);
			params.add(supplierID);
			
			/* 存入创建人ID */
			params.add(fCreatorID);
			
			/* 存入创建时间 */
			Date createTime = null;
			try {
				createTime = FDCDateHelper.getServerTimeStamp();
			} catch (BOSException e1) {
				createTime = new Date();
			}
			params.add(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
			
			/* 存入有效日期 */
			params.add(terminalDate2);
			
			/* 存入是否是最新 1最新 0不是最新 */
			int isLastestQutote = 1;
			for(int k = 0; k < getLastestQuoteTime.size(); k ++){
				Map map = (Map) getLastestQuoteTime.get(k);
				String fMaxDate = String.valueOf(map.get("FMaxDate") == null ? "1990-1-1" : map.get("FMaxDate").toString());
				String fSupplierID = String.valueOf(map.get("FSupplierID") == null ? "" : map.get("FSupplierID").toString());
				if(supplierID.equals(fSupplierID)){
					if(fMaxDate.indexOf(" ") != -1){
    					fMaxDate = fMaxDate.substring(0,fMaxDate.indexOf(" "));
    				}
					if(time.indexOf(" ") != -1){
						time = time.substring(0,time.indexOf(" "));
					}
    				if(Timestamp.valueOf(fMaxDate + " 00:00:00").getTime() > Timestamp.valueOf(time + " 00:00:00").getTime()){
    					isLastestQutote = 0;
    				}
				}
			}
			
			params.add(new Integer(isLastestQutote));
			/* 是否启用 1启用 0禁用 */
			params.add(new Integer(1));
			params.add(materialName);
			params.add("1SAVED");
			params.add(String.valueOf(isValidDate == 1 ? "VALID" : "INVALID"));
			
			/* 存入控制单元 */
			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
			params.add(cuId);
			
			/* 存入组织单元 */
			String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
			params.add(orgUnitId);

			// /**创建一条SQL语句，插入材料信息表*/
//			StringBuffer sql = new StringBuffer();
//			sql.append("INSERT INTO T_MTR_MaterialInfo(FID,FMaterialID,FProjectID,FContractBillID")
//			   .append(" ,FPrice,FQuoteTime,FSupplierID")
//			   .append(" ,FValidDate,FIsLatest,FIsEnabled,FName,FState,FMState)")
//			   .append(" values(?,?,?,?")
//			   .append(" ,?,{ts ?},?")
//			   .append(" ,{ts ?},?,?,?,?,?);");
			
			try {
				/* 调用服务器端Dao执行SQL */
				MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "partAMaterialImportSql");
				
		        
		        /* 根据materialId查询materialGroupId */
				if(null != materialId && !"".equals(materialId)){
					Map rFilterMap = new HashMap();
					rFilterMap.put("FID", materialId);
					String rMaterialGroupID = getMaterialGroupIdByMaterialId(rFilterMap,"T_BD_Material","FMaterialGroupID");
					if(null != rMaterialGroupID && !"".equals(rMaterialGroupID)){
						/* 获得指标名称集合 */
				        List fNames = getMaterialIndexId(rMaterialGroupID.trim());
						/* 循环插入指标值 */
				        for(int k = 0; k < fNames.size(); k ++){
				        	Map materialIndexIdMap = (Map) fNames.get(k);
				        	String materialIndexId = String.valueOf(materialIndexIdMap.get("FID") == null ? "" : materialIndexIdMap.get("FID"));
				        	if("".equals(materialIndexId)){
				        		continue ;
				        	}
				        	List newParams = new ArrayList();
				        	newParams.add(getBOSID("6E5BD60C"));
	    					newParams.add(bosId);
	    					/* 插入materialIndexId */
	    					newParams.add(materialIndexId);
	    					/* 插入值 */
	    					newParams.add("");
	    					MaterialInfoFactory.getRemoteInstance().addMaterialData(newParams, "importMaterialIndexInfoSql");
				        }
					}
				}
				++ result;
			} catch (Exception e) {
				handUIException(e);
				logger.error(e.getMessage());
				/* 此时导入失败,不调用handUIException(e)处理异常,在后面给出提示“导入失败” */
				--result; 
			}
		}
		return result;
	}

	/**
	 * 描述：<获得特性指标名称>
	 * 
	 * @author <luoxiaolong>
	 * @param <materialGruopId>
	 * @return <null> 创建时间 <2010/11/16>
	 *         <p>
	 * 
	 *         修改人：<修改人>
	 *         <p>
	 *         修改时间：<yyyy/mm/dd>
	 *         <p>
	 *         修改描述：<修改描述>
	 *         <p>
	 * 
	 * @see <相关的类>
	 */
	private List getMaterialIndexId(String materialGruopId){
		
		/** 声明一个返回值变量 */
		List fNames = new ArrayList();
		Map filterMap = new HashMap();
		filterMap.put("FMaterialGroupID", materialGruopId);
		List list = new ArrayList();
		list.add("FID");
		try {
			fNames = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, "T_MTR_MaterialIndex", list);
		} catch (Exception e) {
			handUIException(e);
		}
		return fNames;
	}

	/**
	 * 描述：<查询最新报价时间>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <String> 创建时间 <2010/11/16>
	 *         <p>
	 * 
	 *         修改人：<修改人>
	 *         <p>
	 *         修改时间：<yyyy/mm/dd>
	 *         <p>
	 *         修改描述：<修改描述>
	 *         <p>
	 * 
	 * @see <相关的类>
	 */
    private List getLastestQuoteTime(){
    	List lastestQuoteDate = new ArrayList();
    	try {
    		lastestQuoteDate = MaterialInfoFactory.getRemoteInstance().getLastestQuoteTime();
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		return lastestQuoteDate;
    }

	/**
	 * 描述：<右下角表中的数据行>
	 * 
	 * @param <>
	 * @return <List>
	 * @throws <>
	 */
	public List getParentUIAllRowData(){
		/** 声明一个返回值变量 */
		List parentDateList = null;
			
		/** 获得父UI中，右下角表 */
		KDTable rightDownTable = null;
		
		/* 判断是否获得了父对象 */
		if(null != getUIContext().get("materialUI")){
			
			/** 获得父UI对象 */
			MaterialInfoUI materialUI = (MaterialInfoUI) getUIContext().get("materialUI");
			
			/** 获得父UI，右下角的表 */
			rightDownTable = materialUI.MaterialInfo;
			
		}else if(null != MaterialInfoUI.tblMaterial){
			
			/** 获得父UI，右下角的表 */
			rightDownTable = MaterialInfoUI.tblMaterial;
			
		}else{
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetDataFail"));
		}
		
		/** 判断是否获得了表对象 */
		if(null == rightDownTable){
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetDataFail"));
			return parentDateList;
		}
		
		/* 判断是否有数据 */
		if(rightDownTable.getRowCount() <= 0){
			return new ArrayList();
		}
		
		/** 获得第一行 */
		IRow parentRow = rightDownTable.getRow(0);
		
		/* 判断第一行数据是否真正的存在 */
		if(null == parentRow){
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetDataFail"));
			return parentDateList;
		}
		
		/** 存入单价、报价时间、供应商所在的列号 */
		int[] parentDataHeadIndex = new int[]{
				parentRow.getCell("price").getColumnIndex(),
				parentRow.getCell("quoteTime").getColumnIndex(),
				parentRow.getCell("supplier").getColumnIndex()
		};
		parentDateList = new ArrayList();
		
		/* 循环取出单价、报价时间、供应商的值并以键值对的形式保存到集合中 */
		for(int i = 0; i < rightDownTable.getRowCount(); i ++){
			Map parentSelectedRow = new HashMap();
			parentSelectedRow.put("price", rightDownTable.getRow(i).getCell(parentDataHeadIndex[0]).getValue());
			parentSelectedRow.put("quoteTime", rightDownTable.getRow(i).getCell(parentDataHeadIndex[1]).getValue());
			parentSelectedRow.put("suppiler", rightDownTable.getRow(i).getCell(parentDataHeadIndex[2]).getValue());
			parentDateList.add(parentSelectedRow);
		}
		
		return parentDateList;
	}

	/**
	 * 描述：<判断导入的物料类型是否是相同的，并判断在待导入数据中是否存在已有有数据行>
	 * 
	 * @param <materialId>
	 * @return <1(有重复数据行)、-1(获取数据失败)、0(没有重复数据行)>
	 * @throws <>
	 */
	public int isExistRepeatData(List allSelectedRow){
		/** 定义返回值变量 */
		int result = 0;

		// /**获得表T_MTR_MaterialInfo所有的信息*/
//		List getMaterialInfos = getMaterialAllInfos();
			
		if(null == getMaterialInfos){
			return -1;
		}
		
		List getParentUIAllRowData = getParentUIAllRowData();

		String rightUpTableSelectedRowId = MaterialInfoUI.rightUpTableSelectedRowId;
		
		/** 存入父UI所选择的物料类型 */
		Map parentFilterMap = new HashMap();
		parentFilterMap.put("FID", rightUpTableSelectedRowId);
		String parentSelectedType = getMaterialGroupIdByMaterialId(parentFilterMap,"T_BD_Material","FMaterialGroupID");
		
		/** 记录供应商是否为空 */
		List supplierIsNullList = new ArrayList(); 

		/* 循环判断，是否有重复行 */
		for(int i = 0; i < allSelectedRow.size(); i ++){
			/* 判断获取数据是否失败 */
			if(allSelectedRow.get(i) != null){
				
				Map rowMap = (Map) allSelectedRow.get(i);
				String thisMaterialId = rowMap.get("materialId").toString().trim();
				
				/* 判断物料类型是否相同 */
				Map filterMap = new HashMap();
				filterMap.put("FID", thisMaterialId);
				String thisSelectedType = getMaterialGroupIdByMaterialId(filterMap,"T_BD_Material","FMaterialGroupID");
				if(null != parentSelectedType && null != thisSelectedType){
					if(!parentSelectedType.equals(thisSelectedType)){
						MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "TypeIsDifferent"));
						result = -1;
						return result;
					}
				}else{
					MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetTypeIsFail"));
					result = -1;
					return -1;
				}
				
				Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
				/* 根据物料名称和编码确定materialId */
				String materialName = String.valueOf(rowMap.get("FName") == null ? "" : rowMap.get("FName").toString().trim());
				String materialNumber = String.valueOf(rowMap.get("materialNumber") == null ? "" : rowMap.get("materialNumber").toString().trim());
				if(tDouble.matcher(materialNumber).matches()){
					materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
				}
				if(tDouble.matcher(materialName).matches()){
					materialName = materialName.substring(0, materialName.indexOf("."));
				}
				Map materialMap = new HashMap();
				materialMap.put("FName_l2", materialName.trim());
				materialMap.put("FNumber", materialNumber.trim());
				materialMap.put("FMaterialGroupID", parentSelectedType);
				String materialId = getMaterialIDByFNameAndFNumber("T_BD_Material","FID",materialMap);
				if(null == materialId){
					MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail") + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InMaterialTable") + (i + 1) + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "NameAndNumberIsNotExist"));
					result = -1;
					return result;
				}
				
				/* 循环判断(-) */
				for(int j = 0; j < getParentUIAllRowData.size(); j ++){
					Map parentRowDataMap = (Map) getParentUIAllRowData.get(j);
					double pPrice = Double.parseDouble(String.valueOf(parentRowDataMap.get("price") == null ? "0" : parentRowDataMap.get("price").toString().trim()));
					double sPrice = Double.parseDouble(String.valueOf(rowMap.get("price") == null ? "0" : rowMap.get("price").toString().trim()));
					String pQuoteTime = String.valueOf(parentRowDataMap.get("quoteTime") == null ? new java.util.Date().toLocaleString() : parentRowDataMap.get("quoteTime").toString().trim());
					pQuoteTime = pQuoteTime.substring(0, pQuoteTime.indexOf(" "));
					String sQuoteTime = String.valueOf(rowMap.get("quoteTime") == null ? new java.util.Date().toLocaleString() : rowMap.get("quoteTime").toString().trim());
					String pSuppiler = String.valueOf(parentRowDataMap.get("suppiler") == null ? "" : parentRowDataMap.get("suppiler"));
					String sSuppiler = String.valueOf(rowMap.get("suppiler") == null ? "" : rowMap.get("suppiler").toString().trim());
					
					/* 判断单价、报价时间、供应商是否相同 */
					if(pPrice == sPrice
							&& pQuoteTime.equals(sQuoteTime)
							&& pSuppiler.equals(sSuppiler)){
						 // 判断供应商是否为空
						 if(!"".equals(sSuppiler)){
							 MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "had") + (i + 1) + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "data") + (j + 1) + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "repeat"));
							 result = 1;
							 return result;
						 }
					}
				}
				
				String suppilerID = String.valueOf(rowMap.get("supplierID") == null ? "" : rowMap.get("supplierID").toString().trim());
				/* 循环判断(二) */
				for(int j = 0; j < getMaterialInfos.size(); j ++){
					Map materialInfo = (Map) getMaterialInfos.get(j);
					double pPrice = Double.parseDouble(String.valueOf(materialInfo.get("FPrice") == null ? "0" : materialInfo.get("FPrice").toString().trim()));
					double sPrice = Double.parseDouble(rowMap.get("price").toString().trim());
					String pQuoteTime = String.valueOf(materialInfo.get("FQuoteTime") == null ? new java.util.Date().toLocaleString() : materialInfo.get("FQuoteTime").toString().trim());
					pQuoteTime = pQuoteTime.substring(0, pQuoteTime.indexOf(" "));
					String sQuoteTime = rowMap.get("quoteTime").toString().trim();
					String fMaterialID = String.valueOf(materialInfo.get("FMaterialID") == null ? "" : materialInfo.get("FMaterialID").toString().trim());
					String fName = String.valueOf(materialInfo.get("FSupplierID") == null ? "" : materialInfo.get("FSupplierID").toString().trim());
					String suppiler = String.valueOf(rowMap.get("suppiler") == null ? "" : rowMap.get("suppiler").toString().trim());
					
					/* 判断单价、报价时间、供应商是否相同 */
					if(materialId.equals(fMaterialID) 
							&& pPrice == sPrice
							&& pQuoteTime.equals(sQuoteTime)
							&& fName.equals(suppilerID)){
						 // 判断供应商是否为空
						 if("".equals(suppilerID) || "".equals(suppiler)){
							 int a = -1;
								for(int k = 0; k < supplierIsNullList.size(); k ++){
									if(null == supplierIsNullList.get(k)) continue;
									int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
									if(i+1 == sub){
										a = 1;
									}
								}
								if(a == -1)
									supplierIsNullList.add(new Integer(i + 1));
						 }else{
							 MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail") + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InMaterialTable") + (i + 1) + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "rowDataRepeat2"));
							 result = 1;
							 return result;
						 }
					}else if("".equals(suppilerID) || "".equals(suppiler)){
						int a = -1;
						for(int k = 0; k < supplierIsNullList.size(); k ++){
							if(null == supplierIsNullList.get(k)) continue;
							int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
							if(i+1 == sub){
								a = 1;
							}
						}
						if(a == -1)
						    supplierIsNullList.add(new Integer(i + 1));
					}
				}
				/* 当第一次插入数据时 */
				if(getMaterialInfos.size() <=0 && ("".equals(suppilerID))){
					int a = -1;
					for(int k = 0; k < supplierIsNullList.size(); k ++){
						if(null == supplierIsNullList.get(k)) continue;
						int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
						if(i+1 == sub){
							a = 1;
						}
					}
					if(a == -1)
						supplierIsNullList.add(new Integer(i + 1));
				}
				/* 到这里就没有重复行数据，返回0 */
			}else{
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetDataFail"));
				result = -1;
			}
		}
		
		if(supplierIsNullList.size() > 0){
			StringBuffer isNull = new StringBuffer();
			isNull.append(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InMaterialTable"));
			for(int k = 0; k < supplierIsNullList.size(); k ++){
				if(k == (supplierIsNullList.size()-1)){
					isNull.append(supplierIsNullList.get(k).toString());
				}else{
					isNull.append(supplierIsNullList.get(k).toString() + "、");
				}
			}
			isNull.append(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "SupplierIsNullOrNotExist"));
			MsgBox.showInfo("导入数据失败！供应商为空或不存在不能导入！");
//			if(answer == 0){
//				result = 0;
//			}else if(answer == 2){
				result = 1;
//			}
		}
		
		return result;
	}

	/**
	 * 描述：<根据物料ID查询单价、报价时间、供应商>
	 * 
	 * @param <>
	 * @return <String>
	 * @throws <>
	 */
	public List getMaterialAllInfos(){
		List getMaterialInfos = new ArrayList();
		try {
			List list = new ArrayList();
			list.add("FPrice");
			list.add("FQuoteTime");
			list.add("FSupplierID");
			list.add("FMaterialID");
			getMaterialInfos = MaterialInfoFactory.getRemoteInstance().getMaterialData(new HashMap(), "T_MTR_MaterialInfo", list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return getMaterialInfos;
	}

	/**
	 * 描述：<根据物料名称和编码查询materialId>
	 * 
	 * @author <luoxiaolong>
	 * @param <tableName,fieldName,filters>
	 * @return <int> 创建时间 <2010/11/16>
	 *         <p>
	 * 
	 *         修改人：<修改人>
	 *         <p>
	 *         修改时间：<yyyy/mm/dd>
	 *         <p>
	 *         修改描述：<修改描述>
	 *         <p>
	 * 
	 * @see <相关的类>
	 */
    public String getMaterialIDByFNameAndFNumber(String tableName,String fieldName,Map filterMap){
    	List fieldList = new ArrayList();
    	fieldList.add(fieldName);
    	
    	/** 存入materialId */
    	String materialId = null;
    	
    	try {
    		List materialList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
    		if(null != materialList && materialList.size() > 0){
    			Map materialMap = (Map) materialList.get(0);
    			materialId = materialMap.get(fieldName).toString();
    		}
		} catch (Exception e) {
			handUIException(e);
		}
		return materialId;
    }

	/**
	 * 描述：<根据物料id，获得物料类型>
	 * 
	 * @param <materialId>
	 * @return <String>
	 * @throws <>
	 */
	public String getMaterialGroupIdByMaterialId(Map filterMap,String tableName,String fieldName){
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		String materialGroupId = null;
		try{
			List materialGroupList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if(null != materialGroupList && materialGroupList.size() > 0){
				Map materialGroupMap = (Map) materialGroupList.get(0);
				materialGroupId = materialGroupMap.get(fieldName).toString();
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			handUIException(e);
		}
		return materialGroupId;
	}

	/**
	 * 描述：<获得被选中所有行数据>
	 * 
	 * @param <>
	 * @return <List>
	 * @throws <>
	 */
	private List getSelectedAllRowData(){
		/** 获得右下角选中的所有区域，是一个集合 */
		List rows = this.tblMaterial.getSelectManager().getBlocks();
		
		/** 将选中的所有行的数据存入集合中 */
		List allSelectedRow = null;
		
		/* block.getBeginRow()为获得选中区域的开始行，block.getEndRow()为获得选中区域的结束行行 */
		if(rows.size() >= 0){	
			
			/** 在这里只需要选择的第一个区域 */
			KDTSelectBlock block = (KDTSelectBlock)rows.get(0);
			
			/** 获得右上角的表选中那一行的行号 */
			int rightUpRowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			
			/** 获得右上角的表选中那一行的数据 */
			IRow rightUpRow = this.tblMain.getRow(rightUpRowIndex);
			
			/** 实例化对象 */
			allSelectedRow = new ArrayList();
			
			/* 以下为获得选中所有行的行号，循环取出选中数据，并以键值对的形式保存到集合中 */
			for(int i = block.getBeginRow(); i <= block.getEndRow(); i ++){
				
				/** 获得选中的单行数据 */
				IRow rowDownRow = this.tblMaterial.getRow(i);
				
				/** 保存选中行数据 */
				Map rightUpSet = new HashMap();
				
				/** 声明一个过虑的Map */
				Map filterMap = new HashMap();
				filterMap.put("FID", rightUpRow.getCell("id").getValue().toString().trim());
				/** 存入要查询的字段 */
				List fieldList = new ArrayList();
				fieldList.add("FCurProjectID");
				fieldList.add("FCreatorID");
				fieldList.add("FLastUpdateUserID");
				/** 执行查询，获取合同信息 */
				List contractInfoList = getCurrentProjectInfo(filterMap,"T_CON_ContractBill",fieldList);
				
				if(contractInfoList.size() == 0){
					MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetDataFail"));
					return allSelectedRow;
				}
				
				Map infoMap = (Map) contractInfoList.get(0);
				
				
				/** 存入工程项目ID */
				String currentProjectId = infoMap.get("FCurProjectID").toString();
				
				/** 存入FCreatorID */
				String fCreatorID = infoMap.get("FCreatorID").toString();
				
				/** 存入FLastUpdateUserID */
				String fLastUpdateUserID = infoMap.get("FLastUpdateUserID").toString();
				
				/* 判断是否真正获得了工程项目的ID */
				if("".equals(currentProjectId.trim())){
					MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "GetDataFail"));
					return allSelectedRow;
				}
				
				/* 存入合同ID */
				rightUpSet.put("contractId", rightUpRow.getCell("id").getValue());
				
				/* 存入工程项目ID */
				rightUpSet.put("currentProjectId", currentProjectId);
				
				/** 将要导入数据的各列，取出其列号存入数组中 */
				int[] rightUpCells = new int[]{
						rightUpRow.getCell("contractName").getColumnIndex(),
						rightUpRow.getCell("partB.name").getColumnIndex(),
						rightUpRow.getCell("signDate").getColumnIndex()
				};
				
				/* 存入合同名称的值 */
				rightUpSet.put("contractName", rightUpRow.getCell(rightUpCells[0]).getValue());
				
				/* 存入供应商的值 */
				rightUpSet.put("suppiler", rightUpRow.getCell(rightUpCells[1]).getValue());
				
				/* 存入供应商ID */
				Map filterSupplierMap = new HashMap();
				filterSupplierMap.put("FID", rightUpRow.getCell("id").getValue().toString().trim());
				String sgetSuppplierID = getSuppplierID(filterSupplierMap,"T_CON_ContractBill","FPartBID");
				rightUpSet.put("supplierID", String.valueOf(sgetSuppplierID == null ? "" : sgetSuppplierID));
				
				/* 存入报价时间的值 */
				rightUpSet.put("quoteTime", rightUpRow.getCell(rightUpCells[2]).getValue());
				
				int[] rightDownCells = new int[]{
						rowDownRow.getCell("materialNumber").getColumnIndex(),
						rowDownRow.getCell("materialName").getColumnIndex(), rowDownRow.getCell("model").getColumnIndex(),
						rowDownRow.getCell("unit").getColumnIndex(), rowDownRow.getCell("price").getColumnIndex(),
						rowDownRow.getCell("state").getColumnIndex()
				};
				
				/* 存入编码的值 */
				rightUpSet.put("materialNumber", rowDownRow.getCell(rightDownCells[0]).getValue());
				
				/* 存入名称的值 */
				rightUpSet.put("FName", rowDownRow.getCell(rightDownCells[1]).getValue());
				
				/* 存入材料ID */
				Map materialMap = new HashMap();
				materialMap.put("FName_l2", rowDownRow.getCell(rightDownCells[1]).getValue().toString().trim());
				materialMap.put("FNumber", rowDownRow.getCell(rightDownCells[0]).getValue().toString().trim());
				String materialId = getMaterialIDByFNameAndFNumber("T_BD_Material","FID",materialMap);
				rightUpSet.put("materialId", materialId);
				
				/* 存入规格型号的值 */
				rightUpSet.put("model", rowDownRow.getCell(rightDownCells[2]).getValue());
				
				/* 存入单位的值 */
				rightUpSet.put("unit", rowDownRow.getCell(rightDownCells[3]).getValue());
				
				/* 存入单价的值 */
				rightUpSet.put("price", rowDownRow.getCell(rightDownCells[4]).getValue());
				
				/** 存入FCreatorID */
				rightUpSet.put("fCreatorID", fCreatorID);
				
				/** 存入FLastUpdateUserID */
				rightUpSet.put("fLastUpdateUserID", fLastUpdateUserID);
				
				/* 存入状态 */
				rightUpSet.put("state", rowDownRow.getCell(rightDownCells[5]).getValue());
				
				allSelectedRow.add(rightUpSet);
			}
		}else{
			/* 没有选择任何区域，给出提示 */
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceRow"));
		}
		return allSelectedRow;
	}

	/**
	 * 描述：<获得物料ID>
	 * 
	 * @param <>
	 * @return <String>
	 * @throws <>
	 */
	public String getMaterialID(String id){
		String materialId = null;
		try {
			materialId = MaterialInfoFactory.getRemoteInstance().getMaterialID(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		return materialId;
	}

	/**
	 * 描述：<根据材料信息，获得当前工程项目id>
	 * 
	 * @param <materialId>
	 * @return <String>
	 * @throws <>
	 */
	public List getCurrentProjectInfo(Map filterMap, String tableName, List fieldList){
		List resultList = new ArrayList(); 
		try {
			resultList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		return resultList;
	}

	/**
	 * 描述：<根据材料信息，获得供应商id>
	 * 
	 * @param <materialId>
	 * @return <String>
	 * @throws <>
	 */
	public String getSuppplierID(Map filterMap, String tableName, String fieldName){
		
		String supplierId = null;
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		try{
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if(null != list && list.size() > 0){
				Map map = (Map) list.get(0);
				supplierId = String.valueOf(map.get(fieldName) == null ? "" : map.get(fieldName).toString());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			handUIException(e);
		}
		return supplierId;
	}

	/**
	 * 描述：<获得主键ID>
	 * 
	 * @param <bosType>
	 * @return <String>
	 * @throws <>
	 */
	public String getBOSID(String bosType){
		String bosId = null;
		try {
			bosId = MaterialInfoFactory.getRemoteInstance().getBOSID(bosType);
		} catch (Exception e) {
			logger.error(e);
			handUIException(e);
		}
		return bosId;
	}
	
    /**
     * output class constructor
     */
    public PartAMaterialImportorListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	/**
	 * 描述：<页面加载设置显示属性>
	 * 
	 * @param <>
	 * @return <>
	 * @throws <Exception>
	 */
	public void onShow() throws Exception {
		super.onShow();
		
		isEnterTreeValue = false; 
		
		/* 设置确定导入和取消按扭可以点击 */
		if(this.tblMaterial.getRowCount() > 0){
			this.KDBtnConfirmImport.setEnabled(true);
		}else{
			this.KDBtnConfirmImport.setEnabled(false);
		}
		
		this.KDBtnCancel.setEnabled(true);
		/* 隐藏一些button */
		this.btnAudit.setVisible(false);
		this.btnUnAudit.setVisible(false);
		this.btnAuditResult.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnWorkFlowG.setVisible(false);
		this.menuBar.setVisible(false);
//		this.toolBar.setVisible(false);
	}

	/**
	 * 描述：<页面加载>
	 * 
	 * @param <>
	 * @return <>
	 * @throws <Exception>
	 */
	 public void onLoad() throws Exception {
		super.onLoad();
		getMaterialInfos = getMaterialAllInfos();
	}

	/**
	 * 描述：<右上角表的表格选择行改变解法的事件>
	 * 
	 * @param <e>
	 * @return <>
	 * @throws <Exception>
	 */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
        isEnterTreeValue = false;
        this.KDBtnConfirmImport.setEnabled(false);
        if(this.tblMaterial.getRowCount() > 0){
    		this.KDBtnConfirmImport.setEnabled(true);
    	}
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

	/**
	 * 描述：<过滤掉右下角表单显示不是已审批的数据>
	 * 
	 * @param <e>
	 * @return <>
	 * @throws <Exception>
	 */
	protected EntityViewInfo genBillQueryView(KDTSelectEvent e) {
    	KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
    	
    	if(isEnterTreeValue){
    		filter.getFilterItems().add(new FilterItemInfo("state", "xxxxxxxxx"));
    	}else{
    		/* 使材料明细列表中显示已审批的材料明细 */
        	filter.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
    	}
    	
    	
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
				
			}
    	}
		return view;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK iPk,
    		EntityViewInfo view) {
    	return super.getQueryExecutor(iPk, view);
    }

	/**
	 * 描述：<选择树节点改变触法的事件>
	 * 
	 * @param <e>
	 * @return <>
	 * @throws <Exception>
	 */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
        isEnterTreeValue = true;
        
        /* 当右下角的明细表有数据时,可以点击，反之则不能点击 */
        
        /* 设置确定导入和取消按扭可以点击 */
		if(this.tblMaterial.getRowCount() > 0){
			this.KDBtnConfirmImport.setEnabled(true);
		}else{
			this.KDBtnConfirmImport.setEnabled(false);
		}
		// /*存入父UI中,右下角表的数据*/
//    	List getAllRowData = getAllRowData();
//   	
		// /*在材料明细单中若存在与材料信息库相同的报价信息，就以红色标注*/
//    	for(int k = 0; k < getAllRowData.size(); k ++){
//    			Map rowMap = (Map) getAllRowData.get(k);
//    			double rPrice = Double.parseDouble(String.valueOf(rowMap.get("price") == null ? "0" : rowMap.get("price").toString().trim()));
//    			String rQuoteTime = String.valueOf(rowMap.get("quoteTime") == null? new java.util.Date().toLocaleString() : rowMap.get("quoteTime").toString().trim());
//    			String rSupplierID = String.valueOf(rowMap.get("supplierID")==null ? "" : rowMap.get("supplierID").toString().trim());
//    			String rMaterialID = String.valueOf(rowMap.get("materialId") == null ? "" : rowMap.get("materialId").toString().trim());
//    			
		// /*判断物料类型是否相同*/
//				Map rFilterMap = new HashMap();
//				rFilterMap.put("FID", rMaterialID);
//				String rMaterialGroupID = getMaterialGroupIdByMaterialId(rFilterMap,"T_BD_Material","FMaterialGroupID");
//				
//    			if(null != getMaterialInfos){
//        			for(int j = 0; j < getMaterialInfos.size(); j ++){
//        				Map dataMap = (Map) getMaterialInfos.get(j);
//        				double fPrice = Double.parseDouble(String.valueOf(dataMap.get("FPrice") == null ? "0" : dataMap.get("FPrice").toString().trim()));
//        				String fQuoteTime = String.valueOf(dataMap.get("FQuoteTime") == null ? new java.util.Date().toLocaleString() : dataMap.get("FQuoteTime").toString().trim());
//        				String fSupplierID = String.valueOf(dataMap.get("FSupplierID") == null ? "" : dataMap.get("FSupplierID").toString().trim());
//        				String fMaterialID = String.valueOf(dataMap.get("FMaterialID") == null ? new java.util.Date().toLocaleString() : dataMap.get("FMaterialID").toString());
//        				
//        				Map fFilterMap = new HashMap();
//        				fFilterMap.put("FID", fMaterialID);
//        				String fMaterialGroupID = getMaterialGroupIdByMaterialId(fFilterMap,"T_BD_Material","FMaterialGroupID");
//        				
		// /*如果不属于一个大类，就不变色*/
//        				if(null != rMaterialGroupID && null != fMaterialGroupID && rMaterialGroupID.equals(fMaterialGroupID)){
//        					if(fQuoteTime.indexOf(" ") != -1){
//            					fQuoteTime = fQuoteTime.substring(0,fQuoteTime.indexOf(" "));
//            				}
//            				
//            				if(rPrice == fPrice && rQuoteTime.equals(fQuoteTime) && rSupplierID.equals(fSupplierID)){
		// /*设置重复项变色*/
//            					this.tblMaterial.getRow(k).getStyleAttributes().setBackground(new Color(255,0,0));
//            				}
//        				}
//        			}
//        		}
//    	}
    }

    protected void treeSelectChange() throws Exception {
		super.treeSelectChange();
	}

	/**
	 * 描述：<当右下角的明细表有数据时,可以点击，反之则不能点击>
	 * 
	 * @param <e>
	 * @return <>
	 * @throws <Exception>
	 */
    protected void treeContractType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeContractType_valueChanged(e);
        /* 当右下角的明细表有数据时,可以点击，反之则不能点击 */
        
        /* 设置确定导入和取消按扭可以点击 */
		if(this.tblMaterial.getRowCount() > 0){
			this.KDBtnConfirmImport.setEnabled(true);
		}else{
			this.KDBtnConfirmImport.setEnabled(false);
		}
    }

    /**
     * output tblMaterial_tableClicked method
     */
    protected void tblMaterial_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	isEnterTreeValue = false;
//    	if(this.tblMaterial.getRowCount() > 0 && this.tblMaterial.getSelectManager().getActiveRowIndex() != -1){
//    		this.KDBtnConfirmImport.setEnabled(true);
//    	}else{
//    		this.KDBtnConfirmImport.setEnabled(false);
//    	}
    }
    
    protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception {
//		// TODO Auto-generated method stub
//		super.tblBill_tableClicked(e);
	}

	/**
     * output tblMaterial_tableSelectChanged method
     */
    protected void tblMaterial_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
    	isEnterTreeValue = false;
//        super.tblMaterial_tableSelectChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }

    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionView_actionPerformed
     */
    public void actionView_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionView_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionRefresh_actionPerformed
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
//    	this.tblMaterial.removeRows();
//        super.actionRefresh_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionLocate_actionPerformed
     */
    public void actionLocate_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLocate_actionPerformed(e);
    }

    /**
     * output actionQuery_actionPerformed
     */
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuery_actionPerformed(e);
    }

    /**
     * output actionImportData_actionPerformed
     */
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionImportData_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionExportData_actionPerformed
     */
    public void actionExportData_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportData_actionPerformed(e);
    }

    /**
     * output actionToExcel_actionPerformed
     */
    public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToExcel_actionPerformed(e);
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionPublishReport_actionPerformed
     */
    public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPublishReport_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionQueryScheme_actionPerformed
     */
    public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQueryScheme_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionCopyTo_actionPerformed
     */
    public void actionCopyTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopyTo_actionPerformed(e);
    }

    /**
     * output actionTraceUp_actionPerformed
     */
    public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceUp_actionPerformed(e);
    }

    /**
     * output actionTraceDown_actionPerformed
     */
    public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionTraceDown_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionAuditResult_actionPerformed
     */
    public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAuditResult_actionPerformed(e);
    }

    /**
     * output actionViewDoProccess_actionPerformed
     */
    public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewDoProccess_actionPerformed(e);
    }

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMultiapprove_actionPerformed(e);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNextPerson_actionPerformed(e);
    }

    /**
     * output actionWorkFlowG_actionPerformed
     */
    public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkFlowG_actionPerformed(e);
    }

    /**
     * output actionSendSmsMessage_actionPerformed
     */
    public void actionSendSmsMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendSmsMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    /**
     * output actionWorkflowList_actionPerformed
     */
    public void actionWorkflowList_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionWorkflowList_actionPerformed(e);
    }

    /**
     * output actoinViewSignature_actionPerformed
     */
    public void actoinViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actoinViewSignature_actionPerformed(e);
    }

    /**
     * output actionAudit_actionPerformed
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionUnAudit_actionPerformed
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

    /**
     * output actionRespite_actionPerformed
     */
    public void actionRespite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRespite_actionPerformed(e);
    }

    /**
     * output actionCancelRespite_actionPerformed
     */
    public void actionCancelRespite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancelRespite_actionPerformed(e);
    }

    /**
     * output actionRevise_actionPerformed
     */
    public void actionRevise_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRevise_actionPerformed(e);
    }

    
    private void setTablePropery(){    	
//    	tblMaterial.getColumn(PartAMaterialContants.ORIGINAL_PRICE).getStyleAttributes().setNumberFormat("##.00");
    	tblMaterial.getColumn(PartAMaterialContants.ORIGINAL_PRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//    	tblMaterial.getColumn(PartAMaterialContants.PRICE).getStyleAttributes().setNumberFormat("##.00");
    	tblMaterial.getColumn(PartAMaterialContants.PRICE).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//    	tblMaterial.getColumn(PartAMaterialContants.AMOUNT).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	tblMaterial.getColumn(PartAMaterialContants.AMOUNT).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	tblMaterial.getColumn(PartAMaterialContants.QUANTITY).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//    	tblMaterial.getColumn(PartAMaterialContants.QUANTITY).getStyleAttributes().setNumberFormat("#,###.00");

		// 添加材料明细单的金额合计行,new add by jiadong at 2010-6-9.
    	addBillsStatRow();        
    }

	/**
	 * 添加统计行 new add by jiadong at 2010-6-9
	 */
    private void addBillsStatRow(){
    	// 求明细总金额
    	String amount = getBillsAmount();
    	// 生成金额统计行
    	IRow footRow = createBillsStatFootRow();
    	// 设置统计金额的精度，为物料价格中精度最大的
    	footRow.getCell(PartAMaterialContants.AMOUNT).getStyleAttributes().setNumberFormat(GerneraterFormatString(maxBillsPrecision));
    	// 把统计金额放入到统计行中
    	footRow.getCell(PartAMaterialContants.AMOUNT).setValue(amount);
    }

	/**
	 * 求出合同下面的材料明细(tblMaterial)的总金额 add by joey_jia 2010-06-09
	 */
    private String getBillsAmount(){
    	// 初始化统计变量
    	BigDecimal amount = FDCHelper.ZERO;
    	// 初始化金额精度，统计金额的精度应该等于分录中的最大精度。
		// 统计金额，求和
		for(int i =0 ;i<tblMaterial.getRowCount();i++){
			if(tblMaterial.getCell(i, PartAMaterialContants.AMOUNT) != null){
				amount = FDCHelper.add(amount, FDCHelper.toBigDecimal(tblMaterial.getCell(i, PartAMaterialContants.AMOUNT).getValue()));
			}
		}
		// 如果统计金额为0就不显示
//		if(amount.intValue() == 0) return "";
		return amount.toString();
    }

	/**
	 * 生成/获取统计行 by joey_jia 2010-06-09
	 * 
	 * @return 统计行
	 */
    private IRow createBillsStatFootRow(){
    	IRow footRow = null;
    	KDTFootManager footRowManager= tblMaterial.getFootManager();
    	if(footRowManager==null){
    		// 初始化footRowManager
    		String total=EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Total");
    		footRowManager = new KDTFootManager(tblMaterial);
    		footRowManager.addFootView();
    		tblMaterial.setFootManager(footRowManager);
    		// 设置footRow
    		footRow= footRowManager.addFootRow(0);
    		footRow.setUserObject("FDC_PARAM_TOTALCOST");
    		footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
    		
    		this.tblMaterial.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
    		this.tblMaterial.getIndexColumn().setWidth(30);
    		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
    		// 设置索引列的文本值
    		footRowManager.addIndexText(0, total);

    	}else{
    		footRow=tblMaterial.getFootRow(0);
    		if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
    			footRow=tblMaterial.addFootRow(0);
    		}
    	}
    	return footRow;
    }
    
    
   
    protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		if (view == null) {
			return;
		}
		PartAMaterialCollection partAMaterialCollection = PartAMaterialFactory.getRemoteInstance().getPartAMaterialCollection(view);
		int precision = 0;
		int mergeFromRowIndex = 0;
		int mergeToRowIndex = 0;
		for (Iterator iter = partAMaterialCollection.iterator(); iter.hasNext();) {
			mergeFromRowIndex = mergeToRowIndex;
			PartAMaterialInfo element = (PartAMaterialInfo) iter.next();
			for (Iterator iter2 = element.getEntrys().iterator(); iter2.hasNext();) {
				mergeToRowIndex++;
				PartAMaterialEntryInfo entry = (PartAMaterialEntryInfo) iter2.next();
				precision = entry.getMaterial().getPricePrecision();
				// 求最大精度 add by jiadong 2010-06-10
				if (maxBillsPrecision < precision) {
					maxBillsPrecision = precision;
				}
				IRow row = getBillListTable().addRow();
				row.getCell(PartAMaterialContants.ID).setValue(element.getId().toString());
				row.getCell(PartAMaterialContants.STATE).setValue(element.getState());
				row.getCell(PartAMaterialContants.VERSION).setValue(element.getVersion() + ""); // added by owen_wen 2010-08-23
				row.getCell(PartAMaterialContants.PARTA_MATERIAL_NUMBER).setValue(element.getNumber()); // added by owen_wen 2010-08-23

				if (entry.getMainContractBill() != null) {
					row.getCell(PartAMaterialContants.CONTRACT_NUMBER).setValue(entry.getMainContractBill().getNumber());
					row.getCell(PartAMaterialContants.CONTRACT_NAME).setValue(entry.getMainContractBill().getName());
				}

				row.getCell(PartAMaterialContants.MATERIAL_NUMBER).setValue(entry.getMaterial().getNumber());
				row.getCell(PartAMaterialContants.MATERIAL_NAME).setValue(entry.getMaterial().getName());
				row.getCell(PartAMaterialContants.MODEL).setValue(entry.getMaterial().getModel());
				row.getCell(PartAMaterialContants.UNIT).setValue(entry.getMaterial().getBaseUnit().getName());
				row.getCell(PartAMaterialContants.ORIGINAL_PRICE).setValue(entry.getOriginalPrice());
				row.getCell(PartAMaterialContants.PRICE).setValue(entry.getPrice());
				row.getCell(PartAMaterialContants.QUANTITY).setValue(entry.getQuantity() + "");
				row.getCell(PartAMaterialContants.AMOUNT).setValue(entry.getAmount());
				row.getCell(PartAMaterialContants.ARRIVE_DATE).setValue(entry.getArriveDate());
				row.getCell(PartAMaterialContants.DESCRIPTION).setValue(entry.getDescription());
				row.getCell(PartAMaterialContants.AMOUNT).getStyleAttributes().setNumberFormat(
						GerneraterFormatString(entry.getMaterial().getPricePrecision()));
			}
			setTableMerge(mergeFromRowIndex, mergeToRowIndex - 1);
		}
		this.setTablePropery();
	}

	/**
	 * 设置表格融合，每次修订都需要单独设置一次
	 * 
	 * @author owen_wen
	 */
	private void setTableMerge(int mergeFromRowIndex, int mergeToRowIndex) {
		tblMaterial.getMergeManager().mergeBlock(mergeFromRowIndex, 1, mergeToRowIndex, 1); // 状态列
		tblMaterial.getMergeManager().mergeBlock(mergeFromRowIndex, 2, mergeToRowIndex, 2, KDTMergeManager.FREE_MERGE); // 材料单据编码 列
		tblMaterial.getMergeManager().mergeBlock(mergeFromRowIndex, 3, mergeToRowIndex, 3, KDTMergeManager.FREE_MERGE); // 领用合同编码 列
		tblMaterial.getMergeManager().mergeBlock(mergeFromRowIndex, 4, mergeToRowIndex, 4, KDTMergeManager.FREE_MERGE); // 领用合同名称 列
		tblMaterial.getMergeManager().mergeBlock(mergeFromRowIndex, 14, mergeToRowIndex, 14, KDTMergeManager.FREE_MERGE); // Version列
	}

	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("entrys.id");
		selectors.add("entrys.mainContractBill.number");
		selectors.add("entrys.mainContractBill.name");
		selectors.add("entrys.material.number");
		selectors.add("entrys.material.*");
		selectors.add("entrys.material.name");
		selectors.add("entrys.material.model");
		selectors.add("entrys.material.baseUnit.name");
		selectors.add("entrys.originalPrice");
		selectors.add("entrys.price");
		selectors.add("entrys.quantity");
		selectors.add("entrys.amount");
		selectors.add("entrys.arriveDate");
		selectors.add("entrys.description");
		selectors.add("version");
		selectors.add("number");
		selectors.add("state");
		selectors.add("id");
		selectors.add("creator.name");
		selectors.add("auditor.name");
		return selectors;
	}
}