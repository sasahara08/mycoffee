package com.example.my_coffee_list.service;

import org.springframework.stereotype.Service;

import com.example.my_coffee_list.entity.Bean;
import com.example.my_coffee_list.repository.BeanRepository;

@Service
public class BeanService {

  private BeanRepository beanRepository;

  public BeanService(BeanRepository beanRepository) {
    this.beanRepository = beanRepository;
  }

  // 豆の名前で検索、無ければDBに追加して検索。
  public Bean beanSearch(String name) {
    Bean searchBean = beanRepository.findByName(name);

    if (searchBean == null || searchBean.getName().isEmpty()) {
      System.out.println("null");
      Bean bean = new Bean();
      bean.setName(name);
      System.out.println(bean);
      beanRepository.save(bean);
      return beanRepository.findByName(name);
    } else {
      return searchBean;
    }
  }

}
