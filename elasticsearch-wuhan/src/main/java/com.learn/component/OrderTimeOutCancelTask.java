package com.learn.component;

import com.learn.elasticsearch.Document;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.mapper.CommentMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/21 13:06
 * @Created by dshuyou
 */
@Component
public class OrderTimeOutCancelTask {
	private Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private RestHighLevelClient client;

	/**
	 * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
	 * 每10分钟扫描一次
	 */
	@Scheduled(cron = "0 0/10 * ? * ?")
	private void cancelTimeOutOrder() {
		/*List<Map<String,Object>> list = commentMapper.findAll();
		List<SourceEntity> queries = new ArrayList<>();
		for (int i = 0;i<list.size();i++){
			SourceEntity sourceEntity = new SourceEntity();
			sourceEntity.setSource(list.get(i));
			sourceEntity.setId(String.valueOf(list.get(i).get("id")));
			queries.add(sourceEntity);
		}
		Document document = new Document(client);
		try {
			LOGGER.info("开始导入数据");
			document.bulkIndex("comment",queries);
			LOGGER.info("成功导入数据");
		} catch (IOException e) {
			LOGGER.info("导入失败");
		}*/
	}
}
