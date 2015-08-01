package com.kingdee.eas.fdc.costdb.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.costdb.FdcMaterialCollection;
import com.kingdee.eas.fdc.costdb.FdcMaterialInfo;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.framework.util.FilterUtility;

public class FdcMaterialControllerBean extends AbstractFdcMaterialControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.costdb.app.FdcMaterialControllerBean");

	/**
	 * 新增逻辑检查 1.检查编码名称是否为空。 2.检查编码名称是否重复。
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 */
	protected void addnewCheck(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// this._checkNumberBlank(ctx, model);
		// this._checkNameBlank(ctx, model);
//		this._checkNumberDup(ctx, model);
		// this._checkNameDup(ctx, model);
	}

	/**
	 * 
	 * 描述：由于基类中该方法进行了增加了CU过滤判断， 但是进行number是否重复和CU没有关系，暂时重载
	 * 
	 * @author:dongpeng
	 * @see com.kingdee.eas.framework.app.AbstractDataBaseControllerBean#_checkNumberDup(com.kingdee.bos.Context,
	 *      com.kingdee.bos.dao.IObjectValue)
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		FdcMaterialInfo fmi = (FdcMaterialInfo)model;
		DataBaseInfo dataBaseInfo = (DataBaseInfo) model;
		FilterInfo filter = new FilterInfo();
		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS);
		filter.getFilterItems().add(filterItem);
		if (dataBaseInfo.getId() != null) {
			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
			filter.getFilterItems().add(filterItem);
			// filter.setMaskString("#1 and #2");
		}
		filter.getFilterItems().add(new FilterItemInfo("project.id",fmi.getProject().getId().toString()));//工程项目隔离
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
		// @todo 由于getPKList效率很低，使用exists()替代。
		// IObjectPK[] pks = super._getPKList(ctx,filter,sorter);

		// if(pks.length>0)
		if (super._exists(ctx, filter)) {
			String number = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Number) + dataBaseInfo.getNumber();
			throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
		}

	}
}