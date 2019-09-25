package com.techshard.batch.configuration;

import com.techshard.batch.dao.entity.Cliente;
import com.techshard.batch.dao.entity.Voltage;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	private static final String QUERY = "SELECT id, nombre, apellido, email FROM clientes ";

	@Bean
	public ItemReader<Cliente> reader(DataSource dataSource) {
		JdbcCursorItemReader<Cliente> databaseReader = new JdbcCursorItemReader<>();

		databaseReader.setDataSource(dataSource);
		databaseReader.setSql(QUERY);
		databaseReader.setRowMapper(new BeanPropertyRowMapper<>(Cliente.class));

		return databaseReader;
	}

//    @Bean
//    public VoltageProcessor processor() {
//        return new VoltageProcessor();
//    }

	@Bean
	ItemProcessor<Cliente, Voltage> processor() {
		return new VoltageProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Voltage> writer(final DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Voltage>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO voltage (nombre, apellido) VALUES (:nombre, :apellido)").dataSource(dataSource)
				.build();
	}

	@Bean
	public Job importVoltageJob(NotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importVoltageJob").incrementer(new RunIdIncrementer()).listener(listener)
				.flow(step1).end().build();
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Voltage> writer, ItemReader<Cliente> reader,
			ItemProcessor<Cliente, Voltage> processor) {
		return stepBuilderFactory.get("step1").<Cliente, Voltage>chunk(10).reader(reader).processor(processor)
				.writer(writer).build();
	}
}
