package com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Date;

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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillInfo;
import com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.OtherSplitBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.app.CoreBillBaseControllerBean;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class OtherSplitBillControllerBean extends AbstractOtherSplitBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.aimcost.prjdynamiccostbill.app.OtherSplitBillControllerBean");
    /**
     * …Û∫À
     */
    protected void _audit(Context ctx, IObjectValue model) throws BOSException {
//    	super._audit(ctx, model);
    	OtherSplitBillInfo info = (OtherSplitBillInfo) model;
    	info.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
    	info.setAuditTime(new Date());
    	info.setState(FDCBillStateEnum.AUDITTED);
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("state"));
    	sic.add(new SelectorItemInfo("auditor"));
    	sic.add(new SelectorItemInfo("auditTime"));
    	
    	try {
    		_updatePartial(ctx, info, sic);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    /**
     * ∑¥…Û∫À
     */
    protected void _unAudit(Context ctx, IObjectValue model)
    		throws BOSException {
//    	super._unAudit(ctx, model);
    	OtherSplitBillInfo info = (OtherSplitBillInfo) model;
    	info.setAuditor(null);
    	info.setAuditTime(null);
    	info.setState(FDCBillStateEnum.SUBMITTED);
    	
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("state"));
    	sic.add(new SelectorItemInfo("auditor"));
    	sic.add(new SelectorItemInfo("auditTime"));
    	
    	try {
    		_updatePartial(ctx, info, sic);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
}