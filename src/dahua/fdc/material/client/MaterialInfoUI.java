/*
 * @(#)MaterialInfoUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */

package com.kingdee.eas.fdc.material.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.data.xy.XYDataset;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.material.MaterialCollection;
import com.kingdee.eas.basedata.master.material.MaterialGroupFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.basedata.master.material.MaterialInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.material.MaterialIndexCollection;
import com.kingdee.eas.fdc.material.MaterialIndexFactory;
import com.kingdee.eas.fdc.material.MaterialIndexInfo;
import com.kingdee.eas.fdc.material.MaterialIndexValueCollection;
import com.kingdee.eas.fdc.material.MaterialIndexValueFactory;
import com.kingdee.eas.fdc.material.MaterialIndexValueInfo;
import com.kingdee.eas.fdc.material.MaterialInfoCollection;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.fdc.material.MaterialInfoInfo;
import com.kingdee.eas.fi.rpt.IReportProcess;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 描述：材料信息库<p>
 * @author 向晓帆
 * @version EAS 4.0
 * @see IReportProcess
 */

public class MaterialInfoUI extends AbstractMaterialInfoUI {
	
	/**缺省版本标识*/
	private static final long serialVersionUID = 1L;
	
	/**日志*/
	private static final Logger logger = CoreUIObject.getLogger(MaterialInfoUI.class);
	
	/**声明资源文件位置*/
    public static final String MATERIALIMPORT_RESOURCEPATH = "com.kingdee.eas.fdc.material.client.MaterialImportResource";
	private static final String MATERIALINFO_RESOURCEPATH = "com.kingdee.eas.fdc.material.MaterialInfoResource";
   
	/** 定义变量存入table */
	public static KDTable tblMaterial = null;
	public static String rightUpTableSelectedRowId = null;
	
	private boolean bl = false;
	
	/**获得表T_MTR_MaterialInfo所有的信息*/
	private List getMaterialInfos = null;

	/**记录供应商是否为空*/
	private List supplierIsNullList = null; 
	
	public IUIWindow uiWindow = null ;
	
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
	
	private ArrayList material_dataRepeat = null;
	
	 /**
	* 描述：<构造函数>
	* @author <luoxiaolong>
	* @param  <e>
	* @return  <null>
	* 创建时间  <2010/11/18> <p>
	* 
	* 修改人：<修改人> <p>
	* 修改时间：<yyyy/mm/dd> <p>
	* 修改描述：<修改描述> <p>
	*
	* @see  <相关的类>
	*/
	public MaterialInfoUI() throws Exception {
		super();
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

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if (getTypeSelectedTreeNode() != null)
			// 加上这句就可以把树上的节点对象传到EditUI里面去了。
			this.getUIContext().put("materialNodeInfo",
					getTypeSelectedTreeNode().getUserObject());
	}

	/**
	 * 描述：获得树的节点 创建时间：2010-11-16
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.kDTree1
				.getLastSelectedPathComponent();
	}

	/**
	 * 描述：增删改界面打开方式
	 * 创建时间：2010-11-16
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	private void openModel(String state) throws UIException {
		//获得材料详细信息表里面选中的一行
		IRow row= null;
		if(state.equals(OprtState.ADDNEW)){
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}else {
			row = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex());
		}
		//声明并获得选中行的ID
		String materialInfoId = null;
		if (row != null && row.getCell("id") != null) {
			materialInfoId = String.valueOf(row.getCell("id").getValue() == null ? "" : row.getCell("id").getValue().toString());
		}
//		//检查是否选中
//		this.checkSelected();
	
		//得到树的对象
		MaterialGroupInfo info = this.getMaterialGroupInfo();
		/*
		 * 得到id不为空
		 */
		if (materialInfoId != null) {
			//创建新的UIcontext对象,
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put(UIContext.ID, materialInfoId);
			uiContext.put("materialNodeInfo", info);
			uiContext.put("materialInfoTable", this.MaterialInfo);
			uiContext.put("isViewEnbled", (Boolean)getUIContext().get("isViewEnbled"));
			
			
			
			/*
			 * 如果获得的单元格不为空那么就为他赋值
			 * 将界面跳转需要的值放入到UIContext对象中
			 */
			if(this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
					"number").getValue()!=null && !"".equals(this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
					"number").getValue())){
				
				uiContext.put("tblNumberValue", this.tblMain.getCell(
						this.tblMain.getSelectManager().getActiveRowIndex(),
				"number").getValue().toString().trim());
			}else{
				uiContext.put("tblNumberValue", "");
			}
			/*
			 * 如果获得的单元格不为空那么就为他赋值
			 */
			if (this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"name").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"name").getValue())) {
				
				uiContext.put("tblNameValue",this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "name")
						.getValue().toString().trim());
			}else{
				uiContext.put("tblNameValue","");
			}
			/*
			 * 如果获得的单元格不为空那么就为他赋值
			 */
			if(this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"model").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"model").getValue())){
				
				uiContext.put("tblModelValue", this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"model")
						.getValue().toString().trim());
				
			}else{
				uiContext.put("tblModelValue", "");
			}
			
			/*
			 * 如果获得的单元格不为空那么就为他赋值
			 */
			if (this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"baseUnit.name").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"baseUnit.name").getValue())) {
				uiContext.put("tblUnitValue", this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"baseUnit.name")
						.getValue().toString().trim());
				
			} else {
				uiContext.put("tblUnitValue", "");
			}
			/*
			 * 如果获得的单元格不为空那么就为他赋值
			 */
			if (this.tblMain.getCell(
					this.tblMain.getSelectManager().getActiveRowIndex(),
			"id").getValue()!=null && !"".equals(this.tblMain.getCell(
			this.tblMain.getSelectManager().getActiveRowIndex(),
			"id").getValue())) {
				uiContext.put("tblMaterialId", this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(), "id")
						.getValue().toString().trim());
				
			} else {
				uiContext.put("tblMaterialId", "");
			}
			
			/*
			 * 如果获得的单元格不为空那么就为他赋值
			 */
			if(MaterialInfo.getRowCount() != 0 && MaterialInfo.getSelectManager().size() != 0){
				uiContext.put("materialInfoId", String.valueOf(this.MaterialInfo.getCell(
						this.MaterialInfo.getSelectManager().getActiveRowIndex(),"id") == null ? "" : this.MaterialInfo.getCell(
								this.MaterialInfo.getSelectManager().getActiveRowIndex(),"id").getValue().toString().trim()));
	        }
			
		

			//设置UI的打开方式
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			//显示UI
			IUIWindow uiWindow = uiFactory.create(MaterialInfoEditUI.class
					.getName(), uiContext, null, state);

			uiWindow.show();
		}
	}

	/**
	 * 描述：界面初始化方法 创建时间：2010-11-8
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public void onLoad() throws Exception {
		
		// 设置根节点+号
		this.kDTree1.setShowsRootHandles(true);
		super.onLoad();
		
		/*打开条件查询按钮  */
		this.actionQuery.setVisible(true);
		this.actionQuery.setEnabled(true);
		/*加载树*/
		BuilderTreeUtil.getTreeUtilInstance().buiderTree(this.kDTree1,
				MaterialGroupFactory.getRemoteInstance(), EASResource.getString(MATERIALINFO_RESOURCEPATH,"treeTittle"));
		/* 给树增加事件*/
		this.kDTree1.addTreeSelectionListener(new TreeSelectionListener() {
			/* 值改变事件*/
			public void valueChanged(TreeSelectionEvent e) {
				try {
					treeMain_valueChanged(e);
				} catch (Exception e1) {
					handUIException(e1);
				} finally {
				}
			}

		});
		/*给材料表增加点击事件*/
		this.tblMain.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getClickCount() == 1) {
					try {
						tblMain_tableClicked(e);
					} catch (Exception e1) {
						handUIException(e1);
					}
				} else if (e.getClickCount() == 2) {
					SysUtil.abort();
				}
			}

		});
		/*给材料表增加选择事件*/

		this.tblMain.addKDTSelectListener(new KDTSelectListener() {

			public void tableSelectChanged(KDTSelectEvent e) {

				try {
					tblMain_tableSelectChanged(e);
					tblMain_tableSelectChanged2(e);
				} catch (Exception e1) {
					handUIException(e1);  
				}
			}

		});
		/*给报价信息表增加鼠标点击事件*/
		this.MaterialInfo.addKDTMouseListener(new KDTMouseListener() {

			public void tableClicked(KDTMouseEvent e) {
				if (e.getClickCount() == 1) {
					SysUtil.abort();
				} else if (e.getClickCount() == 2) {
					try {
						checkSelected();
						openModel(OprtState.VIEW);
					} catch (UIException e1) {
						handUIException(e1);
					}
				}
			}

		});
		//设置按钮状态
		setBtnState();
		setSortAuto(tblMain);
	}

	
	/** 
	 * 设置表格自动列排序
	 * @Author：owen_wen
	 * @CreateTime：2012-11-19
	 */
	private void setSortAuto(KDTable table) {
		for (int i = 0, size = table.getColumnCount(); i < size; i++) {
			table.getColumn(i).setSortable(true);
		}
		KDTSortManager sm = new KDTSortManager(table);
		sm.setSortAuto(true);
	}

	/**
	 * 描述：设置按钮状态
	 * 创建时间：2010-11-26<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private void setBtnState(){
		/*设置核准按钮图标*/
		this.audit.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		/*设置反核准按钮图标*/
		this.unAudit.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		/*设置核准按钮（菜单）图标*/
		this.auditItem.setIcon(EASResource.getIcon("imgTbtn_auditing"));
		/*设置反核准按钮（菜单）图标*/
		this.unAuditItem.setIcon(EASResource.getIcon("imgTbtn_fauditing"));
		/*设置分布图按钮图标*/
		this.showDotsMapItem.setIcon(EASResource.getIcon("imgTbtn_chart"));
		/*设置趋势图按钮图标*/
		this.showLineMapItem.setIcon(EASResource.getIcon("imgTbtn_chart"));
		/*设置从Excel导入按钮图标*/
		this.ExcelImportItem.setIcon(EASResource.getIcon("imgTbtn_importexcel"));
		/*设置导出Excel模版按钮图标*/
		this.ExportExcelItem.setIcon(EASResource.getIcon("imgTbtn_dcdwj"));
		/*设置从材料信息导入按钮图标*/
		this.MaterialImportItem.setIcon(EASResource.getIcon("imgTbtn_importfromzz"));
		/*设置导入按钮状态*/
		this.menuItemImportData.setVisible(false);
		/*设置导出按钮状态*/
		this.menuItemExportData.setVisible(false);
		/*设置分布图按钮状态*/
		this.showDotsMapItem.setEnabled(true);
		/*设置趋势图按钮状态*/
		this.showLineMapItem.setEnabled(true);
		/*设置导出Excel模版按钮状态*/
		this.ExportExcelItem.setEnabled(true);
		/*设置从Excel导入按钮状态*/
		this.actionImportData.setEnabled(true);
		/*设置从材料信息导入按钮状态*/
		this.actionImportDataFormMaterial.setEnabled(true);
		/*设置导出按钮状态*/
		this.actionExportExcel.setEnabled(true);
	}
	
	/**
	 * 描述：鼠标单击材料表事件 创建时间：2010-11-8
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	public  void reflushTable() throws Exception{
		tblMain_tableSelectChanged2(selectEvent); 
		
	}
	
	/**
	 * 描述：材料信息库详细表事件方法 创建时间：2010-11-12
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	int count = 0;
	private static String materialInfoId = null ;
	static  KDTSelectEvent selectEvent = null ;
	private void tblMain_tableSelectChanged2(KDTSelectEvent e) throws Exception {
		
			super.tblMain_tableSelectChanged(e);
			
			// 是否选中
			if (e.getSelectBlock() == null){
				return;
			}
		
		/*
		 * 获得当前选中的行，得出选中行的ID
		 */
		selectEvent = e ;
		KDTSelectBlock curSelect = e.getSelectBlock();
		int top = curSelect.getTop();
		if(null != this.getMainTable().getCell(top,this.getKeyFieldName())){
			if (top > -1 && null != this.getMainTable().getCell(top,this.getKeyFieldName()).getValue()) {
				materialInfoId = this.getMainTable().getCell(top,this.getKeyFieldName()).getValue().toString();
			}
		}
		
		if(materialInfoId != null){
			// 调用查询详细信息方法
			this.builderMaterialInfoTable();
		}
		count++;
	}

	/**
	 * 描述：表格选择事件 创建时间：2010-11-9
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		/**得到选择行的行号*/
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		if (rowIndex == -1)
			return;
		
		String treeNode = this.getMaterialTittle();
		/** 得到选择行model（规格型号）列的值*/
		Object materialModel = this.tblMain.getRow(rowIndex).getCell("model").getValue();
		Object materialNumber = this.tblMain.getRow(rowIndex).getCell("number").getValue();
		/**存MaterInfo标题字符*/
		StringBuffer sb = new StringBuffer();
		if(treeNode != null){
			sb.append(treeNode);
		}
		/*
		 * 如果选择行的number(编码)不为空 设置值
		 */
		if (materialNumber != null) {
			sb.append("-" + materialNumber);
			this.kDContainer2.setTitle(sb.toString());
		}
		/*
		 * 如果选择行的model（规格型号）不为空 设置值
		 */
		if (materialModel != null) {
			sb.append("-" + materialModel);
			this.kDContainer2.setTitle(sb.toString());
		}
		sb.reverse();
	}

	/**
	 * 描述：点击树改变值触发事件 创建时间：2010-11-5
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		/**创建基础物料信息表*/
		this.builderMaterialTable();
		/*设置基础物料标题*/
		this.kDContainer1.setTitle(this.getMaterialTittle());
		MaterialInfo.removeRows();
		int rowCount = this.tblMain.getRowCount();
		/*循环遍历设置时间显示方式*/
		for (int i = 0; i < rowCount; i++) {
			/*
			 * 如果日期列的值不为空将他的格式设置为 yyyy-mm-dd
			 */
			if (null != this.tblMain.getRow(i).getCell("quoteTime").getValue()) {
				String quoteTime = this.tblMain.getRow(i).getCell("quoteTime")
						.getValue().toString();
				quoteTime = quoteTime.substring(0, 10);
				this.tblMain.getRow(i).getCell("quoteTime").setValue(quoteTime);
			}
		}
		
		/*默认选中第一行*/
		if(this.tblMain.getRowCount() != 0){
			this.tblMain.getSelectManager().select(0,0);
		}
	}

	/**
	 * 描述：获得树的节点名称
	 * 创建时间：2010-11-20<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private String getMaterialTittle(){
		TreePath path = this.kDTree1.getSelectionPath();
		// 根据path得到节点
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();
		return treenode.toString();
		
	}

	/**
	 * 描述：获得当前详细信息的审核状态
	 * 创建时间：2010-11-22<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private boolean getMaterialInfoState() throws EASBizException, BOSException{
		/*获得材料信息表ID*/
		String materialInfoId = this.getMaterialInfoId();
		String state = this.getState(materialInfoId);
		if(materialInfoId!=null && !materialInfoId.equals("")){
			if(null == state || "".equals(state)){
				/*如果是空，则不做任何造作*/
			}
			/**如果是核准状态将不能进行以下操作*/
			else if (state.equals("已审批")) {
				FDCMsgBox.showWarning(this,EASResource.getString(MATERIALINFO_RESOURCEPATH,"auditState"));
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 获得状态
	 * @param materialInfoId
	 * @return
	 */
	private String getState(String materialInfoId){
		Map map = new HashMap();
		map.put("FID", materialInfoId);
		List fields = new ArrayList();
		fields.add("FState");
		String state = null;
		try {
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(map, "T_MTR_MaterialInfo", fields);
			Map mapField = (Map) list.get(0);
			state = mapField.get("FState").toString();
			if(state.equals("4AUDITTED")){
				state = "已审批";
				return state;
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return state;
	}
	/**
	 * 描述：获得编辑界面名字 
	 * 创建时间：2010-11-8
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	protected String getEditUIName() {
		return MaterialInfoEditUI.class.getName();
	}

	/**
	 * 描述：设置编辑界面打开的方式 
	 * 创建时间：2010-11-8
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * 描述：获得远程调用接口 
	 * 创建时间：2010-11-8
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return MaterialInfoFactory.getRemoteInstance();
	}




	boolean checkIsAddNew = false;

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = true;
		this.checkSelected();
		this.openModel(OprtState.ADDNEW);
		// this.sendValue();
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = false;
		this.checkSelected();
		this.materialInfoCheckSelected();
		this.openModel(OprtState.VIEW);
	}

	/**
	 * 描述：修改详细信息，判断为审批状态时不能修改 创建时间：2010-11-22
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = false;
		this.checkSelected();
		this.materialInfoCheckSelected();
		boolean flag = this.getMaterialInfoState();
		/*
		 * 如果为审核状态则不能进行编辑操作
		 */
		if (!flag) {
			this.openModel(OprtState.EDIT);
		}
	}

	/**
	 * 描述：删除方法 创建时间：2010-12-2
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkIsAddNew = false;
		this.checkSelected();
		this.materialInfoCheckSelected();
		boolean flag = this.getMaterialInfoState();

		if (!flag && MaterialInfo.getRowCount() != 0 && MaterialInfo.getSelectManager().size() != 0) {
			// 获得材料详细信息表里面选中的一行
			IRow row = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex());
			// 声明并获得选中行的ID
			String materialInfoId = null;
			if (row.getCell("id").getValue() != null) {
				materialInfoId = row.getCell("id").getValue().toString();
			}

			if (MsgBox.showConfirm2(EASResource.getString(MATERIALINFO_RESOURCEPATH, "sureDelete")) == 0) {
				MaterialInfoFactory.getRemoteInstance().deleteMaterialInfoRecord(new ObjectUuidPK(BOSUuid.read(materialInfoId)));
				MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "delDone"));
			} else {
				SysUtil.abort();
			}
		}
		this.refreahAfterAuditAndUnAudit(e);
	}

	/**
	 * 描述： 刷新方法 创建时间：2010-12-2
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		/** 加载树 */
		BuilderTreeUtil.getTreeUtilInstance().buiderTree(this.kDTree1, MaterialGroupFactory.getRemoteInstance(),
				EASResource.getString(MATERIALINFO_RESOURCEPATH, "treeTittle"));
		this.MaterialInfo.removeRows();
		this.tblMain.removeRows();
		/*
		 * 
		 * 此处为计数器,当特性指标为空时,则不会进入 否则新增指标表头列大于0,逐个删除
		 */
		if (headLength > 0) {
			for (int i = 0; i < headLength; i++) {
				this.MaterialInfo.removeColumn(0);
			}
		}
		headLength = -1;

	}

	public void refreahAfterAuditAndUnAudit(ActionEvent e) throws EASBizException, BOSException {
		int tblIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		/*
		 * 如果为选中报价信息表中的一行，就不做任何动作
		 */
		if (MaterialInfo.getRowCount() == 0 && !checkIsAddNew) {
			this.tblMain.getSelectManager().select(tblIndex, tblIndex);
			// MsgBox.showWarning("请选中材料报价信息表中一行！");
			return;
		}
		builderMaterialTable();

		/*
		 * 如果ID不为空，则重新构建报价信息表
		 */
		if (materialInfoId != null) {
			this.builderMaterialInfoTable();
		}

		this.tblMain.getSelectManager().select(tblIndex, tblIndex);
	}

	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		this.showWindow(bl);
	}

	   /**
	 * 
	 * @description 弹出自己的界面 并且先加载过滤界面
	 * @author 杜仕超
	 * @createDate 2010-11-17
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	private void showWindow(boolean blo) throws UIException {
		if (blo) {
			UIContext uiContext = new UIContext(this);
			IUIFactory uiFactory;
			uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
			IUIWindow uiWindow = uiFactory.create(MaterialInfoQueryUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}

	/**
	 * 描述：<从材料明细单导入>
	 * 
	 * @author <luoxiaolong>
	 * @param <e>
	 * @return <null> 创建时间 <2010/11/18>
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
	public void actionImportDataFormMaterial_actionPerformed(ActionEvent e)
			throws Exception {
		
		/** 判断是否选中行 */
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceAnyRow"));
			return;
		}

		tblMaterial = this.MaterialInfo;
		/** 存入选中行id */
		rightUpTableSelectedRowId = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();

		/* 弹出材料明细单窗口 */
		MaterialInfoUI materialUI = (MaterialInfoUI) this;
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("materialUI", materialUI);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(PartAMaterialImportorListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();

		/* 刷新本页面 */
		this.refreahAfterAuditAndUnAudit(e);

	}

    /**
	 * 描述：<导出Excel文件>
	 * 
	 * @author <luoxiaolong>
	 * @param <e>
	 * @return <null> 创建时间 <2010/11/18>
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
	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {

		/* 得到选中的path */
		TreePath path = this.kDTree1.getSelectionPath();
		if (path == null) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceNode"));
			return ;
		}
		/* 根据path得到节点 */
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path.getLastPathComponent();

		// 得到树的对象
		MaterialGroupInfo materialGroupInfo = null;
		if (treenode != null && treenode.getUserObject() instanceof MaterialGroupInfo) {
			// 得到树的对象
			materialGroupInfo = (MaterialGroupInfo) treenode.getUserObject();
		}

		if (null == materialGroupInfo) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceNode"));
			return ;
		}

		/* 设置默认路径 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));

		/* 设置过虑器 */
		chooser.setFileFilter(new DataImportFilterType());

		/* 设置标题 */
		chooser.setDialogTitle(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ExportExcelModel"));

		/* 弹出对话框 */
		chooser.showSaveDialog(null);

		if (null == chooser.getSelectedFile()) {
			return;
		}

		/** 获得选取文件路径 */
		String filePath = chooser.getSelectedFile().getPath();

		/** 创建一个工作薄 */
		HSSFWorkbook wb = new HSSFWorkbook();

		/** 创建一张表 */
		HSSFSheet sheet = wb.createSheet();

		/** 实例化模板类 */
		CreateExcelModel model = new CreateExcelModel();

		/* 获得指标名称集合 */
		List fNames = getMaterialIndexName(materialGroupInfo.getId().toString());

		/* 调用方法,创建Excel模板 */
		model.createExcelMode(fNames, wb, sheet);

		try {
			/* 调用方法,输出Excel模板 */
			model.outputExcel(wb, filePath);
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ExportModelSuccess"));
		} catch (Exception ex) {
			/* 处理文件正在被使用的情况 */
			logger.error(ex.getMessage());
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportModeFailBecauseFileUsed"));
		}
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
	private List getMaterialIndexName(String materialGruopId) {

		/** 声明一个返回值变量 */
		List fNames = new ArrayList();
		Map filterMap = new HashMap();
		filterMap.put("FMaterialGroupID", materialGruopId);
		filterMap.put("FIsEnabled", new Integer(1));
		List list = new ArrayList();
		list.add("FName_l2");
		try {
			fNames = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, "T_MTR_MaterialIndex", list);
		} catch (Exception e) {
			handUIException(e);
		}
		return fNames;
	}

	/**
	 * 描述：<从Excel导入>
	 * 
	 * @author <luoxiaolong>
	 * @param <e>
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
    public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		/*
		 * 导入流程： 1、解析Excel，一行一行的解析Excel，解析完一行就做相应的验证、验证不通过就给出提示，并终止程序
		 * 验证所有行，如果都通过了，就返回一个List。
		 * 2、解析List、对每一行数据根据物料类型判断是否存在该特性指标，不存在就提示该特性指标不存在，并终止程序 3、向数据库里里插入数据
		 */

		//		/** 判断是否选中行 */
		//		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
		//			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseChoiceAnyRow"));
		//			return ;
		//		}
		
		getMaterialInfos = getMaterialAllInfos();

		/* 获得右下角表中的数据 */
		// importDataFormExcel();
		/* 新功能，新调用 */
		ImportDataFormExcel idfe = new ImportDataFormExcel();
		idfe.importData(this.MaterialInfo, this.tblMain, getMaterialInfos);

		/* 刷新一下页面 */
		this.refreahAfterAuditAndUnAudit(e);
	}

    /**
	 * 描述：<获得Excel文件并解析数据>
	 * 
	 * @author <luoxiaolong>
	 * @param <null>
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
    private void importDataFormExcel() {
		material_dataRepeat = new ArrayList();
		/** 清空集合 */
		supplierIsNullList = new ArrayList();
		/* 第一种方式，标准模板导入 */
		// DatataskCaller task = new DatataskCaller();
		// task.setParentComponent(this);
		// if (getImportParam() != null){
		// task.invoke(getImportParam(), DatataskMode.ImpMode,true,true);
		// }
		// actionRefresh_actionPerformed(e);

		/* 第二种方式，POI方式导入 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* 设置过虑器 */
		chooser.setFileFilter(new DataImportFilterType());
		/* 弹出对话框 */
		chooser.showDialog(null, EASResource.getString(MATERIALINFO_RESOURCEPATH, "importForExcel"));
		/* 判断是否选中文件 */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* 获得选取文件路径 */
		String filePath = chooser.getSelectedFile().getPath();
		/* 创建对Excel工作簿文件的引用 */
		HSSFWorkbook excelWork = null;
		HSSFSheet sheet = null;
		try {
			excelWork = new HSSFWorkbook(new FileInputStream(filePath));
			/** 创建对工作表的引用,按索引引用,在Excel文档中，第一张工作表的缺省索引是0 */
			sheet = excelWork.getSheetAt(0);
		} catch (Exception e) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseConfirmFileIsRight"));
			return;
		}
		/** 创建一个叠代器 */
		Iterator it = sheet.rowIterator();

		/** 声明一个数组，存入基础的列名 */
		String[] baseColumnName = { material_Type, material_Number, material_Name, material_Model, material_Unit, material_Price, material_QuoteTime,
				material_Supplier, material_ProjectName, material_ContractName, material_Validate };

		/** 声明一个空的集合，存入特性指标列名 */
		List otherColumnList = new ArrayList();

		/** 声明一个空的Map，存入特性指标 */
		Map otherColumnMap = new HashMap();

		/** 声明一个空集合，存入基础的列名，列以键值对的形式保存，键为列名，值为列号 */
		List baseColumnList = new ArrayList();

		/** 声明一个空的Map，存入基础的列 */
		Map baseColumnMap = new HashMap();

		/** 声明一个集合存入每一行的数据 */
		List oneRowDataList = new ArrayList();

		/** 声明一个集合，存入所有列的数据 */
		List getAllRowDataFormExcel = new ArrayList();

		/* 判断是否符合模板 */
		if (!judgeIsModel(sheet.rowIterator(), baseColumnName)) {
			return;
		}

		if (sheet.getLastRowNum() < 1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"));
			return;
		} else if (sheet.getLastRowNum() < 2) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "QuoteInformationNull"));
			return;
		}

		/* 循环读取每一行 */
		while (it.hasNext()) {
			/** 获得行对象 */
			HSSFRow row = (HSSFRow) it.next();
			/* 不解析第一行 */
			if (row.getRowNum() < 1) {
				continue;
			}
			/** 声明一个列对象 */
			HSSFCell cell = null;
			Map rowMap = new HashMap();
			for (int i = 0; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i);
				String cellValue = null;

				if (null != cell) {
					/* 输出单元内容，cell.getStringCellValue()就是取所在单元的值 */
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						cellValue = cell.getStringCellValue();
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							double d = cell.getNumericCellValue();
							DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
							cellValue = format.format(HSSFDateUtil.getJavaDate(d));
						} else {
							// cellValue =
							// String.valueOf(cell.getNumericCellValue());
							DecimalFormat df = new DecimalFormat("###000.0000000000000000");
							cellValue = df.format(cell.getNumericCellValue());
							if (cellValue.indexOf(".") != -1) {
								String m = cellValue.substring(cellValue.indexOf("."));
								if (Double.parseDouble(m) == 0) {
									cellValue = cellValue.substring(0, m.indexOf("0") + cellValue.indexOf(".") - 1);
								} else {
									cellValue = cellValue.substring(0, cellValue.indexOf(".") + 7);
								}
							}
						}
					} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
						cellValue = String.valueOf(cell.getBooleanCellValue());
					} else {
						cellValue = "";
					}
				} else {
					cellValue = "";
				}
				if (cellValue.indexOf(",") != -1) {
					FDCMsgBox.showInfo("导入数据失败！在导入的数据中不能含有英文逗号！");
					return;
				}
				int m = -1;
				/* 解析第二行的列 */
				if (row.getRowNum() == 1) {
					for (int j = 0; j < baseColumnName.length; j++) {
						/* 判断是否为基础列，并记录是那一列 */
						if (cellValue.equals(baseColumnName[j])) {
							baseColumnMap.put("base_" + i, baseColumnName[j]);
							baseColumnList.add(baseColumnMap);
							m = 1;
							break;
						}
					}
					if (cellValue != null && !"".equals(cellValue) && m == -1) {
						otherColumnMap.put("other_" + i, cellValue);
						otherColumnList.add(otherColumnMap);
					}
				}

				/* 解析>2行的列 */
				if (row.getRowNum() > 1) {
					Map thisRow = null;
					String key = null;
					int keyIndex = -1;

					int n = -1;
					/* 循环基础列 */
					for (int j = 0; j < baseColumnList.size(); j++) {
						thisRow = (Map) baseColumnList.get(j);
						key = thisRow.keySet().toArray()[j].toString();
						keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
						if (keyIndex == i) {
							/* 以键值对的形式保存值 */
							rowMap.put(thisRow.get(key), cellValue);
							n = 1;
							break;
						}
					}

					if (n == -1) {
						/* 循环特性指标列 */
						for (int j = 0; j < otherColumnList.size(); j++) {
							thisRow = (Map) otherColumnList.get(j);
							key = thisRow.keySet().toArray()[j].toString();
							keyIndex = Integer.parseInt(key.substring(key.indexOf("_") + 1));
							if (keyIndex == i && cellValue != null) {
								String tempCellValue = cellValue;
								try {
									cellValue = String.valueOf(Double.parseDouble(cellValue));
									cellValue = cellValue.substring(0, cellValue.indexOf("."));
								} catch (Exception ex) {
									cellValue = tempCellValue;
								}
								/* 以键值对的形式保存值 */
								rowMap.put(thisRow.get(key), cellValue);
								break;
							}
						}
					}
				}

			}

					if (row.getRowNum() > 1) {
				/* 将这一行解析的数据存入集合中 */
				oneRowDataList.add(rowMap);
			}

			/* 用户数据处理 */
			if (row.getRowNum() > 1) {

				int isExist = -1;
				List newList = oneRowDataList;
				for (int n = 0; n < newList.size(); n++) {
					Map map = (Map) newList.get(n);
					Object[] obj = map.values().toArray();
					for (int f = 0; f < obj.length; f++) {
						if (!"".equals(obj[f].toString().trim())) {
							isExist = 1;
							break;
						}
					}
				}

				if (isExist == 1) {

					/* 验证该行数据是否与已有表中数据重复 */
					if (!validateDataIsRepeat(oneRowDataList, row.getRowNum(), baseColumnName)) {
						return;
					}

					/* 验证通过后将这一列数据存入集合中 */
					getAllRowDataFormExcel.add(oneRowDataList);
					/* 清空集合 */
					oneRowDataList = new ArrayList();
				}// else{
				// MsgBox.showInfo("导入失败！在Excel表的第" + (row.getRowNum() + 1) +
				// "行，为空行！");
				// return ;
				// }
			}
		}

		/* 供应商为空或不存在 */
		if (supplierIsNullList.size() > 0) {
			StringBuffer isNull = new StringBuffer();
			isNull.append(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel"));
			for (int k = 0; k < supplierIsNullList.size(); k++) {
				if (k == (supplierIsNullList.size() - 1)) {
					isNull.append(supplierIsNullList.get(k).toString());
				} else {
					isNull.append(supplierIsNullList.get(k).toString() + "、");
				}
			}
			MsgBox.showInfo("导入数据失败！" + isNull + "行，供应商为空或不存在！");
			return ;
		}
		
		/* 数据重复 */
		if (material_dataRepeat.size() > 0) {
			StringBuffer isNull = new StringBuffer();
			isNull.append(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel"));
			for (int k = 0; k < material_dataRepeat.size(); k++) {
				if (k == (material_dataRepeat.size() - 1)) {
					isNull.append(material_dataRepeat.get(k).toString());
				} else {
					isNull.append(material_dataRepeat.get(k).toString() + "、");
				}
			}
			if (this.MaterialInfo.getRowCount() == 0) {
				MsgBox.showInfo("导入数据失败！" + isNull + "行数据数据与Excel表前面行数据重复！");
			} else {
				MsgBox.showInfo("导入数据失败！" + isNull + "行数据数据重复！");
			}
			return;
		}
		
		/* 向数据库中插入数据 */
		if (addImportData(getAllRowDataFormExcel, baseColumnName) >= 1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataSuccess"));
			return;
		} else {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "OperateFail"));
			return ;
		}
		
    }

	/**
	 * 描述：<判断模板是否正确>
	 * 
	 * @author <luoxiaolong>
	 * @param <it,baseColumnName>
	 * @return <boolean> 创建时间 <2010/11/16>
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
	public boolean judgeIsModel(Iterator it, String[] baseColumnName) {

		/** 声明一个变量，存入Excel表中有多少行数据 */
		int rowCount = -1;

		while (it.hasNext()) {
			/** 获得行对象 */
			HSSFRow row = (HSSFRow) it.next();

			/* 解析 “列名” 行 */
			if (row.getRowNum() == 1) {
				++rowCount;
				/* 循环判断在“列名”行里是否包含合部的基础列，如果是则符合模板，反之则不符合 */
				for (int i = 0; i < baseColumnName.length; i++) {
					int m = -1;
					for (int j = 0; j < row.getLastCellNum(); j++) {
						HSSFCell cell = row.getCell(j);
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING && baseColumnName[i].equals(cell.getStringCellValue())) {
							m = 1;
							break;
						}
					}
					/* 不存在基础列时 */
					if (m == -1) {
						MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MustHave") + baseColumnName[i]
								+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Column"));
						return false;
					}
				}
				break;
			}
		}

		/* 判断如果在Excel表中没有数据 */
		if (rowCount == -1) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ModelError"));
			return false;
		}

		return true;
	}

	/**
	 * 描述：<向数据库里导入数据>
	 * 
	 * @author <luoxiaolong>
	 * @param <allDataRowExcel,baseColumnName>
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
    public int addImportData(List allDataRowExcel, String[] baseColumnName) {
    	
    	
    	/** 声明一个返回值变量 */
		int result = 0;

		/* 存入从Excel解析出来的所有行 */
		List allDataRow = allDataRowExcel;
    	
    	/* 根据供应商查询出，供应商所对应的最新报价时间 */
		List getLastestQuoteTime = getLastestQuoteTime();
    	
    	/* 循环插入所有数据行至数据库中 */
		for (int i = 0; i < allDataRow.size(); i++) {
			if (null == allDataRow.get(i)) {
				continue;
			}
			List row = (List) allDataRow.get(i);

			/* 解析每一行数据 */
			for (int j = 0; j < row.size(); j++) {

				Map rowMap = (Map) row.get(j);

				/** 设置有郊日期为一个极限时间 */
				Timestamp terminalDate = Timestamp.valueOf("2050-12-31 00:00:00");

				/** 设置有效无效 0无效 1有效 */
				int isValidDate = 0;
				if (terminalDate.getTime() > new java.util.Date().getTime()) {
					isValidDate = 1;
				}

				/* 声明一个集合，存入每一行的数据 */
				List params = new ArrayList();

				/** 取得主键ID */
				String bosId = getBOSID("9D390CBB");
				params.add(bosId);

				/* 存入materialId */
				// String fMaterialID =
				// this.tblMain.getRow(this.tblMain.getSelectManager
				// ().getActiveRowIndex()).getCell("id").getValue().toString();
				Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
				/* 根据物料名称和编码确定materialId */
				String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
				String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
				if (tDouble.matcher(rowMap.get(material_Number).toString().trim()).matches()) {
					materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
				}
				if (tDouble.matcher(rowMap.get(material_Name).toString().trim()).matches()) {
					materialName = materialName.substring(0, materialName.indexOf("."));
				}
				Map materialMap = new HashMap();
				materialMap.put("FName_l2", materialName);
				materialMap.put("FNumber", materialNumber);
				String fMaterialID = getMaterialIDByFNameAndFNumber("T_BD_Material", "FID", materialMap);
				params.add(fMaterialID);

				/* 操作“项目名称”一列 */
				String projName = String.valueOf(rowMap.get(material_ProjectName) == null ? "" : rowMap.get(material_ProjectName).toString());
				Map projMap = new HashMap();
				projMap.put("FName_l2", projName);
				/* 根据项目名称查询项目id */
				String projId = this.getIdByName("T_FDC_CurProject", "FID", projMap);
				if (projId != null) {
					params.add(projId);
				} else {
					params.add("");
				}

				/* 操作“合同名称”一列 */
				String conName = String.valueOf(rowMap.get(material_ContractName) == null ? "" : rowMap.get(material_ContractName).toString());
				Map conMap = new HashMap();
				conMap.put("FName", conName);
				/* 根据合同名称查询项目id */
				String conId = this.getIdByName("T_CON_ContractBill", "FID", conMap);
				if (conId != null) {
					params.add(conId);
				} else {
					params.add("");
				}

				BigDecimal price = new BigDecimal(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());
				params.add(price);
				String quoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? "2010-11-11" : rowMap.get(material_QuoteTime).toString());
				params.add(quoteTime);

				/* 操作“供应商”一列 */
				String supplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
				Map supMap = new HashMap();
				supMap.put("FName_l2", supplier);
				/* 根据供应商名称查询供应商id */
				String supId = this.getIdByName("T_BD_Supplier", "FID", supMap);
				if (supId != null) {
					params.add(supId);
				} else {
					params.add("");
				}

				/* 存入创建人ID */
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				params.add(user.getId().toString());

				/* 存入创建时间 */
				Date createTime = null;
				try {
					createTime = FDCDateHelper.getServerTimeStamp();
				} catch (BOSException e1) {
					createTime = new Date();
				}
				params.add(new SimpleDateFormat("yyyy-MM-dd").format(createTime));

				/* 存入有效期 */
				String validate = String
						.valueOf((rowMap.get(material_Validate) == null || "".equals(rowMap.get(material_Validate).toString().trim())) ? "2050-11-11"
								: rowMap.get(material_Validate).toString().trim());
				params.add(validate);

				/* 存入是否是最新 1最新 0不是最新 */
				int isLastestQutote = 1;
				for (int k = 0; k < getLastestQuoteTime.size(); k++) {
					Map map = (Map) getLastestQuoteTime.get(k);
					String fMaxDate = String.valueOf(map.get("FMaxDate") == null ? "1990-1-1" : map.get("FMaxDate").toString());
					String fSupplierID = String.valueOf(map.get("FSupplierID") == null ? "" : map.get("FSupplierID").toString());
					if (supId.equals(fSupplierID)) {
						if (fMaxDate.indexOf(" ") != -1) {
							fMaxDate = fMaxDate.substring(0, fMaxDate.indexOf(" "));
						}
						if (quoteTime.indexOf(" ") != -1) {
							quoteTime = quoteTime.substring(0, quoteTime.indexOf(" "));
						}
						if (Timestamp.valueOf(fMaxDate + " 00:00:00").getTime() > Timestamp.valueOf(quoteTime + " 00:00:00").getTime()) {
							isLastestQutote = 0;
						}
					}
				}
				params.add(new Integer(isLastestQutote));

				/* 存入是否启用 1启用 0禁用 */
				params.add(new Integer(1));
				String fName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
				params.add(fName);
				/* 存入状态 */
				params.add("1SAVED");
				/* 存入有效无效 */
				params.add(String.valueOf(isValidDate == 1 ? "VALID" : "INVALID"));

				/* 存入控制单元 */
				String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString().trim();
				params.add(cuId);

				/* 存入组织单元 */
				String orgUnitId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString().trim();
				params.add(orgUnitId);

				/* 存入 (从Excel取出的)特性指标列 */
				List fNamesExcel = new ArrayList();

				/* 判断是不是基础列，如果不是基础列，那就是特性指标列 */
				Set set = rowMap.keySet();
				Iterator iColumn = set.iterator();
				while (iColumn.hasNext()) {
					int k = -1;
					String columnName = iColumn.next().toString().trim();
					for (int m = 0; m < baseColumnName.length; m++) {
						if (columnName.equals(baseColumnName[m])) {
							k = 1;
						}
					}
					if (k == -1) {
						fNamesExcel.add(columnName);
					}
				}

				try {
					/* 调用服务器端Dao执行SQL */
					MaterialInfoFactory.getRemoteInstance().addMaterialData(params, "importMaterialInfoSql");

					/* 根据物料类别查询该物料是属于哪个种类 */
					String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
					Map materialGroupMap = new HashMap();
					materialGroupMap.put("FName_l2", excelMaterialType);
					String materialGroupId = getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);

					List materialIndexs = this.getMaterialIndexIdByMaterialId(fMaterialID);

					/* 循环插入指标值 */
					for (int k = 0; k < materialIndexs.size(); k++) {
						Map allMaterialIndexMap = (Map) materialIndexs.get(k);
						String allMaterialIndex = allMaterialIndexMap.get("FID").toString();
						List newParams = new ArrayList();
						newParams.add(getBOSID("6E5BD60C"));
						newParams.add(bosId);

						/* materialIndexId */
						String materialIndexId = "";

						/* 指标值 */
						String indexValue = "";

						for (int m = 0; m < fNamesExcel.size(); m++) {
							String fNameExcel = fNamesExcel.get(m).toString().trim();
							Map filterMap = new HashMap();
							filterMap.put("FName_l2", fNameExcel);
							filterMap.put("FMaterialGroupID", materialGroupId);

							/* 根据materialGroupId和fName名称，查询materialIndexId */
							Map materialIndexMap = new HashMap();
							materialIndexMap.put("FMaterialGroupID", materialGroupId);
							materialIndexMap.put("FName_l2", fNameExcel);

							materialIndexId = getMaterialIndexIdByMaterialGroupIdAndIndexName("T_MTR_MaterialIndex", "FID", materialIndexMap);

							if (materialGroupId == null) {
								MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "IndexName") + fNameExcel
										+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "NotExistInExcel") + (j + 1)
										+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Row"));
								return -1;
							}

							if (materialIndexId.equals(allMaterialIndex)) {
								indexValue = String.valueOf(rowMap.get(fNamesExcel.get(m).toString().trim()) == null ? "" : rowMap.get(
										fNamesExcel.get(m).toString().trim()).toString());
								break;
							}
						}

						/* materialIndexId */
						newParams.add(allMaterialIndex);

						/* 插入指标值 */
						newParams.add(indexValue);

						// /*循环删除指标*/
						// int n = MaterialInfoFactory.getRemoteInstance().
						// deleteMaterialIndexValue(fMaterialID,
						// materialIndexId);

						/* 循环增加指标 */
						MaterialInfoFactory.getRemoteInstance().addMaterialData(newParams, "importMaterialIndexInfoSql");
					}

					++result;
				} catch (Exception e) {
					handUIException(e);
					logger.error(e.getMessage());
					/* 此时导入失败,不调用handUIException(e)处理异常,在后面给出提示“导入失败” */
					--result;
				}
			}
		}
		return result;
    }

	/**
	 * 描述：<根据物料id，获得特性指标id>
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
	private List getMaterialIndexIdByMaterialId(String materialId) {
		Map filterMap = new HashMap();
		filterMap.put("FID", materialId);
		String materialGroupId = getMaterialGroupIdByMaterialId(filterMap, "T_BD_Material", "FMaterialGroupID");
		return getMaterialIndexId(materialGroupId);
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
	private List getMaterialIndexId(String materialGruopId) {

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
	 * 描述：<根据物料id，获得物料类型>
	 * 
	 * @param <materialId>
	 * @return <String>
	 * @throws <>
	 */
	public String getMaterialGroupIdByMaterialId(Map filterMap, String tableName, String fieldName) {
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		String materialGroupId = null;
		try {
			List materialGroupList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if (null != materialGroupList && materialGroupList.size() > 0) {
				Map materialGroupMap = (Map) materialGroupList.get(0);
				materialGroupId = materialGroupMap.get(fieldName).toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
		return materialGroupId;
	}

	/**
	 * 描述：</*根据materialGroupId和fName名称，查询materialIndexId*>
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
    public String getMaterialIndexIdByMaterialGroupIdAndIndexName(String tableName, String fieldName, Map filters) {
		String result = null;
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		try {
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, fieldList);
			if (null != list && list.size() > 0) {
				Map map = (Map) list.get(0);
				if (null != map.get(fieldName)) {
					result = map.get(fieldName).toString();
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return result;
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
	private List getLastestQuoteTime() {
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
	 * 描述：<根据名称，获得id>
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
	public String getIdByName(String tableName, String fieldName, Map filters) {
		String result = null;
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
		try {
			List list = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, fieldList);
			if (null != list && list.size() > 0) {
				Map map = (Map) list.get(0);
				if (null != map.get(fieldName)) {
					result = map.get(fieldName).toString();
				}
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return result;
	}

	/**
	 * 描述：<根据指标名称，获得id>
	 * 
	 * @author <luoxiaolong>
	 * @param <filters,tableName,fieldName>
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
	private String getMaterialIndexId(Map filters, String tableName, String fieldName) {
		String materialIndexId = null;
		List list = new ArrayList();
		list.add(fieldName);
		List materialIndexList;
		try {
			materialIndexList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, list);
			if (null != materialIndexList && materialIndexList.size() > 0) {
				Map materialMap = (Map) materialIndexList.get(0);
				materialIndexId = materialMap.get(fieldName).toString();
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return materialIndexId;
	}

	/**
	 * 描述：<根据bosType，获得BOSID>
	 * 
	 * @author <luoxiaolong>
	 * @param <bosType>
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
	public String getBOSID(String bosType) {

		/** 声明一个返回值变量 */
		String bosID = null;

		try {
			bosID = MaterialInfoFactory.getRemoteInstance().getBOSID(bosType);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}

		return bosID;

	}

	/**
	 * 描述：<验证数据是否重复>
	 * 
	 * @author <luoxiaolong>
	 * @param <oneRowDataList 从Excel解析出来的一行数据，rowNum 解析Excel到几行了，baseColumnName
	 *        存基础列的数组>
	 * @return <true 验证通过，false 验证失败> 创建时间 <2010/11/16>
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
	private boolean validateDataIsRepeat(List oneRowDataList, int rowNum, String[] baseColumnName) {

		Map rowMap = (Map) oneRowDataList.get(0);

		/** 声明一个数组，存入必录列的列名 */
		String[] notNullColumnNames = { material_Type, material_Number, material_Name, material_Model, material_Unit, material_Price,
				material_QuoteTime, material_Supplier };

		/* 判断是必录项是否已经录入， */
		for (int i = 0; i < notNullColumnNames.length; i++) {
			if (null == rowMap.get(notNullColumnNames[i]) || "".equals(rowMap.get(notNullColumnNames[i]).toString())) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Row2") + notNullColumnNames[i]
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ColumnIsNotNull"));
				return false;
			}
		}

		/** 声明一个数组，存入字符串长度需要限制的列 */
		String[] limitCharacterLengths = { material_Number, material_Name, material_Model, material_Unit };
		for (int i = 0; i < limitCharacterLengths.length; i++) {
			if (null == rowMap.get(limitCharacterLengths[i]) && rowMap.get(limitCharacterLengths[i]).toString().trim().length() > 80) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + limitCharacterLengths[i]
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "TooLength"));
				return false;
			}
		}

		/**
		 * 在这里要做三个验证、 第一、判断是物料类型是否相同、名称、编码两列是否在数据库中存在 第二、（单价、报价时间、供应商）三字段值是否重复
		 * 第三、根据物料类别判断是否存在该指标
		 */
		Pattern pDate = Pattern.compile("[1-9][0-9]{3}\\-((0[0-9]|1[0-2])|[1-9])\\-((0[0-9]|1[0-9]|2[0-9]|3[0-1])|[1-9])");
		Pattern pDouble = Pattern.compile("([0-9]{1,21})|([0-9]{1,21}\\.[0-9]{0,100})");
		Pattern pIsNumber = Pattern.compile("([0-9]{1,1000})|([0-9]{1,1000}\\.[0-9]{0,1000})");

		double excelPrice = Double.parseDouble(String.valueOf(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString()));
		String excelPriceString = String.valueOf(rowMap.get(material_Price) == null ? "0" : rowMap.get(material_Price).toString());

		if (!(pIsNumber.matcher(excelPriceString).matches())) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaceInputNumberType"));
			return false;
		}// else{
		// /*解析集合，获得单价、报价时间、供应商、物料分类、以及特性指标的值，并验证格式*/
		// if(rowMap.get(material_Price).toString().length() > 13){
		// MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH,
		// "ImportDataFail") +
		// EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") +
		// (rowNum + 1) + "行，输入的数值过大！");
		// return false;
		// }
		// }

		/* 解析集合，获得单价、报价时间、供应商、物料分类、以及特性指标的值，并验证格式 */
		if (excelPriceString.indexOf(".") != -1) {
			if (excelPriceString.substring(0, excelPriceString.indexOf(".")).toString().length() > 12) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PriceTooLength"));
				return false;
			}
		} else {
			if (excelPriceString.length() > 12) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PriceTooLength"));
				return false;
			}
		}

		if (!(pDouble.matcher(excelPriceString).matches())) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaceInputNumberType"));
			return false;
		}

		String excelQuoteTime = String.valueOf(rowMap.get(material_QuoteTime) == null ? (new java.util.Date()).toLocaleString() : rowMap.get(
				material_QuoteTime).toString());

		/* 判断日期是否则符合格式标准 */
		if (!pDate.matcher(excelQuoteTime).matches()) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseInputDateType"));
			return false;
		}

		/* 判断报价日期是否合法 */
		if (!validate(excelQuoteTime)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "QuoteTimeError"));
			return false;
		}

		if (excelQuoteTime.indexOf(" ") != -1) {
			excelQuoteTime = excelQuoteTime.substring(0, excelQuoteTime.indexOf(" "));
		}

		String excelSupplier = String.valueOf(rowMap.get(material_Supplier) == null ? "" : rowMap.get(material_Supplier).toString());
		Map supMap = new HashMap();
		supMap.put("FName_l2", excelSupplier);
		/* 根据供应名称查询项目id */
		String supId = this.getIdByName("T_BD_Supplier", "FID", supMap);
		supId = String.valueOf(supId == null ? "" : supId.toString().trim());

		String validate = String.valueOf((rowMap.get(material_Validate) == null || "2050-10-1"
				.equals(rowMap.get(material_Validate).toString().trim())) ? "2050-11-11" : rowMap.get(material_Validate).toString().trim());

		validate = String.valueOf("".equals(validate) ? "2050-10-1" : validate);

		/* 判断日期是否则符合格式标准 */
		if (!pDate.matcher(validate).matches()) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "PleaseInputDateType2"));
			return false;
		}

		/* 判断有效日期是否合法 */
		if (null != validate && !"".equals(validate) && !validate(validate)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ValidateError"));
			return false;
		}

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long excelTimeL = format.parse(excelQuoteTime).getTime();
			long validateL = format.parse(validate).getTime();

			/* 有效日期不能小于报价时间 */
			if (excelTimeL > validateL) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "TimeError"));
				return false;
			}
		} catch (Exception ex) {
			handUIException(ex);
		}

		/* 根据物料类别查询该物料是属于哪个种类 */
		String excelMaterialType = String.valueOf(rowMap.get(material_Type) == null ? "" : rowMap.get(material_Type).toString());
		Map materialGroupMap = new HashMap();
		materialGroupMap.put("FName_l2", excelMaterialType);
		String excelMaterialGroupId = getIdByName("T_BD_MaterialGroup", "FID", materialGroupMap);
		excelMaterialGroupId = String.valueOf(excelMaterialGroupId == null ? "" : excelMaterialGroupId.trim());

		Pattern tDouble = Pattern.compile("([0-9]{1,17}\\.0)");
		/* 根据物料名称和编码确定materialId */
		String materialName = String.valueOf(rowMap.get(material_Name) == null ? "" : rowMap.get(material_Name).toString());
		String materialNumber = String.valueOf(rowMap.get(material_Number) == null ? "" : rowMap.get(material_Number).toString());
		if (tDouble.matcher(rowMap.get(material_Number).toString().trim()).matches()) {
			materialNumber = materialNumber.substring(0, materialNumber.indexOf("."));
		}
		if (tDouble.matcher(rowMap.get(material_Name).toString().trim()).matches()) {
			materialName = materialName.substring(0, materialName.indexOf("."));
		}
		
		Map materialMap = new HashMap();
		materialMap.put("FName_l2", materialName.trim());
		materialMap.put("FNumber", materialNumber.trim());
		materialMap.put("FMaterialGroupID", excelMaterialGroupId);
		String materialId = getMaterialIDByFNameAndFNumber("T_BD_Material", "FID", materialMap);

		/* 存入 (从Excel取出的)特性指标列 */
		List fNamesExcel = new ArrayList();

		/* 判断是不是基础列，如果不是基础列，那就是特性指标列 */
		Set set = rowMap.keySet();
		Iterator iColumn = set.iterator();
		while (iColumn.hasNext()) {
			String columnName = iColumn.next().toString().trim();
			int m = -1;
			for (int i = 0; i < baseColumnName.length; i++) {
				if (columnName.equals(baseColumnName[i])) {
					m = 1;
					break;
				}
			}
			if (m == -1) {
				fNamesExcel.add(columnName);
			}
		}

		/* 第一步、判断是物料类型是否相同 */
		if (null == excelMaterialGroupId || "".equals(excelMaterialGroupId)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum - 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialTypeNotExist"));
			return false;
		}
		if (null == materialId) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "NameAndNumberIsNotExist"));
			return false;
		}
    	
    	String parentMaterialID = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		Map mapMaterial = new HashMap();
		mapMaterial.put("FID", parentMaterialID);
		String parentMaterialGroupID = this.getIdByName("T_BD_Material", "FMaterialGroupID", mapMaterial);
		if (!excelMaterialGroupId.equals(parentMaterialGroupID)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum - 1)
					+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialTypeDiferent"));
			return false;
		}

		/* 验证数据重复（一）获得右下角表中的数据 */
		for (int i = 0; i < this.MaterialInfo.getRowCount(); i++) {
			IRow row = this.MaterialInfo.getRow(i);
			double parentPrice = Double.parseDouble(row.getCell("price").getValue().toString());
			String parentQuoteTime = row.getCell("quoteTime").getValue().toString();
			if (parentQuoteTime.indexOf(" ") != -1) {
				parentQuoteTime = parentQuoteTime.substring(0, parentQuoteTime.indexOf(" "));
			}
			String parentSupplier = String.valueOf(row.getCell("supplier").getValue() == null ? "" : row.getCell("supplier").getValue().toString());

			/* 第二步，验证数据是否重复 */
			if (excelPrice == parentPrice && excelQuoteTime.equals(parentQuoteTime) && excelSupplier.equals(parentSupplier)) {
				int a = -1;
				for (int k = 0; k < material_dataRepeat.size(); k++) {
					if (null == material_dataRepeat.get(k))
						continue;
					int sub = Integer.parseInt(material_dataRepeat.get(k).toString());
					if (rowNum + 1 == sub) {
						a = 1;
					}
				}
				if (a == -1) {
					material_dataRepeat.add(new Integer(rowNum + 1));
				}
			}
		}

		/* 验证数据重复（二） */
		/* 循环判断(二) */
		for (int j = 0; j < getMaterialInfos.size(); j++) {
			Map materialInfo = (Map) getMaterialInfos.get(j);
			double pPrice = Double.parseDouble(String
					.valueOf(materialInfo.get("FPrice") == null ? "0" : materialInfo.get("FPrice").toString().trim()));
			String pQuoteTime = String.valueOf(materialInfo.get("FQuoteTime") == null ? new java.util.Date().toLocaleString() : materialInfo.get(
					"FQuoteTime").toString().trim());
			if (pQuoteTime.indexOf(" ") != -1) {
				pQuoteTime = pQuoteTime.substring(0, pQuoteTime.indexOf(" "));
			}
			String fMaterialID = String.valueOf(materialInfo.get("FMaterialID") == null ? "" : materialInfo.get("FMaterialID").toString().trim());
			String fName = String.valueOf(materialInfo.get("FSupplierID") == null ? "" : materialInfo.get("FSupplierID").toString().trim());

			/* 判断单价、报价时间、供应商是否相同 */
			if (materialId.equals(fMaterialID) && pPrice == excelPrice && pQuoteTime.equals(excelQuoteTime) && fName.equals(supId)) {
				if ("".equals(excelSupplier) || "".equals(supId)) {
					int a = -1;
					for (int k = 0; k < supplierIsNullList.size(); k++) {
						if (null == supplierIsNullList.get(k))
							continue;
						int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
						if (rowNum + 1 == sub) {
							a = 1;
						}
					}
					if (a == -1) {
						supplierIsNullList.add(new Integer(rowNum + 1));
					}
				} else {
					int a = -1;
					for (int k = 0; k < material_dataRepeat.size(); k++) {
						if (null == material_dataRepeat.get(k))
							continue;
						int sub = Integer.parseInt(material_dataRepeat.get(k).toString());
						if (rowNum + 1 == sub) {
							a = 1;
						}
					}
					if (a == -1) {
						material_dataRepeat.add(new Integer(rowNum + 1));
					}
				}
			} else if ("".equals(excelSupplier) || "".equals(supId)) {
				int a = -1;
				for (int k = 0; k < supplierIsNullList.size(); k++) {
					if (null == supplierIsNullList.get(k))
						continue;
					int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
					if (rowNum + 1 == sub) {
						a = 1;
					}
				}
				if (a == -1) {
					supplierIsNullList.add(new Integer(rowNum + 1));
				}
			}
		}
		
		/* 验证数据重复（三） */
		if (this.MaterialInfo.getRowCount() == 0) {
			/* 供应商为空 */
			if (null != supId && "".equals(supId)) {
				int a = -1;
				for (int k = 0; k < supplierIsNullList.size(); k++) {
					if (null == supplierIsNullList.get(k))
						continue;
					int sub = Integer.parseInt(supplierIsNullList.get(k).toString());
					if (rowNum + 1 == sub) {
						a = 1;
					}
				}
				if (a == -1) {
					supplierIsNullList.add(new Integer(rowNum + 1));
				}
			} else {// 数据重复

			}
		}
		
		Map newMaterialInfoMap = new HashMap();
		newMaterialInfoMap.put("FPrice", String.valueOf(excelPrice));
		newMaterialInfoMap.put("FQuoteTime", excelQuoteTime);
		newMaterialInfoMap.put("FMaterialID", materialId);
		newMaterialInfoMap.put("FSupplierID", supId);
		getMaterialInfos.add(newMaterialInfoMap);

		/* 第三步，验证Excel数据表中的特性指标是否存在 */

		/* 查出该物料分类下有哪些指标 */
		Map map = new HashMap();
		map.put("FMaterialGroupID", excelMaterialGroupId);
		List fParentNames = getMaterialIndexFNameByMaterialGroup("T_MTR_MaterialIndex", "FName_l2", map);
		for (int i = 0; i < fNamesExcel.size(); i++) {
			String fName = fNamesExcel.get(0).toString();
			int m = -1;
			for (int j = 0; j < fParentNames.size(); j++) {
				Map fNameMap = (Map) fParentNames.get(j);
				if (fName.equals(fNameMap.get("FName_l2").toString()))
					m = 1;
			}
			if (m == -1) {
				MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "ImportDataFail")
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "InExcel") + (rowNum + 1)
						+ EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "MaterialIndexNotExist"));
				return false;
			}
		}
		return true;
    }

	/**
	 * 描述：<判断日期>
	 * 
	 * @param <>
	 * @return <String>
	 * @throws <>
	 */
	public static boolean validate(String dateString) {

		Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
		Matcher m = p.matcher(dateString);
		if (!m.matches()) {
			return false;
		} // 得到年月日
		String[] array = dateString.split("-");
		int year = Integer.parseInt(array[0]);
		int month = Integer.parseInt(array[1]);
		int day = Integer.parseInt(array[2]);
		if (month < 1 || month > 12) {
			return false;
		}
		int[] monthLengths = new int[] { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(year)) {
			monthLengths[2] = 29;
		} else {
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if (day < 1 || day > monthLength) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是闰年
	 */
	private static boolean isLeapYear(int year) {
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
	}

	/**
	 * 描述：<根据物料ID查询单价、报价时间、供应商>
	 * 
	 * @param <>
	 * @return <String>
	 * @throws <>
	 */
	public List getMaterialAllInfos() {
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
			handUIExceptionAndAbort(e);
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
    public String getMaterialIDByFNameAndFNumber(String tableName, String fieldName, Map filterMap) {
		List fieldList = new ArrayList();
		fieldList.add(fieldName);

		/** 存入materialId */
		String materialId = null;

		try {
			List materialList = MaterialInfoFactory.getRemoteInstance().getMaterialData(filterMap, tableName, fieldList);
			if (null != materialList && materialList.size() > 0) {
				Map materialMap = (Map) materialList.get(0);
				materialId = materialMap.get(fieldName).toString();
			}
		} catch (Exception e) {
			handUIException(e);
		}
		return materialId;
	}

	/**
	 * 描述：<根据物料类别，查询该类别下有哪些指标>
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
    public List getMaterialIndexFNameByMaterialGroup(String tableName, String fieldName, Map filters) {
		List fieldList = new ArrayList();
		fieldList.add(fieldName);
    	
    	/** 存入指标 */
		List indexName = new ArrayList();
    	
    	try {
			indexName = MaterialInfoFactory.getRemoteInstance().getMaterialData(filters, tableName, fieldList);
		} catch (Exception e) {
			handUIException(e);
		}
		return indexName;

	}

	/**
	 * 描述：获得材料信息表的ID 创建时间：2010-11-22
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */

	private String getMaterialInfoId() {
		IRow row = null;
		String materialInfoId = "";
		if (MaterialInfo.getRowCount() != 0 && MaterialInfo.getSelectManager().size() != 0) {
			row = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex());
			if (row.getCell("id") != null) {
				materialInfoId = row.getCell("id").getValue().toString();
			}
		}
    	
    	return materialInfoId;
	}

	/**
	 * 描述：获得材料ID 创建时间：2010-11-22
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	String materialId = null;

	private String getMaterialId() {
    	
    	IRow row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		if (row != null) {
			materialId = row.getCell("id").getValue().toString();
		}
		return materialId;
	}

	/**
	 * 描述：审批事件 创建时间：2010-11-22
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	
    	materialInfoCheckSelected();
    	
    	/* 判断是否有状态 */
		if (null == this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
				|| "".equals(this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
						.toString().trim())) {
			MsgBox.showInfo("选中行没有状态！");
			return;
    	}
    	
    	
    	/* 判断选中行数据是的判断是否为有效状态 */
		String state = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue().toString();
		if (EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Nullity").equals(state)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Auditing"));
			return;
    	}
    	
    	String id = this.getMaterialInfoId();
		MaterialInfoInfo info = null;
		if (id != null && !id.equals("")) {
			info = (MaterialInfoInfo) MaterialInfoFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(id)));
			if (null == info.getState() || "".equals(info.getState().getValue())) {
				abort();
			}
		}
		if (id == null) {
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "importForExcel"));
		}
		if (info.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showWarning(this, EASResource.getString(MATERIALINFO_RESOURCEPATH, "auditState"));
		} else {
			MaterialInfoFactory.getRemoteInstance().audit(BOSUuid.read(id));
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "auditSuccessfully"));
			this.refreahAfterAuditAndUnAudit(e);
		}
	}

	/**
	 * 描述：反审批事件 创建时间：2010-11-22
	 * <p>
	 * 
	 * 修改人：
	 * <p>
	 * 修改时间：
	 * <p>
	 * 修改描述：
	 * <p>
	 * 
	 * @see
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	materialInfoCheckSelected();

		/* 判断选中行数据是的判断是否为有效状态 */
		/* 判断是否有状态 */
		if (null == this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
				|| "".equals(this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue()
						.toString().trim())) {
			MsgBox.showInfo("选中行没有状态！");
			return;
    	}
    	
    	String state = this.MaterialInfo.getRow(this.MaterialInfo.getSelectManager().getActiveRowIndex()).getCell("state").getValue().toString();
		if (EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "Nullity").equals(state)) {
			MsgBox.showInfo(EASResource.getString(MATERIALIMPORT_RESOURCEPATH, "UnAuditing"));
			return;
    	}
    	
    	String id = this.getMaterialInfoId();
		MaterialInfoInfo info = null;
		if (id != null && !id.equals("")) {
			info = (MaterialInfoInfo) MaterialInfoFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(id)));
			if (null == info.getState() || "".equals(info.getState().getValue())) {
				abort();
			}
		}
		if (id == null) {
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "failedGetData"));
		}
		if (!info.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showWarning(this, EASResource.getString(MATERIALINFO_RESOURCEPATH, "unAudit"));
		} else {
			MaterialInfoFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH, "unAuditSuc"));
			this.refreahAfterAuditAndUnAudit(e);
			
		}
    }   
    
	/**
	 * 
	 * @description 分布图
	 * @author 刘一珉
	 * @createDate 2010-11-5
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */

	public void actionShowDotsMap_actionPerformed(ActionEvent e)
	throws Exception {
		this.checkSelected();
		//得到选择行的行号
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		//得到选择行name（名称）列的值
		String materialName = (String)this.tblMain.getRow(rowIndex).getCell("name").getValue();
		
		XYDataset dataSet = MaterialInfoFactory.getRemoteInstance().getDotDataSet(materialName, materialInfoId);
		
		//得到选择行model（规格型号）列的值
		String materialModel = "单价：（元/" + this.tblMain.getRow(rowIndex).getCell("baseUnit.name").getValue().toString() + "）";
		
		try {
			MaterialScatterChartUI.showChart(this, dataSet,materialModel);
		} catch (UIException ue) {
			logger.error(ue.getMessage());
			handUIException(ue);
		}


	}

	/**
	 * 趋势图
	 * 
	 * @description
	 * @author 刘一珉
	 * @createDate 2010-11-5
	 * @param
	 * @return
	 * 
	 * @version EAS1.0
	 * @see
	 */
	public void actionShowLineMap_actionPerformed(ActionEvent e)
	throws Exception {
		this.checkSelected();
		
		//得到选择行的行号
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		//得到选择行name（名称）列的值
		String materialName = (String)this.tblMain.getRow(rowIndex).getCell("name").getValue();
		//得到选择行model（规格型号）列的值
		String materialModel = "单价：（元/" + this.tblMain.getRow(rowIndex).getCell("baseUnit.name").getValue().toString() + "）";
		XYDataset dataSet = MaterialInfoFactory.getRemoteInstance().getLineDataSet(materialName, materialInfoId);
		try {
			MaterialLineChartUI.showChart(this, dataSet,materialModel);
		} catch (UIException ue) {
			logger.error(ue.getMessage());
			handUIException(ue);
		}
	}
	/**
	 * 
	 * @description		显示所有按钮
	 * @author			杜仕超
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private void initEnabledTrue() {
		this.actionAddNew.setEnabled(true);
		this.actionView.setEnabled(true);
		this.actionEdit.setEnabled(true);
		this.actionRemove.setEnabled(true);
		this.actionRefresh.setEnabled(true);
		this.actionQuery.setEnabled(true);
		/*图形图标*/
		this.actionShowDotsMap.setEnabled(true);
		this.actionShowLineMap.setEnabled(true);
		/*导入导出图标     工具栏*/
		this.KDBtnExcelImport.setEnabled(true);
		this.KDExportExcel.setEnabled(true);
		this.KDBtnMaterialImport.setEnabled(true);
		/*导入导出图标     菜单栏*/
		this.MaterialImportItem.setEnabled(true);
		this.ExcelImportItem.setEnabled(true);
		this.ExportExcelItem.setEnabled(true);
		/*核准*/
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setEnabled(true);
	}
	/**
	 * 
	 * @description		灰掉所有按钮
	 * @author			杜仕超
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private void initEnabledFalse() {
		  
		/*特别灰按钮*/
		this.btnView.setEnabled(false);
		this.menuItemView.setEnabled(false);
		this.showLineMap.setEnabled(false);		
		this.showDotsMap.setEnabled(false);
		this.showDotsMapItem.setEnabled(false);
		this.showLineMapItem.setEnabled(false);
		/*灰掉原有的按钮*/
		this.actionAddNew.setEnabled(false);
		this.actionView.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionRefresh.setEnabled(false);
		this.actionQuery.setEnabled(false);
		/*图形图标*/
		this.actionShowDotsMap.setEnabled(false);
		this.actionShowLineMap.setEnabled(false);
		/*导入导出图标     工具栏*/
		this.KDBtnExcelImport.setEnabled(false);
		this.KDExportExcel.setEnabled(false);
		this.KDBtnMaterialImport.setEnabled(false);
		/*导入导出图标     菜单栏*/
		this.MaterialImportItem.setEnabled(false);
		this.ExcelImportItem.setEnabled(false);
		this.ExportExcelItem.setEnabled(false); 
		/*核准*/
		this.actionAudit.setEnabled(false);
		this.actionUnAudit.setEnabled(false);

	}

	/**
	 * 
	 * @description		判断是否是集团 CU
	 * @author			杜仕超
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private boolean isIsCU() {
		
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
			this.initEnabledTrue();
			return true;
		}else{
			this.initEnabledFalse();
			return false;
		}
			
	 
	}
	/**
	 * 
	 * @description		判断是否为采购组织  并 是否为实体/虚体
	 * @author			杜仕超
	 * @createDate		2010-12-2
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0	
	 * @see
	 */
	private boolean isIsPurchaseOrgUnit() {
		 /**判断是否为采购组织*/
		if(SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			/**是否为实体/虚体*/
			if(SysContext.getSysContext().getCurrentPurchaseUnit().isIsBizUnit()){
				this.initEnabledTrue();
				return true;
			}else{
				this.initEnabledFalse();
				return false;
			}
		}
		this.initEnabledFalse();
		return false;
	}
	public void onShow() {

		 
		this.showLineMap.setEnabled(true);
		this.showDotsMap.setEnabled(true);

		/* 设置从Excel导入按扭、从材料明细导入按扭的属性 */
		this.KDBtnExcelImport.setVisible(true);
		this.KDBtnMaterialImport.setVisible(true);
		this.KDExportExcel.setVisible(true);
		this.KDBtnExcelImport.setToolTipText("从Excel导入");
		
		/*打开条件查询按钮*/  
		this.actionQuery.setVisible(true);
		this.actionQuery.setEnabled(true);
		bl = true;
		
		this.btnAttachment.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.btnAuditResult.setVisible(false);
		
		this.tblMain.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd"); 
		
		/**判断CU隔离*/ 
		if(this.isIsCU()){
			this.initEnabledTrue();
		}else{
		/**判断 采购组织 实体  或  虚体*/
			this.isIsPurchaseOrgUnit();
			
			if(this.isIsPurchaseOrgUnit()){
				this.getUIContext().put("isViewEnbled", Boolean.TRUE);
			}else{
				this.getUIContext().put("isViewEnbled", Boolean.FALSE);
			}
		}
		
	}


	/**
	 * 描述：为新打开页面传值
	 * 创建时间：2010-12-2<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 *
	 * @see  
	*/
	private void sendValue() {
		
//		if(this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"number").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"number").getValue().equals("")){
//			this.getUIContext().put(
//					"tblNumberValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"number").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblNumberValue",
//					"");
//		}
//		
//		if(this.tblMain.getCell(
//				this.tblMain.getSelectManager().getActiveRowIndex(),
//				"name").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"name").getValue().equals("")){
//			this.getUIContext().put(
//					"tblNameValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"name").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblNameValue",
//					"");
//		}
//		
//		if(this.tblMain.getCell(
//				this.tblMain.getSelectManager().getActiveRowIndex(),
//				"model").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"model").getValue().equals("")){
//			this.getUIContext().put(
//					"tblModelValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"model").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblModelValue",
//					"");
//		}
//		
//		if(this.tblMain.getCell(
//				this.tblMain.getSelectManager().getActiveRowIndex(),
//				"baseUnit.name").getValue()!=null && !this.tblMain.getCell(
//						this.tblMain.getSelectManager().getActiveRowIndex(),
//						"baseUnit.name").getValue().equals("")){
//			this.getUIContext().put(
//					"tblUnitValue",
//					this.tblMain.getCell(
//							this.tblMain.getSelectManager().getActiveRowIndex(),
//					"baseUnit.name").getValue().toString().trim());
//			
//		}else{
//			this.getUIContext().put(
//					"tblUnitValue",
//					"");
//		}

	}
	

    
    /**
     * 描述：获得数据并根据过滤条件将值放入table
     * 创建时间：2010-11-17<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @param treeId 树节点参数
     * @see  
    */
    private void insertDataToTblMain(String treeId ) throws BOSException, EASBizException{
    	/*删除表中所有行*/
    	this.tblMain.removeRows();
    	IRow row = null;
    	Map map = null ;
    	
    	/*调用远程方法返回装有对象的MAP*/
    	Map resultMap  = MaterialInfoFactory.getRemoteInstance().getMaterialInfoMsg(new ObjectUuidPK(treeId)) ;
    	MaterialInfoInfo  pInfo = null;
    	MaterialCollection mcol = (MaterialCollection) resultMap.get("materialInfo");
    	map = (Map) resultMap.get("priceInfo");
    	for (int i = 0; i < mcol.size(); i++) {
    		MaterialInfo mInfo = mcol.get(i);
    		pInfo = (MaterialInfoInfo) map.get(mInfo.getId().toString());
    		row = this.tblMain.addRow();
			/*名称   如果不为空则赋值*/
			row.getCell("name").setValue(mInfo.getName());
			/*编码   如果不为空则赋值*/
			row.getCell("number").setValue(mInfo.getNumber());
			/*规格型号   如果不为空则赋值*/
			row.getCell("model").setValue(mInfo.getModel());
			/*单位   如果不为空则赋值*/
			row.getCell("baseUnit.name").setValue(mInfo.getBaseUnit().getName());
			/*ID   如果不为空则赋值*/
			row.getCell("id").setValue(mInfo.getId());
			/*最新报价   如果不为空则赋值*/
			if (pInfo == null) {
				continue;
			}
			row.getCell("winPrice").setValue(pInfo.getPrice());
			/*报价时间   如果不为空则赋值*/
			row.getCell("quoteTime").setValue(pInfo.getQuoteTime());
			/*供应商   如果不为空则赋值*/
			if (pInfo.getSupplier() != null) {
				row.getCell("supplier").setValue(pInfo.getSupplier().getName());
			}
		}
    	
    	this.setTableAutoSort();
	}

	/**
	 * 描述：设置表的列可自动排序
	 * @Author：jian_cao
	 * @CreateTime：2012-9-3
	 */
	private void setTableAutoSort() {
		// 设置表格自动列排序
		// 仅可自动排序的列才能自动排序
		for (int i = 0, size = this.tblMain.getColumnCount(); i < size; i++) {
			this.tblMain.getColumn(i).setSortable(true);
		}

		new KDTSortManager(this.tblMain).setSortAuto(true);
	}
    
    /**
     * 描述：获得树对象
     * 创建时间：2010-11-17<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @see  
    */
    private MaterialGroupInfo getMaterialGroupInfo(){
    	/*得到选中的path*/
		TreePath path = this.kDTree1.getSelectionPath();
		if (path == null) {
			SysUtil.abort();
		}
		/* 根据path得到节点*/
		DefaultKingdeeTreeNode treenode = (DefaultKingdeeTreeNode) path
				.getLastPathComponent();
		if(treenode != null && treenode.getUserObject() instanceof MaterialGroupInfo){
			/*得到树的对象*/
			MaterialGroupInfo info = (MaterialGroupInfo) treenode.getUserObject();
			return info;
			
		}else
			return null;
    }
    
    /**
     * 描述：构建表
     * 创建时间：2010-11-17<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @see  
    */
    private int headLength = -1;
    private void builderMaterialTable() throws BOSException, EASBizException{
    	this.setTableStyle();
    	/*获得树对象*/
    	MaterialGroupInfo groupInfo = this.getMaterialGroupInfo();
    	String materialGroupId = null;
    	/*
    	 * 如果树对象不为空，调用插入数据方法将数据放入表中
    	 */
    	if(groupInfo != null){
    		materialGroupId  = groupInfo.getId().toString();
    		insertDataToTblMain(materialGroupId);//
    		/*获得返回的指标对象集合*/
			IRowSet rowSet = MaterialInfoFactory.getRemoteInstance().selectTraitAndSuperIndex(groupInfo.getLongNumber());
    		/*指标集合*/
    		List indexList = new ArrayList();
    		/*指标ID集合*/
    		List idList= new ArrayList();
    		
    		try {
				while (rowSet.next()) {
					/* 从T_MTR_MaterialIndex中得到该特性指标ID */
					idList.add(rowSet.getString("FID"));
					/* 得到特性指标的名字 */
					indexList.add(rowSet.getString("FName_l2"));
				}
			} catch (SQLException e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
    		
    		/*如果报价信息表表头小于2行则新增一行*/
    		if(this.MaterialInfo.getHeadRowCount() < 2){
    			this.MaterialInfo.addHeadRow();
    		}
    		/*
    		 * 
    		 * 此处为计数器,当特性指标为空时,则不会进入
    		 * 否则新增指标表头列大于0,逐个删除
    		 */
    		if(headLength > 0){
    			for (int i = 0; i < headLength ; i++) {
    				this.MaterialInfo.removeColumn(0);
    			}
    		}
    		/**将计数器的长度设置为特性指标长度*/
    		headLength = indexList.size();
    		/*
    		 * 这里是根据计数器判断是否第一次进入此方法,
    		 * 如果是则动态生成特性指标的列
    		 */
    		if (headLength > 0) {
    			/*循环遍历特性指标大小生成特性指标表头*/
				for (int i = 0; i < indexList.size(); i++) {
					IColumn col = this.MaterialInfo.addColumn(i);
					this.MaterialInfo.getHeadRow(1).getCell(i).setValue(
							indexList.get(i).toString());
					this.MaterialInfo.getColumn(i).setKey(
							idList.get(i).toString());

				}
				/*获得特性指标表头的第0行,将值设置成特性指标*/
				this.MaterialInfo.getHeadRow(0).getCell(0).setValue(EASResource.getString(MATERIALINFO_RESOURCEPATH,"index"));
			}
    		/*获得表格融合管理器*/
			KDTMergeManager mm = this.MaterialInfo.getHeadMergeManager();
			/*融合特性指标表头第0行*/
			mm.mergeBlock(0, 0, 0, indexList.size() - 1);
			/*循环融合除去动态生成的特性指标列*/
			for (int i = 0; i < 10; i++) {
				mm.mergeBlock(0, indexList.size() + i, 1, indexList.size() + i);
			}

    		
    	}else{
    		this.tblMain.removeRows(false);
    		MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH,"childNote"));
    	}
    		
    }
    /**
     * 描述：是否选中详细信息表
     * 创建时间：2010-11-20<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @see  
    */
    private void materialInfoCheckSelected(){
    	if(MaterialInfo.getRowCount() == 0 || MaterialInfo.getSelectManager().size() == 0)
        {
            MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH,"selectQuote"));
            this.abort();
        }
    }
    
    /**
     * 描述：设置materialinfo表的状态
     * 创建时间：2010-11-18<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @see  
    */
    private void setTableStyle(){
    	/* 设置价格的显示位置 */
    	this.MaterialInfo.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("winPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		/*设置时间的格式 */
		this.tblMain.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		this.MaterialInfo.getColumn("quoteTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd");
		/* 设置选择方式*/
		this.MaterialInfo.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		/*设置只能选择一行*/
		this.MaterialInfo.getSelectManager().setSelectMode(2);
		/*设置只能选择一行*/
		this.tblMain.getSelectManager().setSelectMode(2);
		/*设置表格锁定*/
		this.MaterialInfo.getStyleAttributes().setLocked(true);
    }
    
    
    /**
     * 描述：构建材料信息表
     * 创建时间：2010-11-24<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @see  
    */
    private void builderMaterialInfoTable() throws BOSException{
    	/*获得树对象*/
    	MaterialGroupInfo groupInfo = this.getMaterialGroupInfo();
    	String materialGroupId = null;
    	/* 如果树对象不为空，调用插入数据方法将数据放入表中 */
    	if(groupInfo != null){
    		materialGroupId  = groupInfo.getId().toString();
    		this.createMaterialInfoTable();
    	}else{
    		this.tblMain.removeRows(false);
    		MsgBox.showInfo("请选择子节点!");
    	}
    }
    
    
    /**
     * 描述：创建材料信息表并填充数据
     * 创建时间：2010-11-23<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     *
     * @see  
    */
    private void createMaterialInfoTable() throws BOSException{
    	getMaterialIndexAndValue(this.getMaterialId());
    }
 
    /**
     * 描述：获得特性指标与特性指标值
     * 创建时间：2010-11-23<p>
     * 
     * 修改人：<p>
     * 修改时间： <p>
     * 修改描述： <p>
     * @throws UuidException 
     * @throws BOSException 
     * @param materialId 
     * @see  
    */
    private void getMaterialIndexAndValue(String materialId) throws BOSException, UuidException{
    	/*检查是否选中报价信息表*/
    	this.MaterialInfo.checkParsed();
    	/*获得返回的材料信息对象集合*/
    	MaterialInfoCollection mic =  getMaterInfoCollection(materialId);
    	IRow row = null;
    	/*删除表中所有行*/
    	MaterialInfo.removeRows();
    	Iterator  it = mic.iterator();
    	/*循环遍历获得材料信息info对象*/
    	while(it.hasNext()){
    		
    		MaterialInfoInfo info =(MaterialInfoInfo) it.next();
    		row = MaterialInfo.addRow();
    		/*根据info对象中的ID 获得特性指标值*/
    		MaterialIndexValueCollection indexv =getMaterialIndexValue(info.getId().toString());
    		
    		Iterator  its = indexv.iterator();
    		/*循环遍历获得特性指标值对象*/
    		while(its.hasNext()){
    			MaterialIndexValueInfo iinfo =(MaterialIndexValueInfo) its.next();
    			MaterialIndexInfo mminfo = iinfo.getMaterialIndex();
    			if(mminfo != null && iinfo.getValue() != null){
    				if(row.getCell(mminfo.getId().toString()) != null){
    					row.getCell(mminfo.getId().toString()).setValue(iinfo.getValue());
    				}
    			}
    		}
    		/*设置 单价列值*/
    		row.getCell("price").setValue(info.getPrice());
    		/*设置 最新报价列值 */
    		row.getCell("quoteTime").setValue(info.getQuoteTime());
    		/*设置 供应商列值*/
    		row.getCell("supplier").setValue(info.getSupplier());
    		/*设置 工程项目列值*/
    		row.getCell("project").setValue(info.getProject());
    		/*设置 合同列值*/
    		if(null != info.getContractBill()){
    			row.getCell("contractBill").setValue(info.getContractBill().getName());
    		}
    		/*设置 合同状态列值*/
    		row.getCell("state").setValue(info.getMState());
    		/*设置 ID列值*/
    		row.getCell("id").setValue(info.getId());
    		/*设置 有效日期列值*/
    		row.getCell("validDate").setValue(info.getValidDate());
    		/*设置 单价列值*/
    		row.getCell("creator").setValue(info.getCreator().getName());
    		/*设置 审核状态列值*/
    		if( info.getState() != null
    				&&  !info.getState().equals("")
    				&& FDCBillStateEnum.AUDITTED.equals(info.getState())) {
    			row.getCell("sstate").setValue("核准");
    		}else{
    			row.getCell("sstate").setValue("未核准");
    		}
    	}
    }
	/**
	 * 描述：获得指标方法
	 * 创建时间：2010-11-26<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private MaterialIndexValueCollection getMaterialIndexValue(String id) {
		
		EntityViewInfo view = new EntityViewInfo();
		/*设置需要查询的字段*/
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("materialIndex.*"));	 
		/*设置需要过滤的条件*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("material.id", id));
	    view.setFilter(filter);
        try {
        	/*调用远程方法获得对象并将其返回*/
			return MaterialIndexValueFactory.getRemoteInstance().getMaterialIndexValueCollection(view);
			
		} catch (BOSException e) { 
			handUIException(e);
		}

		return null;

	}
	/**
	 * 描述：获得报价信息
	 * 创建时间：2010-11-26<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private MaterialInfoCollection getMaterInfoCollection(String id) {
		
		EntityViewInfo view = new EntityViewInfo();
		/*设置需要查询的字段*/
		view.getSelector().add(new SelectorItemInfo("*"));	 
		view.getSelector().add(new SelectorItemInfo("supplier.*"));	 
		view.getSelector().add(new SelectorItemInfo("indexValue.id"));	
		view.getSelector().add(new SelectorItemInfo("indexValue.value"));	
		view.getSelector().add(new SelectorItemInfo("indexValue.materialIndex.id"));	 
		view.getSelector().add(new SelectorItemInfo("project.name"));	 
		view.getSelector().add(new SelectorItemInfo("contractBill.name"));	 
		view.getSelector().add(new SelectorItemInfo("material.*"));	
		view.getSelector().add(new SelectorItemInfo("creator.*"));	
		/*设置过滤条件*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("material.id", id));
	    view.setFilter(filter);
	    try {
        	/*调用远程方法获得对象并将其返回*/
			return MaterialInfoFactory.getRemoteInstance().getMaterialInfoCollection(view);
			
		} catch (BOSException e) { 
			handUIException(e);
		}

		return null;

	}
 
	/**
	 * 描述：获得指标实体集合
	 * 创建时间：2010-11-26<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private MaterialIndexCollection getMaterialIndexCollection(String materialGroupId){
		/*设置需要查询的字段*/
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("name"));
		/*设置过滤条件*/
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("materialGroup", materialGroupId));
		view.setFilter(filter);
		FilterInfo filter2 = new FilterInfo();
		filter2.getFilterItems().add(new FilterItemInfo("isEnabled",new Integer(1)));
		try {
			view.getFilter().mergeFilter(filter2, "and");
			/*调用远程方法获得对象并将其返回*/
			return MaterialIndexFactory.getRemoteInstance().getMaterialIndexCollection(view);
		} catch (BOSException e) {
			handUIException(e);
		}
		return null;
	}
	
	/**
	 * 描述：判断是否选中基础物料表中的一行
	 * 创建时间：2010-11-26<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	public void checkSelected(){
		/*
		 * 如果表里没有选择行，提示用户选择行
		 */
        if(tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
        {
            MsgBox.showInfo(this, EASResource.getString(MATERIALINFO_RESOURCEPATH,"baseMaterial"));
            this.abort();
        }
    }
	
	/**
	 * 描述：判断是否选择了树的子节点
	 * 创建时间：2010-12-2<p>
	 * 
	 * 修改人：<p>
	 * 修改时间： <p>
	 * 修改描述： <p>
	 *
	 * @see  
	*/
	private boolean checkChildNode() {
		TreePath tpath = kDTree1.getSelectionPath();
		/*
		 * 如果得到的Path不空，那么用户就没有选择树，就给出提示
		 */
		if(tpath == null){
			MsgBox.showInfo("请选择节点！");
			return false;
		}
		DefaultKingdeeTreeNode kdtn = (DefaultKingdeeTreeNode)tpath.getLastPathComponent();
 		/*
 		 * 如果子节点的值为空，提示未选择子节点 
 		 */
		if (!kdtn.isLeaf()) {
			MsgBox.showInfo(EASResource.getString(MATERIALINFO_RESOURCEPATH,"childNote"));
			abort();
			return false;
		}
		return true;
	}
	
	
}