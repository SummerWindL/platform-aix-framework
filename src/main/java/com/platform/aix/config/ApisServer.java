package com.platform.aix.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ApisServer {

    private String ip = "127.0.0.1";

    private int port = 18060;

    private int threadCount = 8;

}