package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.util.app.DbUtil;

public class ProjectStatusControllerBean extends AbstractProjectStatusControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectStatusControllerBean");
	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ProjectStatus set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(1), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ProjectStatus set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(0), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	  protected void _isReferenced(Context ctx,IObjectPK pk )throws BOSException ,EASBizException
	    {
	    	if(CurProjectFactory.getLocalInstance(ctx).exists("where projectStatus.id = '"+pk.toString()+"'")){
	    		throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.PROJECTTYPE_REFERENCE);
	    	}
//	        BizReference ref = null;
//	        try
//	        {
//	            ref = ReferenceDAO.getReference(ctx,BOSUuid.read(pk.toString()));
//	        }
//	        catch (Exception e)
//	        {
//	            throw new ObjectReferedException(ref , e);
//	        }
	//
//	        if (ref != null)
//	        {
//	            throw new ObjectReferedException( (Object) ref);
//	        }
	    }
}