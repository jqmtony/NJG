package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ValuationModeFactory
{
    private ValuationModeFactory()
    {
    }
    public static com.kingdee.eas.fdc.basedata.IValuationMode getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IValuationMode)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E0A26D92") ,com.kingdee.eas.fdc.basedata.IValuationMode.class);
    }
    
    public static com.kingdee.eas.fdc.basedata.IValuationMode getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IValuationMode)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E0A26D92") ,com.kingdee.eas.fdc.basedata.IValuationMode.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.basedata.IValuationMode getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IValuationMode)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E0A26D92"));
    }
    public static com.kingdee.eas.fdc.basedata.IValuationMode getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.basedata.IValuationMode)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E0A26D92"));
    }
}