package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreProjectTempFactory
{
    private PreProjectTempFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTemp getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTemp)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("8EB7B507") ,com.kingdee.eas.port.pm.invest.IPreProjectTemp.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreProjectTemp getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTemp)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("8EB7B507") ,com.kingdee.eas.port.pm.invest.IPreProjectTemp.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTemp getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTemp)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("8EB7B507"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTemp getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTemp)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("8EB7B507"));
    }
}