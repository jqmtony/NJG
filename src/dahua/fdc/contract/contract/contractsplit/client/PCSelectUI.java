/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.contractsplit.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.contract.programming.IProgrammingContract;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.client.EntryTreeSumField;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class PCSelectUI extends AbstractPCSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(PCSelectUI.class);
    private boolean isOk = false;
    private ProgrammingContractCollection pcc = new ProgrammingContractCollection();
    private Map pcMap = new HashMap();
    private int rowNum = 0;  
    
    private EntryTreeSumField sumField = new EntryTreeSumField();
    
    public PCSelectUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	this.tblMain.checkParsed();
		super.onLoad();
		this.btnAllSelect.setIcon(EASResource.getIcon("imgTbtn_selectall"));
		this.btnNoneSelect.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		buildProjectTree();
		
		this.treeMain.setSelectionRow(0);
	}
    private void buildProjectTree() throws Exception {
    	CurProjectInfo curProject=(CurProjectInfo) this.getUIContext().get("curProject");
    	String sql = "select a.CFSplitProjectID from CT_BD_CurProjectSplitProject a left join T_FDC_CurProject b on b.fid=a.FParentID where b.fid='"+curProject.getId()+"'";
		FullOrgUnitInfo orgUnit = curProject.getFullOrgUnit();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", orgUnit.getId().toString()));
//    	filter.getFilterItems().add(new FilterItemInfo("isWholeAgeStage", Boolean.FALSE));
    	filter.getFilterItems().add(new FilterItemInfo("id", sql,CompareType.INNER));
    	filter.getFilterItems().add(new FilterItemInfo("id", curProject.getId(),CompareType.NOTEQUALS));
		ITreeBuilder treeBuilder = TreeBuilderFactory.createTreeBuilder(new DefaultLNTreeNodeCtrl(CurProjectFactory.getRemoteInstance()), 50,5, filter);
		treeBuilder.buildTree(this.treeMain);
		this.treeMain.expandRow(0);
	}
    public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
	}
    private void loadDatas() throws BOSException {
		this.tblMain.removeRows();
		DefaultKingdeeTreeNode treeNode = getProjSelectedTreeNode();
		if(treeNode.getUserObject()==null||!(treeNode.getUserObject() instanceof CurProjectInfo)) {
			return;
		}
		CurProjectInfo projectInfo = (CurProjectInfo) treeNode.getUserObject();
//		ContractTypeInfo contractTypeInfp=(ContractTypeInfo)this.getUIContext().get("contractType");
		ProgrammingContractCollection pcCollection=null;
		IProgrammingContract ipc=ProgrammingContractFactory.getRemoteInstance();
		
		if(pcMap.containsKey(projectInfo.getId().toString())){
			pcCollection=(ProgrammingContractCollection) pcMap.get(projectInfo.getId().toString());
		}else{
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("programming.isLatest",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("programming.state",FDCBillStateEnum.AUDITTED_VALUE));
//			filter.getFilterItems().add(new FilterItemInfo("isLeaf",1));
			
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("id");
			sel.add("longNumber");
			sel.add("name");
			sel.add("level");
			sel.add("amount");
			sel.add("isCiting");
			sel.add("programming.project.name");
			sel.add("balance");
			ev.setSelector(sel);
			
			
			filter.getFilterItems().add(new FilterItemInfo("programming.project.id",projectInfo.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("contractType.id",contractTypeInfp.getId().toString()));
//			filter.getFilterItems().add(new FilterItemInfo("contractType.id",null));
//			filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
			ev.setFilter(filter);
			SorterItemInfo sii = new SorterItemInfo();
			sii.setPropertyName("longNumber");
			sii.setSortType(SortType.ASCEND);
			ev.getSorter().add(sii);
			pcCollection = ipc.getProgrammingContractCollection(ev);
			
			pcMap.put(projectInfo.getId().toString(), pcCollection);
		}
		CellTreeNode node=null;
		if (pcCollection.size() != 0) {
			IRow row;
			for (int i = 0; i < pcCollection.size(); i++) {
				row = this.tblMain.addRow();
				node=new CellTreeNode();
				row.getCell("select").setValue(Boolean.valueOf(false));
				ProgrammingContractInfo pcInfo=pcCollection.get(i);
				row.getCell("id").setValue(pcInfo.getId().toString());
				row.getCell("name").setValue(pcInfo.getName());
				row.getCell("number").setValue(pcInfo.getLongNumber());
				row.setUserObject(pcInfo);
				
				if(pcInfo.isIsCiting()){
					row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
					row.getCell("select").getStyleAttributes().setLocked(true);
				}
			}
		}
	}
    
    private void initTableData(){
    	this.tblMain.removeRows();
		DefaultKingdeeTreeNode treeNode = getProjSelectedTreeNode();
		if(treeNode.getUserObject()==null||!(treeNode.getUserObject() instanceof CurProjectInfo)) {
			return;
		}
		CurProjectInfo projectInfo = (CurProjectInfo) treeNode.getUserObject();
		
    	EntityViewInfo viewInfo = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("parent.*");
		viewInfo.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("programming.isLatest",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("programming.state",FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("programming.project.id",projectInfo.getId().toString()));
		
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("id");
		sel.add("parent.id");
		sel.add("longNumber");
		sel.add("name");
		sel.add("level");
		sel.add("amount");
		sel.add("isCiting");
		sel.add("programming.project.name");
		sel.add("balance");
		viewInfo.setSelector(sel);
		
		if (getUIContext().get("projectId") != null) {
			String projectId = getUIContext().get("projectId").toString();
			filter.appendFilterItem("programming.project.id", projectId);
		}
		/*********** add by lihaiou,2013.0922. for bug R130916-0204*************/
		Boolean allowZero = getUIContext().get("allowZero") == null ? Boolean.TRUE : Boolean.valueOf(getUIContext().get("allowZero").toString());
		if (!allowZero.booleanValue()) {
			filter.getFilterItems().add(new FilterItemInfo("amount", new Integer(0), CompareType.GREATER));
		}
		/*************** modify end*********************/
		viewInfo.setFilter(filter);
		SorterItemCollection sort = new SorterItemCollection();
    	sort.add(new SorterItemInfo("sortNumber"));
//    	
    	viewInfo.setSorter(sort);
		
		ProgrammingContractCollection  cols = null;
    	try {
			 cols = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(viewInfo);
		} catch (BOSException e) {
			handUIExceptionAndAbort(e);
		}
		
    	if(cols != null && cols.size() > 0){ 
    		rowNum = cols.size();
    		this.tblMain.setRowCount(rowNum);
    		for(Iterator it = cols.iterator();it.hasNext();){
    			ProgrammingContractInfo info = (ProgrammingContractInfo) it.next();
    			info.setIsLeaf(chargeIsLeaf(info,cols));
    			IRow row = this.tblMain.addRow();
     			setRowData(info, row);
    		}
    		//向上汇总
//			sumField.caclTotalAmount(this.tblMain);
    	}
    }
    
	private boolean chargeIsLeaf(ProgrammingContractInfo info,ProgrammingContractCollection  cols) {
		String id = info.getId().toString(); 
		for (int i = 0; i < rowNum; i++) {
			ProgrammingContractInfo parent = cols.get(i).getParent();
			if(parent==null) continue;
			if(id.equals(parent.getId().toString())){
				return false;
			}
		}
		return true;
	}

	private void setRowData(ProgrammingContractInfo pcInfo, IRow row) {
		CellTreeNode node = new CellTreeNode();
    	row.setUserObject(pcInfo);
    	node.setValue(pcInfo.getLongNumber());
    	node.setTreeLevel(pcInfo.getLevel());
    	row.getCell("select").setValue(Boolean.valueOf(false));
		row.getCell("id").setValue(pcInfo.getId().toString());
		row.getCell("name").setValue(pcInfo.getName());
		row.getCell("number").setValue(pcInfo.getLongNumber());
		row.setUserObject(pcInfo);
		
		if(pcInfo.isIsCiting()){
			row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
			row.getCell("select").getStyleAttributes().setLocked(true);
		}
    	
    	final int level = pcInfo.getLevel()-1;
		final boolean isLeaf = pcInfo.isIsLeaf();
		node.setTreeLevel(level);
		
		if(isLeaf){
			node.setHasChildren(false);
			if(level>1){
				row.getStyleAttributes().setHided(true);
			}
		}else{
//			row.getStyleAttributes().setBackground(Color.BLACK);
			if(level<=1){
				if(level==0){
					node.setCollapse(false);
				}else{
					node.setCollapse(true);
				}
			}else{
				node.setCollapse(true);//是否只隐藏根结点
				row.getStyleAttributes().setHided(true);
			}
			node.addClickListener(new NodeClickListener(){
				public void doClick(CellTreeNode source, ICell cell,
						int type) {
					tblMain.revalidate();
					
				}
			});
			node.setHasChildren(true);
			row.getStyleAttributes().setBackground(FDCColorConstants.lockColor);
			row.getStyleAttributes().setLocked(true);
			row.getCell("number").getStyleAttributes().setLocked(false);
		}

		row.getCell("number").setValue(node);
	}
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		IRow row;
		pcc.clear();
		boolean flag = false;
		for (int i = 0, count = tblMain.getRowCount(); i < count; i++) {
			row = tblMain.getRow(i);
			if (((Boolean) row.getCell("select").getValue()).booleanValue()) {
				ProgrammingContractInfo pc = (ProgrammingContractInfo) row.getUserObject();
				pcc.add(pc);
				flag = true;
			}
		}
		if (flag) {
			setConfirm(true);
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,"Import_success"));
		}
	}
    protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
    	setConfirm(false);
    	disposeUIWindow();
	}
    
    public void setConfirm(boolean isOk) {
		this.isOk = isOk;
		disposeUIWindow();
	}
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			ProgrammingContractInfo pc=(ProgrammingContractInfo) this.tblMain.getRow(i).getUserObject();
			if(!pc.isIsCiting()){
				this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(true));
			}
		}
	}
	
	public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			this.tblMain.getRow(i).getCell("select").setValue(Boolean.valueOf(false));
		}
	}
	protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
//		if(e.getColIndex()==tblMain.getColumnIndex("select")){
//			Boolean old=(Boolean)e.getOldValue();
//			Boolean now=(Boolean)e.getValue();
//			select(e.getRowIndex(),old.booleanValue(),now.booleanValue());
//		}
	}
	 public void select(int row, boolean old, boolean now){
	    	if(old==now) return;
	    	tblMain.getCell(row, "select").setValue(Boolean.valueOf(now));
	    	ProgrammingContractInfo acctSelect=(ProgrammingContractInfo)tblMain.getRow(row).getUserObject();
			ProgrammingContractInfo acct=null;
			int level=acctSelect.getLevel();
			//下级
	    	for(int i=row+1;i<tblMain.getRowCount();i++){
	    		acct = (ProgrammingContractInfo)tblMain.getRow(i).getUserObject();
	    		if(acct.getLevel()>level&&!tblMain.getCell(i, "select").getStyleAttributes().isLocked()){
	    			tblMain.getCell(i, "select").setValue(Boolean.valueOf(now));
	    		}else{
	    			break;
	    		}
	    	}
	    	
	    	//上级
	    	int parentLevel=level-1;
	    	if(now){
	        	for(int i=row-1;i>=0;i--){
	        		if(parentLevel==0){
	        			break;
	        		}
	        		acct = (ProgrammingContractInfo)tblMain.getRow(i).getUserObject();
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
	    		
	    		//不选择,检查同级是否有选择的
	    		boolean hasSelected=false;
	    		//下面的行
	        	for(int i=row+1;i<tblMain.getRowCount();i++){
	        		acct = (ProgrammingContractInfo)tblMain.getRow(i).getUserObject();
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
	    		//上面的行
	        	
	        	if(!hasSelected){
	            	for(int i=row-1;i>=0;i--){
	            		acct = (ProgrammingContractInfo)tblMain.getRow(i).getUserObject();
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

	    			//设置父级
	    			parentLevel=level-1;
	            	for(int j=row;j>=0;j--){
	            		if(parentLevel==0){
	            			break;
	            		}
	            		acct = (ProgrammingContractInfo)tblMain.getRow(j).getUserObject();
	            		if(acct.getLevel()==parentLevel){
	            			ICell cell = tblMain.getCell(j, "select");
							cell.setValue(Boolean.FALSE);
							parentLevel--;
	            		}
	            	}
	    		
	        	}

	    	}
			
	    }
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
//		loadDatas();
		initTableData();
	}
	public ProgrammingContractCollection getData() throws Exception {
		return pcc;
	}
	public boolean isOk() {
		return isOk;
	}
}