package ru.hogwarts.hightschool.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {

    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

    public int getFast() {
        logger.info("Was invoked method getFast");
        return Stream.iterate(1, a -> a +1)
//                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer :: sum);
    }
}
