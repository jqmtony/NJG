package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Iterator;

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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.framework.app.CoreBaseControllerBean;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class GuerdonOfPayReqBillControllerBean extends AbstractGuerdonOfPayReqBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.GuerdonOfPayReqBillControllerBean");
    protected void _reCalcAmount(Context ctx, String payReqID)throws BOSException, EASBizException
    {
    }
    protected Result _addnew(Context ctx, IObjectCollection colls) throws BOSException, EASBizException {
    	Result result=new Result();
    	LineResult line;
    	for (Iterator iter = colls.iterator(); iter.hasNext();) {
			GuerdonOfPayReqBillInfo info = (GuerdonOfPayReqBillInfo) iter.next();
			final IObjectPK pk = _addnew(ctx, info);
			line=new LineResult();
			line.setPk(pk);
			line.setSucess(true);
			result.addLineResult(line);
		}
    	return result;
    }
}