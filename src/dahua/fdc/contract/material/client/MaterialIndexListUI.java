/*
 * @(#)MaterialIndexListUI.java
 * 
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.material.client;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.master.material.MaterialGroupFactory;
import com.kingdee.eas.basedata.master.material.MaterialGroupInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.material.MaterialIndexFactory;
import com.kingdee.eas.fdc.material.MaterialIndexInfo;
import com.kingdee.eas.fdc.material.MaterialInfoFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.util.UuidException;

	/**
	 * @description		材料特性指标的增、删、改、查,启用,禁用<p>
	 * @author  		杜仕超
	 * @version 		EAS 7.0
	 * @see 
	 */
	public class MaterialIndexListUI extends AbstractMaterialIndexListUI {
		
		/**缺省版本标识*/
		private static final long serialVersionUID = -1643532346356l;
		
		/**定义树节点*/
		private static String localNode = "";
		
		/**获得资源文件路径*/
		private static final String RESOURC_FILE_PATH = "com/kingdee/eas/fdc/material/MaterialIndexResource";
		
		/**初始化根节点名称*/
		private  String treeRootName = this.setResourc("treeRoot"); 
		
	 /**
	  * @description    读取资源文件
	  * @author			杜仕超
	  * @createDate		2010-11-10
	  * @param	
	  * @return					
	  * @version		EAS7.0
	  * @see
	  */
	private String setResourc(String name){
		return EASResource.getString(RESOURC_FILE_PATH, name);
	}
	
	/**
	 * onLoad
	 */
	public void onLoad() throws Exception {
		
		super.onLoad(); 	
		/*执行构建树_初始化*/
		this.materTree();
	} 
	
	/**
	 * @description		构造树
	 * @author			杜仕超
	 * @createDate		2010-11-10
	 * @param	
	 * @return			boolean	
	 * @version			EAS7.0
	 * @see
	 */
    private void materTree() throws Exception{
    	
    	BuilderTreeUtil.getTreeUtilInstance().buiderTree(this.kDTree1,MaterialGroupFactory.getRemoteInstance(), treeRootName);
    }
    
	/**
	 * @description		给用户显示提示信息
	 * @author			杜仕超
	 * @createDate		2010-11-10
	 * @param	
	 * @return			boolean	
	 * @version			EAS7.0
	 * @see
	 */
	private boolean getManagerMessage(){
		 
		/*获得选中那一行的行号*/
		int clickIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		/*获得选中那一行的数据*/
		IRow clickRow = this.tblMain.getRow(clickIndex);
		/*判断是否为基础指标 是则显示不能操作*/
		if (this.setResourc("baseIndex").equals(clickRow.getCell("source").getValue().toString().trim())) { 
			FDCMsgBox.showWarning(this.setResourc("notUseSystemData"));
			return false;
		} else {
			/*判断是否为上级指标,是则显示不能操作*/
			if (this.setResourc("upIndex").equals(clickRow.getCell("source").getValue().toString().trim())) {
				FDCMsgBox.showWarning(this.setResourc("notUsePraent"));
				return false;
			}
		}
		return true;
	}
  
	/** 
	 * @description		设置按钮状态
	 * @author			杜仕超
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	private void setEditEnabled(boolean isView){
		/*设置是否可操作*/
		if(isView){
		
			this.actionEdit.setEnabled(isView);
			this.actionAddNew.setEnabled(isView);
			this.actionRemove.setEnabled(isView);
			this.actionCancelCancel.setEnabled(isView);
			this.actionCancel.setEnabled(isView);  
		}else{ 
			this.actionEdit.setEnabled(isView);
			this.actionAddNew.setEnabled(isView);
			this.actionRemove.setEnabled(isView);
			this.actionCancelCancel.setEnabled(isView);
			this.actionCancel.setEnabled(isView);  
		}
		
	}
	
	/** 
	 * @description		onShow 初始化设置
	 * @author			杜仕超
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	public void onShow() throws Exception {
		
			super.onShow();
			/*置日期显示格式*/
			this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd"); 
			/*以比例形式显示mainSplitPane左20% 右80%*/
			this.mainSplitPane.setDividerLocation(200);
			/*设置栏按钮为灰色*/
			this.setEditEnabled(false);
			
	}   
	
	/** 
	 * @description		设置显示指标类型
	 * @author			杜仕超
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	private void setTableSource() throws Exception {
		/*获得table 总行数*/
		int rowCount = tblMain.getRowCount();
		/*依依获取  来源,树节点 的值*/
		for (int i = 0; i < rowCount; i++) {
			IRow iRow = tblMain.getRow(i);
			ICell iCellSource = iRow.getCell("source");
			ICell iCellGroupid = iRow.getCell("materialGroup.id");
			String sourceValue = iCellSource.getValue().toString();
			String groupid = null;
			/*非空判断 且 设置父节点的来源列 为 上级指标*/
			if (null != iCellGroupid) { 
				groupid = iCellGroupid.getValue().toString();
				if (!this.setResourc("baseIndex").equals(sourceValue)) {
					if (!localNode.equals(groupid)) {
						iCellSource.setValue(this.setResourc("upIndex"));
					}
				}
			} else {
				return;
			}
		}
	}
	
   /**
	* @description		得到TREE选中的状态
    * @author			
    * @createDate		2010年11月6日
    * @param			tree:是tree的名称
    * @return			DefaultKingdeeTreeNode		
    * @version			EAS7.0
    * @see
     */
    private DefaultKingdeeTreeNode getSelectNode(KDTree tree){
    	if(tree.getLastSelectedPathComponent() != null){
    		return (DefaultKingdeeTreeNode) tree.getLastSelectedPathComponent();
    	}else{
    		return null;
    	} 
    }

	/**
	 * @description		执行操作树的变化事件
	 * @author			杜仕超
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	public void kDTree1_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception{ 
		
		this.tblMain.removeRows(true);
		super.kDTree1_valueChanged(e);		
		this.tblMain.removeRows(); 
		/*设置按钮显示状态*/
		this.setEditEnabled(false);
		this.actionAddNew.setEnabled(true);
		/*如果用户选择根节点时,灰掉新增按钮*/
		if(this.getSelectNode(this.kDTree1)!= null &&treeRootName.equals(this.getSelectNode(this.kDTree1).toString())){
			this.actionAddNew.setEnabled(false);
		}else 
			/*设置 CU 隔离*/
			if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
			this.actionAddNew.setEnabled(false);
		}
		  
		this.setTableSource();
		 
	} 
	
	/** 
	 * @description		获得所有父节点ID 并装入set 集合
	 * @author			杜仕超
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	private void getParentNodeId(TreeNode treeNode,Set set){ 
		/*获取父节点 */
		DefaultKingdeeTreeNode parentTree =(DefaultKingdeeTreeNode) treeNode.getParent();
		/*如果用户选择的不是根节点时且不为空时才可装入*/
			if(null != parentTree.getUserObject() && !treeRootName.equals(treeNode.getParent().toString().trim())){
				Object object = ((DefaultMutableTreeNode) parentTree).getUserObject();
				if(object instanceof MaterialGroupInfo){
					set.add(((MaterialGroupInfo)object).getId().toString().trim()); 
				}
				/*调用获取父节点方法,并获得所有父节点*/
				getParentNodeId(parentTree,set);
			}
			return ;
	}  
	
	/** 
	 * @description		获得过滤条件
	 * @author				杜仕超
	 * @createDate		2010-11-10
	 * @param	
	 * @return			FilterInfo		
	 * @version			EAS7.0
	 * @see
	 */
    private FilterInfo getMaterialIndexFilter() throws Exception {
    	/*初始化过滤对象*/
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		TreePath path = kDTree1.getSelectionPath();
		
		DefaultKingdeeTreeNode nodeName = null;
		/*判断路径不为空 并设置 mainContainer控件 的标题*/
		if (null != path) {
			nodeName = (DefaultKingdeeTreeNode) path.getLastPathComponent();
			mainContainer.setTitle(nodeName.getText());
			mainContainer.setToolTipText(nodeName.getText());

			Set set = new HashSet();   
			/*判断节点对象是否为MaterialGroupInfo 的对象,是就将所有父节点 ID 装入 Set */
			DefaultKingdeeTreeNode node = this.getSelectNode(this.kDTree1);
			if (node.getUserObject() instanceof MaterialGroupInfo) {
				localNode = ((MaterialGroupInfo) node.getUserObject()).getId().toString().trim();
				set.add(localNode);
				this.getParentNodeId(node, set);
			}
			/*两者非空判断*/
			if (null != set && set.size() > 0) {
			  	/*where 条件 树节点*/ 
				filterItems.add(new FilterItemInfo("materialGroup.id", set,CompareType.INCLUDE));
				/*    添加一个条件 where 里*/
				filterItems.add(new FilterItemInfo("materialIndex.source","BASEINDEX"));
				/*两个条件合并 或 的关系*/ 
				filter.setMaskString("#0 or #1");
			} else {
				/*故意给它一个永远不会执行成功的条件*/
				filterItems.add(new FilterItemInfo("materialGroup.id","STOP", CompareType.EQUALS));
			} 
		}else{
			/*故意给它一个永远不会执行成功的条件*/
			filterItems.add(new FilterItemInfo("materialGroup.id","STOP", CompareType.EQUALS));
		}
		/*返回过滤器*/
			return filter;
    }
    
   /** 
    * @description		执行查询
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK dataPK,
    		EntityViewInfo viewInfo) {
    	
    	FilterInfo filter = null;
    	
    	try{
    		/*获取过滤器的值  并连接(与 的形式)判断 执行*/
    		filter = getMaterialIndexFilter(); 
    		/*非空判断 并执行查询*/
			if (this.getDialog() != null && viewInfo.getFilter() != null ) {
				FilterInfo commFilter = viewInfo.getFilter();
				if (filter != null && commFilter != null)
					filter.mergeFilter(commFilter, "and");
			} else { 
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution != null && querySolution.getEntityViewInfo() != null) {
					EntityViewInfo ev = Util.getInnerFilterInfo(querySolution);
					if (ev.getFilter() != null) {
						filter.mergeFilter(ev.getFilter(), "and");
					}
				}
			}
		}catch (Exception e1) {
			handUIException(e1);
		} 
		viewInfo.setFilter(filter);
    	
    	return super.getQueryExecutor(dataPK, viewInfo);
    } 

	/**
	 * output class constructor  
	 */
    public MaterialIndexListUI() throws Exception {
		super();
	}

   /** 
    * @description		启用操作
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		/*判断是否选中行*/
		this.checkSelected();
		/*判断是否为基础指标 或 上级指标 如果是就不能操作*/
		if(this.getManagerMessage()){
			super.actionCancelCancel_actionPerformed(e);
			this.setTableSource(); 	

		}else{
			return ;
		}
	} 
	
   /** 
    * @description		根据materialIndexId,获得特性指标值
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	private List getMaterialIndexValues(String materialIndexId){
		/*实例化一个List 对象 装取查询的字段的值集合*/
		List materialIndexValues = new ArrayList();
		/*实例化一个Map 对象 装取过滤条件和值*/
		Map indexValueMap = new HashMap();
		indexValueMap.put("FMaterialindexID", materialIndexId);
		/*实例化一个List 对象 装取查询的字段*/
		List indexValueList = new ArrayList();
		indexValueList.add("FValue");
		try {
			/*调用ControlBean远程方法*/
			materialIndexValues = MaterialInfoFactory.getRemoteInstance().getMaterialData(indexValueMap, "T_MTR_MaterialindexValue", indexValueList);
		} catch (Exception e) {
			handUIException(e);
		}
		/*返回所有查询后字段的值集合*/
		return materialIndexValues;
	}
	 
   /** 
    * @description		禁用操作
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		/*先判断是否被选中*/
		this.checkSelected();
		/*调用自定义方法*/
		if(this.getManagerMessage()){  
				/*取得当前选择的行号*/
				int clickIndex = this.tblMain.getSelectManager().getActiveRowIndex();
				/*非空判断*/
				if(null != this.tblMain.getRow(clickIndex).getCell("id").getValue()){
					/*判断当前选择行是否与其它表关联*/
					String MaterialIndexID = this.tblMain.getRow(clickIndex).getCell("id").getValue().toString().trim();
					List materialIndexVlaues = this.getMaterialIndexValues(MaterialIndexID);
					boolean temp = true;
					/*遍历查询字段后的值*/
					for(int j = 0; j < materialIndexVlaues.size(); j ++){
						if(null != materialIndexVlaues.get(j)){
							Map map = (Map) materialIndexVlaues.get(j);
							if(null !=map.get("FValue") && !"".equals(map.get("FValue").toString())){
								temp = false;
							}
						}
					}
					if(!temp){
						FDCMsgBox.showWarning(this, this.setResourc("notCancel"));
						return ;
					}
				} 
				super.actionCancel_actionPerformed(e);
				this.setTableSource(); 	
			
		}else{
			return ;
		}
	}
  
   /** 
    * @description		新增 且作出相应的判断
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(null == this.getSelectNode(this.kDTree1)){
			FDCMsgBox.showWarning(this.setResourc("selectNode"));
			return;  
		}else{
			DefaultKingdeeTreeNode node = this.getSelectNode(this.kDTree1);  
			/*判断是否为根节点*/
			if(treeRootName.equals(node.toString().trim())){
			
				FDCMsgBox.showWarning(this.setResourc("notAddNew"));
				return;
			}
			/*获得当前节点对象并传到UIContext里*/
			MaterialGroupInfo info = (MaterialGroupInfo)node.getUserObject();
			this.getUIContext().put("MaterialGroupInfo", info);
		}
		super.actionAddNew_actionPerformed(e);
		this.setTableSource(); 	 
	} 
	
   /**
    * @description		修改操作
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		/*判断并作出提示*/
		if(this.getManagerMessage()){
			super.actionEdit_actionPerformed(e);
			this.setTableSource(); 	
		}else{
			return ;
		}
	}

   /**
    * @description		删除操作
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
			this.checkSelected();
			/*判断调用基类方法是否 开启*/
			if(getBaseDataInfo().isIsEnabled()){
				FDCMsgBox.showWarning(this.setResourc("notRrmove"));
				abort();
			}
			/*判断并作出提示*/
			if(this.getManagerMessage()){  	
					int clickIndex = this.tblMain.getSelectManager().getActiveRowIndex();
					if(null != this.tblMain.getRow(clickIndex).getCell("id").getValue()){
						/*判断当前选择行是否与其它表关联*/
 						String MaterialIndexID = this.tblMain.getRow(clickIndex).getCell("id").getValue().toString().trim();
 						List materialIndexVlaues = this.getMaterialIndexValues(MaterialIndexID);
 						boolean temp = true;
 						/*遍历查询字段后的值*/
 						for(int j = 0; j < materialIndexVlaues.size(); j ++){
 							if(null != materialIndexVlaues.get(j)){
 								Map map = (Map) materialIndexVlaues.get(j);
 								if(null !=map.get("FValue") && !"".equals(map.get("FValue").toString())){
 									temp = false;
 								}
 							}
 						}
 						if(!temp){
 							FDCMsgBox.showWarning(this, this.setResourc("ghostData"));
 							return ;
 						}
					}  
			super.actionRemove_actionPerformed(e);
			this.setTableSource();
		} else {
			return;
		}
   } 
	 
   /**
    * @description		点击 表  事件
    * @author			杜仕超
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)	throws Exception {
		/*判断是否选中行*/
		if(e.getType()==0 || e.getType()==3){
				super.tblMain_tableClicked(e);
				this.setEditEnabled(false);
				/*CU隔离*/
				if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
					this.actionAddNew.setEnabled(true);
				}
				this.setTableSource(); 
		        return ;
		}else{
  
				MaterialIndexInfo info = (MaterialIndexInfo) getBaseDataInfo();
				if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
					this.setEditEnabled(false);
				}else{
					this.setEditEnabled(true);
				}
				/*是否选中*/
				if( info.isIsEnabled()){
					this.actionCancel.setEnabled(true);
					this.actionCancelCancel.setEnabled(false);
				}else {
					this.actionCancel.setEnabled(false);
					this.actionCancelCancel.setEnabled(true);
				}
				if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
					this.actionCancel.setEnabled(false);
					this.actionCancelCancel.setEnabled(false);
				}
		}
		super.tblMain_tableClicked(e);
		this.setTableSource();
	}
	
	/** 
	 * @description		获得实例对象
	 * @author			杜仕超
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
  	protected FDCDataBaseInfo getBaseDataInfo() {
		checkSelected();
		MaterialIndexInfo info = null;
		/*判断表格中有行数据*/
		if(this.tblMain.getRowCount() > 0){
			String id = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
		
			try {
				info = (MaterialIndexInfo) MaterialIndexFactory.getRemoteInstance().getValue(new ObjectUuidPK(BOSUuid.read(id)));
			} catch (EASBizException e) {
				handUIException(e);
			} catch (BOSException e) {
				handUIException(e);
			} catch (UuidException e) {
				handUIException(e);
			}
		}
			return info;		
	}

  	/** 
	 * @description		调用远程接口
	 * @author			杜仕超
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return MaterialIndexFactory.getRemoteInstance();
	}

	/** 
	 * @description		获得编辑页面名称
	 * @author			杜仕超
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	protected String getEditUIName() {
		return MaterialIndexEditUI.class.getName();
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		
		setTableSource();
		super.actionRefresh_actionPerformed(e);
	}
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed 
	 */
	public void actionCalculator_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		super.actionView_actionPerformed(e);
	}
 
	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	} 
}