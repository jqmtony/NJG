package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK; //import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean; //import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.basedata.ContractCostPropertyCollection;

public class ContractCostPropertyControllerBean extends AbstractContractCostPropertyControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ContractCostPropertyControllerBean");

	protected boolean _disEnabled(Context ctx, IObjectPK ctPK, IObjectValue model) throws BOSException, EASBizException {
		return super._disEnabled(ctx, ctPK, model);
	}

	protected boolean _enabled(Context ctx, IObjectPK ctPK, IObjectValue model) throws BOSException, EASBizException {
		return super._enabled(ctx, ctPK, model);
	}

	// private boolean setEnable(Context ctx, IObjectPK ctPK, IObjectValue
	// model, boolean flag) throws EASBizException, BOSException {
	// IFDCDataBase m = (IFDCDataBase) model;
	// FDCDataBaseInfo dataBaseInfo = m.getFDCDataBaseInfo(ctPK);
	// dataBaseInfo.setIsEnabled(flag);
	// _update(ctx, ctPK, dataBaseInfo);
	// return true;
	// }

//	private boolean setEnable(Context ctx, IObjectPK ctPK, IObjectValue model, boolean flag) throws EASBizException, BOSException {
//		if (model == null) {
//			model = this.getValue(ctx, ctPK);
//		}
//
//		ContractCostPropertyInfo m = (ContractCostPropertyInfo) model;
//		// FDCDataBaseInfo dataBaseInfo = m.getFDCDataBaseInfo(ctPK);
//		m.setIsEnabled(flag);
//		_update(ctx, ctPK, m);
//		return true;
//	}
}