package com.nick.wood.graphics_library_3d.objects.mesh_objects;

import com.nick.wood.graphics_library_3d.Material;
import com.nick.wood.graphics_library_3d.Vertex;
import com.nick.wood.maths.objects.matrix.Matrix4f;
import com.nick.wood.maths.objects.vector.Vec2d;
import com.nick.wood.maths.objects.vector.Vec2f;
import com.nick.wood.maths.objects.vector.Vec3f;
import org.lwjgl.system.CallbackI;

import java.util.Arrays;

public class Terrain implements MeshObject {

	private final Material material;
	private final double[][] terrainHeightMap;
	private final Mesh mesh;
	private final double cellSpace;
	private final int width;
	private final int height;

	// package private so you have to use builder so builder can build mesh's when open gl is initialised
	Terrain(double[][] terrainHeightMap, String texture, double cellSpace) {
		super();
		this.terrainHeightMap = terrainHeightMap;
		this.material = new Material(texture);
		this.cellSpace = cellSpace;
		this.width = terrainHeightMap.length;
		this.height = terrainHeightMap[0].length;
		this.mesh = buildMesh(terrainHeightMap, cellSpace);
	}

	private Mesh buildMesh(double[][] terrainHeightMap, double cellSpace) {

		// set up data for mesh
		Vertex[] vertex = new Vertex[terrainHeightMap.length * terrainHeightMap[0].length];
		int[] indices = new int[
				(terrainHeightMap.length - 1) *
						(terrainHeightMap[0].length - 1) *
						6
				];

		// make all vertex's
		for (int y = 0; y < terrainHeightMap.length; y++) {
			for (int x = 0; x < terrainHeightMap[y].length; x++) {

				// tex coord based on height
				Vec2f texCoord = new Vec2f(0.2f, 0.2f);
				if (terrainHeightMap[x][y] < -0) {
					texCoord = new Vec2f(0.7f, 0.7f);
				}
				else if (terrainHeightMap[x][y] < 30) {
					texCoord = new Vec2f(0.7f, 0.2f);
				}

				vertex[y * width + x] =
						new Vertex(
								new Vec3f((float) cellSpace * x, (float) cellSpace * y, (float) terrainHeightMap[x][y]),
								texCoord,
								Vec3f.Z);
			}
		}

		// now go through and fix all normals.
		// normals should be the normal of the plane made by the 4 vertex around the point that make a square
		// start one in and end one before the ends
		for (int y = 1; y < terrainHeightMap.length - 1; y++) {
			for (int x = 1; x < terrainHeightMap[y].length - 1; x++) {

				// up
				Vec3f up = vertex[(y - 1) * width + x].getPos();

				// down
				Vec3f down = vertex[(y + 1) * width + x].getPos();

				// left
				Vec3f left = vertex[y * width + x - 1].getPos();

				// right
				Vec3f right = vertex[y * width + x + 1].getPos();

				Vec3f normalOne = normalOfTriangle(up, down, left);
				Vec3f normalTwo = normalOfTriangle(down, up, right);

				vertex[y * width + x].setNormal(normalOne.add(normalTwo).scale(0.5f).normalise());

			}
		}

		// now do indices
		int indexCount = 0;
		for (int y = 0; y < terrainHeightMap.length - 1; y++) {
			for (int x = 0; x < terrainHeightMap[y].length - 1; x++) {

				// by each square
				// top left vertex
				indices[indexCount++] = (y*width) + x;

				// top right
				indices[indexCount++] = (y*width) + x + 1;

				// bottom left
				indices[indexCount++] = ((y+1)*width) + x;

				// top right
				indices[indexCount++] = (y*width) + x + 1;

				// bottom right
				indices[indexCount++] = ((y+1)*width) + x + 1;

				// bottom left
				indices[indexCount++] = ((y+1)*width) + x;

			}
		}

		return new Mesh(vertex, indices, material, false);

	}

	private Vec3f normalOfTriangle(Vec3f a, Vec3f b, Vec3f c) {
		// normal of triangle one (up. down, left)
		// line from down to up
		Vec3f ab = b.subtract(a);
		// line fro down to left
		Vec3f ac = c.subtract(a);
		// normal is cross prod
		return ab.cross(ac);
	}

	@Override
	public Mesh getMesh() {
		return mesh;
	}

	@Override
	public Matrix4f getMeshTransformation() {
		return Matrix4f.Identity;
	}

	@Override
	public String getStringToCompare() {
		return "TERRAIN" + Arrays.deepHashCode(terrainHeightMap) + cellSpace;
	}

}