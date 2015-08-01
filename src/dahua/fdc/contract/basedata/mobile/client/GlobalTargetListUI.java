/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.mobile.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.mobile.GlobalTargetBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class GlobalTargetListUI extends AbstractGlobalTargetListUI {

	private static final Logger logger = CoreUIObject.getLogger(GlobalTargetListUI.class);

	protected static final String COL_ID = "id";
	// protected static final String COL_SEQ_NUM = "seqNum";
	protected static final String COL_NUMBER = "number";
	protected static final String COL_NAME = "name";
	// protected static final String COL_IS_SEQ_EXE = "isSeqExe";
	protected static final String COL_STATE = "state";
	protected static final String COL_DESCRIPTION = "description";
	protected static final String COL_CREATOR_NAME = "creator.name";
	protected static final String COL_CREATE_TIME = "createTime";
	protected static final String COL_LAST_UPDATE_USER_NAME = "lastUpdateUser.name";
	protected static final String COL_LAST_UPDATE_TIME = "lastUpdateTime";
	protected static final String COL_AUDITOR_NAME = "auditor.name";
	protected static final String COL_AUDIT_TIME = "auditTime";

	/**
	 * output class constructor
	 */
	public GlobalTargetListUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * 描述：
	 * 
	 * @return
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#getBizInterface()
	 */
	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return GlobalTargetBillFactory.getRemoteInstance();
	}

	/**
	 * 描述：
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#getEditUIName()
	 */
	@Override
	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return GlobalTargetEditUI.class.getName();
	}

	/**
	 * 描述：
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#getEditUIModal()
	 */
	@Override
	protected String getEditUIModal() {
		// TODO Auto-generated method stub
		return super.getEditUIModal();
	}

	/**
	 * 描述：
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#onLoad()
	 */
	@Override
	public void onLoad() throws Exception {
		super.onLoad();
	}

	@Override
	protected void initTable() {
		// TODO Auto-generated method stub
		super.initTable();
	}

	/**
	 * 描述：
	 * 
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-29
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#initWorkButton()
	 */
	@Override
	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();

		// //////////////////////////////////////////////////////////////////
	}

	/**
	 * 描述：
	 * 
	 * @throws Exception
	 * @author rd_skyiter_wang
	 * @createDate 2014-10-31
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#afterOnLoad()
	 */
	protected void afterOnLoad() throws Exception {
		super.afterOnLoad();
	}

	/**
	 * 重设组件
	 */
	public void resetComponent() throws Exception {
		super.resetComponent();

		// /////////////////////////////////////////////////////////////////////

		KDTable tbl = getMainTable();

		// tbl.getColumn(COL_SEQ_NUM).getStyleAttributes().setHided((!(isShowMore || isEditting ||
		// isSuperEditting)));
		// tbl.getColumn(COL_CREATOR_NAME).getStyleAttributes().setHided(!isShowMore);
		// tbl.getColumn(COL_CREATE_TIME).getStyleAttributes().setHided(!isShowMore);
		// tbl.getColumn(COL_LAST_UPDATE_USER_NAME).getStyleAttributes().setHided(!isShowMore);
		// tbl.getColumn(COL_LAST_UPDATE_TIME).getStyleAttributes().setHided(!isShowMore);

		tbl.getColumn(COL_STATE).getStyleAttributes().setHided(!isSuperEditting);
		tbl.getColumn(COL_CREATOR_NAME).getStyleAttributes().setHided(!isSuperEditting);
		tbl.getColumn(COL_CREATOR_NAME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_CREATE_TIME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_LAST_UPDATE_USER_NAME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_LAST_UPDATE_TIME).getStyleAttributes().setHided(false);
		tbl.getColumn(COL_AUDITOR_NAME).getStyleAttributes().setHided(!isSuperEditting);
		tbl.getColumn(COL_AUDIT_TIME).getStyleAttributes().setHided(!isSuperEditting);

		// /////////////////////////////////////////////////////////////////////

		// 只有超级编辑状态下，才能导出
		FDCClientHelper.setActionEnV(actionExportBillSql, isSuperEditting);
		FDCClientHelper.setActionEnV(actionExportEntrySql, isSuperEditting);
		// FDCClientHelper.setActionEnV(actionExportItemSql, isSuperEditting);
		FDCClientHelper.setActionEnV(actionExportSql, isSuperEditting);

		// /////////////////////////////////////////////////////////////////////
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fdc.basedata.mobile.client.FdcMobileListUI#actionAddNew_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		FilterInfo filter = new FilterInfo();
		boolean isExists = getBizInterface().exists(filter);
		if (isExists) {
			MsgBox.showWarning(this, "只能新增一条记录");
			SysUtil.abort();
		}

		super.actionAddNew_actionPerformed(e);
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

	/**
	 * 描述：取得导出单据SQL对应的数据库表名称
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportBillTableName() {
		return "T_FDC_GlobalTargetBill";
	}

	/**
	 * 描述：取得导出分录SQL对应的数据库表名称
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportEntryTableName() {
		return "T_FDC_GlobalTargetEntry";
	}

	/**
	 * 描述：取得导出清单SQL对应的数据库表名称
	 * 
	 * @return
	 * @author rd_skyiter_wang
	 * @createDate 2014-11-14
	 */
	protected String getExportItemTableName() {
		return null;
		// return "T_FDC_GlobalTargetItem";
	}

	// //////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////

}