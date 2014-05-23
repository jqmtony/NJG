package com.kingdee.eas.port.pm.invest;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PreProjectTempE1Factory
{
    private PreProjectTempE1Factory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempE1 getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempE1)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BF9E97D3") ,com.kingdee.eas.port.pm.invest.IPreProjectTempE1.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempE1 getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempE1)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BF9E97D3") ,com.kingdee.eas.port.pm.invest.IPreProjectTempE1.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempE1 getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempE1)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BF9E97D3"));
    }
    public static com.kingdee.eas.port.pm.invest.IPreProjectTempE1 getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.IPreProjectTempE1)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BF9E97D3"));
    }
}