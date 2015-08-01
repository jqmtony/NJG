package com.kingdee.eas.fdc.costdb.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashSet;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import java.math.BigDecimal;

import com.kingdee.eas.framework.app.TreeBaseControllerBean;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.costdb.MarketInfoTypeInfo;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.jdbc.rowset.IRowSet;

public class MarketInfoTypeControllerBean extends AbstractMarketInfoTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.costdb.app.MarketInfoTypeControllerBean");
    protected Object[] _getRelateData(Context ctx, String id, String[] tables)throws BOSException
    {
       	if (tables == null) {
			return new Object[0];
		}
		HashSet set = new HashSet();
		for (int i = 0; i < tables.length; i++) {
			String table = tables[i];
			String sql = "select count(*) as count from " + table
					+ " where FTypeID='" + id + "'";
			IRowSet rowSet = SQLExecutorFactory.getLocalInstance(ctx, sql)
					.executeSQL();
			try {
				if (rowSet.next()) {
					BigDecimal count = rowSet.getBigDecimal(1);
					if (count.intValue() > 0) {
						set.add(table);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e.getMessage());
			}
		}
		return set.toArray();
    }
    protected boolean _updateRelateData(Context ctx, String oldID, String newID, Object[] tables)throws BOSException
    {
        return false;
    }
}