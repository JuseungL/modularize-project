package com.juseungl.modulebatch.batch;

import com.juseungl.moduledomain.domains.member.domain.AfterEntity;
import com.juseungl.moduledomain.domains.member.domain.BeforeEntity;
import com.juseungl.moduledomain.domains.member.repository.AfterEntityRepository;
import com.juseungl.moduledomain.domains.member.repository.BeforeEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ChunkBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final BeforeEntityRepository beforeEntityRepository;
    private final AfterEntityRepository afterEntityRepository;

    @Bean
    public Job transferDataJob() {
        System.out.println("transfer data job");
        return new JobBuilder("transferDataJob", jobRepository)
                .start(transferDataStep())
                .build();
    }

    @Bean
    public Step transferDataStep() {
        System.out.println("transferData");
        return new StepBuilder("transferData", jobRepository)
                .<BeforeEntity, AfterEntity> chunk(10, platformTransactionManager)
                .reader(beforeReader())
                .processor(middleProcessor())
                .writer(afterWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<BeforeEntity> beforeReader() {
        return new RepositoryItemReaderBuilder<BeforeEntity>()
                .name("beforeReader")
                .pageSize(10)
                .methodName("findAll")
                .repository(beforeEntityRepository)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<BeforeEntity, AfterEntity> middleProcessor() {
        return new ItemProcessor<BeforeEntity, AfterEntity>() {
            @Override
            public AfterEntity process(BeforeEntity item) throws Exception {
                AfterEntity afterEntity = AfterEntity.builder()
                        .username(item.getUsername())
                        .build();
                return afterEntity;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<AfterEntity> afterWriter() {
        return new RepositoryItemWriterBuilder<AfterEntity>()
                .repository(afterEntityRepository)
                .methodName("save")
                .build();
    }
}
