package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeSplitTempFactory
{
    private ConChangeSplitTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AA3E33D7") ,com.kingdee.eas.fdc.contract.IConChangeSplitTemp.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeSplitTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AA3E33D7") ,com.kingdee.eas.fdc.contract.IConChangeSplitTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AA3E33D7"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AA3E33D7"));
    }
}