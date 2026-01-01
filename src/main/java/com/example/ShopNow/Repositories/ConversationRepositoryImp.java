package com.example.ShopNow.Repositories;

import com.example.ShopNow.Models.Chats.Conversation;
import com.example.ShopNow.Models.Chats.ConversationParticipant;
import com.example.ShopNow.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ConversationRepositoryImp implements   ConversationRepository{
    @Autowired
    public ConversationPartRepository conversationPartRepository;

    @Autowired
    public UserRepository userRepository;

//    @Override
//    public Conversation findDirectConversation(int user1,int user2) {
//        Optional<User> use1=userRepository.findById(user1);
//        Optional<ConversationParticipant> user1Data=conversationPartRepository.findByUser(use1);
//        Optional<User> use2=userRepository.findById(user2);
//        Optional<ConversationParticipant> user2Data=conversationPartRepository.findByUser(use2);
//        return null;
//    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Conversation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Conversation> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Conversation> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Conversation getOne(Integer integer) {
        return null;
    }

    @Override
    public Conversation getById(Integer integer) {
        return null;
    }

    @Override
    public Conversation getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Conversation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Conversation> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Conversation> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Conversation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Conversation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Conversation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Conversation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Conversation> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Conversation> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Conversation> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Conversation> findAll() {
        return List.of();
    }

    @Override
    public List<Conversation> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Conversation entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Conversation> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Conversation> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Conversation> findAll(Pageable pageable) {
        return null;
    }
}
