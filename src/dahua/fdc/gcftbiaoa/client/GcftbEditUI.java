/**
 * output package name
 */
package com.kingdee.eas.fdc.gcftbiaoa.client;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.excel.impl.facade.wizzard.WizzardIO;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.aimcost.costkf.CqgsBaseCollection;
import com.kingdee.eas.fdc.aimcost.costkf.CqgsBaseFactory;
import com.kingdee.eas.fdc.aimcost.costkf.ICqgsBase;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.IProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.gcftbiaoa.AllocationIndex;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryDetailInfo;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbEntryInfo;
import com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo;
import com.kingdee.eas.fdc.gcftbiaoa.TreeNodeInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.multiDetail.DetailPanel;
import com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class GcftbEditUI extends AbstractGcftbEditUI {
	private static final Logger logger = CoreUIObject.getLogger(GcftbEditUI.class);
	private final static String CANTUNAUDIT = "cantUnAudit";
	private final static String CANTAUDIT = "cantAudit";
	private final static String CANTUNAUDITEDITSTATE = "cantUnAuditEditState";
	private final static String CANTAUDITEDITSTATE = "cantAuditEditState";
	
	private KDWorkButton importExcelButton = new KDWorkButton("导入Excel");
	private KDWorkButton outExcelButton = new KDWorkButton("导出Excel模板");

	/**
	 * output class constructor
	 */
	public GcftbEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		btnModify.setEnabled(true);
		contengineeringProject.setEnabled(false);
		contstatus.setEnabled(false);
		chkisLast.setEnabled(false);
		contbbh.setEnabled(false);
		contgsmc.setEnabled(false);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("FullOrgUnit.id", SysContext.getSysContext()
						.getCurrentOrgUnit().getId()));
		// 工程项目过滤
		KDBizPromptBox gcxm = (KDBizPromptBox) this.kdtEntrys.getColumn(
				"engineeringProject").getEditor().getComponent();
		gcxm.setEntityViewInfo(view);
		// 收益项目过滤
		KDBizPromptBox syxm = (KDBizPromptBox) this.kdtDetail.getColumn(
				"benefitProject").getEditor().getComponent();
		syxm.setEntityViewInfo(view);
		// 显示名称
		ObjectValueRender kdtEntrys_engineeringProject_OVR = new ObjectValueRender();
		kdtEntrys_engineeringProject_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEntrys.getColumn("engineeringProject").setRenderer(kdtEntrys_engineeringProject_OVR);
		// 设置颜色
		kdtEntrys.getColumn("engineeringProject").setRequired(true);
		kdtEntrys.getColumn("facilityName").setRequired(true);
		kdtEntrys.getColumn("proptreyRight").setRequired(true);
		kdtEntrys.getColumn("constructionArea").setRequired(true);
		kdtEntrys.getColumn("startTime").setRequired(true);
		kdtEntrys.getColumn("completionTime").setRequired(true);
		kdtEntrys.getColumn("totalCost").setRequired(true);
		kdtEntrys.getColumn("totalAmount").setRequired(true);
		kdtEntrys.getColumn("share").setRequired(true);
		kdtEntrys.getColumn("sharePrice").setRequired(true);
		kdtDetail.getColumn("benefitProject").setRequired(true);
		kdtDetail.getColumn("allocationBase").setRequired(true);
		kdtDetail.getColumn("shareAmount").setRequired(true);
		//分录不可编辑
		kdtEntrys.getColumn("share").getStyleAttributes().setLocked(true);
		kdtEntrys.getColumn("sharePrice").getStyleAttributes().setLocked(true);
		kdtDetail.getColumn("allocationBase").getStyleAttributes().setLocked(true);
		kdtDetail.getColumn("shareAmount").getStyleAttributes().setLocked(true);
		
		kdtEntrys.getColumn("share").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtEntrys.getColumn("sharePrice").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtDetail.getColumn("allocationBase").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		kdtDetail.getColumn("shareAmount").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		
		this.kdtDetail.getHeadRow(0).getCell("benefitProject").setValue("受益项目");
		//连续提交设置
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		setDetailPanel(kdtEntrys_detailPanel, kDContainer1);
		setDetailPanel(kdtDetail_detailPanel, kDContainer2);
		//重构枚举
		KDComboBox kdtEntrys_allocationIndex_ComboBox = new KDComboBox();
        kdtEntrys_allocationIndex_ComboBox.setName("kdtEntrys_allocationIndex_ComboBox");
        kdtEntrys_allocationIndex_ComboBox.setVisible(true);
        kdtEntrys_allocationIndex_ComboBox.addItems(AllocationIndex.getEnumList().toArray());
        KDTDefaultCellEditor kdtEntrys_allocationIndex_CellEditor = new KDTDefaultCellEditor(kdtEntrys_allocationIndex_ComboBox);
        this.kdtEntrys.getColumn("allocationIndex").setEditor(kdtEntrys_allocationIndex_CellEditor);
        
        setEntryButtonAction();
        setEntryDetailButtonAction();
        //合计
        setTableToSumField(kdtEntrys,new String[]{"totalCost","totalAmount","costHasOccurred","share"});
        setTableToSumField(kdtDetail,new String[]{"allocationBase","shareAmount"});   
        
        //导入导出的图标
        importExcelButton.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		outExcelButton.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		
		kDContainer1.addButton(importExcelButton);
		kDContainer1.addButton(outExcelButton);
		//按钮监听
		importExcelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					importExcelButton_actionPerformed(e);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
		});
		outExcelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					outExcelButton_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	//设置按钮
	private void setDetailPanel(DetailPanel detail,KDContainer kDContainer){
		KDWorkButton addNewLineButton = detail.getAddNewLineButton();
		KDWorkButton insertLineButton = detail.getInsertLineButton();
		KDWorkButton removeLinesButton = detail.getRemoveLinesButton();
		
		addNewLineButton.setText("新增分录");
		insertLineButton.setText("插入分录");
		removeLinesButton.setText("删除分录");
		kDContainer.addButton(addNewLineButton);
		kDContainer.addButton(insertLineButton);
		kDContainer.addButton(removeLinesButton);
		kDContainer.getContentPane().add(detail.getEntryTable(),BorderLayout.CENTER);
	}
	//选取Excel
	public static String showExcelSelectDlg(CoreUIObject ui){
    	KDFileChooser chsFile = new KDFileChooser();
    	String XLS = "xls";
    	String Key_File = "Key_File";
    	//筛选xls格式的文件
    	SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, (new StringBuilder("MS Excel")).append(LanguageManager.getLangMessage(Key_File, WizzardIO.class.getName(), "\u64CD\u4F5C\u5931\u8D25")).toString());
    	chsFile.addChoosableFileFilter(Filter_Excel);
    	int ret = chsFile.showOpenDialog(ui);
    	if(ret != 0)
    		SysUtil.abort();

    	File file = chsFile.getSelectedFile();
    	String fileName = file.getAbsolutePath();
    	return fileName;
    }

	//导入表格
	private void importExcelButton_actionPerformed(ActionEvent e) throws FileNotFoundException, IOException, EASBizException, BOSException {
		final String path = showExcelSelectDlg(this);//打开窗口，获得路径
		if (path == null) {
			return;
		}
		KDSBook kdsbook = POIXlsReader.parse2(path);//Excel页面集
		if (kdsbook == null)
			return ;
		if(KDSBookToBook.traslate(kdsbook).getSheetCount()>1){
			MsgBox.showWarning(this,"读EXCEL出错,EXCEl Sheet数量不匹配！");
			return;
		}
		final Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);//第一页集合
		checkExelData(kdtEntrys, excelSheet);
		
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }
        else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
			public void afterExec(Object arg0) throws Exception {
				Boolean bol=(Boolean)arg0;
				if(bol){
					MsgBox.showInfo("导入成功！");
				}
			}
			public Object exec() throws Exception {
				boolean bol=importExcelToTable(excelSheet,kdtEntrys);
				return bol;
			}
    	}
	    );
	    dialog.show();
	}
	
	private boolean checkExelData(KDTable table,Sheet excelSheet) throws EASBizException, BOSException{
    	Map e_colNameMap = new HashMap();
		int e_maxRow = excelSheet.getMaxRowIndex();
		int e_maxColumn = excelSheet.getMaxColIndex();
		for (int col = 0; col <= e_maxColumn; col++) {//获取Excel表头
			String excelColName = excelSheet.getCell(0, col, true).getText();
			e_colNameMap.put(excelColName, new Integer(col));
		}
		table.removeColumn(table.getColumnIndex("ftje"));
		table.removeColumn(table.getColumnIndex("ftjs"));
		table.removeColumn(table.getColumnIndex("syxm"));
		IRow headRow = table.getHeadRow(0);
		IColumn addColumn = table.addColumn();
        addColumn.setKey("syxm");
        headRow.getCell(addColumn.getKey()).setValue("受益项目");
        addColumn = table.addColumn();
        addColumn.setKey("ftjs");
        headRow.getCell(addColumn.getKey()).setValue("分摊基数");
        addColumn = table.addColumn();
        addColumn.setKey("ftje");
        headRow.getCell(addColumn.getKey()).setValue("分摊金额");
		
		for (int col = 0; col< table.getColumnCount(); col++) {//获取table表头
			if (table.getColumn(col).getStyleAttributes().isHided()) {
				continue;//表头的就直接跳出
			}
			String colName = (String) table.getHeadRow(0).getCell(col).getValue();
			Integer colInt = (Integer) e_colNameMap.get(colName);
			if (colInt == null) {
				MsgBox.showWarning(this,"表头结构不一致！表格上的关键列:" + colName + "在EXCEL中没有出现！");
				return false;
			}
		}
		StringBuffer erroMsg = new StringBuffer();
		
		for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
			for (int col = 0; col < table.getColumnCount(); col++) { 
				if (table.getColumn(col).getStyleAttributes().isHided()) {
    				continue;
    			}
				String colName = (String) table.getHeadRow(0).getCell(col).getValue();
				Integer colInt = (Integer) e_colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
				com.kingdee.bos.ctrl.common.variant.Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (com.kingdee.bos.ctrl.common.variant.Variant.isNull(cellRawVal)) {
					continue;
				}
				String colValue = cellRawVal.toString();
				if(("工程项目").equals(colName)&&!CurProjectFactory.getRemoteInstance().exists("where name='"+colValue+"'"))
					erroMsg.append("第["+rowIndex+"]行 工程项目 ["+colValue+"] 在系统中不存在！\n");
				if(("待分摊是否整体工程项目").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"待分摊是否整体工程项目", colValue, "bool"));
				if(("设施名称").equals(colName)&&!ProductTypeFactory.getRemoteInstance().exists("where name='"+colValue+"'"))
					erroMsg.append("第["+rowIndex+"]行 设施名称 ["+colValue+"] 在系统中不存在！\n");
				if(("产权情况").equals(colName)&&!CqgsBaseFactory.getRemoteInstance().exists("where name='"+colValue+"'"))
					erroMsg.append("第["+rowIndex+"]行 产权情况 ["+colValue+"] 在系统中不存在！\n");
				if(("建筑面积").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"建筑面积", colValue, "bigdecimal"));  
				if(("开工时间").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"开工时间", excelSheet.getCell(rowIndex, colInt.intValue(), true).getText(), "date"));
				if(("实际开工时间").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"实际开工时间", excelSheet.getCell(rowIndex, colInt.intValue(), true).getText(), "date"));
				if(("竣工时间").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"竣工时间", excelSheet.getCell(rowIndex, colInt.intValue(), true).getText(), "date"));
				if(("实际竣工时间").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"实际竣工时间", excelSheet.getCell(rowIndex, colInt.intValue(), true).getText(), "date"));
				if(("成本总额").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"成本总额", colValue, "bigdecimal"));
				if(("已发生成本").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"已发生成本", colValue, "bigdecimal"));
				if(("分摊指标").equals(colName)){
					boolean flse = true;
					Iterator iterator = AllocationIndex.getEnumList().iterator();
					while(iterator.hasNext()){				//清除空格
						if(iterator.next().toString().equals(colValue.trim()))
							flse = false;
					}
					if(flse)
						erroMsg.append("第["+rowIndex+"]行 分摊指标枚举类型 ["+colValue+"] 不存在！\n");
				}
				if(("应分摊总量").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"应分摊总量", colValue, "bigdecimal"));
				if(("待分摊总量").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"待分摊总量", colValue, "bigdecimal"));
				if(("分摊单价").equals(colName))
					erroMsg.append(verifyFieldFormat(rowIndex,"分摊单价", colValue, "bigdecimal"));
				
				if(("受益项目").equals(colName)&&!CurProjectFactory.getRemoteInstance().exists("where name='"+colValue+"'")){
					erroMsg.append("第["+rowIndex+"]行 受益项目 ["+colValue+"] 在系统中不存在！\n");
				}
				if("分摊基数".equals(colName)){
					erroMsg.append(verifyFieldFormat(rowIndex,"分摊基数", colValue, "bigdecimal"));  
				}
				if(("分摊金额").equals(colName)){
					erroMsg.append(verifyFieldFormat(rowIndex,"分摊金额", colValue, "bigdecimal"));  
				}
			}
		}
		if(UIRuleUtil.isNotNull(erroMsg.toString())){
			table.removeColumn(table.getColumnIndex("ftje"));
			table.removeColumn(table.getColumnIndex("ftjs"));
			table.removeColumn(table.getColumnIndex("syxm"));
			FDCMsgBox.showConfirm3a("数据合法性校验失败，请查看详细信息更正！", erroMsg.toString());
			SysUtil.abort();
		}
		return true;
	}
	
	//读取Excel
	private boolean importExcelToTable(Sheet excelSheet, KDTable table) throws Exception {
    	Map e_colNameMap = new HashMap();
		int e_maxRow = excelSheet.getMaxRowIndex();
		int e_maxColumn = excelSheet.getMaxColIndex();
		for (int col = 0; col <= e_maxColumn; col++) {//获取Excel表头
			String excelColName = excelSheet.getCell(0, col, true).getText();
			e_colNameMap.put(excelColName, new Integer(col));
		}
		for (int col = 0; col< table.getColumnCount(); col++) {//获取table表头
			if (table.getColumn(col).getStyleAttributes().isHided()) {
				continue;
			}
			String colName = (String) table.getHeadRow(0).getCell(col).getValue();
			Integer colInt = (Integer) e_colNameMap.get(colName);
			if (colInt == null) {
				MsgBox.showWarning(this,"表头结构不一致！表格上的关键列:" + colName + "在EXCEL中没有出现！");
				return false;
			}
		}
		table.removeRows();
		
		storeFields();
		//清除原来的数据
		editData.getEntrys().clear();
		ICurProject ICurProject = CurProjectFactory.getRemoteInstance();
		IProductType IProductType = ProductTypeFactory.getRemoteInstance();
		ICqgsBase IcqgsBase = CqgsBaseFactory.getRemoteInstance();
		
		for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
			GcftbEntryInfo entry = new GcftbEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			GcftbEntryDetailInfo detailInfo = new GcftbEntryDetailInfo();
			detailInfo.setId(BOSUuid.create(detailInfo.getBOSType()));
			for (int col = 0; col < table.getColumnCount(); col++) {
				if (table.getColumn(col).getStyleAttributes().isHided()) {
    				continue;
    			}
				String colName = (String) table.getHeadRow(0).getCell(col).getValue();
				Integer colInt = (Integer) e_colNameMap.get(colName);

				if (colInt == null) {
					continue;
				}
				
				com.kingdee.bos.ctrl.common.variant.Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
				if (com.kingdee.bos.ctrl.common.variant.Variant.isNull(cellRawVal)) {
					continue;
				}
				String colValue = cellRawVal.toString();
				//一一塞值
				if(("工程项目").equals(colName)){
					CurProjectCollection curProjectCollection = ICurProject.getCurProjectCollection("select id,number,name where name='"+colValue+"'");
					entry.setEngineeringProject(curProjectCollection.size()>0?curProjectCollection.get(0):null);
				}
				if(("待分摊是否整体工程项目").equals(colName))
					if("是".equals(colValue))
					entry.setAllshare(Boolean.TRUE);
					else if ("否".equals(colValue)){
						entry.setAllshare(Boolean.FALSE);
						}
					else if ("".equals(colValue)){
						entry.setAllshare(Boolean.FALSE);
						}
				if(("设施名称").equals(colName)){
					ProductTypeCollection productTypeCollection = IProductType.getProductTypeCollection("select id,number,name where name='"+colValue+"'");
					entry.setFacilityName(productTypeCollection.size()>0?productTypeCollection.get(0):null);
				}
				if(("产权情况").equals(colName)){
					CqgsBaseCollection cqgsBaseCollection = IcqgsBase.getCqgsBaseCollection("select id,number,name where name='"+colValue+"'");
					entry.setProptreyRight(cqgsBaseCollection.size()>0?cqgsBaseCollection.get(0):null);
				}
				if(("建筑面积").equals(colName))
					entry.setConstructionArea(UIRuleUtil.getBigDecimal(colValue));
				if(("开工时间").equals(colName))
					entry.setStartTime(UIRuleUtil.getDateValue(excelSheet.getCell(rowIndex, colInt.intValue(), true).getText()));
				if(("实际开工时间").equals(colName))
					entry.setActualStartTine(UIRuleUtil.getDateValue(excelSheet.getCell(rowIndex, colInt.intValue(), true).getText()));
				if(("竣工时间").equals(colName))
					entry.setCompletionTime(UIRuleUtil.getDateValue(excelSheet.getCell(rowIndex, colInt.intValue(), true).getText()));
				if(("实际竣工时间").equals(colName))
					entry.setActualCompeltionTime(UIRuleUtil.getDateValue(excelSheet.getCell(rowIndex, colInt.intValue(), true).getText()));
				if(("成本总额").equals(colName))
					entry.setTotalCost(UIRuleUtil.getBigDecimal(colValue));
				if(("已发生成本").equals(colName))
					entry.setCostHasOccurred(UIRuleUtil.getBigDecimal(colValue));
				if(("分摊指标").equals(colName)){
					if("建筑面积".equals(colValue))
						entry.setAllocationIndex(AllocationIndex.coveredArea);
					else if("销售面积".equals(colValue))
						entry.setAllocationIndex(AllocationIndex.saleArea);
				}
				if(("应分摊总量").equals(colName))
					entry.setTotalAmount(UIRuleUtil.getBigDecimal(colValue));
				if(("待分摊总量").equals(colName))
					entry.setShare(UIRuleUtil.getBigDecimal(colValue));
				if(("分摊单价").equals(colName))
					entry.setSharePrice(UIRuleUtil.getBigDecimal(colValue));
				if(("备注").equals(colName))
					entry.setRemark(UIRuleUtil.getString(colValue));
				//受益项目	分摊基数	分摊金额
				if(("受益项目").equals(colName)){
					CurProjectCollection curProjectCollection = ICurProject.getCurProjectCollection("select id,number,name where name='"+colValue+"'");
					detailInfo.setBenefitProject(curProjectCollection.size()>0?curProjectCollection.get(0):null);
				}
				if(("分摊基数").equals(colName)){
					detailInfo.setAllocationBase(UIRuleUtil.getBigDecimal(colValue));
				}
				if(("分摊金额").equals(colName)){
					detailInfo.setShareAmount(UIRuleUtil.getBigDecimal(colValue));
				}
				entry.getDetail().add(detailInfo);
			}
			editData.getEntrys().add(entry);
		}
		table.removeColumn(table.getColumnIndex("ftje"));
		table.removeColumn(table.getColumnIndex("ftjs"));
		table.removeColumn(table.getColumnIndex("syxm"));
		loadFields();
		return true;
	}	
		
	
	//校验
	private String verifyFieldFormat(int rowIndex ,String name, Object value, String dataType){
	    if(dataType.equals("int")){
	        String intString = (String)value;
//		        if(!Pattern.matches("[+|-]?\\d+", intString))
	        if(!Pattern.matches("[+]?\\d+", intString))
	        	return "第["+rowIndex+"]行 "+name+" 不是数字格式或为负数,值["+value+"]。\n";
	    }else if(dataType.equals("bigdecimal")){
	        String bigDecimalStirng = (String)value;
	        if(!Pattern.matches("[+]?\\d+(.\\d+)?(e[+]\\d+)?", bigDecimalStirng))
//		        if(!Pattern.matches("[-|+]?\\d+(.\\d+)?(e[-|+]\\d+)?", bigDecimalStirng))
	        	return "第["+rowIndex+"]行 "+name+" 不是数字格式或为负数,值["+value+"]。\n";
	    }else if(dataType.equals("date")){
	        String dateString = (String)value;
	        if(!Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", dateString))
	        	return "第["+rowIndex+"]行 "+name+" 日期格式不对,值["+value+"]。\n";
	    }else if(dataType.equals("bool") && !value.equals("是") && !value.equals("否"))
	    	return "第["+rowIndex+"]行 "+name+" 格式不对,值["+value+"]。\n";
	    return "";
	}
	//导出表格
	private void outExcelButton_actionPerformed(ActionEvent e) throws Exception {
		ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();
        
        KDTable tempTable = new KDTable();
        
        IRow entryRow = kdtEntrys.getHeadRow(0);
        IRow headRow = tempTable.addHeadRow();
        for (int i = 0; i < this.kdtEntrys.getColumnCount(); i++) {
        	tempTable.addColumn();
        	headRow.getCell(i).setValue(entryRow.getCell(i).getValue());
		}
        tempTable.getColumn(0).getStyleAttributes().setHided(true);
//        tempTable.removeRows();
        IColumn addColumn = tempTable.addColumn();
        addColumn.setKey("syxm");
        headRow.getCell(addColumn.getKey()).setValue("受益项目");
        addColumn = tempTable.addColumn();
        addColumn.setKey("ftjs");
        headRow.getCell(addColumn.getKey()).setValue("分摊基数");
        addColumn = tempTable.addColumn();
        addColumn.setKey("ftje");
        headRow.getCell(addColumn.getKey()).setValue("分摊金额");

        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
        tablesVO[0]=new KDTables2KDSBookVO(tempTable);
		tablesVO[0].setTableName("工程分摊明细");
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
        
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("公建配套工程分摊导入模板.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				FDCMsgBox.showInfo("导出成功！");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
	}

	protected void beforeStoreFields(ActionEvent arg0) throws Exception {
		super.beforeStoreFields(arg0);
	}
	
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		boolean flse = (oprtType.equals("EDIT")||oprtType.equals("ADDNEW"))?true:false;
		importExcelButton.setEnabled(flse);
		if(kdtDetail_detailPanel!=null){
			kdtDetail_detailPanel.getAddNewLineButton().setEnabled(flse);
			kdtDetail_detailPanel.getInsertLineButton().setEnabled(flse);
			kdtDetail_detailPanel.getRemoveLinesButton().setEnabled(flse);
		}
		if(kdtEntrys_detailPanel!=null){
			kdtEntrys_detailPanel.getAddNewLineButton().setEnabled(flse);
			kdtEntrys_detailPanel.getInsertLineButton().setEnabled(flse);
			kdtEntrys_detailPanel.getRemoveLinesButton().setEnabled(flse);
		}
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		for (int i = 0; i < kdtEntrys.getRowCount(); i++) {
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "engineeringProject").getValue())) {
				MsgBox.showWarning("项目不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "facilityName").getValue())) {
				MsgBox.showWarning("设施名称不能为空！");
				SysUtil.abort();
			}
			if(UIRuleUtil.isNull(kdtEntrys.getCell(i,"proptreyRight").getValue
			 ())){
			 MsgBox.showWarning("产权情况不能为空！");
			 SysUtil.abort();
			 }
//			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "constructionArea")
//					.getValue())) {
//				MsgBox.showWarning("建筑面积不能为空！");
//				SysUtil.abort();
//			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "startTime").getValue())) {
				MsgBox.showWarning("开工时间不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "completionTime")
					.getValue())) {
				MsgBox.showWarning("竣工时间不能为空！");
				SysUtil.abort();
			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "totalCost").getValue())) {
				MsgBox.showWarning("成本总额不能为空！");
				SysUtil.abort();
			}
//			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "costHasOccurred").getValue())) {
//				MsgBox.showWarning("已发生不能为空！");
//				SysUtil.abort();
//			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "totalAmount").getValue())) {
				MsgBox.showWarning("应分摊总量不能为空！");
				SysUtil.abort();
			}
//			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "share").getValue())){
//				MsgBox.showWarning("待分摊总量不能为空！");
//				SysUtil.abort();
//			}else{
				if(((BigDecimal) (kdtEntrys.getCell(i, "share").getValue())).compareTo(BigDecimal.ZERO)==-1){
					MsgBox.showWarning("待分摊总量不能为负数！");
					SysUtil.abort();
				}
//			}
			if (UIRuleUtil.isNull(kdtEntrys.getCell(i, "sharePrice").getValue())) {
				MsgBox.showWarning("分摊单价不能为空！");
				SysUtil.abort();
			}
		}
		
		for(int x= 0; x<kdtEntrys.getRowCount(); x++){
			IRow row = kdtEntrys.getRow(x);
			String xmId = ((CurProjectInfo)row.getCell("engineeringProject").getValue()).getId().toString();
			String lxId =((ProductTypeInfo)row.getCell("facilityName").getValue()).getId().toString();
			for(int l= 1+x; l<kdtEntrys.getRowCount();l++){
				row = kdtEntrys.getRow(l);
				String xmIdT = ((CurProjectInfo)row.getCell("engineeringProject").getValue()).getId().toString();
				String lxIdT =((ProductTypeInfo)row.getCell("facilityName").getValue()).getId().toString();
				if((xmId+lxId).equals(xmIdT+lxIdT)){
					MsgBox.showWarning("第"+(x+1)+"行与第"+(l+1)+"行工程项目+设施名称重复！！！");
					SysUtil.abort();
				}
			}
		}
		storeFields();
		
		for (int j = 0; j < editData.getEntrys().size(); j++) {
			GcftbEntryInfo gcftbEntryInfo = editData.getEntrys().get(j);
			
			Set<String> projectSet = new HashSet<String>();
			for (int k = 0; k < gcftbEntryInfo.getDetail().size(); k++) {
				GcftbEntryDetailInfo detailInfo = gcftbEntryInfo.getDetail().get(k);
				
				if (UIRuleUtil.isNull(detailInfo.getBenefitProject())) {
					MsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊项目不能为空！");
					SysUtil.abort();
				}
//				if (UIRuleUtil.isNull(detailInfo.getAllocationBase())) {
//					MsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊基数不能为空！");
//					SysUtil.abort();
//				}
//				if (UIRuleUtil.isNull(detailInfo.getShareAmount())) {
//					MsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊金额不能为空！");
//					SysUtil.abort();
//				}
				projectSet.add(detailInfo.getBenefitProject().getId().toString());
			}
			
			if(projectSet.size()!=gcftbEntryInfo.getDetail().size()){
				FDCMsgBox.showWarning(gcftbEntryInfo.getEngineeringProject().getName()+ "对应的分摊项目重复！");
				SysUtil.abort();
			}
		}
	}

	// 编辑停止事件
	protected void kdtEntrys_editStopped(KDTEditEvent e) throws Exception {
		super.kdtEntrys_editStopped(e);
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		 //项目是否结束
		 if(colIndex == kdtEntrys.getColumnIndex("engineeringProject") &&
		 kdtEntrys.getCell(rowIndex, colIndex).getValue() != null)
		 {
			 CurProjectInfo xminfo = (CurProjectInfo)kdtEntrys.getCell(rowIndex,
			 colIndex).getValue();
			 boolean end = xminfo.isProjectEnd();
			 if(end == true){
			 MsgBox.showWarning("项目已结束，请选择别的项目");
			 SysUtil.abort();
			 }
		 }
		changeTableDataLinens(kdtEntrys);
		 setTableToSumField(kdtEntrys,new String[]{"totalCost","totalAmount","costHasOccurred","share"});
	     setTableToSumField(kdtDetail,new String[]{"allocationBase","shareAmount"});    
	}

	//子分录的按钮构造(只有两个)
	private void setEntryDetailButtonAction() {
		ActionListener[] actions = kdtDetail_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtDetail_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
		kdtDetail_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						removeDetailEntry();
					}
			});
		kdtDetail_detailPanel.getInsertLineButton().removeActionListener(actions[0]);
		kdtDetail_detailPanel.getInsertLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		kdtDetail_detailPanel.getAddNewLineButton().removeActionListener(actions[0]);
		kdtDetail_detailPanel.getAddNewLineButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		kdtDetail_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
	              public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	            	  GcftbEntryInfo entryInfo = checkSelectEntry();
	            	  IObjectValue vo = event.getObjectValue();
	            	  vo.put("id", BOSUuid.create(new GcftbEntryDetailInfo().getBOSType()));
	            	  vo.put("parent1", entryInfo);
	              }
	              public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	              }
	      });
		kdtDetail_detailPanel.addInsertListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener(){
			public void beforeEvent(DetailPanelEvent detailpanelevent) throws Exception {
				GcftbEntryInfo entryInfo = checkSelectEntry();
				IObjectValue vo = detailpanelevent.getObjectValue();
				vo.put("id", BOSUuid.create(new GcftbEntryDetailInfo().getBOSType()));
				vo.put("parent1", entryInfo);
			}
			public void afterEvent(DetailPanelEvent detailpanelevent) throws Exception {
			}
		});
	}
	//分录的三个按钮构造
	private void setEntryButtonAction() {
		ActionListener[] actions = kdtEntrys_detailPanel.getRemoveLinesButton().getActionListeners();
		if (actions != null && actions.length > 0)
			kdtEntrys_detailPanel.getRemoveLinesButton().removeActionListener(actions[0]);
		kdtEntrys_detailPanel.getRemoveLinesButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						kdtEntrys.removeRow(kdtEntrys.getSelectManager().getActiveRowIndex());
					}
			});
		kdtEntrys_detailPanel.getInsertLineButton().removeActionListener(actions[0]);
		kdtEntrys_detailPanel.getInsertLineButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
			});
		kdtEntrys_detailPanel.getAddNewLineButton().removeActionListener(actions[0]);
		kdtEntrys_detailPanel.getAddNewLineButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		kdtEntrys_detailPanel.addAddListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener() {
	              public void beforeEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	            	  IObjectValue vo = event.getObjectValue();
	            	  vo.put("id", BOSUuid.create(new GcftbEntryInfo().getBOSType()));
	              }
	              public void afterEvent(com.kingdee.eas.framework.client.multiDetail.DetailPanelEvent event) throws Exception {
	              }
	      });
		kdtEntrys_detailPanel.addInsertListener(new com.kingdee.eas.framework.client.multiDetail.IDetailPanelListener(){
			public void beforeEvent(DetailPanelEvent detailpanelevent) throws Exception {
				IObjectValue vo = detailpanelevent.getObjectValue();
				vo.put("id", BOSUuid.create(new GcftbEntryInfo().getBOSType()));
			}
			public void afterEvent(DetailPanelEvent detailpanelevent) throws Exception {
			}
		});
	}
	//子分录(删除控制)
	private void removeDetailEntry(){
		int activeRowIndex = kdtDetail.getSelectManager().getActiveRowIndex();
		if(activeRowIndex==-1)return;
		GcftbEntryDetailInfo detailInfo = (GcftbEntryDetailInfo)kdtDetail.getRow(activeRowIndex).getUserObject();
		
		String afterId = "";
		if(detailInfo.getParent1()!=null)
			afterId = detailInfo.getParent1().getId().toString();
		for (int i = 0; i < this.kdtEntrys.getRowCount(); i++) {
			IRow row = this.kdtEntrys.getRow(i);
			if(row.getUserObject()==null ||!(row.getUserObject() instanceof GcftbEntryInfo))
				continue;
			GcftbEntryInfo entryInfo = (GcftbEntryInfo)row.getUserObject();
			if(afterId.equals(entryInfo.getId().toString())){
				entryInfo.getDetail().remove(detailInfo);
			}
		}
		kdtDetail.removeRow(kdtDetail.getSelectManager().getActiveRowIndex());
		try {
			changeTableDataLinens(kdtEntrys);
		} catch (BOSException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	//分录的对应
	private GcftbEntryInfo checkSelectEntry(){
		int activeRowIndex = this.kdtEntrys.getSelectManager().getActiveRowIndex();
		if(this.kdtEntrys.getSelectManager().getActiveRowIndex()==-1){
			FDCMsgBox.showWarning("请先选中明细分录！");
			SysUtil.abort();
		}
		
		return (GcftbEntryInfo)this.kdtEntrys.getRow(activeRowIndex).getUserObject();
	}
	
	//分摊基数赋值
	protected void kdtDetail_editStopped(KDTEditEvent e) throws Exception {
		super.kdtDetail_editStopped(e);
		changeTableDataLinens(kdtDetail);
		
		 setTableToSumField(kdtEntrys,new String[]{"totalCost","totalAmount","costHasOccurred","share"});
	     setTableToSumField(kdtDetail,new String[]{"allocationBase","shareAmount"});    
		
	}
	
	//分录表格监听
	private void changeTableDataLinens(KDTable table) throws BOSException, SQLException{
		int indexRow = table.getSelectManager().getActiveRowIndex();
		if(indexRow==-1)return;
		IRow row = table.getRow(indexRow);
		if(table.getName().equals("kdtEntrys")){
			//判断项目是否全部分摊
			boolean Ft = UIRuleUtil.getBooleanValue(row.getCell("allshare").getValue());
			CurProjectInfo projectInfo = (CurProjectInfo)row.getCell("engineeringProject").getValue();
			BigDecimal jzmj = null;
			BigDecimal cqgsJzmj = null;
			if(projectInfo == null){
				MsgBox.showWarning("请选择项目");
				SysUtil.abort();
			}
			ProductTypeInfo cplxInfo=(ProductTypeInfo)row.getCell("facilityName").getValue();
			if(cplxInfo == null){
				MsgBox.showWarning("请选择设施");
				SysUtil.abort();
			}
			cqgsJzmj = getCQGSjzmj(projectInfo.getId().toString(), cplxInfo.getId().toString());
			jzmj = getjzmj(projectInfo.getId().toString());
			if(Ft){
				row.getCell("constructionArea").setValue(jzmj);
			}else{
				row.getCell("constructionArea").setValue(cqgsJzmj);
			}
			
			if(UIRuleUtil.isNull(row.getCell("costHasOccurred").getValue())){
				//单价(已发生成本/总量)
				row.getCell("sharePrice").setValue(FDCHelper.divide(row.getCell("totalCost").getValue(), row.getCell("totalAmount").getValue(),10,4));
			}else{
				row.getCell("sharePrice").setValue(FDCHelper.divide(row.getCell("costHasOccurred").getValue(), row.getCell("totalAmount").getValue(),10,4));
			}
			AllocationIndex cationIndex = (AllocationIndex)row.getCell("allocationIndex").getValue();
			updateDetailBaseAmount(-1,cationIndex);
			row.getCell("share").setValue(FDCHelper.subtract(row.getCell("totalAmount").getValue(), UIRuleUtil.sum(kdtDetail, "allocationBase")));
		}else{
			//判断项目是否有
			CurProjectInfo syxmInfo = (CurProjectInfo)row.getCell("benefitProject").getValue();
			if(syxmInfo != null){
				//判断项目是否结束
				boolean end = syxmInfo.isProjectEnd();
				if(end == false){
					int activeRowIndex = this.kdtEntrys.getSelectManager().getActiveRowIndex();
					if(activeRowIndex==-1)return;
					AllocationIndex cationIndex = (AllocationIndex)this.kdtEntrys.getCell(activeRowIndex, "allocationIndex").getValue();
					updateDetailBaseAmount(indexRow,cationIndex);
					this.kdtEntrys.getCell(activeRowIndex, "share").setValue(FDCHelper.subtract(this.kdtEntrys.getCell(activeRowIndex,"totalAmount").getValue(), UIRuleUtil.sum(kdtDetail, "allocationBase")));
				}else{
					MsgBox.showWarning("项目已结束，请选择别的项目");
					SysUtil.abort();
				}
			}else{
				MsgBox.showWarning("请选择项目");
			}
		}
	} 
	
	
	private void updateDetailBaseAmount(int rowIndex,AllocationIndex cationIndex) throws BOSException, SQLException{
		if(rowIndex==-1){
			for (int i = 0; i < this.kdtDetail.getRowCount(); i++)
				updateDetail(i, cationIndex);
		}else{
			updateDetail(rowIndex, cationIndex);
		}
	}
	//子分录的数据的联动
	private void updateDetail(int rowIndex,AllocationIndex cationIndex) throws BOSException, SQLException{
		IRow row = this.kdtDetail.getRow(rowIndex);
		CurProjectInfo syxmInfo = (CurProjectInfo)row.getCell("benefitProject").getValue();
		if(syxmInfo != null){
			//项目结束就不联动；
			if(syxmInfo.isProjectEnd())
				return;
		}
		GcftbEntryDetailInfo detailInfo = new GcftbEntryDetailInfo();
		if(row.getUserObject()!=null && (row.getUserObject() instanceof GcftbEntryDetailInfo))
			detailInfo = (GcftbEntryDetailInfo)row.getUserObject();
		if(row.getCell("benefitProject").getValue()==null){
			row.getCell("allocationBase").setValue(BigDecimal.ZERO);
			row.getCell("shareAmount").setValue(BigDecimal.ZERO);
			detailInfo.setAllocationBase(BigDecimal.ZERO);
			detailInfo.setShareAmount(BigDecimal.ZERO);
			return;
		}
		CurProjectInfo projectInfo = (CurProjectInfo)row.getCell("benefitProject").getValue();
		BigDecimal baseAmount = getBaseAmount(projectInfo.getId().toString(), cationIndex);
		BigDecimal area = getCQGSarea(projectInfo.getId().toString(), cationIndex);
		BigDecimal sharePrice = BigDecimal.ZERO;
		int activeRowIndex = this.kdtEntrys.getSelectManager().getActiveRowIndex();
		if(activeRowIndex!=-1)
			sharePrice = UIRuleUtil.getBigDecimal(this.kdtEntrys.getCell(activeRowIndex, "sharePrice").getValue());
		if(baseAmount.compareTo(BigDecimal.ZERO)!=0){
			row.getCell("allocationBase").setValue(baseAmount);//分摊基数
			detailInfo.setAllocationBase(baseAmount);
			row.getCell("shareAmount").setValue(FDCHelper.multiply(baseAmount, sharePrice,4));//分摊金额
			detailInfo.setShareAmount(FDCHelper.multiply(baseAmount, sharePrice,4));
		}else{
			row.getCell("allocationBase").setValue(area);
			detailInfo.setAllocationBase(area);
			row.getCell("shareAmount").setValue(FDCHelper.multiply(area, sharePrice,4));
			detailInfo.setShareAmount(FDCHelper.multiply(area, sharePrice,4));
		}
	}
	
	//分摊面积取面积指标管理中的 面积
	private BigDecimal getBaseAmount(String projectId,AllocationIndex index) throws BOSException, SQLException{
		BigDecimal amount = BigDecimal.ZERO;
		StringBuffer sb = new StringBuffer();
		// 产品建筑指标 == 动态——竣工查账 优先取：项目规划指标 == 目标指标
		sb.append(" select case when max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)=0");
		sb.append(" and max(case when pe.fname_l2 ='可售面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) = 0");
		sb.append(" then max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end) else");
		sb.append(" max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) end,");
		sb.append("   ");
		sb.append(" case when max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)=0");
		sb.append(" and max(case when pe.fname_l2 ='可售面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) = 0");
		sb.append(" then max(case when pe.fname_l2 ='可售面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end) else");
		sb.append(" max(case when pe.fname_l2 ='可售面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) end");
		sb.append(" from T_FDC_ProjectIndexDataEntry entry  ");
		sb.append(" left join T_FDC_ApportionType  pe on pe.fid = entry.FApportionTypeID");
		sb.append(" left join T_FDC_ProjectIndexData data on data.fid=entry.FParentID ");
		sb.append(" left join T_FDC_CurProject  ct on ct.fid = data.FProjOrOrgID ");
		sb.append(" left join T_FDC_TargetType  tag on tag.fid =entry.FTargetTypeID ");
		sb.append(" where ct.fid ='").append(projectId).append("'");
		sb.append(" and (data.fisLatestVer=1 OR data.fisLatestSubVer=1)");
		sb.append(" and data.FProductTypeID is null");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
		while(rowset.next())
			amount = index.equals(AllocationIndex.coveredArea)?UIRuleUtil.getBigDecimal(rowset.getBigDecimal(1)):UIRuleUtil.getBigDecimal(rowset.getBigDecimal(2));
		return amount;
	}
	
	//建筑面积取面积指标管理中的 面积
	private BigDecimal getjzmj(String projectId) throws BOSException, SQLException{
		BigDecimal jzmj = BigDecimal.ZERO;
		StringBuffer sb = new StringBuffer();
		// 产品建筑指标 == 动态——竣工查账 优先取：项目规划指标 == 目标指标
		sb.append(" select case when max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)=0 ");
		sb.append(" and max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end ) = 0");
		sb.append(" then max(case  when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end) else");
		sb.append(" max(case  when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)  end,");
		sb.append("   ");
		sb.append(" case when max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='3COMPLETEAREA' then isnull(entry.FIndexValue,0) else 0 end)=0 ");
		sb.append(" and max(case when pe.fname_l2 ='建筑面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end ) = 0");
		sb.append(" then max(case  when pe.fname_l2 ='建筑面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end) else");
		sb.append(" max(case  when pe.fname_l2 ='建筑面积' and data.FVerName ='1AIMCOSTAREA' then isnull(entry.FIndexValue,0) else 0 end)  end");
		sb.append(" from T_FDC_ProjectIndexDataEntry entry");
		sb.append(" left join T_FDC_ApportionType  pe on pe.fid = entry.FApportionTypeID");
		sb.append(" left join T_FDC_ProjectIndexData data on data.fid=entry.FParentID ");
		sb.append(" left join T_FDC_CurProject  ct on ct.fid = data.FProjOrOrgID ");
		sb.append(" left join T_FDC_TargetType  tag on tag.fid =entry.FTargetTypeID ");
		sb.append(" where ct.fid ='").append(projectId).append("'");
		sb.append(" and (data.fisLatestVer=1 OR data.fisLatestSubVer=1)");
		sb.append(" and data.FProductTypeID is null");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
		while(rowset.next())
			if(UIRuleUtil.getBigDecimal(rowset.getBigDecimal(1)).compareTo(BigDecimal.ZERO)!=0){
				jzmj = UIRuleUtil.getBigDecimal(rowset.getBigDecimal(1));
			}else{
				jzmj = UIRuleUtil.getBigDecimal(rowset.getBigDecimal(2));
			}
		return jzmj;
	}
	//分摊面积取产权归属表中的 面积
	private BigDecimal getCQGSarea(String projectId,AllocationIndex index) throws BOSException, SQLException{
		BigDecimal area = BigDecimal.ZERO;
		StringBuffer sb = new StringBuffer();
		sb.append(" select sum(entry.CFBuidlingArea),sum(entry.CFSaleArea)");
		sb.append("  from CT_COS_CQGSEntry entry ");
		sb.append(" left join CT_COS_CQGS bt on bt.fid = entry.FParentID ");
		sb.append(" where bt.CFProjectNameID='").append(projectId).append("'");
		sb.append(" and bt.CFLasted = '1' ");
		sb.append(" and entry.CFBuildingNameID is null");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
		while(rowset.next())
			area = index.equals(AllocationIndex.coveredArea)?UIRuleUtil.getBigDecimal(rowset.getBigDecimal(1)):UIRuleUtil.getBigDecimal(rowset.getBigDecimal(2));
		return area;
	}
	//建筑面积取产权归属表中的 面积
	private BigDecimal getCQGSjzmj(String projectId,String BuilDingNameId) throws BOSException, SQLException{
		BigDecimal area = BigDecimal.ZERO;
		StringBuffer sb = new StringBuffer();
		sb.append(" select entry.CFBuidlingArea");
		sb.append("  from CT_COS_CQGSEntry entry ");
		sb.append(" left join CT_COS_CQGS bt on bt.fid = entry.FParentID ");
		sb.append(" where bt.CFProjectNameID='").append(projectId).append("'");
		sb.append(" and entry.CFBUILDINGNAMEID ='").append(BuilDingNameId).append("'");
		sb.append(" and bt.CFLasted = '1' ");
		IRowSet rowset = new FDCSQLBuilder().appendSql(sb.toString()).executeQuery();
		while(rowset.next())
			area = UIRuleUtil.getBigDecimal(rowset.getBigDecimal(1));
		return area;
	}
	/**
	 * output loadFields method
	 */
	public void loadFields() {
		super.loadFields();
		setAuditBtnEnable();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		setAuditBtnEnable();
	}

	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnAddLine_actionPerformed(e);
	}

	/**
	 * 设置审批按钮显示
	 */
	private void setAuditBtnEnable() {
		if (editData.getStatus() == null) {
			aduitAction.setEnabled(false);
			unAduit.setEnabled(false);
		} else if (FDCBillStateEnum.SUBMITTED.equals(editData.getStatus())) {
			aduitAction.setEnabled(true);
			unAduit.setEnabled(false);
			// actionSave.setEnabled(false);
			actionEdit.setEnabled(true);
		} else if (FDCBillStateEnum.AUDITTED.equals(editData.getStatus())) {
			aduitAction.setEnabled(false);
			actionEdit.setEnabled(false);
			unAduit.setEnabled(true);
		}
	}

	// 是否修订
	private boolean isBillModify() {
		// Boolean isSet = (Boolean) getUIContext().get(IS_MODIFY);
		// return isSet != null && isSet.booleanValue();
		// return getUIContext().containsKey(IS_MODIFY);
		return false;
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (isModify()) {
			FDCMsgBox.showInfo("单据已被修改，请先提交。");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.SUBMITTED, CANTAUDIT);
		super.actionAudit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditBtnEnable();
	}

	public void actionUnaudit_actionPerformed(ActionEvent e) throws Exception {
		if (!editData.isIsLast()) {
			FDCMsgBox.showInfo("不是最新版，不能反审批！");
			this.abort();
		}
		checkBeforeAuditOrUnAudit(FDCBillStateEnum.AUDITTED, CANTUNAUDIT);
		super.actionUnaudit_actionPerformed(e);
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setAuditBtnEnable();
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.lockUIForViewStatus();
		setAuditBtnEnable();
	}

	private void handleOldData() {
		if (!(getOprtState() == STATUS_FINDVIEW || getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}

	private void syncDataFromDB() throws Exception {
		// 由传递过来的ID获取值对象
		if (getUIContext().get(UIContext.ID) == null) {
			String s = EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_IDIsNull");
			MsgBox.showError(s);
			SysUtil.abort();
		}
		IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
		setDataObject(getValue(pk));
		loadFields();
	}

	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning)
			throws Exception {
		// 检查单据是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		boolean b = editData != null && editData.getStatus() != null
				&& editData.getStatus().equals(state);
		if (!b) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}

		if (getOprtState().equals(STATUS_EDIT)) {
			String warn = null;
			if (state.equals(FDCBillStateEnum.AUDITTED)) {
				warn = CANTUNAUDITEDITSTATE;
			} else {
				warn = CANTAUDITEDITSTATE;
			}
			MsgBox.showWarning(this, FDCClientUtils.getRes(warn));
			SysUtil.abort();
		}
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.gcftbiaoa.GcftbFactory.getRemoteInstance();
	}

	/**
	 * output createNewDetailData method
	 */
	protected IObjectValue createNewDetailData(KDTable table) {

		return null;
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		if (getUIContext().get("ForInfo") != null) {
			GcftbInfo info = (GcftbInfo) getUIContext().get("ForInfo");
			info.setBbh(String.valueOf(getUIContext().get("Bbh")));
			info.setId(null);
			info.setIsLast(false);
			for (int i = 0; i < info.getEntrys().size(); i++) {
				GcftbEntryInfo entryInfo = info.getEntrys().get(i);
				entryInfo.setId(BOSUuid.create(entryInfo.getBOSType()));
				for (int j = 0; j < entryInfo.getDetail().size(); j++) {
					entryInfo.getDetail().get(j).setId(BOSUuid.create(new GcftbEntryDetailInfo().getBOSType()));
				}
			}
			return info;
		} else {
			com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo objectValue = new com.kingdee.eas.fdc.gcftbiaoa.GcftbInfo();
			objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo) (com.kingdee.eas.common.client.SysContext
							.getSysContext().getCurrentUser()));
			if (getUIContext().get("treeNodeInfo") == null) {
				MsgBox.showWarning("合同不能为空！");
				SysUtil.abort();
			}
			TreeNodeInfo conInfo = (TreeNodeInfo) getUIContext().get(
					"treeNodeInfo");
			objectValue.setEngineeringProject(conInfo);
			objectValue.setGsmc(conInfo.getCompany().getName());
			objectValue.setBbh("1");
			objectValue.setBizDate(new Date());
			objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
			// objectValue.setAmount(BigDecimal.ZERO);
			return objectValue;

		}
	}

}