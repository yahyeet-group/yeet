package com.yahyeet.boardbook.model.repository;

import com.yahyeet.boardbook.model.entity.MatchPlayer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface that defines all the methods a match team repository must implement
 */
public interface IMatchPlayerRepository extends IRepository<MatchPlayer>{
	/**
	 * Retrieves all match players that belong to a match
	 *
	 * @param id Match id
	 * @return A completable future that resolves to a list of match players
	 */
	CompletableFuture<List<MatchPlayer>> findMatchPlayersByMatchId(String id);

	/**
	 * Retrieves all match players that belong to a user
	 *
	 * @param id User id
	 * @return A completable future that resolves to a list of match players
	 */
	CompletableFuture<List<MatchPlayer>> findMatchPlayersByUserId(String id);
}
