package com.ywl.redis.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ywl.redis.model.Phone;
import com.ywl.redis.service.PhoneService;
import com.ywl.redis.util.Constants;
import com.ywl.redis.util.StringUtil;
import com.ywl.redis.vo.DynamicVO;
import com.ywl.redis.vo.PhoneVO;

@Service
public class PhoneServiceImpl implements PhoneService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	List<Phone> phones = Arrays.asList(new Phone(1, "苹果"), new Phone(2, "小米"), new Phone(3, "华为"), new Phone(4, "一加"),
			new Phone(5, "vivo"), new Phone(6, "oppo"));

	@Override
	public Boolean buyPhone(int phoneId) {
		//		修改变量中的元素的分值。模拟购买，点一下，值 +1
		Double result = redisTemplate.opsForZSet().incrementScore(Constants.SALES_LIST, String.valueOf(phoneId), 1);
		long currentTimeMillis = System.currentTimeMillis();
		String msg = currentTimeMillis + Constants.separator + phones.get(phoneId - 1).getName();
		//		在左边元素添加值
		redisTemplate.opsForList().leftPush(Constants.BUY_DYNAMIC, msg);
		return result > 0;
	}

	@Override
	public List<PhoneVO> getPhoneList() {
		// 排序 从高到低 取前5位 可以获取 value score
		Set<TypedTuple<Object>> phonTuples = redisTemplate.opsForZSet().reverseRangeWithScores(Constants.SALES_LIST, 0,
				4);
		// 获取前五个排名 ，但是 无法获取 score
        Set<Object> objects = redisTemplate.opsForZSet().reverseRangeByScore(Constants.SALES_LIST, 0, 4);

        // 获取排名
        Long rank = redisTemplate.opsForZSet().reverseRank(Constants.SALES_LIST, "1");

        List<PhoneVO> list = new ArrayList<PhoneVO>();
		for (TypedTuple<Object> typedTuple : phonTuples) {
			PhoneVO vo = new PhoneVO();
			int phoneId = Integer.parseInt((String) typedTuple.getValue());
			vo.setName(phones.get(phoneId - 1).getName());
			vo.setSales(typedTuple.getScore());
			list.add(vo);
		}
		return list;
	}

	@Override
	public List<DynamicVO> getDynamicList() {
		List<DynamicVO> dynamicList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			//	获取集合指定位置的值
			String result = (String) redisTemplate.opsForList().index(Constants.BUY_DYNAMIC, i);
			if (StringUtils.isEmpty(result)) {
				break;
			}
			String[] arr = result.split(Constants.separator);
			Long time = Long.valueOf(arr[0]);
			String phone = arr[1];
			DynamicVO vo = new DynamicVO();
			vo.setPhone(phone);
			vo.setTime(StringUtil.showTime(new Date(time)));
			dynamicList.add(vo);
		}
		//	截取集合的长度
		redisTemplate.opsForList().trim(Constants.BUY_DYNAMIC, 0, 19);
		return dynamicList;
	}

	@Override
	public int phoneRank(int phoneId) {
		//	从高到低排序，获取元素的索引 从 0 开始
		Long rank = redisTemplate.opsForZSet().reverseRank(Constants.SALES_LIST, String.valueOf(phoneId));
		return rank == null ? 0 : rank.intValue();
	}

	@Override
	public Boolean clear() {
		// 删除键
		Boolean buy = redisTemplate.delete(Constants.BUY_DYNAMIC);
		Boolean sale = redisTemplate.delete(Constants.SALES_LIST);
		if (buy == true && sale == true) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void initCache() {
		// TODO Auto-generated method stub

	}

}
