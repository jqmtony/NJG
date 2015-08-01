package com.kingdee.eas.fdc.basedata;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import java.util.Map;
import com.kingdee.bos.framework.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ICostAccountFacade extends IBizCtrl
{
    public void assignOrgsCostAccount(IObjectPK orgPK) throws BOSException, EASBizException;
    public void disAssignOrgsCostAccount(IObjectPK orgPK) throws BOSException, EASBizException;
    public void disAssignSelectOrgsCostAccount(CostAccountCollection costAccounts) throws BOSException, EASBizException;
    public List assignProjsCostAccount(IObjectPK projectPK) throws BOSException, EASBizException;
    public void disAssignProjsCostAccount(IObjectPK projectPK) throws BOSException, EASBizException;
    public void disAssignSelectProjsCostAccount(CostAccountCollection costAccounts) throws BOSException, EASBizException;
    public List assignOrgProject(IObjectPK orgPK) throws BOSException, EASBizException;
    public void disAssignOrgProject(IObjectPK orgPK) throws BOSException, EASBizException;
    public ArrayList disAssignSelectOrgProject(CostAccountCollection costAccounts) throws BOSException, EASBizException;
    public void handleEnterDB(CostAccountInfo model, boolean isEnterDB) throws BOSException, EASBizException;
    public List assignToNextLevel(IObjectPK orgPK, Set nodeIds, boolean isProjectSet) throws BOSException, EASBizException;
    public boolean updateStageData(Set idSet, boolean isChecked) throws BOSException;
    public boolean checkCurrentCostAccountIsDetailsNode(String id, String orgId) throws BOSException;
    public void updateToLeafNode(Set idSet) throws BOSException;
    public List assignCostAccountBatch(Set orgSet, Set projectSet, Set costAccountList, Map isAddMap) throws BOSException;
    public List disAssignAccountBatch(Set orgSet, Set projectSet, Map costAccountList) throws BOSException;
    public RetValue updateAccountStage(ParamValue paramValue) throws BOSException, EASBizException;
}