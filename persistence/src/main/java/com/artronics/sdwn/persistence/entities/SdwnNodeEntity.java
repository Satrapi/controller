package com.artronics.sdwn.persistence.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "nodes")
public class SdwnNodeEntity extends AbstractNode
{
    private DeviceConnection device;

    //Normal as default value
    private Type type = Type.NORMAL;
    private Status status = Status.ACTIVE;
    private int battery;

    protected Date created;
    protected Date updated;

    public SdwnNodeEntity()
    {
    }

    public SdwnNodeEntity(Long address)
    {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "device_id", nullable = false)
    public DeviceConnection getDevice()
    {
        return device;
    }

    public void setDevice(DeviceConnection device)
    {
        this.device = device;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }

    @Column(name = "battery")
    public int getBattery()
    {
        return battery;
    }

    public void setBattery(int battery)
    {
        this.battery = battery;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated(Date updated)
    {
        this.updated = updated;
    }

    @PrePersist
    protected void onCreate()
    {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate()
    {
        updated = new Date();
    }

    public enum Type
    {
        SINK,
        NORMAL
    }

    public enum Status
    {
        ACTIVE,
        DISABLE,
    }

    @Override
    public int hashCode()
    {
        //use getters for getting fields(for ORM) see this SO answer:
        //http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java
        Long devId = getDeviceId();
        Long add = getAddress();

        int result =17;

        int addr = (int) (add ^ (add >>> 32));
        int devIdi =devId==null ? 0: (int) (devId ^ (devId >>> 32));

        result+=addr+devIdi;

        return 31*result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof SdwnNodeEntity))
            return false;
        if (obj == this)
            return true;

        SdwnNodeEntity rhs = (SdwnNodeEntity) obj;
        return this.getAddress().equals(rhs.getAddress()) &&
                this.getDeviceId().equals(rhs.getDeviceId());

    }
}
