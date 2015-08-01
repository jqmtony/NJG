package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartAMaterialEntryFactory
{
    private PartAMaterialEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IPartAMaterialEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterialEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B9A9A143") ,com.kingdee.eas.fdc.material.IPartAMaterialEntry.class);
    }
    
    public static com.kingdee.eas.fdc.material.IPartAMaterialEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterialEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B9A9A143") ,com.kingdee.eas.fdc.material.IPartAMaterialEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IPartAMaterialEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterialEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B9A9A143"));
    }
    public static com.kingdee.eas.fdc.material.IPartAMaterialEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterialEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B9A9A143"));
    }
}