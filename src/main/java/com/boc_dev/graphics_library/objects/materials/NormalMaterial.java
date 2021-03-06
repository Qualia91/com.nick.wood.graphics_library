package com.boc_dev.graphics_library.objects.materials;

import com.boc_dev.graphics_library.objects.managers.TextureManager;
import com.boc_dev.graphics_library.Shader;
import com.boc_dev.maths.objects.vector.Vec3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.UUID;

public class NormalMaterial implements Material {

	private UUID materialID;
	private String texture;
	private String normalMap;
	private Vec3f diffuseColour;
	private Vec3f specularColour;
	private float shininess;
	private float reflectance;

	public NormalMaterial(UUID materialID, String texture, String normalMap, Vec3f diffuseColour, Vec3f specularColour, float shininess, float reflectance) {
		this.materialID = materialID;
		this.texture = texture;
		this.normalMap = normalMap;
		this.diffuseColour = diffuseColour;
		this.specularColour = specularColour;
		this.shininess = shininess;
		this.reflectance = reflectance;
	}

	public NormalMaterial(UUID materialID, String texture, String normalMap) {
		this(materialID, texture, normalMap, Vec3f.ONE, Vec3f.ONE, 1, 1);
	}

	@Override
	public void initRender(TextureManager textureManager, Shader shader) {

		// bind texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, textureManager.getTextureId(texture));

		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, textureManager.getTextureId(normalMap));
		shader.setUniform("normal_text_sampler", 1);
		shader.setUniform("material.hasNormalMap", 1);

		shader.setUniform("material.diffuse", diffuseColour);
		shader.setUniform("material.specular", specularColour);
		shader.setUniform("material.shininess", shininess);
		shader.setUniform("material.reflectance", reflectance);
	}

	@Override
	public void endRender() {
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
}
