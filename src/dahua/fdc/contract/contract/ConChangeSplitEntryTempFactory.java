package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ConChangeSplitEntryTempFactory
{
    private ConChangeSplitEntryTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("F5F9D143") ,com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("F5F9D143") ,com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("F5F9D143"));
    }
    public static com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IConChangeSplitEntryTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("F5F9D143"));
    }
}