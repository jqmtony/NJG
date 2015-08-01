package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.app.ObjectBaseControllerBean;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.framework.ObjectBaseCollection;
import java.util.List;
import com.kingdee.eas.fdc.basedata.ProjectIndexDataEntryCollection;

public class ProjectIndexDataEntryControllerBean extends AbstractProjectIndexDataEntryControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectIndexDataEntryControllerBean");
    protected void _batchSave(Context ctx, IObjectCollection coll, FilterInfo filter)throws BOSException, EASBizException
    {
    }
    protected IRowSet _sum(Context ctx, List projIdList, String productTypeId)throws BOSException, EASBizException
    {
        return null;
    }
    protected void _updateVerStatus(Context ctx, String proId, String ptId, String verNo)throws BOSException, EASBizException
    {
    }
    protected void _audit(Context ctx, String projOrOrgId, String productTypeId)throws BOSException
    {
    }
    protected void _unAudit(Context ctx, String projOrOrgId, String productTypeId)throws BOSException
    {
    }
    protected void _setAudittingStatus(Context ctx, String projOrOrgId, String productTypeId)throws BOSException
    {
    }
    
    //指标保存：可售面积等没有TargetTyp
    protected IObjectPK _addnew(Context ctx , IObjectValue model) throws   BOSException , EASBizException
	{
    	ProjectIndexDataEntryInfo info = (ProjectIndexDataEntryInfo) 	model;
    	if(info.getApportionType()!=null && info.getTargetType()==null){
    		ApportionTypeInfo appinfo = ApportionTypeFactory.getLocalInstance(ctx).getApportionTypeInfo(new ObjectUuidPK(info.getApportionType().getId().toString()));
    		info.setTargetType(appinfo.getTargetType());
    	}
    	
    	return super._addnew(ctx,model);
	}
}