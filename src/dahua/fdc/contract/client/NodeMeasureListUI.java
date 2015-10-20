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
    
    //״̬
    private static final String COL_STATE = "state";
    //����
    private static final String COL_DATE = "date";
    //����
    private static final String COL_NUMBER = "number";
    //������Ŀ
    private static final String COL_PROJECTNAME = "projectName";
    //��ͬ
    private static final String COL_CONTRACTNAME = "contractName";
    //���
    private static final String COL_AMOUNT = "amount";
    //�Ƶ���
    private static final String COL_CREATOR = "creator";
    //�Ƶ��¼�
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
     * ��ͬ�б����¼�
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * ��ͬ�б�ѡ��仯�¼�
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }

    /**
     * ������Ŀ���仯�¼�
     */
    protected void treeProject_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeProject_valueChanged(e);
    }

    /**
     * ��ͬ�������仯�¼�
     */
    protected void treeContractType_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        super.treeContractType_valueChanged(e);
    }

    /**
     * ����
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAudit_actionPerformed(e);
    }

    /**
     * ������
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
	//�ڵ�Ƽ�table
	protected KDTable getBillListTable() {
		return this.tableMeasure;
	}
	//�Ƿ���ʾ�ϼ���
	protected boolean isFootVisible() {
		return false;
	}

	//���ݺ�ͬ��ʾ�ڵ�Ƽ۵���
	protected boolean displayBillByContract(KDTSelectEvent e, EntityViewInfo view) throws BOSException {
		if(view==null){
			return false ;
		}
		int pre = getPre(e);
		//���þ���
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
	//�ڵ�Ƽ���Ҫ��ѯ���ֶ�
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

	//���ݺ�ͬ��ò�ѯ�͹�������EntityViewInfo
	protected EntityViewInfo genBillQueryView(KDTSelectEvent e) {
		return super.genBillQueryView(e);
	}

	//���غ�ͬ�������� ���ӶԽڵ�Ƽۺ�ͬ���ж�
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();
		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		//ֻ��ʾ�ڵ�Ƽۺ�ͬ
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
	 * ���ӿ��ٶ�λ�ֶ�
	 * @author owen_wen 2010-09-07
	 */
	protected String[] getLocateNames() {
		return new String[] {"number", "contractName", "partB.name", "contractType.name", "signDate", };
	}
}