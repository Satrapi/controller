package com.artronics.sdwn.device.serialPort;

import com.artronics.sdwn.device.DeviceDriver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeviceDriverSerialPort implements DeviceDriver
{
    private final static Logger log = Logger.getLogger(DeviceDriverSerialPort.class);

    private String connectionString;
    private Integer timeout;

    @Override
    public void init()
    {

    }

    @Value("${com.artronics.sdwn.device.connection.connection_string}")
    public void setConnectionString(String connectionString)
    {
        this.connectionString = connectionString;
    }

    @Value("${com.artronics.sdwn.device.connection.timeout}")
    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }
}
