package com.nateabaker.officecrawl.gui;

import javafx.scene.control.Cell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class GUI {

	Stage stage;
	OrthographicCamera camera;
	Table table;
	Skin skin;

	public GUI() {
		stage = new Stage();
		camera = new OrthographicCamera(160, 90);
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		/** Begin table */

		table = new Table(skin);
		table.setFillParent(true);
		table.debug();
		TextButton button = new TextButton("1", skin);
		TextButton button2 = new TextButton("2", skin);
		TextButton button3 = new TextButton("3", skin);
		TextButton button4 = new TextButton("4", skin);

		table.add("").expand(30, 10).align(Align.bottom).fill(0.8f, 0);
		table.add(button).expand(30, 10).align(Align.bottom).fill(0.8f, 0);
		table.add(button2).expand(30, 10).align(Align.bottom).fill(0.8f, 0);
		table.add(button3).expand(30, 10).align(Align.bottom).fill(0.8f, 0);
		table.add(button4).expand(30, 10).align(Align.bottom).fill(0.8f, 0);
		table.add("").expand(30, 10).align(Align.bottom).fill(0.8f, 0);

		/** End table */

		stage.addActor(table);
	}

	public void update(float delta) {
		stage.act();
		stage.draw();
	}

	public void resize(int width, int height) {
		// stage.getViewport().setScreenSize(width, height);
	}

	public void dispose() {
		stage.dispose();
	}

}
