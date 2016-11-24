package org.cloudsimplus.builders;

import java.util.ArrayList;
import java.util.List;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterSimple;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.DatacenterCharacteristicsSimple;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.resources.FileStorage;

/**
 * A Builder class to createDatacenter {@link DatacenterSimple} objects.
 *
 * @author Manoel Campos da Silva Filho
 */
public class DatacenterBuilder extends Builder {
    public static final String DATACENTER_NAME_FORMAT = "Datacenter%d";
    private final SimulationScenarioBuilder scenario;

    private double costPerBwMegabit = 0.0;
    private double costPerCpuSecond = 3.0;
    private double costPerStorage = 0.001;
    private double costPerMem = 0.05;
    private double schedulingInterval = 1;
    private double timezone = 10;

    private final List<Datacenter> datacenters;
    private int numberOfCreatedDatacenters;
	private List<FileStorage> storageList;

	public DatacenterBuilder(SimulationScenarioBuilder scenario) {
	    this.scenario = scenario;
        this.datacenters = new ArrayList<>();
		this.storageList = new ArrayList<>();
        this.numberOfCreatedDatacenters = 0;
    }

    public List<Datacenter> getDatacenters() {
        return datacenters;
    }

    public Datacenter get(final int index) {
        if(index >= 0 && index < datacenters.size())
            return datacenters.get(index);

        return Datacenter.NULL;
    }

    public Host getHostOfDatacenter(final int hostIndex, final int datacenterIndex){
        return get(datacenterIndex).getHost(hostIndex);
    }

    public Host getFirstHostFromFirstDatacenter(){
        return getHostOfDatacenter(0,0);
    }

    public DatacenterBuilder createDatacenter(final List<Host> hosts) {
        if (hosts == null || hosts.isEmpty()) {
            throw new RuntimeException("The hosts parameter has to have at least 1 host.");
        }

        DatacenterCharacteristics characteristics =
                new DatacenterCharacteristicsSimple (hosts)
                      .setTimeZone(timezone)
                      .setCostPerSecond(costPerCpuSecond)
                      .setCostPerMem(costPerMem)
                      .setCostPerStorage(costPerStorage)
                      .setCostPerBw(costPerBwMegabit);
        String name = String.format(DATACENTER_NAME_FORMAT, numberOfCreatedDatacenters++);
        Datacenter datacenter =
                new DatacenterSimple(scenario.getSimulation(), characteristics, new VmAllocationPolicySimple())
                    .setStorageList(storageList)
                    .setSchedulingInterval(schedulingInterval);
        datacenter.setName(name);
        this.datacenters.add(datacenter);
        return this;
    }

    public double getCostPerBwMegabit() {
        return costPerBwMegabit;
    }

    public DatacenterBuilder setCostPerBwMegabit(double defaultCostPerBwByte) {
        this.costPerBwMegabit = defaultCostPerBwByte;
        return this;
    }

    public double getCostPerCpuSecond() {
        return costPerCpuSecond;
    }

    public DatacenterBuilder setCostPerCpuSecond(double defaultCostPerCpuSecond) {
        this.costPerCpuSecond = defaultCostPerCpuSecond;
        return this;
    }

    public double getCostPerStorage() {
        return costPerStorage;
    }

    public DatacenterBuilder setCostPerStorage(double defaultCostPerStorage) {
        this.costPerStorage = defaultCostPerStorage;
        return this;
    }

    public double getCostPerMem() {
        return costPerMem;
    }

    public DatacenterBuilder setCostPerMem(double defaultCostPerMem) {
        this.costPerMem = defaultCostPerMem;
        return this;
    }

    public double getTimezone() {
        return timezone;
    }

    public DatacenterBuilder setTimezone(double defaultTimezone) {
        this.timezone = defaultTimezone;
        return this;
    }

    public double getSchedulingInterval() {
        return schedulingInterval;
    }

    public DatacenterBuilder setSchedulingInterval(double schedulingInterval) {
        this.schedulingInterval = schedulingInterval;
        return this;
    }

	public DatacenterBuilder setStorageList(List<FileStorage> storageList) {
		this.storageList = storageList;
		return this;
	}

	public DatacenterBuilder addStorageToList(FileStorage storage) {
		this.storageList.add(storage);
		return this;
	}

}
