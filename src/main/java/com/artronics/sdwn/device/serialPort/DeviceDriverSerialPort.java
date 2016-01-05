package com.artronics.sdwn.device.serialPort;

import com.artronics.sdwn.device.DeviceDriver;
import gnu.io.CommPortIdentifier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
public class DeviceDriverSerialPort implements DeviceDriver
{
    private final static Logger log = Logger.getLogger(DeviceDriverSerialPort.class);

    private String connectionString;
    private Integer timeout;

    private CommPortIdentifier identifier;

    @Override
    public void init()
    {
        log.debug("Connection String is: \""+connectionString+"\"");
        setConnection();
    }

    private void setConnection()
    {
        Enumeration portsEnum = CommPortIdentifier.getPortIdentifiers();

        log.debug("Searching for available serial ports:");
        int count=0;
        while (portsEnum.hasMoreElements()) {
            count++;
            CommPortIdentifier port = (CommPortIdentifier) portsEnum.nextElement();

            log.debug(count + " : \""+port.getName()+"\"");
            if (port.getName().equals(connectionString)) {
                identifier = port;

                log.debug("Match found.");
                return;
            }
        }
        log.debug("No match found. Remember Connection String must be equal to com port's name");
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
