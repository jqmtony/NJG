package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RealDateRelEntryFactory
{
    private RealDateRelEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("D9446817") ,com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("D9446817") ,com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("D9446817"));
    }
    public static com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IRealDateRelEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("D9446817"));
    }
}