/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.scheme.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.metadata.data.DataTableInfo;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.sql.TransUtil;
import com.kingdee.bos.sql.dom.stmt.SqlStmt;
import com.kingdee.bos.sql.parser.Lexer;
import com.kingdee.bos.sql.parser.SqlStmtParser;
import com.kingdee.bos.sql.parser.TokenList;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.SimpleTimer;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.scheme.FdcEntityObjectUtil;
import com.kingdee.eas.fdc.basedata.util.FdcStringUtil;
import com.kingdee.eas.fi.gl.client.ClosePeriodUI;
import com.kingdee.eas.fm.common.EnvUtils;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.fm.common.client.FMIsqlUIHandler;
import com.kingdee.eas.fm.common.client.SQLStmtInfo;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FdcIsqlUI extends AbstractFdcIsqlUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4897878142703271851L;

	private static final Logger logger = CoreUIObject.getLogger(FdcIsqlUI.class);

	public final String ACTIONKEY_SHOW_DIRECT_BYUUID = "SHOW_DIRECT_BYUUID";

	/**
	 * output class constructor
	 */
	public FdcIsqlUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void initWorkButton() {
		super.initWorkButton();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kingdee.eas.fm.common.client.FMIsqlUI#onLoad()
	 */
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();

		initCtrl();

		detachListeners();
		initTableStyle();
		attachListeners();
	}

	public void initCtrl() {
	}

	protected void attachListeners() {
		ActionMap entryActionMap = this.getActionMap();

		// ACTIONKEY_SHOW_DIRECT_BYUUID
		entryActionMap.put(ACTIONKEY_SHOW_DIRECT_BYUUID, this.showDirectByUUIDListener);

	}

	protected void detachListeners() {
		ActionMap entryActionMap = this.getActionMap();
		entryActionMap.put(ACTIONKEY_SHOW_DIRECT_BYUUID, null);

	}

	protected void initTableStyle() {
		InputMap imEntry = this.getInputMap(ClosePeriodUI.WHEN_IN_FOCUSED_WINDOW);

		// ACTIONKEY_SHOW_DIRECT_BYUUID
		KeyStroke ctrl_l = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK);
		imEntry.put(ctrl_l, ACTIONKEY_SHOW_DIRECT_BYUUID);
	}

	protected EditShortcutListener showDirectByUUIDListener = new EditShortcutListener(ACTIONKEY_SHOW_DIRECT_BYUUID); //

	// updateLongname
	class EditShortcutListener extends AbstractAction {
		String shortcut;

		EditShortcutListener(String sc) {
			shortcut = sc;
		}

		public void actionPerformed(ActionEvent evt) {
			if (ACTIONKEY_SHOW_DIRECT_BYUUID.equals(shortcut)) {
				try {
					actionShowDirectByUUID_actionPerformed(null);
				} catch (Exception e) {
					e.printStackTrace();
					FdcIsqlUI.this.handleException(e);
				}
			}
		}
	}

	public void actionShowDirectByUUID_actionPerformed(ActionEvent e) throws Exception {
		super.actionShowDirectByUUID_actionPerformed(e);

		KDTable tbl = (KDTable) scrpRowsets.getViewport().getView();
		if (null == tbl) {
			MsgBox.showWarning(this, "没有查询结果");
			SysUtil.abort();
		} else {
			String uuid = getSelectUUID(tbl);
			showDirectByUUID(uuid);
		}
	}

	protected void showDirectByUUID(String uuid) throws Exception {
		// 取得实体，包括对应的数据表
		EntityObjectInfo entityInfo = FdcEntityObjectUtil.getEntity(null, uuid, true);
		DataTableInfo tableInfo = entityInfo.getTable();
		String tableName = tableInfo.getName();

		String sql = FdcEntityObjectUtil.generateQuerySql(null, entityInfo, tableInfo, uuid);
		if (FdcStringUtil.isBlank(sql)) {
			String msg = "PM字典表中没有" + tableName + "对应的实体对象，无法使用别名，是否使用普通方式查询？";
			int result = MsgBox.showConfirm2(this, msg);
			if (AdvMsgBox.OK_OPTION == result) {
				sql = "SELECT * FROM " + tableName + " WHERE FID = '" + uuid + "'";
			} else {
				SysUtil.abort();
			}
		}

		String oldText = this.txtScript.getText();
		String newText = oldText + "\n\n" + sql + ";";
		this.txtScript.setText(newText);

		_executeSQL(sql);
	}

	protected void _executeSQL(String sql) throws Exception {
		if (FdcStringUtil.isBlank(sql)) {
			return;
		}

		_executeSQL(sql, true);
	}

	protected void _executeSQL(String sql, boolean tryToEditable) throws BOSException, EASBizException, SQLException {
		SimpleTimer st = new SimpleTimer();
		IFMIsqlFacade isql = FMIsqlFacadeFactory.getRemoteInstance();
		com.kingdee.eas.fm.common.SqlResult rs = null;
		
		//在7.0环境构建不通过,因此此处注释, 在6.0环境单独使用sp包
//		try {
//			rs = isql.executeSql(sql);

//		} catch (BOSException exp) {
//			fillException(exp);
//		}

		long time = st.getTime();
		if (rs != null) {
			_showResult(rs, sql, tryToEditable);
		}
		scrpRowsets.repaint();
		txtMsg.insert("\ncomplete! use time:" + time + "\tend time " + new Date(), 0);
	}

	protected void _showResult(com.kingdee.eas.fm.common.SqlResult rs, String sql, boolean tryToEditable) throws SQLException {
		String nsql = sql;
		if (sql.startsWith(TransUtil.Dialect_Prefix)) {
			nsql = sql.substring(TransUtil.Dialect_Prefix.length());
		}
		SQLStmtInfo sqlStmtInfo;
		try {
			Lexer lexer = new Lexer(nsql);
			TokenList _tokList = new TokenList(lexer);
			SqlStmt sqlStmt = new SqlStmtParser(_tokList).stmt();
			sqlStmtInfo = FMIsqlUIHandler.getSQLStmtInfo(sqlStmt);
		} catch (ParserException pe) {
			// 解析错误或者是非ksql能解析的方言
			sqlStmtInfo = new SQLStmtInfo();
			sqlStmtInfo.canAudoUpdate = false;
			sqlStmtInfo.isSelect = false;
		}

		List rowSets = rs.getRowSets();

		if (rowSets == null || rowSets.size() <= 0) {
			tabMain.setSelectedComponent(scrpMsg);
		} else if (rowSets.size() > 1) {
			KDPanel pan = new KDPanel();
			pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
			pan.setSize(scrpRowsets.getSize());
			scrpRowsets.getViewport().add(pan);
			for (int i = 0; i < rowSets.size(); i++) {
				KDTable table = new KDTable();
				KDTMenuManager tableMenuMgr = new KDTMenuManager(table);
				tableMenuMgr.setTempFile(EnvUtils.getTempDir());

				table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
				table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

				fillData(table, (IRowSet) rowSets.get(i), sqlStmtInfo);

				table.setEditable(tryToEditable && sqlStmtInfo.canAudoUpdate);

				table.addKDTEditListener(new KDTEditAdapter() {

					public void editStopped(KDTEditEvent e) {
						table_editStopped(e);
					}

				});

				pan.add(table);
			}
			tabMain.setSelectedComponent(scrpRowsets);
		} else if (rowSets.size() == 1) {
			KDTable table = new KDTable();
			KDTMenuManager tableMenuMgr = new KDTMenuManager(table);
			tableMenuMgr.setTempFile(EnvUtils.getTempDir());

			table.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
			table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);

			fillData(table, (IRowSet) rowSets.get(0), sqlStmtInfo);

			table.setEditable(tryToEditable && sqlStmtInfo.canAudoUpdate);
			table.addKDTEditListener(new KDTEditAdapter() {

				public void editStopped(KDTEditEvent e) {
					table_editStopped(e);
				}

			});

			scrpRowsets.getViewport().add(table);

			scrpRowsets.getViewport().setLayout(new GridLayout(rowSets.size(), 1));
			tabMain.setSelectedComponent(scrpRowsets);
		}

		if (rs.getUpdateCount() > 0) {
			txtMsg.insert("\nupdate Count:" + rs.getUpdateCount(), 0);
		}
	}

	protected String getSelectUUID(KDTable tbl) {
		String uuid = null;

		Object uuidObj = FDCTableHelper.getOnlySelectCellValue(this, tbl);
		if (null == uuidObj) {
			MsgBox.showWarning(this, "单元格无值");
			SysUtil.abort();
		} else {
			uuid = uuidObj.toString().trim();
		}

		if (FdcStringUtil.isBlank(uuid)) {
			MsgBox.showWarning(this, "单元格无值");
			SysUtil.abort();
		}

		return uuid;
	}

}