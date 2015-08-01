package com.kingdee.eas.fdc.material;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PartAMaterialFactory
{
    private PartAMaterialFactory()
    {
    }
    public static com.kingdee.eas.fdc.material.IPartAMaterial getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterial)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4D6EE46F") ,com.kingdee.eas.fdc.material.IPartAMaterial.class);
    }
    
    public static com.kingdee.eas.fdc.material.IPartAMaterial getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterial)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4D6EE46F") ,com.kingdee.eas.fdc.material.IPartAMaterial.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.material.IPartAMaterial getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterial)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4D6EE46F"));
    }
    public static com.kingdee.eas.fdc.material.IPartAMaterial getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.material.IPartAMaterial)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4D6EE46F"));
    }
}