/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ValuationModeFactory;
import com.kingdee.eas.fdc.basedata.ValuationModeInfo;
import com.kingdee.eas.fdc.migrate.StringUtils;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 计价模式
 *  @author liangliang_ye
 */
public class ValuationModeEditUI extends AbstractValuationModeEditUI {
	private static final Logger logger = CoreUIObject.getLogger(ValuationModeEditUI.class);

	public ValuationModeEditUI() throws Exception {
		super();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (StringUtils.isEmpty(txtNumber.getText())) {
			txtNumber.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NUMBER_IS_EMPTY);
		}
		if (StringUtils.isEmpty(editData.getName())) {
			txtName.requestFocus(true);
			throw new FDCBasedataException(FDCBasedataException.NAME_IS_EMPTY);
		}
	}

	protected IObjectValue createNewData() {
		ValuationModeInfo info = new ValuationModeInfo();
		info.setIsEnabled(true);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setCreateTime(new Timestamp(new Date().getTime()));

		try {
			String sql = "select count(fid) from  T_FDC_ValuationMode  where FNumber like '0%'";
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(sql);
			IRowSet rowSet = builder.executeQuery();
			int count = 0;
			while (rowSet.next()) {
				count = rowSet.getInt(1);
			}
			count++;
			NumberFormat fomat = new DecimalFormat("00");
			info.setNumber(fomat.format(count));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;

	}

	protected void initDataStatus() {
		super.initDataStatus();
		actionAddNew.setEnabled(isGroup());
		actionRemove.setEnabled(isGroup());
		actionEdit.setEnabled(isGroup());
		actionSave.setEnabled(isGroup());
		actionSubmit.setEnabled(isGroup());
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ValuationModeFactory.getRemoteInstance();
	}

	private boolean isGroup() {
		return OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());
	}
}