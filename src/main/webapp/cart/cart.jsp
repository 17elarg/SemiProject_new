<%@page import="data.dao.ProductDao"%>
<%@page import="data.dto.ProductDto"%>
<%@page import="data.dto.CartDto"%>
<%@page import="data.dao.CartDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link
	href="https://fonts.googleapis.com/css2?family=Dokdo&family=Gaegu&family=Gugi&family=Nanum+Pen+Script&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<title>Insert title here</title>
<style type="text/css">
ol li {
	display: block;
}
</style>
<script type="text/javascript">
$(function() {
	//드롭박스에서 수량 변경할 때마다 CartDao의 장바구니제품 수량 수정하는 메소드 updateCart() 실행됨.
	$("select[name=cnt]").change(function() {
		var selectTag = $(this);
		var cnt = $(this).val();
		var product_id = $(this).parent().find("#productId").val();
		var productPrice = $(this).parent().find("#productPrice").val();
		var cntXprice = $(this).parent().find("#cntXprice");
		var totalCart = $("#totalCart");
		var totalPrice = $("#totalPrice").val();
		$.ajax({
			type : "get",
			dataType : "html",
			url : "updateCart.jsp",
			data : {"cnt" : cnt, "product_id" : product_id},
			success : function(data) {
				selectTag.prev().text(cnt);
				cntXprice.text(productPrice*cnt+" 원");
				totalCart.text(totalPrice+" 원");				
				//console.log("product_id: "+product_id+" / cnt: "+cnt); //콘솔에 product_id와 cnt 출력해서 확인
			}
		});
	});
	
	//장바구니에서 제품 삭제
	$("button.deleteCart").click(function(){
		
		var product_id = $(this).attr("product_id");
		
		$.ajax({
			type : "get",
			dataType : "html",
			url : "deleteCart.jsp",
			data : {"product_id" : product_id},
			success : function() {
				//새로고침
				location.reload();
			}
			
		});
	});
});

</script>
</head>
<%
//로그인 상태 확인 후 장바구니 페이지 나타내기 위해 loginok 만듦
String loginok = (String) session.getAttribute("loginok");

//CartDto에서 장바구니 리스트 출력하는 메소드 호출
CartDao dao = new CartDao();
List<CartDto> list = dao.readCart();

//ProductDao 선언. product_id로 제품이미지와 제품명 받아오기 위해
ProductDao pdao = new ProductDao();
%>
<body>
	<%
	//로그인 안되어 있을 경우 '로그인','홈으로 돌아가기' 버튼만 보임
	if (loginok != null) { //로그인 파트 완성되면 != > == 로 바꾸기
	%>
	<div>
		로그인이 필요한 서비스입니다.
		<button onclick="#">로그인</button>
		<button onclick="#">홈으로 돌아가기</button>
	</div>
	<%
	
	//로그인 되어 있을 경우 장바구니 목록 띄움
	} else {
	%>
	<form action="cartToOrderAction.jsp" method="post" class="">
		<div>
			<h2>
				장바구니에 들어있는 제품입니다. <i id="totalCart"><%=dao.totalCart()%> 원</i>
			</h2>
		</div>
		<ol>
			<!-- 장바구니 리스트 -->
			<%
			int totalC = 0;
			for (CartDto dto : list) {
			%>
			<li>
				<div class="main_img">
					<!-- 제품사진 -->
					<img src="../AppleProduct_img/<%=pdao.getProductPhoto(dto.getProduct_id())%>">
				</div>
				<div>
					<!-- 제품명&색상-->
					<span id="name" value="<%=dto.getProduct_id()%>"><%=pdao.getProductName(dto.getProduct_id())%></span>
					<span id="color">(색상: <%=dto.getColor()%>)</span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- 제품 수량 -->
					<span name="cnt" value="<%=dto.getCnt()%>"><%=dto.getCnt()%></span>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="cnt">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
					</select> <span class="rs-quantity-icon form-dropdown-chevron"></span>
					&nbsp;&nbsp;&nbsp;&nbsp;

					<!-- 제품 가격 -->
					<span id="cntXprice"><%=dto.getCnt()*dto.getPrice()%> 원</span>
					<%totalC += dto.getCnt()*dto.getPrice();%>

					<button type="button" class="deleteCart" product_id="<%=dto.getProduct_id()%>">삭제</button>

					<!-- hidden -->
					<input type="hidden" name="productName" value="<%=pdao.getProductName(dto.getProduct_id())%>">
					<input type="hidden" name="productId" id="productId" value="<%=dto.getProduct_id()%>">
					<input type="hidden" name="productPrice" id="productPrice" value="<%=dto.getPrice()%>">
					<input type="hidden" name="productPhoto" value="<%=pdao.getProductName(dto.getProduct_id())%>">
					<input type="hidden" name="productCnt" value="<%=pdao.getProductName(dto.getProduct_id())%>">

				</div>
				<div></div>
			</li>
			<%
			}
			%>
		</ol>
		<div>
			<!-- 소계, 배송, 총계, 결제버튼 -->
					<%-- <input type="hidden" name="totalPrice" id="totalPrice" value="<%=dao.totalCart()%>"> --%>
					<input type="hidden" name="totalPrice" id="totalPrice" value="<%=totalC%>">
			<table>
				<tr>
					<th>소계</th>
					<td><i id="totalCart"><%=totalC%> 원</i></td>
				</tr>
				<tr>
					<th>배송</th>
					<td>무료</td>
				</tr>
				<tr>
					<th>총계</th>
					<td><i id="totalCart"><%=totalC%> 원</i></td>
				</tr>
				<tr>
					<td colspan="2"><button type="submit" style="width: 100px;">결제</button>
					</td>
				</tr>

			</table>
		</div>
	</form>
	<%
	}
	%>
</body>
</html>

