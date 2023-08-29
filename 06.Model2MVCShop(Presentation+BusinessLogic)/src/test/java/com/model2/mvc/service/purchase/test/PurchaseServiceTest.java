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
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
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
		purchase.setDivyAddr("서울");
		purchase.setDivyRequest("빨리");
		purchase.setTranCode("100");
		purchase.setDivyDate("23/08/30");
		
		purchaseService.addPurchase(purchase);
		
		//==> console 확인
		//System.out.println(purchase);
		
	}
	
	//@Test
	public void testGetPurchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		purchase = purchaseService.getPurchase(10003);

		//System.out.println(user);
		
		//==> API 확인
		Assert.assertEquals(10005, purchase.getPurchaseProd().getProdNo());
		Assert.assertEquals("user05", purchase.getBuyer().getUserId());
		Assert.assertEquals("SCOTT", purchase.getReceiverName());
		Assert.assertEquals("111-1111", purchase.getReceiverPhone());
		Assert.assertEquals("서울", purchase.getDivyAddr());
		Assert.assertEquals("빨리", purchase.getDivyRequest());
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
		purchase.setDivyAddr("부산");
		purchase.setDivyRequest("천천히");
		purchase.setDivyDate("23/09/30");
//		purchase.setTranNo(10003);
		
		purchaseService.updatePurchase(purchase);
		
		
		purchase =  purchaseService.getPurchase(10003);
		
		//==> console 확인
		System.out.println(purchase);
			
		//==> API 확인
		Assert.assertEquals("tester", purchase.getReceiverName());
		Assert.assertEquals("19191919", purchase.getReceiverPhone());
		Assert.assertEquals("부산",  purchase.getDivyAddr());
		Assert.assertEquals("천천히", purchase.getDivyRequest());
		Assert.assertEquals("23/08/30", purchase.getDivyDate());
	 }
	 
	 //==>  주석을 풀고 실행하면....
	 @Test
	 public void testGetPurchaseListAll() throws Exception{
		 
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
		//==> console 확인
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
	 	
	 	//==> console 확인
	 	//System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 }
	 
}
