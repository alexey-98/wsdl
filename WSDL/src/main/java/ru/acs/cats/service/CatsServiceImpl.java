package ru.acs.cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ru.acs.entities.CatEntity;
import ru.acs.repositories.CatRepository;

public class CatsServiceImpl implements CatsService {
	@Autowired
	private CatRepository catRepository;

	@Transactional
	public CatEntity create(CatEntity cat) {		
		return catRepository.save(cat);
	}

	@Override
	public CatEntity findById(int id) {
		return catRepository.findOne(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(CatEntity cat) throws Exception {
		CatEntity updatedCat = catRepository.findOne(cat.getId());

		if (null == updatedCat)
			throw new Exception("Cat not found");

		updatedCat.setUser(cat.getUser());
		;
		updatedCat.setDescription(cat.getDescription());
		updatedCat.setContent(cat.getContent());
		catRepository.save(updatedCat);

		return updatedCat.getId();
	}

}
