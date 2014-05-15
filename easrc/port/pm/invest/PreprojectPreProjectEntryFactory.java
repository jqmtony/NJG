package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreprojectPreProjectEntryFactory
{
    private PreprojectPreProjectEntryFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("08C833E9") ,com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("08C833E9") ,com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("08C833E9"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreprojectPreProjectEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("08C833E9"));
    }
}