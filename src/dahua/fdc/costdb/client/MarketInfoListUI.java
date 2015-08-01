/**
 * output package name
 */
package com.kingdee.eas.fdc.costdb.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitCollection;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICtrlUnit;
import com.kingdee.eas.basedata.org.IFullOrgUnit;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.costdb.IMarketInfoType;
import com.kingdee.eas.fdc.costdb.MarketInfoFactory;
import com.kingdee.eas.fdc.costdb.MarketInfoInfo;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeFactory;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class MarketInfoListUI extends AbstractMarketInfoListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(MarketInfoListUI.class);
	//获得当前组织单元
	private OrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentOrgUnit();

	private Set set = new HashSet();

	//获取有权限的组织
	protected Set authorizedOrgs = null;
	
	//获得直接上级CU
	private Set upCU = new HashSet();
	private FileGetter fg;
	/**
	 * output class constructor
	 */
	public MarketInfoListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		KDTable table = this.tblMain;
		int[] rows = KDTableUtil.getSelectedRows(this.tblMain);
		boolean deleteEnabled = true;
		String currentOrgId = currentOrg.getId().toString();
		for (int i = 0; i < rows.length; i++) {
			IRow row = table.getRow(rows[i]);
			if(row != null){
				ICell cellOrgId = row.getCell("org.id");
				if (cellOrgId != null && cellOrgId.getValue() != null
						&& !currentOrgId.equals(cellOrgId.getValue().toString())) {
					deleteEnabled = false;
					break;
				}
			}

		}
		//当前组织只可维护当前组织建立的类型
		if (deleteEnabled) {
			this.actionRemove.setEnabled(true);
			this.actionEdit.setEnabled(true);
//			this.actionAddNew.setEnabled(true);
			this.actionAttachment.setEnabled(true);
		} else {
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
//			this.actionAddNew.setEnabled(false);
//			this.actionAttachment.setEnabled(false);
		}
	}

	

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkSelectType(this,getTypeSelectedTreeNode());
		super.actionAddNew_actionPerformed(e);
	}


	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		checkSelected();	
		String boID = getSelectedKeyValue();
		super.actionRemove_actionPerformed(e);
		//删除资料时 同时删除相应附件
		removeAllAttach(boID);
        this.actionAttachment.setVisible(true);
        this.actionAttachment.setEnabled(true);
	}


	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
        String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
        //获得当前组织的所有下级组织的ids
        FilterInfo filter = new FilterInfo();
        if(mainQuery.getFilter()!=null){
        	filter = mainQuery.getFilter();
        }
        //获得通过查询得到的条件连接
        String mark = filter.getMaskString();
        Set childLongNumberSet = new HashSet();
        childLongNumberSet = getDownAllOrgIdSet(longNumber);
		childLongNumberSet.add(this.currentOrg.getId().toString());
		if(authorizedOrgs.size()!=0){
//			filter.getFilterItems().add(new FilterItemInfo("type.id", set,CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("org.id", authorizedOrgs,CompareType.INCLUDE));
		}
			
		filter.getFilterItems().add(
				new FilterItemInfo("org.id",childLongNumberSet,CompareType.INCLUDE));
		int i = filter.getFilterItems().size();
        if(mark==null){
        	mark = "( ";
        }else{
        	mark = mark +"and ";
        }
        //拼接查询连接	
//        filter.getFilterItems().add(new FilterItemInfo("isGroupFile",Boolean.TRUE));
//        filter.setMaskString(mark +"(#"+(i-2)+" and #"+(i-1)+")) or #"+i);
        filter.setMaskString(mark +"#"+(i-2)+" and #"+(i-1)+")");
        
//        filter.setMaskString(mark +" #"+(i-1));
		mainQuery.setFilter(filter);
//		this.refreshList();
		execQuery();
	}


	
	protected String getEditUIName() {
		return MarketInfoEditUI.class.getName();
	}

	// 新增窗口显示方式 页签显示
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketInfoFactory.getRemoteInstance();
	}

	private void buildTypeTree() throws Exception {
		authorizedOrgs = new HashSet();
		Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
				 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
		            OrgType.CostCenter, 
		            null,  null, null);
		if(orgs!=null){
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while(it.hasNext()){
				authorizedOrgs.add(it.next());
			}
		}
		FilterInfo filter = new FilterInfo();
        if (SysContext.getSysContext().getCurrentCtrlUnit() == null)
        {
            filter = new FilterInfo();
        }
        //组织隔离
//        filter.getFilterItems().add(
//                new FilterItemInfo(IFWEntityStruct.objectBase_CU, SysContext.getSysContext().getCurrentCtrlUnit()
//                        .getId().toString(), CompareType.EQUALS));
        //本城市公司只能看到集团下的类型和本城市公司建立的类型
        String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
        //获得当前组织的所有下级组织的ids
        Set childLongNumberSet = new HashSet();
        try {
        	childLongNumberSet = getDownOrgIdSet(longNumber);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		childLongNumberSet.add(this.currentOrg.getId().toString());
//        filter.getFilterItems().add(
//                new FilterItemInfo("org.id", SysContext.getSysContext().getCurrentOrgUnit()
//                        .getId().toString(), CompareType.EQUALS));
		//集团和区域可以查看所有下级城市公司的类型信息 同时下级城市组织可以查看集团的类型信息
        filter.getFilterItems().add(
                new FilterItemInfo("org.id", childLongNumberSet, CompareType.INCLUDE));
        //集团建立的类型下级城市公司可以查看
        filter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.SYS_CU_ID, CompareType.EQUALS));
        filter.getFilterItems().add(
                new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.DEF_CU_ID, CompareType.EQUALS));
        filter.getFilterItems().add(
        		new FilterItemInfo("isGroupFile",Boolean.TRUE));
        /**********添加过滤条件：是组织共享的文件也都显示  -by neo*********/
        filter.setMaskString("(#0 or #1 or #2) or #3");
//        filter.setMaskString("#0 or #1 or #2");
        
		TreeModel model = FDCClientHelper.createDataTree(MarketInfoTypeFactory
				.getRemoteInstance(), filter, "全部类型");
		this.treeMain.setModel(model);
		this.treeMain.setSelectionNode((DefaultKingdeeTreeNode)model.getRoot());
		set = new HashSet();
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode)model.getRoot();
//		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
//			MarketInfoTypeInfo type = (MarketInfoTypeInfo)((DefaultKingdeeTreeNode)model.getRoot()).getUserObject();
//			set.add(type.getId().toString());
//		}	
		nodeToSet(typeNode,set);		
	}
	//左边 类型树 值变化事件
	protected void treeType_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		//先清空原有set中的ids
		set = new HashSet();
		//获得当前节点下的所有id
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (typeNode == null) {
			return;
		}
		MarketInfoTypeInfo type = null;
		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
			type = (MarketInfoTypeInfo)typeNode.getUserObject();
			set.add(type.getId().toString());
		}
		nodeToSet(typeNode,set);
		this.tblMain.removeRows();
		this.getMainFilter();
//		this.actionAddNew.setEnabled(true);
//		fillTable();
	}
	//获得当前节点所有子节点id 放入Set中
	private void nodeToSet(DefaultKingdeeTreeNode typeNode,Set set){
		for(int i=0;i<typeNode.getChildCount();i++){
			if(typeNode.getChildAt(i)!=null && typeNode.getChildAt(i) instanceof DefaultKingdeeTreeNode){
				DefaultKingdeeTreeNode childNode = (DefaultKingdeeTreeNode)typeNode.getChildAt(i);
				if(childNode.getUserObject() instanceof MarketInfoTypeInfo){
					MarketInfoTypeInfo type = (MarketInfoTypeInfo)childNode.getUserObject();
					set.add(type.getId().toString());
				}
				nodeToSet(childNode,set);
			}
			
		}
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void fillTable() throws Exception {
//		this.mainQuery.setFilter(this.getMainFilter());
		this.tblMain.removeRows();
		this.refreshList();
	}
	public FilterInfo getMainFilter (){
		FilterInfo filter = new FilterInfo();
//		if(mainQuery.getFilter()!=null){
//			filter = mainQuery.getFilter();
//		}

        String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
        //获得当前组织的所有下级组织的ids
        Set childLongNumberSet = new HashSet();
        Set allOrg = new HashSet();
        try {
//        	childLongNumberSet = getDownOrgIdSet(longNumber);
        	allOrg = getDownAllOrgIdSet(longNumber);
		} catch (BOSException e) {
			e.printStackTrace();
		}
//		childLongNumberSet.add(this.currentOrg.getId().toString());
		allOrg.add(this.currentOrg.getId().toString());
		if(set.size()!=0){
			filter.getFilterItems().add(new FilterItemInfo("type.id", set,CompareType.INCLUDE));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("org.id",authorizedOrgs,CompareType.INCLUDE));	
//		filter.getFilterItems().add(
//				new FilterItemInfo("org.id",childLongNumberSet,CompareType.INCLUDE));
		if(allOrg.size() != 0){
			filter.getFilterItems().add(
					new FilterItemInfo("org.id",allOrg,CompareType.INCLUDE));
		}
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		/**********添加过滤条件：再根节点下，是组织共享的文件也都显示  -by neo*********/
		if(typeNode.getRoot() == typeNode){
			filter.getFilterItems().add(new FilterItemInfo("isGroupFile",Boolean.TRUE));
			filter.setMaskString("(#0 and #1 and #2) or #3");
		}else {
			filter.setMaskString("(#0 and #1 and #2)");
		}
		
		this.mainQuery.setFilter(filter);
		execQuery();
		return filter;
	}

	public void onLoad() throws Exception {
//		if(!MarketHelper.checkOrgPerm(this,this.currentOrg.getId().toString()))
//			this.abort();
		this.setUITitle("文件序时簿");
		getCtrlOrg();
//		buildTypeTree();
//		this.mainQuery.setFilter(this.getMainFilter());
        if (mainQuery == null)
        {
            mainQuery = new EntityViewInfo();
            setDataObject(mainQuery);
        }
//        this.mainQuery.setFilter(this.getMainFilter());
        
		super.onLoad();
		this.getMainFilter();
//		buildTypeTree();
//		fillTable();
		this.treeMain.setAutoscrolls(true);
		this.treeMain
				.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
					public void valueChanged(
							javax.swing.event.TreeSelectionEvent e) {
						try {
							treeType_valueChanged(e);
						} catch (Exception exc) {
							handUIException(exc);
						} finally {
						}
					}
				});
		
		//屏蔽部分无用控件
		this.actionAttachment.setEnabled(true);
		this.actionAttachment.setVisible(true);
		this.menuBiz.setVisible(false);
		this.menuBiz.setEnabled(false);
		this.actionGroupMoveTree.setEnabled(false);
		this.actionGroupMoveTree.setVisible(false);
		this.actionMoveTree.setEnabled(false);
		this.actionMoveTree.setVisible(false);
		FDCHelper.formatTableDate(this.tblMain,"createTime");
		FDCHelper.formatTableDate(this.tblMain,"lastUpdateTime");
	}
	//对当前选择的类型节点进行检查
	public static void checkSelectType(CoreUIObject ui,
			DefaultKingdeeTreeNode selectTreeNode) {
		//如果没有选中节点
		if (selectTreeNode == null) {
			MsgBox.showWarning(ui, "请选择文件类型！");
			SysUtil.abort();
		}
		//
		if (!(selectTreeNode.getUserObject() instanceof MarketInfoTypeInfo)) {
			MsgBox.showWarning(ui, "请选择文件类型！");
			SysUtil.abort();
		}
	}
	//获得当前选择的类型节点
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain
				.getLastSelectedPathComponent();
	}
	//把当前选择类型放入上下文，以备在新增市场数据时使用
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		if(getTypeSelectedTreeNode()!=null){
			uiContext.put("type", getTypeSelectedTreeNode().getUserObject());
		}
	}
	//获得当前节点所有下级管理单元id
	protected Set getDownOrgIdSet(String longNumber) throws BOSException {
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber",longNumber + "!%",
						CompareType.LIKE));
		orgView.setFilter(orgFilter);
		ICtrlUnit iCtrlUnit = CtrlUnitFactory.getRemoteInstance();
		CtrlUnitCollection ctrlUnitColl = iCtrlUnit.getCtrlUnitCollection(orgView);
		Set orgIdSet = new HashSet(ctrlUnitColl.size());
		for (int i = 0, n = ctrlUnitColl.size(); i < n; i++) {
			orgIdSet.add(ctrlUnitColl.get(i).getId().toString());
		}
//		ICompanyOrgUnit iComp = CompanyOrgUnitFactory.getRemoteInstance();
//		CompanyOrgUnitCollection ancestorComps = iComp
//				.getCompanyOrgUnitCollection(orgView);
//		Set orgIdSet = new HashSet(ancestorComps.size());
//		for (int i = 0, n = ancestorComps.size(); i < n; i++) {
//			orgIdSet.add(ancestorComps.get(i).getId().toString());
//		}
		return orgIdSet;
	}
	//获得当前节点所有下级组织单元id
	protected Set getDownAllOrgIdSet(String longNumber) throws BOSException {
		EntityViewInfo orgView = new EntityViewInfo();
		FilterInfo orgFilter = new FilterInfo();
		orgFilter.getFilterItems().add(
				new FilterItemInfo("longNumber",longNumber + "!%",
						CompareType.LIKE));
		orgView.setFilter(orgFilter);
		IFullOrgUnit iFullOrg  = FullOrgUnitFactory.getRemoteInstance();
		FullOrgUnitCollection coll = iFullOrg.getFullOrgUnitCollection(orgView);
//		ICtrlUnit iCtrlUnit = CtrlUnitFactory.getRemoteInstance();
//		CtrlUnitCollection ctrlUnitColl = iCtrlUnit.getCtrlUnitCollection(orgView);
		Set orgIdSet = new HashSet(coll.size());
		for (int i = 0, n = coll.size(); i < n; i++) {
			orgIdSet.add(coll.get(i).getId().toString());
		}

		return orgIdSet;
	}
	//子类必须重载实现，否则不能实现定位与移动。
	protected IObjectPK getSelectedTreeKeyValue() {

		MarketInfoInfo marketInfo = null;
		IObjectPK detail = new ObjectUuidPK(getSelectedKeyValue());
		if (detail == null) {
			return null;
		}
		try {
			marketInfo = (MarketInfoInfo) getBizInterface().getValue(
					detail);
		} catch (Exception e) {
			handUIException(e);
		}
		if (marketInfo.getType() == null
				|| marketInfo.getType().getId() == null) {
			this.treeMain
					.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain
							.getModel().getRoot());
			abort();
		}
		return new ObjectUuidPK(marketInfo.getType().getId());
	
	}
	//类型编辑界面
	protected String getGroupEditUIName() {
		return MarketInfoTypeEditUI.class.getName();
	}

	protected String getQueryFieldName() {
		return "type.id";
	}
	//左边树形结构远程实例
	protected ITreeBase getTreeInterface() throws Exception {
		return MarketInfoTypeFactory.getRemoteInstance();
	}

	public void actionGroupRemove_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
		checkCtrlUnit();
		ArrayList list = new ArrayList();
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
		if (typeNode == null) {
			return;
		}
		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
			MarketInfoTypeInfo type = (MarketInfoTypeInfo)typeNode.getUserObject();
			if(type.isIsDefault()){
				MsgBox.showWarning(this,"系统预设类型不允许删除！");
				SysUtil.abort();
			}
			list.add(type.getId().toString());
		}
		MarketInfoTypeInfo type = null;
		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
			type = (MarketInfoTypeInfo)typeNode.getUserObject();
		if(type.getOrg()!=null 
			&& !(type.getOrg().getId().toString().equals(currentOrg.getId().toString()))){
			MsgBox.showWarning(this,"不能删除其他组织建立的类型！");
			SysUtil.abort();
			}
		}
//		ArrayList list = getSelectedIdValues();
		int size = list.size();
		String[] tables = new String[]{"t_cdb_marketinfo"};
		IMarketInfoType iMarketType = MarketInfoTypeFactory.getRemoteInstance();
		for(int i = 0; i< size;i++){
			String id = list.get(i).toString();
			if(iMarketType.exists("select id where parent.id ='"+id+"' ")){
				MsgBox.showWarning(this,"有明细节点，请先删除明细数据！");
				SysUtil.abort();
			}
			Object[] newTables = iMarketType.getRelateData(id, tables);
			if(newTables.length>0){
				MsgBox.showWarning("类型已经被引用,不能删除!");
				SysUtil.abort();
			}
		}
		super.actionGroupRemove_actionPerformed(e);

	}

	public void actionGroupAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkCtrlUnit();
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
		if (typeNode == null) {
			return;
		}
		MarketInfoTypeInfo type = null;
		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
			type = (MarketInfoTypeInfo)typeNode.getUserObject();
		if(type.getOrg()!=null 
			&& !(type.getOrg().getId().toString().equals(currentOrg.getId().toString()))){
			MsgBox.showWarning(this,"不能在其他组织建立的类型下新增类型！");
			SysUtil.abort();
			}
		}
		super.actionGroupAddNew_actionPerformed(e);
	}

	public void actionGroupEdit_actionPerformed(ActionEvent e) throws Exception {
		checkCtrlUnit();
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
		if (typeNode == null) {
			return;
		}
		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
			MarketInfoTypeInfo type = (MarketInfoTypeInfo)typeNode.getUserObject();
			if(type.isIsDefault()){
				MsgBox.showWarning(this,"系统预设类型不允许修改！");
				SysUtil.abort();
			}
		}
		MarketInfoTypeInfo type = null;
		if(typeNode.getUserObject() instanceof MarketInfoTypeInfo){
			type = (MarketInfoTypeInfo)typeNode.getUserObject();
		if(type.getOrg()!=null 
			&& !(type.getOrg().getId().toString().equals(currentOrg.getId().toString()))){
			MsgBox.showWarning(this,"不能修改其他组织建立的类型！");
			SysUtil.abort();
			}
		}
		super.actionGroupEdit_actionPerformed(e);
	}
	//已有变化事件，重载掉父类的
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		
	}
	//由于原有构建树只显示当前组织建立的，不能显示其下级建立的类型 故重载掉
	//2008-10-28 由于initTree涉及到很多父类方法，不重载 原构建树buildTypeTree()不使用
	protected void initTree() throws Exception {
		super.initTree();
		set = new HashSet();
		TreeModel model = (DefaultTreeModel) this.treeMain.getModel();
		DefaultKingdeeTreeNode typeNode = (DefaultKingdeeTreeNode)model.getRoot();
		nodeToSet(typeNode,set);	
	}

	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected();
        if (boID == null)
        {
            return;
        }
		if(this.actionEdit.isEnabled()){
			acm.showAttachmentListUIByBoID(boID,"",this,true,null);
		}else{
			acm.showAttachmentListUIByBoID(boID,"",this,false,null);
		}
	}
	//打开附件内容
	private void viewAttachmentContent(String attachmentId) throws Exception{
		checkSelected();		
		getFileGetter().viewAttachment(attachmentId,this);
	}
	//获得资料的第一个附件id
	private String getFirstAttachmentId(String marketInfoId) throws BOSException{
		AttachmentInfo firstAttachment = null;
		if(marketInfoId != null && !marketInfoId.equals("")){
	        EntityViewInfo viewAttch = new EntityViewInfo();
	        viewAttch.getSelector().add("*");
	        viewAttch.getSelector().add("attachment.*");
	        FilterInfo filter = new FilterInfo();
	        filter.getFilterItems().add(
	        	new FilterItemInfo("boID",marketInfoId));
	        viewAttch.setFilter(filter);
	        BoAttchAssoCollection coll = BoAttchAssoFactory
	        	.getRemoteInstance().getBoAttchAssoCollection(viewAttch);
	        if(coll == null || coll.size() ==0){
	        	MsgBox.showWarning(this,"当前资料没有相关附件！");
	        	SysUtil.abort();
	        }
	        if(coll != null ){
	        	for(int i =0;i<coll.size();i++){
	            BoAttchAssoInfo info = coll.get(i);
	            if(info != null && info.getAttachment()!=null){
	            	if(firstAttachment == null){
	            		firstAttachment = info.getAttachment();
	            	}else if(firstAttachment != null 
	            			&& firstAttachment.getCreateTime().after(info.getAttachment().getCreateTime())){
	            		firstAttachment = info.getAttachment();
	            	}
	            	}
	        	}
	        	return firstAttachment.getId().toString();
	        }else{
	        	return null;
	        }
	    }
		return null;
	}
	
    private FileGetter getFileGetter() throws Exception {
        if(fg==null) fg=new FileGetter((IAttachment)AttachmentFactory.getRemoteInstance(),AttachmentFtpFacadeFactory.getRemoteInstance());
        return fg;
    }
    //打开附件
	public void actionViewContent_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
        String id = this.getFirstAttachmentId(getSelectedKeyValue());        
        if(id != null){
        	viewAttachmentContent(id);
        }
	}

	//双击打开附件 点击查看文件信息
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		if(e.getActionCommand() != null && !e.getActionCommand().equals("") 
				&& e.getActionCommand().equals("Double Clicked")){
	        String id = this.getFirstAttachmentId(getSelectedKeyValue());        
	        if(id != null){
	        	viewAttachmentContent(id);
	        }
		}else{
			super.actionView_actionPerformed(e);
		}

	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		fillTable();
	}
	
	//删除单据时候删除相应附件
    protected void removeAllAttach(String boID){
        RealModeIDList idListAll = new RealModeIDList();
		List list;
		try {
			list = this.getAllAttachment(boID);
			for(int i =0 ; i< list.size(); i++){
				AttachmentInfo info;
				if(list.get(i) instanceof AttachmentInfo){
					info = (AttachmentInfo)list.get(i);
					idListAll.add(info.getId().toString());
				}
			}
			if(idListAll.size() != 0){
				removeAttachment(boID,idListAll);
			}
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}

    }
	protected List getAllAttachment(String id) throws Exception{
		List list = new ArrayList();
		if(id != null){
	        EntityViewInfo viewAttch = new EntityViewInfo();
	        viewAttch.getSelector().add("*");
	        viewAttch.getSelector().add("attachment.*");
	        FilterInfo filter = new FilterInfo();
	        filter.getFilterItems().add(
	        	new FilterItemInfo("boID",id));
	        viewAttch.setFilter(filter);
	        viewAttch.getSorter().add(new SorterItemInfo("attachment.createTime"));
	        BoAttchAssoCollection coll = BoAttchAssoFactory
	        	.getRemoteInstance().getBoAttchAssoCollection(viewAttch);
	        if(coll == null || coll.size() ==0){
	        	return null;
	        }else{
	        	for(int i =0;i<coll.size();i++){
		            BoAttchAssoInfo info = coll.get(i);
		            if(info != null && info.getAttachment()!=null){
		            	list.add(info.getAttachment());
		            }
		        }
	        }
		}
		return list;
	}
	
    /**
     * 删除附件
     * @param boID 对象关联ID 
     * @param idList 附件ids
     * @throws EASBizException
     * @throws BOSException
     */
    protected void removeAttachment(String boID,IIDList idList) throws EASBizException, BOSException {
        AttachmentClientManager attachmentClientManager = AttachmentManagerFactory.getClientManager();
        attachmentClientManager.deleteAssoAttachment(boID,idList);
    }
    
    protected FilterInfo getDefaultFilterForTree()
    {	
    	FullOrgUnitInfo orgUnit = currentOrg.castToFullOrgUnitInfo();
    	getUpCU(orgUnit);
    	
    	FilterInfo filter = new FilterInfo();
    	if (SysContext.getSysContext().getCurrentCtrlUnit() == null)
    	{
    		filter = new FilterInfo();
    	}
    	//本城市公司只能看到集团下的类型和本城市公司建立的类型
    	String longNumber = SysContext.getSysContext().getCurrentOrgUnit().getLongNumber();
    	//获得当前组织的所有下级组织的ids
    	Set childLongNumberSet = new HashSet();
    	try {
    		childLongNumberSet = getDownOrgIdSet(longNumber);
    	} catch (BOSException e) {
    		e.printStackTrace();
    	}
    	childLongNumberSet.add(this.currentOrg.getId().toString());

    	//集团和区域可以查看所有下级城市公司的类型信息 同时下级城市组织可以查看集团的类型信息
    	filter.getFilterItems().add(
           new FilterItemInfo("org.id", childLongNumberSet, CompareType.INCLUDE));
    	//用户组织权限隔离
		filter.getFilterItems().add(
				new FilterItemInfo("org.id",authorizedOrgs,CompareType.INCLUDE));	
    	//集团建立的类型下级城市公司可以查看
    	filter.getFilterItems().add(
           new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.SYS_CU_ID, CompareType.EQUALS));
    	filter.getFilterItems().add(
           new FilterItemInfo(IFWEntityStruct.objectBase_CU, OrgConstants.DEF_CU_ID, CompareType.EQUALS));
    	filter.setMaskString("(#0 and #1) or #2 or #3");
    	if(upCU.size() != 0){
    		//当前组织所在的CU
    		filter.getFilterItems().add(
        		new FilterItemInfo("org.id", upCU, CompareType.INCLUDE));
        	filter.setMaskString("(#0 and #1) or #2 or #3 or #4");
    	}
        return filter;
    }
    protected String getRootName()
    {
        return "全部类型";
    }
    private void getCtrlOrg(){
		authorizedOrgs = new HashSet();
		Map orgs;
		try {
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}

    }
    public void checkCtrlUnit(){
    	if(!currentOrg.getId().toString().equals(OrgConstants.SYS_CU_ID)
    			&& !currentOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
//        	FullOrgUnitInfo fullOrg = this.currentOrg.castToFullOrgUnitInfo();
//        	String id = "";
//        	if(fullOrg.getCU() != null){
//        		id = fullOrg.getCU().getId().toString();
//        	}        	
//        	CtrlUnitInfo info = null;
//        	try {
//        		if(id != null && !id.equals("")){
//        			info = CtrlUnitFactory.getRemoteInstance()
//    				.getCtrlUnitInfo(new ObjectUuidPK(BOSUuid.read(id)));
//        		}
//    		} catch (EASBizException e) {
//    			e.printStackTrace();
//    		} catch (BOSException e) {
//    			e.printStackTrace();
//    		} catch (UuidException e) {
//    			e.printStackTrace();
//    		}
        	if(!this.currentOrg.isIsCU() ){
        		MsgBox.showWarning(this,"只有集团和管理单元才可以维护类型！");
        		SysUtil.abort();
        	}
    	}

    }
    protected void getUpCU(FullOrgUnitInfo orgUnit){
    	upCU.clear();
    	if(orgUnit == null){
    		return;
    	}
		if(orgUnit.isIsCU()){
			upCU.add(orgUnit.getId().toString());
			return;
		}
    	if(orgUnit.getParent() != null){

    		String id = orgUnit.getParent().getId().toString();
    		try {
				FullOrgUnitInfo info = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(BOSUuid.read(id)));
				if(info.isIsCU()){
					upCU.add(info.getId().toString());
					return;
				}else{
					getUpCU(info);
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			}
    	}
    }
}