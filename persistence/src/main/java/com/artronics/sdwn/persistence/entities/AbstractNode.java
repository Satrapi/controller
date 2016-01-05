package com.artronics.sdwn.persistence.entities;


import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractNode
{
    protected Long id;
    protected Long address;

    @Transient
    protected Long deviceId;

    public AbstractNode()
    {
    }

    public AbstractNode(Long address, Long deviceId)
    {
        this.address = address;
        this.deviceId = deviceId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Column(name = "address",nullable = false,unique = false)
    public Long getAddress()
    {
        return address;
    }

    public void setAddress(Long address)
    {
        this.address = address;
    }

    public Long getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }


    @Override
    public String toString()
    {
        return "Node: "+address.toString();
    }
}
