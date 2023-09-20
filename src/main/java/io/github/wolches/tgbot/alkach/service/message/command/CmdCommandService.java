package io.github.wolches.tgbot.alkach.service.message.command;

import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.Chat;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import io.github.wolches.tgbot.alkach.service.common.ProcessService;
import io.github.wolches.tgbot.alkach.util.TextService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class CmdCommandService implements CommandProcessingService {

    private static final String CMD_COMMAND = "/cmd";

    private static final String OPERATION_NOT_PERMITTED_MESSAGE =   "Operation not permitted!";
    private static final String EXCEPTION_OCCURED_MESSAGE =         "An exception has occured, check logs for additional info.";
    private static final String EXECUTION_RESULT_MESSAGE =          "Execution result:" +
                                                                    "```\r\n%s```";

    private static final String[] CMD = {"C:\\Windows\\System32\\cmd.exe", "/C"};

    private final TextService textService;
    private final ProcessService processService;

    @Override
    public String processMessageInternal(Message message, Chat chat, ChatUser user) {
        try {
            if (user.getUser().getSettings().isAdmin()) {
                String[] commandArguments = textService.getCommandArguments(CMD_COMMAND, message.getText());
                ArrayList<String> exec = new ArrayList<>();
                exec.addAll(Arrays.asList(CMD));
                exec.addAll(Arrays.asList(commandArguments));
                Process process = processService.createProcess(exec);
                return String.format(EXECUTION_RESULT_MESSAGE, processService.getExecutionResult(process));
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
        return CMD_COMMAND;
    }
}
