package gov.lanl.cnls.linkedprocess.xmpp.lopfarm;

import org.jdom.Element;
import gov.lanl.cnls.linkedprocess.LinkedProcess;

/**
 * User: marko
 * Date: Jun 25, 2009
 * Time: 2:25:52 PM
 */
public class TerminateVm extends FarmIq {

    public String getChildElementXML() {

        Element terminateVmElement = new Element(LinkedProcess.TERMINATE_VM_TAG, LinkedProcess.LOP_FARM_NAMESPACE);
        if(this.vmJid != null) {
            terminateVmElement.setAttribute(LinkedProcess.VM_JID_ATTRIBUTE, this.vmJid);
        }

        return LinkedProcess.xmlOut.outputString(terminateVmElement);
    }
}