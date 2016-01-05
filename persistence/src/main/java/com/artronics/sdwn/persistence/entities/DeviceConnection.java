package com.artronics.sdwn.persistence.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "device_connections")
public class DeviceConnection
{
    private Long id;

    private List<SdwnNodeEntity> nodes=new ArrayList<>();

    protected String connectionString;

    protected SdwnControllerEntity sdwnControllerEntity;

    protected Date created;
    protected Date updated;

    public DeviceConnection()
    {
    }

    public DeviceConnection(String connectionString)
    {
        this.connectionString = connectionString;
    }

    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "sdwnControllerEntity"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id",nullable = false,unique = true)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<SdwnNodeEntity> getNodes()
    {
        return nodes;
    }

    public void setNodes(List<SdwnNodeEntity> nodes)
    {
        this.nodes = nodes;
    }

    @Column(name = "connection_string",nullable = false,unique = false)
    public String getConnectionString()
    {
        return connectionString;
    }

    public void setConnectionString(String connectionString)
    {
        this.connectionString = connectionString;
    }

    @OneToOne(fetch = FetchType.EAGER)//,mappedBy = "deviceConnection")
    @PrimaryKeyJoinColumn
    public SdwnControllerEntity getSdwnControllerEntity()
    {
        return sdwnControllerEntity;
    }

    public void setSdwnControllerEntity(SdwnControllerEntity sdwnControllerEntity)
    {
        this.sdwnControllerEntity = sdwnControllerEntity;
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

    public void addSdwnNode(SdwnNodeEntity node){
        nodes.add(node);
    }
}
