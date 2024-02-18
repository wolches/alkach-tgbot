package io.github.wolches.tgbot.alkach.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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
        return getExecutionResult(process, Charset.defaultCharset());
    }

    /**
     * Gets the execution result from the given process using the specified charset.
     *
     * @param  process  the process from which to retrieve the execution result
     * @param  charset  the character set to use for decoding the result
     * @return          the execution result as a string
     */
    public String getExecutionResult(Process process, Charset charset) throws IOException {
        try (InputStream stdOut = process.getInputStream()) {
            byte[] bytes = stdOut.readNBytes(256);
            String result = new String(bytes, charset);
            //System.out.println(result);
            return result;
        }
    }
}
