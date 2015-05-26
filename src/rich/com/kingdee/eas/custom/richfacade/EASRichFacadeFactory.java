package com.kingdee.eas.custom.richfacade;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class EASRichFacadeFactory
{
    private EASRichFacadeFactory()
    {
    }
    public static com.kingdee.eas.custom.richfacade.IEASRichFacade getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.custom.richfacade.IEASRichFacade)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("452A57AD") ,com.kingdee.eas.custom.richfacade.IEASRichFacade.class);
    }
    
    public static com.kingdee.eas.custom.richfacade.IEASRichFacade getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.custom.richfacade.IEASRichFacade)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("452A57AD") ,com.kingdee.eas.custom.richfacade.IEASRichFacade.class, objectCtx);
    }
    public static com.kingdee.eas.custom.richfacade.IEASRichFacade getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.custom.richfacade.IEASRichFacade)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("452A57AD"));
    }
    public static com.kingdee.eas.custom.richfacade.IEASRichFacade getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.custom.richfacade.IEASRichFacade)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("452A57AD"));
    }
}