package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichInvoiceRequestFactory
{
    private RichInvoiceRequestFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequest getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequest)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("4140BC2C") ,com.kingdee.eas.custom.richinf.IRichInvoiceRequest.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequest getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequest)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("4140BC2C") ,com.kingdee.eas.custom.richinf.IRichInvoiceRequest.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequest getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequest)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("4140BC2C"));
    }
    public static com.kingdee.eas.custom.richinf.IRichInvoiceRequest getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichInvoiceRequest)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("4140BC2C"));
    }
}