/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.view.MetaDataBriefInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fm.common.EJBAccessFactory;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 更改单据经办人
 * @author owen_wen 
 * @date 2012-12-24
 */
public class AssignHandlerUI extends AbstractAssignHandlerUI {
    private static final Logger logger = CoreUIObject.getLogger(AssignHandlerUI.class);
    private final String baseQuerySQLStmt = "select mainTable.fid boID, creator.fname_l2 creator, handler.fname_l2 handler, mainTable.fbizDate bizDate, "
		+ "mainTable.fstate, mainTable.fnumber, mainTable.fname from tableName as mainTable \n" 
		+ " left join T_PM_User creator on creator.fid = mainTable.FCreatorID \n "
		+ " left join T_PM_User handler on handler.fid = mainTable.FHandlerID \n"
		+ " left join T_BD_Person person ";    
    private final String baseUpdateSQLStmt = "update tableName set FHandlerId = _handlerID_ where fid in (ids)";
    
	private StringBuffer whereClause = new StringBuffer();
    
    public AssignHandlerUI() throws Exception {
        super();
    }
    
	public void onLoad() throws Exception {
		super.onLoad();
		initEntitySelectorF7();
		initCreatorF7();
		initHandlerF7();
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.kDContainer1.add(actionAllSelect).setIcon(EASResource.getIcon("imgTbtn_selectall"));
		this.kDContainer1.add(actionNoneSelect).setIcon(EASResource.getIcon("imgTbtn_deleteall"));
	}
	
	/**
	 * 初始化房地产实体选择器
	 */
	private void initEntitySelectorF7() throws Exception {
		prmtBillF7.setSelector(new FDCEntitySelectorF7());
		prmtBillF7.setDisplayFormat("$alias$");
	}
	
	private void initCreatorF7() {
		FDCClientUtils.setPersonF7(this.prmtCreatorF7, this, null);
	}
	
	private void initHandlerF7() {
		FDCClientUtils.setPersonF7(this.prmtHandlerF7, this, null);
	}
	
	private void initWhereClause() {
		whereClause.setLength(0);
		whereClause.append(" on person.fid = creator.FPersonId \n"); // 默认与制单人ID比较
		PersonInfo person = (PersonInfo) prmtCreatorF7.getData();
		whereClause.append(" where person.fid = '" + person.getId().toString() + "' \n");
	}

	/**
	 *  初始化sql查询语句
	 * @throws BOSException 
	 * @throws BOSException 
	 */
	private String getSQLStatement() throws BOSException {
		StringBuffer sqlStatement = new StringBuffer();
		sqlStatement.append(baseQuerySQLStmt.replaceFirst("tableName", getTableName()));
		initWhereClause();
		sqlStatement.append(whereClause);
		return sqlStatement.toString();
	}

	private String getTableName() throws BOSException {
		MetaDataBriefInfo metaDataInfo = (MetaDataBriefInfo) prmtBillF7.getData();
		EntityObjectInfo eoi = EJBAccessFactory.createRemoteInstance().getEntityInfo(metaDataInfo.getBOType());
		return eoi.getTable().getName();
	}

	protected void prmtBillF7_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() == null || prmtCreatorF7.getData() == null)
			return;

		fillTable();
	}
	
	protected void prmtCreatorF7_dataChanged(DataChangeEvent e) throws Exception {
		if (e.getNewValue() == null || prmtBillF7.getData() == null)
			return;

		fillTable();
	}

	private IRowSet getQueryData() throws BOSException {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql(getSQLStatement());
		logger.info(sqlBuilder.getTestSql());
		return sqlBuilder.executeQuery();
	}
	
	private void fillTable() throws BOSException, SQLException {
		tblMain.removeRows(false);
		IRowSet rowSet = getQueryData();
		while (rowSet.next()) {
			IRow row = tblMain.addRow();
			row.getCell("isSelected").setValue(Boolean.FALSE);
			row.getCell("boId").setValue(rowSet.getString("boID"));
			row.getCell("creator").setValue(rowSet.getString("creator"));
			row.getCell("handler").setValue(rowSet.getString("handler"));
			row.getCell("bizDate").setValue(rowSet.getDate("bizDate"));
			row.getCell("state").setValue(FDCBillStateEnum.getEnum(rowSet.getString("fState")));
			row.getCell("number").setValue(rowSet.getString("fnumber"));
			row.getCell("name").setValue(rowSet.getString("fname"));
		}		
	}
	
	private String getUpdateSQLStmt() throws BOSException, SQLException {
		PersonInfo person = (PersonInfo) prmtHandlerF7.getData();
		if (person == null) {
			FDCMsgBox.showInfo("请先选择转交人。");
			prmtHandlerF7.setFocusable(true);
			this.abort();
		}

		String newHandlerId = "(select fid from T_PM_User where FPersonId = '" + person.getId().toString() + "')";
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(newHandlerId);
		IRowSet rowSet = sqlBuilder.executeQuery();
		if (rowSet.size() > 1) {
			FDCMsgBox.showInfo("待转交人对应了多个用户，无法确定转交人，请系统管理员重新指定用户与职员的对应关系。");
			this.abort();
		} else if (rowSet.size() == 0) {
			FDCMsgBox.showInfo("待转交人没有对应用户，无法确定转交人，请系统管理员重新指定用户与职员的对应关系。");
			this.abort();
		}
		
		String updateSQLStmt = baseUpdateSQLStmt.replaceFirst("tableName", getTableName());
		rowSet.next();
		updateSQLStmt = updateSQLStmt.replaceFirst("_handlerID_", "'" + rowSet.getString(1) + "'");
		
		String ids = getSelectedId();
		if (ids.length() == 0) {
			FDCMsgBox.showInfo("您没有选择待转交的单据，请先从单据列表中选择。");
			this.abort();
		}

		updateSQLStmt = updateSQLStmt.replaceFirst("ids", ids);
		logger.info(updateSQLStmt);
		return updateSQLStmt;
	}
	
	private String getSelectedId() {
		StringBuffer ids = new StringBuffer();
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow currentRow = tblMain.getRow(i);
			if (new Boolean(currentRow.getCell("isSelected").getValue().toString()).booleanValue()) {
				if (ids.length() > 0) {
					ids.append(", ");
				}
				ids.append("'" + currentRow.getCell("boId").getValue() + "'");
			}
		}
		
		return ids.toString();
	}
	
	public void actionBtnConfirm_actionPerformed(ActionEvent e) throws Exception {
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(getUpdateSQLStmt());
		sqlBuilder.execute();
		fillTable(); // 重新取数，刷新表格
	}

	public void actionBtnCancle_actionPerformed(ActionEvent e) throws Exception {
		getUIWindow().close();
	}
	
	public void actionAllSelect_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			tblMain.getRow(i).getCell("isSelected").setValue(Boolean.TRUE);
		}
	}

	public void actionNoneSelect_actionPerformed(ActionEvent e) throws Exception {
		for(int i = 0; i<tblMain.getRowCount(); i++){
			tblMain.getRow(i).getCell("isSelected").setValue(Boolean.FALSE);
		}
	}
}