package playground;

import playground.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    ScheduleRepository scheduleRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverGroudRegistered_ScheduleCreate(@Payload GroudRegistered groudRegistered){

      if(groudRegistered.isMe()){

            System.out.println("##### listener ScheduleCreate : " + groudRegistered.toJson());
            Schedule scheduleRegistered = new Schedule();
            scheduleRegistered.setDate("20200801");
            scheduleRegistered.setGroundId(groudRegistered.getId());
            scheduleRegistered.setGroundType(groudRegistered.getGroundType());
            scheduleRegistered.setMaxPlayer(groudRegistered.getMaxPlayer());
            scheduleRegistered.setStatus("Available");

            scheduleRepository.save(scheduleRegistered);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCanceled_ScheduleCancel(@Payload BookCanceled bookCanceled){

        if(bookCanceled.isMe()){
            System.out.println("##### listener ScheduleCancel : " + bookCanceled.toJson());

            try {

                    // view 객체 조회
                    List<Schedule> scheduleList = scheduleRepository.findByGroundId(bookCanceled.getGroundId());
                    for(Schedule schedule : scheduleList){
                        // view 객체에 이벤트의 eventDirectValue 를 set 함
                        schedule.setStatus("Available");
                        schedule.setUserName("");
                        // view 레파지 토리에 save
                        scheduleRepository.save(schedule);
                    }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBooked_ScheduleAdd(@Payload Booked booked){

        if(booked.isMe()){
            System.out.println("##### listener ScheduleAdd : " + booked.toJson());

            try {
                // view 객체 조회
                List<Schedule> scheduleList = scheduleRepository.findByGroundId(booked.getGroundId());
                for(Schedule schedule : scheduleList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    schedule.setStatus(booked.getStatus());
                    schedule.setUserName(booked.getUserName());
                    // view 레파지 토리에 save
                    scheduleRepository.save(schedule);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
