package com.artronics.sdwn.controller.initializer;

import com.artronics.sdwn.controller.SdwnController;
import com.artronics.sdwn.controller.session.SessionManager;
import com.artronics.sdwn.domain.entities.SdwnControllerEntity;
import com.artronics.sdwn.domain.repositories.SdwnControllerRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SdwnApplicationInitializer implements ApplicationRunner,
                                                   ApplicationListener<ContextRefreshedEvent>,
                                                   DisposableBean
{
    private final static Logger log = Logger.getLogger(SdwnApplicationInitializer.class);

    private SessionManager sessionManager;

    private SdwnController sdwnController;

    private SdwnControllerEntity controllerEntity;

    private SdwnControllerRepo controllerRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        sdwnController.start();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
    }

    @Override
    public void destroy() throws Exception
    {
        log.debug("Destroying Controller context.");
        log.debug("Change Controller status to SHUTDOWN");
        controllerEntity.setStatus(SdwnControllerEntity.Status.SHUTDOWN);
        controllerRepo.save(controllerEntity);

        sdwnController.stop();

        sessionManager.close();
    }

    public void setSessionManager(SessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    @Autowired
    public void setSdwnController(SdwnController sdwnController)
    {
        this.sdwnController = sdwnController;
    }

    @Autowired
    public void setControllerEntity(
            SdwnControllerEntity controllerEntity)
    {
        this.controllerEntity = controllerEntity;
    }

    @Autowired
    public void setControllerRepo(
            SdwnControllerRepo controllerRepo)
    {
        this.controllerRepo = controllerRepo;
    }

}
