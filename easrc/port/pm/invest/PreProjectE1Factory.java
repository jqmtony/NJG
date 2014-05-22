package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreProjectE1Factory
{
    private PreProjectE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("26822E5F") ,com.kingdee.eas.port.pm.invest.IPreProjectE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreProjectE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("26822E5F") ,com.kingdee.eas.port.pm.invest.IPreProjectE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("26822E5F"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("26822E5F"));
    }
}