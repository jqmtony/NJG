package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichExamedEntryDjrentryFactory
{
    private RichExamedEntryDjrentryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F959E230") ,com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F959E230") ,com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F959E230"));
    }
    public static com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichExamedEntryDjrentry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F959E230"));
    }
}