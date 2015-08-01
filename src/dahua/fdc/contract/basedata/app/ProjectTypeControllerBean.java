package com.kingdee.eas.fdc.basedata.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.util.app.DbUtil;

public class ProjectTypeControllerBean extends AbstractProjectTypeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.basedata.app.ProjectTypeControllerBean");

	protected boolean _enabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ProjectType set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(1), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
	protected boolean _disEnabled(Context ctx, IObjectPK ctPK) throws BOSException, EASBizException {
	    String sql = "update T_FDC_ProjectType set  FIsEnabled = ?  where FID = ? ";
	      Object[] params = new Object[]{new Integer(0), ctPK.toString()};
	    DbUtil.execute(ctx,sql,params);
		return true;
	}
    
    protected void _isReferenced(Context ctx,IObjectPK pk )throws BOSException ,EASBizException
    {
    	if(CurProjectFactory.getLocalInstance(ctx).exists("where projectType.id = '"+pk.toString()+"'")){
    		throw new com.kingdee.eas.fdc.basedata.FDCBasedataException(FDCBasedataException.PROJECTTYPE_REFERENCE);
    	}
//        BizReference ref = null;
//        try
//        {
//            ref = ReferenceDAO.getReference(ctx,BOSUuid.read(pk.toString()));
//        }
//        catch (Exception e)
//        {
//            throw new ObjectReferedException(ref , e);
//        }
//
//        if (ref != null)
//        {
//            throw new ObjectReferedException( (Object) ref);
//        }
    }
 
//    protected void _checkNumberDup(Context ctx , IObjectValue model) throws EASBizException, BOSException{
//
//    	DataBaseInfo info = (DataBaseInfo) model;
//    	EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
//		SelectorItemCollection sic = view.getSelector();
//		sic.add("id");
//		ProjectTypeCollection c = ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(view);
//		if(c.size()!=0){
//		    throw new EASBizException(TreeBaseException.CHECKNUMBERDUPLICATED ,
//                    new Object[]
//                    {info.getNumber()});
//		}
//    
//    }
//    
//    protected void _checkNameDup(Context ctx , IObjectValue model) throws EASBizException, BOSException{
//
//    	DataBaseInfo info = (DataBaseInfo) model;
//    	EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		filter.getFilterItems().add(new FilterItemInfo("name", info.getName()));
//		SelectorItemCollection sic = view.getSelector();
//		sic.add("id");
//		ProjectTypeCollection c = ProjectTypeFactory.getLocalInstance(ctx).getProjectTypeCollection(view);
//		if(c.size()!=0){
//		    throw new EASBizException(TreeBaseException.CHECKDUPLICATED ,
//                    new Object[]
//                    {info.getName()});
//		}
//    
//    }
    
}