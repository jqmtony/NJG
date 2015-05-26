package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichExamedFactory
{
    private RichExamedFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichExamed getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamed)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("7E855488") ,com.kingdee.eas.custom.richinf.IRichExamed.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichExamed getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamed)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("7E855488") ,com.kingdee.eas.custom.richinf.IRichExamed.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichExamed getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamed)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("7E855488"));
    }
    public static com.kingdee.eas.custom.richinf.IRichExamed getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamed)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("7E855488"));
    }
}