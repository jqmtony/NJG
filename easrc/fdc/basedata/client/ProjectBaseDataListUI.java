package com.kingdee.eas.fdc.basedata.client;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.OperationPhasesFactory;
import com.kingdee.eas.fdc.basedata.OperationPhasesInfo;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class ProjectBaseDataListUI extends AbstractProjectBaseDataListUI {
	private static final Logger logger = CoreUIObject.getLogger(ProjectBaseDataListUI.class);

	private ProjectBaseEditUI projectBaseEditUI=null;
	private OperationPhasesEditUI operationPhasesEditUI =null;
	private boolean isRefresh=false;
	public ProjectBaseDataListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		this.actionAddNew.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_new"));
		this.actionEdit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_edit"));
		this.actionRemove.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_delete"));
		this.actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		this.actionUnAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_UNAUDIT);
	}
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);
		DefaultKingdeeTreeNode node=getSelectedTreeNode();
		UIContext uiContext = new UIContext(this);
		if(node.getUserObject() instanceof FullOrgUnitInfo){
			ProjectBaseInfo newInfo=new ProjectBaseInfo();
			BOSUuid newId=BOSUuid.create(newInfo.getBOSType());
			uiContext.put("newId",newId);
			uiContext.put("cityProject",node.getUserObject());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProjectBaseEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
			if(ProjectBaseFactory.getRemoteInstance().exists(new ObjectUuidPK(newId))){
				refresh(newId.toString(),newInfo);
			}
		}else if(node.getUserObject() instanceof ProjectBaseInfo){
			OperationPhasesInfo newInfo=new OperationPhasesInfo();
			BOSUuid newId=BOSUuid.create(newInfo.getBOSType());
			uiContext.put("newId",newId);
			uiContext.put("projectBase",node.getUserObject());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(OperationPhasesEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
			if(OperationPhasesFactory.getRemoteInstance().exists(new ObjectUuidPK(newId))){
				refresh(newId.toString(),newInfo);
			}
		}else if(node.getUserObject() instanceof OperationPhasesInfo){
			OperationPhasesInfo newInfo=new OperationPhasesInfo();
			BOSUuid newId=BOSUuid.create(newInfo.getBOSType());
			uiContext.put("newId",newId);
			OperationPhasesInfo parent = (OperationPhasesInfo) node.getUserObject();
			uiContext.put("projectBase",parent.getProjectBase());
			uiContext.put("parent",parent);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(OperationPhasesEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
			if(OperationPhasesFactory.getRemoteInstance().exists(new ObjectUuidPK(newId))){
				refresh(newId.toString(),newInfo);
			}
		}
	}
	protected void refresh(String id,IObjectValue value) throws Exception{
		isRefresh=true;
		initTree();
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
		DefaultKingdeeTreeNode selectNode=selectNode(id,root,value);
		isRefresh=false;
		if(selectNode!=null){
			this.treeMain.setSelectionNode(selectNode);
			this.treeMain.expandPath(this.treeMain.getSelectionPath());
		}
	}
	public DefaultKingdeeTreeNode selectNode(String id,DefaultKingdeeTreeNode node,IObjectValue value) throws EASBizException, BOSException{
		if(id==null) return null;
		DefaultKingdeeTreeNode selectNode=null;
		Object obj = node.getUserObject();
		if(value instanceof FullOrgUnitInfo){
			if(obj!=null&&obj instanceof FullOrgUnitInfo){
				if(id.equals(((FullOrgUnitInfo)obj).getId().toString())){
					return node;
				}
			}
		}else if(value instanceof ProjectBaseInfo){
			if(obj!=null&&obj instanceof ProjectBaseInfo){
				if(id.equals(((ProjectBaseInfo)obj).getId().toString())){
					return node;
				}
			}
		}else if(value instanceof OperationPhasesInfo){
			if(obj!=null&&obj instanceof OperationPhasesInfo){
				if(id.equals(((OperationPhasesInfo)obj).getId().toString())){
					return node;
				}
			}
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			selectNode=selectNode(id,(DefaultKingdeeTreeNode) node.getChildAt(i),value);
			if(selectNode!=null) return selectNode;
		}
		return selectNode;
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);
		DefaultKingdeeTreeNode node=getSelectedTreeNode();
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		
		if(node.getUserObject() instanceof ProjectBaseInfo){
			ProjectBaseInfo projectbaseInfo = (ProjectBaseInfo) node.getUserObject();
			String id = projectbaseInfo.getId().toString();
			projectbaseInfo=ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = projectbaseInfo.getState();
			
			if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}
		}else if(node.getUserObject() instanceof OperationPhasesInfo){
			OperationPhasesInfo operationPhasesInfo = (OperationPhasesInfo) getSelectedTreeNode().getUserObject();
			String id = operationPhasesInfo.getId().toString();
			operationPhasesInfo=OperationPhasesFactory.getRemoteInstance().getOperationPhasesInfo(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = operationPhasesInfo.getState();
			
			if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}
		}
		UIContext uiContext = new UIContext(this);
		if(node.getUserObject() instanceof ProjectBaseInfo){
			ProjectBaseInfo projectbaseInfo = (ProjectBaseInfo) node.getUserObject();
			String id = projectbaseInfo.getId().toString();
			uiContext.put("ID",id);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ProjectBaseEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}else if(node.getUserObject() instanceof OperationPhasesInfo){
			OperationPhasesInfo operationPhasesInfo = (OperationPhasesInfo) node.getUserObject();
			String id = operationPhasesInfo.getId().toString();
			uiContext.put("ID",id);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(OperationPhasesEditUI.class.getName(), uiContext, null, OprtState.EDIT);
			uiWindow.show();
		}
		treeMain_valueChanged(null);
	}
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);
		DefaultKingdeeTreeNode node=getSelectedTreeNode();
		DefaultKingdeeTreeNode parentNode=(DefaultKingdeeTreeNode) node.getParent();
		String parentid=null;
		IObjectValue value=(IObjectValue) parentNode.getUserObject();
		if(parentNode!=null&&parentNode.getUserObject()!=null){
			if(parentNode.getUserObject() instanceof FullOrgUnitInfo){
				parentid=((FullOrgUnitInfo)parentNode.getUserObject()).getId().toString();
			}else if(parentNode.getUserObject() instanceof ProjectBaseInfo){
				parentid=((ProjectBaseInfo)parentNode.getUserObject()).getId().toString();
			}else if(parentNode.getUserObject() instanceof OperationPhasesInfo){
				parentid=((OperationPhasesInfo)parentNode.getUserObject()).getId().toString();
			}
		}
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		if(node.isLeaf()){
			FilterInfo filter=new FilterInfo();
			if(node.getUserObject() instanceof ProjectBaseInfo){
				ProjectBaseInfo projectbaseInfo = (ProjectBaseInfo) node.getUserObject();
				String id = projectbaseInfo.getId().toString();
				projectbaseInfo=ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(id),sels);
				FDCBillStateEnum state = projectbaseInfo.getState();
				
				if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
					FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
					SysUtil.abort();
				}
				filter.getFilterItems().add(new FilterItemInfo("projectBase.id",id));
//				if(SellProjectFactory.getRemoteInstance().exists(filter)){
//					FDCMsgBox.showWarning("已经被售楼项目引用，不能进行删除操作！");
//					SysUtil.abort();
//				}
				if(confirmRemove(this)){
					ProjectBaseFactory.getRemoteInstance().delete(new ObjectUuidPK(id));
				}
			}else if(node.getUserObject() instanceof OperationPhasesInfo){
				OperationPhasesInfo operationPhasesInfo = (OperationPhasesInfo) getSelectedTreeNode().getUserObject();
				String id = operationPhasesInfo.getId().toString();
				operationPhasesInfo=OperationPhasesFactory.getRemoteInstance().getOperationPhasesInfo(new ObjectUuidPK(id),sels);
				FDCBillStateEnum state = operationPhasesInfo.getState();
				
				if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
					FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
					SysUtil.abort();
				}
				filter.getFilterItems().add(new FilterItemInfo("projectBase.id",id));
				if(CurProjectFactory.getRemoteInstance().exists(filter)){
					FDCMsgBox.showWarning("已经被工程项目引用，不能进行删除操作！");
					SysUtil.abort();
				}
				if(confirmRemove(this)){
					OperationPhasesFactory.getRemoteInstance().delete(new ObjectUuidPK(id));
				}
			}
			FDCClientUtils.showOprtOK(this);
			refresh(parentid,value);
		}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);
		DefaultKingdeeTreeNode node=getSelectedTreeNode();
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		if(node.getUserObject() instanceof ProjectBaseInfo){
			ProjectBaseInfo projectbaseInfo = (ProjectBaseInfo) node.getUserObject();
			String id = projectbaseInfo.getId().toString();
			projectbaseInfo=ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = projectbaseInfo.getState();
			
			if (!FDCBillStateEnum.SUBMITTED.equals(state)) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				SysUtil.abort();
			}
			ProjectBaseFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}else if(node.getUserObject() instanceof OperationPhasesInfo){
			OperationPhasesInfo operationPhasesInfo = (OperationPhasesInfo) getSelectedTreeNode().getUserObject();
			String id = operationPhasesInfo.getId().toString();
			operationPhasesInfo=OperationPhasesFactory.getRemoteInstance().getOperationPhasesInfo(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = operationPhasesInfo.getState();
			
			if (!FDCBillStateEnum.SUBMITTED.equals(state)) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				SysUtil.abort();
			}
			OperationPhasesFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		treeMain_valueChanged(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkTreeNodeSelected(e);
		DefaultKingdeeTreeNode node=getSelectedTreeNode();
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		if(node.getUserObject() instanceof ProjectBaseInfo){
			ProjectBaseInfo projectbaseInfo = (ProjectBaseInfo) node.getUserObject();
			String id = projectbaseInfo.getId().toString();
			projectbaseInfo=ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = projectbaseInfo.getState();
			
			if (!FDCBillStateEnum.AUDITTED.equals(state)) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				SysUtil.abort();
			}
			ProjectBaseFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		}else if(node.getUserObject() instanceof OperationPhasesInfo){
			OperationPhasesInfo operationPhasesInfo = (OperationPhasesInfo) getSelectedTreeNode().getUserObject();
			String id = operationPhasesInfo.getId().toString();
			operationPhasesInfo=OperationPhasesFactory.getRemoteInstance().getOperationPhasesInfo(new ObjectUuidPK(id),sels);
			FDCBillStateEnum state = operationPhasesInfo.getState();
			
			if (!FDCBillStateEnum.AUDITTED.equals(state)) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				SysUtil.abort();
			}
			OperationPhasesFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
		treeMain_valueChanged(null);
	}
	private void initTree() throws Exception {
		ProjectTreeBuilderForXH projectTreeBuilder = new ProjectTreeBuilderForXH();
		projectTreeBuilder.build(this, this.treeMain, this.actionOnLoad);
		if (this.treeMain.getRowCount() > 0) {
			this.treeMain.setSelectionRow(0);
			this.treeMain.expandPath(this.treeMain.getSelectionPath());
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent treeselectionevent) throws Exception {
		if(isRefresh)return;
		DefaultKingdeeTreeNode node=getSelectedTreeNode();
		if(node!=null){
			this.actionAddNew.setEnabled(true);
			if(!(node.getUserObject() instanceof ProjectBaseInfo||node.getUserObject() instanceof OperationPhasesInfo)){
				this.actionAudit.setEnabled(false);
				this.actionUnAudit.setEnabled(false);
				this.actionRemove.setEnabled(false);
				this.actionEdit.setEnabled(false);
			}else{
				this.actionAudit.setEnabled(true);
				this.actionUnAudit.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionEdit.setEnabled(true);
			}
			if(!node.isLeaf()){
				this.actionRemove.setEnabled(false);
			}
			if(node.getUserObject() instanceof OrgStructureInfo){
				this.actionAddNew.setEnabled(false);
			}
		}
		if(this.projectBaseEditUI!=null){
			this.projectBaseEditUI.destroyWindow();
		}
		if(this.operationPhasesEditUI!=null){
			this.operationPhasesEditUI.destroyWindow();
		}
		if(node!=null){
			if(node.getUserObject() instanceof ProjectBaseInfo){
				ProjectBaseInfo projectbaseInfo = (ProjectBaseInfo) getSelectedTreeNode().getUserObject();
				String id = projectbaseInfo.getId().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID",id);
				this.projectBaseEditUI=(ProjectBaseEditUI) UIFactoryHelper.initUIObject(ProjectBaseEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				this.scrollPane.setViewportView(projectBaseEditUI);
			}else if(node.getUserObject() instanceof OperationPhasesInfo){
				OperationPhasesInfo operationPhasesInfo = (OperationPhasesInfo) getSelectedTreeNode().getUserObject();
				String id = operationPhasesInfo.getId().toString();
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID",id);
				this.operationPhasesEditUI=(OperationPhasesEditUI) UIFactoryHelper.initUIObject(OperationPhasesEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				this.scrollPane.setViewportView(operationPhasesEditUI);
			}else{
				this.projectBaseEditUI=null;
				this.operationPhasesEditUI=null;
				this.scrollPane.setViewportView(null);
			}
		}else{
			this.projectBaseEditUI=null;
			this.operationPhasesEditUI=null;
			this.scrollPane.setViewportView(null);
		}
	}
	public DefaultKingdeeTreeNode getSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) this.treeMain.getLastSelectedPathComponent();
	}
	public void checkTreeNodeSelected(ActionEvent e) {
		DefaultKingdeeTreeNode treeNode = getSelectedTreeNode();
		if(treeNode == null){
			MsgBox.showConfirm2(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Leaf_MustSelected"));
            SysUtil.abort();
        }else{
        	return;
        }
	}
}