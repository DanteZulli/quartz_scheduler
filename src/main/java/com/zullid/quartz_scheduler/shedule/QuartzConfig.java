package com.zullid.quartz_scheduler.shedule;

import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
// import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
// import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.zullid.quartz_scheduler.config.AutowiringSpringBeanJobFactory;

// La clase QuartzConfig se encarga de configurar Quartz en el contexto de Spring Boot. 
// Define un JobDetail que invoca un método específico en un bean de Spring, 
// configura un disparador para la tarea y crea un Scheduler para planificar
//  y ejecutar las tareas según la configuración establecida.
@Configuration
public class QuartzConfig {

    private static Logger log = LoggerFactory.getLogger("QuartzConfig");
    private ApplicationContext applicationContext;
    private DataSource dataSource;

    public QuartzConfig(ApplicationContext applicationContext, DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.dataSource = dataSource;
    }

    // Este código crea una "Job Factory" que se integra con Spring y permite
    // la inyección de dependencias en los trabajos de Quartz. Es útil cuando se
    // desea acceder a otros componentes administrados por Spring desde los trabajos
    // de Quartz.
    /**
     * 
     * @return jobFactory a Factory for build jobs base on AutoWiring
     */
    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    // Este código crea y configura un objeto SchedulerFactoryBean, responsable de
    // la creación y configuración del Scheduler de Quartz. Se establecen
    // propiedades de Quartz, como el nombre de la instancia y el identificador
    // único. Además, se configuran opciones adicionales, como la capacidad de
    // sobrescribir trabajos existentes, el inicio automático del Scheduler, el
    // origen de datos, la fábrica de trabajos y la espera de finalización de
    // trabajos al apagarse.
    /**
     * 
     * @param triggers allow array of triggers
     * @return factory for build schedulers
     */
    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "PET_BILLING");
        properties.setProperty("org.quartz.scheduler.instanceId", String.valueOf(UUID.randomUUID()));
        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(true);
        schedulerFactory.setQuartzProperties(properties);
        schedulerFactory.setDataSource(dataSource);
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);
        if (ArrayUtils.isNotEmpty(triggers)) {
            schedulerFactory.setTriggers(triggers);
        }
        return schedulerFactory;
    }

    // Este método crea y configura un objeto SimpleTriggerFactoryBean que
    // representa un desencadenador (trigger) de tipo simple en Quartz.
    /**
     * 
     * @param jobDetail       Objeto que contiene los Details del Job
     * @param pollFrequencyMs Frecuencia en MS
     * @param triggerName     Nombre del Trigger
     * @return factoryBean Factory que permite construir Triggers
     */
    /*
     * static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long
     * pollFrequencyMs, String triggerName) {
     * log.debug("createTrigger(jobDetail={}, pollFrequencyMs={}, triggerName={})",
     * jobDetail.toString(),
     * pollFrequencyMs, triggerName);
     * SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
     * factoryBean.setJobDetail(jobDetail);
     * factoryBean.setStartDelay(0L);
     * factoryBean.setRepeatInterval(pollFrequencyMs);
     * factoryBean.setName(triggerName);
     * factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
     * factoryBean.setMisfireInstruction(SimpleTrigger.
     * MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
     * return factoryBean;
     * }
     */

    // Este método crea y configura un objeto CronTriggerFactoryBean que representa
    // un desencadenador cron en Quartz
    /**
     * 
     * @param jobDetail  Objeto que contiene los Details del Job
     * @param cronExpression
     * @param triggerName
     * @return Factory que permite construir el Cron Trigger
     */
    static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression, String triggerName) {
        log.debug("createCronTrigger(jobDetail={}, cronExpression={}, triggerName={})", jobDetail.toString(),
                cronExpression, triggerName);
        // To fix an issue with time-based cron jobs
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setName(triggerName);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        return factoryBean;
    }

    // Este método crea y configura un "jobDetail" en Quartz con las
    // configuraciones especificadas, incluyendo el nombre del trabajo, la clase del
    // trabajo y la durabilidad del trabajo.
    /**
     * 
     * @param jobClass La clase que contiene el Job a ejecutar
     * @param jobName  Nombre del Job
     * @return Factory que permite construir los Job Details
     */
    static JobDetailFactoryBean createJobDetail(Class<PetJob> jobClass, String jobName) {
        log.debug("createJobDetail(jobClass={}, jobName={})", jobClass.getName(), jobName);
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        return factoryBean;
    }

}