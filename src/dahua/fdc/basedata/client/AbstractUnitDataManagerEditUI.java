/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

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
public abstract class AbstractUnitDataManagerEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractUnitDataManagerEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizCompany;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAddress;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtTaxManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUnitNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contArtificialPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDutyPerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtSelfDirector;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtWatcher;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDirector;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtRightManager;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCompanyNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRightLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelRightLine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizOwnCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkHasUnit;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkHasBuildingProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateOwnCompanyBuild;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxControlType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxGetType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxBusinessType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtArtificialPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtDutyPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer8;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer9;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer10;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer11;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer12;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer13;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer14;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer15;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtLicenseNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtBusinessAddress;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBuild;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateRegister;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBusinessFrom;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker dateBusinessTo;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField ftxtRegisterCapital;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField ftxtRealCapital;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtUnitNumber;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer16;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer17;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer18;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer20;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer21;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPostNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFax;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtWeb;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLinkMobile;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDirectorAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDirectorDelLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelfDirectorAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelfDirectorDelLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWatcherAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWatcherDelLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaxAddLine;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaxDelLine;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer22;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtAreaTaxNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer23;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer24;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtCountryTaxNum;
    protected com.kingdee.bos.ctrl.swing.KDComboBox boxTaxPayerType;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel5;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView3;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView4;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile5;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView5;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile6;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView6;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile7;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView7;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile8;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView8;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile10;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView10;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile11;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView11;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel4;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel5;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel6;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel7;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel8;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel9;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel10;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel12;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel13;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel14;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator7;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator8;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator9;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator10;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator11;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator13;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator14;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator15;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator16;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator17;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator18;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator19;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer25;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtBankNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer26;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtAddress;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtLinkMan;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddPaymanager;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewPaymanager;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile9;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView9;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFile12;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnFileView12;
    protected com.kingdee.bos.ctrl.swing.KDList lstManagerProject;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel6;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel11;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel7;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnManagerProject;
    protected com.kingdee.eas.fdc.basedata.UnitDataManagerInfo editData = null;
    protected ActionAddRightLine actionAddRightLine = null;
    protected ActionDelRightLine actionDelRightLine = null;
    protected ActionAddTaxLine actionAddTaxLine = null;
    protected ActionDelTaxLine actionDelTaxLine = null;
    protected ActionAddDirectorLine actionAddDirectorLine = null;
    protected ActionDelDirectorLine actionDelDirectorLine = null;
    protected ActionAddSelfDirectorLine actionAddSelfDirectorLine = null;
    protected ActionDelSelfDirectorLine actionDelSelfDirectorLine = null;
    protected ActionAddWatcherLine actionAddWatcherLine = null;
    protected ActionDelWatcherLine actionDelWatcherLine = null;
    protected ActionAddPaymanager actionAddPaymanager = null;
    protected ActionViewPaymanager actionViewPaymanager = null;
    /**
     * output class constructor
     */
    public AbstractUnitDataManagerEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractUnitDataManagerEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionAddRightLine
        this.actionAddRightLine = new ActionAddRightLine(this);
        getActionManager().registerAction("actionAddRightLine", actionAddRightLine);
         this.actionAddRightLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelRightLine
        this.actionDelRightLine = new ActionDelRightLine(this);
        getActionManager().registerAction("actionDelRightLine", actionDelRightLine);
         this.actionDelRightLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddTaxLine
        this.actionAddTaxLine = new ActionAddTaxLine(this);
        getActionManager().registerAction("actionAddTaxLine", actionAddTaxLine);
         this.actionAddTaxLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelTaxLine
        this.actionDelTaxLine = new ActionDelTaxLine(this);
        getActionManager().registerAction("actionDelTaxLine", actionDelTaxLine);
         this.actionDelTaxLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddDirectorLine
        this.actionAddDirectorLine = new ActionAddDirectorLine(this);
        getActionManager().registerAction("actionAddDirectorLine", actionAddDirectorLine);
         this.actionAddDirectorLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelDirectorLine
        this.actionDelDirectorLine = new ActionDelDirectorLine(this);
        getActionManager().registerAction("actionDelDirectorLine", actionDelDirectorLine);
         this.actionDelDirectorLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddSelfDirectorLine
        this.actionAddSelfDirectorLine = new ActionAddSelfDirectorLine(this);
        getActionManager().registerAction("actionAddSelfDirectorLine", actionAddSelfDirectorLine);
         this.actionAddSelfDirectorLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelSelfDirectorLine
        this.actionDelSelfDirectorLine = new ActionDelSelfDirectorLine(this);
        getActionManager().registerAction("actionDelSelfDirectorLine", actionDelSelfDirectorLine);
         this.actionDelSelfDirectorLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddWatcherLine
        this.actionAddWatcherLine = new ActionAddWatcherLine(this);
        getActionManager().registerAction("actionAddWatcherLine", actionAddWatcherLine);
         this.actionAddWatcherLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionDelWatcherLine
        this.actionDelWatcherLine = new ActionDelWatcherLine(this);
        getActionManager().registerAction("actionDelWatcherLine", actionDelWatcherLine);
         this.actionDelWatcherLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAddPaymanager
        this.actionAddPaymanager = new ActionAddPaymanager(this);
        getActionManager().registerAction("actionAddPaymanager", actionAddPaymanager);
         this.actionAddPaymanager.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewPaymanager
        this.actionViewPaymanager = new ActionViewPaymanager(this);
        getActionManager().registerAction("actionViewPaymanager", actionViewPaymanager);
         this.actionViewPaymanager.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel4 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contAddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtTaxManager = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contUnitNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contArtificialPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDutyPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kdtSelfDirector = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtWatcher = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtDirector = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtRightManager = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCompanyNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnAddRightLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelRightLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.bizOwnCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkHasUnit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkHasBuildingProject = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.dateOwnCompanyBuild = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.boxControlType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.boxGetType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.boxBusinessType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtArtificialPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtDutyPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer8 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer9 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer10 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer11 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer12 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer13 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer14 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer15 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLicenseNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtBusinessAddress = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.dateBuild = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateRegister = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateBusinessFrom = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.dateBusinessTo = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.ftxtRegisterCapital = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.ftxtRealCapital = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtUnitNumber = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabelContainer16 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer17 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer18 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer19 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer20 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer21 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtPostNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtFax = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtWeb = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtLinkMobile = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnDirectorAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDirectorDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSelfDirectorAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSelfDirectorDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWatcherAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWatcherDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTaxAddLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTaxDelLine = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer22 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtAreaTaxNum = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer23 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer24 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCountryTaxNum = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.boxTaxPayerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDPanel5 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnFile1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView1 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView2 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile3 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView3 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile4 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView4 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile5 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView5 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile6 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView6 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile7 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView7 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile8 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView8 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile10 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView10 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile11 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView11 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel6 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel7 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel8 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel9 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel10 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel12 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel13 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel14 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator7 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator8 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator9 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator10 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator11 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator13 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator14 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator15 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator16 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator17 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator18 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDSeparator19 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.kDLabelContainer25 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBankNum = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabelContainer26 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtBank = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtAddress = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtLinkMan = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.btnAddPaymanager = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewPaymanager = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile9 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView9 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFile12 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnFileView12 = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.lstManagerProject = new com.kingdee.bos.ctrl.swing.KDList();
        this.kDPanel6 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDLabel11 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDPanel7 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.btnManagerProject = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contCompany.setName("contCompany");
        this.bizCompany.setName("bizCompany");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDPanel3.setName("kDPanel3");
        this.kDPanel4.setName("kDPanel4");
        this.contAddress.setName("contAddress");
        this.kdtTaxManager.setName("kdtTaxManager");
        this.contUnitNumber.setName("contUnitNumber");
        this.contArtificialPerson.setName("contArtificialPerson");
        this.contDutyPerson.setName("contDutyPerson");
        this.kdtSelfDirector.setName("kdtSelfDirector");
        this.kdtWatcher.setName("kdtWatcher");
        this.kdtDirector.setName("kdtDirector");
        this.kdtRightManager.setName("kdtRightManager");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtCompanyNumber.setName("txtCompanyNumber");
        this.btnAddRightLine.setName("btnAddRightLine");
        this.btnDelRightLine.setName("btnDelRightLine");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.bizOwnCompany.setName("bizOwnCompany");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.chkHasUnit.setName("chkHasUnit");
        this.chkHasBuildingProject.setName("chkHasBuildingProject");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDSeparator5.setName("kDSeparator5");
        this.dateOwnCompanyBuild.setName("dateOwnCompanyBuild");
        this.boxControlType.setName("boxControlType");
        this.boxGetType.setName("boxGetType");
        this.boxBusinessType.setName("boxBusinessType");
        this.txtArtificialPerson.setName("txtArtificialPerson");
        this.txtDutyPerson.setName("txtDutyPerson");
        this.kDLabelContainer8.setName("kDLabelContainer8");
        this.kDLabelContainer9.setName("kDLabelContainer9");
        this.kDLabelContainer10.setName("kDLabelContainer10");
        this.kDLabelContainer11.setName("kDLabelContainer11");
        this.kDLabelContainer12.setName("kDLabelContainer12");
        this.kDLabelContainer13.setName("kDLabelContainer13");
        this.kDLabelContainer14.setName("kDLabelContainer14");
        this.kDLabelContainer15.setName("kDLabelContainer15");
        this.txtLicenseNumber.setName("txtLicenseNumber");
        this.txtBusinessAddress.setName("txtBusinessAddress");
        this.dateBuild.setName("dateBuild");
        this.dateRegister.setName("dateRegister");
        this.dateBusinessFrom.setName("dateBusinessFrom");
        this.dateBusinessTo.setName("dateBusinessTo");
        this.ftxtRegisterCapital.setName("ftxtRegisterCapital");
        this.ftxtRealCapital.setName("ftxtRealCapital");
        this.txtUnitNumber.setName("txtUnitNumber");
        this.kDSeparator6.setName("kDSeparator6");
        this.kDLabelContainer16.setName("kDLabelContainer16");
        this.kDLabelContainer17.setName("kDLabelContainer17");
        this.kDLabelContainer18.setName("kDLabelContainer18");
        this.kDLabelContainer19.setName("kDLabelContainer19");
        this.kDLabelContainer20.setName("kDLabelContainer20");
        this.kDLabelContainer21.setName("kDLabelContainer21");
        this.txtDescription.setName("txtDescription");
        this.kDLabel1.setName("kDLabel1");
        this.txtPostNumber.setName("txtPostNumber");
        this.txtTel.setName("txtTel");
        this.txtFax.setName("txtFax");
        this.txtWeb.setName("txtWeb");
        this.txtLinkMobile.setName("txtLinkMobile");
        this.btnDirectorAddLine.setName("btnDirectorAddLine");
        this.btnDirectorDelLine.setName("btnDirectorDelLine");
        this.btnSelfDirectorAddLine.setName("btnSelfDirectorAddLine");
        this.btnSelfDirectorDelLine.setName("btnSelfDirectorDelLine");
        this.btnWatcherAddLine.setName("btnWatcherAddLine");
        this.btnWatcherDelLine.setName("btnWatcherDelLine");
        this.btnTaxAddLine.setName("btnTaxAddLine");
        this.btnTaxDelLine.setName("btnTaxDelLine");
        this.kDLabelContainer22.setName("kDLabelContainer22");
        this.txtAreaTaxNum.setName("txtAreaTaxNum");
        this.kDLabelContainer23.setName("kDLabelContainer23");
        this.kDLabelContainer24.setName("kDLabelContainer24");
        this.txtCountryTaxNum.setName("txtCountryTaxNum");
        this.boxTaxPayerType.setName("boxTaxPayerType");
        this.kDPanel5.setName("kDPanel5");
        this.btnFile1.setName("btnFile1");
        this.btnFileView1.setName("btnFileView1");
        this.btnFile2.setName("btnFile2");
        this.btnFileView2.setName("btnFileView2");
        this.btnFile3.setName("btnFile3");
        this.btnFileView3.setName("btnFileView3");
        this.btnFile4.setName("btnFile4");
        this.btnFileView4.setName("btnFileView4");
        this.btnFile5.setName("btnFile5");
        this.btnFileView5.setName("btnFileView5");
        this.btnFile6.setName("btnFile6");
        this.btnFileView6.setName("btnFileView6");
        this.btnFile7.setName("btnFile7");
        this.btnFileView7.setName("btnFileView7");
        this.btnFile8.setName("btnFile8");
        this.btnFileView8.setName("btnFileView8");
        this.btnFile10.setName("btnFile10");
        this.btnFileView10.setName("btnFileView10");
        this.btnFile11.setName("btnFile11");
        this.btnFileView11.setName("btnFileView11");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.kDLabel4.setName("kDLabel4");
        this.kDLabel5.setName("kDLabel5");
        this.kDLabel6.setName("kDLabel6");
        this.kDLabel7.setName("kDLabel7");
        this.kDLabel8.setName("kDLabel8");
        this.kDLabel9.setName("kDLabel9");
        this.kDLabel10.setName("kDLabel10");
        this.kDLabel12.setName("kDLabel12");
        this.kDLabel13.setName("kDLabel13");
        this.kDLabel14.setName("kDLabel14");
        this.kDSeparator7.setName("kDSeparator7");
        this.kDSeparator8.setName("kDSeparator8");
        this.kDSeparator9.setName("kDSeparator9");
        this.kDSeparator10.setName("kDSeparator10");
        this.kDSeparator11.setName("kDSeparator11");
        this.kDSeparator13.setName("kDSeparator13");
        this.kDSeparator14.setName("kDSeparator14");
        this.kDSeparator15.setName("kDSeparator15");
        this.kDSeparator16.setName("kDSeparator16");
        this.kDSeparator17.setName("kDSeparator17");
        this.kDSeparator18.setName("kDSeparator18");
        this.kDSeparator19.setName("kDSeparator19");
        this.kDLabelContainer25.setName("kDLabelContainer25");
        this.txtBankNum.setName("txtBankNum");
        this.kDLabelContainer26.setName("kDLabelContainer26");
        this.txtBank.setName("txtBank");
        this.txtAddress.setName("txtAddress");
        this.txtLinkMan.setName("txtLinkMan");
        this.btnAddPaymanager.setName("btnAddPaymanager");
        this.btnViewPaymanager.setName("btnViewPaymanager");
        this.btnFile9.setName("btnFile9");
        this.btnFileView9.setName("btnFileView9");
        this.btnFile12.setName("btnFile12");
        this.btnFileView12.setName("btnFileView12");
        this.lstManagerProject.setName("lstManagerProject");
        this.kDPanel6.setName("kDPanel6");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDLabel11.setName("kDLabel11");
        this.kDPanel7.setName("kDPanel7");
        this.btnManagerProject.setName("btnManagerProject");
        // CoreUI
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(100);		
        this.contCompany.setBoundLabelUnderline(true);
        // bizCompany		
        this.bizCompany.setRequired(true);
        this.bizCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDTabbedPane1
        // kDPanel1
        // kDPanel2
        // kDPanel3
        // kDPanel4
        // contAddress		
        this.contAddress.setBoundLabelText(resHelper.getString("contAddress.boundLabelText"));		
        this.contAddress.setBoundLabelLength(100);		
        this.contAddress.setBoundLabelUnderline(true);
        // kdtTaxManager		
        this.kdtTaxManager.setFormatXml(resHelper.getString("kdtTaxManager.formatXml"));
        this.kdtTaxManager.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtTaxManager_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contUnitNumber		
        this.contUnitNumber.setBoundLabelText(resHelper.getString("contUnitNumber.boundLabelText"));		
        this.contUnitNumber.setBoundLabelLength(100);		
        this.contUnitNumber.setBoundLabelUnderline(true);
        // contArtificialPerson		
        this.contArtificialPerson.setBoundLabelText(resHelper.getString("contArtificialPerson.boundLabelText"));		
        this.contArtificialPerson.setBoundLabelLength(100);		
        this.contArtificialPerson.setBoundLabelUnderline(true);
        // contDutyPerson		
        this.contDutyPerson.setBoundLabelText(resHelper.getString("contDutyPerson.boundLabelText"));		
        this.contDutyPerson.setBoundLabelLength(100);		
        this.contDutyPerson.setBoundLabelUnderline(true);
        // kdtSelfDirector		
        this.kdtSelfDirector.setFormatXml(resHelper.getString("kdtSelfDirector.formatXml"));

        

        // kdtWatcher		
        this.kdtWatcher.setFormatXml(resHelper.getString("kdtWatcher.formatXml"));

        

        // kdtDirector		
        this.kdtDirector.setFormatXml(resHelper.getString("kdtDirector.formatXml"));

        

        // kdtRightManager		
        this.kdtRightManager.setFormatXml(resHelper.getString("kdtRightManager.formatXml"));
        this.kdtRightManager.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtRightManager_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // txtCompanyNumber		
        this.txtCompanyNumber.setText(resHelper.getString("txtCompanyNumber.text"));		
        this.txtCompanyNumber.setEnabled(false);
        // btnAddRightLine
        this.btnAddRightLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRightLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRightLine.setText(resHelper.getString("btnAddRightLine.text"));		
        this.btnAddRightLine.setToolTipText(resHelper.getString("btnAddRightLine.toolTipText"));
        // btnDelRightLine
        this.btnDelRightLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelRightLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDelRightLine.setText(resHelper.getString("btnDelRightLine.text"));		
        this.btnDelRightLine.setToolTipText(resHelper.getString("btnDelRightLine.toolTipText"));
        this.btnDelRightLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    wbtProductDelLine_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // bizOwnCompany
        this.bizOwnCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizOwnCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelLength(100);		
        this.kDLabelContainer6.setBoundLabelUnderline(true);
        // chkHasUnit		
        this.chkHasUnit.setText(resHelper.getString("chkHasUnit.text"));
        // chkHasBuildingProject		
        this.chkHasBuildingProject.setText(resHelper.getString("chkHasBuildingProject.text"));
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setBoundLabelUnderline(true);
        // kDSeparator5
        // dateOwnCompanyBuild
        // boxControlType		
        this.boxControlType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ControlTypeEnum").toArray());
        // boxGetType		
        this.boxGetType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.GetTypeEnum").toArray());
        this.boxGetType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    boxGetType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // boxBusinessType		
        this.boxBusinessType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.BusinessTypeEnum").toArray());
        // txtArtificialPerson		
        this.txtArtificialPerson.setMaxLength(80);
        // txtDutyPerson		
        this.txtDutyPerson.setMaxLength(80);
        // kDLabelContainer8		
        this.kDLabelContainer8.setBoundLabelText(resHelper.getString("kDLabelContainer8.boundLabelText"));		
        this.kDLabelContainer8.setBoundLabelLength(100);		
        this.kDLabelContainer8.setBoundLabelUnderline(true);
        // kDLabelContainer9		
        this.kDLabelContainer9.setBoundLabelText(resHelper.getString("kDLabelContainer9.boundLabelText"));		
        this.kDLabelContainer9.setBoundLabelLength(100);		
        this.kDLabelContainer9.setBoundLabelUnderline(true);
        // kDLabelContainer10		
        this.kDLabelContainer10.setBoundLabelText(resHelper.getString("kDLabelContainer10.boundLabelText"));		
        this.kDLabelContainer10.setBoundLabelLength(100);		
        this.kDLabelContainer10.setBoundLabelUnderline(true);
        // kDLabelContainer11		
        this.kDLabelContainer11.setBoundLabelText(resHelper.getString("kDLabelContainer11.boundLabelText"));		
        this.kDLabelContainer11.setBoundLabelLength(100);		
        this.kDLabelContainer11.setBoundLabelUnderline(true);
        // kDLabelContainer12		
        this.kDLabelContainer12.setBoundLabelText(resHelper.getString("kDLabelContainer12.boundLabelText"));		
        this.kDLabelContainer12.setBoundLabelLength(100);		
        this.kDLabelContainer12.setBoundLabelUnderline(true);
        // kDLabelContainer13		
        this.kDLabelContainer13.setBoundLabelText(resHelper.getString("kDLabelContainer13.boundLabelText"));		
        this.kDLabelContainer13.setBoundLabelLength(100);		
        this.kDLabelContainer13.setBoundLabelUnderline(true);
        // kDLabelContainer14		
        this.kDLabelContainer14.setBoundLabelText(resHelper.getString("kDLabelContainer14.boundLabelText"));		
        this.kDLabelContainer14.setBoundLabelLength(100);		
        this.kDLabelContainer14.setBoundLabelUnderline(true);
        // kDLabelContainer15		
        this.kDLabelContainer15.setBoundLabelText(resHelper.getString("kDLabelContainer15.boundLabelText"));		
        this.kDLabelContainer15.setBoundLabelLength(100);		
        this.kDLabelContainer15.setBoundLabelUnderline(true);
        // txtLicenseNumber		
        this.txtLicenseNumber.setMaxLength(80);
        // txtBusinessAddress		
        this.txtBusinessAddress.setMaxLength(80);
        // dateBuild
        // dateRegister
        // dateBusinessFrom
        // dateBusinessTo
        // ftxtRegisterCapital		
        this.ftxtRegisterCapital.setDataType(1);		
        this.ftxtRegisterCapital.setPrecision(2);
        // ftxtRealCapital		
        this.ftxtRealCapital.setDataType(1);		
        this.ftxtRealCapital.setPrecision(2);
        // txtUnitNumber		
        this.txtUnitNumber.setMaxLength(80);
        // kDSeparator6
        // kDLabelContainer16		
        this.kDLabelContainer16.setBoundLabelText(resHelper.getString("kDLabelContainer16.boundLabelText"));		
        this.kDLabelContainer16.setBoundLabelLength(100);		
        this.kDLabelContainer16.setBoundLabelUnderline(true);
        // kDLabelContainer17		
        this.kDLabelContainer17.setBoundLabelText(resHelper.getString("kDLabelContainer17.boundLabelText"));		
        this.kDLabelContainer17.setBoundLabelLength(100);		
        this.kDLabelContainer17.setBoundLabelUnderline(true);
        // kDLabelContainer18		
        this.kDLabelContainer18.setBoundLabelText(resHelper.getString("kDLabelContainer18.boundLabelText"));		
        this.kDLabelContainer18.setBoundLabelLength(100);		
        this.kDLabelContainer18.setBoundLabelUnderline(true);
        // kDLabelContainer19		
        this.kDLabelContainer19.setBoundLabelText(resHelper.getString("kDLabelContainer19.boundLabelText"));		
        this.kDLabelContainer19.setBoundLabelLength(100);		
        this.kDLabelContainer19.setBoundLabelUnderline(true);
        // kDLabelContainer20		
        this.kDLabelContainer20.setBoundLabelText(resHelper.getString("kDLabelContainer20.boundLabelText"));		
        this.kDLabelContainer20.setBoundLabelLength(100);		
        this.kDLabelContainer20.setBoundLabelUnderline(true);
        // kDLabelContainer21		
        this.kDLabelContainer21.setBoundLabelText(resHelper.getString("kDLabelContainer21.boundLabelText"));		
        this.kDLabelContainer21.setBoundLabelLength(100);		
        this.kDLabelContainer21.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // txtPostNumber		
        this.txtPostNumber.setText(resHelper.getString("txtPostNumber.text"));		
        this.txtPostNumber.setMaxLength(80);
        // txtTel		
        this.txtTel.setText(resHelper.getString("txtTel.text"));		
        this.txtTel.setMaxLength(80);
        // txtFax		
        this.txtFax.setText(resHelper.getString("txtFax.text"));		
        this.txtFax.setMaxLength(80);
        // txtWeb		
        this.txtWeb.setText(resHelper.getString("txtWeb.text"));		
        this.txtWeb.setMaxLength(80);
        // txtLinkMobile		
        this.txtLinkMobile.setText(resHelper.getString("txtLinkMobile.text"));		
        this.txtLinkMobile.setMaxLength(80);
        // btnDirectorAddLine
        this.btnDirectorAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddDirectorLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDirectorAddLine.setText(resHelper.getString("btnDirectorAddLine.text"));
        // btnDirectorDelLine
        this.btnDirectorDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelDirectorLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnDirectorDelLine.setText(resHelper.getString("btnDirectorDelLine.text"));
        // btnSelfDirectorAddLine
        this.btnSelfDirectorAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddSelfDirectorLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelfDirectorAddLine.setText(resHelper.getString("btnSelfDirectorAddLine.text"));
        // btnSelfDirectorDelLine
        this.btnSelfDirectorDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelSelfDirectorLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSelfDirectorDelLine.setText(resHelper.getString("btnSelfDirectorDelLine.text"));
        // btnWatcherAddLine
        this.btnWatcherAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddWatcherLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWatcherAddLine.setText(resHelper.getString("btnWatcherAddLine.text"));
        // btnWatcherDelLine
        this.btnWatcherDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelWatcherLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWatcherDelLine.setText(resHelper.getString("btnWatcherDelLine.text"));
        // btnTaxAddLine
        this.btnTaxAddLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddTaxLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaxAddLine.setText(resHelper.getString("btnTaxAddLine.text"));
        // btnTaxDelLine
        this.btnTaxDelLine.setAction((IItemAction)ActionProxyFactory.getProxy(actionDelTaxLine, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaxDelLine.setText(resHelper.getString("btnTaxDelLine.text"));
        // kDLabelContainer22		
        this.kDLabelContainer22.setBoundLabelText(resHelper.getString("kDLabelContainer22.boundLabelText"));		
        this.kDLabelContainer22.setBoundLabelLength(100);		
        this.kDLabelContainer22.setBoundLabelUnderline(true);
        // txtAreaTaxNum		
        this.txtAreaTaxNum.setMaxLength(80);
        // kDLabelContainer23		
        this.kDLabelContainer23.setBoundLabelText(resHelper.getString("kDLabelContainer23.boundLabelText"));		
        this.kDLabelContainer23.setBoundLabelLength(100);		
        this.kDLabelContainer23.setBoundLabelUnderline(true);
        // kDLabelContainer24		
        this.kDLabelContainer24.setBoundLabelText(resHelper.getString("kDLabelContainer24.boundLabelText"));		
        this.kDLabelContainer24.setBoundLabelLength(100);		
        this.kDLabelContainer24.setBoundLabelUnderline(true);
        // txtCountryTaxNum		
        this.txtCountryTaxNum.setMaxLength(80);
        // boxTaxPayerType		
        this.boxTaxPayerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.TaxPayerTypeEnum").toArray());
        // kDPanel5
        // btnFile1		
        this.btnFile1.setText(resHelper.getString("btnFile1.text"));
        this.btnFile1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile1_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView1		
        this.btnFileView1.setText(resHelper.getString("btnFileView1.text"));
        this.btnFileView1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView1_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile2		
        this.btnFile2.setText(resHelper.getString("btnFile2.text"));
        this.btnFile2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile2_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile2_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView2		
        this.btnFileView2.setText(resHelper.getString("btnFileView2.text"));
        this.btnFileView2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView2_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile3		
        this.btnFile3.setText(resHelper.getString("btnFile3.text"));
        this.btnFile3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile3_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile3_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView3		
        this.btnFileView3.setText(resHelper.getString("btnFileView3.text"));
        this.btnFileView3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView3_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile4		
        this.btnFile4.setText(resHelper.getString("btnFile4.text"));
        this.btnFile4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile4_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile4_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView4		
        this.btnFileView4.setText(resHelper.getString("btnFileView4.text"));
        this.btnFileView4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView4_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile5		
        this.btnFile5.setText(resHelper.getString("btnFile5.text"));
        this.btnFile5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile5_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile5_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView5		
        this.btnFileView5.setText(resHelper.getString("btnFileView5.text"));
        this.btnFileView5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView5_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile6		
        this.btnFile6.setText(resHelper.getString("btnFile6.text"));
        this.btnFile6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile6_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile6_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView6		
        this.btnFileView6.setText(resHelper.getString("btnFileView6.text"));
        this.btnFileView6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView6_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile7		
        this.btnFile7.setText(resHelper.getString("btnFile7.text"));
        this.btnFile7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile7_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile7_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView7		
        this.btnFileView7.setText(resHelper.getString("btnFileView7.text"));
        this.btnFileView7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView7_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile8		
        this.btnFile8.setText(resHelper.getString("btnFile8.text"));
        this.btnFile8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile8_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile8_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView8		
        this.btnFileView8.setText(resHelper.getString("btnFileView8.text"));
        this.btnFileView8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView8_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile10		
        this.btnFile10.setText(resHelper.getString("btnFile10.text"));
        this.btnFile10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile10_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile10_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView10		
        this.btnFileView10.setText(resHelper.getString("btnFileView10.text"));
        this.btnFileView10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView10_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile11		
        this.btnFile11.setText(resHelper.getString("btnFile11.text"));
        this.btnFile11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile11_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile11_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView11		
        this.btnFileView11.setText(resHelper.getString("btnFileView11.text"));
        this.btnFileView11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView11_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // kDLabel4		
        this.kDLabel4.setText(resHelper.getString("kDLabel4.text"));
        // kDLabel5		
        this.kDLabel5.setText(resHelper.getString("kDLabel5.text"));
        // kDLabel6		
        this.kDLabel6.setText(resHelper.getString("kDLabel6.text"));		
        this.kDLabel6.setVisible(false);
        // kDLabel7		
        this.kDLabel7.setText(resHelper.getString("kDLabel7.text"));
        // kDLabel8		
        this.kDLabel8.setText(resHelper.getString("kDLabel8.text"));
        // kDLabel9		
        this.kDLabel9.setText(resHelper.getString("kDLabel9.text"));
        // kDLabel10		
        this.kDLabel10.setText(resHelper.getString("kDLabel10.text"));
        // kDLabel12		
        this.kDLabel12.setText(resHelper.getString("kDLabel12.text"));
        // kDLabel13		
        this.kDLabel13.setText(resHelper.getString("kDLabel13.text"));		
        this.kDLabel13.setVisible(false);
        // kDLabel14		
        this.kDLabel14.setText(resHelper.getString("kDLabel14.text"));
        // kDSeparator7
        // kDSeparator8
        // kDSeparator9
        // kDSeparator10
        // kDSeparator11
        // kDSeparator13
        // kDSeparator14
        // kDSeparator15		
        this.kDSeparator15.setVisible(false);
        // kDSeparator16
        // kDSeparator17
        // kDSeparator18		
        this.kDSeparator18.setVisible(false);
        // kDSeparator19
        // kDLabelContainer25		
        this.kDLabelContainer25.setBoundLabelText(resHelper.getString("kDLabelContainer25.boundLabelText"));		
        this.kDLabelContainer25.setBoundLabelLength(100);		
        this.kDLabelContainer25.setBoundLabelUnderline(true);
        // txtBankNum		
        this.txtBankNum.setMaxLength(80);
        // kDLabelContainer26		
        this.kDLabelContainer26.setBoundLabelText(resHelper.getString("kDLabelContainer26.boundLabelText"));		
        this.kDLabelContainer26.setBoundLabelLength(100);		
        this.kDLabelContainer26.setBoundLabelUnderline(true);
        // txtBank		
        this.txtBank.setMaxLength(80);
        // txtAddress		
        this.txtAddress.setMaxLength(80);
        // txtLinkMan		
        this.txtLinkMan.setMaxLength(80);
        // btnAddPaymanager
        this.btnAddPaymanager.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddPaymanager, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddPaymanager.setText(resHelper.getString("btnAddPaymanager.text"));
        this.btnAddPaymanager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnAddPaymanager_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnViewPaymanager
        this.btnViewPaymanager.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewPaymanager, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewPaymanager.setText(resHelper.getString("btnViewPaymanager.text"));
        this.btnViewPaymanager.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnViewPaymanager_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFile9		
        this.btnFile9.setText(resHelper.getString("btnFile9.text"));
        this.btnFile9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile9_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile9_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView9		
        this.btnFileView9.setText(resHelper.getString("btnFileView9.text"));
        this.btnFileView9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView9_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnFile12		
        this.btnFile12.setText(resHelper.getString("btnFile12.text"));
        this.btnFile12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFile12_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.btnFile12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    btnFile12_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // btnFileView12		
        this.btnFileView12.setText(resHelper.getString("btnFileView12.text"));
        this.btnFileView12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnFileView12_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // lstManagerProject
        // kDPanel6
        // kDScrollPane1
        // kDLabel11		
        this.kDLabel11.setText(resHelper.getString("kDLabel11.text"));		
        this.kDLabel11.setVerticalAlignment(1);		
        this.kDLabel11.setPreferredSize(new Dimension(95,0));
        // kDPanel7		
        this.kDPanel7.setPreferredSize(new Dimension(100,10));
        // btnManagerProject		
        this.btnManagerProject.setText(resHelper.getString("btnManagerProject.text"));
        this.btnManagerProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnManagerProject_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 600, 500));
        this.setLayout(null);
        contCompany.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contCompany, null);
        kDTabbedPane1.setBounds(new Rectangle(10, 40, 578, 451));
        this.add(kDTabbedPane1, null);
        kDLabelContainer1.setBounds(new Rectangle(314, 10, 270, 19));
        this.add(kDLabelContainer1, null);
        //contCompany
        contCompany.setBoundEditor(bizCompany);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        kDTabbedPane1.add(kDPanel3, resHelper.getString("kDPanel3.constraints"));
        kDTabbedPane1.add(kDPanel4, resHelper.getString("kDPanel4.constraints"));
        kDTabbedPane1.add(kDPanel5, resHelper.getString("kDPanel5.constraints"));
        //kDPanel1
        kDPanel1.setLayout(null);        contAddress.setBounds(new Rectangle(10, 10, 270, 19));
        kDPanel1.add(contAddress, null);
        kDLabelContainer16.setBounds(new Rectangle(10, 130, 270, 19));
        kDPanel1.add(kDLabelContainer16, null);
        kDLabelContainer17.setBounds(new Rectangle(10, 70, 270, 19));
        kDPanel1.add(kDLabelContainer17, null);
        kDLabelContainer18.setBounds(new Rectangle(10, 40, 270, 19));
        kDPanel1.add(kDLabelContainer18, null);
        kDLabelContainer19.setBounds(new Rectangle(10, 160, 270, 19));
        kDPanel1.add(kDLabelContainer19, null);
        kDLabelContainer20.setBounds(new Rectangle(10, 190, 270, 19));
        kDPanel1.add(kDLabelContainer20, null);
        kDLabelContainer21.setBounds(new Rectangle(10, 100, 270, 19));
        kDPanel1.add(kDLabelContainer21, null);
        txtDescription.setBounds(new Rectangle(10, 252, 344, 94));
        kDPanel1.add(txtDescription, null);
        kDLabel1.setBounds(new Rectangle(10, 225, 100, 19));
        kDPanel1.add(kDLabel1, null);
        //contAddress
        contAddress.setBoundEditor(txtAddress);
        //kDLabelContainer16
        kDLabelContainer16.setBoundEditor(txtWeb);
        //kDLabelContainer17
        kDLabelContainer17.setBoundEditor(txtTel);
        //kDLabelContainer18
        kDLabelContainer18.setBoundEditor(txtPostNumber);
        //kDLabelContainer19
        kDLabelContainer19.setBoundEditor(txtLinkMan);
        //kDLabelContainer20
        kDLabelContainer20.setBoundEditor(txtLinkMobile);
        //kDLabelContainer21
        kDLabelContainer21.setBoundEditor(txtFax);
        //kDPanel2
        kDPanel2.setLayout(null);        kdtTaxManager.setBounds(new Rectangle(299, 204, 261, 204));
        kDPanel2.add(kdtTaxManager, null);
        contUnitNumber.setBounds(new Rectangle(10, 37, 270, 19));
        kDPanel2.add(contUnitNumber, null);
        kDLabelContainer8.setBounds(new Rectangle(10, 118, 270, 19));
        kDPanel2.add(kDLabelContainer8, null);
        kDLabelContainer9.setBounds(new Rectangle(10, 91, 270, 19));
        kDPanel2.add(kDLabelContainer9, null);
        kDLabelContainer10.setBounds(new Rectangle(292, 145, 270, 19));
        kDPanel2.add(kDLabelContainer10, null);
        kDLabelContainer11.setBounds(new Rectangle(10, 64, 270, 19));
        kDPanel2.add(kDLabelContainer11, null);
        kDLabelContainer12.setBounds(new Rectangle(10, 10, 270, 19));
        kDPanel2.add(kDLabelContainer12, null);
        kDLabelContainer13.setBounds(new Rectangle(10, 145, 270, 19));
        kDPanel2.add(kDLabelContainer13, null);
        kDLabelContainer14.setBounds(new Rectangle(292, 118, 270, 19));
        kDPanel2.add(kDLabelContainer14, null);
        kDLabelContainer15.setBounds(new Rectangle(292, 91, 270, 19));
        kDPanel2.add(kDLabelContainer15, null);
        kDSeparator6.setBounds(new Rectangle(10, 177, 552, 8));
        kDPanel2.add(kDSeparator6, null);
        btnTaxAddLine.setBounds(new Rectangle(511, 181, 22, 19));
        kDPanel2.add(btnTaxAddLine, null);
        btnTaxDelLine.setBounds(new Rectangle(536, 181, 22, 19));
        kDPanel2.add(btnTaxDelLine, null);
        kDLabelContainer22.setBounds(new Rectangle(10, 237, 270, 19));
        kDPanel2.add(kDLabelContainer22, null);
        kDLabelContainer23.setBounds(new Rectangle(10, 267, 270, 19));
        kDPanel2.add(kDLabelContainer23, null);
        kDLabelContainer24.setBounds(new Rectangle(10, 207, 270, 19));
        kDPanel2.add(kDLabelContainer24, null);
        kDLabelContainer25.setBounds(new Rectangle(10, 327, 270, 19));
        kDPanel2.add(kDLabelContainer25, null);
        kDLabelContainer26.setBounds(new Rectangle(10, 297, 270, 19));
        kDPanel2.add(kDLabelContainer26, null);
        btnFile9.setBounds(new Rectangle(315, 11, 22, 19));
        kDPanel2.add(btnFile9, null);
        btnFileView9.setBounds(new Rectangle(391, 11, 22, 19));
        kDPanel2.add(btnFileView9, null);
        btnFile12.setBounds(new Rectangle(315, 37, 22, 19));
        kDPanel2.add(btnFile12, null);
        btnFileView12.setBounds(new Rectangle(391, 37, 22, 19));
        kDPanel2.add(btnFileView12, null);
        //contUnitNumber
        contUnitNumber.setBoundEditor(txtUnitNumber);
        //kDLabelContainer8
        kDLabelContainer8.setBoundEditor(dateBuild);
        //kDLabelContainer9
        kDLabelContainer9.setBoundEditor(ftxtRegisterCapital);
        //kDLabelContainer10
        kDLabelContainer10.setBoundEditor(dateBusinessTo);
        //kDLabelContainer11
        kDLabelContainer11.setBoundEditor(txtBusinessAddress);
        //kDLabelContainer12
        kDLabelContainer12.setBoundEditor(txtLicenseNumber);
        //kDLabelContainer13
        kDLabelContainer13.setBoundEditor(dateBusinessFrom);
        //kDLabelContainer14
        kDLabelContainer14.setBoundEditor(dateRegister);
        //kDLabelContainer15
        kDLabelContainer15.setBoundEditor(ftxtRealCapital);
        //kDLabelContainer22
        kDLabelContainer22.setBoundEditor(txtAreaTaxNum);
        //kDLabelContainer23
        kDLabelContainer23.setBoundEditor(boxTaxPayerType);
        //kDLabelContainer24
        kDLabelContainer24.setBoundEditor(txtCountryTaxNum);
        //kDLabelContainer25
        kDLabelContainer25.setBoundEditor(txtBankNum);
        //kDLabelContainer26
        kDLabelContainer26.setBoundEditor(txtBank);
        //kDPanel3
        kDPanel3.setLayout(null);        contArtificialPerson.setBounds(new Rectangle(10, 163, 270, 19));
        kDPanel3.add(contArtificialPerson, null);
        contDutyPerson.setBounds(new Rectangle(293, 163, 270, 19));
        kDPanel3.add(contDutyPerson, null);
        kdtSelfDirector.setBounds(new Rectangle(193, 229, 150, 169));
        kDPanel3.add(kdtSelfDirector, null);
        kdtWatcher.setBounds(new Rectangle(377, 229, 150, 169));
        kDPanel3.add(kdtWatcher, null);
        kdtDirector.setBounds(new Rectangle(10, 229, 150, 169));
        kDPanel3.add(kdtDirector, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 10, 270, 19));
        kDPanel3.add(kDLabelContainer2, null);
        kDLabelContainer3.setBounds(new Rectangle(293, 10, 270, 19));
        kDPanel3.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(293, 40, 270, 19));
        kDPanel3.add(kDLabelContainer4, null);
        kDLabelContainer6.setBounds(new Rectangle(9, 127, 270, 19));
        kDPanel3.add(kDLabelContainer6, null);
        chkHasUnit.setBounds(new Rectangle(294, 127, 132, 19));
        kDPanel3.add(chkHasUnit, null);
        chkHasBuildingProject.setBounds(new Rectangle(437, 127, 123, 19));
        kDPanel3.add(chkHasBuildingProject, null);
        kDLabelContainer7.setBounds(new Rectangle(10, 40, 270, 19));
        kDPanel3.add(kDLabelContainer7, null);
        kDSeparator5.setBounds(new Rectangle(10, 152, 552, 8));
        kDPanel3.add(kDSeparator5, null);
        btnDirectorAddLine.setBounds(new Rectangle(112, 209, 22, 19));
        kDPanel3.add(btnDirectorAddLine, null);
        btnDirectorDelLine.setBounds(new Rectangle(137, 209, 22, 19));
        kDPanel3.add(btnDirectorDelLine, null);
        btnSelfDirectorAddLine.setBounds(new Rectangle(296, 209, 22, 19));
        kDPanel3.add(btnSelfDirectorAddLine, null);
        btnSelfDirectorDelLine.setBounds(new Rectangle(320, 209, 22, 19));
        kDPanel3.add(btnSelfDirectorDelLine, null);
        btnWatcherAddLine.setBounds(new Rectangle(479, 209, 22, 19));
        kDPanel3.add(btnWatcherAddLine, null);
        btnWatcherDelLine.setBounds(new Rectangle(504, 209, 22, 19));
        kDPanel3.add(btnWatcherDelLine, null);
        kDPanel6.setBounds(new Rectangle(11, 69, 553, 51));
        kDPanel3.add(kDPanel6, null);
        //contArtificialPerson
        contArtificialPerson.setBoundEditor(txtArtificialPerson);
        //contDutyPerson
        contDutyPerson.setBoundEditor(txtDutyPerson);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(bizOwnCompany);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(boxBusinessType);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(boxControlType);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(boxGetType);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(dateOwnCompanyBuild);
        //kDPanel6
kDPanel6.setLayout(new BorderLayout(0, 0));        kDPanel6.add(kDScrollPane1, BorderLayout.CENTER);
        kDPanel6.add(kDLabel11, BorderLayout.WEST);
        kDPanel6.add(kDPanel7, BorderLayout.EAST);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(lstManagerProject, null);
        //kDPanel7
kDPanel7.setLayout(new BorderLayout(0, 0));        kDPanel7.add(btnManagerProject, BorderLayout.NORTH);
        //kDPanel4
        kDPanel4.setLayout(null);        kdtRightManager.setBounds(new Rectangle(10, 30, 550, 378));
        kDPanel4.add(kdtRightManager, null);
        btnAddRightLine.setBounds(new Rectangle(508, 10, 22, 19));
        kDPanel4.add(btnAddRightLine, null);
        btnDelRightLine.setBounds(new Rectangle(533, 10, 22, 19));
        kDPanel4.add(btnDelRightLine, null);
        //kDPanel5
        kDPanel5.setLayout(null);        btnFile1.setBounds(new Rectangle(171, 10, 22, 19));
        kDPanel5.add(btnFile1, null);
        btnFileView1.setBounds(new Rectangle(247, 10, 22, 19));
        kDPanel5.add(btnFileView1, null);
        btnFile2.setBounds(new Rectangle(171, 40, 22, 19));
        kDPanel5.add(btnFile2, null);
        btnFileView2.setBounds(new Rectangle(247, 40, 22, 19));
        kDPanel5.add(btnFileView2, null);
        btnFile3.setBounds(new Rectangle(171, 70, 22, 19));
        kDPanel5.add(btnFile3, null);
        btnFileView3.setBounds(new Rectangle(247, 70, 22, 19));
        kDPanel5.add(btnFileView3, null);
        btnFile4.setBounds(new Rectangle(171, 100, 22, 19));
        kDPanel5.add(btnFile4, null);
        btnFileView4.setBounds(new Rectangle(247, 100, 22, 19));
        kDPanel5.add(btnFileView4, null);
        btnFile5.setBounds(new Rectangle(171, 130, 22, 19));
        kDPanel5.add(btnFile5, null);
        btnFileView5.setBounds(new Rectangle(247, 130, 22, 19));
        kDPanel5.add(btnFileView5, null);
        btnFile6.setBounds(new Rectangle(171, 160, 22, 19));
        kDPanel5.add(btnFile6, null);
        btnFileView6.setBounds(new Rectangle(247, 160, 22, 19));
        kDPanel5.add(btnFileView6, null);
        btnFile7.setBounds(new Rectangle(171, 190, 22, 19));
        kDPanel5.add(btnFile7, null);
        btnFileView7.setBounds(new Rectangle(247, 190, 22, 19));
        kDPanel5.add(btnFileView7, null);
        btnFile8.setBounds(new Rectangle(171, 220, 22, 19));
        kDPanel5.add(btnFile8, null);
        btnFileView8.setBounds(new Rectangle(247, 220, 22, 19));
        kDPanel5.add(btnFileView8, null);
        btnFile10.setBounds(new Rectangle(171, 249, 22, 19));
        kDPanel5.add(btnFile10, null);
        btnFileView10.setBounds(new Rectangle(247, 249, 22, 19));
        kDPanel5.add(btnFileView10, null);
        btnFile11.setBounds(new Rectangle(171, 280, 22, 19));
        kDPanel5.add(btnFile11, null);
        btnFileView11.setBounds(new Rectangle(247, 280, 22, 19));
        kDPanel5.add(btnFileView11, null);
        kDLabel2.setBounds(new Rectangle(10, 130, 100, 19));
        kDPanel5.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(10, 190, 100, 19));
        kDPanel5.add(kDLabel3, null);
        kDLabel4.setBounds(new Rectangle(10, 100, 100, 19));
        kDPanel5.add(kDLabel4, null);
        kDLabel5.setBounds(new Rectangle(10, 220, 100, 19));
        kDPanel5.add(kDLabel5, null);
        kDLabel6.setBounds(new Rectangle(118, 339, 100, 19));
        kDPanel5.add(kDLabel6, null);
        kDLabel7.setBounds(new Rectangle(10, 70, 100, 19));
        kDPanel5.add(kDLabel7, null);
        kDLabel8.setBounds(new Rectangle(10, 249, 100, 19));
        kDPanel5.add(kDLabel8, null);
        kDLabel9.setBounds(new Rectangle(10, 160, 100, 19));
        kDPanel5.add(kDLabel9, null);
        kDLabel10.setBounds(new Rectangle(10, 40, 100, 19));
        kDPanel5.add(kDLabel10, null);
        kDLabel12.setBounds(new Rectangle(10, 10, 100, 19));
        kDPanel5.add(kDLabel12, null);
        kDLabel13.setBounds(new Rectangle(10, 340, 100, 19));
        kDPanel5.add(kDLabel13, null);
        kDLabel14.setBounds(new Rectangle(10, 280, 100, 19));
        kDPanel5.add(kDLabel14, null);
        kDSeparator7.setBounds(new Rectangle(10, 60, 471, 8));
        kDPanel5.add(kDSeparator7, null);
        kDSeparator8.setBounds(new Rectangle(10, 90, 471, 8));
        kDPanel5.add(kDSeparator8, null);
        kDSeparator9.setBounds(new Rectangle(10, 120, 471, 8));
        kDPanel5.add(kDSeparator9, null);
        kDSeparator10.setBounds(new Rectangle(10, 150, 471, 8));
        kDPanel5.add(kDSeparator10, null);
        kDSeparator11.setBounds(new Rectangle(10, 180, 471, 8));
        kDPanel5.add(kDSeparator11, null);
        kDSeparator13.setBounds(new Rectangle(10, 210, 471, 8));
        kDPanel5.add(kDSeparator13, null);
        kDSeparator14.setBounds(new Rectangle(10, 240, 471, 8));
        kDPanel5.add(kDSeparator14, null);
        kDSeparator15.setBounds(new Rectangle(2, 373, 471, 8));
        kDPanel5.add(kDSeparator15, null);
        kDSeparator16.setBounds(new Rectangle(10, 274, 471, 8));
        kDPanel5.add(kDSeparator16, null);
        kDSeparator17.setBounds(new Rectangle(10, 300, 471, 8));
        kDPanel5.add(kDSeparator17, null);
        kDSeparator18.setBounds(new Rectangle(10, 360, 471, 8));
        kDPanel5.add(kDSeparator18, null);
        kDSeparator19.setBounds(new Rectangle(10, 30, 471, 8));
        kDPanel5.add(kDSeparator19, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtCompanyNumber);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
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
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAddPaymanager);
        this.toolBar.add(btnViewPaymanager);

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.bizCompany, "data");
		dataBinder.registerBinding("artificialPerson", String.class, this.txtArtificialPerson, "_multiLangItem");
		dataBinder.registerBinding("dutyPerson", String.class, this.txtDutyPerson, "_multiLangItem");
		dataBinder.registerBinding("unitNumber", String.class, this.txtUnitNumber, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");
		dataBinder.registerBinding("postalCode", String.class, this.txtPostNumber, "text");
		dataBinder.registerBinding("phone", String.class, this.txtTel, "text");
		dataBinder.registerBinding("fax", String.class, this.txtFax, "text");
		dataBinder.registerBinding("eip", String.class, this.txtWeb, "text");
		dataBinder.registerBinding("linkMobile", String.class, this.txtLinkMobile, "text");
		dataBinder.registerBinding("address", String.class, this.txtAddress, "_multiLangItem");
		dataBinder.registerBinding("linkMan", String.class, this.txtLinkMan, "_multiLangItem");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.ftxtRegisterCapital, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_EDIT, this.ftxtRealCapital, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.UnitDataManagerEditUIHandler";
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
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.basedata.UnitDataManagerInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
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
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("artificialPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dutyPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postalCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("phone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fax", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eip", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkMobile", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkMan", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.ftxtRegisterCapital.setVisible(true);
		            this.ftxtRegisterCapital.setEnabled(false);
		            this.ftxtRealCapital.setVisible(true);
		            this.ftxtRealCapital.setEnabled(false);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output bizCompany_dataChanged method
     */
    protected void bizCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtTaxManager_editStopped method
     */
    protected void kdtTaxManager_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtRightManager_editStopped method
     */
    protected void kdtRightManager_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output wbtProductDelLine_actionPerformed method
     */
    protected void wbtProductDelLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output bizOwnCompany_dataChanged method
     */
    protected void bizOwnCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output boxGetType_itemStateChanged method
     */
    protected void boxGetType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output btnFile1_actionPerformed method
     */
    protected void btnFile1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile1_mouseReleased method
     */
    protected void btnFile1_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView1_actionPerformed method
     */
    protected void btnFileView1_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile2_actionPerformed method
     */
    protected void btnFile2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile2_mouseReleased method
     */
    protected void btnFile2_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView2_actionPerformed method
     */
    protected void btnFileView2_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile3_actionPerformed method
     */
    protected void btnFile3_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile3_mouseReleased method
     */
    protected void btnFile3_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView3_actionPerformed method
     */
    protected void btnFileView3_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile4_actionPerformed method
     */
    protected void btnFile4_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile4_mouseReleased method
     */
    protected void btnFile4_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView4_actionPerformed method
     */
    protected void btnFileView4_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile5_actionPerformed method
     */
    protected void btnFile5_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile5_mouseReleased method
     */
    protected void btnFile5_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView5_actionPerformed method
     */
    protected void btnFileView5_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile6_actionPerformed method
     */
    protected void btnFile6_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile6_mouseReleased method
     */
    protected void btnFile6_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView6_actionPerformed method
     */
    protected void btnFileView6_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile7_actionPerformed method
     */
    protected void btnFile7_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile7_mouseReleased method
     */
    protected void btnFile7_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView7_actionPerformed method
     */
    protected void btnFileView7_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile8_actionPerformed method
     */
    protected void btnFile8_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile8_mouseReleased method
     */
    protected void btnFile8_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView8_actionPerformed method
     */
    protected void btnFileView8_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile10_actionPerformed method
     */
    protected void btnFile10_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile10_mouseReleased method
     */
    protected void btnFile10_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView10_actionPerformed method
     */
    protected void btnFileView10_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile11_actionPerformed method
     */
    protected void btnFile11_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile11_mouseReleased method
     */
    protected void btnFile11_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView11_actionPerformed method
     */
    protected void btnFileView11_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddPaymanager_mouseReleased method
     */
    protected void btnAddPaymanager_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnViewPaymanager_mouseReleased method
     */
    protected void btnViewPaymanager_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFile9_actionPerformed method
     */
    protected void btnFile9_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile9_mouseReleased method
     */
    protected void btnFile9_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView9_actionPerformed method
     */
    protected void btnFileView9_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile12_actionPerformed method
     */
    protected void btnFile12_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnFile12_mouseReleased method
     */
    protected void btnFile12_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnFileView12_actionPerformed method
     */
    protected void btnFileView12_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnManagerProject_actionPerformed method
     */
    protected void btnManagerProject_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("artificialPerson"));
        sic.add(new SelectorItemInfo("dutyPerson"));
        sic.add(new SelectorItemInfo("unitNumber"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("postalCode"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("eip"));
        sic.add(new SelectorItemInfo("linkMobile"));
        sic.add(new SelectorItemInfo("address"));
        sic.add(new SelectorItemInfo("linkMan"));
        return sic;
    }        
    	

    /**
     * output actionAddRightLine_actionPerformed method
     */
    public void actionAddRightLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelRightLine_actionPerformed method
     */
    public void actionDelRightLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddTaxLine_actionPerformed method
     */
    public void actionAddTaxLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelTaxLine_actionPerformed method
     */
    public void actionDelTaxLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddDirectorLine_actionPerformed method
     */
    public void actionAddDirectorLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelDirectorLine_actionPerformed method
     */
    public void actionDelDirectorLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddSelfDirectorLine_actionPerformed method
     */
    public void actionAddSelfDirectorLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelSelfDirectorLine_actionPerformed method
     */
    public void actionDelSelfDirectorLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddWatcherLine_actionPerformed method
     */
    public void actionAddWatcherLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionDelWatcherLine_actionPerformed method
     */
    public void actionDelWatcherLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAddPaymanager_actionPerformed method
     */
    public void actionAddPaymanager_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewPaymanager_actionPerformed method
     */
    public void actionViewPaymanager_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionAddRightLine class
     */
    protected class ActionAddRightLine extends ItemAction
    {
        public ActionAddRightLine()
        {
            this(null);
        }

        public ActionAddRightLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddRightLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRightLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRightLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionAddRightLine", "actionAddRightLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDelRightLine class
     */
    protected class ActionDelRightLine extends ItemAction
    {
        public ActionDelRightLine()
        {
            this(null);
        }

        public ActionDelRightLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelRightLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRightLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelRightLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionDelRightLine", "actionDelRightLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAddTaxLine class
     */
    protected class ActionAddTaxLine extends ItemAction
    {
        public ActionAddTaxLine()
        {
            this(null);
        }

        public ActionAddTaxLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddTaxLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTaxLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddTaxLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionAddTaxLine", "actionAddTaxLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDelTaxLine class
     */
    protected class ActionDelTaxLine extends ItemAction
    {
        public ActionDelTaxLine()
        {
            this(null);
        }

        public ActionDelTaxLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelTaxLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTaxLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelTaxLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionDelTaxLine", "actionDelTaxLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAddDirectorLine class
     */
    protected class ActionAddDirectorLine extends ItemAction
    {
        public ActionAddDirectorLine()
        {
            this(null);
        }

        public ActionAddDirectorLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddDirectorLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddDirectorLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddDirectorLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionAddDirectorLine", "actionAddDirectorLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDelDirectorLine class
     */
    protected class ActionDelDirectorLine extends ItemAction
    {
        public ActionDelDirectorLine()
        {
            this(null);
        }

        public ActionDelDirectorLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelDirectorLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelDirectorLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelDirectorLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionDelDirectorLine", "actionDelDirectorLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAddSelfDirectorLine class
     */
    protected class ActionAddSelfDirectorLine extends ItemAction
    {
        public ActionAddSelfDirectorLine()
        {
            this(null);
        }

        public ActionAddSelfDirectorLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddSelfDirectorLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddSelfDirectorLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddSelfDirectorLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionAddSelfDirectorLine", "actionAddSelfDirectorLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDelSelfDirectorLine class
     */
    protected class ActionDelSelfDirectorLine extends ItemAction
    {
        public ActionDelSelfDirectorLine()
        {
            this(null);
        }

        public ActionDelSelfDirectorLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelSelfDirectorLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSelfDirectorLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelSelfDirectorLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionDelSelfDirectorLine", "actionDelSelfDirectorLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAddWatcherLine class
     */
    protected class ActionAddWatcherLine extends ItemAction
    {
        public ActionAddWatcherLine()
        {
            this(null);
        }

        public ActionAddWatcherLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddWatcherLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddWatcherLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddWatcherLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionAddWatcherLine", "actionAddWatcherLine_actionPerformed", e);
        }
    }

    /**
     * output ActionDelWatcherLine class
     */
    protected class ActionDelWatcherLine extends ItemAction
    {
        public ActionDelWatcherLine()
        {
            this(null);
        }

        public ActionDelWatcherLine(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionDelWatcherLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelWatcherLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionDelWatcherLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionDelWatcherLine", "actionDelWatcherLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAddPaymanager class
     */
    protected class ActionAddPaymanager extends ItemAction
    {
        public ActionAddPaymanager()
        {
            this(null);
        }

        public ActionAddPaymanager(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAddPaymanager.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddPaymanager.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddPaymanager.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionAddPaymanager", "actionAddPaymanager_actionPerformed", e);
        }
    }

    /**
     * output ActionViewPaymanager class
     */
    protected class ActionViewPaymanager extends ItemAction
    {
        public ActionViewPaymanager()
        {
            this(null);
        }

        public ActionViewPaymanager(IUIObject uiObject)
        {
            super(uiObject);
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewPaymanager.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPaymanager.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewPaymanager.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractUnitDataManagerEditUI.this, "ActionViewPaymanager", "actionViewPaymanager_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "UnitDataManagerEditUI");
    }




}