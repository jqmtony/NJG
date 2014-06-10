package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingCompareEntryFactory
{
    private ProgrammingCompareEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E1960159") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E1960159") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E1960159"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingCompareEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E1960159"));
    }
}