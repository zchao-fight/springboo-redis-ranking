package com.ywl.redis.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ywl.redis.model.Phone;
import com.ywl.redis.service.PhoneService;
import com.ywl.redis.vo.DynamicVO;
import com.ywl.redis.vo.PhoneVO;

//@Controller
@RestController
public class PhoneController {
	List<Phone> phones = Arrays.asList(new Phone(1, "苹果"), new Phone(2, "小米"), new Phone(3, "华为"), new Phone(4, "一加"),
			new Phone(5, "vivo"), new Phone(6, "oppo"));

	@Autowired
	private PhoneService phoneService;

	@RequestMapping("/buy/{phoneId}")
	public String buyPhone(@PathVariable int phoneId) {
		Boolean buyPhone = phoneService.buyPhone(phoneId);
		if (buyPhone) {
			return "购买成功";
		} else {
			return "购买失败";
		}

	}

	@RequestMapping("/getPhoneAll")
	public List<PhoneVO> demo1() {
		List<PhoneVO> list = phoneService.getPhoneList();
		return list;
	}

	@RequestMapping("/")
	public Map<String, Object> home() {
		Map<String, Object> map = new HashMap<>();
		for (Phone phone : phones) {
			int ranking = phoneService.phoneRank(phone.getId()) + 1;
			phone.setRanking(ranking == 0 ? "榜上无名" : "销量排名：" + ranking);
		}

//		ModelAndView view = new ModelAndView("index");
//		view.addObject("phones", phones);
		map.put("phones", phones);

		List<PhoneVO> phbList = phoneService.getPhoneList();
		List<DynamicVO> dynamicList = phoneService.getDynamicList();
		map.put("dynamicList", dynamicList);
		map.put("phoneList", phbList);
//		view.addObject("dynamicList", dynamicList);
//		view.addObject("phbList", phbList);
		return map;
	}

	@RequestMapping("/rank/{phoneId}")
	public int getPhoneRank(@PathVariable int phoneId) {
		int rank = phoneService.phoneRank(phoneId) + 1;
		return rank;
	}

	@RequestMapping("/clear")
	public String clear() {
		Boolean clear = phoneService.clear();
		if (clear) {
			return "清理成功";
		} else {
			return "清理失败";
		}
	}
}
