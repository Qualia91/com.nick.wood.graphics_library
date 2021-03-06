package com.boc_dev.graphics_library.objects.mesh_objects;

import com.boc_dev.graphics_library.objects.mesh_objects.renderer_objects.OpenGlMesh;
import com.boc_dev.graphics_library.objects.mesh_objects.renderer_objects.RendererObject;
import com.boc_dev.graphics_library.objects.text.Character;
import com.boc_dev.graphics_library.objects.text.CharacterData;
import com.boc_dev.graphics_library.objects.text.FontAlignment;
import com.boc_dev.maths.objects.vector.Vec2f;
import com.boc_dev.maths.objects.vector.Vec3f;

public class BasicMeshCreator {

	public Mesh createCircle(int numberOfPointsAroundEdge, RendererObject rendererObject) {

		Vertex[] vertexArray = new Vertex[numberOfPointsAroundEdge + 2];

		// put the center as 0
		vertexArray[numberOfPointsAroundEdge + 1] = new Vertex(
				Vec3f.ZERO,
				new Vec2f(0.5f, 0.5f),
				Vec3f.X.neg(),
				Vec3f.Y.neg(),
				Vec3f.Z.neg()
		);

		double angleStep = (Math.PI * 2) / numberOfPointsAroundEdge;
		// plus one so we have a vertex at the end to to make the next loop easier
		for (int i = 0; i < numberOfPointsAroundEdge + 1; i++) {
			float y = (float) (0.5 * Math.sin(angleStep * i));
			float z = (float) (0.5 * Math.cos(angleStep * i));
			vertexArray[i] = new Vertex(
					new Vec3f(0, y, z),
					new Vec2f(-y + 0.5f, -z + 0.5f),
					Vec3f.X.neg(),
					Vec3f.Y.neg(),
					Vec3f.Z.neg()
			);
		}

		// now do indices
		int[] indexArray = new int[vertexArray.length * 3];

		for (int i = 0; i < numberOfPointsAroundEdge + 1; i++) {

			// center
			indexArray[3 * i] = i % (numberOfPointsAroundEdge);

			// edge bottom
			indexArray[3 * i + 1] = (i + 1) % (numberOfPointsAroundEdge);

			// edge top
			indexArray[3 * i + 2] = numberOfPointsAroundEdge + 1;

		}

		return new SingleMesh(vertexArray, indexArray, rendererObject);

	}

	public Mesh createCube(boolean invertedNormals, RendererObject rendererObject) {
		int[] indices = new int[]{
				//Back face
				0, 1, 3,
				3, 1, 2,

				//Front face
				4, 5, 7,
				7, 5, 6,

				//Right face
				8, 9, 11,
				11, 9, 10,

				//Left face
				12, 13, 15,
				15, 13, 14,

				//Top face
				16, 17, 19,
				19, 17, 18,

				//Bottom face
				20, 21, 23,
				23, 21, 22
		};

		int normalSign = 1;
		if (invertedNormals) {
			normalSign = -1;
			int[] temp = new int[indices.length];
			for (int i = 0; i < indices.length; i++) {
				temp[i] = indices[indices.length - 1 - i];
			}
			indices = temp;
		}

		float xSeg = (float) (1.0 / 3.0);
		double xSegDouble = 1.0 / 3.0;
		float ySeg = 0.5f;

		Vertex[] vertices = new Vertex[]{

				//Front Face
				new Vertex(new Vec3f(0.5f, 0.5f, 0.5f), new Vec2f(xSeg, ySeg), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign)),
				new Vertex(new Vec3f(0.5f, -0.5f, 0.5f), new Vec2f(0, ySeg), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign)),
				new Vertex(new Vec3f(0.5f, -0.5f, -0.5f), new Vec2f(0, 1), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign)),
				new Vertex(new Vec3f(0.5f, 0.5f, -0.5f), new Vec2f(xSeg, 1), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign)),

				//Back Face
				new Vertex(new Vec3f(-0.5f, 0.5f, -0.5f), new Vec2f(xSeg, ySeg), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign)),
				new Vertex(new Vec3f(-0.5f, -0.5f, -0.5f), new Vec2f((float) (xSegDouble * 2), ySeg), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign)),
				new Vertex(new Vec3f(-0.5f, -0.5f, 0.5f), new Vec2f((float) (xSegDouble * 2), 0), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign)),
				new Vertex(new Vec3f(-0.5f, 0.5f, 0.5f), new Vec2f(xSeg, 0), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign)),

				//Bottom Face
				new Vertex(new Vec3f(0.5f, 0.5f, -0.5f), new Vec2f((float) (xSegDouble * 2), ySeg), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign)),
				new Vertex(new Vec3f(0.5f, -0.5f, -0.5f), new Vec2f((float) (xSegDouble * 2), 1), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign)),
				new Vertex(new Vec3f(-0.5f, -0.5f, -0.5f), new Vec2f(1, 1), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign)),
				new Vertex(new Vec3f(-0.5f, 0.5f, -0.5f), new Vec2f(1, ySeg), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign), Vec3f.Y.scale(-normalSign)),

				//Top Face
				new Vertex(new Vec3f(-0.5f, 0.5f, 0.5f), new Vec2f(xSeg, ySeg), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign)),
				new Vertex(new Vec3f(-0.5f, -0.5f, 0.5f), new Vec2f(xSeg, 1), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign)),
				new Vertex(new Vec3f(0.5f, -0.5f, 0.5f), new Vec2f((float) (xSegDouble * 2), 1), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign)),
				new Vertex(new Vec3f(0.5f, 0.5f, 0.5f), new Vec2f((float) (xSegDouble * 2), ySeg), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign), Vec3f.Y.scale(normalSign)),

				//Left Face
				new Vertex(new Vec3f(0.5f, 0.5f, 0.5f), new Vec2f(0, 0), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign)),
				new Vertex(new Vec3f(0.5f, 0.5f, -0.5f), new Vec2f(0, ySeg), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign)),
				new Vertex(new Vec3f(-0.5f, 0.5f, -0.5f), new Vec2f(xSeg, ySeg), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign)),
				new Vertex(new Vec3f(-0.5f, 0.5f, 0.5f), new Vec2f(xSeg, 0), Vec3f.Y.scale(normalSign), Vec3f.Z.scale(normalSign), Vec3f.X.scale(normalSign)),

				//Right Face
				new Vertex(new Vec3f(-0.5f, -0.5f, 0.5f), new Vec2f((float) (xSegDouble * 2), 0.0f), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign)),
				new Vertex(new Vec3f(-0.5f, -0.5f, -0.5f), new Vec2f((float) (xSegDouble * 2), ySeg), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign)),
				new Vertex(new Vec3f(0.5f, -0.5f, -0.5f), new Vec2f(1.0f, ySeg), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign)),
				new Vertex(new Vec3f(0.5f, -0.5f, 0.5f), new Vec2f(1.0f, 0.0f), Vec3f.Y.scale(-normalSign), Vec3f.Z.scale(-normalSign), Vec3f.X.scale(-normalSign))};

		return new SingleMesh(vertices, indices, rendererObject);
	}

	public Mesh createSquare(RendererObject rendererObject) {
		Vertex[] vertices = new Vertex[]{
				new Vertex(new Vec3f(0.0f, -0.5f, 0.5f), new Vec2f(1.0f, 0.0f), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg()),
				new Vertex(new Vec3f(0.0f, 0.5f, 0.5f), new Vec2f(0.0f, 0.0f), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg()),
				new Vertex(new Vec3f(0.0f, 0.5f, -0.5f), new Vec2f(0.0f, 1.0f), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg()),
				new Vertex(new Vec3f(0.0f, -0.5f, -0.5f), new Vec2f(1.0f, 1.0f), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg())};
		int[] indices = new int[]{
				0, 1, 2,
				3, 0, 2
		};
		return new SingleMesh(vertices, indices, rendererObject);
	}

	public Mesh createText(RendererObject rendererObject, String text, CharacterData characterData, float fontSize, FontAlignment fontAlignment) {

		int cursorX = 0;
		int cursorY = 0;

		// loop over letters
		char[] chars = text.toCharArray();

		Vertex[] vertices = new Vertex[chars.length * 4];
		int[] indices = new int[chars.length * 6];

		int vertexStartNum = 0;
		int indexStartNum = 0;

		float base = 1;
		float textWidth = 0;

		for (char letter : chars) {

			Character character = characterData.getCharacter(letter);

			if (character == null) {
				character = characterData.getCharacter(35);
			}

			base = character.getBase();

			float x = cursorX + character.getxOffset();
			float y = cursorY + character.getyOffset();
			float maxX = x + character.getSizeX();
			float maxY = y + character.getSizeY();

			x *= fontSize/character.getBase();
			y *= fontSize/character.getBase();
			maxX *= fontSize/character.getBase();
			maxY *= fontSize/character.getBase();

			// now center the values
			float properX = (2 * x) - 1;
			float properY = (-2 * y) + 1;
			float properMaxX = (2 * maxX) - 1;
			float properMaxY = (-2 * maxY) + 1;

			// texture coords
			float xTex = character.getxTextureCoord();
			float yTex = character.getyTextureCoord();
			float maxXTex = character.getxMaxTextureCoord();
			float maxYTex = character.getyMaxTextureCoord();

			vertices[vertexStartNum + 0] = new Vertex(new Vec3f(0.0f, -properX, -properY), new Vec2f(xTex, yTex), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg());
			vertices[vertexStartNum + 1] = new Vertex(new Vec3f(0.0f, -properX, -properMaxY), new Vec2f(xTex, maxYTex), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg());
			vertices[vertexStartNum + 2] = new Vertex(new Vec3f(0.0f, -properMaxX, -properMaxY), new Vec2f(maxXTex, maxYTex), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg());
			vertices[vertexStartNum + 3] = new Vertex(new Vec3f(0.0f, -properMaxX, -properY), new Vec2f(maxXTex, yTex), Vec3f.X.neg(), Vec3f.Y.neg(), Vec3f.Z.neg());

			indices[indexStartNum + 0] = 0 + vertexStartNum;
			indices[indexStartNum + 1] = 1 + vertexStartNum;
			indices[indexStartNum + 2] = 2 + vertexStartNum;
			indices[indexStartNum + 3] = 2 + vertexStartNum;
			indices[indexStartNum + 4] = 3 + vertexStartNum;
			indices[indexStartNum + 5] = 0 + vertexStartNum;

			vertexStartNum += 4;
			indexStartNum += 6;
			cursorX += character.getxAdvance();

			textWidth = properMaxX;
		}

		// alignment
		float startPos;
		switch (fontAlignment) {
			case END:

				for (Vertex vertex : vertices) {
					vertex.setPos(new Vec3f(
							vertex.getPos().getX(),
							vertex.getPos().getY() + textWidth,
							vertex.getPos().getZ()));
				}
				break;
			case CENTER:

				for (Vertex vertex : vertices) {
					vertex.setPos(new Vec3f(
							vertex.getPos().getX(),
							vertex.getPos().getY() + (textWidth/2.0f),
							vertex.getPos().getZ()));
				}
				break;
			default:
				break;
		}

		return new SingleMesh(vertices, indices, rendererObject);
	}


	public Mesh createMarchingMeshMap(RendererObject rendererObject, float[][] terrainHeightMap, double cellSpace) {

		// set up data for mesh
		int width = terrainHeightMap.length;
		Vertex[] vertex = new Vertex[terrainHeightMap.length * terrainHeightMap[0].length];
		int[] indices = new int[
				(terrainHeightMap.length - 1) *
						(terrainHeightMap[0].length - 1) *
						6
				];

		// make all vertex's
		for (int x = 0; x < terrainHeightMap.length; x++) {
			for (int y = 0; y < terrainHeightMap[x].length; y++) {

				vertex[y * width + x] =
						new Vertex(
								new Vec3f((float) cellSpace * x, (float) cellSpace * y, terrainHeightMap[x][y]),
								new Vec2f(1 - ((float) y / terrainHeightMap[y].length), 1 - ((float) x / terrainHeightMap.length)),
								Vec3f.Z,
								Vec3f.Z,
								Vec3f.Z);
			}
		}

		// now go through and fix all normals.
		// normals should be the normal of the plane made by the 4 vertex around the point that make a square
		// start one in and end one before the ends
		// make the edges have the same as the next lines in
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

				Vec3f norm = normalOne.add(normalTwo).scale(0.5f).normalise();

				vertex[y * width + x].setNormal(norm);
				vertex[y * width + x].setTangent(up);
				vertex[y * width + x].setBitangent(left);

				if (y == 1) {
					vertex[x].setNormal(norm);
					vertex[x].setTangent(up);
					vertex[x].setBitangent(left);
				}

				if (x == 1) {
					vertex[y * width].setNormal(norm);
					vertex[y * width].setTangent(up);
					vertex[y * width].setBitangent(left);
				}

				if (y == terrainHeightMap.length - 2) {
					vertex[(y + 1) * width + x].setNormal(norm);
					vertex[(y + 1) * width + x].setTangent(up);
					vertex[(y + 1) * width + x].setBitangent(left);
				}

				if (x == terrainHeightMap[y].length - 2) {
					vertex[y * width + x + 1].setNormal(norm);
					vertex[y * width + x + 1].setTangent(up);
					vertex[y * width + x + 1].setBitangent(left);
				}
			}
		}

		// now do indices
		int indexCount = 0;
		for (int y = 0; y < terrainHeightMap.length - 1; y++) {
			for (int x = 0; x < terrainHeightMap[y].length - 1; x++) {

				// by each square
				// top left vertex
				indices[indexCount++] = (y * width) + x;

				// top right
				indices[indexCount++] = (y * width) + x + 1;

				// bottom left
				indices[indexCount++] = ((y + 1) * width) + x;

				// top right
				indices[indexCount++] = (y * width) + x + 1;

				// bottom right
				indices[indexCount++] = ((y + 1) * width) + x + 1;

				// bottom left
				indices[indexCount++] = ((y + 1) * width) + x;

			}
		}

		return new SingleMesh(vertex, indices, rendererObject);

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

	public Mesh createMarchingMeshMap(OpenGlMesh openGlMesh, Vec3f[] vertexPositions) {
		// set up data for mesh
		Vertex[] vertex = new Vertex[vertexPositions.length];
		int[] indices = new int[vertexPositions.length];

		// make all vertex's
		for (int x = 0; x < vertexPositions.length; x++) {
			vertex[x] =
					new Vertex(
							vertexPositions[x],
							new Vec2f(0, 0),
							Vec3f.Z,
							Vec3f.Z,
							Vec3f.Z);

			indices[x] = x;
		}

		// the normals
		// vertex's are in order. every 3 makes up a traingle
		// therefore, get every 3 vertex's and calc all the normals together
		for (int x = 0; x < vertexPositions.length - 3; x += 6) {

			build(vertex, x, new Vec2f(0, 0), new Vec2f(0, 1), new Vec2f(1, 1));

			if (x + 3 > vertexPositions.length) {
				continue;
			}

			build(vertex, x + 3, new Vec2f(0, 0), new Vec2f(0, 1), new Vec2f(1, 1));

		}

		return new SingleMesh(vertex, indices, openGlMesh);
	}

	private void build(Vertex[] vertex, int x, Vec2f texCoordA, Vec2f texCoordB, Vec2f texCoordC) {
		// one
		Vec3f up = vertex[x].getPos();

		// two
		Vec3f down = vertex[x + 1].getPos();

		// three
		Vec3f left = vertex[x + 2].getPos();

		Vec3f normal = normalOfTriangle(up, down, left);

		vertex[x].setNormal(normal);
		vertex[x + 1].setNormal(normal);
		vertex[x + 2].setNormal(normal);
		vertex[x].setBitangent(up);
		vertex[x + 1].setBitangent(up);
		vertex[x + 2].setBitangent(up);
		vertex[x].setTangent(left);
		vertex[x + 1].setTangent(left);
		vertex[x + 2].setTangent(left);


		vertex[x].setTextureCoord(texCoordA);
		vertex[x + 1].setTextureCoord(texCoordB);
		vertex[x + 2].setTextureCoord(texCoordC);
	}
}
