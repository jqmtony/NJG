package com.kingdee.eas.fdc.earlywarn;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class DHWarnMsgFacadeFactory
{
    private DHWarnMsgFacadeFactory()
    {
    }
    public static com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("90D6137E") ,com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade.class);
    }
    
    public static com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("90D6137E") ,com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("90D6137E"));
    }
    public static com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.earlywarn.IDHWarnMsgFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("90D6137E"));
    }
}