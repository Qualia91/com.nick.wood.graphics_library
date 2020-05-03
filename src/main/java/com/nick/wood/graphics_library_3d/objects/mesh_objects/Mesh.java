package com.nick.wood.graphics_library_3d.objects.mesh_objects;

import com.nick.wood.graphics_library_3d.Material;
import com.nick.wood.graphics_library_3d.Vertex;

public class Mesh {

	private final boolean invertedNormals;
	private MeshCommonData meshCommonData;

	public Mesh(Vertex[] vertices, int[] indices, Material material, boolean invertedNormals) {
		meshCommonData = new MeshCommonData(vertices, indices, material);
		this.invertedNormals = invertedNormals;
	}

	public void create() {
		meshCommonData.create();
	}

	public void createWithoutMaterialGen() {
		meshCommonData.createWithoutMaterialGen();
	}

	public void destroy() {
		meshCommonData.destroy();
	}

	public void destroyWithoutMaterialGen() {
		meshCommonData.destroyWithoutMaterialDes();
	}

	public Vertex[] getVertices() {
		return meshCommonData.getVertices();
	}

	public int[] getIndices() {
		return meshCommonData.getIndices();
	}

	public int getVao() {
		return meshCommonData.getVao();
	}

	public int getPbo() {
		return meshCommonData.getPbo();
	}

	public int getIbo() {
		return meshCommonData.getIbo();
	}

	public int getTbo() {
		return meshCommonData.getTbo();
	}

	public Material getMaterial() {
		return meshCommonData.getMaterial();
	}

	public int getNbo() {
		return meshCommonData.getNbo();
	}

	public void initRender() {
		meshCommonData.initRender();
	}

	public void endRender() {
		meshCommonData.endRender();
	}

	public int getVertexCount() {
		return meshCommonData.getVertexCount();
	}

	public boolean isInvertedNormals() {
		return invertedNormals;
	}
}
