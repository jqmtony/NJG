/**
 * output package name
 */
package com.kingdee.eas.port.equipment.maintenance.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractEquMaintBookEditUI extends com.kingdee.eas.xr.client.XRBillBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractEquMaintBookEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contidone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contequNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contuseDepart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxiadaPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contequName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contweixiuType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxiadaDepart;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCU;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmodel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStatus;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contxiadaTiem;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbaoqianxiangmu;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmainNeirong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contgenghuanling;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanStartTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contplanEndTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contmainone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtequNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtuseDepart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtxiadaPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtequName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtweixiuType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtxiadaDepart;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCU;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtmodel;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboStatus;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkxiadaTiem;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanebaoqianxiangmu;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtbaoqianxiangmu;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanemainNeirong;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtmainNeirong;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanegenghuanling;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtgenghuanling;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkplanStartTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkplanEndTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanemainone;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtmainone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contdianqishebei;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchuangdong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contrunhua;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contqiexiao;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contbanzhang;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhuxiuone;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjianyanyuanone;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleixing;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contleixingone;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchejianyijian;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contchejianlingdao;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjieshouren;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyijiaoren;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyijiaodanwei;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjiaojieTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contacStartTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contacEndTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtdianqishebei;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtchuangdong;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtrunhua;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtqiexiao;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtbanzhang;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtzhuxiuone;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjianyanyuanone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtleixing;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtleixingone;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPanechejianyijian;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtchejianyijian;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtchejianlingdao;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjieshouren;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtyijiaoren;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtyijiaodanwei;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkjiaojieTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkacStartTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkacEndTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBizStatus;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkLastUpdateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtLastUpdateUser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE3;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE3_detailPanel = null;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE2;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE2_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contshijian;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contyingdu;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneshijian;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtshijian;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane scrollPaneyingdu;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtyingdu;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtE1;
	protected com.kingdee.eas.framework.client.multiDetail.DetailPanel kdtE1_detailPanel = null;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhuxiuqiangong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contzhuxiudiangong;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contjianyanyuan;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtzhuxiuqiangong;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtzhuxiudiangong;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtjianyanyuan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtidone;
    protected com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractEquMaintBookEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractEquMaintBookEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionUnAudit
        actionUnAudit.setEnabled(true);
        actionUnAudit.setDaemonRun(false);

        _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
        actionUnAudit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionUnAudit.NAME");
        actionUnAudit.putValue(ItemAction.NAME, _tempStr);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLastUpdateUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contidone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contequNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contuseDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxiadaPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contequName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contweixiuType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxiadaDepart = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCU = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmodel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStatus = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contxiadaTiem = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contbaoqianxiangmu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmainNeirong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contgenghuanling = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanStartTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contplanEndTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contmainone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtequNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtuseDepart = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtxiadaPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtequName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtweixiuType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtxiadaDepart = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCU = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtmodel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkxiadaTiem = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.scrollPanebaoqianxiangmu = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtbaoqianxiangmu = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanemainNeirong = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtmainNeirong = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPanegenghuanling = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtgenghuanling = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkplanStartTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkplanEndTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.scrollPanemainone = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtmainone = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contdianqishebei = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchuangdong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contrunhua = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contqiexiao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contbanzhang = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contzhuxiuone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contjianyanyuanone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contleixing = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contleixingone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.contchejianyijian = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contchejianlingdao = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjieshouren = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyijiaoren = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyijiaodanwei = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjiaojieTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contacStartTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contacEndTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtdianqishebei = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtchuangdong = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtrunhua = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtqiexiao = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtbanzhang = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtzhuxiuone = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtjianyanyuanone = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtleixing = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtleixingone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.scrollPanechejianyijian = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtchejianyijian = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtchejianlingdao = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtjieshouren = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtyijiaoren = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtyijiaodanwei = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkjiaojieTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkacStartTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkacEndTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.comboBizStatus = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkLastUpdateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtLastUpdateUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtE3 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtE2 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contshijian = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contyingdu = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.scrollPaneshijian = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtshijian = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.scrollPaneyingdu = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtyingdu = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdtE1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contzhuxiuqiangong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contzhuxiudiangong = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contjianyanyuan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtzhuxiuqiangong = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtzhuxiudiangong = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtjianyanyuan = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtidone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel3.setName("kDPanel3");
        this.contAuditTime.setName("contAuditTime");
        this.contBizStatus.setName("contBizStatus");
        this.contAuditor.setName("contAuditor");
        this.contLastUpdateTime.setName("contLastUpdateTime");
        this.contLastUpdateUser.setName("contLastUpdateUser");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.contidone.setName("contidone");
        this.contNumber.setName("contNumber");
        this.contequNumber.setName("contequNumber");
        this.contuseDepart.setName("contuseDepart");
        this.contxiadaPerson.setName("contxiadaPerson");
        this.contBizDate.setName("contBizDate");
        this.contequName.setName("contequName");
        this.contweixiuType.setName("contweixiuType");
        this.contxiadaDepart.setName("contxiadaDepart");
        this.contCU.setName("contCU");
        this.contmodel.setName("contmodel");
        this.contStatus.setName("contStatus");
        this.contxiadaTiem.setName("contxiadaTiem");
        this.contbaoqianxiangmu.setName("contbaoqianxiangmu");
        this.contmainNeirong.setName("contmainNeirong");
        this.contgenghuanling.setName("contgenghuanling");
        this.contDescription.setName("contDescription");
        this.contplanStartTime.setName("contplanStartTime");
        this.contplanEndTime.setName("contplanEndTime");
        this.contmainone.setName("contmainone");
        this.txtNumber.setName("txtNumber");
        this.prmtequNumber.setName("prmtequNumber");
        this.txtuseDepart.setName("txtuseDepart");
        this.prmtxiadaPerson.setName("prmtxiadaPerson");
        this.pkBizDate.setName("pkBizDate");
        this.txtequName.setName("txtequName");
        this.prmtweixiuType.setName("prmtweixiuType");
        this.prmtxiadaDepart.setName("prmtxiadaDepart");
        this.prmtCU.setName("prmtCU");
        this.txtmodel.setName("txtmodel");
        this.comboStatus.setName("comboStatus");
        this.pkxiadaTiem.setName("pkxiadaTiem");
        this.scrollPanebaoqianxiangmu.setName("scrollPanebaoqianxiangmu");
        this.txtbaoqianxiangmu.setName("txtbaoqianxiangmu");
        this.scrollPanemainNeirong.setName("scrollPanemainNeirong");
        this.txtmainNeirong.setName("txtmainNeirong");
        this.scrollPanegenghuanling.setName("scrollPanegenghuanling");
        this.txtgenghuanling.setName("txtgenghuanling");
        this.txtDescription.setName("txtDescription");
        this.pkplanStartTime.setName("pkplanStartTime");
        this.pkplanEndTime.setName("pkplanEndTime");
        this.scrollPanemainone.setName("scrollPanemainone");
        this.txtmainone.setName("txtmainone");
        this.contdianqishebei.setName("contdianqishebei");
        this.contchuangdong.setName("contchuangdong");
        this.contrunhua.setName("contrunhua");
        this.contqiexiao.setName("contqiexiao");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.contbanzhang.setName("contbanzhang");
        this.kDLabel3.setName("kDLabel3");
        this.contzhuxiuone.setName("contzhuxiuone");
        this.kDLabel4.setName("kDLabel4");
        this.contjianyanyuanone.setName("contjianyanyuanone");
        this.kDLabel5.setName("kDLabel5");
        this.contleixing.setName("contleixing");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel7.setName("kDLabel7");
        this.contleixingone.setName("contleixingone");
        this.kDLabel8.setName("kDLabel8");
        this.contchejianyijian.setName("contchejianyijian");
        this.contchejianlingdao.setName("contchejianlingdao");
        this.contjieshouren.setName("contjieshouren");
        this.contyijiaoren.setName("contyijiaoren");
        this.contyijiaodanwei.setName("contyijiaodanwei");
        this.contjiaojieTime.setName("contjiaojieTime");
        this.contacStartTime.setName("contacStartTime");
        this.contacEndTime.setName("contacEndTime");
        this.txtdianqishebei.setName("txtdianqishebei");
        this.txtchuangdong.setName("txtchuangdong");
        this.txtrunhua.setName("txtrunhua");
        this.txtqiexiao.setName("txtqiexiao");
        this.prmtbanzhang.setName("prmtbanzhang");
        this.prmtzhuxiuone.setName("prmtzhuxiuone");
        this.prmtjianyanyuanone.setName("prmtjianyanyuanone");
        this.txtleixing.setName("txtleixing");
        this.txtleixingone.setName("txtleixingone");
        this.scrollPanechejianyijian.setName("scrollPanechejianyijian");
        this.txtchejianyijian.setName("txtchejianyijian");
        this.prmtchejianlingdao.setName("prmtchejianlingdao");
        this.prmtjieshouren.setName("prmtjieshouren");
        this.prmtyijiaoren.setName("prmtyijiaoren");
        this.prmtyijiaodanwei.setName("prmtyijiaodanwei");
        this.pkjiaojieTime.setName("pkjiaojieTime");
        this.pkacStartTime.setName("pkacStartTime");
        this.pkacEndTime.setName("pkacEndTime");
        this.pkAuditTime.setName("pkAuditTime");
        this.comboBizStatus.setName("comboBizStatus");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkLastUpdateTime.setName("pkLastUpdateTime");
        this.prmtLastUpdateUser.setName("prmtLastUpdateUser");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtCreator.setName("prmtCreator");
        this.kDPanel4.setName("kDPanel4");
        this.kDPanel5.setName("kDPanel5");
        this.kDPanel6.setName("kDPanel6");
        this.kDPanel7.setName("kDPanel7");
        this.kDPanel2.setName("kDPanel2");
        this.kdtE3.setName("kdtE3");
        this.kdtE2.setName("kdtE2");
        this.contshijian.setName("contshijian");
        this.contyingdu.setName("contyingdu");
        this.scrollPaneshijian.setName("scrollPaneshijian");
        this.txtshijian.setName("txtshijian");
        this.scrollPaneyingdu.setName("scrollPaneyingdu");
        this.txtyingdu.setName("txtyingdu");
        this.kdtE1.setName("kdtE1");
        this.contzhuxiuqiangong.setName("contzhuxiuqiangong");
        this.contzhuxiudiangong.setName("contzhuxiudiangong");
        this.contjianyanyuan.setName("contjianyanyuan");
        this.prmtzhuxiuqiangong.setName("prmtzhuxiuqiangong");
        this.prmtzhuxiudiangong.setName("prmtzhuxiudiangong");
        this.prmtjianyanyuan.setName("prmtjianyanyuan");
        this.txtidone.setName("txtidone");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // kDPanel1		
        this.kDPanel1.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel1.border.title")));		
        this.kDPanel1.setPreferredSize(new Dimension(1003,211));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contBizStatus		
        this.contBizStatus.setBoundLabelText(resHelper.getString("contBizStatus.boundLabelText"));		
        this.contBizStatus.setBoundLabelLength(100);		
        this.contBizStatus.setBoundLabelUnderline(true);		
        this.contBizStatus.setEnabled(false);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contLastUpdateTime		
        this.contLastUpdateTime.setBoundLabelText(resHelper.getString("contLastUpdateTime.boundLabelText"));		
        this.contLastUpdateTime.setBoundLabelLength(100);		
        this.contLastUpdateTime.setBoundLabelUnderline(true);
        // contLastUpdateUser		
        this.contLastUpdateUser.setBoundLabelText(resHelper.getString("contLastUpdateUser.boundLabelText"));		
        this.contLastUpdateUser.setBoundLabelLength(100);		
        this.contLastUpdateUser.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // contidone		
        this.contidone.setBoundLabelText(resHelper.getString("contidone.boundLabelText"));		
        this.contidone.setBoundLabelLength(100);		
        this.contidone.setBoundLabelUnderline(true);		
        this.contidone.setVisible(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contequNumber		
        this.contequNumber.setBoundLabelText(resHelper.getString("contequNumber.boundLabelText"));		
        this.contequNumber.setBoundLabelLength(100);		
        this.contequNumber.setBoundLabelUnderline(true);		
        this.contequNumber.setVisible(true);
        // contuseDepart		
        this.contuseDepart.setBoundLabelText(resHelper.getString("contuseDepart.boundLabelText"));		
        this.contuseDepart.setBoundLabelLength(100);		
        this.contuseDepart.setBoundLabelUnderline(true);		
        this.contuseDepart.setVisible(true);
        // contxiadaPerson		
        this.contxiadaPerson.setBoundLabelText(resHelper.getString("contxiadaPerson.boundLabelText"));		
        this.contxiadaPerson.setBoundLabelLength(100);		
        this.contxiadaPerson.setBoundLabelUnderline(true);		
        this.contxiadaPerson.setVisible(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contequName		
        this.contequName.setBoundLabelText(resHelper.getString("contequName.boundLabelText"));		
        this.contequName.setBoundLabelLength(100);		
        this.contequName.setBoundLabelUnderline(true);		
        this.contequName.setVisible(true);
        // contweixiuType		
        this.contweixiuType.setBoundLabelText(resHelper.getString("contweixiuType.boundLabelText"));		
        this.contweixiuType.setBoundLabelLength(100);		
        this.contweixiuType.setBoundLabelUnderline(true);		
        this.contweixiuType.setVisible(true);
        // contxiadaDepart		
        this.contxiadaDepart.setBoundLabelText(resHelper.getString("contxiadaDepart.boundLabelText"));		
        this.contxiadaDepart.setBoundLabelLength(100);		
        this.contxiadaDepart.setBoundLabelUnderline(true);		
        this.contxiadaDepart.setVisible(true);
        // contCU		
        this.contCU.setBoundLabelText(resHelper.getString("contCU.boundLabelText"));		
        this.contCU.setBoundLabelLength(100);		
        this.contCU.setBoundLabelUnderline(true);		
        this.contCU.setEnabled(false);
        // contmodel		
        this.contmodel.setBoundLabelText(resHelper.getString("contmodel.boundLabelText"));		
        this.contmodel.setBoundLabelLength(100);		
        this.contmodel.setBoundLabelUnderline(true);		
        this.contmodel.setVisible(true);
        // contStatus		
        this.contStatus.setBoundLabelText(resHelper.getString("contStatus.boundLabelText"));		
        this.contStatus.setBoundLabelLength(100);		
        this.contStatus.setBoundLabelUnderline(true);		
        this.contStatus.setEnabled(false);
        // contxiadaTiem		
        this.contxiadaTiem.setBoundLabelText(resHelper.getString("contxiadaTiem.boundLabelText"));		
        this.contxiadaTiem.setBoundLabelLength(100);		
        this.contxiadaTiem.setBoundLabelUnderline(true);		
        this.contxiadaTiem.setVisible(true);
        // contbaoqianxiangmu		
        this.contbaoqianxiangmu.setBoundLabelText(resHelper.getString("contbaoqianxiangmu.boundLabelText"));		
        this.contbaoqianxiangmu.setBoundLabelLength(100);		
        this.contbaoqianxiangmu.setBoundLabelUnderline(true);		
        this.contbaoqianxiangmu.setVisible(true);
        // contmainNeirong		
        this.contmainNeirong.setBoundLabelText(resHelper.getString("contmainNeirong.boundLabelText"));		
        this.contmainNeirong.setBoundLabelLength(168);		
        this.contmainNeirong.setBoundLabelUnderline(true);		
        this.contmainNeirong.setVisible(true);
        // contgenghuanling		
        this.contgenghuanling.setBoundLabelText(resHelper.getString("contgenghuanling.boundLabelText"));		
        this.contgenghuanling.setBoundLabelLength(100);		
        this.contgenghuanling.setBoundLabelUnderline(true);		
        this.contgenghuanling.setVisible(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contplanStartTime		
        this.contplanStartTime.setBoundLabelText(resHelper.getString("contplanStartTime.boundLabelText"));		
        this.contplanStartTime.setBoundLabelLength(100);		
        this.contplanStartTime.setBoundLabelUnderline(true);		
        this.contplanStartTime.setVisible(true);
        // contplanEndTime		
        this.contplanEndTime.setBoundLabelText(resHelper.getString("contplanEndTime.boundLabelText"));		
        this.contplanEndTime.setBoundLabelLength(100);		
        this.contplanEndTime.setBoundLabelUnderline(true);		
        this.contplanEndTime.setVisible(true);
        // contmainone		
        this.contmainone.setBoundLabelText(resHelper.getString("contmainone.boundLabelText"));		
        this.contmainone.setBoundLabelLength(168);		
        this.contmainone.setBoundLabelUnderline(true);		
        this.contmainone.setVisible(true);
        // txtNumber
        // prmtequNumber		
        this.prmtequNumber.setQueryInfo("com.kingdee.eas.port.equipment.record.app.EquIdQuery");		
        this.prmtequNumber.setVisible(true);		
        this.prmtequNumber.setEditable(true);		
        this.prmtequNumber.setDisplayFormat("$number$");		
        this.prmtequNumber.setEditFormat("$number$");		
        this.prmtequNumber.setCommitFormat("$number$");		
        this.prmtequNumber.setRequired(false);
        prmtequNumber.addDataChangeListener(new DataChangeListener() {
		public void dataChanged(DataChangeEvent e) {
			try {
				prmtequNumber_Changed();
			}
			catch (Exception exc) {
				handUIException(exc);
			}
		}
	});

        // txtuseDepart		
        this.txtuseDepart.setVisible(true);		
        this.txtuseDepart.setHorizontalAlignment(2);		
        this.txtuseDepart.setMaxLength(80);		
        this.txtuseDepart.setRequired(false);
        // prmtxiadaPerson		
        this.prmtxiadaPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtxiadaPerson.setVisible(true);		
        this.prmtxiadaPerson.setEditable(true);		
        this.prmtxiadaPerson.setDisplayFormat("$name$");		
        this.prmtxiadaPerson.setEditFormat("$number$");		
        this.prmtxiadaPerson.setCommitFormat("$number$");		
        this.prmtxiadaPerson.setRequired(false);
        this.prmtxiadaPerson.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtxiadaPerson_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkBizDate
        // txtequName		
        this.txtequName.setVisible(true);		
        this.txtequName.setHorizontalAlignment(2);		
        this.txtequName.setMaxLength(80);		
        this.txtequName.setRequired(false);
        // prmtweixiuType		
        this.prmtweixiuType.setQueryInfo("com.kingdee.eas.port.equipment.base.app.MaintenanceTypeQuery");		
        this.prmtweixiuType.setVisible(true);		
        this.prmtweixiuType.setEditable(true);		
        this.prmtweixiuType.setDisplayFormat("$name$");		
        this.prmtweixiuType.setEditFormat("$number$");		
        this.prmtweixiuType.setCommitFormat("$number$");		
        this.prmtweixiuType.setRequired(false);
        // prmtxiadaDepart		
        this.prmtxiadaDepart.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtxiadaDepart.setVisible(true);		
        this.prmtxiadaDepart.setEditable(true);		
        this.prmtxiadaDepart.setDisplayFormat("$name$");		
        this.prmtxiadaDepart.setEditFormat("$number$");		
        this.prmtxiadaDepart.setCommitFormat("$number$");		
        this.prmtxiadaDepart.setRequired(false);
        // prmtCU		
        this.prmtCU.setEnabled(false);
        // txtmodel		
        this.txtmodel.setVisible(true);		
        this.txtmodel.setHorizontalAlignment(2);		
        this.txtmodel.setMaxLength(80);		
        this.txtmodel.setRequired(false);
        // comboStatus		
        this.comboStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBillStatusEnum").toArray());		
        this.comboStatus.setEnabled(false);
        // pkxiadaTiem		
        this.pkxiadaTiem.setVisible(true);		
        this.pkxiadaTiem.setRequired(false);
        // scrollPanebaoqianxiangmu
        // txtbaoqianxiangmu		
        this.txtbaoqianxiangmu.setVisible(true);		
        this.txtbaoqianxiangmu.setRequired(false);		
        this.txtbaoqianxiangmu.setMaxLength(500);
        // scrollPanemainNeirong
        // txtmainNeirong		
        this.txtmainNeirong.setVisible(true);		
        this.txtmainNeirong.setRequired(false);		
        this.txtmainNeirong.setMaxLength(500);
        // scrollPanegenghuanling
        // txtgenghuanling		
        this.txtgenghuanling.setVisible(true);		
        this.txtgenghuanling.setRequired(false);		
        this.txtgenghuanling.setMaxLength(300);
        // txtDescription
        // pkplanStartTime		
        this.pkplanStartTime.setVisible(true);		
        this.pkplanStartTime.setRequired(false);
        // pkplanEndTime		
        this.pkplanEndTime.setVisible(true);		
        this.pkplanEndTime.setRequired(false);
        // scrollPanemainone
        // txtmainone		
        this.txtmainone.setVisible(true);		
        this.txtmainone.setRequired(false);		
        this.txtmainone.setMaxLength(255);
        // contdianqishebei		
        this.contdianqishebei.setBoundLabelText(resHelper.getString("contdianqishebei.boundLabelText"));		
        this.contdianqishebei.setBoundLabelLength(100);		
        this.contdianqishebei.setBoundLabelUnderline(true);		
        this.contdianqishebei.setVisible(true);
        // contchuangdong		
        this.contchuangdong.setBoundLabelText(resHelper.getString("contchuangdong.boundLabelText"));		
        this.contchuangdong.setBoundLabelLength(170);		
        this.contchuangdong.setBoundLabelUnderline(true);		
        this.contchuangdong.setVisible(true);
        // contrunhua		
        this.contrunhua.setBoundLabelText(resHelper.getString("contrunhua.boundLabelText"));		
        this.contrunhua.setBoundLabelLength(135);		
        this.contrunhua.setBoundLabelUnderline(true);		
        this.contrunhua.setVisible(true);
        // contqiexiao		
        this.contqiexiao.setBoundLabelText(resHelper.getString("contqiexiao.boundLabelText"));		
        this.contqiexiao.setBoundLabelLength(100);		
        this.contqiexiao.setBoundLabelUnderline(true);		
        this.contqiexiao.setVisible(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // contbanzhang		
        this.contbanzhang.setBoundLabelText(resHelper.getString("contbanzhang.boundLabelText"));		
        this.contbanzhang.setBoundLabelLength(0);		
        this.contbanzhang.setBoundLabelUnderline(true);		
        this.contbanzhang.setVisible(true);
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // contzhuxiuone		
        this.contzhuxiuone.setBoundLabelText(resHelper.getString("contzhuxiuone.boundLabelText"));		
        this.contzhuxiuone.setBoundLabelLength(0);		
        this.contzhuxiuone.setBoundLabelUnderline(true);		
        this.contzhuxiuone.setVisible(true);
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // contjianyanyuanone		
        this.contjianyanyuanone.setBoundLabelText(resHelper.getString("contjianyanyuanone.boundLabelText"));		
        this.contjianyanyuanone.setBoundLabelLength(0);		
        this.contjianyanyuanone.setBoundLabelUnderline(true);		
        this.contjianyanyuanone.setVisible(true);
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // contleixing		
        this.contleixing.setBoundLabelText(resHelper.getString("contleixing.boundLabelText"));		
        this.contleixing.setBoundLabelLength(0);		
        this.contleixing.setBoundLabelUnderline(true);		
        this.contleixing.setVisible(true);
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // contleixingone		
        this.contleixingone.setBoundLabelText(resHelper.getString("contleixingone.boundLabelText"));		
        this.contleixingone.setBoundLabelLength(0);		
        this.contleixingone.setBoundLabelUnderline(true);		
        this.contleixingone.setVisible(true);
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // contchejianyijian		
        this.contchejianyijian.setBoundLabelText(resHelper.getString("contchejianyijian.boundLabelText"));		
        this.contchejianyijian.setBoundLabelLength(100);		
        this.contchejianyijian.setBoundLabelUnderline(true);		
        this.contchejianyijian.setVisible(true);
        // contchejianlingdao		
        this.contchejianlingdao.setBoundLabelText(resHelper.getString("contchejianlingdao.boundLabelText"));		
        this.contchejianlingdao.setBoundLabelLength(100);		
        this.contchejianlingdao.setBoundLabelUnderline(true);		
        this.contchejianlingdao.setVisible(true);
        // contjieshouren		
        this.contjieshouren.setBoundLabelText(resHelper.getString("contjieshouren.boundLabelText"));		
        this.contjieshouren.setBoundLabelLength(100);		
        this.contjieshouren.setBoundLabelUnderline(true);		
        this.contjieshouren.setVisible(true);
        // contyijiaoren		
        this.contyijiaoren.setBoundLabelText(resHelper.getString("contyijiaoren.boundLabelText"));		
        this.contyijiaoren.setBoundLabelLength(100);		
        this.contyijiaoren.setBoundLabelUnderline(true);		
        this.contyijiaoren.setVisible(true);
        // contyijiaodanwei		
        this.contyijiaodanwei.setBoundLabelText(resHelper.getString("contyijiaodanwei.boundLabelText"));		
        this.contyijiaodanwei.setBoundLabelLength(100);		
        this.contyijiaodanwei.setBoundLabelUnderline(true);		
        this.contyijiaodanwei.setVisible(true);
        // contjiaojieTime		
        this.contjiaojieTime.setBoundLabelText(resHelper.getString("contjiaojieTime.boundLabelText"));		
        this.contjiaojieTime.setBoundLabelLength(100);		
        this.contjiaojieTime.setBoundLabelUnderline(true);		
        this.contjiaojieTime.setVisible(true);
        // contacStartTime		
        this.contacStartTime.setBoundLabelText(resHelper.getString("contacStartTime.boundLabelText"));		
        this.contacStartTime.setBoundLabelLength(100);		
        this.contacStartTime.setBoundLabelUnderline(true);		
        this.contacStartTime.setVisible(true);
        // contacEndTime		
        this.contacEndTime.setBoundLabelText(resHelper.getString("contacEndTime.boundLabelText"));		
        this.contacEndTime.setBoundLabelLength(100);		
        this.contacEndTime.setBoundLabelUnderline(true);		
        this.contacEndTime.setVisible(true);
        // txtdianqishebei		
        this.txtdianqishebei.setVisible(true);		
        this.txtdianqishebei.setHorizontalAlignment(2);		
        this.txtdianqishebei.setMaxLength(100);		
        this.txtdianqishebei.setRequired(false);
        // txtchuangdong		
        this.txtchuangdong.setVisible(true);		
        this.txtchuangdong.setHorizontalAlignment(2);		
        this.txtchuangdong.setMaxLength(100);		
        this.txtchuangdong.setRequired(false);
        // txtrunhua		
        this.txtrunhua.setVisible(true);		
        this.txtrunhua.setHorizontalAlignment(2);		
        this.txtrunhua.setMaxLength(100);		
        this.txtrunhua.setRequired(false);
        // txtqiexiao		
        this.txtqiexiao.setVisible(true);		
        this.txtqiexiao.setHorizontalAlignment(2);		
        this.txtqiexiao.setMaxLength(100);		
        this.txtqiexiao.setRequired(false);
        // prmtbanzhang		
        this.prmtbanzhang.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtbanzhang.setVisible(true);		
        this.prmtbanzhang.setEditable(true);		
        this.prmtbanzhang.setDisplayFormat("$name$");		
        this.prmtbanzhang.setEditFormat("$number$");		
        this.prmtbanzhang.setCommitFormat("$number$");		
        this.prmtbanzhang.setRequired(false);
        // prmtzhuxiuone		
        this.prmtzhuxiuone.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtzhuxiuone.setVisible(true);		
        this.prmtzhuxiuone.setEditable(true);		
        this.prmtzhuxiuone.setDisplayFormat("$name$");		
        this.prmtzhuxiuone.setEditFormat("$number$");		
        this.prmtzhuxiuone.setCommitFormat("$number$");		
        this.prmtzhuxiuone.setRequired(false);
        // prmtjianyanyuanone		
        this.prmtjianyanyuanone.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtjianyanyuanone.setVisible(true);		
        this.prmtjianyanyuanone.setEditable(true);		
        this.prmtjianyanyuanone.setDisplayFormat("$name$");		
        this.prmtjianyanyuanone.setEditFormat("$number$");		
        this.prmtjianyanyuanone.setCommitFormat("$number$");		
        this.prmtjianyanyuanone.setRequired(false);
        // txtleixing		
        this.txtleixing.setVisible(true);		
        this.txtleixing.setHorizontalAlignment(2);		
        this.txtleixing.setMaxLength(50);		
        this.txtleixing.setRequired(false);
        // txtleixingone		
        this.txtleixingone.setVisible(true);		
        this.txtleixingone.setHorizontalAlignment(2);		
        this.txtleixingone.setMaxLength(50);		
        this.txtleixingone.setRequired(false);
        // scrollPanechejianyijian
        // txtchejianyijian		
        this.txtchejianyijian.setVisible(true);		
        this.txtchejianyijian.setRequired(false);		
        this.txtchejianyijian.setMaxLength(500);
        // prmtchejianlingdao		
        this.prmtchejianlingdao.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtchejianlingdao.setVisible(true);		
        this.prmtchejianlingdao.setEditable(true);		
        this.prmtchejianlingdao.setDisplayFormat("$name$");		
        this.prmtchejianlingdao.setEditFormat("$number$");		
        this.prmtchejianlingdao.setCommitFormat("$number$");		
        this.prmtchejianlingdao.setRequired(false);
        // prmtjieshouren		
        this.prmtjieshouren.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtjieshouren.setVisible(true);		
        this.prmtjieshouren.setEditable(true);		
        this.prmtjieshouren.setDisplayFormat("$name$");		
        this.prmtjieshouren.setEditFormat("$number$");		
        this.prmtjieshouren.setCommitFormat("$number$");		
        this.prmtjieshouren.setRequired(false);
        // prmtyijiaoren		
        this.prmtyijiaoren.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtyijiaoren.setVisible(true);		
        this.prmtyijiaoren.setEditable(true);		
        this.prmtyijiaoren.setDisplayFormat("$name$");		
        this.prmtyijiaoren.setEditFormat("$number$");		
        this.prmtyijiaoren.setCommitFormat("$number$");		
        this.prmtyijiaoren.setRequired(false);
        // prmtyijiaodanwei		
        this.prmtyijiaodanwei.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminItemQuery");		
        this.prmtyijiaodanwei.setVisible(true);		
        this.prmtyijiaodanwei.setEditable(true);		
        this.prmtyijiaodanwei.setDisplayFormat("$name$");		
        this.prmtyijiaodanwei.setEditFormat("$number$");		
        this.prmtyijiaodanwei.setCommitFormat("$number$");		
        this.prmtyijiaodanwei.setRequired(false);
        // pkjiaojieTime		
        this.pkjiaojieTime.setVisible(true);		
        this.pkjiaojieTime.setRequired(false);
        // pkacStartTime		
        this.pkacStartTime.setVisible(true);		
        this.pkacStartTime.setRequired(false);
        // pkacEndTime		
        this.pkacEndTime.setVisible(true);		
        this.pkacEndTime.setRequired(false);
        // pkAuditTime		
        this.pkAuditTime.setTimeEnabled(true);		
        this.pkAuditTime.setEnabled(false);
        // comboBizStatus		
        this.comboBizStatus.addItems(EnumUtils.getEnumList("com.kingdee.eas.xr.app.XRBizActionEnum").toArray());		
        this.comboBizStatus.setEnabled(false);		
        this.comboBizStatus.setVisible(false);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);		
        this.prmtAuditor.setCommitFormat("$name$");		
        this.prmtAuditor.setDisplayFormat("$name$");		
        this.prmtAuditor.setEditFormat("$name$");
        // pkLastUpdateTime		
        this.pkLastUpdateTime.setTimeEnabled(true);		
        this.pkLastUpdateTime.setEnabled(false);
        // prmtLastUpdateUser		
        this.prmtLastUpdateUser.setEnabled(false);		
        this.prmtLastUpdateUser.setDisplayFormat("$name$");		
        this.prmtLastUpdateUser.setEditFormat("$name$");		
        this.prmtLastUpdateUser.setCommitFormat("$name$");
        // pkCreateTime		
        this.pkCreateTime.setTimeEnabled(true);		
        this.pkCreateTime.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);		
        this.prmtCreator.setCommitFormat("$name$");		
        this.prmtCreator.setEditFormat("$name$");		
        this.prmtCreator.setDisplayFormat("$name$");
        // kDPanel4
        // kDPanel5
        // kDPanel6
        // kDPanel7
        // kDPanel2
        // kdtE3
		String kdtE3StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"jianchaxiangmu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"yaoqiu\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jielun\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{jianchaxiangmu}</t:Cell><t:Cell>$Resource{yaoqiu}</t:Cell><t:Cell>$Resource{shice}</t:Cell><t:Cell>$Resource{jielun}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE3.setFormatXml(resHelper.translateString("kdtE3",kdtE3StrXML));

                this.kdtE3.putBindContents("editData",new String[] {"seq","jianchaxiangmu","yaoqiu","shice","jielun"});


        this.kdtE3.checkParsed();
        KDTextField kdtE3_jianchaxiangmu_TextField = new KDTextField();
        kdtE3_jianchaxiangmu_TextField.setName("kdtE3_jianchaxiangmu_TextField");
        kdtE3_jianchaxiangmu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_jianchaxiangmu_CellEditor = new KDTDefaultCellEditor(kdtE3_jianchaxiangmu_TextField);
        this.kdtE3.getColumn("jianchaxiangmu").setEditor(kdtE3_jianchaxiangmu_CellEditor);
        KDTextField kdtE3_yaoqiu_TextField = new KDTextField();
        kdtE3_yaoqiu_TextField.setName("kdtE3_yaoqiu_TextField");
        kdtE3_yaoqiu_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_yaoqiu_CellEditor = new KDTDefaultCellEditor(kdtE3_yaoqiu_TextField);
        this.kdtE3.getColumn("yaoqiu").setEditor(kdtE3_yaoqiu_CellEditor);
        KDTextField kdtE3_shice_TextField = new KDTextField();
        kdtE3_shice_TextField.setName("kdtE3_shice_TextField");
        kdtE3_shice_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_shice_CellEditor = new KDTDefaultCellEditor(kdtE3_shice_TextField);
        this.kdtE3.getColumn("shice").setEditor(kdtE3_shice_CellEditor);
        KDTextField kdtE3_jielun_TextField = new KDTextField();
        kdtE3_jielun_TextField.setName("kdtE3_jielun_TextField");
        kdtE3_jielun_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE3_jielun_CellEditor = new KDTDefaultCellEditor(kdtE3_jielun_TextField);
        this.kdtE3.getColumn("jielun").setEditor(kdtE3_jielun_CellEditor);
        // kdtE2
		String kdtE2StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"xiangmuone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kongone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"fuone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kongtwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"futwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"kongthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"futhree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jieluntwo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"note\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{xiangmuone}</t:Cell><t:Cell>$Resource{kongone}</t:Cell><t:Cell>$Resource{fuone}</t:Cell><t:Cell>$Resource{kongtwo}</t:Cell><t:Cell>$Resource{futwo}</t:Cell><t:Cell>$Resource{kongthree}</t:Cell><t:Cell>$Resource{futhree}</t:Cell><t:Cell>$Resource{jieluntwo}</t:Cell><t:Cell>$Resource{note}</t:Cell></t:Row><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq_Row2}</t:Cell><t:Cell>$Resource{xiangmuone_Row2}</t:Cell><t:Cell>$Resource{kongone_Row2}</t:Cell><t:Cell>$Resource{fuone_Row2}</t:Cell><t:Cell>$Resource{kongtwo_Row2}</t:Cell><t:Cell>$Resource{futwo_Row2}</t:Cell><t:Cell>$Resource{kongthree_Row2}</t:Cell><t:Cell>$Resource{futhree_Row2}</t:Cell><t:Cell>$Resource{jieluntwo_Row2}</t:Cell><t:Cell>$Resource{note_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"0\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"0\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"1\" t:right=\"8\" /><t:Block t:top=\"0\" t:left=\"9\" t:bottom=\"1\" t:right=\"9\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE2.setFormatXml(resHelper.translateString("kdtE2",kdtE2StrXML));

                this.kdtE2.putBindContents("editData",new String[] {"seq","xiangmuone","kongone","fuone","kongtwo","futwo","kongthree","futhree","jieluntwo","note"});


        this.kdtE2.checkParsed();
        KDTextField kdtE2_xiangmuone_TextField = new KDTextField();
        kdtE2_xiangmuone_TextField.setName("kdtE2_xiangmuone_TextField");
        kdtE2_xiangmuone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_xiangmuone_CellEditor = new KDTDefaultCellEditor(kdtE2_xiangmuone_TextField);
        this.kdtE2.getColumn("xiangmuone").setEditor(kdtE2_xiangmuone_CellEditor);
        KDTextField kdtE2_kongone_TextField = new KDTextField();
        kdtE2_kongone_TextField.setName("kdtE2_kongone_TextField");
        kdtE2_kongone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_kongone_CellEditor = new KDTDefaultCellEditor(kdtE2_kongone_TextField);
        this.kdtE2.getColumn("kongone").setEditor(kdtE2_kongone_CellEditor);
        KDTextField kdtE2_fuone_TextField = new KDTextField();
        kdtE2_fuone_TextField.setName("kdtE2_fuone_TextField");
        kdtE2_fuone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_fuone_CellEditor = new KDTDefaultCellEditor(kdtE2_fuone_TextField);
        this.kdtE2.getColumn("fuone").setEditor(kdtE2_fuone_CellEditor);
        KDTextField kdtE2_kongtwo_TextField = new KDTextField();
        kdtE2_kongtwo_TextField.setName("kdtE2_kongtwo_TextField");
        kdtE2_kongtwo_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_kongtwo_CellEditor = new KDTDefaultCellEditor(kdtE2_kongtwo_TextField);
        this.kdtE2.getColumn("kongtwo").setEditor(kdtE2_kongtwo_CellEditor);
        KDTextField kdtE2_futwo_TextField = new KDTextField();
        kdtE2_futwo_TextField.setName("kdtE2_futwo_TextField");
        kdtE2_futwo_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_futwo_CellEditor = new KDTDefaultCellEditor(kdtE2_futwo_TextField);
        this.kdtE2.getColumn("futwo").setEditor(kdtE2_futwo_CellEditor);
        KDTextField kdtE2_kongthree_TextField = new KDTextField();
        kdtE2_kongthree_TextField.setName("kdtE2_kongthree_TextField");
        kdtE2_kongthree_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_kongthree_CellEditor = new KDTDefaultCellEditor(kdtE2_kongthree_TextField);
        this.kdtE2.getColumn("kongthree").setEditor(kdtE2_kongthree_CellEditor);
        KDTextField kdtE2_futhree_TextField = new KDTextField();
        kdtE2_futhree_TextField.setName("kdtE2_futhree_TextField");
        kdtE2_futhree_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_futhree_CellEditor = new KDTDefaultCellEditor(kdtE2_futhree_TextField);
        this.kdtE2.getColumn("futhree").setEditor(kdtE2_futhree_CellEditor);
        KDTextField kdtE2_jieluntwo_TextField = new KDTextField();
        kdtE2_jieluntwo_TextField.setName("kdtE2_jieluntwo_TextField");
        kdtE2_jieluntwo_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_jieluntwo_CellEditor = new KDTDefaultCellEditor(kdtE2_jieluntwo_TextField);
        this.kdtE2.getColumn("jieluntwo").setEditor(kdtE2_jieluntwo_CellEditor);
        KDTextField kdtE2_note_TextField = new KDTextField();
        kdtE2_note_TextField.setName("kdtE2_note_TextField");
        kdtE2_note_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE2_note_CellEditor = new KDTDefaultCellEditor(kdtE2_note_TextField);
        this.kdtE2.getColumn("note").setEditor(kdtE2_note_CellEditor);
        // contshijian		
        this.contshijian.setBoundLabelText(resHelper.getString("contshijian.boundLabelText"));		
        this.contshijian.setBoundLabelLength(100);		
        this.contshijian.setBoundLabelUnderline(true);		
        this.contshijian.setVisible(true);
        // contyingdu		
        this.contyingdu.setBoundLabelText(resHelper.getString("contyingdu.boundLabelText"));		
        this.contyingdu.setBoundLabelLength(100);		
        this.contyingdu.setBoundLabelUnderline(true);		
        this.contyingdu.setVisible(true);
        // scrollPaneshijian
        // txtshijian		
        this.txtshijian.setVisible(true);		
        this.txtshijian.setRequired(false);		
        this.txtshijian.setMaxLength(200);
        // scrollPaneyingdu
        // txtyingdu		
        this.txtyingdu.setVisible(true);		
        this.txtyingdu.setRequired(false);		
        this.txtyingdu.setMaxLength(100);
        // kdtE1
		String kdtE1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:NumberFormat>&amp;int</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"xiangmuthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"biaozhun\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"shiji\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"jielunthree\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"beizhuone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{xiangmuthree}</t:Cell><t:Cell>$Resource{biaozhun}</t:Cell><t:Cell>$Resource{shiji}</t:Cell><t:Cell>$Resource{jielunthree}</t:Cell><t:Cell>$Resource{beizhuone}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtE1.setFormatXml(resHelper.translateString("kdtE1",kdtE1StrXML));

                this.kdtE1.putBindContents("editData",new String[] {"seq","xiangmuthree","biaozhun","shiji","jielunthree","beizhuone"});


        this.kdtE1.checkParsed();
        KDTextField kdtE1_xiangmuthree_TextField = new KDTextField();
        kdtE1_xiangmuthree_TextField.setName("kdtE1_xiangmuthree_TextField");
        kdtE1_xiangmuthree_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_xiangmuthree_CellEditor = new KDTDefaultCellEditor(kdtE1_xiangmuthree_TextField);
        this.kdtE1.getColumn("xiangmuthree").setEditor(kdtE1_xiangmuthree_CellEditor);
        KDTextField kdtE1_biaozhun_TextField = new KDTextField();
        kdtE1_biaozhun_TextField.setName("kdtE1_biaozhun_TextField");
        kdtE1_biaozhun_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_biaozhun_CellEditor = new KDTDefaultCellEditor(kdtE1_biaozhun_TextField);
        this.kdtE1.getColumn("biaozhun").setEditor(kdtE1_biaozhun_CellEditor);
        KDTextField kdtE1_shiji_TextField = new KDTextField();
        kdtE1_shiji_TextField.setName("kdtE1_shiji_TextField");
        kdtE1_shiji_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_shiji_CellEditor = new KDTDefaultCellEditor(kdtE1_shiji_TextField);
        this.kdtE1.getColumn("shiji").setEditor(kdtE1_shiji_CellEditor);
        KDTextField kdtE1_jielunthree_TextField = new KDTextField();
        kdtE1_jielunthree_TextField.setName("kdtE1_jielunthree_TextField");
        kdtE1_jielunthree_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_jielunthree_CellEditor = new KDTDefaultCellEditor(kdtE1_jielunthree_TextField);
        this.kdtE1.getColumn("jielunthree").setEditor(kdtE1_jielunthree_CellEditor);
        KDTextField kdtE1_beizhuone_TextField = new KDTextField();
        kdtE1_beizhuone_TextField.setName("kdtE1_beizhuone_TextField");
        kdtE1_beizhuone_TextField.setMaxLength(100);
        KDTDefaultCellEditor kdtE1_beizhuone_CellEditor = new KDTDefaultCellEditor(kdtE1_beizhuone_TextField);
        this.kdtE1.getColumn("beizhuone").setEditor(kdtE1_beizhuone_CellEditor);
        // contzhuxiuqiangong		
        this.contzhuxiuqiangong.setBoundLabelText(resHelper.getString("contzhuxiuqiangong.boundLabelText"));		
        this.contzhuxiuqiangong.setBoundLabelLength(100);		
        this.contzhuxiuqiangong.setBoundLabelUnderline(true);		
        this.contzhuxiuqiangong.setVisible(true);
        // contzhuxiudiangong		
        this.contzhuxiudiangong.setBoundLabelText(resHelper.getString("contzhuxiudiangong.boundLabelText"));		
        this.contzhuxiudiangong.setBoundLabelLength(100);		
        this.contzhuxiudiangong.setBoundLabelUnderline(true);		
        this.contzhuxiudiangong.setVisible(true);
        // contjianyanyuan		
        this.contjianyanyuan.setBoundLabelText(resHelper.getString("contjianyanyuan.boundLabelText"));		
        this.contjianyanyuan.setBoundLabelLength(144);		
        this.contjianyanyuan.setBoundLabelUnderline(true);		
        this.contjianyanyuan.setVisible(true);
        // prmtzhuxiuqiangong		
        this.prmtzhuxiuqiangong.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtzhuxiuqiangong.setVisible(true);		
        this.prmtzhuxiuqiangong.setEditable(true);		
        this.prmtzhuxiuqiangong.setDisplayFormat("$name$");		
        this.prmtzhuxiuqiangong.setEditFormat("$number$");		
        this.prmtzhuxiuqiangong.setCommitFormat("$number$");		
        this.prmtzhuxiuqiangong.setRequired(false);
        // prmtzhuxiudiangong		
        this.prmtzhuxiudiangong.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtzhuxiudiangong.setVisible(true);		
        this.prmtzhuxiudiangong.setEditable(true);		
        this.prmtzhuxiudiangong.setDisplayFormat("$name$");		
        this.prmtzhuxiudiangong.setEditFormat("$number$");		
        this.prmtzhuxiudiangong.setCommitFormat("$number$");		
        this.prmtzhuxiudiangong.setRequired(false);
        // prmtjianyanyuan		
        this.prmtjianyanyuan.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtjianyanyuan.setVisible(true);		
        this.prmtjianyanyuan.setEditable(true);		
        this.prmtjianyanyuan.setDisplayFormat("$name$");		
        this.prmtjianyanyuan.setEditFormat("$number$");		
        this.prmtjianyanyuan.setCommitFormat("$number$");		
        this.prmtjianyanyuan.setRequired(false);
        // txtidone		
        this.txtidone.setVisible(true);		
        this.txtidone.setHorizontalAlignment(2);		
        this.txtidone.setMaxLength(100);		
        this.txtidone.setRequired(false);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtequNumber,txtequName,txtmodel,txtuseDepart,prmtxiadaPerson,prmtxiadaDepart,pkxiadaTiem,prmtweixiuType,txtbaoqianxiangmu,txtmainNeirong,txtgenghuanling,txtshijian,txtyingdu,prmtzhuxiuqiangong,prmtzhuxiudiangong,prmtjianyanyuan,txtdianqishebei,txtchuangdong,txtrunhua,txtqiexiao,prmtbanzhang,prmtzhuxiuone,prmtjianyanyuanone,txtleixing,txtleixingone,txtchejianyijian,prmtchejianlingdao,prmtjieshouren,prmtyijiaoren,prmtyijiaodanwei,pkjiaojieTime,pkacStartTime,pkacEndTime,pkplanStartTime,pkplanEndTime,txtidone,txtmainone}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDPanel1.setBounds(new Rectangle(5, 5, 1003, 163));
        this.add(kDPanel1, new KDLayout.Constraints(5, 5, 1003, 163, 0));
        kDPanel3.setBounds(new Rectangle(5, 384, 1003, 194));
        this.add(kDPanel3, new KDLayout.Constraints(5, 384, 1003, 194, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(738, 583, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(738, 583, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizStatus.setBounds(new Rectangle(1072, 588, 270, 19));
        this.add(contBizStatus, new KDLayout.Constraints(1072, 588, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(738, 604, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(738, 604, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateTime.setBounds(new Rectangle(371, 604, 270, 19));
        this.add(contLastUpdateTime, new KDLayout.Constraints(371, 604, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contLastUpdateUser.setBounds(new Rectangle(371, 583, 270, 19));
        this.add(contLastUpdateUser, new KDLayout.Constraints(371, 583, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(5, 604, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(5, 604, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(5, 583, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(5, 583, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane1.setBounds(new Rectangle(5, 170, 1003, 212));
        this.add(kDTabbedPane1, new KDLayout.Constraints(5, 170, 1003, 212, 0));
        contidone.setBounds(new Rectangle(308, 607, 270, 19));
        this.add(contidone, new KDLayout.Constraints(308, 607, 270, 19, 0));
        //kDPanel1
        kDPanel1.setLayout(null);        contNumber.setBounds(new Rectangle(11, 13, 207, 19));
        kDPanel1.add(contNumber, null);
        contequNumber.setBounds(new Rectangle(11, 33, 207, 19));
        kDPanel1.add(contequNumber, null);
        contuseDepart.setBounds(new Rectangle(782, 33, 207, 19));
        kDPanel1.add(contuseDepart, null);
        contxiadaPerson.setBounds(new Rectangle(268, 53, 207, 19));
        kDPanel1.add(contxiadaPerson, null);
        contBizDate.setBounds(new Rectangle(268, 13, 207, 19));
        kDPanel1.add(contBizDate, null);
        contequName.setBounds(new Rectangle(268, 33, 207, 19));
        kDPanel1.add(contequName, null);
        contweixiuType.setBounds(new Rectangle(11, 53, 207, 19));
        kDPanel1.add(contweixiuType, null);
        contxiadaDepart.setBounds(new Rectangle(525, 53, 207, 19));
        kDPanel1.add(contxiadaDepart, null);
        contCU.setBounds(new Rectangle(525, 13, 207, 19));
        kDPanel1.add(contCU, null);
        contmodel.setBounds(new Rectangle(525, 33, 207, 19));
        kDPanel1.add(contmodel, null);
        contStatus.setBounds(new Rectangle(782, 13, 207, 19));
        kDPanel1.add(contStatus, null);
        contxiadaTiem.setBounds(new Rectangle(782, 53, 207, 19));
        kDPanel1.add(contxiadaTiem, null);
        contbaoqianxiangmu.setBounds(new Rectangle(11, 75, 464, 35));
        kDPanel1.add(contbaoqianxiangmu, null);
        contmainNeirong.setBounds(new Rectangle(11, 113, 464, 35));
        kDPanel1.add(contmainNeirong, null);
        contgenghuanling.setBounds(new Rectangle(525, 75, 246, 35));
        kDPanel1.add(contgenghuanling, null);
        contDescription.setBounds(new Rectangle(1075, -7, 270, 19));
        kDPanel1.add(contDescription, null);
        contplanStartTime.setBounds(new Rectangle(782, 73, 207, 19));
        kDPanel1.add(contplanStartTime, null);
        contplanEndTime.setBounds(new Rectangle(782, 93, 207, 19));
        kDPanel1.add(contplanEndTime, null);
        contmainone.setBounds(new Rectangle(525, 113, 464, 35));
        kDPanel1.add(contmainone, null);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contequNumber
        contequNumber.setBoundEditor(prmtequNumber);
        //contuseDepart
        contuseDepart.setBoundEditor(txtuseDepart);
        //contxiadaPerson
        contxiadaPerson.setBoundEditor(prmtxiadaPerson);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contequName
        contequName.setBoundEditor(txtequName);
        //contweixiuType
        contweixiuType.setBoundEditor(prmtweixiuType);
        //contxiadaDepart
        contxiadaDepart.setBoundEditor(prmtxiadaDepart);
        //contCU
        contCU.setBoundEditor(prmtCU);
        //contmodel
        contmodel.setBoundEditor(txtmodel);
        //contStatus
        contStatus.setBoundEditor(comboStatus);
        //contxiadaTiem
        contxiadaTiem.setBoundEditor(pkxiadaTiem);
        //contbaoqianxiangmu
        contbaoqianxiangmu.setBoundEditor(scrollPanebaoqianxiangmu);
        //scrollPanebaoqianxiangmu
        scrollPanebaoqianxiangmu.getViewport().add(txtbaoqianxiangmu, null);
        //contmainNeirong
        contmainNeirong.setBoundEditor(scrollPanemainNeirong);
        //scrollPanemainNeirong
        scrollPanemainNeirong.getViewport().add(txtmainNeirong, null);
        //contgenghuanling
        contgenghuanling.setBoundEditor(scrollPanegenghuanling);
        //scrollPanegenghuanling
        scrollPanegenghuanling.getViewport().add(txtgenghuanling, null);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contplanStartTime
        contplanStartTime.setBoundEditor(pkplanStartTime);
        //contplanEndTime
        contplanEndTime.setBoundEditor(pkplanEndTime);
        //contmainone
        contmainone.setBoundEditor(scrollPanemainone);
        //scrollPanemainone
        scrollPanemainone.getViewport().add(txtmainone, null);
        //kDPanel3
        kDPanel3.setLayout(null);        contdianqishebei.setBounds(new Rectangle(12, 14, 619, 19));
        kDPanel3.add(contdianqishebei, null);
        contchuangdong.setBounds(new Rectangle(12, 34, 619, 19));
        kDPanel3.add(contchuangdong, null);
        contrunhua.setBounds(new Rectangle(12, 54, 619, 19));
        kDPanel3.add(contrunhua, null);
        contqiexiao.setBounds(new Rectangle(12, 74, 619, 19));
        kDPanel3.add(contqiexiao, null);
        kDLabel1.setBounds(new Rectangle(12, 95, 225, 15));
        kDPanel3.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(14, 110, 86, 19));
        kDPanel3.add(kDLabel2, null);
        contbanzhang.setBounds(new Rectangle(101, 110, 91, 19));
        kDPanel3.add(contbanzhang, null);
        kDLabel3.setBounds(new Rectangle(196, 110, 31, 19));
        kDPanel3.add(kDLabel3, null);
        contzhuxiuone.setBounds(new Rectangle(224, 110, 91, 19));
        kDPanel3.add(contzhuxiuone, null);
        kDLabel4.setBounds(new Rectangle(317, 110, 44, 19));
        kDPanel3.add(kDLabel4, null);
        contjianyanyuanone.setBounds(new Rectangle(354, 110, 91, 19));
        kDPanel3.add(contjianyanyuanone, null);
        kDLabel5.setBounds(new Rectangle(447, 110, 32, 19));
        kDPanel3.add(kDLabel5, null);
        contleixing.setBounds(new Rectangle(474, 110, 91, 19));
        kDPanel3.add(contleixing, null);
        kDLabel6.setBounds(new Rectangle(568, 110, 62, 19));
        kDPanel3.add(kDLabel6, null);
        kDLabel7.setBounds(new Rectangle(14, 130, 53, 19));
        kDPanel3.add(kDLabel7, null);
        contleixingone.setBounds(new Rectangle(69, 130, 91, 19));
        kDPanel3.add(contleixingone, null);
        kDLabel8.setBounds(new Rectangle(162, 130, 113, 19));
        kDPanel3.add(kDLabel8, null);
        contchejianyijian.setBounds(new Rectangle(14, 150, 619, 28));
        kDPanel3.add(contchejianyijian, null);
        contchejianlingdao.setBounds(new Rectangle(719, 12, 270, 19));
        kDPanel3.add(contchejianlingdao, null);
        contjieshouren.setBounds(new Rectangle(719, 34, 270, 19));
        kDPanel3.add(contjieshouren, null);
        contyijiaoren.setBounds(new Rectangle(719, 54, 270, 19));
        kDPanel3.add(contyijiaoren, null);
        contyijiaodanwei.setBounds(new Rectangle(719, 74, 270, 19));
        kDPanel3.add(contyijiaodanwei, null);
        contjiaojieTime.setBounds(new Rectangle(719, 94, 270, 19));
        kDPanel3.add(contjiaojieTime, null);
        contacStartTime.setBounds(new Rectangle(719, 114, 270, 19));
        kDPanel3.add(contacStartTime, null);
        contacEndTime.setBounds(new Rectangle(719, 134, 270, 19));
        kDPanel3.add(contacEndTime, null);
        //contdianqishebei
        contdianqishebei.setBoundEditor(txtdianqishebei);
        //contchuangdong
        contchuangdong.setBoundEditor(txtchuangdong);
        //contrunhua
        contrunhua.setBoundEditor(txtrunhua);
        //contqiexiao
        contqiexiao.setBoundEditor(txtqiexiao);
        //contbanzhang
        contbanzhang.setBoundEditor(prmtbanzhang);
        //contzhuxiuone
        contzhuxiuone.setBoundEditor(prmtzhuxiuone);
        //contjianyanyuanone
        contjianyanyuanone.setBoundEditor(prmtjianyanyuanone);
        //contleixing
        contleixing.setBoundEditor(txtleixing);
        //contleixingone
        contleixingone.setBoundEditor(txtleixingone);
        //contchejianyijian
        contchejianyijian.setBoundEditor(scrollPanechejianyijian);
        //scrollPanechejianyijian
        scrollPanechejianyijian.getViewport().add(txtchejianyijian, null);
        //contchejianlingdao
        contchejianlingdao.setBoundEditor(prmtchejianlingdao);
        //contjieshouren
        contjieshouren.setBoundEditor(prmtjieshouren);
        //contyijiaoren
        contyijiaoren.setBoundEditor(prmtyijiaoren);
        //contyijiaodanwei
        contyijiaodanwei.setBoundEditor(prmtyijiaodanwei);
        //contjiaojieTime
        contjiaojieTime.setBoundEditor(pkjiaojieTime);
        //contacStartTime
        contacStartTime.setBoundEditor(pkacStartTime);
        //contacEndTime
        contacEndTime.setBoundEditor(pkacEndTime);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contBizStatus
        contBizStatus.setBoundEditor(comboBizStatus);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contLastUpdateTime
        contLastUpdateTime.setBoundEditor(pkLastUpdateTime);
        //contLastUpdateUser
        contLastUpdateUser.setBoundEditor(prmtLastUpdateUser);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        kDTabbedPane1.add(kDPanel6, resHelper.getString("kDPanel6.constraints"));
        kDTabbedPane1.add(kDPanel7, resHelper.getString("kDPanel7.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel4
        kDPanel4.setLayout(null);        kdtE3.setBounds(new Rectangle(1, 0, 999, 173));
        kdtE3_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE3,new com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Info(),null,false);
        kDPanel4.add(kdtE3_detailPanel, null);
        //kDPanel5
        kDPanel5.setLayout(null);        kdtE2.setBounds(new Rectangle(0, -2, 999, 176));
        kdtE2_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE2,new com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Info(),null,false);
        kDPanel5.add(kdtE2_detailPanel, null);
        //kDPanel6
        kDPanel6.setLayout(null);        contshijian.setBounds(new Rectangle(1, 4, 550, 61));
        kDPanel6.add(contshijian, null);
        contyingdu.setBounds(new Rectangle(1, 94, 550, 61));
        kDPanel6.add(contyingdu, null);
        //contshijian
        contshijian.setBoundEditor(scrollPaneshijian);
        //scrollPaneshijian
        scrollPaneshijian.getViewport().add(txtshijian, null);
        //contyingdu
        contyingdu.setBoundEditor(scrollPaneyingdu);
        //scrollPaneyingdu
        scrollPaneyingdu.getViewport().add(txtyingdu, null);
        //kDPanel7
        kDPanel7.setLayout(null);        kdtE1.setBounds(new Rectangle(-3, -1, 1000, 176));
        kdtE1_detailPanel = (com.kingdee.eas.framework.client.multiDetail.DetailPanel)com.kingdee.eas.framework.client.multiDetail.HMDUtils.buildDetail(this,dataBinder,kdtE1,new com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Info(),null,false);
        kDPanel7.add(kdtE1_detailPanel, null);
        //kDPanel2
        kDPanel2.setLayout(null);        contzhuxiuqiangong.setBounds(new Rectangle(10, 8, 270, 19));
        kDPanel2.add(contzhuxiuqiangong, null);
        contzhuxiudiangong.setBounds(new Rectangle(10, 61, 270, 19));
        kDPanel2.add(contzhuxiudiangong, null);
        contjianyanyuan.setBounds(new Rectangle(10, 114, 270, 19));
        kDPanel2.add(contjianyanyuan, null);
        //contzhuxiuqiangong
        contzhuxiuqiangong.setBoundEditor(prmtzhuxiuqiangong);
        //contzhuxiudiangong
        contzhuxiudiangong.setBoundEditor(prmtzhuxiudiangong);
        //contjianyanyuan
        contjianyanyuan.setBoundEditor(prmtjianyanyuan);
        //contidone
        contidone.setBoundEditor(txtidone);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("equNumber", com.kingdee.eas.port.equipment.record.EquIdInfo.class, this.prmtequNumber, "data");
		dataBinder.registerBinding("useDepart", String.class, this.txtuseDepart, "text");
		dataBinder.registerBinding("xiadaPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtxiadaPerson, "data");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("equName", String.class, this.txtequName, "text");
		dataBinder.registerBinding("weixiuType", com.kingdee.eas.port.equipment.base.MaintenanceTypeInfo.class, this.prmtweixiuType, "data");
		dataBinder.registerBinding("xiadaDepart", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtxiadaDepart, "data");
		dataBinder.registerBinding("CU", com.kingdee.eas.basedata.org.CtrlUnitInfo.class, this.prmtCU, "data");
		dataBinder.registerBinding("model", String.class, this.txtmodel, "text");
		dataBinder.registerBinding("status", com.kingdee.eas.xr.app.XRBillStatusEnum.class, this.comboStatus, "selectedItem");
		dataBinder.registerBinding("xiadaTiem", java.util.Date.class, this.pkxiadaTiem, "value");
		dataBinder.registerBinding("baoqianxiangmu", String.class, this.txtbaoqianxiangmu, "text");
		dataBinder.registerBinding("mainNeirong", String.class, this.txtmainNeirong, "text");
		dataBinder.registerBinding("genghuanling", String.class, this.txtgenghuanling, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("planStartTime", java.util.Date.class, this.pkplanStartTime, "value");
		dataBinder.registerBinding("planEndTime", java.util.Date.class, this.pkplanEndTime, "value");
		dataBinder.registerBinding("mainone", String.class, this.txtmainone, "text");
		dataBinder.registerBinding("dianqishebei", String.class, this.txtdianqishebei, "text");
		dataBinder.registerBinding("chuangdong", String.class, this.txtchuangdong, "text");
		dataBinder.registerBinding("runhua", String.class, this.txtrunhua, "text");
		dataBinder.registerBinding("qiexiao", String.class, this.txtqiexiao, "text");
		dataBinder.registerBinding("banzhang", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtbanzhang, "data");
		dataBinder.registerBinding("zhuxiuone", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtzhuxiuone, "data");
		dataBinder.registerBinding("jianyanyuanone", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtjianyanyuanone, "data");
		dataBinder.registerBinding("leixing", String.class, this.txtleixing, "text");
		dataBinder.registerBinding("leixingone", String.class, this.txtleixingone, "text");
		dataBinder.registerBinding("chejianyijian", String.class, this.txtchejianyijian, "text");
		dataBinder.registerBinding("chejianlingdao", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtchejianlingdao, "data");
		dataBinder.registerBinding("jieshouren", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtjieshouren, "data");
		dataBinder.registerBinding("yijiaoren", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtyijiaoren, "data");
		dataBinder.registerBinding("yijiaodanwei", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtyijiaodanwei, "data");
		dataBinder.registerBinding("jiaojieTime", java.util.Date.class, this.pkjiaojieTime, "value");
		dataBinder.registerBinding("acStartTime", java.util.Date.class, this.pkacStartTime, "value");
		dataBinder.registerBinding("acEndTime", java.util.Date.class, this.pkacEndTime, "value");
		dataBinder.registerBinding("auditTime", java.sql.Timestamp.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("bizStatus", com.kingdee.eas.xr.app.XRBizActionEnum.class, this.comboBizStatus, "selectedItem");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkLastUpdateTime, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtLastUpdateUser, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("E3.seq", int.class, this.kdtE3, "seq.text");
		dataBinder.registerBinding("E3", com.kingdee.eas.port.equipment.maintenance.EquMaintBookE3Info.class, this.kdtE3, "userObject");
		dataBinder.registerBinding("E3.jianchaxiangmu", String.class, this.kdtE3, "jianchaxiangmu.text");
		dataBinder.registerBinding("E3.yaoqiu", String.class, this.kdtE3, "yaoqiu.text");
		dataBinder.registerBinding("E3.shice", String.class, this.kdtE3, "shice.text");
		dataBinder.registerBinding("E3.jielun", String.class, this.kdtE3, "jielun.text");
		dataBinder.registerBinding("E2.seq", int.class, this.kdtE2, "seq.text");
		dataBinder.registerBinding("E2", com.kingdee.eas.port.equipment.maintenance.EquMaintBookE2Info.class, this.kdtE2, "userObject");
		dataBinder.registerBinding("E2.xiangmuone", String.class, this.kdtE2, "xiangmuone.text");
		dataBinder.registerBinding("E2.kongone", String.class, this.kdtE2, "kongone.text");
		dataBinder.registerBinding("E2.fuone", String.class, this.kdtE2, "fuone.text");
		dataBinder.registerBinding("E2.kongtwo", String.class, this.kdtE2, "kongtwo.text");
		dataBinder.registerBinding("E2.futwo", String.class, this.kdtE2, "futwo.text");
		dataBinder.registerBinding("E2.kongthree", String.class, this.kdtE2, "kongthree.text");
		dataBinder.registerBinding("E2.futhree", String.class, this.kdtE2, "futhree.text");
		dataBinder.registerBinding("E2.jieluntwo", String.class, this.kdtE2, "jieluntwo.text");
		dataBinder.registerBinding("E2.note", String.class, this.kdtE2, "note.text");
		dataBinder.registerBinding("shijian", String.class, this.txtshijian, "text");
		dataBinder.registerBinding("yingdu", String.class, this.txtyingdu, "text");
		dataBinder.registerBinding("E1.seq", int.class, this.kdtE1, "seq.text");
		dataBinder.registerBinding("E1", com.kingdee.eas.port.equipment.maintenance.EquMaintBookE1Info.class, this.kdtE1, "userObject");
		dataBinder.registerBinding("E1.xiangmuthree", String.class, this.kdtE1, "xiangmuthree.text");
		dataBinder.registerBinding("E1.biaozhun", String.class, this.kdtE1, "biaozhun.text");
		dataBinder.registerBinding("E1.shiji", String.class, this.kdtE1, "shiji.text");
		dataBinder.registerBinding("E1.jielunthree", String.class, this.kdtE1, "jielunthree.text");
		dataBinder.registerBinding("E1.beizhuone", String.class, this.kdtE1, "beizhuone.text");
		dataBinder.registerBinding("zhuxiuqiangong", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtzhuxiuqiangong, "data");
		dataBinder.registerBinding("zhuxiudiangong", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtzhuxiudiangong, "data");
		dataBinder.registerBinding("jianyanyuan", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtjianyanyuan, "data");
		dataBinder.registerBinding("idone", String.class, this.txtidone, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.port.equipment.maintenance.app.EquMaintBookEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }


    /**
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtequNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"NONE",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("NONE");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
		}

    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("equNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("useDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xiadaPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("equName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("weixiuType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xiadaDepart", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("CU", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("model", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("status", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("xiadaTiem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("baoqianxiangmu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainNeirong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("genghuanling", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planStartTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planEndTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mainone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dianqishebei", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chuangdong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("runhua", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qiexiao", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("banzhang", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhuxiuone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jianyanyuanone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leixing", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("leixingone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chejianyijian", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chejianlingdao", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jieshouren", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yijiaoren", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yijiaodanwei", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jiaojieTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("acStartTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("acEndTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizStatus", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.jianchaxiangmu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.yaoqiu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.shice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E3.jielun", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.xiangmuone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.kongone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.fuone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.kongtwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.futwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.kongthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.futhree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.jieluntwo", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E2.note", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("shijian", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("yingdu", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.seq", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.xiangmuthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.biaozhun", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.shiji", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.jielunthree", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("E1.beizhuone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhuxiuqiangong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("zhuxiudiangong", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("jianyanyuan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("idone", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output prmtxiadaPerson_dataChanged method
     */
    protected void prmtxiadaPerson_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }


    /**
     * output prmtequNumber_Changed() method
     */
    public void prmtequNumber_Changed() throws Exception
    {
        System.out.println("prmtequNumber_Changed() Function is executed!");
            txtequName.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtequNumber.getData(),"name")));

    txtmodel.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtequNumber.getData(),"model")));

    txtuseDepart.setText(com.kingdee.bos.ui.face.UIRuleUtil.getString(com.kingdee.bos.ui.face.UIRuleUtil.getProperty((com.kingdee.bos.dao.IObjectValue)prmtequNumber.getData(),"usingDept.name")));


    }
    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("equNumber.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("equNumber.id"));
        	sic.add(new SelectorItemInfo("equNumber.number"));
        	sic.add(new SelectorItemInfo("equNumber.name"));
		}
        sic.add(new SelectorItemInfo("useDepart"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("xiadaPerson.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("xiadaPerson.id"));
        	sic.add(new SelectorItemInfo("xiadaPerson.number"));
        	sic.add(new SelectorItemInfo("xiadaPerson.name"));
		}
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("equName"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("weixiuType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("weixiuType.id"));
        	sic.add(new SelectorItemInfo("weixiuType.number"));
        	sic.add(new SelectorItemInfo("weixiuType.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("xiadaDepart.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("xiadaDepart.id"));
        	sic.add(new SelectorItemInfo("xiadaDepart.number"));
        	sic.add(new SelectorItemInfo("xiadaDepart.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("CU.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("CU.id"));
        	sic.add(new SelectorItemInfo("CU.number"));
        	sic.add(new SelectorItemInfo("CU.name"));
		}
        sic.add(new SelectorItemInfo("model"));
        sic.add(new SelectorItemInfo("status"));
        sic.add(new SelectorItemInfo("xiadaTiem"));
        sic.add(new SelectorItemInfo("baoqianxiangmu"));
        sic.add(new SelectorItemInfo("mainNeirong"));
        sic.add(new SelectorItemInfo("genghuanling"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("planStartTime"));
        sic.add(new SelectorItemInfo("planEndTime"));
        sic.add(new SelectorItemInfo("mainone"));
        sic.add(new SelectorItemInfo("dianqishebei"));
        sic.add(new SelectorItemInfo("chuangdong"));
        sic.add(new SelectorItemInfo("runhua"));
        sic.add(new SelectorItemInfo("qiexiao"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("banzhang.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("banzhang.id"));
        	sic.add(new SelectorItemInfo("banzhang.number"));
        	sic.add(new SelectorItemInfo("banzhang.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("zhuxiuone.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("zhuxiuone.id"));
        	sic.add(new SelectorItemInfo("zhuxiuone.number"));
        	sic.add(new SelectorItemInfo("zhuxiuone.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jianyanyuanone.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jianyanyuanone.id"));
        	sic.add(new SelectorItemInfo("jianyanyuanone.number"));
        	sic.add(new SelectorItemInfo("jianyanyuanone.name"));
		}
        sic.add(new SelectorItemInfo("leixing"));
        sic.add(new SelectorItemInfo("leixingone"));
        sic.add(new SelectorItemInfo("chejianyijian"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("chejianlingdao.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("chejianlingdao.id"));
        	sic.add(new SelectorItemInfo("chejianlingdao.number"));
        	sic.add(new SelectorItemInfo("chejianlingdao.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jieshouren.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jieshouren.id"));
        	sic.add(new SelectorItemInfo("jieshouren.number"));
        	sic.add(new SelectorItemInfo("jieshouren.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("yijiaoren.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("yijiaoren.id"));
        	sic.add(new SelectorItemInfo("yijiaoren.number"));
        	sic.add(new SelectorItemInfo("yijiaoren.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("yijiaodanwei.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("yijiaodanwei.id"));
        	sic.add(new SelectorItemInfo("yijiaodanwei.number"));
        	sic.add(new SelectorItemInfo("yijiaodanwei.name"));
		}
        sic.add(new SelectorItemInfo("jiaojieTime"));
        sic.add(new SelectorItemInfo("acStartTime"));
        sic.add(new SelectorItemInfo("acEndTime"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("bizStatus"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("lastUpdateTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
    	sic.add(new SelectorItemInfo("E3.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E3.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E3.jianchaxiangmu"));
    	sic.add(new SelectorItemInfo("E3.yaoqiu"));
    	sic.add(new SelectorItemInfo("E3.shice"));
    	sic.add(new SelectorItemInfo("E3.jielun"));
    	sic.add(new SelectorItemInfo("E2.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E2.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E2.xiangmuone"));
    	sic.add(new SelectorItemInfo("E2.kongone"));
    	sic.add(new SelectorItemInfo("E2.fuone"));
    	sic.add(new SelectorItemInfo("E2.kongtwo"));
    	sic.add(new SelectorItemInfo("E2.futwo"));
    	sic.add(new SelectorItemInfo("E2.kongthree"));
    	sic.add(new SelectorItemInfo("E2.futhree"));
    	sic.add(new SelectorItemInfo("E2.jieluntwo"));
    	sic.add(new SelectorItemInfo("E2.note"));
        sic.add(new SelectorItemInfo("shijian"));
        sic.add(new SelectorItemInfo("yingdu"));
    	sic.add(new SelectorItemInfo("E1.seq"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("E1.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("E1.xiangmuthree"));
    	sic.add(new SelectorItemInfo("E1.biaozhun"));
    	sic.add(new SelectorItemInfo("E1.shiji"));
    	sic.add(new SelectorItemInfo("E1.jielunthree"));
    	sic.add(new SelectorItemInfo("E1.beizhuone"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("zhuxiuqiangong.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("zhuxiuqiangong.id"));
        	sic.add(new SelectorItemInfo("zhuxiuqiangong.number"));
        	sic.add(new SelectorItemInfo("zhuxiuqiangong.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("zhuxiudiangong.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("zhuxiudiangong.id"));
        	sic.add(new SelectorItemInfo("zhuxiudiangong.number"));
        	sic.add(new SelectorItemInfo("zhuxiudiangong.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("jianyanyuan.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("jianyanyuan.id"));
        	sic.add(new SelectorItemInfo("jianyanyuan.number"));
        	sic.add(new SelectorItemInfo("jianyanyuan.name"));
		}
        sic.add(new SelectorItemInfo("idone"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionUnAudit_actionPerformed(e);
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionUnAudit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.client", "EquMaintBookEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.port.equipment.maintenance.client.EquMaintBookEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.port.equipment.maintenance.EquMaintBookFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo objectValue = new com.kingdee.eas.port.equipment.maintenance.EquMaintBookInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/port/equipment/maintenance/EquMaintBook";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.port.equipment.maintenance.app.EquMaintBookQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return kdtE3;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}