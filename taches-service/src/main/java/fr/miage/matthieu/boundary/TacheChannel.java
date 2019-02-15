package fr.miage.matthieu.boundary;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TacheChannel {

    @Input
    SubscribableChannel input();
}
