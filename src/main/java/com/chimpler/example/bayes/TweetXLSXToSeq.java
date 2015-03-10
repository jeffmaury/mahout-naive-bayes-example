/*
 * Copyright (c) 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chimpler.example.bayes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * http://www.chimpler.com
 */
public class TweetXLSXToSeq {
  private static final int CATEGORY_CELL = 4;
  private static final int URL_CELL = 9;
  private static final int TEXT_CELL = 10;
	public static void main(String args[]) throws Exception {
		if (args.length != 2) {
			System.err.println("Arguments: [input xlsx file] [output sequence file]");
			return;
		}
		String inputFileName = args[0];
		String outputDirName = args[1];
		Configuration configuration = new Configuration();
		FileSystem fs = FileSystem.get(configuration);
		Writer writer = new SequenceFile.Writer(fs, configuration, new Path(outputDirName + "/chunk-0"),
				Text.class, Text.class);
		
		int count = 0;
    InputStream is = new FileInputStream(inputFileName);
    XSSFWorkbook wk = new XSSFWorkbook(is);
    Sheet sheet = wk.getSheetAt(0);
    Text key = new Text();
    Text value = new Text();
    for(int i=2;i < sheet.getLastRowNum();++i) {
      Row row = sheet.getRow(i);
			String category = row.getCell(CATEGORY_CELL).toString();
			if ((category != null) || (category.trim().length() > 0)) {
	      String id = row.getCell(URL_CELL).toString();
	      if (id.indexOf('/') != (-1)) {
	        id = id.substring(id.lastIndexOf('/')+1);
	      }
	      String message = row.getCell(TEXT_CELL).toString();
	      key.set("/" + category + "/" + id);
	      value.set(message);
	      writer.append(key, value);
	      count++;
			}
		}
		writer.close();
		System.out.println("Wrote " + count + " entries.");
	}
}
