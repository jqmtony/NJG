package com.kingdee.eas.custom.richinf;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class RichCompayWriteOffDjEntryFactory
{
    private RichCompayWriteOffDjEntryFactory()
    {
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("77C11D0B") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry.class);
    }
    
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("77C11D0B") ,com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("77C11D0B"));
    }
    public static com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richinf.IRichCompayWriteOffDjEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("77C11D0B"));
    }
}