package com.zullid.quartz_scheduler.shedule;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zullid.quartz_scheduler.service.PetService;

// La API de Quartz provee una interfaz que tiene solo UN método: EJECUTAR.
// Debe ser implementada por la clase que contiene el trabajo a realizar
// La clase PetJob define la lógica a ejecutar cuando se activa el trabajo "PetJob". 
// Realiza acciones específicas del trabajo y utiliza un servicio PetService para realizar 
// el procesamiento adicional necesario.

@Component
@DisallowConcurrentExecution
public class PetJob implements Job {

    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PetService ps;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        log.info("Trabajo ** {} ** iniciado @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());

        ps.callPetProcess();

        log.info("Siguiente trabajo programado @ {}", context.getNextFireTime());
    }

}
