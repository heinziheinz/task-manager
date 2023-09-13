package model;

import java.time.LocalDateTime;

public class CurrentTimeImpl implements CurrentTime{

    public LocalDateTime getCurrentTime(){
        return LocalDateTime.now();
    };
}
