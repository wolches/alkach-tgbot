package io.github.wolches.tgbot.alkach.bot.contract.authorization;

import org.hibernate.cfg.NotYetImplementedException;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@AuthorizationRequired
public class AbsSenderProxy extends AbsSender {

    private final DefaultAbsSender delegate;

    public AbsSenderProxy(DefaultAbsSender delegate) {
        this.delegate = delegate;
    }

    @Override
    public Boolean execute(SetChatPhoto setChatPhoto) throws TelegramApiException {
        return delegate.execute(setChatPhoto);
    }

    @Override
    public List<Message> execute(SendMediaGroup sendMediaGroup) throws TelegramApiException {
        return delegate.execute(sendMediaGroup);
    }

    @Override
    public Boolean execute(AddStickerToSet addStickerToSet) throws TelegramApiException {
        return delegate.execute(addStickerToSet);
    }

    @Override
    public Boolean execute(SetStickerSetThumb setStickerSetThumb) throws TelegramApiException {
        return delegate.execute(setStickerSetThumb);
    }

    @Override
    public Boolean execute(CreateNewStickerSet createNewStickerSet) throws TelegramApiException {
        return delegate.execute(createNewStickerSet);
    }

    @Override
    public File execute(UploadStickerFile uploadStickerFile) throws TelegramApiException {
        return delegate.execute(uploadStickerFile);
    }

    @Override
    public Serializable execute(EditMessageMedia editMessageMedia) throws TelegramApiException {
        return delegate.execute(editMessageMedia);
    }

    @Override
    public Message execute(SendAnimation sendAnimation) throws TelegramApiException {
        return delegate.execute(sendAnimation);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendDocument sendDocument) {
        return delegate.executeAsync(sendDocument);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendPhoto sendPhoto) {
        return delegate.executeAsync(sendPhoto);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideo sendVideo) {
        return delegate.executeAsync(sendVideo);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVideoNote sendVideoNote) {
        return delegate.executeAsync(sendVideoNote);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendSticker sendSticker) {
        return delegate.executeAsync(sendSticker);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAudio sendAudio) {
        return delegate.executeAsync(sendAudio);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendVoice sendVoice) {
        return delegate.executeAsync(sendVoice);
    }

    @Override
    public CompletableFuture<List<Message>> executeAsync(SendMediaGroup sendMediaGroup) {
        return delegate.executeAsync(sendMediaGroup);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetChatPhoto setChatPhoto) {
        return delegate.executeAsync(setChatPhoto);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(AddStickerToSet addStickerToSet) {
        return delegate.executeAsync(addStickerToSet);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(SetStickerSetThumb setStickerSetThumb) {
        return delegate.executeAsync(setStickerSetThumb);
    }

    @Override
    public CompletableFuture<Boolean> executeAsync(CreateNewStickerSet createNewStickerSet) {
        return delegate.executeAsync(createNewStickerSet);
    }

    @Override
    public CompletableFuture<File> executeAsync(UploadStickerFile uploadStickerFile) {
        return delegate.executeAsync(uploadStickerFile);
    }

    @Override
    public CompletableFuture<Serializable> executeAsync(EditMessageMedia editMessageMedia) {
        return delegate.executeAsync(editMessageMedia);
    }

    @Override
    public CompletableFuture<Message> executeAsync(SendAnimation sendAnimation) {
        return delegate.executeAsync(sendAnimation);
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
        delegate.executeAsync(method, callback);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> CompletableFuture<T> executeAsync(Method method) throws TelegramApiException {
        return delegate.executeAsync(method);
    }

    @Override
    public <T extends Serializable, Method extends BotApiMethod<T>> T execute(Method method) throws TelegramApiException {
        return delegate.execute(method);
    }

    @Override
    public Message execute(SendDocument sendDocument) throws TelegramApiException {
        return delegate.execute(sendDocument);
    }

    @Override
    public Message execute(SendPhoto sendPhoto) throws TelegramApiException {
        return delegate.execute(sendPhoto);
    }

    @Override
    public Message execute(SendVideo sendVideo) throws TelegramApiException {
        return delegate.execute(sendVideo);
    }

    @Override
    public Message execute(SendVideoNote sendVideoNote) throws TelegramApiException {
        return delegate.execute(sendVideoNote);
    }

    @Override
    public Message execute(SendSticker sendSticker) throws TelegramApiException {
        return delegate.execute(sendSticker);
    }

    @Override
    public Message execute(SendAudio sendAudio) throws TelegramApiException {
        return delegate.execute(sendAudio);
    }

    @Override
    public Message execute(SendVoice sendVoice) throws TelegramApiException {
        return delegate.execute(sendVoice);
    }
}
