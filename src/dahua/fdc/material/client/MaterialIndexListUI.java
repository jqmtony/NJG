/*
 * @(#)MaterialIndexListUI.java
 * 
 * �����������������޹�˾��Ȩ���� 
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
	 * @description		��������ָ�������ɾ���ġ���,����,����<p>
	 * @author  		���˳�
	 * @version 		EAS 7.0
	 * @see 
	 */
	public class MaterialIndexListUI extends AbstractMaterialIndexListUI {
		
		/**ȱʡ�汾��ʶ*/
		private static final long serialVersionUID = -1643532346356l;
		
		/**�������ڵ�*/
		private static String localNode = "";
		
		/**�����Դ�ļ�·��*/
		private static final String RESOURC_FILE_PATH = "com/kingdee/eas/fdc/material/MaterialIndexResource";
		
		/**��ʼ�����ڵ�����*/
		private  String treeRootName = this.setResourc("treeRoot"); 
		
	 /**
	  * @description    ��ȡ��Դ�ļ�
	  * @author			���˳�
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
		/*ִ�й�����_��ʼ��*/
		this.materTree();
	} 
	
	/**
	 * @description		������
	 * @author			���˳�
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
	 * @description		���û���ʾ��ʾ��Ϣ
	 * @author			���˳�
	 * @createDate		2010-11-10
	 * @param	
	 * @return			boolean	
	 * @version			EAS7.0
	 * @see
	 */
	private boolean getManagerMessage(){
		 
		/*���ѡ����һ�е��к�*/
		int clickIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		
		/*���ѡ����һ�е�����*/
		IRow clickRow = this.tblMain.getRow(clickIndex);
		/*�ж��Ƿ�Ϊ����ָ�� ������ʾ���ܲ���*/
		if (this.setResourc("baseIndex").equals(clickRow.getCell("source").getValue().toString().trim())) { 
			FDCMsgBox.showWarning(this.setResourc("notUseSystemData"));
			return false;
		} else {
			/*�ж��Ƿ�Ϊ�ϼ�ָ��,������ʾ���ܲ���*/
			if (this.setResourc("upIndex").equals(clickRow.getCell("source").getValue().toString().trim())) {
				FDCMsgBox.showWarning(this.setResourc("notUsePraent"));
				return false;
			}
		}
		return true;
	}
  
	/** 
	 * @description		���ð�ť״̬
	 * @author			���˳�
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	private void setEditEnabled(boolean isView){
		/*�����Ƿ�ɲ���*/
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
	 * @description		onShow ��ʼ������
	 * @author			���˳�
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	public void onShow() throws Exception {
		
			super.onShow();
			/*��������ʾ��ʽ*/
			this.tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat("yyyy-MM-dd"); 
			/*�Ա�����ʽ��ʾmainSplitPane��20% ��80%*/
			this.mainSplitPane.setDividerLocation(200);
			/*��������ťΪ��ɫ*/
			this.setEditEnabled(false);
			
	}   
	
	/** 
	 * @description		������ʾָ������
	 * @author			���˳�
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	private void setTableSource() throws Exception {
		/*���table ������*/
		int rowCount = tblMain.getRowCount();
		/*������ȡ  ��Դ,���ڵ� ��ֵ*/
		for (int i = 0; i < rowCount; i++) {
			IRow iRow = tblMain.getRow(i);
			ICell iCellSource = iRow.getCell("source");
			ICell iCellGroupid = iRow.getCell("materialGroup.id");
			String sourceValue = iCellSource.getValue().toString();
			String groupid = null;
			/*�ǿ��ж� �� ���ø��ڵ����Դ�� Ϊ �ϼ�ָ��*/
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
	* @description		�õ�TREEѡ�е�״̬
    * @author			
    * @createDate		2010��11��6��
    * @param			tree:��tree������
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
	 * @description		ִ�в������ı仯�¼�
	 * @author			���˳�
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
		/*���ð�ť��ʾ״̬*/
		this.setEditEnabled(false);
		this.actionAddNew.setEnabled(true);
		/*����û�ѡ����ڵ�ʱ,�ҵ�������ť*/
		if(this.getSelectNode(this.kDTree1)!= null &&treeRootName.equals(this.getSelectNode(this.kDTree1).toString())){
			this.actionAddNew.setEnabled(false);
		}else 
			/*���� CU ����*/
			if(!SysContext.getSysContext().getCurrentOrgUnit().isIsCU()){
			this.actionAddNew.setEnabled(false);
		}
		  
		this.setTableSource();
		 
	} 
	
	/** 
	 * @description		������и��ڵ�ID ��װ��set ����
	 * @author			���˳�
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
	private void getParentNodeId(TreeNode treeNode,Set set){ 
		/*��ȡ���ڵ� */
		DefaultKingdeeTreeNode parentTree =(DefaultKingdeeTreeNode) treeNode.getParent();
		/*����û�ѡ��Ĳ��Ǹ��ڵ�ʱ�Ҳ�Ϊ��ʱ�ſ�װ��*/
			if(null != parentTree.getUserObject() && !treeRootName.equals(treeNode.getParent().toString().trim())){
				Object object = ((DefaultMutableTreeNode) parentTree).getUserObject();
				if(object instanceof MaterialGroupInfo){
					set.add(((MaterialGroupInfo)object).getId().toString().trim()); 
				}
				/*���û�ȡ���ڵ㷽��,��������и��ڵ�*/
				getParentNodeId(parentTree,set);
			}
			return ;
	}  
	
	/** 
	 * @description		��ù�������
	 * @author				���˳�
	 * @createDate		2010-11-10
	 * @param	
	 * @return			FilterInfo		
	 * @version			EAS7.0
	 * @see
	 */
    private FilterInfo getMaterialIndexFilter() throws Exception {
    	/*��ʼ�����˶���*/
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		TreePath path = kDTree1.getSelectionPath();
		
		DefaultKingdeeTreeNode nodeName = null;
		/*�ж�·����Ϊ�� ������ mainContainer�ؼ� �ı���*/
		if (null != path) {
			nodeName = (DefaultKingdeeTreeNode) path.getLastPathComponent();
			mainContainer.setTitle(nodeName.getText());
			mainContainer.setToolTipText(nodeName.getText());

			Set set = new HashSet();   
			/*�жϽڵ�����Ƿ�ΪMaterialGroupInfo �Ķ���,�Ǿͽ����и��ڵ� ID װ�� Set */
			DefaultKingdeeTreeNode node = this.getSelectNode(this.kDTree1);
			if (node.getUserObject() instanceof MaterialGroupInfo) {
				localNode = ((MaterialGroupInfo) node.getUserObject()).getId().toString().trim();
				set.add(localNode);
				this.getParentNodeId(node, set);
			}
			/*���߷ǿ��ж�*/
			if (null != set && set.size() > 0) {
			  	/*where ���� ���ڵ�*/ 
				filterItems.add(new FilterItemInfo("materialGroup.id", set,CompareType.INCLUDE));
				/*    ���һ������ where ��*/
				filterItems.add(new FilterItemInfo("materialIndex.source","BASEINDEX"));
				/*���������ϲ� �� �Ĺ�ϵ*/ 
				filter.setMaskString("#0 or #1");
			} else {
				/*�������һ����Զ����ִ�гɹ�������*/
				filterItems.add(new FilterItemInfo("materialGroup.id","STOP", CompareType.EQUALS));
			} 
		}else{
			/*�������һ����Զ����ִ�гɹ�������*/
			filterItems.add(new FilterItemInfo("materialGroup.id","STOP", CompareType.EQUALS));
		}
		/*���ع�����*/
			return filter;
    }
    
   /** 
    * @description		ִ�в�ѯ
    * @author			���˳�
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
    		/*��ȡ��������ֵ  ������(�� ����ʽ)�ж� ִ��*/
    		filter = getMaterialIndexFilter(); 
    		/*�ǿ��ж� ��ִ�в�ѯ*/
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
    * @description		���ò���
    * @author			���˳�
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		/*�ж��Ƿ�ѡ����*/
		this.checkSelected();
		/*�ж��Ƿ�Ϊ����ָ�� �� �ϼ�ָ�� ����ǾͲ��ܲ���*/
		if(this.getManagerMessage()){
			super.actionCancelCancel_actionPerformed(e);
			this.setTableSource(); 	

		}else{
			return ;
		}
	} 
	
   /** 
    * @description		����materialIndexId,�������ָ��ֵ
    * @author			���˳�
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	private List getMaterialIndexValues(String materialIndexId){
		/*ʵ����һ��List ���� װȡ��ѯ���ֶε�ֵ����*/
		List materialIndexValues = new ArrayList();
		/*ʵ����һ��Map ���� װȡ����������ֵ*/
		Map indexValueMap = new HashMap();
		indexValueMap.put("FMaterialindexID", materialIndexId);
		/*ʵ����һ��List ���� װȡ��ѯ���ֶ�*/
		List indexValueList = new ArrayList();
		indexValueList.add("FValue");
		try {
			/*����ControlBeanԶ�̷���*/
			materialIndexValues = MaterialInfoFactory.getRemoteInstance().getMaterialData(indexValueMap, "T_MTR_MaterialindexValue", indexValueList);
		} catch (Exception e) {
			handUIException(e);
		}
		/*�������в�ѯ���ֶε�ֵ����*/
		return materialIndexValues;
	}
	 
   /** 
    * @description		���ò���
    * @author			���˳�
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		/*���ж��Ƿ�ѡ��*/
		this.checkSelected();
		/*�����Զ��巽��*/
		if(this.getManagerMessage()){  
				/*ȡ�õ�ǰѡ����к�*/
				int clickIndex = this.tblMain.getSelectManager().getActiveRowIndex();
				/*�ǿ��ж�*/
				if(null != this.tblMain.getRow(clickIndex).getCell("id").getValue()){
					/*�жϵ�ǰѡ�����Ƿ������������*/
					String MaterialIndexID = this.tblMain.getRow(clickIndex).getCell("id").getValue().toString().trim();
					List materialIndexVlaues = this.getMaterialIndexValues(MaterialIndexID);
					boolean temp = true;
					/*������ѯ�ֶκ��ֵ*/
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
    * @description		���� ��������Ӧ���ж�
    * @author			���˳�
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
			/*�ж��Ƿ�Ϊ���ڵ�*/
			if(treeRootName.equals(node.toString().trim())){
			
				FDCMsgBox.showWarning(this.setResourc("notAddNew"));
				return;
			}
			/*��õ�ǰ�ڵ���󲢴���UIContext��*/
			MaterialGroupInfo info = (MaterialGroupInfo)node.getUserObject();
			this.getUIContext().put("MaterialGroupInfo", info);
		}
		super.actionAddNew_actionPerformed(e);
		this.setTableSource(); 	 
	} 
	
   /**
    * @description		�޸Ĳ���
    * @author			���˳�
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		/*�жϲ�������ʾ*/
		if(this.getManagerMessage()){
			super.actionEdit_actionPerformed(e);
			this.setTableSource(); 	
		}else{
			return ;
		}
	}

   /**
    * @description		ɾ������
    * @author			���˳�
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
			this.checkSelected();
			/*�жϵ��û��෽���Ƿ� ����*/
			if(getBaseDataInfo().isIsEnabled()){
				FDCMsgBox.showWarning(this.setResourc("notRrmove"));
				abort();
			}
			/*�жϲ�������ʾ*/
			if(this.getManagerMessage()){  	
					int clickIndex = this.tblMain.getSelectManager().getActiveRowIndex();
					if(null != this.tblMain.getRow(clickIndex).getCell("id").getValue()){
						/*�жϵ�ǰѡ�����Ƿ������������*/
 						String MaterialIndexID = this.tblMain.getRow(clickIndex).getCell("id").getValue().toString().trim();
 						List materialIndexVlaues = this.getMaterialIndexValues(MaterialIndexID);
 						boolean temp = true;
 						/*������ѯ�ֶκ��ֵ*/
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
    * @description		��� ��  �¼�
    * @author			���˳�
    * @createDate		2010-11-10
    * @param	
    * @return					
    * @version			EAS7.0
    * @see
    */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)	throws Exception {
		/*�ж��Ƿ�ѡ����*/
		if(e.getType()==0 || e.getType()==3){
				super.tblMain_tableClicked(e);
				this.setEditEnabled(false);
				/*CU����*/
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
				/*�Ƿ�ѡ��*/
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
	 * @description		���ʵ������
	 * @author			���˳�
	 * @createDate		2010-11-11
	 * @param	
	 * @return					
	 * @version			EAS7.0
	 * @see
	 */
  	protected FDCDataBaseInfo getBaseDataInfo() {
		checkSelected();
		MaterialIndexInfo info = null;
		/*�жϱ������������*/
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
	 * @description		����Զ�̽ӿ�
	 * @author			���˳�
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
	 * @description		��ñ༭ҳ������
	 * @author			���˳�
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