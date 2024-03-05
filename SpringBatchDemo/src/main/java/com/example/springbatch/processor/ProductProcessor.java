package com.example.springbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.springbatch.model.Product;

public class ProductProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {

		Double prodCost = item.getProdCost();

		item.setProdDisc(prodCost * 5 / 100);
		item.setProdGST(prodCost * 10 / 100);
		return item;
	}

}
