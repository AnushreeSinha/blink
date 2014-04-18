package com.blink.designer.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.blink.designer.model.BaseBlinkModel;
import com.blink.designer.model.Entity;
import com.blink.util.TreeMaker;
import com.blink.util.TreeNode;
import com.google.gson.Gson;



@Path("/{entitytype}")
public class CRUDService {

	@Autowired
	private TypeRegistry typeRegistry;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	Gson gson = new Gson();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response create(String json, @PathParam("entitytype") String entityType) {
		System.err.println(json +":" + entityType);
		BaseBlinkModel baseBlinkModel =  gson.fromJson(json, typeRegistry.getType(entityType));
		System.err.print(baseBlinkModel);

		if( baseBlinkModel.getId() == 0)
			entityManager.persist(baseBlinkModel);
		else 
			entityManager.merge(baseBlinkModel);
		entityManager.flush();


		return Response.ok(gson.toJson(baseBlinkModel)).build();
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{ids}")
	public Response read(@PathParam("entitytype") String entityType, @PathParam("ids")Long id) {

		BaseBlinkModel baseBlinkModel;
		try {
			baseBlinkModel = typeRegistry.getType(entityType).newInstance();
			baseBlinkModel.setId(id);
			baseBlinkModel= entityManager.find(baseBlinkModel.getClass(), id);

		} catch (InstantiationException e) {
			e.printStackTrace();
			return Response.status(505).build();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(505).build();
		}

		return Response.ok(gson.toJson(baseBlinkModel)).build();
	}


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response readAll(@PathParam("entitytype") String entityType) {

		BaseBlinkModel baseBlinkModel;
		List<BaseBlinkModel> baseBlinkModels;
		try {
			baseBlinkModel = typeRegistry.getType(entityType).newInstance();
			baseBlinkModels= entityManager.createQuery("from "+ baseBlinkModel.getClass().getName()).getResultList();

		} catch (InstantiationException e) {
			e.printStackTrace();
			return Response.status(505).build();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return Response.status(505).build();
		}

		return Response.ok( gson.toJson(baseBlinkModels)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{ids}")
	public Response update(String json) {

		return Response.ok().build();
	}


	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{ids}")
	public Response delete(@PathParam("ids") String json) {
		return Response.ok().build();
	}


	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{parententity}/tree")
	public Response getBulkAsTree(@PathParam("entitytype") String entityType, @PathParam("parententity") String parentEntity) {

		TreeMaker treeMaker = new TreeMaker();
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();

		Class  clazz =  typeRegistry.getType(entityType);

		List<BaseBlinkModel> results = entityManager.createQuery("from " + clazz.getName() ).getResultList();
		for(BaseBlinkModel baseBlinkModel : results) {
			TreeNode treeNode = new TreeNode(baseBlinkModel.getId(), baseBlinkModel.getName(), ((Entity) baseBlinkModel).getParentPackage().getId()  ,  false, false);
			treeNodes.add(treeNode);
		}

		results = entityManager.createQuery("from " + typeRegistry.getType(parentEntity).getName() ).getResultList();
		for(BaseBlinkModel baseBlinkModel : results) {
			TreeNode treeNode = new TreeNode(baseBlinkModel.getId(), baseBlinkModel.getName(), (long) 1, false, false);
			treeNodes.add(treeNode);
		}

		treeNodes.add(new TreeNode(1l, "Package Root", (long) 0, false, false));
		return Response.ok(treeMaker.convertJSON(treeNodes)).build();
	}

	
	public TypeRegistry getTypeRegistry() {
		return typeRegistry;
	}

	public void setTypeRegistry(TypeRegistry typeRegistry) {
		this.typeRegistry = typeRegistry;
	}
}
