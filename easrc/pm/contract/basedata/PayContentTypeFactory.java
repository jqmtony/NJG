package com.kingdee.eas.port.pm.contract.basedata;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PayContentTypeFactory
{
    private PayContentTypeFactory()
    {
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IPayContentType getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IPayContentType)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4D22EDB8") ,com.kingdee.eas.port.pm.contract.basedata.IPayContentType.class);
    }
    
    public static com.kingdee.eas.port.pm.contract.basedata.IPayContentType getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IPayContentType)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4D22EDB8") ,com.kingdee.eas.port.pm.contract.basedata.IPayContentType.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IPayContentType getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IPayContentType)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4D22EDB8"));
    }
    public static com.kingdee.eas.port.pm.contract.basedata.IPayContentType getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.contract.basedata.IPayContentType)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4D22EDB8"));
    }
}