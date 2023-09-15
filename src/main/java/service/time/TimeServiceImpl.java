package service.time;

import java.time.LocalDateTime;

public class TimeServiceImpl implements TimeService {

    public LocalDateTime getTime(){
        return LocalDateTime.now();
    };
}
