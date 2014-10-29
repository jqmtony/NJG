package com.kingdee.eas.port.pm.invest.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
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
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.xr.XRBillBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.xr.app.XRBillBaseControllerBean;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestCollection;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestFactory;
import com.kingdee.eas.port.pm.invest.ProjectStartRequestInfo;
import com.kingdee.eas.port.pm.invest.client.YIPlanAccredEditUI;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingCollection;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingEntryInfo;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingFactory;
import com.kingdee.eas.port.pm.invest.investplan.ProgrammingInfo;

public class ProjectStartRequestControllerBean extends AbstractProjectStartRequestControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.port.pm.invest.app.ProjectStartRequestControllerBean");

	protected void _audit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		super._audit(ctx, pk);
		ProjectStartRequestInfo reqInfo = ProjectStartRequestFactory.getLocalInstance(ctx).getProjectStartRequestInfo(pk);
		ProgrammingInfo info = new ProgrammingInfo();
		try {
			ProgrammingCollection coll = ProgrammingFactory.getLocalInstance(ctx).getProgrammingCollection("where SourceBillId='"+reqInfo.getId()+"' order by version desc");
			if(coll.size()>0){
				info = coll.get(0);
				info = ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(new ObjectUuidPK(info.getId()));
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					entry.setIsInvite(true);//最新
					for(int j=0;j<entry.getCostEntries().size();j++){
						entry.getCostEntries().get(j).setBeizhu("最新");
					}
				}
				ProgrammingFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void _unAudit(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		super._unAudit(ctx, pk);
		ProjectStartRequestInfo reqInfo = ProjectStartRequestFactory.getLocalInstance(ctx).getProjectStartRequestInfo(pk);
		ProgrammingInfo info = new ProgrammingInfo();
		try {
			ProgrammingCollection coll = ProgrammingFactory.getLocalInstance(ctx).getProgrammingCollection("where SourceBillId='"+reqInfo.getId()+"' order by version desc");
			if(coll.size()>0){
				info = coll.get(0);
				info = ProgrammingFactory.getLocalInstance(ctx).getProgrammingInfo(new ObjectUuidPK(info.getId()));
				for(int i=0;i<info.getEntries().size();i++){
					ProgrammingEntryInfo entry = info.getEntries().get(i);
					entry.setIsInvite(false);//最新
					for(int j=0;j<entry.getCostEntries().size();j++){
						entry.getCostEntries().get(j).setBeizhu("");
					}
				}
				ProgrammingFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void _unAudit(Context ctx, IObjectPK[] pks) throws BOSException,EASBizException {
		super._unAudit(ctx, pks);
	}
	
	
   
}