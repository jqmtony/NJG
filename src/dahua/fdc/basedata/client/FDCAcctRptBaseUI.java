/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class FDCAcctRptBaseUI extends AbstractFDCAcctRptBaseUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCAcctRptBaseUI.class);
    
    protected Map acctMap = new HashMap();
    /**
     * output class constructor
     */
    public FDCAcctRptBaseUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void execQuery() {
		// TODO Auto-generated method stub
		super.execQuery();
	}
	
    public void onLoad() throws Exception {
    	// TODO Auto-generated method stub
    	super.onLoad();
    	initControl();
    }
    protected void initTable() throws Exception{
    	KDTable table = this.tblMain;
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		table.getViewManager().setFreezeView(-1, 2);
//		table.setRefresh(false);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
		table.getColumn("acctName").getStyleAttributes().setLocked(true);
		if(table.getHeadRowCount()==1){
			table.addHeadRow(1, (IRow) table.getHeadRow(0).clone());
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		table.setColumnMoveable(true);
    }
    protected void fillTable()  throws Exception {
    	tblMain.removeRows(false);
		FilterInfo acctFilter = new FilterInfo();
		acctFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnterDB", new Integer(1)));
		TreeModel costAcctTree = null;
		// 根据当前指定项目条件构造科目树
		costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory
				.getRemoteInstance(), acctFilter);
		 this.initAcct(acctFilter);

		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		tblMain.getTreeColumn().setDepth(maxLevel);
		 for (int i = 0; i < root.getChildCount(); i++) {
			 fillNode((DefaultMutableTreeNode) root.getChildAt(i), tblMain);
		 }
		
		setUnionData(tblMain);
	}
	
    protected void fetchData() throws Exception{
		
	}
	protected List getUnionColumns(){
		List columns = new ArrayList();
		columns.add("amount");
		return columns;
	}
	protected void setUnionData(KDTable table){
		
	}
	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}
	protected void initAcct(FilterInfo acctFilter) throws BOSException {
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnterDB", new Integer(1)));
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("number"));
		sel.add(new SelectorItemInfo("name"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
	}
	
	protected void fillNode(DefaultMutableTreeNode node, KDTable table) throws BOSException, SQLException, EASBizException {
		
	}
	protected void initControl() {
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnView.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
//		this.btnLocate.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemView.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
//		this.menuItemLocate.setVisible(false);
		this.menuEdit.setVisible(false);
	}
	protected FDCCustomerParams objParam = new FDCCustomerParams();
	protected FDCCustomerParams getParas() {
		return objParam; 
	}
	

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * 描述：是否支持EAS高级统计(EAS800新增的功能)
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2015-4-1
	 */
	// @Override
	protected boolean isSupportEASPivot() {
		// return super.isSupportEASPivot();
		return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

}