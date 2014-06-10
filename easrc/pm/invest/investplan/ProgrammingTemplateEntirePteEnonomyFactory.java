package com.kingdee.eas.port.pm.invest.investplan;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ProgrammingTemplateEntirePteEnonomyFactory
{
    private ProgrammingTemplateEntirePteEnonomyFactory()
    {
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("ABFF92C9") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy.class);
    }
    
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("ABFF92C9") ,com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy.class, objectCtx);
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("ABFF92C9"));
    }
    public static com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.port.pm.invest.investplan.IProgrammingTemplateEntirePteEnonomy)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("ABFF92C9"));
    }
}