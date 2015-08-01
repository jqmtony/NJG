/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ITargetType;
import com.kingdee.eas.fdc.basedata.TargetTypeFactory;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ����:ָ������
 * @author jackwang  date:2007-5-25 <p>
 * @version EAS4.1
 */
public class TargetTypeListUI extends AbstractTargetTypeListUI {
	private static final Logger logger = CoreUIObject.getLogger(TargetTypeListUI.class);

	/**
	 * output class constructor
	 */
	public TargetTypeListUI() throws Exception {
		super();
		//ȥ����ѯ���ܣ�ʡȥ�ɴ������һ��bugs
		this.btnQuery.setVisible(false);
		this.menuItemQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
	}

	/**
	 * 
	 */
	public void onLoad() throws Exception {
		this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener() {
			public void afterDataFill(KDTDataRequestEvent e) {
				// do something
				String strTemp = "";
				for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
					strTemp = tblMain.getRow(i).getCell(0).getValue().toString();
					strTemp = strTemp.replace('!', '.');
					tblMain.getRow(i).getCell(0).setValue(strTemp);
				}
			}
		});
		super.onLoad();
		if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())) {
			this.btnAddNew.setEnabled(true);
			this.btnEdit.setEnabled(true);
			this.btnRemove.setEnabled(true);
/*			this.btnEnabled.setVisible(true);
			this.btnDisEnabled.setVisible(true);*/
			this.actionCancelCancel.setVisible(true);
			this.actionCancel.setVisible(true);
			this.menuItemAddNew.setEnabled(true);
			this.menuItemEdit.setEnabled(true);
			this.menuItemRemove.setEnabled(true);
			// this.menuItemCancel.setv(true)
		} else {
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.btnRemove.setEnabled(false);
/*			this.btnEnabled.setVisible(false);
			this.btnDisEnabled.setVisible(false);*/
			this.actionCancelCancel.setVisible(false);
			this.actionCancel.setVisible(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
			this.menuItemRemove.setEnabled(false);
		}
		
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
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
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		String id=null;
		if(getSelectedTreeNode().getUserObject() instanceof TreeBaseInfo){
			id=getSelectedNodeKeyValue();
		}
    	if(id!=null){
	    	FilterInfo filter=new FilterInfo();
	    	filter.appendFilterItem("targetType.id", id);
	    	if(ApportionTypeFactory.getRemoteInstance().exists(filter)){
	    		MsgBox.showInfo(this,"�����Ѿ�������!");
	    		SysUtil.abort();
	    	}
    	}
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		verifyNotAccepted(row);
		super.actionRemove_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		// TODO �Զ����ɷ������
		return TargetTypeFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		// TODO �Զ����ɷ������
		return TargetTypeEditUI.class.getName();
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
		if (this.tblMain.getSelectManager().getActiveRowIndex() != -1) {
			if (this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled") != null) {
				boolean status = ((Boolean) this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex()).getCell("isEnabled").getValue()).booleanValue();
				// ����ÿһ�й����isEnabled��ֵ�ı䣬����WBT��״̬Ҳ�ı�
				changeWBTEnabeld(status);
			}
		} else {
			disabledWBT();

		}
	}

	/**
	 * ����ÿһ�й����isEnabled��ֵ�ı䣬����btn��״̬Ҳ�ı�
	 * 
	 * @param isEnabled
	 *            boolean
	 */
	private void changeWBTEnabeld(boolean isEnabled) {
		this.actionCancelCancel.setEnabled(!isEnabled);
		this.actionCancel.setEnabled(isEnabled);
	}

	/**
	 * ������/��ֹ��ťdisabled
	 */
	private void disabledWBT() {
		this.actionCancelCancel.setEnabled(false);
		this.actionCancel.setEnabled(false);
	}

	//	 ��д���ء����ڵ㡱���Ƶķ���
	protected String getRootName() {
		// return "root";
		return EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, FDCBaseDataClientUtils.TARGETTYPE);
	}

	/**
	 * output actionEnabled_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ITargetType iTargetType = TargetTypeFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iTargetType.enabled(pk)) {
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
			tblMain.removeRows();// �����²�ѯ
			this.actionCancelCancel.setEnabled(false);
			this.actionCancel.setEnabled(true);
		}
	}

	/**
	 * output actionDisEnabled_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		IRow row = checkSelected(e);
		if (row == null)
			return;
		String id = row.getCell("id").getValue().toString().trim();
		ITargetType iTargetType = TargetTypeFactory.getRemoteInstance();
		IObjectPK pk = new ObjectStringPK(id);
		if (iTargetType.disEnabled(pk)) {
			this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
			tblMain.removeRows();// �����²�ѯ
			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(true);
		}
	}

	private IRow checkSelected(ActionEvent e) {
		IRow row = null;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			// ����ָ��һ����¼
			MsgBox.showWarning("����ָ��һ����¼!");
			SysUtil.abort();
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}
		return row;
	}

	protected void showResultMessage(String message) {
		// setMessageText(EASResource.getString(message));
		setMessageText(message);
		// setMessageIcon(SHOW_MESSAGE_ICON_ERROR);
		// setMessageBgcolor(SHOW_MESSAGE_BG_ERROR);
		showMessage();
	}

	/**
	 * 
	 * ����Ƿ�ɾ������ϵͳԤ����Ϣ
	 */

	private void verifyNotAccepted(IRow row) {
		if (row.getCell("CU.id").getValue() != null && (OrgConstants.SYS_CU_ID.equals(row.getCell("CU.id").getValue().toString()))) {
			MsgBox.showError(com.kingdee.eas.util.client.EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "Delete_SysData"));
			SysUtil.abort();
		}
	}

	// Ĭ�Ͻ��е�ǰCU�Ĺ��ˡ���������ء�
	protected FilterInfo getDefaultFilterForQuery() {
		FilterInfo filter = new FilterInfo();

		if (isIgnoreCUFilter()) {
			return filter;
		}

		if (SysContext.getSysContext().getCurrentCtrlUnit() == null) {
			return filter;
		}

		// ��ǰCU������CU���˴���
		//        filter.getFilterItems().add(
		//                new FilterItemInfo(IFWEntityStruct.objectBase_CU, SysContext
		//                        .getSysContext().getCurrentCtrlUnit().getId()
		//                        .toString(), CompareType.EQUALS));
		//        filter.getFilterItems().add(
		//                new FilterItemInfo(IFWEntityStruct.objectBase_CU,
		//                        OrgConstants.SYS_CU_ID, CompareType.EQUALS));
		//        filter.getFilterItems().add(
		//                new FilterItemInfo(IFWEntityStruct.objectBase_CU,
		//                        OrgConstants.DEF_CU_ID, CompareType.EQUALS));
		//        filter.setMaskString("#0 or #1 or #2");
		return filter;
	}

	/**
	 * �����ѯ�ӽڵ����ݵĲ�ѯ������
	 * Ĭ��ʵ��Ϊ��EntityViewInfo�����һ��getQueryFieldName()=treeNodeInfo�Ĺ�������
	 * ����
	 */
	protected void buildTreeFilter() {
		KDTreeNode treeNode = getSelectedTreeNode();

		if (treeNode == null) {
			return;
		}

		if (mainQuery.getFilter() == null) {
			mainQuery.setFilter(new FilterInfo());
		}

		FilterItemCollection col = mainQuery.getFilter().getFilterItems();

		//�Ƴ���һ�εĹ����ֶ���
		Iterator iter = col.iterator();

		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		mainQuery.getFilter().remove("maskString");

		if (treeNode.getUserObject() instanceof TreeBaseInfo) {
			col.add(new FilterItemInfo(getLongNumberFieldName(), ((TreeBaseInfo) treeNode.getUserObject()).getLongNumber()));
			col.add(new FilterItemInfo(getLongNumberFieldName(), ((TreeBaseInfo) treeNode.getUserObject()).getLongNumber() + "!%", CompareType.LIKE));

			//            if (!isIncludeAllChildren())
			//            {
			//                col.add(new FilterItemInfo("level" ,
			//                                           new Integer( ( (TreeBaseInfo)
			//                    treeNode.getUserObject())
			//                    .getLevel() - +1)));
			//                mainQuery.getFilter().setMaskString("#0 or (#1 and #2)");
			//            }
			//            else
			//            {
			mainQuery.getFilter().setMaskString("#0 or #1");
			//            }
		} else {
			//            if (!isIncludeAllChildren())
			//            {
			//                col.add(new FilterItemInfo("level" , new Integer(1)));
			//            }
		}

		//����CU���ˡ�
		//        try
		//        {
		//            /*
		//            if(getDefaultFilterForQuery() != null && getDefaultFilterForQuery().size()>0)
		//            {
		//                if(this.mainQuery.getFilter() != null && this.mainQuery.getFilter().getFilterItems().size()>0)
		//                {
		//                
		//                this.mainQuery.getFilter().mergeFilter(getDefaultFilterForQuery(),
		//                    "AND");
		//                }
		//                else
		//                {
		//                    this.mainQuery.setFilter(getDefaultFilterForQuery());
		//                }
		//            }*/
		////            this.getEntityViewInfo(this.mainQuery);
		//            
		//        }
		//        catch (BOSException ex)
		//        {
		//            this.handleException(ex);
		//            this.abort();
		//        }

		iter = mainQuery.getSorter().iterator();

		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}

		if (getCurrentOrder() != null) {
			mainQuery.getSorter().add(getCurrentOrder());
		} else {
			mainQuery.getSorter().add(new SorterItemInfo(getLongNumberFieldName()));
		}
	}
	// ֧�ֱ��롢���ƵĶ�λ����
	protected String[] getLocateNames()
    {
        String[] locateNames = new String[2];
        locateNames[0] = IFWEntityStruct.tree_LongNumber;
        locateNames[1] = IFWEntityStruct.dataBase_Name;
        return locateNames;
    }
    protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 ����Ҫ������������������ȡUI�򿪷�ʽ��
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }

	// //////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////

	/**
	 * �������Ƿ�֧��EAS�߼�ͳ��(EAS800�����Ĺ���)
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