package org.linkedprocess.farm;

import org.linkedprocess.LinkedProcess;
import org.linkedprocess.farm.os.Vm;
import org.linkedprocess.farm.os.VmScheduler;
import org.linkedprocess.farm.os.errors.VmNotFoundException;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 * @version LoPSideD 0.1
 */
public class StatusEventHandler implements VmScheduler.LopStatusEventHandler {

    protected Farm farm;

    public StatusEventHandler(Farm farm) {
        this.farm = farm;
    }

    public void schedulerStatusChanged(LinkedProcess.Status status) {
        this.farm.sendPresence(status, Farm.STATUS_MESSAGE);
    }

    public void virtualMachineStatusChanged(String vmId, LinkedProcess.Status status) {
        try {

            Vm vm = this.farm.getVm(vmId);
            if (status == LinkedProcess.Status.INACTIVE) {
                vm.terminateSelf();
            } else {
                // TODO:?
            }

        } catch (VmNotFoundException e) {
            Farm.LOGGER.severe(e.getMessage());
        }
    }
}
