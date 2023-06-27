package com.zullid.quartz_scheduler.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * This JobFactory autowires automatically the created quartz bean with spring @Autowired dependencies.
 * 
 * @author jelies https://gist.github.com/jelies/5085593
 * 
 */
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory {

	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
	}

	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}
}
