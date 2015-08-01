package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.util.app.DbUtil;
/**
 * 描述:变更原因
 * @author jackwang  date:2006-7-7 <p>
 * @version EAS5.1
 */
public class ChangeReasonControllerBean extends AbstractChangeReasonControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ChangeReasonControllerBean");

	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ChangeReason set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(1), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ChangeReason set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(0), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	
	// Added by Owen_wen 2011-04-05
	// 变更原因名称 字段 修改为 同一个类型下不允许重复， 不同类型允许重复。
	protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		ChangeReasonInfo changeReasonInfo = (ChangeReasonInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Name, changeReasonInfo.getName(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (changeReasonInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, changeReasonInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
		}
		
		// 同一变更类型下的变更名称不能重复
		if (changeReasonInfo.getChangeType() != null) {
			filter.getFilterItems().add(new FilterItemInfo("changeType", changeReasonInfo.getChangeType().getId().toString()));
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));

		if (super._exists(ctx, filter)) {
			String name = this._getPropertyAlias(ctx, changeReasonInfo, IFWEntityStruct.dataBase_Name) + changeReasonInfo.getName();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { name });
		}
	}
}