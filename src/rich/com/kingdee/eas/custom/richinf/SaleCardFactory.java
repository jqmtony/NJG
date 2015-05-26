package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class SaleCardFactory
{
    private SaleCardFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.ISaleCard getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCard)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E3B79B05") ,com.kingdee.eas.custom.richinf.ISaleCard.class);
    }
    
    public static com.kingdee.eas.custom.richinf.ISaleCard getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCard)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E3B79B05") ,com.kingdee.eas.custom.richinf.ISaleCard.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.ISaleCard getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCard)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E3B79B05"));
    }
    public static com.kingdee.eas.custom.richinf.ISaleCard getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.ISaleCard)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E3B79B05"));
    }
}