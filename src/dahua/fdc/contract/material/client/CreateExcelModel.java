/*
 * @(#)CreateExcelModel.java
 * 
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.fdc.material.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import com.kingdee.eas.util.client.EASResource;

/**
 * ����������Excelģ��<p>
 * @author luoxiaolong
 * @version EAS 6.0
 * @see MaterialInfoUI MaterialInfoControllerBean
 */
public class CreateExcelModel {
		
	/**������Դ�ļ�λ��*/
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
	
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
	
	/**
	* ������<���캯��>
	* @author <luoxiaolong>
	* @param  <>
	* @return  <>
	* ����ʱ��  <2010/11/18> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	public CreateExcelModel(){
		material_Type = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialType");
		material_Number = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialNumber");
		material_Name = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialName");
		material_Model = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Model");
		material_Unit = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Unit");
		material_Price = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Price");
		material_QuoteTime = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "QuoteTime");
		material_Supplier = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Supplier");
		material_ProjectName = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ProjectName");
		material_ContractName = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ContractName");
		material_Validate = EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Validate");
	}
	
	/**
	* ������<����Excelģ��>
	* @author <luoxiaolong>
	* @param  <e>
	* @return  <>
	* ����ʱ��  <2010/11/18> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	*
	* @see  <��ص���>
	*/
	public void createExcelMode(List fNames, HSSFWorkbook wb, HSSFSheet sheet){
		
		/*����������,����������*/
//		wb.createSheet(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialQuoteInfoModel"));
		wb.createSheet();
		/*����sheet����*/
        wb.setSheetName(0,EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialQuoteInfo"));
        
        // ������Ԫ����ʽ
		HSSFCellStyle baseCellStyle = wb.createCellStyle();

		// ָ����Ԫ����ж���
		baseCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// ָ����Ԫ��ֱ���ж���
		baseCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// ָ������Ԫ��������ʾ����ʱ�Զ�����
		baseCellStyle.setWrapText(true);
		
		// ���õ�Ԫ�񱳾�ɫ
		baseCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		baseCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		
		// ������Ԫ����ʽ
		HSSFCellStyle otherCellStyle = wb.createCellStyle();

		// ָ����Ԫ����ж���
		otherCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// ָ����Ԫ��ֱ���ж���
		otherCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// ָ������Ԫ��������ʾ����ʱ�Զ�����
		otherCellStyle.setWrapText(true);
		
		// ���õ�Ԫ�񱳾�ɫ
		otherCellStyle.setFillForegroundColor(HSSFColor.RED.index);
		otherCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		
		
		// ������Ԫ����ʽ
		HSSFCellStyle firstRowStyle = wb.createCellStyle();
		
		// ָ����Ԫ����ж���
		firstRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// ָ����Ԫ��ֱ���ж���
		firstRowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// ָ������Ԫ��������ʾ����ʱ�Զ�����
		firstRowStyle.setWrapText(true);
		
		
		// ���õ�Ԫ������
		HSSFFont font = wb.createFont();
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("����");
		font.setFontHeight((short) 250);
		baseCellStyle.setFont(font);
		otherCellStyle.setFont(font);
		firstRowStyle.setFont(font);
		
		
        /*����ģ���һ��*/
        HSSFRow firstRow = sheet.createRow(0); 
        firstRow.setHeight((short)800);
        HSSFCell firstRowCell0 = firstRow.createCell(0);
        firstRowCell0.setCellValue(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "RemarkYellowIsNotNull"));
        if(fNames.size() > 0){
        	HSSFCell firstRowCell5 = firstRow.createCell(5);
            firstRow.getSheet().addMergedRegion(new Region(0, (short) 5, 0, (short) (fNames.size() + 4)));
            firstRowCell5.setCellValue(new HSSFRichTextString(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialIndex")));
            firstRowCell5.setCellStyle(firstRowStyle);
        }
        
        /*�����ڶ���*/
        HSSFRow secondRow = sheet.createRow(1);
        secondRow.setHeight((short)600);
        
        /**���û����е�һ��*/
        HSSFCell secondRowCell0 = secondRow.createCell(0);
        secondRowCell0.setCellValue(new HSSFRichTextString(material_Type));
        secondRowCell0.setCellStyle(baseCellStyle);
        
        /**���û����еڶ���*/
        HSSFCell secondRowCell1 = secondRow.createCell(1);
        secondRowCell1.setCellValue(new HSSFRichTextString(material_Number));
        secondRowCell1.setCellStyle(baseCellStyle);
        
        /**���û����е�����*/
        HSSFCell secondRowCell2 = secondRow.createCell(2);
        secondRowCell2.setCellValue(new HSSFRichTextString(material_Name));
        secondRowCell2.setCellStyle(baseCellStyle);
        
        /**���û����е�����*/
        HSSFCell secondRowCell3 = secondRow.createCell(3);
        secondRowCell3.setCellValue(new HSSFRichTextString(material_Model));
        secondRowCell3.setCellStyle(baseCellStyle);
        
        /**���û����е�����*/
        HSSFCell secondRowCell4 = secondRow.createCell(4);
        secondRowCell4.setCellValue(new HSSFRichTextString(material_Unit));
        secondRowCell4.setCellStyle(baseCellStyle);
        
        /*ѭ���������ָ����*/
        for(int i = 0; i < fNames.size(); i ++){
        	HSSFCell secondRowCellValue = secondRow.createCell(i + 5);
        	Map map = (Map) fNames.get(i);
        	secondRowCellValue.setCellValue(new HSSFRichTextString(map.get("FName_l2").toString()));
        	secondRowCellValue.setCellStyle(otherCellStyle);
        }
        
        /**���û����е�����*/
        HSSFCell secondRowCell5 = secondRow.createCell(5 + fNames.size());
        secondRowCell5.setCellValue(new HSSFRichTextString(material_Price));
        secondRowCell5.setCellStyle(baseCellStyle);
        
        /**���û����е�����*/
        HSSFCell secondRowCell6 = secondRow.createCell(6 + fNames.size());
        secondRowCell6.setCellValue(new HSSFRichTextString(material_QuoteTime));
        secondRowCell6.setCellStyle(baseCellStyle);
        
        /**���û����е�����*/
        HSSFCell secondRowCell7 = secondRow.createCell(7 + fNames.size());
        secondRowCell7.setCellValue(new HSSFRichTextString(material_Supplier));
        secondRowCell7.setCellStyle(baseCellStyle);
        
        /**���û����еڰ���*/
        HSSFCell secondRowCell8 = secondRow.createCell(8 + fNames.size());
        secondRowCell8.setCellValue(new HSSFRichTextString(material_ProjectName));
        secondRowCell8.setCellStyle(otherCellStyle);
        
        /**���û����еھ���*/
        HSSFCell secondRowCell9 = secondRow.createCell(9 + fNames.size());
        secondRowCell9.setCellValue(new HSSFRichTextString(material_ContractName));
        secondRowCell9.setCellStyle(otherCellStyle);
        
        /**���û����е�ʮ��*/
        HSSFCell secondRowCell10 = secondRow.createCell(10 + fNames.size());
        secondRowCell10.setCellValue(new HSSFRichTextString(material_Validate));
        secondRowCell10.setCellStyle(otherCellStyle);
        
        /*�����еĿ��*/
        int columnNumber = 10 + fNames.size();
        for(int i = 0; i < columnNumber + 1; i ++){
        	sheet.setColumnWidth(i, 3000);
        }
        
	}
	
	/**
	* ������<���Excelģ��>
	* @author <luoxiaolong>
	* @param  <String>
	* @return  <>
	* ����ʱ��  <2010/11/18> <p>
	* 
	* �޸��ˣ�<�޸���> <p>
	* �޸�ʱ�䣺<yyyy/mm/dd> <p>
	* �޸�������<�޸�����> <p>
	 * @throws IOException 
	*
	* @see  <��ص���>
	*/
	public void outputExcel(HSSFWorkbook wb, String filePath) throws IOException {		
        if(filePath.indexOf(".xls") == -1){
        	filePath = filePath + ".xls";
        }
        FileOutputStream fos = null;
		fos = new FileOutputStream(new File(filePath));
		wb.write(fos);
		fos.close();
	}
}
