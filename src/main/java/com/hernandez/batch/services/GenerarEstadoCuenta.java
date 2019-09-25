package com.hernandez.batch.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerarEstadoCuenta {
	private static final Logger LOG = LoggerFactory.getLogger(GenerarEstadoCuenta.class);

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	public void executeJob() throws Exception {
		LOG.info("Inicio: " + new Date());
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
		jobLauncher.run(job, jobParameters);

	}
}
