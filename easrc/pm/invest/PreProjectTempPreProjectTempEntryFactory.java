package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreProjectTempPreProjectTempEntryFactory
{
    private PreProjectTempPreProjectTempEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C0167A21") ,com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C0167A21") ,com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C0167A21"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempPreProjectTempEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C0167A21"));
    }
}