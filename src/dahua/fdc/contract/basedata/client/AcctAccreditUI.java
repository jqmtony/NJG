/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.KDTree.DynamicKingdeeUtilTreeNode;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.RoleInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.AcctAccreditFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeCollection;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditSchemeInfo;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserCollection;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditUserInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class AcctAccreditUI extends AbstractAcctAccreditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AcctAccreditUI.class);
    private String rootSchemeId="kX2MAgEeEADgAAAAwKgQ5GcPHIU=";
    /**
     * output class constructor
     */
    public AcctAccreditUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
//        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
//        super.tblMain_tableSelectChanged(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
		//        super.treeMain_valueChanged(e);
    }

    /**
     * output actionAddUser_actionPerformed
     */
    public void actionAddUser_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddUser_actionPerformed(e);
        AcctAccreditSchemeInfo selectSchemeInfo = getSelectSchemeInfo();
		if(selectSchemeInfo!=null&&selectSchemeInfo.getId().toString().equals(rootSchemeId)){
			FDCMsgBox.showWarning(this, "请先选择方案");
			SysUtil.abort();
		}
		prmt = getPrmtUser();
		prmt.setDataBySelector();
        if(prmt.getValue()!=null){
        	Object[] objs=(Object[])prmt.getValue();
        	for(int i=0;i<objs.length;i++){
        		IRow row = tblUser.addRow();
        		RoleInfo info = (RoleInfo) objs[i];
        		AcctAccreditUserInfo user=new AcctAccreditUserInfo();
        		user.setId(BOSUuid.create(user.getBOSType()));
        		user.setRole(info);
        		user.setScheme(selectSchemeInfo);
        		row.setUserObject(user);
        		loadUserRow(row);
        	}
        }
        
    }

    /**
     * output actionDelUser_actionPerformed
     */
    public void actionDelUser_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelUser_actionPerformed(e);
        int[] selectedRows = KDTableUtil.getSelectedRows(tblUser);
        for(int i=selectedRows.length-1;i>=0;i--){
        	tblUser.removeRow(selectedRows[i]);
        }
    }

    /**
     * output actionAddScheme_actionPerformed
     */
    public void actionAddScheme_actionPerformed(ActionEvent e) throws Exception
    {
    	AcctAccreditSchemeUI.showUI(this, null, OprtState.ADDNEW);
    	refreshSchemeTree();
    }

    /**
     * output actionDelScheme_actionPerformed
     */
    public void actionDelScheme_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelScheme_actionPerformed(e);
    	AcctAccreditSchemeInfo scheme = getSelectSchemeInfo();
    	if(scheme==null||(scheme!=null&&scheme.getId().toString().equals(rootSchemeId))){
    		FDCMsgBox.showError(this, "请选择方案");
    		SysUtil.abort();
    	}
    	if(tblMain.getRowCount() != 0){
    		FDCMsgBox.showError(this, "请先删除科目!");
    		SysUtil.abort();
    	}
    	if(FDCMsgBox.showConfirm2New(this, "请确认是否删除方案？")==FDCMsgBox.YES){
    		AcctAccreditSchemeFactory.getRemoteInstance().delete(new ObjectUuidPK(scheme.getId().toString()));
    		refreshSchemeTree();
    		FDCMsgBox.showInfo(this, "方案已删除");
    	}
    }

    /**
     * output actionViewScheme_actionPerformed
     */
    public void actionViewScheme_actionPerformed(ActionEvent e) throws Exception
    {
    	AcctAccreditSchemeInfo scheme = getSelectSchemeInfo();
    	if(scheme==null||(scheme!=null&&scheme.getId().toString().equals(rootSchemeId))){
    		FDCMsgBox.showError(this, "请选择方案");
    		SysUtil.abort();
    	}
    	
    	AcctAccreditSchemeUI.showUI(this, scheme.getId().toString(), OprtState.VIEW);
    	refreshSchemeTree();
    }

    /**
     * output actionEditScheme_actionPerformed
     */
    public void actionEditScheme_actionPerformed(ActionEvent e) throws Exception
    {
    	AcctAccreditSchemeInfo scheme = getSelectSchemeInfo();
    	if(scheme==null||(scheme!=null&&scheme.getId().toString().equals(rootSchemeId))){
    		FDCMsgBox.showError(this, "请选择方案");
    		SysUtil.abort();
    	}
    	AcctAccreditSchemeUI.showUI(this, scheme.getId().toString(), OprtState.EDIT);
    	refreshSchemeTree();
    }

    public void actionAssignAcct_actionPerformed(ActionEvent e) throws Exception {
        AcctAccreditSchemeInfo scheme = getSelectSchemeInfo();
		if(scheme!=null&&scheme.getId().toString().equals(rootSchemeId)){
			FDCMsgBox.showWarning(this, "请先选择方案");
			SysUtil.abort();
		}
    	if(scheme!=null&&scheme.getId()!=null){
    		AcctAccreditAssignUI.showUI(this, scheme.getId().toString(), OprtState.EDIT);
    	}else{
    		AcctAccreditAssignUI.showUI(this, null, OprtState.EDIT);
    	}
    }
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	
        AcctAccreditSchemeInfo selectSchemeInfo = getSelectSchemeInfo();
		if(selectSchemeInfo!=null&&selectSchemeInfo.getId().toString().equals(rootSchemeId)){
			FDCMsgBox.showWarning(this, "请先选择方案");
			SysUtil.abort();
		}
    	super.actionSave_actionPerformed(e);
    	CoreBaseCollection users=new CoreBaseCollection();
    	Set set=new HashSet();//用于判断是否存在重复
    	for(int i=0;i<tblUser.getRowCount();i++){
    		AcctAccreditUserInfo info=(AcctAccreditUserInfo)tblUser.getRow(i).getUserObject();
    		users.add(info);
    	}
    	if(users.size()==0){
    		AcctAccreditUserInfo info=new AcctAccreditUserInfo();
    		info.setScheme(selectSchemeInfo);
    		users.add(info);
    	}
    	
    	AcctAccreditUserFactory.getRemoteInstance().save(users);
    	FDCMsgBox.showInfo(this,"保存成功");
    }

    
    protected void initTable() {
    	super.initTable();
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblUser.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	tblUser.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
    	tblMain.getColumn("acctNumber").setRenderer(FDCRenderHelper.getLongNumberRender());
    	tblMain.getStyleAttributes().setLocked(true);
    	tblUser.getStyleAttributes().setLocked(true);
    }
    protected void fillTable() throws Exception {
    	fetchData();
    	fillAcctTable();
    	fillUserTable();
    }
    private void fillAcctTable()throws Exception {
    	tblMain.removeRows(false);
    	CostAccountCollection c=getCostAccunts();
    	if(c==null||c.size()==0){
    		return;
    	}
    	int maxLevel=getMaxLevel();
    	tblMain.getTreeColumn().setDepth(maxLevel);
    	for(int i=0;i<c.size();i++){
    		CostAccountInfo info=c.get(i);
    		IRow row=tblMain.addRow();
    		row.setTreeLevel(info.getLevel()-1);
    		row.getCell("id").setValue(info.getId().toString());
    		row.getCell("acctNumber").setValue(info.getLongNumber());
    		row.getCell("acctName").setValue(info.getName());
    	}
    	
    }
    private void fillUserTable()throws Exception {
    	tblUser.removeRows(false);
    	AcctAccreditUserCollection acctAccreditUsers=getAcctAccreditUsers();
    	if(acctAccreditUsers==null||acctAccreditUsers.size()==0){
    		return;
    	}
    	for (Iterator iter = acctAccreditUsers.iterator(); iter.hasNext();) {
			AcctAccreditUserInfo info = (AcctAccreditUserInfo) iter.next();
			if (info.getRole() == null) {
				continue;
			}
			IRow row=tblUser.addRow();
			row.setUserObject(info);
			loadUserRow(row);
		}
    }

    private void loadUserRow(IRow row){
    	AcctAccreditUserInfo info =(AcctAccreditUserInfo)row.getUserObject();
    	if(info==null){
    		return;
    	}
		row.getCell("id").setValue(info.getId().toString());
		if (info.getRole() != null) {
			row.getCell("roleNumber").setValue(info.getRole().getNumber());
			row.getCell("roleName").setValue(info.getRole().getName());
		}
    }
    private Map dataMap=new HashMap();
    protected void fetchData() throws Exception {
    	Map param=new HashMap();
    	AcctAccreditSchemeInfo selectSchemeInfo = getSelectSchemeInfo();
    	if(selectSchemeInfo==null){
    		//空方案没有数据
    		return;
//    		param.put("schemeId", null);
    	}else{
    		param.put("schemeId", selectSchemeInfo.getId().toString());
    	}
    	dataMap=AcctAccreditFacadeFactory.getRemoteInstance().fetchData(param);
    }
	protected String getEditUIName() {
		return null;
	}
	public void onLoad() throws Exception {
		if(!AcctAccreditHelper.hasUsed(null)){
			FDCMsgBox.showWarning(this, "请先启用责任成本科目分配参数");
			SysUtil.abort();
		}

		super.onLoad();
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				plLeft.setResizeWeight(0.6);
				plLeft.setDividerLocation(0.6);
				plRight.setResizeWeight(0.5);
				plRight.setDividerLocation(0.5);
			}
		});
		this.plRight.setDividerLocation(450);
		treeMain.setVisible(false);
		treeView.setVisible(false);
	}
	
	protected void initTree() throws Exception {
/*		OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		try {
			SysContext.getSysContext().setCurrentOrgUnit(SysContext.getSysContext().getCurrentCtrlUnit());
			ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
			treeBuilder.build(this, this.treeMain, this.actionOnLoad);
			this.treeMain.expandAllNodes(true, (TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
		}finally{
			SysContext.getSysContext().setCurrentOrgUnit(currentOrgUnit);
		}*/
		super.initTree();
		buildSchemeTree();
		selectScheme(null);
		treeScheme.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIExceptionAndAbort(exc);
                } 
            }
        });
	}
	private void buildSchemeTree() throws BOSException{
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("isEnabled", Boolean.TRUE);
		view.getSelector().add("id");
		view.getSelector().add("number");
		view.getSelector().add("name");
		view.getSelector().add("description");
		view.getSorter().add(new SorterItemInfo("number"));
		view.getSorter().add(new SorterItemInfo("name"));
		AcctAccreditSchemeCollection c=AcctAccreditSchemeFactory.getRemoteInstance().getAcctAccreditSchemeCollection(view);
		Vector v=new Vector();
		for(Iterator iter=c.iterator();iter.hasNext();){
			AcctAccreditSchemeInfo info=(AcctAccreditSchemeInfo)iter.next();
			v.add(info);
		}
		AcctAccreditSchemeInfo info=new AcctAccreditSchemeInfo();
		info.setId(BOSUuid.read(rootSchemeId));
		info.setName("成本科目授权方案");
		info.setNumber("root");
		DefaultKingdeeTreeNode root = new DefaultKingdeeTreeNode(info);
		KingdeeTreeModel model=new KingdeeTreeModel(root, false);
		root=(DefaultKingdeeTreeNode)model.getRoot();
		DynamicKingdeeUtilTreeNode.createChildren(root, v);
		this.treeScheme.setModel(model);
	}
	
	private void refreshSchemeTree() throws BOSException {
		AcctAccreditSchemeInfo selectSchemeInfo = getSelectSchemeInfo();
		String schemeId = null;
		if (selectSchemeInfo != null && selectSchemeInfo.getId() != null) {
			schemeId = selectSchemeInfo.getId().toString();
		}
		buildSchemeTree();
		selectScheme(schemeId);
	}
	
	private AcctAccreditSchemeInfo getSelectSchemeInfo(){
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeScheme.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() == null || OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof AcctAccreditSchemeInfo) {
			AcctAccreditSchemeInfo info = (AcctAccreditSchemeInfo) node.getUserObject();
			return info;
		}
		return null;
	}
	
	private CostAccountCollection getCostAccunts(){
		if(dataMap!=null){
			return (CostAccountCollection)dataMap.get("CostAccountCollection");
		}
		return null;
	}
	
	private AcctAccreditUserCollection getAcctAccreditUsers(){
		if(dataMap!=null){
			return (AcctAccreditUserCollection)dataMap.get("AcctAccreditUserCollection");
		}
		return null;
	}
	private int getMaxLevel(){
		Integer maxLevel=null;
		if(dataMap!=null){
			maxLevel=(Integer)dataMap.get("maxLevel");
		}
		if(maxLevel==null){
			return 0;
		}else{
			return maxLevel.intValue();
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.plLeft.setOneTouchExpandable(true);
		this.plRight.setOneTouchExpandable(true);
		actionAddScheme.putValue(Action.SHORT_DESCRIPTION, "新增方案");
		actionAddScheme.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_new"));
		actionViewScheme.putValue(Action.SHORT_DESCRIPTION, "查看方案");
		actionViewScheme.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_view"));
		actionEditScheme.putValue(Action.SHORT_DESCRIPTION, "修改方案");
		actionEditScheme.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_edit"));
		actionDelScheme.putValue(Action.SHORT_DESCRIPTION, "删除方案");
		actionDelScheme.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_delete"));
		KDWorkButton btnAdd=new KDWorkButton(actionAddScheme);
		btnAdd.setEnabled(true);
		KDWorkButton btnView=new KDWorkButton(actionViewScheme);
		btnView.setEnabled(true);
		KDWorkButton btnEdit=new KDWorkButton(actionEditScheme);
		btnEdit.setEnabled(true);
		KDWorkButton btnDel=new KDWorkButton(actionDelScheme);
		btnDel.setEnabled(true);
        schemeTreeView.getControlPane().add(btnAdd);
        schemeTreeView.getControlPane().add(btnView);
        schemeTreeView.getControlPane().add(btnEdit);
        schemeTreeView.getControlPane().add(btnDel);
        schemeTreeView.setShowControlPanel(true);
        
        this.contUser.add(actionAddUser);
        this.contUser.add(actionDelUser);
        actionAddUser.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_new"));
        actionAddUser.setVisible(true);
        actionAddUser.setEnabled(true);
        actionDelUser.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTree_delete"));
        actionDelUser.setVisible(true);
        actionDelUser.setEnabled(true);
        prmt = getPrmtUser();
        this.contUser.add(prmt);
        prmt.setVisible(false);
        actionSave.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_save"));
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		setButtonDefaultStyl(btnSave);
		
		actionAssignAcct.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_distribute"));
		
		FDCClientHelper.setActionEnable(new ItemAction[]{actionAddNew,actionEdit,actionLocate,
				actionView,actionRemove,actionQuery,
				actionPrint,actionPrintPreview}, false);
		
		menuItemAssignAcct.setAccelerator(KeyStroke.getKeyStroke("ctrl F"));
		if(SysContext.getSysContext().getCurrentOrgUnit().getId().toString().equals(OrgConstants.DEF_CU_ID)){
			//集团
			actionAssignAcct.setEnabled(true);
			btnAdd.setEnabled(true);
			btnDel.setEnabled(true);
			btnView.setEnabled(true);
			btnEdit.setEnabled(true);
		}else{
			//			actionAssignAcct.setEnabled(false);
			//			btnAdd.setEnabled(false);
			//			btnDel.setEnabled(false);
			//			btnView.setEnabled(false);
			//			btnEdit.setEnabled(false);
		}
		
	}
	
	KDBizPromptBox prmt=null;
	private KDBizPromptBox getPrmtUser(){
		if(prmt==null){
			prmt=new  KDBizPromptBox();
		}
		prmt.setData(null);
			prmt.setQueryInfo("com.kingdee.eas.base.permission.app.RoleQuery");
			prmt.setEnabledMultiSelection(true);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("*");
			prmt.setSelectorCollection(sic);
			EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		for (int i = 0; i < tblUser.getRowCount(); i++) {
			AcctAccreditUserInfo info = (AcctAccreditUserInfo) tblUser.getRow(i).getUserObject();
			if (info.getRole() != null) {
				filter.getFilterItems().add(new FilterItemInfo("id", info.getRole().getId(), CompareType.NOTEQUALS));
			}
		}
		view.setFilter(filter);
		prmt.setEntityViewInfo(view);
		return prmt;
	}
	/**
	 * 在树结点上选择特定的scheme
	 * @param schemeId
	 */
	private void selectScheme(String schemeId){
		if(schemeId==null){
			treeScheme.setSelectionRow(0);
			return;
		}
		DefaultKingdeeTreeNode root=(DefaultKingdeeTreeNode)treeScheme.getModel().getRoot();
		for(int i=0;i<root.getChildCount();i++){
			DefaultKingdeeTreeNode child = (DefaultKingdeeTreeNode)root.getChildAt(i);
			if(child.getUserObject() instanceof AcctAccreditSchemeInfo){
				AcctAccreditSchemeInfo info=(AcctAccreditSchemeInfo)child.getUserObject();
				if(info.getId().toString().equals(schemeId)){
					treeScheme.setSelectionNode(child);
					return;
				}
				
			}
		}
		treeScheme.setSelectionRow(0);
	}
	protected void treeScheme_valueChanged(TreeSelectionEvent e) throws Exception {
		fillTable();
	}
}