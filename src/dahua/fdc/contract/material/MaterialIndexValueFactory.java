package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialIndexValueFactory
{
    private MaterialIndexValueFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialIndexValue getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndexValue)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6E5BD60C") ,com.kingdee.eas.fdc.material.IMaterialIndexValue.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialIndexValue getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndexValue)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6E5BD60C") ,com.kingdee.eas.fdc.material.IMaterialIndexValue.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialIndexValue getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndexValue)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6E5BD60C"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialIndexValue getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndexValue)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6E5BD60C"));
    }
}