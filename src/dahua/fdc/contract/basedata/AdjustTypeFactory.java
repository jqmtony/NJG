package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class AdjustTypeFactory
{
    private AdjustTypeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IAdjustType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A0F66BDB") ,com.kingdee.eas.fdc.basedata.IAdjustType.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IAdjustType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A0F66BDB") ,com.kingdee.eas.fdc.basedata.IAdjustType.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IAdjustType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A0F66BDB"));
    }
    public static com.kingdee.eas.fdc.basedata.IAdjustType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IAdjustType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A0F66BDB"));
    }
}