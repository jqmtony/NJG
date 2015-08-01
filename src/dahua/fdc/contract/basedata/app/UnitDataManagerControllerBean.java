package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Iterator;

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
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesFactory;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DirectorCollection;
import com.kingdee.eas.fdc.basedata.DirectorFactory;
import com.kingdee.eas.fdc.basedata.DirectorInfo;
import com.kingdee.eas.fdc.basedata.ICurProjProductEntries;
import com.kingdee.eas.fdc.basedata.IDirector;
import com.kingdee.eas.fdc.basedata.IInnerManager;
import com.kingdee.eas.fdc.basedata.ILicenceManager;
import com.kingdee.eas.fdc.basedata.IRightManager;
import com.kingdee.eas.fdc.basedata.ISelfDirector;
import com.kingdee.eas.fdc.basedata.ITaxManager;
import com.kingdee.eas.fdc.basedata.IWatcher;
import com.kingdee.eas.fdc.basedata.InnerManagerFactory;
import com.kingdee.eas.fdc.basedata.InnerManagerInfo;
import com.kingdee.eas.fdc.basedata.LicenceManagerFactory;
import com.kingdee.eas.fdc.basedata.LicenceManagerInfo;
import com.kingdee.eas.fdc.basedata.RightManagerCollection;
import com.kingdee.eas.fdc.basedata.RightManagerFactory;
import com.kingdee.eas.fdc.basedata.RightManagerInfo;
import com.kingdee.eas.fdc.basedata.SelfDirectorCollection;
import com.kingdee.eas.fdc.basedata.SelfDirectorFactory;
import com.kingdee.eas.fdc.basedata.SelfDirectorInfo;
import com.kingdee.eas.fdc.basedata.TaxManagerCollection;
import com.kingdee.eas.fdc.basedata.TaxManagerFactory;
import com.kingdee.eas.fdc.basedata.TaxManagerInfo;
import com.kingdee.eas.fdc.basedata.UnitDataManagerCollection;
import com.kingdee.eas.fdc.basedata.WatcherCollection;
import com.kingdee.eas.fdc.basedata.WatcherFactory;
import com.kingdee.eas.fdc.basedata.WatcherInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.basedata.UnitDataManagerInfo;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;
/**
 * 描述:组织数据管理
 * @author jackwang  date:2007-5-17 <p>
 * @version EAS5.3
 */
public class UnitDataManagerControllerBean extends AbstractUnitDataManagerControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.UnitDataManagerControllerBean");
    protected IObjectPK _addnew(Context ctx, IObjectValue model) throws BOSException, EASBizException
    {
		IObjectPK objectPK = null;
		UnitDataManagerInfo udmi = (UnitDataManagerInfo) model;
//		营业执照信息
		LicenceManagerInfo lmi = udmi.getLicenceManager();
		if(lmi!=null){
			IObjectPK lmiPK = LicenceManagerFactory.getLocalInstance(ctx).addnew(lmi);
			lmi.put("id", lmiPK.getKeyValue("id"));
			udmi.setLicenceManager(lmi);
		}

//		内部管理信息
		InnerManagerInfo imi = udmi.getInnerManager();
		if(imi!=null){
			IObjectPK imiPK = InnerManagerFactory.getLocalInstance(ctx).addnew(imi);
			imi.put("id", imiPK.getKeyValue("id"));
			udmi.setInnerManager(imi);
		}
		//
		objectPK = super._addnew(ctx, model);
		udmi.put("id", objectPK.getKeyValue("id"));
		//税务信息
		TaxManagerCollection tmc = udmi.getTaxManager();
		if(tmc!=null&&tmc.size()!=0){
			ITaxManager iTaxManager = TaxManagerFactory.getLocalInstance(ctx);
			Iterator iterator = tmc.iterator();
			TaxManagerInfo ti;
			while (iterator.hasNext()) {
				ti = (TaxManagerInfo) iterator.next();
				ti.setParent(udmi);
				iTaxManager.addnew(ti);
			}
		}	
		
		//董事
		DirectorCollection dc = udmi.getDirector();
		if(dc!=null&&dc.size()!=0){
			IDirector iDirector = DirectorFactory.getLocalInstance(ctx);
			Iterator iterator = dc.iterator();
			DirectorInfo di;
			while (iterator.hasNext()) {
				di = (DirectorInfo) iterator.next();
				di.setParent(udmi);
				iDirector.addnew(di);
			}
		}
		//独立董事
		SelfDirectorCollection sdc = udmi.getSelfDirector();
		if(sdc!=null&&sdc.size()!=0){
			ISelfDirector iSelfDirector = SelfDirectorFactory.getLocalInstance(ctx);
			Iterator iterator = sdc.iterator();
			SelfDirectorInfo sdi;
			while (iterator.hasNext()) {
				sdi = (SelfDirectorInfo) iterator.next();
				sdi.setParent(udmi);
				iSelfDirector.addnew(sdi);
			}
		}
		//监事
		WatcherCollection wc = udmi.getWatcher();
		if(wc!=null&&wc.size()!=0){
			IWatcher iWatcher = WatcherFactory.getLocalInstance(ctx);
			Iterator iterator = wc.iterator();
			WatcherInfo wi;
			while (iterator.hasNext()) {
				wi = (WatcherInfo) iterator.next();
				wi.setParent(udmi);
				iWatcher.addnew(wi);
			}
		}		
		//权益管理信息
		if (udmi.getRightManager() != null) {
			IRightManager iRightManager = RightManagerFactory.getLocalInstance(ctx);

			RightManagerCollection rmc = udmi.getRightManager();
			Iterator iterator = rmc.iterator();
			RightManagerInfo rmi;
			while (iterator.hasNext()) {
				rmi = (RightManagerInfo) iterator.next();
				rmi.setParent(udmi);
				iRightManager.addnew(rmi);
			}
		}
        return objectPK;
    }
    protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
		UnitDataManagerInfo udmi = (UnitDataManagerInfo) model;
//		营业执照信息
		LicenceManagerInfo lmi = udmi.getLicenceManager();
		if(lmi!=null){
			ILicenceManager ilm = LicenceManagerFactory.getLocalInstance(ctx);
			if(udmi.get("lmiNumber")!=null){
				ilm.delete("where id = '" + udmi.get("lmiNumber").toString() + "'");
				lmi.setId(BOSUuid.read(udmi.get("lmiNumber").toString()));
//				ilm.update(new ObjectUuidPK(udmi.get("lmiNumber").toString()),lmi);//.addnew(lmi);
			}//else{
				IObjectPK lmiPK =ilm.addnew(lmi);
				lmi.setId(BOSUuid.read(lmiPK.toString()));
//				lmi.put("id", BOSUUlmiPK.getKeyValue("id"));
			//}			
			udmi.setLicenceManager(lmi);
		}

//		内部管理信息
		InnerManagerInfo imi = udmi.getInnerManager();
		if(imi!=null){
			IInnerManager iim = InnerManagerFactory.getLocalInstance(ctx);
			if(udmi.get("imiNumber")!=null){
				iim.delete("where id = '" + udmi.get("imiNumber").toString() + "'");
				imi.setId(BOSUuid.read(udmi.get("imiNumber").toString()));
//				iim.update(new ObjectUuidPK(udmi.get("imiNumber").toString()),imi);
//				iim.update(new ObjectUuidPK(udmi.get("imiNumber").toString()),imi);
//				iim.submit(imi);
//				IObjectPK imiPK = iim.addnew(imi);
			}//else{
				IObjectPK imiPK = iim.addnew(imi);
				imi.setId(BOSUuid.read(imiPK.toString()));
//				imi.put("id", imiPK.getKeyValue("id"));				
			//}
			udmi.setInnerManager(imi);
		}
		//
		//税务信息
		TaxManagerCollection tmc = udmi.getTaxManager();
		ITaxManager iTaxManager = TaxManagerFactory.getLocalInstance(ctx);		
		if(tmc!=null&&tmc.size()!=0){
			iTaxManager.delete("where parent.id = '" + udmi.getId().toString() + "'");
			Iterator iterator = tmc.iterator();
			TaxManagerInfo ti;
			while (iterator.hasNext()) {
				ti = (TaxManagerInfo) iterator.next();
				ti.setParent(udmi);
				iTaxManager.addnew(ti);
			}
		}	
		
		//董事
		DirectorCollection dc = udmi.getDirector();
		IDirector iDirector = DirectorFactory.getLocalInstance(ctx);
		if(dc!=null&&dc.size()!=0){
			iDirector.delete("where parent.id = '" + udmi.getId().toString() + "'");
			Iterator iterator = dc.iterator();
			DirectorInfo di;
			while (iterator.hasNext()) {
				di = (DirectorInfo) iterator.next();
				di.setParent(udmi);
				iDirector.addnew(di);
			}
		}
		//独立董事
		SelfDirectorCollection sdc = udmi.getSelfDirector();
		if(sdc!=null&&sdc.size()!=0){
			ISelfDirector iSelfDirector = SelfDirectorFactory.getLocalInstance(ctx);
			iSelfDirector.delete("where parent.id = '" + udmi.getId().toString() + "'");
			Iterator iterator = sdc.iterator();
			SelfDirectorInfo sdi;
			while (iterator.hasNext()) {
				sdi = (SelfDirectorInfo) iterator.next();
				sdi.setParent(udmi);
				iSelfDirector.addnew(sdi);
			}
		}
		//监事
		WatcherCollection wc = udmi.getWatcher();
		if(wc!=null&&wc.size()!=0){
			IWatcher iWatcher = WatcherFactory.getLocalInstance(ctx);
			iWatcher.delete("where parent.id = '" + udmi.getId().toString() + "'");
			Iterator iterator = wc.iterator();
			WatcherInfo wi;
			while (iterator.hasNext()) {
				wi = (WatcherInfo) iterator.next();
				wi.setParent(udmi);
				iWatcher.addnew(wi);
			}
		}		
		//权益管理信息
		if (udmi.getRightManager() != null) {
			IRightManager iRightManager = RightManagerFactory.getLocalInstance(ctx);
			iRightManager.delete("where parent.id = '" + udmi.getId().toString() + "'");
			RightManagerCollection rmc = udmi.getRightManager();
			Iterator iterator = rmc.iterator();
			RightManagerInfo rmi;
			while (iterator.hasNext()) {
				rmi = (RightManagerInfo) iterator.next();
				rmi.setParent(udmi);
				iRightManager.addnew(rmi);
			}
		}
      
    
        super._update(ctx, pk, model);
    }
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
    	UnitDataManagerInfo udmi = this.getUnitDataManagerInfo(ctx,pk);
    	String udmiID = pk.toString();
    	String imID=null;
    	String lmID=null;
    	if( udmi.getInnerManager()!=null){
	    	imID = udmi.getInnerManager().getId().toString();	    	
    	}
    	if(udmi.getLicenceManager()!=null){
    		lmID = udmi.getLicenceManager().getId().toString();
    	}
		//税务信息
		ITaxManager iTaxManager = TaxManagerFactory.getLocalInstance(ctx);
		iTaxManager.delete("where parent.id = '" + udmiID + "'");		
		//董事
		IDirector iDirector = DirectorFactory.getLocalInstance(ctx);
		iDirector.delete("where parent.id = '" + udmiID + "'");
		//独立董事
		ISelfDirector iSelfDirector = SelfDirectorFactory.getLocalInstance(ctx);
		iSelfDirector.delete("where parent.id = '" + udmiID + "'");
		//监事
		IWatcher iWatcher = WatcherFactory.getLocalInstance(ctx);
		iWatcher.delete("where parent.id = '" + udmiID + "'");	
		//权益管理信息
		IRightManager iRightManager = RightManagerFactory.getLocalInstance(ctx);
		iRightManager.delete("where parent.id = '" + udmiID + "'");
    	super._delete(ctx, pk);
//		营业执照信息
    	if(lmID!=null){
			ILicenceManager ilm = LicenceManagerFactory.getLocalInstance(ctx);
			ilm.delete("where id = '" + lmID + "'");
    	}
//		内部管理信息
    	if(imID!=null){
			IInnerManager iim = InnerManagerFactory.getLocalInstance(ctx);
			iim.delete("where id = '" + imID + "'");
    	}
	}
}