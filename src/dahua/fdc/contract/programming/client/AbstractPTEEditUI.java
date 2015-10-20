/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

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
public abstract class AbstractPTEEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPTEEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlHIde;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdplContract;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdplPayPlan;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpConstract;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabel lblAttachment;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstractName;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtScope;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcCost;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcEconomy;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCostEntries;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEconomyEntriese;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtProblem;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstractNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttachment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton bntSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttachment;
    protected com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo editData = null;
    protected ActionSubmit actionSubmit = null;
    protected ActionAttachment actionAttachment = null;
    /**
     * output class constructor
     */
    public AbstractPTEEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPTEEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        this.actionAttachment = new ActionAttachment(this);
        getActionManager().registerAction("actionAttachment", actionAttachment);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pnlHIde = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdplContract = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdplPayPlan = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdpConstract = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lblAttachment = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.pnlAttachment = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtConstract = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConstractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtScope = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kdcCost = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdcEconomy = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtCostEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEconomyEntriese = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtProblem = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConstractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAttachment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bntSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.pnlHIde.setName("pnlHIde");
        this.kdplContract.setName("kdplContract");
        this.kdplPayPlan.setName("kdplPayPlan");
        this.kdpConstract.setName("kdpConstract");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.lblAttachment.setName("lblAttachment");
        this.pnlAttachment.setName("pnlAttachment");
        this.txtConstract.setName("txtConstract");
        this.txtConstractName.setName("txtConstractName");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtScope.setName("txtScope");
        this.kdcCost.setName("kdcCost");
        this.kdcEconomy.setName("kdcEconomy");
        this.kdtCostEntries.setName("kdtCostEntries");
        this.kdtEconomyEntriese.setName("kdtEconomyEntriese");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtProblem.setName("txtProblem");
        this.txtDescription.setName("txtDescription");
        this.txtConstractNumber.setName("txtConstractNumber");
        this.txtAttachment.setName("txtAttachment");
        this.bntSubmit.setName("bntSubmit");
        this.btnAttachment.setName("btnAttachment");
        // CoreUI		
        this.setPreferredSize(new Dimension(1024,768));
        // kDTabbedPane2		
        this.kDTabbedPane2.setPreferredSize(new Dimension(766,1022));
        // pnlHIde		
        this.pnlHIde.setVisible(false);
        // kdplContract
        // kdplPayPlan
        // kdpConstract		
        this.kdpConstract.setBorder(null);
        // kDTabbedPane1
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setAutoscrolls(true);
        // lblAttachment		
        this.lblAttachment.setText(resHelper.getString("lblAttachment.text"));
        // pnlAttachment
        // txtConstract		
        this.txtConstract.setMaxLength(80);		
        this.txtConstract.setEditable(false);		
        this.txtConstract.setOpaque(false);
        // txtConstractName		
        this.txtConstractName.setRequired(true);		
        this.txtConstractName.setMaxLength(80);
        // kDScrollPane2
        // txtScope		
        this.txtScope.setMaxLength(1024);		
        this.txtScope.setRows(3);		
        this.txtScope.setText(resHelper.getString("txtScope.text"));
        // kdcCost		
        this.kdcCost.setTitle(resHelper.getString("kdcCost.title"));
        // kdcEconomy		
        this.kdcEconomy.setTitle(resHelper.getString("kdcEconomy.title"));		
        this.kdcEconomy.setVisible(false);
        // kdtCostEntries		
        this.kdtCostEntries.setFormatXml(resHelper.getString("kdtCostEntries.formatXml"));
        this.kdtCostEntries.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtCostEntries_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCostEntries.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtCostEntries_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCostEntries.addKDTDataRequestListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestListener() {
            public void tableDataRequest(com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent e) {
                try {
                    kdtCostEntries_tableDataRequest(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCostEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtCostEntries.putBindContents("editData",new String[] {"id","costAccount.longNumber","costAccount.name","assignScale","contractScale","description"});


        // kdtEconomyEntriese		
        this.kdtEconomyEntriese.setFormatXml(resHelper.getString("kdtEconomyEntriese.formatXml"));		
        this.kdtEconomyEntriese.setVisible(false);
        this.kdtEconomyEntriese.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEconomyEntriese_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEconomyEntriese.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEconomyEntriese_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEconomyEntriese.putBindContents("editData",new String[] {"paymentType.name","scale","condition","description","id"});


        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setToolTipText(resHelper.getString("kDLabelContainer7.toolTipText"));
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtProblem		
        this.txtProblem.setMaxLength(1024);		
        this.txtProblem.setRows(3);
        // txtDescription		
        this.txtDescription.setMaxLength(1024);
        // txtConstractNumber		
        this.txtConstractNumber.setRequired(true);		
        this.txtConstractNumber.setMaxLength(80);
        this.txtConstractNumber.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    txtConstractNumber_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtConstractNumber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    txtConstractNumber_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    txtConstractNumber_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtConstractNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtConstractNumber_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtConstractNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                try {
                    txtConstractNumber_keyTyped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    txtConstractNumber_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyPressed(java.awt.event.KeyEvent e) {
                try {
                    txtConstractNumber_keyPressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtAttachment		
        this.txtAttachment.setOpaque(false);		
        this.txtAttachment.setEditable(false);		
        this.txtAttachment.setMaxLength(80);
        this.txtAttachment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    txtAttachment_mouseEntered(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                try {
                    txtAttachment_mouseExited(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtAttachment.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                try {
                    txtAttachment_mouseMoved(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // bntSubmit
        this.bntSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.bntSubmit.setText(resHelper.getString("bntSubmit.text"));		
        this.bntSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // btnAttachment
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affixmanage"));
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
        this.setBounds(new Rectangle(10, 10, 1024, 768));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1024, 768));
        kDTabbedPane2.setBounds(new Rectangle(1, 1, 1023, 766));
        this.add(kDTabbedPane2, new KDLayout.Constraints(1, 1, 1023, 766, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        pnlHIde.setBounds(new Rectangle(1025, 137, 750, 186));
        this.add(pnlHIde, new KDLayout.Constraints(1025, 137, 750, 186, 0));
        //kDTabbedPane2
        kDTabbedPane2.add(kdplContract, resHelper.getString("kdplContract.constraints"));
        kDTabbedPane2.add(kdplPayPlan, resHelper.getString("kdplPayPlan.constraints"));
        //kdplContract
        kdplContract.setLayout(null);        kdpConstract.setBounds(new Rectangle(5, 5, 1000, 135));
        kdplContract.add(kdpConstract, null);
        kDTabbedPane1.setBounds(new Rectangle(10, 145, 1000, 580));
        kdplContract.add(kDTabbedPane1, null);
        //kdpConstract
        kdpConstract.setLayout(null);        kDLabelContainer1.setBounds(new Rectangle(5, 5, 300, 20));
        kdpConstract.add(kDLabelContainer1, null);
        kDLabelContainer3.setBounds(new Rectangle(555, 5, 300, 20));
        kdpConstract.add(kDLabelContainer3, null);
        kDLabelContainer4.setBounds(new Rectangle(4, 30, 852, 70));
        kdpConstract.add(kDLabelContainer4, null);
        lblAttachment.setBounds(new Rectangle(5, 109, 100, 19));
        kdpConstract.add(lblAttachment, null);
        pnlAttachment.setBounds(new Rectangle(104, 105, 753, 22));
        kdpConstract.add(pnlAttachment, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtConstract);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtConstractName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtScope, null);
        pnlAttachment.setLayout(null);        //kDTabbedPane1
        kDTabbedPane1.add(kdcCost, resHelper.getString("kdcCost.constraints"));
        kDTabbedPane1.add(kdcEconomy, resHelper.getString("kdcEconomy.constraints"));
        //kdcCost
kdcCost.getContentPane().setLayout(new BorderLayout(0, 0));        kdcCost.getContentPane().add(kdtCostEntries, BorderLayout.CENTER);
        //kdcEconomy
kdcEconomy.getContentPane().setLayout(new BorderLayout(0, 0));        kdcEconomy.getContentPane().add(kdtEconomyEntriese, BorderLayout.CENTER);
        kdplPayPlan.setLayout(null);        //pnlHIde
        pnlHIde.setLayout(null);        kDLabelContainer5.setBounds(new Rectangle(4, 5, 650, 70));
        pnlHIde.add(kDLabelContainer5, null);
        kDLabelContainer6.setBounds(new Rectangle(4, 83, 650, 20));
        pnlHIde.add(kDLabelContainer6, null);
        kDLabelContainer2.setBounds(new Rectangle(5, 108, 300, 20));
        pnlHIde.add(kDLabelContainer2, null);
        kDLabelContainer7.setBounds(new Rectangle(4, 138, 650, 20));
        pnlHIde.add(kDLabelContainer7, null);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtProblem, null);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDescription);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtConstractNumber);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtAttachment);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
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
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(bntSubmit);
        this.toolBar.add(btnAttachment);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("parent.longNumber", String.class, this.txtConstract, "text");
		dataBinder.registerBinding("name", String.class, this.txtConstractName, "text");
		dataBinder.registerBinding("scope", String.class, this.txtScope, "text");
		dataBinder.registerBinding("pteCost.id", com.kingdee.bos.util.BOSUuid.class, this.kdtCostEntries, "id.text");
		dataBinder.registerBinding("pteCost.costAccount.longNumber", String.class, this.kdtCostEntries, "costAccountNumber.text");
		dataBinder.registerBinding("pteCost.assignScale", java.math.BigDecimal.class, this.kdtCostEntries, "assignScale.text");
		dataBinder.registerBinding("pteCost.contractScale", java.math.BigDecimal.class, this.kdtCostEntries, "contractScale.text");
		dataBinder.registerBinding("pteCost.description", String.class, this.kdtCostEntries, "description.text");
		dataBinder.registerBinding("pteCost", com.kingdee.eas.fdc.contract.programming.PTECostInfo.class, this.kdtCostEntries, "userObject");
		dataBinder.registerBinding("pteCost.costAccount.name", String.class, this.kdtCostEntries, "costAccount.text");
		dataBinder.registerBinding("pteEnonomy.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEconomyEntriese, "id.text");
		dataBinder.registerBinding("pteEnonomy.paymentType.name", String.class, this.kdtEconomyEntriese, "paymentType.text");
		dataBinder.registerBinding("pteEnonomy.scale", java.math.BigDecimal.class, this.kdtEconomyEntriese, "scale.text");
		dataBinder.registerBinding("pteEnonomy.condition", String.class, this.kdtEconomyEntriese, "condition.text");
		dataBinder.registerBinding("pteEnonomy.description", String.class, this.kdtEconomyEntriese, "description.text");
		dataBinder.registerBinding("pteEnonomy", com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo.class, this.kdtEconomyEntriese, "userObject");
		dataBinder.registerBinding("problem", String.class, this.txtProblem, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("number", String.class, this.txtConstractNumber, "text");
		dataBinder.registerBinding("attachment", String.class, this.txtAttachment, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.PTEEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo)ov;
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
		getValidateHelper().registerBindProperty("parent.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.costAccount.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.assignScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.contractScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.costAccount.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.paymentType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.scale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.condition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("problem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachment", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output kdtCostEntries_tableClicked method
     */
    protected void kdtCostEntries_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStopped method
     */
    protected void kdtCostEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStarting method
     */
    protected void kdtCostEntries_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_activeCellChanged method
     */
    protected void kdtCostEntries_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStopping method
     */
    protected void kdtCostEntries_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editValueChanged method
     */
    protected void kdtCostEntries_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStarted method
     */
    protected void kdtCostEntries_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_tableDataRequest method
     */
    protected void kdtCostEntries_tableDataRequest(com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomyEntriese_tableClicked method
     */
    protected void kdtEconomyEntriese_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomyEntriese_editStopped method
     */
    protected void kdtEconomyEntriese_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_keyTyped method
     */
    protected void txtConstractNumber_keyTyped(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_propertyChange method
     */
    protected void txtConstractNumber_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_keyReleased method
     */
    protected void txtConstractNumber_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_keyPressed method
     */
    protected void txtConstractNumber_keyPressed(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_focusGained method
     */
    protected void txtConstractNumber_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_mouseClicked method
     */
    protected void txtConstractNumber_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_mouseReleased method
     */
    protected void txtConstractNumber_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtAttachment_mouseMoved method
     */
    protected void txtAttachment_mouseMoved(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtAttachment_mouseEntered method
     */
    protected void txtAttachment_mouseEntered(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtAttachment_mouseExited method
     */
    protected void txtAttachment_mouseExited(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("parent.longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("scope"));
    sic.add(new SelectorItemInfo("pteCost.id"));
    sic.add(new SelectorItemInfo("pteCost.costAccount.longNumber"));
    sic.add(new SelectorItemInfo("pteCost.assignScale"));
    sic.add(new SelectorItemInfo("pteCost.contractScale"));
    sic.add(new SelectorItemInfo("pteCost.description"));
        sic.add(new SelectorItemInfo("pteCost.*"));
//        sic.add(new SelectorItemInfo("pteCost.number"));
    sic.add(new SelectorItemInfo("pteCost.costAccount.name"));
    sic.add(new SelectorItemInfo("pteEnonomy.id"));
    sic.add(new SelectorItemInfo("pteEnonomy.paymentType.name"));
    sic.add(new SelectorItemInfo("pteEnonomy.scale"));
    sic.add(new SelectorItemInfo("pteEnonomy.condition"));
    sic.add(new SelectorItemInfo("pteEnonomy.description"));
        sic.add(new SelectorItemInfo("pteEnonomy.*"));
//        sic.add(new SelectorItemInfo("pteEnonomy.number"));
        sic.add(new SelectorItemInfo("problem"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("attachment"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPTEEditUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachment class
     */     
    protected class ActionAttachment extends ItemAction {     
    
        public ActionAttachment()
        {
            this(null);
        }

        public ActionAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPTEEditUI.this, "ActionAttachment", "actionAttachment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "PTEEditUI");
    }




}