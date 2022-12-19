package io.github.wolches.tgbot.alkach.service.message.command;

import io.github.wolches.tgbot.alkach.domain.model.Chat;
import io.github.wolches.tgbot.alkach.domain.model.ChatUser;
import io.github.wolches.tgbot.alkach.service.util.TextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecCommandService implements CommandProcessingService {

    private static final String EXEC_COMMAND = "/exec";

    private static final String OPERATION_NOT_PERMITTED_MESSAGE =   "Operation not permitted!";
    private static final String EXCEPTION_OCCURED_MESSAGE =         "An exception has occured, check logs for additional info.";
    private static final String EXECUTION_RESULT_MESSAGE =          "Execution result:" +
                                                                    "\r\n%s";

    private final TextService textService;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        try {
            if (user.getSettings().isAdmin()) {
                List<String> commandArguments = textService.getCommandArguments(EXEC_COMMAND, message.getText());
                Process process = createProcess(commandArguments);
                return String.format(EXECUTION_RESULT_MESSAGE, getExecutionResult(process));
            } else {
                return OPERATION_NOT_PERMITTED_MESSAGE;
            }
        } catch (Throwable e) {
            log.error("An exception has occured when executing '{}'", message.getText(), e);
            return EXCEPTION_OCCURED_MESSAGE;
        }
    }

    @Override
    public String getCommandString() {
        return EXEC_COMMAND;
    }

    private Process createProcess(List<String> tokens) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(tokens);
        return processBuilder.start();
    }

    private String getExecutionResult(Process process) throws IOException {
        byte[] bytes = new byte[256]; //Execution resuilt is limited with 64-256 symbols
        InputStream stdOut = process.getInputStream();
        int read = stdOut.read(bytes);
        return new String(Arrays.copyOfRange(bytes, 0, read));
    }
}
