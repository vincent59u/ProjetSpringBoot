package fr.miage.matthieu.boundary;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TacheChannel {
    @Output
    MessageChannel output();
}
