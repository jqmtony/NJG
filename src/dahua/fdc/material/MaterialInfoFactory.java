package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class MaterialInfoFactory
{
    private MaterialInfoFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IMaterialInfo getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialInfo)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9D390CBB") ,com.kingdee.eas.fdc.material.IMaterialInfo.class);
    }
    
    public static com.kingdee.eas.fdc.material.IMaterialInfo getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialInfo)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9D390CBB") ,com.kingdee.eas.fdc.material.IMaterialInfo.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IMaterialInfo getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialInfo)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9D390CBB"));
    }
    public static com.kingdee.eas.fdc.material.IMaterialInfo getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IMaterialInfo)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9D390CBB"));
    }
}