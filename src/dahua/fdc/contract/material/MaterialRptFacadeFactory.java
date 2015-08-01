package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialRptFacadeFactory
{
    private MaterialRptFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialRptFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialRptFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("21F8DE43") ,com.kingdee.eas.fdc.material.IMaterialRptFacade.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialRptFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialRptFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("21F8DE43") ,com.kingdee.eas.fdc.material.IMaterialRptFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialRptFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialRptFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("21F8DE43"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialRptFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialRptFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("21F8DE43"));
    }
}