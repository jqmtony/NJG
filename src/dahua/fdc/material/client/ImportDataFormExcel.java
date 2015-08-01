/*
 * @(#)MaterialInfoUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
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
 * �������������ݴ�Excel<p>
 * @author luoxiaolong
 * @version EAS 7.0
 * @see MaterialInfoUI
 */
public class ImportDataFormExcel extends CoreUI{

	MaterialInfoUI materialInfoUI = new MaterialInfoUI();
	
	/**ȱʡ�汾��ʶ*/
	private static final long serialVersionUID = 1L;

	/**��־*/
	private static final Logger logger = CoreUIObject.getLogger(MaterialInfoUI.class);
	
	/**������Դ�ļ�λ��*/
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
    private static final String MATERIALINFO_RESOURCEPATH = "com.kingdee.eas.fdc.material.MaterialInfoResource";
    
    /**�������еĴ�����Ϣ*/
    private List errorAllInfos = null;
    
    /**�������е���ȷ������*/
    private List allRightDataRow = null;
    
    /**Excel���������Ƿ��ظ�*/
	private ArrayList material_dataRepeat = null;
	
	/**��¼��Ӧ���Ƿ�Ϊ��*/
	private List supplierIsNullList = null;
	
	/**����������еġ��������*/
	private String material_Type = null;
	
	/**����������еġ����ϱ��롱*/
	private String material_Number = null;
	
	/**����������еġ��������ơ�*/
	private String material_Name = null;
	
	/**����������еġ�����ͺš�*/
	private String material_Model = null;
	
	/**����������еġ���λ��*/
	private String material_Unit = null;
	
	/**����������еġ����ۡ�*/
	private String material_Price = null;
	
	/**����������еġ�����ʱ�䡱*/
	private String material_QuoteTime = null;
	
	/**����������еġ���Ӧ�̡�*/
	private String material_Supplier = null;
	
	/**����������еġ���Ŀ���ơ�*/
	private String material_ProjectName = null;
	
	/**����������еġ���ͬ���ơ�*/
	private String material_ContractName = null;
	
	/**����������еġ���Ч�ڡ�*/
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
	* ������<���Excel�ļ�����������>
	* @author <luoxiaolong>
	* @param  <null>
	* @return  <null>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	public void importData(KDTable materialTableInfo, KDTable tblTableMain, List getMaterialInfos){
		
		allRightDataRow = new ArrayList();
		
		errorAllInfos = new ArrayList();
		
		material_dataRepeat = new ArrayList();
		
		/**��ռ���*/
		supplierIsNullList = new ArrayList();
    	/*�ڶ��ַ�ʽ��POI��ʽ����*/
        JFileChooser chooser = new JFileChooser(new File("c:\\"));
        /*���ù�����*/
        chooser.setFileFilter(new DataImportFilterType());
        /*�����Ի���*/
        int BB = chooser.showDialog(null, EASResource.getString(MATERIALINFO_RESOURCEPATH,"importForExcel"));
        
        if(BB == 1){
        	return ;
        }
        
        /*�ж��Ƿ�ѡ���ļ�*/
        if(chooser.getSelectedFile() == null){
        	return ;
        }
        /*���ѡȡ�ļ�·��*/
        String filePath = chooser.getSelectedFile().getPath();
        /*������Excel�������ļ�������*/
	    HSSFWorkbook excelWork = null;
	    HSSFSheet sheet = null;
		try {
			excelWork = new HSSFWorkbook(new FileInputStream(filePath));
			 /**�����Թ����������,����������,��Excel�ĵ��У���һ�Ź������ȱʡ������0*/
			sheet = excelWork.getSheetAt(0);
		} catch (Exception e) {
			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),getResourceString("PleaseConfirmFileIsRight"));
			return ;
		} 
		/**����һ��������*/
		Iterator it = sheet.rowIterator();
			
		/**����һ�����飬�������������*/
		String[] baseColumnName = {material_Type,material_Number,material_Name,material_Model,material_Unit,material_Price,material_QuoteTime,material_Supplier,material_ProjectName,material_ContractName,material_Validate};
			
		/**����һ���յļ��ϣ���������ָ������*/
		List otherColumnList = new ArrayList();
			
		/**����һ���յ�Map����������ָ��*/
		Map otherColumnMap = new HashMap();
			
		/**����һ���ռ��ϣ�������������������Լ�ֵ�Ե���ʽ���棬��Ϊ������ֵΪ�к�*/
		List baseColumnList = new ArrayList();
			
		/**����һ���յ�Map�������������*/
		Map baseColumnMap = new HashMap();
			
		/**����һ�����ϴ���ÿһ�е�����*/
		List oneRowDataList = new ArrayList();
			
		/**����һ�����ϣ����������е�����*/
	    List getAllRowDataFormExcel = new ArrayList();	
	    	
	    /*�ж��Ƿ����ģ��*/
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
	    
		/*ѭ����ȡÿһ��*/
		while(it.hasNext()){
				/**����ж���*/
				HSSFRow row = (HSSFRow) it.next();
				/*��������һ��*/
				if(row.getRowNum() < 1){
					continue ;
				}
				/**����һ���ж���*/
				HSSFCell cell = null;
				Map rowMap = new HashMap();
				for (int i = 0; i < row.getLastCellNum(); i++) {
				
				cell = row.getCell(i);
				//���������������������͵�ֵ���Ͱ���ת�����ַ�������
				if (i == 1 && cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
					String cellValue = null;
					
					if(null != cell){
						/*�����Ԫ���ݣ�cell.getStringCellValue()����ȡ���ڵ�Ԫ��ֵ*/
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
					/*�����ڶ��е���*/
					if(row.getRowNum() == 1 ){
						for(int j = 0; j < baseColumnName.length; j ++){
							/*�ж��Ƿ�Ϊ�����У�����¼����һ��*/
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
						
					/*����>2�е���*/
					if(row.getRowNum() > 1){
						Map thisRow = null;
						String key = null;
						int keyIndex = -1;
						
						int n = -1;
						/*ѭ��������*/
						for(int j = 0; j < baseColumnList.size(); j ++){
							thisRow = (Map)baseColumnList.get(j);
							key = thisRow.keySet().toArray()[j].toString();
							keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
 							if(keyIndex == i){
								/*�Լ�ֵ�Ե���ʽ����ֵ*/
								rowMap.put(thisRow.get(key), cellValue);
								n = 1;
									break;
								}
							}
							
							if(n == -1){
								/*ѭ������ָ����*/
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
										/*�Լ�ֵ�Ե���ʽ����ֵ*/
										rowMap.put(thisRow.get(key), cellValue);
										break;
									}
								}
							}
						}
						
					}

					if(row.getRowNum() > 1){
						/*����һ�н��������ݴ��뼯����*/
						oneRowDataList.add(rowMap);
					}
					
					/*�û����ݴ���*/
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
							
							/*��֤���������Ƿ������б��������ظ�*/
							validateDataIsRepeat(oneRowDataList,row.getRowNum(),baseColumnName,materialTableInfo, tblTableMain, getMaterialInfos, otherColumnList);
							
							/*��֤ͨ������һ�����ݴ��뼯����*/
							getAllRowDataFormExcel.add(oneRowDataList);
							/*��ռ���*/
							oneRowDataList = new ArrayList();
						}
					}
		}

		/*��Ӧ��Ϊ�ջ򲻴���*/
		if(supplierIsNullList.size() > 0){
			StringBuffer isNull = new StringBuffer();
			for(int k = 0; k < supplierIsNullList.size(); k ++){
				if(k == (supplierIsNullList.size()-1)){
					isNull.append(supplierIsNullList.get(k).toString());
				}else{
					isNull.append(supplierIsNullList.get(k).toString() + "��");
				}
			}
//			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),isNull + "�У���Ӧ��Ϊ�ջ򲻴��ڣ�");
//			errorAllInfos.add(getResourceString("Di") + isNull + getResourceString("SupplierIsNotExist"));
//			return ;
		}
		
		/*�����ظ�*/
		if(material_dataRepeat.size() > 0){
			StringBuffer isNull = new StringBuffer();
			for(int k = 0; k < material_dataRepeat.size(); k ++){
				if(k == (material_dataRepeat.size()-1)){
					isNull.append(material_dataRepeat.get(k).toString());
				}else{
					isNull.append(material_dataRepeat.get(k).toString() + "��");
				}
			}
//			MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),isNull + "����������ϱ�����Ϣ���е������ظ���������Excel���������ظ���");
//			errorAllInfos.add(getResourceString("Di") + isNull + getResourceString("DataRepeat3"));
		}
		
		StringBuffer errorInfo = new StringBuffer();
		for(int i = 0; i < errorAllInfos.size(); i ++){
			errorInfo.append(errorAllInfos.get(i) + "\r\n");
		}
		
		int answerInt = -1;
		/*�������ڴ�����Ϣ*/
		if(!"".equals(errorInfo.toString())){
			answerInt = MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"),errorInfo.toString());
		}
		
		if(answerInt != -1 && answerInt != 0){
			return ;
		}
		
		/*�����ݿ��в�������*/
		if(addImportData(allRightDataRow,baseColumnName) >= 1){
			if (allRightDataRow.size() == 0) {
				MsgBox.showWarning("û�е���ɹ��������С�");
			} else {

				MsgBox.showDetailAndOK(null, getResourceString("ImportDataSuccess"), allRightDataRow.size()
						+ getResourceString("ImportNRow"), 1);
			}
		}else{
			if(allRightDataRow.size() != 0){
				MsgBox.showDetailAndOK(null,getResourceString("OperateFail"),getResourceString("OperateFail"),1);
			}else{
				if (allRightDataRow.size() == 0) {
					MsgBox.showWarning("û�е���ɹ��������С�");
				} else {
					MsgBox.showDetailAndOK(null, getResourceString("ImportDataSuccess"), allRightDataRow.size()
							+ getResourceString("ImportNRow"), 1);
				}
			}
		}
	}
	
	/**
	* ������<��֤�����Ƿ��ظ�>
	* @author <luoxiaolong>
	* @param  <oneRowDataList ��Excel����������һ�����ݣ�rowNum ����Excel�������ˣ�baseColumnName ������е�����>
	* @return  <true ��֤ͨ����false ��֤ʧ��>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
    private void validateDataIsRepeat(List oneRowDataList,int rowNum,String[] baseColumnName, KDTable materialTableInfo, KDTable tblTableMain, List getMaterialInfos, List otherColumnName){
    	
    	/**��һ�����Ƿ��д������� falseû�� true��*/
    	boolean isErrorData = false;
    	
    	Map rowMap = (Map) oneRowDataList.get(0);
    	
    	Object[] valuesArray = rowMap.values().toArray();
    	/*ѭ���жϸ������Ƿ���Ӣ�Ķ���*/
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
    	
    	/**����һ�����飬�����¼�е�����*/
		String[] notNullColumnNames = {material_Type,material_Number,material_Name,material_Model,material_Unit,material_Price,material_QuoteTime,material_Supplier};
		
		/*�ж��Ǳ�¼���Ƿ��Ѿ�¼�룬*/
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
    	
		/**�Ǳ�¼������ַ���������Ҫ���Ƶ���*/
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
		
		/*�ж�����ָ�����Ƿ�¼��*/
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
    	 * ������Ҫ��������֤��
    	 * ��һ���ж������������Ƿ���ͬ�����ơ����������Ƿ������ݿ��д���
    	 * �ڶ��������ۡ�����ʱ�䡢��Ӧ�̣����ֶ�ֵ�Ƿ��ظ�
    	 * ������������������ж��Ƿ���ڸ�ָ��
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
			/*�������ϣ���õ��ۡ�����ʱ�䡢��Ӧ�̡����Ϸ��ࡢ�Լ�����ָ���ֵ������֤��ʽ*/
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
    	/*�ж��û��Ƿ����빩Ӧ��*/
    	if(!"".equals(excelSupplier)){
    		excelSupplier = dealExcelData(excelSupplier);
    		Map supMap = new HashMap();
    		supMap.put("FName_l2", excelSupplier);
    		/*���ݹ�Ӧ���Ʋ�ѯ��Ŀid*/
    		supId = materialInfoUI.getIdByName("T_BD_Supplier", "FID", supMap);
    		supId = String.valueOf(supId == null ? "" : supId.toString().trim());
    	}
    	
    	String validate = String.valueOf((rowMap.get(material_Validate) == null || "2050-10-1".equals(rowMap.get(material_Validate).toString().trim())) ? "2050-11-11" : rowMap.get(material_Validate).toString().trim());
    	
    	validate = String.valueOf("".equals(validate) ? "2050-12-31" : validate);
    	
    	boolean vb = false;
    	/*�ж������Ƿ�����ϸ�ʽ��׼*/
    	if(!pDate.matcher(validate).matches()){
    		vb = true;
//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PleaseInputDateType2"));
    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PleaseInputDateType2"));
    		isErrorData = true;
//    		return false;
    	}
    	/*�ж���Ч�����Ƿ�Ϸ�*/
    	if(null != validate && !"".equals(validate) && !materialInfoUI.validate(validate) && !vb){
    		vb = true;
//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("ValidateError"));
    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("ValidateError"));
    		isErrorData = true;
//    		return false;
    	}
    	
    	
    	String excelQuoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? (new java.util.Date()).toLocaleString() : rowMap.get(material_QuoteTime).toString());
    	/*�Ա�����������֤*/
    	if(!"".equals(excelQuoteTime) && null != rowMap.get(material_QuoteTime)){
    		boolean b = false;
	    	/*�ж������Ƿ�����ϸ�ʽ��׼*/
	    	if(!pDate.matcher(excelQuoteTime).matches()){
	    		b = true;
	//    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum + 1) + getResourceString("PleaseInputDateType"));
	    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("PleaseInputDateType"));
	    		isErrorData = true;
	//    		return false;
	    	}
	    	
	    	/*�жϱ��������Ƿ�Ϸ�*/
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
	    	
	    	/*�ж��û��Ƿ������˱���ʱ��*/
	    	if(!b && !vb){
		    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    	try{
		    		long excelTimeL = format.parse(excelQuoteTime).getTime();
		    		long validateL = format.parse(validate).getTime();
		    		
		    		/*��Ч���ڲ���С�ڱ���ʱ��*/
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
    	
    	/*������������ѯ�������������ĸ�����*/
    	String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
    	//excelMaterialType = dealExcelData(excelMaterialType);
    	Map materialGroupMap = new HashMap();
		materialGroupMap.put("FNumber", excelMaterialType);
		String excelMaterialGroupId = materialInfoUI.getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);
		excelMaterialGroupId = String.valueOf(excelMaterialGroupId == null ? "" : excelMaterialGroupId.trim());

		Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
		/*�����������ƺͱ���ȷ��materialId*/
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
		
    	/*���� (��Excelȡ����)����ָ����*/
    	List fNamesExcel = new ArrayList();
    	
    	/*�ж��ǲ��ǻ����У�������ǻ����У��Ǿ�������ָ����*/
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
		//    	/*��һ�����ж������������Ƿ���ͬ*/
		//    	if((null == excelMaterialGroupId || "".equals(excelMaterialGroupId)) && !"".equals(excelMaterialType)){
		//    		bMaterialType = true;
		////    		MsgBox.showConfirm3(null,getResourceString("ErrorInfoCue"), + (rowNum-1) + getResourceString("MaterialTypeNotExist"));
		//    		errorAllInfos.add(getResourceString("Di") +  + (rowNum + 1) + getResourceString("MaterialTypeNotExist"));
		//    		isErrorData = true;
		////    		return false;
		//    	}else if("".equals(excelMaterialType)){
		//    		bMaterialType = true;
		//    	}
    	
    	/*�ж��û��Ƿ����������ƺͱ���*/
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
    	
    	/*��֤�����ظ���һ��������½Ǳ��е�����*/
    	for(int i = 0; i < materialTableInfo.getRowCount(); i ++){
    		IRow row = materialTableInfo.getRow(i);
    		double parentPrice = Double.parseDouble(row.getCell("price").getValue().toString());
    		String parentQuoteTime = row.getCell("quoteTime").getValue().toString();
    		if(parentQuoteTime.indexOf(" ") != -1){
    			parentQuoteTime = parentQuoteTime.substring(0,parentQuoteTime.indexOf(" "));
    		}
    		String parentSupplier = String.valueOf(row.getCell("supplier").getValue() == null ? "" : row.getCell("supplier").getValue().toString());
    		
    		/*�ڶ�������֤�����Ƿ��ظ�*/
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
    	
    	/*��֤�����ظ�������*/
    	/*ѭ���ж�(��)*/
		for(int j = 0; j < getMaterialInfos.size(); j ++){
			Map materialInfo = (Map) getMaterialInfos.get(j);
			double pPrice = Double.parseDouble(String.valueOf(materialInfo.get("FPrice") == null ? "0" : materialInfo.get("FPrice").toString().trim()));
			String pQuoteTime = String.valueOf(materialInfo.get("FQuoteTime") == null ? new java.util.Date().toLocaleString() : materialInfo.get("FQuoteTime").toString().trim());
			if(pQuoteTime.indexOf(" ") != -1){
				pQuoteTime = pQuoteTime.substring(0, pQuoteTime.indexOf(" "));
			}
			String fMaterialID = String.valueOf(materialInfo.get("FMaterialID") == null ? "" : materialInfo.get("FMaterialID").toString().trim());
			String fName = String.valueOf(materialInfo.get("FSupplierID") == null ? "" : materialInfo.get("FSupplierID").toString().trim());
			
			/*�ж��Ƿ����materialId*/
			if(null != materialId){
				/*�жϵ��ۡ�����ʱ�䡢��Ӧ���Ƿ���ͬ*/
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
		
		/*��֤�����ظ�������*/
		if(materialTableInfo.getRowCount() == 0){
			/*��Ӧ��Ϊ��*/
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
    	
    	/*����������֤Excel���ݱ��е�����ָ���Ƿ����*/
		
		/*��������Ϸ���������Щָ��*/
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
    	
    	/*��������Ŀ���ơ�һ��*/
		String projName = String.valueOf(rowMap.get(material_ProjectName) == null ? "" : rowMap.get(material_ProjectName).toString());
		projName = dealExcelData(projName);
		Map projMap = new HashMap();
		projMap.put("FName_l2", projName);
		/*������Ŀ���Ʋ�ѯ��Ŀid*/
		String projId = materialInfoUI.getIdByName("T_FDC_CurProject", "FID", projMap);
		projId = String.valueOf(projId == null ? "" : projId);
		if("".equals(projId) && !"".equals(projName)){
			errorAllInfos.add(getResourceString("Di") + (rowNum + 1)+ getResourceString("ProjectIsNotExist"));
		}
			
		/*��������ͬ���ơ�һ��*/
		String conName = String.valueOf(rowMap.get(material_ContractName) == null ? "" : rowMap.get(material_ContractName).toString());
		conName = dealExcelData(conName);
		Map conMap = new HashMap();
		conMap.put("FName", conName);
		/*���ݺ�ͬ���Ʋ�ѯ��Ŀid*/
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
	* ������<�ж�ģ���Ƿ���ȷ>
	* @author <luoxiaolong>
	* @param  <it,baseColumnName>
	* @return  <boolean>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
    public boolean judgeIsModel(Iterator it,String[] baseColumnName){
    	
    	/**����һ������������Excel�����ж���������*/
    	int rowCount = -1;
    	
    	while(it.hasNext()){
			/**����ж���*/
			HSSFRow row = (HSSFRow) it.next();
			
			/*���� �������� ��*/
			if(row.getRowNum() == 1){
				++ rowCount; 
				/*ѭ���ж��ڡ������������Ƿ�����ϲ��Ļ����У�����������ģ�壬��֮�򲻷���*/
				for(int i = 0; i < baseColumnName.length; i ++){
					int m = -1;
					for(int j = 0; j < row.getLastCellNum(); j ++){
						HSSFCell cell = row.getCell(j);
						if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING && baseColumnName[i].equals(cell.getStringCellValue())){
							m = 1;
							break ;
						}
					}
					/*�����ڻ�����ʱ*/
					if(m == -1){
						MsgBox.showConfirm3(null,getResourceString("ModelError2"),EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MustHave") + baseColumnName[i] + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Column"));
						return false;
					}
				}
				break ;
			}
    	}
    	
    	/*�ж������Excel����û������*/
    	if(rowCount == -1){
    		MsgBox.showConfirm3(null,EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail") + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"),EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail") + EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"));
    		return false;
    	}
    		
    	return true;
    }
    
    /**
	* ������<�����ݿ��ﵼ������>
	* @author <luoxiaolong>
	* @param  <allDataRowExcel,baseColumnName>
	* @return  <int>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
    public int addImportData(List allDataRowExcel, String[] baseColumnName){
    	
    	
    	/**����һ������ֵ����*/
    	int result = 0;

    	/*�����Excel����������������*/
    	List allDataRow = allDataRowExcel;
    	
    	/*���ݹ�Ӧ�̲�ѯ������Ӧ������Ӧ�����±���ʱ��*/
    	List getLastestQuoteTime = getLastestQuoteTime();
    	
    	/*ѭ���������������������ݿ���*/
    	for(int i = 0; i < allDataRow.size(); i ++){
    		if(null == allDataRow.get(i)){
    			continue ;
    		}
    		List row = (List) allDataRow.get(i);
    		
    		/*����ÿһ������*/
    		for(int j = 0; j < row.size(); j ++){
    			
    			Map rowMap = (Map) row.get(j);
    			
    			/**�����н�����Ϊһ������ʱ��*/
    			Timestamp terminalDate = Timestamp.valueOf("2050-12-31 00:00:00");
    			
    	    	/**������Ч��Ч 0��Ч 1��Ч*/
    			int isValidDate = 0;
    			if(terminalDate.getTime() > new java.util.Date().getTime()){
    				isValidDate = 1;
    			}
    			
    			/*����һ�����ϣ�����ÿһ�е�����*/
    			List params = new ArrayList();
    			
    			/**ȡ������ID*/
    			String bosId = materialInfoUI.getBOSID("9D390CBB");   			
    			params.add(bosId);
    			Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
    			/*�����������ƺͱ���ȷ��materialId*/
    			String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
    			//materialName = dealExcelData(materialName);
    			String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
    			//materialNumber = dealExcelData(materialNumber);
    			Map materialMap = new HashMap();
    			materialMap.put("FName_l2", materialName);
    			materialMap.put("FNumber", materialNumber);
    			String fMaterialID = materialInfoUI.getMaterialIDByFNameAndFNumber("T_BD_Material","FID",materialMap);
    			//�����ѯ�����ϱ���Ϊ�� �Ͳ��ܲ��뵽���ݿ�
    			if (fMaterialID == null || "".equals(fMaterialID)) {
					MsgBox.showWarning("�������ϱ��������û���ҵ���Ӧ������ID����������ȷ�����ϱ��������.");
					SysUtil.abort();
				}
    			params.add(fMaterialID);
    			
    			/*��������Ŀ���ơ�һ��*/
    			String projName = String.valueOf(rowMap.get(material_ProjectName) == null ? "" : rowMap.get(material_ProjectName).toString());
    			projName = dealExcelData(projName);
    			Map projMap = new HashMap();
    			projMap.put("FName_l2", projName);
    			/*������Ŀ���Ʋ�ѯ��Ŀid*/
    			String projId = materialInfoUI.getIdByName("T_FDC_CurProject", "FID", projMap);
    			if(projId != null){
    				params.add(projId);
    			}else{
    				params.add("");
    			}
    			
    			
    			/*��������ͬ���ơ�һ��*/
    			String conName = String.valueOf(rowMap.get(material_ContractName) == null ? "" : rowMap.get(material_ContractName).toString());
    			conName = dealExcelData(conName);
    			Map conMap = new HashMap();
    			conMap.put("FName", conName);
    			/*���ݺ�ͬ���Ʋ�ѯ��Ŀid*/
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
    			
    			/*��������Ӧ�̡�һ��*/
    			String supplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
    			supplier = dealExcelData(supplier);
    			Map supMap = new HashMap();
    			supMap.put("FName_l2", supplier);
    			/*���ݹ�Ӧ�����Ʋ�ѯ��Ӧ��id*/
    			String supId = materialInfoUI.getIdByName("T_BD_Supplier", "FID", supMap);
    			if(supId != null){
    				params.add(supId);
    			}else{
    				params.add("");
    			}
    			
    			/*���봴����ID*/
    			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
    			params.add(user.getId().toString());
    			
    			/*���봴��ʱ��*/
    			Date createTime = null;
				try {
					createTime = FDCDateHelper.getServerTimeStamp();
				} catch (BOSException e1) {
					createTime = new Date();
				}
    			params.add(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
    			
    			/*������Ч��*/
    			String validate = String.valueOf((rowMap.get(material_Validate) == null || "".equals(rowMap.get(material_Validate).toString().trim())) ? "2050-12-31" : rowMap.get(material_Validate).toString().trim());
    			params.add(validate);
    			
    			/*�����Ƿ������� 1���� 0��������*/
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
    			
    			/*�����Ƿ����� 1���� 0����*/
    			params.add(new Integer(1));
    			String fName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
    			params.add(fName);
    			/*����״̬*/
    			params.add("1SAVED");
    			/*������Ч��Ч*/
    			params.add(String.valueOf(isValidDate == 1 ? "VALID" : "INVALID"));
    			
    			/*������Ƶ�Ԫ*/
    			String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
    			params.add(cuId);
    			
    			/*������֯��Ԫ*/
    			String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
    			params.add(orgUnitId);
    			
    			
    			/*���� (��Excelȡ����)����ָ����*/
    	    	List fNamesExcel = new ArrayList();
    	    	
    	    	/*�ж��ǲ��ǻ����У�������ǻ����У��Ǿ�������ָ����*/
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
    				/*���÷�������Daoִ��SQL*/
    				MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "importMaterialInfoSql");
    				
    				/*������������ѯ�������������ĸ�����*/
    		    	String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
    		    	Map materialGroupMap = new HashMap();
    				materialGroupMap.put("FName_l2", excelMaterialType);
    				String materialGroupId = materialInfoUI.getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);
    				
    				List materialIndexs = getMaterialIndexIdByMaterialId(fMaterialID);
    				
    				/*ѭ������ָ��ֵ*/
    				for(int k = 0; k < materialIndexs.size(); k ++){
    					Map allMaterialIndexMap = (Map) materialIndexs.get(k);
    					String allMaterialIndex = allMaterialIndexMap.get("FID").toString();
    					List newParams = new ArrayList();
    					newParams.add(materialInfoUI.getBOSID("6E5BD60C"));
    					newParams.add(bosId);
    					
    					/*materialIndexId*/
    					String materialIndexId = "";
    					
    					/*ָ��ֵ*/
    					String indexValue = "";
    					
    					for(int m = 0; m < fNamesExcel.size(); m ++){
        					String fNameExcel = fNamesExcel.get(m).toString().trim();
        					Map filterMap = new HashMap();
        					filterMap.put("FName_l2", fNameExcel);
        					filterMap.put("FMaterialGroupID", materialGroupId);
        				
        					/*����materialGroupId��fName���ƣ���ѯmaterialIndexId*/
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
    					
    					/*����ָ��ֵ*/
    					newParams.add(indexValue);
    					
//    					/*ѭ��ɾ��ָ��*/
//    					int n = MaterialInfoFactory.getRemoteInstance().deleteMaterialIndexValue(fMaterialID, materialIndexId);
    					
    					/*ѭ������ָ��*/
    					MaterialInfoFactory.getRemoteInstance().addMaterialData(newParams, "importMaterialIndexInfoSql");
    				}
    				
    				
    				
    				++ result;
    			} catch (Exception e) {
    				handUIException(e);
    				logger.error(e.getMessage());
    				/*��ʱ����ʧ��,������handUIException(e)�����쳣,�ں��������ʾ������ʧ�ܡ�*/
    				--result; 
    			}
    		}
    	}   
    	return result;
    }
    
    /**
	* ������<��Դ�ļ�>
	* @author <luoxiaolong>
	* @param  <tableName,fieldName,filters>
	* @return  <String>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
    public String getResourceString(String resourceKey){ 
    	return EASResource.getString(MATERIALIMPORT_RESOURCEPATH, resourceKey);
    }
    
    /**
	* ������<��ѯ���±���ʱ��>
	* @author <luoxiaolong>
	* @param  <tableName,fieldName,filters>
	* @return  <String>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
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
	* ������<��������id���������ָ��id>
	* @author <luoxiaolong>
	* @param  <materialGruopId>
	* @return  <null>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	private List getMaterialIndexIdByMaterialId(String materialId){
		Map filterMap = new HashMap();
		filterMap.put("FID", materialId);
		String materialGroupId = materialInfoUI.getMaterialGroupIdByMaterialId(filterMap,"T_BD_Material","FMaterialGroupID");
		return getMaterialIndexId(materialGroupId);
	}
	
	/**
	* ������<�������ָ������>
	* @author <luoxiaolong>
	* @param  <materialGruopId>
	* @return  <null>
	* ����ʱ��  <2010/11/16> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	private List getMaterialIndexId(String materialGruopId){
		
		/**����һ������ֵ����*/
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
