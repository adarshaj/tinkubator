/*
 * Copyright (c) 2009. The LoPSideD implementation of the Linked Process
 * protocol is an open-source project founded at the Center for Nonlinear Studies
 * at the Los Alamos National Laboratory in Los Alamos, New Mexico. Please visit
 * http://linkedprocess.org and LICENSE.txt for more information.
 */

package org.linkedprocess.villein.proxies;

import org.jdom.Document;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.packet.DiscoverItems;
import org.linkedprocess.Jid;
import org.linkedprocess.villein.Dispatcher;
import org.linkedprocess.villein.Villein;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A RegistryProxy is a proxy to a registry. A registry is an XMPP client and is identified by a full-qualified JID.
 * The bare JID of a registry is the registry's countryside. The purpose of a registry is to maintain a list of countrysides with active farms.
 * A registry is a good starting place for identifying countrysides and expanding your countryside's roster and thus, access to compute cycles.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @version LoPSideD 0.1
 */
public class RegistryProxy extends XmppProxy {

    private Set<DiscoverItems.Item> discoItems;

    public RegistryProxy(final Jid jid, final Dispatcher dispatcher) {
        this.jid = jid;
        this.dispatcher = dispatcher;
        this.refreshDiscoInfo();
        this.refreshDiscoItems();


    }

    public RegistryProxy(final Jid jid, final Dispatcher dispatcher, final Document discoInfoDocument) {
        this.jid = jid;
        this.dispatcher = dispatcher;
        this.discoInfoDocument = discoInfoDocument;
        this.refreshDiscoItems();

    }

    public void refreshDiscoItems() {
        try {

            this.discoItems = new HashSet<DiscoverItems.Item>();
            ServiceDiscoveryManager discoManager = this.dispatcher.getServiceDiscoveryManager();
            DiscoverItems discoItems = discoManager.discoverItems(this.jid.toString());
            Iterator<DiscoverItems.Item> itty = discoItems.getItems();
            while (itty.hasNext()) {
                this.discoItems.add(itty.next());
            }
        } catch (Exception e) {
            Villein.LOGGER.warning("Problem loading disco#items: " + e.getMessage());
        }
    }

    public Set<CountrysideProxy> getActiveCountrysides() {
        Set<CountrysideProxy> countrysideProxies = new HashSet<CountrysideProxy>();
        for (DiscoverItems.Item item : discoItems) {
            CountrysideProxy countrysideProxy = new CountrysideProxy(new Jid(item.getEntityID()));
            countrysideProxies.add(countrysideProxy);
        }
        return countrysideProxies;
    }

}
