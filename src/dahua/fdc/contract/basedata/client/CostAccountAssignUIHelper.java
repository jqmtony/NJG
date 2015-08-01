package com.kingdee.eas.fdc.basedata.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Locale;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.ExceptionHandler;

public class CostAccountAssignUIHelper {

	public static CurProjectCollection getProjects() {
		
		CurProjectCollection coll = new CurProjectCollection();
		
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit == null || !currentFIUnit.isIsBizUnit()) return coll;
		
		 
		String fiUnitId = currentFIUnit.getId().toString();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", fiUnitId));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		view.getSelector().add("id");
		view.getSelector().add("longNumber");
		view.getSelector().add("name");
		try {
			coll = CurProjectFactory.getRemoteInstance().getCurProjectCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		
		return coll;
		
	}
	
	public static void loadProjects(KDTable table) {
		table.removeRows(false);
		CurProjectCollection projects = getProjects();
		IRow row = null;
		for (Iterator iter = projects.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			row = table.addRow();
			row.getCell("selected").setValue(Boolean.FALSE);
			row.getCell("id").setValue(element.getId().toString());
			row.getCell("longNumber").setValue(element.getLongNumber());
			row.getCell("Name").setValue(element.getName());
			
		}
	}
	
	public static ResultSet getCostAccounts(String projId) {
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		if(currentFIUnit == null || !currentFIUnit.isIsBizUnit()) return null;
		String fiUnitId = currentFIUnit.getId().toString();
		Locale locale = SysContext.getSysContext().getLocale();
		String loc = locale.getLanguage();
		FDCSQLBuilder builder = new FDCSQLBuilder();
		
		builder.appendSql("select * from (");
		
		builder.appendSql("select flongnumber, fname_" + loc + " fname, FIsLeaf, 1 isAssigned from T_FDC_CostAccount where FIsEnable = 1 and FCurProject = ");
		
		builder.appendParam(projId);
		
		builder.appendSql(" union ");
		
		builder.appendSql("select flongnumber, fname_" + loc + " fname, FIsLeaf, 0 isAssigned from T_FDC_CostAccount orgCC where FIsEnable = 1 and FFullOrgUnit = ");
		
		builder.appendParam(fiUnitId);
		
		builder.appendSql(" and not exists(select fid from t_fdc_costaccount proCC where FCurProject = ");
		
		builder.appendParam(projId);
		
		builder.appendSql(" and orgCC.flongnumber = proCC.flongnumber and FSRCCOSTACCOUNTID is not null)");
		
		builder.appendSql(") x order by flongnumber");
		
		ResultSet resultSet = null;
		try {
			resultSet = builder.executeQuery();
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
	
		return resultSet;
	}
	
	public static void loadCostAccounts(KDTable table, String projId, boolean isAssigned) {
		table.removeRows(false);
		ResultSet ccSet = getCostAccounts(projId);
		IRow row = null;
		try {
			while(ccSet.next()) {
				
				boolean isAss = ccSet.getBoolean("isAssigned");
				
				if(isAss == isAssigned) {
					row = table.addRow();
					
					row.getCell("selected").setValue(Boolean.valueOf(isAss));
					row.getCell("accountNumber").setValue(ccSet.getString("flongnumber"));
					row.getCell("accountName").setValue(ccSet.getString("fname"));
					row.getCell("isLeaf").setValue(Boolean.valueOf(ccSet.getBoolean("FIsLeaf")));
				}
			}
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
	}
	
	public static void selectAll(KDTable table, boolean select) {
        int rowCount = table.getRowCount();
        
        for (int i = 0; i < rowCount; i++) {
        	table.getCell(i, "selected").setValue(Boolean.valueOf(select));
		}

	}
}
