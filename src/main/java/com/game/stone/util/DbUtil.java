package com.game.stone.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.game.stone.model.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DbUtil {
	private static List<Record> dataList=new ArrayList<>();

	public  static void initData(){
		File file = new File("record.json");
		if (file.exists() && file.isFile()) {
			try {
				FileReader reader = new FileReader(file);
				BufferedReader br = new BufferedReader(reader);
				String json = br.readLine();
				br.close();

				Gson gson = new Gson();
				dataList = gson.fromJson(json, new TypeToken<List<Record>>() {
				}.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addRecord(Record vo) {
		dataList.add(vo);
		try {
			Gson gson = new Gson();
			String json = gson.toJson(dataList);
			FileWriter writer = new FileWriter("record.json");
			BufferedWriter out = new BufferedWriter(writer);
			out.write(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Record> getTopRanking(int n) {
		Collections.sort(dataList, new Comparator<Record>() {
			@Override
			public int compare(Record o1, Record o2) {
				return o2.getScore() - o1.getScore();
			}
		});

		List<Record> list = new ArrayList<>();
		for (int i = 0; i < n && i < dataList.size(); i++) {
			list.add(dataList.get(i));
		}
		return list;
	}

}
