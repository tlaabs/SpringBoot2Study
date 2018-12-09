package com.community.batch.jobs;

import com.community.batch.domain.User;
import com.community.batch.domain.enums.UserStatus;
import com.community.batch.jobs.inactive.listener.InactiveStepListener;
import com.community.batch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Configuration
public class InactiveUserJobConfig {

    private final static int CHUNK_SIZE = 15;
    private final EntityManagerFactory entityManagerFactory;

    private UserRepository userRepository;

    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep){
        return jobBuilderFactory.get("inactiveUserJob")
                .preventRestart()
                .start(inactiveJobStep)
                .build();
    }

    @Bean
    public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory, ListItemReader<User> inactiveUserReader,InactiveStepListener inactiveStepListener){
        return stepBuilderFactory.get("inactiveUserStep")
                .<User, User> chunk(CHUNK_SIZE) //커밋단위
                .reader(inactiveUserReader)
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .listener(inactiveStepListener)
                .build();
    }

    /*
    @Bean(destroyMethod = "")
    @StepScope
    public JpaPagingItemReader<User> inactiveUserJpaReader(){
        JpaPagingItemReader<User> jpaPagingItemReader =
                //https://jojoldu.tistory.com/337 페이징 문제
                new JpaPagingItemReader(){
                    @Override
                    public int getPage() {
                        return 0;
                    }
                };
        jpaPagingItemReader.setQueryString("select u from User as u where u.updatedDate < :updatedDate and u.status = :status");

        Map<String, Object> map = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        map.put("updatedDate", now.minusYears(1));
        map.put("status", UserStatus.ACTIVE);

        jpaPagingItemReader.setParameterValues(map);

        jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
        jpaPagingItemReader.setPageSize(CHUNK_SIZE);
        return jpaPagingItemReader;
    }
    */

    @Bean
    @StepScope //각 Step의 실행마다 새로 빈을 만들기 때문에 지연 생성이 가능하다?
    public ListItemReader<User> inactiveUserReader(@Value("#{jobParameters[nowDate]}") Date nowDate, UserRepository userRepository){
        LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());
        List<User> inactiveUsers =
                userRepository.findByUpdatedDateBeforeAndStatusEquals(LocalDateTime.now().minusYears(1), UserStatus.ACTIVE);
        return new ListItemReader<>(inactiveUsers);
    }

    public ItemProcessor<User, User> inactiveUserProcessor(){
//        return User::setInactive; 메서드 레퍼런스?
        return new ItemProcessor<User, User>() {
            @Override
            public User process(User user) throws Exception {
                return user.setInactive();
            }
        };
    }

//    public ItemWriter<User> inactiveUserWriter(){
//        return ((List<? extends User> users) -> userRepository.saveAll(users));
//    }

    private JpaItemWriter<User> inactiveUserWriter(){
        JpaItemWriter<User> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);
        return jpaItemWriter;
    }
}
