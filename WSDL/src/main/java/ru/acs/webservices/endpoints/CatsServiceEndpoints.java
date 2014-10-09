package ru.acs.webservices.endpoints;

import javax.annotation.Resource;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import ru.acs.cat.Cat;
import ru.acs.cats.service.CatsService;
import ru.acs.entities.CatEntity;
import ru.acs.webservices.getcatservices.GetCatRequest;
import ru.acs.webservices.getcatservices.GetCatResponse;
import ru.acs.webservices.savecatservices.SaveCatRequest;
import ru.acs.webservices.savecatservices.SaveCatResponse;

@Endpoint
public class CatsServiceEndpoints {
	private static final String GET_TARGET_NAMESPACE = "http://ru/acs/webservices/getCatServices";
	private static final String SAVE_TARGET_NAMESPACE = "http://ru/acs/webservices/saveCatServices";

	@Resource(name = "CatService")
	private CatsService catService;

	public Cat convertEntityToModel(CatEntity catEntity) {
		Cat cat = new Cat();
		cat.setId(catEntity.getId());
		cat.setUser(catEntity.getUser());
		cat.setDescription(catEntity.getDescription());
		cat.setContent(new String(catEntity.getContent()));
		return cat;

	}

	public CatEntity convertModelToEntity(Cat cat) {
		CatEntity catEntity = new CatEntity();
		catEntity.setId(cat.getId());
		catEntity.setUser(cat.getUser());
		catEntity.setDescription(cat.getDescription());
		catEntity.setContent(cat.getContent().getBytes());
		return catEntity;
	}

	@PayloadRoot(localPart = "GetCatRequest", namespace = GET_TARGET_NAMESPACE)
	public @ResponsePayload GetCatResponse getCatDetails(
			@RequestPayload GetCatRequest request) throws Exception {
		System.out.println("Try Get Cat !");
		CatEntity cat = catService.findById(request.getId());
		if (cat != null) {
			GetCatResponse response = new GetCatResponse();
			response.setCatDetails(convertEntityToModel(cat));
			System.out.println("Get cat successfully!");
			return response;
		} else {
			throw new Exception("Cat with this id not found!");
		}
	}

	@PayloadRoot(localPart = "SaveCatRequest", namespace = SAVE_TARGET_NAMESPACE)
	public @ResponsePayload SaveCatResponse saveCatDetails(
			@RequestPayload SaveCatRequest request) throws Exception {
		System.out.println("Try save cat !");
		Integer catId = request.getCatDetails().getId();
		SaveCatResponse response = new SaveCatResponse();
		if (catId != null && catService.findById(catId) != null) {
			response.setId(catService.update(convertModelToEntity(request
					.getCatDetails())));
			System.out
					.println("Update cat with id:" + catId + " successfully!");

		} else {
			response.setId(catService.create(convertModelToEntity(request
					.getCatDetails())).getId());
			System.out
					.println("Create cat with id:" + response.getId() +" successfully!");

		}
		return response;
	}

	public void setUserService(CatsService catService_i) {
		this.catService = catService_i;
	}
}
