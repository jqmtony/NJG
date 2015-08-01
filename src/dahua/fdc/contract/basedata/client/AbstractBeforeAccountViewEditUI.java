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
public abstract class AbstractBeforeAccountViewEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractBeforeAccountViewEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSettAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contIntendAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtIntendAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOtherProAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOtherProAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOtherSettAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOtherSettAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOtherIntendAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOtherIntendAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBeforeOtherAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBeforeOtherAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBeforeDevAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBeforeDevAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBeforeSettAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBeforeSettAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSettAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProductAccount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtProductAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdminFees;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMarketFees;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdminFees;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMarketFees;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompany;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompany;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCompensation;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCompensationAccount;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntrys;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGuerdon;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtGuerdonAccount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtTempAccount;
    protected com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractBeforeAccountViewEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractBeforeAccountViewEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contProAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contSettAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contIntendAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtIntendAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contOtherProAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtOtherProAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contOtherSettAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtOtherSettAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contOtherIntendAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtOtherIntendAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBeforeOtherAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBeforeOtherAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBeforeDevAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBeforeDevAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contBeforeSettAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtBeforeSettAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtSettAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProductAccount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtProductAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contAdminFees = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMarketFees = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtAdminFees = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtMarketFees = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCompany = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCompany = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCompensation = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCompensationAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtEntrys = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contGuerdon = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtGuerdonAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtTempAccount = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contProAccount.setName("contProAccount");
        this.prmtProAccount.setName("prmtProAccount");
        this.contSettAccount.setName("contSettAccount");
        this.contIntendAccount.setName("contIntendAccount");
        this.prmtIntendAccount.setName("prmtIntendAccount");
        this.contOtherProAccount.setName("contOtherProAccount");
        this.prmtOtherProAccount.setName("prmtOtherProAccount");
        this.contOtherSettAccount.setName("contOtherSettAccount");
        this.prmtOtherSettAccount.setName("prmtOtherSettAccount");
        this.contOtherIntendAccount.setName("contOtherIntendAccount");
        this.prmtOtherIntendAccount.setName("prmtOtherIntendAccount");
        this.contBeforeOtherAccount.setName("contBeforeOtherAccount");
        this.prmtBeforeOtherAccount.setName("prmtBeforeOtherAccount");
        this.contBeforeDevAccount.setName("contBeforeDevAccount");
        this.prmtBeforeDevAccount.setName("prmtBeforeDevAccount");
        this.contBeforeSettAccount.setName("contBeforeSettAccount");
        this.prmtBeforeSettAccount.setName("prmtBeforeSettAccount");
        this.prmtSettAccount.setName("prmtSettAccount");
        this.contProductAccount.setName("contProductAccount");
        this.prmtProductAccount.setName("prmtProductAccount");
        this.contAdminFees.setName("contAdminFees");
        this.contMarketFees.setName("contMarketFees");
        this.prmtAdminFees.setName("prmtAdminFees");
        this.prmtMarketFees.setName("prmtMarketFees");
        this.contCompany.setName("contCompany");
        this.prmtCompany.setName("prmtCompany");
        this.contCompensation.setName("contCompensation");
        this.prmtCompensationAccount.setName("prmtCompensationAccount");
        this.kDContainer1.setName("kDContainer1");
        this.kdtEntrys.setName("kdtEntrys");
        this.contGuerdon.setName("contGuerdon");
        this.prmtGuerdonAccount.setName("prmtGuerdonAccount");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.prmtTempAccount.setName("prmtTempAccount");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancel.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemSave.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);
        // contProAccount		
        this.contProAccount.setBoundLabelText(resHelper.getString("contProAccount.boundLabelText"));		
        this.contProAccount.setBoundLabelLength(160);		
        this.contProAccount.setBoundLabelUnderline(true);
        // prmtProAccount		
        this.prmtProAccount.setDisplayFormat("$longName$");		
        this.prmtProAccount.setEditFormat("$number$");		
        this.prmtProAccount.setCommitFormat("$number$");		
        this.prmtProAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtProAccount.setRequired(true);		
        this.prmtProAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtProAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtProAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtProAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contSettAccount		
        this.contSettAccount.setBoundLabelText(resHelper.getString("contSettAccount.boundLabelText"));		
        this.contSettAccount.setBoundLabelLength(160);		
        this.contSettAccount.setBoundLabelUnderline(true);
        // contIntendAccount		
        this.contIntendAccount.setBoundLabelText(resHelper.getString("contIntendAccount.boundLabelText"));		
        this.contIntendAccount.setBoundLabelLength(160);		
        this.contIntendAccount.setBoundLabelUnderline(true);
        // prmtIntendAccount		
        this.prmtIntendAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtIntendAccount.setDisplayFormat("$longName$");		
        this.prmtIntendAccount.setEditFormat("$number$");		
        this.prmtIntendAccount.setCommitFormat("$number$");		
        this.prmtIntendAccount.setRequired(true);		
        this.prmtIntendAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtIntendAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtIntendAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtIntendAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtIntendAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contOtherProAccount		
        this.contOtherProAccount.setBoundLabelText(resHelper.getString("contOtherProAccount.boundLabelText"));		
        this.contOtherProAccount.setBoundLabelLength(160);		
        this.contOtherProAccount.setBoundLabelUnderline(true);
        // prmtOtherProAccount		
        this.prmtOtherProAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtOtherProAccount.setDisplayFormat("$longName$");		
        this.prmtOtherProAccount.setEditFormat("$number$");		
        this.prmtOtherProAccount.setCommitFormat("$number$");		
        this.prmtOtherProAccount.setRequired(true);		
        this.prmtOtherProAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtOtherProAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtOtherProAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtOtherProAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtOtherProAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contOtherSettAccount		
        this.contOtherSettAccount.setBoundLabelText(resHelper.getString("contOtherSettAccount.boundLabelText"));		
        this.contOtherSettAccount.setBoundLabelLength(160);		
        this.contOtherSettAccount.setBoundLabelUnderline(true);
        // prmtOtherSettAccount		
        this.prmtOtherSettAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtOtherSettAccount.setDisplayFormat("$longName$");		
        this.prmtOtherSettAccount.setEditFormat("$number$");		
        this.prmtOtherSettAccount.setCommitFormat("$number$");		
        this.prmtOtherSettAccount.setRequired(true);		
        this.prmtOtherSettAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtOtherSettAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtOtherSettAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtOtherSettAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtOtherSettAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contOtherIntendAccount		
        this.contOtherIntendAccount.setBoundLabelText(resHelper.getString("contOtherIntendAccount.boundLabelText"));		
        this.contOtherIntendAccount.setBoundLabelLength(160);		
        this.contOtherIntendAccount.setBoundLabelUnderline(true);		
        this.contOtherIntendAccount.setVisible(false);
        this.contOtherIntendAccount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    contOtherIntendAccount_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // prmtOtherIntendAccount		
        this.prmtOtherIntendAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtOtherIntendAccount.setDisplayFormat("$longName$");		
        this.prmtOtherIntendAccount.setEditFormat("$number$");		
        this.prmtOtherIntendAccount.setCommitFormat("$number$");		
        this.prmtOtherIntendAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtOtherIntendAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtOtherIntendAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtOtherIntendAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtOtherIntendAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contBeforeOtherAccount		
        this.contBeforeOtherAccount.setBoundLabelText(resHelper.getString("contBeforeOtherAccount.boundLabelText"));		
        this.contBeforeOtherAccount.setBoundLabelLength(160);		
        this.contBeforeOtherAccount.setBoundLabelUnderline(true);
        // prmtBeforeOtherAccount		
        this.prmtBeforeOtherAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtBeforeOtherAccount.setDisplayFormat("$longName$");		
        this.prmtBeforeOtherAccount.setCommitFormat("$number$");		
        this.prmtBeforeOtherAccount.setEditFormat("$number$");		
        this.prmtBeforeOtherAccount.setRequired(true);		
        this.prmtBeforeOtherAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtBeforeOtherAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtBeforeOtherAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtBeforeOtherAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtBeforeOtherAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contBeforeDevAccount		
        this.contBeforeDevAccount.setBoundLabelText(resHelper.getString("contBeforeDevAccount.boundLabelText"));		
        this.contBeforeDevAccount.setBoundLabelLength(160);		
        this.contBeforeDevAccount.setBoundLabelUnderline(true);
        // prmtBeforeDevAccount		
        this.prmtBeforeDevAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtBeforeDevAccount.setDisplayFormat("$longName$");		
        this.prmtBeforeDevAccount.setEditFormat("$number$");		
        this.prmtBeforeDevAccount.setCommitFormat("$number$");		
        this.prmtBeforeDevAccount.setRequired(true);		
        this.prmtBeforeDevAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtBeforeDevAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtBeforeDevAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtBeforeDevAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtBeforeDevAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contBeforeSettAccount		
        this.contBeforeSettAccount.setBoundLabelText(resHelper.getString("contBeforeSettAccount.boundLabelText"));		
        this.contBeforeSettAccount.setBoundLabelLength(160);		
        this.contBeforeSettAccount.setBoundLabelUnderline(true);
        // prmtBeforeSettAccount		
        this.prmtBeforeSettAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtBeforeSettAccount.setDisplayFormat("$longName$");		
        this.prmtBeforeSettAccount.setEditFormat("$number$");		
        this.prmtBeforeSettAccount.setCommitFormat("$number$");		
        this.prmtBeforeSettAccount.setRequired(true);		
        this.prmtBeforeSettAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtBeforeSettAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtBeforeSettAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtBeforeSettAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtBeforeSettAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtSettAccount		
        this.prmtSettAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtSettAccount.setDisplayFormat("$longName$");		
        this.prmtSettAccount.setEditFormat("$number$");		
        this.prmtSettAccount.setCommitFormat("$number$");		
        this.prmtSettAccount.setRequired(true);		
        this.prmtSettAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtSettAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtSettAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtSettAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtSettAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contProductAccount		
        this.contProductAccount.setBoundLabelText(resHelper.getString("contProductAccount.boundLabelText"));		
        this.contProductAccount.setBoundLabelLength(160);		
        this.contProductAccount.setBoundLabelUnderline(true);
        // prmtProductAccount		
        this.prmtProductAccount.setDisplayFormat("$longName$");		
        this.prmtProductAccount.setEditFormat("$number$");		
        this.prmtProductAccount.setCommitFormat("$number$");		
        this.prmtProductAccount.setRequired(true);		
        this.prmtProductAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtProductAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");
        this.prmtProductAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtProductAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtProductAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtProductAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contAdminFees		
        this.contAdminFees.setBoundLabelText(resHelper.getString("contAdminFees.boundLabelText"));		
        this.contAdminFees.setBoundLabelLength(160);		
        this.contAdminFees.setBoundLabelUnderline(true);
        // contMarketFees		
        this.contMarketFees.setBoundLabelText(resHelper.getString("contMarketFees.boundLabelText"));		
        this.contMarketFees.setBoundLabelLength(160);		
        this.contMarketFees.setBoundLabelUnderline(true);
        // prmtAdminFees		
        this.prmtAdminFees.setDisplayFormat("$longName$");		
        this.prmtAdminFees.setEditFormat("$number$");		
        this.prmtAdminFees.setCommitFormat("$number$");		
        this.prmtAdminFees.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtAdminFees.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");		
        this.prmtAdminFees.setRequired(true);
        this.prmtAdminFees.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtAdminFees_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtAdminFees.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtAdminFees_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtMarketFees		
        this.prmtMarketFees.setDisplayFormat("$longName$");		
        this.prmtMarketFees.setEditFormat("$number$");		
        this.prmtMarketFees.setCommitFormat("$number$");		
        this.prmtMarketFees.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtMarketFees.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");		
        this.prmtMarketFees.setRequired(true);
        this.prmtMarketFees.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtMarketFees_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtMarketFees.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtMarketFees_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contCompany		
        this.contCompany.setBoundLabelText(resHelper.getString("contCompany.boundLabelText"));		
        this.contCompany.setBoundLabelLength(160);		
        this.contCompany.setBoundLabelUnderline(true);
        // prmtCompany		
        this.prmtCompany.setDisplayFormat("$name$");		
        this.prmtCompany.setEditFormat("$number$");		
        this.prmtCompany.setCommitFormat("$number$");		
        this.prmtCompany.setQueryInfo("com.kingdee.eas.basedata.org.app.CompanyBizUnitQuery");		
        this.prmtCompany.setEnabled(false);
        this.prmtCompany.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contCompensation		
        this.contCompensation.setBoundLabelText(resHelper.getString("contCompensation.boundLabelText"));		
        this.contCompensation.setBoundLabelLength(160);		
        this.contCompensation.setBoundLabelUnderline(true);
        // prmtCompensationAccount		
        this.prmtCompensationAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtCompensationAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");		
        this.prmtCompensationAccount.setDisplayFormat("$longName$");		
        this.prmtCompensationAccount.setEditFormat("$number$");		
        this.prmtCompensationAccount.setCommitFormat("$number$");		
        this.prmtCompensationAccount.setRequired(true);
        this.prmtCompensationAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtCompensationAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtCompensationAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtCompensationAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdtEntrys		
        this.kdtEntrys.setFormatXml(resHelper.getString("kdtEntrys.formatXml"));

        

        // contGuerdon		
        this.contGuerdon.setBoundLabelText(resHelper.getString("contGuerdon.boundLabelText"));		
        this.contGuerdon.setBoundLabelLength(160);		
        this.contGuerdon.setBoundLabelUnderline(true);
        // prmtGuerdonAccount		
        this.prmtGuerdonAccount.setDisplayFormat("$longName$");		
        this.prmtGuerdonAccount.setEditFormat("$number$");		
        this.prmtGuerdonAccount.setCommitFormat("$number$");		
        this.prmtGuerdonAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.F7AccountViewQuery");		
        this.prmtGuerdonAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");		
        this.prmtGuerdonAccount.setRequired(true);
        this.prmtGuerdonAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtGuerdonAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtGuerdonAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtGuerdonAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(160);
        // prmtTempAccount		
        this.prmtTempAccount.setQueryInfo("com.kingdee.eas.basedata.master.account.app.AccountViewQuery");		
        this.prmtTempAccount.setDefaultF7UIName("com.kingdee.eas.basedata.master.account.client.F7AccountViewUI");		
        this.prmtTempAccount.setCommitFormat("$number$");		
        this.prmtTempAccount.setDisplayFormat("$longName$");		
        this.prmtTempAccount.setEditFormat("$number$");
        this.prmtTempAccount.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtTempAccount_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtTempAccount.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    prmtTempAccount_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtBeforeOtherAccount,prmtProAccount,prmtSettAccount,prmtIntendAccount,prmtOtherProAccount,prmtOtherSettAccount,prmtBeforeSettAccount,prmtBeforeDevAccount,prmtProductAccount,prmtAdminFees,prmtMarketFees,prmtOtherIntendAccount}));
        this.setFocusCycleRoot(true);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 950, 550));
        this.setLayout(null);
        contProAccount.setBounds(new Rectangle(11, 37, 432, 19));
        this.add(contProAccount, null);
        contSettAccount.setBounds(new Rectangle(506, 37, 432, 19));
        this.add(contSettAccount, null);
        contIntendAccount.setBounds(new Rectangle(11, 64, 432, 19));
        this.add(contIntendAccount, null);
        contOtherProAccount.setBounds(new Rectangle(506, 64, 432, 19));
        this.add(contOtherProAccount, null);
        contOtherSettAccount.setBounds(new Rectangle(11, 91, 432, 19));
        this.add(contOtherSettAccount, null);
        contOtherIntendAccount.setBounds(new Rectangle(506, 197, 432, 19));
        this.add(contOtherIntendAccount, null);
        contBeforeOtherAccount.setBounds(new Rectangle(506, 10, 432, 19));
        this.add(contBeforeOtherAccount, null);
        contBeforeDevAccount.setBounds(new Rectangle(11, 118, 432, 19));
        this.add(contBeforeDevAccount, null);
        contBeforeSettAccount.setBounds(new Rectangle(506, 91, 432, 19));
        this.add(contBeforeSettAccount, null);
        contProductAccount.setBounds(new Rectangle(506, 118, 432, 19));
        this.add(contProductAccount, null);
        contAdminFees.setBounds(new Rectangle(11, 145, 432, 19));
        this.add(contAdminFees, null);
        contMarketFees.setBounds(new Rectangle(506, 145, 432, 19));
        this.add(contMarketFees, null);
        contCompany.setBounds(new Rectangle(11, 10, 432, 19));
        this.add(contCompany, null);
        contCompensation.setBounds(new Rectangle(11, 172, 432, 19));
        this.add(contCompensation, null);
        kDContainer1.setBounds(new Rectangle(11, 223, 927, 300));
        this.add(kDContainer1, null);
        contGuerdon.setBounds(new Rectangle(506, 172, 432, 19));
        this.add(contGuerdon, null);
        kDLabelContainer1.setBounds(new Rectangle(11, 197, 433, 19));
        this.add(kDLabelContainer1, null);
        //contProAccount
        contProAccount.setBoundEditor(prmtProAccount);
        //contSettAccount
        contSettAccount.setBoundEditor(prmtSettAccount);
        //contIntendAccount
        contIntendAccount.setBoundEditor(prmtIntendAccount);
        //contOtherProAccount
        contOtherProAccount.setBoundEditor(prmtOtherProAccount);
        //contOtherSettAccount
        contOtherSettAccount.setBoundEditor(prmtOtherSettAccount);
        //contOtherIntendAccount
        contOtherIntendAccount.setBoundEditor(prmtOtherIntendAccount);
        //contBeforeOtherAccount
        contBeforeOtherAccount.setBoundEditor(prmtBeforeOtherAccount);
        //contBeforeDevAccount
        contBeforeDevAccount.setBoundEditor(prmtBeforeDevAccount);
        //contBeforeSettAccount
        contBeforeSettAccount.setBoundEditor(prmtBeforeSettAccount);
        //contProductAccount
        contProductAccount.setBoundEditor(prmtProductAccount);
        //contAdminFees
        contAdminFees.setBoundEditor(prmtAdminFees);
        //contMarketFees
        contMarketFees.setBoundEditor(prmtMarketFees);
        //contCompany
        contCompany.setBoundEditor(prmtCompany);
        //contCompensation
        contCompensation.setBoundEditor(prmtCompensationAccount);
        //kDContainer1
kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kdtEntrys, BorderLayout.CENTER);
        //contGuerdon
        contGuerdon.setBoundEditor(prmtGuerdonAccount);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(prmtTempAccount);

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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
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

    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("proAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtProAccount, "data");
		dataBinder.registerBinding("intendAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtIntendAccount, "data");
		dataBinder.registerBinding("otherProAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtOtherProAccount, "data");
		dataBinder.registerBinding("otherSettAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtOtherSettAccount, "data");
		dataBinder.registerBinding("otherIntendAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtOtherIntendAccount, "data");
		dataBinder.registerBinding("beforeOtherAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtBeforeOtherAccount, "data");
		dataBinder.registerBinding("beforeDevAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtBeforeDevAccount, "data");
		dataBinder.registerBinding("beforeSettAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtBeforeSettAccount, "data");
		dataBinder.registerBinding("settAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtSettAccount, "data");
		dataBinder.registerBinding("productAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtProductAccount, "data");
		dataBinder.registerBinding("adminFees", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtAdminFees, "data");
		dataBinder.registerBinding("marketFees", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtMarketFees, "data");
		dataBinder.registerBinding("company", com.kingdee.eas.basedata.org.CompanyOrgUnitInfo.class, this.prmtCompany, "data");
		dataBinder.registerBinding("compensationAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtCompensationAccount, "data");
		dataBinder.registerBinding("guerdonAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtGuerdonAccount, "data");
		dataBinder.registerBinding("tempAccount", com.kingdee.eas.basedata.master.account.AccountViewInfo.class, this.prmtTempAccount, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.BeforeAccountViewEditUIHandler";
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
        this.prmtBeforeOtherAccount.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)ov;
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
		getValidateHelper().registerBindProperty("proAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("intendAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("otherProAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("otherSettAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("otherIntendAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beforeOtherAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beforeDevAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("beforeSettAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("settAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("productAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminFees", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("marketFees", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("company", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("compensationAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("guerdonAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tempAccount", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output prmtProAccount_willShow method
     */
    protected void prmtProAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtProAccount_willCommit method
     */
    protected void prmtProAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtIntendAccount_willShow method
     */
    protected void prmtIntendAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtIntendAccount_willCommit method
     */
    protected void prmtIntendAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtOtherProAccount_willShow method
     */
    protected void prmtOtherProAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtOtherProAccount_willCommit method
     */
    protected void prmtOtherProAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtOtherSettAccount_willShow method
     */
    protected void prmtOtherSettAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtOtherSettAccount_willCommit method
     */
    protected void prmtOtherSettAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output contOtherIntendAccount_focusLost method
     */
    protected void contOtherIntendAccount_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output prmtOtherIntendAccount_willShow method
     */
    protected void prmtOtherIntendAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtOtherIntendAccount_willCommit method
     */
    protected void prmtOtherIntendAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtBeforeOtherAccount_willShow method
     */
    protected void prmtBeforeOtherAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtBeforeOtherAccount_willCommit method
     */
    protected void prmtBeforeOtherAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtBeforeDevAccount_willShow method
     */
    protected void prmtBeforeDevAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtBeforeDevAccount_willCommit method
     */
    protected void prmtBeforeDevAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtBeforeSettAccount_willShow method
     */
    protected void prmtBeforeSettAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtBeforeSettAccount_willCommit method
     */
    protected void prmtBeforeSettAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtSettAccount_willShow method
     */
    protected void prmtSettAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtSettAccount_willCommit method
     */
    protected void prmtSettAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtProductAccount_willCommit method
     */
    protected void prmtProductAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtProductAccount_willShow method
     */
    protected void prmtProductAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtAdminFees_willShow method
     */
    protected void prmtAdminFees_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtAdminFees_willCommit method
     */
    protected void prmtAdminFees_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtMarketFees_willCommit method
     */
    protected void prmtMarketFees_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtMarketFees_willShow method
     */
    protected void prmtMarketFees_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtCompany_dataChanged method
     */
    protected void prmtCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtCompensationAccount_willCommit method
     */
    protected void prmtCompensationAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtCompensationAccount_willShow method
     */
    protected void prmtCompensationAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtGuerdonAccount_willCommit method
     */
    protected void prmtGuerdonAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output prmtGuerdonAccount_willShow method
     */
    protected void prmtGuerdonAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtTempAccount_willShow method
     */
    protected void prmtTempAccount_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output prmtTempAccount_willCommit method
     */
    protected void prmtTempAccount_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("proAccount.*"));
        sic.add(new SelectorItemInfo("intendAccount.*"));
        sic.add(new SelectorItemInfo("otherProAccount.*"));
        sic.add(new SelectorItemInfo("otherSettAccount.*"));
        sic.add(new SelectorItemInfo("otherIntendAccount.*"));
        sic.add(new SelectorItemInfo("beforeOtherAccount.*"));
        sic.add(new SelectorItemInfo("beforeDevAccount.*"));
        sic.add(new SelectorItemInfo("beforeSettAccount.*"));
        sic.add(new SelectorItemInfo("settAccount.*"));
        sic.add(new SelectorItemInfo("productAccount.*"));
        sic.add(new SelectorItemInfo("adminFees.*"));
        sic.add(new SelectorItemInfo("marketFees.*"));
        sic.add(new SelectorItemInfo("company.*"));
        sic.add(new SelectorItemInfo("compensationAccount.*"));
        sic.add(new SelectorItemInfo("guerdonAccount.*"));
        sic.add(new SelectorItemInfo("tempAccount.*"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "BeforeAccountViewEditUI");
    }




}