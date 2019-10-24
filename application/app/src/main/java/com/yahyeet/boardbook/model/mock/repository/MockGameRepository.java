package com.yahyeet.boardbook.model.mock.repository;

import com.yahyeet.boardbook.model.entity.Game;
import com.yahyeet.boardbook.model.entity.User;
import com.yahyeet.boardbook.model.repository.IGameRepository;
import com.yahyeet.boardbook.model.repository.IRepositoryListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

/**
 * Mock implementation of a game repository
 */
public class MockGameRepository extends AbstractMockRepository<Game> implements IGameRepository {
}