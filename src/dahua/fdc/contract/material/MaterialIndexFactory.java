package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialIndexFactory
{
    private MaterialIndexFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialIndex getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndex)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09E88265") ,com.kingdee.eas.fdc.material.IMaterialIndex.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialIndex getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndex)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09E88265") ,com.kingdee.eas.fdc.material.IMaterialIndex.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialIndex getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndex)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09E88265"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialIndex getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialIndex)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09E88265"));
    }
}