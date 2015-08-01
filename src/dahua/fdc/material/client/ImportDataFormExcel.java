/*
 * @(#)MaterialInfoUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述：导入数据从Excel<p>
 * @author luoxiaolong
 * @version EAS 7.0
 * @see MaterialInfoUI
 */
public class ImportDataFormExcel extends CoreUI{

	MaterialInfoUI materialInfoUI = new MaterialInfoUI();
	
	/**缺省版本标识*/
	private static final long serialVersionUID = 1L;

	/**日志*/
	private static final Logger logger = CoreUIObject.getLogger(MaterialInfoUI.class);
	
	/**声明资源文件位置*/
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
    private static final String MATERIALINFO_RESOURCEPATH = "com.kingdee.eas.fdc.material.MaterialInfoResource";
    
    /**存入所有的错误信息*/
    private List errorAllInfos = null;
    
    /**存入所有的正确数据行*/
    private List allRightDataRow = null;
    
    /**Excel表中数据是否重复*/
	private ArrayList material_dataRepeat = null;
	
	/**记录供应商是否为空*/
	private List supplierIsNullList = null;
	
	/**定义基础列中的“物料类别”*/
	private String material_Type = null;
	
	/**定义基础列中的“物料编码”*/
	private String material_Number = null;
	
	/**定义基础列中的“物料名称”*/
	private String material_Name = null;
	
	/**定义基础列中的“规格型号”*/
	private String material_Model = null;
	
	/**定义基础列中的“单位”*/
	private String material_Unit = null;
	
	/**定义基础列中的“单价”*/
	private String material_Price = null;
	
	/**定义基础列中的“报价时间”*/
	private String material_QuoteTime = null;
	
	/**定义基础列中的“供应商”*/
	private String material_Supplier = null;
	
	/**定义基础列中的“项目名称”*/
	private String material_ProjectName = null;
	
	/**定义基础列中的“合同名称”*/
	private String material_ContractName = null;
	
	/**定义基础列中的“有效期”*/
	private String material_Validate = null;
	
	public ImportDataFormExcel() throws Exception {
		super();
		material_Type = getResourceString("MaterialType");
		material_Number = getResourceString("MaterialNumber");
		material_Name = getResourceString("MaterialName");
		material_Model = getResourceString("Model");
		material_Unit = getResourceString("Unit");
		material_Price = getResourceString("Price");
		material_QuoteTime = getResourceString("QuoteTime");
		material_Supplier = getResourceString("Supplier");
		material_ProjectName = getResourceString("ProjectName");
		material_ContractName = getResourceString("ContractName");
		material_Validate = getResourceString("Validate");
	}
	
    /**
	* 描述：<获得Excel文件并解析数据>
	* @author <luoxiaolong>
	* @param  <null>
	* @return  <null>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
	public void importData(KDTable materialTableInfo, KDTable tblTableMain, List getMaterialInfos){
		
		allRightDataRow = new ArrayList();
		
		errorAllInfos = new ArrayList();
		
		material_dataRepeat = new ArrayList();
		
		/**清空集合*/
		supplierIsNullList = new ArrayList();
    	/*第二种方式，POI方式导入*/
        JFileChooser chooser = new JFileChooser(new File("c:\\"));
        /*设置过虑器*/
        chooser.setFileFilter(new DataImportFilterType());
        /*弹出对话框*/
        int BB = chooser.showDialog(null, EASResource.getString(MATERIALINFO_RESOURCEPATH,"importForExcel"));
        
        if(BB == 1){
        	return ;
        }
        
        /*判断是否选中文件*/
        if(chooser.getSelectedFile() == null){
        	return ;
        }
        /*获得选取文件路径*/
        String filePath = chooser.getSelectedFile().getPath();
        /*创建对Excel工作簿文件的引用*/
	    HSSFWorkbook excelWork = null;
	    HSSFSheet sheet = null;
		try {
			excelWork = new HSSFWorkbook(new FileInputStream(filePath));
			 /**创建对工作表的引用,按索引引用,在Excel文档中，第一张工作表的缺省索引是0*/
			sheet = excelWork.getSheetAt(0);
		} catch (Exception e) {
			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),getResourceString("PleaseConfirmFileIsRight"));
			return ;
		} 
		/**创建一个叠代器*/
		Iterator it = sheet.rowIterator();
			
		/**声明一个数组，存入基础的列名*/
		String[] baseColumnName = {material_Type,material_Number,material_Name,material_Model,material_Unit,material_Price,material_QuoteTime,material_Supplier,material_ProjectName,material_ContractName,material_Validate};
			
		/**声明一个空的集合，存入特性指标列名*/
		List otherColumnList = new ArrayList();
			
		/**声明一个空的Map，存入特性指标*/
		Map otherColumnMap = new HashMap();
			
		/**声明一个空集合，存入基础的列名，列以键值对的形式保存，键为列名，值为列号*/
		List baseColumnList = new ArrayList();
			
		/**声明一个空的Map，存入基础的列*/
		Map baseColumnMap = new HashMap();
			
		/**声明一个集合存入每一行的数据*/
		List oneRowDataList = new ArrayList();
			
		/**声明一个集合，存入所有列的数据*/
	    List getAllRowDataFormExcel = new ArrayList();	
	    	
	    /*判断是否符合模板*/
	    if(!judgeIsModel(sheet.rowIterator(),baseColumnName)){
	    	return ;
	    }
	    	
	    if(sheet.getLastRowNum() < 1){
	    	MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),getResourceString("ModelError"));
	    	return ;
	    }else if(sheet.getLastRowNum() < 2){
	    	MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),getResourceString("QuoteInformationNull"));
	    	return ;
	    }
	    
		/*循环读取每一行*/
		while(it.hasNext()){
				/**获得行对象*/
				HSSFRow row = (HSSFRow) it.next();
				/*不解析第一行*/
				if(row.getRowNum() < 1){
					continue ;
				}
				/**声明一个列对象*/
				HSSFCell cell = null;
				Map rowMap = new HashMap();
				for (int i = 0; i < row.getLastCellNum(); i++) {
				
				cell = row.getCell(i);
				//如果编码输入的是数字类型的值，就把它转换成字符串类型
				if (i == 1 && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
					String cellValue = null;
					
					if(null != cell){
						/*输出单元内容，cell.getStringCellValue()就是取所在单元的值*/
						if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
							cellValue = cell.getStringCellValue();
						}else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								double d = cell.getNumericCellValue();
								DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								cellValue = format.format(HSSFDateUtil.getJavaDate(d));
							}else{
//								cellValue = String.valueOf(cell.getNumericCellValue());
								DecimalFormat df = new DecimalFormat("###000.0000000000000000"); 
								cellValue = df.format(cell.getNumericCellValue());
								if(cellValue.indexOf(".") != -1){
									String m = cellValue.substring(cellValue.indexOf("."));
									if(Double.parseDouble(m) == 0){
										cellValue = cellValue.substring(0, m.indexOf("0") + cellValue.indexOf(".") -1);
									}else{
										cellValue = cellValue.substring(0, cellValue.indexOf(".")+7);
									}
								}
							}
						}else if(cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
							cellValue = String.valueOf(cell.getBooleanCellValue());
						}else{
							cellValue = "";
						}
					}else{
						cellValue = "";
					}

					int m = -1;
					/*解析第二行的列*/
					if(row.getRowNum() == 1 ){
						for(int j = 0; j < baseColumnName.length; j ++){
							/*判断是否为基础列，并记录是那一列*/
							if(cellValue.equals(baseColumnName[j])){
								baseColumnMap.put("base_" + i,baseColumnName[j]);
								baseColumnList.add(baseColumnMap);
								m = 1;
 								break ;
							}
						}
						if(cellValue != null && !"".equals(cellValue) && m == -1){
							otherColumnMap.put("other_" + i,cellValue);
							otherColumnList.add(otherColumnMap);
						}
					}
						
					/*解析>2行的列*/
					if(row.getRowNum() > 1){
						Map thisRow = null;
						String key = null;
						int keyIndex = -1;
						
						int n = -1;
						/*循环基础列*/
						for(int j = 0; j < baseColumnList.size(); j ++){
							thisRow = (Map)baseColumnList.get(j);
							key = thisRow.keySet().toArray()[j].toString();
							keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
 							if(keyIndex == i){
								/*以键值对的形式保存值*/
								rowMap.put(thisRow.get(key), cellValue);
								n = 1;
									break;
								}
							}
							
							if(n == -1){
								/*循环特性指标列*/
								for(int j = 0; j < otherColumnList.size(); j ++){
									thisRow = (Map)otherColumnList.get(j);
									key = thisRow.keySet().toArray()[j].toString();
									keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
									if(keyIndex == i && cellValue != null){
										String tempCellValue = cellValue;
										try{
											cellValue = String.valueOf(Double.parseDouble(cellValue));
											cellValue = cellValue.substring(0,cellValue.indexOf("."));
										}catch(Exception ex){
											cellValue = tempCellValue;
										}
										/*以键值对的形式保存值*/
										rowMap.put(thisRow.get(key), cellValue);
										break;
									}
								}
							}
						}
						
					}

					if(row.getRowNum() > 1){
						/*将这一行解析的数据存入集合中*/
						oneRowDataList.add(rowMap);
					}
					
					/*用户数据处理*/
					if(row.getRowNum() > 1){
						
						int isExist = -1;
						List newList = oneRowDataList;
						for(int n = 0; n< newList.size(); n ++){
							Map map = (Map) newList.get(n);
							Object[] obj = map.values().toArray();
							for(int f = 0; f < obj.length ; f ++){
								if(!"".equals(obj[f].toString().trim())){
									isExist = 1;
									break ;
								}
							}
						}
							
						if(isExist == 1){
							
							/*验证该行数据是否与已有表中数据重复*/
							validateDataIsRepeat(oneRowDataList,row.getRowNum(),baseColumnName,materialTableInfo, tblTableMain, getMaterialInfos, otherColumnList);
							
							/*验证通过后将这一列数据存入集合中*/
							getAllRowDataFormExcel.add(oneRowDataList);
							/*清空集合*/
							oneRowDataList = new ArrayList();
						}
					}
		}

		/*供应商为空或不存在*/
		if(supplierIsNullList.size() > 0){
			StringBuffer isNull = new StringBuffer();
			for(int k = 0; k < supplierIsNullList.size(); k ++){
				if(k == (supplierIsNullList.size()-1)){
					isNull.append(supplierIsNullList.get(k).toString());
				}else{
					isNull.append(supplierIsNullList.get(k).toString() + "、");
				}
			}
//			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),isNull + "行，供应商为空或不存在！");
//			errorAllInfos.add(getResourceString("Di") + isNull + getResourceString("SupplierIsNotExist"));
//			return ;
		}
		
		/*数据重复*/
		if(material_dataRepeat.size() > 0){
			StringBuffer isNull = new StringBuffer();
			for(int k = 0; k < material_dataRepeat.size(); k ++){
				if(k == (material_dataRepeat.size()-1)){
					isNull.append(material_dataRepeat.get(k).toString());
				}else{
					isNull.append(material_dataRepeat.get(k).toString() + "、");
				}
			}
//			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),isNull + "行数据与材料报价信息表中的数据重复，或者与Excel表中数据重复！");
//			errorAllInfos.add(getResourceString("Di") + isNull + getResourceString("DataRepeat3"));
		}
		
		StringBuffer errorInfo = new StringBuffer();
		for(int i = 0; i < errorAllInfos.size(); i ++){
			errorInfo.append(errorAllInfos.get(i) + "\r\n");
		}
		
		int answerInt = -1;
		/*假若存在错误信息*/
		if(!"".equals(errorInfo.toString())){
			answerInt = MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),errorInfo.toString());
		}
		
		if(answerInt != -1 && answerInt != 0){
			return ;
		}
		
		/*向数据库中插入数据*/
		if(addImportData(allRightDataRow,baseColumnName) >= 1){
			if (allRightDataRow.size() == 0) {
				MsgBox.showWarning("没有导入成功的数据行。");
			} else {

				MsgBox.showDetailAndOK(null, getResourceString("ImportDataSuccess"), allRightDataRow.size()
						+ getResourceString("ImportNRow"), 1);
			}
		}else{
			if(allRightDataRow.size() != 0){
				MsgBox.showDetailAndOK(null,getResourceString("OperateFail"),getResourceString("OperateFail"),1);
			}else{
				if (allRightDataRow.size() == 0) {
					MsgBox.showWarning("没有导入成功的数据行。");
				} else {
					MsgBox.showDetailAndOK(null, getResourceString("ImportDataSuccess"), allRightDataRow.size()
							+ getResourceString("ImportNRow"), 1);
				}
			}
		}
	}
	
	/**
	* 描述：<验证数据是否重复>
	* @author <luoxiaolong>
	* @param  <oneRowDataList 从Excel解析出来的一行数据，rowNum 解析Excel到几行了，baseColumnName 存基础列的数组>
	* @return  <true 验证通过，false 验证失败>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
    private void validateDataIsRepeat(List oneRowDataList,int rowNum,String[] baseColumnName, KDTable materialTableInfo, KDTable tblTableMain, List getMaterialInfos, List otherColumnName){
    	
    	/**这一行中是否有错误数据 false没有 true有*/
    	boolean isErrorData = false;
    	
    	Map rowMap = (Map) oneRowDataList.get(0);
    	
    	Object[] valuesArray = rowMap.values().toArray();
    	/*循环判断该行中是否有英文逗号*/
    	for(int i = 0; i < valuesArray.length; i ++){
    		if(null != valuesArray[i]){
    			String valueString = valuesArray[i].toString();
    			if(valueString.indexOf(",") != -1){
					errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Comma"));
    				isErrorData = true;
    				break ;
    			}
    		}
    	}
    	
    	/**声明一个数组，存入必录列的列名*/
		String[] notNullColumnNames = {material_Type,material_Number,material_Name,material_Model,material_Unit,material_Price,material_QuoteTime,material_Supplier};
		
		/*判断是必录项是否已经录入，*/
		for(int i = 0; i < notNullColumnNames.length; i ++){
			if(null == rowMap.get(notNullColumnNames[i]) || "".equals(rowMap.get(notNullColumnNames[i]).toString())){
//	    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("Row2") + notNullColumnNames[i] + getResourceString("ColumnIsNotNull"));
				errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + notNullColumnNames[i] + getResourceString("ColumnIsNotNull"));
				isErrorData = true;
//	    		return false;
	    	}
			if(null != rowMap.get(notNullColumnNames[i]) && rowMap.get(notNullColumnNames[i]).toString().length() > 80){
				errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + notNullColumnNames[i] + getResourceString("TooLength1"));
				isErrorData = true;
			}
		}
    	
		/**非必录项，存入字符串长度需要限制的列*/
		String[] limitCharacterLengths = {material_ProjectName,material_ContractName,material_Validate};
		for(int i = 0; i < limitCharacterLengths.length; i ++){
			if(null != rowMap.get(limitCharacterLengths[i]) && rowMap.get(limitCharacterLengths[i]).toString().trim().length() > 80){
//				MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + limitCharacterLengths[i] + getResourceString("TooLength"));
				errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + limitCharacterLengths[i] + getResourceString("TooLength2"));
//				return false;
			}
			
			if(null != rowMap.get(limitCharacterLengths[i]) && "".equals(rowMap.get(limitCharacterLengths[i]).toString().trim())){
				errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + limitCharacterLengths[i] + getResourceString("ColumnIsNullIgnore"));
			}
			
			if(null == rowMap.get(limitCharacterLengths[i])){
				errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + limitCharacterLengths[i] + getResourceString("ColumnIsNullIgnore"));
			}
		}
		
		/*判断特性指标列是否录入*/
		if(otherColumnName.size() > 0 && null != otherColumnName.get(0)){
			Map oterColumnMap = (Map) otherColumnName.get(0);
			Object[] otherColumnArray = oterColumnMap.values().toArray();
			for(int i = 0; i < otherColumnArray.length; i ++){
				if(null == otherColumnArray[i]){
					errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + otherColumnArray[i].toString().trim() + getResourceString("ColumnIsNullIgnore"));
					continue ;
				}
				if(null != rowMap.get(otherColumnArray[i].toString().trim()) && "".equals(rowMap.get(otherColumnArray[i]).toString().trim())){
					errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + otherColumnArray[i].toString().trim() + getResourceString("ColumnIsNullIgnore"));
				}else if(null == rowMap.get(otherColumnArray[i].toString().trim())){
					errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("Row2") + otherColumnArray[i].toString().trim() + getResourceString("ColumnIsNullIgnore"));
				}
			}
			
		}
    	/**
    	 * 在这里要做三个验证、
    	 * 第一、判断是物料类型是否相同、名称、编码两列是否在数据库中存在
    	 * 第二、（单价、报价时间、供应商）三字段值是否重复
    	 * 第三、根据物料类别判断是否存在该指标
    	 */
		Pattern pDate = Pattern.compile("[1-9][0-9]{3}\\-((0[0-9]|1[0-2])|[1-9])\\-((0[0-9]|1[0-9]|2[0-9]|3[0-1])|[1-9])");
		Pattern pDouble = Pattern.compile("([0-9]{1,21})|([0-9]{1,21}\\.[0-9]{0,100})");
		Pattern pIsNumber = Pattern.compile("([0-9]{1,1000})|([0-9]{1,1000}\\.[0-9]{0,1000})");
		
		String priceStr = String.valueOf(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());
		double excelPrice = 0;
		
		if(!"".equals(priceStr) && pIsNumber.matcher(priceStr).matches()){
			excelPrice = Double.parseDouble(String.valueOf("".equals(priceStr) ? "0" : priceStr));
			String excelPriceString = String.valueOf(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());
			
			boolean a = false;
			/*解析集合，获得单价、报价时间、供应商、物料分类、以及特性指标的值，并验证格式*/
			if(excelPriceString.indexOf(".") != -1){
				a = true;
				if(excelPriceString.substring(0,excelPriceString.indexOf(".")).toString().length() > 12){
//					MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PriceTooLength"));
					errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PriceTooLength"));
					isErrorData = true;
//					return false;
				}
			}else{
				a = true;
				if(excelPriceString.length() > 12){
//					MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PriceTooLength"));
					errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PriceTooLength"));
					isErrorData = true;
//					return false;
				}
			}
			
			if(!(pDouble.matcher(excelPriceString).matches()) &&  !a){
//				MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PleaceInputNumberType"));
				errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PleaceInputNumberType"));
				isErrorData = true;
//				return false;
			}
		}else if(!(pIsNumber.matcher(priceStr).matches()) && !"".equals(priceStr)){
//			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PleaceInputNumberType"));
			errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("PleaceInputNumberType"));
			isErrorData = true;
//			return false;
		}
		
     	String excelSupplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
    	String supId = "";
    	/*判断用户是否输入供应商*/
    	if(!"".equals(excelSupplier)){
    		excelSupplier = dealExcelData(excelSupplier);
    		Map supMap = new HashMap();
    		supMap.put("FName_l2", excelSupplier);
    		/*根据供应名称查询项目id*/
    		supId = materialInfoUI.getIdByName("T_BD_Supplier", "FID", supMap);
    		supId = String.valueOf(supId == null ? "" : supId.toString().trim());
    	}
    	
    	String validate = String.valueOf((rowMap.get(material_Validate) == null || "2050-10-1".equals(rowMap.get(material_Validate).toString().trim())) ? "2050-11-11" : rowMap.get(material_Validate).toString().trim());
    	
    	validate = String.valueOf("".equals(validate) ? "2050-12-31" : validate);
    	
    	boolean vb = false;
    	/*判断日期是否则符合格式标准*/
    	if(!pDate.matcher(validate).matches()){
    		vb = true;
//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PleaseInputDateType2"));
    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PleaseInputDateType2"));
    		isErrorData = true;
//    		return false;
    	}
    	/*判断有效日期是否合法*/
    	if(null != validate && !"".equals(validate) && !materialInfoUI.validate(validate) && !vb){
    		vb = true;
//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("ValidateError"));
    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("ValidateError"));
    		isErrorData = true;
//    		return false;
    	}
    	
    	
    	String excelQuoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? (new java.util.Date()).toLocaleString() : rowMap.get(material_QuoteTime).toString());
    	/*对报价日期作验证*/
    	if(!"".equals(excelQuoteTime) && null != rowMap.get(material_QuoteTime)){
    		boolean b = false;
	    	/*判断日期是否则符合格式标准*/
	    	if(!pDate.matcher(excelQuoteTime).matches()){
	    		b = true;
	//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PleaseInputDateType"));
	    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PleaseInputDateType"));
	    		isErrorData = true;
	//    		return false;
	    	}
	    	
	    	/*判断报价日期是否合法*/
	    	if(!materialInfoUI.validate(excelQuoteTime) && !b){
	    		b = true;
	//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("QuoteTimeError"));
	    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PleaseInputDateType"));
	    		isErrorData = true;
	//    		return false;
	    	}
	    	
	    	if(excelQuoteTime.indexOf(" ") != -1){
	    		excelQuoteTime = excelQuoteTime.substring(0,excelQuoteTime.indexOf(" "));
	    	}
	    	
	    	/*判断用户是否输入了报价时间*/
	    	if(!b && !vb){
		    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    	try{
		    		long excelTimeL = format.parse(excelQuoteTime).getTime();
		    		long validateL = format.parse(validate).getTime();
		    		
		    		/*有效日期不能小于报价时间*/
		    		if(excelTimeL > validateL){
		//        		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),getResourceString("TimeError"));
		        		errorAllInfos.add(getResourceString("TimeError"));
		        		isErrorData = true;
		//        		return false;
		        	}
		    	}catch(Exception ex){
		    		handUIException(ex);
		    	}
	    	}
    	}
    	
    	/*根据物料类别查询该物料是属于哪个种类*/
    	String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
    	//excelMaterialType = dealExcelData(excelMaterialType);
    	Map materialGroupMap = new HashMap();
		materialGroupMap.put("FNumber", excelMaterialType);
		String excelMaterialGroupId = materialInfoUI.getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);
		excelMaterialGroupId = String.valueOf(excelMaterialGroupId == null ? "" : excelMaterialGroupId.trim());

		Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
		/*根据物料名称和编码确定materialId*/
		String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
		//materialName = dealExcelData(materialName);
		String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
		//materialNumber = dealExcelData(materialNumber);
		if(tDouble.matcher(materialNumber).matches()){
			materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
		}
		if(tDouble.matcher(materialName).matches()){
			materialName = materialName.substring(0, materialName.indexOf("."));
		}
		
		Map materialMap = new HashMap();
		materialMap.put("FName_l2", materialName.trim());
		materialMap.put("FNumber", materialNumber.trim());
		materialMap.put("FMaterialGroupID", excelMaterialGroupId);
		String materialId = materialInfoUI.getMaterialIDByFNameAndFNumber("T_BD_Material","FID",materialMap);
		
    	/*存入 (从Excel取出的)特性指标列*/
    	List fNamesExcel = new ArrayList();
    	
    	/*判断是不是基础列，如果不是基础列，那就是特性指标列*/
    	Set set = rowMap.keySet();
    	Iterator iColumn = set.iterator();
    	while(iColumn.hasNext()){
    		String columnName = iColumn.next().toString().trim();
    		int m = -1;
    		for(int i = 0; i < baseColumnName.length; i ++){
    			if(columnName.equals(baseColumnName[i])){
    				m = 1;
    				break ;
    			}	
    		}
    		if(m == -1){
    			fNamesExcel.add(columnName);
    		}
    	}

		//    	boolean bMaterialType = false;
		//    	/*第一步、判断是物料类型是否相同*/
		//    	if((null == excelMaterialGroupId || "".equals(excelMaterialGroupId)) && !"".equals(excelMaterialType)){
		//    		bMaterialType = true;
		////    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum-1) + getResourceString("MaterialTypeNotExist"));
		//    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("MaterialTypeNotExist"));
		//    		isErrorData = true;
		////    		return false;
		//    	}else if("".equals(excelMaterialType)){
		//    		bMaterialType = true;
		//    	}
    	
    	/*判断用户是否输入了名称和编码*/
    	if(!"".equals(materialName) || !"".equals(materialNumber)){
	    	if(null == materialId){
	//			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("NameAndNumberIsNotExist"));
				errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("NameAndNumberIsNotExist"));
				isErrorData = true;
	//			return false;
			}
    	}

		//    	String parentMaterialID = tblTableMain.getRow(tblTableMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		//    	Map mapMaterial = new HashMap();
		//    	mapMaterial.put("FID", parentMaterialID);
		//    	String parentMaterialGroupID = materialInfoUI.getIdByName("T_BD_Material", "FMaterialGroupID", mapMaterial);
		//    	if(!excelMaterialGroupId.equals(parentMaterialGroupID) && !bMaterialType){
		////    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum-1) + getResourceString("MaterialTypeDiferent"));
		//    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("MaterialTypeDiferent"));
		//    		isErrorData = true;
		////    		return false;
		//    	}
    	
    	/*验证数据重复（一）获得右下角表中的数据*/
    	for(int i = 0; i < materialTableInfo.getRowCount(); i ++){
    		IRow row = materialTableInfo.getRow(i);
    		double parentPrice = Double.parseDouble(row.getCell("price").getValue().toString());
    		String parentQuoteTime = row.getCell("quoteTime").getValue().toString();
    		if(parentQuoteTime.indexOf(" ") != -1){
    			parentQuoteTime = parentQuoteTime.substring(0,parentQuoteTime.indexOf(" "));
    		}
    		String parentSupplier = String.valueOf(row.getCell("supplier").getValue() == null ? "" : row.getCell("supplier").getValue().toString());
    		
    		/*第二步，验证数据是否重复*/
    		if(excelPrice == parentPrice && excelQuoteTime.equals(parentQuoteTime) && excelSupplier.equals(parentSupplier)){
    			int a = -1;
				for(int k = 0; k < material_dataRepeat.size(); k ++){
					if(null == material_dataRepeat.get(k)) continue;
					int sub = Integer.parseInt(material_dataRepeat.get(k).toString());
					if(rowNum + 1 == sub){
						a = 1;
					}
				}
				if(a == -1){
					material_dataRepeat.add(new Integer(rowNum + 1));
					errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("DataRepeat3"));
					isErrorData = true;
				}
    		}
    	}
    	
    	/*验证数据重复（二）*/
    	/*循环判断(二)*/
		for(int j = 0; j < getMaterialInfos.size(); j ++){
			Map materialInfo = (Map) getMaterialInfos.get(j);
			double pPrice = Double.parseDouble(String.valueOf(materialInfo.get("FPrice") == null ? "0" : materialInfo.get("FPrice").toString().trim()));
			String pQuoteTime = String.valueOf(materialInfo.get("FQuoteTime") == null ? new java.util.Date().toLocaleString() : materialInfo.get("FQuoteTime").toString().trim());
			if(pQuoteTime.indexOf(" ") != -1){
				pQuoteTime = pQuoteTime.substring(0, pQuoteTime.indexOf(" "));
			}
			String fMaterialID = String.valueOf(materialInfo.get("FMaterialID") == null ? "" : materialInfo.get("FMaterialID").toString().trim());
			String fName = String.valueOf(materialInfo.get("FSupplierID") == null ? "" : materialInfo.get("FSupplierID").toString().trim());
			
			/*判断是否存在materialId*/
			if(null != materialId){
				/*判断单价、报价时间、供应商是否相同*/
				if(materialId.equals(fMaterialID) 
						&& pPrice == excelPrice
						&& pQuoteTime.equals(excelQuoteTime)
						&& fName.equals(supId)){
					if("".equals(excelSupplier) || "".equals(supId)){
						int a = -1;
						for(int k = 0; k < supplierIsNullList.size(); k ++){
							if(null == supplierIsNullList.get(k)) continue;
							int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
							if(rowNum + 1 == sub){
								a = 1;
							}
						}
						if(a == -1 && !"".equals(excelSupplier)){
							errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("SupplierIsNotExist"));
							supplierIsNullList.add(new Integer(rowNum + 1));
							isErrorData = true;
						}
					}else{
//						MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("rowDataRepeat2"));
//						return false;
						int a = -1;
						for(int k = 0; k < material_dataRepeat.size(); k ++){
							if(null == material_dataRepeat.get(k)) continue;
							int sub = Integer.parseInt(material_dataRepeat.get(k).toString());
							if(rowNum + 1 == sub){
								a = 1;
							}
						}
						if(a == -1){
							material_dataRepeat.add(new Integer(rowNum + 1));
							errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("DataRepeat3"));
							isErrorData = true;
						}
					}
				}else if("".equals(excelSupplier) || "".equals(supId)){
					int a = -1;
					for(int k = 0; k < supplierIsNullList.size(); k ++){
						if(null == supplierIsNullList.get(k)) continue;
						int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
						if(rowNum + 1 == sub){
							a = 1;
						}
					}
					if(a == -1 && !"".equals(excelSupplier)){
						errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("SupplierIsNotExist"));
						supplierIsNullList.add(new Integer(rowNum + 1));
						isErrorData = true;
					}
				}
			}
		}
		
		/*验证数据重复（三）*/
		if(materialTableInfo.getRowCount() == 0){
			/*供应商为空*/
			if(null != supId && "".equals(supId)){
				int a = -1;
				for(int k = 0; k < supplierIsNullList.size(); k ++){
					if(null == supplierIsNullList.get(k)) continue;
					int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
					if(rowNum + 1 == sub){
						a = 1;
					}
				}
				if(a == -1 && !"".equals(excelSupplier)){
					errorAllInfos.add(getResourceString("Di") + (rowNum + 1) + getResourceString("SupplierIsNotExist"));
					supplierIsNullList.add(new Integer(rowNum + 1));
					isErrorData = true;
				}
			}
		}
		
		Map newMaterialInfoMap = new HashMap();
		newMaterialInfoMap.put("FPrice", String.valueOf(excelPrice));
		newMaterialInfoMap.put("FQuoteTime", excelQuoteTime);
		newMaterialInfoMap.put("FMaterialID", materialId);
		newMaterialInfoMap.put("FSupplierID", supId);
		getMaterialInfos.add(newMaterialInfoMap);
    	
    	/*第三步，验证Excel数据表中的特性指标是否存在*/
		
		/*查出该物料分类下有哪些指标*/
    	Map map = new HashMap();
    	map.put("FMaterialGroupID", excelMaterialGroupId);
		List fParentNames = materialInfoUI.getMaterialIndexFNameByMaterialGroup("T_MTR_MaterialIndex","FName_l2",map);
    	for(int i = 0; i < fNamesExcel.size(); i ++){
    		String fName = fNamesExcel.get(0).toString();
    		int m = -1;
    		for(int j = 0; j < fParentNames.size(); j ++){
    			Map fNameMap = (Map) fParentNames.get(j);
    			if(fName.equals(fNameMap.get("FName_l2").toString())){ 
    				m = 1;
    			}
    		}
    		if(m == -1 && fNamesExcel.size() != 0){
//    			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1)+ getResourceString("MaterialIndexNotExist"));
//    			errorAllInfos.add(getResourceString("Di") + (rowNum + 1)+ getResourceString("MaterialIndexNotExist"));
    			isErrorData = true;
//    			return false;
    		}
    	}
    	
    	/*操作“项目名称”一列*/
		String projName = String.valueOf(rowMap.get(material_ProjectName) == null ? "" : rowMap.get(material_ProjectName).toString());
		projName = dealExcelData(projName);
		Map projMap = new HashMap();
		projMap.put("FName_l2", projName);
		/*根据项目名称查询项目id*/
		String projId = materialInfoUI.getIdByName("T_FDC_CurProject", "FID", projMap);
		projId = String.valueOf(projId == null ? "" : projId);
		if("".equals(projId) && !"".equals(projName)){
			errorAllInfos.add(getResourceString("Di") + (rowNum + 1)+ getResourceString("ProjectIsNotExist"));
		}
			
		/*操作“合同名称”一列*/
		String conName = String.valueOf(rowMap.get(material_ContractName) == null ? "" : rowMap.get(material_ContractName).toString());
		conName = dealExcelData(conName);
		Map conMap = new HashMap();
		conMap.put("FName", conName);
		/*根据合同名称查询项目id*/
		String conId = materialInfoUI.getIdByName("T_CON_ContractBill", "FID", conMap);
		conId = String.valueOf(conId == null ? "" : conId);
		if("".equals(conId) && !"".equals(conName)){
			errorAllInfos.add(getResourceString("Di") + (rowNum + 1)+ getResourceString("ContractIsNotExist"));
		}
			
	    if(!isErrorData){
	    	allRightDataRow.add(oneRowDataList);
	    }

    }
    
    public String dealExcelData(String excelDataStr){
    	Pattern p1 = Pattern.compile("0{1,10}[1-9]{1,10}\\.[0-9]{0,1000}");
    	Pattern p2 = Pattern.compile("0{1,10}[1-9]{1,10}");
		if(p1.matcher(excelDataStr).matches()){
			if(excelDataStr.indexOf(".") != -1){
				String[] excelDataArray = excelDataStr.split("\\.");
				String excelStrBefore = excelDataArray[0];
				excelStrBefore = excelStrBefore.substring(excelStrBefore.lastIndexOf("0") + 1,excelStrBefore.length());
				String excelStrAfter = excelDataArray[1];
				excelStrAfter = excelStrAfter.substring(0,excelStrAfter.indexOf("0"));
				excelDataStr = excelStrBefore + "." + excelStrAfter;
			}else{
				excelDataStr = excelDataStr.substring(excelDataStr.lastIndexOf("0") + 1,excelDataStr.length());
			}
		}
		if(p2.matcher(excelDataStr).matches()){
			excelDataStr = excelDataStr.substring(excelDataStr.lastIndexOf("0") + 1,excelDataStr.length());
		}
		return excelDataStr;
    }
    /**
	* 描述：<判断模板是否正确>
	* @author <luoxiaolong>
	* @param  <it,baseColumnName>
	* @return  <boolean>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
    public boolean judgeIsModel(Iterator it,String[] baseColumnName){
    	
    	/**声明一个变量，存入Excel表中有多少行数据*/
    	int rowCount = -1;
    	
    	while(it.hasNext()){
			/**获得行对象*/
			HSSFRow row = (HSSFRow) it.next();
			
			/*解析 “列名” 行*/
			if(row.getRowNum() == 1){
				++ rowCount; 
				/*循环判断在“列名”行里是否包含合部的基础列，如果是则符合模板，反之则不符合*/
				for(int i = 0; i < baseColumnName.length; i ++){
					int m = -1;
					for(int j = 0; j < row.getLastCellNum(); j ++){
						HSSFCell cell = row.getCell(j);
						if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING && baseColumnName[i].equals(cell.getStringCellValue())){
							m = 1;
							break ;
						}
					}
					/*不存在基础列时*/
					if(m == -1){
						MsgBox.showConfirm3(null,getResourceString("ModelError2"),EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MustHave") + baseColumnName[i] + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Column"));
						return false;
					}
				}
				break ;
			}
    	}
    	
    	/*判断如果在Excel表中没有数据*/
    	if(rowCount == -1){
    		MsgBox.showConfirm3(null,EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail") + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"),EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail") + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"));
    		return false;
    	}
    		
    	return true;
    }
    
    /**
	* 描述：<向数据库里导入数据>
	* @author <luoxiaolong>
	* @param  <allDataRowExcel,baseColumnName>
	* @return  <int>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
    public int addImportData(List allDataRowExcel, String[] baseColumnName){
    	
    	
    	/**声明一个返回值变量*/
    	int result = 0;

    	/*存入从Excel解析出来的所有行*/
    	List allDataRow = allDataRowExcel;
    	
    	/*根据供应商查询出，供应商所对应的最新报价时间*/
    	List getLastestQuoteTime = getLastestQuoteTime();
    	
    	/*循环插入所有数据行至数据库中*/
    	for(int i = 0; i < allDataRow.size(); i ++){
    		if(null == allDataRow.get(i)){
    			continue ;
    		}
    		List row = (List) allDataRow.get(i);
    		
    		/*解析每一行数据*/
    		for(int j = 0; j < row.size(); j ++){
    			
    			Map rowMap = (Map) row.get(j);
    			
    			/**设置有郊日期为一个极限时间*/
    			Timestamp terminalDate = Timestamp.valueOf("2050-12-31 00:00:00");
    			
    	    	/**设置有效无效 0无效 1有效*/
    			int isValidDate = 0;
    			if(terminalDate.getTime() > new java.util.Date().getTime()){
    				isValidDate = 1;
    			}
    			
    			/*声明一个集合，存入每一行的数据*/
    			List params = new ArrayList();
    			
    			/**取得主键ID*/
    			String bosId = materialInfoUI.getBOSID("9D390CBB");   			
    			params.add(bosId);
    			Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
    			/*根据物料名称和编码确定materialId*/
    			String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
    			//materialName = dealExcelData(materialName);
    			String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
    			//materialNumber = dealExcelData(materialNumber);
    			Map materialMap = new HashMap();
    			materialMap.put("FName_l2", materialName);
    			materialMap.put("FNumber", materialNumber);
    			String fMaterialID = materialInfoUI.getMaterialIDByFNameAndFNumber("T_BD_Material","FID",materialMap);
    			//如果查询的物料编码为空 就不能插入到数据库
    			if (fMaterialID == null || "".equals(fMaterialID)) {
					MsgBox.showWarning("根据物料编码和名称没有找到对应的物料ID，请填入正确的物料编码和名称.");
					SysUtil.abort();
				}
    			params.add(fMaterialID);
    			
    			/*操作“项目名称”一列*/
    			String projName = String.valueOf(rowMap.get(material_ProjectName) == null ? "" : rowMap.get(material_ProjectName).toString());
    			projName = dealExcelData(projName);
    			Map projMap = new HashMap();
    			projMap.put("FName_l2", projName);
    			/*根据项目名称查询项目id*/
    			String projId = materialInfoUI.getIdByName("T_FDC_CurProject", "FID", projMap);
    			if(projId != null){
    				params.add(projId);
    			}else{
    				params.add("");
    			}
    			
    			
    			/*操作“合同名称”一列*/
    			String conName = String.valueOf(rowMap.get(material_ContractName) == null ? "" : rowMap.get(material_ContractName).toString());
    			conName = dealExcelData(conName);
    			Map conMap = new HashMap();
    			conMap.put("FName", conName);
    			/*根据合同名称查询项目id*/
    			String conId = materialInfoUI.getIdByName("T_CON_ContractBill", "FID", conMap);
    			if(conId != null){
    				params.add(conId);
    			}else{
    				params.add("");
    			}
    			
    			BigDecimal price = new BigDecimal(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());
    			params.add(price);
    			String quoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? "2010-11-11" : rowMap.get(material_QuoteTime).toString());
    			params.add(quoteTime);
    			
    			/*操作“供应商”一列*/
    			String supplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
    			supplier = dealExcelData(supplier);
    			Map supMap = new HashMap();
    			supMap.put("FName_l2", supplier);
    			/*根据供应商名称查询供应商id*/
    			String supId = materialInfoUI.getIdByName("T_BD_Supplier", "FID", supMap);
    			if(supId != null){
    				params.add(supId);
    			}else{
    				params.add("");
    			}
    			
    			/*存入创建人ID*/
    			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    			params.add(user.getId().toString());
    			
    			/*存入创建时间*/
    			Date createTime = null;
				try {
					createTime = FDCDateHelper.getServerTimeStamp();
				} catch (BOSException e1) {
					createTime = new Date();
				}
    			params.add(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
    			
    			/*存入有效期*/
    			String validate = String.valueOf((rowMap.get(material_Validate) == null || "".equals(rowMap.get(material_Validate).toString().trim())) ? "2050-12-31" : rowMap.get(material_Validate).toString().trim());
    			params.add(validate);
    			
    			/*存入是否是最新 1最新 0不是最新*/
    			int isLastestQutote = 1;
    			for(int k = 0; k < getLastestQuoteTime.size(); k ++){
    				Map map = (Map) getLastestQuoteTime.get(k);
    				String fMaxDate = String.valueOf(map.get("FMaxDate") == null ? "1990-1-1" : map.get("FMaxDate").toString());
    				String fSupplierID = String.valueOf(map.get("FSupplierID") == null ? "" : map.get("FSupplierID").toString());
    				if(supId.equals(fSupplierID)){
    					if(fMaxDate.indexOf(" ") != -1){
        					fMaxDate = fMaxDate.substring(0,fMaxDate.indexOf(" "));
        				}
    					if(quoteTime.indexOf(" ") != -1){
    						quoteTime = quoteTime.substring(0,quoteTime.indexOf(" "));
    					}
        				if(Timestamp.valueOf(fMaxDate + " 00:00:00").getTime() > Timestamp.valueOf(quoteTime + " 00:00:00").getTime()){
        					isLastestQutote = 0;
        				}
    				}
    			}
    			params.add(new Integer(isLastestQutote));
    			
    			/*存入是否启用 1启用 0禁用*/
    			params.add(new Integer(1));
    			String fName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
    			params.add(fName);
    			/*存入状态*/
    			params.add("1SAVED");
    			/*存入有效无效*/
    			params.add(String.valueOf(isValidDate == 1 ? "VALID" : "INVALID"));
    			
    			/*存入控制单元*/
    			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
    			params.add(cuId);
    			
    			/*存入组织单元*/
    			String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
    			params.add(orgUnitId);
    			
    			
    			/*存入 (从Excel取出的)特性指标列*/
    	    	List fNamesExcel = new ArrayList();
    	    	
    	    	/*判断是不是基础列，如果不是基础列，那就是特性指标列*/
    	    	Set set = rowMap.keySet();
    	    	Iterator iColumn = set.iterator();
    	    	while(iColumn.hasNext()){
    	    		int k = -1;
    	    		String columnName = iColumn.next().toString().trim();
    	    		for(int m = 0; m < baseColumnName.length; m ++){
    	    			if(columnName.equals(baseColumnName[m])){
    	    				k = 1;
    	    			}
    	    		}
    	    		if(k == -1){
    	    			fNamesExcel.add(columnName);
    	    		}
    	    	}	
    			
    			try {
    				/*调用服务器端Dao执行SQL*/
    				MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "importMaterialInfoSql");
    				
    				/*根据物料类别查询该物料是属于哪个种类*/
    		    	String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
    		    	Map materialGroupMap = new HashMap();
    				materialGroupMap.put("FName_l2", excelMaterialType);
    				String materialGroupId = materialInfoUI.getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);
    				
    				List materialIndexs = getMaterialIndexIdByMaterialId(fMaterialID);
    				
    				/*循环插入指标值*/
    				for(int k = 0; k < materialIndexs.size(); k ++){
    					Map allMaterialIndexMap = (Map) materialIndexs.get(k);
    					String allMaterialIndex = allMaterialIndexMap.get("FID").toString();
    					List newParams = new ArrayList();
    					newParams.add(materialInfoUI.getBOSID("6E5BD60C"));
    					newParams.add(bosId);
    					
    					/*materialIndexId*/
    					String materialIndexId = "";
    					
    					/*指标值*/
    					String indexValue = "";
    					
    					for(int m = 0; m < fNamesExcel.size(); m ++){
        					String fNameExcel = fNamesExcel.get(m).toString().trim();
        					Map filterMap = new HashMap();
        					filterMap.put("FName_l2", fNameExcel);
        					filterMap.put("FMaterialGroupID", materialGroupId);
        				
        					/*根据materialGroupId和fName名称，查询materialIndexId*/
            				Map materialIndexMap = new HashMap();
            				materialIndexMap.put("FMaterialGroupID", materialGroupId);
            				materialIndexMap.put("FName_l2", fNameExcel);
            				
        					materialIndexId = materialInfoUI.getMaterialIndexIdByMaterialGroupIdAndIndexName("T_MTR_MaterialIndex","FID",materialIndexMap);
        					
        					if(null == materialIndexId){
        						continue ;
        					}
        					
        					if(materialGroupId == null){
        						MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),getResourceString("IndexName") + fNameExcel + getResourceString("NotExistInExcel") + (j + 1) + getResourceString("Row"));
        						return -1;
        					}
        					
        					if(materialIndexId.equals(allMaterialIndex)){
        						indexValue = String.valueOf(rowMap.get(fNamesExcel.get(m).toString().trim()) == null ? "": rowMap.get(fNamesExcel.get(m).toString().trim()).toString());
        						break ;
        					}
        				}
    					
    					/*materialIndexId*/
    					newParams.add(allMaterialIndex);
    					
    					/*插入指标值*/
    					newParams.add(indexValue);
    					
//    					/*循环删除指标*/
//    					int n = MaterialInfoFactory.getRemoteInstance().deleteMaterialIndexValue(fMaterialID, materialIndexId);
    					
    					/*循环增加指标*/
    					MaterialInfoFactory.getRemoteInstance().addMaterialData(newParams, "importMaterialIndexInfoSql");
    				}
    				
    				
    				
    				++ result;
    			} catch (Exception e) {
    				handUIException(e);
    				logger.error(e.getMessage());
    				/*此时导入失败,不调用handUIException(e)处理异常,在后面给出提示“导入失败”*/
    				--result; 
    			}
    		}
    	}   
    	return result;
    }
    
    /**
	* 描述：<资源文件>
	* @author <luoxiaolong>
	* @param  <tableName,fieldName,filters>
	* @return  <String>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
    public String getResourceString(String resourceKey){ 
    	return EASResource.getString(MATERIALIMPORT_RESOURCEPATH, resourceKey);
    }
    
    /**
	* 描述：<查询最新报价时间>
	* @author <luoxiaolong>
	* @param  <tableName,fieldName,filters>
	* @return  <String>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
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
	* 描述：<根据物料id，获得特性指标id>
	* @author <luoxiaolong>
	* @param  <materialGruopId>
	* @return  <null>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
	private List getMaterialIndexIdByMaterialId(String materialId){
		Map filterMap = new HashMap();
		filterMap.put("FID", materialId);
		String materialGroupId = materialInfoUI.getMaterialGroupIdByMaterialId(filterMap,"T_BD_Material","FMaterialGroupID");
		return getMaterialIndexId(materialGroupId);
	}
	
	/**
	* 描述：<获得特性指标名称>
	* @author <luoxiaolong>
	* @param  <materialGruopId>
	* @return  <null>
	* 创建时间  <2010/11/16> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
	private List getMaterialIndexId(String materialGruopId){
		
		/**声明一个返回值变量*/
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
	
}
