package com.zullid.quartz_scheduler.shedule;

import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

// Esta clase de configuración define los detalles del trabajo (jobDetails) y 
// los desencadenadores (triggers) asociados para "PetJob". 
// Los detalles del trabajo se crean a través del método jobMemberClassPet y 
// los desencadenadores se crean a través del método triggerMemberClassPet, 
// utilizando métodos de configuración auxiliares de QuartzConfig.
@Configuration
public class QuartzSchedulerJobs {

    @Value("${pet.cron}")
    private String CRON_PET;
    Logger log = LoggerFactory.getLogger(getClass());

    // Crea y devuelve un objeto JobDetailFactoryBean para el trabajo "PetJob" de la
    // clase especificada.
    /**
     * 
     * @return un trabajo de una clase especificada
     */
    @Bean(name = "memberClassPet")
    public JobDetailFactoryBean jobMemberClassPet() {
        return QuartzConfig.createJobDetail(PetJob.class, "Clase PetJob");
    }

    // Crea y devuelve un objeto CronTriggerFactoryBean para el desencadenador
    // (trigger) del trabajo "PetJob" de la clase especificada.
    /*
     * 
     * 
     * @param jobDetail el detalle del trabajo asociado al desencadenador
     * 
     * @return un desencadenador (trigger) para el trabajo "PetJob"
     */
    @Bean(name = "memberClassPetTrigger")
    public CronTriggerFactoryBean triggerMemberClassPet(@Qualifier("memberClassPet") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_PET, "Clase PetTrigger");
    }

}