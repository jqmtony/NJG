package com.kingdee.eas.fdc.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTempFactory
{
    private ProgrammingTempFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("EE2D5BDB") ,com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp.class);
    }
    
    public static com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("EE2D5BDB") ,com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("EE2D5BDB"));
    }
    public static com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.basedata.IProgrammingTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("EE2D5BDB"));
    }
}