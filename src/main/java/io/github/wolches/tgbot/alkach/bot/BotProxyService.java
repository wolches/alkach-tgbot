package io.github.wolches.tgbot.alkach.bot;

import io.github.wolches.tgbot.alkach.authorization.AuthorizationRequired;
import io.github.wolches.tgbot.alkach.bot.contract.BotApi;
import io.github.wolches.tgbot.alkach.domain.persistence.model.chat.ChatUser;
import lombok.Getter;
import lombok.SneakyThrows;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.stickers.AddStickerToSet;
import org.telegram.telegrambots.meta.api.methods.stickers.CreateNewStickerSet;
import org.telegram.telegrambots.meta.api.methods.stickers.SetStickerSetThumb;
import org.telegram.telegrambots.meta.api.methods.stickers.UploadStickerFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

//@AuthorizationRequired
@Component
public class BotProxyService extends AbsSender implements BotApi {

    private BotInstance bot;

    @Getter
    private boolean initialized = false;

    public void setBot(BotInstance bot) {
        if (!initialized && this.bot == null) {
            this.bot = bot;
            initialized = true;
        }
    }

    @SneakyThrows
    public boolean isChatUserAdmin(ChatUser chatUser) {
        return bot.isUserAdmin(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }

    public boolean isChatUserActive(ChatUser chatUser) {
        return bot.isChatUserActive(chatUser.getChat().getTelegramId(), chatUser.getUser().getTelegramId());
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException {
        return bot.execute(setChatPhoto);
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        return bot.execute(sendMediaGroup);
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException {
        return bot.execute(addStickerToSet);
    }

    @Override
    public Boolean execute(SetStickerSetThumb setStickerSetThumb) throws TelegramApiException {
        return bot.execute(setStickerSetThumb);
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        return bot.execute(createNewStickerSet);
    }

    @Override
    public File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        return bot.execute(uploadStickerFile);
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException {
        return bot.execute(editMessageMedia);
    }

    @Override
    public Message execute(SendAnimation sendAnimation) throws TelegramApiException {
        return bot.execute(sendAnimation);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        return bot.executeAsync(sendDocument);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        return bot.executeAsync(sendPhoto);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        return bot.executeAsync(sendVideo);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        return bot.executeAsync(sendVideoNote);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        return bot.executeAsync(sendSticker);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        return bot.executeAsync(sendAudio);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        return bot.executeAsync(sendVoice);
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        return bot.executeAsync(sendMediaGroup);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        return bot.executeAsync(setChatPhoto);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet) {
        return bot.executeAsync(addStickerToSet);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumb setStickerSetThumb) {
        return bot.executeAsync(setStickerSetThumb);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        return bot.executeAsync(createNewStickerSet);
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        return bot.executeAsync(uploadStickerFile);
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        return bot.executeAsync(editMessageMedia);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        return bot.executeAsync(sendAnimation);
    }

    @Override
    protected <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void sendApiMethodAsync(Method method, Callback callback) {
        throw new NotYetImplementedException();
    }

    @Override
    protected <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> sendApiMethodAsync(Method method) {
        throw new NotYetImplementedException();
    }

    @Override
    protected <T extends Serializable, Method extends BotApiMethod<T>> T sendApiMethod(Method method) throws TelegramApiException {
        throw new NotYetImplementedException();
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>, Callback extends SentCallback<T>> void executeAsync(Method method, Callback callback) throws TelegramApiException {
        bot.executeAsync(method, callback);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        return bot.executeAsync(method);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        return bot.execute(method);
    }

    @Override
    public Message execute(SendDocument sendDocument) throws TelegramApiException {
        return bot.execute(sendDocument);
    }

    @Override
    public Message execute(SendPhoto sendPhoto) throws TelegramApiException {
        return bot.execute(sendPhoto);
    }

    @Override
    public Message execute(SendVideo sendVideo) throws TelegramApiException {
        return bot.execute(sendVideo);
    }

    @Override
    public Message execute(SendVideoNote sendVideoNote) throws TelegramApiException {
        return bot.execute(sendVideoNote);
    }

    @Override
    public Message execute(SendSticker sendSticker) throws TelegramApiException {
        return bot.execute(sendSticker);
    }

    @Override
    public Message execute(SendAudio sendAudio) throws TelegramApiException {
        return bot.execute(sendAudio);
    }

    @Override
    public Message execute(SendVoice sendVoice) throws TelegramApiException {
        return bot.execute(sendVoice);
    }
}
