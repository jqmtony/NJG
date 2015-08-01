package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.REAutoRememberCollection;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.REAutoRememberInfo;

public class REAutoRememberControllerBean extends AbstractREAutoRememberControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.REAutoRememberControllerBean");

	// TODO
	// protected void _save(Context ctx, IObjectValue reAutoRemember) throws
	// BOSException {
	// }

	protected void _save(Context ctx, String userID, String orgUnitID, String function, String value) throws BOSException {
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("userID", userID));
		filterInfo.getFilterItems().add(new FilterItemInfo("orgUnitID", orgUnitID));
		filterInfo.getFilterItems().add(new FilterItemInfo("function", function));

		try {
			if (_exists(ctx, filterInfo)) {
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.appendSql("update T_FDC_REAutoRemeber set FValue=? ");
				builder.appendSql("where FUserID=? and FOrgUnitID=? and FFunction=?");
				builder.addParam(value);
				builder.addParam(userID);
				builder.addParam(orgUnitID);
				builder.addParam(function);
				builder.executeUpdate();
			} else {
				REAutoRememberInfo autoRememberInfo = new REAutoRememberInfo();
				autoRememberInfo.setUserID(userID);
				autoRememberInfo.setOrgUnitID(orgUnitID);
				autoRememberInfo.setFunction(function);
				autoRememberInfo.setValue(value);
				this.save(ctx, autoRememberInfo);
			}

		} catch (EASBizException e) {
			e.printStackTrace();
		}

	}

	protected String _getValue(Context ctx, String userID, String orgUnitID, String function) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(new FilterItemInfo("userID", userID));
		filterInfo.getFilterItems().add(new FilterItemInfo("orgUnitID", orgUnitID));
		filterInfo.getFilterItems().add(new FilterItemInfo("function", function));
		view.setFilter(filterInfo);
		REAutoRememberCollection reAutoRemebers = REAutoRememberFactory.getLocalInstance(ctx).getREAutoRememberCollection(view);
		if (FDCHelper.isEmpty(reAutoRemebers) || reAutoRemebers.size() == 0) {
			return null;
		}
		return reAutoRemebers.get(0).getValue();
	}
}