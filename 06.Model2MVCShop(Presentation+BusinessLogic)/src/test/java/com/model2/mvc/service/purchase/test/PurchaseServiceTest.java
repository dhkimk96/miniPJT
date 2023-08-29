package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	//@Test
	public void testAddPurchase() throws Exception {
		
		Purchase purchase= new Purchase();
		purchase.setPurchaseProd(productService.getProduct(10006));
		purchase.setBuyer(userService.getUser("user04"));
		purchase.setPaymentOption("1");
		purchase.setReceiverName("SCOTT");
		purchase.setReceiverPhone("111-1111");
		purchase.setDivyAddr("����");
		purchase.setDivyRequest("����");
		purchase.setTranCode("100");
		purchase.setDivyDate("23/08/30");
		
		purchaseService.addPurchase(purchase);
		
		//==> console Ȯ��
		//System.out.println(purchase);
		
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10003);

		//System.out.println(user);
		
		//==> API Ȯ��
		Assert.assertEquals(10005, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user05", purchase.getBuyer().getUserId());
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		Assert.assertEquals("111-1111", purchase.getReceiverPhone());
		Assert.assertEquals("����", purchase.getDivyAddr());
		Assert.assertEquals("����", purchase.getDivyRequest());
		Assert.assertEquals("100", purchase.getTranCode());

		Assert.assertNotNull(purchaseService.getPurchase(10000));
		Assert.assertNotNull(purchaseService.getPurchase(10001));
	}
	
	//@Test
	 public void updatePurchase() throws Exception{
		 
		Purchase purchase= purchaseService.getPurchase(10003);
		
		purchase.setPaymentOption("0");
		purchase.setReceiverName("tester");
		purchase.setReceiverPhone("19191919");
		purchase.setDivyAddr("�λ�");
		purchase.setDivyRequest("õõ��");
		purchase.setDivyDate("23/09/30");
//		purchase.setTranNo(10003);
		
		purchaseService.updatePurchase(purchase);
		
		
		purchase =  purchaseService.getPurchase(10003);
		
		//==> console Ȯ��
		System.out.println(purchase);
			
		//==> API Ȯ��
		Assert.assertEquals("tester", purchase.getReceiverName());
		Assert.assertEquals("19191919", purchase.getReceiverPhone());
		Assert.assertEquals("�λ�",  purchase.getDivyAddr());
		Assert.assertEquals("õõ��", purchase.getDivyRequest());
		Assert.assertEquals("23/08/30", purchase.getDivyDate());
	 }
	 
	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 @Test
	 public void testGetPurchaseListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("user05");
	 	map = purchaseService.getPurchaseList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console Ȯ��
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
}
