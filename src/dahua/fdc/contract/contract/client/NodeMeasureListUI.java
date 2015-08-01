/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Rectangle;
import java.awt.event.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.contract.NodeMeasureCollection;
import com.kingdee.eas.fdc.contract.NodeMeasureFactory;
import com.kingdee.eas.fdc.contract.NodeMeasureInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class NodeMeasureListUI extends AbstractNodeMeasureListUI
{
    private static final Logger logger = CoreUIObject.getLogger(NodeMeasureListUI.class);
    
    //状态
    private static final String COL_STATE = "state";
    //日期
    private static final String COL_DATE = "date";
    //编码
    private static final String COL_NUMBER = "number";
    //工程项目
    private static final String COL_PROJECTNAME = "projectName";
    //合同
    private static final String COL_CONTRACTNAME = "contractName";
    //金额
    private static final String COL_AMOUNT = "amount";
    //制单人
    private static final String COL_CREATOR = "creator";
    //制单事件
    private static final String COL_CREATETIME = "createTime";
    //ID
    private static final String COL_ID = "id";
    
    /**
     * output class constructor
     */
    public NodeMeasureListUI() throws Exception
    {
        super();
    }


    public void storeFields()
    {
        super.storeFields();
    }

    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected(this.tblMain);
		super.actionAddNew_actionPerformed(e);
	}


	/**
     * 合同列表点击事件
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * 合同列表选择变化事件
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * 工程项目树变化事件
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
    }

    /**
     * 合同类型树变化事件
     */
    protected void treeContractType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeContractType_valueChanged(e);
    }

    /**
     * 审批
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * 反审批
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }

	protected ICoreBillBase getRemoteInterface() throws BOSException {
		return NodeMeasureFactory.getRemoteInstance();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return NodeMeasureFactory.getRemoteInstance();
	}
	
	protected void audit(List ids) throws Exception {
		NodeMeasureFactory.getRemoteInstance().audit(ids);
	}

	protected void unAudit(List ids) throws Exception {
		NodeMeasureFactory.getRemoteInstance().unAudit(ids);
		
	}
	//节点计价table
	protected KDTable getBillListTable() {
		return this.tableMeasure;
	}
	//是否显示合计行
	protected boolean isFootVisible() {
		return false;
	}

	//根据合同显示节点计价单据
	protected boolean displayBillByContract(KDTSelectEvent e, EntityViewInfo view) throws BOSException {
		if(view==null){
			return false ;
		}
		int pre = getPre(e);
		//设置精度
		String oriFormat = FDCClientHelper.getNumberFormat(pre,true);
		getBillListTable().getColumn(COL_AMOUNT).getStyleAttributes().setNumberFormat(oriFormat);
		NodeMeasureCollection  coll = NodeMeasureFactory.getRemoteInstance().getNodeMeasureCollection(view);
		for(Iterator it = coll.iterator();it.hasNext();){
			NodeMeasureInfo info = (NodeMeasureInfo)it.next();
			IRow row = getBillListTable().addRow();
			row.getCell(COL_CONTRACTNAME).setValue(info.getContractBill().getName());
			row.getCell(COL_NUMBER).setValue(info.getNumber());
			row.getCell(COL_PROJECTNAME).setValue(info.getCurProject().getName());
			row.getCell(COL_STATE).setValue(info.getState());
			row.getCell(COL_DATE).setValue(info.getBizDate());
			row.getCell(COL_CREATOR).setValue(info.getCreator().getName());
			row.getCell(COL_CREATETIME).setValue(info.getCreateTime());
			row.getCell(COL_AMOUNT).setValue(info.getAmount());
			row.getCell(COL_ID).setValue(info.getId().toString());
		}
		return true;
	}
	
	public void initUIContentLayout() {
		super.initUIContentLayout();
				
		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10, 250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10, 733, 609));
	}
    protected String getBillStatePropertyName() {
    	return "state";
    }
	//节点计价需要查询的字段
	protected SelectorItemCollection genBillQuerySelector() {
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("curProject.name");
		selectors.add("contractBill.name");
		selectors.add("number");
		selectors.add("state");
		selectors.add("bizDate");
		selectors.add("amount");
		selectors.add("creator.name");
		selectors.add("createTime");
		selectors.add("description");
		selectors.add("id");
		return selectors;
	}

	//根据合同获得查询和过滤条件EntityViewInfo
	protected EntityViewInfo genBillQueryView(KDTSelectEvent e) {
		return super.genBillQueryView(e);
	}

	//重载合同过滤条件 增加对节点计价合同的判断
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();
		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		//只显示节点计价合同
		filterItems.add(new FilterItemInfo("isMeasureContract",Boolean.TRUE));
		return filter;
	}
	protected void initTable() {
		super.initTable();
//		this.actionAttachment.setVisible(false);
		FDCHelper.formatTableNumber(tableMeasure, COL_AMOUNT);
		FDCHelper.formatTableDate(tableMeasure,COL_DATE);
		FDCHelper.formatTableDate(tableMeasure,COL_CREATETIME);
	}
	
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.NodeMeasureEditUI.class.getName();
	}
	
	/**
	 * 增加快速定位字段
	 * @author owen_wen 2010-09-07
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", "signDate", };
	}
}