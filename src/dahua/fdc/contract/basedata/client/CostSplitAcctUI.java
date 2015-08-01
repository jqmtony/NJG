/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICurProject;
import com.kingdee.eas.fdc.basedata.ProjectStatusInfo;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUCollection;
import com.kingdee.eas.fdc.basedata.ProjectWithCostCenterOUFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ConChangeSplitEditUI;
import com.kingdee.eas.fdc.contract.client.ContractCostSplitEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitEditUI;
import com.kingdee.eas.fdc.finance.client.PaymentSplitWithoutTxtConEditUI;
import com.kingdee.eas.fdc.finance.client.WorkLoadSplitEditUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * �ɱ���Ŀ����
 */
public class CostSplitAcctUI extends AbstractCostSplitAcctUI {
	private static final Logger logger = CoreUIObject.getLogger(CostSplitAcctUI.class);
	private static final Color canntSelectColor = new Color(0xFEFED3);
	private CurProjectInfo addressProject = null;

	private FilterInfo filter = null;
	
	private boolean isFinacial = false;// �жϲ���һ�廯�Ƿ�����״ֵ̬
	
	private CostAccountCollection cac = new CostAccountCollection();
    
    private boolean isOk = false;		//add by jelon
    private CostAccountCollection cacheCostAccoutCollection = new CostAccountCollection();
    private CurProjectInfo curProject = null;
	private Map parentMap = null;
	//��ĿID����
	private Set curProjectCollection = new HashSet();
	/**
	 * output class constructor
	 */
	public CostSplitAcctUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
//		tblMain.checkParsed();// table����!
//		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
//			public void afterDataFill(KDTDataRequestEvent e) {
//				// do something
//				String strTemp = "";
//				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
//					strTemp = tblMain.getRow(i).getCell(1).getValue().toString();
//					strTemp = strTemp.replace('!', '.');
//					tblMain.getRow(i).getCell(1).setValue(strTemp);
//					tblMain.getRow(i).getCell(0).setValue(strTemp);
//					tblMain.getRow(i).getCell(2).setValue(strTemp);
//				}
//			}
//		});
		super.onLoad();
		initFinacial();
		// ���ø���ģʽ�ɱ�����һ�廯�Ĵ���
		onFinacial();
		this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
//		this.btnAllSelect.setText("ȫѡ");
		this.btnNoneSelect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
//		this.btnNoneSelect.setText("ȫ��");
		if (getUIContext().get("address") != null) {
			if (getUIContext().get("address") instanceof FullOrgUnitInfo) {
				
			} else if (getUIContext().get("address") instanceof CurProjectInfo) {
				addressProject = (CurProjectInfo) getUIContext().get("address");
			}
		}
		//��ȡ��ͬ��ֺͱ����ֵ���Ŀ��
		if (getUIContext().get("Owner") != null) {
			String className = getUIContext().get("Owner").getClass().getName();
			if (className.equals(WorkLoadSplitEditUI.class.getName())
					|| className.equals(PaymentSplitEditUI.class.getName())) {
				getCurProjectInfos();
			}
		}
		buildProjectTree();
		
		
		this.treeMain.setSelectionRow(0);
//		loadDatas(filter);
	}
	public void onShow() throws Exception {
		super.onShow();
		selectCurOrgTreeNode();
		this.buiderLevelButtom(curProject);
	}
	/**
	 * ��ʼʱѡ�е�ǰ���
	 * @author sxhong  		Date 2007-3-6
	 * @throws BOSException
	 */
	private void selectCurOrgTreeNode() throws BOSException {
		final OrgUnitInfo currentOrgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		final CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		
		CurProjectInfo curProj=(CurProjectInfo)getUIContext().get("curProject");
		if(curProj==null){
			if(currentOrgUnit.isIsCostOrgUnit()){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo myfilter=new FilterInfo();
				myfilter.appendFilterItem("costCenterOU.id", currentOrgUnit.getId().toString());
				view.setFilter(myfilter);
				final ProjectWithCostCenterOUCollection projectWithCostCenterOUCollection = ProjectWithCostCenterOUFactory.getRemoteInstance().getProjectWithCostCenterOUCollection(view);
				if(projectWithCostCenterOUCollection.size()==1){
					curProj=projectWithCostCenterOUCollection.get(0).getCurProject();
				}
				
			}
		}
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		DefaultKingdeeTreeNode node=root;
		while(node!=null){
			if(node.getUserObject() instanceof OrgStructureInfo){
				final OrgStructureInfo info = (OrgStructureInfo)node.getUserObject();
				
				//ɾ��������֯�µ���
				if(info.getUnit().getId().equals(currentFIUnit.getId())){
					deleteOtherOrgNode(node);
				}
//				
//				if(info.getUnit().getId().equals(currentOrgUnit.getId())){
//					treeMain.setSelectionNode(node);
//					int row=treeMain.getSelectionRows()[0];
//					treeMain.expandRow(row);
//					break;
//				}
			}else if(curProj!=null&&node.getUserObject() instanceof CurProjectInfo){
				CurProjectInfo info =(CurProjectInfo) node.getUserObject();
				if(info.getId().equals(curProj.getId())){
					treeMain.setSelectionNode(node);
					int row=treeMain.getSelectionRows()[0];
					treeMain.expandRow(row);
					curProject = info;
					break;
				}
			}
			node=(DefaultKingdeeTreeNode)node.getNextNode();
		}
	
	}
	
	private void deleteOtherOrgNode(DefaultKingdeeTreeNode node){
		DefaultKingdeeTreeNode curNode=node;
		DefaultKingdeeTreeNode parentNode=(DefaultKingdeeTreeNode)curNode.getParent();
		while(parentNode!=null){
			treeMain.removeAllChildrenFromParent(parentNode);
			treeMain.insertNodeInto(curNode, parentNode, 0);
//			parentNode.removeAllChildren();
//			parentNode.add(node);
			curNode=parentNode;
			parentNode=(DefaultKingdeeTreeNode)curNode.getParent();
		}
	}
	private void buildProjectTree() throws Exception {
		//����ʾ�Ѿ����õĹ�����Ŀ�������Ƿ���֯���룿��	jelon 12/30/2006
		Boolean isPreSplit = Boolean.valueOf(false);
		if(getUIContext().get("isPreSplit") != null)
		{
			isPreSplit = (Boolean)getUIContext().get("isPreSplit");
		}
		
		if(!isPreSplit.booleanValue())
		{
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, true,
					curProjectCollection);
			//��֯����
			//		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false);

			treeMain.setShowsRootHandles(true);
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		}
		else
		{
			ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder(false, false,
					curProjectCollection);

			treeMain.setShowsRootHandles(true);
			projectTreeBuilder.build(this, treeMain, actionOnLoad);
		}

	}

	private void addSelector(EntityViewInfo evInfo) {
		evInfo.getSelector().add("id");
		evInfo.getSelector().add("longNumber");
		evInfo.getSelector().add("name");
		//add begin jelon
		SelectorItemCollection selectors=evInfo.getSelector();
        //selectors.add("id");
		//selectors.add("name");
		selectors.add("number");
		//selectors.add("longNumber");		
		selectors.add("displayName");	
		selectors.add("level");;	
		selectors.add("isLeaf");
		// �ɲ�ֳɱ�
		selectors.add("isSplit");
		
		selectors.add("codingNumber");
		selectors.add("curProject");
		selectors.add("curProject.id");
		selectors.add("curProject.number");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.name");
		selectors.add("curProject.displayName");
		selectors.add("curProject.level");
		selectors.add("curProject.isLeaf");

		 selectors.add("parent.id");
		 selectors.add("parent.name");
		//add end jelon
	}

	private void loadDatas(FilterInfo filter) throws BOSException {
		this.tblMain.removeRows();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evInfo = new EntityViewInfo();
		
		addSelector(evInfo);
		
		DefaultKingdeeTreeNode treeNode = getProjSelectedTreeNode();
		if(treeNode.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) treeNode.getUserObject();
			AcctAccreditHelper.handAcctAccreditFilter(null, projectInfo.getId().toString(), filter);
		}
	
		evInfo.setFilter(filter);

		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evInfo);
		
		/**************************** �������ɱ����ѡ��ɱ���Ŀ���ú�ͬ��ֺͱ����ֵĿ�Ŀ *******************************/
		 if (getUIContext().get("Owner") != null) {
			String className = getUIContext().get("Owner").getClass().getName();
			if (className.equals(WorkLoadSplitEditUI.class.getName())
					|| className.equals(PaymentSplitEditUI.class.getName())) {
				costAccountCollection = getContractSlpitAndChangeSplitCostAccounts();
			}
		}
		/**************************** �������ɱ����ѡ��ɱ���Ŀ���ú�ͬ��ֺͱ����ֵĿ�Ŀ *******************************/
		
		tblMain.checkParsed();// table����!
		tblMain.getColumn("select").setWidth(50);
		tblMain.getColumn("number").setWidth(211);
		tblMain.getColumn("name").setWidth(130);
		tblMain.getColumn("id").getStyleAttributes().setHided(true);
		CellTreeNode node=null;
		
		/*** �ɲ�ֳɱ���Ŀ�߼����� begin ***/
		parentMap = new HashMap();
		String pId = "";
		String Id = "";
		CostAccountInfo costAccountInfo = null;
		for (int i = 0; i < costAccountCollection.size(); i++) {
		    costAccountInfo = costAccountCollection.get(i);
			if (costAccountInfo.getParent() != null) {
				Id = costAccountInfo.getId().toString();
				pId = costAccountInfo.getParent().getId().toString();
				
				if (!Id.equals(pId)) {
					parentMap.put(pId, costAccountInfo.getParent().getName());
				}
			}
		}

		/*** �ɲ�ֳɱ���Ŀ�߼����� end ***/
		
		if (costAccountCollection.size() != 0) {
			IRow row;
			for (int i = 0; i < costAccountCollection.size(); i++) {
				row = this.tblMain.addRow();
				node=new CellTreeNode();
				row.getCell("select").setValue(Boolean.valueOf(false));
			
//				row.getCell("number").setValue(costAccountCollection.get(i).getLongNumber().replace('!', '.'));
				costAccountInfo = costAccountCollection.get(i);
				
				row.getCell("name").setValue(costAccountInfo.getName(SysContext.getSysContext().getLocale()));
				row.getCell("id").setValue(costAccountInfo.getId().toString());
				row.setUserObject(costAccountInfo);
				// row.getCell("isenabled").setValue(String.valueOf(costAccountCollection.get(j).getAsstAccount().getName()));
				
				node.setValue(costAccountInfo.getLongNumber().replace('!', '.'));
				final int level = costAccountInfo.getLevel() - 1;
				final boolean isLeaf = costAccountInfo.isIsLeaf();
				node.setTreeLevel(level);
				// ��õ�ǰ�ɱ���ĿID
				String id = costAccountInfo.getId().toString();
				// �����жϣ��ж�map�����Ƿ����Ϊ���ڵ�ĳɱ���Ŀ
				if (isLeaf || !parentMap.containsKey(id)) {		
					node.setHasChildren(false);
					if (level > 1) {
						row.getStyleAttributes().setHided(true);
					}
				}else{

					if(level<=1){
						if(level==0){
							node.setCollapse(false);
						}else{
							node.setCollapse(true);
						}
					}else{
						node.setCollapse(true);//�Ƿ�ֻ���ظ����
						row.getStyleAttributes().setHided(true);
					}
					

					node.addClickListener(new NodeClickListener(){
						public void doClick(CellTreeNode source, ICell cell,
								int type) {
							// source.getTreeLevel();
							tblMain.revalidate();
							
						}
					});
					node.setHasChildren(true);
					
					row.getStyleAttributes().setBackground(canntSelectColor);
				    
				}
			
				row.getCell("number").setValue(node);

				row.setTreeLevel(level);
			}

		}
//		tblMain.setPreferredSize(new Dimension(0,0));

	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
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
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
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
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed method
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		IRow row;
		cac.clear();
		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				cac.add(cai);
				flag = true;
			}
		}
		if (flag) {
			CostAccountFactory.getRemoteInstance().importDatas(cac, this.addressProject.getId());
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
	}

	/**
	 * output actionCancel_actionPerformed method
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		this.getUIWindow().close();
	}

	/**
	 * output actionAllSelect_actionPerformed method
	 */
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
		}
	}

	/**
	 * output actionNoneSelect_actionPerformed method
	 */
	public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
		}
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		treeSelectChange();
	}

	/**
	 * 
	 * �����������ѡ��ı䣬���¹�������ִ�в�ѯ
	 * 
	 * @author:liupd ����ʱ�䣺2006-7-25
	 *               <p>
	 */
	private void treeSelectChange() throws Exception {
		/*
		 * ������Ŀ��
		 */
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		//�����Ѿ�ѡ���˵ĳɱ���Ŀ
		cacheCostAccoutCollection.addCollection(getSelectCostAccounts());
		
//		if (OrgViewUtils.isTreeNodeDisable(node)) {
//			return;
//		}
		
		//�б�����Ԥ�Ȳ��ʹ��
		Boolean isPreSplit = Boolean.valueOf(false);
		if(getUIContext().get("isPreSplit") != null)
		{
			isPreSplit = (Boolean)getUIContext().get("isPreSplit");
		}
		Boolean isLeafProject = Boolean.valueOf(false) ;
		
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			
			if(isPreSplit.booleanValue())
			{
				isLeafProject = Boolean.valueOf(projectInfo.isIsLeaf());
			}
			buiderLevelButtom(projectInfo);
			filterItems.add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));	
			//2009-05-22 ����ȷ�� ��ģʽҲ���ܲ�ֵ��ѹرա���ʧ������ת��Ŀ
//			if(isFinacial){
				//������ݲ��ܲ�ֵ��ѹرա���ʧ������ת��Ŀ
				Set set=new HashSet();
				set.add(ProjectStatusInfo.closeID);
				set.add(ProjectStatusInfo.flowID);
				set.add(ProjectStatusInfo.transferID);
				filterItems.add(new FilterItemInfo("curProject.projectStatus.id",set,CompareType.NOTINCLUDE));
//			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
									
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui == null || oui.getUnit() == null)
				return;		
			FullOrgUnitInfo info = oui.getUnit();
			
			if(isPreSplit.booleanValue())
			{
				isLeafProject = Boolean.valueOf(info.isIsLeaf());
			}
			buiderLevelButtom(info);
			//TODO �ݲ�����ѡ��˾�Ŀ�Ŀ	jelon
			//filterItems.add(new FilterItemInfo("fullOrgUnit.id", info.getId().toString(), CompareType.EQUALS));		
			filterItems.add(new FilterItemInfo("fullOrgUnit.id", BOSUuid.create("null"), CompareType.EQUALS));
			
			
		}
		
		
//		if (getProjSelectedTreeNode() != null && getProjSelectedTreeNode().getUserObject() instanceof TreeBaseInfo) {
//			TreeBaseInfo projTreeNodeInfo = (TreeBaseInfo) getProjSelectedTreeNode().getUserObject();
//			BOSUuid id = projTreeNodeInfo.getId();
//			// ѡ����ǳɱ����ģ�ȡ�óɱ����ļ��¼��ɱ����ģ�����У��µ����к�ͬ
//			if (projTreeNodeInfo instanceof FullOrgUnitInfo) {
//				// Set idSet = ContractClientUtils.genOrgUnitIdSet(id);
//				// filterItems.add(new FilterItemInfo("fullOrgUnit.id", idSet,
//				// CompareType.INCLUDE));
//				filterItems.add(new FilterItemInfo("fullOrgUnit.id", id, CompareType.EQUALS));				
//			}
//			// ѡ�������Ŀ��ȡ����Ŀ�µ�����Դ�ɱ���Ŀ
//			else if (projTreeNodeInfo instanceof CurProjectInfo) {
//				// Set idSet = ContractClientUtils.genProjectIdSet(id);
//				// filterItems.add(new FilterItemInfo("curProject.id", idSet,
//				// CompareType.INCLUDE));
//				filterItems.add(new FilterItemInfo("curProject.id", id, CompareType.EQUALS));
//			}
//		}else{
//			return;
//		}
		
		
		//filterItems.add(new FilterItemInfo("isSource", Boolean.valueOf(true), CompareType.EQUALS));
		filterItems.add(new FilterItemInfo("isEnabled", Boolean.valueOf(true), CompareType.EQUALS));
		this.filter = filter;
		
		if(isPreSplit.booleanValue() && !isLeafProject.booleanValue())
		{
			tblMain.removeRows();
			return ;
		}
		
		/*** ����ֻ�鿴�ɲ�ֿ�Ŀ�߼� begin ��������Ŀ���ı�ʱ�� ***/
		// ����Ƿ�ѡ��ֻ�鿴�ɲ�ֿ�Ŀ
		boolean isSelectIsSplit = this.isSplitAcct.isSelected();
		Boolean isFinacial = (Boolean) getUIContext().get("isFinacial");
		if (isFinacial != null && isFinacial.booleanValue()) {
			isSelectIsSplit = false;
		}
		// ���ѡ������˵����ɲ�ֵĿ�Ŀ
		if (isSelectIsSplit) {
			filter.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));
			this.loadDatas(filter);
		} else {
			filter.remove("isSplit");
			//			this.isSplitAcct.setVisible(false);
			//			this.isSplitAcct.setEnabled(false);
			this.isSplitAcct.setSelected(false);
			this.loadDatas(filter);
		}
		// ����ʱ��ʼ����ϸ��Ŀ
		/*
		 * if (contractIsInvalid()) { // �����ͬ���Ϲ� filter.remove("isSplit");
		 * this.isSplitAcct.setEnabled(false);
		 * this.isSplitAcct.setSelected(false); this.loadDatas(filter); }
		 */
		
		/*** ����ֻ�鿴�ɲ�ֿ�Ŀ�߼� end ��������Ŀ���ı�ʱ�� ***/
		
		for (int i = 0, count = tblMain.getRowCount(); cacheCostAccoutCollection.size()>0&&i < count; i++) {
			IRow row = tblMain.getRow(i);
			CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
			if(cacheCostAccoutCollection.contains(cai)){
//                  row.getCell("select").setValue(Boolean.TRUE);
				select(i, false, true);
			}
		}
		if (this.tblMain.getRowCount() > 0) {
			tblMain.getSelectManager().select(0, 0);
		}
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
	
	
	
	
	//from here to end 		add by jelon
    protected void btnCancel2_actionPerformed(ActionEvent e) throws Exception {
    	setConfirm(false);
    	disposeUIWindow();
    	cacheCostAccoutCollection.clear();
    }
    
    protected void btnConfirm_actionPerformed(ActionEvent e) throws Exception {
    	confirm();
    }
    
	private void confirm() throws Exception {
		//checkSelected();
    	//getData();
    	setConfirm(true);
	}

    public CostAccountCollection getData() throws Exception {

//		IRow row;
		cac.clear();
		cac.addCollection(getSelectCostAccounts());
		cac.addCollection(cacheCostAccoutCollection);
		cacheCostAccoutCollection.clear();
/*		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				
				//ֻ������ϸ��Ŀ
				if(cai.isIsLeaf()){
					cac.add(cai);
				}
				
				flag = true;
			}
		}*/
		
		/*
		if (flag) {
			CostAccountFactory.getRemoteInstance().importDatas(cac, this.addressProject.getId());
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
		*/
		
		/*
        SelectorItemCollection selectors = new SelectorItemCollection();
        selectors.add("id");
		selectors.add("name");
		selectors.add("number");
		selectors.add("longNumber");		
		selectors.add("displayName");	
		selectors.add("level");;	
		selectors.add("isLeaf");
		
		selectors.add("curProject");
		selectors.add("curProject.id");
		selectors.add("curProject.number");
		selectors.add("curProject.longNumber");
		selectors.add("curProject.name");
		selectors.add("curProject.displayName");
		selectors.add("curProject.level");
		selectors.add("curProject.isLeaf");
		
		String id = getSelectedKeyValue();
		CostAccountInfo costAccountInfo = CostAccountFactory.getRemoteInstance().getCostAccountInfo(
				new ObjectUuidPK(id), selectors);
		*/
		
    	/*if(!costAccountInfo.isIsLeaf()) {
    		MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
    		SysUtil.abort();
    	}*/
		
//		disposeUIWindow();
		
		return cac;
	}

    public boolean isOk() {
    	return isOk;
    }

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		if (isOk) {
			if (getUIContext().get("isMeasureSplit") != null) {
				CostAccountCollection accts = null;
				try {
					accts = getData();
					if (accts != null && accts.size() > 0) {
						for (Iterator iter = accts.iterator(); iter.hasNext();) {
							CostAccountInfo info = (CostAccountInfo) iter.next();
							if (info.getCurProject() != null && !info.getCurProject().isIsLeaf()) {
								FDCMsgBox.showError(this, "���ۺ�ֻͬ��ѡ����ϸ��Ŀ�Ŀ�Ŀ��");
								SysUtil.abort();
							}
						}
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					handUIExceptionAndAbort(e);
				}
			}
		}
		disposeUIWindow();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.framework.client.AbstractCoreUI#getSelectors()
	 */
	public SelectorItemCollection getSelectors() {
		// TODO �Զ����ɷ������
		return super.getSelectors();
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.tblMain_editStopped(e);
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent)
	 */
	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.tblMain_editValueChanged(e);
		
		if(e.getColIndex()==tblMain.getColumnIndex("select")){
			Boolean old=(Boolean)e.getOldValue();
			Boolean now=(Boolean)e.getValue();
			select(e.getRowIndex(),old.booleanValue(),now.booleanValue());
			
			/*
			
			int idx=e.getRowIndex();
			CostAccountInfo acct=(CostAccountInfo)tblMain.getRow(idx).getUserObject();
			int level=acct.getLevel();
			Boolean selected=(Boolean)e.getValue();

			IRow row=null;
			boolean isSelected=false;
			
			
			if(acct.isIsLeaf()){		//��ϸ��Ŀ
				isSelected=false;
				
				
				if(selected.equals(Boolean.TRUE)){	//ѡ��
					isSelected=true;
					
					//�����ϼ���Ŀ��ѡ��״̬
					for(int i=idx-1; i>=0; i--){
						row=tblMain.getRow(i);
						acct=(CostAccountInfo)row.getUserObject();
						
						if(acct.getLevel()==level-1){
							row.getCell("select").setValue(Boolean.TRUE);

							if((level-1)==1){
								break;
							}
							level-=1;
						}
					}
					
				}else{		//ȡ��ѡ��
					
					//��鵱ǰ��Ŀ����ı�����Ŀ�Ƿ���ѡ��
					for(int i=idx+1; i<tblMain.getRowCount(); i++){
						row=tblMain.getRow(i);
						acct=(CostAccountInfo)row.getUserObject();
						if(acct.getLevel()==level){
							if(row.getCell("select").getValue().equals(Boolean.TRUE)){
								isSelected=true;
								break;
							}
						}else{
							break;
						}
					}
					
					//��鵱ǰ��Ŀǰ��ı�����Ŀ�Ƿ���ѡ��
					if(!isSelected){
						for(int i=idx-1; i>=0; i--){
							row=tblMain.getRow(i);
							acct=(CostAccountInfo)row.getUserObject();
							if(acct.getLevel()==level){
								if(row.getCell("select").getValue().equals(Boolean.TRUE)){
									isSelected=true;
								}
							}else{
								break;
							}
						}						
					}
					
					

					//���ͬ����Ŀ�Ƿ���ѡ�������ѡ����ȡ�����ϼ���Ŀ�Ĳ���
					boolean isFound=false;
					
					boolean isTrue=true;
					while(isTrue){
						isTrue=false;
						
						isFound=false;
						
						//��鵱ǰ��Ŀ����ı�����Ŀ�Ƿ���ѡ��
						for(int i=idx+1; i<tblMain.getRowCount(); i++){
							row=tblMain.getRow(i);
							acct=(CostAccountInfo)row.getUserObject();
							if(acct.getLevel()==level){
								if(row.getCell("select").getValue().equals(Boolean.TRUE)){
									isFound=true;
									break;
								}
							}else if(acct.getLevel()>level){
								continue;
							}else{
								break;
							}
						}
						
						//��鵱ǰ��Ŀǰ��ı�����Ŀ�Ƿ���ѡ��
						if(!isFound){
							for(int i=idx-1; i>=0; i--){
								row=tblMain.getRow(i);
								acct=(CostAccountInfo)row.getUserObject();
								if(acct.getLevel()==level){
									if(row.getCell("select").getValue().equals(Boolean.TRUE)){
										isFound=true;
									}
								}else if(acct.getLevel()>level){
									continue;
								}else{
									break;
								}
							}						
						}
						

						if(!isFound){							
							level--;
							
							for(int i=idx-1; i>=0; i--){
								row=tblMain.getRow(i);
								acct=(CostAccountInfo)row.getUserObject();
								
								if(acct.getLevel()==level){
									row.getCell("select").setValue(Boolean.FALSE);
									
									isTrue=true;									
									break;
								}else if(acct.getLevel()>level){
									continue;
								}else{
									break;
								}
							}

						}
					}

					
				}
				
				
				//�����ϼ���Ŀ��ѡ��״̬
				for(int i=idx-1; i>=0; i--){
					row=tblMain.getRow(i);
					acct=(CostAccountInfo)row.getUserObject();
					
					if(acct.getLevel()==level-1){
						if(isTrue){
							row.getCell("select").setValue(Boolean.TRUE);
						}else{
							row.getCell("select").setValue(Boolean.FALSE);
						}

						if((level-1)==1){
							break;
						}
						level-=1;
					}
				}
				
			}else{		//����ϸ��Ŀ
				
				//boolean isTrue=(e.getValue().equals(Boolean.TRUE));
				//�¼�
				for(int i=e.getRowIndex()+1; i<tblMain.getRowCount(); i++){
					row=tblMain.getRow(i);
					acct=(CostAccountInfo)row.getUserObject();
					if(acct.getLevel()>level){
						row.getCell("select").setValue(selected);
					}else{
						break;
					}
				}
				
				//�ϼ�
				int temp=level;
				for(int i=e.getRowIndex()-1; i>0; i--){
					row=tblMain.getRow(i);
					acct=(CostAccountInfo)row.getUserObject();
					if(acct.getLevel()<level){
						if(acct.getLevel()+1==temp){
							row.getCell("select").setValue(selected);
						}
						temp--;
					}else if(acct.getLevel()==1||acct.getLevel()>=level){
						break;
					}
				}
				
			}
			
		*/}
	}

	/* ���� Javadoc��
	 * @see com.kingdee.eas.fdc.contract.client.AbstractCostSplitAcctUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO �Զ����ɷ������
		super.tblMain_tableClicked(e);
	}

    
    public boolean destroyWindow() {
    	setConfirm(false);
    	return super.destroyWindow();
    }
	
    public void select(int row, boolean old, boolean now){
    	if(old==now) return;
    	tblMain.getCell(row, "select").setValue(Boolean.valueOf(now));
		CostAccountInfo acctSelect=(CostAccountInfo)tblMain.getRow(row).getUserObject();
		if (old) {
			// ��CostAccout���󻺴���ɾ����Ӧ�Ķ���
			cacheCostAccoutCollection.remove(acctSelect);
		}
		CostAccountInfo acct=null;
		int level=acctSelect.getLevel();
		//�¼�
    	for(int i=row+1;i<tblMain.getRowCount();i++){
    		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
    		if(acct.getLevel()>level){
    			tblMain.getCell(i, "select").setValue(Boolean.valueOf(now));
    			// ��CostAccout���󻺴���ɾ����Ӧ�Ķ���
    			cacheCostAccoutCollection.remove(acct);
    		}else{
    			break;
    		}
    	}
    	
    	//�ϼ�
    	int parentLevel=level-1;
    	if(now){
        	for(int i=row-1;i>=0;i--){
        		if(parentLevel==0){
        			break;
        		}
        		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==parentLevel){
        			ICell cell = tblMain.getCell(i, "select");
        			if(cell.getValue()!=Boolean.TRUE) {
						cell.setValue(Boolean.TRUE);
						parentLevel--;
					}else{
						break;
					}

        		}
        	}
    	}else{
    		
    		//��ѡ��,���ͬ���Ƿ���ѡ���
    		boolean hasSelected=false;
    		//�������
        	for(int i=row+1;i<tblMain.getRowCount();i++){
        		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==level){
        			ICell cell = tblMain.getCell(i, "select");
        			if(cell.getValue()==Boolean.TRUE) {
        				hasSelected=true;
        				break;
        			}else if(acct.getLevel()<level){
        				break;
        			}
        		}
        	}
    		//�������
        	
        	if(!hasSelected){
            	for(int i=row-1;i>=0;i--){
            		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
            		if(acct.getLevel()==level){
            			ICell cell = tblMain.getCell(i, "select");
            			if(cell.getValue()==Boolean.TRUE) {
            				hasSelected=true;
            				break;
    					}
            		}else if(acct.getLevel()<level){
            			row=i;
            			break;
            		}
            	}
        	}
        	
        	if(!hasSelected){

    			//���ø���
    			parentLevel=level-1;
            	for(int j=row;j>=0;j--){
            		if(parentLevel==0){
            			break;
            		}
            		acct = (CostAccountInfo)tblMain.getRow(j).getUserObject();
            		if(acct.getLevel()==parentLevel){
            			ICell cell = tblMain.getCell(j, "select");
						cell.setValue(Boolean.FALSE);
						// ��CostAccout���󻺴���ɾ����Ӧ�Ķ���
						cacheCostAccoutCollection.remove(acct);
						parentLevel--;
            		}
            	}
    		
        	}
    	}
		
    }
	
    public CostAccountCollection getSelectCostAccounts(){
    	CostAccountCollection c=new CostAccountCollection();
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			IRow row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				CostAccountInfo cai = (CostAccountInfo) row.getUserObject();
				
				if (isFinacial) {
					String className = "";
					if (getUIContext().get("Owner") != null) {
						className = getUIContext().get("Owner").getClass().getName();
						if (className.equals(WorkLoadSplitEditUI.class.getName())
								|| className.equals(PaymentSplitEditUI.class.getName())
								|| className
										.equals(PaymentSplitWithoutTxtConEditUI.class.getName())) {
							// ֻ������ϸ��Ŀ
							if (cai.isIsLeaf()) {
								c.add(cai);
							} 
						} else {
							// ֻ������ϸ��Ŀ
							if (cai.isIsLeaf() || !parentMap.containsKey(cai.getId().toString())) {
								c.add(cai);
							} 
						}
					}
				} else {
					// ֻ������ϸ��Ŀ
					if (cai.isIsLeaf() || !parentMap.containsKey(cai.getId().toString())) {
						c.add(cai);
					} 
				}
			}
		}
		return c;
    }
	
    
    /**
	 * @description �ж��Ƿ����ò���һ�廯����ģʽ
	 * @author ����
	 * @createDate 2011-8-8
	 * @throws Exception
	 * @version EAS7.0
	 * @see
	 */
    protected void initFinacial()throws Exception{
		if(FDCUtils.IsFinacial(null, SysContext.getSysContext().getCurrentFIUnit().getId().toString())){
			isFinacial=true;
		}
    }

	/**
	 * ֻ�鿴�ɲ�ֿ�Ŀ ��������Ŀ���ı�ʱ��
	 */
    public void actionIsSplitAcct_actionPerformed(ActionEvent e) throws Exception {
    	// ����Ƿ�ѡ��ֻ�鿴�ɲ�ֿ�Ŀ
    	boolean isSelected = this.isSplitAcct.isSelected();
    	// ���ѡ������˵����ɲ�ֵĿ�Ŀ
		if (isSelected) {
			filter.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));
		} else {// ����ȫ����ʾ
			filter.remove("isSplit");
		}
		treeSelectChange();
	}
    
    /**
	 * ��õ�ǰ�ɱ���Ŀ����󼶴�
	 * 
	 * @return ��󼶴�
	 */
    private int getMaxLevel(Object curProject) {
		// ������ݿ��������
		FDCSQLBuilder builder = new FDCSQLBuilder();
    	// ��������
    	int level = 0;
    	// ��ѯ���ݿ�sql
		String sql = null;
		// ������Ŀ���ɼ���
		if (curProject instanceof CurProjectInfo) {
			sql = "select max(FLevel) from T_FDC_CostAccount where FCurProject = ?";
			builder.appendSql(sql);
			builder.addParam(((CurProjectInfo) curProject).getId().toString());
		} else if (curProject instanceof FullOrgUnitInfo) {// ������֯���ɼ���
			sql = "select max(FLevel) from T_FDC_CostAccount where FFullorgunit = ?";
			builder.appendSql(sql);
			builder.addParam(((FullOrgUnitInfo) curProject).getId().toString());
		} else {// �������п�Ŀ���ɼ���
			sql = "select max(FLevel) from T_FDC_CostAccount";
			builder.appendSql(sql);
		}
		try {
			// ��÷��ص���󼶴�
			IRowSet set = builder.executeQuery();
			while (set.next()) {
				level = set.getInt(1);
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		} 
		return level;
	}

	/**
	 * ��̬�������ΰ�ť
	 * 
	 * @throws Exception
	 */
    private void buiderLevelButtom(Object curProject) throws Exception {
    	// ��ȡ��󼶴�
		int level = this.getMaxLevel(curProject);
		this.levelPanel.removeAll();
		// �������ΰ�ť��X����ֵ
		int x = 0;
		// �������ɼ��ΰ�ť
		for (int i = 0; i < level; i++) {
			// ����һ�����ΰ�ť
			KDWorkButton levelBtn = new KDWorkButton();
			// ���ð�ť�ļ���
			levelBtn.setText(Integer.toString(i + 1));
			// ���ð�ť����
			levelBtn.setFont(new Font("Dialog", 0, 9));
			// ���ð�ť��С
			levelBtn.setSize(14, 16);
			// ����ÿ����ť��λ��
			levelBtn.setLocation(x, 0);
			// Ϊÿ����ť��ӵ���¼�
			levelBtn.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					try {
						// ���ü��ΰ�ť�¼�
						actionClickLevel(e);
					} catch (Exception e1) {
						handUIExceptionAndAbort(e1);
					}
				}
				
			});
			// ����ť��ӵ�ҳ����
			this.levelPanel.add(levelBtn);
			// ÿ��ѭ���ı䰴ť��X����ֵ
			this.levelPanel.repaint();
			x = x + 14;
		}
	}

	/**
	 * ÿ�����ΰ�ť�ĵ���¼�
	 * 
	 * @param e
	 *            ����İ�ť�¼�
	 * @throws Exception
	 */
    public void actionClickLevel(ActionEvent e) throws Exception {
    	// ���¼��л�ȡ��ǰ����İ�ť
    	KDWorkButton btn = (KDWorkButton) e.getSource();
    	// �õ�����İ�ť����ֵ
    	int sth = Integer.parseInt(btn.getText());
    	// ���ݻ�õİ�ť����ֵ���в�ͬ�Ĺ���
		changeTreeView(sth);
	}

    /**
	 * ���ε����ť
	 * 
	 * @param level
	 *            ��ǰ����ļ���
	 */
    private void changeTreeView(int level) {
    	IRow row;
		int rowLevel;
		level = level - 1;
		CellTreeNode node;
		// �����������
    	for (int i = 0; i < tblMain.getRowCount(); i++) {
    		row = tblMain.getRow(i);
			rowLevel = row.getTreeLevel();
			node = (CellTreeNode) row.getCell("number").getValue();
			// ������ε��ڵ�ǰ�����ļ���ʱ����չ����
			if (level == rowLevel) {
				row.getStyleAttributes().setHided(false);
				node.setCollapse(true);
			} else {// ����������
				row.getStyleAttributes().setHided(true);
			}
			// ������δ��ڵ�ǰ��������
			if (level > rowLevel) {
				row.getStyleAttributes().setHided(false);
				node.setCollapse(false);
			}
			// �������С�ڵ�ǰ��������
			if (level < rowLevel) {
			
				row.getStyleAttributes().setHided(true);
				node.setCollapse(true);
			
			}
		}
	}
    
    
	/**
	 * 
	 * @description ���ø���ģʽ�ɱ�����һ�廯�Ĵ���
	 * @author ����
	 * @createDate 2011-8-8
	 * @version EAS7.0
	 * @see
	 */
    public void onFinacial() {
    	// �ж��Ƿ����ø���ģʽ�ɱ�����һ�廯
		String className = "";
		if (getUIContext().get("Owner") != null) {
			// ��ȡ����ɱ���Ŀ��ť���ڽ����className
			className = getUIContext().get("Owner").getClass().getName();
			// �Ƚ��Ƿ��ǹ�������ֽ����className
			if (className.equals(WorkLoadSplitEditUI.class.getName())
					|| className.equals(PaymentSplitEditUI.class.getName())
					|| className.equals(PaymentSplitWithoutTxtConEditUI.class.getName())) {
			}
		}
		// �ж��Ƿ����ø���ģʽ�ɱ�����һ�廯
		if (isFinacial) {
			// ��ֻ��ѯ�ɲ�ֿ�Ŀ��Ϊ�����ú�Ĭ�ϲ�ѡ��״̬
			this.isSplitAcct.setSelected(false);
			this.isSplitAcct.setEnabled(false);
		} 
    }

	/**
	 * ��ѯ��ͬ�Ƿ����Ϲ�
	 */
	public boolean contractIsInvalid() {
		// ���SQL
		String sql = isInvalid();
		if (sql.equals("") || sql == null) {
			return false;
		}
		// ��ʼ�Ƿ����Ϲ�
		boolean isInvalid = false;
		// �Ƿ����ϼ��� ���ڷ������Ϻ�ͬ�����Ķ�������
		List isInvalidList = new ArrayList();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(sql);
		try {
			IRowSet set = builder.executeQuery();
			// �������ݿⷵ�صĽ������������뼯��
			while (set.next()) {
				isInvalid = set.getBoolean(1);
				isInvalidList.add(Boolean.valueOf(isInvalid));
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
		// �������
		for (int i = 0; i < isInvalidList.size(); i++) {
			boolean temp = ((Boolean) isInvalidList.get(i)).booleanValue();
			// Ϊ�棬�Ǳ����Ϲ�
			if (temp) {
				isInvalid = true;
				break;
			}
		}
		return isInvalid;
	}
	
	/**
	 * ��ѯ��ͬ�Ƿ����Ϲ�SQL���ƴװ
	 * 
	 * @return sql ���
	 */
	private String isInvalid() {
		// ��ô�ҳ�������
		String className = this.getUIContext().get("Owner").getClass().getName();
		// ȡ�ú�ͬ�ı��루������ָ����룩
		String costNumber = null;
		String sql = "";
		if (getUIContext().get("txtCostBillNumber") != null) {
			costNumber = getUIContext().get("txtCostBillNumber").toString();
			// �����ֵ�sqlƴװ
			if (className.equals(ConChangeSplitEditUI.class.getName())) {
				sql = "select FISinvalid from T_CON_ConChangeSplit where fcontractchangeid in (select fid from T_CON_ContractChangeBill where fnumber = '"
					+ costNumber + "')";
			} else if (className.equals(ContractCostSplitEditUI.class.getName())) {// ��ͬ���sqlƴװ
				sql = "select FIsInvalid from T_CON_ContractCostSplit where FContractBillID in (select fid from T_CON_ContractBill where fnumber = '"
					+ costNumber + "') ";
			}
		}
		return sql;
	}

	/**
	 * 
	 * @description ��ȡ��ͬ��ֺͱ����ֵĿ�Ŀ
	 * @author ����
	 * @createDate 2011-8-22
	 * @return
	 * @version EAS7.0
	 * @see
	 */
	private CostAccountCollection getContractSlpitAndChangeSplitCostAccounts() {
		// ��Ŀ�ĳ����뼯��
		List longNumbers = new ArrayList();
		// ��Ŀ����
		CostAccountCollection costAccountCollection = new CostAccountCollection();
		try {
			// ��ȡ��ͬID
			String contractBillId = getUIContext().get("contractBillId") != null ? getUIContext()
					.get("contractBillId").toString() : "";
			// ͨ����ͬID��ѯ��ͬ��ֺͱ����ֹ����Ŀ�Ŀ������
			StringBuffer sql = new StringBuffer();
			sql.append(" select FLONGNUMBER,FCURPROJECT from T_FDC_COSTACCOUNT tfc ");
			sql
					.append(" left join T_CON_ContractCostSplitEntry tcse on tcse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONTRACTCOSTSPLIT tcs on tcs.FID=tcse.FPARENTID ");
			sql
					.append(" left join T_CON_CONCHANGESPLITENTRY tccse on tccse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONCHANGESPLIT tcc on tcc.FID=tccse.FPARENTID ");
			sql.append(" where tcs.FCONTRACTBILLID='" + contractBillId
					+ "' or tcc.FCONTRACTBILLID='" + contractBillId
							+ "' order by flongnumber asc");
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql.toString());
			builder.execute();
			IRowSet set = builder.executeQuery();
			// �������ݿⷵ�صĽ������������뼯��
			while (set.next()) {
				// ��Ŀ������
				String longNumber = set.getString(1);
				longNumbers.add(longNumber);
			}
			// ͨ����Ŀ�������ѯ����Ŀ����Ϣ�Լ���Ŀ�ĸ���Ŀ
			this.addCostAccount(longNumbers, costAccountCollection);
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}
	
		return costAccountCollection;
	}

	/**
	 * ͨ���������ȡ��Ŀ
	 * 
	 * @description
	 * @author ����
	 * @createDate 2011-8-22
	 * @param longNumbers
	 * @param costAccountInfos
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @see
	 */
	public void addCostAccount(List longNumbers, CostAccountCollection costAccountCollection)
			throws BOSException, EASBizException {
		// ��ȡԶ�̽ӿ�
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		DefaultKingdeeTreeNode treeNode = getProjSelectedTreeNode();
		CurProjectInfo projectInfo = null;
		// ��ȡ��ǰ��������Ŀ
		if(treeNode.getUserObject() instanceof CurProjectInfo) {
			projectInfo = (CurProjectInfo) treeNode.getUserObject();
		}
		if (projectInfo != null) {
			if (longNumbers.size() > 0) {
				for (int i = 0; i < longNumbers.size(); i++) {
					String longNumber = (String) longNumbers.get(i);
					// �ָ���Ŀ�ĳ�����
					String[] tempLongNumbers = longNumber.split("!");
					String sp = "";
					String number = "";
					// ������Ŀ�ĳ�����
					for (int j = 0; j < tempLongNumbers.length; j++) {
						number += sp + tempLongNumbers[j];
						sp = "!";
						// ��װ��ѯ ����
						EntityViewInfo evInfo = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo();
						
						// �ж��Ƿ�ѡ��ֻ��ѯ�ɲ�ֿ�Ŀѡ��
						if (isSplitAcct.isSelected()) {
							filterInfo.getFilterItems().add(
									new FilterItemInfo("isSplit", new Integer(1)));
						} else {
							filterInfo.remove("isSplit");
						}
						 filterInfo.getFilterItems().add(
								new FilterItemInfo("longnumber", number, CompareType.EQUALS));
					
						filterInfo.getFilterItems().add(
								new FilterItemInfo("curProject.id", projectInfo.getId().toString(),
										CompareType.EQUALS));
						 
						evInfo.setFilter(filterInfo);
						CostAccountInfo costAccountInfo = iCostAccount.getCostAccountCollection(
								evInfo).get(0);
						if (costAccountInfo != null) {
							if (longNumbers.contains(costAccountInfo.getLongNumber())
									&& !costAccountInfo.isIsLeaf()) {
								this.getCostAccountCollection(projectInfo, iCostAccount, evInfo,
										filterInfo, costAccountInfo, costAccountCollection);

							} else {
								costAccountCollection.add(costAccountInfo);
							}
						}
						
					}
				}
			}
		}
	}
	/**
	 * 
	 * @description
	 * @author ����
	 * @createDate 2011-9-9
	 * @param projectInfo
	 * @param iCostAccount
	 * @param evInfo
	 * @param filterInfo
	 * @param costAccountInfo
	 * @param costAccountCollection
	 * @throws BOSException
	 * @version EAS7.0
	 * @see
	 */
	public void getCostAccountCollection(CurProjectInfo projectInfo, ICostAccount iCostAccount,
			EntityViewInfo evInfo,
			FilterInfo filterInfo, CostAccountInfo costAccountInfo,
			CostAccountCollection costAccountCollection) throws BOSException {
		if (costAccountInfo != null && costAccountInfo.getLevel() > 1) {
			// ��װ��ѯ ����
			evInfo = new EntityViewInfo();
			filterInfo = new FilterInfo();
			addSelector(evInfo);
			// �ж��Ƿ�ѡ��ֻ��ѯ�ɲ�ֿ�Ŀѡ��
			if (isSplitAcct.isSelected()) {
				filterInfo.getFilterItems().add(new FilterItemInfo("isSplit", new Integer(1)));
			} else {
				filterInfo.remove("isSplit");
			}
						
			filterInfo.getFilterItems().add(
						new FilterItemInfo("longnumber", costAccountInfo.getLongNumber() + "%",
							CompareType.LIKE));

			filterInfo.getFilterItems().add(
					new FilterItemInfo("curProject.id", projectInfo.getId().toString(),
							CompareType.EQUALS));
			// ����
			SorterItemCollection sorter = new SorterItemCollection();
			SorterItemInfo seq = new SorterItemInfo("longnumber");
			seq.setSortType(SortType.ASCEND);
			sorter.add(seq);
			evInfo.setSorter(sorter);
			evInfo.setFilter(filterInfo);
			CostAccountCollection costAccounts = iCostAccount.getCostAccountCollection(evInfo);
			for (int z = 0; z < costAccounts.size(); z++) {
				CostAccountInfo costAccount = costAccounts.get(z);
				costAccountCollection.add(costAccount);
			}
		} else {
			if (costAccountInfo != null) {
				costAccountCollection.add(costAccountInfo);
			}
		}
	}
	
	/**
	 * ��ȡ��ͬ��ֺͱ����ֵ���Ŀ
	 * 
	 * @description
	 * @author ����
	 * @createDate 2011-8-23
	 * @version EAS7.0
	 * @see
	 */
	private void getCurProjectInfos() {
		List allProjects = new ArrayList();

		try {
			String contractBillId = getUIContext().get("contractBillId") != null ? getUIContext()
					.get("contractBillId").toString() : "";
			StringBuffer sql = new StringBuffer();
			sql.append(" select tfp.FLONGNUMBER from T_FDC_CURPROJECT tfp");
			sql.append(" left join T_FDC_COSTACCOUNT tfc on tfc.FCURPROJECT=tfp.fid ");
			sql
					.append(" left join T_CON_ContractCostSplitEntry tcse on tcse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONTRACTCOSTSPLIT tcs on tcs.FID=tcse.FPARENTID ");
			sql
					.append(" left join T_CON_CONCHANGESPLITENTRY tccse on tccse.FCOSTACCOUNTID=tfc.FID ");
			sql.append(" left join T_CON_CONCHANGESPLIT tcc on tcc.FID=tccse.FPARENTID ");
			sql.append(" where tcs.FCONTRACTBILLID='" + contractBillId
					+ "' or tcc.FCONTRACTBILLID='" + contractBillId + "' ");
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql.toString());

			builder.execute();
			IRowSet set = builder.executeQuery();
			// �������ݿⷵ�صĽ������������뼯��
			while (set.next()) {
				String curproject = set.getString(1);
				allProjects.add(curproject);
			}
			this.addCurproject(allProjects);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handUIExceptionAndAbort(e);
		}
	}

	/**
	 * ͨ����Ŀ�������ȡ��Ŀ��
	 * 
	 * @description
	 * @author ����
	 * @createDate 2011-8-23
	 * @param allProjects
	 * @throws BOSException
	 * @throws EASBizException
	 * @version EAS7.0
	 * @see
	 */
	public void addCurproject(List allProjects)
			throws BOSException, EASBizException {
		    ICurProject iCurProject = CurProjectFactory.getRemoteInstance();

		if (allProjects.size() > 0) {
			for (int i = 0; i < allProjects.size(); i++) {
				String longNumber = (String) allProjects.get(i);
				// �ָ���Ŀ�ĳ�����
					String[] tempLongNumbers = longNumber.split("!");
					String sp = "";
					String number = "";
					// ������Ŀ�ĳ�����
					for (int j = 0; j < tempLongNumbers.length; j++) {
						number += sp + tempLongNumbers[j];
						sp = "!";
						EntityViewInfo evInfo = new EntityViewInfo();
						FilterInfo filterInfo = new FilterInfo();
						filterInfo.getFilterItems().add(new FilterItemInfo("longnumber", number));
					// filterInfo.getFilterItems().add(
					// new FilterItemInfo("longnumber", longNumber,
					// CompareType.LIKE));
						evInfo.setFilter(filterInfo);
						CurProjectInfo curProjectInfo = iCurProject.getCurProjectCollection(evInfo)
							.get(0);
					if (curProjectInfo != null) {
							curProjectCollection.add(curProjectInfo.getId().toString());
						}
					}
				}
		}
	}
	
	/**
	 * �����жϲ���¼�
	 * 
	 * @return
	 */
	public Map getParentIdMap() {
		return this.parentMap;
	}
}