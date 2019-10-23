package com.learn.controller;

import com.learn.config.CustomMultiThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dshuyou
 * @date 2019/10/17 23:27
 */
@Controller
@RequestMapping(value="/multithreading")
public class CustomThreadController {
	@Autowired
	private CustomMultiThreadService customMultiThreadingService;

	@ResponseBody
	@RequestMapping(value="/dotask")
	public String doTask() {
		for (int i=0;i<10;i++){
			customMultiThreadingService.executeAysncTask1(i);
			customMultiThreadingService.executeAsyncTask2(i);
		}

		return "success";
	}
}

