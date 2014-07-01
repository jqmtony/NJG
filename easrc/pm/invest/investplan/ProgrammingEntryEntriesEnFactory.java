package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingEntryEntriesEnFactory
{
    private ProgrammingEntryEntriesEnFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("43357CD3") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("43357CD3") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("43357CD3"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingEntryEntriesEn)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("43357CD3"));
    }
}