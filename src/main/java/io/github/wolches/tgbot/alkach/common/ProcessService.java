package io.github.wolches.tgbot.alkach.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    public Process createProcess(List<String> tokens) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(tokens);
        return processBuilder.start();
    }

    public String getExecutionResult(Process process) throws IOException {
        byte[] bytes = new byte[256]; //Execution resuilt is limited with 64-256 symbols
        InputStream stdOut = process.getInputStream();
        int read = stdOut.read(bytes);
        return new String(Arrays.copyOfRange(bytes, 0, read));
    }
}
