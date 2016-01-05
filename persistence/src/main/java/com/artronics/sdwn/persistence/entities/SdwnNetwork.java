package com.artronics.sdwn.persistence.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "networks")
public class SdwnNetwork
{
    private  Long id;

    private List<SdwnControllerEntity> controllers=new ArrayList<>();

    protected String ip;

    protected Date created;
    protected Date updated;

    public SdwnNetwork()
    {
    }

    public SdwnNetwork(String ip)
    {
        this.ip = ip;
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

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public List<SdwnControllerEntity> getControllers()
    {
        return controllers;
    }

    public void setControllers(
            List<SdwnControllerEntity> controllers)
    {
        this.controllers = controllers;
    }

    @Column(name = "ip",nullable = false,unique = true)
    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
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

    public void addSdwnController(SdwnControllerEntity controller)
    {
        controllers.add(controller);
    }
}
