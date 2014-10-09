package ru.acs.cats.service;

import ru.acs.entities.CatEntity;

public interface CatsService {
	public CatEntity create( CatEntity cat );
	public CatEntity findById( int id );
	public Integer update( CatEntity cat ) throws Exception;
}
