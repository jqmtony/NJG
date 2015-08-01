/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CostAccountMutilF7UI extends AbstractCostAccountMutilF7UI
{
    private static final Logger logger = CoreUIObject.getLogger(CostAccountMutilF7UI.class);
    private static final Color canntSelectColor = new Color(0xFEFED3);
    private boolean isCanceled  = true;
    /**
     * output class constructor
     */
    public CostAccountMutilF7UI() throws Exception
    {
        super();
    }
    public boolean isCanceled() {
		return isCanceled;
	}
    
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    
    public void onLoad() throws Exception {
    	super.onLoad();
    	kDTabbedPane1.setTitleAt(0, "成本科目");
    	kDTabbedPane1.setToolTipTextAt(0,"成本科目");
    	
		this.tblMain.addKDTEditListener(new KDTEditAdapter() {
			public void editValueChanged(KDTEditEvent e) {
				if(e.getColIndex()==tblMain.getColumnIndex("selected")){
					Boolean old=(Boolean)e.getOldValue();
					Boolean now=(Boolean)e.getValue();
					select(e.getRowIndex(),old.booleanValue(),now.booleanValue());
				}
			}
		});
		 
		Object view = this.getUIContext().get("view");
		EntityViewInfo viewInfo = (EntityViewInfo) view;
		FilterInfo filter =  viewInfo.getFilter();
		ProjectInfo projectInfo = (ProjectInfo) getUIContext().get("project");
		StringBuffer maskString  = new StringBuffer();
		int i  = 0;
		if(filter==null){
			filter  = new FilterInfo();
		}else{
			maskString.append("#").append(i);
			i++;
		}
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", projectInfo.getId().toString(), CompareType.EQUALS));
		if (i == 0) {
			maskString.append(" #").append(i);
		} else {
			maskString.append(" and #").append(i);
		}
        i++;
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE, CompareType.EQUALS));
		maskString.append(" and #").append(i);
		i++;
		filter.setMaskString(maskString.toString());
		this.loadDatas(filter);
    	
    	tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    }
    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {

    }
    
   
    public Object getData() {
    	ArrayList costAccountsSelected = new java.util.ArrayList();
		int rowCount = tblMain.getRowCount();
		for(int i =0; i<rowCount ;i++){
			IRow row  = tblMain.getRow(i);
			if (Boolean.valueOf(row.getCell("selected").getValue().toString()) == Boolean.TRUE) {
				CostAccountInfo acctSelect=(CostAccountInfo)tblMain.getRow(i).getUserObject();
				String longNumber = acctSelect.getLongNumber();
				int equalsCount = 0;
				for(int j =0; j<rowCount ;j++){
					IRow rowTemp  = tblMain.getRow(j);
					if (Boolean.valueOf(rowTemp.getCell("selected").getValue().toString()) == Boolean.TRUE) {
						CostAccountInfo acctSelectTemp=(CostAccountInfo)tblMain.getRow(j).getUserObject();
						if(acctSelectTemp.getLongNumber().startsWith(longNumber)){
							equalsCount++;							
						}
					}
				}
				if(equalsCount==1)//选中的最子集
					costAccountsSelected.add(acctSelect);
			}
		}
     	return costAccountsSelected.toArray();
    }
    
    
    protected void btnOk_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	setCanceled(false);
    	this.getUIWindow().close();
    }
    
    protected void btnClose_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    	this.getUIWindow().close();
    }


 
    
    
    protected void tblMain_editValueChanged(KDTEditEvent e) throws Exception {
		
    }
    
    
    
    
    protected ICoreBase getBizInterface() throws Exception {
    	return CostAccountFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}
//	public SelectorItemCollection getSelectors(){
//		SelectorItemCollection sic = new SelectorItemCollection();
//	    sic.add(new SelectorItemInfo("id"));
//	    sic.add(new SelectorItemInfo("longNumber"));
//	    sic.add(new SelectorItemInfo("name"));
//	    sic.add(new SelectorItemInfo("displayName"));
//	    sic.add(new SelectorItemInfo("level"));
//	    sic.add(new SelectorItemInfo("isLeaf"));
//        return sic;
//	}
	
	
	private void loadDatas(FilterInfo filter) throws BOSException {
		this.tblMain.removeRows();
		ICostAccount iCostAccount = CostAccountFactory.getRemoteInstance();
		EntityViewInfo evInfo = new EntityViewInfo();
		evInfo.getSelector().add("id");
		evInfo.getSelector().add("longNumber");
		evInfo.getSelector().add("name");
		SelectorItemCollection selectors=evInfo.getSelector();    
		selectors.add("number");	
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
		evInfo.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		evInfo.getSorter().add(sii);
		CostAccountCollection costAccountCollection = iCostAccount.getCostAccountCollection(evInfo);
		tblMain.getColumn("selected").getStyleAttributes().setLocked(false);
		tblMain.getColumn("number").getStyleAttributes().setLocked(false);
		CellTreeNode node=null;
		if (costAccountCollection.size() != 0) {
			for (int i = 0; i < costAccountCollection.size(); i++) {
				IRow row = this.tblMain.addRow();
				node=new CellTreeNode();
				row.getCell("selected").setValue(Boolean.valueOf(false));
				final CostAccountInfo costAccountInfo = costAccountCollection.get(i);
				row.getCell("name").setValue(costAccountInfo.getName(SysContext.getSysContext().getLocale()));
				row.getCell("id").setValue(costAccountInfo.getId().toString());
				row.setUserObject(costAccountInfo);
				
				node.setValue(costAccountInfo.getLongNumber().replace('!', '.'));
				final int level = costAccountInfo.getLevel()-1;
				final boolean isLeaf = costAccountInfo.isIsLeaf();
				node.setTreeLevel(level);
				
				if(isLeaf){
					node.setHasChildren(false);
					if(level>1){
						row.getStyleAttributes().setHided(false);
					}
				}else{
					if(level<=1){
						if(level==0){
							node.setCollapse(false);
						}else{ 
							node.setCollapse(false);
						}
					}else{
						node.setCollapse(false);//是否只隐藏根结点
						row.getStyleAttributes().setHided(false);
					}
					node.addClickListener(new NodeClickListener(){
						public void doClick(CellTreeNode source, ICell cell,
								int type) {
							tblMain.revalidate();
							
						}
					});
					node.setHasChildren(true);
					row.getStyleAttributes().setBackground(canntSelectColor);
				}

				row.getCell("number").setValue(node);

			}
			
		}		
	}
	
	public void select(int row, boolean old, boolean now) {
		if(old==now) return;
		tblMain.getCell(row, "selected").setValue(Boolean.valueOf(now));
		CostAccountInfo acctSelect=(CostAccountInfo)tblMain.getRow(row).getUserObject();
		CostAccountInfo acct=null;
		int level=acctSelect.getLevel();
		//下级
		if(!now)
    	for(int i=row+1;i<tblMain.getRowCount();i++){
    		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
    		if(acct.getLevel()>level){
    			tblMain.getCell(i, "selected").setValue(Boolean.valueOf(now));
    			
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
        		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==parentLevel){
        			ICell cell = tblMain.getCell(i, "selected");
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
        		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
        		if(acct.getLevel()==level){
        			ICell cell = tblMain.getCell(i, "selected");
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
            		acct = (CostAccountInfo)tblMain.getRow(i).getUserObject();
            		if(acct.getLevel()==level){
            			ICell cell = tblMain.getCell(i, "selected");
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
            		acct = (CostAccountInfo)tblMain.getRow(j).getUserObject();
            		if(acct.getLevel()==parentLevel){
            			ICell cell = tblMain.getCell(j, "selected");
						cell.setValue(Boolean.FALSE);
						parentLevel--;
            		}
            	}
    		
        	}

    	}
		
	}
	
	
}