package com.ywl.redis.service;

import java.util.List;

import com.ywl.redis.vo.DynamicVO;
import com.ywl.redis.vo.PhoneVO;

public interface PhoneService {

	Boolean buyPhone(int phoneId);

	List<PhoneVO> getPhoneList();

	List<DynamicVO> getDynamicList();

	int phoneRank(int phoneId);

	Boolean clear();

	void initCache();
}
