/**
 * output package name
 */
package com.kingdee.eas.port.markesupplier.subase.client;

import java.awt.event.*;

import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeCollection;
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeFactory;
import com.kingdee.eas.port.markesupplier.subase.MarketAccreditationTypeInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSplAreaInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.port.markesupplier.subase.MarketSupplierFileTypInfo;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;

/**
 * output class name
 */
public class MarketSupplierAppraiseTemplateListUI extends AbstractMarketSupplierAppraiseTemplateListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierAppraiseTemplateListUI.class);
    private MarketAccreditationTypeInfo supplierFileTypeInfo = null;
    /**
     * output class constructor
     */
    public MarketSupplierAppraiseTemplateListUI() throws Exception
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
    
    protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
    public void onLoad() throws Exception {
    	super.onLoad();
    	btnCancel.setVisible(true);
    	btnCancelCancel.setVisible(true);
    	if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
    		this.actionEdit.setEnabled(true);
    		this.actionAddNew.setEnabled(true);
    		this.actionRemove.setEnabled(true);
    	} else {
    		this.actionEdit.setEnabled(false);
    		this.actionAddNew.setEnabled(false);
    		this.actionRemove.setEnabled(false);
    	}
    	
    	this.actionGroupAddNew.setEnabled(false);
		KDTreeNode root = new KDTreeNode("评审类型");
		KingdeeTreeModel mode=new KingdeeTreeModel(root);
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view=new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable", Boolean.TRUE));	
		view.setFilter(filter);
		MarketAccreditationTypeCollection col=MarketAccreditationTypeFactory.getRemoteInstance().getMarketAccreditationTypeCollection(view);
		for(int i=0;i<col.size();i++){
			KDTreeNode child = new KDTreeNode(col.get(i).getName());
			child.setUserObject(col.get(i));
			root.add(child);
		}
		mode.setRoot(root);
		this.treeMain.setModel(mode);
		
		this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
    }

    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCancel_actionPerformed(e);
        refresh(e);
    }
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("supplierFileType", supplierFileTypeInfo);
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if(node!=null&&node.getUserObject() instanceof MarketAccreditationTypeInfo){
			supplierFileTypeInfo=(MarketAccreditationTypeInfo)node.getUserObject();
		}else{
			supplierFileTypeInfo=null;
		}
		this.execQuery();
	}
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		try {
			FilterInfo filter = new FilterInfo();
			if(node!=null&&node.getUserObject() instanceof MarketAccreditationTypeInfo){
				filter.getFilterItems().add(new FilterItemInfo("AccreditationType.id", supplierFileTypeInfo.getId().toString()));	
			}
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (BOSException e) {
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	ObjectUuidPK pk = new ObjectUuidPK(this.getSelectedKeyValue());
    	getBizInterface().cancelCancel(pk, getBizInterface().getValue(pk));
        refresh(e);
    }


    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	ObjectUuidPK pk = new ObjectUuidPK(this.getSelectedKeyValue());
    	MarketSupplierAppraiseTemplateInfo Info = (MarketSupplierAppraiseTemplateInfo)getBizInterface().getValue(pk);
    	if(Info.isIsEnable()){
    		MsgBox.showWarning("已启用，不能执行此操作！");SysUtil.abort();
    	}
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected();
    	ObjectUuidPK pk = new ObjectUuidPK(this.getSelectedKeyValue());
    	MarketSupplierAppraiseTemplateInfo Info = (MarketSupplierAppraiseTemplateInfo)getBizInterface().getValue(pk);
    	if(Info.isIsEnable()){
    		MsgBox.showWarning("已启用，不能执行此操作！");SysUtil.abort();
    	}
        super.actionRemove_actionPerformed(e);
    }
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateFactory.getRemoteInstance();
    }

    /**
     * output getTreeInterface method
     */
    protected ITreeBase getTreeInterface() throws Exception
    {
        return com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateTreeFactory.getRemoteInstance();
    }

    /**
     * output getGroupEditUIName method
     */
    protected String getGroupEditUIName()
    {
        return com.kingdee.eas.port.markesupplier.subase.client.MarketSupplierAppraiseTemplateTreeEditUI.class.getName();
    }

    /**
     * output getQueryFieldName method
     */
    protected String getQueryFieldName()
    {
        return "treeid.id";
    }

    /**
     * output getKeyFieldName method
     */
    protected String getKeyFieldName()
    {
        return "id";
    }

    /**
     * output getRootName method
     */
    protected String getRootName()
    {
        return "评审模板";
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo objectValue = new com.kingdee.eas.port.markesupplier.subase.MarketSupplierAppraiseTemplateInfo();
		
        return objectValue;
    }

}