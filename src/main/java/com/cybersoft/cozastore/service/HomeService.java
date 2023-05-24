package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.response.CategoryResponse;
import com.cybersoft.cozastore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {


    @Autowired
    CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategory(){
        //Dữ liệu lấy được từ database
        List<CategoryEntity> list = categoryRepository.findAll();
        //Dữ liệu trả ra cho FE
        List<CategoryResponse> responseList = new ArrayList<>();
        for (CategoryEntity item : list ) {
            //Duyệt qua từng dòng dữ liệu query được từ CategorEntity
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(item.getId());
            categoryResponse.setName(item.getName());

            responseList.add(categoryResponse);
        }

        return responseList;
    }

}
