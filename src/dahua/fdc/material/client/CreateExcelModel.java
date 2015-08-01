/*
 * @(#)CreateExcelModel.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述：创建Excel模板<p>
 * @author luoxiaolong
 * @version EAS 6.0
 * @see MaterialInfoUI MaterialInfoControllerBean
 */
public class CreateExcelModel {
		
	/**声明资源文件位置*/
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
	
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
	
	/**
	* 描述：<构造函数>
	* @author <luoxiaolong>
	* @param  <>
	* @return  <>
	* 创建时间  <2010/11/18> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
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
	* 描述：<创建Excel模板>
	* @author <luoxiaolong>
	* @param  <e>
	* @return  <>
	* 创建时间  <2010/11/18> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
	public void createExcelMode(List fNames, HSSFWorkbook wb, HSSFSheet sheet){
		
		/*创建工作薄,并设置名称*/
//		wb.createSheet(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialQuoteInfoModel"));
		wb.createSheet();
		/*设置sheet名称*/
        wb.setSheetName(0,EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialQuoteInfo"));
        
        // 创建单元格样式
		HSSFCellStyle baseCellStyle = wb.createCellStyle();

		// 指定单元格居中对齐
		baseCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 指定单元格垂直居中对齐
		baseCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// 指定当单元格内容显示不下时自动换行
		baseCellStyle.setWrapText(true);
		
		// 设置单元格背景色
		baseCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		baseCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		
		// 创建单元格样式
		HSSFCellStyle otherCellStyle = wb.createCellStyle();

		// 指定单元格居中对齐
		otherCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 指定单元格垂直居中对齐
		otherCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// 指定当单元格内容显示不下时自动换行
		otherCellStyle.setWrapText(true);
		
		// 设置单元格背景色
		otherCellStyle.setFillForegroundColor(HSSFColor.RED.index);
		otherCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		
		
		// 创建单元格样式
		HSSFCellStyle firstRowStyle = wb.createCellStyle();
		
		// 指定单元格居中对齐
		firstRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 指定单元格垂直居中对齐
		firstRowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		// 指定当单元格内容显示不下时自动换行
		firstRowStyle.setWrapText(true);
		
		
		// 设置单元格字体
		HSSFFont font = wb.createFont();
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 250);
		baseCellStyle.setFont(font);
		otherCellStyle.setFont(font);
		firstRowStyle.setFont(font);
		
		
        /*创建模板第一行*/
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
        
        /*创建第二行*/
        HSSFRow secondRow = sheet.createRow(1);
        secondRow.setHeight((short)600);
        
        /**设置基础列第一列*/
        HSSFCell secondRowCell0 = secondRow.createCell(0);
        secondRowCell0.setCellValue(new HSSFRichTextString(material_Type));
        secondRowCell0.setCellStyle(baseCellStyle);
        
        /**设置基础列第二列*/
        HSSFCell secondRowCell1 = secondRow.createCell(1);
        secondRowCell1.setCellValue(new HSSFRichTextString(material_Number));
        secondRowCell1.setCellStyle(baseCellStyle);
        
        /**设置基础列第三列*/
        HSSFCell secondRowCell2 = secondRow.createCell(2);
        secondRowCell2.setCellValue(new HSSFRichTextString(material_Name));
        secondRowCell2.setCellStyle(baseCellStyle);
        
        /**设置基础列第四列*/
        HSSFCell secondRowCell3 = secondRow.createCell(3);
        secondRowCell3.setCellValue(new HSSFRichTextString(material_Model));
        secondRowCell3.setCellStyle(baseCellStyle);
        
        /**设置基础列第四列*/
        HSSFCell secondRowCell4 = secondRow.createCell(4);
        secondRowCell4.setCellValue(new HSSFRichTextString(material_Unit));
        secondRowCell4.setCellStyle(baseCellStyle);
        
        /*循环添加特性指标列*/
        for(int i = 0; i < fNames.size(); i ++){
        	HSSFCell secondRowCellValue = secondRow.createCell(i + 5);
        	Map map = (Map) fNames.get(i);
        	secondRowCellValue.setCellValue(new HSSFRichTextString(map.get("FName_l2").toString()));
        	secondRowCellValue.setCellStyle(otherCellStyle);
        }
        
        /**设置基础列第五列*/
        HSSFCell secondRowCell5 = secondRow.createCell(5 + fNames.size());
        secondRowCell5.setCellValue(new HSSFRichTextString(material_Price));
        secondRowCell5.setCellStyle(baseCellStyle);
        
        /**设置基础列第六列*/
        HSSFCell secondRowCell6 = secondRow.createCell(6 + fNames.size());
        secondRowCell6.setCellValue(new HSSFRichTextString(material_QuoteTime));
        secondRowCell6.setCellStyle(baseCellStyle);
        
        /**设置基础列第七列*/
        HSSFCell secondRowCell7 = secondRow.createCell(7 + fNames.size());
        secondRowCell7.setCellValue(new HSSFRichTextString(material_Supplier));
        secondRowCell7.setCellStyle(baseCellStyle);
        
        /**设置基础列第八列*/
        HSSFCell secondRowCell8 = secondRow.createCell(8 + fNames.size());
        secondRowCell8.setCellValue(new HSSFRichTextString(material_ProjectName));
        secondRowCell8.setCellStyle(otherCellStyle);
        
        /**设置基础列第九列*/
        HSSFCell secondRowCell9 = secondRow.createCell(9 + fNames.size());
        secondRowCell9.setCellValue(new HSSFRichTextString(material_ContractName));
        secondRowCell9.setCellStyle(otherCellStyle);
        
        /**设置基础列第十列*/
        HSSFCell secondRowCell10 = secondRow.createCell(10 + fNames.size());
        secondRowCell10.setCellValue(new HSSFRichTextString(material_Validate));
        secondRowCell10.setCellStyle(otherCellStyle);
        
        /*设置列的宽度*/
        int columnNumber = 10 + fNames.size();
        for(int i = 0; i < columnNumber + 1; i ++){
        	sheet.setColumnWidth(i, 3000);
        }
        
	}
	
	/**
	* 描述：<输出Excel模板>
	* @author <luoxiaolong>
	* @param  <String>
	* @return  <>
	* 创建时间  <2010/11/18> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	 * @throws IOException 
	*
	* @see  <相关的类>
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
